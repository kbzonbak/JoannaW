package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.time.LocalDateTime;


public class VeVCDExcelOrderReportDataDTO extends ExcelOrderReportDataDTO {
	
	private LocalDateTime originaldeliverydate;
	private String requestnumber;
	private String commune;
	private String salestorename;
	private String salestorecode;
	
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
	public String getSalestorename() {
		return salestorename;
	}
	public void setSalestorename(String salestorename) {
		this.salestorename = salestorename;
	}
	public String getSalestorecode() {
		return salestorecode;
	}
	public void setSalestorecode(String salestorecode) {
		this.salestorecode = salestorecode;
	}
}
