package bbr.b2b.extractors.manager.classes;

import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.log4j.Logger;
import com.falabella.b2b.schemas.ol.admin_archivo_descarga_request.AdminArchivoDescargaRequest;
import com.falabella.b2b.schemas.ol.admin_archivo_descarga_request.File;
import com.falabella.b2b.schemas.ol.admin_archivo_descarga_request.Files;
import com.falabella.b2b.schemas.ol.admin_archivo_descarga_response.AdminArchivoDescargaResponse;
import com.falabella.b2b.schemas.ol.admin_archivo_descarga_response.Message;
import com.falabella.b2b.schemas.ol.admin_archivo_descarga_response.Messages;
import bbr.b2b.b2blink.logistic.xml.OC_customer.DiscountCharge;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Local;
import bbr.b2b.b2blink.logistic.xml.OC_customer.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Action;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Charges;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Client;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Predistributions;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Predistributions.Predistribution;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Discounts;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Section;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.keycloak.classes.JsonUtils;
import bbr.b2b.extraactors.falabella.dto.CredentialRipleyDTO;
import bbr.b2b.extractors.manager.interfaces.FalabellaWSOrderManagerLocal;
import bbr.b2b.extractors.timers.manager.interfaces.SchedulerManagerLocal;
import bbr.b2b.extractors.utils.WsSoaService;
import bbr.b2b.irsb2blink.wsclient.classes.ClientConnection;
import bbr.b2b.irsb2blink.wsclient.classes.InitParamsWs;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.esb.services.webservices.facade.InitParamCSDTO;

@SuppressWarnings("restriction")
@Stateless(name = "managers/FalabellaWSOrderManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FalabellaWSOrderManager implements FalabellaWSOrderManagerLocal {

	private static Logger logger = Logger.getLogger(FalabellaWSOrderManager.class);
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd/MM/yy");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static JAXBContext jc = null;
	private static final String SITECODE = "CL1301";
	private static final String SITENAME = "FALABELLA";
	private static final String SERVICE = "LOC";
	String headerseOC = B2BSystemPropertiesUtil.getProperty("falabellaheaderseOC");
	String headerseOD = B2BSystemPropertiesUtil.getProperty("falabellapredeliveryheader");
	String headerseOE = B2BSystemPropertiesUtil.getProperty("falabellavevheader");


	@EJB
	SchedulerManagerLocal schmanager;

	@Resource
	private SessionContext ctx;

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.OC_customer");
		return jc;
	}

	public void doProcess() {
	}

	@Override
	public void doProcessOC() throws Exception {
		try {
			requestOrders(SERVICE, "eOC");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void doProcessOD() throws Exception {
		try {
			requestOrders(SERVICE, "eOD");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void doProcessOE() throws Exception {
		try {
			requestOrders(SERVICE, "eOE");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void doProcessVtaInv() throws Exception {
		try {
			requestOrders(SERVICE, "eVTA");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void requestOrders(String service, String requestType) throws Exception {

		try {
			logger.info("Empresa: " + SITENAME + " SERVICIO DESCARGA ÓRDENES WS");

			WsSoaService wsSoaService = new WsSoaService();
			List<InitParamCSDTO> credentials = wsSoaService.getCredentials(SITECODE, service);

			for (InitParamCSDTO credential : credentials) {
				
				if(credential.getCredenciales() == null || credential.getCredenciales().isEmpty()){
					logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " credenciales vacias o nulas");
					continue;
				}
				
				logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " descargando órdenes");

				CredentialRipleyDTO credentialBasicDto = JsonUtils.getObjectFromJson(credential.getCredenciales(),CredentialRipleyDTO.class);
				InitParamsWs initParamsWs = new InitParamsWs();
				initParamsWs.setUserNameWs(credentialBasicDto.getUser());
				initParamsWs.setPasswordWs(credentialBasicDto.getPassword());
				initParamsWs.setWsEndpoint("https://b2b.falabella.com/b2bwsfaclpr/ws/adminArchivoService");
				initParamsWs.setProxy(B2BSystemPropertiesUtil.getProperty("PROXY").equals("true"));
				initParamsWs.setProxyAddress(B2BSystemPropertiesUtil.getProperty("PROXY_HOST"));
				initParamsWs.setProxyPort(Integer.parseInt(B2BSystemPropertiesUtil.getProperty("PROXY_PORT")));
				ClientConnection connection = ClientConnection.getInstance(initParamsWs);
				
				AdminArchivoDescargaRequest requestDescarga = new AdminArchivoDescargaRequest();
				Files files = new Files();
				File file = new File();
				file.setTipo(requestType);
				files.getFile().add(file);
				requestDescarga.setFiles(files);
				
				AdminArchivoDescargaResponse adminArchivoDescargaResponse = connection.getPortClient().descargaArchivo(requestDescarga);
				List<Message> messageList = adminArchivoDescargaResponse.getMessages().getMessage();
				for (Message message : messageList) {
					doTransformeOC(message, credential.getAccesscode(), requestType);
				}
				
				//Pruebas
//				if(requestType.equals("eOC")) {
//					//Hace la llamada al WS
//					//AdminArchivoDescargaResponse adminArchivoDescargaResponse = connection.getPortClient().descargaArchivo(requestDescarga);
//					AdminArchivoDescargaResponse adminArchivoDescargaResponse = descargaArchivo(requestDescarga);
//					List<Message> messageList = adminArchivoDescargaResponse.getMessages().getMessage();
//
//					for (Message message : messageList) {
//						doTransformeOC(message, credential.getAccesscode(), requestType);
//					}
//				}else if(requestType.equals("eOD")){
//					AdminArchivoDescargaResponse adminArchivoDescargaResponse = descargaArchivoeOD(requestDescarga);
//					List<Message> messageList = adminArchivoDescargaResponse.getMessages().getMessage();
//
//					for (Message message : messageList) {
//						doTransformeOC(message, credential.getAccesscode(), requestType);
//					}
//				}else {
//					AdminArchivoDescargaResponse adminArchivoDescargaResponse = descargaArchivoeOE(requestDescarga);
//					List<Message> messageList = adminArchivoDescargaResponse.getMessages().getMessage();
//
//					for (Message message : messageList) {
//						doTransformeOC(message, credential.getAccesscode(), requestType);
//					}
//				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	//Pruebas
//	private AdminArchivoDescargaResponse descargaArchivo(AdminArchivoDescargaRequest requestDescarga) {
//		
//		AdminArchivoDescargaResponse adminArchivoDescargaResponse = new AdminArchivoDescargaResponse();
//				
//		Message message = new Message();
//		message.setCode("216033832");
//		message.setValue("NRO_OC|RUT|DV_RUT|RAZON_SOCIAL|DIRECCION|FONO|COMPRADOR|FECHA_AUTORIZACION|ESTADO|DIAS_PAGO|VALUTA|CARGO|DESCUENTO|TOTAL_VENTA|TOTAL_COSTO|TOTAL_CARGOS|TOTAL_DESCUENTOS|TOTAL_OC|TOTAL_IVA|FECHA_DESDE|FECHA_HASTA|UPC|SKU|DESCRIPCION_LARGA|MARCA|MODELO|SUBLINEA|TALLA|COLOR|COSTO_UNI|PRECIO_UNI|CANTIDAD_PROD|UNI_COMPRA|CARGO_ADIC|DESC_ADIC|TOT_CARGOS|TOT_DESCUENT|MONEDA_VENTA|MONEDA_COSTO\n" +
//				"121304098965|29466597|26/10/2020|05/11/2020||800388388 opción 01|||PUERTO MONTT|PUERTO MONTT                  ||| |2000|LOCAL VENTA A DISTANCIA|CT Verde (CD Santiago)|13 900  - 2100 - Orden:8000724340 - HC:22:38 - Recibe: ana solis 09-9313149|2006932132008|6932132        |ALMOHADA AFAMILY 2.0P 50X70|2|2745||||RF||||13/11/2020||||ESTANDAR|0|05/11/2020|05/11/2020|");  
//		Messages messages = new Messages();
//		messages.getMessage().add(message);
//		adminArchivoDescargaResponse.setMessages(messages);
//		return adminArchivoDescargaResponse;
//	}
//	
//	private AdminArchivoDescargaResponse descargaArchivoeOD(AdminArchivoDescargaRequest requestDescarga) {
//		
//		AdminArchivoDescargaResponse adminArchivoDescargaResponse = new AdminArchivoDescargaResponse();
//				
//		Message message = new Message();
//		message.setCode("216033832");
//		message.setValue("NRO_OD|FECHA_EMISION_OD|NRO_OC|RUT|DV_RUT|RAZON_SOCIAL|FECHA_EMISION|UPC|SKU|DESCRIPCION_LARGA|MODELO|TALLA|COLOR|NRO_LOCAL|LOCAL|UNIDADES|EMPAQUES\n" + 
//				"121287078619|29324206|09/10/2020|28/10/2020||800388388 opción 01|||CASABLANCA|CASABLANCA                    ||| |2000|LOCAL VENTA A DISTANCIA|(CT HUB-CROSS DOCK ) FALABELLA RETAIL S.A.|8 900  - 2100 - Orden:5639476912 - HC:19:16 - Recibe: Pedro  Rosas 92280793|4242002817385|5585931        |REFRIGERADOR BOSCH KAD90VI 533LTS|1|930245||||AD||||30/10/2020||||KAD90VI|0|28/10/2020|28/10/2020|\r\n" + 
//				"121289552568|29324277|09/10/2020|15/10/2020||800388388 opción 01|||QUILPUE|QUILPUE                       ||| |2000|LOCAL VENTA A DISTANCIA|(CT HUB-CROSS DOCK ) FALABELLA RETAIL S.A.|8 900  - 2100 - Orden:5641100140 - HC:00:24 - Recibe: Claudia  Oyanedel  57088917|4242002929835|5174441        |COCINA 4 BOSCH  PRO425 INOX|1|192934||||AD||||17/10/2020||||PRO425 INOX|0|15/10/2020|15/10/2020|");
//		Messages messages = new Messages();
//		messages.getMessage().add(message);
//		adminArchivoDescargaResponse.setMessages(messages);
//		return adminArchivoDescargaResponse;
//	}
//	
//	private AdminArchivoDescargaResponse descargaArchivoeOE(AdminArchivoDescargaRequest requestDescarga) {
//		
//		AdminArchivoDescargaResponse adminArchivoDescargaResponse = new AdminArchivoDescargaResponse();
//				
//		Message message = new Message();
//		message.setCode("216033832");
//		message.setValue("NRO_F12|NRO_OC|FECHA_EMISION_OC|FECHA_DESPACHO_PACTADA|NOM_COMPRADOR|TELEFONO_COMPRADOR|NOM_RECEPTOR|DIRECCION_RECEPTOR|COMUNA_RECEPTOR|CIUDAD_RECEPTOR|COD_POSTAL_RECEPTOR|TELEFONO_RECEPTOR|ATENCION|NRO_LOCAL|LOCAL|DESPACHAR_A|OBSERVACION|UPC|SKU|DESCRIPCION|UNIDADES|PRECIO_COSTO|DESCUENTO|PAPEL_REGALO|SALUDO|HORARIO_PROGRAMADO|DOMICILIO/OFICINA|EMAIL|ENTRE_CALLE|FECHA_REPARTO_CLIENTE|REGION|IDENTIFICACION_CLIENTE|RUT_COMPRADOR|MODELO_SKU|PISO|FECHA_DESDE|FECHA_HASTA|TALLA\n" + 
//				"121304098965|29466597|26/10/2020|05/11/2020||800388388 opción 01|||PUERTO MONTT|PUERTO MONTT                  ||| |2000|LOCAL VENTA A DISTANCIA|CT Verde (CD Santiago)|13 900  - 2100 - Orden:8000724340 - HC:22:38 - Recibe: ana solis 09-9313149|2006932132008|6932132        |ALMOHADA AFAMILY 2.0P 50X70|2|2745||||RF||||13/11/2020||||ESTANDAR|0|05/11/2020|05/11/2020|");
//		Messages messages = new Messages();
//		messages.getMessage().add(message);
//		adminArchivoDescargaResponse.setMessages(messages);
//		return adminArchivoDescargaResponse;
//	}

	public void doTransformeOC(Message message, String accesCode, String requestType) throws OperationFailedException, ParseException, DatatypeConfigurationException, JAXBException {
		
		HashMap<String, List<String>> numOrders = new HashMap<>();
		HashMap<String, String> mapMessage = new HashMap<String, String>();
		
		String header = "";
		if (requestType.equals("eOC")) {
			header = headerseOC;
		} else if (requestType.equals("eOD")) {
			header = headerseOD;
		} else {
			header = headerseOE;
		}

		if (header != null && !header.isEmpty()) {

			String[] headersArray = header.split("\\|",-1);
			String[] messageLines = message.getValue().split("\n");
			
			boolean isFirst = true;
			
			// Agrupo las lineas por el numero de orden.
			for (String linea : messageLines) {
				// Validación de primera de vuelta para eliminar titulos.
				if (isFirst) {
					isFirst = false;
					continue;
				}
				
				String[] info = linea.split("\\|",-1);

				String numOrder = info[0];

				if (numOrders.containsKey(numOrder)) {
					List<String> temLinea = numOrders.get(numOrder);
					temLinea.add(linea);
					numOrders.put(numOrder, temLinea);
				} else {
					List<String> lineaList = new ArrayList<>();
					lineaList.add(linea);
					numOrders.put(numOrder, lineaList);
				}
			}

			for (HashMap.Entry<String, List<String>> entry : numOrders.entrySet()) {
				// Seteo las lineas correspondientes a la Orden
				List<String> lineas = entry.getValue();

				// OC CUSTOMER
				ObjectFactory objFactory = new ObjectFactory();
				Order orderXML = objFactory.createOrder();

				// Seteo datos de cabecera
				String[] infoMensaje = lineas.get(0).split("\\|",-1);
				

				// For para setear las cabecera con su respectivo valor
				for (int j = 0; j < headersArray.length; j++) {
					mapMessage.put(headersArray[j], infoMensaje[j].trim());
				}
				
				if(requestType.equals("eOC")) {
					Action action = new Action();
					action.setCode("IGNORAR SI EXISTE");
					action.setDescription("IGNORAR SI EXISTE");
					orderXML.setAction(action);
				}else if(requestType.equals("eOE")){
					Action action = new Action();
					action.setCode("PROCESAR Y DEJAR NULA ANTERIOR");
					action.setDescription("PROCESAR Y DEJAR NULA ANTERIOR");
					orderXML.setAction(action);
				}
				
				//NUMERO OC
				orderXML.setNumber(getField(mapMessage, "NRO_OC", true));
				//NUMERO SOLICITUD
				orderXML.setRequest("");
				
				//PreviousOrdertype
				if(requestType.equals("eOC")){
					orderXML.setPreviousordertype("");
					orderXML.setComplete(false);
				}else if(requestType.equals("eOD")){
					orderXML.setPreviousordertype("eOC");
					orderXML.setComplete(true);
				}else {
					orderXML.setPreviousordertype("");
					orderXML.setComplete(true);
				}
				
				Local local = new Local();
				// Codigo Local de entrega
				if(requestType.equals("eOD")) {
					local.setCode(getField(mapMessage,"NRO_LOCAL", true));
					local.setName(getField(mapMessage,"LOCAL", true));
					local.setEan13("");
					local.setAddress("");
					orderXML.setDeliveryplace(local);
				}else if (requestType.equals("eOE")) {
					
					String desp = getField(mapMessage,"DESPACHAR_A", true);
					MessageDigest md = null;
					
					try {
						md = MessageDigest.getInstance("MD5");
					} catch (NoSuchAlgorithmException e) {
						new OperationFailedException("Error al generar código de local");
					}
					
					String code = new BigInteger(1, md.digest(desp.getBytes())).toString(16);
					int code2 = new BigInteger(1, md.digest(desp.getBytes())).intValue();
					code2 = Math.abs(code2);
					
					local.setCode(String.valueOf(code2));
					local.setName(desp);
					local.setEan13(code);
					local.setAddress(desp);
					orderXML.setDeliveryplace(local);
				}

				//ESTADO OC
				if(requestType.equals("eOC")) {
					orderXML.setStatus("SIN_DISTRIBUCION");
				}else if(requestType.equals("eOD")) {
					orderXML.setStatus("LIBERADA");
				}else if(requestType.equals("eOE")) {
					orderXML.setStatus("LIBERADA");
				}
				//TIPO OC
				if(requestType.equals("eOC") || requestType.equals("eOD")) {
					orderXML.setOrdertype(requestType);
					orderXML.setOrdertypename("Retail");
				}
				else if(requestType.equals("eOE")) {
					if(getField(mapMessage,"DESPACHAR_A",true).equalsIgnoreCase("Cliente")){
						orderXML.setOrdertype("eOEPD");
						orderXML.setOrdertypename("VEV PD");
					}else {
						orderXML.setOrdertype("eOECD");
						orderXML.setOrdertypename("VEV CD");
					}
				}
				
				//SECCION
				Section seccion = new Section();
				seccion.setCode("");
				seccion.setName("");
				orderXML.setSection(seccion);
				
				//FECHA EMISION
				Locale locale = new Locale("es", "CL");
				GregorianCalendar gcal = new GregorianCalendar(locale);
				if(requestType.equals("eOC")) {
					gcal.setTime(getDateField(mapMessage, "FECHA_AUTORIZACION", true));
					XMLGregorianCalendar issuedate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					orderXML.setIssuedate(issuedate);
				}else if(requestType.equals("eOE")) {
					gcal.setTime(getDateField(mapMessage, "FECHA_EMISION_OC", true));
					XMLGregorianCalendar issuedate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					orderXML.setIssuedate(issuedate);
				}
				
				//FECHA VIGENCIA
				Locale locale2 = new Locale("es", "CL");
				GregorianCalendar gcal2 = new GregorianCalendar(locale2);
				if(requestType.equals("eOE")){
					//NO ENCUENTRA EL CAMPO "FECHA_DESPACHO PACTADA "DESDE EL WS - bbr.b2b.common.adtclasses.exceptions.OperationFailedException: Campo FECHA_DESPACHO_PACTADA nulo o ausente desde el ws
					gcal2.setTime(getDateField(mapMessage,"FECHA_DESPACHO_PACTADA", true));
					XMLGregorianCalendar xmlgcal_valid = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal2);
					orderXML.setEffectivdate(xmlgcal_valid);
				}else if (requestType.equals("eOC")) {
					gcal2.setTime(getDateField(mapMessage,"FECHA_DESDE",true));
					XMLGregorianCalendar xmlgcal_valid = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal2);
					orderXML.setEffectivdate(xmlgcal_valid);
				}
				//FECHA VENCIMIENTO
				if(requestType.equals("eOC")){
					if(getDateField(mapMessage,"FECHA_HASTA",true) != null) {
						gcal.setTime(getDateField(mapMessage,"FECHA_HASTA",true));
						XMLGregorianCalendar xmlgcal_expiration = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
						orderXML.setExpirationdate(xmlgcal_expiration);
					}else {
						gcal.setTime(getDateField(mapMessage, "FECHA_AUTORIZACION",true));
						XMLGregorianCalendar xmlgcal_expiration = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
						orderXML.setExpirationdate(xmlgcal_expiration);
					}
				}else if (requestType.equals("eOE")) {
					if(getDateField(mapMessage,"FECHA_HASTA",true) != null) {
						gcal.setTime(getDateField(mapMessage,"FECHA_HASTA",true));
						XMLGregorianCalendar xmlgcal_expiration = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
						orderXML.setExpirationdate(xmlgcal_expiration);
					}else {
						gcal.setTime(getDateField(mapMessage, "FECHA_EMISION_OC",true));
						XMLGregorianCalendar xmlgcal_expiration = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
						orderXML.setExpirationdate(xmlgcal_expiration);
					}
				}
				
				//FECHA COMPROMISO
				if(requestType.equals("eOE")) {
					if(getField(mapMessage, "NRO_F12", false) != null) {
						orderXML.setRequest(getField(mapMessage, "NRO_F12", false));
					}
					gcal.setTime(getDateField(mapMessage,"FECHA_REPARTO_CLIENTE", false));
					XMLGregorianCalendar xmlgcal_delivery = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					orderXML.setCommitmentdate(xmlgcal_delivery);
				}

				//Local de venta
				if(requestType.equals("eOE")) {
					Local localVenta = new Local();
					localVenta.setCode(getField(mapMessage,"NRO_LOCAL",true));
					localVenta.setName(getField(mapMessage,"LOCAL",true));
					orderXML.setSaleplace(localVenta);
				}

				//FORMA DE PAGO
				if(requestType.equals("eOC")) {
					orderXML.setPaymentcondition(getField(mapMessage, "DIAS_PAGO",true));
				}else if (requestType.equals("eOE")){
					orderXML.setPaymentcondition("60");
				}
				
				//COMENTARIOS
				orderXML.setObservation("");
				//RESPONSABLE
				orderXML.setResponsible("");
				//COMPRADOR (RETAIL)
				orderXML.setBuyer(SITECODE);
				//VENDEDOR (PROVEEDOR)
				orderXML.setVendor(accesCode);
				//CLIENTE 
				if(requestType.equals("eOE")) { 
					Client client = new Client();
					orderXML.setClient(client);
					String receiverName = getField(mapMessage,"NOM_RECEPTOR", false);
					if (receiverName != null && !receiverName.isEmpty()){
						receiverName = receiverName.trim().replaceAll("\\s+"," ");
					}
					orderXML.getClient().setName(receiverName);
					orderXML.getClient().setIdentity(getField(mapMessage,"RUT_COMPRADOR",false));
					orderXML.getClient().setPhone(getField(mapMessage, "TELEFONO_COMPRADOR",false ));
					orderXML.getClient().setAddress(getField(mapMessage, "DIRECCION_RECEPTOR",false ));	
					orderXML.getClient().setStreetnumber("");	
					orderXML.getClient().setApartment(getField(mapMessage, "DOMICILIO/OFICINA",false ));
					orderXML.getClient().setHousenumber(getField(mapMessage, "DESPACHAR_A",false));
					orderXML.getClient().setRegion(getField(mapMessage, "REGION",false));
					orderXML.getClient().setCommune(getField(mapMessage, "COMUNA_RECEPTOR",false));
					orderXML.getClient().setCity(getField(mapMessage, "CIUDAD_RECEPTOR",false));
					orderXML.getClient().setObservation(getField(mapMessage, "OBSERVACION",true));
				}
				
				//DESCUENTOS
				if(requestType.equals("eOC")) {
					DiscountCharge discountCharge = new DiscountCharge();
					discountCharge.setCode("");
					discountCharge.setPercentage(false);
					discountCharge.setDescription("");
					discountCharge.setValue(getDoubleField(mapMessage,"TOTAL_DESCUENTOS", false) != null ? getDoubleField(mapMessage,"TOTAL_DESCUENTOS", false) : 0 );
					Discounts discounts = new Discounts();
					discounts.getDiscounts().add(discountCharge);
					orderXML.setDiscounts(discounts);
				}else if(requestType.equals("eOE")) {
					DiscountCharge discountCharge = new DiscountCharge();
					discountCharge.setCode("");
					discountCharge.setPercentage(false);
					discountCharge.setDescription("");
					discountCharge.setValue(getDoubleField(mapMessage,"TOTAL_DESCUENTOS", false) != null ? getDoubleField(mapMessage,"TOTAL_DESCUENTOS", false) : 0 );
					Discounts discounts = new Discounts();
					discounts.getDiscounts().add(discountCharge);
					orderXML.setDiscounts(discounts);
				}
				if(requestType.equals("eOC")) {
					DiscountCharge discountCharge = new DiscountCharge();
					discountCharge.setCode("");
					discountCharge.setPercentage(false);
					discountCharge.setDescription("");
					discountCharge.setValue(getDoubleField(mapMessage,"TOT_CARGOS",false) != null ? getDoubleField(mapMessage,"TOTAL_DESCUENTOS", false).floatValue() : 0);
					Charges charges = new Charges();
					charges.getCharges().add(discountCharge);
					orderXML.setCharges(charges);
				}else if(requestType.equals("eOE")) {
					DiscountCharge discountCharge = new DiscountCharge();
					discountCharge.setCode("");
					discountCharge.setPercentage(false);
					discountCharge.setDescription("");
					discountCharge.setValue(getDoubleField(mapMessage,"DESCUENTO",false) != null ? getDoubleField(mapMessage,"DESCUENTO",false).floatValue() : 0);
					Charges charges = new Charges();
					charges.getCharges().add(discountCharge);
					orderXML.setCharges(charges);
				}
				
				//DETALLES
				orderXML.setDetails(setDetails(objFactory, lineas, headersArray,requestType));
				
				//Total
				if(requestType.equals("eOC")) {
					double total = orderXML.getDetails().getDetail().stream().mapToDouble(o -> o.getFinalcost() * o.getQuantityumc()).sum();
					orderXML.setTotal(total);
				}else if(requestType.equals("eOE")) {
					orderXML.setTotal(Double.parseDouble(getField(mapMessage, "UNIDADES", true)) * Double.parseDouble(getField(mapMessage, "PRECIO_COSTO", true)));
				}
				
				JAXBContext jc = getJC();
				Marshaller m = jc.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				m.marshal(orderXML, sw);
				String msg = sw.toString();
				
				logger.info(msg);
				schmanager.doSendMessageQueue(SITENAME, accesCode, orderXML.getNumber(), msg, null);
				msg = "";
			}
		}
		
		//Limpiar las variables para evitar desbordamiento de memoria.
		numOrders.clear();
		mapMessage.clear();
	}
	
	private Details setDetails(ObjectFactory objFactory, List<String> lineas, String[] headersArray , String requestType) throws OperationFailedException, ParseException {

		HashMap<String, String> mapMessageSku = new HashMap<String, String>();
		HashMap<String, String> mapMessage = new HashMap<String, String>();
		
		Details detalles = objFactory.createOrderDetails();
		List<Detail> listaDetalles = detalles.getDetail();
		
		int position = 0;
		int posicion = 1;
		
		for (int i = 0; i < headersArray.length; i++) {
			if(headersArray[i].equals("SKU")) {
				position = i;
				break;
			}
		}	
		
		for(String linea: lineas) {
			String[] lineaSeparadas = linea.split("\\|",-1);
			mapMessageSku.put(lineaSeparadas[position], linea);
		}
		
		List<String> listadoSkuOrdenado = new ArrayList<>(mapMessageSku.keySet());
		Collections.sort(listadoSkuOrdenado);
		
		for(String sku : listadoSkuOrdenado ) {
			//Tengo el sku ordenado y la linea completa
			String linea = mapMessageSku.get(sku);
			
			Detail detalle = objFactory.createOrderDetailsDetail();
			String[] lineaSeparada = linea.split("\\|",-1);
			
			for (int i = 0; i < headersArray.length; i++) {
				mapMessage.put(headersArray[i], lineaSeparada[i].trim());
			}
			
			//CORRELATIVO
			detalle.setPosition(posicion);
			
			//CODIGO PRODUCTO COMPRADOR (ES EL MISMO PARA eOC Y eOE)
			if(requestType.equals("eOC") || requestType.equals("eOE")) {
				detalle.setEan13(getField(mapMessage, "UPC", true));
			}
	
			detalle.setSkubuyer(getField(mapMessage, "SKU", true));
			
			//DESCRIPCION PRODUCTO 
			if(requestType.equals("eOC")){
				detalle.setProductdescription(getField(mapMessage, "DESCRIPCION_LARGA", true));
				detalle.setDescriptionumc(getField(mapMessage, "MODELO", true));
				String cantidadEmpaque = getField(mapMessage, "CANTIDAD_PROD", false);
				detalle.setQuantityumc(Integer.parseInt(cantidadEmpaque));
				detalle.setListcost(Double.parseDouble(getField(mapMessage, "PRECIO_UNI", false)));
				Double costoUnitario = Double.valueOf(getField(mapMessage, "COSTO_UNI", true));
				detalle.setFinalcost(costoUnitario);
				detalle.setListprice(Double.parseDouble(getField(mapMessage, "PRECIO_UNI", false)));
				
				//DESCUENTO DE LOS PRODUCTOS EOC
				DiscountCharge discountCharge = new DiscountCharge();
				discountCharge.setCode("");
				discountCharge.setPercentage(false);
				discountCharge.setDescription("");
				bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Discounts discount = new bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Discounts();
				discount.getDiscount().add(discountCharge);
				detalle.setDiscounts(discount);
				
			}else if(requestType.equals("eOE")) {
				detalle.setProductdescription(getField(mapMessage, "DESCRIPCION", true));
				detalle.setDescriptionumc(getField(mapMessage, "MODELO_SKU", true));
				Double costoUnitario = Double.valueOf(getField(mapMessage, "PRECIO_COSTO", true));
				detalle.setFinalcost(costoUnitario);
				detalle.setListprice(Double.parseDouble(getField(mapMessage, "PRECIO_COSTO", true)));
				detalle.setListcost(costoUnitario);
				
				//*FIX PROD_EMPAQUE Y CANTIDAD_EMPAQUE OC-INTERNO*
				detalle.setQuantityumc(new Integer(1));
				Integer cantidadempaque = Integer.parseInt(getField(mapMessage, "UNIDADES", true));
				detalle.setUmbXUmc(cantidadempaque);
				
				//DESCUENTO DE LOS PRODUCTOS EOE
				DiscountCharge discountCharge = new DiscountCharge();
				discountCharge.setCode("");
				discountCharge.setPercentage(false);
				discountCharge.setDescription("");
				//discountCharge.setValue(getDoubleField(mapMessage,"",true) != null ? getDoubleField(mapMessage,"",true).floatValue() : 0);
				bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Discounts discount = new bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Discounts();
				discount.getDiscount().add(discountCharge);
				detalle.setDiscounts(discount);
			}
			
			//INNERPACK
			detalle.setInnerpack(1);
			
			//CANTIDAD EMPAQUE
			if(requestType.equals("eOD")) {
				String cantidadEmpaque = getField(mapMessage, "UNIDADES", true);
				detalle.setQuantityumc(Integer.parseInt(cantidadEmpaque));
				detalle.setUmbXUmc(detalle.getInnerpack());
			}
			
			//CARGO DE LOS PRODUCTOS
			if(requestType.equals("eOC")) {
				bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Charges charges = new bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Charges();
				DiscountCharge charge = new DiscountCharge();
				charge.setCode("");
				charge.setPercentage(false);
				charge.setDescription("");
				charge.setValue(getDoubleField(mapMessage,"COSTO_UNI",false) != null ? getDoubleField(mapMessage,"COSTO_UNI",false).floatValue() : 0);
				charges.getCharge().add(charge);
				detalle.setCharges(charges);
			}else if(requestType.equals("eOE")) {
				bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Charges charges = new bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Charges();
				DiscountCharge charge = new DiscountCharge();
				charge.setCode("");
				charge.setPercentage(false);
				charge.setDescription("");
				charge.setValue(getDoubleField(mapMessage,"PRECIO_COSTO",true) != null ? getDoubleField(mapMessage,"PRECIO_COSTO",true).floatValue() : 0);
				charges.getCharge().add(charge);
				detalle.setCharges(charges);
			}
			
			//PREDISTRIBUCION
			if(requestType.equals("eOD")) {
				Predistribution predistribution = new Predistribution();
				Local local = new Local();
				predistribution.setDeliveryplace(local);
				predistribution.getDeliveryplace().setCode(getField(mapMessage,"NRO_LOCAL",true));
				predistribution.getDeliveryplace().setName(getField(mapMessage,"LOCAL", true));
				predistribution.setQuantity(Integer.parseInt(getField(mapMessage,"UNIDADES",true)));
				Predistributions predistributions = new Predistributions();
				predistributions.getPredistribution().add(predistribution);
				detalle.setPredistributions(predistributions);
			}
			
			posicion++;
			listaDetalles.add(detalle);
		}
		
		//Limpiar las variables para evitar desbordamiento de memoria.
		mapMessageSku.clear();
		mapMessage.clear();
		
		return detalles;
	}

	private String getField(Map<String, String> mappedfields, String key, boolean isRequired) throws OperationFailedException {
		String field = mappedfields.get(key);
		if (isRequired && (field == null)) {
			throw new OperationFailedException("Campo " + key + " nulo o ausente");
		}
		return field;
	}

	private Date getDateField(Map<String, String> mappedfields, String key, boolean isRequired)
			throws OperationFailedException, ParseException {

		Date field = dateFormatter(mappedfields.get(key));
		if (isRequired && field == null)
			throw new OperationFailedException("Campo " + key + " nulo o ausente desde el ws");

		return field;
	}

	private Date dateFormatter(String date) throws ParseException {
		if (date != null && !date.isEmpty()) {
			if (date.length() == 8) {
				return dateFormatter2.parse(date);
			} else {
				return dateFormatter.parse(date);
			}
		}
		return null;
	}
	
	private Double getDoubleField(Map<String, String> mappedfields, String key, boolean isRequired) throws OperationFailedException {

		String field = mappedfields.get(key);
		Double doubleField = field != null && !field.isEmpty() ? Double.valueOf(field.replace(",", ".")) : null;
		if (isRequired && (doubleField == null || doubleField == 0 ))
			throw new OperationFailedException("Campo " + key + " nulo o ausente desde el ws");
		else
			return (doubleField == null ? null : (doubleField >= 0 ? doubleField : 0));

	}

}
