package it.cnr.isti.labsedc.concern.event;

import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.listener.ServiceChannelProperties;

  public class ConcernEvaluationRequestEvent<T> extends ConcernBasicEvent<T> {
    
	private static final long serialVersionUID = 6545740721731539243L;
	private String evaluationRule;
	private ServiceChannelProperties propertyRequested;

	public ConcernEvaluationRequestEvent(
			T data,
			CepType type,
			String evaluationRule,
			ServiceChannelProperties propertyRequested,
			String senderID,
			String checksum,
			long timestamp) {
		super(data, type, senderID, checksum, timestamp);
		this.setEvaluationRule(evaluationRule);
		this.setPropertyRequested(propertyRequested);
	}

	public ServiceChannelProperties getPropertyRequested() {
		return propertyRequested;
	}

	public void setPropertyRequested(ServiceChannelProperties propertyRequested) {
		this.propertyRequested = propertyRequested;
	}

	public String getEvaluationRule() {
		return evaluationRule;
	}

	public void setEvaluationRule(String evaluationRule) {
		this.evaluationRule = evaluationRule;
	}
}
