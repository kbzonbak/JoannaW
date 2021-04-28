package bbr.b2b.regional.logistic.deliveries.entities;

import java.util.Date;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryStateType;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrder;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDODelivery;

public class DODelivery implements IDODelivery {

	private Long id;
	private Long number;
	private Date originaldate;
	private Date commiteddate;
	private Date currentstdate;
	private String currentstwho;
	private String currentstcomment;
	private String dispatcheremail;
	private Vendor vendor;
	private DODeliveryStateType currentstatetype;
	private DirectOrder directorder;
	private Long couriertag = 0L;
	
	public Long getId(){ 
		return this.id;
	}
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
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public DODeliveryStateType getCurrentstatetype(){ 
		return this.currentstatetype;
	}
	public DirectOrder getDirectorder(){ 
		return this.directorder;
	}
	public void setId(Long id){ 
		this.id = id;
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
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
	public void setCurrentstatetype(DODeliveryStateType currentstatetype){ 
		this.currentstatetype = currentstatetype;
	}
	public void setDirectorder(DirectOrder directorder){ 
		this.directorder = directorder;
	}
	public Long getCouriertag() {
		return couriertag;
	}
	public void setCouriertag(Long couriertag) {
		this.couriertag = couriertag;
	}
	
	
}
