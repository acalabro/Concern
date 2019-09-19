package cnr.isti.labsedc.glimpse_reloaded.listener;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ListenerChannelTask implements Runnable {


	private String channelTaskName;
	private Session receiverSession;
    private static final Logger logger = LogManager.getLogger(ListenerChannelTask.class);


	public ListenerChannelTask(String channelTaskName, Session receiverSession) {
		this.channelTaskName = channelTaskName;
		this.receiverSession = receiverSession;
	}

	public String getChannelTaskName() {
		return this.channelTaskName;
	}

	public void run() {

		logger.info("...within the executor named " + this.getChannelTaskName());
		try {
			Queue queue = receiverSession.createQueue(channelTaskName);
			MessageConsumer consumer = receiverSession.createConsumer(queue);
			logger.info("...consumer named " + consumer.toString() + " created within the executor named " + this.getChannelTaskName());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
