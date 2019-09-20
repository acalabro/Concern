package cnr.isti.labsedc.glimpse_reloaded.register;

import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TopicConnection;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cnr.isti.labsedc.glimpse_reloaded.listener.ServiceChannel;

public class ChannelsManagementRegistry {

	private static final Logger logger = LogManager.getLogger(ChannelsManagementRegistry.class);

	public static HashMap<String, String> ActiveQueues = new HashMap<String, String>();
	//creator and queue name

	public static HashMap<ServiceChannel, String> ServiceListeningOnWhichChannel = new HashMap<ServiceChannel, String>();
	//channel on which the system will listen for incoming messages (requests to forward to a specific cep)

	public static HashMap<Session, TopicConnection> ActiveSessions = new HashMap<Session, TopicConnection>();
	//map the session associated to a connection

	public static HashMap<String, String> ConsumerListeningOnWhichChannel = new HashMap<String, String>();
	//map the consumer that requests an evaluation and are waiting for a response on that channel dnamically created

	public static HashMap<String, String> ProbesOpenChannels = new HashMap<String, String>();
	//channels available for probes

	public static ActiveMQConnectionFactory connectionFactory;
	//the brokerconnection factory

	public void setConnectionFactory(ActiveMQConnectionFactory factory) {
		ChannelsManagementRegistry.connectionFactory = factory;
	}

	public static ActiveMQConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public static TopicConnection GetNewTopicConnection() throws JMSException {
		return  ChannelsManagementRegistry.connectionFactory.createTopicConnection();
	}

	public static Session GetNewSession(TopicConnection receiverConnection) throws JMSException {
		receiverConnection.createSession(true, Session.SESSION_TRANSACTED);
		Session session = receiverConnection.createSession(true, Session.SESSION_TRANSACTED);
		ChannelsManagementRegistry.ActiveSessions.put(session, receiverConnection);
		return session;
	}

	public static Queue GetNewSessionQueue(String creator, Session receiverSession, String queueName) throws JMSException {
		Queue queue = receiverSession.createQueue(queueName);
		ChannelsManagementRegistry.ActiveQueues.put(creator,queueName);
		return queue;
	}

	public static void LogDrop() {
		for (int i = 0; i<ActiveQueues.size();i++) {
			logger.info(ActiveQueues.values().toArray()[i].toString());
		}

	}
}
