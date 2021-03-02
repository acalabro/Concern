package it.cnr.isti.labsedc.concern.event;

import it.cnr.isti.labsedc.concern.cep.CepType;

public class ConcernArduinoEvent<T> extends ConcernAbstractEvent<T> {

	private static final long serialVersionUID = 1L;
	private String relayStatus;
	
	public ConcernArduinoEvent(
			T sensorData, 
			CepType type, 
			String senderID, 
			String checksum, 
			long timestamp, 
			String relayStatus) {
		super(sensorData, type, senderID, checksum, timestamp);
		this.relayStatus = relayStatus;		
	}

	public void setRelayStatus(String relayStatus) {
		this.relayStatus = relayStatus;
	}
	
	public String getRelayStstus() {
		return this.relayStatus;
	}

}
