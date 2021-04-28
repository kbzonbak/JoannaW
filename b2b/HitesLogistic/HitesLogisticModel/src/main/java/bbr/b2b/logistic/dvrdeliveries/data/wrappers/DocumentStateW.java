package bbr.b2b.logistic.dvrdeliveries.data.wrappers;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDocumentState;

public class DocumentStateW extends ElementDTO implements IDocumentState {

	private LocalDateTime when;
	private Boolean status;
	private String documentresponsemessage;
	private String documentresponsecode;
	private String user;
	private String usertype;
	private Long documentid;

	public LocalDateTime getWhen(){ 
		return this.when;
	}
	public Boolean getStatus(){ 
		return this.status;
	}
	public String getDocumentresponsemessage(){ 
		return this.documentresponsemessage;
	}
	public String getDocumentresponsecode(){ 
		return this.documentresponsecode;
	}
	public String getUser(){ 
		return this.user;
	}
	public String getUsertype(){ 
		return this.usertype;
	}
	public Long getDocumentid(){ 
		return this.documentid;
	}
	public void setWhen(LocalDateTime when){ 
		this.when = when;
	}
	public void setStatus(Boolean status){ 
		this.status = status;
	}
	public void setDocumentresponsemessage(String documentresponsemessage){ 
		this.documentresponsemessage = documentresponsemessage;
	}
	public void setDocumentresponsecode(String documentresponsecode){ 
		this.documentresponsecode = documentresponsecode;
	}
	public void setUser(String user){ 
		this.user = user;
	}
	public void setUsertype(String usertype){ 
		this.usertype = usertype;
	}
	public void setDocumentid(Long documentid){ 
		this.documentid = documentid;
	}
}
