package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class CourierStateReportResultDTO extends BaseResultDTO {
	
	private String sendNumber;
	private String deliveryNumber;
	private CourierStateReportDTO[] courierStates;
	
	public String getSendNumber() {
		return sendNumber;
	}
	public void setSendNumber(String sendNumber) {
		this.sendNumber = sendNumber;
	}
	public CourierStateReportDTO[] getCourierStates() {
		return courierStates;
	}
	public void setCourierStates(CourierStateReportDTO[] courierStates) {
		this.courierStates = courierStates;
	}
	public String getDeliveryNumber() {
		return deliveryNumber;
	}
	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
	
}
