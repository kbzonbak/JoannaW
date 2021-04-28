package bbr.b2b.soa.mercadolibre.managers.classes;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import bbr.b2b.common.rest.utils.APIRESTClientContentType;
import bbr.b2b.common.rest.utils.AuthDataDTO;
import bbr.b2b.common.rest.utils.IAPIRESTClientConnection;
import bbr.b2b.common.utils.JsonUtils;
import bbr.b2b.soa.mercadolibre.config.classes.ServiceConfiguration;
import bbr.b2b.soa.mercadolibre.dto.classes.AccessTokenResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.AvailableQuantity;
import bbr.b2b.soa.mercadolibre.dto.classes.ItemResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.OrderResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.QuestionResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.ShipmentResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.items.ItemData;
import bbr.b2b.soa.mercadolibre.dto.classes.orders.OrderData;
import bbr.b2b.soa.mercadolibre.dto.classes.questions.QuestionData;
import bbr.b2b.soa.mercadolibre.dto.classes.shipments.ShipmentData;
import bbr.b2b.soa.mercadolibre.managers.interfaces.IMeliRestClient;

@Service
public class MeliRestClient implements IMeliRestClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(MeliRestClient.class);

	@Autowired
	private ServiceConfiguration serviceConfiguration;

	@Autowired
	private IAPIRESTClientConnection apiConnection;

	@Override
	public Optional<ItemResponse> getItemData(String itemId, String accessToken) {
		ItemResponse iresponse = null;

		// En este caso la ruta ya incluye el topico items
		String url = serviceConfiguration.getServiceHost() + itemId;
		ResponseEntity<String> response = apiConnection.executeRequest(url, HttpMethod.GET, accessToken);

		if (HttpStatus.OK.equals(response.getStatusCode())) {
			// MOSTRAR EL RESULTADO
			if (response != null && response.getBody() != null) {
				LOGGER.debug("Respuesta desde meli: {}", response.getBody());
				// CONVIERTE RESPUESTA JSON A OBJETO
				ItemData data = JsonUtils.getObjectFromJson(response.getBody(), ItemData.class);
				iresponse = new ItemResponse();
				iresponse.setData(data);
				iresponse.setJsonBody(response.getBody());
			}
		}
		Optional<ItemResponse> result = Optional.ofNullable(iresponse);
		return result;
	}

	@Override
	public Optional<OrderResponse> getOrderData(String orderId, String accessToken) {
		OrderResponse oresponse = null;

		// En este caso la ruta ya incluye el topico orders
		String url = serviceConfiguration.getServiceHost() + orderId;
		ResponseEntity<String> response = apiConnection.executeRequest(url, HttpMethod.GET, accessToken);

		if (HttpStatus.OK.equals(response.getStatusCode())) {
			// MOSTRAR EL RESULTADO
			if (response != null && response.getBody() != null) {
				LOGGER.debug("Respuesta desde meli: {}", response.getBody());
				// CONVIERTE RESPUESTA JSON A OBJETO
				OrderData data = JsonUtils.getObjectFromJson(response.getBody(), OrderData.class);
				oresponse = new OrderResponse();
				oresponse.setData(data);
				oresponse.setJsonBody(response.getBody());
			}
		}
		Optional<OrderResponse> result = Optional.ofNullable(oresponse);
		return result;
	}

	@Override
	public Optional<QuestionResponse> getQuestionData(String questionId, String accessToken) {
		QuestionResponse qresponse = null;

		// En este caso la ruta ya incluye el topico questions
		String url = serviceConfiguration.getServiceHost() + questionId;
		ResponseEntity<String> response = apiConnection.executeRequest(url, HttpMethod.GET, accessToken);

		if (HttpStatus.OK.equals(response.getStatusCode())) {
			// MOSTRAR EL RESULTADO
			if (response != null && response.getBody() != null) {
				LOGGER.debug("Respuesta desde meli: {}", response.getBody());
				// CONVIERTE RESPUESTA JSON A OBJETO
				QuestionData data = JsonUtils.getObjectFromJson(response.getBody(), QuestionData.class);
				qresponse = new QuestionResponse();
				qresponse.setData(data);
				qresponse.setJsonBody(response.getBody());
			}
		}
		Optional<QuestionResponse> result = Optional.ofNullable(qresponse);
		return result;
	}

	@Override
	public Optional<ShipmentResponse> getShipmentData(String itemId, String accessToken) {
		ShipmentResponse iresponse = null;

		// En este caso la ruta ya incluye el topico items
		String url = serviceConfiguration.getServiceHost() + itemId;
		ResponseEntity<String> response = apiConnection.executeRequest(url, HttpMethod.GET, accessToken);

		if (HttpStatus.OK.equals(response.getStatusCode())) {
			// MOSTRAR EL RESULTADO
			if (response != null && response.getBody() != null) {
				LOGGER.debug("Respuesta desde meli: {}", response.getBody());
				// CONVIERTE RESPUESTA JSON A OBJETO
				ShipmentData data = JsonUtils.getObjectFromJson(response.getBody(), ShipmentData.class);
				iresponse = new ShipmentResponse();
				iresponse.setData(data);
				iresponse.setJsonBody(response.getBody());
			}
		}
		Optional<ShipmentResponse> result = Optional.ofNullable(iresponse);
		return result;
	}

	@Override
	public Optional<File> getShipmentLabelData(ShipmentData shipment, String accessToken) {
		// TODO DVI TEST
		File fresponse = null;
		// La URL para etiquetas es de la forma:
		// https://api.mercadolibre.com/shipment_labels?shipment_ids=20178600648&response_type=zpl2
		String url = serviceConfiguration.getServiceHost() + "/shipment_labels?shipment_ids=" + shipment.getId() + "&response_type=zpl2";
		ResponseEntity<File> response = apiConnection.downloadRequest(url, null, null, new AuthDataDTO(accessToken));

		if (HttpStatus.OK.equals(response.getStatusCode())) {
			// MOSTRAR EL RESULTADO
			if (response != null && response.getBody() != null) {
				LOGGER.debug("Respuesta desde meli: {}", response.getBody());
				fresponse = response.getBody();
			}
		}
		Optional<File> result = Optional.ofNullable(fresponse);
		return result;
	}

	@Override
	public Optional<ItemResponse> updateStockOfItem(String itemId, int new_stock, String accessToken) {
		ItemResponse iresponse = null;

		// En este caso la ruta no incluye el topico items, se debe agregar
		String url = serviceConfiguration.getServiceHost() + serviceConfiguration.getPathItem() + "/" + itemId;
		AvailableQuantity objJson = new AvailableQuantity();
		objJson.setAvailable_quantity(new_stock);
		String json = JsonUtils.getJsonFromObject(objJson, AvailableQuantity.class);

		ResponseEntity<String> response = apiConnection.executeRequest(url, json, APIRESTClientContentType.JSON,
				HttpMethod.PUT, accessToken);

		if (HttpStatus.OK.equals(response.getStatusCode())) {
			// MOSTRAR EL RESULTADO
			if (response != null && response.getBody() != null)
				LOGGER.debug("Respuesta desde meli: {}", response.getBody());
			// CONVIERTE RESPUESTA JSON A OBJETO
			ItemData data = JsonUtils.getObjectFromJson(response.getBody(), ItemData.class);
			iresponse = new ItemResponse();
			iresponse.setData(data);
			iresponse.setJsonBody(response.getBody());
		}
		Optional<ItemResponse> result = Optional.ofNullable(iresponse);
		return result;
	}

	@Override
	public Optional<AccessTokenResponse> getAccessToken(String authorizationCode) {
		AccessTokenResponse token = null;

		String url = serviceConfiguration.getServiceHost() + serviceConfiguration.getPathToken();
		// El código de autorizacion se obtiene desde la BD para la empresa
		Map<String, Object> params = new HashMap<>();
		params.put("grant_type", serviceConfiguration.getGrantTypeAuthorization());
		params.put("client_id", serviceConfiguration.getClientId());
		params.put("client_secret", serviceConfiguration.getClientSecret());
		params.put("code", authorizationCode);
		params.put("redirect_uri", serviceConfiguration.getRedirectUri());

		ResponseEntity<String> response = apiConnection.executeRequest(url, params, APIRESTClientContentType.FORM,
				HttpMethod.POST);

		if (HttpStatus.OK.equals(response.getStatusCode())) {
			// MOSTRAR EL RESULTADO
			if (response != null && response.getBody() != null)
				LOGGER.debug("Respuesta desde meli: {}", response.getBody());
			// CONVIERTE RESPUESTA JSON A OBJETO
			token = JsonUtils.getObjectFromJson(response.getBody(), AccessTokenResponse.class);
		}
		Optional<AccessTokenResponse> result = Optional.ofNullable(token);
		return result;
	}

	@Override
	public Optional<AccessTokenResponse> getRefreshToken(String authorizationCode) {
		// TODO TEST
		AccessTokenResponse token = null;

		String url = serviceConfiguration.getServiceHost() + serviceConfiguration.getPathToken();
		// El código de autorizacion se obtiene desde la BD para la empresa
		Map<String, Object> params = new HashMap<>();
		params.put("grant_type", serviceConfiguration.getGrantTypeRefresh());
		params.put("client_id", serviceConfiguration.getClientId());
		params.put("client_secret", serviceConfiguration.getClientSecret());
		params.put("refresh_token", authorizationCode);

		ResponseEntity<String> response = apiConnection.executeRequest(url, params, APIRESTClientContentType.FORM,
				HttpMethod.POST);

		if (HttpStatus.OK.equals(response.getStatusCode())) {
			// MOSTRAR EL RESULTADO
			if (response != null && response.getBody() != null)
				LOGGER.debug("Respuesta desde meli: {}", response.getBody());
			// CONVIERTE RESPUESTA JSON A OBJETO
			token = JsonUtils.getObjectFromJson(response.getBody(), AccessTokenResponse.class);
		}
		Optional<AccessTokenResponse> result = Optional.ofNullable(token);
		return result;
	}

}
