package com.moecrow.demo.commons;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.stereotype.Component;

/**
 * @author willz
 * @date 2020.11.12
 */
public class UserScope implements Scope {
    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        System.out.println("get " + s);
        return null;
    }

    @Override
    public Object remove(String s) {
        System.out.println("rem " + s);
        return null;
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {
        System.out.println("reg " + s);
    }

    @Override
    public Object resolveContextualObject(String s) {
        System.out.println("resolve " + s);
        return null;
    }

    @Override
    public String getConversationId() {
        System.out.println("getconv");
        return null;
    }
}
