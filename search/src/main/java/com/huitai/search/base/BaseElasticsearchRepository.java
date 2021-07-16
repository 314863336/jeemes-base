package com.huitai.search.base;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * description: BaseElasticsearchRepository ES Repository层基础类 <br>
 * date: 2020/4/22 15:39 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
public interface BaseElasticsearchRepository<T, ID> extends ElasticsearchRepository<T, ID> {
}
