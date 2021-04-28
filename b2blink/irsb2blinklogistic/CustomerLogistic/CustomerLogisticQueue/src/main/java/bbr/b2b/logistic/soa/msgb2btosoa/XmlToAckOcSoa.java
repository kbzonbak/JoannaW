package bbr.b2b.logistic.soa.msgb2btosoa;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden.NotificacionReciboOrden;
import bbr.b2b.b2blink.logistic.xml.NotificacionReciboRec.NotificacionReciboRec;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.customer.classes.OrderServerLocal;
import bbr.b2b.logistic.customer.classes.OrderStateServerLocal;
import bbr.b2b.logistic.customer.classes.OrderStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.VendorServerLocal;
import bbr.b2b.logistic.customer.data.wrappers.OrderW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateTypeW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateW;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;


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
	SoaStateTypeServerLocal soastatetypeserver;
	
	@EJB
	SoaStateServerLocal soastateserver;
	
	
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
		
		// ESTADO
		if (message.getEstado() == null || message.getEstado().trim().equals("")){
			throw new LoadDataException("No hay datos para Estado");
		}		
	}
	
	public void processMessage(NotificacionReciboOrden message) throws ServiceException {
				
		SoaStateTypeW recOkSt = soastatetypeserver.getByPropertyAsSingleResult("code", "RECIBIDO_OK");
		SoaStateTypeW recErrSt = soastatetypeserver.getByPropertyAsSingleResult("code", "RECIBIDO_ERROR");
				
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
			throw new LoadDataException("El número de oc no es númerico");
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
		List<OrderW> orderArr = orderserver.getByQuery("select oc from Order as oc where oc.vendor.id = " + vendorArr[0].getId() + " and oc.number = " + orderNumber +" and oc.valid = "+ true);
		
		if (orderArr == null || orderArr.size() == 0){
			throw new LoadDataException("La Orden número " + orderNumber + " para el proveedor " + codProveedor + " y el comprador " + codComprador + " no existe");
		}
		
		OrderW order = orderArr.get(0);		
		
		Date now = new Date();
		
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
				
		// se cambia el estado soa de la oc
		order.setSoastatetypeid(recOk ? recOkSt.getId() : recErrSt.getId());
		
		// nuevo estado soa
		SoaStateW state = new SoaStateW();
		state.setOrderid(order.getId());
		state.setSoastatetypeid(recOk ? recOkSt.getId() : recErrSt.getId());
		state.setWhen(now);
		
		state = soastateserver.addSoaState(state);
		
		// ACTUALIZA LA OC
		order = orderserver.updateOrder(order);
		
		logger.info("La OC " + order.getNumber() + " cambio a estado SOA recibido" + "("+stateMsg+")");		
	}

}
