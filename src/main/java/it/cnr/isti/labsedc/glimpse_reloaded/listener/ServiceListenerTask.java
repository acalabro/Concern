package it.cnr.isti.labsedc.glimpse_reloaded.listener;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.cnr.isti.labsedc.glimpse_reloaded.register.ChannelsManagementRegistry;

public class ServiceListenerTask implements Runnable {


	private String channelTaskName;
	private Session receiverSession;
    private static final Logger logger = LogManager.getLogger(ServiceListenerTask.class);


	public ServiceListenerTask(String channelTaskName, Session receiverSession) {
		this.channelTaskName = channelTaskName;
		this.receiverSession = receiverSession;
	}

	public String getChannelTaskName() {
		return this.channelTaskName;
	}

	public void run() {

		logger.info("...within the executor named " + this.getChannelTaskName());
		try {
			Queue queue = ChannelsManagementRegistry.GetNewSessionQueue(this.toString(), receiverSession,channelTaskName);

			MessageConsumer consumer = receiverSession.createConsumer(queue);
			//RegisterForCommunicationChannels.ServiceListeningOnWhichChannel.put(key, value)
			logger.info("...consumer named " + consumer.toString() + " created within the executor named " + this.getChannelTaskName());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
