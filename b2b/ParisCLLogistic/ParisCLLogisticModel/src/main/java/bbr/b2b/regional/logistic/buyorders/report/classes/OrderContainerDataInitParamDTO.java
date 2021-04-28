package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class OrderContainerDataInitParamDTO implements Serializable {

	private String vendorcode;//rut del prov
	private Long[] orderids;
	private OrderCriteriaDTO[] orderby;
	

	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long[] getOrderids() {
		return orderids;
	}
	public void setOrderids(Long[] orderids) {
		this.orderids = orderids;
	}
	public OrderCriteriaDTO[] getOrderby() {
		return orderby;
	}
	public void setOrderby(OrderCriteriaDTO[] orderby) {
		this.orderby = orderby;
	}
		
}
