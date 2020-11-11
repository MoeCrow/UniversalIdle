package com.moecrow.demo.interceptor;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.text.MessageFormat;

/**
 * @author willz
 * @date 2020.11.11
 */
@Log
@Component
public class MyChannelInterceptor implements ChannelInterceptor {

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        //用户已经断开连接
        if(StompCommand.DISCONNECT.equals(command)){
            String user = "";
            Principal principal = accessor.getUser();
            if(principal != null && StringUtils.isNoneBlank(principal.getName())){
                user = principal.getName();
            }else{
                user = accessor.getSessionId();
            }

            log.info(MessageFormat.format("用户{0}的WebSocket连接已经断开", user));
        }
    }

}
