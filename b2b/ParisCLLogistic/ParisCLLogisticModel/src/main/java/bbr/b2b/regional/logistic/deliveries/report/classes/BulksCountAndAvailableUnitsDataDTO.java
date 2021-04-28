package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class BulksCountAndAvailableUnitsDataDTO implements Serializable {

	private Long deliveryid;
	private Integer boxcount;
	private Integer palletcount;
	private Double availableunits;
	
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public Integer getBoxcount() {
		return boxcount;
	}
	public void setBoxcount(Integer boxcount) {
		this.boxcount = boxcount;
	}
	public Integer getPalletcount() {
		return palletcount;
	}
	public void setPalletcount(Integer palletcount) {
		this.palletcount = palletcount;
	}
	public Double getAvailableunits() {
		return availableunits;
	}
	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}	
}
