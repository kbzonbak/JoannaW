package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DODeliverySourceDataResultDTO extends BaseResultDTO {

	private DODeliverySourceDataDTO[] sourcedatadeliverys = null;
	private int totalRows;
	
	public DODeliverySourceDataDTO[] getSourcedatadeliverys() {
		return sourcedatadeliverys;
	}
	public void setSourcedatadeliverys(DODeliverySourceDataDTO[] sourcedatadeliverys) {
		this.sourcedatadeliverys = sourcedatadeliverys;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
}
