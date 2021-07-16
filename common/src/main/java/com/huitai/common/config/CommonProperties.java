package com.huitai.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * description: CommonProperties <br>
 * date: 2020/4/15 17:29 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
@Configuration
@ConfigurationProperties(prefix = "common", ignoreUnknownFields = false)
@PropertySource(value = {"classpath:common.properties"})
@Component
public class CommonProperties {

    private Integer generate_id_util_number;

    private Long data_dictionary_cache_expiration_time;

    private Long system_config_cache_expiration_time;

    public Long getSystem_config_cache_expiration_time() {
        return system_config_cache_expiration_time;
    }

    public void setSystem_config_cache_expiration_time(Long system_config_cache_expiration_time) {
        this.system_config_cache_expiration_time = system_config_cache_expiration_time;
    }

    public Long getData_dictionary_cache_expiration_time() {
        return data_dictionary_cache_expiration_time;
    }

    public void setData_dictionary_cache_expiration_time(Long data_dictionary_cache_expiration_time) {
        this.data_dictionary_cache_expiration_time = data_dictionary_cache_expiration_time;
    }

    public Integer getGenerate_id_util_number() {
        return generate_id_util_number;
    }

    public void setGenerate_id_util_number(Integer generate_id_util_number) {
        this.generate_id_util_number = generate_id_util_number;
    }
}
