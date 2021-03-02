package it.cnr.isti.labsedc.concern.event;

import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.eventListener.ChannelProperties;

  public class ConcernEvaluationRequestEvent<T> extends ConcernAbstractEvent<T> {
    
	private static final long serialVersionUID = 6545740721731539243L;
	private String evaluationRule;
	private ChannelProperties propertyRequested;

	public ConcernEvaluationRequestEvent(
			T data,
			CepType type,
			String evaluationRule,
			ChannelProperties propertyRequested,
			String senderID,
			String checksum,
			long timestamp) {
		super(data, type, senderID, checksum, timestamp);
		this.setEvaluationRule(evaluationRule);
		this.setPropertyRequested(propertyRequested);
	}

	public ChannelProperties getPropertyRequested() {
		return propertyRequested;
	}

	public void setPropertyRequested(ChannelProperties propertyRequested) {
		this.propertyRequested = propertyRequested;
	}

	public String getEvaluationRule() {
		return evaluationRule;
	}

	public void setEvaluationRule(String evaluationRule) {
		this.evaluationRule = evaluationRule;
	}
}
