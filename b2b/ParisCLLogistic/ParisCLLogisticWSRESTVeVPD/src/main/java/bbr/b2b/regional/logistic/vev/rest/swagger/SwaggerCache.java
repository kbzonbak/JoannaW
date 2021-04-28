package bbr.b2b.regional.logistic.vev.rest.swagger;

import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.jaxrs.Reader;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import io.swagger.util.Json;
import io.swagger.util.Yaml;

public class SwaggerCache {
	
	 private final Map<Integer,String> swaggerMap = new ConcurrentHashMap<>();
	    
	 public String getSwaggerJson(final Set<Class<?>> classes,@NotNull final URL url) {
		 int hash = url.toExternalForm().hashCode();
		 if (swaggerMap.containsKey(hash)) {
			 // Load from cache	            
			 return swaggerMap.get(hash);
		 }
		 else {
            return generateJson(hash,classes,url);
        }
    }
    
    public String getSwaggerYaml(final Set<Class<?>> classes,@NotNull final URL url) {  
        int hash = url.toExternalForm().hashCode();
        if (swaggerMap.containsKey(hash)) {
            // Load from cache
            return swaggerMap.get(hash);
        }
        else {
            return generateYaml(hash,classes,url);
        }
    }
    
    private String generateJson(int hash,final Set<Class<?>> classes,URL url){
        Swagger swagger = createSwagger(classes,url);
        try {
            String swaggerJson = Json.pretty().writeValueAsString(swagger);
            swaggerMap.put(hash, swaggerJson);
            return swaggerJson;
        } catch (JsonProcessingException ex) {
        }
        return null;
    }
    
    private String generateYaml(int hash,final Set<Class<?>> classes,URL url){
        Swagger swagger = createSwagger(classes,url);
        try {
            String swaggerYaml = Yaml.pretty().writeValueAsString(swagger);
            swaggerMap.put(hash, swaggerYaml);
            return swaggerYaml;
        } catch (JsonProcessingException ex) {
        }
        return null;
    }
    
    private Swagger createSwagger(final Set<Class<?>> classes,final URL url){
        Swagger swagger = new Reader(new Swagger()).read(classes);
        swagger.addScheme(Scheme.forValue(url.getProtocol().toUpperCase()));
        swagger.setHost(url.getHost() + DOUBLE_POINT + url.getPort());
        swagger.setBasePath(getBasePath(swagger.getBasePath(), url));
        return swagger;
    }
    
    private String getBasePath(final String existingBasePath,final URL url){
        String path = url.getPath();
        if (existingBasePath!=null && !existingBasePath.isEmpty()) {
            int i = path.indexOf(existingBasePath + SLASH);
            return path.substring(0, i) + existingBasePath;
        }
        else {
            int i = path.indexOf(APIEE_CONTEXT);
            return path.substring(0, i);            
        }
    }
    
    private static final String APIEE_CONTEXT = "/api-doc/";
    private static final String DOUBLE_POINT = ":";
    private static final String SLASH = "/";
}
