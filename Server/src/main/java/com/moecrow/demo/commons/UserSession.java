package com.moecrow.demo.commons;

import com.moecrow.demo.dao.entity.User;
import com.moecrow.demo.dao.reporitory.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author willz
 * @date 2020.11.12
 */
@Data
@Scope(value = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class UserSession {
    UserRepository userRepository;

    public UserSession(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String userId;

    public User getUser() {
        return userRepository.find(userId);
    }
}
