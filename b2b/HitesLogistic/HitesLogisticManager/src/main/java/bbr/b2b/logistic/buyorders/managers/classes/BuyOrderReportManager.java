package bbr.b2b.logistic.buyorders.managers.classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.LockModeType;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO.ComparisonOperator;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.logistic.datings.classes.ReserveDetailServerLocal;
import bbr.b2b.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.logistic.datings.managers.interfaces.DatingReportManagerLocal;
import bbr.b2b.logistic.datings.report.classes.DatingRequestRejectInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.DeleteDatingInitParamDTO;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcDeliveryDetailServerLocal;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcDeliveryServerLocal;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcDeliveryStateServerLocal;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcDeliveryStateTypeServerLocal;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcFileServerLocal;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryDetailW;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryStateTypeW;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryStateW;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryW;
import bbr.b2b.logistic.ddcdeliveries.report.classes.DdcDeliveryAddInitParamDTO;
import bbr.b2b.logistic.ddcdeliveries.report.classes.DdcDeliveryAddResultDTO;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderDetailServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderStateServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderStateTypeServerLocal;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderDetailW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateTypeW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderW;
import bbr.b2b.logistic.ddcorders.report.classes.DdcFileArrayResultDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcFileDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcFileInitParamDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDeliveryStateDataDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDeliveryStateReportResultDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailDataDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailExcelReportResultDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailReportInitParamDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailReportResultDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDetailReportTotalDataDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderExcelReportInitParamDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderExcelReportResultDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderIdsByPagesDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderReportDataDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderReportInitParamDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderReportResultDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderStateTypeArrayResultDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderStateTypeDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrdersVendorInitParamDTO;
import bbr.b2b.logistic.ddcorders.report.classes.RejectDDCOrdersInitParamDTO;
import bbr.b2b.logistic.ddcorders.report.classes.RejectDDCOrdersResultDTO;
import bbr.b2b.logistic.ddcorders.report.classes.RescheduleDateDDCOrdersDataDTO;
import bbr.b2b.logistic.ddcorders.report.classes.RescheduleDateDDCOrdersInitParamDTO;
import bbr.b2b.logistic.ddcorders.report.classes.RescheduleDateDDCOrdersResultDTO;
import bbr.b2b.logistic.deliveries.managers.interfaces.DeliveryReportManagerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DatingRequestServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryStateServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryStateTypeServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrOrderDeliveryDetailServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrOrderDeliveryServerLocal;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateTypeW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDeleteInitParamDTO;
import bbr.b2b.logistic.dvrorders.classes.ChargeDiscountDvrOrderDetailServerLocal;
import bbr.b2b.logistic.dvrorders.classes.ChargeDiscountDvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderDetailServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateTypeServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrPreDeliveryDetailServerLocal;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateTypeW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderW;
import bbr.b2b.logistic.dvrorders.report.classes.ChargeDiscountByOrderDetailInitParamDTO;
import bbr.b2b.logistic.dvrorders.report.classes.ChargeDiscountDataByDvrOrderDetailDTO;
import bbr.b2b.logistic.dvrorders.report.classes.ChargeDiscountDvrOrderDetailDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.ChargeDiscountResultReportDTO;
import bbr.b2b.logistic.dvrorders.report.classes.CheckDVHOrdersExecuteActionsResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DiscountByOrderDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DiscountByOrderInitParamDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DiscountByOrderResultReportDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderDetailDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderDetailExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderDetailReportInitParamDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderDetailReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderDetailReportTotalDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderReportDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderReportInitParamDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailReportInitParamDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailReportTotalDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderExcelReportInitParamDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderIdsByPagesDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderLabelArrayResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderPdfDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderPdfDetailDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderPdfReportDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderPdfReportInitParamDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderPdfReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderReportDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderReportInitParamDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderStateTypeDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderStateTypeResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrdersVendorInitParamDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailReportInitParamDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailReportTotalDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.RejectDDCOrdersDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.RejectDVHOrdersDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.RejectDVHOrdersInitParamDTO;
import bbr.b2b.logistic.dvrorders.report.classes.RejectDVHOrdersResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.RescheduleDateDVHOrdersDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.RescheduleDateDVHOrdersInitParamDTO;
import bbr.b2b.logistic.dvrorders.report.classes.RescheduleDateDVHOrdersResultDTO;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.logistic.managers.interfaces.BuyOrderReportManagerRemote;
import bbr.b2b.logistic.report.classes.UserLogDataDTO;
import bbr.b2b.logistic.utils.LogisticStatusCodeUtils;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;

@Stateless(name = "managers/BuyOrderReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BuyOrderReportManager implements BuyOrderReportManagerLocal, BuyOrderReportManagerRemote {

	private static Logger logger = Logger.getLogger(BuyOrderReportManager.class);
	
	
	private static final Integer 	RESCHEDULING_MAXLENGTH_COMMENT  		= 50;
	private static final Integer 	REJECT_MAXLENGTH_COMMENT				= 50;
	
	@Resource
	private javax.ejb.SessionContext mySessionCtx;

	@EJB
	DatingReportManagerLocal datingreportmanager;
	
	@EJB
	DeliveryReportManagerLocal deliveryreportmanagerlocal;
	
	@EJB
	OrderTypeServerLocal ordertypeserver;
	
	@EJB
	ChargeDiscountDvrOrderServerLocal chargediscountdvrorderserver;
	
	@EJB
	ChargeDiscountDvrOrderDetailServerLocal chargediscountdvrorderdetailserver;
	
	@EJB
	DatingServerLocal datingserver;
	
	@EJB
	DatingRequestServerLocal datingrequestserver;
	
	@EJB
	DvrDeliveryServerLocal drdvrdeliveryserver;
	
	@EJB
	DvrDeliveryStateServerLocal dvrdeliverystateserver;
	
	@EJB
	DvrOrderServerLocal dvrorderserver;
	
	@EJB
	DvrOrderStateTypeServerLocal dvrorderstatetypeserver;
	
	@EJB
	DvrOrderStateServerLocal dvrorderstateserver;
	
	@EJB
	DvrOrderDetailServerLocal dvrorderdetailserver;
	
	@EJB
	DvrPreDeliveryDetailServerLocal dvrpredeliverydetailserver;
	
	@EJB
	DvrOrderDeliveryServerLocal dvrorderdeliveryserver;
	
	@EJB
	DvrOrderDeliveryDetailServerLocal dvrorderdeliverydetailserver;
	
	@EJB
	DvrDeliveryServerLocal dvrdeliveryserver;
	
	@EJB
	DvrDeliveryStateTypeServerLocal dvrdeliverystatetypeserver;
	
	@EJB
	DdcOrderServerLocal ddcorderserver;
	
	@EJB
	DdcOrderStateTypeServerLocal ddcorderstatetypeserver;
	
	@EJB
	DdcOrderStateServerLocal ddcorderstateserver;
	
	@EJB
	DdcOrderDetailServerLocal ddcorderdetailserver;
	
	@EJB
	DdcDeliveryServerLocal ddcdeliveryserver;
	
	@EJB
	DdcDeliveryStateServerLocal ddcdeliverystateserver;
	
	@EJB
	DdcDeliveryStateTypeServerLocal ddcdeliverystatetypeserver;
	
	@EJB
	DdcDeliveryDetailServerLocal ddcdeliverydetailserver;
	
	@EJB
	DdcFileServerLocal ddcfileserver;
		
	@EJB
	LocationServerLocal locationserver;
	
	@EJB
	ReserveDetailServerLocal reservedetailserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	
	public DvrOrderW doAcceptDvrOrder(Long dvrorderid, String username, String usertype) throws NotFoundException, OperationFailedException, AccessDeniedException {
		// Se buscan los tipos de estado de orden Liberada, Aceptada
		DvrOrderStateTypeW releasedstate = dvrorderstatetypeserver.getByPropertyAsSingleResult("code", "LIBERADA");
		DvrOrderStateTypeW acceptedstate = dvrorderstatetypeserver.getByPropertyAsSingleResult("code", "ACEPTADA");
		DvrOrderStateTypeW modifiedstate = dvrorderstatetypeserver.getByPropertyAsSingleResult("code", "MODIFICADA");
		
		
		// Se busca la orden
		DvrOrderW[] dvrorderArr = dvrorderserver.getByPropertyAsArray("id", dvrorderid);
		if(dvrorderArr.length == 0){
			throw new NotFoundException("No existe dvrorden para ID " + dvrorderid);
		}
		DvrOrderW dvrorderW = dvrorderArr[0];
		
		// Verifica su estado de DVR OC (Liberado o Modificada)
		if(dvrorderW.getCurrentstatetypeid().equals(releasedstate.getId()) || dvrorderW.getCurrentstatetypeid().equals(modifiedstate.getId())) {
			logger.info("Aceptando Orden de Compra DVR ID " + dvrorderW.getId() + ". Número " + dvrorderW.getNumber());
		
			DvrOrderStateW newstate = new DvrOrderStateW(); 
			LocalDateTime now = LocalDateTime.now();
			
			newstate = new DvrOrderStateW();
			newstate.setDvrorderid(dvrorderid);
			newstate.setDvrorderstatetypeid(acceptedstate.getId());

			//newstate.setUsertype(usertype);
			newstate.setWhen(now);
			newstate.setWho(username);

			newstate = dvrorderstateserver.addDvrOrderState(newstate);
			
			// Se actualiza la DVR OC
			dvrorderW.setCurrentstatetypeid(acceptedstate.getId());
			dvrorderW.setCurrentstatetypedate(now);
			
			dvrorderW = dvrorderserver.updateDvrOrder(dvrorderW);

		}
		
		return dvrorderW;
	}
	
	public void doAcceptDvrOrders(Long[] dvrorderids, String username, String usertype) throws NotFoundException, OperationFailedException, AccessDeniedException {
		
		if (dvrorderids != null && dvrorderids.length > 0) {
			for (Long dvrorderid : dvrorderids) {
				doAcceptDvrOrder(dvrorderid, username, usertype);
			}
		}
	}
	
	public void doCalculateTotalOfDvrOrders(Long[] dvrorderids)  throws NotFoundException, AccessDeniedException, OperationFailedException { 
		  
		dvrpredeliverydetailserver.flush();
		dvrpredeliverydetailserver.clear();
		// Actualiza DVrPredelivery
		dvrpredeliverydetailserver.doCalculateTotalDvrPredelivery(dvrorderids);
		// Actualiza DvrOrderDelivery
		dvrorderdetailserver.doCalculateTotalDvrOrderDetail(dvrorderids);
		// Actualiza DvrOrder
		dvrorderserver.doCalculateTotalDvrOrder(dvrorderids);	
	}

	
	public DvrOrderStateTypeResultDTO getDvrOrderStateTypes(){
		DvrOrderStateTypeResultDTO resultDTO = new DvrOrderStateTypeResultDTO();

		try {
			// Obtiene estados visibles
			DvrOrderStateTypeW[] dvrorderstatetypes = dvrorderstatetypeserver.getByPropertyAsArray("visible", true);
			if (dvrorderstatetypes == null || dvrorderstatetypes.length <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L800");
			}

			DvrOrderStateTypeDTO[] dvrorderstatetypedtos = new DvrOrderStateTypeDTO[dvrorderstatetypes.length];
			BeanExtenderFactory.copyProperties(dvrorderstatetypes, dvrorderstatetypedtos, DvrOrderStateTypeDTO.class);

			resultDTO.setDvrorderstatetype(dvrorderstatetypedtos);
			return resultDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}
		
	public DvrOrderReportResultDTO getDvrOrderReportByVendorAndFilter(DvrOrderReportInitParamDTO initParamDTO, boolean withTotals, boolean isPaginated){
		DvrOrderReportResultDTO resultDTO = new DvrOrderReportResultDTO();
		DvrOrderReportDataDTO[] dvrordersArr = null;
		int total;
		
		try{
			// Tipo de Orden excluido para el reporte
			OrderTypeW dvhTypeW = ordertypeserver.getByPropertyAsSingleResult("code", "DVH"); 
			
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];

			// Local de entrega
			if (! initParamDTO.getDeliverylocationid().equals(new Long(LogisticConstants.getINT_TODOS()))){
				LocationW[] locationArr = locationserver.getByPropertyAsArray("id", initParamDTO.getDeliverylocationid()); 
				if(locationArr.length == 0){
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1904");
				}
			}
			
			// Datos
			dvrordersArr = dvrorderserver.getDvrOrdersByVendorAndCriterium(vendorW.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getSearchvalue(), initParamDTO.getDvrorderstatetypeid(), initParamDTO.getSince(), initParamDTO.getUntil(), initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
			// Si es consulta desde el filtro, se muesta información de la paginación
			if(withTotals){
				total = dvrorderserver.getCountDvrOrdersByVendorAndCriterium(vendorW.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getSearchvalue(), initParamDTO.getDvrorderstatetypeid(), initParamDTO.getSince(), initParamDTO.getUntil(), initParamDTO.getFiltertype());

				// Setea Información de paginación
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(dvrordersArr.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
				resultDTO.setTotalreg(total);
			}
			
			resultDTO.setDvrorders(dvrordersArr);
			
			return resultDTO;
			
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1801");
			
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1801");
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1801");
		}
	}
	
	public DvrOrderExcelReportResultDTO getDvrOrdersExcelReportByOrders(DvrOrderExcelReportInitParamDTO initParamDTO, UserLogDataDTO userDataDTO, boolean providerUser, boolean isByPages) {
		DvrOrderExcelReportResultDTO resultDTO = new DvrOrderExcelReportResultDTO();
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			
			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			for (Long dvrOrderId : initParamDTO.getDvrorderids()) {
				properties[0] = new PropertyInfoDTO("id", "id", dvrOrderId);
				properties[1] = new PropertyInfoDTO("vendor.dni", "vendordni", initParamDTO.getVendorcode());
				List<DvrOrderW> ordersTmp = dvrorderserver.getByProperties(properties);

				if (ordersTmp.isEmpty()) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
				}
			}

			// Si viene de reporte por páginas no es necesario realizar la consulta
			int totalRows = 0;
			if (!isByPages) {
				totalRows = dvrorderserver.countDvrOrdersExcelReportByOrders(initParamDTO.getDvrorderids());
			}

			// Validar la cantidad de registros con el máximo permitido
			if (totalRows > LogisticConstants.getEXCEL_MAX_NUMBER_OF_ROWS()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
			}
			
			resultDTO = dvrorderserver.getDvrOrdersExcelReportByOrders(initParamDTO.getDvrorderids());
			if (resultDTO.getDvrorders() == null || resultDTO.getDvrorders().length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2109");
			}
			
			if (providerUser) {
				doAcceptDvrOrders(initParamDTO.getDvrorderids(), userDataDTO.getUsername(), LogisticConstants.USER_TYPE_VENDOR);
			}
			
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}

		return resultDTO;
	}
	
	
	public DvhOrderExcelReportResultDTO getDvhOrdersExcelReportByOrders(DvrOrderExcelReportInitParamDTO initParamDTO, UserLogDataDTO userDataDTO, boolean providerUser, boolean isByPages) {
		DvhOrderExcelReportResultDTO resultDTO = new DvhOrderExcelReportResultDTO();
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			
			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			for (Long dvrOrderId : initParamDTO.getDvrorderids()) {
				properties[0] = new PropertyInfoDTO("id", "id", dvrOrderId);
				properties[1] = new PropertyInfoDTO("vendor.dni", "vendordni", initParamDTO.getVendorcode());
				List<DvrOrderW> ordersTmp = dvrorderserver.getByProperties(properties);

				if (ordersTmp.isEmpty()) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
				}
			}

			// Si viene de reporte por páginas no es necesario realizar la consulta
			int totalRows = 0;
			if (!isByPages) {
				totalRows = dvrorderserver.countDvhOrdersExcelReportByOrders(initParamDTO.getDvrorderids());
			}

			// Validar la cantidad de registros con el máximo permitido
			if (totalRows > LogisticConstants.getEXCEL_MAX_NUMBER_OF_ROWS()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
			}
			
			resultDTO = dvrorderserver.getDvhOrdersExcelReportByOrders(initParamDTO.getDvrorderids());
			if (resultDTO.getDvhorders() == null || resultDTO.getDvhorders().length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2110");
			}
			
			if (providerUser) {
				doAcceptDvrOrders(initParamDTO.getDvrorderids(), userDataDTO.getUsername(), LogisticConstants.USER_TYPE_VENDOR);
			}
			
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}

		return resultDTO;
	}
	
	public DvrOrderPdfReportResultDTO getPdfDvrOrderReportByOrders(DvrOrderPdfReportInitParamDTO initParamDTO, UserLogDataDTO userDataDTO, boolean providerUser, boolean isByPages) {
		DvrOrderPdfReportResultDTO resultDTO = new DvrOrderPdfReportResultDTO();
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			
			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			for (Long dvrOrderId : initParamDTO.getDvrorderids()) {
				properties[0] = new PropertyInfoDTO("id", "id", dvrOrderId);
				properties[1] = new PropertyInfoDTO("vendor.dni", "vendordni", initParamDTO.getVendorcode());
				List<DvrOrderW> ordersTmp = dvrorderserver.getByProperties(properties);

				if (ordersTmp.isEmpty()) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
				}
			}

			// Si viene de reporte por páginas no es necesario realizar la consulta
			int totalRows = 0;
			if (!isByPages) {
				totalRows = dvrorderserver.countDvrOrdersPdfDetailByOrders(initParamDTO.getDvrorderids());
			}

			// Validar la cantidad de registros con el máximo permitido
			if (totalRows > LogisticConstants.getPDF_MAX_NUMBER_OF_ROWS_OC()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2103");
			}
			
			// Obtener los detalles
			DvrOrderPdfDetailDTO[] details = dvrorderserver.getDvrOrdersPdfDetailByOrders(initParamDTO.getDvrorderids());
			HashMap<Long, List<DvrOrderPdfDetailDTO>> detailMap = (HashMap<Long, List<DvrOrderPdfDetailDTO>>)
											Arrays.stream(details).collect(Collectors.groupingBy(DvrOrderPdfDetailDTO::getNumber));
			
			// Obtener las órdenes
			DvrOrderPdfDataDTO[] result = dvrorderserver.getDvrOrdersPdfDataByOrders(initParamDTO.getDvrorderids());
			
			List<DvrOrderPdfReportDataDTO> dvrOrders = new ArrayList<DvrOrderPdfReportDataDTO>();
			DvrOrderPdfReportDataDTO dvrOrder;
			for (DvrOrderPdfDataDTO data : result) {
				dvrOrder = new DvrOrderPdfReportDataDTO();
				dvrOrder.setData(data);
				dvrOrder.setDetails(detailMap.get(data.getNumber()));
				
				dvrOrders.add(dvrOrder);
			}
			
			resultDTO.setDvrorders((DvrOrderPdfReportDataDTO[]) dvrOrders.toArray(new DvrOrderPdfReportDataDTO[dvrOrders.size()]));
			
			if (providerUser) {
				doAcceptDvrOrders(initParamDTO.getDvrorderids(), userDataDTO.getUsername(), LogisticConstants.USER_TYPE_VENDOR);
			}
			
		} catch (NotFoundException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		} catch (AccessDeniedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}

		return resultDTO;
	}
	
	// TODO MME: Validar si el reporte es solo para dvr o para dvr dvh indistintamente
	public DvrOrderLabelArrayResultDTO getDvrOrderLabelsByOrders(DvrOrderExcelReportInitParamDTO initParamDTO, UserLogDataDTO userDataDTO, boolean providerUser, boolean isByPages) {
		DvrOrderLabelArrayResultDTO resultDTO = new DvrOrderLabelArrayResultDTO();
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			
			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			for (Long dvrOrderId : initParamDTO.getDvrorderids()) {
				properties[0] = new PropertyInfoDTO("id", "id", dvrOrderId);
				properties[1] = new PropertyInfoDTO("vendor.dni", "vendordni", initParamDTO.getVendorcode());
				List<DvrOrderW> ordersTmp = dvrorderserver.getByProperties(properties);

				if (ordersTmp.isEmpty()) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
				}
			}

			// Si viene de reporte por páginas no es necesario realizar la consulta
			int totalRows = 0;
			if (!isByPages) {
				totalRows = dvrorderserver.countDvrOrderLabelsByOrders(initParamDTO.getDvrorderids());
			}

			// Validar la cantidad de registros con el máximo permitido
			if (totalRows > LogisticConstants.getEXCEL_MAX_NUMBER_OF_ROWS()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
			}
			
			resultDTO = dvrorderserver.getDvrOrderLabelsByOrders(initParamDTO.getDvrorderids());
						
		} catch (NotFoundException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}
		
		return resultDTO;
	}
	
	public DvrOrderIdsByPagesDTO getDvrOrderIdsByPages(DvrOrderReportInitParamDTO initParamDTO, PageRangeDTO[] pageRangeDTO) {

		DvrOrderIdsByPagesDTO resultDTO = new DvrOrderIdsByPagesDTO();
		
		try {
			// Tipo de Orden excluido para el reporte
			OrderTypeW dvhTypeW = ordertypeserver.getByPropertyAsSingleResult("code", "DVH"); 
			
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];

			// Local de entrega
			if (!initParamDTO.getDeliverylocationid().equals(new Long(LogisticConstants.getINT_TODOS()))){
				LocationW[] locationArr = locationserver.getByPropertyAsArray("id", initParamDTO.getDeliverylocationid()); 
				if (locationArr.length == 0) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1904");
				}
			}
						
			Long[] dvrOrderIds = dvrorderserver.getDvrOrderIdsByPages(vendorW.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getSearchvalue(), initParamDTO.getDvrorderstatetypeid(),  initParamDTO.getSince(), initParamDTO.getUntil(), initParamDTO.getFiltertype(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangeDTO);

			// Validar la cantidad de registros con el máximo permitido
			if (dvrOrderIds != null && dvrOrderIds.length > LogisticConstants.getEXCEL_MAX_NUMBER_OF_ROWS()) {				
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
			}
			
			resultDTO.setDvrorderids(dvrOrderIds);

		} catch (NotFoundException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		} catch (AccessDeniedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}
		
		return resultDTO;
	}
	
	public DvrOrderDetailReportResultDTO getDvrOrderDetailByOrder(DvrOrderDetailReportInitParamDTO initParamDTO, UserLogDataDTO userDataDTO, boolean providerUser, boolean withTotals, boolean isPaginated){
		
		DvrOrderDetailReportResultDTO resultDTO = new DvrOrderDetailReportResultDTO();
		DvrOrderDetailDataDTO[] dvrorderdetail;
		DvrOrderDetailReportTotalDataDTO totalresult;
		int total;
		
		try{
			// Tipo de Orden excluido para el reporte
			OrderTypeW dvhTypeW = ordertypeserver.getByPropertyAsSingleResult("code", "DVH"); 
			
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Chequea la existencia de la orden dvr para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParamDTO.getDvrorderid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
			DvrOrderW[] dvrorderArr = dvrorderserver.getByPropertiesAsArray(properties);

			if (dvrorderArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
			}
			DvrOrderW dvrorderW = dvrorderArr[0];
			
			// Obtiene datos
			dvrorderdetail = dvrorderdetailserver.getDvrOrdersDetailByDvrOrder(initParamDTO.getDvrorderid(), vendorW.getId(), dvhTypeW.getId(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
			
			if(withTotals){
				totalresult = dvrorderdetailserver.getCountDvrOrdersDetailsByOrder(initParamDTO.getDvrorderid(), vendorW.getId(), dvhTypeW.getId());

				// Setear Información de la Paginación
				int rows = initParamDTO.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(dvrorderdetail.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
				resultDTO.setTotal(totalresult);
			}
			
			// TODO MME: SE ACEPTA LA OC SI ES PROVEEDOR Y ESTA EN ESTADO LIBERADA O MODIFICADA
			if(providerUser){
				// Acepta OC DVR
				doAcceptDvrOrder(initParamDTO.getDvrorderid(), userDataDTO.getUsername(), "PROVEEDOR");
				dvrorderserver.flush();
			}

			// Obtiene información de la OC
			DvrOrderReportDataDTO[] currentdvrorderArr = dvrorderserver.getDvrOrdersByVendorAndCriterium(vendorW.getId(), dvrorderW.getDeliverylocationid(), dvrorderW.getNumber().toString(), null, null, null, 2, 0, 0, null, false);
			
			resultDTO.setDvrorder(currentdvrorderArr[0]);
			resultDTO.setDvrorderdetail(dvrorderdetail);
			return resultDTO;
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1901");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1901");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1901");
		}	
	}
	
	public DvrOrderDetailExcelReportResultDTO getDvrOrderDetailExcelReportByOrder(DvrOrderDetailReportInitParamDTO initParamDTO) {
		DvrOrderDetailExcelReportResultDTO resultDTO = new DvrOrderDetailExcelReportResultDTO();
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			
			// Obtener el tipo de orden 'DVH'
			OrderTypeW otDVH = ordertypeserver.getByPropertyAsSingleResult("code", "DVH");
			
			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParamDTO.getDvrorderid());
			properties[1] = new PropertyInfoDTO("vendor.dni", "vendordni", initParamDTO.getVendorcode());
			List<DvrOrderW> ordersTmp = dvrorderserver.getByProperties(properties);
						
			if (ordersTmp.isEmpty()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
			}
			
			// Valida que la orden no sea de tipo 'DVH'
			if (ordersTmp.get(0).getOrdertypeid().equals(otDVH.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2108");
			}
						
			int totalRows = dvrorderdetailserver.countDvrOrderDetailExcelReportByOrder(initParamDTO.getDvrorderid());

			// Validar la cantidad de registros con el máximo permitido
			if (totalRows > LogisticConstants.getEXCEL_MAX_NUMBER_OF_ROWS()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
			}
			
			resultDTO = dvrorderdetailserver.getDvrOrderDetailExcelReportByOrder(initParamDTO.getDvrorderid());
						
		} catch (NotFoundException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}
		
		return resultDTO;
	}
	
	public DvhOrderDetailExcelReportResultDTO getDvhOrderDetailExcelReportByOrder(DvrOrderDetailReportInitParamDTO initParamDTO) {
		DvhOrderDetailExcelReportResultDTO resultDTO = new DvhOrderDetailExcelReportResultDTO();
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			
			// Obtener el tipo de orden 'DVH'
			OrderTypeW otDVH = ordertypeserver.getByPropertyAsSingleResult("code", "DVH");
			
			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParamDTO.getDvrorderid());
			properties[1] = new PropertyInfoDTO("vendor.dni", "vendordni", initParamDTO.getVendorcode());
			List<DvrOrderW> ordersTmp = dvrorderserver.getByProperties(properties);
						
			if (ordersTmp.isEmpty()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
			}
			
			// Valida que la orden sea de tipo 'DVH'
			if (!ordersTmp.get(0).getOrdertypeid().equals(otDVH.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2108");
			}
						
			int totalRows = dvrorderdetailserver.countDvhOrderDetailExcelReportByOrder(initParamDTO.getDvrorderid());

			// Validar la cantidad de registros con el máximo permitido
			if (totalRows > LogisticConstants.getEXCEL_MAX_NUMBER_OF_ROWS()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
			}
			
			resultDTO = dvrorderdetailserver.getDvhOrderDetailExcelReportByOrder(initParamDTO.getDvrorderid());
						
		} catch (NotFoundException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}
		
		return resultDTO;
	}
	
	public DvrPredeliveryDetailReportResultDTO getDvrPredeliveryDetailByOrder(DvrPredeliveryDetailReportInitParamDTO initParamDTO, boolean withTotals, boolean isPaginated){
		DvrPredeliveryDetailReportResultDTO resultDTO = new DvrPredeliveryDetailReportResultDTO();
		DvrPredeliveryDetailDataDTO[] dvrpredeliverydetail;
		DvrPredeliveryDetailReportTotalDataDTO totalresult;
		int total;
		
		try{
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Chequea la existencia de la orden dvr para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParamDTO.getDvrorderid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
			DvrOrderW[] dvrorderArr = dvrorderserver.getByPropertiesAsArray(properties);
			if (dvrorderArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
			}
			
			// Obtiene datos
			dvrpredeliverydetail = dvrpredeliverydetailserver.getDvrPredeliveryDetailByOrder(initParamDTO.getDvrorderid(), vendorW.getId(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
			
			if(withTotals){
				totalresult = dvrpredeliverydetailserver.getCountDvrPredeliveryDetailsByOrder(initParamDTO.getDvrorderid(), vendorW.getId());

				// Setear Información de la Paginación
				int rows = initParamDTO.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(dvrpredeliverydetail.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
				resultDTO.setTotal(totalresult);
			}

			resultDTO.setDvrpredeliverydetail(dvrpredeliverydetail);
			return resultDTO;
			
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1901");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1901");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1901");
		}
	}
	
	public DvrPredeliveryDetailExcelReportResultDTO getDvrPredeliveryDetailExcelReportByOrder(DvrPredeliveryDetailReportInitParamDTO initParamDTO) {
		DvrPredeliveryDetailExcelReportResultDTO resultDTO = new DvrPredeliveryDetailExcelReportResultDTO();
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			
			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParamDTO.getDvrorderid());
			properties[1] = new PropertyInfoDTO("vendor.dni", "vendordni", initParamDTO.getVendorcode());
			List<DvrOrderW> ordersTmp = dvrorderserver.getByProperties(properties);
						
			if (ordersTmp.isEmpty()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
			}
			
			int totalRows = dvrorderdetailserver.countDvrOrderDetailExcelReportByOrder(initParamDTO.getDvrorderid());

			// Validar la cantidad de registros con el máximo permitido
			if (totalRows > LogisticConstants.getEXCEL_MAX_NUMBER_OF_ROWS()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
			}
			
			resultDTO = dvrpredeliverydetailserver.getDvrPredeliveryDetailExcelReportByOrder(initParamDTO.getDvrorderid());
						
		} catch (NotFoundException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}
		
		return resultDTO;
	}
	
	public DiscountByOrderResultReportDTO getDiscountByOrder(DiscountByOrderInitParamDTO initParamDTO) {
		DiscountByOrderResultReportDTO resultDTO = new DiscountByOrderResultReportDTO();
		
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Obtiene Datos
			DiscountByOrderDataDTO[] discountbyorderArr = chargediscountdvrorderserver.getDiscountDataFromOrder(initParamDTO.getDvrorderid(), vendorW.getId());
						
			resultDTO.setDiscountbyorderdata(discountbyorderArr);
			return resultDTO;
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1910");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1910");
		}
		
	}	
	
	public ChargeDiscountResultReportDTO getChargeDiscountByOrderDetail(ChargeDiscountByOrderDetailInitParamDTO initParamDTO){
		
		ChargeDiscountResultReportDTO resultDTO = new ChargeDiscountResultReportDTO();
		List<ChargeDiscountDvrOrderDetailDataDTO> chargedataList = new ArrayList<ChargeDiscountDvrOrderDetailDataDTO>();
		List<ChargeDiscountDvrOrderDetailDataDTO> discountdataList = new ArrayList<ChargeDiscountDvrOrderDetailDataDTO>();
		
		try {
			
			// Obtiene Datos
			ChargeDiscountDataByDvrOrderDetailDTO[] chargediscountdataArr = chargediscountdvrorderdetailserver.getChargeDiscountDataFromOrderDetail(initParamDTO.getDvrorderid(), initParamDTO.getItemid(), initParamDTO.getPosition());

			// Itera sobre resultado para obtener cargos y descuentos informados
			for(ChargeDiscountDataByDvrOrderDetailDTO chargediscountdata : chargediscountdataArr){
				
				ChargeDiscountDvrOrderDetailDataDTO data = new ChargeDiscountDvrOrderDetailDataDTO();
				
				// Cargo
				if(chargediscountdata.getCharge()){
					data.setDescription(chargediscountdata.getDescription());
					data.setValue(chargediscountdata.getValue());
					chargedataList.add(data);
				}
				// Descuento
				else{
					data.setDescription(chargediscountdata.getDescription());
					data.setValue(chargediscountdata.getValue());
					discountdataList.add(data);
				}				
			}
			
			resultDTO.setChargedata(chargedataList.toArray(new ChargeDiscountDvrOrderDetailDataDTO[chargedataList.size()]));
			resultDTO.setDiscountdata(discountdataList.toArray(new ChargeDiscountDvrOrderDetailDataDTO[discountdataList.size()]));
			
			return resultDTO;
		
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1911");
		}
		
	}
	
	public DdcOrderW doAcceptDdcOrder(Long ddcorderid, String username, String usertype, String comment) throws NotFoundException, OperationFailedException, AccessDeniedException {
		
		// Se buscan los tipos de estado de orden 'Liberada', 'Modificada' y 'Aceptada'
		DdcOrderStateTypeW ostReleased = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCORDER_STATE_RELEASED);
		DdcOrderStateTypeW ostModified = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCORDER_STATE_MODIFIED);
		DdcOrderStateTypeW ostAccepted = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCORDER_STATE_ACCEPTED);
		
		// Buscar la orden
		DdcOrderW[] ddcorderArr = ddcorderserver.getByPropertyAsArray("id", ddcorderid);
		if (ddcorderArr.length == 0) {
			throw new NotFoundException("No existe orden DDC para ID " + ddcorderid);
		}
		DdcOrderW ddcorderW = ddcorderArr[0];
		
		// Verificar el estado de la orden ('Liberada' o 'Modificada')
		if (ddcorderW.getCurrentstatetypeid().equals(ostReleased.getId()) || ddcorderW.getCurrentstatetypeid().equals(ostModified.getId())) {
			logger.info("Aceptando Orden de Compra DDC ID " + ddcorderW.getId() + ". Número " + ddcorderW.getNumber());

			LocalDateTime now = LocalDateTime.now();
			
			// Actualizar la orden
			ddcorderW.setCurrentstatetypedate(now);
			ddcorderW.setCurrentstatetypewho(username);
			ddcorderW.setCurrentstatetypecomment(comment);
			ddcorderW.setCurrentstatetypeid(ostAccepted.getId());
									
			ddcorderW = ddcorderserver.updateDdcOrder(ddcorderW);
			
			DdcOrderStateW newstate = new DdcOrderStateW(); 
			newstate = new DdcOrderStateW();
			newstate.setWhen(now);
			newstate.setWho(username);
			newstate.setComment(comment);
			newstate.setDdcorderid(ddcorderid);
			newstate.setDdcorderstatetypeid(ostAccepted.getId());
						
			newstate = ddcorderstateserver.addDdcOrderState(newstate);
		}
		
		return ddcorderW;
	}
	
	public void doAcceptDdcOrders(Long[] ddcorderids, String username, String usertype, String comment) throws NotFoundException, OperationFailedException, AccessDeniedException {
		
		if (ddcorderids != null && ddcorderids.length > 0) {
			for (Long ddcorderid : ddcorderids) {
				doAcceptDdcOrder(ddcorderid, username, usertype, comment);
			}
		}
	}

	public void doCalculateTotalOfDdcOrders(Long[] ddcorderids)  throws NotFoundException, AccessDeniedException, OperationFailedException { 

		ddcdeliverydetailserver.flush();
		ddcdeliverydetailserver.clear();

		// Actualiza DdcOrderDetail
		ddcorderdetailserver.doCalculateTotalDdcOrderDetails(ddcorderids);
		
		// Actualiza DvrOrder
		ddcorderserver.doCalculateTotalDdcOrders(ddcorderids);	
	}
	
	
	public DdcOrderStateTypeArrayResultDTO getDdcOrderStateTypes() {
		DdcOrderStateTypeArrayResultDTO resultDTO = new DdcOrderStateTypeArrayResultDTO();

		try {
			// Obtener los estados visibles
			DdcOrderStateTypeW[] ddcorderstatetypes = ddcorderstatetypeserver.getByPropertyAsArray("showable", true);
			if (ddcorderstatetypes == null || ddcorderstatetypes.length <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L800");
			}

			DdcOrderStateTypeDTO[] ddcorderstatetypedtos = new DdcOrderStateTypeDTO[ddcorderstatetypes.length];
			BeanExtenderFactory.copyProperties(ddcorderstatetypes, ddcorderstatetypedtos, DdcOrderStateTypeDTO.class);

			resultDTO.setDdcorderstatetype(ddcorderstatetypedtos);
			
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public DdcOrderReportResultDTO getDdcOrderReportByVendorAndFilter(DdcOrderReportInitParamDTO initParamDTO, boolean withTotals, boolean isPaginated) {
		DdcOrderReportResultDTO resultDTO = new DdcOrderReportResultDTO();
		DdcOrderReportDataDTO[] ddcordersArr = null;
		int total;
		
		try{
			// Obtener el proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];

			// Datos
			ddcordersArr = ddcorderserver.getDdcOrdersByVendorAndCriterium(vendorW.getId(), initParamDTO.getFiltervalue(), initParamDTO.getSince(), initParamDTO.getUntil(), initParamDTO.getDdcorderstatetypeid(), initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
			
			// Si es consulta desde el filtro, se muesta información de la paginación
			if (withTotals) {
				total = ddcorderserver.countDdcOrdersByVendorAndCriterium(vendorW.getId(), initParamDTO.getFiltervalue(), initParamDTO.getSince(), initParamDTO.getUntil(), initParamDTO.getDdcorderstatetypeid(), initParamDTO.getFiltertype());

				// Setea la información de paginación
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(ddcordersArr.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
				resultDTO.setTotalreg(total);
			}
			
			resultDTO.setDdcorders(ddcordersArr);
			
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1801");
			
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1801");
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1801");
		}
		
		return resultDTO;
	}
	
	public DdcOrderExcelReportResultDTO getDdcOrdersExcelReportByOrders(DdcOrderExcelReportInitParamDTO initParamDTO, UserLogDataDTO userDataDTO, boolean providerUser, boolean isByPages) {
		DdcOrderExcelReportResultDTO resultDTO = new DdcOrderExcelReportResultDTO();
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			
			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			for (Long ddcOrderId : initParamDTO.getDdcorderids()) {
				properties[0] = new PropertyInfoDTO("id", "id", ddcOrderId);
				properties[1] = new PropertyInfoDTO("vendor.dni", "vendordni", initParamDTO.getVendorcode());
				List<DdcOrderW> ordersTmp = ddcorderserver.getByProperties(properties);

				if (ordersTmp.isEmpty()) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
				}
			}

			// Si viene de reporte por páginas no es necesario realizar la consulta
			int totalRows = 0;
			if (!isByPages) {
				totalRows = ddcorderserver.countDdcOrdersExcelReportByOrders(initParamDTO.getDdcorderids());
			}

			// Validar la cantidad de registros con el máximo permitido
			if (totalRows > LogisticConstants.getEXCEL_MAX_NUMBER_OF_ROWS()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
			}
			
			resultDTO = ddcorderserver.getDdcOrdersExcelReportByOrders(initParamDTO.getDdcorderids());
			if (providerUser) {
				doAcceptDdcOrders(initParamDTO.getDdcorderids(), userDataDTO.getUsername(), LogisticConstants.USER_TYPE_VENDOR, "Descarga Excel");
			}
			
		} catch (NotFoundException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		} catch (AccessDeniedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}

		return resultDTO;
	}
	
	public DdcOrderIdsByPagesDTO getDdcOrderIdsByPages(DdcOrderReportInitParamDTO initParamDTO, PageRangeDTO[] pageRangeDTO) {

		DdcOrderIdsByPagesDTO resultDTO = new DdcOrderIdsByPagesDTO();
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];

			Long[] ddcOrderIds = ddcorderserver.getDdcOrderIdsByPages(vendorW.getId(), initParamDTO.getFiltervalue(), initParamDTO.getSince(), initParamDTO.getUntil(), initParamDTO.getDdcorderstatetypeid(), initParamDTO.getFiltertype(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangeDTO);

			// Validar la cantidad de registros con el máximo permitido
			if (ddcOrderIds != null && ddcOrderIds.length > LogisticConstants.getEXCEL_MAX_NUMBER_OF_ROWS()) {				
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
			}
			
			resultDTO.setDdcorderids(ddcOrderIds);

		} catch (NotFoundException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		} catch (AccessDeniedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}
		
		return resultDTO;
	}
	
	public DdcOrderDetailReportResultDTO getDdcOrderDetailByDdcOrder(DdcOrderDetailReportInitParamDTO initParamDTO, UserLogDataDTO userDataDTO, boolean providerUser, boolean withTotals, boolean isPaginated) {
		DdcOrderDetailReportResultDTO resultDTO = new DdcOrderDetailReportResultDTO();
		DdcOrderDetailDataDTO[] ddcorderdetail;
		DdcOrderDetailReportTotalDataDTO totalresult;
		int total;
		
		try{
			// Obtener el proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Chequea la existencia de la orden ddc para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParamDTO.getDdcorderid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
			DdcOrderW[] ddcorderArr = ddcorderserver.getByPropertiesAsArray(properties);

			if (ddcorderArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
			}
			DdcOrderW ddcorderW = ddcorderArr[0];
			
			// Obtiene los datos
			ddcorderdetail = ddcorderdetailserver.getDdcOrderDetailByDdcOrder(initParamDTO.getDdcorderid(), vendorW.getId(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
			
			if (withTotals) {
				totalresult = ddcorderdetailserver.countDdcOrderDetailByDdcOrder(initParamDTO.getDdcorderid(), vendorW.getId());

				// Setear Información de la Paginación
				int rows = initParamDTO.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(ddcorderdetail.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
				resultDTO.setTotal(totalresult);
			}
			
			// Se acepta la orden si es proveedor y está en estados 'Liberada' O 'Modificada'
			if (providerUser) {
				// Aceptar OC DDC
				doAcceptDdcOrder(initParamDTO.getDdcorderid(), userDataDTO.getUsername(), userDataDTO.getUsertype(), "Detalle de orden");
				ddcorderserver.flush();
			}

			// Obtiene información de la OC
			DdcOrderReportDataDTO[] currentddcorderArr = ddcorderserver.getDdcOrdersByVendorAndCriterium(vendorW.getId(), ddcorderW.getNumber().toString(), null, null, null, 1, 0, 0, null, false);
			
			resultDTO.setDdcorder(currentddcorderArr[0]);
			resultDTO.setDdcorderdetail(ddcorderdetail);
						
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1901");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1901");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1901");
		}
		
		return resultDTO;
	}
	
	public DdcOrderDetailExcelReportResultDTO getDdcOrderDetailExcelReportByOrder(DdcOrderDetailReportInitParamDTO initParamDTO) {
		DdcOrderDetailExcelReportResultDTO resultDTO = new DdcOrderDetailExcelReportResultDTO();
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			
			// Chequea la existencia de la orden para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParamDTO.getDdcorderid());
			properties[1] = new PropertyInfoDTO("vendor.dni", "vendordni", initParamDTO.getVendorcode());
			List<DdcOrderW> ordersTmp = ddcorderserver.getByProperties(properties);
						
			if (ordersTmp.isEmpty()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
			}
			
			int totalRows = ddcorderdetailserver.countDdcOrderDetailExcelReportByOrder(initParamDTO.getDdcorderid());

			// Validar la cantidad de registros con el máximo permitido
			if (totalRows > LogisticConstants.getEXCEL_MAX_NUMBER_OF_ROWS()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
			}
			
			resultDTO = ddcorderdetailserver.getDdcOrderDetailExcelReportByOrder(initParamDTO.getDdcorderid());
						
		} catch (NotFoundException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}
		
		return resultDTO;
	}
	
	public DdcOrderDeliveryStateReportResultDTO getDdcOrderDeliveryStateByDdcOrder(DdcOrderDetailReportInitParamDTO initParamDTO, boolean isPaginated) {
		DdcOrderDeliveryStateReportResultDTO resultDTO = new DdcOrderDeliveryStateReportResultDTO();
		DdcOrderDeliveryStateDataDTO[] ddcdeliverystates;
		
		try{
			// Obtener el proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Chequea la existencia de la orden ddc para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParamDTO.getDdcorderid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
			DdcOrderW[] ddcorderArr = ddcorderserver.getByPropertiesAsArray(properties);

			if (ddcorderArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
			}
			DdcOrderW ddcorderW = ddcorderArr[0];
			
			// Valida si la orden tiene despacho asociado
			if (ddcorderW.getCurrentddcdeliveryid() != null && ddcorderW.getCurrentddcdeliveryid() > 0) {
				// Obtiene los datos
				ddcdeliverystates = ddcdeliverystateserver.getDdcOrderDeliveryStateByDdcOrder(initParamDTO.getDdcorderid(), vendorW.getId(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				
				resultDTO.setDdcdeliverystates(ddcdeliverystates);
			}
						
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1905");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1905");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1905");
		}
		
		return resultDTO;
	}
	
	public DdcDeliveryAddResultDTO doAddDdcDeliveryOfDdcOrders(DdcDeliveryAddInitParamDTO initParamDTO, UserLogDataDTO userDataDTO) {
		DdcDeliveryAddResultDTO resultDTO = new DdcDeliveryAddResultDTO();

		try {
			// Obtener el proveedor
			VendorW[] vendorWs = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorWs == null || vendorWs.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorWs[0];
			
			// Estado de entrega 'En Ruta'
			DdcDeliveryStateTypeW dstOnRoute = ddcdeliverystatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCDELIVERY_STATE_ON_ROUTE);
			
			List<DdcOrderW> ddcOrderWs = new ArrayList<DdcOrderW>();
			DdcOrderW ddcOrderW;
			DdcDeliveryW ddcDeliveryW;
			DdcDeliveryStateTypeW dstCurrent;
			for (Long ddcOrderId : initParamDTO.getDdcorderids()) {
				// Obtener la orden
				ddcOrderW = ddcorderserver.getById(ddcOrderId, LockModeType.PESSIMISTIC_WRITE);
				
				// Validar el proveedor de la orden
				if (!ddcOrderW.getVendorid().equals(vendorW.getId())) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
				}
				
				// Validar si la orden tiene despachos asociados
				if (ddcOrderW.getCurrentddcdeliveryid() != null && ddcOrderW.getCurrentddcdeliveryid() > 0) {
					// Validar que esté vigente y no sea 'En Ruta'
					ddcDeliveryW = ddcdeliveryserver.getById(ddcOrderW.getCurrentddcdeliveryid());
					dstCurrent = ddcdeliverystatetypeserver.getById(ddcDeliveryW.getCurrentstatetypeid());
					
					if (dstCurrent.getClosed()) {
						return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No es posible generar un despacho. La Orden " + ddcOrderW.getNumber() + " no se encuentra en un estado válido", false);
					}
					else if (dstCurrent.getId().equals(dstOnRoute.getId())) {
						return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No es posible generar un despacho. La Orden " + ddcOrderW.getNumber() + " ya tiene un despacho vigente asociado", false);
					}
				}
				
				
				// Validar que la orden tenga unidades pendientes
				if (ddcOrderW.getPendingunits() == 0) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No es posible generar un despacho. La Orden " + ddcOrderW.getNumber() + " no tiene unidades pendientes", false);
				}
				
				ddcOrderWs.add(ddcOrderW);
			}
			
			LocalDateTime now = LocalDateTime.now();
			
			// Busca las OC Vigentes del proveedor
			DdcOrderReportDataDTO[] ddcReportVAlidOrdersArr = ddcorderserver.getDdcOrdersByVendorAndCriterium(vendorW.getId(), null, null, null, null, 0, 0, 0, null, false);
			
			// Filtra por:
			// - OC distintas a las seleccioandas
			// - Fecha de reprogramación < hoy 
			// - pendiente > 0
			Long[] ddcorderids = Arrays.stream(ddcReportVAlidOrdersArr)
													.filter(ocr -> ddcOrderWs.stream()
																.noneMatch(oc -> oc.getId().equals(ocr.getDdcorderid()))
													&& (ocr.getCurrentdeliverydate().toLocalDate().isBefore(LocalDate.now()))
													&& (ocr.getPendingunits() > 0))
					.map(oc-> oc.getDdcorderid())
					.toArray(Long[]::new);
			
			
			if(ddcorderids.length > 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No puede solicitar cita de cualquier OC si aún tiene otras que se encuentren vigentes, pendientes de reprogramar y con artículos sin despachar.", false);
			}
			
			List<Long> ddcDeliveryNumbers = new ArrayList<Long>();
			DdcOrderDetailW[] ddcOrderDetailWs;
			DdcDeliveryDetailW ddcDeliveryDetailW;
			PropertyInfoDTO prop1;
			PropertyInfoDTO prop2;
			for (DdcOrderW ddcOrder : ddcOrderWs) {
				// Generar un despacho en estado 'En Ruta' por el total de unidades pendientes
				ddcDeliveryW = new DdcDeliveryW();
				ddcDeliveryW.setNumber(ddcdeliveryserver.getNextSequenceDeliveryNumber());
				ddcDeliveryW.setOriginaldate(ddcOrder.getCurrentdeliverydate());
				ddcDeliveryW.setCommitteddate(null);
				ddcDeliveryW.setCurrentstatetypedate(now);
				ddcDeliveryW.setCurrentstatetypewho(userDataDTO.getUsername());
				ddcDeliveryW.setCurrentstatetypecomment("Despachando");
				ddcDeliveryW.setDdcorderid(ddcOrder.getId());
				ddcDeliveryW.setCurrentstatetypeid(dstOnRoute.getId());
				ddcDeliveryW.setVendorid(vendorW.getId());
				
				ddcDeliveryW = ddcdeliveryserver.addDdcDelivery(ddcDeliveryW);
				
				ddcDeliveryNumbers.add(ddcDeliveryW.getNumber());
				
				// Agregar el estado del despacho
				DdcDeliveryStateW ddcDeliveryStateW = new DdcDeliveryStateW();
				ddcDeliveryStateW.setWhen(now);
				ddcDeliveryStateW.setUsername(userDataDTO.getUsername());
				ddcDeliveryStateW.setUsertype(userDataDTO.getUsertype());
				ddcDeliveryStateW.setStatedate(ddcDeliveryW.getCommitteddate());
				ddcDeliveryStateW.setComment("Despachando");
				ddcDeliveryStateW.setDdcdeliveryid(ddcDeliveryW.getId());
				ddcDeliveryStateW.setDdcdeliverystatetypeid(dstOnRoute.getId());
				
				ddcdeliverystateserver.addDdcDeliveryState(ddcDeliveryStateW);
				
				// Generar detalles del despacho por todos aquellos SKU que presentan unidades pendientes mayores a cero
				prop1 = new PropertyInfoDTO("ddcorder.id", "ddcorderid", ddcOrder.getId());
				prop2 = new PropertyInfoDTO("pendingunits", "pendingunits", 0.0, ComparisonOperator.GREATER_THAN);
				ddcOrderDetailWs = ddcorderdetailserver.getByPropertiesAsArray(prop1, prop2); 
				
				for (DdcOrderDetailW ddcOrderDetailW : ddcOrderDetailWs) {
					ddcDeliveryDetailW = new DdcDeliveryDetailW();
					ddcDeliveryDetailW.setPendingunits(ddcOrderDetailW.getPendingunits());
					ddcDeliveryDetailW.setAvailableunits(ddcOrderDetailW.getPendingunits());
					ddcDeliveryDetailW.setReceivedunits(0.0);
					ddcDeliveryDetailW.setDdcdeliveryid(ddcDeliveryW.getId());
					ddcDeliveryDetailW.setItemid(ddcOrderDetailW.getItemid());
					ddcDeliveryDetailW.setPosition(ddcOrderDetailW.getPosition());
					
					ddcdeliverydetailserver.addDdcDeliveryDetail(ddcDeliveryDetailW);
				}
				
				// Actualizar la orden con su último despacho
				ddcOrder.setCurrentddcdeliveryid(ddcDeliveryW.getId());
				
				ddcOrder = ddcorderserver.updateDdcOrder(ddcOrder);			
				ddcorderserver.flush();
				
				// Aceptar OC DDC
				doAcceptDdcOrder(ddcOrder.getId(), userDataDTO.getUsername(), userDataDTO.getUsertype(), "Despachando");
				ddcorderserver.flush();
			}
			
			// Recalcular las unidades de las órdenes			 
			doCalculateTotalOfDdcOrders(ddcOrderWs.stream().map(oc -> oc.getId()).toArray(Long[]::new));

			resultDTO.setDdcdeliverynumbers((Long[]) ddcDeliveryNumbers.toArray(new Long[ddcDeliveryNumbers.size()]));

		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public DdcFileArrayResultDTO getDdcFilesByDdcOrders(DdcFileInitParamDTO initParamDTO) {
		DdcFileArrayResultDTO resultDTO = new DdcFileArrayResultDTO();
		
		try {
			// Valida que se haya seleccionado algún registro
			if (initParamDTO.getDdcorderids() == null || initParamDTO.getDdcorderids().length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1912");
			}
			
			// Obtener el proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Obtener el tipo de estado de la orden DDC 'Recepcionada'
			DdcOrderStateTypeW ostReceived = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCORDER_STATE_RECEIVED);
			
			DdcOrderW ddcOrderW;
			for (Long ddcOrderId : initParamDTO.getDdcorderids()) {
				// Obtener la orden DDC
				ddcOrderW = ddcorderserver.getById(ddcOrderId);
				
				// Valida la existencia de las órdenes DDC para ese proveedor
				if (!ddcOrderW.getVendorid().equals(vendorW.getId())) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
				}
				
				// Valida que la orden se encuentre en estado 'Recepcionada'
				if (!ddcOrderW.getCurrentstatetypeid().equals(ostReceived.getId())) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1913");
				}
			}
			
			// Valida que se hayan encontrado archivos para las órdenes
			DdcFileDTO[] ddcFiles = ddcfileserver.getDdcFilesByDdcOrders(initParamDTO.getDdcorderids());
			if (Arrays.stream(ddcFiles)
					  .filter(file -> file.getDdcfilename() == null || file.getDdcfilename().equals(""))
					  .count() > 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1914");
			}
			
			resultDTO.setDdcfiles(ddcFiles);

		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public DdcOrderIdsByPagesDTO getDdcFileOrderIdsByPages(DdcOrderReportInitParamDTO initParamDTO, PageRangeDTO[] pageRangeDTO) {

		DdcOrderIdsByPagesDTO resultDTO = new DdcOrderIdsByPagesDTO();
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];

			Long[] ddcOrderIds = ddcorderserver.getDdcOrderIdsByPages(vendorW.getId(), initParamDTO.getFiltervalue(), initParamDTO.getSince(), initParamDTO.getUntil(), initParamDTO.getDdcorderstatetypeid(), initParamDTO.getFiltertype(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangeDTO);

			resultDTO.setDdcorderids(ddcOrderIds);

		} catch (NotFoundException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		} catch (AccessDeniedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}
		
		return resultDTO;
	}
	
	// DVH
	public DvhOrderReportResultDTO getDvhOrderReportByVendorAndFilter(DvhOrderReportInitParamDTO initParamDTO, boolean withTotals, boolean isPaginated){
		DvhOrderReportResultDTO resultDTO = new DvhOrderReportResultDTO();
		DvhOrderReportDataDTO[] dvhordersArr = null;
		int total;
		
		try{
			//LocalDateTime now = LocalDateTime.now();
						
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];

			// Local de entrega
			if (! initParamDTO.getDeliverylocationid().equals(new Long(LogisticConstants.getINT_TODOS()))){
				LocationW[] locationArr = locationserver.getByPropertyAsArray("id", initParamDTO.getDeliverylocationid()); 
				if(locationArr.length == 0){
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1904");
				}
			}
			
			// Datos
			dvhordersArr = dvrorderserver.getDvhOrdersByVendorAndCriterium(vendorW.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getSearchvalue(), initParamDTO.getOrderstatetypeid(), initParamDTO.getSince(), initParamDTO.getUntil(), initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
			// Si es consulta desde el filtro, se muesta información de la paginación
			if(withTotals){
				total = dvrorderserver.getCountDvhOrdersByVendorAndCriterium(vendorW.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getSearchvalue(), initParamDTO.getOrderstatetypeid(), initParamDTO.getSince(), initParamDTO.getUntil(), initParamDTO.getFiltertype());

				// Setea Información de paginación
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(dvhordersArr.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
				resultDTO.setTotalreg(total);
			}
			
			resultDTO.setDvhorders(dvhordersArr);
			
			return resultDTO;
			
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1801");
			
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1801");
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1801");
		}

	}

	
	public DvrOrderIdsByPagesDTO getDvhOrderIdsByPages(DvhOrderReportInitParamDTO initParamDTO, PageRangeDTO[] pageRangeDTO) {

		DvrOrderIdsByPagesDTO resultDTO = new DvrOrderIdsByPagesDTO();
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];

			// Local de entrega
			if (!initParamDTO.getDeliverylocationid().equals(new Long(LogisticConstants.getINT_TODOS()))){
				LocationW[] locationArr = locationserver.getByPropertyAsArray("id", initParamDTO.getDeliverylocationid()); 
				if (locationArr.length == 0) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1904");
				}
			}
						
			Long[] dvrOrderIds = dvrorderserver.getDvhOrderIdsByPages(vendorW.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getSearchvalue(), initParamDTO.getOrderstatetypeid(), initParamDTO.getSince(), initParamDTO.getUntil(), initParamDTO.getFiltertype(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangeDTO);

			// Validar la cantidad de registros con el máximo permitido
			if (dvrOrderIds != null && dvrOrderIds.length > LogisticConstants.getEXCEL_MAX_NUMBER_OF_ROWS()) {				
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
			}
			
			resultDTO.setDvrorderids(dvrOrderIds);

		} catch (NotFoundException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		} catch (AccessDeniedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}
		
		return resultDTO;
	}

	public DvhOrderDetailReportResultDTO getDvhOrderDetailByOrder(DvhOrderDetailReportInitParamDTO initParamDTO, UserLogDataDTO userDataDTO, boolean providerUser, boolean withTotals, boolean isPaginated){
		
		DvhOrderDetailReportResultDTO resultDTO = new DvhOrderDetailReportResultDTO();
		DvhOrderDetailDataDTO[] dvhorderdetail;
		DvhOrderDetailReportTotalDataDTO totalresult;
		int total;
		
		try{
			// Tipo de Orden DVH
			OrderTypeW dvhTypeW = ordertypeserver.getByPropertyAsSingleResult("code", "DVH"); 
			
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Chequea la existencia de la orden dvr para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParamDTO.getDvrorderid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
			DvrOrderW[] dvrorderArr = dvrorderserver.getByPropertiesAsArray(properties);

			if (dvrorderArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
			}
			DvrOrderW dvrorderW = dvrorderArr[0];
			
			// Obtiene datos
			dvhorderdetail = dvrorderdetailserver.getDvhOrdersDetailByDvrOrder(initParamDTO.getDvrorderid(), vendorW.getId(), dvhTypeW.getId(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
			
			if(withTotals){
				totalresult = dvrorderdetailserver.getCountDvhOrdersDetailsByOrder(initParamDTO.getDvrorderid(), vendorW.getId(), dvhTypeW.getId());

				// Setear Información de la Paginación
				int rows = initParamDTO.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(dvhorderdetail.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
				resultDTO.setTotal(totalresult);
			}
			
			// TODO MME: SE ACEPTA LA OC SI ES PROVEEDOR Y ESTA EN ESTADO LIBERADA O MODIFICADA
			if(providerUser){
				// Acepta OC DVR
				doAcceptDvrOrder(initParamDTO.getDvrorderid(), userDataDTO.getUsername(), "PROVEEDOR");
				dvrorderserver.flush();
			}

			// Obtiene información de la OC
			DvhOrderReportDataDTO[] currentdvrorderArr = dvrorderserver.getDvhOrdersByVendorAndCriterium(vendorW.getId(), dvrorderW.getDeliverylocationid(), dvrorderW.getNumber().toString(), null, null, null, 2, 0, 0, null, false);
			
			resultDTO.setDvhorder(currentdvrorderArr[0]);
			resultDTO.setDvhorderdetail(dvhorderdetail);
			return resultDTO;
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1901");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1901");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1901");
		}	
	}

	// Valida si es posible reprogramar las fechas de entregas de las OC seleccionadas
	public CheckDVHOrdersExecuteActionsResultDTO doCheckDVHOrdersExecuteActions(DvrOrdersVendorInitParamDTO initParamDTO)
	{
		CheckDVHOrdersExecuteActionsResultDTO resultDTO = new CheckDVHOrdersExecuteActionsResultDTO();
		Boolean existsDeliveryToDelete = false;
		
		try {
			// Tipo de Orden DVH
			OrderTypeW dvhTypeW = ordertypeserver.getByPropertyAsSingleResult("code", "DVH"); 
				
			// Estados Vigentes de Ordenes	
			DvrOrderStateTypeW[] validOCStateArr = dvrorderstatetypeserver.getByPropertyAsArray("valid", true);
			
			// Estados DvrDelivery
			DvrDeliveryStateTypeW reqSt  = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_SOLICITADA");
			DvrDeliveryStateTypeW datSt  = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");
			DvrDeliveryStateTypeW rschSt = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "RE_AGENDAR");
			
			// Estados del despacho permitidos para realizar la acción
			Long[] allowedDeliveryStIds = {reqSt.getId(), datSt.getId(), rschSt.getId()};
			
			// Valida que se infomen OC
			if(initParamDTO.getDvrorderids() == null || initParamDTO.getDvrorderids().length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L405");
			}
			
			Long[] orderids = Arrays.stream(initParamDTO.getDvrorderids()).distinct().toArray(Long[]::new);
			
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Obtiene DVH
			DvrOrderW[] dvhOrderArr = dvrorderserver.getDvhOrderByIdsAndVendor(orderids, vendorW.getId(), dvhTypeW.getId());
			if(dvhOrderArr.length != orderids.length) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1930");
			}
			
			if(dvhOrderArr.length > 0) {
				
				// Valida que las OC seleccionadas se encuentren en estados vigentes
				DvrOrderW[] validDvhOrderArr = Arrays.stream(dvhOrderArr)
													.filter(oc ->  Arrays.stream(validOCStateArr)
																				.anyMatch(st-> st.getId().equals(oc.getCurrentstatetypeid())))
													.toArray(DvrOrderW[]::new);
				
				if(validDvhOrderArr.length != dvhOrderArr.length) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1931");
				}
				
				// Busca si OC seleccionadas tienen entregas asociadas
				Long[] validDvhOrderIdsArr = Arrays.stream(validDvhOrderArr).map(oc->oc.getId()).toArray(Long[]::new);
				
				DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getDvrDeliveryByOrderIds(validDvhOrderIdsArr);
				
				// Existen al menos una OC con entregas
				if(dvrdeliveryArr.length > 0) {
					// Busca entregas que estén en estados distintos a  "Reagendar", "Cita Solicitada" o "Cita Asignada"
					Long[] dvrDeliveryIdNotValid = Arrays.stream(dvrdeliveryArr)
													.filter(del -> Arrays.stream(allowedDeliveryStIds)
																		.noneMatch(st -> st.equals(del.getCurrentstatetypeid())))
													.map(del->del.getId())
													.toArray(Long[]::new);
					
					// Al menos una de las OC tiene entregas en otros estados
					if(dvrDeliveryIdNotValid.length > 0) {
						return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1932");
					}
					else {
						existsDeliveryToDelete = true;
					}
				}

				resultDTO.setExistsDeliveryToDelete(existsDeliveryToDelete);

			}
			
			return resultDTO;
		
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}
	
	// Actualiza las fechas de entrga de Ordenes DVH
	public RescheduleDateDVHOrdersResultDTO doRescheduleDateToDVHOrders(RescheduleDateDVHOrdersInitParamDTO initParamDTO,  UserLogDataDTO userDataDTO) {	
		RescheduleDateDVHOrdersResultDTO resultDTO = new RescheduleDateDVHOrdersResultDTO();
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nowDate = now.toLocalDate().atStartOfDay();
		
		DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		// Errores
		List<BaseResultDTO> validationErrorList = new ArrayList<BaseResultDTO>();
		String error ="";
		
		String comment = "";
		
		// Mapas
		// Key: ordernumber
		// Value: DvrOrderW
		Map<Long, DvrOrderW> dvrOrderMap = new HashMap<Long, DvrOrderW>();
		
		// Para validar OC Repetidas
		// Key: OC Number
		Set<Long> dvrOrderFileUniqueValidationSet = new HashSet<Long>();
		
		try {
			// Tipo de Orden DVH
			OrderTypeW dvhTypeW = ordertypeserver.getByPropertyAsSingleResult("code", "DVH"); 
					
			// Estado de OC "REPROGRAMADA
			DvrOrderStateTypeW reschSt = dvrorderstatetypeserver.getByPropertyAsSingleResult("code", "REPROGRAMADA");
			
			// Maxima cantidad de errores a mostrar
			Integer  maxErrorsQtyToShow =  LogisticConstants.getMAX_LIST_ERROR_TO_SHOW();
			
			// Validaciones de las OC seleccionadas
			Long[] dvrOrderNumbersArr = Arrays.stream(initParamDTO.getRescheduledatedvhordersdata()).map(oc -> oc.getDvrordernumber()).toArray(Long[]::new);
			DvrOrderW[] initParamOrdersArr = dvrorderserver.getDvrOrderByNumbers(dvrOrderNumbersArr);
			Long[] dvrorderids = Arrays.stream(initParamOrdersArr).map(oc -> oc.getId()).toArray(Long[]::new);
					
			DvrOrdersVendorInitParamDTO checkInitParam = new DvrOrdersVendorInitParamDTO();
			checkInitParam.setDvrorderids(dvrorderids);
			checkInitParam.setVendorcode(initParamDTO.getVendorcode());
			CheckDVHOrdersExecuteActionsResultDTO checkResultDTO = doCheckDVHOrdersExecuteActions(checkInitParam);
			if(! checkResultDTO.getStatuscode().equals("0")) {
				resultDTO.setStatuscode(checkResultDTO.getStatuscode());
				resultDTO.setStatusmessage(checkResultDTO.getStatusmessage());
				return resultDTO;
			}
					
			// Proveedor (ya se validó que existe)
			VendorW vendorW = vendorserver.getByPropertyAsSingleResult("code", initParamDTO.getVendorcode());
			
			
			// Validaciones previas de los datos informados
			for(RescheduleDateDVHOrdersDataDTO rescheduledata : initParamDTO.getRescheduledatedvhordersdata()) {
				
				// Si la llamada se hizo desde el servicio que prcesa archivo, se agrega el prefijo con el número de línea
				// al mensaje de error
				String prefix = initParamDTO.getFilename() != null ? "Fila " + rescheduledata.getRownumber() + ": " : "";
				
				// Obtiene orden informada
				PropertyInfoDTO[] props = new PropertyInfoDTO[3];
				props[0] = new PropertyInfoDTO("ordertype.id", "ordertypeid", dvhTypeW.getId());
				props[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
				props[2] = new PropertyInfoDTO("number", "number", rescheduledata.getDvrordernumber());
						
				DvrOrderW[] dvhOrderArr = dvrorderserver.getByPropertiesAsArray(props);
				if(dvhOrderArr.length == 0) {
					error = prefix + "Orden Nro " + rescheduledata.getDvrordernumber() + " no existe para el proveedor";
					validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					
					// Fin validaciones para orden actual
					continue;
				}	
				
				// Validar que no se informn OC repetidas
				if(! dvrOrderFileUniqueValidationSet.contains(rescheduledata.getDvrordernumber())){
					dvrOrderFileUniqueValidationSet.add(rescheduledata.getDvrordernumber());
				}
				else {
					error =   prefix + ": El número de orden " + rescheduledata.getDvrordernumber() + " se ha informado más de una vez";
					validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				
				// Guarda orden en mapa
				dvrOrderMap.put(dvhOrderArr[0].getNumber(), dvhOrderArr[0]);
				
				// Valida que fecha de entrega de la orden sea igual o posterior a la fecha actual (hoy)
				
				if(rescheduledata.getRescheduledate().isBefore(nowDate)){
					error =  prefix + "Para la orden de compra " + rescheduledata.getDvrordernumber()  + ", se está ingresando una fecha anterior a hoy.";
					validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				
				// Valida que fecha de entrega de la orden sea anterior o igual a su fecha de vencimiento
				if(rescheduledata.getRescheduledate().isAfter(dvhOrderArr[0].getExpiration())) {
					error =  prefix + "Para la orden de compra " + rescheduledata.getDvrordernumber()  + ", se está ingresando una fecha que es posterior a su vencimiento (" + dtf.format(dvhOrderArr[0].getExpiration()) + ")";
					validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
			
				// El sistema valida que los comentarios ingresados no superen el largo máximo definido.
				if(rescheduledata.getComment() != null ) {
					if(rescheduledata.getComment().length() > RESCHEDULING_MAXLENGTH_COMMENT) {
						error =  prefix + "Para la orden de compra " + rescheduledata.getDvrordernumber() + ", se está ingresando un comentario que supera los " + REJECT_MAXLENGTH_COMMENT + " caracteres.";
						validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
					else {
						comment = rescheduledata.getComment();	
					}
				}
			}
			
			// Si existen errores en los datos de entrada, se informa el error
			if(validationErrorList.size() > 0) {
				// Calcula cantidad de errores a mostrar
				int errorsSize = validationErrorList.size();
				if(validationErrorList.size() > maxErrorsQtyToShow){
					errorsSize = maxErrorsQtyToShow;
				}

				BaseResultDTO[] curResultArr = new BaseResultDTO[errorsSize];
				for (int i = 0; i < errorsSize; i++) {
					BaseResultDTO curResult = new BaseResultDTO();
					curResult.setStatuscode("L1");
					curResult.setStatusmessage(validationErrorList.get(i).getStatusmessage());
					curResultArr[i] = curResult;
				}
				resultDTO.setValidationerrors(curResultArr);
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}
			
			
			// No hay errores, se hace la llamada a WS de Hites
			// TODO MME: Implementar llamada	
			
			// El sistema identifica todas las OC cuyas fechas fueron aceptadas por Hites.
			
			// Guarda Nro de OC
			StringBuilder ordersSb = new StringBuilder();
			String separator = "";
			
			// Guada ID de las OC
			List<Long> modDvhorderids = new ArrayList<Long>();
			
			// TODO MME: Provisoriamente, las toma todas
			for(RescheduleDateDVHOrdersDataDTO rescheduledata : initParamDTO.getRescheduledatedvhordersdata()) {
				// Obtiene orden
				DvrOrderW dvhorderW = dvrOrderMap.get(rescheduledata.getDvrordernumber());
				
				// Guarda Nro de Orden para LOG
				ordersSb.append(separator);
				separator = "-";
				ordersSb.append(dvhorderW.getNumber());
				
				// Guarda ID de las OC que serán actualizadas
				modDvhorderids.add(dvhorderW.getId());
				
				// Se eliminan las entregas de esas OC. 
						
				// Busca DvrDelivery de la Orden
				// Fue validado previamente que las entregas existntes están en estados  "Reagendar", "Cita Solicitada" o "Cita Asignada"
				Long[] currdvrorderids = {dvhorderW.getId()};
				DvrDeliveryW[] dvrdeliveryArr = drdvrdeliveryserver.getDvrDeliveryByOrderIds(currdvrorderids);
				
				if(dvrdeliveryArr.length > 0) {
					// Busca si existen cita asociada
					Long[] dvrdeliveryIds = Arrays.stream(dvrdeliveryArr).map(del -> del.getId()).toArray(Long[]::new);
					DatingW[] datingArr = datingserver.getDatingByDvrDeliveryIds(dvrdeliveryIds);
					if(datingArr.length > 0) {
						
						// Elimina cita
						for(DatingW datingW : datingArr) {
							DeleteDatingInitParamDTO datinginitParamDTO = new DeleteDatingInitParamDTO();
							datinginitParamDTO.setComment("Eliminada automáticamente por reprogramación de proveedor");
							datinginitParamDTO.setReserveid(datingW.getId());
							BaseResultDTO deleteDatingResultDTO = datingreportmanager.doDeleteDating(datinginitParamDTO, userDataDTO);
							if(! deleteDatingResultDTO.getStatuscode().equals("0")) {
								resultDTO.setStatuscode(deleteDatingResultDTO.getStatuscode());
								resultDTO.setStatusmessage(deleteDatingResultDTO.getStatusmessage());
								getSessionContext().setRollbackOnly();
								return resultDTO;
							}
							
						}
					}
					
					for(DvrDeliveryW dvrdeliveryW : dvrdeliveryArr) {
						// Se rechazan las solictudes de cita asociadas
						DatingRequestRejectInitParamDTO rejectInitParamDTO = new DatingRequestRejectInitParamDTO();
						rejectInitParamDTO.setDvrdeliveryid(dvrdeliveryW.getId());
						//rejectInitParamDTO.setLocationcode(locationcode);
						rejectInitParamDTO.setReason("Eliminada automáticamente por reprogramación de proveedor");
						BaseResultDTO datingrequestResultDTO = datingreportmanager.doRejectDatingRequest(rejectInitParamDTO, userDataDTO);
						if(! datingrequestResultDTO.getStatuscode().equals("0")) {
							resultDTO.setStatuscode(datingrequestResultDTO.getStatuscode());
							resultDTO.setStatusmessage(datingrequestResultDTO.getStatusmessage());
							getSessionContext().setRollbackOnly();
							return resultDTO;
						}
						
						// Se eliminan los despachos
						DvrDeliveryDeleteInitParamDTO deletedeliveryInitParamDTO = new DvrDeliveryDeleteInitParamDTO();
						deletedeliveryInitParamDTO.setDvrdeliveryid(dvrdeliveryW.getId());
						deletedeliveryInitParamDTO.setVendorcode(vendorW.getCode());
						BaseResultDTO deliveryResultDTO = deliveryreportmanagerlocal.doDeleteDvrDelivery(deletedeliveryInitParamDTO, userDataDTO);
						if(! deliveryResultDTO.getStatuscode().equals("0")) {
							resultDTO.setStatuscode(deliveryResultDTO.getStatuscode());
							resultDTO.setStatusmessage(deliveryResultDTO.getStatusmessage());
							getSessionContext().setRollbackOnly();
							return resultDTO;
						}
					}
				}
				
				// Actualiza las fechas de reprogramación según lo ingresado por el usuario.
				dvhorderW.setReschedulingdate(rescheduledata.getRescheduledate());
				
				// Aumenta en uno el contador de reprogramaciones.
				Integer count = dvhorderW.getReschedulingcounter() != null ? dvhorderW.getReschedulingcounter() : 0;
				dvhorderW.setReschedulingcounter(count + 1);
				dvhorderW.setCurrentstatetypeid(reschSt.getId());
				dvhorderW.setComment(comment);
				dvhorderW = dvrorderserver.updateDvrOrder(dvhorderW);
				
				// Actualiza al estado "Reprogramada", generando una nueva entrada en su historial de modificaciones 
				// que refleje el comentario ingresado por el usuario.// Estado
				DvrOrderStateW newstate = new DvrOrderStateW(); 
				newstate.setDvrorderid(dvhorderW.getId());
				newstate.setDvrorderstatetypeid(reschSt.getId());
				newstate.setWhen(now);
				newstate.setWho(userDataDTO.getUsername());
				newstate = dvrorderstateserver.addDvrOrderState(newstate);

			}
			 //TODO MME: revisar si es necesario hacer esta llamada
			// Calculo de totale de las OC informadas
			Long[] orderidsAr = (Long[]) modDvhorderids.toArray(new Long[modDvhorderids.size()]);
			doCalculateTotalOfDvrOrders(orderidsAr);
						
			return resultDTO;
			
							
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}	
	}

	
	// Rechaza las Ordenes DVH
	public RejectDVHOrdersResultDTO doRejectDVHOrders(RejectDVHOrdersInitParamDTO initParamDTO, UserLogDataDTO userDataDTO) {	
		
		RejectDVHOrdersResultDTO resultDTO = new RejectDVHOrdersResultDTO();

		
		LocalDateTime now = LocalDateTime.now();
		
		// Errores
		List<BaseResultDTO> validationErrorList = new ArrayList<BaseResultDTO>();
		String error ="";
		
		String comment= "";
		
		// Mapas
		// Key: ordernumber
		// Value: DvrOrderW
		Map<Long, DvrOrderW> dvrOrderMap = new HashMap<Long, DvrOrderW>();
		
		// Para validar OC Repetidas
		// Key: OC Number
		Set<Long> dvrOrderFileUniqueValidationSet = new HashSet<Long>();
		
		try {
			// Tipo de Orden DVH
			OrderTypeW dvhTypeW = ordertypeserver.getByPropertyAsSingleResult("code", "DVH"); 
					
			// Estado de OC "REPROGRAMADA
			DvrOrderStateTypeW rechSt = dvrorderstatetypeserver.getByPropertyAsSingleResult("code", "RECHAZADA_PROVEEDOR");
			
			// Maxima cantidad de errores a mostrar
			Integer  maxErrorsQtyToShow =  LogisticConstants.getMAX_LIST_ERROR_TO_SHOW();
			
			// Validaciones de las OC seleccionadas
			Long[] dvrOrderNumbersArr = Arrays.stream(initParamDTO.getRejectdvhordersdata()).map(oc -> oc.getDvrordernumber()).toArray(Long[]::new);
			DvrOrderW[] initParamOrdersArr = dvrorderserver.getDvrOrderByNumbers(dvrOrderNumbersArr);
			Long[] dvrorderids = Arrays.stream(initParamOrdersArr).map(oc -> oc.getId()).toArray(Long[]::new);
					
			DvrOrdersVendorInitParamDTO checkInitParam = new DvrOrdersVendorInitParamDTO();
			checkInitParam.setDvrorderids(dvrorderids);
			checkInitParam.setVendorcode(initParamDTO.getVendorcode());
			BaseResultDTO checkResultDTO = doCheckDVHOrdersExecuteActions(checkInitParam);
			if(! checkResultDTO.getStatuscode().equals("0")) {
				resultDTO.setStatuscode(checkResultDTO.getStatuscode());
				resultDTO.setStatusmessage(checkResultDTO.getStatusmessage());
				return resultDTO;
			}
			
			// Proveedor (ya se validó que existe)
			VendorW vendorW = vendorserver.getByPropertyAsSingleResult("code", initParamDTO.getVendorcode());
			
			
			// Validaciones previas de los datos informados
			for(RejectDVHOrdersDataDTO rejectdata : initParamDTO.getRejectdvhordersdata()) {
				// Si la llamada se hizo desde el servicio que prcesa archivo, se agrega el prefijo con el número de línea
				// al mensaje de error
				String prefix = initParamDTO.getFilename() != null ? "Fila " + rejectdata.getRownumber() + ": " : "";

				// Obtiene orden informada
				PropertyInfoDTO[] props = new PropertyInfoDTO[3];
				props[0] = new PropertyInfoDTO("ordertype.id", "ordertypeid", dvhTypeW.getId());
				props[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
				props[2] = new PropertyInfoDTO("number", "number", rejectdata.getDvrordernumber());
				
						
				DvrOrderW[] dvhOrderArr = dvrorderserver.getByPropertiesAsArray(props);
				if(dvhOrderArr.length == 0) {
					
					error = prefix + "Orden Nro " + rejectdata.getDvrordernumber() + " no existe para el proveedor";
					validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					
					// Fin validaciones para orden actual
					continue;
				}	
				
				// Validar que no se informn OC repetidas
				if(! dvrOrderFileUniqueValidationSet.contains(rejectdata.getDvrordernumber())){
					dvrOrderFileUniqueValidationSet.add(rejectdata.getDvrordernumber());
				}
				else {
					error =   prefix + ": El número de orden " + rejectdata.getDvrordernumber() + " se ha informado más de una vez";
					validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				
				// Guarda orden en mapa
				dvrOrderMap.put(dvhOrderArr[0].getNumber(), dvhOrderArr[0]);
			
				// El sistema valida que los comentarios ingresados no superen el largo máximo definido.
				if(rejectdata.getComment() != null ) {
					if(rejectdata.getComment().length() > REJECT_MAXLENGTH_COMMENT) {
						error =  prefix + "Para la orden de compra " + rejectdata.getDvrordernumber() + ", se está ingresando un comentario que supera los " + REJECT_MAXLENGTH_COMMENT + " caracteres.";
						validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
					else {
						comment = rejectdata.getComment();	
					}
				}
			}
			
			// Si existen errores en los datos de entrada, se informa el error
			if(validationErrorList.size() > 0) {
				// Calcula cantidad de errores a mostrar
				int errorsSize = validationErrorList.size();
				if(validationErrorList.size() > maxErrorsQtyToShow){
					errorsSize = maxErrorsQtyToShow;
				}
	
				BaseResultDTO[] curResultArr = new BaseResultDTO[errorsSize];
				for (int i = 0; i < errorsSize; i++) {
					BaseResultDTO curResult = new BaseResultDTO();
					curResult.setStatuscode("L1");
					curResult.setStatusmessage(validationErrorList.get(i).getStatusmessage());
					curResultArr[i] = curResult;
				}
				resultDTO.setValidationerrors(curResultArr);
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}

			// No hay errores, se hace la llamada a WS de Hites
			// TODO MME: Implementar llamada	
						
			// El sistema identifica todas las OC cuyos rechazos fueron aceptados por Hites.
			
			// Guarda Nro de OC
			StringBuilder ordersSb = new StringBuilder();
			String separator = "";
			
			// Guada ID de las OC
			List<Long> modDvhorderids = new ArrayList<Long>();
			
			// TODO MME: Provisoriamente, las toma todas
			for(RejectDVHOrdersDataDTO rejectdata : initParamDTO.getRejectdvhordersdata()) {
				// Obtiene orden
				DvrOrderW dvhorderW = dvrOrderMap.get(rejectdata.getDvrordernumber());
				
				// Guarda Nro de Orden para LOG
				ordersSb.append(separator);
				separator = "-";
				ordersSb.append(dvhorderW.getNumber());
				
				// Guarda ID de las OC que serán actualizadas
				modDvhorderids.add(dvhorderW.getId());
				

				// Se eliminan las entregas de esas OC. 
						
				// Busca DvrDelivery de la Orden
				// Fue validado previamente que las entregas existntes están en estados  "Reagendar", "Cita Solicitada" o "Cita Asignada"
				Long[] currdvrorderids = {dvhorderW.getId()};
				DvrDeliveryW[] dvrdeliveryArr = drdvrdeliveryserver.getDvrDeliveryByOrderIds(currdvrorderids);
				
				if(dvrdeliveryArr.length > 0) {
					// Busca si existen cita asociada
					Long[] dvrdeliveryIds = Arrays.stream(dvrdeliveryArr).map(del -> del.getId()).toArray(Long[]::new);
					DatingW[] datingArr = datingserver.getDatingByDvrDeliveryIds(dvrdeliveryIds);
					if(datingArr.length > 0) {
						
						// Elimina cita
						for(DatingW datingW : datingArr) {
							DeleteDatingInitParamDTO datinginitParamDTO = new DeleteDatingInitParamDTO();
							datinginitParamDTO.setComment("Eliminada automáticamente por reprogramación de proveedor");
							datinginitParamDTO.setReserveid(datingW.getId());
							BaseResultDTO deleteDatingResultDTO = datingreportmanager.doDeleteDating(datinginitParamDTO, userDataDTO);
							if(! deleteDatingResultDTO.getStatuscode().equals("0")) {
								resultDTO.setStatuscode(deleteDatingResultDTO.getStatuscode());
								resultDTO.setStatusmessage(deleteDatingResultDTO.getStatusmessage());
								getSessionContext().setRollbackOnly();
								return resultDTO;
							}
							
						}
					}
					
					for(DvrDeliveryW dvrdeliveryW : dvrdeliveryArr) {
						// Se rechazan las solictudes de cita asociadas
						DatingRequestRejectInitParamDTO rejectInitParamDTO = new DatingRequestRejectInitParamDTO();
						rejectInitParamDTO.setDvrdeliveryid(dvrdeliveryW.getId());
						//rejectInitParamDTO.setLocationcode(locationcode);
						rejectInitParamDTO.setReason("Eliminada automáticamente por reprogramación de proveedor");
						BaseResultDTO datingrequestResultDTO = datingreportmanager.doRejectDatingRequest(rejectInitParamDTO, userDataDTO);
						if(! datingrequestResultDTO.getStatuscode().equals("0")) {
							resultDTO.setStatuscode(datingrequestResultDTO.getStatuscode());
							resultDTO.setStatusmessage(datingrequestResultDTO.getStatusmessage());
							getSessionContext().setRollbackOnly();
							return resultDTO;
						}
						
						// Se eliminan los despachos
						DvrDeliveryDeleteInitParamDTO deletedeliveryInitParamDTO = new DvrDeliveryDeleteInitParamDTO();
						deletedeliveryInitParamDTO.setDvrdeliveryid(dvrdeliveryW.getId());
						deletedeliveryInitParamDTO.setVendorcode(vendorW.getCode());
						BaseResultDTO deliveryResultDTO = deliveryreportmanagerlocal.doDeleteDvrDelivery(deletedeliveryInitParamDTO, userDataDTO);
						if(! deliveryResultDTO.getStatuscode().equals("0")) {
							resultDTO.setStatuscode(deliveryResultDTO.getStatuscode());
							resultDTO.setStatusmessage(deliveryResultDTO.getStatusmessage());
							getSessionContext().setRollbackOnly();
							return resultDTO;
						}
					}
				}

				// Actualiza estado de la OC
				dvhorderW.setCurrentstatetypeid(rechSt.getId());
				dvhorderW.setComment(comment);
				dvhorderW = dvrorderserver.updateDvrOrder(dvhorderW);
				
				// Actualiza al estado "Rechazda Proveedor", generando una nueva entrada en su historial de modificaciones 
				// que refleje el comentario ingresado por el usuario.// Estado
				DvrOrderStateW newstate = new DvrOrderStateW(); 
				newstate.setDvrorderid(dvhorderW.getId());
				newstate.setDvrorderstatetypeid(rechSt.getId());
				newstate.setWhen(now);
				newstate.setWho(userDataDTO.getUsername());
				
				newstate = dvrorderstateserver.addDvrOrderState(newstate);
				
			}

			return resultDTO;
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	// Valida si es posible reprogramar las fechas de entregas de las OC DDC seleccionadas
	public BaseResultDTO doCheckDDCOrdersExecuteActions(DdcOrdersVendorInitParamDTO initParamDTO)
	{
		BaseResultDTO resultDTO = new BaseResultDTO ();
		
		// Mapa de OC
		Map<Long, DdcOrderW> ddcOrderMap = new HashMap<Long, DdcOrderW>();
		
		try {
			
			// Estados Vigentes de Ordenes	
			DdcOrderStateTypeW[] validOCStateArr = ddcorderstatetypeserver.getByPropertyAsArray("valid", true);
			
			// Estados despachos
			DdcDeliveryStateTypeW rutSt = ddcdeliverystatetypeserver.getByPropertyAsSingleResult("code", "EN_RUTA");
			
			
			// Valida que se infomen OC
			if(initParamDTO.getDdcorderids() == null || initParamDTO.getDdcorderids().length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L405");
			}
			Long[] orderids = Arrays.stream(initParamDTO.getDdcorderids()).distinct().toArray(Long[]::new);

			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
						
			// Obtiene Ordenes
			DdcOrderW[] ddcOrderArr = ddcorderserver.getDdcOrderByIdsAndVendor(orderids, vendorW.getId());
			for(DdcOrderW ddcOrderW : ddcOrderArr) {
				ddcOrderMap.put(ddcOrderW.getId(), ddcOrderW);
			}
			
			
			if(ddcOrderArr.length > 0) {

				// Valida que las OC seleccionadas se encuentren en estados vigentes
				DdcOrderW[] validDdcOrderArr = Arrays.stream(ddcOrderArr)
													.filter(oc ->  Arrays.stream(validOCStateArr)
																				.anyMatch(st-> st.getId().equals(oc.getCurrentstatetypeid())))
													.toArray(DdcOrderW[]::new);
				
				if(validDdcOrderArr.length != ddcOrderArr.length) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1931");
				}
				Long[] ddcOrderIdsArr = Arrays.stream(ddcOrderArr).map(oc->oc.getId()).toArray(Long[]::new);
				
				// Busca despachos asociados a las ordenes
				DdcDeliveryW[] ddcdeliveryRelatedOrdersArr = ddcdeliveryserver.getDdcDeliveryByOrderids(ddcOrderIdsArr);

				// El sistema valida que las OC no tengan despachos con estado "En Ruta"
				Long[] ddcorderWithDeliveryRouteArr = Arrays.stream(ddcdeliveryRelatedOrdersArr)
															.filter(del -> del.getCurrentstatetypeid().equals(rutSt.getId()))
															.map(del-> del.getDdcorderid())
															.toArray(Long[]::new);


				// Se avisa al usuario, por cada OC, del error detectado
				if(ddcorderWithDeliveryRouteArr.length > 0) {
					StringBuilder sb = new StringBuilder();
					for(Long ddcorderid : ddcorderWithDeliveryRouteArr ) {
						Long ocnumber = ddcOrderMap.get(ddcorderid).getNumber();
						sb.append("La orden de compra " + ocnumber + " posee un despacho con estado 'En Ruta'\n");
					}
					String errorMsg = sb.toString();
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", errorMsg, false);
				}
				
				
				// El sistema valida que las OC tengan cantidades pendientes mayores a cero.
				Long[] ddcorderWithoutPending = Arrays.stream(validDdcOrderArr)
													.filter(oc->oc.getPendingunits() <= 0)
													.map(oc->oc.getId())
													.toArray(Long[]::new);
				
				if(ddcorderWithoutPending.length > 0) {
					StringBuilder sb = new StringBuilder();
					for(Long ddcorderid : ddcorderWithoutPending ) {
						Long ocnumber = ddcOrderMap.get(ddcorderid).getNumber();
						sb.append("La orden de compra "+ ocnumber + " no posee artículos pendientes por entregar\n");
					}
					String errorMsg = sb.toString();
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", errorMsg, false);
				}
			}
			
			return resultDTO;			
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}
	
	
	// Actualiza las fechas de compromiso de Ordenes DDC
	public RescheduleDateDDCOrdersResultDTO doRescheduleDateToDDCOrders(RescheduleDateDDCOrdersInitParamDTO initParamDTO,  UserLogDataDTO userDataDTO) {
		
		RescheduleDateDDCOrdersResultDTO resultDTO = new RescheduleDateDDCOrdersResultDTO();
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nowDate = now.toLocalDate().atStartOfDay();
		
		DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		
		// Errores
		List<BaseResultDTO> validationErrorList = new ArrayList<BaseResultDTO>();
		String error ="";
		
		String comment = "";
		
		// Mapas
		// Key: ordernumber
		// Value: DdcOrderW
		Map<Long, DdcOrderW> ddcOrderMap = new HashMap<Long, DdcOrderW>();
		
		// Para validar OC Repetidas
		// Key: OC Number
		Set<Long> ddcOrderFileUniqueValidationSet = new HashSet<Long>();
		
		try {
			// Estado de OC "REPROGRAMADA
			DdcOrderStateTypeW reschSt = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", "REPROGRAMADA");
			
			// Maxima cantidad de errores a mostrar
			Integer  maxErrorsQtyToShow =  LogisticConstants.getMAX_LIST_ERROR_TO_SHOW();
			
			// Validaciones de las OC seleccionadas
			Long[] ddcOrderNumbersArr = Arrays.stream(initParamDTO.getRescheduledateddcordersdata()).map(oc -> oc.getDdcordernumber()).toArray(Long[]::new);
			DdcOrderW[] initParamOrdersArr = ddcorderserver.getDdcOrderByNumbers(ddcOrderNumbersArr);
			Long[] ddcorderids = Arrays.stream(initParamOrdersArr).map(oc -> oc.getId()).toArray(Long[]::new);
					
			DdcOrdersVendorInitParamDTO checkInitParam = new DdcOrdersVendorInitParamDTO();
			checkInitParam.setDdcorderids(ddcorderids);
			checkInitParam.setVendorcode(initParamDTO.getVendorcode());
			BaseResultDTO checkResultDTO = doCheckDDCOrdersExecuteActions(checkInitParam);
			if(! checkResultDTO.getStatuscode().equals("0")) {
				resultDTO.setStatuscode(checkResultDTO.getStatuscode());
				resultDTO.setStatusmessage(checkResultDTO.getStatusmessage());
				return resultDTO;
			}
			
			// Obtiene proveedor (ya se validó que existe)
			VendorW vendorW = vendorserver.getByPropertyAsSingleResult("code", initParamDTO.getVendorcode());
			
			// Validaciones previas de los datos informados
			for(RescheduleDateDDCOrdersDataDTO rescheduledata : initParamDTO.getRescheduledateddcordersdata()) {

				// Si la llamada se hizo desde el servicio que prcesa archivo, se agrega el prefijo con el número de línea
				// al mensaje de error
				String prefix = initParamDTO.getFilename() != null ? "Fila " + rescheduledata.getRownumber() + ": " : "";
				
				// Obtiene orden informada
				PropertyInfoDTO[] props = new PropertyInfoDTO[2];
				props[0] = new PropertyInfoDTO("number", "number", rescheduledata.getDdcordernumber()); 
				props[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
				
				DdcOrderW[] ddcOrderArr = ddcorderserver.getByPropertiesAsArray(props);
				
				if(ddcOrderArr.length == 0) {
					error = prefix + "El número de orden " + rescheduledata.getDdcordernumber() + " no existe para el proveedor";
					validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					
					// Fin validaciones para orden actual
					continue;
				}	
				
				// Validar que no se informn OC repetidas
				if(! ddcOrderFileUniqueValidationSet.contains(rescheduledata.getDdcordernumber())){
					ddcOrderFileUniqueValidationSet.add(rescheduledata.getDdcordernumber());
				}
				else {
					error =   prefix + ": El número de orden " + rescheduledata.getDdcordernumber() + " se ha informado más de una vez";
					validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				
				// Guarda orden en mapa
				ddcOrderMap.put(ddcOrderArr[0].getNumber(), ddcOrderArr[0]);
				
				// Valida que fecha de compromiso de la orden sea igual o posterior a la fecha actual (hoy)
				if(rescheduledata.getRescheduledate().isBefore(nowDate)){
					error =  prefix + "Para la orden de compra " + rescheduledata.getDdcordernumber()  + ", se está ingresando una fecha anterior a hoy.";
					validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				
				// Valida que fecha de entrega de la orden sea anterior o igual a su fecha de vencimiento
				if(rescheduledata.getRescheduledate().isAfter(ddcOrderArr[0].getExpiration())) {
					error =  prefix + "Para la orden de compra " + rescheduledata.getDdcordernumber()  + ", se está ingresando una fecha que es posterior a su vencimiento (" + dtf.format(ddcOrderArr[0].getExpiration()) + ")";
					validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				

				// El sistema valida que los comentarios ingresados no superen el largo máximo definido.
				if(rescheduledata.getComment() != null ) {
					if(rescheduledata.getComment().length() > RESCHEDULING_MAXLENGTH_COMMENT) {
						error =  prefix + "Para la orden de compra " + rescheduledata.getDdcordernumber() + ", se está ingresando un comentario que supera los " + REJECT_MAXLENGTH_COMMENT + " caracteres.";
						validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
					else {
						comment = rescheduledata.getComment();	
					}
				}
				
			}

			// Si existen errores en los datos de entrada, se informa el error
			if(validationErrorList.size() > 0) {
				// Calcula cantidad de errores a mostrar
				int errorsSize = validationErrorList.size();
				if(validationErrorList.size() > maxErrorsQtyToShow){
					errorsSize = maxErrorsQtyToShow;
				}
	
				BaseResultDTO[] curResultArr = new BaseResultDTO[errorsSize];
				for (int i = 0; i < errorsSize; i++) {
					BaseResultDTO curResult = new BaseResultDTO();
					curResult.setStatuscode("L1");
					curResult.setStatusmessage(validationErrorList.get(i).getStatusmessage());
					curResultArr[i] = curResult;
				}
				resultDTO.setValidationerrors(curResultArr);
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}
			
			// No hay errores, se hace la llamada a WS de Hites
			// TODO MME: Implementar llamada	
			
			// El sistema identifica todas las OC cuyas fechas fueron aceptadas por Hites.

			// TODO MME: Provisoriamente, las toma todas
			for(RescheduleDateDDCOrdersDataDTO rescheduledata : initParamDTO.getRescheduledateddcordersdata()) {
				// Obtiene orden
				DdcOrderW ddcorderW = ddcOrderMap.get(rescheduledata.getDdcordernumber());
				
				// Actualiza las fechas de reprogramación según lo ingresado por el usuario.
				ddcorderW.setCurrentdeliverydate(rescheduledata.getRescheduledate());
				
				// Aumenta en uno el contador de reprogramaciones.
				Integer count = ddcorderW.getReschedulingcounter() != null ? ddcorderW.getReschedulingcounter() : 0;
				ddcorderW.setReschedulingcounter(count + 1);
				ddcorderW.setCurrentstatetypeid(reschSt.getId());
				ddcorderW.setComment(comment);
				ddcorderW = ddcorderserver.updateDdcOrder(ddcorderW);
				
				// Actualiza al estado "Reprogramada", generando una nueva entrada en su historial de modificaciones 
				// que refleje el comentario ingresado por el usuario.// Estado
				DdcOrderStateW newstate = new DdcOrderStateW(); 
				newstate.setDdcorderid(ddcorderW.getId());
				newstate.setDdcorderstatetypeid(reschSt.getId());
				newstate.setWhen(now);
				newstate.setWho(userDataDTO.getUsername());
				newstate.setComment(comment);
				
				newstate = ddcorderstateserver.addDdcOrderState(newstate);				
			}

			return resultDTO;
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}
	
	// Rechaza las Ordenes DDC
	public RejectDDCOrdersResultDTO doRejectDDCOrders(RejectDDCOrdersInitParamDTO initParamDTO, UserLogDataDTO userDataDTO) {
		
		RejectDDCOrdersResultDTO resultDTO = new RejectDDCOrdersResultDTO();
		
		LocalDateTime now = LocalDateTime.now();
		
		// Errores
		List<BaseResultDTO> validationErrorList = new ArrayList<BaseResultDTO>();
		String error ="";
		
		String comment = "";
		
		// Mapas
		// Key: ordernumber
		// Value: DvrOrderW
		Map<Long, DdcOrderW> ddcOrderMap = new HashMap<Long, DdcOrderW>();
		
		// Para validar OC Repetidas
		// Key: OC Number
		Set<Long> ddcOrderFileUniqueValidationSet = new HashSet<Long>();

		
		try {
			// Estado de OC RECHAZADA
			DdcOrderStateTypeW rechSt = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", "RECHAZADA");		
			
			
			// Maxima cantidad de errores a mostrar
			Integer  maxErrorsQtyToShow =  LogisticConstants.getMAX_LIST_ERROR_TO_SHOW();
		
			// Validaciones de las OC seleccionadas
			Long[] ddcOrderNumbersArr = Arrays.stream(initParamDTO.getRejectddcordersdata()).map(oc -> oc.getDdcordernumber()).toArray(Long[]::new);
			DdcOrderW[] initParamOrdersArr = ddcorderserver.getDdcOrderByNumbers(ddcOrderNumbersArr);
			Long[] ddcorderids = Arrays.stream(initParamOrdersArr).map(oc -> oc.getId()).toArray(Long[]::new);
					
			DdcOrdersVendorInitParamDTO checkInitParam = new DdcOrdersVendorInitParamDTO();
			checkInitParam.setDdcorderids(ddcorderids);
			checkInitParam.setVendorcode(initParamDTO.getVendorcode());
			BaseResultDTO checkResultDTO = doCheckDDCOrdersExecuteActions(checkInitParam);
			if(! checkResultDTO.getStatuscode().equals("0")) {
				resultDTO.setStatuscode(checkResultDTO.getStatuscode());
				resultDTO.setStatusmessage(checkResultDTO.getStatusmessage());
				return resultDTO;
			}
								
			// Proveedor (ya se validó que existe)
			VendorW vendorW = vendorserver.getByPropertyAsSingleResult("code", initParamDTO.getVendorcode());

			// Validaciones previas de los datos informados
			for(RejectDDCOrdersDataDTO rejectdata : initParamDTO.getRejectddcordersdata()) {
				
				// Si la llamada se hizo desde el servicio que prcesa archivo, se agrega el prefijo con el número de línea
				// al mensaje de error
				String prefix = initParamDTO.getFilename() != null ? "Fila " + rejectdata.getRownumber() + ": " : "";

				// Obtiene orden informada
				PropertyInfoDTO[] props = new PropertyInfoDTO[2];
				props[0] = new PropertyInfoDTO("number", "number", rejectdata.getDdcordernumber()); 
				props[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
				
				DdcOrderW[] ddcOrderArr = ddcorderserver.getByPropertiesAsArray(props);
				
				
				if(ddcOrderArr.length == 0) {
					
					error = prefix + "Orden Nro " + rejectdata.getDdcordernumber() + " no existe para el proveedor";
					validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					
					// Fin validaciones para orden actual
					continue;
				}	
				
				// Validar que no se informn OC repetidas
				if(! ddcOrderFileUniqueValidationSet.contains(rejectdata.getDdcordernumber())){
					ddcOrderFileUniqueValidationSet.add(rejectdata.getDdcordernumber());
				}
				else {
					error =   prefix + ": El número de orden " + rejectdata.getDdcordernumber() + " se ha informado más de una vez";
					validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				
				// Guarda orden en mapa
				ddcOrderMap.put(ddcOrderArr[0].getNumber(), ddcOrderArr[0]);
				
				// El sistema valida que los comentarios ingresados no superen el largo máximo definido.
				if(rejectdata.getComment() != null ) {
					if(rejectdata.getComment().length() > REJECT_MAXLENGTH_COMMENT) {
						error =  prefix + "Para la orden de compra " + rejectdata.getDdcordernumber() + ", se está ingresando un comentario que supera los " + REJECT_MAXLENGTH_COMMENT + " caracteres.";
						validationErrorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
					else {
						comment = rejectdata.getComment();	
					}
				}
			}

			// Si existen errores en los datos de entrada, se informa el error
			if(validationErrorList.size() > 0) {
				// Calcula cantidad de errores a mostrar
				int errorsSize = validationErrorList.size();
				if(validationErrorList.size() > maxErrorsQtyToShow){
					errorsSize = maxErrorsQtyToShow;
				}
	
				BaseResultDTO[] curResultArr = new BaseResultDTO[errorsSize];
				for (int i = 0; i < errorsSize; i++) {
					BaseResultDTO curResult = new BaseResultDTO();
					curResult.setStatuscode("L1");
					curResult.setStatusmessage(validationErrorList.get(i).getStatusmessage());
					curResultArr[i] = curResult;
				}
				resultDTO.setValidationerrors(curResultArr);
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}
			
			
			// No hay errores, se hace la llamada a WS de Hites
			// TODO MME: Implementar llamada	
						
			// El sistema identifica todas las OC cuyos rechazos fueron aceptados por Hites.
			
//			// Guarda Nro de OC
//			StringBuilder ordersSb = new StringBuilder();
//			String separator = "";
//			
//			// Guada ID de las OC
//			List<Long> modDvhorderids = new ArrayList<Long>();
			
			// TODO MME: Provisoriamente, las toma todas
			for(RejectDDCOrdersDataDTO rejectdata : initParamDTO.getRejectddcordersdata()) {
				
				// Obtiene orden
				DdcOrderW ddcorderW = ddcOrderMap.get(rejectdata.getDdcordernumber());
				
				// Actualiza estado de la OC
				ddcorderW.setCurrentstatetypeid(rechSt.getId());
				ddcorderW.setComment(comment);
				ddcorderW = ddcorderserver.updateDdcOrder(ddcorderW);
				
				// Actualiza al estado "Rechazda Proveedor", generando una nueva entrada en su historial de modificaciones 
				// que refleje el comentario ingresado por el usuario.// Estado
				DdcOrderStateW newstate = new DdcOrderStateW(); 
				newstate.setDdcorderid(ddcorderW.getId());
				newstate.setDdcorderstatetypeid(rechSt.getId());
				newstate.setWhen(now);
				newstate.setWho(userDataDTO.getUsername());
				newstate.setComment(comment);

				newstate = ddcorderstateserver.addDdcOrderState(newstate);
			}
			
			return resultDTO;
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}		
	}

	
}
