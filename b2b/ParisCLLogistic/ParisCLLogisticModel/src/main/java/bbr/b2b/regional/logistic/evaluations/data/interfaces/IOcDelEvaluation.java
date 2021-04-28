package bbr.b2b.regional.logistic.evaluations.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IOcDelEvaluation extends IElement {

	public Long getDelivery();
	public Long getOrder();
	public Date getDatingdate();
	public Double getDeliveryscore();
	public Double getQualitypercent();
	public Double getLevelservicepercent();
	public Double getTotalreceivedunits();
	public Double getTotalavailableunits();
	public void setDelivery(Long delivery);
	public void setOrder(Long order);
	public void setDatingdate(Date datingdate);
	public void setDeliveryscore(Double deliveryscore);
	public void setQualitypercent(Double qualitypercent);
	public void setLevelservicepercent(Double levelservicepercent);
	public void setTotalreceivedunits(Double totalreceivedunits);
	public void setTotalavailableunits(Double totalavailableunits);
}
