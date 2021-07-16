package com.huitai.search.base;

import com.huitai.core.base.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * description: BaseElasticsearchService ES服务层基础类 <br>
 * date: 2020/4/22 15:37 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
public abstract class BaseElasticsearchService {

    @Autowired
    protected ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * description: 创建索引和映射 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 14:47 <br>
     * author: PLF <br>
     */
    public void addIndex(Class<? extends BaseEntity> clazz) {
        if (!elasticsearchRestTemplate.indexExists(clazz)) {
            elasticsearchRestTemplate.createIndex(clazz);
            elasticsearchRestTemplate.putMapping(clazz);
        }
    }

    /**
     * description: 删除指定索引 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 14:47 <br>
     * author: PLF <br>
     */
    public void deleteIndex(Class<? extends BaseEntity> clazz) {
        if (elasticsearchRestTemplate.indexExists(clazz))
            elasticsearchRestTemplate.deleteIndex(clazz);
    }

}
