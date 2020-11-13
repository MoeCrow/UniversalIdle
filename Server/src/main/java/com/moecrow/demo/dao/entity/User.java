package com.moecrow.demo.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
