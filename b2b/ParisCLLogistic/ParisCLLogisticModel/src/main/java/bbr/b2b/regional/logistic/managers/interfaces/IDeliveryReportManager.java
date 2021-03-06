package bbr.b2b.regional.logistic.managers.interfaces;

import java.util.HashMap;
import java.util.List;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.PreDeliveryDetailW;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderContainerDataInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderContainerDataResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailDataInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailDataResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.UploadPreDeliveryDetailDataInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.UploadPreDeliveryDetailDataResultDTO;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingRequestW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BoxW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkDetailW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.PalletW;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDeliveryOfOrdersAndFlowTypesInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDeliveryOfOrdersAndFlowTypesResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddImportedDeliveryOfOrdersInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryAvailabilityInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailReportInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailReportResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryItemsArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryReportInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryReportResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryStateTypeArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DoDeleteDeliveryInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.OrderUnitsArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListDataArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListReportInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UpdateAllRefDocumentsOfDeliveryInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadDeliveryAvailabilityInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadPackingListInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadPackingListResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadPredistributedPackingListInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadPredistributedPackingListResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ValidateDeliveryItemsArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ValidateDeliveryItemsInitParamDTO;
import bbr.b2b.regional.logistic.items.report.classes.FlowTypesInitParamDTO;
import bbr.b2b.regional.logistic.items.report.classes.FlowTypesResultDTO;
import bbr.b2b.regional.logistic.report.classes.BaseResultsDTO;

public interface IDeliveryReportManager {

	DeliveryItemsArrayResultDTO getDeliveryItemsByOrders(ValidateDeliveryItemsInitParamDTO initParams);
	ValidateDeliveryItemsArrayResultDTO getPendingItemsData(Long[] orderids, List<String> pSKUList, boolean sendmailnotification, String username, String useremail, String usertype, String vendorcode);
	OrderW doDeleteOrderInDeliveries(Long orderid) throws LoadDataException, ServiceException;	
	AddDeliveryOfOrdersAndFlowTypesResultDTO doAddDeliveryOfOrdersAndFlowTypes(AddDeliveryOfOrdersAndFlowTypesInitParamDTO initParams, boolean byVeVCD);
	FlowTypesResultDTO getFlowTypes();
	BaseResultDTO updateFlowTypes(FlowTypesInitParamDTO initParam);
	DeliveryStateTypeArrayResultDTO getDeliveryStateTypes();
	DeliveryStateTypeArrayResultDTO getAllDeliveryStateTypes();
	DeliveryReportResultDTO getDeliveryReport(DeliveryReportInitParamDTO initParamDTO, boolean byFilter);
	DeliveryDetailReportResultDTO getDeliveryDetailReport(DeliveryDetailReportInitParamDTO initParams, boolean isByFilter);
	DeliveryDetailArrayResultDTO getFileDeliveryDetailReport(DeliveryDetailInitParamDTO initParams);
	BaseResultDTO doDeleteDelivery(DoDeleteDeliveryInitParamDTO initParams);
	OrderUnitsArrayResultDTO getOrderDeliveryUnits(Long deliveryId, String vendorcode);
	ProposedPackingListResultDTO getProposedPackingListByDetailOrInnerpack(ProposedPackingListInitParamDTO initParams);
	UploadPackingListResultDTO doUploadPackingList(UploadPackingListInitParamDTO initParams);
	UploadPredistributedPackingListResultDTO doUploadPredistributedPackingListFromStockOrders(UploadPredistributedPackingListInitParamDTO initParamDTO);
	ProposedPackingListDataArrayResultDTO getProposedPackingList(ProposedPackingListReportInitParamDTO initParams, boolean isByPages);
	BaseResultDTO doUpdateOrderDeliveryAvailability(DeliveryAvailabilityInitParamDTO initParams);
	BaseResultsDTO doUploadOrderDeliveryAvailability(UploadDeliveryAvailabilityInitParamDTO initParamDTO);
	DeliveryW[] doAddDeliveriesByPackingList(OrderW[] order, DeliveryW basedelivery, DatingRequestW basedatingrequest, PalletW[] pallets, BoxW[] boxes, BulkDetailW[] bulkdetails, boolean hasatc, Long guideNumber, String importationGuide) throws AccessDeniedException, NotFoundException, OperationFailedException;
	void doUpdateDeliveriesByPackingList(HashMap<Long, OrderW> orderMap, DeliveryW basedelivery, DatingRequestW basedatingrequest, PalletW[] pallets, BoxW[] boxes, BulkDetailW[] bulkdetails, PreDeliveryDetailW[] predeliveryDetails, Long guideNumber, String importationGuide) throws AccessDeniedException, NotFoundException, OperationFailedException;
	DeliveryReportResultDTO getDeliveryDataById(Long deliveryid);
	AddDeliveryOfOrdersAndFlowTypesResultDTO doAddImportedDeliveryOfOrders(AddImportedDeliveryOfOrdersInitParamDTO initParams);
	PreDeliveryDetailDataResultDTO getPreDeliveryDetailsForImportedDelivery(PreDeliveryDetailDataInitParamDTO initParams, boolean byFilter);
	OrderContainerDataResultDTO getOrdersContainersForImportedDelivery(OrderContainerDataInitParamDTO initParams);
	UploadPreDeliveryDetailDataResultDTO doUploadPreDeliveryDetailsForImportedDelivery(UploadPreDeliveryDetailDataInitParamDTO initParams);
	
	ProposedPackingListDataArrayResultDTO doUpdateAllRefDocumentsOfDelivery(UpdateAllRefDocumentsOfDeliveryInitParamDTO initParams);
	void doCalculateTotalBulkDetailOfDelivery(Long deliveryid);
}
