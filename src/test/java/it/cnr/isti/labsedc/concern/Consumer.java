package it.cnr.isti.labsedc.concern;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.event.ConcernEvaluationRequestEvent;
import it.cnr.isti.labsedc.concern.eventListener.ChannelProperties;

public class Consumer {

	public static void testConsumer(String brokerUrl, String topicName, String username, String password, String data) {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);
            MessageProducer producer = session.createProducer(topic);
			ObjectMessage msg = session.createObjectMessage();
			ConcernEvaluationRequestEvent<String> asd = new ConcernEvaluationRequestEvent<String>(
					data,
					CepType.DROOLS,
					"evaluationRule",
					ChannelProperties.GENERICREQUESTS,
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

		testConsumer(brokerUrl, "ServiceChannel-ONE", "vera", "griselda", "Evaluation Rule ONE");
		Thread.sleep(500);
		testConsumer(brokerUrl, "ServiceChannel-TWO", "vera", "griselda", "Evaluation Rule TWO");
		Thread.sleep(500);
//		testProducer(brokerUrl, "ServiceChannel-THREE", "vera", "griselda");
//		Thread.sleep(500);
//		testProducer(brokerUrl, "ServiceChannel-FOUR", "vera", "griselda");
//		Thread.sleep(500);
//		testProducer(brokerUrl, "ServiceChannel-FIVE", "vera", "griselda");
	}
}
