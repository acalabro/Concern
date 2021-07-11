package it.cnr.isti.labsedc.concern.notification;

import java.util.ArrayList;

public class LoraNotifier {

	public static String spreadingFactor;
	public static ArrayList<String> messageQueue = new ArrayList<>();
	
	public LoraNotifier() {
		
	}

	public static void NotifyOnSerial(String message) {
		
	}
	
	public static void Enqueue(String message) {
		messageQueue.add(message);
	}
}
