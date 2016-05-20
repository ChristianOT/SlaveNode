package org.gradle.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.gradle.dataBaseRepositories.MolecularSystemRepository;
import org.gradle.domain.Atom;
import org.gradle.domain.MolecularSystem;
import org.gradle.domain.Molecule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
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
public class SlaveNode {

	private static final Logger log = LoggerFactory.getLogger(SlaveNode.class);
	/*---- Autowired ----*/
	@Autowired
	ConfigurableApplicationContext context;

	@Autowired
	MolecularSystemRepository msr;

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
	@JmsListener(destination = "yoink-request", containerFactory = "myJmsContainerFactory", concurrency = "1")
	public void receiveMessage(String message) throws Exception {
		log.info("Received " + message);
		process(message);
		createAnswer("good Job!");
		log.info("Job done. Sending answer.");
		// context.close();
		FileSystemUtils.deleteRecursively(new File("activemq-data"));
	}

	/*
	 * process, which SN is supposed to execute
	 */
	public void process(String message) throws Exception {
		List<String> mss = new ArrayList<String>();
		System.out.println("line 77");
		MolecularSystem ms = msr.findOne(Long.parseLong(message));
		Molecule[] msArray = new Molecule[ms.molecules.size()];
		ms.molecules.toArray(msArray);
		System.out.println("line 82" + msArray.length);
		for (Molecule mole : msArray) {
			System.out.println(mole.getAtoms().size());
			for (Atom atom : mole.atoms) {
				System.out.println(atom);
			}
		}
	}

	// return mss;
	

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