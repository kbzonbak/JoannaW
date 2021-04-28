package bbr.b2b.regional.logistic.kpi.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevCDDetail;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

public class KPIvevCDDetail implements IKPIvevCDDetail{

	private Long id;
	private Long ordernumber;
	private String orderrequestnumber;
	private String currentorderstate;
	private Date currentorderstatedate;
	private Date fpi;
	private Date sendingdate;
	private Integer deliverydelayeddays;
	private Integer delayeddays;
	private String iteminternalcode;
	private String vendoritemcode;
	private String itemname;
	private Double itemfinalcost;
	private Double detailunits;
	private Double detailtodeliveryunits;
	private Double detailreceivedunits;
	private Double detailpendingunits;
	private String finallocationcode;
	private String finallocationname;
	private Integer finefactordays;
	private Double finefactor;
	private Vendor vendor;
	private Department department;
	private Location salestore;
	private KPIvevCDType kpivevcdtype;
	private KPIvevCD kpivevcd;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getCurrentorderstate() {
		return currentorderstate;
	}
	public void setCurrentorderstate(String currentorderstate) {
		this.currentorderstate = currentorderstate;
	}
	public Date getCurrentorderstatedate() {
		return currentorderstatedate;
	}
	public void setCurrentorderstatedate(Date currentorderstatedate) {
		this.currentorderstatedate = currentorderstatedate;
	}
	public Date getFpi() {
		return fpi;
	}
	public void setFpi(Date fpi) {
		this.fpi = fpi;
	}
	public Date getSendingdate() {
		return sendingdate;
	}
	public void setSendingdate(Date sendingdate) {
		this.sendingdate = sendingdate;
	}
	public Integer getDeliverydelayeddays() {
		return deliverydelayeddays;
	}
	public void setDeliverydelayeddays(Integer deliverydelayeddays) {
		this.deliverydelayeddays = deliverydelayeddays;
	}
	public Integer getDelayeddays() {
		return delayeddays;
	}
	public void setDelayeddays(Integer delayeddays) {
		this.delayeddays = delayeddays;
	}
	public String getIteminternalcode() {
		return iteminternalcode;
	}
	public void setIteminternalcode(String iteminternalcode) {
		this.iteminternalcode = iteminternalcode;
	}
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
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
	public Double getDetailunits() {
		return detailunits;
	}
	public void setDetailunits(Double detailunits) {
		this.detailunits = detailunits;
	}
	public Double getDetailtodeliveryunits() {
		return detailtodeliveryunits;
	}
	public void setDetailtodeliveryunits(Double detailtodeliveryunits) {
		this.detailtodeliveryunits = detailtodeliveryunits;
	}
	public Double getDetailreceivedunits() {
		return detailreceivedunits;
	}
	public void setDetailreceivedunits(Double detailreceivedunits) {
		this.detailreceivedunits = detailreceivedunits;
	}
	public Double getDetailpendingunits() {
		return detailpendingunits;
	}
	public void setDetailpendingunits(Double detailpendingunits) {
		this.detailpendingunits = detailpendingunits;
	}
	public String getFinallocationcode() {
		return finallocationcode;
	}
	public void setFinallocationcode(String finallocationcode) {
		this.finallocationcode = finallocationcode;
	}
	public String getFinallocationname() {
		return finallocationname;
	}
	public void setFinallocationname(String finallocationname) {
		this.finallocationname = finallocationname;
	}
	public Integer getFinefactordays() {
		return finefactordays;
	}
	public void setFinefactordays(Integer finefactordays) {
		this.finefactordays = finefactordays;
	}
	public Double getFinefactor() {
		return finefactor;
	}
	public void setFinefactor(Double finefactor) {
		this.finefactor = finefactor;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Location getSalestore() {
		return salestore;
	}
	public void setSalestore(Location salestore) {
		this.salestore = salestore;
	}
	public KPIvevCDType getKpivevcdtype() {
		return kpivevcdtype;
	}
	public void setKpivevcdtype(KPIvevCDType kpivevcdtype) {
		this.kpivevcdtype = kpivevcdtype;
	}
	public KPIvevCD getKpivevcd() {
		return kpivevcd;
	}
	public void setKpivevcd(KPIvevCD kpivevcd) {
		this.kpivevcd = kpivevcd;
	}
}
