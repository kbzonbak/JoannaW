package bbr.b2b.regional.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DatingDataResultDTO extends BaseResultDTO {
	
	private DatingDataDTO dating;
	private DatingDetailDTO[] datingdetail;
	
	public DatingDataDTO getDating() {
		return dating;
	}
	public void setDating(DatingDataDTO dating) {
		this.dating = dating;
	}
	public DatingDetailDTO[] getDatingdetail() {
		return datingdetail;
	}
	public void setDatingdetail(DatingDetailDTO[] datingdetail) {
		this.datingdetail = datingdetail;
	}
}
