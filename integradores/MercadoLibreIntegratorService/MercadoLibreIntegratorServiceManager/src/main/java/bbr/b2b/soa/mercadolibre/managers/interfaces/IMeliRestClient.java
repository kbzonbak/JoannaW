package bbr.b2b.soa.mercadolibre.managers.interfaces;

import java.io.File;
import java.util.Optional;

import bbr.b2b.soa.mercadolibre.dto.classes.AccessTokenResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.ItemResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.OrderResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.QuestionResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.ShipmentResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.shipments.ShipmentData;

public interface IMeliRestClient {

	public Optional<AccessTokenResponse> getAccessToken(String authorizationCode);

	public Optional<AccessTokenResponse> getRefreshToken(String authorizationCode);

	public Optional<ItemResponse> updateStockOfItem(String itemId, int new_stock, String accessToken);

	public Optional<ItemResponse> getItemData(String itemId, String accessToken);

	public Optional<OrderResponse> getOrderData(String orderId, String accessToken);

	public Optional<QuestionResponse> getQuestionData(String questionId, String accessToken);

	public Optional<ShipmentResponse> getShipmentData(String itemId, String accessToken);

	public Optional<File> getShipmentLabelData(ShipmentData shipment, String accessToken);

}
