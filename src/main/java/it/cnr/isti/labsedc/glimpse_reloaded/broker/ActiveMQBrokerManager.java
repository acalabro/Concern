package it.cnr.isti.labsedc.glimpse_reloaded.broker;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.SslBrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.usage.SystemUsage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.cnr.isti.labsedc.glimpse_reloaded.App;
import it.cnr.isti.labsedc.glimpse_reloaded.cep.DroolsComplexEventProcessorManager;

public class ActiveMQBrokerManager implements BrokerManager, Runnable {

	private static BrokerService broker;
	private static TransportConnector connector;
	private static ActiveMQConnectionFactory connectionFactory;
	private static long ACTIVEMQ_MEMORY_USAGE = 0;
	private static long ACTIVEMQ_TEMP_USAGE = 0;
	private static String ACTIVEMQ_HOST;
    private static final Logger logger = LogManager.getLogger(DroolsComplexEventProcessorManager.class);
	private static String ACTIVEMQ_LOGINNAME;
	private static String ACTIVEMQ_PASSWORD;

	public ActiveMQBrokerManager(String instanceExecutionHost, Long maxMemoryUsage, Long maxCacheUsage, String loginName, String password) {
		ACTIVEMQ_MEMORY_USAGE = maxMemoryUsage;
		ACTIVEMQ_TEMP_USAGE = maxCacheUsage;
		ACTIVEMQ_HOST = instanceExecutionHost;
		ACTIVEMQ_LOGINNAME = loginName;
		ACTIVEMQ_PASSWORD = password;
	}

	@Override
	public void run() {
		logger.info("Start SSL Broker");
		broker = new SslBrokerService();
		broker.setPersistent(false);

		try {
			logger.debug("Creating TrasportConnector");
			connector = new TransportConnector();
			connector.setUri(new URI(ACTIVEMQ_HOST));
			broker.addConnector(connector);
			broker.setUseJmx(false);

			SystemUsage systemUsage= broker.getSystemUsage();
			systemUsage.getMemoryUsage().setLimit(ACTIVEMQ_MEMORY_USAGE);
			systemUsage.getTempUsage().setLimit(ACTIVEMQ_TEMP_USAGE);

			broker.start();
			logger.debug("Waiting for broker start");
			broker.waitUntilStarted();
			logger.debug("broker started");

			logger.debug("Enabling SSL ConnectionFactory");
			connectionFactory = new ActiveMQSslConnectionFactory(ACTIVEMQ_HOST);
			connectionFactory.setUserName(ACTIVEMQ_LOGINNAME);
			connectionFactory.setPassword(ACTIVEMQ_PASSWORD);
			connectionFactory.setTrustedPackages(new ArrayList<String>(Arrays.asList("it.cnr.isti.labsedc.glimpse_reloaded.event")));
			connectionFactory.createConnection();
			logger.debug("Connection sucessfully created");
			App.componentStarted.put(this.getClass().getSimpleName(), true);

		} catch (Exception e) {
			logger.error(e.getCause());
		}
	}

	public static void StopActiveMQBroker() {
		try {
			broker.stop();
			logger.debug("Waiting for broker stop");
			broker.waitUntilStopped();
			logger.debug("broker stopped");
			connector.stop();
			logger.debug("Connector Stopped");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
