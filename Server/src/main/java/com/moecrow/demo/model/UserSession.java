package com.moecrow.demo.model;

import lombok.Data;
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
    private User user;
}
