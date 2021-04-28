package bbr.b2b.regional.logistic.couriers.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.couriers.data.interfaces.ICourierTicketDetail;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;

public class CourierTicketDetail implements ICourierTicketDetail {

	private CourierTicket courierticket;
	private DODelivery dodelivery;
	private CourierTicketDetailId id;
	private Boolean processed;
	private Date startdate;
	private Boolean success;
	private String result;
	
	public CourierTicket getCourierticket() {
		return courierticket;
	}
	public void setCourierticket(CourierTicket courierticket) {
		this.courierticket = courierticket;
	}
	public DODelivery getDodelivery() {
		return dodelivery;
	}
	public void setDodelivery(DODelivery dodelivery) {
		this.dodelivery = dodelivery;
	}
	public CourierTicketDetailId getId() {
		return id;
	}
	public void setId(CourierTicketDetailId id) {
		this.id = id;
	}
	public Boolean getProcessed() {
		return processed;
	}
	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
