package bbr.b2b.extractors.manager.classes;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
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
import com.sun.xml.bind.marshaller.DataWriter;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.Ventas;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.Ventas.Comprador;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.Ventas.Vendedor;
import bbr.b2b.b2blink.logistic.xml.OC_customer.DiscountCharge;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Local;
import bbr.b2b.b2blink.logistic.xml.OC_customer.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Action;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Charges;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Client;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Discounts;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Section;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Predistributions;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Predistributions.Predistribution;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.keycloak.classes.JsonUtils;
import bbr.b2b.extraactors.falabella.dto.CredentialSodimacDTO;
import bbr.b2b.extractors.manager.interfaces.SodimacWSOrderManagerLocal;
import bbr.b2b.extractors.timers.manager.interfaces.SchedulerManagerLocal;
import bbr.b2b.extractors.utils.MyCharacterEscapeHandler;
import bbr.b2b.extractors.utils.WsSoaService;
import bbr.b2b.irsb2blink.sodimac.wsclient.classes.ClientConnection;
import bbr.b2b.irsb2blink.sodimac.wsclient.classes.InitParamsWs;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.esb.services.webservices.facade.InitParamCSDTO;

@SuppressWarnings("restriction")
@Stateless(name = "managers/SodimacWSOrderManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SodimacWSManager implements SodimacWSOrderManagerLocal {

	private static Logger logger = Logger.getLogger(SodimacWSManager.class);
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
	private static final SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd-MM-yy");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static JAXBContext jc = null;
	private static final String SITECODE = "CL1001";
	private static final String SITENAME = "SODIMAC";
	private static final String SERVICE = "LOC";
	private static final String SERVICE_RVT = "RVT";
	private static final String SERVICE_RIV = "RIV";
	String headerseOC = B2BSystemPropertiesUtil.getProperty("sodimacheaders");
	String headerseOD = B2BSystemPropertiesUtil.getProperty("sodimacpredeliveryheader");
	String headerseOE = B2BSystemPropertiesUtil.getProperty("sodimacvevheader");
//
	@EJB
	SchedulerManagerLocal schmanager;
//
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

	public void doProcess() throws Exception {
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

	public void doProcessOD() throws Exception {
		try {
			requestOrders(SERVICE, "eOD");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void doProcessOE() throws Exception {
		try {
			requestOrders(SERVICE, "eOE");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void doProcessVtaInv() throws Exception {
		try {
			requestVta(SERVICE_RVT ,SERVICE_RIV ,"ReporteVentaeInventario");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void requestVta(String service_rvt, String service_riv , String requestType) throws Exception {
		try {
			
			logger.info("Empresa: " + SITENAME + " SERVICIO DESCARGA REPORTE VENTAS E INVENTARIO WS");
			
			//Ir a buscar credenciales al WS-BBR (SOA)
			WsSoaService wsSoaService = new WsSoaService();
			List<InitParamCSDTO> credentials_rvt = wsSoaService.getCredentials(SITECODE, service_rvt);
//			List<InitParamCSDTO> credentials_riv = wsSoaService.getCredentials(SITECODE, service_riv);
			
			//Recorrer las credenciales
			for (InitParamCSDTO credential : credentials_rvt) {
				//Conexion al WS de sodimac con las credenciales del WS-BBR
				CredentialSodimacDTO credentialSodimacDTO = JsonUtils.getObjectFromJson(credential.getCredenciales(), CredentialSodimacDTO.class);
				InitParamsWs initParamsWs = new InitParamsWs();
				initParamsWs.setUserNameWs(credentialSodimacDTO.getUser());
				initParamsWs.setPasswordWs(credentialSodimacDTO.getPassword());
				initParamsWs.setWsEndpoint("http://b2b.sodimac.com/b2bwsextsoclpr/ws/adminArchivoService");
				initParamsWs.setProxy(B2BSystemPropertiesUtil.getProperty("PROXY").equals("true"));
				initParamsWs.setProxyAddress(B2BSystemPropertiesUtil.getProperty("PROXY_HOST"));
				initParamsWs.setProxyPort(Integer.parseInt(B2BSystemPropertiesUtil.getProperty("PROXY_PORT")));
				ClientConnection connection = new ClientConnection(initParamsWs);
				AdminArchivoDescargaRequest requestDescarga = new AdminArchivoDescargaRequest();
				Files files = new Files();
				File file = new File();
				file.setTipo(requestType);
				files.getFile().add(file);
				requestDescarga.setFiles(files);
				
				AdminArchivoDescargaResponse adminArchivoDescargaResponse = descargaArchivo(requestDescarga);
				List<Message> messageList = adminArchivoDescargaResponse.getMessages().getMessage();

				for (Message message : messageList) {
					if (message.getCode().equals("1")) {
						logger.info(message.getValue());
					} else {
						doTransformReportVta(message, credential.getAccesscode(), requestType,credential);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private AdminArchivoDescargaResponse descargaArchivo(AdminArchivoDescargaRequest requestDescarga) {

		AdminArchivoDescargaResponse adminArchivoDescargaResponse = new AdminArchivoDescargaResponse();

		Message message = new Message();
		message.setCode("216033832");
		message.setValue("CAMPO1|CAMPO2|CAMPO3|CAMPO4|CAMPO5|CAMPO6|CAMPO7|CAMPO8|CAMPO9|CAMPO10|CAMPO11|CAMPO12|CAMPO13|CAMPO14|CAMPO15|CAMPO16|CAMPO17|CAMPO18\n"+
						"7807311076228|1877852        |0|COCINA 4P 66L F2808T FENSA|0316010101     |COCINA / ESTUFAS PISO A GAS 4 PLATOS|FENSA               ||70|HC P Est|2019-07-22|0|0|3|2|0|0|0\r\n" + 
						"7807311719828|3171221        |0|PE REF NF TMF TX 61 FENSA|0316010404     |REFRIGERACION NO FROST/AUTOMATICO|FENSA               ||66|HC-NUBLE|2019-07-22|0|0|0|-1|0|0|0");
     	Messages messages = new Messages();
		messages.getMessage().add(message);
		adminArchivoDescargaResponse.setMessages(messages);
		return adminArchivoDescargaResponse;
	}
	
	private void doTransformReportVta(Message message, String accesscode, String requestType ,InitParamCSDTO credential) throws Exception {
		//Creo el archivo CSV
		String downloadFolder = "C:\\Users\\Carlos Ortiz\\Downloads\\pruebaSodimacReporte"; 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = Calendar.getInstance().getTime();
		String fileNameVta = "VTA_" + SITENAME + "_" + sdf.format(now) + ".csv";
		java.io.File tempFileVta = new java.io.File(downloadFolder, fileNameVta);
		PrintWriter writer = new PrintWriter(tempFileVta);
		writer.write(message.getValue());
		writer.flush();
		writer.close();
		
		//Transformar mensaje en xml y enviarlo a la cola
		try {
			
			String result = "";
			JAXBContext jc = JAXBContext.newInstance("bbr.b2b.b2blink.commercial.xml.ReporteVentas");
			bbr.b2b.b2blink.commercial.xml.ReporteVentas.ObjectFactory objFactory = new bbr.b2b.b2blink.commercial.xml.ReporteVentas.ObjectFactory();
			Ventas ventasrequest = objFactory.createVentas();
			ventasrequest.setNombreportal("SODIMAC_COMERCIAL");

			Comprador comprador = objFactory.createVentasComprador();
			comprador.setUnidadNegocio("BuyerBussinessArea");
			comprador.setRut(credential.getAccesscode());
			comprador.setRazonSocial(credential.getCompanyname());
			ventasrequest.setComprador(comprador);

			Vendedor vendedor = objFactory.createVentasVendedor();
			vendedor.setRut("");
			vendedor.setRazonSocial("");
			ventasrequest.setVendedor(vendedor);

			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			GZIPOutputStream gzipstream = new GZIPOutputStream(outstream);
			
			String rutaCompletaArchivo = tempFileVta.toString();
			java.io.File file = new java.io.File(rutaCompletaArchivo);
			byte[] encodedContent = null;
			encodedContent = java.nio.file.Files.readAllBytes(file.toPath());
			byte[] databytes = encodedContent;
			gzipstream.write(databytes);
			gzipstream.close();
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			String data_compressed = encoder.encode(outstream.toByteArray());
			ventasrequest.setData(data_compressed);

			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", new MyCharacterEscapeHandler());
			m.marshal(ventasrequest, dataWriter);
			result = stringWriter.toString();
			
			logger.info(result);
			schmanager.doSendMessageQueue(SITENAME,accesscode, message.getCode(), result, null);
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

				try {
					
					if(credential.getCredenciales() == null || credential.getCredenciales().isEmpty()){
						logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " credenciales vacias o nulas");
						continue;
					}
					
					logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " descargando órdenes");
					
					CredentialSodimacDTO credentialSodimacDTO = JsonUtils.getObjectFromJson(credential.getCredenciales(),CredentialSodimacDTO.class);
					InitParamsWs initParamsWs = new InitParamsWs();
					initParamsWs.setUserNameWs(credentialSodimacDTO.getUser());
					initParamsWs.setPasswordWs(credentialSodimacDTO.getPassword());
					initParamsWs.setWsEndpoint("http://b2b.sodimac.com/b2bwsextsoclpr/ws/adminArchivoService");
					initParamsWs.setProxy(B2BSystemPropertiesUtil.getProperty("PROXY").equals("true"));
					initParamsWs.setProxyAddress(B2BSystemPropertiesUtil.getProperty("PROXY_HOST"));
					initParamsWs.setProxyPort(Integer.parseInt(B2BSystemPropertiesUtil.getProperty("PROXY_PORT")));
					ClientConnection connection = new ClientConnection(initParamsWs);
					AdminArchivoDescargaRequest requestDescarga = new AdminArchivoDescargaRequest();
					Files files = new Files();
					File file = new File();
					file.setTipo(requestType);
					files.getFile().add(file);
					requestDescarga.setFiles(files);
					
					AdminArchivoDescargaResponse adminArchivoDescargaResponse = connection.getPortClient().descargaArchivo(requestDescarga);
					List<Message> messageList = adminArchivoDescargaResponse.getMessages().getMessage();
			
					for (Message message : messageList) {
						if(message.getCode().equals("1")){
							logger.info(message.getValue());
						}else {
							doTransformeOC(message, credential.getAccesscode(), requestType);
						}
					}
					
				} catch (Exception e) {
					logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + "ocurrío un error al descargar ordenes");
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
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

				String[] info = linea.split("\\|",-1);;

				String numOrder = info[1];

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
				String[] infoMensaje = new String[28];
				infoMensaje = lineas.get(0).split("\\|",-1);
				

				// For para setear las cabecera con su respectivo valor
				for (int j = 0; j < headersArray.length; j++) {
					mapMessage.put(headersArray[j], infoMensaje[j]);
				}
				
				if(requestType.equals("eOC") || requestType.equals("eOD")) {
					if(getField(mapMessage, "TIPO_OC",true ).equals("SE")) {
						logger.warn("Se descarta la orden " + getField(mapMessage, "NRO_OC", true) + " al ser tipo SE para Sodimac");
						break;
					}
				}
				
				if (requestType.equals("eOC")) {
					Action action = new Action();
					action.setCode("");
					action.setDescription("");
					orderXML.setAction(action);
				} else if (requestType.equals("eOE")) {
					Action action = new Action();
					action.setCode("");
					action.setDescription("");
					orderXML.setAction(action);
				}

				// NUMERO OC
				orderXML.setNumber(getField(mapMessage, "NRO_OC", true));

				// NUMERO SOLICITUD
				if (requestType.equals("eOE")) {
					orderXML.setRequest(getField(mapMessage, "RESERVA", false));
				} else {
					orderXML.setRequest("");
				}

				// PreviousOrdertype
				if (requestType.equals("eOC")) {
					orderXML.setPreviousordertype("");
					orderXML.setComplete(false);
				} else if (requestType.equals("eOD")) {
					orderXML.setPreviousordertype("eOC");
					orderXML.setComplete(true);
				} else {
					orderXML.setPreviousordertype("");
					orderXML.setComplete(true);
				}

				// ESTADO OC
				if (requestType.equals("eOC")) {
					orderXML.setStatus(mapMessage.get("ESTADO_OC"));
					logger.info(orderXML.getStatus());
				} else if (requestType.equals("eOD")) {
					orderXML.setStatus(mapMessage.get("ESTADO_OC"));
					logger.info(orderXML.getStatus());
				} else if (requestType.equals("eOE")) {
					orderXML.setStatus("LIBERADA");
				}

				//TIPO OC
				if(requestType.equals("eOC") || requestType.equals("eOD")) {
					orderXML.setOrdertype(requestType);
					orderXML.setOrdertypename(mapMessage.get("TIPO_OC"));
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

				// SECCION
				Section seccion = new Section();
				seccion.setCode("");
				seccion.setName("");
				orderXML.setSection(seccion);

				// FECHAS
				Locale locale = new Locale("es", "CL");
				GregorianCalendar gcal = new GregorianCalendar(locale);

				// FECHA EMISION
				if (requestType.equals("eOC")) {
					String fechaTransformada = mapMessage.get("FECHA_EMISION");
					fechaTransformada.replace("/", "-");
					Date date = new SimpleDateFormat("dd-MM-yyyy").parse(fechaTransformada);
					gcal.setTime(date);
					XMLGregorianCalendar xmlgcal_emitted = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					orderXML.setIssuedate(xmlgcal_emitted);
				} else if (requestType.equals("eOE")) {
					String fechaTransformada = mapMessage.get("FECHA_EMISION_OC");
					fechaTransformada.replace("/", "-");
					Date date = new SimpleDateFormat("dd/MM/yyyy").parse(fechaTransformada);
					gcal.setTime(date);
					XMLGregorianCalendar xmlgcal_emitted = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					orderXML.setIssuedate(xmlgcal_emitted);
				}
				// FECHA VIGENCIA
				if (requestType.equals("eOC")) {
					String fechaTransformada = mapMessage.get("FECHA_ESPER");
					fechaTransformada.replace("/", "-");
					Date date = new SimpleDateFormat("dd-MM-yyyy").parse(fechaTransformada);
					gcal.setTime(date);
					XMLGregorianCalendar xmlgcal_valid = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					orderXML.setEffectivdate(xmlgcal_valid);
				} else if (requestType.equals("eOE")) {
					String fechaTransformada = mapMessage.get("FECHA_DESPACHO_PACTADA");
					fechaTransformada.replace("/", "-");
					Date date = new SimpleDateFormat("dd/MM/yyyy").parse(fechaTransformada);
					gcal.setTime(date);
					XMLGregorianCalendar xmlgcal_valid = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					orderXML.setEffectivdate(xmlgcal_valid);
				}

				// FECHA VENCIMIENTO
				if (requestType.equals("eOC")) {
					if (getDateField(mapMessage, "FECHA_VENC", false) != null) {
						String fechaTransformada = mapMessage.get("FECHA_VENC");
						fechaTransformada.replace("/", "-");
						Date date = new SimpleDateFormat("dd-MM-yyyy").parse(fechaTransformada);
						gcal.setTime(date);
						XMLGregorianCalendar xmlgcal_expiration = DatatypeFactory.newInstance()
								.newXMLGregorianCalendar(gcal);
						orderXML.setExpirationdate(xmlgcal_expiration);
					} else {
						String fechaTransformada = mapMessage.get("FECHA_ESPER");
						fechaTransformada.replace("/", "-");
						Date date = new SimpleDateFormat("dd-MM-yyyy").parse(fechaTransformada);
						gcal.setTime(date);
						XMLGregorianCalendar xmlgcal_expiration = DatatypeFactory.newInstance()
								.newXMLGregorianCalendar(gcal);
						orderXML.setExpirationdate(xmlgcal_expiration);
					}
				} else if (requestType.equals("eOE")) {
					if (getDateField(mapMessage, "FECHA_VENC", false) != null) {
						String fechaTransformada = mapMessage.get("FECHA_VENC");
						fechaTransformada.replace("/", "-");
						Date date = new SimpleDateFormat("dd/MM/yyyy").parse(fechaTransformada);
						gcal.setTime(date);
						XMLGregorianCalendar xmlgcal_expiration = DatatypeFactory.newInstance()
								.newXMLGregorianCalendar(gcal);
						orderXML.setExpirationdate(xmlgcal_expiration);
					} else {
						String fechaTransformada = mapMessage.get("FECHA_DESPACHO_PACTADA");
						fechaTransformada.replace("/", "-");
						Date date = new SimpleDateFormat("dd/MM/yyyy").parse(fechaTransformada);
						gcal.setTime(date);
						XMLGregorianCalendar xmlgcal_expiration = DatatypeFactory.newInstance()
								.newXMLGregorianCalendar(gcal);
						orderXML.setExpirationdate(xmlgcal_expiration);
					}
				}

				// FECHA COMPROMISO
				if (requestType.equals("eOC")) {
					String fechaTransformada = mapMessage.get("FECHA_ESPER");
					fechaTransformada.replace("/", "-");
					Date date = new SimpleDateFormat("dd-MM-yyyy").parse(fechaTransformada);
					gcal.setTime(date);
					XMLGregorianCalendar xmlgcal_delivery = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					orderXML.setCommitmentdate(xmlgcal_delivery);
				} else if (requestType.equals("eOE")) {
					String fechaTransformada = mapMessage.get("FECHA_REPARTO_CLIENTE");
					fechaTransformada.replace("/", "-");
					int numero = transformarFecha(fechaTransformada);
					if(numero < 10) {
						fechaTransformada = fechaTransformada.substring(0,3) + 0+numero +fechaTransformada.substring(6);
					}else {
						fechaTransformada = fechaTransformada.substring(0,3) + numero +fechaTransformada.substring(6);
					}
					Date date = new SimpleDateFormat("dd-MM-yyyy").parse(fechaTransformada);
					gcal.setTime(date);
					XMLGregorianCalendar xmlgcal_delivery = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					orderXML.setCommitmentdate(xmlgcal_delivery);
				}
				
				// TOTAL
				if (requestType.equals("eOC")) {
					orderXML.setTotal(getDoubleField(mapMessage, "TOTAL_OC_BRUTO", true));
				} else if (requestType.equals("eOE")) {
					orderXML.setTotal(getDoubleField(mapMessage, "PRECIO_COSTO", false));
				}

				// CODIGO LOCAL DE ENTREGA
				Local local = new Local();
				if (requestType.equals("eOD")) {
					local.setCode(getField(mapMessage, "COD_L_ENTREGA", true));
					local.setName(getField(mapMessage, "DESC_L_ENTREGA", true));
					local.setEan13("");
					local.setAddress("");
					orderXML.setDeliveryplace(local);
				} else if (requestType.equals("eOE")) {
					//Este campo no viene en el mensaje
					local.setCode(String.valueOf(doGenerateLocationCode(getField(mapMessage, "DESPACHAR_A", true))));
					local.setName(getField(mapMessage, "DESPACHAR_A", true));
					local.setEan13(doGenerateEan13(getField(mapMessage, "DESPACHAR_A", true)));
					local.setAddress("");
					orderXML.setDeliveryplace(local);
				}

				// FORMA DE PAGO
				if (requestType.equals("eOC")) {
					orderXML.setPaymentcondition(getField(mapMessage, "FORMA_PAGO", true).trim());
				} else {
					orderXML.setPaymentcondition("");
				}

				// COMENTARIOS
				orderXML.setObservation("");

				// RESPONSABLE
				if (requestType.equals("eOC")) {
					orderXML.setResponsible(getField(mapMessage, "COMPRADOR", true));
				} else {
					orderXML.setResponsible("");
				}

				orderXML.setBuyer(SITECODE);
				orderXML.setVendor(accesCode);

				// CLIENTE
				if (requestType.equals("eOE")) {
					Client client = new Client();
					orderXML.setClient(client);
					String receiverName = getField(mapMessage, "NOM_RECEPTOR", false);
					if (receiverName != null && !receiverName.isEmpty()) {
						receiverName = receiverName.trim().replaceAll("\\s+", " ");
					}
					orderXML.getClient().setName(receiverName);
					orderXML.getClient().setIdentity(getField(mapMessage, "DNI_COMPRADOR", false));
					orderXML.getClient().setPhone(getField(mapMessage, "TELEFONO_COMPRADOR", false));
					orderXML.getClient().setAddress(getField(mapMessage, "DIRECCION_RECEPTOR", false));
					orderXML.getClient().setStreetnumber("");
					orderXML.getClient().setApartment(getField(mapMessage, "DOMICILIO/OFICINA", false));
					orderXML.getClient().setHousenumber(getField(mapMessage, "DESPACHAR_A", false));
					orderXML.getClient().setRegion(getField(mapMessage, "REGION", false));
					orderXML.getClient().setCommune(getField(mapMessage, "COMUNA_RECEPTOR", false));
					orderXML.getClient().setCity(getField(mapMessage, "CIUDAD_RECEPTOR", false).trim());
					orderXML.getClient().setObservation(getField(mapMessage, "OBSERVACION", false));
				}

				// DESCUENTOS GENERALES
				if (requestType.equals("eOC")) {
					DiscountCharge discountCharge = new DiscountCharge();
					discountCharge.setCode("");
					discountCharge.setPercentage(false);
					discountCharge.setDescription("");
					discountCharge.setValue(getDoubleField(mapMessage, "TOTAL_DESCUENTOS", false) != null ? getDoubleField(mapMessage, "TOTAL_DESCUENTOS", false) : 0);

					Discounts discounts = new Discounts();
					discounts.getDiscounts().add(discountCharge);
					orderXML.setDiscounts(discounts);

				} else if (requestType.equals("eOE")) {
					DiscountCharge discountCharge = new DiscountCharge();
					discountCharge.setCode("");
					discountCharge.setPercentage(false);
					discountCharge.setDescription("");
					discountCharge.setValue(getDoubleField(mapMessage, "DESCUENTO", false) != null ? getDoubleField(mapMessage, "DESCUENTO", false) : 0);

					Discounts discounts = new Discounts();
					discounts.getDiscounts().add(discountCharge);
					orderXML.setDiscounts(discounts);
				}

				// CARGOS GENERALES
				if (requestType.equals("eOC")) {
					DiscountCharge discountCharge = new DiscountCharge();
					discountCharge.setCode("");
					discountCharge.setPercentage(false);
					discountCharge.setDescription("");
					discountCharge.setValue(getDoubleField(mapMessage, "TOTAL_CARGOS", false) != null ? getDoubleField(mapMessage, "TOTAL_DESCUENTOS", false).floatValue() : 0);

					Charges charges = new Charges();
					charges.getCharges().add(discountCharge);
					orderXML.setCharges(charges);

				} else if (requestType.equals("eOE")) {
					DiscountCharge discountCharge = new DiscountCharge();
					discountCharge.setCode("");
					discountCharge.setPercentage(false);
					discountCharge.setDescription("");
					Double totalCharge = getDoubleField(mapMessage, "UNIDADES", true)
							* getDoubleField(mapMessage, "DESCUENTO", false);
					discountCharge.setValue(totalCharge);

					Charges charges = new Charges();
					charges.getCharges().add(discountCharge);
					orderXML.setCharges(charges);

				}
				// DETALLES
				orderXML.setDetails(setDetails(objFactory, lineas, headersArray, requestType));

				JAXBContext jc = getJC();
				Marshaller m = jc.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				m.marshal(orderXML, sw);
				String msg = sw.toString();

				logger.info(msg);
				schmanager.doSendMessageQueue(SITENAME, accesCode, orderXML.getNumber(), msg,null);
				msg = "";
			}		
		}
		
		//Limpiar las variables para evitar desbordamiento de memoria.
		numOrders.clear(); 
		mapMessage.clear();
	}
	
	private int doGenerateLocationCode(String localCode) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			new OperationFailedException("Error al generar código de local");
		}
		int code2 = new BigInteger(1, md.digest(localCode.getBytes())).intValue();
		code2 = Math.abs(code2);
		
		return code2; 
	}
	
	private String doGenerateEan13(String ean13) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			new OperationFailedException("Error al generar código de local");
		}
		String code = new BigInteger(1, md.digest(ean13.getBytes())).toString(16);
		return code;
	}

	private Details setDetails(ObjectFactory objFactory, List<String> lineas, String[] headersArray, String requestType) throws OperationFailedException, ParseException {
		
		HashMap<String, String> mapMessageSku = new HashMap<String, String>();
		HashMap<String, String> mapMessageTemp = new HashMap<String, String>();
		HashMap<String, String> listaSku = new HashMap<String, String>();
		HashMap<String, String> mapMessage = new HashMap<String, String>();
		
		Details detalles = objFactory.createOrderDetails();
		List<Detail> listaDetalles = detalles.getDetail();
		
		int position = 0;
		int posicion = 1;

		for (int i = 0; i < headersArray.length; i++) {
			if (headersArray[i].equals("SKU")) {
				position = i;
				break;
			}
		}

		for (String linea : lineas) {
			String[] lineaSeparadas = linea.split("\\|",-1);
			mapMessageSku.put(lineaSeparadas[position], linea);
		}
		
		//Metodo para fixear el campo umb_x_umc.
		if(!requestType.equals("eOE")) {
			int suma = 0;
			for(String lineaTemporal : lineas) {
				String[] lineaSeparada = lineaTemporal.split("\\|",-1);
				for (int i = 0; i < headersArray.length; i++) {
					mapMessageTemp.put(headersArray[i], lineaSeparada[i].trim());
				}
				//Si esta solo suma el valor.
				if(listaSku.containsKey(mapMessageTemp.get("SKU"))) {
					suma = suma + Integer.parseInt(mapMessageTemp.get("CANTIDAD_EMPAQ"));
					listaSku.put(mapMessageTemp.get("SKU"), String.valueOf(suma));
				}else {
					//Si no esta lo agrega a la lista
					listaSku.put(mapMessageTemp.get("SKU") , mapMessageTemp.get("CANTIDAD_EMPAQ"));
					suma = Integer.parseInt(mapMessageTemp.get("CANTIDAD_EMPAQ"));
				}
			}
		}
				
		List<String> listadoSkuOrdenado = new ArrayList<>(mapMessageSku.keySet());
		Collections.sort(listadoSkuOrdenado);
		
		for (String sku : listadoSkuOrdenado) {
			// Tengo el sku ordenado y la linea completa
			String linea = mapMessageSku.get(sku);

			Detail detalle = objFactory.createOrderDetailsDetail();
			String[] lineaSeparada = linea.split("\\|",-1);

			for (int i = 0; i < headersArray.length; i++) {
				mapMessage.put(headersArray[i], lineaSeparada[i].trim());
			}

			if(requestType.equals("eOE")){
				detalle.setPosition(Integer.parseInt(getField(mapMessage,"NRO_OC", true)));
			}else {
				detalle.setPosition(Integer.parseInt(getField(mapMessage,"N_ITEM", true)));
			}
			
			// SKU
			detalle.setSkubuyer(getField(mapMessage, "SKU", true));
			String skuParam = detalle.getSkubuyer();

			// DESCRIPCION
			if (requestType.equals("eOC")) {
				detalle.setDescriptionumc(getField(mapMessage, "UM_EMPAQUE", false));
				detalle.setEan13(getField(mapMessage, "UPC", false));
				detalle.setProductdescription(getField(mapMessage, "DESCRIPCION_LARGA", true));
				int cantidad = Integer.parseInt(getField(mapMessage, "CANTIDAD_EMPAQ", true));
				detalle.setQuantityumc(cantidad);
				// COSTO LISTA
				detalle.setListcost(Double.parseDouble(getField(mapMessage, "PRECIO_UNI", false)));
				// COSTO FINAL
				detalle.setFinalcost(Double.parseDouble(getField(mapMessage, "PRECIO_UNI", false)));
				// PRECIO LISTA
				detalle.setListprice(Double.parseDouble(getField(mapMessage, "PRECIO_UNI", false)));
			}
	
			else if (requestType.equals("eOE")) {
				if (getField(mapMessage, "RESERVA", false) != null) {
					detalle.setDescriptionumc(getField(mapMessage, "RESERVA", false));
					detalle.setEan13(getField(mapMessage, "UPC", false));
					detalle.setProductdescription(getField(mapMessage, "DESCRIPCION", true));
					detalle.setQuantityumc(1);
					// COSTO LISTA
					Double costoUnitario = Double.valueOf(getField(mapMessage, "PRECIO_COSTO", false));
					detalle.setListcost(costoUnitario);
					// COSTO FINAL
					detalle.setFinalcost(costoUnitario);
					// PRECIO LISTA
					detalle.setListprice(costoUnitario);
				}
			}

			// CODIGO EMPAQUE
			if (requestType.equals("eOC")) {
				detalle.setCodeumc(getField(mapMessage, "COD_EMPAQUE", true));
			} else if (requestType.equals("eOE")) {
				detalle.setCodeumc(getField(mapMessage, "SKU", true));
			}

			// DESCRIPCION_UM_UNIDAD;
			if (requestType.equals("eOC")) {
				detalle.setDescriptionumb(getField(mapMessage, "UM_EMPAQUE", true));
			} else if (requestType.equals("eOE")) {
				detalle.setDescriptionumb(getField(mapMessage, "RESERVA", true));
			}

			// INNERPACK
			detalle.setInnerpack(1);

			// CANTIDAD EMPAQUE
			if (requestType.equals("eOD")) {
				Integer cantidadEmp = Integer.parseInt(getField(mapMessage, "CANTIDAD_EMPAQ", true)); //225
				Integer cantidadProd=  Integer.parseInt(getField(mapMessage, "CANTIDAD_PROD", true)); //900
				detalle.setQuantityumc(cantidadProd/cantidadEmp);
				if(listaSku.containsKey(mapMessage.get("SKU"))) {
					detalle.setUmbXUmc(Integer.parseInt(listaSku.get(mapMessage.get("SKU"))));
				}
			}
			if (requestType.equals("eOE")) {
				detalle.setUmbXUmc(Integer.parseInt(getField(mapMessage, "UNIDADES", false)));
			}
			
			// DESCUENTO DE LOS PRODUCTOS
			if (requestType.equals("eOC")) {
				DiscountCharge discountCharge = new DiscountCharge();
				discountCharge.setCode("DE1");
				discountCharge.setPercentage(false);
				discountCharge.setDescription("");
				discountCharge.setValue(getDoubleField(mapMessage, "DESCUENTO_ADIC", false));
				bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Discounts discount = new bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Discounts();
				discount.getDiscount().add(discountCharge);
				detalle.setDiscounts(discount);
			} else if (requestType.equals("eOE")) {
				DiscountCharge discountCharge = new DiscountCharge();
				discountCharge.setCode("DE1");
				discountCharge.setPercentage(false);
				discountCharge.setDescription("");
				discountCharge.setValue(getDoubleField(mapMessage, "DESCUENTO", false));
				bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Discounts discount = new bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Discounts();
				discount.getDiscount().add(discountCharge);
				detalle.setDiscounts(discount);
			}

			// CARGO DE LOS PRODUCTOS
			if (requestType.equals("eOC") || requestType.equals("eOE")) {
				bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Charges charges = new bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Charges();
				DiscountCharge charge = new DiscountCharge();
				charge.setCode("CA1");
				charge.setPercentage(false);
				charge.setDescription("");
				charge.setValue(getDoubleField(mapMessage, "CARGO_ADIC", false) != null
						? getDoubleField(mapMessage, "CARGO_ADIC", false).floatValue()
						: 0);
				charges.getCharge().add(charge);
				detalle.setCharges(charges);
			}

			if (requestType.equals("eOD")) {
				detalle.setPredistributions(setPredistributions(objFactory, lineas, headersArray, requestType, skuParam));
			}

			posicion++;
			listaDetalles.add(detalle);
		}
		
		//Limpiar las variables para evitar desbordamiento de memoria.
		mapMessageSku.clear();
		mapMessageTemp.clear();
		listaSku.clear();
		mapMessage.clear();
		
		return detalles;
	}
	
	public Predistributions setPredistributions(ObjectFactory objFactory, List<String> lineasCompletas, String[] headersArray, String requestType, String skuParam) throws OperationFailedException {
		
		HashMap<String, String> mapMessage = new HashMap<String, String>();
		
		Predistributions predistributions = objFactory.createOrderDetailsDetailPredistributions();
		List<Predistribution> listaPredistribution = predistributions.getPredistribution();
		
		int positionSKU = 0;
		
		//Capturo la posicion del SKU en los header
		for (int i = 0; i < headersArray.length; i++) {
			if (headersArray[i].equals("SKU")) {
				positionSKU = i;
				break;
			}
		}
		
		//Recorro linea a linea
		for (String linea : lineasCompletas) {
			//Separo cada linea por pipe |
			String[] lineaSeparadas = linea.split("\\|",-1);
			
			//Agrego al mapMesagge la respectiva cabecera y su valor.
			for (int i = 0; i < headersArray.length; i++) {
				mapMessage.put(headersArray[i], lineaSeparadas[i]);
			}
			
			String skuValue = mapMessage.get("SKU");
			
			//Valido si el sku por parametro es igual al que viene en las lineas.
			if(skuParam.equals(skuValue.trim())) {
				
				//Genero las instancias
				Predistribution predistribution = objFactory.createOrderDetailsDetailPredistributionsPredistribution();
				Local local = new Local();
				predistribution.setDeliveryplace(local);
				
				//Seteo los valores
				predistribution.getDeliveryplace().setCode(getField(mapMessage, "NRO_TIENDA", true)); //NRO_TIENDA
				predistribution.getDeliveryplace().setName(getField(mapMessage, "TIENDA", true)); //TIENDA
				predistribution.getDeliveryplace().setEan13(getField(mapMessage, "NRO_TIENDA", true));
				predistribution.getDeliveryplace().setAddress("");
				predistribution.setQuantity(Integer.parseInt(getField(mapMessage, "CANTIDAD_PROD", true)));//CANTIDAD_PROD
				predistribution.setShippingQuantity("");
				predistribution.setReceivedQuantity("");
				predistribution.setPendingQuantity("");
				
				//Agrego la predistribucion a la lista
				listaPredistribution.add(predistribution);
			}
		}
		
		//Limpiar las variables para evitar desbordamiento de memoria.
		mapMessage.clear();
		
		//Retorno la lista ya armada.
		return predistributions;
	}

	private String getField(Map<String, String> mappedfields, String key, boolean isRequired)
			throws OperationFailedException {
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

	private Double getDoubleField(Map<String, String> mappedfields, String key, boolean isRequired)
			throws OperationFailedException {

		String field = mappedfields.get(key);
		Double doubleField = field != null && !field.isEmpty() ? Double.valueOf(field.replace(",", ".")) : null;
		if (isRequired && (doubleField == null || doubleField == 0))
			throw new OperationFailedException("Campo " + key + " nulo o ausente desde el ws");
		else
			return (doubleField == null ? null : (doubleField >= 0 ? doubleField : 0));

	}
	
	public int transformarFecha(String fecha) {
		int result = 0;
		if(fecha.contains("ene") || fecha.contains("jan")) {
			result = 1;
		}
		else if(fecha.contains("feb")) {
			result = 2;
		}
		else if(fecha.contains("mar")) {
			result = 3;
		}
		else if(fecha.contains("abr") || fecha.contains("apr")) {
			result = 4;
		}
		else if(fecha.contains("may")) {
			result = 5;
		}
		else if(fecha.contains("jun")) {
			result = 6;
		}
		else if(fecha.contains("jul")) {
			result = 7;
		}
		else if(fecha.contains("ago") || fecha.contains("aug")) {
			result = 8;
		}
		else if(fecha.contains("sep")) {
			result = 9;
		}
		else if(fecha.contains("oct")) {
			result = 10;
		}
		else if(fecha.contains("nov")) {
			result = 11;
		}
		else if(fecha.contains("dic") || fecha.contains("dec")) {
			result = 12;
		}
		return result;
	}

}
