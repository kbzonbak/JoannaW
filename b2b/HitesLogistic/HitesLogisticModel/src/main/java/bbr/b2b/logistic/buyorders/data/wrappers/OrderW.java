package bbr.b2b.logistic.buyorders.data.wrappers;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.buyorders.data.interfaces.IOrder;

public class OrderW extends ElementDTO implements IOrder {

	private Long number;
	private LocalDateTime emitted;
	private LocalDateTime creationdate;
	private LocalDateTime currentsoastatetypedate;
	private Long responsableid;
	private Long retailerid;
	private Long vendorid;
	private Long sectionid;
	private Long ordertypeid;
	private Long clientid;
	private Long currentsoastatetypeid;

	public Long getNumber() {
		return number;
	}

	public LocalDateTime getEmitted() {
		return emitted;
	}

	public LocalDateTime getCreationdate() {
		return creationdate;
	}

	public Long getResponsableid() {
		return responsableid;
	}

	public Long getRetailerid() {
		return retailerid;
	}

	public Long getVendorid() {
		return vendorid;
	}

	public Long getSectionid() {
		return sectionid;
	}

	public Long getOrdertypeid() {
		return ordertypeid;
	}

	public Long getClientid() {
		return clientid;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public void setEmitted(LocalDateTime emitted) {
		this.emitted = emitted;
	}

	public void setCreationdate(LocalDateTime creationdate) {
		this.creationdate = creationdate;
	}

	public void setResponsableid(Long responsableid) {
		this.responsableid = responsableid;
	}

	public void setRetailerid(Long retailerid) {
		this.retailerid = retailerid;
	}

	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}

	public void setSectionid(Long sectionid) {
		this.sectionid = sectionid;
	}

	public void setOrdertypeid(Long ordertypeid) {
		this.ordertypeid = ordertypeid;
	}

	public void setClientid(Long clientid) {
		this.clientid = clientid;
	}

	public LocalDateTime getCurrentsoastatetypedate() {
		return currentsoastatetypedate;
	}

	public void setCurrentsoastatetypedate(LocalDateTime currentsoastatetypedate) {
		this.currentsoastatetypedate = currentsoastatetypedate;
	}

	public Long getCurrentsoastatetypeid() {
		return currentsoastatetypeid;
	}

	public void setCurrentsoastatetypeid(Long currentsoastatetypeid) {
		this.currentsoastatetypeid = currentsoastatetypeid;
	}

}
