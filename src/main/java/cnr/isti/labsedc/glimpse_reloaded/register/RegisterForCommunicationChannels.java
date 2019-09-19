package cnr.isti.labsedc.glimpse_reloaded.register;

import java.util.HashMap;

import org.apache.activemq.ActiveMQConnectionFactory;

import cnr.isti.labsedc.glimpse_reloaded.cep.CepType;
import cnr.isti.labsedc.glimpse_reloaded.listener.ServiceChannel;

public class RegisterForCommunicationChannels {

	public static HashMap<CepType, String> CepActiveOnWhichChannels = new HashMap<CepType, String>(); //channel where the cep will listen for incoming evaluation requests

	public static HashMap<ServiceChannel, String> ServiceListeningOnWhichChannel = new HashMap<ServiceChannel, String>();
	//channel on which the system will listen for incoming messages (requests to forward to a specific cep)

	public static HashMap<String, String> ConsumerListeningOnWhichChannel = new HashMap<String, String>();
	//map the consumer that requests an evaluation and are waiting for a response on that channel dnamically created

	public static HashMap<String, String> ProbesOpenChannels = new HashMap<String, String>();
	//channels available for probes

	public static ActiveMQConnectionFactory connectionFactory;
	//the brokerconnection factory

	public void setConnectionFactory(ActiveMQConnectionFactory factory) {
		RegisterForCommunicationChannels.connectionFactory = factory;
	}

	public static ActiveMQConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

}
