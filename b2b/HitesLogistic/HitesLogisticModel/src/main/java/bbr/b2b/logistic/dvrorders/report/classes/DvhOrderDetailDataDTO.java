package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class DvhOrderDetailDataDTO implements Serializable {

	private Long dvrorderid;
	private Long itemid;
	private Integer position;
	private String sku;
	private String vendoritemcode;
	private String itemddescription;
	private String size;
	private String color;
	private String section;
	private Double basecost;
	private Double finalcost;
	private Double totalunits;
	private Double totalamount;

	public Long getDvrorderid() {
		return dvrorderid;
	}

	public void setDvrorderid(Long dvrorderid) {
		this.dvrorderid = dvrorderid;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getVendoritemcode() {
		return vendoritemcode;
	}

	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}

	public String getItemddescription() {
		return itemddescription;
	}

	public void setItemddescription(String itemddescription) {
		this.itemddescription = itemddescription;
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

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
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

	public Double getTotalunits() {
		return totalunits;
	}

	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}

	public Double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}

}
