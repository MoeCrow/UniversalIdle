package com.moecrow.demo;

import com.moecrow.demo.commons.SpringContextUtils;
import com.moecrow.demo.commons.UserScope;
import com.moecrow.demo.dao.entity.User;
import com.moecrow.demo.dao.reporitory.UserRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
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
        User user = userRepository.find("token", "123");

        if (user == null) {
            user = new User();

            user.setName("tester");
            user.setToken("123");

            userRepository.save(user);
        }
    }
}
