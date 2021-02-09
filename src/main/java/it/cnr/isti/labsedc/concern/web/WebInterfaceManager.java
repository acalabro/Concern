package it.cnr.isti.labsedc.concern.web;

import it.cnr.isti.labsedc.concern.GlimpseApp;

public class WebInterfaceManager {

	public WebInterfaceManager() {
		GlimpseApp.componentStarted.put(this.getClass().getSimpleName(), true);
	}
}
