package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVAuditDetailDTO implements Serializable{

	private String username;
	private String usertype;
	private String userenterprisename;
	private String vendorname;
	private String vevaudittype;	
	private String item;
	private String itemvalue;
	private String initialdata;
	private String finaldata;
	private String date;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getItemvalue() {
		return itemvalue;
	}
	public void setItemvalue(String itemvalue) {
		this.itemvalue = itemvalue;
	}
	public String getInitialdata() {
		return initialdata;
	}
	public void setInitialdata(String initialdata) {
		this.initialdata = initialdata;
	}
	public String getFinaldata() {
		return finaldata;
	}
	public void setFinaldata(String finaldata) {
		this.finaldata = finaldata;
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
	public String getUserenterprisename() {
		return userenterprisename;
	}
	public void setUserenterprisename(String userenterprisename) {
		this.userenterprisename = userenterprisename;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public String getVevaudittype() {
		return vevaudittype;
	}
	public void setVevaudittype(String vevaudittype) {
		this.vevaudittype = vevaudittype;
	}
}
