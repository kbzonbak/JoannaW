package bbr.b2b.logistic.dvrorders.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IDvrOrderDetail extends IIdentifiable {

	public Double getBasecost();
	public Double getFinalcost();
	public String getUmbbarcode();
	public String getUmbdescription();
	public String getUmcbarcode();
	public String getUmcdescription();
	public Double getUmbxumc();
	public String getUmccode();
	public String getUmbcode();
	public Double getTotalunits();
	public Double getTodeliveryunits();
	public Double getReceivedunits();
	public Double getPendingunits();
	public Double getTotalneed();
	public Double getTotaltodelivery();
	public Double getTotalreceived();
	public Double getTotalpending();
	public Integer getTolerance();
	public String getInnerpack();
	public String getCasepack();
	public LocalDateTime getItemdeliverydate();
	public String getComment();
	public Double getNormalprice();
	public Double getOfferprice();
	public String getBarcode1();
	public String getBarcode2();
	public void setBasecost(Double basecost);
	public void setFinalcost(Double finalcost);
	public void setUmbbarcode(String umbbarcode);
	public void setUmbdescription(String umbdescription);
	public void setUmcbarcode(String umcbarcode);
	public void setUmcdescription(String umcdescription);
	public void setUmbxumc(Double umbxumc);
	public void setUmccode(String umccode);
	public void setUmbcode(String umbcode);
	public void setTotalunits(Double totalunits);
	public void setTodeliveryunits(Double todeliveryunits);
	public void setReceivedunits(Double receivedunits);
	public void setPendingunits(Double pendingunits);
	public void setTotalneed(Double totalneed);
	public void setTotaltodelivery(Double totaltodelivery);
	public void setTotalreceived(Double totalreceived);
	public void setTotalpending(Double totalpending);
	public void setTolerance(Integer tolerance);
	public void setInnerpack(String innerpack);
	public void setCasepack(String casepack);
	public void setItemdeliverydate(LocalDateTime itemdeliverydate);
	public void setComment(String comment);
	public void setNormalprice(Double normalprice);
	public void setOfferprice(Double offerprice);
	public void setBarcode1(String barcode1);
	public void setBarcode2(String barcode2);
}
