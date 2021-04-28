package bbr.b2b.logistic.customer.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.customer.data.interfaces.ISoaRecState;

public class SoaRecStateW extends ElementDTO implements ISoaRecState {

	private Date when;
	private String comment;
	private Long receptionid;
	private Long soastatetypeid;

	public Date getWhen(){ 
		return this.when;
	}
	public String getComment(){ 
		return this.comment;
	}
	public Long getReceptionid(){ 
		return this.receptionid;
	}
	public Long getSoastatetypeid(){ 
		return this.soastatetypeid;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setReceptionid(Long receptionid){ 
		this.receptionid = receptionid;
	}
	public void setSoastatetypeid(Long soastatetypeid){ 
		this.soastatetypeid = soastatetypeid;
	}
}
