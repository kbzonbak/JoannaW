package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DvrDeliveryDetailExcelDataDTO implements Serializable {

	private Long deliverynumber;
	private String currentdeliverystatetypename;
	private String deliverylocationcode;
	private String deliverylocationname;
	private LocalDateTime confirmationdate;
	private Long ordernumber;
	private String destinationlocationcode;
	private String destinationlocationname;
	private String itemsku;
	private String itemname;
	private Double availableunits;
	private Double receivedunits;
	
	public Long getDeliverynumber() {
		return deliverynumber;
	}
	public void setDeliverynumber(Long deliverynumber) {
		this.deliverynumber = deliverynumber;
	}
	public String getCurrentdeliverystatetypename() {
		return currentdeliverystatetypename;
	}
	public void setCurrentdeliverystatetypename(String currentdeliverystatetypename) {
		this.currentdeliverystatetypename = currentdeliverystatetypename;
	}
	public String getDeliverylocationcode() {
		return deliverylocationcode;
	}
	public void setDeliverylocationcode(String deliverylocationcode) {
		this.deliverylocationcode = deliverylocationcode;
	}
	public String getDeliverylocationname() {
		return deliverylocationname;
	}
	public void setDeliverylocationname(String deliverylocationname) {
		this.deliverylocationname = deliverylocationname;
	}
	public LocalDateTime getConfirmationdate() {
		return confirmationdate;
	}
	public void setConfirmationdate(LocalDateTime confirmationdate) {
		this.confirmationdate = confirmationdate;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getDestinationlocationcode() {
		return destinationlocationcode;
	}
	public void setDestinationlocationcode(String destinationlocationcode) {
		this.destinationlocationcode = destinationlocationcode;
	}
	public String getDestinationlocationname() {
		return destinationlocationname;
	}
	public void setDestinationlocationname(String destinationlocationname) {
		this.destinationlocationname = destinationlocationname;
	}
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public Double getAvailableunits() {
		return availableunits;
	}
	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
}
