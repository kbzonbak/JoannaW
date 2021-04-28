package bbr.b2b.logistic.schedule.api.classes;

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
import bbr.b2b.logistic.managers.interfaces.PendingMessageManagerLocal;
import bbr.b2b.logistic.managers.interfaces.SOANotificationManagerLocal;
import bbr.b2b.logistic.managers.interfaces.SoaRecNotificationManagerLocal;
import bbr.b2b.logistic.schedule.api.dto.ApiResultDTO;

@Path("/schedule")
@Stateless(name = "ws/ScheduleManagerServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ScheduleManagerServer {
	
	SOANotificationManagerLocal soaNotificationManagerLocal;
	PendingMessageManagerLocal pendingMessageManagerLocal;
	SoaRecNotificationManagerLocal soaRecNotificationManagerLocal;
		
	public ScheduleManagerServer() throws OperationFailedException {
		try {
			Object objectManagerReference = (new InitialContext()).lookup("java:global/CustomerLogisticEAR/CustomerLogisticManager/managers/SoaNotificationManager!bbr.b2b.logistic.managers.interfaces.SOANotificationManagerLocal");
			soaNotificationManagerLocal = (SOANotificationManagerLocal) PortableRemoteObject.narrow(objectManagerReference, SOANotificationManagerLocal.class);
			
			Object objectManagerReference2 = (new InitialContext()).lookup("java:global/CustomerLogisticEAR/CustomerLogisticManager/managers/PendingMessageManager!bbr.b2b.logistic.managers.interfaces.PendingMessageManagerLocal");
			pendingMessageManagerLocal = (PendingMessageManagerLocal) PortableRemoteObject.narrow(objectManagerReference2, PendingMessageManagerLocal.class);
			
			Object objectManagerReference3 = (new InitialContext()).lookup("java:global/CustomerLogisticEAR/CustomerLogisticManager/managers/SoaRecNotificationManager!bbr.b2b.logistic.managers.interfaces.SoaRecNotificationManagerLocal");
			soaRecNotificationManagerLocal = (SoaRecNotificationManagerLocal) PortableRemoteObject.narrow(objectManagerReference3, SoaRecNotificationManagerLocal.class);
			
			
		} catch (Exception e) {
			throw new OperationFailedException("Error instanciado session beans", e);
		}
	}
	
	@Path("hello")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response sayHello() {
		return Response.ok("Hello World desde el API REST", MediaType.APPLICATION_JSON).build();
	}

	
	@Path("soanotification/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doSendSoaNotifications() {
		String result = new String();
		try {
			soaNotificationManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente el envío de notificaciones de soa"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("soarecnotification/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doSendSoaRecNotifications() {
		String result = new String();
		try {
			soaRecNotificationManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente el envío de notificaciones (Recepciones) de soa"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("pendingmessage/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doSendPendingMessage() {
		String result = new String();
		try {
			pendingMessageManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente el envío de mensajes pendientes"); 
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
