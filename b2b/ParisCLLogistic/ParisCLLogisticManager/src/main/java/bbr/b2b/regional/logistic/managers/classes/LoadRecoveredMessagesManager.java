package bbr.b2b.regional.logistic.managers.classes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.managers.interfaces.LoadRecoveredMessagesManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.LoadRecoveredMessagesManagerRemote;
import bbr.b2b.regional.logistic.utils.MsgRecoveryServices;
import bbr.b2b.regional.logistic.utils.QSender;

@Stateless(name = "managers/LoadRecoveredMessagesManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LoadRecoveredMessagesManager implements LoadRecoveredMessagesManagerLocal, LoadRecoveredMessagesManagerRemote {

	private static Logger logger = Logger.getLogger(LoadRecoveredMessagesManager.class);

	private static boolean isServiceStarted = false;

	private static String businessAreaCountry = "";

	private static String[] msgtypes = new String[22];

	@Resource
	private SessionContext ctx;

	private void doAddMessagesTypes() {
		int i = 0;

		try {
			businessAreaCountry = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE();
		} catch (OperationFailedException e) {
			e.printStackTrace();
		}
		msgtypes[i++] = businessAreaCountry + "_ORDEN_"; // Ordenes de Compra
		msgtypes[i++] = businessAreaCountry + "_CAMBIOESTADO_"; // Cambio de estado de Ordenes
		msgtypes[i++] = businessAreaCountry + "_ACK_"; // Avisos
		msgtypes[i++] = businessAreaCountry + "_ACKCE_"; // Avisos Cambio Estado
		msgtypes[i++] = businessAreaCountry + "_VENDOR_"; // Carga de Vendedores
		msgtypes[i++] = businessAreaCountry + "_PRODUCTO_"; // Productos
		msgtypes[i++] = businessAreaCountry + "_RECEPCION_"; // Recepción
		msgtypes[i++] = businessAreaCountry + "_RM_"; // RM
		msgtypes[i++] = businessAreaCountry + "_EVALUACION_"; // Evaluación
		msgtypes[i++] = businessAreaCountry + "_PLIMPRESP_"; // PL Importado Respuesta
		msgtypes[i++] = businessAreaCountry + "_PLIMP_"; // PL Importado
		msgtypes[i++] = businessAreaCountry + "_ASN_"; // ASN
		msgtypes[i++] = businessAreaCountry + "_GUIA_"; // Gu�a Despacho
		msgtypes[i++] = businessAreaCountry + "_INT1887_";
		msgtypes[i++] = businessAreaCountry + "_INT1885_";
		msgtypes[i++] = businessAreaCountry + "_INT1874_";
		msgtypes[i++] = businessAreaCountry + "_INT1851_";
		msgtypes[i++] = businessAreaCountry + "_INT1849_";
		msgtypes[i++] = businessAreaCountry + "_INT1846_";
		msgtypes[i++] = businessAreaCountry + "_INT1879_";
		msgtypes[i++] = businessAreaCountry + "_INT1887_";
		msgtypes[i++] = businessAreaCountry + "_ACKOCSOA_"; // Ack	Oc SOA
	}

	public SessionContext getCtx() {
		return ctx;
	}

	public synchronized void scheduleTimer(long initialinterval, long milliseconds) {
		// Cancelar todos los timers previamente asociados a este manager
		TimerService ts = ctx.getTimerService();
		// Obtiene todos los timers asociados al bean
		Collection<Timer> timers = ts.getTimers();
		// ... y los cancela
		for (Iterator iterator = timers.iterator(); iterator.hasNext();) {
			Timer timer = (Timer) iterator.next();
			timer.cancel();
		}
		// Crear el timerservice
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron para recarga de mensajes no procesados");
		isServiceStarted = true;
		doAddMessagesTypes();
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	@Timeout
	public synchronized void timeoutHandler(Timer timer) {
		if (!isServiceStarted)
			return;
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
		logger.info(" ----- " + sdf.format(today) + "  Evento Timer Recibido: " + timer.getInfo() + " ----- ");

		// Se revisa si hay mensaje para procesar
		MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
		HashMap<String, String> mapContent = null;
		Iterator it1 = null;
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
				Map.Entry e = (Map.Entry) it1.next();
				String path = (String) e.getKey();
				String filenameAux = path.substring(path.lastIndexOf(File.separator), path.length());
				String filename = filenameAux.split("_")[0] + "_" + filenameAux.split("_")[1] + "_" + filenameAux.split("_")[2];
				String message = (String) e.getValue();
				if (message != null && message.length() > 0) {
					try {
						if (msgtype.equalsIgnoreCase(businessAreaCountry + "_ORDEN_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_ordenes", message);
							logger.info("Mensaje de Orden Canonico recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_CAMBIOESTADO_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_cambioestado", message);
							logger.info("Mensaje de Cancelación recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_ACK_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_ackb2b", message);
							logger.info("Mensaje de Rechazo o Información recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_ACKCE_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_ackce", message);
							logger.info("Mensaje de Rechazo o Información Cambio Estado recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_VENDOR_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_vendor", message);
							logger.info("Mensaje de Carga de Vendedores recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_PRODUCTO_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_productos", message);
							logger.info("Mensaje de Productos recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_RECEPCION_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_recepcion", message);
							logger.info("Mensaje de Recepción recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_RM_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_rm", message);
							logger.info("Mensaje de RM recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_EVALUACION_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_evaluacion", message);
							logger.info("Mensaje de Evaluación recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_PLIMPRESP_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_plimprespb2b", message);
							logger.info("Mensaje de Respuesta PL Importado recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_PLIMP_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_plimp", message);
							logger.info("Mensaje de PL Importado recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_ASN_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_asn", message);
							logger.info("Mensaje de ASN recuperado: " + filenameAux.split("_")[2] + "_" + filenameAux.split("_")[3]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_GUIA_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_guiadespacho", message);
							logger.info("Mensaje de Gu�a de Despacho recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_INT1887_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1887", message);
							logger.info("Mensaje 1887 recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_INT1885_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1885", message);
							logger.info("Mensaje 1885 recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_INT1874_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1874", message);
							logger.info("Mensaje 1874 recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_INT1851_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1851", message);
							logger.info("Mensaje 1851 recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_INT1849_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1849", message);
							logger.info("Mensaje 1849 recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_INT1846_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1846", message);
							logger.info("Mensaje 1846 recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_INT1879_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1879", message);
							logger.info("Mensaje 1879 recuperado: " + filenameAux.split("_")[2]);
						} else if (msgtype.equalsIgnoreCase(businessAreaCountry + "_INT1887_")) {
							QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1887", message);
							logger.info("Mensaje 1887 recuperado: " + filenameAux.split("_")[2]);
						} else if(msgtype.equalsIgnoreCase(businessAreaCountry + "_ACKOCSOA_")){
							QSender.getInstance().putMessage("jboss/qcf_soa", "jboss/wsmq/q_pariscllogin", message);
							logger.info("Mensaje de ACK OC SOA recuperado: " + message);
						}

					} catch (Exception ex) {
						ex.printStackTrace();
						// Si ocurrió un error al enviar el archivo, se graba el mensaje para reencolarlo
						try {
							msgRecoveryServices.saveMsgToFile(filename, message, ex);
						} catch (Exception e1) {
							logger.debug(e1.getLocalizedMessage());
						}
					}
				}
			}
		}
	}
}
