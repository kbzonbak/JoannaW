package bbr.b2b.regional.logistic.datings.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.datings.data.interfaces.IReserveDetail;

public class ReserveDetail implements IReserveDetail {

	private Date when;
	private ReserveDetailId id;
	private DatingResource datingresource;
	private Reserve reserve;

	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}
	public ReserveDetailId getId(){ 
		return this.id;
	}
	public DatingResource getDatingresource(){ 
		return this.datingresource;
	}
	public Reserve getReserve(){ 
		return this.reserve;
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
}
