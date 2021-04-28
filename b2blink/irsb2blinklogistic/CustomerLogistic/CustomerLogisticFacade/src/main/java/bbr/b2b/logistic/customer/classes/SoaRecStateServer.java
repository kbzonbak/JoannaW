package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.SoaRecStateW;
import bbr.b2b.logistic.customer.entities.Reception;
import bbr.b2b.logistic.customer.entities.SoaRecState;
import bbr.b2b.logistic.customer.entities.SoaStateType;

@Stateless(name = "servers/customer/SoaRecStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SoaRecStateServer extends LogisticElementServer<SoaRecState, SoaRecStateW> implements SoaRecStateServerLocal {


	@EJB
	ReceptionServerLocal receptionserver;

	@EJB
	SoaStateTypeServerLocal soastatetypeserver;

	public SoaRecStateW addSoaRecState(SoaRecStateW soarecstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SoaRecStateW) addElement(soarecstate);
	}
	public SoaRecStateW[] getSoaRecStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SoaRecStateW[]) getIdentifiables();
	}
	public SoaRecStateW updateSoaRecState(SoaRecStateW soarecstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SoaRecStateW) updateElement(soarecstate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(SoaRecState entity, SoaRecStateW wrapper) {
		wrapper.setReceptionid(entity.getReception() != null ? new Long(entity.getReception().getId()) : new Long(0));
		wrapper.setSoastatetypeid(entity.getSoastatetype() != null ? new Long(entity.getSoastatetype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(SoaRecState entity, SoaRecStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getReceptionid() != null && wrapper.getReceptionid() > 0) { 
			Reception reception = receptionserver.getReferenceById(wrapper.getReceptionid());
			if (reception != null) { 
				entity.setReception(reception);
			}
		}
		if (wrapper.getSoastatetypeid() != null && wrapper.getSoastatetypeid() > 0) { 
			SoaStateType soastatetype = soastatetypeserver.getReferenceById(wrapper.getSoastatetypeid());
			if (soastatetype != null) { 
				entity.setSoastatetype(soastatetype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "SoaRecState";
	}
	@Override
	protected void setEntityname() {
		entityname = "SoaRecState";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
