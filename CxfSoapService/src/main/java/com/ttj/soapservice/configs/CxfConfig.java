package com.ttj.soapservice.configs;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfig {
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus(){
        SpringBus bus = new SpringBus();
        return bus;
    }

    @Bean
    public ServletRegistrationBean<CXFServlet> messageDispatcherServlet(
            ApplicationContext applicationContext, SpringBus springBus){

        CXFServlet cxfServlet = new CXFServlet();
        cxfServlet.setBus(springBus);
        return new ServletRegistrationBean<>(cxfServlet, "/*");
    }
}
