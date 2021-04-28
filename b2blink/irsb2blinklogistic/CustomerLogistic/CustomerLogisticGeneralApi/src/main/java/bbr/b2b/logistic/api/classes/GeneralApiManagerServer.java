package bbr.b2b.logistic.api.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.keycloak.classes.JsonUtils;
import bbr.b2b.logistic.api.dto.ApiResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckOrderInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckOrderResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckReceptionInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckReceptionResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.HistoryInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderDetailReportInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderDetailReportResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderHistoryDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderStateTypeResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.ResendInitParamDTO;
import bbr.b2b.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;


@Path("/buyorders")
@Stateless(name = "ws/GeneralApiManagerServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class GeneralApiManagerServer {

	BuyOrderReportManagerLocal buyOrderReportManagerLocal;
	
	
	public GeneralApiManagerServer() throws OperationFailedException {
		try {
			Object objectManagerReference1 = (new InitialContext()).lookup("java:global/CustomerLogisticEAR/CustomerLogisticManager/managers/BuyOrderReportManager!bbr.b2b.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal");
			buyOrderReportManagerLocal = (BuyOrderReportManagerLocal) PortableRemoteObject.narrow(objectManagerReference1, BuyOrderReportManagerLocal.class);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Path("hello")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response sayHello() {
		return Response.ok("Hello World desde el API REST", MediaType.APPLICATION_JSON).build();
	}

	
	@Path("getorderstatetypes")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response getorderstatetypes() {
		String result = new String();
		try {
			OrderStateTypeResultDTO test = buyOrderReportManagerLocal.getOrderStateTypes();
			//ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente el envío de mensajes pendientes"); 
			//result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			result = JsonUtils.getJsonFromObject(test, OrderStateTypeResultDTO.class);
			return Response.status(200).entity(test).build();
		} catch (Exception e) {
			e.printStackTrace();
			//ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			//result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("getOrderReportByVendorAndFilter")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response getOrderReportByVendorAndFilter(OrderReportInitParamDTO initParamDTO) {
		String result = new String();
		try {
			OrderReportResultDTO resultDTO = buyOrderReportManagerLocal.getOrderReportByVendorAndFilter(initParamDTO);
			//ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente el envío de correos pendientes"); 
			result = JsonUtils.getJsonFromObject(resultDTO, OrderReportResultDTO.class);
			return Response.status(200).entity(resultDTO).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("getOrderDetailByOrder")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response getOrderDetailByOrder(OrderDetailReportInitParamDTO initParamDTO) {
		String result = new String();
		try {
			OrderDetailReportResultDTO resultDTO = buyOrderReportManagerLocal.getOrderDetailByOrder(initParamDTO);
			//ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la eliminación de mensajes ya enviados"); 
			result = JsonUtils.getJsonFromObject(resultDTO, OrderDetailReportResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	
	@Path("doResendOC")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doResendOC(ResendInitParamDTO initParamDTO) {
		String result = new String();
		try {
			BaseResultDTO resultDTO = buyOrderReportManagerLocal.doResendOC(initParamDTO);
			result = JsonUtils.getJsonFromObject(resultDTO, BaseResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	
	
	@Path("getOrderHistory")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response getOrderHistory(HistoryInitParamDTO initParamDTO) {
		String result = new String();
		try {
			OrderHistoryDTO resultDTO = buyOrderReportManagerLocal.getOrderHistory(initParamDTO);
			result = JsonUtils.getJsonFromObject(resultDTO, OrderHistoryDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	
	
	@Path("doCheckOrderByRetail")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doCheckOrderByRetail(CheckOrderInitParamDTO initParamDTO) {
		String result = new String();
		try {
			CheckOrderResultDTO resultDTO = buyOrderReportManagerLocal.doCheckOrderByRetail(initParamDTO);
			result = JsonUtils.getJsonFromObject(resultDTO, CheckOrderResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("doCheckReceptionByRetail")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doCheckReceptionByRetail(CheckReceptionInitParamDTO initParamDTO) {
		String result = new String();
		try {
			CheckReceptionResultDTO resultDTO = buyOrderReportManagerLocal.doCheckReceptionByRetail(initParamDTO);
			result = JsonUtils.getJsonFromObject(resultDTO, CheckReceptionResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
}
