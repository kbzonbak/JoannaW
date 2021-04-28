package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class KPIvevPDDetailCalculateDTO implements Serializable{

	private Long id;
	private Long ordernumber;
	private String orderrequestnumber;
	private Long currentorderstatetypeid;
	private String currentorderstate;
	private Long currentdeliverystatetypeid;
	private String currentdeliverystate;
	private Date deliverycurrentdate;
	private LocalDateTime deliverycurrentdateldt;
	private Date courierpendingdate;
	private LocalDateTime courierpendingdateldt;
	private Date fcm;
	private LocalDateTime fcmldt;
	private Date frep;
	private LocalDateTime frepldt;
	private Date sendingdate;
	private LocalDateTime sendingdateldt;
	private Date clientrescheduledate;
	private LocalDateTime clientrescheduledateldt;
	private Date vendorrescheduledate;
	private LocalDateTime vendorrescheduledateldt;
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
	private Integer finefactordays; // d�as de atraso a considerar para el cálculo de multas	
	private Double finefactor; // obsoleto con el nuevo m�todo de cálculo
	private Long vendorid;
	private Long departmentid;
	private Long salestoreid;
	private Long kpivevpdtypeid;
	private Long kpivevpdid;
	
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
	public Long getCurrentorderstatetypeid() {
		return currentorderstatetypeid;
	}
	public void setCurrentorderstatetypeid(Long currentorderstatetypeid) {
		this.currentorderstatetypeid = currentorderstatetypeid;
	}
	public String getCurrentorderstate() {
		return currentorderstate;
	}
	public void setCurrentorderstate(String currentorderstate) {
		this.currentorderstate = currentorderstate;
	}
	public Long getCurrentdeliverystatetypeid() {
		return currentdeliverystatetypeid;
	}
	public void setCurrentdeliverystatetypeid(Long currentdeliverystatetypeid) {
		this.currentdeliverystatetypeid = currentdeliverystatetypeid;
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
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public Long getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(Long departmentid) {
		this.departmentid = departmentid;
	}
	public Long getSalestoreid() {
		return salestoreid;
	}
	public void setSalestoreid(Long salestoreid) {
		this.salestoreid = salestoreid;
	}
	public Long getKpivevpdtypeid() {
		return kpivevpdtypeid;
	}
	public void setKpivevpdtypeid(Long kpivevpdtypeid) {
		this.kpivevpdtypeid = kpivevpdtypeid;
	}
	public Long getKpivevpdid() {
		return kpivevpdid;
	}
	public void setKpivevpdid(Long kpivevpdid) {
		this.kpivevpdid = kpivevpdid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getDeliverycurrentdateldt() {
		return deliverycurrentdateldt;
	}
	public void setDeliverycurrentdateldt(LocalDateTime deliverycurrentdateldt) {
		this.deliverycurrentdateldt = deliverycurrentdateldt;
	}
	public LocalDateTime getCourierpendingdateldt() {
		return courierpendingdateldt;
	}
	public void setCourierpendingdateldt(LocalDateTime courierpendingdateldt) {
		this.courierpendingdateldt = courierpendingdateldt;
	}
	public LocalDateTime getFcmldt() {
		return fcmldt;
	}
	public void setFcmldt(LocalDateTime fcmldt) {
		this.fcmldt = fcmldt;
	}
	public LocalDateTime getFrepldt() {
		return frepldt;
	}
	public void setFrepldt(LocalDateTime frepldt) {
		this.frepldt = frepldt;
	}
	public LocalDateTime getSendingdateldt() {
		return sendingdateldt;
	}
	public void setSendingdateldt(LocalDateTime sendingdateldt) {
		this.sendingdateldt = sendingdateldt;
	}
	public LocalDateTime getClientrescheduledateldt() {
		return clientrescheduledateldt;
	}
	public void setClientrescheduledateldt(LocalDateTime clientrescheduledateldt) {
		this.clientrescheduledateldt = clientrescheduledateldt;
	}
	public LocalDateTime getVendorrescheduledateldt() {
		return vendorrescheduledateldt;
	}
	public void setVendorrescheduledateldt(LocalDateTime vendorrescheduledateldt) {
		this.vendorrescheduledateldt = vendorrescheduledateldt;
	}
	
	
}