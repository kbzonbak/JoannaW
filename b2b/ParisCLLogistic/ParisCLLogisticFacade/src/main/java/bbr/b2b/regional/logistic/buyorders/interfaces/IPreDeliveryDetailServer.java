package bbr.b2b.regional.logistic.buyorders.interfaces;

import java.util.List;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.PreDeliveryDetailW;
import bbr.b2b.regional.logistic.buyorders.entities.PreDeliveryDetail;
import bbr.b2b.regional.logistic.buyorders.entities.PreDeliveryDetailId;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PredeliveryDetailDTO;
public interface IPreDeliveryDetailServer extends IGenericServer<PreDeliveryDetail, PreDeliveryDetailId, PreDeliveryDetailW> {

	PreDeliveryDetailW addPreDeliveryDetail(PreDeliveryDetailW predeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PreDeliveryDetailW[] getPreDeliveryDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	PreDeliveryDetailW updatePreDeliveryDetail(PreDeliveryDetailW predeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PreDeliveryDetailW[] getPreDeliveryDetailsByOrderIds(List<Long> orderids) throws OperationFailedException, NotFoundException;
	PreDeliveryDetailW[] getPreDeliveryDetailsOfOrder(Long ordernumber) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PreDeliveryDetailW[] getPreDeliveryDetailsOfOrderByFlowType(Long orderid, Long flowtypeid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PreDeliveryDetailReportDataDTO[] getPreDeliveryDetailsByOrder(Long orderid, Long vendorid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException;
	PreDeliveryDetailReportTotalDataDTO getPreDeliveryDetailsCountByOrder(Long orderid,  Long vendorid) throws ServiceException;
	PredeliveryDetailDTO[] getPredeliveryDetailByOrders(Long[] orderids) throws OperationFailedException, NotFoundException;
	PreDeliveryDetailW[] getPreDeliveryDetailsOfOrderByItems(Long orderid, Long... itemid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PreDeliveryDetailW[] getPreDeliveryDetailsOfOrderByFlowType(Long[] orderid, Long flowtypeid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PreDeliveryDetailW[] getPredeliveryDetailsByDelivery(Long deliveryid) throws OperationFailedException, NotFoundException;
}
