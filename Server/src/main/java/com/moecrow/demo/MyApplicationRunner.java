package com.moecrow.demo;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moecrow.demo.config.GlobalDataConfig;
import com.moecrow.demo.config.data.BaseTemplateConfig;
import com.moecrow.demo.dao.entity.User;
import com.moecrow.demo.dao.reporitory.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author willz
 * @date 2020.11.12
 */
@Log
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GlobalDataConfig globalDataConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            long start = System.currentTimeMillis();
            File configDir = ResourceUtils.getFile("classpath:config");
            File[] configFiles = configDir.listFiles();

            AtomicInteger countConfig = new AtomicInteger();

            for (File configFile : configFiles) {
                InputStream inputStream = new FileInputStream(configFile);
                String resource = new Scanner(inputStream).useDelimiter("\\Z").next();
                JsonObject json = new JsonParser().parse(resource).getAsJsonObject();

                for (String key : json.keySet()) {
                    String className = "com.moecrow.demo.config.data." + key;
                    try {
                        Class<?> clazz = Class.forName(className);

                        Object singleConfig = new Gson().fromJson(json.get(key), clazz);

                        Method setter = GlobalDataConfig.class.
                                getMethod("set" + key, clazz);
                        setter.invoke(globalDataConfig, singleConfig);
                        countConfig.getAndIncrement();
                    } catch (ClassNotFoundException e) {
                        log.warning("specified configuration class is not defined: " + className);
                    } catch (NoSuchMethodException e) {
                        log.warning("specified configuration setter is not defined: " +
                                "com.moecrow.demo.config.GlobalDataConfig.set" + key);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            long diff = System.currentTimeMillis() - start;

            log.info(countConfig + " configurations has loaded in: " + diff + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info(globalDataConfig.getBaseTemplateConfig().get("100").getName());
        log.info(globalDataConfig.getTinyTemplateConfig().get("example_key"));

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

                    .lastBattle(new Date())
                    .bonus(0)

                    .build();

            userRepository.save(user);
        }
    }
}
