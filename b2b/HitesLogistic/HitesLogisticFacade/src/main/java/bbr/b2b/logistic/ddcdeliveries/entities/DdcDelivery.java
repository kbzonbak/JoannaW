package bbr.b2b.logistic.ddcdeliveries.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.ddcdeliveries.data.interfaces.IDdcDelivery;
import bbr.b2b.logistic.ddcorders.entities.DdcOrder;
import bbr.b2b.logistic.vendors.entities.Vendor;

public class DdcDelivery implements IDdcDelivery {

	private Long id;
	private Long number;
	private LocalDateTime originaldate;
	private LocalDateTime committeddate;
	private LocalDateTime currentstatetypedate;
	private String currentstatetypewho;
	private String currentstatetypecomment;
	private DdcOrder ddcorder;
	private DdcDeliveryStateType currentstatetype;
	private Vendor vendor;
	
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
	public LocalDateTime getOriginaldate() {
		return originaldate;
	}
	public void setOriginaldate(LocalDateTime originaldate) {
		this.originaldate = originaldate;
	}
	public LocalDateTime getCommitteddate() {
		return committeddate;
	}
	public void setCommitteddate(LocalDateTime committeddate) {
		this.committeddate = committeddate;
	}
	public LocalDateTime getCurrentstatetypedate() {
		return currentstatetypedate;
	}
	public void setCurrentstatetypedate(LocalDateTime currentstatetypedate) {
		this.currentstatetypedate = currentstatetypedate;
	}
	public String getCurrentstatetypewho() {
		return currentstatetypewho;
	}
	public void setCurrentstatetypewho(String currentstatetypewho) {
		this.currentstatetypewho = currentstatetypewho;
	}
	public String getCurrentstatetypecomment() {
		return currentstatetypecomment;
	}
	public void setCurrentstatetypecomment(String currentstatetypecomment) {
		this.currentstatetypecomment = currentstatetypecomment;
	}
	public DdcOrder getDdcorder() {
		return ddcorder;
	}
	public void setDdcorder(DdcOrder ddcorder) {
		this.ddcorder = ddcorder;
	}
	public DdcDeliveryStateType getCurrentstatetype() {
		return currentstatetype;
	}
	public void setCurrentstatetype(DdcDeliveryStateType currentstatetype) {
		this.currentstatetype = currentstatetype;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
}
