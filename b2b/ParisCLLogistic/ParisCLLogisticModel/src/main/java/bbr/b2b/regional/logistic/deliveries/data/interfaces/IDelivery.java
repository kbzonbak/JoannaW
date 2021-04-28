package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDelivery extends IElement {

	public Long getNumber();
	public Date getCreated();
	public Date getCurrentstatetypedate();
	public Long getReceptionnumber();
	public Date getReceptiondate();
	public Long getRefdocument();
	public String getContainer();
	public String getImp();
	public Integer getComplementscount();
	public void setNumber(Long number);
	public void setCreated(Date created);
	public void setCurrentstatetypedate(Date currentstatetypedate);
	public void setReceptionnumber(Long receptionnumber);
	public void setReceptiondate(Date receptiondate);
	public void setRefdocument(Long refdocument);
	public void setContainer(String container);
	public void setImp(String imp);
	public void setComplementscount(Integer complementscount);
	public Boolean getHasatc();
	public void setHasatc(Boolean hasatc);
}
