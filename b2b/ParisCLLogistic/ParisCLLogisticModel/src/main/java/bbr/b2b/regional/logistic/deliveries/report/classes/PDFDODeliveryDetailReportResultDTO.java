package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class PDFDODeliveryDetailReportResultDTO extends BaseResultDTO {

	PDFDODeliveryDetailReportDataDTO[] delivertDeliveryDetailReportDataDTO = null;

	PDFDODeliveryReportDataDTO deliveryReportDataDTO = null;

	public PDFDODeliveryDetailReportDataDTO[] getDelivertDeliveryDetailReportDataDTO() {
		return delivertDeliveryDetailReportDataDTO;
	}

	public void setDelivertDeliveryDetailReportDataDTO(PDFDODeliveryDetailReportDataDTO[] delivertDeliveryDetailReportDataDTO) {
		this.delivertDeliveryDetailReportDataDTO = delivertDeliveryDetailReportDataDTO;
	}

	public PDFDODeliveryReportDataDTO getDeliveryReportDataDTO() {
		return deliveryReportDataDTO;
	}

	public void setDeliveryReportDataDTO(PDFDODeliveryReportDataDTO deliveryReportDataDTO) {
		this.deliveryReportDataDTO = deliveryReportDataDTO;
	}

}
