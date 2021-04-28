package bbr.b2b.regional.logistic.buyorders.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.buyorders.data.interfaces.IVeVAudit;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

public class VeVAudit implements IVeVAudit{

	private Long id;
	private Date when;
	private String username;
	private String usertype;
	private String userenterprisecode;
	private String userenterprisename;
	private String interfacecontent;
	private String item;
	private String itemvalue;
	private String initialdata;
	private String finaldata;
	private Vendor vendor;
	private VeVUpdateType vevupdatetype;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
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
	public String getUserenterprisecode() {
		return userenterprisecode;
	}
	public void setUserenterprisecode(String userenterprisecode) {
		this.userenterprisecode = userenterprisecode;
	}
	public String getUserenterprisename() {
		return userenterprisename;
	}
	public void setUserenterprisename(String userenterprisename) {
		this.userenterprisename = userenterprisename;
	}
	public String getInterfacecontent() {
		return interfacecontent;
	}
	public void setInterfacecontent(String interfacecontent) {
		this.interfacecontent = interfacecontent;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public VeVUpdateType getVevupdatetype() {
		return vevupdatetype;
	}
	public void setVevupdatetype(VeVUpdateType vevupdatetype) {
		this.vevupdatetype = vevupdatetype;
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
	
}
