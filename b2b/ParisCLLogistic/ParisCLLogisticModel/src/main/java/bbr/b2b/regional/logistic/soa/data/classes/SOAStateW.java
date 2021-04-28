package bbr.b2b.regional.logistic.soa.data.classes;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.soa.data.interfaces.ISOAState;

public class SOAStateW extends ElementDTO implements ISOAState {

	private Long orderid;
	private Long soastatetypeid;
	private Date when;
	private String comment;

	public Long getOrderid() {
		return orderid;
	}

	public Date getWhen() {
		return when;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}	

	public void setWhen(Date when) {
		this.when = when;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getSoastatetypeid() {
		return soastatetypeid;
	}

	public void setSoastatetypeid(Long soastatetypeid) {
		this.soastatetypeid = soastatetypeid;
	}	
}
