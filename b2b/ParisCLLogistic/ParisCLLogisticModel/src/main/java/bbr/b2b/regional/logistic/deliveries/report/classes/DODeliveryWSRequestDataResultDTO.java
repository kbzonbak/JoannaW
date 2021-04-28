package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DODeliveryWSRequestDataResultDTO extends BaseResultDTO {
	
	private Long ticketnumber;
	private DODeliveryResultBean[] faileddeliveries;
	private DODeliveryResultBean[] successdeliveries;
	private BaseResultDTO[] validationerrors;
	
	public Long getTicketnumber() {
		return ticketnumber;
	}

	public void setTicketnumber(Long ticketnumber) {
		this.ticketnumber = ticketnumber;
	}

	public DODeliveryResultBean[] getFaileddeliveries() {
		return faileddeliveries;
	}

	public void setFaileddeliveries(DODeliveryResultBean[] faileddeliveries) {
		this.faileddeliveries = faileddeliveries;
	}

	public DODeliveryResultBean[] getSuccessdeliveries() {
		return successdeliveries;
	}

	public void setSuccessdeliveries(DODeliveryResultBean[] successdeliveries) {
		this.successdeliveries = successdeliveries;
	}

	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}

	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}
	
}
