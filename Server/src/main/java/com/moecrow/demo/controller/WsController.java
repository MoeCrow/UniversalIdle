package com.moecrow.demo.controller;

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

    @MessageMapping("/welcome")
    @SendTo("/topic/say")
    public ResponseMessage say(RequestMessage message) {
        User user = userSession.getUser();
        log.info("user:" + user.getUserName());

        return new ResponseMessage("welcome," + message.getName() + " !");
    }

    /**
     * 定时推送消息
     */
    @Scheduled(fixedRate = 1000)
    public void callback() {
        // 发现消息
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback", "定时推送消息时间: " + df.format(new Date()));
    }
}
