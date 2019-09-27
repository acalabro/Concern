package it.cnr.isti.labsedc.glimpse_reloaded;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import it.cnr.isti.labsedc.glimpse_reloaded.cep.CepType;
import it.cnr.isti.labsedc.glimpse_reloaded.event.GlimpseEvaluationRequestEvent;
import it.cnr.isti.labsedc.glimpse_reloaded.listener.ServiceChannelProperties;

public class Producer {

	public static void testProducer(String brokerUrl, String queueName, String username, String password) {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(queue);
			ObjectMessage msg = session.createObjectMessage();
			GlimpseEvaluationRequestEvent<String> asd = new GlimpseEvaluationRequestEvent<String>(
					"asd",
					CepType.DROOLS,
					"asd",
					ServiceChannelProperties.GENERICREQUESTS,
					"ProducerTest",
					"asdasdasd",
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

/*
		testProducer(brokerUrl, "DroolsService-InstanceOne", "vera", "griselda");
		Thread.sleep(500);
		testProducer(brokerUrl, "DroolsService-InstanceTwo", "vera", "griselda");
		Thread.sleep(500);
*/

		testProducer(brokerUrl, "ServiceChannel-ONE", "vera", "griselda");
		Thread.sleep(500);
		testProducer(brokerUrl, "ServiceChannel-TWO", "vera", "griselda");
		Thread.sleep(500);
		testProducer(brokerUrl, "ServiceChannel-THREE", "vera", "griselda");
		Thread.sleep(500);
		testProducer(brokerUrl, "ServiceChannel-FOUR", "vera", "griselda");
		Thread.sleep(500);
		testProducer(brokerUrl, "ServiceChannel-FIVE", "vera", "griselda");
	}
}
