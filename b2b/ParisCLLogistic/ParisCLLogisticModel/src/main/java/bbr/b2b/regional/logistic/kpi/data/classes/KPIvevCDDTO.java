package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;
import java.util.Date;

public class KPIvevCDDTO implements Serializable{

	private Long vendorid;
	private Long departmentid;
	private Long salestoreid;
	private Date fpi;
	private Integer totalreceivedconformed;
	private Integer totalreceiveddelayed;
	private Integer totalproviderdelayed;
	private Integer creditnotes;
	
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
			
}
