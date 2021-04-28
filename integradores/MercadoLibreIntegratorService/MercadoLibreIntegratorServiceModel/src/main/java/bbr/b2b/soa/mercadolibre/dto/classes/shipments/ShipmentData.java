package bbr.b2b.soa.mercadolibre.dto.classes.shipments;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class ShipmentData implements Serializable {

	private String id;

	private String mode;

	private String date_created;

	private String last_updated;

	private Long order_id;

	private String created_by;

	private String site_id;

	private Integer order_cost;

	private Integer base_cost;

	private String status;

	private String substatus;

}
