package it.cnr.isti.labsedc.glimpse_reloaded;

import java.util.HashMap;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.cnr.isti.labsedc.glimpse_reloaded.broker.ActiveMQBrokerManager;
import it.cnr.isti.labsedc.glimpse_reloaded.broker.BrokerManager;
import it.cnr.isti.labsedc.glimpse_reloaded.cep.ComplexEventProcessorManager;
import it.cnr.isti.labsedc.glimpse_reloaded.cep.DroolsComplexEventProcessorManager;
import it.cnr.isti.labsedc.glimpse_reloaded.listener.ServiceListenerManager;
import it.cnr.isti.labsedc.glimpse_reloaded.register.ChannelsManagementRegistry;
import it.cnr.isti.labsedc.glimpse_reloaded.utils.ChannelUtilities;

public class App
{
	//private static StorageController storage;
	private static BrokerManager broker;
	private static ComplexEventProcessorManager cep;
	//private static ClientManager clientMan;
	//private static NotificationManager notificationMan;
	//private static WebInterfaceManager web;
	private static ServiceListenerManager lcManager;
	private static String brokerUrl;
	private static Long maxMemoryUsage;
	private static Long maxCacheUsage;
	private static ChannelsManagementRegistry channelRegistry;
	private static ActiveMQConnectionFactory factory;
    public static Logger logger = LogManager.getRootLogger();
	private static final boolean SHUTDOWN = false;
	public static HashMap<String, Boolean> componentStarted = new HashMap<String, Boolean>();
	private static String username;
	private static String password;

    public static void main( String[] args ) throws InterruptedException
    {
    	brokerUrl = "tcp://localhost:61616";
    	maxMemoryUsage = 128000l;
    	maxCacheUsage = 128000l;
    	factory = new ActiveMQConnectionFactory(brokerUrl);
    	username = "vera";
    	password = "griselda";

    	logger.info("Starting components");
    	StartComponents(factory, brokerUrl, maxMemoryUsage, maxCacheUsage);
    }

	public static void StartComponents(ActiveMQConnectionFactory factory, String brokerUrl, long maxMemoryUsage, long maxCacheUsage) throws InterruptedException {

		//storage = new InfluxDBStorageController();
	    broker = new ActiveMQBrokerManager(brokerUrl, maxMemoryUsage, maxCacheUsage, username, password);
	    logger = LogManager.getLogger(App.class);
    	logger.debug(App.class.getSimpleName() + " is launching the broker.");
    	broker.run();
    	logger.debug(App.class.getSimpleName() + " broker launched.");
    	channelRegistry = new ChannelsManagementRegistry();
    	logger.debug("Channels Management Registry created");
    	logger.debug("PATH: " + System.getProperty("user.dir"));
    	channelRegistry.setConnectionFactory(factory);

    	//STARTING CEP ONE
    	cep = new DroolsComplexEventProcessorManager("InstanceOne", System.getProperty("user.dir")+ "/src/main/resources/startupRule.drl", username, password);
    	cep.start();

    	while (!cep.cepHasCompletedStartup()) {
    		Thread.sleep(100);
    		System.out.println("wait for First CEP start");
    	}

    	//STARTING CEP TWO
    	cep = new DroolsComplexEventProcessorManager("InstanceTwo", System.getProperty("user.dir")+ "/src/main/resources/startupRule.drl", username, password);
    	cep.start();

    	while (!cep.cepHasCompletedStartup()) {
    		Thread.sleep(100);
    		System.out.println("wait for Second CEP start");
    	}

    	//STARTING CEP THREE
    	cep = new DroolsComplexEventProcessorManager("InstanceThree", System.getProperty("user.dir")+ "/src/main/resources/startupRule.drl", username, password);
    	cep.start();


    	lcManager = new ServiceListenerManager(ChannelUtilities.loadChannels(), username, password);
    	lcManager.start();

    	/*
    	clientMan = new ClientManager();
    	notificationMan = new NotificationManager();
    	web = new WebInterfaceManager();
    	 */
    	
    	if(SHUTDOWN) {
	    	ServiceListenerManager.killAllServiceListeners();
	    	ActiveMQBrokerManager.StopActiveMQBroker();
	    	System.exit(0);
    	}
	}
}
