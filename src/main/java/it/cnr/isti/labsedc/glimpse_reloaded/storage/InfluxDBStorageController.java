package it.cnr.isti.labsedc.glimpse_reloaded.storage;

import it.cnr.isti.labsedc.glimpse_reloaded.App;
import it.cnr.isti.labsedc.glimpse_reloaded.event.Event;

public class InfluxDBStorageController implements StorageController {

	@Override
	public boolean connectToDB() {
		// TODO Auto-generated method stub
		App.componentStarted.put(this.getClass().getSimpleName(), true);
		return true;
	}

	@Override
	public boolean disconnectFromDB() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveMessage(Event<?> message) {
		// TODO Auto-generated method stub
		return false;
	}

}
