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
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.msgb2b.XmlToReceptionLocal;
import bbr.b2b.logistic.notifications.managers.interfaces.NotificationsReportManagerLocal;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.utils.BackUpUtils;
import bbr.b2b.logistic.utils.MsgRecoveryServices;
import bbr.b2b.logistic.utils.NamespaceFilter;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.logistic.xml.ack.ACK.Motivo;
import bbr.b2b.logistic.xml.reception.Recepcion;

@Stateless(name = "handlers/ReceptionHandler")
@TransactionManagement(TransactionManagementType.BEAN)
public class ReceptionHandler implements ReceptionHandlerLocal {

	private static final String SEPARATE_WITH_COMMA = "\",\"";
	
	private static Logger logger = Logger.getLogger(ReceptionHandler.class);
	private static Logger loggerAck = Logger.getLogger("LogNotificacion");

	@Resource
	private ManagedExecutorService executor;

	@EJB
	XmlToReceptionLocal xmltoreception;
	
	@EJB
	SchedulerManagerLocal schedulermanager;
	
	@EJB
	NotificationsReportManagerLocal notificationReport;
	
	@EJB
	VendorServerLocal vendorserver;
	
	private static JAXBContext jc = null;

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.logistic.xml.reception");
		return jc;
	}	
	
	
	private static JAXBContext jAck = null;

	private static JAXBContext getACKJC() throws JAXBException {
		if (jAck == null)
			jAck = JAXBContext.newInstance("bbr.b2b.logistic.xml.ack");
		return jAck;
	}
	
	
	private void doSendACK(String number, Integer state, String messagetype, String message, LocalDateTime datetosend){
		
		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
			String now = LocalDateTime.now().format(dateTimeFormatter);
			
			// Envía respuesta
			JAXBContext jclocal = getACKJC();
			bbr.b2b.logistic.xml.ack.ObjectFactory objFactory = new bbr.b2b.logistic.xml.ack.ObjectFactory();
			bbr.b2b.logistic.xml.ack.ACK ackMSg = objFactory.createACK();

			ackMSg.setIdentificador(number);
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
			doBackUpMessage(result, number, "ACK");

			// Se registra el resultado de carga de mensajes en log de ACK
			loggerAck.info(",\"" + messagetype + SEPARATE_WITH_COMMA + number + SEPARATE_WITH_COMMA + state + SEPARATE_WITH_COMMA + message + "\"");

			// Se agrega mensaje a tabla de pending message
			schedulermanager.doAddMessageQueue("qcf_hites", "ql_ack", "ACK", number, "", result, datetosend);			
			
		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger.error("Generación de XML de rechazo via JAXB fallo. " + e2.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	public void handleMessage(String headervalues, String content, javax.ejb.MessageDrivenContext ctx) throws OperationFailedException, NotFoundException {
		
		UserTransaction usertransaction = null;
		String asnnumberFromXml = null;
		NodeList nodeList = null;
		LocalDateTime now = LocalDateTime.now();

		try {
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setNamespaceAware(true);
				DocumentBuilder db = dbf.newDocumentBuilder();
				StringReader reader = new StringReader(content);
				InputSource inputSource = new InputSource(reader);

				Document doc = db.parse(inputSource);
				reader.close();

				nodeList = doc.getElementsByTagName("recepcion");
			} catch (Exception e) {
				throw new OperationFailedException(e.getMessage());
			}

			if (nodeList == null || nodeList.getLength() == 0){
				throw new JAXBException("No se puede encontrar el Nodo 'recepcion' dentro del XML.");
			}
			
			JAXBContext jc = getJC();
			Unmarshaller u = jc.createUnmarshaller();
			XMLReader reader = XMLReaderFactory.createXMLReader();
			NamespaceFilter inFilter = new NamespaceFilter(null, false);
			inFilter.setParent(reader);
			InputSource is = new InputSource(new StringReader(content));	
			SAXSource source = new SAXSource(inFilter, is);
			Recepcion receptionParser = (Recepcion) u.unmarshal(source);
			
			// Obtiene numero del ASN asociado
			String asnnumber = null;
			try {
				asnnumber = receptionParser.getNumAsn();
			} catch (Exception e) {
				logger.error("No se puede obtener numero de asn!");
				throw new LoadDataException("No se puede obtener numero de asn");
			}
			
			logger.info("Inicio de carga de Recepción con ASN Nro: " + asnnumber);	
			
			// Comienzo con transacion
			usertransaction = ctx.getUserTransaction();
			usertransaction.begin();
			
			// Se procesa el mensaje
			asnnumberFromXml = xmltoreception.processMessageOrder(receptionParser);
			
			// Commit de la transacion
			usertransaction.commit();
			
			logger.info("Recepción con asnnumber " + asnnumberFromXml + " cargada");
			
			// RESPALDA MENSAJE			
			doBackUpMessage(content, asnnumberFromXml.toString(), "RE");
			
			try {
				// Obteniendo proveedor
				VendorW vendorW = vendorserver.getVendorByAsnNumber(receptionParser.getNumAsn());
				
				if (vendorW != null) {
					// Agrega notificación
					notificationReport.doAddNotificationData(vendorW.getId(), asnnumberFromXml, "RECEPCION");
				}
				
			} catch(Exception e){
				e.printStackTrace();
			}		
			
			// Se reporta el estado de la transacción
			doSendACK(asnnumberFromXml.toString(), 0, "RE", "Recepción cargada correctamente", now);
			
		} catch (JAXBException e) {
			logger.error(e.getMessage());
			e.printStackTrace();

			// Rollback de la transacion
			rollback(usertransaction);
			
			// Numero de ASN
			String asnNumber ="";
			String[] asnSp1 = null;
			String[] asnSp2 = null;
			try {
				// Se obtiene de igual forma el número de la orden
				asnSp1 = content.split("<num_asn>");
				if (asnSp1.length <= 0) {
					throw new Exception();
				}
				asnSp2 = asnSp1[1].split("</num_asn>");
				asnNumber = asnSp2[0].trim();
			} catch (Exception e2) {
				asnNumber = "NO IDENTIFICADO";
			}
			
			// Respalda mensaje
			doBackUpMessage(content, asnNumber, "RE");

			// Envia mensaje de error
			doSendACK(asnNumber, 1, "RE", "El mensaje no contiene el formato de una recepción", now);
		
			
		} catch(LoadDataException e){
			
			logger.error("Error en la carga");
			// Rollback de la transacion
			rollback(usertransaction);

			logger.error(e.getMessage());

			// Numero de ASN
			String asnNumber ="";
			String[] asnSp1 = null;
			String[] asnSp2 = null;
			try {
				// Se obtiene de igual forma el número de la orden
				asnSp1 = content.split("<num_asn>");
				if (asnSp1.length <= 0) {
					throw new Exception();
				}
				asnSp2 = asnSp1[1].split("</num_asn>");
				asnNumber = asnSp2[0].trim();
			} catch (Exception e2) {
				asnNumber = "NO IDENTIFICADO";
			}
			
			// Respalda mensaje
			doBackUpMessage(content, asnNumber, "RE");
			
			// Se genera mensaje de rechazo
			doSendACK(asnNumber.toString(), 1, "RE", e.getMessage(), now);

			
			
		} catch(Exception ex){
			ex.printStackTrace();

			logger.error("Error ");
			
			// Rollback de la transacion
			rollback(usertransaction);

			// RECOVERY
			MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();			
			
			// Numero de ASN
			String asnNumber ="";
			String[] asnSp1 = null;
			String[] asnSp2 = null;
			try {
				// Se obtiene de igual forma el número de la orden
				asnSp1 = content.split("<num_asn>");
				if (asnSp1.length <= 0) {
					throw new Exception();
				}
				asnSp2 = asnSp1[1].split("</num_asn>");
				asnNumber = asnSp2[0].trim();
			} catch (Exception e2) {
				asnNumber = "NO IDENTIFICADO";
			}			

			// Respalda mensaje
			doBackUpMessage(content, asnNumber, "RE");

			
			String messagetypeToRecovery = LogisticConstants.getBUSINESSAREA() + LogisticConstants.getCOUNTRYCODE() + "_RECEPCION_" + asnNumber;
			try {
				msgRecoveryServices.saveMsgToFile(messagetypeToRecovery, content, ex);
			} catch (Exception e) {
				logger.error("Error en Recovery de RECEPCION ASN Nro " + asnNumber);
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
	
}
