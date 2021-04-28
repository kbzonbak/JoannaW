package bbr.b2b.regional.logistic.evaluations.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IReceptionEvaluation extends IElement {

	public Boolean getAutogenerated();
	public String getWho();
	public Date getWhen();
	public Double getScore();
	public String getComments();
	public void setAutogenerated(Boolean autogenerated);
	public void setWho(String who);
	public void setWhen(Date when);
	public void setScore(Double score);
	public void setComments(String comments);
}