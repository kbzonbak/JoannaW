package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;

public class AssignedDatingDataDTO implements Serializable {

	private Long vendorid;
	private String vendorcode;
	private String vendorname;
	private Long dvrdeliveryid;
	private Long dvrdeliverynumber;
	private Long reserveid;
	private Long moduleid;
	private Long dockid;
	private String currentdvrdeliverystatecode;
	private String currentdvrdeliverystate;
	
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

	public Long getDvrdeliveryid() {
		return dvrdeliveryid;
	}

	public void setDvrdeliveryid(Long dvrdeliveryid) {
		this.dvrdeliveryid = dvrdeliveryid;
	}

	public Long getDvrdeliverynumber() {
		return dvrdeliverynumber;
	}

	public void setDvrdeliverynumber(Long dvrdeliverynumber) {
		this.dvrdeliverynumber = dvrdeliverynumber;
	}

	public Long getReserveid() {
		return reserveid;
	}

	public void setReserveid(Long reserveid) {
		this.reserveid = reserveid;
	}

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

	public String getCurrentdvrdeliverystatecode() {
		return currentdvrdeliverystatecode;
	}

	public void setCurrentdvrdeliverystatecode(String currentdvrdeliverystatecode) {
		this.currentdvrdeliverystatecode = currentdvrdeliverystatecode;
	}

	public String getCurrentdvrdeliverystate() {
		return currentdvrdeliverystate;
	}

	public void setCurrentdvrdeliverystate(String currentdvrdeliverystate) {
		this.currentdvrdeliverystate = currentdvrdeliverystate;
	}

}
