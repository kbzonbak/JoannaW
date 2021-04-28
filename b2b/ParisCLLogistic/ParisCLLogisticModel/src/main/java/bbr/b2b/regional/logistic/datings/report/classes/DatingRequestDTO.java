package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;


public class DatingRequestDTO implements Serializable {

	private Long id;
	private Long number;
	private String when;
	private String requesteddate;
	private Integer estimatedboxes;
	private Integer estimatedpallets;
	private Integer trucks;
	private String requester;
	private String email;
	private String phone;
	private String comment;
	private Long deliveryid;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}	
	public String getWhen() {
		return when;
	}
	public void setWhen(String when) {
		this.when = when;
	}
	public String getRequesteddate() {
		return requesteddate;
	}
	public void setRequesteddate(String requesteddate) {
		this.requesteddate = requesteddate;
	}
	public Integer getEstimatedboxes() {
		return estimatedboxes;
	}
	public void setEstimatedboxes(Integer estimatedboxes) {
		this.estimatedboxes = estimatedboxes;
	}
	public Integer getEstimatedpallets() {
		return estimatedpallets;
	}
	public void setEstimatedpallets(Integer estimatedpallets) {
		this.estimatedpallets = estimatedpallets;
	}
	public Integer getTrucks() {
		return trucks;
	}
	public void setTrucks(Integer trucks) {
		this.trucks = trucks;
	}
	public String getRequester() {
		return requester;
	}
	public void setRequester(String requester) {
		this.requester = requester;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}		
}
