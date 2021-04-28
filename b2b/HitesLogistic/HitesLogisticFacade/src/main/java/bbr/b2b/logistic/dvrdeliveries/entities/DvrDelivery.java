package bbr.b2b.logistic.dvrdeliveries.entities;

import bbr.b2b.logistic.dvrdeliveries.entities.DvrDeliveryStateType;
import bbr.b2b.logistic.vendors.entities.Vendor;
import bbr.b2b.logistic.locations.entities.Location;

import java.time.LocalDateTime;

import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrDelivery;

public class DvrDelivery implements IDvrDelivery {

	private Long id;
	private Long number;
	private LocalDateTime created;
	private LocalDateTime currentstatetypedate;
	private LocalDateTime pluploaddate;
	private DvrDeliveryStateType currentstatetype;
	private Vendor vendor;
	private Location deliverylocation;

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

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getCurrentstatetypedate() {
		return currentstatetypedate;
	}

	public void setCurrentstatetypedate(LocalDateTime currentstatetypedate) {
		this.currentstatetypedate = currentstatetypedate;
	}

	public LocalDateTime getPluploaddate() {
		return pluploaddate;
	}

	public void setPluploaddate(LocalDateTime pluploaddate) {
		this.pluploaddate = pluploaddate;
	}

	public DvrDeliveryStateType getCurrentstatetype() {
		return currentstatetype;
	}

	public void setCurrentstatetype(DvrDeliveryStateType currentstatetype) {
		this.currentstatetype = currentstatetype;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Location getDeliverylocation() {
		return deliverylocation;
	}

	public void setDeliverylocation(Location deliverylocation) {
		this.deliverylocation = deliverylocation;
	}

}
