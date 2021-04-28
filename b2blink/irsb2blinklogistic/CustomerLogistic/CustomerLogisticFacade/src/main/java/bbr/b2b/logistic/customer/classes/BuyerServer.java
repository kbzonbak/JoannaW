package bbr.b2b.logistic.customer.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.BuyerW;
import bbr.b2b.logistic.customer.entities.Buyer;

@Stateless(name = "servers/customer/BuyerServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BuyerServer extends LogisticElementServer<Buyer, BuyerW> implements BuyerServerLocal {


	public BuyerW addBuyer(BuyerW buyer) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BuyerW) addElement(buyer);
	}
	public BuyerW[] getBuyers() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BuyerW[]) getIdentifiables();
	}
	public BuyerW updateBuyer(BuyerW buyer) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BuyerW) updateElement(buyer);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Buyer entity, BuyerW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Buyer entity, BuyerW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Buyer";
	}
	@Override
	protected void setEntityname() {
		entityname = "Buyer";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
