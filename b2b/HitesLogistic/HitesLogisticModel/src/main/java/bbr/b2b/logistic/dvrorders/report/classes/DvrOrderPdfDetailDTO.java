package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class DvrOrderPdfDetailDTO implements Serializable {

	private Long number;
	private Integer position;
	private String itemsku;
	private String itemname;
	private Double totalunits;
	private Double finalcost;
	private Double totaldiscounts;
	private Double totalneed;
	
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public Double getTotalunits() {
		return totalunits;
	}
	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}
	public Double getFinalcost() {
		return finalcost;
	}
	public void setFinalcost(Double finalcost) {
		this.finalcost = finalcost;
	}
	public Double getTotaldiscounts() {
		return totaldiscounts;
	}
	public void setTotaldiscounts(Double totaldiscounts) {
		this.totaldiscounts = totaldiscounts;
	}
	public Double getTotalneed() {
		return totalneed;
	}
	public void setTotalneed(Double totalneed) {
		this.totalneed = totalneed;
	}
}
