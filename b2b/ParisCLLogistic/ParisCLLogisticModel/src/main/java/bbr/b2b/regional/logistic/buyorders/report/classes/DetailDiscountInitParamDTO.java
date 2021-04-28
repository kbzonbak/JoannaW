package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class DetailDiscountInitParamDTO implements Serializable{

	private String vendorcode;//rut del prov
	private String locationcode;
	private Long orderid;
	private Long itemid;
	private OrderCriteriaDTO[] orderby;
	

	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public OrderCriteriaDTO[] getOrderby() {
		return orderby;
	}
	public void setOrderby(OrderCriteriaDTO[] orderby) {
		this.orderby = orderby;
	}
	
}
