package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDODelivery;

public class DODeliveryW extends ElementDTO implements IDODelivery {

	private Long number;
	private Date originaldate;
	private Date commiteddate;
	private Date currentstdate;
	private String currentstwho;
	private String currentstcomment;
	private String dispatcheremail; 
	private Long vendorid;
	private Long currentstatetypeid;
	private Long orderid;
	private Long couriertag = 0L;

	public Long getNumber(){ 
		return this.number;
	}
	public Date getOriginaldate(){ 
		return this.originaldate;
	}
	public Date getCommiteddate(){ 
		return this.commiteddate;
	}
	public Date getCurrentstdate(){ 
		return this.currentstdate;
	}
	public String getCurrentstwho(){ 
		return this.currentstwho;
	}
	public String getCurrentstcomment(){ 
		return this.currentstcomment;
	}
	public String getDispatcheremail() {
		return dispatcheremail;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getCurrentstatetypeid(){ 
		return this.currentstatetypeid;
	}
	public Long getOrderid(){ 
		return this.orderid;
	}
	public void setNumber(Long number){ 
		this.number = number;
	}
	public void setOriginaldate(Date originaldate){ 
		this.originaldate = originaldate;
	}
	public void setCommiteddate(Date commiteddate){ 
		this.commiteddate = commiteddate;
	}
	public void setCurrentstdate(Date currentstdate){ 
		this.currentstdate = currentstdate;
	}
	public void setCurrentstwho(String currentstwho){ 
		this.currentstwho = currentstwho;
	}
	public void setCurrentstcomment(String currentstcomment){ 
		this.currentstcomment = currentstcomment;
	}
	public void setDispatcheremail(String dispatcheremail) {
		this.dispatcheremail = dispatcheremail;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setCurrentstatetypeid(Long currentstatetypeid){ 
		this.currentstatetypeid = currentstatetypeid;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public Long getCouriertag() {
		return couriertag;
	}
	public void setCouriertag(Long couriertag) {
		this.couriertag = couriertag;
	}
	
}
