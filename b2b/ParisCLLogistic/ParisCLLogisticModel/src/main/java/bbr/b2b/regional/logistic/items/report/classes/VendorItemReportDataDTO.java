package bbr.b2b.regional.logistic.items.report.classes;

import java.io.Serializable;

public class VendorItemReportDataDTO implements Serializable{

	private String itemsku;
	private String description;
	private String itemclass;
	private String vendoritemcode;
	private String flowtype;
	private String size;
	private String color;
	private String dimension;
	private int casepack;
	private int innerpack;
	private String barcode;
	private String ean1;
	private String ean2;
	private String ean3;
	
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getItemclass() {
		return itemclass;
	}
	public void setItemclass(String itemclass) {
		this.itemclass = itemclass;
	}
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}
	public String getFlowtype() {
		return flowtype;
	}
	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public int getCasepack() {
		return casepack;
	}
	public void setCasepack(int casepack) {
		this.casepack = casepack;
	}
	public int getInnerpack() {
		return innerpack;
	}
	public void setInnerpack(int innerpack) {
		this.innerpack = innerpack;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getEan1() {
		return ean1;
	}
	public void setEan1(String ean1) {
		this.ean1 = ean1;
	}
	public String getEan2() {
		return ean2;
	}
	public void setEan2(String ean2) {
		this.ean2 = ean2;
	}
	public String getEan3() {
		return ean3;
	}
	public void setEan3(String ean3) {
		this.ean3 = ean3;
	}
	
	
}
