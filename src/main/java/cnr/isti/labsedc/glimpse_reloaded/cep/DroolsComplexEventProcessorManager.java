package cnr.isti.labsedc.glimpse_reloaded.cep;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TopicConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cnr.isti.labsedc.glimpse_reloaded.register.RegisterForCommunicationChannels;

public class DroolsComplexEventProcessorManager extends ComplexEventProcessorManager {

    private static final Logger logger = LogManager.getLogger(DroolsComplexEventProcessorManager.class);
	private TopicConnection receiverConnection;
	private Queue queue;
	private Session receiverSession;

	public DroolsComplexEventProcessorManager() {
		super();
		logger.info("asd");

	}

	@Override
	public void run() {
		try {
			receiverConnection = RegisterForCommunicationChannels.getConnectionFactory().createTopicConnection();
			receiverSession = receiverConnection.createSession(true, Session.SESSION_TRANSACTED);
			queue = receiverSession.createQueue("DroolsService");
			RegisterForCommunicationChannels.CepActiveOnWhichChannels.put(CepType.DROOLS, queue.getQueueName());
			logger.info("...CEP named " + this.getClass().getCanonicalName() + " creates a listening channel called: " + queue.getQueueName());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub

	}

}
