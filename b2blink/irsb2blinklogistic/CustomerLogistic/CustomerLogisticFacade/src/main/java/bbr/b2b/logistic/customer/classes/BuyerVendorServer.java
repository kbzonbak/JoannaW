package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.customer.data.wrappers.BuyerVendorW;
import bbr.b2b.logistic.customer.entities.BuyerVendor;
import bbr.b2b.logistic.customer.entities.BuyerVendorId;

@Stateless(name = "servers/customer/BuyerVendorServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BuyerVendorServer extends BaseLogisticEJB3Server<BuyerVendor, BuyerVendorId, BuyerVendorW>
		implements BuyerVendorServerLocal {
	@EJB
	BuyerVendorServerLocal buyervendorserver;

	public BuyerVendorW addBuyerVendor(BuyerVendorW buyervendor)
			throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BuyerVendorW) addIdentifiable(buyervendor);
	}

	public BuyerVendorW[] getBuyerVendor() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BuyerVendorW[]) getIdentifiables();
	}

	public BuyerVendorW updateBuyerVendor(BuyerVendorW buyervendor)
			throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BuyerVendorW) updateIdentifiable(buyervendor);
	}

	protected void copyRelationsEntityToWrapper(BuyerVendor entity, BuyerVendorW wrapper) {
		wrapper.setBuyerid((entity.getId() != null) ? new Long(entity.getId().getBuyerid().longValue()) : new Long(0L));
		wrapper.setVendorid(
				(entity.getId() != null) ? new Long(entity.getId().getVendorid().longValue()) : new Long(0L));
		wrapper.setCode((entity.getId() != null) ? entity.getId().getCode() : "");
	}

	protected void copyRelationsWrapperToEntity(BuyerVendor entity, BuyerVendorW wrapper) {
	}

	protected void setEntitylabel() {
		this.entitylabel = "BuyerVendor";
	}

	protected void setEntityname() {
		this.entityname = "BuyerVendor";
	}
}
