package bbr.esb.services.data.classes;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public class ContractedServiceMonitorDataDTO implements IIdentifiable {

	private static final long serialVersionUID = -4990472699022865109L;

	private String sitename;

	private String servicename;

	private String accesscode;

	private Integer pendingmessages;
	
	private String detail;

	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getAccesscode() {
		return accesscode;
	}

	public void setAccesscode(String accesscode) {
		this.accesscode = accesscode;
	}

	public Integer getPendingmessages() {
		return pendingmessages;
	}

	public void setPendingmessages(Integer pendingmessages) {
		this.pendingmessages = pendingmessages;
	}

	public String getDetail() {
		return detail ;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}




}
