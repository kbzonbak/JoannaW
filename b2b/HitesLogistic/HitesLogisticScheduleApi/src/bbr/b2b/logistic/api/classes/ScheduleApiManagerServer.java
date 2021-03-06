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


import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.keycloak.classes.JsonUtils;
import bbr.b2b.logistic.api.dto.ApiResultDTO;
import bbr.b2b.logistic.managers.interfaces.CheckBuyOrderStateManagerLocal;
import bbr.b2b.logistic.managers.interfaces.LoadRecoveredMessagesManagerLocal;
import bbr.b2b.logistic.managers.interfaces.NotificationsTimerManagerLocal;
import bbr.b2b.logistic.managers.interfaces.PendingMailManagerLocal;
import bbr.b2b.logistic.managers.interfaces.PendingMessageManagerLocal;
import bbr.b2b.logistic.managers.interfaces.SOANotificationManagerLocal;

@Path("/schedule")
@Stateless(name = "ws/ScheduleApiManagerServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ScheduleApiManagerServer {

	CheckBuyOrderStateManagerLocal checkBuyOrderStateManagerLocal;
	LoadRecoveredMessagesManagerLocal loadRecoveredMessagesManagerLocal;
	NotificationsTimerManagerLocal notificationsTimerManagerLocal;
	PendingMailManagerLocal pendingMailManagerLocal;
	PendingMessageManagerLocal pendingMessageManagerLocal;
	SOANotificationManagerLocal soaNotificationManagerLocal;
	
	public ScheduleApiManagerServer() throws OperationFailedException {
		try {
			Object objectManagerReference6 = (new InitialContext()).lookup("java:global/HitesLogisticEAR/HitesLogisticManager/managers/SOANotificationManager!bbr.b2b.logistic.managers.interfaces.SOANotificationManagerLocal");
			soaNotificationManagerLocal = (SOANotificationManagerLocal) PortableRemoteObject.narrow(objectManagerReference6, SOANotificationManagerLocal.class);
			
			Object objectManagerReference5 = (new InitialContext()).lookup("java:global/HitesLogisticEAR/HitesLogisticManager/managers/CheckBuyOrderStateManager!bbr.b2b.logistic.managers.interfaces.CheckBuyOrderStateManagerLocal");
			checkBuyOrderStateManagerLocal = (CheckBuyOrderStateManagerLocal) PortableRemoteObject.narrow(objectManagerReference5, CheckBuyOrderStateManagerLocal.class);
			
			Object objectManagerReference4 = (new InitialContext()).lookup("java:global/HitesLogisticEAR/HitesLogisticManager/managers/LoadRecoveredMessagesManager!bbr.b2b.logistic.managers.interfaces.LoadRecoveredMessagesManagerLocal");
			loadRecoveredMessagesManagerLocal = (LoadRecoveredMessagesManagerLocal) PortableRemoteObject.narrow(objectManagerReference4, LoadRecoveredMessagesManagerLocal.class);
			
			Object objectManagerReference3 = (new InitialContext()).lookup("java:global/HitesLogisticEAR/HitesLogisticManager/managers/NotificationsTimerManager!bbr.b2b.logistic.managers.interfaces.NotificationsTimerManagerLocal");
			notificationsTimerManagerLocal = (NotificationsTimerManagerLocal) PortableRemoteObject.narrow(objectManagerReference3, NotificationsTimerManagerLocal.class);
			
			Object objectManagerReference2 = (new InitialContext()).lookup("java:global/HitesLogisticEAR/HitesLogisticManager/managers/PendingMailManager!bbr.b2b.logistic.managers.interfaces.PendingMailManagerLocal");
			pendingMailManagerLocal = (PendingMailManagerLocal) PortableRemoteObject.narrow(objectManagerReference2, PendingMailManagerLocal.class);
			
			Object objectManagerReference1 = (new InitialContext()).lookup("java:global/HitesLogisticEAR/HitesLogisticManager/managers/PendingMessageManager!bbr.b2b.logistic.managers.interfaces.PendingMessageManagerLocal");
			pendingMessageManagerLocal = (PendingMessageManagerLocal) PortableRemoteObject.narrow(objectManagerReference1, PendingMessageManagerLocal.class);
			
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
	
	@Path("checkbuyorderstate/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doCheckBuyOrderState() {
		String result = new String();
		try {
			checkBuyOrderStateManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente el chequeo de vencimiento de ??rdenes"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("messagerecover/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doMessageRecover() {
		String result = new String();
		try {
			loadRecoveredMessagesManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente la recuperaci??n de mensajes"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("notification/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doSendNotifications() {
		String result = new String();
		try {
			notificationsTimerManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente el env??o de notificaciones"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("pendingmail/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doSendPendingMail() {
		String result = new String();
		try {
			pendingMailManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente el env??o de correos pendientes"); 
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
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente el env??o de mensajes pendientes"); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			ApiResultDTO resultDTO = new ApiResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, ApiResultDTO.class);
			return Response.status(500).entity(result).build();
		}
	}
	
	@Path("soanotification/execute")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response doSendSoaNotifications() {
		String result = new String();
		try {
			soaNotificationManagerLocal.doProcess();
			ApiResultDTO resultDTO = new ApiResultDTO("0", "Se ha ejecutado correctamente el env??o de notificaciones de soa"); 
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
