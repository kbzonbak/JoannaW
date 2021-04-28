package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IBulkDetail;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IBulkDetailPK;

public class BulkDetailW implements IBulkDetailPK, IBulkDetail {

	private Double units;
	private String refdocument;
	private Long orderid;
	private Long deliveryid;
	private Long itemid;
	private Long locationid;
	private Long bulkid;
	private Long taxdocumentid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = bulkid.longValue() - ((IBulkDetailPK) arg0).getBulkid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = orderid.longValue() - ((IBulkDetailPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = deliveryid.longValue() - ((IBulkDetailPK) arg0).getDeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IBulkDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = locationid.longValue() - ((IBulkDetailPK) arg0).getLocationid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Double getUnits(){ 
		return this.units;
	}
	public String getRefdocument(){ 
		return this.refdocument;
	}
	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getDeliveryid(){ 
		return this.deliveryid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public Long getBulkid(){ 
		return this.bulkid;
	}
	public void setUnits(Double units){ 
		this.units = units;
	}
	public void setRefdocument(String refdocument){ 
		this.refdocument = refdocument;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setDeliveryid(Long deliveryid){ 
		this.deliveryid = deliveryid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
	public void setBulkid(Long bulkid){ 
		this.bulkid = bulkid;
	}
	public Long getTaxdocumentid() {
		return taxdocumentid;
	}
	public void setTaxdocumentid(Long taxdocumentid) {
		this.taxdocumentid = taxdocumentid;
	}
}
