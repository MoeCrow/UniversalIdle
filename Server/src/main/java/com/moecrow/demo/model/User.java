package com.moecrow.demo.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author willz
 * @date 2020.11.11
 */
@Data
@Builder
public class User {
    private Integer id;

    private String userName;

    private String password;

    private String mobile;

    private String email;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}
