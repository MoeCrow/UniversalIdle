package com.moecrow.demo.interceptor;

import com.moecrow.demo.commons.SpringContextUtils;
import com.moecrow.demo.model.User;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.Map;

/**
 * @author willz
 * @date 2020.11.11
 */
@Log
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        //获取参数
        String token = SpringContextUtils.getRequest().getParameter("token");

        if (StringUtils.isBlank(token)) {
            return false;
        }

        //todo login check
        User user = User.builder().id(1).userName(token).build();
        attributes.put("user", user);

        if(user != null){
            log.info(MessageFormat.format("用户{0}请求建立WebSocket连接", user.getUserName()));
            return true;
        }else{
            log.severe("未登录系统，禁止连接WebSocket");
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }

}
