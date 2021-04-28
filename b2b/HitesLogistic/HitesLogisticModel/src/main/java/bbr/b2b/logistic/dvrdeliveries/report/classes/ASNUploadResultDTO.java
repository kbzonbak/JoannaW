package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ASNUploadResultDTO extends BaseResultDTO {

	private WSSentMessageResponseDTO[] asnsentmessagedto;
	private WSSentMessageResponseDTO datingsentmessagedto;

	public WSSentMessageResponseDTO[] getAsnsentmessagedto() {
		return asnsentmessagedto;
	}

	public void setAsnsentmessagedto(WSSentMessageResponseDTO[] asnsentmessagedto) {
		this.asnsentmessagedto = asnsentmessagedto;
	}

	public WSSentMessageResponseDTO getDatingsentmessagedto() {
		return datingsentmessagedto;
	}

	public void setDatingsentmessagedto(WSSentMessageResponseDTO datingsentmessagedto) {
		this.datingsentmessagedto = datingsentmessagedto;
	}

}
