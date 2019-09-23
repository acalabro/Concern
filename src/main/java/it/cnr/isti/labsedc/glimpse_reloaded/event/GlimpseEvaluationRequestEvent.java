package it.cnr.isti.labsedc.glimpse_reloaded.event;

import it.cnr.isti.labsedc.glimpse_reloaded.cep.CepType;

public abstract class GlimpseEvaluationRequestEvent<T> extends GlimpseBasicEvent<T> {

	private String evaluationRule;

	public GlimpseEvaluationRequestEvent(T data,CepType type, String evaluationRule, String senderID, String checksum, long timestamp) {
		super(data, type, senderID, checksum, timestamp);
		this.setEvaluationRule(evaluationRule);
	}

	public String getEvaluationRule() {
		return evaluationRule;
	}

	public void setEvaluationRule(String evaluationRule) {
		this.evaluationRule = evaluationRule;
	}
}
