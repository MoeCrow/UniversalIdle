package com.moecrow.demo.service.impl;

import com.moecrow.demo.dao.entity.User;
import com.moecrow.demo.dao.reporitory.UserRepository;
import com.moecrow.demo.commons.UserSession;
import com.moecrow.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserSession userSession;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean checkAndUseMoney(Integer money) {
        User user = userSession.getUser();

        if (user.getMoney() < money) {
            return false;
        }

        userRepository.increase(user.getId(), User.builder().money(-money).build());
        return true;
    }

    @Override
    public void addBonus(Integer bonus) {
        User user = userSession.getUser();
        userRepository.increase(user.getId(), User.builder().bonus(bonus).build());
    }
}
