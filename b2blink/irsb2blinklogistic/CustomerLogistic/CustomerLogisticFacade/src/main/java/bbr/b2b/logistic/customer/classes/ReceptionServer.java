package bbr.b2b.logistic.customer.classes;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionW;
import bbr.b2b.logistic.customer.entities.Buyer;
import bbr.b2b.logistic.customer.entities.Location;
import bbr.b2b.logistic.customer.entities.OrderType;
import bbr.b2b.logistic.customer.entities.Reception;
import bbr.b2b.logistic.customer.entities.SoaStateType;
import bbr.b2b.logistic.customer.entities.Vendor;

@Stateless(name = "servers/customer/ReceptionServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReceptionServer extends LogisticElementServer<Reception, ReceptionW> implements ReceptionServerLocal {


	@EJB
	LocationServerLocal deliveryplaceserver;

	@EJB
	BuyerServerLocal buyerserver;

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	OrderTypeServerLocal ordertypeserver;

	@EJB
	SoaStateTypeServerLocal soastatetypeserver;

	public ReceptionW addReception(ReceptionW reception) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionW) addElement(reception);
	}
	public ReceptionW[] getReceptions() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionW[]) getIdentifiables();
	}
	public ReceptionW updateReception(ReceptionW reception) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionW) updateElement(reception);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Reception entity, ReceptionW wrapper) {
		wrapper.setDeliveryplaceid(entity.getDeliveryplace() != null ? new Long(entity.getDeliveryplace().getId()) : new Long(0));
		wrapper.setBuyerid(entity.getBuyer() != null ? new Long(entity.getBuyer().getId()) : new Long(0));
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setOrdertypeid(entity.getOrdertype() != null ? new Long(entity.getOrdertype().getId()) : new Long(0));
		wrapper.setSoastatetypeid(entity.getSoastatetype() != null ? new Long(entity.getSoastatetype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Reception entity, ReceptionW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDeliveryplaceid() != null && wrapper.getDeliveryplaceid() > 0) { 
			Location deliveryplace = deliveryplaceserver.getReferenceById(wrapper.getDeliveryplaceid());
			if (deliveryplace != null) { 
				entity.setDeliveryplace(deliveryplace);
			}
		}
		if (wrapper.getBuyerid() != null && wrapper.getBuyerid() > 0) { 
			Buyer buyer = buyerserver.getReferenceById(wrapper.getBuyerid());
			if (buyer != null) { 
				entity.setBuyer(buyer);
			}
		}
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getOrdertypeid() != null && wrapper.getOrdertypeid() > 0) { 
			OrderType ordertype = ordertypeserver.getReferenceById(wrapper.getOrdertypeid());
			if (ordertype != null) { 
				entity.setOrdertype(ordertype);
			}
		}
		if (wrapper.getSoastatetypeid() != null && wrapper.getSoastatetypeid() > 0) { 
			SoaStateType soastatetype = soastatetypeserver.getReferenceById(wrapper.getSoastatetypeid());
			if (soastatetype != null) { 
				entity.setSoastatetype(soastatetype);
			}
		}
	}
	
	public ReceptionW[] getReceptionsToSendSoa(Long buyerid, Long vendorid, Long soastateid) throws OperationFailedException, NotFoundException {
		List<PropertyInfoDTO> propertiesList = new ArrayList<PropertyInfoDTO>();
		
		propertiesList.add(new PropertyInfoDTO("rec.vendor.id", "vendorid", vendorid));
		propertiesList.add(new PropertyInfoDTO("rec.buyer.id", "buyerid", buyerid));
		propertiesList.add(new PropertyInfoDTO("rec.soastatetype.id", "soastateid", soastateid));

        StringBuilder sb = new StringBuilder("Select distinct rec FROM Reception rec " +	
    			"where rec.vendor.id = :vendorid AND rec.soastatetype.id  =:soastateid AND rec.buyer.id =:buyerid");
        
        List list = getByQuery(sb.toString(), propertiesList.toArray(new PropertyInfoDTO[propertiesList.size()]));
        return (ReceptionW[]) list.toArray(new ReceptionW[list.size()]);
	}

	
	@Override
	protected void setEntitylabel() {
		entitylabel = "Reception";
	}
	@Override
	protected void setEntityname() {
		entityname = "Reception";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
