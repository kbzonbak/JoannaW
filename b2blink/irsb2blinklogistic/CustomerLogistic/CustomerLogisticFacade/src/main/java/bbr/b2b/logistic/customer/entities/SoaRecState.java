package bbr.b2b.logistic.customer.entities;

import java.util.Date;
import bbr.b2b.logistic.customer.entities.Reception;
import bbr.b2b.logistic.customer.entities.SoaStateType;
import bbr.b2b.logistic.customer.data.interfaces.ISoaRecState;

public class SoaRecState implements ISoaRecState {

	private Long id;
	private Date when;
	private String comment;
	private Reception reception;
	private SoaStateType soastatetype;

	public Long getId(){ 
		return this.id;
	}
	public Date getWhen(){ 
		return this.when;
	}
	public String getComment(){ 
		return this.comment;
	}
	public Reception getReception(){ 
		return this.reception;
	}
	public SoaStateType getSoastatetype(){ 
		return this.soastatetype;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setReception(Reception reception){ 
		this.reception = reception;
	}
	public void setSoastatetype(SoaStateType soastatetype){ 
		this.soastatetype = soastatetype;
	}
}
