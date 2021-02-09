package it.cnr.isti.labsedc.concern.client;

import it.cnr.isti.labsedc.concern.GlimpseApp;

public class ClientManager {

	public ClientManager() {
		GlimpseApp.componentStarted.put(this.getClass().getSimpleName(), true);
	}

}
