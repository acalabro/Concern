package cnr.isti.labsedc.glimpse_reloaded.event;

import cnr.isti.labsedc.glimpse_reloaded.cep.CepType;

public abstract class GlimpseEvaluationRequestEvent implements Event {

	private String checksum;
	private String sender;
	private String evaluationRule;
	private CepType cepType;
	private long timestamp;

	public GlimpseEvaluationRequestEvent(CepType type, String evaluationRule, String senderID, String checksum, long timestamp) {
		this.setCepType(type);
		this.setEvaluationRule(evaluationRule);
		this.setSender(senderID);
		this.setChecksum(checksum);
		this.timestamp = timestamp;
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

	public String getEvaluationRule() {
		return evaluationRule;
	}

	public void setEvaluationRule(String evaluationRule) {
		this.evaluationRule = evaluationRule;
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
