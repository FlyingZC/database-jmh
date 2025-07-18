package com.sphereex.jmh.shardingsphere5;

import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShardingSpheres {
    
    public static DataSource createDataSource() {
        String configurationFile = System.getProperty("shardingsphere.configurationFile");
        System.out.println("config file path:" + configurationFile);
        try {
            return YamlShardingSphereDataSourceFactory.createDataSource(new File(configurationFile));
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static DataSource createHikariCPDataSource(String driverClassName, String url, String userName, String password, int maxPoolSize) {
        HikariDataSource result = new HikariDataSource();
        result.setDriverClassName(driverClassName);
        result.setJdbcUrl(url);
        result.setUsername(userName);
        result.setPassword(password);
        result.setMaximumPoolSize(maxPoolSize);
        return result;
    }
}
