package it.cnr.isti.labsedc.concern.utils;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TestMosquito {

	public static void main(String[] args) {
	
	//MOSQUITOTEST
	try {
		MqttClient client2 = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
		client2.setCallback( new SimpleMqttCallBack() );
		client2.connect();
		client2.subscribe("iot_data"); 
		System.out.println("listening");
	} catch (MqttException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
		client.connect();
		MqttMessage message = new MqttMessage();
		message.setPayload("Hello world from Java".getBytes());
		client.publish("iot_data", message);
		System.out.println("sent");
		client.disconnect();

	} catch (MqttException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	//ENDMOSQUITOTEST
	}
}
