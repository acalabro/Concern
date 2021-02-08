package it.cnr.isti.labsedc.concern.storage;

import it.cnr.isti.labsedc.concern.App;
import it.cnr.isti.labsedc.concern.event.ConcernEvent;

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
	public boolean saveMessage(ConcernEvent<?> message) {
		// TODO Auto-generated method stub
		return false;
	}

}
