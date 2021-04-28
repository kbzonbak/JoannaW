package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ReceptionDetailExcelArrayResultDTO extends BaseResultDTO {

	private ReceptionDetailExcelDTO[] receptiondetails;

	public ReceptionDetailExcelDTO[] getReceptiondetails() {
		return receptiondetails;
	}

	public void setReceptiondetails(ReceptionDetailExcelDTO[] receptiondetails) {
		this.receptiondetails = receptiondetails;
	}
}
