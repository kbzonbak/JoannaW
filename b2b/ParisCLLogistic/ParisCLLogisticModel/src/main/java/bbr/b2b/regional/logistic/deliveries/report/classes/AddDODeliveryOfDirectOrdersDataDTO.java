package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class AddDODeliveryOfDirectOrdersDataDTO implements Serializable {

	private Long deliveryid;
	private Long deliverynumber;
		
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public Long getDeliverynumber() {
		return deliverynumber;
	}
	public void setDeliverynumber(Long deliverynumber) {
		this.deliverynumber = deliverynumber;
	}
}
