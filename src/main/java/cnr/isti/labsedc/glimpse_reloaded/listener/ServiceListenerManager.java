package cnr.isti.labsedc.glimpse_reloaded.listener;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TopicConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cnr.isti.labsedc.glimpse_reloaded.register.ChannelsManagementRegistry;

public class ServiceListenerManager extends Thread {

	private TopicConnection receiverConnection;
	private Vector<String> loadChannels;
	private static boolean killAll = true;
    private static final Logger logger = LogManager.getLogger(ServiceListenerManager.class);

	public ServiceListenerManager(Vector<String> loadChannels) {
		this.loadChannels = loadChannels;
	}

	public void run() {

		try {
			ServiceListenerManager.killAll = false;
			receiverConnection = ChannelsManagementRegistry.GetNewTopicConnection();
			Session receiverSession = ChannelsManagementRegistry.GetNewSession(receiverConnection);

			ExecutorService executor = Executors.newFixedThreadPool(loadChannels.size());
			logger.info("Creating executors");
			for (int i = 0; i< loadChannels.size(); i++) {
				Runnable worker = new ServiceListenerTask(loadChannels.get(i), receiverSession);
				executor.execute(worker);
			}
			while(!ServiceListenerManager.killAll) {
			}
			logger.info("KILALLLLLLLL");
			executor.shutdown();
			executor.notifyAll();
			List<Runnable> stillActive = executor.shutdownNow();
			for (int i = 0; i< stillActive.size();i++) {
				System.out.println(stillActive.get(i));
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void killAllServiceListeners() {
		ServiceListenerManager.killAll = true;
	}

}
