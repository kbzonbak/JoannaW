package bbr.b2b.logistic.ddcdeliveries.data.wrappers;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.ddcdeliveries.data.interfaces.IDdcDelivery;

public class DdcDeliveryW extends ElementDTO implements IDdcDelivery {

	private Long number;
	private LocalDateTime originaldate;
	private LocalDateTime committeddate;
	private LocalDateTime currentstatetypedate;
	private String currentstatetypewho;
	private String currentstatetypecomment;
	private Long ddcorderid;
	private Long currentstatetypeid;
	private Long vendorid;
	
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
	public Long getDdcorderid() {
		return ddcorderid;
	}
	public void setDdcorderid(Long ddcorderid) {
		this.ddcorderid = ddcorderid;
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

}
