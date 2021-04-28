package bbr.b2b.logistic.ddcorders.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcChargeDiscountW;
import bbr.b2b.logistic.ddcorders.entities.DdcChargeDiscount;

@Stateless(name = "servers/ddcorders/DdcChargeDiscountServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DdcChargeDiscountServer extends LogisticElementServer<DdcChargeDiscount, DdcChargeDiscountW> implements DdcChargeDiscountServerLocal {


	public DdcChargeDiscountW addDdcChargeDiscount(DdcChargeDiscountW ddcchargediscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcChargeDiscountW) addElement(ddcchargediscount);
	}
	public DdcChargeDiscountW[] getDdcChargeDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcChargeDiscountW[]) getIdentifiables();
	}
	public DdcChargeDiscountW updateDdcChargeDiscount(DdcChargeDiscountW ddcchargediscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcChargeDiscountW) updateElement(ddcchargediscount);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DdcChargeDiscount entity, DdcChargeDiscountW wrapper) {
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DdcChargeDiscount entity, DdcChargeDiscountW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DdcChargeDiscount";
	}
	@Override
	protected void setEntityname() {
		entityname = "DdcChargeDiscount";
	}
}
