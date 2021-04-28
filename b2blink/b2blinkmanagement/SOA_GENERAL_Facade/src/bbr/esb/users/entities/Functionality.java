package bbr.esb.users.entities;

import bbr.esb.users.data.interfaces.IFunctionality;

public class Functionality implements IFunctionality {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7697198044382830956L;

	private Long id;

	private String name;

	private String description;

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
