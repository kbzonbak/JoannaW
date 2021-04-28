package bbr.b2b.logistic.ddcorders.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IDdcOrderDetail extends IIdentifiable {

	public Double getBasecost();
	public void setBasecost(Double basecost);
	public Double getFinalcost();
	public void setFinalcost(Double finalcost);
	public Double getNormalprice();
	public void setNormalprice(Double normalprice);
	public Double getOfferprice();
	public void setOfferprice(Double offerprice);
	public String getPackingcode();
	public void setPackingcode(String packingcode);
	public String getPackingdescription();
	public void setPackingdescription(String packingdescription);
	public String getBaseunit();
	public void setBaseunit(String baseunit);
	public Double getProductpackingrate();
	public void setProductpackingrate(Double productpackingrate);
	public String getEan();
	public void setEan(String ean);
	public String getBarcode2();
	public void setBarcode2(String barcode2);
	public String getComment();
	public void setComment(String comment);
	public Double getTolerance();
	public void setTolerance(Double tolerance);
	public LocalDateTime getItemdeliverydate();
	public void setItemdeliverydate(LocalDateTime itemdeliverydate);
	public Double getNeedunits();
	public void setNeedunits(Double needunits);
	public Double getTodeliveryunits();
	public void setTodeliveryunits(Double todeliveryunits);
	public Double getReceivedunits();
	public void setReceivedunits(Double receivedunits);
	public Double getPendingunits();
	public void setPendingunits(Double pendingunits);
	public Double getTotalneed();
	public void setTotalneed(Double totalneed);
	public Double getTotaltodelivery();
	public void setTotaltodelivery(Double totaltodelivery);
	public Double getTotalreceived();
	public void setTotalreceived(Double totalreceived);
	public Double getTotalpending();
	public void setTotalpending(Double totalpending);
}
