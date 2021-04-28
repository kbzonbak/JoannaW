package bbr.b2b.soa.integrator.webservices.classes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bbr.b2b.common.utils.JsonUtils;
import bbr.b2b.soa.integrator.extractors.manager.classes.SodimacOrderExtractorManager;
import bbr.b2b.soa.integrator.extractors.manager.classes.SodimacSalesExtractorManager;
import bbr.b2b.soa.integrator.webservices.dtos.APIResultDTO;
import bbr.b2b.soa.integrator.webservices.interfaces.ISchedulerRESTService;

@RestController
@RequestMapping("/schedule")
public class SchedulerRESTService implements ISchedulerRESTService {

	@Autowired
	private SodimacOrderExtractorManager sodimacOrderExtractorManager;
	
	@Autowired
	private SodimacSalesExtractorManager sodimacSalesExtractorManager;

	@Override
	@GetMapping(path = "/sodimac/eoc/downloadorders/execute", produces = "application/json")
	public ResponseEntity<String> doDownloadEocSales() {
		String result = new String();
		ResponseEntity<String> resultEntity = null;
		try {
			sodimacOrderExtractorManager.doProcessOC();
			APIResultDTO resultDTO = new APIResultDTO("0", "Se ha ejecutado correctamente la descarga de órdenes desde api Sodimac"); 
			result = JsonUtils.getJsonFromObject(resultDTO, APIResultDTO.class);
			resultEntity = ResponseEntity.status(HttpStatus.OK).body(result);			
		} catch (Exception e) {
			e.printStackTrace();
			APIResultDTO resultDTO = new APIResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, APIResultDTO.class);
			resultEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		}		
		return resultEntity;
	}
	
	@Override
	@GetMapping(path = "/sodimac/eod/downloadorders/execute", produces = "application/json")
	public ResponseEntity<String> doDownloadEodSales() {
		String result = new String();
		ResponseEntity<String> resultEntity = null;
		try {
			sodimacOrderExtractorManager.doProcessOD();
			APIResultDTO resultDTO = new APIResultDTO("0", "Se ha ejecutado correctamente la descarga de órdenes desde api Sodimac"); 
			result = JsonUtils.getJsonFromObject(resultDTO, APIResultDTO.class);
			resultEntity = ResponseEntity.status(HttpStatus.OK).body(result);	
		} catch (Exception e) {
			e.printStackTrace();
			APIResultDTO resultDTO = new APIResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, APIResultDTO.class);
			resultEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		}
		return resultEntity;
	}
	
	@Override
	@GetMapping(path = "/sodimac/eoe/downloadorders/execute", produces = "application/json")
	public ResponseEntity<String> doDownloadEoeSales() {
		String result = new String();
		ResponseEntity<String> resultEntity = null;
		try {
			sodimacOrderExtractorManager.doProcessOE();
			APIResultDTO resultDTO = new APIResultDTO("0", "Se ha ejecutado correctamente la descarga de órdenes desde api Sodimac"); 
			result = JsonUtils.getJsonFromObject(resultDTO, APIResultDTO.class);
			resultEntity = ResponseEntity.status(HttpStatus.OK).body(result);	
		} catch (Exception e) {
			e.printStackTrace();
			APIResultDTO resultDTO = new APIResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, APIResultDTO.class);
			resultEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		}
		
		return resultEntity;
	}
	
	@Override
	@GetMapping(path = "/sodimac/evta/downloadorders/execute", produces = "application/json")
	public ResponseEntity<String> doDownloadEvtaSales() {
		String result = new String();
		ResponseEntity<String> resultEntity = null;
		try {
			sodimacSalesExtractorManager.doProcessVtaInv();
			APIResultDTO resultDTO = new APIResultDTO("0", "Se ha ejecutado correctamente la descarga de Venta/Inventario desde api Sodimac"); 
			result = JsonUtils.getJsonFromObject(resultDTO, APIResultDTO.class);
			resultEntity = ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			e.printStackTrace();
			APIResultDTO resultDTO = new APIResultDTO("500", e.getMessage()); 
			result = JsonUtils.getJsonFromObject(resultDTO, APIResultDTO.class);
			resultEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		}
		
		return resultEntity;
	}
	
	@GetMapping(path = "/download")
	public ResponseEntity<Resource> download(String param) throws IOException {

		ResponseEntity<Resource> resultEntity = null;
		
		try {
			HttpHeaders headers = new HttpHeaders();
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        headers.add("Pragma", "no-cache");
	        headers.add("Expires", "0");

	        File file = new File("/opt/fileserver/VTA_96792430_76163495_20210330160451932.csv");
	        
		    Path path = Paths.get(file.getAbsolutePath());
		    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		    
		    resultEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
		    
		}catch (Exception e) {
			e.printStackTrace();				
			resultEntity = ResponseEntity.notFound().build();
		}
		
		return resultEntity;
	}	
}
