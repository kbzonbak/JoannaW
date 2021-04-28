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
import bbr.b2b.logistic.msgregionalb2b.XmlToTaxDocumentRMLocal;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.regional.logistic.utils.BackUpUtils;
import bbr.b2b.regional.logistic.utils.MsgRecoveryServices;
import bbr.b2b.regional.logistic.xml.qack.ACK;
import bbr.b2b.regional.logistic.xml.qrm.QRM;

@Stateless(name = "handlers/TaxDocumentRMHandler")
@TransactionManagement(TransactionManagementType.BEAN)
public class TaxDocumentRMHandler implements TaxDocumentRMHandlerLocal {

	private static Logger logger = Logger.getLogger("CargaMsgesLog");

	private static Logger logger_ack = Logger.getLogger("LogNotificacion");

	@EJB
	XmlToTaxDocumentRMLocal xmltotaxdocumentrm;
	
	@EJB
	SchedulerManagerLocal schedulermanager;

	private static JAXBContext jc = null;

	private static JAXBContext jAck = null;

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.qrm");
		return jc;
	}

	private static JAXBContext getACKJC() throws JAXBException {
		if (jAck == null)
			jAck = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.qack");
		return jAck;
	}

	private void doSendResponse(Long asn, String rm, String messagetype, Integer state, String exception, String date, String content) {
		String asnStr = null;
		String rmStr = null;

		try {
			String[] numberSp1 = null;
			String[] numberSp2 = null;

			if (asn.equals(-1L)) {
				numberSp1 = content.split("<nasn>");
				if (numberSp1.length <= 1) {
					asnStr = "ERROR";
				} else {
					numberSp2 = numberSp1[1].split("</nasn>");
					asnStr = numberSp2[0].trim();
				}
			}
			else {
				asnStr = String.valueOf(asn);
			}
			
			if (rm.equals("-1L")) {
				numberSp1 = content.split("<rm>");
				if (numberSp1.length <= 1) {
					rmStr = "ERROR";
				} else {
					numberSp2 = numberSp1[1].split("</rm>");
					rmStr = numberSp2[0].trim();
				}
			}
			else {
				rmStr = rm;
			}

			JAXBContext jc = getACKJC();
			bbr.b2b.regional.logistic.xml.qack.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.qack.ObjectFactory();
			ACK notification = objFactory.createACK();
			notification.setA0(rmStr);
			notification.setA1(messagetype);
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

			// RESPALDA MENSAJE
			doBackUpMessage(result, rmStr + "_" + asnStr, "ACK_RM");

			String info = state == 0 ? "EXITO" : "FRACASO";

			// Se registra el resultado de carga de mensajes en un log particular
			logger_ack.info(",\"" + messagetype + "\",\"" + rmStr + "\",\"" + info + "\",\"" + exception + "\"");

			// Se planifica el envío de mensaje de ACK a la cola
			schedulermanager.doAddMessageQueue("jboss/qcf_pariscl", "jboss/wsmq/q_ackb2b" , "ACK", rmStr + "_" + asnStr, result);
			
		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger.error("Generación de XML de rechazo via JAXB fallo. " + e2.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleMessage(String content, javax.ejb.MessageDrivenContext ctx) throws OperationFailedException, NotFoundException {

		UserTransaction usertransaction = null;
		Long asn = null;
		String rm = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String now = "[" + sdf.format(new Date()) + "] ";

		try {
			JAXBContext jc = getJC();
			Unmarshaller u = jc.createUnmarshaller();
			InputSource source = new InputSource(new StringReader(content));
			QRM rmParser = (QRM) u.unmarshal(source);

			// Obtener el N°mero de ASN
			try {
				asn = rmParser.getNasn();
			} catch (Exception e) {
				throw new LoadDataException("QRM: No se puede obtener el N°mero de ASN");
			}
			
			// Obtener el N°mero de RM asociado a la OC y factura
			try {
				rm = rmParser.getRm();
			} catch (Exception e) {
				throw new LoadDataException("QRM: No se puede obtener el N°mero de RM asociado a la OC y factura");
			}

			logger.info("Inicio de carga de RM: " + rm);

			// Respaldar el mensaje
			doBackUpMessage(content, rm + "_" + asn, "RM");

			// Comenzar con la transacción
			usertransaction = ctx.getUserTransaction();
			usertransaction.begin();

			// Se procesa el mensaje
			xmltotaxdocumentrm.processMessage(rmParser);

			// Commit de la transacción
			usertransaction.commit();

			logger.info("RM " + rm + " cargado");

			// Reportar el estado de la transacción
			doSendResponse(asn, rm, "RM", 0, "La información de RM se carg� exitosamente", now, content);

		} catch (JAXBException e) {
			logger.error(e.getMessage());
			e.printStackTrace();

			// Rollback de la transacción
			rollback(usertransaction);

			// Respaldar el mensaje
			doBackUpMessage(content, "XXX", "RM");

			// Enviar mensaje de error
			doSendResponse(-1L, "-1", "RM", 1, "La estructura del mensaje no es v�lida", now, content);

		} catch (LoadDataException e) {
			logger.error("Error en la carga");
			logger.error(e.getMessage());
			
			// Rollback de la transacción
			rollback(usertransaction);

			// Enviar mensaje de error
			doSendResponse(asn, rm, "RM", 1, e.getMessage(), now, content);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error ");

			// Rollback de la transacción
			rollback(usertransaction);

			// Guardar mensaje para recuperar
			MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
			String rmStr = "";
			try {
				String[] contentSp = content.split("<rm>");
				rmStr = contentSp[1].substring(0, contentSp[1].indexOf("</rm>"));
			} catch (Exception e) {
				rmStr = "ERROR";
			}

			String messagetype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_RM_" + rmStr;
			try {
				msgRecoveryServices.saveMsgToFile(messagetype, content, ex);
			} catch (Exception e) {
				logger.error("Error guardando mensaje RM " + rmStr + " para recuperación");
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

	private String normalize(String text) {
		// Sustituyendo acentos, �, di�resis, ...
		String[] oldCaracters = { "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�" };
		String[] newCaracters = { "A", "E", "I", "O", "U", "N", "U", "A", "E", "I", "O", "U", "a", "e", "i", "o", "u", "u", "a", "e", "i", "o", "u" };
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