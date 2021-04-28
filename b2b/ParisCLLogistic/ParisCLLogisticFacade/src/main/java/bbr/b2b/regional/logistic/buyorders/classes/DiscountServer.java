package bbr.b2b.regional.logistic.buyorders.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.DiscountW;
import bbr.b2b.regional.logistic.buyorders.entities.Discount;

@Stateless(name = "servers/buyorders/DiscountServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DiscountServer extends LogisticElementServer<Discount, DiscountW> implements DiscountServerLocal {


	public DiscountW addDiscount(DiscountW discount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DiscountW) addElement(discount);
	}
	public DiscountW[] getDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DiscountW[]) getIdentifiables();
	}
	public DiscountW updateDiscount(DiscountW discount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DiscountW) updateElement(discount);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Discount entity, DiscountW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Discount entity, DiscountW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Discount";
	}
	@Override
	protected void setEntityname() {
		entityname = "Discount";
	}
}
