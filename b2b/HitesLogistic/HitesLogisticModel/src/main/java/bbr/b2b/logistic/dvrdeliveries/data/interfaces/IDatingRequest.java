package bbr.b2b.logistic.dvrdeliveries.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDatingRequest extends IElement {

	public String getRequester();
	public String getEmail();
	public String getPhone();
	public LocalDateTime getRequestdate();
	public Integer getTrucks();
	public Integer getPallets();
	public Integer getEstimatedvolume();
	public Integer getEstimatedtime();
	public Integer getEstimatedbulks();
	public String getNeedmodule();
	public String getComment();
	public LocalDateTime getWhen();
	public void setRequester(String requester);
	public void setEmail(String email);
	public void setPhone(String phone);
	public void setRequestdate(LocalDateTime requestdate);
	public void setTrucks(Integer trucks);
	public void setPallets(Integer pallets);
	public void setEstimatedvolume(Integer estimatedvolume);
	public void setEstimatedtime(Integer estimatedtime);
	public void setEstimatedbulks(Integer estimatedbulks);
	public void setNeedmodule(String needmodule);
	public void setComment(String comment);
	public void setWhen(LocalDateTime when);
}
