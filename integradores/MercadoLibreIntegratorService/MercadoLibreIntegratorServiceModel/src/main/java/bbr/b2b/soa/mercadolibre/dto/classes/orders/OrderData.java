package bbr.b2b.soa.mercadolibre.dto.classes.orders;

import java.io.Serializable;

import bbr.b2b.soa.mercadolibre.dto.classes.Person;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class OrderData implements Serializable {

	private Long id;

	private String status;

	private String date_created;

	private String date_closed;

	private String expiration_date;

	private String comment;

	private BillingInfo billing_info;

	private OrderItem[] order_items;

	private Integer total_amount;

	private String currency_id;

	private Person buyer;

	private Person seller;

}
