package com.moecrow.demo.controller;

import com.moecrow.demo.commons.DateUtils;
import com.moecrow.demo.commons.UserSession;
import com.moecrow.demo.commons.UserSessionRepository;
import com.moecrow.demo.dao.entity.User;
import com.moecrow.demo.dao.reporitory.UserRepository;
import com.moecrow.demo.event.ServerStartedEvent;
import com.moecrow.demo.model.RequestMessage;
import com.moecrow.demo.model.ResponseMessage;
import com.moecrow.demo.model.dto.BattleResultMessage;
import com.moecrow.demo.model.dto.BattleStartMessage;
import com.moecrow.demo.model.dto.OfflineRewardMessage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.PreDestroy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Log
@Controller
public class WsController {
    private static final int MAX_OFFLINE = 3600 * 6;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    UserSession userSession;

    @Autowired
    UserSessionRepository userSessionRepository;

    @Autowired
    UserRepository userRepository;

    @EventListener
    public void started(ServerStartedEvent event) {
        log.info("Server started!");
    }

    @MessageExceptionHandler(Exception.class)
    @SendToUser("/queue/errors")
    public Exception handleExceptions(Exception e){
        e.printStackTrace();
        return e;
    }

    @MessageMapping("/battle")
    @SendToUser("/queue/battle")
    public BattleResultMessage startBattle(BattleStartMessage battleStartMessage) {
        User user = userSession.getUser();

        Random random = new Random();
        int reward = random.nextInt(10) + user.getBonus();

        userRepository.increase(user.getId(), User.builder().money(reward).experiences(reward * 10).build());
        userRepository.update(user.getId(), User.builder().lastBattle(new Date()).build());

        return BattleResultMessage.builder()
                .success(true)
                .rewardMessage(battleStartMessage.getMapId() + " finish: +" + reward)
                .build();
    }

    @MessageMapping("/welcome")
    @SendTo("/topic/say")
    public ResponseMessage say(RequestMessage message) {
        User user = userSession.getUser();
        log.info("user:" + user.getName());

        return new ResponseMessage("welcome," + message.getName() + " !");
    }

    @Scheduled(fixedRate = 5000)
    public void heartbeat() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (String id : userSessionRepository.all()) {
            User user = userRepository.find(id);
            Date lastBattle = user.getLastBattle();
            Date now = new Date();

            long offlineTime = DateUtils.getSecondsDuration(lastBattle, now);

            //minimum offline reward is one minute
            if (offlineTime > 60L) {
                if (offlineTime > MAX_OFFLINE) {
                    offlineTime = MAX_OFFLINE;
                }

                int reward = Math.round((1f + .1f * user.getBonus()) * offlineTime);

                userRepository.update(user.getId(), User.builder().lastBattle(now).build());
                userRepository.increase(user.getId(), User.builder().money(reward).experiences(reward * 10).build());

                messagingTemplate.convertAndSendToUser(id, "/queue/offline", OfflineRewardMessage.builder().data("r:" + reward).build());
            }

            messagingTemplate.convertAndSendToUser(id,"/queue/heartbeat", "heartbeat " + df.format(new Date()));
        }
    }
}
