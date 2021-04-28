package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DvrOrderToDeliveryUnitsDTO implements Serializable {

	private Long dvrorderid;
	private Long ocnumber;
	private LocalDateTime deliverydate;
	private Double units;

	public Long getDvrorderid() {
		return dvrorderid;
	}

	public void setDvrorderid(Long dvrorderid) {
		this.dvrorderid = dvrorderid;
	}

	public Long getOcnumber() {
		return ocnumber;
	}

	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}

	public LocalDateTime getDeliverydate() {
		return deliverydate;
	}

	public void setDeliverydate(LocalDateTime deliverydate) {
		this.deliverydate = deliverydate;
	}

	public Double getUnits() {
		return units;
	}

	public void setUnits(Double units) {
		this.units = units;
	}

}
