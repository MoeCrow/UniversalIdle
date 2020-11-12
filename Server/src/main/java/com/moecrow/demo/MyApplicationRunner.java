package com.moecrow.demo;

import com.moecrow.demo.commons.SpringContextUtils;
import com.moecrow.demo.commons.UserScope;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author willz
 * @date 2020.11.12
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        SpringContextUtils.getBeanFactory().registerScope("user", new UserScope());
    }
}
