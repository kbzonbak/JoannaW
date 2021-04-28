package bbr.esb.users.data.classes;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public class AccessDataDTO implements IIdentifiable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8922188987116569202L;

	private String code;

	private Long companykey;

	private String companyname;

	private String name;

	private Long sitekey;

	private String sitename;

	public String getCode() {
		return code;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public String getCompanyname() {
		return companyname;
	}

	public String getName() {
		return name;
	}

	public Long getSitekey() {
		return sitekey;
	}

	public String getSitename() {
		return sitename;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

}
