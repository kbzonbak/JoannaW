package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class WSSentMessageResponseDTO implements Serializable {

	private BaseResultDTO validationresult;
	private String messageid;

	public BaseResultDTO getValidationresult() {
		return validationresult;
	}

	public void setValidationresult(BaseResultDTO validationresult) {
		this.validationresult = validationresult;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

}
