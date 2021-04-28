package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.time.LocalDateTime;

public class VeVCDOrderReportDataDTO extends OrderReportDataDTO {

	private LocalDateTime originaldeliverydate;
	private String requestnumber;
	private String commune;
	private String salestore;
	
	public LocalDateTime getOriginaldeliverydate() {
		return originaldeliverydate;
	}
	public void setOriginaldeliverydate(LocalDateTime originaldeliverydate) {
		this.originaldeliverydate = originaldeliverydate;
	}
	public String getRequestnumber() {
		return requestnumber;
	}
	public void setRequestnumber(String requestnumber) {
		this.requestnumber = requestnumber;
	}
	public String getCommune() {
		return commune;
	}
	public void setCommune(String commune) {
		this.commune = commune;
	}
	public String getSalestore() {
		return salestore;
	}
	public void setSalestore(String salestore) {
		this.salestore = salestore;
	}
}
