package bbr.b2b.logistic.datings.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.datings.data.wrappers.ReserveDetailW;
import bbr.b2b.logistic.datings.entities.ReserveDetail;
import bbr.b2b.logistic.datings.entities.ReserveDetailId;
import bbr.b2b.logistic.datings.report.classes.PreDatingReserveDetailDTO;
import bbr.b2b.logistic.datings.report.classes.ReserveDetailBlockingDataDTO;
import bbr.b2b.logistic.datings.report.classes.ReserveDetailDTO;

public interface IReserveDetailServer extends IGenericServer<ReserveDetail, ReserveDetailId, ReserveDetailW> {

	ReserveDetailW addReserveDetail(ReserveDetailW reservedetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReserveDetailW[] getReserveDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReserveDetailW updateReserveDetail(ReserveDetailW reservedetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReserveDetailDTO[] getReserveDetailsByDateAndLocationExcludeVendor(LocalDateTime since, LocalDateTime until, Long locationid, Long vendorid) throws OperationFailedException, NotFoundException;
	ReserveDetailW[] getReserveDetailByVendorDateLocation(LocalDateTime when, Long locationid, Long vendorid) throws NotFoundException, OperationFailedException;
	ReserveDetailW[] getReserveDetailByIds(Long[] reserveids) throws NotFoundException, OperationFailedException; 
	PreDatingReserveDetailDTO[] getPreDatingReserveDetailByLocationAndDate(Long vendorid, Long locationid, LocalDateTime date, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException;
	int countPreDatingReserveDetailByLocationAndDate(Long vendorid, Long locationid, LocalDateTime date) throws AccessDeniedException;
	ReserveDetailW[] getReserveDetailsOfBlockingGroup(Long blockinggroupid) throws NotFoundException, OperationFailedException;
	ReserveDetailW[] getReserveDetailByDateLocationDockModule(LocalDateTime when, Long locationid, Long dockid, Long moduleid) throws NotFoundException, OperationFailedException;
	ReserveDetailW[] getReserveDetailsByDateAnLocation(LocalDateTime since, LocalDateTime until, Long locationid) throws NotFoundException, OperationFailedException;
	ReserveDetailBlockingDataDTO[] getReserveDetailDataOfBlockingGroup(Long blockinggroupid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException;
	int countReserveDetailDataOfBlockingGroup(Long blockinggroupid) throws AccessDeniedException;
}
