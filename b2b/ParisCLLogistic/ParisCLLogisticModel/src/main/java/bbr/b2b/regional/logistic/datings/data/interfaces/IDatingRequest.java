package bbr.b2b.regional.logistic.datings.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDatingRequest extends IElement {

	public Long getNumber();
	public Date getWhen();
	public Date getRequesteddate();
	public Integer getEstimatedboxes();
	public Integer getEstimatedpallets();
	public Integer getTrucks();
	public String getRequester();
	public String getEmail();
	public String getPhone();
	public String getComment();
	public void setNumber(Long number);
	public void setWhen(Date when);
	public void setRequesteddate(Date requesteddate);
	public void setEstimatedboxes(Integer estimatedboxes);
	public void setEstimatedpallets(Integer estimatedpallets);
	public void setTrucks(Integer trucks);
	public void setRequester(String requester);
	public void setEmail(String email);
	public void setPhone(String phone);
	public void setComment(String comment);
}
