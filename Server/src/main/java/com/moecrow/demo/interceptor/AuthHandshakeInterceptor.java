package com.moecrow.demo.interceptor;

import com.moecrow.demo.commons.SpringContextUtils;
import com.moecrow.demo.dao.entity.User;
import com.moecrow.demo.dao.reporitory.UserRepository;
import com.moecrow.demo.commons.UserSession;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author willz
 * @date 2020.11.11
 */
@Log
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {
    private static int count = 0;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        //获取参数
        String token = SpringContextUtils.getRequest().getParameter("token");

        if (StringUtils.isBlank(token)) {
            return false;
        }

        //todo login check
        User user = userRepository.find(User.builder().token(token).build());

        if(user != null){
            log.info(MessageFormat.format("用户{0}请求建立WebSocket连接", user.getName()));

            attributes.put("user", user);

            UserSession userSession = new UserSession(userRepository);
            userSession.setUserId(user.getId());

            attributes.put("scopedTarget.userSession", userSession);

            user.setLastLogin(new Date());
            userRepository.update(user.getId(), User.builder().lastLogin(new Date()).build());
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
