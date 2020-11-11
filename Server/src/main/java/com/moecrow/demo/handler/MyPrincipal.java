package com.moecrow.demo.handler;

import java.security.Principal;

/**
 * @author willz
 * @date 2020.11.11
 */
public class MyPrincipal implements Principal {
    private String loginName;

    public MyPrincipal(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String getName() {
        return loginName;
    }
}
