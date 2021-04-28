package bbr.b2b.regional.logistic.vev.rest.classes;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.vev.rest.swagger.SwaggerService;

@ApplicationPath("/")
public class RestApplicationConfig extends Application {
    
	private Set<Object> singletons = new HashSet<Object>(); 
	private Set<Class<?>> empty = new HashSet<Class<?>>(); 

	public RestApplicationConfig() throws OperationFailedException {
		singletons.add(new DODeliveryAPIRESTManager());
		singletons.add(new DummyAPIRestManager());
		singletons.add(new SwaggerService());
	}
	
	@Override	
	public Set<Object> getSingletons() {
		return singletons;
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		// SI SE QUIERE AGREGAR UN ENDPOINT AL SWAGGER SE AGREGA ACA
		empty.add(DODeliveryAPIRESTManager.class);
		return empty;
	}	
}