package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class DvrDeliveryDetailDataDTO implements Serializable {

	private Long orderid;
	private Long ocnumber;
	private Long destinationlocationid;
	private String destinationlocationcode;
	private String destinationlocationname;
	private Long itemid;
	private String itemsku;
	private String itemdescription;
	private Double availableunits;
	private Double receivedunits;
	
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}
	public Long getDestinationlocationid() {
		return destinationlocationid;
	}
	public void setDestinationlocationid(Long destinationlocationid) {
		this.destinationlocationid = destinationlocationid;
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
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getItemdescription() {
		return itemdescription;
	}
	public void setItemdescription(String itemdescription) {
		this.itemdescription = itemdescription;
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
