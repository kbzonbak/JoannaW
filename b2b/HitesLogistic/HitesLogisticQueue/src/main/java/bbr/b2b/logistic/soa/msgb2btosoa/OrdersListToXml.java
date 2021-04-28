package bbr.b2b.logistic.soa.msgb2btosoa;

import java.time.LocalDateTime;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.util.DateTimeUtils;
import bbr.b2b.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.buyorders.classes.ResponsableServerLocal;
import bbr.b2b.logistic.buyorders.classes.SectionServerLocal;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderDetailServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateTypeServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrPreDeliveryDetailServerLocal;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderW;
import bbr.b2b.logistic.dvrorders.entities.DvrOrder;
import bbr.b2b.logistic.items.classes.VendorItemServerLocal;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.soa.classes.SOAStateServerLocal;
import bbr.b2b.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.logistic.soa.data.classes.SOAStateTypeW;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;

@Stateless(name = "handlers/OrdersListToXml")
public class OrdersListToXml implements OrdersListToXmlLocal {

	private static JAXBContext jc = null;

	private static Logger logger = Logger.getLogger("SOALog");

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.OC_Interno");
		return jc;
	}

	@EJB
	LocationServerLocal locationserver = null;

	@EJB
	DvrOrderDetailServerLocal orderdetailserver = null;

	@EJB
	OrderServerLocal orderserver = null;
	
	@EJB
	DvrOrderServerLocal dvrorderserver = null;

	@EJB
	DvrOrderStateServerLocal orderstateserver = null;

	@EJB
	DvrOrderStateTypeServerLocal orderstatetypeserver = null;

	@EJB
	OrderTypeServerLocal ordertypeserver = null;

	@EJB
	DvrPreDeliveryDetailServerLocal predeliverydetailserver = null;

	@EJB
	ResponsableServerLocal responsableserver = null;

	@EJB
	SectionServerLocal sectionserver = null;

	@EJB
	VendorItemServerLocal vendoritemserver = null;

	@EJB
	VendorServerLocal vendorserver = null;

	@EJB
	SOAStateTypeServerLocal soastatetypeserver;

	@EJB
	SOAStateServerLocal soastateserver;

	@EJB
	OrderSendToXmlLocal ordersendtoxml;

	//@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@TransactionTimeout(180000)
	public void processMessage(String vendorRut, String buyerRut, boolean acceptorders) throws NotFoundException, OperationFailedException {

		// Se obtiene el proveedor en base a su rut
		VendorW vendor = null;
		VendorW[] vendors = vendorserver.getByPropertyAsArray("code", vendorRut);
		if (vendors != null && vendors.length > 0 && vendors[0] != null)
			vendor = vendors[0];
		else
			throw new NotFoundException("No existe el proveedor con el rut : " + vendorRut);

		// Se obtienen los estados de las OC: Liberada, Modificada y Aceptada
		Long[] releasedState = new Long[3]; 
		releasedState[0] = orderstatetypeserver.getByPropertyAsSingleResult("description", "Liberada").getId();
		releasedState[1] = orderstatetypeserver.getByPropertyAsSingleResult("description", "Modificada").getId();
		releasedState[2] = orderstatetypeserver.getByPropertyAsSingleResult("description", "Aceptada").getId();

		// OME 2015-10-13
		SOAStateTypeW soaNotfSt = soastatetypeserver.getByPropertyAsSingleResult("code", "NOTIFICADO");

		// IRA 2011-12-26: Se obtienen las OC con el flag de env�o a SOA en falso, para el proveedor y el comprador.
		// Adem�s,
		// se revisan las cargadas hace 2 d�as atr�s.
		LocalDateTime since = LocalDateTime.now().minusDays(5); //////////////************************TODO
		
		OrderW[] orderstosend = orderserver.getOrdersToSendSoa(vendor.getId(), since, soaNotfSt.getId());//enviar orden y en processMessage buscar el dvrorder
		
		if ((orderstosend == null || orderstosend.length == 0)) {
			logger.info("No se encontraron ordenes para enviar a SOA del proveedor RUT: " + vendorRut);
			return;
		}

		logger.info("Se encontraron "+orderstosend.length+"  ordenes para enviar a SOA del proveedor RUT: " + vendorRut);
		
		// Se recorren las ordenes liberadas para almacenarlas en un mapa
		for (int i = 0; i < orderstosend.length; i++) {
			ordersendtoxml.processMessage(orderstosend[i], vendor, acceptorders, i+1, orderstosend.length);
		}
			
	}

}
