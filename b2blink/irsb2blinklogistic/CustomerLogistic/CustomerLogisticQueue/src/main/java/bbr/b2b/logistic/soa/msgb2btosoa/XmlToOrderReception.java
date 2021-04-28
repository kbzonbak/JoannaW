package bbr.b2b.logistic.soa.msgb2btosoa;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Java;

import bbr.b2b.b2blink.logistic.xml.RecCustomer.Local;
import bbr.b2b.b2blink.logistic.xml.RecCustomer.Recepcion;
import bbr.b2b.b2blink.logistic.xml.RecCustomer.Recepcion.Details.Detail;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.customer.classes.BuyerServerLocal;
import bbr.b2b.logistic.customer.classes.BuyerVendorServerLocal;
import bbr.b2b.logistic.customer.classes.LocationServerLocal;
import bbr.b2b.logistic.customer.classes.OrderServerLocal;
import bbr.b2b.logistic.customer.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.customer.classes.ReceptionDetailServerLocal;
import bbr.b2b.logistic.customer.classes.ReceptionLocationServerLocal;
import bbr.b2b.logistic.customer.classes.ReceptionServerLocal;
import bbr.b2b.logistic.customer.classes.SoaRecStateServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.VendorServerLocal;
import bbr.b2b.logistic.customer.data.wrappers.BuyerVendorW;
import bbr.b2b.logistic.customer.data.wrappers.BuyerW;
import bbr.b2b.logistic.customer.data.wrappers.LocationW;
import bbr.b2b.logistic.customer.data.wrappers.OrderTypeW;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionDetailW;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionLocationW;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionW;
import bbr.b2b.logistic.customer.data.wrappers.SoaRecStateW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateTypeW;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.customer.entities.ReceptionLocationId;



@Stateless(name = "handlers/XmlToOrderReception")
public class XmlToOrderReception implements XmlToOrderReceptionLocal {

	@EJB
	BuyerServerLocal buyerserver = null;

	@EJB
	OrderServerLocal orderserver = null;
	
	@EJB
	VendorServerLocal vendorserver = null;

	@EJB
	OrderTypeServerLocal ordertypeserver = null;
	
	@EJB
	ReceptionServerLocal receptionServerLocal = null;
	
	@EJB
	ReceptionDetailServerLocal receptiondetailserver = null;
	
	@EJB
	ReceptionLocationServerLocal receptionlocationserver = null;

	@EJB
	LocationServerLocal locationserver = null;

	@EJB
	SoaStateTypeServerLocal soastatetypeserver;

	@EJB
	SoaRecStateServerLocal soarecstateserver;
	
	@EJB
	BuyerVendorServerLocal buyervendorserver;


	private static Logger logger = Logger.getLogger(XmlToOrderReception.class);


	public void processMessage(Recepcion reception) throws Exception{
		
		Long receptionnumber = null;

		// Obtengo n�mero de recepci�n
		receptionnumber = reception.getReceptionnumber();

		orderserver.flush();
		
		ReceptionW rec = null;

		Date now = new Date();
		
		VendorW vendor;
		BuyerW buyer;

		// Valida que el número de recepcion no exista
		ReceptionW[] receptionArr = receptionServerLocal.getByPropertyAsArray("receptionnumber", receptionnumber);

		if (receptionArr == null || receptionArr.length == 0) {
			
			
			rec = new ReceptionW();
			
			rec.setReceptionnumber(receptionnumber);

			// FECHA DE RECEPCIÓN
			Date f_reception = null;
			String date = reception.getReceptiondate().toString();
			try {
				 f_reception = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			} catch (Exception e) {
				logger.info(e.toString());
			}
									
			try {
				buyer = buyerserver.getByPropertyAsSingleResult("code", reception.getBuyer());
			} catch (OperationFailedException ex) {
				throw new OperationFailedException("Código de comprador " + reception.getBuyer() + " no existe.");
			}
			
			try {
				vendor = vendorserver.getByPropertyAsSingleResult("code", reception.getVendor());
			} catch (Exception e) {
				throw new OperationFailedException("Código de Vendor " + reception.getVendor() + " no existe.");
			}
		
		
			try {
				PropertyInfoDTO[] propertiesVendor = new PropertyInfoDTO[2];
				propertiesVendor[0] = new PropertyInfoDTO("id.buyerid", "buyer_id", buyer.getId());
				propertiesVendor[1] = new PropertyInfoDTO("id.vendorid", "vendor_id", vendor.getId());
				List<BuyerVendorW> buyerVendorListW = buyervendorserver.getByProperties(propertiesVendor);
			} catch (Exception e) {
				throw new OperationFailedException("No existe la combinación buyer - vendor: "+ buyer.getCode() + "- "+vendor.getCode());
			}
			
			
			rec.setVendorid(vendor.getId());
			rec.setBuyerid(buyer.getId());
			
			if(reception.getOrdertype() == null || reception.getOrdertype().isEmpty()){
				rec.setOrdertypeid(-1L);
				rec.setOrdertypename("");
			}else{
				OrderTypeW ordertype = createOrderTypeIfNotExist(buyer.getId(), reception.getOrdertype(), reception.getOrdertypename());
				rec.setOrdertypeid(ordertype.getId());
				rec.setOrdertypename(reception.getOrdertypename());
			}
			
			rec.setComplete(reception.isComplete());
			
			if(reception.getDeliveryplace() != null){
				Long deliveryPlaceId = createLocationIfNotExist(buyer.getId(), reception.getDeliveryplace());
				rec.setDeliveryplaceid(deliveryPlaceId);
			}
			rec.setOrdernumber(reception.getOrdernumber());
			rec.setObservation(reception.getObservation());
			rec.setPaymentcondition(reception.getPaymentcondition());
			rec.setReceptiondate(f_reception);
			rec.setReceptionnumber(receptionnumber);
			rec.setResponsible(reception.getResponsible());
			rec.setTotal(reception.getTotal());
			
			SoaStateTypeW porNotSt = soastatetypeserver.getByPropertyAsSingleResult("code", "POR_NOTIFICAR");
			rec.setSoastatetypeid(porNotSt.getId());
			
			// SE AGREGA RECEPCION
			rec = receptionServerLocal.addReception(rec);
			
			SoaRecStateW soarecstate = new SoaRecStateW();
			soarecstate.setComment("");
			soarecstate.setReceptionid(rec.getId());
			soarecstate.setSoastatetypeid(porNotSt.getId());
			soarecstate.setWhen(now);
			
			// SE AGREGA HISTORIAL SOA 
			soarecstateserver.addSoaRecState(soarecstate);
			
			
			// SE AGREGA RECEPTION DETAIL
			for (Detail recdetail: reception.getDetails().getDetail()) {
				
				ReceptionDetailW receptiondetail = new ReceptionDetailW();
				
				receptiondetail.setCodeumc(recdetail.getCodeumc());
				receptiondetail.setDescriptionumb(recdetail.getDescriptionumb());
				receptiondetail.setDescriptionumc(recdetail.getDescriptionumc());
				receptiondetail.setEan13(recdetail.getEan13());
				receptiondetail.setFinalcost(recdetail.getFinalcost());
				receptiondetail.setListcost(recdetail.getListcost());
				receptiondetail.setPosition(recdetail.getPosition());
				receptiondetail.setProductdescription(recdetail.getProductdescription());
				receptiondetail.setQuantityumc(recdetail.getQuantityumc());
				receptiondetail.setReceivedquantity((double)(recdetail.getReceivedquantity()));
				receptiondetail.setReceptionid(rec.getId());
				receptiondetail.setSkubuyer(recdetail.getSkubuyer());
				receptiondetail.setSkuvendor(recdetail.getSkuvendor());
				
				receptiondetailserver.addReceptionDetail(receptiondetail);
				
			}
			
			for (Local loc : reception.getLocals().getLocal()) {
				
				ReceptionLocationW recloc = new ReceptionLocationW();
				Long locid = createLocationIfNotExist(buyer.getId(), loc);
				recloc.setReceptionid(rec.getId());
				recloc.setLocationid(locid);
				
				ReceptionLocationId receptionLocationId = new ReceptionLocationId();
				receptionLocationId.setLocationid(locid);
				receptionLocationId.setReceptionid( rec.getId());
				
				try {
					ReceptionLocationW lista =  receptionlocationserver.getById(receptionLocationId);
				} catch (Exception e) {
						receptionlocationserver.addReceptionLocation(recloc);
				}
				
			}
		}else{
			logger.info("La recepción "+reception.getReceptionnumber() + "ya se encuentra cargada en el sistema");
		}

	}
	
	private OrderTypeW createOrderTypeIfNotExist(Long buyerid, String code, String name)
			throws OperationFailedException, NotFoundException, AccessDeniedException {
		PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
		properties[0] = new PropertyInfoDTO("buyer.id", "buyer_id", buyerid);
		properties[1] = new PropertyInfoDTO("code", "code", code);
		List<OrderTypeW> listOrderTypeW = ordertypeserver.getByProperties(properties);
		if (listOrderTypeW.size() == 0) {
			OrderTypeW orderTypeW = new OrderTypeW();
			orderTypeW.setBuyerid(buyerid);
			orderTypeW.setCode(code);
			orderTypeW.setName(name);
			return ordertypeserver.addOrderType(orderTypeW);
		} else if (listOrderTypeW.size() == 1) {
			return listOrderTypeW.get(0);
		} else {
			throw new OperationFailedException("Existe mas de un tipo de orden para el código");
		}
	}
	
	private Long createLocationIfNotExist(Long buyerid, Local local) throws AccessDeniedException, OperationFailedException, NotFoundException {

		PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
		properties[0] = new PropertyInfoDTO("buyer.id", "buyer_id", buyerid);
		properties[1] = new PropertyInfoDTO("code", "code", local.getCode());
		List<LocationW> listDeliveryPlaceW = locationserver.getByProperties(properties);
		if (listDeliveryPlaceW.size() == 0) {
			LocationW deliveryPlaceW = new LocationW();
			deliveryPlaceW.setBuyerid(buyerid);
			deliveryPlaceW.setCode(local.getCode());
			deliveryPlaceW.setName(local.getName());
			deliveryPlaceW = locationserver.addLocation(deliveryPlaceW);
			return deliveryPlaceW.getId();
		} else if (listDeliveryPlaceW.size() == 1) {
			return listDeliveryPlaceW.get(0).getId();
		} else {
			throw new OperationFailedException("Existe mas de un local para el código");
		}
	}


}
