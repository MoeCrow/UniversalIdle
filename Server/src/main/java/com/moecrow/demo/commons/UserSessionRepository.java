package com.moecrow.demo.commons;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author willz
 * @date 2020.11.12
 */
@Component
public class UserSessionRepository {
    Set<Object> set = new HashSet<>();
    //todo callback

    public Iterable<Object> all() {
        return set;
    }

    public void add(Object key) {
        set.add(key);
    }

    public void remove(Object key) {
        set.remove(key);
    }
}
