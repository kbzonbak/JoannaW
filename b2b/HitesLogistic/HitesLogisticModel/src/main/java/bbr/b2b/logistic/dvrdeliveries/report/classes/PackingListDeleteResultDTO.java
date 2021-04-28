package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class PackingListDeleteResultDTO extends BaseResultDTO {

	private Boolean asnsuccess;
	private Boolean datingsuccess;
	private WSSentMessageResponseDTO[] documentmessages;
	private WSSentMessageResponseDTO datingmessages;

	public Boolean getAsnsuccess() {
		return asnsuccess;
	}

	public void setAsnsuccess(Boolean asnsuccess) {
		this.asnsuccess = asnsuccess;
	}

	public Boolean getDatingsuccess() {
		return datingsuccess;
	}

	public void setDatingsuccess(Boolean datingsuccess) {
		this.datingsuccess = datingsuccess;
	}

	public WSSentMessageResponseDTO[] getDocumentmessages() {
		return documentmessages;
	}

	public void setDocumentmessages(WSSentMessageResponseDTO[] documentmessages) {
		this.documentmessages = documentmessages;
	}

	public WSSentMessageResponseDTO getDatingmessages() {
		return datingmessages;
	}

	public void setDatingmessages(WSSentMessageResponseDTO datingmessages) {
		this.datingmessages = datingmessages;
	}
}
