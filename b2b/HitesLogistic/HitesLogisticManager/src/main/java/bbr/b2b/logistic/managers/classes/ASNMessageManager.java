package bbr.b2b.logistic.managers.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.logistic.datings.report.classes.DatingDataToMessageDTO;
import bbr.b2b.logistic.dvrdeliveries.classes.BulkDetailServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.BulkServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DocumentServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DocumentStateServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryStateServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryStateTypeServerLocal;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DocumentStateW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DocumentW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateTypeW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNDataToMessageDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNDetailDataToMessageDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.WSSentMessageResultDTO;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderW;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.logistic.managers.interfaces.ASNMessageManagerLocal;
import bbr.b2b.logistic.report.classes.WSResultDTO;
import bbr.b2b.logistic.utils.BackUpUtils;
import bbr.b2b.logistic.utils.LogisticStatusCodeUtils;
import bbr.b2b.logistic.xml.lib_appointment.LgfData.ListOfAppointments;
import bbr.b2b.logistic.xml.lib_shipment.Header;
import bbr.b2b.logistic.xml.lib_shipment.IbShipment;
import bbr.b2b.logistic.xml.lib_shipment.IbShipmentDtl;
import bbr.b2b.logistic.xml.lib_shipment.IbShipmentHdr;
import bbr.b2b.logistic.xml.lib_shipment.LgfData;
import bbr.b2b.logistic.xml.lib_shipment.ListOfIbShipments;


@Stateless(name = "managers/ASNMessageManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ASNMessageManager implements ASNMessageManagerLocal {

	private static Logger logger = Logger.getLogger(ASNMessageManager.class);
	private static Logger loggerWS = Logger.getLogger("LogWsASN");
	
	// ASN LGFDATA
	private static final String ASN_PREFIX_FILE	 						= "ISS";	
	private static final String ASN_LGF_VERSION 						= "6.4";
	private static final String ASN_LGF_CLIENT_ENV_CODE					= "Hites";
	private static final String ASN_LGF_PARENT_COMPANY_CODE				= "CAD";
	private static final String ASN_LGF_ENTITY							= "ib_shipment";
	private static final String ASN_LGF_PRIORITY_DATE					= "20501231";
	private static final String ASN_LGF_COMPANYCODE						= "HITES";
	
	// Interfaz Citas Logfire
	private static final String DAT_PREFIX_FILE 						= "IAP";	
	private static final String DAT_LGF_VERSION 						= "6.4";
	private static final String DAT_ORIGIN_SYSTEM 						= "B2B";
	private static final String DAT_LGF_CLIENT_ENV_CODE					= "WMS";
	private static final String DAT_LGF_PARENT_COMPANY_CODE				= "*";
	private static final String DAT_LGF_ENTITY							= "appointment";
	private static final String DAT_DOCK_VALUE							= "Entrada";
		
	@EJB
	DvrOrderServerLocal dvrorderserver;
	
	@EJB
	LocationServerLocal locationserver;
	
	@EJB
	OrderTypeServerLocal ordertypeserver;
	
	@EJB
	DocumentServerLocal documentserver;
	
	@EJB
	DocumentStateServerLocal documentstateserver;
	
	@EJB
	DatingServerLocal datingserver;
	
	@EJB
	DvrDeliveryServerLocal dvrdeliveryserver;
	
	@EJB
	DvrDeliveryStateServerLocal dvrdeliverystateserver;
	
	@EJB
	DvrDeliveryStateTypeServerLocal dvrdeliverystatetypeserver;
	
	@EJB
	BulkServerLocal bulkserver;
	
	@EJB
	BulkDetailServerLocal bulkdetailserver;
	
	@Resource
	private javax.ejb.SessionContext mySessionCtx;
	
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	
	private static JAXBContext jcLgfData = null;
	
	private static JAXBContext getJCLGFDATA() throws JAXBException {
		if (jcLgfData == null)
			jcLgfData = JAXBContext.newInstance("bbr.b2b.logistic.xml.lib_shipment");
		return jcLgfData;
	}
		
	private static JAXBContext jcLgfData_appointment = null;
	
	private static JAXBContext getJCLGFDATA_APPOINTMENT() throws JAXBException{
		if(jcLgfData_appointment == null)
			jcLgfData_appointment = JAXBContext.newInstance("bbr.b2b.logistic.xml.lib_appointment");
		return jcLgfData_appointment;
	}
	
	@Resource
	private ManagedExecutorService executor;
	
	private static CloseableHttpClient client = null;
	
	private static CloseableHttpClient getConnectionToWS(){
		if (client == null) {			
			try {
				client = HttpClients.custom()
		                .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(), NoopHostnameVerifier.INSTANCE))
		                .build();				
			}catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
		return client;
	}
		
	private LgfData doCreateASNMessageLgf(ASNDataToMessageDTO asndata) throws OperationFailedException, NotFoundException {		
		// Formatos decimales
		DecimalFormat df = new DecimalFormat("0");
				
		// Formato de fechas
		DateTimeFormatter msgDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter asnDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd000000");
		DateTimeFormatter docDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		LocalDateTime now = LocalDateTime.now();
		
		// Bodega Internet
		LocationW internetLocationW = locationserver.getByPropertyAsSingleResult("code", "02");
		
		// Tipos de OC
		OrderTypeW predType = ordertypeserver.getByPropertyAsSingleResult("code", "PREDISTRIBUIDA");
		OrderTypeW stockType = ordertypeserver.getByPropertyAsSingleResult("code", "STOCK");
		OrderTypeW vevType = ordertypeserver.getByPropertyAsSingleResult("code", "DVH");

		// Construir el xml para enviar a LOGFIRE
		bbr.b2b.logistic.xml.lib_shipment.ObjectFactory objFactory = new bbr.b2b.logistic.xml.lib_shipment.ObjectFactory();
		
		// Crear los datos del encabezado LogFire
		Header header = objFactory.createHeader();		
		header.setDocumentVersion(ASN_LGF_VERSION); 							// Versión Actual de LogFire
		header.setClientEnvCode(ASN_LGF_CLIENT_ENV_CODE); 						// Ambiente LogFire
		header.setParentCompanyCode(ASN_LGF_PARENT_COMPANY_CODE); 				// Valor fijo: CAD
		header.setEntity(ASN_LGF_ENTITY); 										// Nombre de la interfaz en LogFire
		header.setTimeStamp(msgDateFormat.format(now)); 						// Fecha y hora de envío del mensaje
		header.setMessageId(ASN_PREFIX_FILE + "_" + asndata.getAsnnumber()); 	// Nombre del mensaje
		
		/////////// Crear los detalles del ASN ///////////
		List<IbShipmentDtl> ibShipmentDtlList = new ArrayList<IbShipmentDtl>();
		IbShipmentDtl ibShipmentDtl = null;
		int index = 0;
		for (ASNDetailDataToMessageDTO detail : asndata.getAsndetaildatatomessage()) {
			ibShipmentDtl = objFactory.createIbShipmentDtl();
			
			ibShipmentDtl.setSeqNbr(++index); 																		// Contador de fila de detalle
			ibShipmentDtl.setActionCode(detail.getAction()); 														// Acción: CREATE/UPDATE/DELETE
			ibShipmentDtl.setLpnNbr(detail.getLpncode()); 															// Código LPN
			ibShipmentDtl.setLpnWeight(""); 																		// Peso del LPN
			ibShipmentDtl.setLpnVolume(""); 																		// Volumen del LPN
			ibShipmentDtl.setItemAlternateCode(""); 																// Código de empaque del SKU
			ibShipmentDtl.setItemPartA(detail.getItemsku()); 														// Código SKU (primera parte)
			ibShipmentDtl.setItemPartB(""); 																		// Código SKU (segunda parte)
			ibShipmentDtl.setItemPartC(""); 																		// Código SKU (tercera parte)
			ibShipmentDtl.setItemPartD(""); 																		// Código SKU (cuarta parte)
			ibShipmentDtl.setItemPartE(""); 																		// Código SKU (quinta parte)
			ibShipmentDtl.setItemPartF(""); 																		// Código SKU (sexta parte)
			ibShipmentDtl.setPrePackCode(""); 																		// Código de pre-empaque
			ibShipmentDtl.setPrePackRatio(0); 																		// SKU/pre-empaque
			ibShipmentDtl.setPrePackTotalUnits(0); 																	// SKU totales en pre-empaques
			ibShipmentDtl.setInvnAttrA(""); 																		// Atributo A
			ibShipmentDtl.setInvnAttrB(""); 																		// Atributo B
			ibShipmentDtl.setInvnAttrC(""); 																		// Atributo C
			ibShipmentDtl.setShippedQty(df.format(detail.getUnits())); 												// Cantidad enviada
			ibShipmentDtl.setPriorityDate(ASN_LGF_PRIORITY_DATE); 													// Fecha de prioridad (Valor fijo: 20501231)				
			ibShipmentDtl.setPoNbr(String.valueOf(detail.getOcnumber())); 											// Número de la OC
			ibShipmentDtl.setPalletNbr(""); 																		// Número de pálet
			ibShipmentDtl.setPutawayType(""); 																		// Tipo de ordenamiento
			ibShipmentDtl.setExpiryDate(""); 																		// Fecha de vencimiento del inventario			
			ibShipmentDtl.setBatchNbr(detail.getReferencenumber() != null ? detail.getReferencenumber() : ""); 		// Número de lote del cliente
			ibShipmentDtl.setRecvXdockFacilityCode(asndata.getOrdertypecode().equals("PREDISTRIBUIDA") ?
											detail.getDestinationlocationcode() : ""); 								// Código de la sucursal de destino (solo para orden tipo 'PREDISTRIBUIDA')		
			ibShipmentDtl.setCustField1(""); 																		// Campo configurable 1
			ibShipmentDtl.setCustField2(""); 																		// Campo configurable 2
			ibShipmentDtl.setCustField3(""); 																		// Campo configurable 3
			ibShipmentDtl.setCustField4(""); 																		// Campo configurable 4
			ibShipmentDtl.setCustField5(""); 																		// Campo configurable 5
			ibShipmentDtl.setLpnIsPhysicalPalletFlg(false); 														// LPN-estiba
			ibShipmentDtl.setPoSeqNbr(0); 																			// Posición en el detalle de OC
			ibShipmentDtl.setPrePackRatioSeq(0); 																	// Secuencia en el pre-empaque
			ibShipmentDtl.setLpnLockCode(""); 																		// Código de bloqueo del LPN
			ibShipmentDtl.setItemBarcode(""); 																		// Código de barra del LPN
			ibShipmentDtl.setUom(""); 																				// Unidad de medida del empaque
			ibShipmentDtl.setLpnLength(""); 																		// Largo del LPN
			ibShipmentDtl.setLpnWidth(""); 																			// Ancho del LPN
			ibShipmentDtl.setLpnHeight(""); 																		// Altura del LPN
			
			ibShipmentDtlList.add(ibShipmentDtl);						
		}
		
		/////////// Crear la cabecera del ASN ///////////				
		// Obtiene la orden asociada
		DvrOrderW[] dvrorderArr = dvrorderserver.getByPropertyAsArray("id", asndata.getDvrorderid());
		if (dvrorderArr.length == 0) {
			throw new NotFoundException("Orden de copra no existe") ;
		}
		DvrOrderW dvrorderW =  dvrorderArr[0];
		Long[] dvrorderids = {dvrorderW.getId()};
		
		// Tipo de orden
		OrderTypeW[] orderTypeArr = ordertypeserver.getOrderTypeByDvrOrders(dvrorderids);
		OrderTypeW orderTypeW =  orderTypeArr[0];  
		
		// Local de destino de la orden
		LocationW deliverylocationW = locationserver.getById(dvrorderW.getDeliverylocationid());
		
		// INTNAC : ordenes de bodega 2 Internet.
		String shipmentType = "";
		if (deliverylocationW.getId().equals(internetLocationW.getId())) {
			shipmentType = "INTNAC"; 
		} 
		// NAC : OC de stock
		else if (orderTypeW.getId().equals(stockType.getId())) {
			shipmentType = "NAC";
		}		
		// XD: OC predistribuida
		else if (orderTypeW.getId().equals(predType.getId())) {
			shipmentType = "XD";
		}
		// DVH: OC VeV.
		else if (orderTypeW.getId().equals(vevType.getId())) {
			shipmentType = "DVH";
		}
		
		/////////////////////////////////////////////////////////////////////////////////				
		IbShipmentHdr ibShipmentHbr = objFactory.createIbShipmentHdr();
		
		ibShipmentHbr.setShipmentNbr(asndata.getAsnnumber()); 										// Número de ASN (Nro OC/documento tributario/fecha/correlativo)
		ibShipmentHbr.setFacilityCode(asndata.getDeliverylocationcode()); 							// Centro de distribución
		ibShipmentHbr.setCompanyCode(ASN_LGF_COMPANYCODE); 											// Código de la compañía SAP 
		ibShipmentHbr.setTrailerNbr(asndata.getDocumenttype()); 									// Tipo de Documento
		ibShipmentHbr.setActionCode(asndata.getAction()); 											// Acción: CREATE/UPDATE/DELETE
		ibShipmentHbr.setRefNbr(String.valueOf(asndata.getDocumentnumber()));		 				//Número de referencia
		ibShipmentHbr.setShipmentType(shipmentType); 												// Clase de documento ASN
		ibShipmentHbr.setLoadNbr("L" + asndata.getDvrdeliverynumber()); 							// Número de carga = Número de entrega B2B
		ibShipmentHbr.setManifestNbr(asndata.getDocumentcode()); 									// Tipo de documento (código)
		ibShipmentHbr.setTrailerType(""); 															// Tipo de camión
		ibShipmentHbr.setVendorInfo(asndata.getVendorcode()); 										// Información adicional del proveedor -> code
		ibShipmentHbr.setOriginInfo(""); 															// Información del origen
		ibShipmentHbr.setOriginCode(""); 															// Código del origen
		ibShipmentHbr.setOrigShippedUnits(0); 														// Total de unidades enviadas
		ibShipmentHbr.setLockCode(""); 																// Código de bloqueo
		ibShipmentHbr.setShippedDate(asndata.getPluploaddate() != null ?
										asnDateFormat.format(asndata.getPluploaddate()) :
										""); 														// Fecha de carga de ASN en B2B --> guardar fecha de carga de PL en delivery
		ibShipmentHbr.setOrigShippedLpns(0); 														// Total de LPN enviados
		ibShipmentHbr.setCustField1(asndata.getNetamount() != null ?
										df.format(asndata.getNetamount()) :
										""); 														// Monto neto --> documento
		ibShipmentHbr.setCustField2(asndata.getTaxamount() != null ?
										df.format(asndata.getTaxamount()) :
										""); 														// IVA --> diff en el doc
		ibShipmentHbr.setCustField3(asndata.getTotalamount() != null ?
										df.format(asndata.getTotalamount()) :
										""); 														// Monto total --> doc
		ibShipmentHbr.setCustField4(docDateFormat.format(asndata.getExpirationdate())); 			// Fecha de vencimiento de la orden asociada
		ibShipmentHbr.setCustField5((asndata.getPluploaddate() != null) ?
										docDateFormat.format(asndata.getPluploaddate()) :
										""); 														// Fecha de factura --> guardar fecha de carga de PL en delivery

		// Crear el ASN
		IbShipment IbShipment = objFactory.createIbShipment();			
		IbShipment.setIbShipmentHdr(ibShipmentHbr);
		IbShipment.getIbShipmentDtl().addAll(ibShipmentDtlList);
		
		// Crear la lista de ASNs
		ListOfIbShipments listOfIbShipments = objFactory.createListOfIbShipments();			
		listOfIbShipments.getIbShipment().add(IbShipment);
					
		// Crear los datos de LOGFIRE
		LgfData lgfdata = objFactory.createLgfData();					
		lgfdata.setHeader(header);
		lgfdata.setListOfIbShipments(listOfIbShipments);
		
		return lgfdata;		
	}
	
	private bbr.b2b.logistic.xml.lib_appointment.LgfData doCreateDatingMessageLgf(DatingDataToMessageDTO[] datingdatatomessageArr) throws OperationFailedException, NotFoundException {
		LocalDateTime now = LocalDateTime.now();
		XMLGregorianCalendar xmlDateTimeNow = null;
		
		DateTimeFormatter msgDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		
		try {
			xmlDateTimeNow = DatatypeFactory.newInstance().newXMLGregorianCalendar(now.format(msgDateFormat));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			return null;
		}

		// Construir el xml para enviar a LOGFIRE
		bbr.b2b.logistic.xml.lib_appointment.ObjectFactory objFactory = new bbr.b2b.logistic.xml.lib_appointment.ObjectFactory();
		
		// Crear los datos del encabezado LogFire
		bbr.b2b.logistic.xml.lib_appointment.LgfData.Header header = objFactory.createLgfDataHeader();
		header.setDocumentVersion(DAT_LGF_VERSION); 													// Versión Actual de LogFire
		header.setOriginSystem(DAT_ORIGIN_SYSTEM);														// Sistema
		header.setClientEnvCode(DAT_LGF_CLIENT_ENV_CODE); 												// Ambiente LogFire
		header.setParentCompanyCode(DAT_LGF_PARENT_COMPANY_CODE); 										// Valor fijo: CAD
		header.setEntity(DAT_LGF_ENTITY); 																// Nombre de la interfaz en LogFire
		header.setTimeStamp(xmlDateTimeNow); 															// Fecha y hora de envío del mensaje
		header.setMessageId(DAT_PREFIX_FILE + "_" + datingdatatomessageArr[0].getDvrdeliverynumber()); 	// Nombre del mensaje

		/////////////////////////////////////////////////////////////////////////////////
		// Cabecera de la cita		
		List<bbr.b2b.logistic.xml.lib_appointment.Appointment> appointmentList = new ArrayList<bbr.b2b.logistic.xml.lib_appointment.Appointment>();
		
		// Itera sobre citas informadas
		for (DatingDataToMessageDTO datingdatatomessage : datingdatatomessageArr) {
			bbr.b2b.logistic.xml.lib_appointment.Appointment appointment= objFactory.createAppointment();
			
			// Formato de la fecha de la cita
			XMLGregorianCalendar xmlReserveStart = null;		
			try {
				xmlReserveStart = DatatypeFactory.newInstance().newXMLGregorianCalendar(datingdatatomessage.getReservestart().format(DateTimeFormatter.ISO_DATE_TIME));
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
				return null;
			}
			
			appointment.setFacilityCode(datingdatatomessage.getDeliverylocationcode());
			appointment.setCompanyCode("HITES");
			appointment.setApptNbr(datingdatatomessage.getDvrdeliverynumber().toString());
			appointment.setLoadNbr("L" + datingdatatomessage.getDvrdeliverynumber());
			appointment.setDockType(DAT_DOCK_VALUE);
			appointment.setActionCode(datingdatatomessage.getAction());
			//appointment.setPreferredDockNbr("");
			appointment.setPlannedStartTs(xmlReserveStart);
			appointment.setDuration(BigInteger.valueOf(datingdatatomessage.getReserveminutes()));
			appointment.setEstimatedUnits(BigDecimal.valueOf(datingdatatomessage.getEstimatedunits()).toBigInteger());
			//appointment.setCarrierInfo("");
			//appointment.setTrailerNbr("");
			//appointment.setLoadType("");

			// Agregar cita al listado
			appointmentList.add(appointment);
		}
		
		// Lista de citas
		ListOfAppointments listOfAppointments = objFactory.createLgfDataListOfAppointments();
		listOfAppointments.getAppointment().addAll(appointmentList);		
		
		// Crear los datos de LOGFIRE
		bbr.b2b.logistic.xml.lib_appointment.LgfData lgfdata = objFactory.createLgfData();
		
		lgfdata.setHeader(header);
		lgfdata.setListOfAppointments(listOfAppointments);
		
		return  lgfdata;		
	}
		
	private void doBackUpMessage(String content, String number, String msgType) {
		// EJECUTA UNA TAREA QUE RESPALDA EL MENSAJE.
		// ESTA ES INDEPENDIENTE DE LA CARGA DEL MENSAJE.
		try {
			executor.submit(new BackUpUtils(content, number, msgType));
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		}
	}
		
	// Enviar mensaje ASN vía WS
	private WSResultDTO doSendMessageToWS(String xmlcontent, String messageid) throws IOException {
		WSResultDTO resultDTO = new WSResultDTO (); 
		CloseableHttpResponse response = null;
		CloseableHttpClient client = null;
		
		try {
			String responsecontent = "";
			String responseText = "";
			
			// URL para acceder al WS
			String url = LogisticConstants.getWS_ASN_URL();
			
			// Method
			String method = LogisticConstants.getWS_ASN_METHOD();
		
			// Credenciales
			String user = LogisticConstants.getWS_ASN_USER();
			String pass = LogisticConstants.getWS_ASN_PASSWORD();
			
			// Conexión
			client = getConnectionToWS();			
			
			// Datos a enviar
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(new BasicNameValuePair("xml_data", xmlcontent));			
			HttpEntity entity = new UrlEncodedFormEntity(data);
			
			// Setear parámetros de autenticación
			String usernameAndPassword = user + ":" + pass;
			String authorizationHeaderName = "Authorization";
			String authorizationHeaderValue = "Basic " + new String(Base64.encodeBase64(usernameAndPassword.getBytes()));

			HttpPost httpPost = new HttpPost(url + "/" + method + "/" );
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			httpPost.addHeader(authorizationHeaderName, authorizationHeaderValue);
			httpPost.setEntity(entity);
			
			// Configuración de proxy
			if (LogisticConstants.getHttpProxy()) {
				// Proxy
				String proxyIP = LogisticConstants.getHttpProxyIP();
				Integer proxyPort = LogisticConstants.getHttpProxyPort();
				HttpHost proxyHost = new HttpHost(proxyIP, proxyPort, "http");
				
				// Set the proxy and build a RequestConfig object
				RequestConfig.Builder reqconfigconbuilder= RequestConfig.custom();
				reqconfigconbuilder = reqconfigconbuilder.setProxy(proxyHost);
				RequestConfig config = reqconfigconbuilder.build();
				
				// Agregar configuración del proxy
				httpPost.setConfig(config);
			}			
			
			// Log de llamada
			loggerWS.info("[INPUT " + messageid + "] " + xmlcontent);
			
			// Ejecutar la llamada
			response = client.execute(httpPost);			
			InputStream contentStream = response.getEntity().getContent();
			
			try {
				// Obtener la respuesta del WS
				StringBuilder textBuilder = new StringBuilder();
				Reader reader = new BufferedReader(new InputStreamReader(contentStream, Charset.forName(StandardCharsets.UTF_8.name())));
				int c = 0;
				while ((c = reader.read()) != -1) {
					textBuilder.append((char) c);
				}
				responsecontent = textBuilder.toString();				

				String[] contentTags1 = null;
				String[] contentTags2 = null;
				
				// Obtener los tags de respuesta 
				// - ResponseText
				contentTags1 = responsecontent.split("<message>");
				contentTags2 = contentTags1[1].split("</message>");
				responseText = contentTags2[0].trim();
				
				// - success
				contentTags1 = responsecontent.split("<success>");
				contentTags2 = contentTags1[1].split("</success>");
				String success = contentTags2[0].trim();
				
				// Almacenar los valores informados por WS
				resultDTO.setWscode(String.valueOf(response.getStatusLine().getStatusCode()));
				resultDTO.setResponseText(responseText);
				resultDTO.setSuccess(Boolean.parseBoolean(success));
				
			} catch (Exception e) {
				e.printStackTrace();
				responseText = "No se pudo obtener la respuesta del WS (formato erróneo)";
				e.printStackTrace();
				resultDTO.setWscode(String.valueOf(response.getStatusLine().getStatusCode()));
				resultDTO.setResponseText(responseText);
				resultDTO.setSuccess(false);
			}
			
			loggerWS.info("[OUTPUT " +  messageid + "] " + responsecontent);
			
		} catch (Exception e) {
			e.printStackTrace();
 			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L7000");		 			
		} finally {
			if (response != null) {
				response.close();
			}
		}
		
		return resultDTO;		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WSSentMessageResultDTO doSendDocumentASNCreationMessage(DocumentW documentW, Long vendorId, List<ASNDataToMessageDTO> header, List<ASNDetailDataToMessageDTO> details, String userName, String userType) {
		WSSentMessageResultDTO asnMessageData = new WSSentMessageResultDTO(); 
		
		// Booleano que indica si se presentó error en el envío
		asnMessageData.setSenterror(true);
					
		// Almacenar el estado del resultado de envío
		BaseResultDTO resultDTO = new BaseResultDTO();
					
		try {
			// Estado de envío de documento
			boolean documentState = false;
			
			/////////// Obtener los datos para el mensaje ///////////
			// Cabecera del mensaje
			// MME: Esta query siempre debería devolver un resultado
			// Si devuelve más filas, implica que en el bulto hay más de una orden asociada (definición actual no lo permite)
			// Se usa siempre primer resultado		
			ASNDataToMessageDTO asnData = null;
			if (header != null && header.size() > 0 && details != null && details.size() > 0) {
				asnData = header.get(0);
			}
			else {
				// Almacenar el resultado
				resultDTO.setStatuscode("L6005");
				resultDTO.setStatusmessage("Error generando interfaz de ASN");
				
				asnMessageData.setMessageid(documentW.getNumber());
				asnMessageData.setValidationresult(resultDTO);
				
				return asnMessageData;
			}
			
			// Si no existe el número de ASN crearlo
			if (asnData.getAsnnumber().length() == 0) {
				// Obtener el último número asn generado para ese número de documento/proveedor
				Integer lastCorrelative = documentserver.getCorrelativeFromASNNumber(documentW.getNumber(), vendorId);
				
				// Calcular el nuevo correlativo
				String newCorrelative = String.format("%02d", lastCorrelative + 1);
			
				// Número de ASN (número de orden/número de documento tributario/correlativo)
				String asnNumber = asnData.getOrdernumber() + "_" + documentW.getNumber() + "_" +  newCorrelative;
				asnData.setAsnnumber(asnNumber);					
			}
			
			asnData.setAsndetaildatatomessage(details);
			
			/////////// Crear el ASN ///////////
			LgfData asnmessage = doCreateASNMessageLgf(asnData);
			
			/////////// Respaldar el mensaje y enviar ///////////
			try {
				// Obtener string XML para enviarlo a la cola
				JAXBContext jcAsn = getJCLGFDATA();
				Marshaller m = jcAsn.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				m.marshal(asnmessage, sw);
				String result = sw.toString();

				// Respaldar el mensaje
				doBackUpMessage(result, asnData.getAsnnumber(), ASN_PREFIX_FILE);

				// Enviar el mensaje vía WS
				WSResultDTO wsResultDTO = doSendMessageToWS(result, asnData.getAsnnumber());			

				// Caso 1: Llamada a servicio OK y WS devuelve 'Éxito'
				if (wsResultDTO.getStatuscode().equals("0") && wsResultDTO.getSuccess()) {
					// Almacenar el resultado
					resultDTO.setStatuscode("WS" + wsResultDTO.getWscode());
					resultDTO.setStatusmessage("ASN enviado con éxito");
					
					asnMessageData.setMessageid(documentW.getNumber());
					asnMessageData.setValidationresult(resultDTO);
					asnMessageData.setSenterror(false);
					
					// Actualizar el estado del documento
					documentState = true;
					documentW.setStatus(documentState);
				} 	
				
				// Caso 2: Llamada a servicio devuelve un error
				else if (!wsResultDTO.getStatuscode().equals("0")) {
					// Almacenar el resultado
					resultDTO.setStatuscode(wsResultDTO.getStatuscode());
					resultDTO.setStatusmessage(wsResultDTO.getStatusmessage()); // Error en la llamada a WS
					
					asnMessageData.setMessageid(documentW.getNumber());
					asnMessageData.setValidationresult(resultDTO);		
				}
									
				// Caso 3: Llamada a servicio OK y WS no devuelve 'Éxito'
				// Muestra código y mensaje de error entregado por el WS
				else {
					// Almacenar el resultado
					resultDTO.setStatuscode("WS" + wsResultDTO.getWscode());
					resultDTO.setStatusmessage(wsResultDTO.getResponseText());
					
					asnMessageData.setMessageid(documentW.getNumber());
					asnMessageData.setValidationresult(resultDTO);
				}
				
				// Guardar el número de ASN en el documento
				documentW.setAsnnumber(asnData.getAsnnumber());
				documentW = documentserver.updateDocument(documentW);
				
				// Estado del documento
				DocumentStateW docstateW = new DocumentStateW();
				docstateW.setDocumentid(documentW.getId());
				docstateW.setDocumentresponsecode(wsResultDTO.getWscode());
				docstateW.setDocumentresponsemessage(wsResultDTO.getResponseText());
				docstateW.setStatus(documentState);
				docstateW.setUser(userName);
				docstateW.setUsertype(userType);
				docstateW.setWhen(LocalDateTime.now());
				documentstateserver.addDocumentState(docstateW);
				
			} catch (JAXBException e) {
				// Error en creación de XML 
				e.printStackTrace();
				
				// Almacenar el resultado
				resultDTO.setStatuscode("L6005");
				resultDTO.setStatusmessage("Error generando interfaz de ASN. " + e.getMessage());
				
				asnMessageData.setMessageid(documentW.getNumber());
				asnMessageData.setValidationresult(resultDTO);

			} catch (IOException e) {
				e.printStackTrace();
				
				// Almacenar el resultado
				resultDTO.setStatuscode("L7001");
				resultDTO.setStatusmessage("Error de conexión con WS. " + e.getMessage());
				
				asnMessageData.setMessageid(documentW.getNumber());
				asnMessageData.setValidationresult(resultDTO);
				
			}
			
			// Guardar el número de ASN en el documento
			if (documentW.getAsnnumber() == null || documentW.getAsnnumber().length() == 0) {
				documentW.setAsnnumber(asnData.getAsnnumber());
				documentW = documentserver.updateDocument(documentW);
			}
			
		} catch (Exception e) {
			// Almacenar el resultado
			resultDTO.setStatuscode("L1");
			resultDTO.setStatusmessage("Error en el proceso de creación de ASN");
			
			asnMessageData.setMessageid(documentW.getNumber());
			asnMessageData.setValidationresult(resultDTO);
			
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			logger.error("Error en el proceso de envío de mensaje ASN del documento " + documentW.getNumber());
		}
		
		return asnMessageData;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WSSentMessageResultDTO doSendDocumentASNDeleteMessage(DocumentW documentW, List<ASNDataToMessageDTO> header, List<ASNDetailDataToMessageDTO> details, List<Long> bulkIds) {
		WSSentMessageResultDTO asnMessageData = new WSSentMessageResultDTO(); 
		
		// Booleano que indica si se presentó error en el envío
		asnMessageData.setSenterror(true);
					
		// Almacenar el estado del resultado de envío
		BaseResultDTO resultDTO = new BaseResultDTO();
					
		try {
			/////////// Obtener los datos para el mensaje ///////////
			// Cabecera del mensaje
			// MME: Esta query siempre debería devolver un resultado
			// Si devuelve más filas, implica que en el bulto hay más de una orden asociada (definición actual no lo permite)
			// Se usa siempre primer resultado		
			ASNDataToMessageDTO asnData = null;
			if (header != null && header.size() > 0 && details != null && details.size() > 0) {
				asnData = header.get(0);
			}
			else {
				// Almacenar el resultado
				resultDTO.setStatuscode("L6005");
				resultDTO.setStatusmessage("Error generando interfaz de ASN");
				
				asnMessageData.setMessageid(documentW.getNumber());
				asnMessageData.setValidationresult(resultDTO);
				
				return asnMessageData;
			}
			
			details.stream().forEach(detail -> detail.setAction("DELETE"));
			asnData.setAction("DELETE");
			asnData.setAsndetaildatatomessage(details);
			
			/////////// Crear el ASN ///////////
			LgfData asnmessage = doCreateASNMessageLgf(asnData);
			
			/////////// Respaldar el mensaje y enviar ///////////
			try {
				// Obtener string XML para enviarlo a la cola
				JAXBContext jcAsn = getJCLGFDATA();
				Marshaller m = jcAsn.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				m.marshal(asnmessage, sw);
				String result = sw.toString();

				// Respaldar el mensaje
				doBackUpMessage(result, asnData.getAsnnumber(), ASN_PREFIX_FILE);

				// Enviar el mensaje vía WS
				WSResultDTO wsResultDTO = doSendMessageToWS(result, asnData.getAsnnumber());			

				// Caso 1: Llamada a servicio OK y WS devuelve 'Éxito'
				if (wsResultDTO.getStatuscode().equals("0") && wsResultDTO.getSuccess()) {
					// Almacenar el resultado
					resultDTO.setStatuscode("WS" + wsResultDTO.getWscode());
					resultDTO.setStatusmessage("ASN enviado con éxito");
					
					asnMessageData.setMessageid(documentW.getNumber());
					asnMessageData.setValidationresult(resultDTO);
					asnMessageData.setSenterror(false);
					
					// Eliminar el documento, los bultos y sus detalles
					for (Long bulkId : bulkIds) {
						bulkdetailserver.deleteByProperty("bulk.id", bulkId);
						bulkserver.deleteByProperty("id", bulkId);
					}
					documentserver.deleteByProperty("id", documentW.getId());
				} 	
				
				// Caso 2: Llamada a servicio devuelve un error
				else if (!wsResultDTO.getStatuscode().equals("0")) {
					// Almacenar el resultado
					resultDTO.setStatuscode(wsResultDTO.getStatuscode());
					resultDTO.setStatusmessage(wsResultDTO.getStatusmessage()); // Error en la llamada a WS
					
					asnMessageData.setMessageid(documentW.getNumber());
					asnMessageData.setValidationresult(resultDTO);		
				}
									
				// Caso 3: Llamada a servicio OK y WS no devuelve 'Éxito'
				// Muestra código y mensaje de error entregado por el WS
				else {
					// Almacenar el resultado
					resultDTO.setStatuscode("WS" + wsResultDTO.getWscode());
					resultDTO.setStatusmessage(wsResultDTO.getResponseText());
					
					asnMessageData.setMessageid(documentW.getNumber());
					asnMessageData.setValidationresult(resultDTO);
				}
				
			} catch (JAXBException e) {
				// Error en creación de XML 
				e.printStackTrace();
				
				// Almacenar el resultado
				resultDTO.setStatuscode("L6005");
				resultDTO.setStatusmessage("Error generando interfaz de ASN. " + e.getMessage());
				
				asnMessageData.setMessageid(documentW.getNumber());
				asnMessageData.setValidationresult(resultDTO);

			} catch (IOException e) {
				e.printStackTrace();
				
				// Almacenar el resultado
				resultDTO.setStatuscode("L7001");
				resultDTO.setStatusmessage("Error de conexión con WS. " + e.getMessage());
				
				asnMessageData.setMessageid(documentW.getNumber());
				asnMessageData.setValidationresult(resultDTO);
				
			}
			
		} catch (Exception e) {
			// Almacenar el resultado
			resultDTO.setStatuscode("L1");
			resultDTO.setStatusmessage("Error en el proceso de eliminación de ASN");
			
			asnMessageData.setMessageid(documentW.getNumber());
			asnMessageData.setValidationresult(resultDTO);
			
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			logger.error("Error en el proceso de envío de mensaje ASN del documento " + documentW.getNumber());
		}
		
		return asnMessageData;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WSSentMessageResultDTO doSendDatingCreationMessage(DatingW datingW, DvrDeliveryW dvrDeliveryW, String userName, String userType) {
		WSSentMessageResultDTO datingMessageData = new WSSentMessageResultDTO(); 
		
		// Booleano que indica si se presentó error en el envío
		datingMessageData.setSenterror(true);
				
		// Almacenar el estado del resultado de envío
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try {
			/////////// Obtener los datos para el mensaje ///////////
			DatingDataToMessageDTO[] datingDataToMessage = datingserver.getDatingDataToMessage(dvrDeliveryW.getId());
			
			if (datingDataToMessage == null || datingDataToMessage.length == 0) {
				// Almacenar el resultado
				resultDTO.setStatuscode("L6007");
				resultDTO.setStatusmessage("Error en creación de XML de Cita");
				
				datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
				datingMessageData.setValidationresult(resultDTO);
				
				return datingMessageData;
			}
			
			/////////// Crear el mensaje de cita ///////////
			bbr.b2b.logistic.xml.lib_appointment.LgfData datingnmessage = doCreateDatingMessageLgf(datingDataToMessage);
			
			/////////// Respaldar el mensaje y enviar ///////////
			try {
				// Obtener string XML para enviarlo a la cola
				JAXBContext jcDating = getJCLGFDATA_APPOINTMENT();
				Marshaller m = jcDating.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				m.marshal(datingnmessage, sw);
				String resultDating = sw.toString();

				// Respaldar el mensaje
				doBackUpMessage(resultDating, dvrDeliveryW.getNumber().toString(), DAT_PREFIX_FILE);

				// Enviar el mensaje vía WS
				WSResultDTO wsResultDTO = doSendMessageToWS(resultDating, dvrDeliveryW.getNumber().toString());
				
				// Caso 1: Llamada a servicio OK y WS devuelve éxito
				if (wsResultDTO.getStatuscode().equals("0") && wsResultDTO.getSuccess()) {
					// Almacenar el resultado
					resultDTO.setStatuscode("WS" + wsResultDTO.getWscode());
					resultDTO.setStatusmessage("Cita enviada con éxito");
					
					datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
					datingMessageData.setValidationresult(resultDTO);
					datingMessageData.setSenterror(false);
					
					// Actualizar la cita con el estado 'Enviado'
					datingW.setSentstatus(true);
					datingW = datingserver.updateDating(datingW);
					
					// Actualizar el estado del despacho a 'Cita Confirmada'
					DvrDeliveryStateTypeW dstConfirmed = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_CONFIRMADA");
					LocalDateTime now = LocalDateTime.now();
					
					dvrDeliveryW.setCurrentstatetypeid(dstConfirmed.getId());
					dvrDeliveryW.setCurrentstatetypedate(now);
					dvrDeliveryW = dvrdeliveryserver.updateDvrDelivery(dvrDeliveryW);

					// Estado
					DvrDeliveryStateW dvrDeliveryStateW = new DvrDeliveryStateW();
					dvrDeliveryStateW.setComment("");
					dvrDeliveryStateW.setDvrdeliveryid(dvrDeliveryW.getId());
					dvrDeliveryStateW.setDvrdeliverystatetypeid(dstConfirmed.getId());
					dvrDeliveryStateW.setUser(userName);
					dvrDeliveryStateW.setUsertype(userType);
					dvrDeliveryStateW.setWhen(now);
					dvrdeliverystateserver.addDvrDeliveryState(dvrDeliveryStateW);
				} 	
				
				// Caso 2: Llamada a servicio devuelve un error
				else if(! wsResultDTO.getStatuscode().equals("0")) {
					// Almacenar el resultado
					resultDTO.setStatuscode(wsResultDTO.getStatuscode());
					resultDTO.setStatusmessage(wsResultDTO.getStatusmessage());
					
					datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
					datingMessageData.setValidationresult(resultDTO);
				}
									
				// Caso 3: Otro caso
				// Muestra código y mensaje de error entregado por el WS
				else {
					// Almacenar el resultado
					resultDTO.setStatuscode("WS" + wsResultDTO.getWscode());
					resultDTO.setStatusmessage(wsResultDTO.getResponseText());
					
					datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
					datingMessageData.setValidationresult(resultDTO);
				}					
				
			} catch (JAXBException e) {
				// Error en creación de XML
				e.printStackTrace();
				
				// Almacena resultado
				resultDTO.setStatuscode("L6007");
				resultDTO.setStatusmessage("Error en creación de XML de Cita. " + e.getMessage());
				
				datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
				datingMessageData.setValidationresult(resultDTO);
				
			} catch (IOException e) {
				e.printStackTrace();
				
				// Almacena resultado
				resultDTO.setStatuscode("L7001");
				resultDTO.setStatusmessage("Error de conexión con WS. " + e.getMessage());
				
				datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
				datingMessageData.setValidationresult(resultDTO);
				
			}
			
		} catch (Exception e) {
			// Almacenar el resultado
			resultDTO.setStatuscode("L1");
			resultDTO.setStatusmessage("Error en el proceso de creación de cita");
			
			datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
			datingMessageData.setValidationresult(resultDTO);
			
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			logger.error("Error en el proceso de envío de mensaje de cita del despacho " + dvrDeliveryW.getNumber());
		}
		
		return datingMessageData;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WSSentMessageResultDTO doSendDatingDeleteMessage(DvrDeliveryW dvrDeliveryW) {
		WSSentMessageResultDTO datingMessageData = new WSSentMessageResultDTO(); 
		
		// Booleano que indica si se presentó error en el envío
		datingMessageData.setSenterror(true);
				
		// Almacenar el estado del resultado de envío
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try {
			/////////// Obtener los datos para el mensaje ///////////
			DatingDataToMessageDTO[] datingDataToMessage = datingserver.getDatingDataToMessage(dvrDeliveryW.getId());
			
			if (datingDataToMessage == null || datingDataToMessage.length == 0) {
				// Almacenar el resultado
				resultDTO.setStatuscode("L6007");
				resultDTO.setStatusmessage("Error en creación de XML de Cita");
				
				datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
				datingMessageData.setValidationresult(resultDTO);
				
				return datingMessageData;
			}
			
			Arrays.stream(datingDataToMessage).forEach(data -> data.setAction("DELETE"));
			
			/////////// Crear el mensaje de cita ///////////
			bbr.b2b.logistic.xml.lib_appointment.LgfData datingnmessage = doCreateDatingMessageLgf(datingDataToMessage);
			
			/////////// Respaldar el mensaje y enviar ///////////
			try {
				// Obtener string XML para enviarlo a la cola
				JAXBContext jcDating = getJCLGFDATA_APPOINTMENT();
				Marshaller m = jcDating.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				m.marshal(datingnmessage, sw);
				String resultDating = sw.toString();

				// Respaldar el mensaje
				doBackUpMessage(resultDating, dvrDeliveryW.getNumber().toString(), DAT_PREFIX_FILE);

				// Enviar el mensaje vía WS
				WSResultDTO wsResultDTO = doSendMessageToWS(resultDating, dvrDeliveryW.getNumber().toString());
				
				// Caso 1: Llamada a servicio OK y WS devuelve éxito
				if (wsResultDTO.getStatuscode().equals("0") && wsResultDTO.getSuccess()) {
					// Almacenar el resultado
					resultDTO.setStatuscode("WS" + wsResultDTO.getWscode());
					resultDTO.setStatusmessage("Cita enviada con éxito");
					
					datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
					datingMessageData.setValidationresult(resultDTO);
					datingMessageData.setSenterror(false);
				} 	
				
				// Caso 2: Llamada a servicio devuelve un error
				else if(! wsResultDTO.getStatuscode().equals("0")) {
					// Almacenar el resultado
					resultDTO.setStatuscode(wsResultDTO.getStatuscode());
					resultDTO.setStatusmessage(wsResultDTO.getStatusmessage()); // Error en la llamada a WS
					
					datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
					datingMessageData.setValidationresult(resultDTO);
				}
									
				// Caso 3: Otro caso
				// Muestra código y mensaje de error entregado por el WS
				else {
					// Almacenar el resultado
					resultDTO.setStatuscode("WS" + wsResultDTO.getWscode());
					resultDTO.setStatusmessage(wsResultDTO.getResponseText());
					
					datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
					datingMessageData.setValidationresult(resultDTO);
				}					
				
			} catch (JAXBException e) {
				// Error en creación de XML
				e.printStackTrace();
				
				// Almacena resultado
				resultDTO.setStatuscode("L6007");
				resultDTO.setStatusmessage("Error en creación de XML de Cita. " + e.getMessage());
				
				datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
				datingMessageData.setValidationresult(resultDTO);
				
			} catch (IOException e) {
				e.printStackTrace();
				
				// Almacena resultado
				resultDTO.setStatuscode("L7001");
				resultDTO.setStatusmessage("Error de conexión con WS. " + e.getMessage());
				
				datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
				datingMessageData.setValidationresult(resultDTO);
				
			}
			
		} catch (Exception e) {
			// Almacenar el resultado
			resultDTO.setStatuscode("L1");
			resultDTO.setStatusmessage("Error en el proceso de eliminación de cita");
			
			datingMessageData.setMessageid(dvrDeliveryW.getNumber().toString());
			datingMessageData.setValidationresult(resultDTO);
			
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			logger.error("Error en el proceso de envío de mensaje de cita del despacho " + dvrDeliveryW.getNumber());
		}
		
		return datingMessageData;
	}
}