package com.moecrow.demo.config;

import com.moecrow.demo.config.data.BaseTemplateConfig;
import com.moecrow.demo.config.data.TinyTemplateConfig;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author willz
 * @date 2020.11.16
 */
@Data
@Component
public class GlobalDataConfig {
    private BaseTemplateConfig baseTemplateConfig;

    private TinyTemplateConfig tinyTemplateConfig;
}
