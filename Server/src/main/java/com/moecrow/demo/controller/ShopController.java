package com.moecrow.demo.controller;

import com.moecrow.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ShopController {
    @Autowired
    UserService userService;

    @SubscribeMapping("/shop/buy_some")
    public String buySomeThing() {
        if(userService.checkAndUseMoney(10)) {
            userService.addBonus(1);
            return "ok";
        }
        return "fail";
    }
}
