package bbr.b2b.logistic.queuehandler;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.sax.SAXSource;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.buyorders.classes.RetailerServerLocal;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.msgb2b.XmlToOrderLocal;
import bbr.b2b.logistic.notifications.managers.interfaces.NotificationsReportManagerLocal;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.utils.BackUpUtils;
import bbr.b2b.logistic.utils.MsgRecoveryServices;
import bbr.b2b.logistic.utils.NamespaceFilter;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.logistic.xml.ack.ACK.Motivo;
import bbr.b2b.logistic.xml.order.Orden;


@Stateless(name = "handlers/OrderHandler")
@TransactionManagement(TransactionManagementType.BEAN)
public class OrderHandler implements OrderHandlerLocal {

	private static final String SEPARATE_WITH_COMMA = "\",\"";

	private static Logger logger = Logger.getLogger(OrderHandler.class);
	private static Logger loggerAck = Logger.getLogger("LogNotificacion");

	// Acciones del Mensaje
	private static final String ACCION_CREAR 				= "CREAR";
	private static final String ACCION_MODIFICAR 			= "MODIFICAR";
	private static final String ACCION_CANCELAR 			= "CANCELAR";
	
	@Resource
	private ManagedExecutorService executor;

	@EJB
	XmlToOrderLocal xmltoorder;

	
	@EJB
	RetailerServerLocal retailerserver;
	
	@EJB
	SchedulerManagerLocal schedulermanager;
	
	@EJB
	NotificationsReportManagerLocal notificationReport;

	@EJB
	VendorServerLocal vendorserver;
	
	private static JAXBContext jc = null;

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.logistic.xml.order");
		return jc;
	}	
	
	
	private static JAXBContext jAck = null;

	private static JAXBContext getACKJC() throws JAXBException {
		if (jAck == null)
			jAck = JAXBContext.newInstance("bbr.b2b.logistic.xml.ack");
		return jAck;
	}

	
	private void doSendACK(String ordernumber, Integer state, String messagetype, String message, LocalDateTime datetosend){

		try{
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
			String now = LocalDateTime.now().format(dateTimeFormatter);
						
			//String stateStr = state.equals("0") ? "EXITO" : "FRACASO";
 
			// Envía respuesta
			JAXBContext jclocal = getACKJC();
			bbr.b2b.logistic.xml.ack.ObjectFactory objFactory = new bbr.b2b.logistic.xml.ack.ObjectFactory();
			bbr.b2b.logistic.xml.ack.ACK ackMSg = objFactory.createACK();

			ackMSg.setIdentificador(ordernumber);
			ackMSg.setTipoMensaje(messagetype);
			ackMSg.setEstado(state);
			ackMSg.setFecha(now);

			List<Motivo> details = ackMSg.getMotivo();
			Motivo detail = objFactory.createACKMotivo();
			detail.setCodigo("");
			detail.setDetalle(message);
			details.add(detail);
			
			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jclocal.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(ackMSg, sw);
			String result = sw.toString();

			// RESPALDA MENSAJE
			doBackUpMessage(result, ordernumber, "ACK");

			// Se registra el resultado de carga de mensajes en log de ACK
			loggerAck.info(",\"" + messagetype + SEPARATE_WITH_COMMA + ordernumber + SEPARATE_WITH_COMMA + state + SEPARATE_WITH_COMMA + message + "\"");

			// Se agrega mensaje a tabla de pending message
			schedulermanager.doAddMessageQueue("qcf_hites", "ql_ack", "ACK", ordernumber, "", result, datetosend);
			
			
		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger.error("Generación de XML de rechazo via JAXB fallo. " + e2.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	
	public void handleMessage(String headervalues, String content, javax.ejb.MessageDrivenContext ctx) throws OperationFailedException, NotFoundException {
		
		UserTransaction usertransaction = null;
		String messagetype = "";
		Long numeroOrdenGlob = 0L;
		Long dvrordernumber = null;
		//Node orderNode = null;
		NodeList nodeList = null;
		LocalDateTime now = LocalDateTime.now();
		//Orden orderParser = null;
		//Long numeroOrden= 0L;
		//String retailerCode ="";
		
		try{
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setNamespaceAware(true);
				DocumentBuilder db = dbf.newDocumentBuilder();
				StringReader reader = new StringReader(content);
				InputSource inputSource = new InputSource(reader);

				Document doc = db.parse(inputSource);
				reader.close();

				nodeList = doc.getElementsByTagName("orden");
			} catch (Exception e) {
				throw new OperationFailedException(e.getMessage());
			}

			if (nodeList == null || nodeList.getLength() == 0){
				throw new JAXBException("No se puede encontrar el Nodo 'orden' dentro del XML.");
			}

			//orderNode = nodeList.item(0);

			JAXBContext jc = getJC();
			Unmarshaller u = jc.createUnmarshaller();
			XMLReader reader = XMLReaderFactory.createXMLReader();
			NamespaceFilter inFilter = new NamespaceFilter(null, false);
			inFilter.setParent(reader);
			InputSource is = new InputSource(new StringReader(content));	
			SAXSource source = new SAXSource(inFilter, is);
			Orden orderParser = (Orden) u.unmarshal(source);
			
			
			// Obtiene numero de orden
			Long ocnumber = null;
			try {
				ocnumber = orderParser.getEncabezado().getNumOc();
			} catch (Exception e) {
				logger.error("No se puede obtener numero de orden!");
				throw new LoadDataException("No se puede obtener numero de orden. orden: nro_orden");
			}
			numeroOrdenGlob = ocnumber;
				
			logger.info("Inicio de carga de Orden: " + numeroOrdenGlob);	


			// Comienzo con transacion
			usertransaction = ctx.getUserTransaction();
			usertransaction.begin();

			// Se procesa el mensaje
			dvrordernumber = xmltoorder.processMessageOrder(orderParser);

			// Commit de la transacion
			usertransaction.commit();

			// El mensaje fue procesado exitosmente
			// Identifica el tipo de mensaje

			String messageType ="";
			String messageAck = "";
			
			// La acción a nivel de orden sea ‘CREAR’.
			if(orderParser.getEncabezado().getAccion().equalsIgnoreCase(ACCION_CREAR)) {
				logger.info("Orden: " + dvrordernumber + " cargada");
				messageType = "OC";
				messageAck = "La OC " + dvrordernumber + " se cargó correctamente";
			}
			
			// La acción a nivel de orden sea ‘MODIFICAR’’.
			else if(orderParser.getEncabezado().getAccion().equalsIgnoreCase(ACCION_MODIFICAR)) {
				logger.info("Orden: " + dvrordernumber + " modificada");
				messageType = "MO";
				messageAck = "La OC " + dvrordernumber + " se modificó exitosamente";
			}
			
			// La acción a nivel de orden sea ‘CANCELAR’.
			else if(orderParser.getEncabezado().getAccion().equalsIgnoreCase(ACCION_CANCELAR)) {
				logger.info("Orden: " + dvrordernumber + " cancelada");
				messageType = "CA";
				messageAck = "La OC " + dvrordernumber + " se canceló exitosamente";
			}
			
			
			// RESPALDA MENSAJE			
			doBackUpMessage(content, numeroOrdenGlob.toString(), messageType);

			try {
				// Obteniendo proveedor
				String vendorcode = orderParser.getEncabezado().getProveedor().getCodigo();
				VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", vendorcode);
				if (vendorArr != null && vendorArr.length > 0) {
					// Agrega notificaciones
					// La acción a nivel de orden sea ‘CREAR’’.
					if(orderParser.getEncabezado().getAccion().equalsIgnoreCase(ACCION_CREAR)) {
						notificationReport.doAddNotificationData(vendorArr[0].getId(), numeroOrdenGlob.toString(), "CARGA_OC");
					}
					
					// La acción a nivel de orden sea ‘MODIFICAR’’.
					else if(orderParser.getEncabezado().getAccion().equalsIgnoreCase(ACCION_MODIFICAR)) {
						notificationReport.doAddNotificationData(vendorArr[0].getId(), numeroOrdenGlob.toString(), "MODIFICA_OC");
					}
					
					// La acción a nivel de orden sea ‘CANCELAR’.
					else if(orderParser.getEncabezado().getAccion().equalsIgnoreCase(ACCION_CANCELAR)) {
						notificationReport.doAddNotificationData(vendorArr[0].getId(), numeroOrdenGlob.toString(), "CANCELA_OC");
					}
				}
			
			} catch(Exception e){
				e.printStackTrace();
			}
			
			// Se reporta el estado de la transacción
			doSendACK(dvrordernumber.toString(), 0, messageType, messageAck, now);			
			
		} catch(JAXBException e){
			logger.error(e.getMessage());
			e.printStackTrace();

			// Rollback de la transacion
			rollback(usertransaction);
			
			// Numero orden
			String orderNumber ="";
			String[] orderSp1 = null;
			String[] orderSp2 = null;
			try {
				// Se obtiene de igual forma el número de la orden
				orderSp1 = content.split("<num_oc>");
				if (orderSp1.length <= 0) {
					throw new Exception();
				}
				orderSp2 = orderSp1[1].split("</num_oc>");
				orderNumber = orderSp2[0].trim();
			} catch (Exception e2) {
				orderNumber = "NO IDENTIFICADO";
			}

			// Codigo de sociedad
//			String retailercode = "";
//			String[] buyerSp = null;
//			String[] buyerSp2 = null;
//			try {
//				// Se obtiene de igual forma el código del buyer
//				buyerSp = content.split("<urn2:CompanyID xmlns:urn2=\"urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2\">");
//				buyerSp2 = buyerSp[1].split("</urn2:CompanyID>");
//				retailercode = buyerSp2[0].trim();
//				logger.error("Error en XML, código de buyer: " + retailercode);
//			} catch (Exception e2) {
//				retailercode = "NO IDENTIFICADO";
//			}
//			
			// Tipo de Mensaje
			String messageType = "";
			String[] msgType1 = null;
			String[] msgType2 = null;
			
			String messageAck = "";		
			
			try {
				// Se obtiene el tipo de mensaje
				msgType1 = content.split("<accion>");
				if (msgType1.length <= 0) {
					throw new Exception();
				}
				msgType2 = msgType1[1].split("</accion>");
				messageType = msgType2[0].trim();
				
			} catch (Exception e3) {
				messageType = "N/A";
			}
			
			// La acción a nivel de orden sea ‘CREAR’.
			if(messageType.equalsIgnoreCase(ACCION_CREAR)) {
				messageType = "OC";
				messageAck = "El mensaje no contiene el formato de una orden de compra para carga";
			}
			
			// La acción a nivel de orden sea ‘MODIFICAR’’.
			else if(messageType.equalsIgnoreCase(ACCION_MODIFICAR)) {
				messageType = "MO";
				messageAck = "El mensaje no contiene el formato de una orden de compra para modificación";
			}
			
			// La acción a nivel de orden sea ‘CANCELAR’.
			else if(messageType.equalsIgnoreCase(ACCION_CANCELAR)) {
				messageType = "CA";
				messageAck = "El mensaje no contiene el formato de una orden de compra para cancelación";
			}
			// Tipo de mensaje no corresponde a los tipo válidos
			else {
				messageType = "N/A";
				messageAck = "El mensaje no contiene el formato de una orden de compra";
			}
			
			// Respalda mensaje
			doBackUpMessage(content, orderNumber, messageType);

			// Envia mensaje de error
			doSendACK(orderNumber, 1, messageType, messageAck, now);

		} catch(LoadDataException e){

			logger.error("Error en la carga");
			// Rollback de la transacion
			rollback(usertransaction);

			logger.error(e.getMessage());

			// Numero orden
			String orderNumber ="";
			String[] orderSp1 = null;
			String[] orderSp2 = null;
			try {
				// Se obtiene de igual forma el número de la orden
				orderSp1 = content.split("<num_oc>");
				if (orderSp1.length <= 0) {
					throw new Exception();
				}
				orderSp2 = orderSp1[1].split("</num_oc>");
				orderNumber = orderSp2[0].trim();
			} catch (Exception e2) {
				orderNumber = "NO IDENTIFICADO";
			}
			
			// Identifica tipo de mensaje
			// Tipo de Mensaje
			String messageType = "";
			String[] msgType1 = null;
			String[] msgType2 = null;
			
			try {
				// Se obtiene el tipo de mensaje
				msgType1 = content.split("<accion>");
				if (msgType1.length <= 0) {
					throw new Exception();
				}
				msgType2 = msgType1[1].split("</accion>");
				messageType = msgType2[0].trim();
				
			} catch (Exception e3) {
				messageType = "N/A";
			}
			

			// La acción a nivel de orden sea ‘CREAR’.
			if(messageType.equalsIgnoreCase(ACCION_CREAR)) {
				messageType = "OC";
			}
			
			// La acción a nivel de orden sea ‘MODIFICAR’’.
			else if(messageType.equalsIgnoreCase(ACCION_MODIFICAR)) {
				messageType = "MO";
			}
			
			// La acción a nivel de orden sea ‘CANCELAR’.
			else if(messageType.equalsIgnoreCase(ACCION_CANCELAR)) {
				messageType = "CA";
			}
			// Tipo de mensaje no corresponde a los tipo válidos
			else {
				messageType = "N/A";
			}

			// Respalda mensaje
			doBackUpMessage(content, orderNumber, messageType);
			
			// Se genera mensaje de rechazo
			doSendACK(numeroOrdenGlob.toString(), 1, messageType, e.getMessage(), now);
			
		} catch(Exception ex){

			ex.printStackTrace();

			logger.error("Error ");
			
			// Rollback de la transacion
			rollback(usertransaction);

			// RECOVERY
			MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();			
			
			// Numero orden
			String orderNumber ="";
			String[] orderSp1 = null;
			String[] orderSp2 = null;
			try {
				// Se obtiene de igual forma el número de la orden
				orderSp1 = content.split("<num_oc>");
				if (orderSp1.length <= 0)
					throw new Exception();
				orderSp2 = orderSp1[1].split("</num_oc>");
				orderNumber = orderSp2[0].trim();
			} catch (Exception e2) {
				orderNumber = "NO IDENTIFICADO";
			}
			
			// Identifica tipo de mensaje
			// Tipo de Mensaje
			String messageType = "";
			String[] msgType1 = null;
			String[] msgType2 = null;
			
			try {
				// Se obtiene el tipo de mensaje
				msgType1 = content.split("<accion>");
				if (msgType1.length <= 0) {
					throw new Exception();
				}
				msgType2 = msgType1[1].split("</accion>");
				messageType = msgType2[0].trim();
				
			} catch (Exception e3) {
				messageType = "N/A";
			}
			

			// La acción a nivel de orden sea ‘CREAR’.
			if(messageType.equalsIgnoreCase(ACCION_CREAR)) {
				messageType = "OC";
			}
			
			// La acción a nivel de orden sea ‘MODIFICAR’’.
			else if(messageType.equalsIgnoreCase(ACCION_MODIFICAR)) {
				messageType = "MO";
			}
			
			// La acción a nivel de orden sea ‘CANCELAR’.
			else if(messageType.equalsIgnoreCase(ACCION_CANCELAR)) {
				messageType = "CA";
			}
			// Tipo de mensaje no corresponde a los tipo válidos
			else {
				messageType = "N/A";
			}

			// Respalda mensaje
			doBackUpMessage(content, orderNumber, messageType);

			messagetype = LogisticConstants.getBUSINESSAREA() + LogisticConstants.getCOUNTRYCODE() + "_ORDEN_" + orderNumber;
			try {
				msgRecoveryServices.saveMsgToFile(messagetype, content, ex);
			} catch (Exception e) {
				logger.error("Error en Recovery de OC Nro " + orderNumber);
				logger.error(e.getMessage());
			}
		}
}

	private void rollback(UserTransaction usertransaction) {
		try {
			if (usertransaction != null &&
					(usertransaction.getStatus() == Status.STATUS_ACTIVE ||
							usertransaction.getStatus() == Status.STATUS_MARKED_ROLLBACK))
				usertransaction.rollback();
		} catch (SystemException | SecurityException | IllegalStateException e1) {
			e1.printStackTrace();
		}
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
	

	private String normalize(String text){		
		//Sustituyendo acentos, ñ, diéresis, ..
		String[] oldCaracters = 		{"Á", "É", "Í", "Ó", "Ú", "Ñ", "Ü", "À", "È", "Ì", "Ò", "Ù", "á", "é", "í", "ó", "ú", "ü", "à", "è" , "ì", "ò", "ù"};
		String[] newCaracters = 		{"A", "E", "I", "O", "U", "N", "U", "A", "E", "I", "O", "U", "a", "e", "i", "o", "u", "u", "a", "e" , "i", "o", "u"};
		for (int i = 0; i < oldCaracters.length; i++) {
			text = text.replace(oldCaracters[i], newCaracters[i]);
		}

		return text;
	}
	

}
