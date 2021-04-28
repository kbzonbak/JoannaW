package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryDetail;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryDetailW;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryDetailId;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryDetailReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryItemsMobileDataDTO;
public interface IDODeliveryDetailServer extends IGenericServer<DODeliveryDetail, DODeliveryDetailId, DODeliveryDetailW> {

	DODeliveryDetailW addDODeliveryDetail(DODeliveryDetailW dodeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DODeliveryDetailW[] getDODeliveryDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DODeliveryDetailW updateDODeliveryDetail(DODeliveryDetailW dodeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DODeliveryDetailReportDataDTO[] getDODeliveryDetailReport(Long vendorid, Long deliveryid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException ;
	DODeliveryDetailReportTotalDataDTO getDODeliveryDetailCountReport(Long vendorid, Long deliveryid) throws ServiceException;
	DODeliveryItemsMobileDataDTO[] getDODeliveryItemsReport(Long dodeliveryid);
}
