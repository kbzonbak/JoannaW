package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ShippingCertificationArrayResultDTO extends BaseResultDTO {

	private ShippingCertificationDTO[] shippingcertifications;

	public ShippingCertificationDTO[] getShippingcertifications() {
		return shippingcertifications;
	}

	public void setShippingcertifications(ShippingCertificationDTO[] shippingcertifications) {
		this.shippingcertifications = shippingcertifications;
	}
	
}
