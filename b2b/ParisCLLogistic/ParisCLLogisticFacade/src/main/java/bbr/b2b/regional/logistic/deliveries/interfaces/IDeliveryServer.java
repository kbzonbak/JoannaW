package bbr.b2b.regional.logistic.deliveries.interfaces;

import java.util.List;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderContainerDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailDataDTO;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryW;
import bbr.b2b.regional.logistic.deliveries.entities.Delivery;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDeliveryOfOrdersAndFlowTypesDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.LabelDODeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListDataDTO;

public interface IDeliveryServer extends IElementServer<Delivery, DeliveryW> {

	DeliveryW addDelivery(DeliveryW delivery) throws AccessDeniedException, OperationFailedException, NotFoundException;
	void deleteDelivery(Long deliveryid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DeliveryW[] getDeliverys() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DeliveryW updateDelivery(DeliveryW delivery) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DeliveryReportDataDTO[] getDeliveryReport(String text, Long number, Long vendorid, Long originalvendorid, int filtertype, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	Integer getDeliveryCountReport(String text, Long number, Long vendorid, Long originalvendorid, int filtertype) throws OperationFailedException, AccessDeniedException;
	Long getNextSequenceDeliveryNumber() throws OperationFailedException, NotFoundException;
	ProposedPackingListDataDTO[] getProposedPackingListByDetailOrInnerpack(Long deliveryid, Long vendorid) throws OperationFailedException, AccessDeniedException;
	String getNextSequenceTransactionNumber(int length) throws OperationFailedException, NotFoundException;
	PreDeliveryDetailDataDTO[] getPreDeliveryDetailsForImportedDelivery(Long[] orderid, Long vendorid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	Integer getCountPreDeliveryDetailsForImportedDelivery(Long[] orderid, Long vendorid) throws OperationFailedException, AccessDeniedException;
	OrderContainerDataDTO[] getOrdersContainersForImportedDelivery(Long[] orderids, Long vendorid, OrderCriteriaDTO[] orderby) throws AccessDeniedException;
	LabelDODeliveryReportDataDTO[] getLabelDODeliveryReport(Long deliveryid, Long vendorid) throws ServiceException;
	DeliveryDTO[] getCencosudImportedDeliveriesByContainerDeliveryLocationFlowTypeAndCodeType(String containernumber, Long orderid, Long flowtypeid, String codetype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	AddDeliveryOfOrdersAndFlowTypesDataDTO[] getAddedDeliveriesByContainerDeliveryLocationFlowTypeAndATC(List<Long> deliveryids);
	DeliveryW[] getDeliveryByOrder(Long orderid) throws OperationFailedException, NotFoundException;
}
