package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class DatingRequestContainerInitParamDTO implements Serializable {

	private String locationcode;
	private Long datingrequestid;
	private OrderCriteriaDTO[] orderby;
	
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public Long getDatingrequestid() {
		return datingrequestid;
	}
	public void setDatingrequestid(Long datingrequestid) {
		this.datingrequestid = datingrequestid;
	}
	public OrderCriteriaDTO[] getOrderby() {
		return orderby;
	}
	public void setOrderby(OrderCriteriaDTO[] orderby) {
		this.orderby = orderby;
	}
	
}
