package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.util.List;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class PDFDODeliveryDetailReportResultListResultDTO extends BaseResultDTO {
	
	List<PDFDODeliveryDetailReportResultDTO> deliveriesDetailReportResult = null;

	public List<PDFDODeliveryDetailReportResultDTO> getDeliveriesDetailReportResult() {
		return deliveriesDetailReportResult;
	}

	public void setDeliveriesDetailReportResult(List<PDFDODeliveryDetailReportResultDTO> deliveriesDetailReportResult) {
		this.deliveriesDetailReportResult = deliveriesDetailReportResult;
	}

}
