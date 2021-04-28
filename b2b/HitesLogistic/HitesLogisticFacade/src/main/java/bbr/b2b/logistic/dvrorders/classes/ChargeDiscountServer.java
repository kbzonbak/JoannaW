package bbr.b2b.logistic.dvrorders.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.dvrorders.entities.ChargeDiscount;
import bbr.b2b.logistic.dvrorders.data.wrappers.ChargeDiscountW;

@Stateless(name = "servers/dvrorders/ChargeDiscountServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ChargeDiscountServer extends LogisticElementServer<ChargeDiscount, ChargeDiscountW> implements ChargeDiscountServerLocal {


	public ChargeDiscountW addChargeDiscount(ChargeDiscountW chargediscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChargeDiscountW) addElement(chargediscount);
	}
	public ChargeDiscountW[] getChargeDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChargeDiscountW[]) getIdentifiables();
	}
	public ChargeDiscountW updateChargeDiscount(ChargeDiscountW chargediscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChargeDiscountW) updateElement(chargediscount);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ChargeDiscount entity, ChargeDiscountW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(ChargeDiscount entity, ChargeDiscountW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "ChargeDiscount";
	}
	@Override
	protected void setEntityname() {
		entityname = "ChargeDiscount";
	}
}
