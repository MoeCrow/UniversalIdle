package com.moecrow.demo.handler;

import com.moecrow.demo.commons.SpringContextUtils;
import com.moecrow.demo.commons.UserRepository;
import com.moecrow.demo.model.User;
import com.moecrow.demo.model.UserSession;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.StompSubProtocolHandler;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

/**
 * @author willz
 * @date 2020.11.09
 */
@Log
public class StompWebSocketHandler extends SubProtocolWebSocketHandler {
    @Autowired
    UserRepository userRepository;

    public StompWebSocketHandler(MessageChannel clientInboundChannel, SubscribableChannel clientOutboundChannel) {
        super(clientInboundChannel, clientOutboundChannel);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        User user = (User) session.getAttributes().get("user");
        log.info("新连接建立:" + user.getUserName());
        super.afterConnectionEstablished(session);

        userRepository.add(user.getId(), user);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        User user = (User) session.getAttributes().get("user");
        log.info("连接断开:" + user.getUserName());
        super.afterConnectionClosed(session,closeStatus);

        userRepository.remove(user.getId());
    }
}
