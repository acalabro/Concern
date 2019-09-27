package it.cnr.isti.labsedc.glimpse_reloaded.listener;

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

import it.cnr.isti.labsedc.glimpse_reloaded.event.GlimpseEvaluationRequestEvent;
import it.cnr.isti.labsedc.glimpse_reloaded.register.ChannelsManagementRegistry;
import it.cnr.isti.labsedc.glimpse_reloaded.utils.RoutingUtilities;

public class ServiceListenerTask implements Runnable, MessageListener {


	private String channelTaskName;
	private TopicConnection receiverConnection;
	private String username;
	private String password;
    private static final Logger logger = LogManager.getLogger(ServiceListenerTask.class);
    private static MessageProducer producer;
    private static MessageConsumer consumer;
    private static Session receiverSession;

	public ServiceListenerTask(String channelTaskName, String connectionUsername, String connectionPassword) {
		this.channelTaskName = channelTaskName;
		this.username = connectionUsername;
		this.password = connectionPassword;
	}

	public String getChannelTaskName() {
		return this.channelTaskName;
	}

	public void run() {

		logger.info("...within the executor named " + this.getChannelTaskName());
		try {
			receiverConnection = ChannelsManagementRegistry.GetNewTopicConnection(username, password);
			receiverSession = ChannelsManagementRegistry.GetNewSession(receiverConnection);

			Queue queue = ChannelsManagementRegistry.GetNewSessionQueue(this.toString(), receiverSession,channelTaskName, ServiceChannelProperties.GENERICREQUESTS);
			consumer = receiverSession.createConsumer(queue);
			//RegisterForCommunicationChannels.ServiceListeningOnWhichChannel.put(key, value)
			logger.info("...consumer named " + consumer.toString() + " created within the executor named " + this.getChannelTaskName());

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
				if (casted.getObject() != null && (casted.getObject() instanceof GlimpseEvaluationRequestEvent<?>)) {
					GlimpseEvaluationRequestEvent<?> incomingRequest = (GlimpseEvaluationRequestEvent<?>)casted.getObject();

					String queueWhereToForward= RoutingUtilities.BestCepSelection(incomingRequest);
					if (queueWhereToForward != null) {
						forwardToCep(queueWhereToForward, message);
					}
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}


		if (message instanceof TextMessage) {
			TextMessage msg = (TextMessage) message;
			try {
				logger.info("ServiceListenerTask " + this.channelTaskName + " receives TextMessage: " + msg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	private void forwardToCep(String queueWhereToForward, Message message) {
		try {
	        Queue queue = receiverSession.createQueue(queueWhereToForward);
			producer = receiverSession.createProducer(queue);
			System.out.println("Send: " + queue.getQueueName() + " ------ " + queueWhereToForward);
			message.setJMSDestination(queue);
			producer.send(message);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
