package com.ttj.soapservice;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.support.MessageBuilder;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@SpringBootApplication
public class CxfSoapServiceApplication {
	@Autowired
	private JmsTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(CxfSoapServiceApplication.class, args);
	}
	@EventListener(ApplicationReadyEvent.class)
	public void applicationReadyEvent()throws JMSException {
		String msg = "<soapenv:Envelope \n" +
                "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "xmlns:ser=\"http://services.ttj.com/\"\n" +
                "xmlns:hs=\"http://services.ttj.com/hello-service\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <ser:sayHelloRequest>\n" +
                "         <arg0>\n" +
                "            <hs:userName>Black swan</hs:userName>\n" +
                "         </arg0>\n" +
                "      </ser:sayHelloRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

		Session session = template.getConnectionFactory().createConnection()
                .createSession(false, Session.AUTO_ACKNOWLEDGE);

		TextMessage message = session.createTextMessage(msg);
		message.setJMSReplyTo(new ActiveMQQueue("test.response.queue"));

		template.convertAndSend("test.req.queue", message);

		System.out.println("Jms Message sent");
	}

    @JmsListener(destination = "test.response.queue")
    public void receiveMessage(final Message message) throws JMSException {
        String messageData = null;
        System.out.println("Received message-> " + message);
        String response = null;
        if(message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)message;
            messageData = textMessage.getText();
            System.out.println("Message data-> "+messageData);
        }
    }

}
