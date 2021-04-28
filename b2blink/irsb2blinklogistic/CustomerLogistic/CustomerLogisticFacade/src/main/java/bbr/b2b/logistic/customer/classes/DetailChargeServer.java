package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.customer.data.wrappers.DetailChargeW;
import bbr.b2b.logistic.customer.entities.Detail;
import bbr.b2b.logistic.customer.entities.DetailCharge;
import bbr.b2b.logistic.customer.entities.DetailId;
import bbr.b2b.logistic.customer.entities.DetailchargeId;

@Stateless(name = "servers/customer/DetailChargeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DetailChargeServer extends BaseLogisticEJB3Server<DetailCharge, DetailchargeId, DetailChargeW> implements DetailChargeServerLocal {


	@EJB
	DetailServerLocal detailserver;

	public DetailChargeW addDetailCharge(DetailChargeW detailcharge) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DetailChargeW) addIdentifiable(detailcharge);
	}
	public DetailChargeW[] getDetailCharges() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DetailChargeW[]) getIdentifiables();
	}
	public DetailChargeW updateDetailCharge(DetailChargeW detailcharge) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DetailChargeW) updateIdentifiable(detailcharge);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DetailCharge entity, DetailChargeW wrapper) {
		wrapper.setOrderid(entity.getDetail() != null ? new Long(entity.getDetail().getId().getOrderid()) : new Long(0));
		wrapper.setSkubuyer(entity.getDetail() != null ? entity.getDetail().getId().getSkubuyer() : "");
		wrapper.setPosition(entity.getDetail() != null ? entity.getDetail().getId().getPosition() : new Integer(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DetailCharge entity, DetailChargeW wrapper) throws OperationFailedException, NotFoundException {
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
		entitylabel = "DetailCharge";
	}
	@Override
	protected void setEntityname() {
		entityname = "DetailCharge";
	}
}
