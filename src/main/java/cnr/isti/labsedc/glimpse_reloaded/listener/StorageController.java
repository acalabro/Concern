package cnr.isti.labsedc.glimpse_reloaded.listener;

import cnr.isti.labsedc.glimpse_reloaded.event.Event;

public interface StorageController {


	public boolean connectToDB();
	public boolean disconnectFromDB();

	public boolean saveMessage(Event message);
}
