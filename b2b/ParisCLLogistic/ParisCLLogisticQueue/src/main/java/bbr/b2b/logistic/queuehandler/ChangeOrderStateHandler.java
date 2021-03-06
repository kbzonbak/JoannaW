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
import bbr.b2b.logistic.msgregionalb2b.XmlToChangeOrderStateLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.utils.BackUpUtils;
import bbr.b2b.regional.logistic.utils.MsgRecoveryServices;
import bbr.b2b.regional.logistic.utils.QSender;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.xml.qack.ACK;
import bbr.b2b.regional.logistic.xml.qchangeorderstate.QCAMBIOESTADO;

@Stateless(name = "handlers/ChangeOrderStateHandler")
@TransactionManagement(TransactionManagementType.BEAN)
public class ChangeOrderStateHandler implements ChangeOrderStateHandlerLocal {

	private static Logger logger = Logger.getLogger("CargaMsgesLog");
	private static Logger logger_ack = Logger.getLogger("LogNotificacion");
		
	@EJB
	XmlToChangeOrderStateLocal xmltoochangestate;

	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	private static JAXBContext jc = null;
	private static JAXBContext jAck = null;

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.qchangeorderstate");
		return jc;
	}	
	
	private static JAXBContext getACKJC() throws JAXBException {
		if (jAck == null)
			jAck = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.qack");
		return jAck;
	}
	
	private void doSendResponse(String number, String messagetype, Integer state, String exception, String date, String content, String correlationid)  {
		String numberStr = null;

		try {
			String[] numberSp1 = null;
			String[] numberSp2 = null;			

			if (number.equals(-1L)){
				// Verificar tipo de mensaje
				if (messagetype.equalsIgnoreCase("OC")){
					numberSp1 = content.split("<A1>");
					if (numberSp1.length <= 1){
						numberStr = "XXX";
					}else{
						numberSp2 = numberSp1[1].split("</A1>");
						numberStr = numberSp2[0].trim();
					}					
				}
				else if (messagetype.equalsIgnoreCase("PR")){
					numberSp1 = content.split("<cod_producto>");
					if (numberSp1.length <= 1){
						numberStr = "XXX";
					}else{
						numberSp2 = numberSp1[1].split("</cod_producto>");
						numberStr = numberSp2[0].trim();
					}	
				}					
				else if (messagetype.equalsIgnoreCase("MO")){
					numberSp1 = content.split("<?xml version=\"1.0\" encoding=\"utf-8\"?><QMODIFICAORDEN><MO1>");
					if (numberSp1.length <= 1){
						numberStr = "XXX";
					}else{
						numberSp2 = numberSp1[1].split("</MO1>");
						numberStr = numberSp2[0].trim();
					}	
				}
				else if (messagetype.equalsIgnoreCase("CE")){
					numberSp1 = content.split("<norden>");
					if (numberSp1.length <= 1){
						numberStr = "XXX";
					}else{
						numberSp2 = numberSp1[1].split("</norden>");
						numberStr = numberSp2[0].trim();
					}	
				}	
				else if (messagetype.equalsIgnoreCase("RE")){
					numberSp1 = content.split("<nrecepcion>");
					if (numberSp1.length <= 1){
						numberStr = "XXX";
					}else{
						numberSp2 = numberSp1[1].split("</nrecepcion>");
						numberStr = numberSp2[0].trim();
					}	
				}
			}	
			else{
				numberStr = String.valueOf(number);
			}

			JAXBContext jc = getACKJC();
			bbr.b2b.regional.logistic.xml.qack.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.qack.ObjectFactory();
			ACK notification = objFactory.createACK();
			notification.setA0(number.toString());
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
			doBackUpMessage(result, numberStr, "ACKCE");
						
			String info = state == 0 ? "EXITO" : "FRACASO";
			
			// Se registra el resultado de carga de mensajes en un log particular
			logger_ack.info(",\"" + messagetype + "\",\"" + numberStr + "\",\"" + info + "\",\"" + exception + "\"");
	
			// Se env??a mensaje de ACK a la cola
			try {			
				QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_ackce", result, correlationid);				
			} catch (Exception ex) {
				// Si ocurri?? un error al enviar el archivo, se graba el mensaje para reencolarlo
				MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
				String msgtype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_ACKCE_" + numberStr;
				try {
					msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
				} catch (Exception e1) {
					logger.debug(e1.getLocalizedMessage());
				}
			}			
		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger.error("Generaci??n de XML de rechazo via JAXB fallo. " + e2.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void handleMessage(String content, String correlationid, javax.ejb.MessageDrivenContext ctx) throws OperationFailedException, NotFoundException {

		UserTransaction usertransaction = null;
		String messagetype = "";
		Long orderNumber = 0L;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String now = "[" + sdf.format(new Date()) + "] ";
			
		try {	
			
			JAXBContext jc = getJC();
			Unmarshaller u = jc.createUnmarshaller();
			InputSource source = new InputSource(new StringReader(content));
			QCAMBIOESTADO changestateParser = (QCAMBIOESTADO) u.unmarshal(source);
		
			// Obtiene el numero de orden
			try {
				orderNumber = changestateParser.getNorden();				
			} catch (Exception e) {
				throw new LoadDataException("QCAMBIOESTADO: No se puede obtener el numero de la orden");
			}
			
			logger.info("Inicio de carga de Cambio estado: " + orderNumber);	

			// RESPALDA MENSAJE			
			doBackUpMessage(content, orderNumber.toString(), "CE");

			// Comienzo con transacion
			usertransaction = ctx.getUserTransaction();			
			usertransaction.begin();

			// Se procesa el mensaje
			orderNumber = xmltoochangestate.processMessage(changestateParser);

			// Commit de la transacion
			usertransaction.commit();
			logger.info("Orden: " + orderNumber + " ha cambiado su estado");
			
			// Se reporta el estado de la transacci??n
			doSendResponse(orderNumber.toString(), "CE", 0, "El cambio de estado se cargo correctamente", now, content, correlationid);

		} catch (JAXBException e) {
			logger.error(e.getMessage());
			e.printStackTrace();

			// Rollback de la transacion
			rollback(usertransaction);

			// Respalda mensaje
			doBackUpMessage(content, "XXX", "CE");
			
			// Envia mensaje de error
			doSendResponse("-1", "CE", 1, "La estructura del mensaje no es v???lida", now, content, correlationid);

		} catch (LoadDataException e) {
			logger.error("Error en la carga");
			// Rollback de la transacion
			rollback(usertransaction);
			
			logger.error(e.getMessage());
			
			// Se genera mensaje de rechazo
			doSendResponse(orderNumber.toString(), "CE", 1, e.getMessage(), now, content, correlationid);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error ");
			// Rollback de la transacion
			rollback(usertransaction);

			// RECOVERY
			MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
			String numberStr = "";
			try {
				String[] contentSp = content.split("<norden>");
				numberStr = contentSp[1].substring(0, contentSp[1].indexOf("</norden>"));
			} catch (Exception e) {
				numberStr = "0";
			}

			messagetype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_CAMBIOESTADO_" + numberStr;
			try {
				msgRecoveryServices.saveMsgToFile(messagetype, content, ex);
			} catch (Exception e) {
				logger.error("Error en Recovery de Cambio de estado " + numberStr);
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
		//Sustituyendo acentos, ???, di???resis, ...
		String[] oldCaracters = 		{"??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??" , "??", "??", "??"};
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
