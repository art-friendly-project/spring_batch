package org.com.artfriendlybatch.global.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = "org.com.artfriendlybatch.domain.exhibition",
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager"
)
public class BusinessDataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.business")
    public DataSourceProperties busninessDatasourceProperties() {
        return new DataSourceProperties();
    }

    // Primary data source for business logic
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.business.configuration")
    public DataSource businessDataSource() {
        return busninessDatasourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean
    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Primary
    @Bean(name="entityManagerFactory") // 해당 이름으로 설정해야 팩토리 빈 설정이 된다
    public LocalContainerEntityManagerFactoryBean businessEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("businessDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("org.com.artfriendlybatch.domain.exhibition.entity")
                .persistenceUnit("businessPersistenceUnit")
                .build();
    }

}
