package bbr.common.adtclasses.classes;

import bbr.common.adtclasses.interfaces.IBaseResult;

public class BaseResultDTO implements IBaseResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String statuscode = "0";

	private String statusmessage = "";

	public String getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

	public String getStatusmessage() {
		return statusmessage;
	}

	public void setStatusmessage(String statusmessage) {
		this.statusmessage = statusmessage;
	}

}
