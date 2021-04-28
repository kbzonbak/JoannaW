package bbr.b2b.soa.mercadolibre.dto.classes;

import java.io.Serializable;

import bbr.b2b.soa.mercadolibre.dto.classes.orders.OrderData;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class OrderResponse implements Serializable {

	private OrderData data;

	private String jsonBody;

}
