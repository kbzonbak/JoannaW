package bbr.esb.users.entities;

import java.util.HashSet;
import java.util.Set;

import bbr.esb.users.data.interfaces.IUserType;

public class UserType implements IUserType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4790055223612582602L;

	private Long id;

	private String name;

	private String description;

	private Set<Functionality> functionalitites = new HashSet<Functionality>();

	public String getDescription() {
		return description;
	}

	public Set<Functionality> getFunctionalitites() {
		return functionalitites;
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

	public void setFunctionalitites(Set<Functionality> functionalitites) {
		this.functionalitites = functionalitites;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
