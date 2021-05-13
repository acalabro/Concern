package it.cnr.isti.labsedc.concern.notification;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.cnr.isti.labsedc.concern.ConcernApp;
import it.cnr.isti.labsedc.concern.register.ChannelsManagementRegistry;

public class NotificationManager extends Thread {

	private static Session session;
	private static Topic topic;
	private static Logger logger;
	
	public NotificationManager() {
		ConcernApp.componentStarted.put(this.getClass().getSimpleName(), true);
		logger = LogManager.getLogger(NotificationManager.class);

	}
	
	public static void NotifyToConsumer(String consumerName, String violationMessage) {
		logger.info(violationMessage);

//		try {
//			logger.info("Creating response topic");
//			topic = session.createTopic(ChannelsManagementRegistry.getConsumerChannel(consumerName));
//
//        MessageProducer producer = session.createProducer(topic);
//		TextMessage msg = session.createTextMessage(violationMessage);
//		producer.send(msg);
//		} catch (JMSException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void run() {

	//TODO:
		
	}
}
