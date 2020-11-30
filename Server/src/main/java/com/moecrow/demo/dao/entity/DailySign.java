package com.moecrow.demo.dao.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;

/**
 * @author willz
 * @date 2020.11.16
 */
@Data
@Builder
@Document
@CompoundIndex(def = "{'userId': 1, 'date': 1}")
public class DailySign {
    private String userId;

    private Calendar date;
}
