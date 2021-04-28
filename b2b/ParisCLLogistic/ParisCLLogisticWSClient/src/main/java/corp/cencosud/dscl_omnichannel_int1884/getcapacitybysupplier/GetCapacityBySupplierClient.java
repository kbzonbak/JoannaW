
package corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplier;

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

public class GetCapacityBySupplierClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;

    public GetCapacityBySupplierClient(String url) {
        create0();
        Endpoint GetCapacityBySupplierPortEP = service0 .addEndpoint(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplier", "GetCapacityBySupplierPort"), new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplier", "GetCapacityBySupplierBinding"), url);
        endpoints.put(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplier", "GetCapacityBySupplierPort"), GetCapacityBySupplierPortEP);
        Endpoint GetCapacityBySupplierLocalEndpointEP = service0 .addEndpoint(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplier", "GetCapacityBySupplierLocalEndpoint"), new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplier", "GetCapacityBySupplierLocalBinding"), "xfire.local://GetCapacityBySupplier");
        endpoints.put(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplier", "GetCapacityBySupplierLocalEndpoint"), GetCapacityBySupplierLocalEndpointEP);
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
        service0 = asf.create((corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplier.GetCapacityBySupplier.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplier", "GetCapacityBySupplierLocalBinding"), "urn:xfire:transport:local");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplier", "GetCapacityBySupplierBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
    }

    public GetCapacityBySupplier getGetCapacityBySupplierPort() {
        return ((GetCapacityBySupplier)(this).getEndpoint(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplier", "GetCapacityBySupplierPort")));
    }

    public GetCapacityBySupplier getGetCapacityBySupplierPort(String url) {
        GetCapacityBySupplier var = getGetCapacityBySupplierPort();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public GetCapacityBySupplier getGetCapacityBySupplierLocalEndpoint() {
        return ((GetCapacityBySupplier)(this).getEndpoint(new QName("http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplier", "GetCapacityBySupplierLocalEndpoint")));
    }

    public GetCapacityBySupplier getGetCapacityBySupplierLocalEndpoint(String url) {
        GetCapacityBySupplier var = getGetCapacityBySupplierLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public static void main(String[] args) {
        

        GetCapacityBySupplierClient client = new GetCapacityBySupplierClient("");
        
		//create a default service endpoint
        GetCapacityBySupplier service = client.getGetCapacityBySupplierPort();
        
		//TODO: Add custom client code here
        		//
        		//service.yourServiceOperationHere();
        
		System.out.println("test client completed");
        		System.exit(0);
    }

}
