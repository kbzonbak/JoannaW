package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.ActionW;
import bbr.b2b.logistic.customer.entities.Action;
import bbr.b2b.logistic.customer.entities.Buyer;

@Stateless(name = "servers/customer/ActionServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ActionServer extends LogisticElementServer<Action, ActionW> implements ActionServerLocal {

	@EJB
	BuyerServerLocal buyerserver;
	
	public ActionW addAction(ActionW Action) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ActionW) addElement(Action);
	}
	public ActionW[] getActions() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ActionW[]) getIdentifiables();
	}
	public ActionW updateAction(ActionW Action) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ActionW) updateElement(Action);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Action entity, ActionW wrapper) {
		wrapper.setBuyerid(entity.getBuyer() != null ? new Long(entity.getBuyer().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Action entity, ActionW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getBuyerid() != null && wrapper.getBuyerid() > 0) { 
			Buyer buyer = buyerserver.getReferenceById(wrapper.getBuyerid());
			if (buyer != null) { 
				entity.setBuyer(buyer);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Action";
	}
	@Override
	protected void setEntityname() {
		entityname = "Action";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
