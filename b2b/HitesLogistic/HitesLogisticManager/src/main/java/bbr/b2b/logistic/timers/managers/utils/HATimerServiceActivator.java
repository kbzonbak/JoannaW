/*package bbr.b2b.logistic.timers.managers.utils;

import org.jboss.as.server.ServerEnvironment;
import org.jboss.as.server.ServerEnvironmentService;
import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceNotFoundException;
import org.jboss.msc.value.InjectedValue;
import org.wildfly.clustering.singleton.SingletonServiceBuilderFactory;
import org.wildfly.clustering.singleton.SingletonServiceName;
import org.wildfly.clustering.singleton.election.NamePreference;
import org.wildfly.clustering.singleton.election.PreferredSingletonElectionPolicy;
import org.wildfly.clustering.singleton.election.SimpleSingletonElectionPolicy;

public class HATimerServiceActivator implements ServiceActivator {

	private static final String CONTAINER_NAME = "server";

	public static final String PREFERRED_NODE = HATimerService.NODE_1;

	@Override
	public void activate(ServiceActivatorContext context) {
		install(HATimerService.DEFAULT_SERVICE_NAME, 1, context);
	}

	private static void install(ServiceName name, int quorum, ServiceActivatorContext context) {
		InjectedValue<ServerEnvironment> env = new InjectedValue<>();
		HATimerService service = new HATimerService(env);
		SingletonServiceBuilderFactory factory;
		try {
			factory = (SingletonServiceBuilderFactory) context.getServiceRegistry().getRequiredService(SingletonServiceName.BUILDER.getServiceName(CONTAINER_NAME)).awaitValue();
			factory.createSingletonServiceBuilder(name, service).electionPolicy(new PreferredSingletonElectionPolicy(new SimpleSingletonElectionPolicy(), new NamePreference(PREFERRED_NODE + "/" + CONTAINER_NAME))).requireQuorum(quorum).build(context.getServiceTarget())
					.addDependency(ServerEnvironmentService.SERVICE_NAME, ServerEnvironment.class, env).setInitialMode(ServiceController.Mode.ACTIVE).install();
		} catch (ServiceNotFoundException | IllegalStateException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}*/