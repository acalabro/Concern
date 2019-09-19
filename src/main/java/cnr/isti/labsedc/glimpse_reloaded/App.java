package cnr.isti.labsedc.glimpse_reloaded;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cnr.isti.labsedc.glimpse_reloaded.broker.ActiveMQBrokerManager;
import cnr.isti.labsedc.glimpse_reloaded.broker.BrokerManager;
import cnr.isti.labsedc.glimpse_reloaded.cep.ComplexEventProcessorManager;
import cnr.isti.labsedc.glimpse_reloaded.cep.DroolsComplexEventProcessorManager;
import cnr.isti.labsedc.glimpse_reloaded.notification.NotificationManager;
import cnr.isti.labsedc.glimpse_reloaded.client.ClientManager;
import cnr.isti.labsedc.glimpse_reloaded.listener.ListenerChannelsManager;
import cnr.isti.labsedc.glimpse_reloaded.listener.StorageController;
import cnr.isti.labsedc.glimpse_reloaded.storage.InfluxDBStorageController;
import cnr.isti.labsedc.glimpse_reloaded.utils.ChannelUtilities;
import cnr.isti.labsedc.glimpse_reloaded.web.WebInterfaceManager;

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
	private static ListenerChannelsManager lcManager;
	private static String brokerUrl;
	private static Long maxMemoryUsage;
	private static Long maxCacheUsage;
    private static final Logger logger = LogManager.getLogger(App.class);


    public static void main( String[] args )
    {
    	logger.info("asd");

    	brokerUrl = "tcp://localhost:61616";
    	maxMemoryUsage = 128000l;
    	maxCacheUsage = 128000l;

    	StartComponents();
    }

	private static void StartComponents() {

		//storage = new MySQLStorageController();
		storage = new InfluxDBStorageController();

    	broker = new ActiveMQBrokerManager(brokerUrl, maxMemoryUsage, maxCacheUsage);
    	broker.run();

    	lcManager = new ListenerChannelsManager(brokerUrl, ChannelUtilities.loadChannels());

    	cep = new DroolsComplexEventProcessorManager();
    	clientMan = new cnr.isti.labsedc.glimpse_reloaded.client.ClientManager();
    	notificationMan = new NotificationManager();
    	web = new WebInterfaceManager();
	}
}
