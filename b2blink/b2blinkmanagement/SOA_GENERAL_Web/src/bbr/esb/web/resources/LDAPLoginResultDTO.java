package bbr.esb.web.resources;

import bbr.common.adtclasses.classes.BaseResultDTO;

public class LDAPLoginResultDTO extends BaseResultDTO {

	private boolean valid = false;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
