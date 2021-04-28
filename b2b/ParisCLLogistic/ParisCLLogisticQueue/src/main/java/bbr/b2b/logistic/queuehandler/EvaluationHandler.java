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
import bbr.b2b.logistic.msgregionalb2b.XmlToEvaluationLocal;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.utils.BackUpUtils;
import bbr.b2b.regional.logistic.utils.MsgRecoveryServices;
import bbr.b2b.regional.logistic.utils.QSender;
import bbr.b2b.regional.logistic.xml.qack.ACK;
import bbr.b2b.regional.logistic.xml.qevaluation.QEVALUACION;

@Stateless(name = "handlers/EvaluationHandler")
@TransactionManagement(TransactionManagementType.BEAN)
public class EvaluationHandler implements EvaluationHandlerLocal{

	private static Logger logger = Logger.getLogger("CargaMsgesLog");
	private static Logger logger_ack = Logger.getLogger("LogNotificacion");
		
	@EJB
	XmlToEvaluationLocal xmltoevaluation;

	private static JAXBContext jc = null;
	private static JAXBContext jAck = null;

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.qevaluation");
		return jc;
	}	
	
	private static JAXBContext getACKJC() throws JAXBException {
		if (jAck == null)
			jAck = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.qack");
		return jAck;
	}
	
	private void doSendResponse(Integer state, String exception, String date, String content){
		
		String transactionNumber = null;
				
		String[] strArray1 = null;
		String[] strArray2 = null; 
				
		try {
			// TRANSNBR: N°MERO DE TRANSACción
			strArray1 = content.split("<TransNbr>");
			if (strArray1.length <= 1){
				transactionNumber = "XXXXXXXXX";
			}else{
				strArray2 = strArray1[1].split("</TransNbr>");
				transactionNumber = strArray2[0].trim();
			}		
			
			JAXBContext jc = getACKJC();
			bbr.b2b.regional.logistic.xml.qack.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.qack.ObjectFactory();
			ACK notification = objFactory.createACK();
			notification.setA0(transactionNumber);
			notification.setA1("EV");
			notification.setA2("C001");
			notification.setA3("PARIS");
			notification.setA4(RegionalLogisticConstants.getInstance().getCOUNTRYCODE());
			notification.setA5(state.toString());
			notification.setA6(date + normalize(exception));
			
			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(notification, sw);
			String result = sw.toString();
			
			// Respaldar el mensaje		
			doBackUpMessage(result, transactionNumber, "ACK");
						
			String info = state == 0 ? "EXITO" : "FRACASO";
			
			// Se registra el resultado de carga de mensajes en un log particular
			logger_ack.info(",\"" + "EV" + "\",\"" + transactionNumber + "\",\"" + info + "\",\"" + exception + "\"");
	
			// Se envía mensaje de ACK a la cola
			try {			
				QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_ackb2b", result);				
			} catch (Exception ex) {
				// Si ocurrió un error al enviar el archivo, se graba el mensaje para reencolarlo
				MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
				String msgtype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_ACK_" + transactionNumber;
				try {
					msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
				} catch (Exception e1) {
					logger.debug(e1.getLocalizedMessage());
				}
			}			
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
		Long transactionNumber = 0L;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String now = "[" + sdf.format(new Date()) + "] ";
		
		try {	
			JAXBContext jc = getJC();
			Unmarshaller u = jc.createUnmarshaller();
			InputSource source = new InputSource(new StringReader(content));
			QEVALUACION evaluationParser = (QEVALUACION) u.unmarshal(source);
		
			// Obtener el N°mero de transacción
			try {
				transactionNumber = new Long(evaluationParser.getTransNbr());				
			} catch (Exception e) {
				throw new LoadDataException("QEVALUACION: No se puede obtener el numero de transacción");
			}
			
			logger.info("Inicio de carga de Evaluación de Recepción, Transacción: " + transactionNumber);	

			// Respaldar el mensaje			
			doBackUpMessage(content, transactionNumber.toString(), "EV");

			// Comenzar con la transacción
			usertransaction = ctx.getUserTransaction();			
			usertransaction.begin();

			// Procesar el mensaje
			transactionNumber = xmltoevaluation.processMessage(evaluationParser);

			// Ejecución de la transacción
			usertransaction.commit();
			logger.info("Evaluación de Recepción cargada, Transacción: " + transactionNumber);
			
			// Se reporta el estado de la transacción
			doSendResponse(0, "La Evaluación de Recepción se carg� correctamente", now, content);

		} catch (JAXBException e) {
			logger.error(e.getMessage());
			e.printStackTrace();

			// Rollback de la transacción
			rollback(usertransaction);

			// Respaldar el mensaje
			doBackUpMessage(content, "XXXXXXXXX", "EV");
			
			// Envia mensaje de error
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
			MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
			String numberStr = "";
			try {
				String[] contentSp = content.split("<TransNbr>");
				numberStr = contentSp[1].substring(0, contentSp[1].indexOf("</TransNbr>"));
			} catch (Exception e) {
				numberStr = "0";
			}

			messagetype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_EVALUACION_" + numberStr;
			try {
				msgRecoveryServices.saveMsgToFile(messagetype, content, ex);
			} catch (Exception e) {
				logger.error("Error en Recovery de Evaluación de Recepción, Transacción: " + numberStr);
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
