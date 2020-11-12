package com.moecrow.demo.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author willz
 * @date 2020.11.12
 */
@Data
@Builder
public class BattleResultMessage {
    private Boolean success;

    private List<String> process;

    private String rewardMessage;
}
