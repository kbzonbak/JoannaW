package bbr.b2b.logistic.datings.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDating extends IElement {

	public Long getNumber();

	public void setNumber(Long number);

	public String getComment();

	public void setComment(String comment);

	public Boolean getConfirmated();

	public void setConfirmated(Boolean confirmated);

	public LocalDateTime getConfirmationdate();

	public void setConfirmationdate(LocalDateTime confirmationdate);

	public Boolean getSentstatus();

	public void setSentstatus(Boolean sentstatus);
	
}
