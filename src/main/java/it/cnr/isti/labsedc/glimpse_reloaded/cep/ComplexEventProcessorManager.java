package it.cnr.isti.labsedc.glimpse_reloaded.cep;

import javax.jms.Message;

public abstract class ComplexEventProcessorManager extends Thread{

	public abstract void onMessage(Message message);
}
