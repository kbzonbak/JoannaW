package bbr.b2b.regional.logistic.fillrate.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.fillrate.entities.FillRateDetail;
import bbr.b2b.regional.logistic.fillrate.data.wrappers.FillRateDetailW;
import bbr.b2b.regional.logistic.fillrate.entities.FillRateDetailId;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateDetailReportDataDTO;
public interface IFillRateDetailServer extends IGenericServer<FillRateDetail, FillRateDetailId, FillRateDetailW> {

	FillRateDetailW addFillRateDetail(FillRateDetailW fillratedetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	FillRateDetailW[] getFillRateDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	FillRateDetailW updateFillRateDetail(FillRateDetailW fillratedetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	FillRateDetailReportDataDTO[] getFillRateDetailReportByFillRate(Long fillrateid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int getFillRateDetailCountByFillRate(Long fillrateid) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
