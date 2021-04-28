package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class ItemAtcDTO implements Serializable {

	private Long atcid;
	private String atccode;
	private Long itemid;
	private String itemsku;
	private Long flowtypeid;
	private Long curve;
	
	public Long getAtcid() {
		return atcid;
	}
	public void setAtcid(Long atcid) {
		this.atcid = atcid;
	}
	public String getAtccode() {
		return atccode;
	}
	public void setAtccode(String atccode) {
		this.atccode = atccode;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public Long getFlowtypeid() {
		return flowtypeid;
	}
	public void setFlowtypeid(Long flowtypeid) {
		this.flowtypeid = flowtypeid;
	}
	public Long getCurve() {
		return curve;
	}
	public void setCurve(Long curve) {
		this.curve = curve;
	}
		
}
