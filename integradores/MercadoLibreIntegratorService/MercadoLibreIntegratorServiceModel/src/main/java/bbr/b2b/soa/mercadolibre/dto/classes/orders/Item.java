package bbr.b2b.soa.mercadolibre.dto.classes.orders;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class Item implements Serializable {

	private String id;

	private String title;

	private String category_id;

	private String warranty;

	private String condition;

	private String seller_sku;

	private Integer global_price;

}
