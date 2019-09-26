package it.cnr.isti.labsedc.glimpse_reloaded.utils;

import it.cnr.isti.labsedc.glimpse_reloaded.cep.CepType;
import it.cnr.isti.labsedc.glimpse_reloaded.event.GlimpseEvaluationRequestEvent;
import it.cnr.isti.labsedc.glimpse_reloaded.register.ChannelsManagementRegistry;
import it.cnr.isti.labsedc.glimpse_reloaded.register.QueueAndProperties;

public class RoutingUtilities {

	private static CepType cepType;

	public static <T> QueueAndProperties BestCepSelection(GlimpseEvaluationRequestEvent<T> message) {

		cepType = message.getCepType();
		//TODO: FIX
		return ChannelsManagementRegistry.ActiveCep.get(cepType);
	}


}
