package bbr.b2b.logistic.soa.msgb2btosoa;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;

import org.apache.log4j.Logger;
import org.jboss.annotation.ejb.TransactionTimeout;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.customer.classes.BuyerServerLocal;
import bbr.b2b.logistic.customer.classes.BuyerVendorServerLocal;
import bbr.b2b.logistic.customer.classes.LocationServerLocal;
import bbr.b2b.logistic.customer.classes.OrderServerLocal;
import bbr.b2b.logistic.customer.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.customer.classes.ReceptionDetailServerLocal;
import bbr.b2b.logistic.customer.classes.ReceptionServerLocal;
import bbr.b2b.logistic.customer.classes.SectionServerLocal;
import bbr.b2b.logistic.customer.classes.SoaRecStateServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.VendorServerLocal;
import bbr.b2b.logistic.customer.data.wrappers.BuyerVendorW;
import bbr.b2b.logistic.customer.data.wrappers.BuyerW;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateTypeW;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;


@Stateless(name = "handlers/RecOCToXml")
public class RecOCToXml implements RecOCToXmlLocal {

	private static JAXBContext jc = null;

	private static Logger logger = Logger.getLogger("SOALog");

	@EJB
	BuyerServerLocal buyerserver = null;

	@EJB
	VendorServerLocal vendorserver = null;

	@EJB
	ReceptionServerLocal receptionserver = null;

	@EJB
	SoaStateTypeServerLocal soastatetypeserver;

	@EJB
	RecOCSendToXmlLocal recocsendtoxml;
	
	@EJB
	BuyerVendorServerLocal buyervendorserver;

	@TransactionTimeout(18000)
	public void processMessage(String vendorCode, String buyerCode) throws ServiceException {
		//verifica que existan el buyer y el vendor
		BuyerW buyer = null;
		VendorW vendor = null;
		try {
			buyer = buyerserver.getByPropertyAsSingleResult("code", buyerCode);
		} catch (Exception e) {
			throw new NotFoundException("Código de comprador " + buyerCode + " no existe.");
		}
		
		try {
			vendor = vendorserver.getByPropertyAsSingleResult("code", vendorCode);
		} catch (Exception e) {
			throw new NotFoundException("Código de Vendor " + vendorCode + " no existe.");
		}
	
		//verifica combinación buyer-vendor
		try {
			PropertyInfoDTO[] propertiesVendor = new PropertyInfoDTO[2];
			propertiesVendor[0] = new PropertyInfoDTO("id.buyerid", "buyer_id", buyer.getId());
			propertiesVendor[1] = new PropertyInfoDTO("id.vendorid", "vendor_id", vendor.getId());
			List<BuyerVendorW> buyerVendorListW = buyervendorserver.getByProperties(propertiesVendor);
		} catch (Exception e) {
			throw new NotFoundException("No existe la combinación buyer - vendor: "+ buyer.getCode() + "- "+vendor.getCode());
		}
		SoaStateTypeW soaNotfSt = soastatetypeserver.getByPropertyAsSingleResult("code", "NOTIFICADO");
		
		ReceptionW[] receptionstosend = receptionserver.getReceptionsToSendSoa(buyer.getId(),vendor.getId(),soaNotfSt.getId());
		
		if ((receptionstosend == null || receptionstosend.length == 0)) {
			logger.info("No se encontraron recepciones de OC para enviar a SOA del Buyer: " + buyerCode + "y Vendor: "+ vendorCode);
			return;
		}

		logger.info("Se encontraron " + receptionstosend.length + " recepciones para enviar a SOA del Buyer: " + buyerCode + " y Vendor: "+ vendorCode);
		for (int i = 0; i < receptionstosend.length; i++) {
			recocsendtoxml.processMessage(receptionstosend[i], vendor, buyer, i + 1, receptionstosend.length);
		}

	}
}
