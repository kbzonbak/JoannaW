package bbr.b2b.extractors.api.classes;

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
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.keycloak.classes.JsonUtils;
import bbr.b2b.extractors.api.dto.ApiResultDTO;
import bbr.b2b.extractors.manager.interfaces.FalabellaWSOrderManagerLocal;
import bbr.b2b.extractors.manager.interfaces.HitesEdiOrderManagerLocal;
import bbr.b2b.extractors.manager.interfaces.RipleyApiOrderManagerLocal;
import bbr.b2b.extractors.manager.interfaces.SodimacWSOrderManagerLocal;
import bbr.b2b.extractors.manager.interfaces.TottusWebSalesManagerLocal;
import bbr.b2b.extractors.manager.interfaces.WalmartApiOrderManagerLocal;
import bbr.b2b.extractors.manager.interfaces.WalmartWebOrdersManagerLocal;
import bbr.b2b.extractors.manager.interfaces.WalmartWebSalesManagerLocal;


@Path("/schedule")
@Stateless(name = "ws/ScheduleApiManagerServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ScheduleApiManagerServer {

	
	HitesEdiOrderManagerLocal hitesEdiOrderManagerLocal;
	RipleyApiOrderManagerLocal ripleyApiOrderManagerLocal;
	TottusWebSalesManagerLocal tottusWebSalesManagerLocal;
	WalmartWebSalesManagerLocal walmartWebSalesManagerLocal;
	FalabellaWSOrderManagerLocal falabellaWSOrderManagerLocal;
	WalmartApiOrderManagerLocal walmartApiOrderManagerLocal;
		WalmartWebOrdersManagerLocal walmartWebOrdersManagerLocal;
	SodimacWSOrderManagerLocal sodimacWSOrderManagerLocal;
	
	public ScheduleApiManagerServer() throws OperationFailedException {
		try {
			Object objectManagerReference1 = (new InitialContext()).lookup("java:global/ExternalRetailEAR/ExternalRetailManager/managers/HitesEdiOrderManager!bbr.b2b.extractors.manager.interfaces.HitesEdiOrderManagerLocal");
			hitesEdiOrderManagerLocal = (HitesEdiOrderManagerLocal) PortableRemoteObject.narrow(objectManagerReference1, HitesEdiOrderManagerLocal.class);
			
			Object objectManagerReference2 = (new InitialContext()).lookup("java:global/ExternalRetailEAR/ExternalRetailManager/managers/RipleyApiOrderManager!bbr.b2b.extractors.manager.interfaces.RipleyApiOrderManagerLocal");
			ripleyApiOrderManagerLocal = (RipleyApiOrderManagerLocal) PortableRemoteObject.narrow(objectManagerReference2, RipleyApiOrderManagerLocal.class);
			
			Object objectManagerReference3 = (new InitialContext()).lookup("java:global/ExternalRetailEAR/ExternalRetailManager/managers/TottusWebSalesManager!bbr.b2b.extractors.manager.interfaces.TottusWebSalesManagerLocal");
			tottusWebSalesManagerLocal = (TottusWebSalesManagerLocal) PortableRemoteObject.narrow(objectManagerReference3, TottusWebSalesManagerLocal.class);
			
			Object objectManagerReference4 = (new InitialContext()).lookup("java:global/ExternalRetailEAR/ExternalRetailManager/managers/WalmartWebSalesManager!bbr.b2b.extractors.manager.interfaces.WalmartWebSalesManagerLocal");
			walmartWebSalesManagerLocal = (WalmartWebSalesManagerLocal) PortableRemoteObject.narrow(objectManagerReference4, WalmartWebSalesManagerLocal.class);
			
			Object objectManagerReference5 = (new InitialContext()).lookup("java:global/ExternalRetailEAR/ExternalRetailManager/managers/FalabellaWSOrderManager!bbr.b2b.extractors.manager.interfaces.FalabellaWSOrderManagerLocal");
			falabellaWSOrderManagerLocal = (FalabellaWSOrderManagerLocal) PortableRemoteObject.narrow(objectManagerReference5, FalabellaWSOrderManagerLocal.class);
			
			Object objectManagerReference6 = (new InitialContext()).lookup("java:global/ExternalRetailEAR/ExternalRetailManager/managers/SodimacWSOrderManager!bbr.b2b.extractors.manager.interfaces.SodimacWSOrderManagerLocal");
			sodimacWSOrderManagerLocal = (SodimacWSOrderManagerLocal) PortableRemoteObject.narrow(objectManagerReference6, SodimacWSOrderManagerLocal.class);
			
			Object objectManagerReference7 = (new InitialContext()).lookup("java:global/ExternalRetailEAR/ExternalRetailManager/managers/WalmartApiOrderManager!bbr.b2b.extractors.manager.interfaces.WalmartApiOrderManagerLocal");
			walmartApiOrderManagerLocal = (WalmartApiOrderManagerLocal) PortableRemoteObject.narrow(objectManagerReference7, WalmartApiOrderManagerLocal.class);
			
			Object objectManagerReference8 = (new InitialContext()).lookup("java:global/ExternalRetailEAR/ExternalRetailManager/managers/WalmartWebOrdersManager!bbr.b2b.extractors.manager.interfaces.WalmartWebOrdersManagerLocal");
			walmartWebOrdersManagerLocal = (WalmartWebOrdersManagerLocal) PortableRemoteObject.narrow(objectManagerReference8, WalmartWebOrdersManagerLocal.class);
			
		
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

	
	@Path("hites/downloadorders/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadOrders() {
		String result = new String();
		try {
			hitesEdiOrderManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de archivos edi de hites"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("ripley/downloadorders/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadOrdersRipley() {
		String result = new String();
		try {
			ripleyApiOrderManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de órdenes desde api Ripley"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("tottus/downloadsales/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadSalesTottus() {
		String result = new String();
		try {
			tottusWebSalesManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de ventas desde página de Tottus(crawler)"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("walmart/downloadsales/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadSalesWalmart() {
		String result = new String();
		try {
			walmartWebSalesManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de ventas desde página de Walmart(crawler)"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("walmart/downloadinventory/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadInventoryWalmart() {
		String result = new String();
		try {
			walmartWebSalesManagerLocal.doProcessInventario();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de ventas desde página de Walmart(crawler)"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("falabella/eoc/downloadorders/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadEocSalesFalabella() {
		String result = new String();
		try {
			falabellaWSOrderManagerLocal.doProcessOC();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de órdenes desde api Falabella"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("falabella/eod/downloadorders/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadEodSalesFalabella() {
		String result = new String();
		try {
			falabellaWSOrderManagerLocal.doProcessOD();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de órdenes desde api Falabella"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("falabella/eoe/downloadorders/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadEoeSalesFalabella() {
		String result = new String();
		try {
			falabellaWSOrderManagerLocal.doProcessOE();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de órdenes desde api Falabella"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("falabella/evta/downloadorders/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadEvtaSalesFalabella() {
		String result = new String();
		try {
			falabellaWSOrderManagerLocal.doProcessVtaInv();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de órdenes desde api Falabella"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	
	@Path("walmart/downloadorders/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadOrdersWalmart() {
		String result = new String();
		try {
			walmartApiOrderManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de ventas desde página de Walmart(crawler)"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	
	@Path("walmart/downloadorderreceptions/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadOrderReceptionsWalmart() {
		String result = new String();
		try {
			walmartWebOrdersManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de recepciones de ordenes desde página de Walmart(crawler)"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}


	@Path("sodimac/eoc/downloadorders/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadEocSalesSodimac() {
		String result = new String();
		try {
			sodimacWSOrderManagerLocal.doProcessOC();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de órdenes desde api Sodimac"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("sodimac/eod/downloadorders/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadEodSalesSodimac() {
		String result = new String();
		try {
			sodimacWSOrderManagerLocal.doProcessOD();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de órdenes desde api Sodimac"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("sodimac/eoe/downloadorders/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadEoeSalesSodimac() {
		String result = new String();
		try {
			sodimacWSOrderManagerLocal.doProcessOE();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de órdenes desde api Sodimac"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("sodimac/vta/downloadorders/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doDownloadVtaSalesSodimac() {
		String result = new String();
		try {
			sodimacWSOrderManagerLocal.doProcessVtaInv();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la descarga de órdenes desde api Sodimac"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	
	
}
