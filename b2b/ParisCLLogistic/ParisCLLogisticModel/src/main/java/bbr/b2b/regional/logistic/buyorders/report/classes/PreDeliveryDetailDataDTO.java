package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class PreDeliveryDetailDataDTO implements Serializable {

	private Long orderid;     
	private Long ordernumber;
	private Long itemid;
	private Long locationid;
	private String itemsku;   
	private Long flowtypeid;
	private String flowtype;
	private String itemdesc;	
	private String locationcode;
	private String locationdesc;	  
	private Double totalunits;
	private Double todeliveryunits;
	private Long curve;
	private Long atcid;
	private String atccode;
	private Long originalvendorid;
	private String originalvendorcode;
	private String originalvendorname;
	private Integer rownumber;
	
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public Long getLocationid() {
		return locationid;
	}
	public void setLocationid(Long locationid) {
		this.locationid = locationid;
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
	public String getFlowtype() {
		return flowtype;
	}
	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}
	public String getItemdesc() {
		return itemdesc;
	}
	public void setItemdesc(String itemdesc) {
		this.itemdesc = itemdesc;
	}
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public String getLocationdesc() {
		return locationdesc;
	}
	public void setLocationdesc(String locationdesc) {
		this.locationdesc = locationdesc;
	}
	public Double getTotalunits() {
		return totalunits;
	}
	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}
	public Double getTodeliveryunits() {
		return todeliveryunits;
	}
	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}
	public Long getCurve() {
		return curve;
	}
	public void setCurve(Long curve) {
		this.curve = curve;
	}
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
	public Long getOriginalvendorid() {
		return originalvendorid;
	}
	public void setOriginalvendorid(Long originalvendorid) {
		this.originalvendorid = originalvendorid;
	}
	public String getOriginalvendorcode() {
		return originalvendorcode;
	}
	public void setOriginalvendorcode(String originalvendorcode) {
		this.originalvendorcode = originalvendorcode;
	}
	public String getOriginalvendorname() {
		return originalvendorname;
	}
	public void setOriginalvendorname(String originalvendorname) {
		this.originalvendorname = originalvendorname;
	}
	public Integer getRownumber() {
		return rownumber;
	}
	public void setRownumber(Integer rownumber) {
		this.rownumber = rownumber;
	}	
}
