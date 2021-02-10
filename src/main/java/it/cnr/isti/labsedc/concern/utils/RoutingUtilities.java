package it.cnr.isti.labsedc.concern.utils;

import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.event.ConcernBasicEvent;
import it.cnr.isti.labsedc.concern.event.ConcernEvaluationRequestEvent;
import it.cnr.isti.labsedc.concern.eventListener.ChannelProperties;
import it.cnr.isti.labsedc.concern.register.ChannelsManagementRegistry;
import it.cnr.isti.labsedc.concern.register.QueueAndProperties;

public class RoutingUtilities {

	private static CepType cepType;
	private static ChannelProperties requestedProperties;

	public static QueueAndProperties BestCepSelectionForRules(ConcernEvaluationRequestEvent<?> message) {

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
	
	public static QueueAndProperties BestCepSelectionForEvents(ConcernBasicEvent<?> message) {

		cepType = message.getCepType();
		QueueAndProperties localQaP;

		for (int i = 0; i<ChannelsManagementRegistry.ActiveCep.size();i++) {
			localQaP = (QueueAndProperties) ChannelsManagementRegistry.ActiveCep.keySet().toArray()[i];
			if (cepType ==  localQaP.getLocalCepType()) {
				return localQaP;
					}
			}
		return null;
		}
	
}
