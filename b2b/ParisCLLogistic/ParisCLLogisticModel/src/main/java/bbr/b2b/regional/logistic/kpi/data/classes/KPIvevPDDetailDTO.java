package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;
import java.util.Date;

public class KPIvevPDDetailDTO implements Serializable{

	private Long id;
	private Long ordernumber;
	private String orderrequestnumber;
	private String currentorderstate;
	private Date deliverycurrentdate;
	private Date fcm;
	private int delayeddays;
	private String iteminternalcode;
	private String vendoritemcode;
	private String itemname;
	private Double detailunits;
	private Double detailtodeliveryunits;
	private Double detailreceivedunits;
	private Double detailpendingunits;
	private String finallocationcode;
	private String finallocationname;
	private Long vendorid;
	private Long departmentid;
	private Long kpivevpdtypeid;
	private Long kpivevpdid;
	
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
	public Date getDeliverycurrentdate() {
		return deliverycurrentdate;
	}
	public void setDeliverycurrentdate(Date deliverycurrentdate) {
		this.deliverycurrentdate = deliverycurrentdate;
	}
	public Date getFcm() {
		return fcm;
	}
	public void setFcm(Date fcm) {
		this.fcm = fcm;
	}
	public int getDelayeddays() {
		return delayeddays;
	}
	public void setDelayeddays(int delayeddays) {
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
	
}