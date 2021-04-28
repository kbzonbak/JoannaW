package bbr.b2b.logistic.dvrdeliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.dvrdeliveries.entities.BulkDetail;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.BulkDetailW;
import bbr.b2b.logistic.dvrdeliveries.entities.BulkDetailId;
public interface IBulkDetailServer extends IGenericServer<BulkDetail, BulkDetailId, BulkDetailW> {

	BulkDetailW addBulkDetail(BulkDetailW bulkdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	BulkDetailW[] getBulkDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	BulkDetailW updateBulkDetail(BulkDetailW bulkdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
