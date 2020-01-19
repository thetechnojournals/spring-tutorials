package com.ttj.soapservice.configs;

import com.ttj.services.HelloServiceEndpoint;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.jms.JMSConfigFeature;
import org.apache.cxf.transport.jms.JMSConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.SingleConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.xml.ws.Endpoint;

@Configuration
public class JmsSoapEndpointsConfig {

    @Bean
    public Endpoint jmsEndpoint(SpringBus bus, JMSConfigFeature jmsConfigFeature) {
        EndpointImpl endpoint = new EndpointImpl(bus, new HelloServiceEndpoint());
        endpoint.getFeatures().add(jmsConfigFeature);
        endpoint.publish("jms://");
        return endpoint;
    }
    @Bean
    public JMSConfigFeature jmsConfigFeature(ConnectionFactory mqConnectionFactory){
        JMSConfigFeature feature = new JMSConfigFeature();

        JMSConfiguration jmsConfiguration = new JMSConfiguration();
        jmsConfiguration.setConnectionFactory(mqConnectionFactory);
        jmsConfiguration.setTargetDestination("test.req.queue");
        jmsConfiguration.setMessageType("text");

        feature.setJmsConfig(jmsConfiguration);
        return feature;
    }
}
