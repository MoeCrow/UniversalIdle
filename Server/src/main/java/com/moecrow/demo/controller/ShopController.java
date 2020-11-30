package com.moecrow.demo.controller;

import com.moecrow.demo.model.RequestMessage;
import com.moecrow.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ShopController {
    @Autowired
    UserService userService;

    @SubscribeMapping("/shop/buy_some/{type}/{num}/{str}/{json}")
    public String buySomeThing(@DestinationVariable Integer type,
                               @DestinationVariable Integer num,
                               @DestinationVariable String str,
                               @DestinationVariable RequestMessage json) {
        System.out.println(str);
        System.out.println(json.getName());

        if(num > 0 && userService.checkAndUseMoney(10 * num)) {
            userService.addBonus(1 * num);
            return "ok:" + type;
        }
        return "fail";
    }
}
