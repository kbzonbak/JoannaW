package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class DOVirtualReceptionDetailDataDTO implements Serializable{

	private String iteminternalcode;
	private String itemvendorcode;
	private String itemname;
	private Double itemavailableunits;
	private Double itemreceivedunits;
	
	public String getIteminternalcode() {
		return iteminternalcode;
	}
	public void setIteminternalcode(String iteminternalcode) {
		this.iteminternalcode = iteminternalcode;
	}
	public String getItemvendorcode() {
		return itemvendorcode;
	}
	public void setItemvendorcode(String itemvendorcode) {
		this.itemvendorcode = itemvendorcode;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public Double getItemavailableunits() {
		return itemavailableunits;
	}
	public void setItemavailableunits(Double itemavailableunits) {
		this.itemavailableunits = itemavailableunits;
	}
	public Double getItemreceivedunits() {
		return itemreceivedunits;
	}
	public void setItemreceivedunits(Double itemreceivedunits) {
		this.itemreceivedunits = itemreceivedunits;
	}
	
}
