package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.util.List;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class PDFVeVPDOrderDetailReportResultListResultDTO extends BaseResultDTO {

	List<PDFVeVPDOrderDetailReportResultDTO> orders = null;

	public List<PDFVeVPDOrderDetailReportResultDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<PDFVeVPDOrderDetailReportResultDTO> orders) {
		this.orders = orders;
	}

}
