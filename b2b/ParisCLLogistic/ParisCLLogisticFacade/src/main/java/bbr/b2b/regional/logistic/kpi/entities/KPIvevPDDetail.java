package bbr.b2b.regional.logistic.kpi.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevPDDetail;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

public class KPIvevPDDetail implements IKPIvevPDDetail{

	private Long id;
	private Long ordernumber;
	private String orderrequestnumber;
	private String currentorderstate;
	private String currentdeliverystate;
	private Date deliverycurrentdate;
	private Date courierpendingdate;
	private Date fcm;
	private Date frep;
	private Date sendingdate;
	private Date clientrescheduledate;
	private Date vendorrescheduledate;
	private Integer deliverydelayeddays;
	private Integer courierdeliverydelayeddays;
	private Integer delayeddays;
	private String rescheduleresponsibility;
	private String iteminternalcode;
	private String vendoritemcode;
	private String itemname;
	private Double itemfinalcost;
	private Double detailunits;
	private Double detailtodeliveryunits;
	private Double detailreceivedunits;
	private Double detailpendingunits;
	private Integer finefactordays;
	private Double finefactor;
	private Vendor vendor;
	private Department department;
	private Location salestore;
	private KPIvevPDType kpivevpdtype;
	private KPIvevPD kpivevpd;
	
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
	public String getCurrentdeliverystate() {
		return currentdeliverystate;
	}
	public void setCurrentdeliverystate(String currentdeliverystate) {
		this.currentdeliverystate = currentdeliverystate;
	}
	public Date getDeliverycurrentdate() {
		return deliverycurrentdate;
	}
	public void setDeliverycurrentdate(Date deliverycurrentdate) {
		this.deliverycurrentdate = deliverycurrentdate;
	}
	public Date getCourierpendingdate() {
		return courierpendingdate;
	}
	public void setCourierpendingdate(Date courierpendingdate) {
		this.courierpendingdate = courierpendingdate;
	}
	public Date getFcm() {
		return fcm;
	}
	public void setFcm(Date fcm) {
		this.fcm = fcm;
	}
	public Date getFrep() {
		return frep;
	}
	public void setFrep(Date frep) {
		this.frep = frep;
	}
	public Date getSendingdate() {
		return sendingdate;
	}
	public void setSendingdate(Date sendingdate) {
		this.sendingdate = sendingdate;
	}
	public Date getClientrescheduledate() {
		return clientrescheduledate;
	}
	public void setClientrescheduledate(Date clientrescheduledate) {
		this.clientrescheduledate = clientrescheduledate;
	}
	public Date getVendorrescheduledate() {
		return vendorrescheduledate;
	}
	public void setVendorrescheduledate(Date vendorrescheduledate) {
		this.vendorrescheduledate = vendorrescheduledate;
	}
	public Integer getDeliverydelayeddays() {
		return deliverydelayeddays;
	}
	public void setDeliverydelayeddays(Integer deliverydelayeddays) {
		this.deliverydelayeddays = deliverydelayeddays;
	}
	public Integer getCourierdeliverydelayeddays() {
		return courierdeliverydelayeddays;
	}
	public void setCourierdeliverydelayeddays(Integer courierdeliverydelayeddays) {
		this.courierdeliverydelayeddays = courierdeliverydelayeddays;
	}
	public Integer getDelayeddays() {
		return delayeddays;
	}
	public void setDelayeddays(Integer delayeddays) {
		this.delayeddays = delayeddays;
	}
	public String getRescheduleresponsibility() {
		return rescheduleresponsibility;
	}
	public void setRescheduleresponsibility(String rescheduleresponsibility) {
		this.rescheduleresponsibility = rescheduleresponsibility;
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
	public KPIvevPDType getKpivevpdtype() {
		return kpivevpdtype;
	}
	public void setKpivevpdtype(KPIvevPDType kpivevpdtype) {
		this.kpivevpdtype = kpivevpdtype;
	}
	public KPIvevPD getKpivevpd() {
		return kpivevpd;
	}
	public void setKpivevpd(KPIvevPD kpivevpd) {
		this.kpivevpd = kpivevpd;
	}
}
