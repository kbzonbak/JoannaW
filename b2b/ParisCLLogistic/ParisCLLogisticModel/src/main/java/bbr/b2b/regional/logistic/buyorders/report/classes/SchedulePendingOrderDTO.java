package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;
import java.util.Date;

public class SchedulePendingOrderDTO implements Serializable {

	private Long ordernumber;
	private Date emitted;
	private Date valid;
	private Date expiration;	
	private String vendorcode;
	private String vendorname;
	private String departmentcode;
	private String departmentname;
	private String orderstatetypename;
	private Date orderstatetypedate;
	private String ordertypename;
	private String deliverylocationcode;
	private String deliverylocationname;
	private String destinationlocationcode;
	private String destinationlocationname;
	private String iteminternalcode;
	private String itembarcode;
	private String itemname;
	private String itemcolor;
	private String itemdimension;
	private String itemsize;
	private Integer iteminnerpack;
	private String vendoritemcode;
	private String flowtypename;
	private Double units;
	private Double receivedunits;
	private Double todeliveryunits;
	private Double pendingunits;
	
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public Date getEmitted() {
		return emitted;
	}
	public void setEmitted(Date emitted) {
		this.emitted = emitted;
	}
	public Date getValid() {
		return valid;
	}
	public void setValid(Date valid) {
		this.valid = valid;
	}
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public String getDepartmentcode() {
		return departmentcode;
	}
	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public String getOrderstatetypename() {
		return orderstatetypename;
	}
	public void setOrderstatetypename(String orderstatetypename) {
		this.orderstatetypename = orderstatetypename;
	}
	public Date getOrderstatetypedate() {
		return orderstatetypedate;
	}
	public void setOrderstatetypedate(Date orderstatetypedate) {
		this.orderstatetypedate = orderstatetypedate;
	}
	public String getOrdertypename() {
		return ordertypename;
	}
	public void setOrdertypename(String ordertypename) {
		this.ordertypename = ordertypename;
	}
	public String getDeliverylocationcode() {
		return deliverylocationcode;
	}
	public void setDeliverylocationcode(String deliverylocationcode) {
		this.deliverylocationcode = deliverylocationcode;
	}
	public String getDeliverylocationname() {
		return deliverylocationname;
	}
	public void setDeliverylocationname(String deliverylocationname) {
		this.deliverylocationname = deliverylocationname;
	}
	public String getDestinationlocationcode() {
		return destinationlocationcode;
	}
	public void setDestinationlocationcode(String destinationlocationcode) {
		this.destinationlocationcode = destinationlocationcode;
	}
	public String getDestinationlocationname() {
		return destinationlocationname;
	}
	public void setDestinationlocationname(String destinationlocationname) {
		this.destinationlocationname = destinationlocationname;
	}
	public String getIteminternalcode() {
		return iteminternalcode;
	}
	public void setIteminternalcode(String iteminternalcode) {
		this.iteminternalcode = iteminternalcode;
	}
	public String getItembarcode() {
		return itembarcode;
	}
	public void setItembarcode(String itembarcode) {
		this.itembarcode = itembarcode;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
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
	public Integer getIteminnerpack() {
		return iteminnerpack;
	}
	public void setIteminnerpack(Integer iteminnerpack) {
		this.iteminnerpack = iteminnerpack;
	}
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}
	public String getFlowtypename() {
		return flowtypename;
	}
	public void setFlowtypename(String flowtypename) {
		this.flowtypename = flowtypename;
	}
	public Double getUnits() {
		return units;
	}
	public void setUnits(Double units) {
		this.units = units;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Double getTodeliveryunits() {
		return todeliveryunits;
	}
	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}
	
}
