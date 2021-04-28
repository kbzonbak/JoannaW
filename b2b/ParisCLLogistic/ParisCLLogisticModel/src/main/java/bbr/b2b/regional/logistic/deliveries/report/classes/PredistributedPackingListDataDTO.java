package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class PredistributedPackingListDataDTO implements Serializable {

	private Long orderid;
	private Long ordernumber;
	private String distributionordernumber;
	private Integer sequence;
	private Long itemid;
	private String iteminternalcode;
	private String vendoritemcode;
	private Integer innerpack;
	private Long locationid;
	private String locationcode;
	private String locationshortname;
	private Long vendorid;
	private String vendorcode;
	private String vendorname;
	private String logisticcode;
	private Long flowtypeid;
	private String flowtypename;
	private Double units;
	private String containernumber;
	private String lpncode;
	private Long taxdocumentnumber;
		
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getDistributionordernumber() {
		return distributionordernumber;
	}
	public void setDistributionordernumber(String distributionordernumber) {
		this.distributionordernumber = distributionordernumber;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public String getIteminternalcode() {
		return iteminternalcode;
	}
	public void setIteminternalcode(String iteminternalcode) {
		this.iteminternalcode = iteminternalcode;
	}
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}
	public Integer getInnerpack() {
		return innerpack;
	}
	public void setInnerpack(Integer innerpack) {
		this.innerpack = innerpack;
	}
	public Long getLocationid() {
		return locationid;
	}
	public void setLocationid(Long locationid) {
		this.locationid = locationid;
	}
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public String getLocationshortname() {
		return locationshortname;
	}
	public void setLocationshortname(String locationshortname) {
		this.locationshortname = locationshortname;
	}
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public String getLogisticcode() {
		return logisticcode;
	}
	public void setLogisticcode(String logisticcode) {
		this.logisticcode = logisticcode;
	}
	public Long getFlowtypeid() {
		return flowtypeid;
	}
	public void setFlowtypeid(Long flowtypeid) {
		this.flowtypeid = flowtypeid;
	}
	public String getFlowtypename() {
		return flowtypename;
	}
	public void setFlowtypename(String flowtypename) {
		this.flowtypename = flowtypename;
	}
	public Double getUnits() {
		return units;
	}
	public void setUnits(Double units) {
		this.units = units;
	}
	public String getContainernumber() {
		return containernumber;
	}
	public void setContainernumber(String containernumber) {
		this.containernumber = containernumber;
	}
	public String getLpncode() {
		return lpncode;
	}
	public void setLpncode(String lpncode) {
		this.lpncode = lpncode;
	}
	public Long getTaxdocumentnumber() {
		return taxdocumentnumber;
	}
	public void setTaxdocumentnumber(Long taxdocumentnumber) {
		this.taxdocumentnumber = taxdocumentnumber;
	}
		
}
