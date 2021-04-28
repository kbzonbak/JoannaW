package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class DatingRequestInitParamDTO implements Serializable {

	private OrderUnitsDTO[] orderunits;
	private DatingRequestDTO datingrequest;
	private String vendorcode;
	private Long deliveryid;
	
	public OrderUnitsDTO[] getOrderunits() {
		return orderunits;
	}
	public void setOrderunits(OrderUnitsDTO[] orderunits) {
		this.orderunits = orderunits;
	}
	public DatingRequestDTO getDatingrequest() {
		return datingrequest;
	}
	public void setDatingrequest(DatingRequestDTO datingrequest) {
		this.datingrequest = datingrequest;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}	
}
