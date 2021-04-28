package bbr.b2b.soa.mercadolibre.dto.classes.items;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class ItemData implements Serializable {

	private String id;

	private String site_id;

	private String title;

	private Integer seller_id;

	private String category_id;

	private Integer price;

	private Integer base_price;

	private Integer original_price;

	private String currency_id;

	private Integer initial_quantity;

	private Integer available_quantity;

	private Integer sold_quantity;

	private String buying_mode;

	private String listing_type_id;

	private String condition;

	private String permalink;

	private String thumbnail_id;

	private String thumbnail;

	private String secure_thumbnail;

	private String status;

}
