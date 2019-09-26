package it.cnr.isti.labsedc.glimpse_reloaded.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.cnr.isti.labsedc.glimpse_reloaded.event.GlimpseEvaluationRequestEvent;
import it.cnr.isti.labsedc.glimpse_reloaded.register.ChannelsManagementRegistry;
import it.cnr.isti.labsedc.glimpse_reloaded.register.QueueAndProperties;
import it.cnr.isti.labsedc.glimpse_reloaded.utils.RoutingUtilities;

public class ServiceListenerTask implements Runnable, MessageListener {


	private String channelTaskName;
	private TopicConnection receiverConnection;
    private static final Logger logger = LogManager.getLogger(ServiceListenerTask.class);


	public ServiceListenerTask(String channelTaskName) {
		this.channelTaskName = channelTaskName;
	}

	public String getChannelTaskName() {
		return this.channelTaskName;
	}

	public void run() {

		logger.info("...within the executor named " + this.getChannelTaskName());
		try {
			receiverConnection = ChannelsManagementRegistry.GetNewTopicConnection();
			Session receiverSession = ChannelsManagementRegistry.GetNewSession(receiverConnection);

			Queue queue = ChannelsManagementRegistry.GetNewSessionQueue(this.toString(), receiverSession,channelTaskName, ServiceChannelProperties.GENERICREQUESTS);
			MessageConsumer consumer = receiverSession.createConsumer(queue);
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
					forwardRequestToCEP(RoutingUtilities.BestCepSelection((GlimpseEvaluationRequestEvent<?>)casted.getObject()), (GlimpseEvaluationRequestEvent<?>)casted.getObject());
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

	private void forwardRequestToCEP(QueueAndProperties whereToForward, GlimpseEvaluationRequestEvent<?> messageToForward) {



	}


}
