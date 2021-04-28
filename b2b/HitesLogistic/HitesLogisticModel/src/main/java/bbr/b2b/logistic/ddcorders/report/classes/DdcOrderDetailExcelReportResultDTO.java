package bbr.b2b.logistic.ddcorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DdcOrderDetailExcelReportResultDTO extends BaseResultDTO {

	private DdcOrderDetailExcelReportDataDTO[] ddcorderdetails;

	public DdcOrderDetailExcelReportDataDTO[] getDdcorderdetails() {
		return ddcorderdetails;
	}

	public void setDdcorderdetails(DdcOrderDetailExcelReportDataDTO[] ddcorderdetails) {
		this.ddcorderdetails = ddcorderdetails;
	}
}
