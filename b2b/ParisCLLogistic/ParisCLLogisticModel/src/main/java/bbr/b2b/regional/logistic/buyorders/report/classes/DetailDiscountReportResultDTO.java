package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DetailDiscountReportResultDTO extends BaseResultDTO{

	private String itemdescription;
	private String itemsku;
	private String vendoritemcode;
	private double listcost;
	private double finalcost;
	DetailDiscountReportDataDTO[] detaildiscounts = null;
	
	public String getItemdescription() {
		return itemdescription;
	}
	public void setItemdescription(String itemdescription) {
		this.itemdescription = itemdescription;
	}
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}
	public double getListcost() {
		return listcost;
	}
	public void setListcost(double listcost) {
		this.listcost = listcost;
	}
	public double getFinalcost() {
		return finalcost;
	}
	public void setFinalcost(double finalcost) {
		this.finalcost = finalcost;
	}
	public DetailDiscountReportDataDTO[] getDetaildiscounts() {
		return detaildiscounts;
	}
	public void setDetaildiscounts(DetailDiscountReportDataDTO[] detaildiscounts) {
		this.detaildiscounts = detaildiscounts;
	}
	
}
