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
import it.cnr.isti.labsedc.concern.eventListener.EventListenerManager;
import it.cnr.isti.labsedc.concern.notification.NotificationManager;
import it.cnr.isti.labsedc.concern.register.ChannelsManagementRegistry;
import it.cnr.isti.labsedc.concern.requestListener.ServiceListenerManager;
import it.cnr.isti.labsedc.concern.storage.MySQLStorageController;
import it.cnr.isti.labsedc.concern.utils.ChannelUtilities;

public class ConcernApp
{
	private static BrokerManager broker;
	private static ComplexEventProcessorManager cepMan;
	private static ServiceListenerManager serviceListenerManager;
	private static EventListenerManager eventListenerManager;
	private static NotificationManager notificationManager;
	private static ChannelsManagementRegistry channelRegistry;
	private static MySQLStorageController storageManager;
	
	private static String brokerUrl;
	private static Long maxMemoryUsage;
	private static Long maxCacheUsage;
	public static ActiveMQConnectionFactory factory;
    public static Logger logger = LogManager.getRootLogger();
	private static final boolean SHUTDOWN = false;
	public static HashMap<String, Boolean> componentStarted = new HashMap<String, Boolean>();
	private static String username;
	private static String password;
	private static boolean LOCALBROKER = true;

    public static void main( String[] args ) throws InterruptedException
    {
    	//brokerUrl = "tcp://sedc-nethd.isti.cnr.it:49195";
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
		if (LOCALBROKER) {
			broker = new ActiveMQBrokerManager(brokerUrl, maxMemoryUsage, maxCacheUsage, username, password);
			logger = LogManager.getLogger(ConcernApp.class);
			logger.debug(ConcernApp.class.getSimpleName() + " is launching the broker.");
			broker.run();
			logger.debug(ConcernApp.class.getSimpleName() + " broker launched.");
		} else
			factory = new ActiveMQConnectionFactory(username, password, brokerUrl);		
		
		channelRegistry = new ChannelsManagementRegistry();

    	logger.debug("Channels Management Registry created");
    	System.out.println("PATH: " + System.getProperty("user.dir")+ "/src/main/resources/startupRule.drl");
    	channelRegistry.setConnectionFactory(factory);

    	storageManager = new MySQLStorageController();
    	storageManager.connectToDB();
    	
    	serviceListenerManager = new ServiceListenerManager(ChannelUtilities.loadServiceChannels(), username, password);
    	serviceListenerManager.start();
    	
    	eventListenerManager = new EventListenerManager(ChannelUtilities.loadEventChannels(), username, password, storageManager);
    	eventListenerManager.start();
    	
    	notificationManager = new NotificationManager();
    	notificationManager.start();
    	
    	//STARTING CEP ONE
    	cepMan = new DroolsComplexEventProcessorManager(
    			"InstanceOne",
    			System.getProperty("user.dir")+ "/src/main/resources/startupRule.drl",
    			username, 
    			password, CepType.DROOLS);
    	cepMan.start();

    	while (!cepMan.cepHasCompletedStartup()) {
    		System.out.println("wait for First CEP start");
    		Thread.sleep(100);
    	}

//    	//STARTING CEP TWO
//    	cepMan = new DroolsComplexEventProcessorManager(
//    			"InstanceTwo", 
//    			System.getProperty("user.dir")+ "/src/main/resources/startupRule.drl", 
//    			username, 
//    			password, CepType.DROOLS);
//    	cepMan.start();
//
//    	while (!cepMan.cepHasCompletedStartup()) {
//    		System.out.println("wait for Second CEP start");
//    		Thread.sleep(100);
//    	}

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
