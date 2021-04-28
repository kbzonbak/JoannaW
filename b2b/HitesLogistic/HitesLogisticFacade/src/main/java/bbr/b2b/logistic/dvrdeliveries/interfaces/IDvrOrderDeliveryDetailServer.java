package bbr.b2b.logistic.dvrdeliveries.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.datings.report.classes.AssignedDatingTotalizerByDockDTO;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrOrderDeliveryDetailW;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetail;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetailId;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailExcelReportResultDTO;
public interface IDvrOrderDeliveryDetailServer extends IGenericServer<DvrOrderDeliveryDetail, DvrOrderDeliveryDetailId, DvrOrderDeliveryDetailW> {

	DvrOrderDeliveryDetailW addDvrOrderDeliveryDetail(DvrOrderDeliveryDetailW dvrorderdeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrOrderDeliveryDetailW[] getDvrOrderDeliveryDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrOrderDeliveryDetailW updateDvrOrderDeliveryDetail(DvrOrderDeliveryDetailW dvrorderdeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	AssignedDatingTotalizerByDockDTO[] getAssignedDatingTotalizerDockByDateAndLocation(LocalDateTime since, LocalDateTime until, Long locationid);
	DvrDeliveryDetailExcelReportResultDTO getDvrDeliveryDetailExcelReportByDelivery(Long dvrdeliveryid);
	int countDvrDeliveryDetailExcelReportByDelivery(Long dvrdeliveryid);
	int doUpdateDvrOrderDeliveryDetails(Long dvrdeliveryid);
	void deleteDvrOrderDeliveryDetailByDvrDeliveryById(Long dvrdeliveryid);
}
