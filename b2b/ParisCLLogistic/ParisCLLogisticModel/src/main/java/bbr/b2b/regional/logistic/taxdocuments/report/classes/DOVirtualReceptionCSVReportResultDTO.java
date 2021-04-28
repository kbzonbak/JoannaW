package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DOVirtualReceptionCSVReportResultDTO extends BaseResultDTO{

	private DOVirtualReceptionCSVReportDataDTO[] virtualreceptionreport;

	public DOVirtualReceptionCSVReportDataDTO[] getVirtualreceptionreport() {
		return virtualreceptionreport;
	}

	public void setVirtualreceptionreport(DOVirtualReceptionCSVReportDataDTO[] virtualreceptionreport) {
		this.virtualreceptionreport = virtualreceptionreport;
	}

}
