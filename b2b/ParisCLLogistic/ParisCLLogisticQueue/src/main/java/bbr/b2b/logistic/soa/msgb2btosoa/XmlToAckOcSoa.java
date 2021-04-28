package bbr.b2b.logistic.soa.msgb2btosoa;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden.NotificacionReciboOrden;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.soa.classes.SOAStateServerLocal;
import bbr.b2b.regional.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.regional.logistic.soa.data.classes.SOAStateTypeW;
import bbr.b2b.regional.logistic.soa.data.classes.SOAStateW;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;


@Stateless(name = "handlers/XmlToAckOcSoa")
public class XmlToAckOcSoa implements XmlToAckOcSoaLocal {

	private static Logger logger = Logger.getLogger("SOALog");

	@EJB
	VendorServerLocal vendorserver = null;

	@EJB
	OrderServerLocal orderserver = null;
	
	@EJB
	OrderStateTypeServerLocal orderstatetypeserver = null;
	
	@EJB
	OrderStateServerLocal orderstateserver = null;
	
	@EJB
	SOAStateTypeServerLocal soastatetypeserver;
	
	@EJB
	SOAStateServerLocal soastateserver;
	
	
	private void doValidateAckOcSoaMessage(NotificacionReciboOrden message) throws LoadDataException, OperationFailedException {
		
		// NOMBRE PORTAL
		if (message.getNombreportal() == null || message.getNombreportal().trim().equals("")){
			throw new LoadDataException("No hay datos para Nombre de Portal");
		}
				
		// CODIGO PROVEEDOR
		if (message.getCodproveedor() == null || message.getCodproveedor().trim().equals("")){
			throw new LoadDataException("No hay datos para Codigo Proveedor");
		}
				
		// CODIGO COMPRADOR
		if (message.getCodcomprador() == null || message.getCodcomprador().trim().equals("")){
			throw new LoadDataException("No hay datos para Codigo Comprador");
		}
		
		// NUMERO DE OC
		if (message.getNumorden() == null || message.getNumorden().trim().equals("")){
			throw new LoadDataException("No hay datos para Numero de OC");
		}
		
		// ARCHIVO 
		if (message.getNombreportal() == null || message.getNombreportal().trim().equals("")){
			throw new LoadDataException("No hay datos para Archivo");
		}
		
		// ESTADO
		if (message.getEstado() == null || message.getEstado().trim().equals("")){
			throw new LoadDataException("No hay datos para Estado");
		}		
	}
	
	public void processMessage(NotificacionReciboOrden message) throws ServiceException {
				
		SOAStateTypeW notifSt = soastatetypeserver.getByPropertyAsSingleResult("code", "NOTIFICADO");
		SOAStateTypeW envSt = soastatetypeserver.getByPropertyAsSingleResult("code", "ENVIADO");
		
		SOAStateTypeW recOkSt = soastatetypeserver.getByPropertyAsSingleResult("code", "RECIBIDO_OK");
		SOAStateTypeW recErrSt = soastatetypeserver.getByPropertyAsSingleResult("code", "RECIBIDO_ERROR");
				
		// VALIDACION DEL MENSAJE
		doValidateAckOcSoaMessage(message);
		
		// PROCESAMIENTO DEL MENSAJE 
		// VERIFICA QUE EL PORTAL CORRESPONDA
		String nombrePortal = B2BSystemPropertiesUtil.getProperty("NombrePortal");
		String nombrePortalMsg = message.getNombreportal();
			
		if (!nombrePortal.equalsIgnoreCase(nombrePortalMsg)){
			throw new LoadDataException("El nombre de portal no corresponde");
		}		
		
		// ORDEN
		Long orderNumber = null;
		try{
			orderNumber = new Long(message.getNumorden());
		}catch (Exception e) {
			throw new LoadDataException("El N°mero de oc no es N°merico");
		}
		
		// PROVEEDOR
		String codProveedor = message.getCodproveedor();
		VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", codProveedor); 
		
		if (vendorArr == null || vendorArr.length == 0){
			throw new LoadDataException("No existe proveedor con código " + codProveedor);
		}
					
		// COMPRADOR
		String codComprador = message.getCodcomprador();
						
		// VALIDA SI LA OC EXISTE PARA EL PROVEEDOR Y COMPRADOR
		List<OrderW> orderArr = orderserver.getByQuery("select oc from Order as oc where oc.vendor.id = " + vendorArr[0].getId() + " and oc.number = " + orderNumber);
		
		if (orderArr == null || orderArr.size() == 0){
			throw new LoadDataException("La Orden N°mero " + orderNumber + " para el proveedor " + codProveedor + " y el comprador " + codComprador + " no existe");
		}
		
		OrderW order = orderArr.get(0);		
		
		Date now = new Date();
		
		//2018-02-2018: debe aceptar todos los ack
		// VERIFICA QUE LA OC SE ENCUENTRE EN ESTADO NOTIFICADO O ENVIADO
//		if (!order.getCurrentsoastatetypeid().equals(envSt.getId()) && !order.getCurrentsoastatetypeid().equals(notifSt.getId())){
//			throw new LoadDataException("La orden debe estar en estado Notificado o Enviado");
//		}
		
		// ESTADO
		String stateMsg = message.getEstado();
		
		boolean recOk = false;
		
		// VALIDA SI EL ESTADO ES RECIBIDO OK O CON ERROR
		if (stateMsg.equalsIgnoreCase("OK")){			
			recOk = true;
		}
		else if (stateMsg.equalsIgnoreCase("ERROR")){
			recOk = false;			
		}
		else{
			throw new LoadDataException("El estado " + stateMsg + " no corresponde a un estado válido");
		}
				
		// ARCHIVO
		String  fileName = message.getArchivo();
		
		// SE ALMACENA LA OC EN ESTADO SOA RECIBIDO
		order.setCurrentsoastatetypedate(now);
		order.setCurrentsoastatetypeid(recOk ? recOkSt.getId() : recErrSt.getId());
		
		// NUEVO ESTADO
		SOAStateW state = new SOAStateW();
		state.setOrderid(order.getId());
		state.setSoastatetypeid(recOk ? recOkSt.getId() : recErrSt.getId());
		state.setWhen(now);
		state.setComment(fileName);
		
		state = soastateserver.addSOAState(state);
		
		// ACTUALIZA LA OC
		order = orderserver.updateOrder(order);
		
		logger.info("La OC "+order.getNumber()+"cambio a estado SOA recibido");		
	}		
}
