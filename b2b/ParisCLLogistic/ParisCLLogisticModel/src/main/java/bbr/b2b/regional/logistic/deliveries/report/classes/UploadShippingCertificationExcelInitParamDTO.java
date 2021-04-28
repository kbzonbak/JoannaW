package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;


public class UploadShippingCertificationExcelInitParamDTO implements Serializable{
	
	private String filename;
	private DODeliveryReportDataDTO[] dodeliveryreportdata;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public DODeliveryReportDataDTO[] getDodeliveryreportdata() {
		return dodeliveryreportdata;
	}
	public void setDodeliveryreportdata(DODeliveryReportDataDTO[] dodeliveryreportdata) {
		this.dodeliveryreportdata = dodeliveryreportdata;
	}
		
}
