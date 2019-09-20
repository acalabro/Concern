package it.cnr.isti.labsedc.glimpse_reloaded.cep;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TopicConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drools.core.ClockType;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.core.marshalling.impl.ProtobufMessages.KnowledgeBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.conf.MultithreadEvaluationOption;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

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

        ksession.fireUntilHalt();

/*
		//Container
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSessionConfiguration conf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
		conf.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));


		KieSession asd = kieContainer.newKieSession(conf);
		asd.fireAllRules();
//
//		//KieConfiguration
//		KieBaseConfiguration kieBaseConf = kieService.newKieBaseConfiguration();
//		kieBaseConf.setOption(MultithreadEvaluationOption.NO);
//		kieBaseConf.setOption(EventProcessingOption.STREAM);
//		kieBaseConf.setOption(EventProcessingOption.CLOUD);
//
//        KieModuleModel kproj = kieService.newKieModuleModel();
//
//		 KieBaseModel kBase1 = kproj.newKieBaseModel(instanceName)
//	                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
//	                .setEventProcessingMode(EventProcessingOption.STREAM);
//
//		 KieSessionModel kSessionModel = kBase1
//	                .newKieSessionModel(instanceName + ".KSession")
//	                .setType(KieSessionModel.KieSessionType.STATEFUL)
//	                .setClockType(ClockTypeOption.get("pseudo"))
//	                .setDefault(true);
//
//		 KieSession kSession = kieContainer.newKieSession();
//		 kSession.fireAllRules();
 * */

	}

	private void communicationSetup() throws JMSException {
		receiverConnection = ChannelsManagementRegistry.GetNewTopicConnection();
		receiverSession = ChannelsManagementRegistry.GetNewSession(receiverConnection);
		queue = ChannelsManagementRegistry.GetNewSessionQueue(this.cep.name()+"-"+instanceName, receiverSession, "DroolsService-"+instanceName, ServiceChannelProperties.GENERICREQUESTS);
		logger.info("...CEP named " + this.getInstanceName() + " creates a listening channel called: " + queue.getQueueName());
		MessageConsumer complexEventProcessorReceiver = receiverSession.createConsumer(queue);
		complexEventProcessorReceiver.setMessageListener(this);
		receiverConnection.start();
	}

	@Override
	public void onMessage(Message message) {

		ObjectMessage msg = (ObjectMessage) message;
		try {
				GlimpseEvaluationRequestEvent receivedEvent = (GlimpseEvaluationRequestEvent) msg.getObject();

				System.out.println(receivedEvent.getCepType());

		} catch(ClassCastException | JMSException asd) {
			logger.error("error on casting or getting ObjectMessage to GlimpseEvaluationRequestEvent");
		}
	}
}
