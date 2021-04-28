package bbr.b2b.soa.mercadolibre.webservices.interfaces;

import org.springframework.http.ResponseEntity;

public interface ICallbackService {

	public ResponseEntity<String> processNotification(String jsonBody);

}
