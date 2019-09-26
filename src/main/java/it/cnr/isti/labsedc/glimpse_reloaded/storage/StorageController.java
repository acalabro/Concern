package it.cnr.isti.labsedc.glimpse_reloaded.storage;

import it.cnr.isti.labsedc.glimpse_reloaded.event.Event;

public interface StorageController {


	public boolean connectToDB();
	public boolean disconnectFromDB();

	public boolean saveMessage(Event<?> message);
}
