package bbr.b2b.logistic.queuehandler;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.msgregionalb2b.XmlToPLImportLocal;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.utils.BackUpUtils;
import bbr.b2b.regional.logistic.utils.MsgRecoveryServices;
import bbr.b2b.regional.logistic.xml.qplimport.QPLIMPORTADOREQ;
import bbr.b2b.regional.logistic.xml.qplimportack.QPLIMPORTADORESP;

@Stateless(name = "handlers/PLImportHandler")
@TransactionManagement(TransactionManagementType.BEAN)
public class PLImportHandler implements PLImportHandlerLocal{

	private static Logger logger = Logger.getLogger("CargaMsgesLog");
	private static Logger logger_ack = Logger.getLogger("LogNotificacion");
		
	@EJB
	XmlToPLImportLocal xmltoplimport;

	private static JAXBContext jc = null;
	private static JAXBContext jAck = null;

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.qplimport");
		return jc;
	}	
	
	private static JAXBContext getACKJC() throws JAXBException {
		if (jAck == null)
			jAck = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.qplimportack");
		return jAck;
	}
	
	private void doSendResponse(Integer state, String exception, String date, String content)  {
		
		String sequenceNumber = null;
		String importGuide = null;
		String orderNumber = null;
		String guideNumber = null;
		String containerNumber = null;
		
		String[] strArray1 = null;
		String[] strArray2 = null; 
				
		try {
			// SE: N°MERO DE SECUENCIA
			strArray1 = content.split("<SE>");
			if (strArray1.length <= 1){
				sequenceNumber = "XXXXXXXXXXXX";
			}else{
				strArray2 = strArray1[1].split("</SE>");
				sequenceNumber = strArray2[0].trim();
			}		
			
			// IM: N°MERO GU�A IMPORTAción
			strArray1 = strArray2[1].split("<IM>");
			if (strArray1.length <= 1){
				importGuide = "XXXXXXXXXXX";
			}else{
				strArray2 = strArray1[1].split("</IM>");
				importGuide = strArray2[0].trim();
			}	
			
			// OC: N°MERO DE ORDEN
			strArray1 = strArray2[1].split("<OC>");
			if (strArray1.length <= 1){
				orderNumber = "XXXXXX";
			}else{
				strArray2 = strArray1[1].split("</OC>");
				orderNumber = strArray2[0].trim();
			}	
			
			// GD: N°MERO DE GU�A
			strArray1 = strArray2[1].split("<GD>");
			if (strArray1.length <= 1){
				guideNumber = "XXXXXXXXXX";
			}else{
				strArray2 = strArray1[1].split("</GD>");
				guideNumber = strArray2[0].trim();
			}	
			
			// NC: N°MERO DE CONTENEDOR
			strArray1 = strArray2[1].split("<NC>");
			if (strArray1.length <= 1){
				containerNumber = "XXXXXXXXXXXXXXXXXXXX";
			}else{
				strArray2 = strArray1[1].split("</NC>");
				containerNumber = strArray2[0].trim();
			}	
			
			JAXBContext jc = getACKJC();
			bbr.b2b.regional.logistic.xml.qplimportack.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.qplimportack.ObjectFactory();
			QPLIMPORTADORESP notification = objFactory.createQPLIMPORTADORESP();
			
			notification.setSE(new Long(sequenceNumber));
			notification.setIM(importGuide);
			notification.setOC(new Long(orderNumber));
			notification.setGD(new Long(guideNumber));
			notification.setNC(containerNumber);
			notification.setES(state == 0 ? "Y" : "N");
			notification.setMO(state == 0 ? "" : normalize(exception));
			
			// Obtener la cadena XML para enviarla a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(notification, sw);
			String result = sw.toString();
			
			// Respaldar el mensaje		
			doBackUpMessage(result, importGuide, "PLIMPRESP");
						
			String info = state == 0 ? "EXITO" : "FRACASO";
			
			// Registrar el resultado de la carga de mensajes en un log particular
			logger_ack.info(",\"" + "PLIMP" + "\",\"" + importGuide + "\",\"" + info + "\",\"" + exception + "\"");
	
			// Enviar mensaje de PLIMPRESP a la cola
//			try {			
//				QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_plimprespb2b", result);				
//			} catch (Exception ex) {
//				// Si ocurrió un error al enviar el archivo, se graba el mensaje para reencolarlo
//				MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
//				String msgtype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_PLIMPRESP_" + importGuide;
//				try {
//					msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
//				} catch (Exception e1) {
//					logger.debug(e1.getLocalizedMessage());
//				}
//			}			
		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger.error("Generación de XML de rechazo via JAXB fallo. " + e2.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void handleMessage(String content, javax.ejb.MessageDrivenContext ctx) throws OperationFailedException, NotFoundException {

		UserTransaction usertransaction = null;
		String messagetype = "";
		String container = "";
		long ordernumber;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String now = "[" + sdf.format(new Date()) + "] ";
		
		try {	
			JAXBContext jc = getJC();
			Unmarshaller u = jc.createUnmarshaller();
			InputSource source = new InputSource(new StringReader(content));
			QPLIMPORTADOREQ plimportParser = (QPLIMPORTADOREQ) u.unmarshal(source);
		
			// Obtiene el contenedor
			try {
				container = plimportParser.getNC();				
			} catch (Exception e) {
				throw new LoadDataException("QPLIMPORTADOREQ: No se puede obtener el N°mero de contenedor");
			}
			
			// Obtiene el N°mero de orden
			try {
				ordernumber = plimportParser.getOC();				
			} catch (Exception e) {
				throw new LoadDataException("QPLIMPORTADOREQ: No se puede obtener el N°mero de orden");
			}
			
			logger.info("Inicio de carga del Packing List Internacional (Contenedor " + container + " - OC " + ordernumber + ")");	

			// Respaldar el mensaje			
			doBackUpMessage(content, container + "_" + ordernumber, "PLIMP");

			// Comenzar con la transacción
			usertransaction = ctx.getUserTransaction();		
			usertransaction.begin();

			// Se procesa el mensaje
			xmltoplimport.processMessage(plimportParser);

			// Ejecución de la transacción
			usertransaction.commit();
			logger.info("Packing List Internacional cargado (Contenedor " + container + " - OC " + ordernumber + ")");
						
			// Se reporta el estado de la transacción
			doSendResponse(0, "El Packing List Internacional se carg� correctamente (Contenedor " + container + " - OC " + ordernumber + ")", now, content);

		} catch (JAXBException e) {
			logger.error(e.getMessage());
			e.printStackTrace();

			// Rollback de la transacción
			rollback(usertransaction);

			// Respaldar el mensaje
			doBackUpMessage(content, "XXXXXXXXXXX", "PLIMP");
			
			// Enviar mensaje de error
			doSendResponse(1, "La estructura del mensaje no es v�lida", now, content);

		} catch (LoadDataException e) {
			logger.error("Error en la carga");
			
			// Rollback de la transacción
			rollback(usertransaction);
			
			logger.error(e.getMessage());
			
			// Enviar mensaje de error
			doSendResponse(1, e.getMessage(), now, content);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error ");
			
			// Rollback de la transacción
			rollback(usertransaction);

			// Recuperación
			String containerStr = "";
			try {
				String[] contentSp = content.split("<NC>");
				containerStr = contentSp[1].substring(0, contentSp[1].indexOf("</NC>"));
			} catch (Exception e) {
				containerStr = "XXXXXXXXXXX";
			}
			
			String orderStr = "";
			try {
				String[] contentSp = content.split("<OC>");
				orderStr = contentSp[1].substring(0, contentSp[1].indexOf("</OC>"));
			} catch (Exception e) {
				orderStr = "XXXXXXXXXXX";
			}

			messagetype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_PLIMP_" + containerStr + "_" + orderStr;
			
			MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
			try {
				msgRecoveryServices.saveMsgToFile(messagetype, content, ex);
			} catch (Exception e) {
				logger.error("Error en Recovery de Packing List Internacional, Contenedor " + containerStr + " asociado a la orden " + orderStr);
				logger.error(e.getMessage());
			}
		}
	}

	private void rollback(UserTransaction usertransaction) {
		try {
			if (usertransaction != null && usertransaction.getStatus() == Status.STATUS_ACTIVE)
				usertransaction.rollback();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
	}
	
	private String normalize(String text){		
		//Sustituyendo acentos, �, di�resis, ...
		String[] oldCaracters = 		{"Á", "É", "Í", "Ó", "Ú", "Ñ", "Ü", "À", "È", "Ì", "Ò", "Ù", "á", "é", "í", "ó", "ú", "ü", "à", "è" , "ì", "ò", "ù"};
		String[] newCaracters = 		{"A", "E", "I", "O", "U", "N", "U", "A", "E", "I", "O", "U", "a", "e", "i", "o", "u", "u", "a", "e" , "i", "o", "u"};
		for (int i = 0; i < oldCaracters.length; i++) {
			text = text.replace(oldCaracters[i], newCaracters[i]);
		}

		return text;
	}
	
	@Resource
	private ManagedExecutorService executor;

	private void doBackUpMessage(String content, String number, String msgType){
		
		// EJECUTA UNA TAREA QUE RESPALDA EL MENSAJE.
		// ESTA ES INDEPENDIENTE DE LA CARGA DEL MENSAJE.
		try{
			executor.submit(new BackUpUtils(content, number, msgType));			
		}catch (RejectedExecutionException e) {
			e.printStackTrace();
		}			
	}	
}


