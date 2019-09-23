package it.cnr.isti.labsedc.glimpse_reloaded.event;

import it.cnr.isti.labsedc.glimpse_reloaded.cep.CepType;

public abstract class GlimpseBasicEvent<T> implements Event<T> {

	private String checksum;
	private String sender;
	private CepType cepType;
	private long timestamp;
	protected T data;


	public GlimpseBasicEvent(T data,CepType type,String senderID, String checksum, long timestamp) {
		this.data = data;
		this.timestamp = timestamp;
		this.setCepType(type);
		this.setSender(senderID);
		this.setChecksum(checksum);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
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

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
}
