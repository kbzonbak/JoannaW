//package bbr.b2b.soa.integrator.config.classes;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.annotation.EnableJms;
//import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
//import org.springframework.jms.core.JmsTemplate;
//
//@Configuration
//@EnableJms
//public class ActiveMQJMSConfig {
//
////	@Value("${amq.enabled}")
////	private Boolean active;	
//	
//	
//	@Bean
//	public ActiveMQConnectionFactory connectionFactory() {
//		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();		
//		return connectionFactory;
//	}
//
//	@Bean
//	public JmsTemplate jmsTemplate() {
//		JmsTemplate template = new JmsTemplate();
//		template.setConnectionFactory(connectionFactory());		
//		return template;
//	}
//	
//	@Bean
//	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
//		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//		
////		factory.setTransactionManager(jmsTransactionManager(connectionFactory()));
////		factory.setSessionTransacted(true);
////		factory.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
//		
//		factory.setConnectionFactory(connectionFactory());
//		factory.setConcurrency("1-1");
//		//factory.setAutoStartup(active);
//		return factory;
//	}
//	
////	@Bean
////    public PlatformTransactionManager jmsTransactionManager(ConnectionFactory connectionFactory) {
////        return new JmsTransactionManager(connectionFactory);
////    }
//	
//}
