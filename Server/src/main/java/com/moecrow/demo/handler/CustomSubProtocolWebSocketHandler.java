package com.moecrow.demo.handler;

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

public class CustomSubProtocolWebSocketHandler extends SubProtocolWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomSubProtocolWebSocketHandler.class);


    public CustomSubProtocolWebSocketHandler(MessageChannel clientInboundChannel, SubscribableChannel clientOutboundChannel) {
        super(clientInboundChannel, clientOutboundChannel);
//        setDefaultProtocolHandler(new StompSubProtocolHandler());
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("New websocket connection was established");
//        sessionHandler.register(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        LOGGER.info("New websocket connection:"+session.getPrincipal().getName()+" was closed");
//        sessionHandler.remove(session);
        super.afterConnectionClosed(session,closeStatus);
    }
}
