package it.cnr.isti.labsedc.concern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.Connection;
import org.json.JSONObject;

import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.event.ConcernWiFiEvent;
import it.cnr.isti.labsedc.concern.event.PacketType;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TransponderWiFiProbe {

	static Session session;
	static MessageProducer producer;
	static String macAddress;
	static String receivedDb;
	
	public static void main(String[] args) throws InterruptedException {
		String brokerUrl = "tcp://0.0.0.0:61616";
		String username = "vera";
		String password = "griselda";
		String topicName = "DROOLS-InstanceOne";
		String device = "wlp0s20f3";
		//args 0 = username, args 1 = password, args 2 = brokerUrl, args 3 = topicName, args 4 = device
		
		printHello();
		try {		
	//		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(args[0], args[1], args[2]);
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
			Connection connection = connectionFactory.createConnection();
			
	        session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
	        
	        Topic topic = session.createTopic(topicName);
	        //Topic topic = session.createTopic(args[3]);
	        
	        producer = session.createProducer(topic); 
	        
	        loopThread();
 
		} catch (JMSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}   
	}	
		
	private static void loopThread() {
		//final Process p = Runtime.getRuntime().exec("tcpdump -i "+ args[4] + " -e type mgt");
		try {	
			Process p = Runtime.getRuntime().exec("tcpdump -i wlp0s20f3 -e");
		new Thread(new Runnable() {
		    public void run() {
		        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        String line = null;

		        try {
		            while ((line = input.readLine()) != null)
		            	
		            	if (line != null) //  || line.contains("signal:"))
	                	{
		            		macAddress = "asd";
		            		receivedDb = "10";
	                	
		            	sendMessage(session, new ConcernWiFiEvent<String>(System.currentTimeMillis(),
		            			"Wi-Fi-Probe", 
		            			"Monitor", 
		            			this.getClass().getName()+"-Session1", 
		            			"",
		            			"tracking","Wi-Fi-trace",
		            			CepType.DROOLS,
		            			macAddress,
		            			PacketType.PROBE_REQUEST,
		            			receivedDb),producer);
	                	}
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}).start();

			p.waitFor();
		} catch (InterruptedException | IOException e1) {
			e1.printStackTrace();
		}		
	}

	private static void sendMessage(Session session, ConcernWiFiEvent<String> event, MessageProducer producer) {
		try {
			ObjectMessage msg = session.createObjectMessage();
			msg.setObject(event);
		producer.send(msg);
		System.out.println("Message Sent");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
//	private ConcernWiFiEvent<String> packEvent(String senderID, ) {
//	
//		 ConcernWiFiEvent<String> event = new ConcernWiFiEvent<String>(System.currentTimeMillis(),
//				"WiFi-Probe", "monitoring", "emergencyDataSession", "none", 
//				"device found", "2412Mhz", CepType.DROOLS, "8c:8d:ab:10:40:bd",
//				PacketType.PROBE_REQUEST,-38f);
//		 return event;
//	}

//	private static void testJson() {
//		ConcernWiFiEvent<String> event = new ConcernWiFiEvent<String>(System.currentTimeMillis(),
//				"WiFi-Probe", "monitoring", "emergencyDataSession", "none", 
//				"device found", "2412Mhz", CepType.DROOLS, "8c:8d:ab:10:40:bd",
//				PacketType.PROBE_REQUEST,-38f);
//		JSONObject jsonObj = new JSONObject( event );
//        System.out.println( jsonObj );		
//	}

	private static void printHello() {
		
		System.out.println(" _    _ _       ______ _  ______          _\n"          
		 + "| |  | (_)      |  ___(_) | ___ \\        | |\n"
		 + "| |  | |_ ______| |_   _  | |_/ / __ ___ | |__   ___\n"
		 + "| |/\\| | |______|  _| | | |  __/ '__/ _ \\| '_ \\ / _ \\\n"
		 + "\\  /\\  / |      | |   | | | |  | | | (_) | |_) |  __/\n"
		 + " \\/  \\/|_|      \\_|   |_| \\_|  |_|  \\___/|_.__/ \\___|\n");
		                                                      
	}
}
