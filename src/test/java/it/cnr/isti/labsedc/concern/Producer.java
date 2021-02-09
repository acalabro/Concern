package it.cnr.isti.labsedc.concern;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.event.ConcernEvaluationRequestEvent;
import it.cnr.isti.labsedc.concern.listener.ServiceChannelProperties;

public class Producer {

	public static void testProducer(String brokerUrl, String queueName, String username, String password, String data) {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(queue);
			ObjectMessage msg = session.createObjectMessage();
			ConcernEvaluationRequestEvent<String> asd = new ConcernEvaluationRequestEvent<String>(
					data,
					CepType.DROOLS,
					"evaluationRule",
					ServiceChannelProperties.GENERICREQUESTS,
					"ProducerTest",
					"checksum",
					12331l);
			msg.setObject(asd);
            //Message msg = session.createTextMessage("ASD");
	        producer.send(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		String brokerUrl = "tcp://localhost:61616";

		testProducer(brokerUrl, "ServiceChannel-ONE", "vera", "griselda", "messageONE");
		Thread.sleep(500);
		testProducer(brokerUrl, "ServiceChannel-TWO", "vera", "griselda", "messageTWO");
		Thread.sleep(500);
//		testProducer(brokerUrl, "ServiceChannel-THREE", "vera", "griselda");
//		Thread.sleep(500);
//		testProducer(brokerUrl, "ServiceChannel-FOUR", "vera", "griselda");
//		Thread.sleep(500);
//		testProducer(brokerUrl, "ServiceChannel-FIVE", "vera", "griselda");
	}
}
