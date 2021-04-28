package bbr.b2b.logistic.dvrdeliveries.data.wrappers;

import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrDelivery;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;

public class DvrDeliveryW extends ElementDTO implements IDvrDelivery {

	private Long number;
	private LocalDateTime created;
	private LocalDateTime currentstatetypedate;
	private LocalDateTime pluploaddate;
	private Long currentstatetypeid;
	private Long vendorid;
	private Long deliverylocationid;

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

	public Long getCurrentstatetypeid() {
		return currentstatetypeid;
	}

	public void setCurrentstatetypeid(Long currentstatetypeid) {
		this.currentstatetypeid = currentstatetypeid;
	}

	public Long getVendorid() {
		return vendorid;
	}

	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}

	public Long getDeliverylocationid() {
		return deliverylocationid;
	}

	public void setDeliverylocationid(Long deliverylocationid) {
		this.deliverylocationid = deliverylocationid;
	}

}
