
package corp.cencosud.dscl_omnichannel_int1873.consulttransitline;

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

public class ConsultTransitLineClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;

    public ConsultTransitLineClient(String url) {
        create0();
        Endpoint ConsultTransitLinePortTypeLocalEndpointEP = service0 .addEndpoint(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLine", "ConsultTransitLinePortTypeLocalEndpoint"), new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLine", "ConsultTransitLinePortTypeLocalBinding"), "xfire.local://ConsultTransitLine");
        endpoints.put(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLine", "ConsultTransitLinePortTypeLocalEndpoint"), ConsultTransitLinePortTypeLocalEndpointEP);
        Endpoint ConsultTransitLinePortEP = service0 .addEndpoint(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLine", "ConsultTransitLinePort"), new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLine", "ConsultTransitLineBinding"), url);
        endpoints.put(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLine", "ConsultTransitLinePort"), ConsultTransitLinePortEP);
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
        service0 = asf.create((corp.cencosud.dscl_omnichannel_int1873.consulttransitline.ConsultTransitLinePortType.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLine", "ConsultTransitLinePortTypeLocalBinding"), "urn:xfire:transport:local");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLine", "ConsultTransitLineBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
    }

    public ConsultTransitLinePortType getConsultTransitLinePortTypeLocalEndpoint() {
        return ((ConsultTransitLinePortType)(this).getEndpoint(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLine", "ConsultTransitLinePortTypeLocalEndpoint")));
    }

    public ConsultTransitLinePortType getConsultTransitLinePortTypeLocalEndpoint(String url) {
        ConsultTransitLinePortType var = getConsultTransitLinePortTypeLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public ConsultTransitLinePortType getConsultTransitLinePort() {
        return ((ConsultTransitLinePortType)(this).getEndpoint(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1873/ConsultTransitLine", "ConsultTransitLinePort")));
    }

    public ConsultTransitLinePortType getConsultTransitLinePort(String url) {
        ConsultTransitLinePortType var = getConsultTransitLinePort();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public static void main(String[] args) {
        

        ConsultTransitLineClient client = new ConsultTransitLineClient("");
        
		//create a default service endpoint
        ConsultTransitLinePortType service = client.getConsultTransitLinePort();
        
		//TODO: Add custom client code here
        		//
        		//service.yourServiceOperationHere();
        
		System.out.println("test client completed");
        		System.exit(0);
    }

}
