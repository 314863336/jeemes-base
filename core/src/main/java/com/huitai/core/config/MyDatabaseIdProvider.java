package com.huitai.core.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * description: 数据库标识类
 * date: 2020/7/28 19:26
 * author: TYJ
 * version: 1.0
 */
@Component
public class MyDatabaseIdProvider implements DatabaseIdProvider {

    @Override
    public void setProperties(Properties p) {
        System.out.println(p.getProperty("Oracle"));
    }

    @Override
    public String getDatabaseId(DataSource dataSource) throws SQLException {
        Connection conn = dataSource.getConnection();
        String dbName = conn.getMetaData().getDatabaseProductName();
        String dbAlias = "";
        switch (dbName) {
            case "MySQL":
                dbAlias = "mysql";
                break;
            case "Oracle":
                dbAlias = "oracle";
                break;
            default:
                break;
        }
        return dbAlias;

    }
}
