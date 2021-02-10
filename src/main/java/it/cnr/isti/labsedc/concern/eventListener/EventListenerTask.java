package it.cnr.isti.labsedc.concern.eventListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.cnr.isti.labsedc.concern.event.ConcernBasicEvent;
import it.cnr.isti.labsedc.concern.register.ChannelsManagementRegistry;
import it.cnr.isti.labsedc.concern.register.QueueAndProperties;
import it.cnr.isti.labsedc.concern.utils.RoutingUtilities;

public class EventListenerTask implements Runnable, MessageListener {


	private String eventChannelName;
	private TopicConnection receiverConnection;
	private String username;
	private String password;
    private static final Logger logger = LogManager.getLogger(EventListenerTask.class);
    private static MessageConsumer consumer;
    private static Session receiverSession;

	public EventListenerTask(String eventChannelName, String connectionUsername, String connectionPassword) {
		this.eventChannelName = eventChannelName;
		this.username = connectionUsername;
		this.password = connectionPassword;
	}

	public String getEventChannelName() {
		return this.eventChannelName;
	}

	public void run() {

		logger.info("...within the executor named " + this.getEventChannelName());
		try {
			receiverConnection = ChannelsManagementRegistry.GetNewTopicConnection(username, password);
			receiverSession = ChannelsManagementRegistry.GetNewSession(receiverConnection);

			Queue queue = ChannelsManagementRegistry.GetNewSessionQueue(this.toString(), receiverSession,eventChannelName, ChannelProperties.EVENTS);
			consumer = receiverSession.createConsumer(queue);
			//RegisterForCommunicationChannels.ServiceListeningOnWhichChannel.put(key, value)
			logger.info("...eventListener named " + consumer.toString() + " created within the executor named " + this.getEventChannelName());
			consumer.setMessageListener(this);
			receiverConnection.start();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onMessage(Message message) {

		if (message instanceof ObjectMessage) {
			ObjectMessage casted = (ObjectMessage)message;
			try {
				if (casted.getObject() != null && (casted.getObject() instanceof ConcernBasicEvent<?>)) {
					ConcernBasicEvent<?> incomingRequest = (ConcernBasicEvent<?>)casted.getObject();
					QueueAndProperties queueWhereToForward= RoutingUtilities.BestCepSelectionForEvents(incomingRequest);
					if (queueWhereToForward != null) {
						forwardEventToCEP(queueWhereToForward, message);
					}
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

		if (message instanceof TextMessage) {
			TextMessage msg = (TextMessage) message;
			try {
				logger.info("EventListenerTask " + this.eventChannelName + " receives TextMessage: " + msg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	private void forwardEventToCEP(QueueAndProperties queueWhereToForward, Message message) {
		try {
			receiverConnection = ChannelsManagementRegistry.GetNewTopicConnection(username, password);
            Session session = receiverConnection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueWhereToForward.getQueueAddress());
            MessageProducer producer = session.createProducer(queue);
            ObjectMessage forwarded = (ObjectMessage) message;
			forwarded.setJMSDestination(queue);
            producer.send(forwarded);
            producer.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
