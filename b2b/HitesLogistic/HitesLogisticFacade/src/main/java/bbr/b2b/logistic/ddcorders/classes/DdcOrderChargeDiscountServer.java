package bbr.b2b.logistic.ddcorders.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderChargeDiscountW;
import bbr.b2b.logistic.ddcorders.entities.DdcChargeDiscount;
import bbr.b2b.logistic.ddcorders.entities.DdcOrder;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderChargeDiscount;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderChargeDiscountId;

@Stateless(name = "servers/ddcorders/DdcOrderChargeDiscountServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DdcOrderChargeDiscountServer extends BaseLogisticEJB3Server<DdcOrderChargeDiscount, DdcOrderChargeDiscountId, DdcOrderChargeDiscountW> implements DdcOrderChargeDiscountServerLocal {


	@EJB
	DdcChargeDiscountServerLocal ddcchargediscountserver;

	@EJB
	DdcOrderServerLocal ddcorderserver;

	public DdcOrderChargeDiscountW addDdcOrderChargeDiscount(DdcOrderChargeDiscountW ddcorderchargediscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderChargeDiscountW) addIdentifiable(ddcorderchargediscount);
	}
	public DdcOrderChargeDiscountW[] getDdcOrderChargeDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderChargeDiscountW[]) getIdentifiables();
	}
	public DdcOrderChargeDiscountW updateDdcOrderChargeDiscount(DdcOrderChargeDiscountW ddcorderchargediscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderChargeDiscountW) updateIdentifiable(ddcorderchargediscount);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DdcOrderChargeDiscount entity, DdcOrderChargeDiscountW wrapper) {
		wrapper.setDdcchargediscountid(entity.getDdcchargediscount() != null ? new Long(entity.getDdcchargediscount().getId()) : new Long(0));
		wrapper.setDdcorderid(entity.getDdcorder() != null ? new Long(entity.getDdcorder().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DdcOrderChargeDiscount entity, DdcOrderChargeDiscountW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDdcchargediscountid() != null && wrapper.getDdcchargediscountid() > 0) { 
			DdcChargeDiscount ddcchargediscount = ddcchargediscountserver.getReferenceById(wrapper.getDdcchargediscountid());
			if (ddcchargediscount != null) { 
				entity.setDdcchargediscount(ddcchargediscount);
			}
		}
		if (wrapper.getDdcorderid() != null && wrapper.getDdcorderid() > 0) { 
			DdcOrder ddcorder = ddcorderserver.getReferenceById(wrapper.getDdcorderid());
			if (ddcorder != null) { 
				entity.setDdcorder(ddcorder);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DdcOrderChargeDiscount";
	}
	@Override
	protected void setEntityname() {
		entityname = "DdcOrderChargeDiscount";
	}
}
