package com.huitai.search.entity;

import com.huitai.core.base.BaseEntity;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * description: EsDemo ES示例 <br>
 * date: 2020/4/22 14:23 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
@Document(indexName = "EsDemo" , type = "_doc",shards = 2,replicas = 2)
public class EsDemo extends BaseEntity {
    @Field(type = FieldType.Text)
    private String name;

    public EsDemo() {
    }

    public EsDemo(String id, String name) {
        this.setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
