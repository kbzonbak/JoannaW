package bbr.b2b.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.WSSentMessageResponseDTO;

public class ReceptionCloseResultDTO extends BaseResultDTO {

	private Boolean asnsuccess;
	private WSSentMessageResponseDTO[] documentmessages;

	public Boolean getAsnsuccess() {
		return asnsuccess;
	}

	public void setAsnsuccess(Boolean asnsuccess) {
		this.asnsuccess = asnsuccess;
	}

	public WSSentMessageResponseDTO[] getDocumentmessages() {
		return documentmessages;
	}

	public void setDocumentmessages(WSSentMessageResponseDTO[] documentmessages) {
		this.documentmessages = documentmessages;
	}
}
