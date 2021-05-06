package it.cnr.isti.labsedc.concern;

import javax.jms.JMSException;

import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.event.ConcernEvaluationRequestEvent;
import it.cnr.isti.labsedc.concern.eventListener.ChannelProperties;
import it.cnr.it.labsedc.concern.consumer.ConcernAbstractConsumer;

public class Consumer {

	public static void main(String[] args) throws InterruptedException {
		//String brokerUrl = "tcp://localhost:61616";
		String brokerUrl = "tcp://146.48.84.187:61616";

		ConcernAbstractConsumer cons = new ConcernAbstractConsumer();
		try {
			cons.init(brokerUrl,"vera", "griselda!!");
			ConcernEvaluationRequestEvent<String> ruleToEvaluate = 
					new ConcernEvaluationRequestEvent<String>(
							"theRule",CepType.DROOLS, "An example rule", ChannelProperties.GENERICREQUESTS, "Consumer-ONE", "sessionID", "283423094skndlsfdn", System.currentTimeMillis());
			cons.sendEvaluationRequest("ServiceChannel-ONE", ruleToEvaluate);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
