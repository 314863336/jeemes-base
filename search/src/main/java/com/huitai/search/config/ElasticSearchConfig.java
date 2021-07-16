package com.huitai.search.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * description: ElasticSearchConfig <br>
 * date: 2020/10/29 11:30 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.huitai.**.repository")
public class ElasticSearchConfig {
}
