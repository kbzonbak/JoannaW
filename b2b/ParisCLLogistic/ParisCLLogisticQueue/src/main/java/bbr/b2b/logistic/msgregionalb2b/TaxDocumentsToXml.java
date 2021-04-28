package bbr.b2b.logistic.msgregionalb2b;

import javax.ejb.Stateless;


/*
 * 2016/05/25 (JMA): Se solicta no enviar mas esta interfaz
 */


@Stateless(name = "handlers/TaxDocumentsToXml")
public class TaxDocumentsToXml implements TaxDocumentsToXmlLocal {

//	private static JAXBContext jc = null;
//	
//	private static JAXBContext getJC() throws JAXBException {
//		if (jc == null)
//			jc = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.qguiadespacho");
//		return jc;
//	}	
//	
//	@EJB
//	DeliveryServerLocal deliveryserver;
//	
//	@EJB
//	BulkDetailServerLocal bulkdetailserver;
//	
//	@EJB
//	DatingServerLocal datingserver;
//	
//	CommonQueueUtils qutils = CommonQueueUtils.getInstance();	
//	private Logger logger = Logger.getLogger(TaxDocumentsToXml.class);
//	private static Logger logger_ack = Logger.getLogger("LogNotificacion");

//	public void processMessage(String n_reception) throws LoadDataException, ServiceException, Exception {
//		
//		String transnumber = "";
//		try {
//
//			JAXBContext jc = getJC();
//			bbr.b2b.regional.logistic.xml.qguiadespacho.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.qguiadespacho.ObjectFactory();
//						
//			//Crea objeto que corresponde al mensaje
//			QGUIADESPACHO qdeliverydocuments = objFactory.createQGUIADESPACHO();
//
//			// Buscamos el Lote
//			Long nreception = Long.parseLong(n_reception);
//
//			DeliveryW[] dlArr = deliveryserver.getByPropertyAsArray("receptionnumber", nreception);
//			
//			if (dlArr == null || dlArr.length == 0){
//				throw new LoadDataException("No existe el despacho para el numero de recepción " + n_reception);
//			}
//			
//			DeliveryW deliveryw  = dlArr[0];
//			
//			// N°mero de transacción
//			transnumber = deliveryserver.getNextSequenceTransactionNumber(12);			
//			
//			// Cita
//			DatingW[] dtArr = datingserver.getByPropertyAsArray("delivery.id", deliveryw.getId());
//			
//			if (dtArr == null || dtArr.length == 0){
//				throw new LoadDataException("No existe la cita para el despacho " + deliveryw.getNumber());
//			}
//			
//			DatingW datingW = dtArr[0];
//
//			// N°mero de secuencia
//			qdeliverydocuments.setSecuencia(Long.parseLong(transnumber));			
//			
//			// N°mero de cita
//			qdeliverydocuments.setNasn(datingW.getNumber());
//			
//			// N°mero de recepción
//			qdeliverydocuments.setNrecepcion(nreception);
//
//			Detalles dt = objFactory.createDetalles();
//
//			List<Long> detalle = dt.getNDoc();
//			
//			BulkDetailW[] codesp = bulkdetailserver.getByQueryAsArray("select distinct bkd from BulkDetail as bkd, Bulk as bk, OrderDelivery as odl, Delivery as dl where bkd.bulk = bk and bk.orderdelivery = odl and odl.delivery = dl and dl.id = " + deliveryw.getId());
//			
//			Long detail = null;
//			for (int i = 0; i < codesp.length; i++) {
//
//				detail = Long.parseLong(codesp[i].getRefdocument());
//				detalle.add(detail);
//			}
//
//			qdeliverydocuments.setDetalles(dt);
//
//			// Obtiene string XML para enviarlo a la cola
//			Marshaller m = jc.createMarshaller();
//			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			StringWriter sw = new StringWriter();
//			m.marshal(qdeliverydocuments, sw);
//			String result = sw.toString();
//
//			// RESPALDA MENSAJE			
//			doBackUpMessage(result, deliveryw.getNumber().toString(), "GUIA");
//			
//			String messagetype = "GD";
//			String numberStr = datingW.getNumber().toString();
//			String info = "CREACION";
//			String exception = "Despacho " + deliveryw.getNumber() + " - Recepción " + nreception; 			
//			
//			// Se registra el resultado de carga de mensajes en un log particular
//			logger_ack.info(",\"" + messagetype + "\",\"" + numberStr + "\",\"" + info + "\",\"" + exception + "\"");
//			
//			try {			
//				QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_guiadespacho", result);				
//			} catch (Exception ex) {
//				// Si ocurrió un error al enviar el archivo, se graba el mensaje para reencolarlo
//				MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
//				String msgtype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_GUIA_" + deliveryw.getNumber();
//				try {
//					msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
//				} catch (Exception e1) {
//					logger.debug(e1.getLocalizedMessage());
//				}
//			}	
//		}catch (JAXBException e) {
//			e.printStackTrace();			
//		}catch (Exception e) {
//			e.printStackTrace();			
//		}
//	}
//	
//	private void doBackUpMessage(String content, String number, String msgType){
//		
//		// EJECUTA UNA TAREA QUE RESPALDA EL MENSAJE.
//		// ESTA ES INDEPENDIENTE DE LA CARGA DEL MENSAJE.
//		try{
//			MBeanServer server = MBeanServerLocator.locateJBoss();			
//			ObjectName objectName = new ObjectName("jboss.jca:service=WorkManager");
//			JBossWorkManagerMBean jwm = (JBossWorkManagerMBean)MBeanServerInvocationHandler.newProxyInstance(server,objectName,JBossWorkManagerMBean.class, false);
//			WorkManager wm = jwm.getInstance();
//			
//			wm.scheduleWork(new BackUpUtils(content, number, msgType));		
//		}catch (MalformedObjectNameException e) {
//			e.printStackTrace();
//		}catch (WorkException e) {
//			e.printStackTrace();
//		}		
//	}
}
