package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class PDFVeVPDOrderDetailReportDataDTO implements Serializable {

	private String itemsku;

	private String itemdescription;

	private Double totalneed;
	
	public PDFVeVPDOrderDetailReportDataDTO() {
	}
	
	public PDFVeVPDOrderDetailReportDataDTO(String itemsku, String itemdescription, Double totalneed) {
		super();
		this.itemsku = itemsku;
		this.itemdescription = itemdescription;
		this.totalneed = totalneed;
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

	public Double getTotalneed() {
		return totalneed;
	}

	public void setTotalneed(Double totalneed) {
		this.totalneed = totalneed;
	}

}
