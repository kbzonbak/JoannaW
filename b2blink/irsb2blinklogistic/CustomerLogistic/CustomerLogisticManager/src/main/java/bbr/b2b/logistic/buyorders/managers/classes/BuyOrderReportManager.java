package bbr.b2b.logistic.buyorders.managers.classes;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.logistic.buyorders.data.dto.CheckOrderInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckOrderResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckReceptionInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckReceptionResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.HeaderOrderDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.HistoryDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.HistoryHeaderDTO;
import bbr.b2b.logistic.buyorders.data.dto.HistoryInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderDetailReportInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderDetailReportResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderHistoryDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderPreDeliveryDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderProductDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportDataDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderStateTypeDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderStateTypeResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.ResendInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.TotalPredeliveryDTO;
import bbr.b2b.logistic.buyorders.data.dto.TotalProductsDTO;
import bbr.b2b.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.logistic.customer.classes.BuyerServerLocal;
import bbr.b2b.logistic.customer.classes.ClientServerLocal;
import bbr.b2b.logistic.customer.classes.DetailServerLocal;
import bbr.b2b.logistic.customer.classes.LocationServerLocal;
import bbr.b2b.logistic.customer.classes.OrderServerLocal;
import bbr.b2b.logistic.customer.classes.OrderStateServerLocal;
import bbr.b2b.logistic.customer.classes.OrderStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.customer.classes.ReceptionServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.VendorServerLocal;
import bbr.b2b.logistic.customer.data.wrappers.ClientW;
import bbr.b2b.logistic.customer.data.wrappers.OrderStateTypeW;
import bbr.b2b.logistic.customer.data.wrappers.OrderW;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateTypeW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateW;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.utils.LogisticStatusCodeUtils;


@Stateless(name = "managers/BuyOrderReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BuyOrderReportManager implements BuyOrderReportManagerLocal {

	private static Logger logger = Logger.getLogger(BuyOrderReportManager.class);
	
	@Resource
	private javax.ejb.SessionContext mySessionCtx;

	@EJB
	OrderServerLocal orderserver;
	
	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;
	
	@EJB
	OrderStateServerLocal orderstateserver;
	
	@EJB
	OrderTypeServerLocal ordertypeserver;
	
	@EJB
	LocationServerLocal locationserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	ClientServerLocal clientserver;
	
	@EJB
	DetailServerLocal detailserver;
	
	@EJB
	SoaStateServerLocal soastateserver;
	
	@EJB
	SoaStateTypeServerLocal soastatetypeserver;
	
	@EJB
	BuyerServerLocal buyerserver;
	
	@EJB
	ReceptionServerLocal receptionserver;
	
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}


	public OrderStateTypeResultDTO getOrderStateTypes(){
		OrderStateTypeResultDTO resultDTO = new OrderStateTypeResultDTO();

		try {
			// Obtiene estados visibles
			OrderStateTypeW[] orderstatetypes = orderstatetypeserver.getAllAsArray();
			if (orderstatetypes == null ||orderstatetypes.length <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "9000");
			}

			OrderStateTypeDTO[] orderstatetypedtos = new OrderStateTypeDTO[orderstatetypes.length];
			BeanExtenderFactory.copyProperties(orderstatetypes, orderstatetypedtos, OrderStateTypeDTO.class);

			resultDTO.setOrderstatetype(orderstatetypedtos);
			return resultDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}
	
	// Reporte de OC
	public OrderReportResultDTO getOrderReportByVendorAndFilter(OrderReportInitParamDTO initParamDTO){
		OrderReportResultDTO resultDTO = new OrderReportResultDTO();
		OrderReportDataDTO[] ordersDTOs = null;
		int total;
		
		try{
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "9001");
			}
			VendorW vendorW = vendorArr[0];
			
			//obtener Cliente
			Long clientid;
			if(!initParamDTO.getClientrut().equals("-1")){
				// Obtener Client
				ClientW[] clientArr = clientserver.getByPropertyAsArray("identity", initParamDTO.getClientrut());
				if(clientArr.length == 0){
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "9002");
				}
				clientid = clientArr[0].getId();
			}else{
				clientid = -1L;
			}
			

			// Datos
			ordersDTOs = orderserver.getOrdersByVendorAndCriterium(vendorW.getId(), clientid, initParamDTO);
			// Si es consulta desde el filtro, se muesta información de la paginación
			if(initParamDTO.isByfilter()){
				total = orderserver.getCountOrdersByVendorAndCriterium(vendorW.getId(), clientid, initParamDTO);

				// Setea Información de paginación
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(ordersDTOs.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
				resultDTO.setTotalreg(total);
			}
			
			resultDTO.setOrders(ordersDTOs);
			
			return resultDTO;
			
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "9003");
			
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "9003");
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "9003");
		} catch (ParseException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "9003");
		}
	}
	
	
	public OrderDetailReportResultDTO getOrderDetailByOrder(OrderDetailReportInitParamDTO initParamDTO){
		OrderDetailReportResultDTO resultDTO = new OrderDetailReportResultDTO();
		OrderProductDetailDTO[] productdetail;
		OrderPreDeliveryDetailDTO[] predeliverydetail;
		int total;
		
		try{
			
			//obtener la orden
			OrderW order = null;
			try {
				order = orderserver.getByPropertyAsSingleResult("number", initParamDTO.getOcnumber());
			} catch (Exception e) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "9007");
			}
			
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "9001");
			}
			VendorW vendorW = vendorArr[0];
			
			//obtener Cliente
			Long clientid;
			if(!initParamDTO.getClientcode().equals("-1")){
				// Obtener Client
				ClientW[] clientArr = clientserver.getByPropertyAsArray("identity", initParamDTO.getClientcode());
				if(clientArr.length == 0){
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "9002");
				}
				clientid = clientArr[0].getId();
			}else{
				clientid = -1L;
			}
			
			// Obtiene datos
			
			//header 
			HeaderOrderDetailDTO[] headerdetail = detailserver.getHeaderOrderDetail(String.valueOf(initParamDTO.getOcnumber()),vendorW.getId(),clientid);
			
			//detail
			productdetail = detailserver.getProductOrdersDetails(initParamDTO.getOcnumber(), vendorW.getId(), clientid, initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), initParamDTO.isPaginated());
			
			List<String> productList = new ArrayList<String>();
			if(productdetail.length > 0){
				for (OrderProductDetailDTO product : productdetail) {
					productList.add(product.getCodproducto());
				}
			}
			
			//predelivery
			predeliverydetail = detailserver.getPredeliveryOrderDetails(initParamDTO.getOcnumber(), vendorW.getId(), clientid, initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), initParamDTO.isPaginated(), productList);
			TotalProductsDTO totalproducts = detailserver.getCountProductDetailsByOrder(initParamDTO.getOcnumber(), vendorW.getId(), clientid);
			TotalPredeliveryDTO totalpredelivery = detailserver.getCountPredeliveryDetailsByOrder(initParamDTO.getOcnumber(), vendorW.getId(), clientid);
			
			
			if(initParamDTO.isByfilter()){
				// Setear Información de la Paginación de productos
				int rows = initParamDTO.getRows();
				total = totalproducts.getTotalregs();
				PageInfoDTO pageInfoProd = new PageInfoDTO();
				pageInfoProd.setPagenumber(initParamDTO.getPageNumber());
				pageInfoProd.setRows(productdetail.length);
				pageInfoProd.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfoProd.setTotalrows(total);
				resultDTO.setPageInfoproduct(pageInfoProd);
			}
			
			resultDTO.setTotalpredelivery(totalpredelivery);
			resultDTO.setTotalproducts(totalproducts);
			resultDTO.setHeaderdetail(headerdetail[0]);
			resultDTO.setPredeliverydetail(predeliverydetail);
			resultDTO.setProductdetail(productdetail);
			return resultDTO;
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}
	
	
	public BaseResultDTO doResendOC(ResendInitParamDTO initParamDTO){
		
		BaseResultDTO result = new BaseResultDTO();
		Date now = new Date();
		
		for (String ocnumber : initParamDTO.getOcnumbers()) {
			
			OrderW order = new OrderW();
			try {
				OrderW[] oc = orderserver.getByPropertyAsArray("number", Long.valueOf(ocnumber));
				SoaStateTypeW soastatetype = soastatetypeserver.getByPropertyAsSingleResult("code", "POR_NOTIFICAR");
				
				if (oc == null ||oc.length <= 0) {
					result.setStatuscode("-1");
					result.setStatusmessage("No se encontro la orden");
					return result;
				}
				order = oc[0];
				
				SoaStateW soastate = new SoaStateW();
				soastate.setOrderid(order.getId());
				soastate.setSoastatetypeid(soastatetype.getId());
				soastate.setWhen(now);
				
				soastate= soastateserver.addSoaState(soastate);
				
				order.setSoastatetypeid(soastatetype.getId());
				order = orderserver.updateOrder(order);
			
			} catch (OperationFailedException | NotFoundException | NumberFormatException e) {
				e.printStackTrace();
				result.setStatuscode("-1");
				result.setStatusmessage("No se pudo actualizar la orden");
				return result;
			} catch (AccessDeniedException e) {
				e.printStackTrace();
				result.setStatuscode("-1");
				result.setStatusmessage("No se pudo actualizar la orden");
				return result;
			}
		
		}
		result.setStatuscode("0");
		result.setStatusmessage("Estado de orden actualizado a Por Notificar");
		
		return result;
	}
	
	
	public OrderHistoryDTO getOrderHistory(HistoryInitParamDTO initparam){
		OrderHistoryDTO resultDTO = new OrderHistoryDTO();
		HistoryDetailDTO[] detail = null;
		int total;
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initparam.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "9001");
			}
			VendorW vendorW = vendorArr[0];
			
			//obtener Cliente
			Long clientid;
			if(!initparam.getClientrut().equals("-1")){
				// Obtener Client
				ClientW[] clientArr = clientserver.getByPropertyAsArray("identity", initparam.getClientrut());
				if(clientArr.length == 0){
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "9002");
				}
				clientid = clientArr[0].getId();
			}else{
				clientid = -1L;
			}
			String ocnumber = initparam.getOcnumber();
			
			HistoryHeaderDTO header =  orderserver.getHistoryHeader(ocnumber, vendorW.getId(), clientid);
			detail = orderserver.getHistoryDetail(ocnumber, vendorW.getId(), clientid);
			total = orderserver.getCountHistoryDetail(ocnumber, vendorW.getId(), clientid);
			
			resultDTO.setHistoryheader(header);
			resultDTO.setHistory(detail);
			resultDTO.setTotalregs(total);
			
			
		} catch (OperationFailedException | NotFoundException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;
	}
	
	
	public CheckOrderResultDTO doCheckOrderByRetail(CheckOrderInitParamDTO initparam){
		CheckOrderResultDTO result = new CheckOrderResultDTO();
		result.setExists(false);
		
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("buyer.code", "code", initparam.getSitecode());
			properties[1] = new PropertyInfoDTO("number", "number", Long.valueOf(initparam.getOcnumber()));
			
			List<OrderW> listOrderW = new ArrayList<OrderW>();
			try {
				listOrderW = orderserver.getByProperties(properties);
			} catch (OperationFailedException | NotFoundException e) {
				logger.error("no se ha podido encontrar la orden");
				result.setStatuscode("-1");
				result.setStatusmessage("no se ha podido encontrar la orden");
			}
			if(listOrderW.size() > 0){
				result.setExists(true);
			}
			
		return result;
	}
	
	public CheckReceptionResultDTO doCheckReceptionByRetail(CheckReceptionInitParamDTO initparam){
		CheckReceptionResultDTO result = new CheckReceptionResultDTO();
		result.setFound(false);
		
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("buyer.code", "code", initparam.getSitecode());
			properties[1] = new PropertyInfoDTO("receptionnumber", "receptionnumber", Long.valueOf(initparam.getRecnumber()));
			
			List<ReceptionW> listReceptionW = new ArrayList<ReceptionW>();
			try {
				listReceptionW = receptionserver.getByProperties(properties);
			} catch (OperationFailedException | NotFoundException e) {
				logger.error("no se ha podido encontrar la orden");
				result.setStatuscode("-1");
				result.setStatusmessage("no se ha podido encontrar la orden");
			}
			if(listReceptionW.size() > 0){
				result.setFound(true);
			}
			
		return result;
	}
	
}
