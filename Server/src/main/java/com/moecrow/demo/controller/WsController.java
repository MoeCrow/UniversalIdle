package com.moecrow.demo.controller;

import com.moecrow.demo.commons.UserSessionRepository;
import com.moecrow.demo.dao.entity.User;
import com.moecrow.demo.dao.reporitory.UserRepository;
import com.moecrow.demo.model.RequestMessage;
import com.moecrow.demo.model.ResponseMessage;
import com.moecrow.demo.model.UserSession;
import com.moecrow.demo.model.dto.BattleResultMessage;
import com.moecrow.demo.model.dto.BattleStartMessage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

@Log
@Controller
public class WsController {
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    UserSession userSession;

    @Autowired
    UserSessionRepository userSessionRepository;

    @Autowired
    UserRepository userRepository;

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

    @Scheduled(fixedRate = 10000)
    public void heartbeat() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (User user : userSessionRepository.all()) {
            messagingTemplate.convertAndSendToUser(String.valueOf(user.getId()),"/queue/heartbeat", "heartbeat " + df.format(new Date()));
        }
    }
}
