package it.cnr.isti.labsedc.glimpse_reloaded.notification;

import it.cnr.isti.labsedc.glimpse_reloaded.App;

public class NotificationManager {

	public NotificationManager() {
		App.componentStarted.put(this.getClass().getSimpleName(), true);
	}
}
