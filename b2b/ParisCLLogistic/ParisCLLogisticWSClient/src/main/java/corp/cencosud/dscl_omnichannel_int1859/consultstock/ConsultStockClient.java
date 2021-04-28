
package corp.cencosud.dscl_omnichannel_int1859.consultstock;

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

public class ConsultStockClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;

    public ConsultStockClient(String url) {
        create0();
        Endpoint ConsultStockPortTypeLocalEndpointEP = service0 .addEndpoint(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStock", "ConsultStockPortTypeLocalEndpoint"), new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStock", "ConsultStockPortTypeLocalBinding"), "xfire.local://ConsultStock");
        endpoints.put(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStock", "ConsultStockPortTypeLocalEndpoint"), ConsultStockPortTypeLocalEndpointEP);
        Endpoint ConsultStockPortEP = service0 .addEndpoint(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStock", "ConsultStockPort"), new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStock", "ConsultStockBinding"), url);
        endpoints.put(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStock", "ConsultStockPort"), ConsultStockPortEP);
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
        service0 = asf.create((corp.cencosud.dscl_omnichannel_int1859.consultstock.ConsultStockPortType.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStock", "ConsultStockBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStock", "ConsultStockPortTypeLocalBinding"), "urn:xfire:transport:local");
        }
    }

    public ConsultStockPortType getConsultStockPortTypeLocalEndpoint() {
        return ((ConsultStockPortType)(this).getEndpoint(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStock", "ConsultStockPortTypeLocalEndpoint")));
    }

    public ConsultStockPortType getConsultStockPortTypeLocalEndpoint(String url) {
        ConsultStockPortType var = getConsultStockPortTypeLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public ConsultStockPortType getConsultStockPort() {
        return ((ConsultStockPortType)(this).getEndpoint(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1859/ConsultStock", "ConsultStockPort")));
    }

    public ConsultStockPortType getConsultStockPort(String url) {
        ConsultStockPortType var = getConsultStockPort();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public static void main(String[] args) {
        

        ConsultStockClient client = new ConsultStockClient("");
        
		//create a default service endpoint
        ConsultStockPortType service = client.getConsultStockPort();
        
		//TODO: Add custom client code here
        		//
        		//service.yourServiceOperationHere();
        
		System.out.println("test client completed");
        		System.exit(0);
    }

}
