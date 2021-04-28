package bbr.esb.users.data.classes;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.users.data.interfaces.IUserType;

/**
 * 
 * @author dvillanueva
 */

public class UserTypeDTO extends ElementDTO implements IUserType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9215128178137607991L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}