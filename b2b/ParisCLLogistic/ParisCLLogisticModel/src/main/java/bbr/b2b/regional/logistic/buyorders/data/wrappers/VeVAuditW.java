package bbr.b2b.regional.logistic.buyorders.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IVeVAudit;

public class VeVAuditW extends ElementDTO implements IVeVAudit{

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
	private Long vendorid;
	private Long vevupdatetypeid;
	
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
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public Long getVevupdatetypeid() {
		return vevupdatetypeid;
	}
	public void setVevupdatetypeid(Long vevupdatetypeid) {
		this.vevupdatetypeid = vevupdatetypeid;
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
