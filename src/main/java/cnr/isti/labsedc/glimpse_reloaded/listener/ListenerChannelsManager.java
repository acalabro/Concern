package cnr.isti.labsedc.glimpse_reloaded.listener;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TopicConnection;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ListenerChannelsManager {

	private TopicConnection receiverConnection;
    private static final Logger logger = LogManager.getLogger(ListenerChannelsManager.class);

	public ListenerChannelsManager(ActiveMQConnectionFactory factory, Vector<String> loadChannels) {

	     try {
			receiverConnection = factory.createTopicConnection();
			Session receiverSession = receiverConnection.createSession(true, Session.SESSION_TRANSACTED);

			ExecutorService executor = Executors.newFixedThreadPool(loadChannels.size());
			logger.info("Creating executors");
			for (int i = 0; i< loadChannels.size(); i++) {
				Runnable worker = new ListenerChannelTask(loadChannels.get(i), receiverSession);
				executor.execute(worker);
			}
			while (!executor.isTerminated()) {
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}


	}

}
