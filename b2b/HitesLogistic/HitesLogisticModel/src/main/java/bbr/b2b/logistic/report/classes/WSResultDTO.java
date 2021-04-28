package bbr.b2b.logistic.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class WSResultDTO extends BaseResultDTO {

	private String wscode;
	private Boolean success;
	private String responseText;

	public String getWscode() {
		return wscode;
	}

	public void setWscode(String wscode) {
		this.wscode = wscode;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

}
