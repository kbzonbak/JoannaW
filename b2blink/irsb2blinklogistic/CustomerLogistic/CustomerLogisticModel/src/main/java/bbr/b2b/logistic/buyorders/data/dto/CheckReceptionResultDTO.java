package bbr.b2b.logistic.buyorders.data.dto;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class CheckReceptionResultDTO extends BaseResultDTO{
	
	boolean found;

	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}



}
