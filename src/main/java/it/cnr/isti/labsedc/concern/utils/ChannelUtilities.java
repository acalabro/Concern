package it.cnr.isti.labsedc.concern.utils;

import java.util.Vector;

public class ChannelUtilities {

	public static Vector<String> loadChannels() {

		Vector<String> listenerChannels = new Vector<String>();

		listenerChannels.add("ServiceChannel-ONE");
		listenerChannels.add("ServiceChannel-TWO");
		listenerChannels.add("ServiceChannel-THREE");

		return listenerChannels;
	}
}
