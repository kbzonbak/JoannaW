package bbr.b2b.portal.classes.basics;

import java.io.Serializable;

import bbr.b2b.users.report.classes.UserDTO;

public class CustomUserDTO extends UserDTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2644759734305576658L;
	private String caption = "";

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

		
}
