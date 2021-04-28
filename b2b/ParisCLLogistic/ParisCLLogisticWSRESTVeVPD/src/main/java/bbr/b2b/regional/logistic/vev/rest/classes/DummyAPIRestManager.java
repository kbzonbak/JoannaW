package bbr.b2b.regional.logistic.vev.rest.classes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.keycloak.classes.JsonUtils;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;

@Path("test")
public class DummyAPIRestManager {
	
	@Path("dummyservice")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSome() {
		
		String result = new String();
		Response response = null;
		
		try {							 	            		            	
			BaseResultDTO resultService = new BaseResultDTO();
			
			resultService.setStatuscode("0");
			resultService.setStatusmessage("Esto es una prueba!!");
			
			// CONVIERTE RESPUESTA JSON A OBJETO
			result = JsonUtils.getJsonFromObject(resultService, BaseResultDTO.class);			
					
			response = Response.ok().entity(result).build();				
			
		} catch (Exception e) {
			e.printStackTrace();

			BaseResultDTO baseResult = RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1");
			result = JsonUtils.getJsonFromObject(baseResult, BaseResultDTO.class);
			
			response = Response.serverError().entity(result).build();
		}
		
		// RETORNA UN CODE RESPONSE		
		return response;
	}	
}
