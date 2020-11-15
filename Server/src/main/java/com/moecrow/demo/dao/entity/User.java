package com.moecrow.demo.dao.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author willz
 * @date 2020.11.13
 */
@Data
@Builder
@Document
public class User {
    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String token;

    private String password;

    private Integer money;

    private Integer credit;

    private Integer level;

    private Integer bonus;

    private Integer experiences;

    private Date createdTime;

    private Date lastLogin;
}
