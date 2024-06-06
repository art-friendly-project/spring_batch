package org.com.artfriendlybatch.global.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.second-datasource")
    public DataSourceProperties batchDatasourceProperties() {
        return new DataSourceProperties();
    }

    // Data source for batch jobs
    @Bean
    @ConfigurationProperties("spring.datasource.second-datasource.configuration")
    public DataSource batchDataSource() {
        return batchDatasourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public DataSourceTransactionManager batchTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(batchDataSource());
    }
}
