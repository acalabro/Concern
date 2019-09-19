package cnr.isti.labsedc.glimpse_reloaded.event;

public interface Event {

	public long getTimestamp();
	public void setTimestamp(long timestamp);
	public String getSenderID();
	public void setSenderID(String senderID);

}
