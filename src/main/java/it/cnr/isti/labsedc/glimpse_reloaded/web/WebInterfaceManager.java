package it.cnr.isti.labsedc.glimpse_reloaded.web;

import it.cnr.isti.labsedc.glimpse_reloaded.App;

public class WebInterfaceManager {

	public WebInterfaceManager() {
		App.componentStarted.put(this.getClass().getSimpleName(), true);
	}
}
