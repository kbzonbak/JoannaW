
package org.tempuri;

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

public class ServicioExternoAdmisionCEPClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;

    public ServicioExternoAdmisionCEPClient() {
        create0();
        Endpoint ServicioExternoAdmisionCEPSoapEP = service0 .addEndpoint(new QName("http://tempuri.org/", "ServicioExternoAdmisionCEPSoap"), new QName("http://tempuri.org/", "ServicioExternoAdmisionCEPSoap"), "http://b2b.correos.cl:8008/ServicioAdmisionCEPExterno/cch/ws/enviosCEP/externo/implementacion/ServicioExternoAdmisionCEP.asmx");
        endpoints.put(new QName("http://tempuri.org/", "ServicioExternoAdmisionCEPSoap"), ServicioExternoAdmisionCEPSoapEP);
        Endpoint ServicioExternoAdmisionCEPSoapLocalEndpointEP = service0 .addEndpoint(new QName("http://tempuri.org/", "ServicioExternoAdmisionCEPSoapLocalEndpoint"), new QName("http://tempuri.org/", "ServicioExternoAdmisionCEPSoapLocalBinding"), "xfire.local://ServicioExternoAdmisionCEP");
        endpoints.put(new QName("http://tempuri.org/", "ServicioExternoAdmisionCEPSoapLocalEndpoint"), ServicioExternoAdmisionCEPSoapLocalEndpointEP);
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
        service0 = asf.create((org.tempuri.ServicioExternoAdmisionCEPSoap.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://tempuri.org/", "ServicioExternoAdmisionCEPSoap"), "http://schemas.xmlsoap.org/soap/http");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://tempuri.org/", "ServicioExternoAdmisionCEPSoapLocalBinding"), "urn:xfire:transport:local");
        }
    }

    public ServicioExternoAdmisionCEPSoap getServicioExternoAdmisionCEPSoap() {
        return ((ServicioExternoAdmisionCEPSoap)(this).getEndpoint(new QName("http://tempuri.org/", "ServicioExternoAdmisionCEPSoap")));
    }

    public ServicioExternoAdmisionCEPSoap getServicioExternoAdmisionCEPSoap(String url) {
        ServicioExternoAdmisionCEPSoap var = getServicioExternoAdmisionCEPSoap();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public ServicioExternoAdmisionCEPSoap getServicioExternoAdmisionCEPSoapLocalEndpoint() {
        return ((ServicioExternoAdmisionCEPSoap)(this).getEndpoint(new QName("http://tempuri.org/", "ServicioExternoAdmisionCEPSoapLocalEndpoint")));
    }

    public ServicioExternoAdmisionCEPSoap getServicioExternoAdmisionCEPSoapLocalEndpoint(String url) {
        ServicioExternoAdmisionCEPSoap var = getServicioExternoAdmisionCEPSoapLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public static void main(String[] args) {
			
			ServicioExternoAdmisionCEPClient client = new ServicioExternoAdmisionCEPClient();
	        
	        ServicioExternoAdmisionCEPSoap service = client.getServicioExternoAdmisionCEPSoap();
//	        AdmisionTO admisionTo = doFillAdmisionTOTest();
	        AdmisionTO admisionTo = doFillAdmisionTOTest2();
	        
	        RespuestaAdmisionTO response = service.admitirEnvio("PRUEBA WS 5", "055e7c1784a11213ac3a018d6ab5d8a1", admisionTo);
		
        
        
		//TODO: Add custom client code here
        		//
        		//service.yourServiceOperationHere();
        
		System.out.println("test client completed" + " CODE = " + response.getIdTransaccional());
        		System.exit(0);
    }
    
    public static AdmisionTO doFillAdmisionTOTest(){
		org.tempuri.ObjectFactory objectFactory = new org.tempuri.ObjectFactory();
		AdmisionTO admision = objectFactory.createAdmisionTO();
		
		admision.setExtensionData(objectFactory.createExtensionDataObject());
		admision.setCodigoAdmision("string");
		admision.setClienteRemitente("525536");
		admision.setNombreRemitente("SKECHERS CHILE");
		admision.setDireccionRemitente("AV. KENNEDY 5118");
		admision.setPaisRemitente("056");
		admision.setComunaRemitente("VITACURA");
		admision.setPaisDestinatario("056");
		admision.setNombreDestinatario("Claudia Pavez.");
		admision.setDireccionDestinatario("LOS COPIHUES 3972 - 3972");
		admision.setComunaDestinatario("SAN JOAQUIN");
		admision.setCodigoServicio("25");
		admision.setNumeroTotalPiezas(1);
		admision.setKilos(1D);
		admision.setVolumen(1D);
		admision.setNumeroReferencia("436142");
		admision.setTipoPortes("P");
		admision.setDevolucionConforme("N");
		admision.setNumeroDocumentos(0L);
		return admision;
	}
    
    public static AdmisionTO doFillAdmisionTOTest2(){
		org.tempuri.ObjectFactory objectFactory = new org.tempuri.ObjectFactory();
		AdmisionTO admision = objectFactory.createAdmisionTO();
		
		admision.setExtensionData(objectFactory.createExtensionDataObject());
		admision.setCodigoAdmision("string");
		admision.setClienteRemitente("525536");
		admision.setNombreRemitente("JOSE IGNACIO MARTINEZ ZELADA");
		admision.setDireccionRemitente("Panamericana Norte 3696");
		admision.setPaisRemitente("056");
		admision.setComunaRemitente("Santiago");
		admision.setPaisDestinatario("056");
		admision.setNombreDestinatario("JOSE IGNACIO MARTINEZ ZELADA");
//		admision.setDireccionDestinatario("CALLE ELEUTERIO RAMIREZ 1024 - 602A");//****
		admision.setDireccionDestinatario("LOS COPIHUES 3972 - 3972");//****
		admision.setComunaDestinatario("SANTIAGO");
		admision.setCodigoServicio("25");
		admision.setNumeroTotalPiezas(1);
		admision.setKilos(80.0D);
//		admision.setVolumen(904050.0D);//*****
		admision.setVolumen(999D);//*****
		admision.setNumeroReferencia("472672");
		admision.setTipoPortes("P");
		admision.setDevolucionConforme("N");
		admision.setNumeroDocumentos(0L);
		return admision;
	}

}
