package bbr.b2b.regional.logistic.couriers.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.couriers.data.interfaces.IChileExpressTag;

public class ChileExpressTag extends CourierTag implements IChileExpressTag {
	
	private Long id;
	private Integer parentnumber;
	private byte[] labelimagedata;
	private String epllabelxml;
	private String itemcomment;
	private String servicecomment;
	private String addresseename;
	private String guidenumber;
	private String coveragecomment;
	private String address;
	private String regioncode;
	private String customeradditionaldata;
	private String bulkweight;
	private String bulkheight;
	private String bulkwidth;
	private String bulklengh;
	private String labelbarcode;
	private String labeladditionalreference;
	private String itemdata;
	private String itemshortcomment;
	private Date printoutdate;
	private String bulknumber;
	private String destinationdc;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getParentnumber() {
		return parentnumber;
	}
	public void setParentnumber(Integer parentnumber) {
		this.parentnumber = parentnumber;
	}
	public byte[] getLabelimagedata() {
		return labelimagedata;
	}
	public void setLabelimagedata(byte[] labelimagedata) {
		this.labelimagedata = labelimagedata;
	}
	public String getEpllabelxml() {
		return epllabelxml;
	}
	public void setEpllabelxml(String epllabelxml) {
		this.epllabelxml = epllabelxml;
	}
	public String getItemcomment() {
		return itemcomment;
	}
	public void setItemcomment(String itemcomment) {
		this.itemcomment = itemcomment;
	}
	public String getServicecomment() {
		return servicecomment;
	}
	public void setServicecomment(String servicecomment) {
		this.servicecomment = servicecomment;
	}
	public String getAddresseename() {
		return addresseename;
	}
	public void setAddresseename(String addresseename) {
		this.addresseename = addresseename;
	}
	public String getGuidenumber() {
		return guidenumber;
	}
	public void setGuidenumber(String guidenumber) {
		this.guidenumber = guidenumber;
	}
	public String getCoveragecomment() {
		return coveragecomment;
	}
	public void setCoveragecomment(String coveragecomment) {
		this.coveragecomment = coveragecomment;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRegioncode() {
		return regioncode;
	}
	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}
	public String getCustomeradditionaldata() {
		return customeradditionaldata;
	}
	public void setCustomeradditionaldata(String customeradditionaldata) {
		this.customeradditionaldata = customeradditionaldata;
	}
	public String getBulkweight() {
		return bulkweight;
	}
	public void setBulkweight(String bulkweight) {
		this.bulkweight = bulkweight;
	}
	public String getBulkheight() {
		return bulkheight;
	}
	public void setBulkheight(String bulkheight) {
		this.bulkheight = bulkheight;
	}
	public String getBulkwidth() {
		return bulkwidth;
	}
	public void setBulkwidth(String bulkwidth) {
		this.bulkwidth = bulkwidth;
	}
	public String getBulklengh() {
		return bulklengh;
	}
	public void setBulklengh(String bulklengh) {
		this.bulklengh = bulklengh;
	}
	public String getLabelbarcode() {
		return labelbarcode;
	}
	public void setLabelbarcode(String labelbarcode) {
		this.labelbarcode = labelbarcode;
	}
	public String getLabeladditionalreference() {
		return labeladditionalreference;
	}
	public void setLabeladditionalreference(String labeladditionalreference) {
		this.labeladditionalreference = labeladditionalreference;
	}
	public String getItemdata() {
		return itemdata;
	}
	public void setItemdata(String itemdata) {
		this.itemdata = itemdata;
	}
	public String getItemshortcomment() {
		return itemshortcomment;
	}
	public void setItemshortcomment(String itemshortcomment) {
		this.itemshortcomment = itemshortcomment;
	}
	public Date getPrintoutdate() {
		return printoutdate;
	}
	public void setPrintoutdate(Date printoutdate) {
		this.printoutdate = printoutdate;
	}
	public String getBulknumber() {
		return bulknumber;
	}
	public void setBulknumber(String bulknumber) {
		this.bulknumber = bulknumber;
	}
	public String getDestinationdc() {
		return destinationdc;
	}
	public void setDestinationdc(String destinationdc) {
		this.destinationdc = destinationdc;
	}
	
}
