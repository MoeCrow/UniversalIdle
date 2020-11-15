package com.moecrow.demo.handler;

import com.moecrow.demo.commons.UserSessionRepository;
import com.moecrow.demo.dao.entity.User;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

/**
 * @author willz
 * @date 2020.11.09
 */
@Log
public class StompWebSocketHandler extends SubProtocolWebSocketHandler {
    @Autowired
    UserSessionRepository userSessionRepository;

    public StompWebSocketHandler(MessageChannel clientInboundChannel, SubscribableChannel clientOutboundChannel) {
        super(clientInboundChannel, clientOutboundChannel);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        User user = (User) session.getAttributes().get("user");
        log.info("新连接建立:" + user.getName());
        super.afterConnectionEstablished(session);

        userSessionRepository.add(user.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        User user = (User) session.getAttributes().get("user");
        log.info("连接断开:" + user.getName());
        super.afterConnectionClosed(session,closeStatus);

        userSessionRepository.remove(user.getId());
    }
}
