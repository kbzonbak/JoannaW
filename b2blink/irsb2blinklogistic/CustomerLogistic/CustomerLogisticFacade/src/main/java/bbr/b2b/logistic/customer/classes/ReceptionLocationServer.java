package bbr.b2b.logistic.customer.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionLocationW;
import bbr.b2b.logistic.customer.entities.ReceptionLocation;
import bbr.b2b.logistic.customer.entities.ReceptionLocationId;

@Stateless(name = "servers/customer/ReceptionLocationServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReceptionLocationServer extends BaseLogisticEJB3Server<ReceptionLocation, ReceptionLocationId, ReceptionLocationW> implements ReceptionLocationServerLocal {


	public ReceptionLocationW addReceptionLocation(ReceptionLocationW receptionlocation) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionLocationW) addIdentifiable(receptionlocation);
	}
	public ReceptionLocationW[] getReceptionLocations() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionLocationW[]) getIdentifiables();
	}
	public ReceptionLocationW updateReceptionLocation(ReceptionLocationW receptionlocation) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionLocationW) updateIdentifiable(receptionlocation);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ReceptionLocation entity, ReceptionLocationW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(ReceptionLocation entity, ReceptionLocationW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "ReceptionLocation";
	}
	@Override
	protected void setEntityname() {
		entityname = "ReceptionLocation";
	}
}
