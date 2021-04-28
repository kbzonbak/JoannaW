package bbr.esb.users.entities;

import bbr.esb.services.entities.Site;
import bbr.esb.users.data.interfaces.IAccess;

public class Access implements IAccess {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1538766015942056721L;

	private String code;

	private Company company;

	private AccessKey id;

	private String name;

	private Site site;

	public String getCode() {
		return code;
	}

	public Company getCompany() {
		return company;
	}

	public AccessKey getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Site getSite() {
		return site;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setId(AccessKey id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}
