package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class BulkDetailSummaryDTO implements Serializable{

	private Integer boxcount;
	private Integer palletcount;
	private Double packedunits; 
	private Long orderid;
	private Long departmentid; 
	private Long deliveryid;
	
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
	public Double getPackedunits() {
		return packedunits;
	}
	public void setPackedunits(Double packedunits) {
		this.packedunits = packedunits;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(Long departmentid) {
		this.departmentid = departmentid;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}	
}
