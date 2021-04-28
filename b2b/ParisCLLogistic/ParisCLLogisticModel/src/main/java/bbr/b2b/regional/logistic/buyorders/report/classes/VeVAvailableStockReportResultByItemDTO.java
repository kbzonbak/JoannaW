package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VeVAvailableStockReportResultByItemDTO extends BaseResultDTO {

	private String sku = null;

	private String detail = null;
	
	private String state = null;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public VeVAvailableStockReportResultByItemDTO(String sku, String detail, String state) {
		super();
		this.sku = sku;
		this.detail = detail;
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
