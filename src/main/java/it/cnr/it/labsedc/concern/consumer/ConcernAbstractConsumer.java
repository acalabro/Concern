package it.cnr.it.labsedc.concern.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.event.ConcernEvaluationRequestEvent;

public class ConcernAbstractConsumer implements ConcernConsumer {

	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Topic topic;
	private MessageProducer producer;
	
	public ConcernAbstractConsumer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(String brokerUrl, String topicChannel, String username, String password) throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        topic = session.createTopic(topicChannel);
	}
	@Override
	public void onMessage(Message message) {

		
	}

	@Override
	public void sendEvaluationRequest(String serviceChannel, CepType cepType, ConcernEvaluationRequestEvent<String> evaluationRequests)
			throws JMSException {
        producer = session.createProducer(topic);
		ObjectMessage msg = session.createObjectMessage();
		msg.setObject(evaluationRequests);
        producer.send(msg);
	}

	@Override
	public void listenForResponse(String responseChannel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendTextMessage(String serviceChannel, String textToSend) throws JMSException {
		// TODO Auto-generated method stub

	}



}
