package bbr.esb.services.data.classes;

import java.util.Date;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.services.data.interfaces.ISite;

public class SiteDTO extends ElementDTO implements ISite {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5739839940847868736L;

	private Date created;

	private String name;

	private String code;

	private String retailname;	

	public String getRetailname() {
		return retailname;
	}

	public void setRetailname(String retailname) {
		this.retailname = retailname;
	}

	public String getCode() {
		return code;
	}

	public Date getCreated() {
		return created;
	}

	public String getName() {
		return name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setName(String name) {
		this.name = name;
	}

}
