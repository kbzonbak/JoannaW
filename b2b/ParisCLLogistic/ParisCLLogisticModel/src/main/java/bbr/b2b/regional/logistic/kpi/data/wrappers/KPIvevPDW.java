package bbr.b2b.regional.logistic.kpi.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevPD;

public class KPIvevPDW extends ElementDTO implements IKPIvevPD{

	private Long vendorid;
	private Long departmentid;
	private Long salestoreid;
	private Date fcm;
	private Integer totaldelivered				= 0;
	private Integer totalreceiveddelayed		= 0;
	private Integer totalcourierreceiveddelayed	= 0;
	private Integer totalproviderdelayed		= 0;
	private Integer totalcourierdelayed			= 0;
	private Integer totaldelivering				= 0;
	private Integer totallosts					= 0;
	private Integer creditnotes					= 0;
	private Integer inconsistencies				= 0;
	private Integer customerresponsabilities	= 0;
	private Date executiondate;
	
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
	public Integer getTotalcourierreceiveddelayed() {
		return totalcourierreceiveddelayed;
	}
	public void setTotalcourierreceiveddelayed(Integer totalcourierreceiveddelayed) {
		this.totalcourierreceiveddelayed = totalcourierreceiveddelayed;
	}
	public Integer getTotalproviderdelayed() {
		return totalproviderdelayed;
	}
	public void setTotalproviderdelayed(Integer totalproviderdelayed) {
		this.totalproviderdelayed = totalproviderdelayed;
	}
	public Integer getTotalcourierdelayed() {
		return totalcourierdelayed;
	}
	public void setTotalcourierdelayed(Integer totalcourierdelayed) {
		this.totalcourierdelayed = totalcourierdelayed;
	}
	public Integer getTotaldelivering() {
		return totaldelivering;
	}
	public void setTotaldelivering(Integer totaldelivering) {
		this.totaldelivering = totaldelivering;
	}
	public Integer getTotallosts() {
		return totallosts;
	}
	public void setTotallosts(Integer totallosts) {
		this.totallosts = totallosts;
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
	public Date getExecutiondate() {
		return executiondate;
	}
	public void setExecutiondate(Date executiondate) {
		this.executiondate = executiondate;
	}
			
}
