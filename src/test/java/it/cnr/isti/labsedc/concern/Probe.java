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
			System.out.println("setup conn");
			//ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
			Connection connection = connectionFactory.createConnection();
			System.out.println("connessione creata");

            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			System.out.println("sessione creata");

            Topic topic = session.createTopic(topicName);
			System.out.println("topic creato");

            MessageProducer producer = session.createProducer(topic);     
			System.out.println("producer creato");

			ObjectMessage msg = session.createObjectMessage();
			System.out.println("messaggio creato");

			ConcernCANbusEvent<String> event;
			
			for (int i = 0; i<500000 ; i++) {
				event = new ConcernCANbusEvent<String>(
					canData+i,
					CepType.DROOLS,
					"senderProbeName",
					"sessionID",
					"checksum",
					System.currentTimeMillis(),
					"canParamenterName");
			
				msg.setObject(event);
				producer.send(msg);
				System.out.println("invio evento");

			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		String brokerUrl = "tcp://localhost:61616";
		//String brokerUrl = "tcp://146.48.84.178:61616";
		
		testProbe(brokerUrl, "EventChannel-ONE", "vera", "griselda", "messageCANONE");
//		testProbe(brokerUrl, "EventChannel-ONE", "system", "sedclab1234!!_", "messageCANONE");
//		System.out.println("asdasd1");
//		testProbe(brokerUrl, "EventChannel-ONE", "admin", "sedclab1234!!_", "messageCANONE");
//		System.out.println("asdasdadsasdasd2");
//		testProbe(brokerUrl, "EventChannel-ONE", "guest", "testingBieco#@\"", "messageCANONE");
//		System.out.println("asdasdadsasdasdadsasdasdasd3");
		
		//Thread.sleep(500);
		//testProbe(brokerUrl, "EventChannel-TWO", "vera", "griselda", "messageCANTWO");
		//Thread.sleep(500);
	}
}
