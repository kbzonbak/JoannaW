package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;



public class UploadPackingListResultDTO extends BaseResultDTO{

	private Integer plamount;
	private Double pltotalunits;
	private BaseResultDTO[] validationerrors;
	private DeliveryReportDataDTO deliveryreportdata;
	
	public Integer getPlamount() {
		return plamount;
	}
	public void setPlamount(Integer plamount) {
		this.plamount = plamount;
	}
	public Double getPltotalunits() {
		return pltotalunits;
	}
	public void setPltotalunits(Double pltotalunits) {
		this.pltotalunits = pltotalunits;
	}
	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}
	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}
	public DeliveryReportDataDTO getDeliveryreportdata() {
		return deliveryreportdata;
	}
	public void setDeliveryreportdata(DeliveryReportDataDTO deliveryreportdata) {
		this.deliveryreportdata = deliveryreportdata;
	}
}
