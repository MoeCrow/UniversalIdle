package com.moecrow.demo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * RequestMessage
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2018/2/28
 */
@Data
public class RequestMessage implements Serializable {
    private String name;

//    public String getName() {
//        return name;
//    }
}
