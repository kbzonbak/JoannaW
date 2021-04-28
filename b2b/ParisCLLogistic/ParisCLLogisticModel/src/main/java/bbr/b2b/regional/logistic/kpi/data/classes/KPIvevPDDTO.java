package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;
import java.util.Date;

public class KPIvevPDDTO implements Serializable{

	private Long vendorid;
	private Long departmentid;
	private Long salestoreid;
	private Date fcm;
	private Integer totaldelivered;
	private Integer totalreceiveddelayed;
	private Integer totalproviderdelayed;
	private Integer totaldelivering;
	private Integer creditnotes;
	private Integer inconsistencies;
	private Integer customerresponsabilities;
	
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
	public Date getFcm() {
		return fcm;
	}
	public void setFcm(Date fcm) {
		this.fcm = fcm;
	}
	public Integer getTotaldelivered() {
		return totaldelivered;
	}
	public void setTotaldelivered(Integer totaldelivered) {
		this.totaldelivered = totaldelivered;
	}
	public Integer getTotalreceiveddelayed() {
		return totalreceiveddelayed;
	}
	public void setTotalreceiveddelayed(Integer totalreceiveddelayed) {
		this.totalreceiveddelayed = totalreceiveddelayed;
	}
	public Integer getTotalproviderdelayed() {
		return totalproviderdelayed;
	}
	public void setTotalproviderdelayed(Integer totalproviderdelayed) {
		this.totalproviderdelayed = totalproviderdelayed;
	}
	public Integer getTotaldelivering() {
		return totaldelivering;
	}
	public void setTotaldelivering(Integer totaldelivering) {
		this.totaldelivering = totaldelivering;
	}
	public Integer getCreditnotes() {
		return creditnotes;
	}
	public void setCreditnotes(Integer creditnotes) {
		this.creditnotes = creditnotes;
	}
	public Integer getInconsistencies() {
		return inconsistencies;
	}
	public void setInconsistencies(Integer inconsistencies) {
		this.inconsistencies = inconsistencies;
	}
	public Integer getCustomerresponsabilities() {
		return customerresponsabilities;
	}
	public void setCustomerresponsabilities(Integer customerresponsabilities) {
		this.customerresponsabilities = customerresponsabilities;
	}
			
}