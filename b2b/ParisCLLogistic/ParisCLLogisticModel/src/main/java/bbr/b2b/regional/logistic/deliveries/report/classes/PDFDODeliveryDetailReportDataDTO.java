package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class PDFDODeliveryDetailReportDataDTO implements Serializable {

	private String itemsku;

	private String itemdescription;

	private Integer pendingunits;

	public PDFDODeliveryDetailReportDataDTO() {
	}

	public PDFDODeliveryDetailReportDataDTO(String itemsku, String itemdescription, Integer pendingunits) {
		super();
		this.itemsku = itemsku;
		this.itemdescription = itemdescription;
		this.pendingunits = pendingunits;
	}

	public String getItemsku() {
		return itemsku;
	}

	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}

	public String getItemdescription() {
		return itemdescription;
	}

	public void setItemdescription(String itemdescription) {
		this.itemdescription = itemdescription;
	}

	public Integer getPendingunits() {
		return pendingunits;
	}

	public void setPendingunits(Integer pendingunits) {
		this.pendingunits = pendingunits;
	}

	

}
