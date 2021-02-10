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
import it.cnr.isti.labsedc.concern.event.ConcernCANbusEvent;

public class Probe {

	public static void testProbe(String brokerUrl, String topicName, String username, String password, String canData) {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);
            MessageProducer producer = session.createProducer(topic);
			ObjectMessage msg = session.createObjectMessage();
			ConcernCANbusEvent<String> event;
			
			for (int i = 0; i<5000; i++) {
				event = new ConcernCANbusEvent<String>(
					canData+i,
					CepType.DROOLS,
					"senderProbeName",
					"checksum",
					12331l,
					"canAddress");
			
				msg.setObject(event);
				producer.send(msg);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		String brokerUrl = "tcp://localhost:61616";

		testProbe(brokerUrl, "EventChannel-ONE", "vera", "griselda", "messageCANONE");
//		Thread.sleep(500);
		testProbe(brokerUrl, "EventChannel-TWO", "vera", "griselda", "messageCANTWO");
//		Thread.sleep(500);
		testProbe(brokerUrl, "EventChannel-THREE", "vera", "griselda", "messageCANTHREE");
//		Thread.sleep(500);
		testProbe(brokerUrl, "EventChannel-FOUR", "vera", "griselda", "messageCANFOUR");
//		Thread.sleep(500);
//		testProducer(brokerUrl, "ServiceChannel-FIVE", "vera", "griselda");
	}
}
