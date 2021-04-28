package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;
import java.util.Date;

public class KPIVendorDepartmentSaleStoreDTO implements Serializable{

	private Long vendorid;
	private Long departmentid;
	private Long salestoreid;
	private Date date;
	
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
		
}
