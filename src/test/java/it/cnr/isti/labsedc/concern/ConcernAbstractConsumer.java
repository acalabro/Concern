package it.cnr.isti.labsedc.concern;

import java.util.Vector;

import javax.jms.JMSException;
import javax.jms.Message;

import it.cnr.isti.labsedc.concern.cep.CepType;

public class ConcernAbstractConsumer implements ConcernConsumer {

	public ConcernAbstractConsumer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendEvaluationRequest(String serviceChannel, CepType cepType, Vector<String> evaluationRequests)
			throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void listenForResponse(String responseChannel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendTextMessage(String serviceChannel, String textToSend) throws JMSException {
		// TODO Auto-generated method stub

	}

}
