package bbr.b2b.soa.mercadolibre.webservices.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bbr.b2b.soa.mercadolibre.managers.classes.NotificationManager;
import bbr.b2b.soa.mercadolibre.webservices.interfaces.ICallbackService;

@RestController
@RequestMapping("/callback")
public class CallbackRESTService implements ICallbackService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CallbackRESTService.class);

	@Autowired
	private NotificationManager notificationManager;

	@Override
	@PostMapping(path = "/notifications", consumes = "application/json")
	public ResponseEntity<String> processNotification(@RequestBody String jsonBody) {
		try {
			// Registrar la notificacion en la cola
			LOGGER.info("Notificacion recibida :{}", jsonBody);
			notificationManager.saveNotificationMessage(jsonBody);
			ResponseEntity<String> resultEntity = ResponseEntity.status(HttpStatus.OK).build();
			return resultEntity;
		} catch (Exception e) {
			ResponseEntity<String> resultEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			return resultEntity;
		}
	}

}
