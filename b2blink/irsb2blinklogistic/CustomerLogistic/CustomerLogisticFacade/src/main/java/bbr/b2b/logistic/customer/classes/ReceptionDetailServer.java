package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionDetailW;
import bbr.b2b.logistic.customer.entities.Reception;
import bbr.b2b.logistic.customer.entities.ReceptionDetail;

@Stateless(name = "servers/customer/ReceptionDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReceptionDetailServer extends LogisticElementServer<ReceptionDetail, ReceptionDetailW> implements ReceptionDetailServerLocal {


	@EJB
	ReceptionServerLocal receptionserver;

	public ReceptionDetailW addReceptionDetail(ReceptionDetailW receptiondetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionDetailW) addElement(receptiondetail);
	}
	public ReceptionDetailW[] getReceptionDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionDetailW[]) getIdentifiables();
	}
	public ReceptionDetailW updateReceptionDetail(ReceptionDetailW receptiondetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionDetailW) updateElement(receptiondetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ReceptionDetail entity, ReceptionDetailW wrapper) {
		wrapper.setReceptionid(entity.getReception() != null ? new Long(entity.getReception().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(ReceptionDetail entity, ReceptionDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getReceptionid() != null && wrapper.getReceptionid() > 0) { 
			Reception reception = receptionserver.getReferenceById(wrapper.getReceptionid());
			if (reception != null) { 
				entity.setReception(reception);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "ReceptionDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "ReceptionDetail";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
