package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionLocationW;
import bbr.b2b.logistic.customer.entities.ReceptionLocation;
import bbr.b2b.logistic.customer.entities.ReceptionLocationId;
public interface IReceptionLocationServer extends IGenericServer<ReceptionLocation, ReceptionLocationId, ReceptionLocationW> {

	ReceptionLocationW addReceptionLocation(ReceptionLocationW receptionlocation) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReceptionLocationW[] getReceptionLocations() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReceptionLocationW updateReceptionLocation(ReceptionLocationW receptionlocation) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
