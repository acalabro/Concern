package it.cnr.isti.labsedc.glimpse_reloaded.utils;

import it.cnr.isti.labsedc.glimpse_reloaded.cep.CepType;
import it.cnr.isti.labsedc.glimpse_reloaded.event.GlimpseEvaluationRequestEvent;
import it.cnr.isti.labsedc.glimpse_reloaded.listener.ServiceChannelProperties;
import it.cnr.isti.labsedc.glimpse_reloaded.register.ChannelsManagementRegistry;
import it.cnr.isti.labsedc.glimpse_reloaded.register.QueueAndProperties;

public class RoutingUtilities {

	private static CepType cepType;
	private static ServiceChannelProperties requestedProperties;

	public static String BestCepSelection(GlimpseEvaluationRequestEvent<?> message) {

		cepType = message.getCepType();
		requestedProperties = message.getPropertyRequested();

		QueueAndProperties localQaP;

		for (int i = 0; i<ChannelsManagementRegistry.ActiveCep.size();i++) {
			localQaP = (QueueAndProperties) ChannelsManagementRegistry.ActiveCep.keySet().toArray()[i];
			if (cepType ==  localQaP.getLocalCepType() && requestedProperties ==  localQaP.getLocalProp()) {
				return (String)ChannelsManagementRegistry.ActiveCep.values().toArray()[i];
					}
			}
		return null;
		}
}
