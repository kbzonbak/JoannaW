package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionDetailW;
import bbr.b2b.logistic.customer.entities.ReceptionDetail;

public interface IReceptionDetailServer extends IElementServer<ReceptionDetail, ReceptionDetailW> {

	ReceptionDetailW addReceptionDetail(ReceptionDetailW receptiondetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReceptionDetailW[] getReceptionDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReceptionDetailW updateReceptionDetail(ReceptionDetailW receptiondetail) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
