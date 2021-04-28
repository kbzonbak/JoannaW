package bbr.b2b.regional.logistic.vev.rest.classes;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.keycloak.classes.JsonUtils;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DODeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDODeliveryOfDirectOrdersWSInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDODeliveryOfDirectOrdersWSResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DirectOrderDetailsWSInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DirectOrdersWSInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UpdateDODeliveryWSInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UpdateDODeliveryWSResultDTO;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vev.rest.interfaces.IDODeliveryAPIRESTManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@Path("delivery")
@Api(value = "API de Servicios para Despachos VeV PD")
@SwaggerDefinition(info=@Info(
						title="API de Servicios para Despachos VeV PD",
						description ="Esta API esta destinada a proporcionar una interfaz para realizar operaciones sobre el modulo Logistico de ParisCL",
						version="1.0.0",							
						contact=@Contact(
								email="jperez@bbr.cl", 
								name= "Jimmy Perez"
								),
						license=@License(
				                   name = "BBR Spa", 
				                   url = "http://www.bbr.cl"
				                )
						),
					host="https://test.cencosud.b2b.bbr.cl/"										    
				  )
public class DODeliveryAPIRESTManager implements IDODeliveryAPIRESTManager {
	
	private DODeliveryReportManagerLocal doDeliveryReportManagerLocal;
	
	public DODeliveryAPIRESTManager() throws OperationFailedException {
		getHomes();
	}
	
	private void getHomes() throws OperationFailedException {
		try {
			Object objectManagerReference = null;
			
			objectManagerReference = (new InitialContext()).lookup("java:global/ParisCLLogisticEAR/ParisCLLogisticManager/managers/DODeliveryReportManager!bbr.b2b.regional.logistic.deliveries.managers.interfaces.DODeliveryReportManagerLocal");
			doDeliveryReportManagerLocal = (DODeliveryReportManagerLocal) PortableRemoteObject.narrow(objectManagerReference, DODeliveryReportManagerLocal.class);
			
		} catch (Exception e) {
			throw new OperationFailedException("Error instanciado session beans", e);
		}
	}
	
	@Path("hello")
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Hello", notes = "Hello World", hidden=true)
	@ApiResponses(value = {
		@ApiResponse(
				code=200,
				message = "OK")
	})
	public Response sayHello() {
		return Response.ok("Hello World desde el API REST", MediaType.APPLICATION_JSON).build();
	}

	// Crear Despachos VeV PD
	@Path("addvevpd")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add DODeliveries To DirectOrders", notes = "Crea Despachos VeV PD", hidden=true)
	@ApiResponses(value = {
		@ApiResponse(
				code=200,
				message = "Devuelve un codigo de mensaje controlado y la descripcion, incluso si da error",
				response = AddDODeliveryOfDirectOrdersWSResultDTO.class),
		@ApiResponse(
				code=400,
				message = "Si algun parametro viene nulo"
				)
	})
	public Response doAddDODeliveryOfDirectOrdersWS(@ApiParam(name="initParams", value="Datos de la orden", required=true) AddDODeliveryOfDirectOrdersWSInitParamDTO initParams) {
		String result = new String();

		try {
			// PARAMÉTROS DE LA LLAMADA A BACK-END
			// SE DEBEN HOMOLOGAR LOS DATOS QUE ENTRAN DESDE LA API A LOS QUE SE USAN EN BACK-END
			if (initParams.getOrdenes() != null && initParams.getOrdenes().length > 0 &&
				initParams.getRutproveedor() != null && !initParams.getRutproveedor().isEmpty()) {
				
				for (DirectOrdersWSInitParamDTO detail : initParams.getOrdenes()) {
					if (detail.getNumero() == null) {
						return Response.status(Status.BAD_REQUEST).build();
					}
				}
				
				// LLAMA A SERVICIO MANAGER LOCAL
				AddDODeliveryOfDirectOrdersWSResultDTO doDeliveryAddArrayResultDTO = doDeliveryReportManagerLocal.doAddDODeliveryOfDirectOrdersWS(initParams);
				
				// Carga JSON de salida
				result = JsonUtils.getJsonFromObject(doDeliveryAddArrayResultDTO, AddDODeliveryOfDirectOrdersWSResultDTO.class);
			}
			else
			{
				return Response.status(Status.BAD_REQUEST).build();			
			}
			
		} catch (Exception e) {
			e.printStackTrace();

			AddDODeliveryOfDirectOrdersWSResultDTO baseResult = RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new AddDODeliveryOfDirectOrdersWSResultDTO(), "L1");
			result = JsonUtils.getJsonFromObject(baseResult, AddDODeliveryOfDirectOrdersWSResultDTO.class);
		}
		
		// RETORNA UN CODE RESPONSE		
		return Response.ok().entity(result).build();
	}

	// Actualizar despacho VeV PD
	@Path("updatevevpd")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update DODelivery State / Delivery Date", notes = "Actualiza Estado de Despacho VeV PD y Fecha de Entrega", hidden=true)
	@ApiResponses(value = {
		@ApiResponse(
				code=200,
				message = "Devuelve un codigo de mensaje controlado y la descripcion, incluso si da error",
				response = UpdateDODeliveryWSResultDTO.class),
		@ApiResponse(
				code=400,
				message = "Si algun parametro viene nulo"
				)
	})
	public Response doUpdateDODeliveryWS(@ApiParam(name="initParams", value="Datos de la orden y detalles de la entrega", required=true) UpdateDODeliveryWSInitParamDTO initParams) {
		String result = new String();

		try {
			// PARAMÉTROS DE LA LLAMADA A BACK-END
			// SE DEBEN HOMOLOGAR LOS DATOS QUE ENTRAN DESDE LA API A LOS QUE SE USAN EN BACK-END
			if (initParams.getNumeroOrden() != null && initParams.getRutproveedor() != null && !initParams.getRutproveedor().isEmpty() &&
				initParams.getNuevoestado() != null && !initParams.getNuevoestado().isEmpty() &&
				initParams.getFechaEntrega() != null && !initParams.getFechaEntrega().isEmpty() &&
				initParams.getDetalles() != null && initParams.getDetalles().length > 0) {

				for (DirectOrderDetailsWSInitParamDTO detail : initParams.getDetalles()) {
					if (detail.getSku() == null || detail.getSku().isEmpty() || detail.getCantidad() == null) {
						return Response.status(Status.BAD_REQUEST).build();
					}
				}

				// LLAMA A SERVICIO MANAGER LOCAL
				UpdateDODeliveryWSResultDTO doDeliveryStateResultDTO = doDeliveryReportManagerLocal.doUpdateDODeliveryWS(initParams);
				
				// Carga JSON de salida
				result = JsonUtils.getJsonFromObject(doDeliveryStateResultDTO, UpdateDODeliveryWSResultDTO.class);
			}
			else
			{
				return Response.status(Status.BAD_REQUEST).build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();

			UpdateDODeliveryWSResultDTO baseResult = RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L1");
			result = JsonUtils.getJsonFromObject(baseResult, UpdateDODeliveryWSResultDTO.class);
		}
		
		// RETORNA UN CODE RESPONSE		
		return Response.ok().entity(result).build();
	}
}
