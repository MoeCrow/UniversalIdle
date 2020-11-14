package com.moecrow.demo.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author willz
 * @date 2020.11.13
 */
@Data
@Document
public class User {
    @Id
    private String id;

    private String name;

    @Indexed(unique = true, useGeneratedName = true)
    private String token;

    private String password;

    private Integer money = 0;

    private Integer credit = 0;

    private Integer level = 1;

    private Integer experiences = 0;

    private Date createdTime = new Date();

    private Date lastLogin;
}
