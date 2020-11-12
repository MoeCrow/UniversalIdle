package com.moecrow.demo.handler;

import com.moecrow.demo.model.User;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * @author willz
 * @date 2020.11.12
 */
@Component
public class UserIdHandshakeHandler extends DefaultHandshakeHandler {

    class UserIdPrincipal implements Principal {
        String id;

        public UserIdPrincipal(String id) {
            this.id = id;
        }

        @Override
        public String getName() {
            return id;
        }
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        User user = (User) attributes.get("user");
        return new UserIdPrincipal(String.valueOf(user.getId()));
    }
}
