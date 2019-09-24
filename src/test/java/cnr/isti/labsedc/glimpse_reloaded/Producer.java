package cnr.isti.labsedc.glimpse_reloaded;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

	public static void testProducer(String brokerUrl, String queueName) {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(queue);
			Message msg = session.createTextMessage("ASD");
	        producer.send(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		String brokerUrl = "tcp://localhost:61616";

		testProducer(brokerUrl, "DroolsService-InstanceOne");
		Thread.sleep(500);
		testProducer(brokerUrl, "DroolsService-InstanceTwo");
		Thread.sleep(500);
		testProducer(brokerUrl, "ServiceChannel-ONE");
		Thread.sleep(500);
		testProducer(brokerUrl, "ServiceChannel-TWO");
		Thread.sleep(500);
		testProducer(brokerUrl, "ServiceChannel-THREE");
		Thread.sleep(500);
		testProducer(brokerUrl, "ServiceChannel-FOUR");
		Thread.sleep(500);
		testProducer(brokerUrl, "ServiceChannel-FIVE");
	}
}
