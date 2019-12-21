package com.ttj.batch.tutorial.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.cloud.task.configuration.TaskConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CloudTaskConfig {
    @Bean
    public TaskConfigurer taskConfigurer(@Qualifier("batchDataSource") DataSource batchDataSource){
        return new DefaultTaskConfigurer(batchDataSource);
    }

}
