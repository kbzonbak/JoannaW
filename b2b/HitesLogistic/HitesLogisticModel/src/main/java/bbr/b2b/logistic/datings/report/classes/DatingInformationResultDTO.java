package bbr.b2b.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DatingInformationResultDTO extends BaseResultDTO {

	private DatingInformationDTO datinginformation;
	private DatingTimeDockDataDTO[] datingtimedockdata;

	public DatingInformationDTO getDatinginformation() {
		return datinginformation;
	}

	public void setDatinginformation(DatingInformationDTO datinginformation) {
		this.datinginformation = datinginformation;
	}

	public DatingTimeDockDataDTO[] getDatingtimedockdata() {
		return datingtimedockdata;
	}

	public void setDatingtimedockdata(DatingTimeDockDataDTO[] datingtimedockdata) {
		this.datingtimedockdata = datingtimedockdata;
	}

}
