package bbr.b2b.logistic.soa.queuehandler;

import java.io.StringReader;

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
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.DirectOrdersListToXmlLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.OrdersListToXmlLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToAckDirectOcSoaLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToAckOcSoaLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToInventProvSoaLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToPLProveedorLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToPendingSoaVendorsLocal;
import bbr.b2b.logistic.utils.MsgRecoveryServices;

@Stateless(name = "handlers/SOAHandler")
@TransactionManagement(TransactionManagementType.BEAN)
public class SOAHandler implements SOAHandlerLocal {

	private static JAXBContext jcSolicitudOrdenes = null;

	private static JAXBContext jcAckOc = null;

	private static JAXBContext jcSolProvOCPendiente = null;

	private static JAXBContext jcInvProv = null;
	
	private static JAXBContext jcPackingList = null;

	private static Logger logger = Logger.getLogger("SOALog");

	private static JAXBContext getJC_solicitudOrdenes() throws JAXBException {
		if (jcSolicitudOrdenes == null)
			jcSolicitudOrdenes = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.SolicitudOrdenes");
		return jcSolicitudOrdenes;
	}

	private static JAXBContext getAckOcJC() throws JAXBException {
		if (jcAckOc == null)
			jcAckOc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden");
		return jcAckOc;
	}

	private static JAXBContext getSolProvOCPendienteJC() throws JAXBException {
		if (jcSolProvOCPendiente == null)
			jcSolProvOCPendiente = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes");
		return jcSolProvOCPendiente;
	}

	private static JAXBContext getPackingListJC() throws JAXBException {
		if (jcPackingList == null)
			jcPackingList = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.PL_Proveedor");
		return jcPackingList;
	}
	
	private static JAXBContext getInvProvJC() throws JAXBException {
		if (jcInvProv == null)
			jcInvProv = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.InventarioProveedor");
		return jcInvProv;
	}
	
	@EJB
	OrdersListToXmlLocal orderstoxml;

	@EJB
	XmlToAckOcSoaLocal ackOcToxml;

	@EJB
	XmlToPendingSoaVendorsLocal xmlToPendingSoaVendors;

	@EJB
	SOAStateTypeServerLocal soastatetypeserver;

	@EJB
	DirectOrdersListToXmlLocal directorderstoxml;

	@EJB
	XmlToAckDirectOcSoaLocal directackOcToxml;
	
	@EJB
	XmlToPLProveedorLocal xmlToPLProveedor;
	
	@EJB
	XmlToInventProvSoaLocal xmlToInventProvSoaLocal;


	public void handleMessage(String content, javax.ejb.MessageDrivenContext ctx, Long ticketNumber) throws OperationFailedException, NotFoundException {
		UserTransaction usertransaction = null;
		String messagetype = "";
		boolean commitAction = false;
		try {

			logger.info("Contenido del mensaje :" + content);

			// SOLICITUD DE OC
			if (doValidateSchema(getJC_solicitudOrdenes(), content)) {
				logger.info("Obteniendo SolicitudSOAXSD");
				try {

					// SolicitudOrdenes request = (SolicitudOrdenes) u.unmarshal(source);
					SolicitudOrdenes request = (SolicitudOrdenes) getUnmarshalObject(getJC_solicitudOrdenes(), content);
					// RUT del proveedor desde el mensaje
					String vendorRut = request.getCodproveedor().trim();

					// RUT del comprador desde el mensaje
					String buyerRut = request.getCodcomprador().trim();

					// Comienzo con transacion
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();

					// Se procesa el mensaje de manejo de ordenes y se manejan los errores
					try {
						orderstoxml.processMessage(vendorRut, buyerRut, true);
						commitAction = true;
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Error Procesando Ordenes Via SOA:  " + e.getMessage());
					}
					// Se procesa el mensaje de manejo de ordenes directas y se manejan los errores
					try {
						directorderstoxml.processMessage(vendorRut, true);
						commitAction = true;
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Error Procesando Ordenes Directas Via SOA:  " + e.getMessage());
					}

					// logger.info("Procediendo a commit en SOAHandler...");
					// Commit de la transacci�n
					if (commitAction)
						usertransaction.commit();
					// logger.info("Commit en SOAHandler finalizado");

				} catch (Exception ex) {
					// Si pasa esto, significa que el mensaje no es una solicitud de OC
					ex.printStackTrace();
					logger.error("Error : " + ex.getMessage(), ex);
					logger.error("Procediendo a rollback en SOAHandler...");
					rollback(usertransaction);
					logger.error("Rollback en SOAHandler finalizado");
				}
			}

			else if (doValidateSchema(getSolProvOCPendienteJC(), content)) {
				// Solicitud Ordenes Pendientes
				logger.info("Obteniendo SolicitudProveedoresXSD");
				try {
					SolicitudProveedoresOrdenesPendientes request = (SolicitudProveedoresOrdenesPendientes) getUnmarshalObject(getSolProvOCPendienteJC(), content);

					// Comienzo con transacci�n
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();

					// Se procesa el mensaje
					xmlToPendingSoaVendors.processMessage(request);

					// Commit de la transacci�n
					usertransaction.commit();

				} catch (Exception ex) {
					logger.error("Error : " + ex.getMessage(), ex);
					logger.error("Procediendo a rollback en SOAHandler...");
					rollback(usertransaction);
					logger.error("Rollback en SOAHandler finalizado");
				}

			}

			// VALIDA SI MENSAJE CORRESPONDE A NOTIFICACION DE OC
			else if (doValidateSchema(getAckOcJC(), content)) {
				try {
					NotificacionReciboOrden request = (NotificacionReciboOrden) getUnmarshalObject(getAckOcJC(), content);

					// Comienzo con transacci�n
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();

					// Se procesa el mensaje. Si no es oc normal, puede ser directa
					ackOcToxml.processMessage(request);
					//directackOcToxml.processMessage(request);

					// Commit de la transacci�n
					usertransaction.commit();

				} catch (LoadDataException e) {
					logger.error("Error en la carga:" + e.getMessage());
					e.printStackTrace();
					// Rollback de la transacion
					rollback(usertransaction);

					logger.error(e.getMessage());
				} catch (Exception ex) {
					logger.error("Error : " + ex.getMessage(), ex);
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

					messagetype = "ACKOCSOA" + numberStr;
					try {
						msgRecoveryServices.saveMsgToFile(messagetype, content, ex);
					} catch (Exception e) {
						logger.error("Error en Recovery de OC Nro " + numberStr);
						logger.error(e.getMessage());
					}
				}
			}else if(doValidateSchema(getPackingListJC(), content)){
				logger.info("Obteniendo SolicitudProveedoresXSD");
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

					messagetype = LogisticConstants.getInstance().getBUSINESSAREA() + LogisticConstants.getInstance().getCOUNTRYCODE() + "_PL_PROVEEDOR_";
					try {
						msgRecoveryServices.saveMsgToFile(messagetype, content, ex);
					} catch (Exception e) {
						logger.error("Error en Recovery de Mensaje SOA Solicitud Proveedores OC Pendientes");
						logger.error(e.getMessage());
					}

				}
			}
				else if (doValidateSchema(getInvProvJC(), content)) {
					try {
						logger.info("Mensaje de Inventario para procesar");
						InventarioProveedor request = (InventarioProveedor) getUnmarshalObject(getInvProvJC(), content);

						// Comienzo con transacción
						usertransaction = ctx.getUserTransaction();
						usertransaction.setTransactionTimeout(3000);
						usertransaction.begin();

						// Se procesa el mensaje de manejo de inventario de proveedores y se manejan los errores
						xmlToInventProvSoaLocal.processMessage(request, ticketNumber);
						usertransaction.commit();
						logger.info("Mensaje de Inventario procesado");
					} catch (Exception ex) {
						logger.info("Error al intentar procesar Mensaje de Inventario");
						logger.error("Error : " + ex.getMessage(), ex);
						logger.error("Procediendo a rollback en SoaHandler...");
						rollback(usertransaction);
						logger.error("Rollback en AckOcSoaHandler finalizado");

						// RECOVERY
						/*MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
						//messagetype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + "_INVENTARIO_PROV_SOA_";
						try {
							msgRecoveryServices.saveMsgToFile(messagetype, content, ex);
						} catch (Exception e) {
							logger.error("Error en Recovery de Inventario de Proveedor ");
							logger.error(e.getMessage());
						}*/
					}
				
			}else{
				logger.info("El mensaje no se puede validar contra ningun XSD");
			}

		} catch (Exception ex) {
			logger.error("Error : " + ex.getMessage(), ex);
			logger.error("Procediendo a rollback en SOAHandler...");
			rollback(usertransaction);
			logger.error("Rollback en SOAHandler finalizado");
		}
	}

	private void rollback(UserTransaction usertransaction) {
		try {
			if (usertransaction != null && usertransaction.getStatus() == Status.STATUS_ACTIVE)
				// if (usertransaction != null)
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

	private Object getUnmarshalObject(JAXBContext jc, String content) {
		try {
			Unmarshaller u = jc.createUnmarshaller();
			InputSource source = new InputSource(new StringReader(content));
			return u.unmarshal(source);
		} catch (java.lang.ClassCastException e) {
			return null;
		} catch (JAXBException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
