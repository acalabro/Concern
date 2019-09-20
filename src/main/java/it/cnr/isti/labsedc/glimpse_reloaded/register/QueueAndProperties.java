package it.cnr.isti.labsedc.glimpse_reloaded.register;

import it.cnr.isti.labsedc.glimpse_reloaded.listener.ServiceChannelProperties;

public class QueueAndProperties {

	private String localQName;
	private ServiceChannelProperties localProp;

	public QueueAndProperties(String queueName, ServiceChannelProperties property) {
		this.localProp = property;
		this.localQName = queueName;
	}
	public String getLocalQName() {
		return localQName;
	}
	public void setLocalQ(String localQ) {
		this.localQName = localQ;
	}
	public ServiceChannelProperties getLocalProp() {
		return localProp;
	}
	public void setLocalProp(ServiceChannelProperties localProp) {
		this.localProp = localProp;
	}

}
