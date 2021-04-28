package bbr.b2b.regional.logistic.mobile.classes;

import java.io.Serializable;

public class DODeliveryDetailMobileInitParamDTO implements Serializable {

	private Long userid;
	private Long deliveryid;
	
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	
}
