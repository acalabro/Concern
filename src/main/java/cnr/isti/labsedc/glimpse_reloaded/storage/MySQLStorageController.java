package cnr.isti.labsedc.glimpse_reloaded.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cnr.isti.labsedc.glimpse_reloaded.event.Event;

public class MySQLStorageController implements StorageController {

    private static final Logger logger = LogManager.getLogger(MySQLStorageController.class);

	public MySQLStorageController() {

		logger.info("entering storage");
		}

	@Override
	public boolean connectToDB() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disconnectFromDB() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveMessage(Event message) {
		// TODO Auto-generated method stub
		return false;
	}

}
