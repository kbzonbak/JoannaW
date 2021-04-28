package cl.chilexpress.integracionasistida;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.transport.Channel;
import org.codehaus.xfire.transport.TransportManager;
import org.codehaus.xfire.util.dom.DOMInHandler;
import org.codehaus.xfire.util.dom.DOMOutHandler;

import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.DestinatarioType;
import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.DireccionType;
import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.GenerarIntegracionAsistidaRequestType;
import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.PiezaType;
import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.RemitenteType;
import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidaresp.GenerarIntegracionAsistidaResponseType;

public class IntegracionAsistidaClient {

	private static XFireProxyFactory proxyFactory = new XFireProxyFactory();

	private HashMap endpoints = new HashMap();

	private Service service0;

	public IntegracionAsistidaClient() {
		create0();
		Endpoint IntegracionAsistidaSOAPEP = service0.addEndpoint(new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaSOAP"), new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaSOAP"),
				"http://qaws.ssichilexpress.cl/OSB/GenerarOTDigitalIndividualC2C");
//		"http://localhost:8080/OSB/GenerarOTDigitalIndividualC2C");
		endpoints.put(new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaSOAP"), IntegracionAsistidaSOAPEP);
		Endpoint IntegracionAsistidaPTLocalEndpointEP = service0.addEndpoint(new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaPTLocalEndpoint"), new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaPTLocalBinding"),
				"xfire.local://IntegracionAsistida");
		endpoints.put(new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaPTLocalEndpoint"), IntegracionAsistidaPTLocalEndpointEP);
	}
	
	public IntegracionAsistidaClient(String endpointurl) {
		create0();
		Endpoint IntegracionAsistidaSOAPEP = service0.addEndpoint(new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaSOAP"), new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaSOAP"),
				endpointurl);
		endpoints.put(new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaSOAP"), IntegracionAsistidaSOAPEP);
		Endpoint IntegracionAsistidaPTLocalEndpointEP = service0.addEndpoint(new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaPTLocalEndpoint"), new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaPTLocalBinding"),
				"xfire.local://IntegracionAsistida");
		endpoints.put(new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaPTLocalEndpoint"), IntegracionAsistidaPTLocalEndpointEP);
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
		service0 = asf.create((cl.chilexpress.integracionasistida.IntegracionAsistidaPT.class), props);
		{
			asf.createSoap11Binding(service0, new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaSOAP"), "http://schemas.xmlsoap.org/soap/http");
		}
		{
			asf.createSoap11Binding(service0, new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaPTLocalBinding"), "urn:xfire:transport:local");
		}
	}

	public IntegracionAsistidaPT getIntegracionAsistidaSOAP() {
		return ((IntegracionAsistidaPT) (this).getEndpoint(new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaSOAP")));
	}

	public IntegracionAsistidaPT getIntegracionAsistidaSOAP(String url) {
		IntegracionAsistidaPT var = getIntegracionAsistidaSOAP();
		org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
		return var;
	}

	public IntegracionAsistidaPT getIntegracionAsistidaPTLocalEndpoint() {
		return ((IntegracionAsistidaPT) (this).getEndpoint(new QName("http://www.chilexpress.cl/IntegracionAsistida/", "IntegracionAsistidaPTLocalEndpoint")));
	}

	public IntegracionAsistidaPT getIntegracionAsistidaPTLocalEndpoint(String url) {
		IntegracionAsistidaPT var = getIntegracionAsistidaPTLocalEndpoint();
		org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
		return var;
	}

	public static void main(String[] args) {

		ObjectFactory objectFactory = new ObjectFactory();
		IntegracionAsistidaClient client = new IntegracionAsistidaClient();

		IntegracionAsistidaPT service = client.getIntegracionAsistidaSOAP();
		IntegracionAsistidaRequest integracionAsistidaRequest = doFillIntegracionAsistidaRequest();

		Client cl = Client.getInstance(service);
		cl.addInHandler(new DOMInHandler());
		cl.addInHandler(new IntegracionAsistidaHandler());
		cl.addOutHandler(new DOMOutHandler());
		cl.addOutHandler(new IntegracionAsistidaHandler());

		cl.setProperty(Channel.USERNAME, "UsrTestServicios");
		cl.setProperty(Channel.PASSWORD, "U$$vr2$tS2T");

		IntegracionAsistidaResponse response = objectFactory.createIntegracionAsistidaResponse();

		try {
			response = service.integracionAsistidaOp(integracionAsistidaRequest, null, null);
		} catch (XFireRuntimeException e) {
			e.printStackTrace();
			return;
		}
		GenerarIntegracionAsistidaResponseType responsetype = response.getRespGenerarIntegracionAsistida();
		System.out.println("Respuesta integracion asistida " + responsetype.getEstadoOperacion().getCodigoEstado());
		System.out.println("Respuesta integracion asistida " + responsetype.toString());

		System.out.println("test client completed");
		System.exit(0);
	}

	private static IntegracionAsistidaRequest doFillIntegracionAsistidaRequest() {
		IntegracionAsistidaRequest request = new ObjectFactory().createIntegracionAsistidaRequest();
		cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.ObjectFactory objectFactory = new cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.ObjectFactory();
		GenerarIntegracionAsistidaRequestType gia = objectFactory.createGenerarIntegracionAsistidaRequestType();
		gia.setCodigoProducto(3);
		gia.setCodigoServicio(3);
		gia.setComunaOrigen("RENCA");
		gia.setNumeroTCC(22106942);
		gia.setReferenciaEnvio("916893");
		gia.setReferenciaEnvio2("Compra1");
		gia.setMontoCobrar(0);
		gia.setEoc(0);

		RemitenteType rem = objectFactory.createRemitenteType();
		rem.setNombre("a");
		rem.setEmail("a");
		rem.setCelular("1");
		gia.setRemitente(rem);

		DestinatarioType dest = objectFactory.createDestinatarioType();
		dest.setNombre("Alexis Erazo");
		dest.setEmail("aerazo@chilexpress.cl");
		dest.setCelular("11111");
		gia.setDestinatario(dest);

		DireccionType dir = objectFactory.createDireccionType();
		dir.setComuna("PENALOLEN");
		dir.setCalle("Camino de las Camelias");
		dir.setNumero("0");
		dir.setComplemento("Casa 33");
		gia.setDireccion(dir);

		DireccionType dirdev = objectFactory.createDireccionType();
		dirdev.setComuna("PUDAHUEL");
		dirdev.setCalle("Jose Joaquin Perez");
		dirdev.setNumero("1376");
		dirdev.setComplemento("Piso 2");
		gia.setDireccionDevolucion(dirdev);

		PiezaType pieza = objectFactory.createPiezaType();
		pieza.setAlto(1);
		pieza.setAncho(1);
		pieza.setLargo(1);
		pieza.setPeso(BigDecimal.ONE);
		gia.setPieza(pieza);

		request.setReqGenerarIntegracionAsistida(gia);
		return request;
	}

}
