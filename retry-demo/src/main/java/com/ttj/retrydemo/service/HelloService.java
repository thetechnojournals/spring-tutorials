package com.ttj.retrydemo.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface HelloService {
    @Retryable(value=Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public String sayHello(String name)throws Exception;

    @Recover
    public String fallbackMessage(String name);

    @Retryable(value=Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public int sum(int a, int b)throws Exception;

    @Recover
    public int fallbackSum();
}
