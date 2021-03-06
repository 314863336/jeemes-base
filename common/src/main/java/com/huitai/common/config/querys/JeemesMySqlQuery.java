package com.huitai.common.config.querys;

import com.baomidou.mybatisplus.generator.config.querys.AbstractDbQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * description: XXX
 * date: 2020/4/26 16:05
 * author: TYJ
 * version: 1.0
 */
public class JeemesMySqlQuery extends AbstractDbQuery {

    @Override
    public String tablesSql() {
        return "show table status WHERE 1=1 ";
    }


    @Override
    public String tableFieldsSql() {
        return "show full fields from `%s`";
    }


    @Override
    public String tableName() {
        return "NAME";
    }


    @Override
    public String tableComment() {
        return "COMMENT";
    }


    @Override
    public String fieldName() {
        return "FIELD";
    }


    @Override
    public String fieldType() {
        return "TYPE";
    }


    @Override
    public String fieldComment() {
        return "COMMENT";
    }


    @Override
    public String fieldKey() {
        return "KEY";
    }


    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return "auto_increment".equals(results.getString("Extra"));
    }

    @Override
    public String[] fieldCustom() {
        return new String[]{"NULL"};
    }
}
