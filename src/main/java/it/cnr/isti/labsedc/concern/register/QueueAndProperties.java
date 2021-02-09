package it.cnr.isti.labsedc.concern.register;

import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.listener.ServiceChannelProperties;

public class QueueAndProperties {

	private String localCepIdentifier;
	private ServiceChannelProperties localChannelProp;
	private CepType localCepType;
	private String localQueueAddress;

	public QueueAndProperties(String cepIdentifier, ServiceChannelProperties channelProperty, CepType cepType, String queueAddress) {
		this.localChannelProp = channelProperty;
		this.localCepIdentifier = cepIdentifier;
		this.localCepType = cepType;
		this.localQueueAddress = queueAddress;
	}

	public String getQueueAddress() {
		return this.localQueueAddress;
	}
	
	public void setQueueAddress(String queueAddress) {
		this.localQueueAddress = queueAddress;
	}
	
	public CepType getLocalCepType() {
		return localCepType;
	}
	public void setLocalCepType(CepType localCepType) {
		this.localCepType = localCepType;
	}
	public String getLocalCepIdentifier() {
		return localCepIdentifier;
	}
	public void setLocalQ(String localCepIdentifier) {
		this.localCepIdentifier = localCepIdentifier;
	}
	public ServiceChannelProperties getServiceChannelProperties() {
		return localChannelProp;
	}
	public void setServiceChannelProperties(ServiceChannelProperties localChannelProp) {
		this.localChannelProp = localChannelProp;
	}
}
