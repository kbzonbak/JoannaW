package bbr.b2b.logistic.soa.data.classes;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.soa.data.interfaces.ISOAState;

public class SOAStateW extends ElementDTO implements ISOAState {

	private Long orderid;
	private Long soastatetypeid;
	private LocalDateTime when;
	private String comment;

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
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

	public LocalDateTime getWhen() {
		return when;
	}

	public void setWhen(LocalDateTime when) {
		this.when = when;
	}	
}
