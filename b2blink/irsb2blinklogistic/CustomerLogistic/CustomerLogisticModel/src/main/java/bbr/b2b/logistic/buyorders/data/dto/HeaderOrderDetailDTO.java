package bbr.b2b.logistic.buyorders.data.dto;

public class HeaderOrderDetailDTO {
	private String ocnumber;
	private String state;
	private String emitted;
	private String receptiondate;
	private String type;
	private String locationcode;
	private String deliverylocation;
	private String clientname;
	
	public String getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(String ocnumber) {
		this.ocnumber = ocnumber;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getEmitted() {
		return emitted;
	}
	public void setEmitted(String emitted) {
		this.emitted = emitted;
	}
	public String getReceptiondate() {
		return receptiondate;
	}
	public void setReceptiondate(String receptiondate) {
		this.receptiondate = receptiondate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public String getDeliverylocation() {
		return deliverylocation;
	}
	public void setDeliverylocation(String deliverylocation) {
		this.deliverylocation = deliverylocation;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	
}
