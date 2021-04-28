package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class KPIvevOrderDetailDataDTO implements Serializable{

	private String salestorecode;
	private String salestorename;
	private String vendorcode;
	private String vendorname;
	private Long ordernumber;
	private String orderrequestnumber;
	private String departmentcode;
	private String departmentname;
	private String currentorderstatetypename;
	private String currentdeliverystatetypename;	// solo para PD
	private LocalDateTime sendingdate;
	private LocalDateTime fpi;								// solo para CD
	private LocalDateTime fcm;								// solo para PD
	private LocalDateTime frep;								// solo para PD
	private LocalDateTime currentstatetypedate;
	private Integer delayeddays;
	private String kpivevtypename;
	private String iteminternalcode;
	private String itemname;
	private Double itemfinalcost;
	private Double requestedunits;
	private Double todeliveryunits;
	private Double receivedunits;
	private Double pendingunits;
	private Double firstdayfinepercent;
	private Double nextdaysfinepercent;
	private Double firstdayfineamount;
	private Double nextdaysfineamount;
	
	public String getSalestorecode() {
		return salestorecode;
	}
	public void setSalestorecode(String salestorecode) {
		this.salestorecode = salestorecode;
	}
	public String getSalestorename() {
		return salestorename;
	}
	public void setSalestorename(String salestorename) {
		this.salestorename = salestorename;
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
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getOrderrequestnumber() {
		return orderrequestnumber;
	}
	public void setOrderrequestnumber(String orderrequestnumber) {
		this.orderrequestnumber = orderrequestnumber;
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
	public String getCurrentorderstatetypename() {
		return currentorderstatetypename;
	}
	public void setCurrentorderstatetypename(String currentorderstatetypename) {
		this.currentorderstatetypename = currentorderstatetypename;
	}
	public String getCurrentdeliverystatetypename() {
		return currentdeliverystatetypename;
	}
	public void setCurrentdeliverystatetypename(String currentdeliverystatetypename) {
		this.currentdeliverystatetypename = currentdeliverystatetypename;
	}
	public LocalDateTime getSendingdate() {
		return sendingdate;
	}
	public void setSendingdate(LocalDateTime sendingdate) {
		this.sendingdate = sendingdate;
	}
	public LocalDateTime getFpi() {
		return fpi;
	}
	public void setFpi(LocalDateTime fpi) {
		this.fpi = fpi;
	}
	public LocalDateTime getFcm() {
		return fcm;
	}
	public void setFcm(LocalDateTime fcm) {
		this.fcm = fcm;
	}
	public LocalDateTime getFrep() {
		return frep;
	}
	public void setFrep(LocalDateTime frep) {
		this.frep = frep;
	}
	public LocalDateTime getCurrentstatetypedate() {
		return currentstatetypedate;
	}
	public void setCurrentstatetypedate(LocalDateTime currentstatetypedate) {
		this.currentstatetypedate = currentstatetypedate;
	}
	public Integer getDelayeddays() {
		return delayeddays;
	}
	public void setDelayeddays(Integer delayeddays) {
		this.delayeddays = delayeddays;
	}
	public String getKpivevtypename() {
		return kpivevtypename;
	}
	public void setKpivevtypename(String kpivevtypename) {
		this.kpivevtypename = kpivevtypename;
	}
	public String getIteminternalcode() {
		return iteminternalcode;
	}
	public void setIteminternalcode(String iteminternalcode) {
		this.iteminternalcode = iteminternalcode;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public Double getItemfinalcost() {
		return itemfinalcost;
	}
	public void setItemfinalcost(Double itemfinalcost) {
		this.itemfinalcost = itemfinalcost;
	}
	public Double getRequestedunits() {
		return requestedunits;
	}
	public void setRequestedunits(Double requestedunits) {
		this.requestedunits = requestedunits;
	}
	public Double getTodeliveryunits() {
		return todeliveryunits;
	}
	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}
	public Double getFirstdayfinepercent() {
		return firstdayfinepercent;
	}
	public void setFirstdayfinepercent(Double firstdayfinepercent) {
		this.firstdayfinepercent = firstdayfinepercent;
	}
	public Double getNextdaysfinepercent() {
		return nextdaysfinepercent;
	}
	public void setNextdaysfinepercent(Double nextdaysfinepercent) {
		this.nextdaysfinepercent = nextdaysfinepercent;
	}
	public Double getFirstdayfineamount() {
		return firstdayfineamount;
	}
	public void setFirstdayfineamount(Double firstdayfineamount) {
		this.firstdayfineamount = firstdayfineamount;
	}
	public Double getNextdaysfineamount() {
		return nextdaysfineamount;
	}
	public void setNextdaysfineamount(Double nextdaysfineamount) {
		this.nextdaysfineamount = nextdaysfineamount;
	}
}