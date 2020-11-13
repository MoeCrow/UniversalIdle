package com.moecrow.demo.commons;

import com.moecrow.demo.dao.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author willz
 * @date 2020.11.12
 */
@Component
public class UserSessionRepository {
    Map<Object, User> map = new HashMap<>();
    //todo callback

    public Iterable<User> all() {
        return map.values();
    }

    public User get(Object key) {
        return map.getOrDefault(key, null);
    }

    public void add(Object key, User user) {
        map.put(key, user);
    }

    public void remove(Object key) {
        map.remove(key);
    }
}
