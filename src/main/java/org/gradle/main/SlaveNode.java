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

/**
 * Main class of the SlaveNode.
 * 
 * @author Christian Ouali Turki
 *
 */
@SpringBootApplication
@EnableJms
public class SlaveNode {

	/**
	 * getting copy of application context
	 */
	@Autowired
	ConfigurableApplicationContext context;

	@Autowired
	MolecularSystemRepository msr;

	 /**
	  * setting the ConnectionFactory for jms communication
	  * 
	  * @return a ConnectionFactory
	  */
	@Bean
	ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://rigel.uni-muenster.de:61616");
		return connectionFactory;
	}

	/**
	 * ListenerContainerFactory for listening to jms queues.
	 * 
	 * @param connectionFactory
	 * @return JmsListenerContainerFactory
	 */
	@Bean
	JmsListenerContainerFactory<?> myJmsContainerFactory(ConnectionFactory connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		return factory;
	}

	/**
	 * JmsListener, that is waiting for messages to arrive in the request queue. The
	 * message is processed with the process method. Concurrency is set to 1, but can be increased
	 * to get more messages at once from the queue. The messages will than also be processed 
	 * concurrently.
	 * 
	 * @param message
	 * @throws JMSException
	 * @throws InterruptedException
	 */
	@JmsListener(destination = "request", containerFactory = "myJmsContainerFactory", concurrency = "1")
	public void receiveMessage(String message) throws Exception {
		System.out.println("Received " + message);
		process(message);
		createAnswer("good Job!");
		System.out.println("Job done. Sending answer.");
		FileSystemUtils.deleteRecursively(new File("activemq-data"));
	}

	/*
	 * Process method, currently just printing every atom of a MolecularSystem
	 * to the console.
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

	/*
	 * Creating and sending answer for MasterNode at the end of the job.
	 *  Message will be send to respond queue
	 */
	public void createAnswer(final String string) {
		MessageCreator messageCreator = new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(string);
				return message;
			}
		};
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		System.out.println("Sending a new answer.");
		jmsTemplate.send("respond", messageCreator);
	}

	public static void main(String[] args) {
		FileSystemUtils.deleteRecursively(new File("activemq-data"));
		ConfigurableApplicationContext context = SpringApplication.run(SlaveNode.class, args);
	}
}