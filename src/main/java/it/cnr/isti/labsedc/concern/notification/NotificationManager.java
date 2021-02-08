package it.cnr.isti.labsedc.concern.notification;

import it.cnr.isti.labsedc.concern.App;

public class NotificationManager {

	public NotificationManager() {
		App.componentStarted.put(this.getClass().getSimpleName(), true);
	}
}
