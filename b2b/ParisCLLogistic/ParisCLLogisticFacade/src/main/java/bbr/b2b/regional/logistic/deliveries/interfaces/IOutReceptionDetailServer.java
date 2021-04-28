package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.deliveries.entities.OutReceptionDetail;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OutReceptionDetailW;
import bbr.b2b.regional.logistic.deliveries.entities.OutReceptionDetailId;
public interface IOutReceptionDetailServer extends IGenericServer<OutReceptionDetail, OutReceptionDetailId, OutReceptionDetailW> {

	OutReceptionDetailW addOutReceptionDetail(OutReceptionDetailW outreceptiondetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OutReceptionDetailW[] getOutReceptionDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OutReceptionDetailW updateOutReceptionDetail(OutReceptionDetailW outreceptiondetail) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
