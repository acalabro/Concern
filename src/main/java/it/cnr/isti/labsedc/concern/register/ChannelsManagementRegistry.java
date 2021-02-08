package it.cnr.isti.labsedc.concern.register;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TopicConnection;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.cnr.isti.labsedc.concern.ConcernApp;
import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.listener.ServiceChannelProperties;

public class ChannelsManagementRegistry {

	private static Logger logger ;
	public static HashMap<String, QueueAndProperties> ActiveQueues;
	public static HashMap<QueueAndProperties, String> ActiveCep;
	public static HashMap<ServiceChannelProperties, String> ActiveServicesChannel;
	public static HashMap<Session, TopicConnection> ActiveSessions;
	public static HashMap<String, String> ConsumersChannels;
	public static HashMap<String, String> ProbesChannels;
	public static ActiveMQConnectionFactory connectionFactory;
	//the broker connection factory

	public ChannelsManagementRegistry() {
		logger = LogManager.getLogger(ChannelsManagementRegistry.class);

    	logger.debug("into " + this.getClass().getSimpleName());
		ActiveQueues = new HashMap<String, QueueAndProperties>();
		//creator and queue name

		ActiveCep = new HashMap<QueueAndProperties, String>();
		//cep available on the infrastructure- the string is the queueName

		ActiveServicesChannel = new HashMap<ServiceChannelProperties, String>();
		//channel on which the system will listen for incoming messages organized by service (requests to forward to a specific cep)

		ActiveSessions = new HashMap<Session, TopicConnection>();
		//map the session associated to a connection

		ConsumersChannels = new HashMap<String, String>();
		//map the consumer that requests an evaluation and are waiting for a response on that channel dinamically created

		ProbesChannels = new HashMap<String, String>();
		//channels available for probes
    	logger.debug(this.getClass().getSimpleName() + " started");

		ConcernApp.componentStarted.put(this.getClass().getSimpleName(), true);

    	logger.debug(this.getClass().getSimpleName() + " loaded in registry.");
	}

	public void setConnectionFactory(ActiveMQConnectionFactory factory) {
		ChannelsManagementRegistry.connectionFactory = factory;
	}

	public static ActiveMQConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public static TopicConnection GetNewTopicConnection(String username, String password) throws JMSException {
		ChannelsManagementRegistry.connectionFactory.setTrustedPackages(new ArrayList<String>(Arrays.asList("it.cnr.isti.labsedc.glimpse_reloaded.event,it.cnr.isti.labsedc.glimpse_reloaded.cep,it.cnr.isti.labsedc.glimpse_reloaded.listener".split(","))));
		ChannelsManagementRegistry.connectionFactory.setUserName(username);
		ChannelsManagementRegistry.connectionFactory.setUserName(password);
		return  ChannelsManagementRegistry.connectionFactory.createTopicConnection();
	}

	public static Session GetNewSession(TopicConnection receiverConnection) throws JMSException {
		receiverConnection.createSession(true, Session.SESSION_TRANSACTED);
		Session session = receiverConnection.createSession(true, Session.SESSION_TRANSACTED);
		ChannelsManagementRegistry.ActiveSessions.put(session, receiverConnection);
		return session;
	}

	public static Queue GetNewSessionQueue(String creator, Session receiverSession, String queueName, ServiceChannelProperties property) throws JMSException {
		Queue queue = receiverSession.createQueue(queueName);
		//ChannelsManagementRegistry.ActiveQueues.put(creator,new QueueAndProperties(queueName,property));
		return queue;
	}

	public static void LogDrop() {
		for (int i = 0; i<ActiveQueues.size();i++) {
			logger.info(ActiveQueues.values().toArray()[i].toString());
		}
	}

	public static Queue RegisterNewCepQueue(String CepIdentifier, Session receiverSession, String queueName,
			ServiceChannelProperties channelProperties, CepType cepType) throws JMSException {
		Queue queue = receiverSession.createQueue(queueName);
		ChannelsManagementRegistry.ActiveCep.put(new QueueAndProperties(CepIdentifier,channelProperties, cepType), queueName);
		return queue;
	}
}
