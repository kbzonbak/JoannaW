package bbr.b2b.regional.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ImportedNonDeliveredOrderReportResultDTO extends BaseResultDTO {

	private ImportedNonDeliveredOrderDTO[] nondeliveredorders;

	public ImportedNonDeliveredOrderDTO[] getNondeliveredorders() {
		return nondeliveredorders;
	}

	public void setNondeliveredorders(ImportedNonDeliveredOrderDTO[] nondeliveredorders) {
		this.nondeliveredorders = nondeliveredorders;
	}
		
}
