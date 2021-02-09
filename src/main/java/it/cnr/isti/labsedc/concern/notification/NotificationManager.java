package it.cnr.isti.labsedc.concern.notification;

import it.cnr.isti.labsedc.concern.GlimpseApp;

public class NotificationManager {

	public NotificationManager() {
		GlimpseApp.componentStarted.put(this.getClass().getSimpleName(), true);
	}
}
