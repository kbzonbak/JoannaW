package bbr.b2b.regional.logistic.fillrate.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class FillRateEvolutionInitParamDTO implements Serializable{

	private String vendorcode;
	private Long departmentid;
	private int latestperiods;
	private OrderCriteriaDTO[] orderby;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(Long departmentid) {
		this.departmentid = departmentid;
	}
	public int getLatestperiods() {
		return latestperiods;
	}
	public void setLatestperiods(int latestperiods) {
		this.latestperiods = latestperiods;
	}
	public OrderCriteriaDTO[] getOrderby() {
		return orderby;
	}
	public void setOrderby(OrderCriteriaDTO[] orderby) {
		this.orderby = orderby;
	}
	
}
