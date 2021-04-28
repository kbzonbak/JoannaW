package bbr.b2b.soa.mercadolibre.webservices.interfaces;

import org.springframework.http.ResponseEntity;

public interface IAuthorizationRESTService {

	public ResponseEntity<String> getAuthorizationsOfCompany(String companycode);

	public ResponseEntity<String> addOrUpdateAuthorizationForCompany(String companycode, String jsonBody);

	public ResponseEntity<String> updateStockOfItemForCompany(String companycode, String itemid, String jsonBody);

	public ResponseEntity<String> getItemDataForCompany(String companycode, String itemid);

}
