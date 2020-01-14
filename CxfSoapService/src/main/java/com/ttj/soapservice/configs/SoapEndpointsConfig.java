package com.ttj.soapservice.configs;

import com.ttj.services.HelloServiceEndpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class SoapEndpointsConfig {

    @Bean
    public Endpoint endpoint(SpringBus bus) {
        EndpointImpl endpoint = new EndpointImpl(bus, new HelloServiceEndpoint());
        endpoint.publish("/helloService");
        return endpoint;
    }
}
