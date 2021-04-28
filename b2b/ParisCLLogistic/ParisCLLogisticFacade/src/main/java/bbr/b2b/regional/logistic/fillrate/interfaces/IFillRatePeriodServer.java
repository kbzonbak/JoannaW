package bbr.b2b.regional.logistic.fillrate.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.fillrate.entities.FillRatePeriod;
import bbr.b2b.regional.logistic.fillrate.data.wrappers.FillRatePeriodW;

public interface IFillRatePeriodServer extends IElementServer<FillRatePeriod, FillRatePeriodW> {

	FillRatePeriodW addFillRatePeriod(FillRatePeriodW fillrateperiod) throws AccessDeniedException, OperationFailedException, NotFoundException;
	FillRatePeriodW[] getFillRatePeriods() throws AccessDeniedException, OperationFailedException, NotFoundException;
	FillRatePeriodW updateFillRatePeriod(FillRatePeriodW fillrateperiod) throws AccessDeniedException, OperationFailedException, NotFoundException;
	FillRatePeriodW getFillRatePeriodByMonthAndYear(String month, int year) throws AccessDeniedException, OperationFailedException, NotFoundException;
	FillRatePeriodW[] getFillRatePeriodsByLatest(int lastestperiods) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
