package it.cnr.isti.labsedc.concern.register;

import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.listener.ServiceChannelProperties;

public class QueueAndProperties {

	private String localCepIdentifier;
	private ServiceChannelProperties localChannelProp;
	private CepType localCepType;

	public QueueAndProperties(String cepIdentifier, ServiceChannelProperties channelProperty, CepType cepType) {
		this.localChannelProp = channelProperty;
		this.localCepIdentifier = cepIdentifier;
		this.localCepType = cepType;
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
	public ServiceChannelProperties getLocalProp() {
		return localChannelProp;
	}
	public void setLocalProp(ServiceChannelProperties localChannelProp) {
		this.localChannelProp = localChannelProp;
	}

}
