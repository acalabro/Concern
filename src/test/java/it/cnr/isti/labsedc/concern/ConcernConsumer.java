package it.cnr.isti.labsedc.concern;

import java.util.Vector;

import javax.jms.JMSException;
import javax.jms.MessageListener;

import it.cnr.isti.labsedc.concern.cep.CepType;


public interface ConcernConsumer extends MessageListener {

		void sendEvaluationRequest(String serviceChannel, CepType cepType, Vector<String> evaluationRequests) throws JMSException;
		void listenForResponse(String responseChannel);

		void sendTextMessage(String serviceChannel, String textToSend) throws JMSException;
	}