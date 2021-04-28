package bbr.b2b.soa.integrator.facade.entities;

import lombok.Data;

@Data
public class PreDeliveryDetail {

	private Long id;
	private int version;
	
	private String sku;
	private String storeNumber;
	private String store;
	private Double productAmount;
	private Double packAmount;
	
	private OrderDetail orderDetail;

}