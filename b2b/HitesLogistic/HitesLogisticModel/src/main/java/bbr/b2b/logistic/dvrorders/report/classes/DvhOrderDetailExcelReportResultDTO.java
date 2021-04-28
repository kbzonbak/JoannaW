package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DvhOrderDetailExcelReportResultDTO extends BaseResultDTO {

	private DvhOrderDetailExcelReportDataDTO[] dvhorderdetails;

	public DvhOrderDetailExcelReportDataDTO[] getDvhorderdetails() {
		return dvhorderdetails;
	}

	public void setDvhorderdetails(DvhOrderDetailExcelReportDataDTO[] dvhorderdetails) {
		this.dvhorderdetails = dvhorderdetails;
	}
}