package com.moecrow.demo.interceptor;

import com.moecrow.demo.commons.SpringContextUtils;
import com.moecrow.demo.handler.MyPrincipal;
import com.moecrow.demo.model.User;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.Map;

/**
 * @author willz
 * @date 2020.11.11
 */
@Log
@Component
public class MyHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        //获取参数
        String token = SpringContextUtils.getRequest().getParameter("token");

        log.info("handshake:" + token);

        if(StringUtils.isNotBlank(token)){
            log.info(MessageFormat.format("WebSocket连接开始创建Principal，用户：{0}", token));
            return new MyPrincipal(token);
        }else{
            log.severe("未登录系统，禁止连接WebSocket");
            return null;
        }
    }

}
