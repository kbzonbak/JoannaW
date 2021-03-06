package bbr.esb.users.data.classes;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.users.data.interfaces.IFunctionality;

public class FunctionalityDTO extends ElementDTO implements IFunctionality {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6817306827371718610L;

	private String name;

	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
