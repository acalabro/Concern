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
import it.cnr.isti.labsedc.concern.event.ConcernCANbusEvent;

public class Probe {

	public static void testProbe(String brokerUrl, String queueName, String username, String password, String canData) {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(queue);
			ObjectMessage msg = session.createObjectMessage();
			ConcernCANbusEvent<String> event = new ConcernCANbusEvent<String>(
					canData,
					CepType.DROOLS,
					"senderProbeName",
					"checksum",
					12331l,
					"canAddress");
			
			msg.setObject(event);
	        producer.send(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		String brokerUrl = "tcp://localhost:61616";

		testProbe(brokerUrl, "EventChannel-ONE", "vera", "griselda", "messageCANONE");
		Thread.sleep(500);
		testProbe(brokerUrl, "EventChannel-TWO", "vera", "griselda", "messageCANTWO");
		Thread.sleep(500);
//		testProducer(brokerUrl, "ServiceChannel-THREE", "vera", "griselda");
//		Thread.sleep(500);
//		testProducer(brokerUrl, "ServiceChannel-FOUR", "vera", "griselda");
//		Thread.sleep(500);
//		testProducer(brokerUrl, "ServiceChannel-FIVE", "vera", "griselda");
	}
}
