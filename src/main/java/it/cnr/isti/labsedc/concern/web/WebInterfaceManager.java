package it.cnr.isti.labsedc.concern.web;

import it.cnr.isti.labsedc.concern.App;

public class WebInterfaceManager {

	public WebInterfaceManager() {
		App.componentStarted.put(this.getClass().getSimpleName(), true);
	}
}
