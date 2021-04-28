package bbr.b2b.logistic.soa.queuehandler;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden.NotificacionReciboOrden;
import bbr.b2b.b2blink.logistic.xml.NotificacionReciboRec.NotificacionReciboRec;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor;
import bbr.b2b.b2blink.logistic.xml.RecCustomer.Recepcion;
import bbr.b2b.b2blink.logistic.xml.SolicitudOrdenes.SolicitudOrdenes;
import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes;
import bbr.b2b.b2blink.logistic.xml.SolicitudRecOC.SolicitudRecOC;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.util.Mailer;
import bbr.b2b.logistic.soa.msgb2btosoa.OrdersListToXmlLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.RecOCToXmlLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToAckOcSoaLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToAckRecOcSoaLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToInventarioProveedorLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToOrderLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToOrderReceptionLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToPLProveedorLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToPLProveedorLorealLocal;
import bbr.b2b.logistic.soa.msgb2btosoa.XmlToPendingSoaVendorsLocal;
import bbr.b2b.logistic.utils.BackUpUtils;

@Stateless(name = "handlers/SOAHandler")
@TransactionManagement(TransactionManagementType.BEAN)
public class SoaHandler implements SoaHandlerLocal {

	private static JAXBContext jcOcCustomer = null;
	private static JAXBContext jcSolicitudOrden = null;
	private static JAXBContext jcPackingList = null;
	private static JAXBContext jcAckOc = null;
	private static JAXBContext jcRecOC = null;
	
	private static JAXBContext jcInvProv = null;
	private static JAXBContext jcSolicitudRecOC = null;
	
	private static JAXBContext jcAckRecOc = null;
	private static JAXBContext jcSolProvOCPendiente = null;
	
	private static Logger logger = Logger.getLogger("SOALog");
	
	@Resource
	private ManagedExecutorService executor;

	private static JAXBContext getJC() throws JAXBException {
		if (jcOcCustomer == null)
			jcOcCustomer = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.OC_customer");
		return jcOcCustomer;
	}
	
	private static JAXBContext getJCSolicitudOrden() throws JAXBException {
		if (jcSolicitudOrden == null)
			jcSolicitudOrden = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.SolicitudOrdenes");
		return jcSolicitudOrden;
	}
	
	private static JAXBContext getPackingListJC() throws JAXBException {
		if (jcPackingList == null)
			jcPackingList = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.PL_Proveedor");
		return jcPackingList;
	}
	
	private static JAXBContext getAckOcJC() throws JAXBException {
		if (jcAckOc == null)
			jcAckOc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden");
		return jcAckOc;
	}
	
	private static JAXBContext getInvProvJC() throws JAXBException {
		if (jcInvProv == null)
			jcInvProv = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.InventarioProveedor");
		return jcInvProv;
	}
	
	private static JAXBContext getRecOCJC() throws JAXBException {
		if (jcRecOC == null)
			jcRecOC = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.RecCustomer");
		return jcRecOC;
	}
	
	private static JAXBContext getJC_solicitudRecOC() throws JAXBException {
		if (jcSolicitudRecOC == null)
			jcSolicitudRecOC = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.SolicitudRecOC");
		return jcSolicitudRecOC;
	}
	
	private static JAXBContext getAckRecOcJC() throws JAXBException {
		if (jcAckRecOc == null)
			jcAckRecOc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.NotificacionReciboRec");
		return jcAckRecOc;
	}
	
	private static JAXBContext getSolProvOCPendienteJC() throws JAXBException {
		if (jcSolProvOCPendiente == null)
			jcSolProvOCPendiente = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes");
		return jcSolProvOCPendiente;
	}
	
	
	
	
	@EJB
	XmlToOrderLocal xmlToOrderLocal;
	
	@EJB
	OrdersListToXmlLocal ordersListToXmlLocal;
	
	@EJB
	XmlToPLProveedorLorealLocal xmlToPLProveedorLorealLocal;
	
	@EJB
	XmlToPLProveedorLocal xmlToPLProveedorLocal;
	
	@EJB
	XmlToAckOcSoaLocal ackOcToxml;
	
	@EJB
	XmlToInventarioProveedorLocal xmlToInventarioProveedorLocal;
	
	@EJB
	XmlToOrderReceptionLocal xmlToOrderReceptionLocal;
	
	@EJB
	RecOCToXmlLocal recoctoxml;
	
	@EJB
	XmlToAckRecOcSoaLocal ackRecOcToxml;
	
	@EJB
	XmlToPendingSoaVendorsLocal xmltopendingsoavendors;
	
		
	public void handleMessage(String content, javax.ejb.MessageDrivenContext ctx, Long ticketNumber) {
		UserTransaction usertransaction = null;
		ArrayList<String> errors = new ArrayList<String>();
		boolean commitAction = false;
		try{
			// VALIDA SI MENSAJE CORRESPONDE A SOLICITUD
			
			if (doValidateSchema(getJC(), content)){				
				try{
					Order request = (Order) getUnmarshalObject(getJC(), content);
					logger.info("Se recibe orden número: " + request.getNumber() + " TIPO :" + request.getOrdertype());
					
					// Comienzo con transacción
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();

					// Se procesa el mensaje de manejo de ordenes y se manejan los errores
//					try {
						xmlToOrderLocal.processMessage(request);
//						commitAction = true;
//					} catch (Exception e) {
//						errors.add("Error procesando órdenes Via SOA: " + e.getMessage());
//					}
						usertransaction.commit();	
					// RESPALDA MENSAJE
					doBackUpMessage(content, request.getNumber(), request.getBuyer()+"_OC_CUSTOMER");
					
//					if(commitAction){
//						usertransaction.commit();						
//					}else{
//						for (String errmsg : errors){
//							logger.error(errmsg);
//						}	
//						rollback(usertransaction);
//					}
				}catch (OperationFailedException ex) {
					ex.printStackTrace();
					logger.error("Error : " + ex.getMessage());
					logger.error("Procediendo a rollback en SOAHandler...");
					rollback(usertransaction);
					logger.error("Rollback en SOAHandler finalizado");
					// RESPALDA MENSAJE
					Order request = (Order) getUnmarshalObject(getJC(), content);
					doBackUpMessage(content, request.getNumber(), request.getBuyer()+"_OC_CUSTOMER");
					LocalDateTime now = LocalDateTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				    String date = now.format(formatter);
					// ENVIA CORREO
				    if(Boolean.valueOf(System.getProperty("SENDMAIL_OC_ERROR"))){
						logger.info("Iniciando envío de correo");
						String subject = "Problema al cargar OC ["+request.getNumber()+"] en módulo Customer Service ";
						try {
							Mailer mailer = Mailer.getInstance();
							String[] mailreciever = System.getProperty("MAIL_OC_ERROR").split(",");
							String mailsender = System.getProperty("MAIL_SENDER");
							String mailSession = System.getProperty("MAIL_SESSION");
							String text = " La orden "+request.getNumber()+" del cliente ["+request.getBuyer()+"] no ha podido ser cargada. \n\n"+
											" Fecha: "+date+" \n\n" +
											" Error: "+ex.getMessage()+" \n\n";
							mailer.sendMailBySession(mailreciever, mailreciever, null, mailsender, subject, text, false, null, mailSession);
						} catch (Exception e) {
							logger.error("No se envio el mail del fracaso de envio de mensaje");
						}
				    }
					
				}				
			} else if (doValidateSchema(getJCSolicitudOrden(), content)){
				try{
					SolicitudOrdenes request = (SolicitudOrdenes) getUnmarshalObject(getJCSolicitudOrden(), content);
					logger.info(request);
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
						ordersListToXmlLocal.processMessage(vendorRut, true);
						commitAction = true;
					} catch (Exception e) {
						errors.add("Error Procesando Ordenes Via SOA: \n " + e.getMessage());
					}
					
		
					// Commit de la transacci�n
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
			} else if(doValidateSchema(getPackingListJC(), content)){
				try{
					PLProveedor request = (PLProveedor) getUnmarshalObject(getPackingListJC(), content);
					
					// Comienzo con transacción
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();
					
//					"CL0601": //unimarc
//					"CL1301": //falabella
//					"CL1501": //tottus
//					"CL1201": //Ripley
//					"CL0701": //walmart
					
					// Se procesa el mensaje
					if( request.getCodCliente().equals("CL0601") || request.getCodCliente().equals("CL1301") || request.getCodCliente().equals("CL1501") ||
							request.getCodCliente().equals("CL1201") || request.getCodCliente().equals("CL0701")){
						//PL SOLO CARGA manual Loreal
						xmlToPLProveedorLorealLocal.processMessage(request, ticketNumber);
					}
								
					else{
						//PL con carga automática y manual Loreal
						xmlToPLProveedorLocal.processMessage(request, ticketNumber);
					}
					
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
			//ACK
			else if (doValidateSchema(getAckOcJC(), content)) {
				try {

					NotificacionReciboOrden request = (NotificacionReciboOrden) getUnmarshalObject(getAckOcJC(), content);
					logger.info("Iniciando carga de ACK OC SOA: " + request.getNumorden());

					// Comienzo con transacción
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();

					// Se procesa el mensaje de manejo de ordenes y se manejan los errores
					try {
						ackOcToxml.processMessage(request);
						commitAction = true;
					} catch (Exception e) {
						errors.add("Error procesando ACK: " + e.getMessage());
					}
					
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
			} else if (doValidateSchema(getInvProvJC(), content)){
				try{
					logger.info("Mensaje de Inventario para procesar");
					InventarioProveedor request = (InventarioProveedor) getUnmarshalObject(getInvProvJC(), content);
					
					// Comienzo con transacción
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(3000);
					usertransaction.begin();
					//TODO: llamar a implementacion de llamado a api
					// Se procesa el mensaje de manejo de inventario de proveedores y se manejan los errores
					xmlToInventarioProveedorLocal.processMessage(ticketNumber, request);
					
					usertransaction.commit();					
					logger.info("Mensaje de Inventario procesado");
				} catch (Exception ex) {
					logger.info("Error al intentar procesar Mensaje de Inventario");
					ex.printStackTrace();
					logger.error("Error : " + ex.getMessage());
					logger.error("Procediendo a rollback en SoaHandler...");
					rollback(usertransaction);
					logger.error("Rollback en AckOcSoaHandler finalizado");
					
				}
			} else if (doValidateSchema(getRecOCJC(), content)){
				try{
					logger.info("Mensaje de RecepcionOC para procesar");
					Recepcion request = (Recepcion) getUnmarshalObject(getRecOCJC(), content);
					
					// Comienzo con transacción
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(3000);
					usertransaction.begin();
					xmlToOrderReceptionLocal.processMessage(request);
					usertransaction.commit();					
					logger.info("Mensaje de recepción de oc Procesado");
				} catch (Exception ex) {
					logger.info("Error al intentar procesar Mensaje recepción de OC");
					ex.printStackTrace();
					logger.error("Error : " + ex.getMessage());
					logger.error("Procediendo a rollback en SoaHandler...");
					rollback(usertransaction);
					logger.error("Rollback en AckOcSoaHandler finalizado");
					
				}
			}else if (doValidateSchema(getJC_solicitudRecOC(), content)) {
				// SOLICITUD DE RECEPCION DE OC
				logger.info("Obteniendo Solicitud de Recepciones");
				try {					
					SolicitudRecOC request = (SolicitudRecOC) getUnmarshalObject(getJC_solicitudRecOC(), content);
					
					String vendorCode = request.getCodproveedor().trim();
					String buyerCode = request.getCodcomprador().trim();
	
					// Comienzo con transacion
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();
	
					// Se procesa el mensaje
					recoctoxml.processMessage(vendorCode, buyerCode);
	
					// Commit de la transacción
					usertransaction.commit();
					
				} catch (Exception ex) {
					logger.info("Error al al procesar envío de RecInterno");
					ex.printStackTrace();
					logger.error("Error : " + ex.getMessage());
					logger.error("Procediendo a rollback en SoaHandler...");
					rollback(usertransaction);
					logger.error("Rollback en AckOcSoaHandler finalizado");
				}
			}else if (doValidateSchema(getAckRecOcJC(), content)) {
				// ACK DE RECEPCIONES
				try{
					NotificacionReciboRec request = (NotificacionReciboRec) getUnmarshalObject(getAckRecOcJC(), content);
					
					// Comienzo con transacción
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();
	
					// Se procesa el mensaje					
					ackRecOcToxml.processMessage(request);
	
					// Commit de la transacción
					usertransaction.commit();
				
				} catch (LoadDataException e) {
					logger.error("Error: " + e.getMessage());
					e.printStackTrace();
					// Rollback de la transacion
					rollback(usertransaction);
					
					logger.error(e.getMessage());
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("Error : " + ex.getMessage());
					logger.error("Procediendo a rollback en AckRecOcSoaHandler...");					
					rollback(usertransaction);
					logger.error("Rollback en AckRecOcSoaHandler finalizado");
				}				
			}else if (doValidateSchema(getSolProvOCPendienteJC(), content)) {
				// Solicitud Ordenes Pendientes
				logger.info("Obteniendo SolicitudProveedoresXSD");
				try {
					SolicitudProveedoresOrdenesPendientes request = (SolicitudProveedoresOrdenesPendientes) getUnmarshalObject(getSolProvOCPendienteJC(), content);

					// Comienzo con transacción
					usertransaction = ctx.getUserTransaction();
					usertransaction.setTransactionTimeout(2000);
					usertransaction.begin();

					// Se procesa el mensaje
					xmltopendingsoavendors.processMessage(request);

					// Commit de la transacción
					usertransaction.commit();

				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("Error : " + ex.getMessage());
					logger.error("Procediendo a rollback en SOAHandler...");
					rollback(usertransaction);
					logger.error("Rollback en SOAHandler finalizado");
				}
			}
			else{
				logger.info("El mensaje no se puede validar contra ningun XSD");
				logger.info(content);
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
	//respaldo mensaje
	private void doBackUpMessage(String content, String number, String msgType) {
		try {
			executor.submit(new BackUpUtils(content, number, msgType));
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		}
	}
}
