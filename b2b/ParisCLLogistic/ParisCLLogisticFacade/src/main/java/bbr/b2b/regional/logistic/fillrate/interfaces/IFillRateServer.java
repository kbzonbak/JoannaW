package bbr.b2b.regional.logistic.fillrate.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.fillrate.entities.FillRate;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateEvolutionReportDataDTO;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateReportDataDTO;
import bbr.b2b.regional.logistic.fillrate.data.wrappers.FillRateW;

public interface IFillRateServer extends IElementServer<FillRate, FillRateW> {

	FillRateW addFillRate(FillRateW fillrate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	FillRateW[] getFillRates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	FillRateW updateFillRate(FillRateW fillrate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	FillRateReportDataDTO[] getFillRatesReportByFillRatePeriod(Long fillrateperiodid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int getFillRatesCountByFillRatePeriod(Long fillrateperiodid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	FillRateEvolutionReportDataDTO[] getFillRatesReportByVendorAndDepartmentAndLatestPeriods(Long vendorid, Long departmentid, int latestperiods, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
