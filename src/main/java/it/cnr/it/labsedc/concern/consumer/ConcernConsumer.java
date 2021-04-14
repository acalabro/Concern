package it.cnr.it.labsedc.concern.consumer;

import javax.jms.JMSException;
import javax.jms.MessageListener;

import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.event.ConcernEvaluationRequestEvent;


public interface ConcernConsumer extends MessageListener {

		void sendEvaluationRequest(String serviceChannel, CepType cepType, ConcernEvaluationRequestEvent<String> evaluationRequests) throws JMSException;
		void listenForResponse(String responseChannel);
		void init(String brokerUrl, String topicChannel, String username, String password) throws JMSException;
		void sendTextMessage(String serviceChannel, String textToSend) throws JMSException;
	}