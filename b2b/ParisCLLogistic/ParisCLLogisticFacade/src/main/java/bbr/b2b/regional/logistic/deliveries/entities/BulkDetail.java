package bbr.b2b.regional.logistic.deliveries.entities;

import bbr.b2b.regional.logistic.deliveries.data.interfaces.IBulkDetail;
import bbr.b2b.regional.logistic.taxdocuments.entities.TaxDocument;

public class BulkDetail implements IBulkDetail {

	private BulkDetailId id;
	private Double units;
	private String refdocument;
	private OrderDeliveryDetail orderdeliverydetail;
	private Bulk bulk;
	private TaxDocument taxdocument;

	public BulkDetailId getId(){ 
		return this.id;
	}
	public Double getUnits(){ 
		return this.units;
	}
	public String getRefdocument(){ 
		return this.refdocument;
	}
	public OrderDeliveryDetail getOrderdeliverydetail(){ 
		return this.orderdeliverydetail;
	}
	public Bulk getBulk(){ 
		return this.bulk;
	}
	public void setId(BulkDetailId id){ 
		this.id = id;
	}
	public void setUnits(Double units){ 
		this.units = units;
	}
	public void setRefdocument(String refdocument){ 
		this.refdocument = refdocument;
	}
	public void setOrderdeliverydetail(OrderDeliveryDetail orderdeliverydetail){ 
		this.orderdeliverydetail = orderdeliverydetail;
	}
	public void setBulk(Bulk bulk){ 
		this.bulk = bulk;
	}
	public TaxDocument getTaxdocument() {
		return taxdocument;
	}
	public void setTaxdocument(TaxDocument taxdocument) {
		this.taxdocument = taxdocument;
	}
	
}
