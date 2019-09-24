package it.cnr.isti.labsedc.glimpse_reloaded.cep;

import java.util.Collection;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import it.cnr.isti.labsedc.glimpse_reloaded.event.GlimpseEvaluationRequestEvent;
import it.cnr.isti.labsedc.glimpse_reloaded.listener.ServiceChannelProperties;
import it.cnr.isti.labsedc.glimpse_reloaded.register.ChannelsManagementRegistry;

public class DroolsComplexEventProcessorManager extends ComplexEventProcessorManager implements MessageListener {

    private static final Logger logger = LogManager.getLogger(DroolsComplexEventProcessorManager.class);
	private TopicConnection receiverConnection;
	private Queue queue;
	private Session receiverSession;
	private CepType cep;
	private String instanceName;
	private String staticRuleToLoadAtStartup;
	private boolean started = false;

	private static KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    private static Collection<KiePackage> pkgs;
    private static InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    private static KieSession ksession;

	public DroolsComplexEventProcessorManager(String instanceName, String staticRuleToLoadAtStartup) {
		super();
		logger.info("asd");
		cep = CepType.DROOLS;
		this.instanceName = instanceName;
		this.staticRuleToLoadAtStartup = staticRuleToLoadAtStartup;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	@Override
	public void run() {
		try {
			communicationSetup();
			droolsEngineSetup();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void droolsEngineSetup() {

		Resource drlToLoad = ResourceFactory.newFileResource(staticRuleToLoadAtStartup);
        kbuilder.add(drlToLoad, ResourceType.DRL);

        if(kbuilder.hasErrors()) {
            System.out.println(kbuilder.getErrors().toString());
            throw new RuntimeException("unable to compile dlr");
        }

        pkgs = kbuilder.getKnowledgePackages();
        kbase.addPackages(pkgs);
        ksession = kbase.newKieSession();
		logger.info("...CEP named " + this.getInstanceName() + " created Session and fires rules " + staticRuleToLoadAtStartup + " with errors: " + kbuilder.getKnowledgePackages());
		started  = true;
		ksession.fireUntilHalt();
	}

	private void communicationSetup() throws JMSException {
		receiverConnection = ChannelsManagementRegistry.GetNewTopicConnection();
		receiverSession = ChannelsManagementRegistry.GetNewSession(receiverConnection);
		queue = ChannelsManagementRegistry.RegisterNewCepQueue(this.cep.name()+"-"+instanceName, receiverSession, "DroolsService-"+instanceName, ServiceChannelProperties.GENERICREQUESTS);
		logger.info("...CEP named " + this.getInstanceName() + " creates a listening channel called: " + queue.getQueueName());
		MessageConsumer complexEventProcessorReceiver = receiverSession.createConsumer(queue);
		complexEventProcessorReceiver.setMessageListener(this);
		receiverConnection.start();
	}

	@Override
	public void onMessage(Message message) {

		if (message instanceof ObjectMessage) {
			try {
				ObjectMessage msg = (ObjectMessage) message;
				GlimpseEvaluationRequestEvent<?> receivedEvent = (GlimpseEvaluationRequestEvent<?>) msg.getObject();
				logger.info("...CEP named " + this.getInstanceName() + " receives "  + receivedEvent.getCepType());
				} catch(ClassCastException | JMSException asd) {
					logger.error("error on casting or getting ObjectMessage to GlimpseEvaluationRequestEvent");
				}
		}
		if (message instanceof TextMessage) {
			TextMessage msg = (TextMessage) message;
			try {
				logger.info("CEP " + this.instanceName + " receives TextMessage: " + msg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean cepHasCompletedStartup() {
		return started;
	}
}
