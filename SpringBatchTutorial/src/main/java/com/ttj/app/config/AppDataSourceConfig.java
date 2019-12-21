package com.ttj.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "appEntityManagerFactory",
        transactionManagerRef = "appTransactionManager",
        basePackages = "com.ttj.app.repository"
)
@EnableTransactionManagement
public class AppDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource appDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "appEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean appEntityManagerFactory(EntityManagerFactoryBuilder builder,
            @Qualifier("appDataSource") DataSource appDataSource){

        return builder
                .dataSource(appDataSource)
                .packages("com.ttj.app.domain")
                .persistenceUnit("app")
                .build();
    }

    @Bean(name = "appTransactionManager")
    public PlatformTransactionManager appTransactionManager(@Qualifier("appEntityManagerFactory") EntityManagerFactory
                                                                        appEntityManagerFactory) {

        return new JpaTransactionManager(appEntityManagerFactory);
    }
}
