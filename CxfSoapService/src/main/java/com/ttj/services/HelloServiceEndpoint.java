package com.ttj.services;

import com.ttj.services.hello_service.SayHelloRequest;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(serviceName = "hello-service")
public class HelloServiceEndpoint {
    @WebMethod(action="sayHello", operationName = "sayHelloRequest")
    @WebResult(name = "message")
    public String sayHello(SayHelloRequest request){
        return "Hello "+request.getUserName()+"!!!";
    }
}
