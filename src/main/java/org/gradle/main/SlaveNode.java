package org.gradle.main;

import java.io.File;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.gradle.dataBaseObjects.MyObject;
import org.gradle.dataBaseRepositories.MyObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.util.FileSystemUtils;
//import org.wallerlab.yoink.Yoink;

@SpringBootApplication
@EnableJms
@EnableJpaRepositories("org.gradle.dataBaseRepositories")
@EntityScan("org.gradle.dataBaseObjects")
public class SlaveNode {

	private static final Logger log = LoggerFactory.getLogger(SlaveNode.class);
/*---- Autowired ----*/
	@Autowired
	ConfigurableApplicationContext context;

	@Autowired
	MyObjectRepository mor;
/*---- Beans ----*/
	@Bean
	ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://rigel.uni-muenster.de:61616");
		return connectionFactory;
	}

	@Bean
	JmsListenerContainerFactory<?> myJmsContainerFactory(ConnectionFactory connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		return factory;
	}

/*---- JMS Messaging ----*/
	// Wait for message of the MasterNode
	@JmsListener(destination = "mailbox-destination", containerFactory = "myJmsContainerFactory", concurrency="10")
	public void receiveMessage(String message) throws Exception {
		log.info("Received " + message.toString());
		createAnswer(process(message));
		log.info("Job done. Sending answer.");
		//context.close();
		FileSystemUtils.deleteRecursively(new File("activemq-data"));
	}

	/*
	 * process, which SN is supposed to execute
	 */
	public String process(String message) throws Exception {
		//Yoink.main(string);
		Long l = Long.parseLong(message);
		MyObject mo = mor.findOne(l);
		mo.setContent("new Value");
		mor.save(mo);
		Thread.sleep(1000);
		return mor.findOne(mo.getId()).getId().toString();
	}

	/*
	 * answer for MN created to signal the end of the jab
	 */
	public void createAnswer(final String string) {
		MessageCreator messageCreator = new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(string);
				return message;
			}
		};
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		log.info("Sending a new answer.");
		jmsTemplate.send("mailbox-answer", messageCreator);
	}

/*---- Main ----*/
	public static void main(String[] args) {
		FileSystemUtils.deleteRecursively(new File("activemq-data"));
		ConfigurableApplicationContext context = SpringApplication.run(SlaveNode.class, args);
	}
}