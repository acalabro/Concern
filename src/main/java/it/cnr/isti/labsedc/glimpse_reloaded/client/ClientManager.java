package it.cnr.isti.labsedc.glimpse_reloaded.client;

import it.cnr.isti.labsedc.glimpse_reloaded.App;

public class ClientManager {

	public ClientManager() {
		App.componentStarted.put(this.getClass().getSimpleName(), true);
	}

}
