package bbr.b2b.logistic.parameters.entities;

import bbr.b2b.logistic.parameters.entities.Parameter;

import java.time.LocalDateTime;

import bbr.b2b.logistic.parameters.data.interfaces.IParameterChange;

public class ParameterChange implements IParameterChange {

	private Long id;
	private LocalDateTime when;
	private String user;
	private String usertype;
	private String comment;
	private Parameter parameter;

	public Long getId(){ 
		return this.id;
	}
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
	public Parameter getParameter(){ 
		return this.parameter;
	}
	public void setId(Long id){ 
		this.id = id;
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
	public void setParameter(Parameter parameter){ 
		this.parameter = parameter;
	}
}
