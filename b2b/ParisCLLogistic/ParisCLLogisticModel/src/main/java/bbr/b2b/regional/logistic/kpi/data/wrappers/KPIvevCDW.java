package bbr.b2b.regional.logistic.kpi.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevCD;

public class KPIvevCDW extends ElementDTO implements IKPIvevCD{

	private Date fpi;
	private Integer totalreceivedconformed 	= 0;
	private Integer totalreceiveddelayed	= 0;
	private Integer totalproviderdelayed	= 0;
	private Integer creditnotes				= 0;
	private Date executiondate;
	private Long vendorid;
	private Long departmentid;
	private Long salestoreid;
	
	public Date getFpi() {
		return fpi;
	}
	public void setFpi(Date fpi) {
		this.fpi = fpi;
	}
	public Integer getTotalreceivedconformed() {
		return totalreceivedconformed;
	}
	public void setTotalreceivedconformed(Integer totalreceivedconformed) {
		this.totalreceivedconformed = totalreceivedconformed;
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
	public Integer getCreditnotes() {
		return creditnotes;
	}
	public void setCreditnotes(Integer creditnotes) {
		this.creditnotes = creditnotes;
	}
	public Date getExecutiondate() {
		return executiondate;
	}
	public void setExecutiondate(Date executiondate) {
		this.executiondate = executiondate;
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
	
}
