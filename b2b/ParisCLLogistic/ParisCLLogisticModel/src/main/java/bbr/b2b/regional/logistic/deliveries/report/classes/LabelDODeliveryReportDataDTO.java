package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class LabelDODeliveryReportDataDTO implements Serializable {

	private Long dlnumber;
	private Long ocnumber;
	private String departmentcode;
	private String barcode;
	private String itemsku;
	private String vendoritemcode;
	private String itemdesc;
	private String itemcolor;
	private String itemdimension;
	private String itemsize;
	private Double availableunits;
	private Double listprice;
	private Double finalprice;
	
	public Long getDlnumber() {
		return dlnumber;
	}
	public void setDlnumber(Long dlnumber) {
		this.dlnumber = dlnumber;
	}
	public Long getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}
	public String getDepartmentcode() {
		return departmentcode;
	}
	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
	public String getItemdesc() {
		return itemdesc;
	}
	public void setItemdesc(String itemdesc) {
		this.itemdesc = itemdesc;
	}
	public String getItemcolor() {
		return itemcolor;
	}
	public void setItemcolor(String itemcolor) {
		this.itemcolor = itemcolor;
	}
	public String getItemdimension() {
		return itemdimension;
	}
	public void setItemdimension(String itemdimension) {
		this.itemdimension = itemdimension;
	}
	public String getItemsize() {
		return itemsize;
	}
	public void setItemsize(String itemsize) {
		this.itemsize = itemsize;
	}
	public Double getAvailableunits() {
		return availableunits;
	}
	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}
	public Double getListprice() {
		return listprice;
	}
	public void setListprice(Double listprice) {
		this.listprice = listprice;
	}
	public Double getFinalprice() {
		return finalprice;
	}
	public void setFinalprice(Double finalprice) {
		this.finalprice = finalprice;
	}
}
