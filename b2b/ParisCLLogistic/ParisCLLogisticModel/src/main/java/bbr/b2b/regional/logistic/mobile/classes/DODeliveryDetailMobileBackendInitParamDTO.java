package bbr.b2b.regional.logistic.mobile.classes;

import java.io.Serializable;

public class DODeliveryDetailMobileBackendInitParamDTO implements Serializable {

	private String useremail;

	private Long deliveryid;

	public Long getDeliveryid() {
		return deliveryid;
	}

	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

}
