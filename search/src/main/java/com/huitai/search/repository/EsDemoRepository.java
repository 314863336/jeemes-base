package com.huitai.search.repository;

import com.huitai.search.base.BaseElasticsearchRepository;
import com.huitai.search.entity.EsDemo;

/**
 * description: EsDemoRepository ES数据层基础类 <br>
 * date: 2020/4/22 14:26 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
public interface EsDemoRepository extends BaseElasticsearchRepository<EsDemo, String> {
}
