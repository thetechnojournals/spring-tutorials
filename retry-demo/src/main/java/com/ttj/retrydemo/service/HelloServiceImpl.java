package com.ttj.retrydemo.service;

import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) throws Exception {
        System.out.println("Executing sayHello");
        if("ERROR".equals(name))
            throw new Exception("Throwing exception for retry...");
        return "Hello "+name+"!";
    }
    @Override
    public String fallbackMessage(String name) {
        return name+": Couldn't say hello. Try next time.";
    }

    @Override
    public int sum(int a, int b) throws Exception {
        System.out.println("Executing sum");
        if(a+b==0)
            throw new Exception("Throwing exception for retry...");
        return a+b;
    }
    @Override
    public int fallbackSum(){
        return -1;
    }
}
