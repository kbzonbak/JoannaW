package bbr.esb.users.entities;

import bbr.esb.users.data.interfaces.IUserCompany;

public class UserCompany implements IUserCompany {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866882020309944154L;

	private boolean active;

	private Company company;

	private UserCompanyKey id;

	private User user;

	public boolean getActive() {
		return active;
	}

	public Company getCompany() {
		return company;
	}

	public UserCompanyKey getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setId(UserCompanyKey id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
