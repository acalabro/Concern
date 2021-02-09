package it.cnr.isti.labsedc.concern.utils;

import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.event.GlimpseEvaluationRequestEvent;
import it.cnr.isti.labsedc.concern.listener.ServiceChannelProperties;
import it.cnr.isti.labsedc.concern.register.ChannelsManagementRegistry;
import it.cnr.isti.labsedc.concern.register.QueueAndProperties;

public class RoutingUtilities {

	private static CepType cepType;
	private static ServiceChannelProperties requestedProperties;

	public static QueueAndProperties BestCepSelection(GlimpseEvaluationRequestEvent<?> message) {

		cepType = message.getCepType();
		requestedProperties = message.getPropertyRequested();
		QueueAndProperties localQaP;

		for (int i = 0; i<ChannelsManagementRegistry.ActiveCep.size();i++) {
			localQaP = (QueueAndProperties) ChannelsManagementRegistry.ActiveCep.keySet().toArray()[i];
			if (cepType ==  localQaP.getLocalCepType() && requestedProperties ==  localQaP.getServiceChannelProperties()) {
				return localQaP;
					}
			}
		return null;
		}
}
