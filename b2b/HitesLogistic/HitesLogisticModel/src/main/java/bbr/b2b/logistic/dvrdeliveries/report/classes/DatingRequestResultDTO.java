package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DatingRequestResultDTO extends BaseResultDTO {

	private String deliverylocation;
	private Long deliverynumber;
	private DvrDeliveryStateTypeDataDTO currentstatetypeDTO;
	private BaseResultDTO[] validationerrors;

	public String getDeliverylocation() {
		return deliverylocation;
	}

	public void setDeliverylocation(String deliverylocation) {
		this.deliverylocation = deliverylocation;
	}

	public Long getDeliverynumber() {
		return deliverynumber;
	}

	public void setDeliverynumber(Long deliverynumber) {
		this.deliverynumber = deliverynumber;
	}

	public DvrDeliveryStateTypeDataDTO getCurrentstatetypeDTO() {
		return currentstatetypeDTO;
	}

	public void setCurrentstatetypeDTO(DvrDeliveryStateTypeDataDTO currentstatetypeDTO) {
		this.currentstatetypeDTO = currentstatetypeDTO;
	}

	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}

	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}

}
