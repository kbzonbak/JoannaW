package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AssignedPreDatingResourceGroupDataDTO implements Serializable {

	private Long moduleid;
	private Long dockid;
	private Long reserveid;
	private Long vendorid;
	private String vendorcode;
	private String vendorname;
	private Long blockingroupid;
	private String who;
	private LocalDateTime creationdate;

	public Long getModuleid() {
		return moduleid;
	}

	public void setModuleid(Long moduleid) {
		this.moduleid = moduleid;
	}

	public Long getDockid() {
		return dockid;
	}

	public void setDockid(Long dockid) {
		this.dockid = dockid;
	}

	public Long getReserveid() {
		return reserveid;
	}

	public void setReserveid(Long reserveid) {
		this.reserveid = reserveid;
	}

	public Long getVendorid() {
		return vendorid;
	}

	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public String getVendorname() {
		return vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public Long getBlockingroupid() {
		return blockingroupid;
	}

	public void setBlockingroupid(Long blockingroupid) {
		this.blockingroupid = blockingroupid;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public LocalDateTime getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(LocalDateTime creationdate) {
		this.creationdate = creationdate;
	}

}
