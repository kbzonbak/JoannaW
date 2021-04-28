
package cl.paperless.webapp.online.webservices;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.namespace.QName;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

public class OnlineClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;

    public OnlineClient() {
        create0();
        Endpoint OnlineHttpSoap11EndpointEP = service0 .addEndpoint(new QName("http://webservices.online.webapp.paperless.cl", "OnlineHttpSoap11Endpoint"), new QName("http://webservices.online.webapp.paperless.cl", "OnlineSoap11Binding"), "http://cencosudqa.paperless.cl:80/axis2/services/Online.OnlineHttpSoap11Endpoint/");
        endpoints.put(new QName("http://webservices.online.webapp.paperless.cl", "OnlineHttpSoap11Endpoint"), OnlineHttpSoap11EndpointEP);
        Endpoint OnlinePortTypeLocalEndpointEP = service0 .addEndpoint(new QName("http://webservices.online.webapp.paperless.cl", "OnlinePortTypeLocalEndpoint"), new QName("http://webservices.online.webapp.paperless.cl", "OnlinePortTypeLocalBinding"), "xfire.local://Online");
        endpoints.put(new QName("http://webservices.online.webapp.paperless.cl", "OnlinePortTypeLocalEndpoint"), OnlinePortTypeLocalEndpointEP);
    }

    public Object getEndpoint(Endpoint endpoint) {
        try {
            return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
        } catch (MalformedURLException e) {
            throw new XFireRuntimeException("Invalid URL", e);
        }
    }

    public Object getEndpoint(QName name) {
        Endpoint endpoint = ((Endpoint) endpoints.get((name)));
        if ((endpoint) == null) {
            throw new IllegalStateException("No such endpoint!");
        }
        return getEndpoint((endpoint));
    }

    public Collection getEndpoints() {
        return endpoints.values();
    }

    private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap props = new HashMap();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service0 = asf.create((cl.paperless.webapp.online.webservices.OnlinePortType.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://webservices.online.webapp.paperless.cl", "OnlineSoap11Binding"), "http://schemas.xmlsoap.org/soap/http");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://webservices.online.webapp.paperless.cl", "OnlinePortTypeLocalBinding"), "urn:xfire:transport:local");
        }
    }

    public OnlinePortType getOnlineHttpSoap11Endpoint() {
        return ((OnlinePortType)(this).getEndpoint(new QName("http://webservices.online.webapp.paperless.cl", "OnlineHttpSoap11Endpoint")));
    }

    public OnlinePortType getOnlineHttpSoap11Endpoint(String url) {
        OnlinePortType var = getOnlineHttpSoap11Endpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public OnlinePortType getOnlinePortTypeLocalEndpoint() {
        return ((OnlinePortType)(this).getEndpoint(new QName("http://webservices.online.webapp.paperless.cl", "OnlinePortTypeLocalEndpoint")));
    }

    public OnlinePortType getOnlinePortTypeLocalEndpoint(String url) {
        OnlinePortType var = getOnlinePortTypeLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public static void main(String[] args) {
        

        OnlineClient client = new OnlineClient();
        
		//create a default service endpoint
        OnlinePortType service = client.getOnlineHttpSoap11Endpoint();
        
		//TODO: Add custom client code here
        		//
        		//service.yourServiceOperationHere();
        
		System.out.println("test client completed");
        		System.exit(0);
    }

}
