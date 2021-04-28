package bbr.b2b.logistic.managers.classes;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.managers.interfaces.LoadRecoveredMessagesManagerLocal;
import bbr.b2b.logistic.utils.MsgRecoveryServices;
import bbr.b2b.logistic.utils.QSender;

@Stateless(name = "managers/LoadRecoveredMessagesManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LoadRecoveredMessagesManager implements LoadRecoveredMessagesManagerLocal {

	private static Logger logger = Logger.getLogger(LoadRecoveredMessagesManager.class);

	private static String businessAreaCountry = "";

	private static String[] msgtypes = new String[3];

	private static final String QCF_VALUE 		= "qcf_hites";
	
	@Resource
	private SessionContext ctx;

	private void doAddMessagesTypes() {
		int i = 0;

		try {
			businessAreaCountry = LogisticConstants.getBUSINESSAREA() + LogisticConstants.getCOUNTRYCODE();
		} catch (OperationFailedException e) {
			e.printStackTrace();
		}
		msgtypes[i++] = businessAreaCountry + "_ORDEN_"; 					// Ordenes de Compra
		msgtypes[i++] = businessAreaCountry + "_RECEPCION_"; 				// Recepción
		msgtypes[i++] = businessAreaCountry + "_CANCELACION_"; 				// Cancelación de OC
	}

	public SessionContext getCtx() {
		return ctx;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	public synchronized void doProcess() throws Exception {
		
		try{
			doAddMessagesTypes();
			
			// Se revisa si hay mensaje para procesar
			MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
			HashMap<String, String> mapContent = null;
			Iterator<Entry<String, String>> it1 = null;
			for (int i = 0; i < msgtypes.length; i++) {
				String msgtype = msgtypes[i];
				try {
					mapContent = msgRecoveryServices.readMsgsFromFiles(msgtype);
				} catch (Exception eMSG) {
					logger.warn("Reload_Msg: " + eMSG.getLocalizedMessage());
					break;
				}
				it1 = mapContent.entrySet().iterator();
				while (it1.hasNext()) {
					Map.Entry<String, String> e = (Map.Entry<String, String>) it1.next();
					String path = (String) e.getKey();
					String filenameAux = path.substring(path.lastIndexOf(File.separator), path.length());
					String filename = filenameAux.split("_")[0] + "_" + filenameAux.split("_")[1] + "_" + filenameAux.split("_")[2];
					String message = (String) e.getValue();
					if (message != null && message.length() > 0) {
						try {
							if(msgtype.equalsIgnoreCase(businessAreaCountry + "_ORDEN_")) {
								QSender.getInstance().putMessage(QCF_VALUE, "ql_ordenes", message);
								logger.info("Mensaje de Orden recuperado: " + filenameAux.split("_")[2]);
							} 
							else if(msgtype.equalsIgnoreCase(businessAreaCountry + "_RECEPCION_")){
								QSender.getInstance().putMessage(QCF_VALUE, "ql_recepcion", message);
								logger.info("Mensaje de Recepción recuperado: " + filenameAux.split("_")[2]);
							}
							else if(msgtype.equalsIgnoreCase(businessAreaCountry + "_CANCELACION_")){
								QSender.getInstance().putMessage(QCF_VALUE, "ql_cancelacion", message);
								logger.info("Mensaje de Cancelación de OC recuperado: " + filenameAux.split("_")[2]);
							}
																					
						} catch (Exception ex) {
							ex.printStackTrace();
							// Si ocurri� un error al enviar el archivo, se graba el mensaje para reencolarlo
							try {
								msgRecoveryServices.saveMsgToFile(filename, message, ex);
							} catch (Exception e1) {
								logger.debug(e1.getLocalizedMessage());
							}
						}
					}
				}
			}
			logger.info("Evento autom�tico de recarga de mensajes finalizado");
			
		} catch (Exception e) {
			logger.error("DEMONIO_RECUPERACION_MENSAJES: Error excepcional!");
			e.printStackTrace();
			getCtx().setRollbackOnly();
			throw e;
		}	
	}
}
