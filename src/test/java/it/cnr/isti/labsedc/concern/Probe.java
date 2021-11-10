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
import it.cnr.isti.labsedc.concern.event.ConcernBaseEvent;

public class Probe {

	public static void testProbe(String brokerUrl, String topicName, String username, String password, String eventData, String eventName) {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
			Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);
            MessageProducer producer = session.createProducer(topic);     
			ObjectMessage msg = session.createObjectMessage();
			
			ConcernBaseEvent<String> event = new ConcernBaseEvent<String>(
					System.currentTimeMillis(), 
					new Exception().getStackTrace()[1].getClassName(),
					"AuditingSystem-Monitoring", "sessionA", 
					"checksum",
					eventName, eventData, CepType.DROOLS,"extension");
 				msg.setObject(event);
				producer.send(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		String brokerUrl = "tcp://0.0.0.0:61616";
		//String brokerUrl = "tcp://sedc-nethd.isti.cnr.it:49195";
		printHello();
		testProbe(brokerUrl, "DROOLS-InstanceOne", "vera", "griselda", "Robot-ONE", "SLA Alert");
		Thread.sleep(1000);
		testProbe(brokerUrl, "DROOLS-InstanceOne", "vera", "griselda", "ControlledEnvironment", "overload_one");
		System.out.println("SENT");
	}



	private static void printHello() {		
System.out.println("  _     _    __   _  _  _  _  _ \n"
		+ " /_`| |/_`/|//   /_//_// //_)/_`\n"
		+ "/_, |//_,/ |/   /  / \\/_//_)/_, \n"
		+ "                                \n");	
	}
}
