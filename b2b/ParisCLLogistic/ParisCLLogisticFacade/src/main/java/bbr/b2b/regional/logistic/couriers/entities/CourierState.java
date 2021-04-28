package bbr.b2b.regional.logistic.couriers.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.couriers.data.interfaces.ICourierState;

public class CourierState implements ICourierState {
	private Long id;
	private String description;
	private Date uploaddate;
	private Date startdate;
	
	private CourierTag couriertag;
	private CourierFile courierfile;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getUploaddate() {
		return uploaddate;
	}
	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public CourierTag getCouriertag() {
		return couriertag;
	}
	public void setCouriertag(CourierTag couriertag) {
		this.couriertag = couriertag;
	}
	public CourierFile getCourierfile() {
		return courierfile;
	}
	public void setCourierfile(CourierFile courierfile) {
		this.courierfile = courierfile;
	}
	
}
