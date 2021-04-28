package bbr.b2b.logistic.soa.queuehandler;

import java.io.StringReader;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden.NotificacionReciboOrden;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor;
import bbr.b2b.b2blink.logistic.xml.SolicitudOrdenes.SolicitudOrdenes;
import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.logistic.soa.msgb2btosoa.DirectOrdersListToXmlLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.OrdersListToXmlLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToAckDirectOcSoaLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToAckOcSoaLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToInventProvSoaLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToPLProveedorLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToPendingSoaVendorsLocal;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.utils.MsgRecoveryServices;


@Stateless(name = "handlers/SOAHandler")
@TransactionManagement(TransactionManagementType.BEAN)
public class SOAHandler implements SOAHandlerLocal {

	private static JAXBContext jc = null;
	private static JAXBContext jcSolProvOCPendiente = null;
	private static JAXBContext jcAckOc = null;
	private static JAXBContext jcInvProv = null;
	private static JAXBContext jcPackingList = null;

	private static Logger logger = Logger.getLogger("SOALog");

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.SolicitudOrdenes");
		return jc;
	}
	
	private static JAXBContext getSolProvOCPendienteJC() throws JAXBException {
		if (jcSolProvOCPendiente == null)
			jcSolProvOCPendiente = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes");
		return jcSolProvOCPendiente;
	}

	private static JAXBContext getAckOcJC() throws JAXBException {
		if (jcAckOc == null)
			jcAckOc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden");
		return jcAckOc;
	}
	//MHE CU 149
	private static JAXBContext getInvProvJC() throws JAXBException {
		if (jcInvProv == null)
			jcInvProv = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.InventarioProveedor");
		return jcInvProv;
	}
	
	private static JAXBContext getPackingListJC() throws JAXBException {
		if (jcPackingList == null)
			jcPackingList = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.PL_Proveedor");
		return jcPackingList;
	}
	
	@EJB
	OrdersListToXmlLocal orderstoxml;

	@EJB
	XmlToAckOcSoaLocal ackOcToxml;
	
	
	@EJB
	DirectOrdersListToXmlLocal directorderstoxml;

	@EJB
	XmlToAckDirectOcSoaLocal directackOcToxml;
	
	@EJB
	XmlToInventProvSoaLocal inventProvSOA;
	
	@EJB
	XmlToPendingSoaVendorsLocal xmlToPendingSoaVendors;
	
	@EJB
	XmlToPLProveedorLocal xmlToPLProveedor;
		
	public void handleMessage(String content, javax.ejb.MessageDrivenContext ctx, Long ticketNumber) {
		UserTransaction usertransaction = null;
		String messagetype = "";
		ArrayList<String> errors = new ArrayList<String>();
		boolean commitAction = false;
		try{
			
			logger.info("Contenido del mensaje:" + content);
			
			// VALIDA SI MENSAJE CORRESPONDE A SOLICITUD
			if (doValidateSchema(getJC(), content)){				
				try{
					SolicitudOrdenes request = (SolicitudOrdenes) getUnmarshalObject(getJC(), content);
					
					// RUT del proveedor desde el mensaje
					String vendorRut = request.getCodproveedor().trim();

					// RUT del comprador desde el mensaje
					String buyerRut = request.getCodcomprador().trim();

					// Comienzo con transacción
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();

					// Se procesa el mensaje de manejo de ordenes y se manejan los errores
					try {
						orderstoxml.processMessage(vendorRut, true);
						commitAction = true;
					} catch (Exception e) {
						errors.add("Error Procesando Ordenes Via SOA: \n " + e.getMessage());
					}
					
					// Se procesa el mensaje de manejo de ordenes directas y se manejan los errores
					try {
						directorderstoxml.processMessage(vendorRut, true);
						commitAction = true;
					} catch (Exception e) {
						errors.add("Error Procesando Ordenes Directas Via SOA: \n " + e.getMessage());
					}
		
					// Commit de la transacción
					if(commitAction){
						usertransaction.commit();						
					}else{
						for (String errmsg : errors){
							logger.error(errmsg);
						}	
						rollback(usertransaction);
					}
				
					
				}catch (Exception ex) {
					ex.printStackTrace();
					logger.error("Error : " + ex.getMessage());
					logger.error("Procediendo a rollback en SOAHandler...");
					rollback(usertransaction);
					logger.error("Rollback en SOAHandler finalizado");
				}				
			}
			
			// VALIDA SI MENSAJE CORRESPONDE A SOLICITUD DE órdenes PENDIENTES
			else if(doValidateSchema(getSolProvOCPendienteJC(), content)){
				logger.error("Obteniendo SolicitudProveedoresXSD");
				try{
					SolicitudProveedoresOrdenesPendientes request = (SolicitudProveedoresOrdenesPendientes) getUnmarshalObject(getSolProvOCPendienteJC(), content);
					
					// Comienzo con transacción
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();

					// Se procesa el mensaje
					xmlToPendingSoaVendors.processMessage(request);		
					
					// Commit de la transacción
					usertransaction.commit();
																	
				}catch (Exception ex) {
					ex.printStackTrace();
					logger.error("Error : " + ex.getMessage());
					logger.error("Procediendo a rollback en SOAHandler...");
					rollback(usertransaction);
					logger.error("Rollback en SOAHandler finalizado");
				}	
				
			}			
			
			// VALIDA SI MENSAJE CORRESPONDE A NOTIFICACION DE OC
			else if (doValidateSchema(getAckOcJC(), content)){
				try{
					
					NotificacionReciboOrden request = (NotificacionReciboOrden) getUnmarshalObject(getAckOcJC(), content);
					logger.info("Iniciando carga de ACK OC SOA: "+ request.getNumorden());
					
					
					// Comienzo con transacción
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();
					
					// Se procesa el mensaje de manejo de ordenes y se manejan los errores
					try {
						ackOcToxml.processMessage(request);
						commitAction = true;
					}
					catch (LoadDataException e) {
						errors.add("Error Procesando el ACK de Ordenes Via SOA: \n " + e.getMessage());
					}
					catch (Exception e) {
						e.printStackTrace();
						errors.add("Error Procesando el ACK de Ordenes Via SOA: \n " + e.getMessage());
					}
					
					// Se procesa el mensaje de manejo de ordenes directas y se manejan los errores
					try {
						directackOcToxml.processMessage(request);
						commitAction = true;
					} 
					catch (LoadDataException e) {
						errors.add("Error Procesando el ACK de Ordenes Directas Via SOA: \n " + e.getMessage());
					}
					catch (Exception e) {
						e.printStackTrace();
						errors.add("Error Procesando el ACK de Ordenes Directas Via SOA: \n " + e.getMessage());
					}
					
					// Commit de la transacción
					if(commitAction){
						usertransaction.commit();
					}else {
						for (String errmsg : errors){
							logger.error(errmsg);
						}
						rollback(usertransaction);
					}
						

				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("Error : " + ex.getMessage());
					logger.error("Procediendo a rollback en AckOcSoaHandler...");
					rollback(usertransaction);
					logger.error("Rollback en AckOcSoaHandler finalizado");
					
					// RECOVERY
					MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
					String numberStr = "";
					try {
						String[] contentSp = content.split("<numorden>");
						numberStr = contentSp[1].substring(0, contentSp[1].indexOf("</numorden>"));
					} catch (Exception e) {
						numberStr = "0";
					}

					messagetype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + "_ACKOCSOA_" + numberStr;
					try {
						msgRecoveryServices.saveMsgToFile(messagetype, content, ex);
					} catch (Exception e) {
						logger.error("Error en Recovery de OC Nro " + numberStr);
						logger.error(e.getMessage());
					}			
				}				
			}
			
			// VALIDA SI MENSAJE CORRESPONDE A INVENTARIO MHE CU149
			else if (doValidateSchema(getInvProvJC(), content)){
				try{
					logger.info("Mensaje de Inventario para procesar");
					InventarioProveedor request = (InventarioProveedor) getUnmarshalObject(getInvProvJC(), content);
					
					// Comienzo con transacción
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(3000);
					usertransaction.begin();
					
					// Se procesa el mensaje de manejo de inventario de proveedores y se manejan los errores
					if(ticketNumber == null){
						ticketNumber = System.nanoTime();
						logger.info("Procesando mensaje SOA sin ticket. Se genera internamente el ticket  :" + ticketNumber);	
					}
					
					inventProvSOA.processMessage(request, ticketNumber);
					usertransaction.commit();					
					logger.info("Mensaje de Inventario procesado");
				} catch (Exception ex) {
					logger.info("Error al intentar procesar Mensaje de Inventario");
					ex.printStackTrace();
					logger.error("Error : " + ex.getMessage());
					logger.error("Procediendo a rollback en SoaHandler...");
					rollback(usertransaction);
					logger.error("Rollback en AckOcSoaHandler finalizado");
					
					// RECOVERY
					MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
					messagetype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + "_INVENTARIO_PROV_SOA_";
					try {
						msgRecoveryServices.saveMsgToFile(messagetype, content, ex);
					} catch (Exception e) {
						logger.error("Error en Recovery de Inventario de Proveedor ");
						logger.error(e.getMessage());
					}			
				}				
			}
			
			else if(doValidateSchema(getPackingListJC(), content)){
				logger.error("Obteniendo SolicitudProveedoresXSD");
				try{
					PLProveedor request = (PLProveedor) getUnmarshalObject(getPackingListJC(), content);
					
					// Comienzo con transacción
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();

					// Se procesa el mensaje
					xmlToPLProveedor.processMessage(request, ticketNumber);		
					
					// Commit de la transacción
					usertransaction.commit();
																	
				}catch (Exception ex) {
					ex.printStackTrace();
					logger.error("Error : " + ex.getMessage());
					logger.error("Procediendo a rollback en SOAHandler...");
					rollback(usertransaction);
					logger.error("Rollback en SOAHandler finalizado");
					
					// RECOVERY
					MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();

					messagetype = messagetype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + "_PL_PROVEEDOR_";
					try {
						msgRecoveryServices.saveMsgToFile(messagetype, content, ex);
					} catch (Exception e) {
						logger.error("Error en Recovery de Mensaje SOA PLProveedor");
						logger.error(e.getMessage());
					}

				}	
				
			} else{
				logger.info("El mensaje no se puede validar contra ningun XSD");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			rollback(usertransaction);
		}	
	}

	private void rollback(UserTransaction usertransaction) {
		try {
			if (usertransaction != null && usertransaction.getStatus() == Status.STATUS_ACTIVE)
			//if (usertransaction != null)
				usertransaction.rollback();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
	}
	
	private boolean doValidateSchema(JAXBContext jc, String content) {
		try {
			InputSource source = new InputSource(new StringReader(content));
			Unmarshaller u = jc.createUnmarshaller();
			u.unmarshal(source);
			return true;
		} catch (java.lang.ClassCastException e) {
			return false;
		} catch (JAXBException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private Object getUnmarshalObject(JAXBContext jc, String content){
		try{
			InputSource source = new InputSource(new StringReader(content));
			Unmarshaller u = jc.createUnmarshaller();	
			return u.unmarshal(source);
		}catch (JAXBException e) {
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
}
