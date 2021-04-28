package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.SectionW;
import bbr.b2b.logistic.customer.entities.Buyer;
import bbr.b2b.logistic.customer.entities.Section;

@Stateless(name = "servers/customer/SectionServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SectionServer extends LogisticElementServer<Section, SectionW> implements SectionServerLocal {

	@EJB
	BuyerServerLocal buyerserver;
	
	public SectionW addSection(SectionW section) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SectionW) addElement(section);
	}
	public SectionW[] getSection() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SectionW[]) getIdentifiables();
	}
	public SectionW updateSection(SectionW section) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SectionW) updateElement(section);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Section entity, SectionW wrapper) {
		wrapper.setBuyerid(entity.getBuyer() != null ? new Long(entity.getBuyer().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Section entity, SectionW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getBuyerid() != null && wrapper.getBuyerid() > 0) { 
			Buyer buyer = buyerserver.getReferenceById(wrapper.getBuyerid());
			if (buyer != null) { 
				entity.setBuyer(buyer);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Section";
	}
	@Override
	protected void setEntityname() {
		entityname = "Section";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
