package bbr.b2b.regional.logistic.vev.rest.swagger;

import java.net.URL;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/api-doc/")
public class SwaggerService {

	@Context
    protected Application application;
    
	protected SwaggerCache swaggerCache = new SwaggerCache();
        
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("swagger.json")	
    public String getSwaggerJson(@Context HttpServletRequest request) {  
        URL url = SwaggerUtils.getOriginalRequestURL(request);
        if(url!=null){
            Set<Class<?>> classes = application.getClasses();
            String json = swaggerCache.getSwaggerJson(classes,url);
            return json;
        }
        return null;
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("swagger.yaml")    
    public String getSwaggerYaml(@Context HttpServletRequest request) {  
        URL url = SwaggerUtils.getOriginalRequestURL(request);
        if(url!=null){
            Set<Class<?>> classes = application.getClasses();
            return swaggerCache.getSwaggerYaml(classes, url);
        }
        return null;
    }	
}
