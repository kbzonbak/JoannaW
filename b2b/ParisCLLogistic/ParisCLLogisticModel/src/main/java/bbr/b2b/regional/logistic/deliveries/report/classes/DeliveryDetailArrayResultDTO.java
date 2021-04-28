package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DeliveryDetailArrayResultDTO extends BaseResultDTO{

	private DeliveryDetailReportDataDTO[] deliverydetails = null;
	private String currentstatetype;
	private Boolean retailImported;
	
	public DeliveryDetailReportDataDTO[] getDeliverydetails() {
		return deliverydetails;
	}

	public void setDeliverydetails(DeliveryDetailReportDataDTO[] deliverydetails) {
		this.deliverydetails = deliverydetails;
	}

	public String getCurrentstatetype() {
		return currentstatetype;
	}

	public void setCurrentstatetype(String currentstatetype) {
		this.currentstatetype = currentstatetype;
	}

	public Boolean getRetailImported() {
		return retailImported;
	}

	public void setRetailImported(Boolean retailImported) {
		this.retailImported = retailImported;
	}
	
}
