package bbr.b2b.logistic.datings.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.datings.data.interfaces.IReserveDetail;
import bbr.b2b.logistic.datings.entities.DatingResource;

public class ReserveDetail implements IReserveDetail {

	private ReserveDetailId id;
	private DatingResource datingresource;
	private Reserve reserve;
	private LocalDateTime when;

	public ReserveDetailId getId(){ 
		return this.id;
	}
	public DatingResource getDatingresource(){ 
		return this.datingresource;
	}
	public Reserve getReserve(){ 
		return this.reserve;
	}
	public LocalDateTime getWhen(){ 
		return this.when;
	}
	public void setId(ReserveDetailId id){ 
		this.id = id;
	}
	public void setDatingresource(DatingResource datingresource){ 
		this.datingresource = datingresource;
	}
	public void setReserve(Reserve reserve){ 
		this.reserve = reserve;
	}
	public void setWhen(LocalDateTime when){ 
		this.when = when;
	}
}
