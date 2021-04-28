package bbr.b2b.logistic.customer.entities;

import bbr.b2b.logistic.customer.data.interfaces.IReceptionLocation;

public class ReceptionLocation implements IReceptionLocation {

	private ReceptionLocationId id;

	public ReceptionLocationId getId(){ 
		return this.id;
	}
	public void setId(ReceptionLocationId id){ 
		this.id = id;
	}
}
