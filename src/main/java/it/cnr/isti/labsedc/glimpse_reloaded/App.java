package it.cnr.isti.labsedc.glimpse_reloaded;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.cnr.isti.labsedc.glimpse_reloaded.broker.ActiveMQBrokerManager;
import it.cnr.isti.labsedc.glimpse_reloaded.broker.BrokerManager;
import it.cnr.isti.labsedc.glimpse_reloaded.cep.ComplexEventProcessorManager;
import it.cnr.isti.labsedc.glimpse_reloaded.cep.DroolsComplexEventProcessorManager;
import it.cnr.isti.labsedc.glimpse_reloaded.client.ClientManager;
import it.cnr.isti.labsedc.glimpse_reloaded.listener.ServiceListenerManager;
import it.cnr.isti.labsedc.glimpse_reloaded.notification.NotificationManager;
import it.cnr.isti.labsedc.glimpse_reloaded.register.ChannelsManagementRegistry;
import it.cnr.isti.labsedc.glimpse_reloaded.storage.InfluxDBStorageController;
import it.cnr.isti.labsedc.glimpse_reloaded.storage.StorageController;
import it.cnr.isti.labsedc.glimpse_reloaded.utils.ChannelUtilities;
import it.cnr.isti.labsedc.glimpse_reloaded.web.WebInterfaceManager;

/**
 * Hello world!
 *
 */
public class App
{
	private static StorageController storage;
	private static BrokerManager broker;
	private static ComplexEventProcessorManager cep;
	private static ClientManager clientMan;
	private static NotificationManager notificationMan;
	private static WebInterfaceManager web;
	private static ServiceListenerManager lcManager;
	private static String brokerUrl;
	private static Long maxMemoryUsage;
	private static Long maxCacheUsage;
	private static ChannelsManagementRegistry channelRegistry;
	private static ActiveMQConnectionFactory factory;
    private static final Logger logger = LogManager.getLogger(App.class);
	private static final boolean SHUTDOWN = false;


    public static void main( String[] args )
    {
    	brokerUrl = "tcp://localhost:61616";
    	maxMemoryUsage = 128000l;
    	maxCacheUsage = 128000l;
    	factory = new ActiveMQConnectionFactory(brokerUrl);

    	StartComponents();
    }

	private static void StartComponents() {

		storage = new InfluxDBStorageController();

	    broker = new ActiveMQBrokerManager(brokerUrl, maxMemoryUsage, maxCacheUsage);
    	broker.run();
System.out.println("asd");
    	channelRegistry = new ChannelsManagementRegistry();
    	channelRegistry.setConnectionFactory(factory);

    	cep = new DroolsComplexEventProcessorManager("InstanceOne", "/home/acalabro/workspace/glimpse_reloaded/src/main/resources/startupRule.drl");
    	cep.start();

    	cep = new DroolsComplexEventProcessorManager("InstanceTwo", "/home/acalabro/workspace/glimpse_reloaded/src/main/resources/startupRule.drl");
    	cep.start();

    	lcManager = new ServiceListenerManager(ChannelUtilities.loadChannels());
    	lcManager.start();

    	clientMan = new ClientManager();
    	notificationMan = new NotificationManager();
    	web = new WebInterfaceManager();

    	if(SHUTDOWN) {
	    	ServiceListenerManager.killAllServiceListeners();
	    	ActiveMQBrokerManager.StopActiveMQBroker();
	    	System.exit(0);
    	}

	}

}
