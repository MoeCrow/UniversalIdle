package com.moecrow.demo;

import com.moecrow.demo.dao.entity.User;
import com.moecrow.demo.dao.reporitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author willz
 * @date 2020.11.12
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = userRepository.find(User.builder().token("123").build());

        if (user == null) {
            user = User.builder()
                    .name("test")
                    .token("123")
                    .createdTime(new Date())

                    .money(0)
                    .credit(0)
                    .level(1)
                    .experiences(0)

                    .bonus(0)

                    .build();

            userRepository.save(user);
        }
    }
}
