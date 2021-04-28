package bbr.b2b.soa.mercadolibre.dto.classes.orders;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class OrderItem implements Serializable {

	private Item item;

	private Integer quantity;

	private Integer unit_price;

	private Integer full_unit_price;

	private String currency_id;

	private String listing_type_id;

}
