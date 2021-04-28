package bbr.b2b.regional.logistic.couriers.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IChileExpressTag extends IElement {

	public Integer getParentnumber();

	public void setParentnumber(Integer parentnumber);

	public byte[] getLabelimagedata();

	public void setLabelimagedata(byte[] labelimagedata);

	public String getEpllabelxml();

	public void setEpllabelxml(String epllabelxml);

	public String getItemcomment();

	public void setItemcomment(String itemcomment);

	public String getServicecomment();

	public void setServicecomment(String servicecomment);

	public String getAddresseename();

	public void setAddresseename(String addresseename);

	public String getGuidenumber();

	public void setGuidenumber(String guidenumber);

	public String getCoveragecomment();

	public void setCoveragecomment(String coveragecomment);

	public String getAddress();

	public void setAddress(String address);

	public String getRegioncode();

	public void setRegioncode(String regioncode);

	public String getCustomeradditionaldata();

	public void setCustomeradditionaldata(String customeradditionaldata);

	public String getBulkweight();

	public void setBulkweight(String bulkweight);

	public String getBulkheight();

	public void setBulkheight(String bulkheight);

	public String getBulkwidth();

	public void setBulkwidth(String bulkwidth);

	public String getBulklengh();

	public void setBulklengh(String bulklengh);

	public String getLabelbarcode();

	public void setLabelbarcode(String labelbarcode);

	public String getLabeladditionalreference();

	public void setLabeladditionalreference(String labeladditionalreference);

	public String getItemdata();

	public void setItemdata(String itemdata);

	public String getItemshortcomment();

	public void setItemshortcomment(String itemshortcomment);

	public Date getPrintoutdate();

	public void setPrintoutdate(Date printoutdate);

	public String getBulknumber();

	public void setBulknumber(String bulknumber);

	public String getDestinationdc();

	public void setDestinationdc(String destinationdc);
	
}
