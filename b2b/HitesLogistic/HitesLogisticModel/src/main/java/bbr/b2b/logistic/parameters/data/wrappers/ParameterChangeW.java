package bbr.b2b.logistic.parameters.data.wrappers;

import bbr.b2b.logistic.parameters.data.interfaces.IParameterChange;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;

public class ParameterChangeW extends ElementDTO implements IParameterChange {

	private LocalDateTime when;
	private String user;
	private String usertype;
	private String comment;
	private Long parameterid;

	public LocalDateTime getWhen(){ 
		return this.when;
	}
	public String getUser(){ 
		return this.user;
	}
	public String getUsertype(){ 
		return this.usertype;
	}
	public String getComment(){ 
		return this.comment;
	}
	public Long getParameterid(){ 
		return this.parameterid;
	}
	public void setWhen(LocalDateTime when){ 
		this.when = when;
	}
	public void setUser(String user){ 
		this.user = user;
	}
	public void setUsertype(String usertype){ 
		this.usertype = usertype;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setParameterid(Long parameterid){ 
		this.parameterid = parameterid;
	}
}
