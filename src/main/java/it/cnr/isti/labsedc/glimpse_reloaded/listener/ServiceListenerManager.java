package it.cnr.isti.labsedc.glimpse_reloaded.listener;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceListenerManager extends Thread {

	private Vector<String> loadChannels;
	private static boolean killAll = true;
    private static final Logger logger = LogManager.getLogger(ServiceListenerManager.class);

	public ServiceListenerManager(Vector<String> loadChannels) {
		this.loadChannels = loadChannels;
	}

	public void run() {

			ServiceListenerManager.killAll = false;
			ExecutorService executor = Executors.newFixedThreadPool(loadChannels.size());
			logger.info("Creating executors");
			for (int i = 0; i< loadChannels.size(); i++) {
				Runnable worker = new ServiceListenerTask(loadChannels.get(i));
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
	}

	public static void killAllServiceListeners() {
		ServiceListenerManager.killAll = true;
	}

}
