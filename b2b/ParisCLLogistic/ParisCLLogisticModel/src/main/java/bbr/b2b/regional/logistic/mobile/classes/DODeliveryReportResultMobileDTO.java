package bbr.b2b.regional.logistic.mobile.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DODeliveryReportResultMobileDTO extends BaseResultDTO {

	private DODeliveryReportDataMobileDTO[] dodeliverydata;
	private int totalorderdelivery;
	
	public DODeliveryReportDataMobileDTO[] getDodeliverydata() {
		return dodeliverydata;
	}
	public void setDodeliverydata(DODeliveryReportDataMobileDTO[] dodeliverydata) {
		this.dodeliverydata = dodeliverydata;
	}
	public int getTotalorderdelivery() {
		return totalorderdelivery;
	}
	public void setTotalorderdelivery(int totalorderdelivery) {
		this.totalorderdelivery = totalorderdelivery;
	}
	
}
