package bbr.b2b.regional.logistic.kpi.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevPD;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

public class KPIvevPD implements IKPIvevPD{
	
	private Long id;
	private Date fcm;
	private Integer totaldelivered;
	private Integer totalreceiveddelayed;
	private Integer totalcourierreceiveddelayed;
	private Integer totalproviderdelayed;
	private Integer totalcourierdelayed;
	private Integer totaldelivering;
	private Integer totallosts;
	private Integer creditnotes;
	private Integer inconsistencies;
	private Integer customerresponsabilities;
	private Date executiondate;
	private Vendor vendor;
	private Department department;
	private Location salestore;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
		
}
