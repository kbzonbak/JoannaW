package bbr.b2b.regional.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;


public class DatingRequestResultDTO extends BaseResultDTO {

	private DatingRequestDTO datingrequest;
	private OrderUnitsDTO[] orderunits;
	
	public DatingRequestDTO getDatingrequest() {
		return datingrequest;
	}
	public void setDatingrequest(DatingRequestDTO datingrequest) {
		this.datingrequest = datingrequest;
	}
	public OrderUnitsDTO[] getOrderunits() {
		return orderunits;
	}
	public void setOrderunits(OrderUnitsDTO[] orderunits) {
		this.orderunits = orderunits;
	}	
}
