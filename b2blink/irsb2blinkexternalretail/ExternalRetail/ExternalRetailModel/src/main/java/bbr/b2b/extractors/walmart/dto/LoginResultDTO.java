package bbr.b2b.extractors.walmart.dto;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class LoginResultDTO extends BaseResultDTO{

	private String access_token;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	
}
