//package org.gradle;
//
//import java.io.File;
//
//import javax.jms.ConnectionFactory;
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.Session;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.jms.annotation.EnableJms;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.jms.config.JmsListenerContainerFactory;
//import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessageCreator;
//import org.springframework.util.FileSystemUtils;
//
//@SpringBootApplication
//@EnableJms
//public class TestReceiver {
//
//	/**
//	 * Standard connection factory
//	 * -{@link org.apache.activemq.spring.ActiveMQConnectionFactory}
//	 *
//	 * 
//	 * Broker URL is where the ActiveMQ is running.
//	 * 
//	 * The port used to be 61620 or so, now it is 61616 - careful.
//	 * 
//	 * @return
//	 */
//	@Bean
//	ConnectionFactory connectionFactory() {
//		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
//		connectionFactory.setBrokerURL("tcp://rigel.uni-muenster.de:61616");
//		return connectionFactory;
//	}
//
//	@Bean
//	JmsListenerContainerFactory<?> myJmsContainerFactory(ConnectionFactory connectionFactory) {
//		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
//		factory.setConnectionFactory(connectionFactory);
//		return factory;
//	}
//
//	/**
//	 * Get a copy of the application context
//	 */
//	@Autowired
//	ConfigurableApplicationContext context;
//
//	/**
//	 * When you receive a message, print it out, then shut down the application.
//	 * Finally, clean up any ActiveMQ server stuff.
//	 * 
//	 * @throws JMSException
//	 */
//	// Wait for message of the MasterNode
//	@JmsListener(destination = "mailbox-destination", containerFactory = "myJmsContainerFactory")
//	public void receiveMessage(String message) throws JMSException {
//		System.out.println("Received <" + message + ">");
//		context.close();
//		FileSystemUtils.deleteRecursively(new File("activemq-data"));
//	}
//
//	public static void main(String[] args) {
//		// Clean out any ActiveMQ data from a previous run
//		FileSystemUtils.deleteRecursively(new File("activemq-data"));
//
//		// Launch the application
//		ConfigurableApplicationContext context = SpringApplication.run(TestReceiver.class, args);
//
//		// Send an answer
//		MessageCreator messageCreator = new MessageCreator() {
//			public Message createMessage(Session session) throws JMSException {
//				return session.createTextMessage("slappy day!");
//			}
//		};
//		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
//		System.out.println("Sending a new answer.");
//		jmsTemplate.send("mailbox-answer", messageCreator);
//	}
//}