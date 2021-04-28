package bbr.b2b.regional.logistic.kpi.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevCDDetail;

public class KPIvevCDDetailW extends ElementDTO implements IKPIvevCDDetail{

	private Long ordernumber;
	private String orderrequestnumber;
	private Long currentorderstatetypeid;
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
	private Integer finefactordays; // d�as adicionales de atraso (a partir del segundo)
	private Double finefactor; // obsoleto con el nuevo m�todo de cálculo
	private Long vendorid;
	private Long departmentid;
	private Long salestoreid;
	private Long kpivevcdtypeid;
	private Long kpivevcdid;
	
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
	public Long getKpivevcdtypeid() {
		return kpivevcdtypeid;
	}
	public void setKpivevcdtypeid(Long kpivevcdtypeid) {
		this.kpivevcdtypeid = kpivevcdtypeid;
	}
	public Long getKpivevcdid() {
		return kpivevcdid;
	}
	public void setKpivevcdid(Long kpivevcdid) {
		this.kpivevcdid = kpivevcdid;
	}
}
