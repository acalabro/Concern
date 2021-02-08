package it.cnr.isti.labsedc.concern.storage;

import it.cnr.isti.labsedc.concern.event.ConcernEvent;

public interface StorageController {


	public boolean connectToDB();
	public boolean disconnectFromDB();

	public boolean saveMessage(ConcernEvent<?> message);
}
