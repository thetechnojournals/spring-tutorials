package com.ttj.retrydemo;

import com.ttj.retrydemo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;

@EnableRetry
@SpringBootApplication
public class RetryDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetryDemoApplication.class, args);
	}

	@Autowired
	HelloService helloService;

	@EventListener(ApplicationReadyEvent.class)
	public void test()throws Exception{
		System.out.println(helloService.sayHello("ERROR"));
        System.out.println(helloService.sayHello("Black pearl"));
        System.out.println("-----------------------------------------------------");
        System.out.println("Sum: "+helloService.sum(0, 0));
        System.out.println("Sum: "+helloService.sum(10, 20));

	}
}
