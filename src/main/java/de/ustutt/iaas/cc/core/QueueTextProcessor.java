package de.ustutt.iaas.cc.core;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

import de.ustutt.iaas.cc.TextProcessorConfiguration;

public class QueueTextProcessor implements ITextProcessor {

    private QueueSession session;
    private QueueSender sender;
    private QueueReceiver receiver;

    public QueueTextProcessor(TextProcessorConfiguration conf) {
	super();
	try {
	    Context jndi = null;
	    QueueConnectionFactory conFactory = null;
	    switch (conf.mom) {
	    case ActiveMQ:
		// initialize JNDI context
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		env.put("java.naming.security.principal", "system");
		env.put("java.naming.security.credentials", "manager");
		if (conf.activeMQurl == null) {
		    env.put("java.naming.provider.url", "tcp://localhost:61616");
		} else {
		    env.put("java.naming.provider.url", conf.activeMQurl);
		}
		env.put("connectionFactoryNames", "ConnectionFactory");
		env.put("queue." + conf.requestQueueName, conf.requestQueueName);
		env.put("queue." + conf.responseQueueName, conf.responseQueueName);
		// create JNDI context (will also read jndi.properties file)
		jndi = new InitialContext(env);
		// connect to messaging system
		conFactory = (QueueConnectionFactory) jndi.lookup("ConnectionFactory");
		break;
	    case SQS:
	    default:
		// Create the connection factory using the properties file
		// credential provider.
		conFactory = SQSConnectionFactory.builder().withRegion(Region.getRegion(Regions.EU_WEST_1))
			.withAWSCredentialsProvider(new PropertiesFileCredentialsProvider("aws.properties")).build();
		break;
	    }
	    // create connection
	    QueueConnection connection = conFactory.createQueueConnection();
	    // create session
	    session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
	    Queue requestQueue = null;
	    Queue responseQueue = null;
	    switch (conf.mom) {
	    case ActiveMQ:
		// lookup queue
		requestQueue = (Queue) jndi.lookup(conf.requestQueueName);
		responseQueue = (Queue) jndi.lookup(conf.responseQueueName);
		break;
	    case SQS:
	    default:
		requestQueue = session.createQueue(conf.requestQueueName);
		responseQueue = session.createQueue(conf.responseQueueName);
		break;
	    }
	    // create sender and receiver
	    sender = session.createSender(responseQueue);
	    receiver = session.createReceiver(requestQueue);
	    // start connection (!)
	    connection.start();
	} catch (NamingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (JMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Override
    public String process(String text) {
	// TODO send message to request queue, receive response from response

	// Create a text message like this:
	// TextMessage msg = session.createTextMessage();

	// Add properties to a message like this:
	// msg.setStringProperty("...", "...");
	// Note: The text processor will copy all properties from the request
	// message to the response message

	// Receive messages either like this:
	// receiver.receive();
	// Or add a message listener instead:
	// receiver.setMessageListener(new MyMessageLister());
	
	return text;
    }

}
