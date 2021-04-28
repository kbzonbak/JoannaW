package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.customer.data.wrappers.DetailDiscountW;
import bbr.b2b.logistic.customer.entities.Detail;
import bbr.b2b.logistic.customer.entities.DetailDiscount;
import bbr.b2b.logistic.customer.entities.DetailId;
import bbr.b2b.logistic.customer.entities.DetaildiscountId;

@Stateless(name = "servers/customer/DetailDiscountServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DetailDiscountServer extends BaseLogisticEJB3Server<DetailDiscount, DetaildiscountId, DetailDiscountW> implements DetailDiscountServerLocal {


	@EJB
	DetailServerLocal detailserver;

	public DetailDiscountW addDetailDiscount(DetailDiscountW detaildiscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DetailDiscountW) addIdentifiable(detaildiscount);
	}
	public DetailDiscountW[] getDetailDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DetailDiscountW[]) getIdentifiables();
	}
	public DetailDiscountW updateDetailDiscount(DetailDiscountW detaildiscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DetailDiscountW) updateIdentifiable(detaildiscount);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DetailDiscount entity, DetailDiscountW wrapper) {
		wrapper.setOrderid(entity.getDetail() != null ? new Long(entity.getDetail().getId().getOrderid()) : new Long(0));
		wrapper.setSkubuyer(entity.getDetail() != null ? entity.getDetail().getId().getSkubuyer() :"");
		wrapper.setPosition(entity.getDetail() != null ? entity.getDetail().getId().getPosition() : new Integer(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DetailDiscount entity, DetailDiscountW wrapper) throws OperationFailedException, NotFoundException {
		if ((wrapper.getOrderid() != null && wrapper.getOrderid() > 0) && (wrapper.getSkubuyer() != null && !wrapper.getSkubuyer().isEmpty())) {
			DetailId key = new DetailId();
			key.setOrderid(wrapper.getOrderid());
			key.setSkubuyer(wrapper.getSkubuyer());
			key.setPosition(wrapper.getPosition());
			Detail var = detailserver.getReferenceById(key);
			if (var != null) { 
				entity.setDetail(var);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DetailDiscount";
	}
	@Override
	protected void setEntityname() {
		entityname = "DetailDiscount";
	}
}
