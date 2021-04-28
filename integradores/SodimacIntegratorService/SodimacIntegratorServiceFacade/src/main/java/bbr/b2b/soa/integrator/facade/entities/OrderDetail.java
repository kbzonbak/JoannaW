package bbr.b2b.soa.integrator.facade.entities;

import lombok.Data;

@Data
public class OrderDetail {

	private Long id;
	private int version;
	private Integer position;
	private String upc;
	private String sku;
	private String longDescription;
	private String codePack;
	private String brand;
	private String family;
	private String department;
	private String umPack;
	private String shortDescription;
	private String dedication;
	private String giftPaper;
	private String hall;
	private String lineal;
	private Double unitPrice;
	private Integer productAmount;
	private Integer packAmount;
	private Double palletLayerAmount;
	private Double palletLayerPack;
	private Double additionalCharge;
	private Double additionalDiscount;
	private Double totalCharge;
	private Double totalDiscount;
	private Double itemTotalDiscount;
	private Double totalUnits;
	
	private Order order;

}