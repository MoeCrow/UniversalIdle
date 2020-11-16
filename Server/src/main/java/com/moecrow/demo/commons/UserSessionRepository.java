package com.moecrow.demo.commons;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author willz
 * @date 2020.11.12
 */
@Component
public class UserSessionRepository {
    Set<String> set = new HashSet<>();
    //todo callback

    public Iterable<String> all() {
        return set;
    }

    public void add(String key) {
        set.add(key);
    }

    public void remove(String key) {
        set.remove(key);
    }
}
