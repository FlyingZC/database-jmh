package com.sphereex.jmh.shardingsphere5.code.federation;

import com.sphereex.jmh.shardingsphere5.ShardingSpheres;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class BaseFederationTest {
    
    protected static final DataSource DATA_SOURCE;
    
    protected static final String sqlFilePath;

    public static final String SHARDING_SPHERE_DRIVER = "org.apache.shardingsphere.driver.ShardingSphereDriver";
    
    static {
        Properties props = PropsUtil.loadProperties(System.getProperty("configFilePath"));
        String driverClassName = props.getProperty("driverClassName");
        String url = props.getProperty("url");
        String userName = props.getProperty("userName");
        String password = props.getProperty("password");
        int maxPoolSize = Integer.valueOf(props.getProperty("maxPoolSize"));
        sqlFilePath = props.getProperty("sqlFilePath");
        String adapter = props.getProperty("adapter");
        if (null == adapter || "proxy".equalsIgnoreCase(adapter)) {
            System.out.println("Use proxy adapter");
            System.out.printf("driverClassName: %s, url: %s, userName: %s, password: %s, maxPoolSize: %s, sqlFilePath: %s", driverClassName, url, userName, password, maxPoolSize, sqlFilePath);
            DATA_SOURCE = ShardingSpheres.createHikariCPDataSource(driverClassName, url, userName, password, maxPoolSize);
        } else {
            System.out.println(String.format("Use jdbc adapter, jdbc yaml path: %s, driverClassName: %s, userName: %s, password: %s, maxPoolSize: %s, sqlFilePath: %s", props.getProperty("jdbcYamlPath"), driverClassName, userName, password, maxPoolSize, sqlFilePath));
            System.setProperty("shardingsphere.configurationFile", props.getProperty("jdbcYamlPath"));
            String jdbcUrl = "jdbc:shardingsphere:absolutepath:" + props.getProperty("jdbcYamlPath");
            DATA_SOURCE = ShardingSpheres.createHikariCPDataSource(SHARDING_SPHERE_DRIVER, jdbcUrl, "root", "root", maxPoolSize);
        }
        System.out.println();
    }
    
    public Connection getConnection() {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
