package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DatingRequestDetailDataResultDTO extends BaseResultDTO {

	private DatingRequestDataDTO datingrequestdata;
	
	public DatingRequestDataDTO getDatingrequestdata() {
		return datingrequestdata;
	}

	public void setDatingrequestdata(DatingRequestDataDTO datingrequestdata) {
		this.datingrequestdata = datingrequestdata;
	}

}
