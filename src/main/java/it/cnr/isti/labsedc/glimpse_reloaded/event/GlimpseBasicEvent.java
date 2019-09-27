package it.cnr.isti.labsedc.glimpse_reloaded.event;

import java.io.Serializable;

import it.cnr.isti.labsedc.glimpse_reloaded.cep.CepType;

public abstract class GlimpseBasicEvent<T> implements Event<T>, Serializable {

	private static final long serialVersionUID = 7077313246352116557L;
	private String checksum;
	private String sender;
	private CepType cepType;
	private long timestamp;
	private T data;


	public GlimpseBasicEvent(T data,CepType type,String senderID, String checksum, long timestamp) {
		this.setEventData(data);
		this.setTimestamp(timestamp);
		this.setCepType(type);
		this.setSenderID(senderID);
		this.setChecksum(checksum);
	}

	public T getEventData() {
		return data;
	}

	public void setEventData(T data) {
		this.data = data;
	}

	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public CepType getCepType() {
		return cepType;
	}

	public void setCepType(CepType cepType) {
		this.cepType = cepType;
	}

	public String getSenderID() {
		return sender;
	}

	public void setSenderID(String sender) {
		this.sender = sender;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
}
