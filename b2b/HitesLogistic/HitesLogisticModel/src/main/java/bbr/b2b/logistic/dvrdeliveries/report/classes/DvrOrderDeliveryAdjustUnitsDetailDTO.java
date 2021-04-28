package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class DvrOrderDeliveryAdjustUnitsDetailDTO implements Serializable {

	private Long dvrorderid;
	private Long destinationlocationid;
	private Long itemid;
	private Double availableunits;
	
	public Long getDvrorderid() {
		return dvrorderid;
	}
	public void setDvrorderid(Long dvrorderid) {
		this.dvrorderid = dvrorderid;
	}
	public Long getDestinationlocationid() {
		return destinationlocationid;
	}
	public void setDestinationlocationid(Long destinationlocationid) {
		this.destinationlocationid = destinationlocationid;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public Double getAvailableunits() {
		return availableunits;
	}
	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}
}
