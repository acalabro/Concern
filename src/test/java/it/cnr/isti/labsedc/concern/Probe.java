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
import it.cnr.isti.labsedc.concern.event.ConcernArduinoEvent;

public class Probe {

	public static void testProbe(String brokerUrl, String topicName, String username, String password, String canData, String eventName) {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
			Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);
            MessageProducer producer = session.createProducer(topic);     
			ObjectMessage msg = session.createObjectMessage();
			ConcernArduinoEvent<String> event = new ConcernArduinoEvent<String>(
					System.currentTimeMillis(), 
					"senderA", "EventChannel-ONE", "sessionA", 
					"checksum",
					canData, eventName, CepType.DROOLS,"open");
				msg.setObject(event);
				producer.send(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		String brokerUrl = "tcp://localhost:61616";
		//String brokerUrl = "tcp://sedc-nethd.isti.cnr.it:49195";
		
		testProbe(brokerUrl, "DROOLS-InstanceOne", "vera", "griselda", "SLA Alert", "evento1");
		Thread.sleep(1000);
		testProbe(brokerUrl, "DROOLS-InstanceOne", "vera", "griselda", "load_one", "evento2");
		System.out.println("SENT");
	}
}
