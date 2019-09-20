package cnr.isti.labsedc.glimpse_reloaded.cep;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TopicConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cnr.isti.labsedc.glimpse_reloaded.register.ChannelsManagementRegistry;

public class DroolsComplexEventProcessorManager extends ComplexEventProcessorManager implements MessageListener {

    private static final Logger logger = LogManager.getLogger(DroolsComplexEventProcessorManager.class);
	private TopicConnection receiverConnection;
	private Queue queue;
	private Session receiverSession;
	private CepType cep;
	private String instanceName;

	public DroolsComplexEventProcessorManager(String instanceName) {
		super();
		logger.info("asd");
		cep = CepType.DROOLS;
		this.instanceName = instanceName;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	@Override
	public void run() {
		try {
			receiverConnection = ChannelsManagementRegistry.GetNewTopicConnection();
			receiverSession = ChannelsManagementRegistry.GetNewSession(receiverConnection);
			queue = ChannelsManagementRegistry.GetNewSessionQueue(this.cep.name()+"-"+instanceName,receiverSession, "DroolsService-"+instanceName);
			logger.info("...CEP named " + this.getInstanceName() + " creates a listening channel called: " + queue.getQueueName());

			MessageConsumer complexEventProcessorReceiver = receiverSession.createConsumer(queue);
			complexEventProcessorReceiver.setMessageListener(this);
			receiverConnection.start();

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message message) {

	}

}
