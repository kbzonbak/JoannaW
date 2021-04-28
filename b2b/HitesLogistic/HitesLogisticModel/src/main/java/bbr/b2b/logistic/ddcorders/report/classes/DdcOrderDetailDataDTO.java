package bbr.b2b.logistic.ddcorders.report.classes;

import java.io.Serializable;

public class DdcOrderDetailDataDTO implements Serializable {

	private Integer position;
	private Long itemid;
	private String itemsku;
	private String itemname;
	private String vendoritemcode;
	private String itemsize;
	private String itemcolor;
	private String itemsection;
	private Double basecost;
	private Double finalcost;
	private Double needunits;
	private Double totalneed;
	
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
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
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}
	public String getItemsize() {
		return itemsize;
	}
	public void setItemsize(String itemsize) {
		this.itemsize = itemsize;
	}
	public String getItemcolor() {
		return itemcolor;
	}
	public void setItemcolor(String itemcolor) {
		this.itemcolor = itemcolor;
	}
	public String getItemsection() {
		return itemsection;
	}
	public void setItemsection(String itemsection) {
		this.itemsection = itemsection;
	}
	public Double getBasecost() {
		return basecost;
	}
	public void setBasecost(Double basecost) {
		this.basecost = basecost;
	}
	public Double getFinalcost() {
		return finalcost;
	}
	public void setFinalcost(Double finalcost) {
		this.finalcost = finalcost;
	}
	public Double getNeedunits() {
		return needunits;
	}
	public void setNeedunits(Double needunits) {
		this.needunits = needunits;
	}
	public Double getTotalneed() {
		return totalneed;
	}
	public void setTotalneed(Double totalneed) {
		this.totalneed = totalneed;
	}

}
