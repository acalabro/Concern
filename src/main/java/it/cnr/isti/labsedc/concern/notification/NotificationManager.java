package it.cnr.isti.labsedc.concern.notification;

import it.cnr.isti.labsedc.concern.ConcernApp;

public class NotificationManager {

	public NotificationManager() {
		ConcernApp.componentStarted.put(this.getClass().getSimpleName(), true);
	}
}
