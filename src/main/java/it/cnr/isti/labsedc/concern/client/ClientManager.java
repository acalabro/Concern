package it.cnr.isti.labsedc.concern.client;

import it.cnr.isti.labsedc.concern.App;

public class ClientManager {

	public ClientManager() {
		App.componentStarted.put(this.getClass().getSimpleName(), true);
	}

}
