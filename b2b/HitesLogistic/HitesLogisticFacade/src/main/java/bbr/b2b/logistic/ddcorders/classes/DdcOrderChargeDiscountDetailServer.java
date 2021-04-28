package bbr.b2b.logistic.ddcorders.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderChargeDiscountDetailW;
import bbr.b2b.logistic.ddcorders.entities.DdcChargeDiscount;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderChargeDiscountDetail;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderChargeDiscountDetailId;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderDetail;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderDetailId;

@Stateless(name = "servers/ddcorders/DdcOrderChargeDiscountDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DdcOrderChargeDiscountDetailServer extends BaseLogisticEJB3Server<DdcOrderChargeDiscountDetail, DdcOrderChargeDiscountDetailId, DdcOrderChargeDiscountDetailW> implements DdcOrderChargeDiscountDetailServerLocal {

	@EJB
	DdcChargeDiscountServerLocal ddcchargediscountserver;

	@EJB
	DdcOrderDetailServerLocal ddcorderdetailserver;

	public DdcOrderChargeDiscountDetailW addDdcOrderChargeDiscountDetail(DdcOrderChargeDiscountDetailW ddcorderchargediscountdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderChargeDiscountDetailW) addIdentifiable(ddcorderchargediscountdetail);
	}
	public DdcOrderChargeDiscountDetailW[] getDdcOrderChargeDiscountDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderChargeDiscountDetailW[]) getIdentifiables();
	}
	public DdcOrderChargeDiscountDetailW updateDdcOrderChargeDiscountDetail(DdcOrderChargeDiscountDetailW ddcorderchargediscountdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderChargeDiscountDetailW) updateIdentifiable(ddcorderchargediscountdetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DdcOrderChargeDiscountDetail entity, DdcOrderChargeDiscountDetailW wrapper) {
		wrapper.setDdcchargediscountid(entity.getDdcchargediscount() != null ? new Long(entity.getDdcchargediscount().getId()) : new Long(0));
		wrapper.setDdcorderid(entity.getDdcorderdetail().getId() != null ? new Long(entity.getDdcorderdetail().getId().getDdcorderid()) : new Long(0));
		wrapper.setItemid(entity.getDdcorderdetail().getId() != null ? new Long(entity.getDdcorderdetail().getId().getItemid()) : new Long(0));
		wrapper.setPosition(entity.getDdcorderdetail().getId() != null ? new Integer(entity.getDdcorderdetail().getId().getPosition()) : new Integer(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DdcOrderChargeDiscountDetail entity, DdcOrderChargeDiscountDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDdcchargediscountid() != null && wrapper.getDdcchargediscountid() > 0) { 
			DdcChargeDiscount ddcchargediscount = ddcchargediscountserver.getReferenceById(wrapper.getDdcchargediscountid());
			if (ddcchargediscount != null) { 
				entity.setDdcchargediscount(ddcchargediscount);
			}
		}
		if ((wrapper.getDdcorderid() != null && wrapper.getDdcorderid() > 0) && (wrapper.getItemid() != null && wrapper.getItemid() > 0) && (wrapper.getPosition() != null && wrapper.getPosition() > 0)) {
			DdcOrderDetailId key = new DdcOrderDetailId();
			key.setDdcorderid(wrapper.getDdcorderid());
			key.setItemid(wrapper.getItemid());
			key.setPosition(wrapper.getPosition());
			DdcOrderDetail var = ddcorderdetailserver.getReferenceById(key);
			if (var != null) { 
				entity.setDdcorderdetail(var);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DdcOrderChargeDiscountDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "DdcOrderChargeDiscountDetail";
	}
}
