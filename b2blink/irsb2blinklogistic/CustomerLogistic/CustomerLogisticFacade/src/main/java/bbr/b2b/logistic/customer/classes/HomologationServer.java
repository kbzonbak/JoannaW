package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.HomologationW;
import bbr.b2b.logistic.customer.entities.Buyer;
import bbr.b2b.logistic.customer.entities.Homologation;

@Stateless(name = "servers/customer/HomologationServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class HomologationServer extends LogisticElementServer<Homologation, HomologationW> implements HomologationServerLocal {

	@EJB
	BuyerServerLocal buyerserver;
	
	public HomologationW addHomologation(HomologationW homologation) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (HomologationW) addElement(homologation);
	}
	public HomologationW[] getHomologation() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (HomologationW[]) getIdentifiables(); 
	}
	public HomologationW updateHomologation(HomologationW homologation) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (HomologationW) updateElement(homologation);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Homologation entity, HomologationW wrapper) {
		wrapper.setBuyerid(entity.getBuyer() != null ? new Long(entity.getBuyer().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Homologation entity, HomologationW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getBuyerid() != null && wrapper.getBuyerid() > 0) { 
			Buyer buyer = buyerserver.getReferenceById(wrapper.getBuyerid());
			if (buyer != null) { 
				entity.setBuyer(buyer);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Homologation";
	}
	@Override
	protected void setEntityname() {
		entityname = "Homologation";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
