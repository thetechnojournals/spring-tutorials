package com.ttj.batch.tutorial;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.ComponentScan;

@EnableTask
@EnableBatchProcessing
@SpringBootApplication
@ComponentScan("com.ttj")
public class BatchTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchTutorialApplication.class, args);
	}

}
