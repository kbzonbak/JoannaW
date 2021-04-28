package bbr.esb.services.data.classes;

import bbr.common.adtclasses.classes.BaseResultDTO;
import bbr.esb.users.data.classes.UserDTO;

public class LogonResultDTO extends BaseResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8067737943099378500L;

	private UserDTO user;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
