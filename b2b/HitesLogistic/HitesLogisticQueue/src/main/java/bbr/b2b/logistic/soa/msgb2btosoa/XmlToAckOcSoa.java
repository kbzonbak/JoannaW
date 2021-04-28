package bbr.b2b.logistic.soa.msgb2btosoa;

import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden.NotificacionReciboOrden;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderServerLocal;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderW;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateTypeServerLocal;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderW;
import bbr.b2b.logistic.notifications.managers.interfaces.NotificationsReportManagerLocal;
import bbr.b2b.logistic.soa.classes.SOAStateServerLocal;
import bbr.b2b.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.logistic.soa.data.classes.SOAStateTypeW;
import bbr.b2b.logistic.soa.data.classes.SOAStateW;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;

@Stateless(name = "handlers/XmlToAckOcSoa")
public class XmlToAckOcSoa implements XmlToAckOcSoaLocal {

	private static Logger logger = Logger.getLogger("SOALog");

	@EJB
	VendorServerLocal vendorserver = null;

	@EJB
	OrderServerLocal orderserver = null;
	
	@EJB
	DvrOrderServerLocal dvrorderserver = null;
	
	@EJB
	DdcOrderServerLocal ddcorderserver = null;

	@EJB
	DvrOrderStateTypeServerLocal orderstatetypeserver = null;

	@EJB
	DvrOrderStateServerLocal orderstateserver = null;

	@EJB
	SOAStateTypeServerLocal soastatetypeserver;

	@EJB
	SOAStateServerLocal soastateserver;

	@EJB
	NotificationsReportManagerLocal notificationmanager;

	private void doValidateAckOcSoaMessage(NotificacionReciboOrden message) throws LoadDataException, OperationFailedException {

		// NOMBRE PORTAL
		if (message.getNombreportal() == null || message.getNombreportal().trim().equals("")) {
			throw new LoadDataException("No hay datos para Nombre de Portal");
		}

		// CODIGO PROVEEDOR
		if (message.getCodproveedor() == null || message.getCodproveedor().trim().equals("")) {
			throw new LoadDataException("No hay datos para Codigo Proveedor");
		}

		// CODIGO COMPRADOR
		if (message.getCodcomprador() == null || message.getCodcomprador().trim().equals("")) {
			throw new LoadDataException("No hay datos para Codigo Comprador");
		}

		// NUMERO DE OC
		if (message.getNumorden() == null || message.getNumorden().trim().equals("")) {
			throw new LoadDataException("No hay datos para Numero de OC");
		}

		// ARCHIVO
		if (message.getNombreportal() == null || message.getNombreportal().trim().equals("")) {
			throw new LoadDataException("No hay datos para Archivo");
		}

		// ESTADO
		if (message.getEstado() == null || message.getEstado().trim().equals("")) {
			throw new LoadDataException("No hay datos para Estado");
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean processMessage(NotificacionReciboOrden message) {
		try {
			SOAStateTypeW recOkSt = soastatetypeserver.getByPropertyAsSingleResult("code", "RECIBIDO_OK");
			SOAStateTypeW recErrSt = soastatetypeserver.getByPropertyAsSingleResult("code", "RECIBIDO_ERROR");

			// VALIDACION DEL MENSAJE
			doValidateAckOcSoaMessage(message);

			// PROCESAMIENTO DEL MENSAJE
			// VERIFICA QUE EL PORTAL CORRESPONDA
			String nombrePortal = B2BSystemPropertiesUtil.getProperty("NombrePortal");
			String nombrePortalMsg = message.getNombreportal();

			if (!nombrePortal.equalsIgnoreCase(nombrePortalMsg)) {
				throw new LoadDataException("El nombre de portal no corresponde, OC:" + message.getNumorden());
			}

			// ORDEN
			Long orderNumber = null;
			try {
				orderNumber = new Long(message.getNumorden());
			} catch (Exception e) {
				throw new LoadDataException("El n�mero de oc no es n�merico, OC:" + message.getNumorden());
			}

			// PROVEEDOR
			String codProveedor = message.getCodproveedor();
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", codProveedor);

			if (vendorArr == null || vendorArr.length == 0) {
				throw new LoadDataException("No existe proveedor con c�digo " + codProveedor + ", OC:" + message.getNumorden());
			}
			

			// COMPRADOR
			String codComprador = message.getCodcomprador();

			// VALIDA SI LA OC EXISTE PARA EL PROVEEDOR Y COMPRADOR
			List<OrderW> orderArr = orderserver.getByQuery("select oc from Order as oc where oc.vendor.id = " + vendorArr[0].getId() + " and oc.number = " + orderNumber);

			if (orderArr == null || orderArr.size() == 0) {
				throw new LoadDataException("La Orden número " + orderNumber + " para el proveedor " + codProveedor + " y el comprador " + codComprador + " no existe");
			}

			OrderW order =orderArr.get(0);
			
			DvrOrderW dvrorder = new DvrOrderW();
			DdcOrderW ddcorder = new DdcOrderW();
			boolean isDvr = false;
			try {
				dvrorder = dvrorderserver.getById(order.getId());
				isDvr = true;
			} catch (Exception e) {
				ddcorder = ddcorderserver.getById(order.getId());
			}

			LocalDateTime now = LocalDateTime.now();

			// ESTADO
			String stateMsg = message.getEstado();

			boolean recOk = false;

			// VALIDA SI EL ESTADO ES RECIBIDO OK O CON ERROR
			if (stateMsg.equalsIgnoreCase("OK")) {
				recOk = true;
			} else if (stateMsg.equalsIgnoreCase("ERROR")) {
				recOk = false;
			} else {
				throw new LoadDataException("El estado " + stateMsg + " no corresponde a un estado válido");
			}

			// ARCHIVO
			String fileName = message.getArchivo();


			// NUEVO ESTADO
			SOAStateW state = new SOAStateW();
			state.setOrderid(order.getId());
			state.setSoastatetypeid(recOk ? recOkSt.getId() : recErrSt.getId());
			state.setWhen(now);
			state.setComment(fileName);

			state = soastateserver.addSOAState(state);
			
		

			// SE ALMACENA LA OC EN ESTADO SOA RECIBIDO
			if(isDvr){
				dvrorder.setCurrentsoastatetypedate(now);
				dvrorder.setCurrentsoastatetypeid(recOk ? recOkSt.getId() : recErrSt.getId());
				
				dvrorderserver.updateDvrOrder(dvrorder);
			}else{
				ddcorder.setCurrentsoastatetypedate(now);
				ddcorder.setCurrentsoastatetypeid(recOk ? recOkSt.getId() : recErrSt.getId());
				ddcorderserver.updateDdcOrder(ddcorder);
			}
			
//			if (order.getCurrentsoastatetypeid().equals(recOkSt.getId())) {
//				// Crear notificaci�n log�stica de �xito SOA
//				try {
//					notificationmanager.doAddNotificationData(order.getVendorid(), order.getNumber() + "", "SOA_OK");
//
//				} catch (Exception e) {
//					logger.info("Error al agregar notificación de éxito SOA para OC " + order.getNumber());
//					e.printStackTrace();
//				}
//			} else if (order.getCurrentsoastatetypeid().equals(recErrSt.getId())) {
//				// Crear notificaci�n log�stica de error SOA
//				try {
//					notificationmanager.doAddNotificationData(order.getVendorid(), order.getNumber() + "", "SOA_ERROR");
//
//				} catch (Exception e) {
//					logger.info("Error al agregar notificación de error SOA para OC " + order.getNumber());
//					e.printStackTrace();
//				}
//			}
			if(isDvr){
				logger.info("La OC (DVR) " + order.getNumber() + " cambio a estado SOA recibido");
			}else{
				logger.info("La OC (DDC) " + order.getNumber() + " cambio a estado SOA recibido");
			}
			
			return true;
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			return false;
		}
	}
}
