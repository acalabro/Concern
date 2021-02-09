package it.cnr.isti.labsedc.concern;

import java.util.HashMap;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.cnr.isti.labsedc.concern.broker.ActiveMQBrokerManager;
import it.cnr.isti.labsedc.concern.broker.BrokerManager;
import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.cep.ComplexEventProcessorManager;
import it.cnr.isti.labsedc.concern.cep.DroolsComplexEventProcessorManager;
import it.cnr.isti.labsedc.concern.listener.ServiceListenerManager;
import it.cnr.isti.labsedc.concern.register.ChannelsManagementRegistry;
import it.cnr.isti.labsedc.concern.utils.ChannelUtilities;

public class ConcernApp
{
	//private static StorageController storage;
	private static BrokerManager broker;
	private static ComplexEventProcessorManager cepMan;
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
	    logger = LogManager.getLogger(ConcernApp.class);
    	logger.debug(ConcernApp.class.getSimpleName() + " is launching the broker.");
    	broker.run();

    	logger.debug(ConcernApp.class.getSimpleName() + " broker launched.");
    	channelRegistry = new ChannelsManagementRegistry();

    	logger.debug("Channels Management Registry created");
    	System.out.println("PATH: " + System.getProperty("user.dir")+ "/src/main/resources/startupRule.drl");
    	channelRegistry.setConnectionFactory(factory);

    	//STARTING CEP ONE
    	cepMan = new DroolsComplexEventProcessorManager(
    			"InstanceOne",
    			System.getProperty("user.dir")+ "/src/main/resources/startupRule.drl",
    			username, 
    			password, CepType.DROOLS);
    	cepMan.start();

    	while (!cepMan.cepHasCompletedStartup()) {
    		Thread.sleep(100);
    		System.out.println("wait for First CEP start");
    	}

    	//STARTING CEP TWO
    	cepMan = new DroolsComplexEventProcessorManager(
    			"InstanceTwo", 
    			System.getProperty("user.dir")+ "/src/main/resources/startupRule.drl", 
    			username, 
    			password, CepType.DROOLS);
    	cepMan.start();

    	while (!cepMan.cepHasCompletedStartup()) {
    		Thread.sleep(100);
    		System.out.println("wait for Second CEP start");
    	}

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
