package bbr.b2b.logistic.dvrdeliveries.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDocument extends IElement {
	
	public String getNumber();

	public void setNumber(String number);

	public LocalDateTime getEmitteddate();

	public void setEmitteddate(LocalDateTime emitteddate);

	public Double getNetamount();

	public void setNetamount(Double netamount);
	
	public Double getIva();
	
	public void setIva(Double iva);

	public Double getTotalamount();

	public void setTotalamount(Double totalamount);

	public Boolean getValidated();

	public void setValidated(Boolean validated);

	public Boolean getClosed();

	public void setClosed(Boolean closed);

	public LocalDateTime getReceptiondate();

	public void setReceptiondate(LocalDateTime receptiondate);

	public LocalDateTime getWhen();

	public void setWhen(LocalDateTime when);

	public Long getReceptionnumber();

	public void setReceptionnumber(Long receptionnumber);

	public String getAsnnumber();

	public void setAsnnumber(String asnnumber);

	public String getComment();

	public void setComment(String comment);
	
	public Boolean getStatus();

	public void setStatus(Boolean status);

}
