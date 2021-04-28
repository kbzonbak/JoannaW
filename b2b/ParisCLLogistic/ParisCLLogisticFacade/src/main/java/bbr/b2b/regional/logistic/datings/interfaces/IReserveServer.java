package bbr.b2b.regional.logistic.datings.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.ReserveW;
import bbr.b2b.regional.logistic.datings.entities.Reserve;
import bbr.b2b.regional.logistic.datings.report.classes.ReserveDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ReserveDataDTO;

public interface IReserveServer extends IElementServer<Reserve, ReserveW> {

	ReserveW addReserve(ReserveW reserve) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReserveW[] getReserves() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReserveW updateReserve(ReserveW reserve) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ReserveDTO[] getReservesByDateAndLocation(Date since, Date until, Long locationid) throws NotFoundException, OperationFailedException;
	public ReserveDataDTO[] getReserveData(Long reserveid) throws NotFoundException, OperationFailedException;
	public void doDeleteReserves(Long[] reserveids) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
