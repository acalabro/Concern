package it.cnr.isti.labsedc.concern.event;

import it.cnr.isti.labsedc.concern.cep.CepType;

public class ConcernCANbusEvent<T> extends ConcernAbstractEvent<T> {

	private static final long serialVersionUID = 1L;
	private String canAddress;
	
	public ConcernCANbusEvent(
			T canData, 
			CepType type, 
			String senderID, 
			String checksum, 
			long timestamp, 
			String canAddress) {
		super(canData, type, senderID, checksum, timestamp);
		this.canAddress = canAddress;		
	}

	public void setCanAddress(String canAddress) {
		this.canAddress = canAddress;
	}
	
	public String getCanAddress() {
		return this.canAddress;
	}

}
