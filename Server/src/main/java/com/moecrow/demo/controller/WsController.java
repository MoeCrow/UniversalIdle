package com.moecrow.demo.controller;

import com.moecrow.demo.commons.UserRepository;
import com.moecrow.demo.model.RequestMessage;
import com.moecrow.demo.model.ResponseMessage;
import com.moecrow.demo.model.User;
import com.moecrow.demo.model.UserSession;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * WsController
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2018/2/28
 */
@Log
@Controller
public class WsController {
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    UserSession userSession;

    @Autowired
    UserRepository userRepository;

    @MessageMapping("/welcome")
    @SendTo("/topic/say")
    public ResponseMessage say(RequestMessage message) {
        User user = userSession.getUser();
        log.info("user:" + user.getUserName());

        return new ResponseMessage("welcome," + message.getName() + " !");
    }

    @Scheduled(fixedRate = 10000)
    public void heartbeat() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (User user : userRepository.all()) {
            messagingTemplate.convertAndSendToUser(String.valueOf(user.getId()),"/queue/heartbeat", "heartbeat " + df.format(new Date()));
        }
    }
}
