package bbr.b2b.soa.mercadolibre.webservices.classes;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bbr.b2b.common.utils.JsonUtils;
import bbr.b2b.soa.mercadolibre.dto.classes.AuthorizationData;
import bbr.b2b.soa.mercadolibre.dto.classes.ItemResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.ItemStockUpdateData;
import bbr.b2b.soa.mercadolibre.entities.Authorization;
import bbr.b2b.soa.mercadolibre.entities.Company;
import bbr.b2b.soa.mercadolibre.managers.classes.AuthorizationManager;
import bbr.b2b.soa.mercadolibre.webservices.interfaces.IAuthorizationRESTService;

@RestController
@RequestMapping("/api")
public class AuthorizationRESTService implements IAuthorizationRESTService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationRESTService.class);

	@Autowired
	private AuthorizationManager authorizationManager;

	@Override
	@GetMapping(path = "/company/{companycode}/authorizations", produces = "application/json")
	public ResponseEntity<String> getAuthorizationsOfCompany(@PathVariable("companycode") String companycode) {
		ResponseEntity<String> resultEntity = null;
		Optional<Authorization> optAuth = authorizationManager.findAuthorizationsByCompanyRut(companycode);
		if (optAuth != null && optAuth.isPresent()) {
			Authorization authorization = optAuth.get();
			Company company = authorization.getCompany();
			LOGGER.debug("Empresa asociada la autorización: {}", company);
			authorization.setCompany(null);
			String result = JsonUtils.getJsonFromObject(authorization, Authorization.class);
			resultEntity = ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			resultEntity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return resultEntity;
	}

	@Override
	@PutMapping(path = "/company/{companycode}/authorizations", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> addOrUpdateAuthorizationForCompany(@PathVariable("companycode") String companycode, @RequestBody String jsonBody) {
		ResponseEntity<String> resultEntity = null;
		AuthorizationData data = JsonUtils.getObjectFromJson(jsonBody, AuthorizationData.class);
		Optional<Authorization> optAuth = authorizationManager.addOrUpdateAuthorizationForCompany(companycode, data);
		if (optAuth != null && optAuth.isPresent()) {
			Authorization authorization = optAuth.get();
			Company company = authorization.getCompany();
			LOGGER.debug("Empresa asociada la autorización: {}", company);
			authorization.setCompany(null);
			String result = JsonUtils.getJsonFromObject(authorization, Authorization.class);
			resultEntity = ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			resultEntity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return resultEntity;
	}

	@Override
	@PutMapping(path = "/company/{companycode}/items/{itemid}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> updateStockOfItemForCompany(@PathVariable("companycode") String companycode, @PathVariable("itemid") String itemid, @RequestBody String jsonBody) {
		ResponseEntity<String> resultEntity = null;
		ItemStockUpdateData data = JsonUtils.getObjectFromJson(jsonBody, ItemStockUpdateData.class);
		Optional<ItemResponse> item = authorizationManager.updateStockOfItem(companycode, itemid, data);
		if (item != null && item.isPresent()) {
			ItemResponse iresponse = item.get();
			resultEntity = ResponseEntity.status(HttpStatus.OK).body(iresponse.getJsonBody());
		} else {
			resultEntity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return resultEntity;
	}

	@Override
	@GetMapping(path = "/company/{companycode}/items/{itemid}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> getItemDataForCompany(@PathVariable("companycode") String companycode, @PathVariable("itemid") String itemid) {
		ResponseEntity<String> resultEntity = null;
		Optional<ItemResponse> item = authorizationManager.getItemData(companycode, itemid);
		if (item != null && item.isPresent()) {
			ItemResponse iresponse = item.get();
			resultEntity = ResponseEntity.status(HttpStatus.OK).body(iresponse.getJsonBody());
		} else {
			resultEntity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return resultEntity;
	}

}
