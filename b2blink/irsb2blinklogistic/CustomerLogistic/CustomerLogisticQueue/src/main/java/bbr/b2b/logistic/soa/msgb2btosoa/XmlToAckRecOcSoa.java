package bbr.b2b.logistic.soa.msgb2btosoa;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.NotificacionReciboRec.NotificacionReciboRec;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.customer.classes.ReceptionServerLocal;
import bbr.b2b.logistic.customer.classes.SoaRecStateServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.VendorServerLocal;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionW;
import bbr.b2b.logistic.customer.data.wrappers.SoaRecStateW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateTypeW;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;


@Stateless(name = "handlers/XmlToAckRecOcSoa")
public class XmlToAckRecOcSoa implements XmlToAckRecOcSoaLocal {

	private static Logger logger = Logger.getLogger("SOALog");

	@EJB
	VendorServerLocal vendorserver = null;

	@EJB
	SoaStateTypeServerLocal soastatetypeserver;
	
	@EJB
	ReceptionServerLocal receptionserver;
	
	@EJB
	SoaRecStateServerLocal soarecstateserver;
	
	
	private void doValidateAckRecOcSoaMessage(NotificacionReciboRec message) throws LoadDataException, OperationFailedException {
		
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
		if (message.getNumrecepcion() == null || message.getNumrecepcion().trim().equals("")){
			throw new LoadDataException("No hay datos para Numero de REC");
		}
		
		// ESTADO
		if (message.getEstado() == null || message.getEstado().trim().equals("")){
			throw new LoadDataException("No hay datos para Estado");
		}		
	}
	
	public void processMessage(NotificacionReciboRec message) throws ServiceException {
				
		SoaStateTypeW recOkSt = soastatetypeserver.getByPropertyAsSingleResult("code", "RECIBIDO_OK");
		SoaStateTypeW recErrSt = soastatetypeserver.getByPropertyAsSingleResult("code", "RECIBIDO_ERROR");
				
		// VALIDACION DEL MENSAJE
		doValidateAckRecOcSoaMessage(message);
		
		// PROCESAMIENTO DEL MENSAJE 
		// VERIFICA QUE EL PORTAL CORRESPONDA
		String nombrePortal = B2BSystemPropertiesUtil.getProperty("NombrePortal");
		String nombrePortalMsg = message.getNombreportal();
			
		if (!nombrePortal.equalsIgnoreCase(nombrePortalMsg)){
			throw new LoadDataException("El nombre de portal no corresponde");
		}		
		
		// RECEPCION
		Long receptionNumber = null;
		try{
			receptionNumber = new Long(message.getNumrecepcion());
		}catch (Exception e) {
			throw new LoadDataException("El número de la recepción no es númerico");
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
		List<ReceptionW> receptionArr = receptionserver.getByQuery("select rec from Reception as rec where rec.vendor.id = " + vendorArr[0].getId() + " and rec.receptionnumber = " + receptionNumber );
		
		if (receptionArr == null || receptionArr.size() == 0){
			throw new LoadDataException("La Recepción número " + receptionNumber + " para el proveedor " + codProveedor + " y el comprador " + codComprador + " no existe");
		}
		
		ReceptionW reception = receptionArr.get(0);		
		
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
		reception.setSoastatetypeid(recOk ? recOkSt.getId() : recErrSt.getId());
		
		// nuevo estado soa
		SoaRecStateW state = new SoaRecStateW();
		state.setReceptionid(reception.getId());
		state.setSoastatetypeid(recOk ? recOkSt.getId() : recErrSt.getId());
		state.setWhen(now);
		
		state = soarecstateserver.addSoaRecState(state);
		
		// ACTUALIZA LA OC
		reception = receptionserver.updateReception(reception);
		
		logger.info("La OC " + reception.getReceptionnumber() + " cambio a estado SOA recibido" + "("+stateMsg+")");		
	}		
}
