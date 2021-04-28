package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVUpdateAuditReportDataDTO implements Serializable{

	private Long auditid;
	private String updatedate;
	private String username;
	private String usertype;
	private String userenterprise;
	private Long vendorid;
	private String vendorrut;
	private String vendorname;
	private Long updatetypeid;
	private String updatetype;	
	
	
	public String getVendorrut() {
		return vendorrut;
	}
	public void setVendorrut(String vendorrut) {
		this.vendorrut = vendorrut;
	}
	
	public Long getAuditid() {
		return auditid;
	}
	public void setAuditid(Long auditid) {
		this.auditid = auditid;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getUserenterprise() {
		return userenterprise;
	}
	public void setUserenterprise(String userenterprise) {
		this.userenterprise = userenterprise;
	}
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public Long getUpdatetypeid() {
		return updatetypeid;
	}
	public void setUpdatetypeid(Long updatetypeid) {
		this.updatetypeid = updatetypeid;
	}
	public String getUpdatetype() {
		return updatetype;
	}
	public void setUpdatetype(String updatetype) {
		this.updatetype = updatetype;
	}
	
}
