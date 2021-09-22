package it.cnr.isti.labsedc.concern.mediator;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import it.cnr.isti.labsedc.concern.utils.ConcernMQTTCallBack;

public class Mediator extends Thread {
	
	static MqttClient sender;
	static MqttClient listener;
	static String MQTTEntrypoint;
	static MqttMessage message;
	static String MQTTListenerChannel;
	
	public Mediator(String MQTTEntrypoint, String MQTTListenerChannel) {
		Mediator.MQTTEntrypoint = MQTTEntrypoint;
		Mediator.MQTTListenerChannel = MQTTListenerChannel;
	}

	public void run() {
		try {
			sender = new MqttClient(Mediator.MQTTEntrypoint, MqttClient.generateClientId());
			sender.connect();
			listener = new MqttClient(Mediator.MQTTEntrypoint, MqttClient.generateClientId());
			listener.setCallback(new ConcernMQTTCallBack());
			listener.connect();
			listener.subscribe(MQTTListenerChannel);
			
			
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}		
	
	public static void prepareMessage(String msg) {
		message = new MqttMessage();

	}
	
	public static void send(String channel, MqttMessage msg) {
		
	}
}
