package com.ttj.soapservice.configs;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMqConfig {
    @Value("${spring.activemq.broker-url}")
    private String activeMqUrl;

    @Value("${spring.activemq.broker-url}")
    private String userName;

    @Value("${spring.activemq.broker-url}")
    private String password;

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(mqConnectionFactory());
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(mqConnectionFactory());
        return factory;
    }

    @Bean
    public SingleConnectionFactory mqConnectionFactory(){
        SingleConnectionFactory factory = new SingleConnectionFactory();

        ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory();
        mqConnectionFactory.setBrokerURL(activeMqUrl);
        mqConnectionFactory.setUserName(userName);
        mqConnectionFactory.setPassword(password);

        factory.setTargetConnectionFactory(mqConnectionFactory);
        return factory;
    }

}
