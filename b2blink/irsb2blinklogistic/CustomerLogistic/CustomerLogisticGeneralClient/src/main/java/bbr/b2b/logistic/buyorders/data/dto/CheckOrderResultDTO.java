package bbr.b2b.logistic.buyorders.data.dto;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class CheckOrderResultDTO extends BaseResultDTO{
	
	boolean exists;

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

}
