package it.cnr.isti.labsedc.concern.event;

import it.cnr.isti.labsedc.concern.cep.CepType;

public class ConcernArduinoEvent<T> extends ConcernAbstractEvent<T> {

	private static final long serialVersionUID = 1L;
	private String relayStatus;
	
	public ConcernArduinoEvent(
			long timestamp,
			String senderID,
			String destinationID,
			String sessionID,
			String checksum,
			String name,
			T ruleData,
			CepType type,
			String relayStatus) {
		super(timestamp, senderID, destinationID, sessionID, checksum, name, ruleData, type);
		this.relayStatus = relayStatus;	
	}

	public void setRelayStatus(String relayStatus) {
		this.relayStatus = relayStatus;
	}
	
	public String getRelayStstus() {
		return this.relayStatus;
	}
}
