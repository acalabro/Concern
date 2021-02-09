package it.cnr.isti.labsedc.concern.event;

public interface Event <T>{

	public T getEventData();
	public void setEventData(T t);
	public long getTimestamp();
	public void setTimestamp(long timestamp);
	public String getSenderID();
	public void setSenderID(String senderID);

}
