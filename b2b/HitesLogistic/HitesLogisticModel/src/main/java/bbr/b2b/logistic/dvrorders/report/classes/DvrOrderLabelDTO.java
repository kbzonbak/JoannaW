package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class DvrOrderLabelDTO implements Serializable {

	private Long dvrorderid;
	private Long ordernumber;
	private Long itemid;
	private String itemname;
	private String detailbarcode1;
	private String verificationdigit;
	private String size;
	private String creationyear;
	private String season;
	private String department;
	private String style;
	private String color;
	private String devolution;
	private Double normalprice;
	private Double totalunits;
	private Long vendorid;
	private String vendortradename;
	private Boolean vendordomestic;
	
	public Long getDvrorderid() {
		return dvrorderid;
	}
	public void setDvrorderid(Long dvrorderid) {
		this.dvrorderid = dvrorderid;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getDetailbarcode1() {
		return detailbarcode1;
	}
	public void setDetailbarcode1(String detailbarcode1) {
		this.detailbarcode1 = detailbarcode1;
	}
	public String getVerificationdigit() {
		return verificationdigit;
	}
	public void setVerificationdigit(String verificationdigit) {
		this.verificationdigit = verificationdigit;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getCreationyear() {
		return creationyear;
	}
	public void setCreationyear(String creationyear) {
		this.creationyear = creationyear;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDevolution() {
		return devolution;
	}
	public void setDevolution(String devolution) {
		this.devolution = devolution;
	}
	public Double getNormalprice() {
		return normalprice;
	}
	public void setNormalprice(Double normalprice) {
		this.normalprice = normalprice;
	}
	public Double getTotalunits() {
		return totalunits;
	}
	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public String getVendortradename() {
		return vendortradename;
	}
	public void setVendortradename(String vendortradename) {
		this.vendortradename = vendortradename;
	}
	public Boolean getVendordomestic() {
		return vendordomestic;
	}
	public void setVendordomestic(Boolean vendordomestic) {
		this.vendordomestic = vendordomestic;
	}
	
}
