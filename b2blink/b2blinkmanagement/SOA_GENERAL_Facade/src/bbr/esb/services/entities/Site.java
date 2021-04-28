package bbr.esb.services.entities;

import java.util.Date;

import bbr.esb.services.data.interfaces.ISite;

public class Site implements ISite {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7828297017361842157L;

	private Date created;

	private Long id;

	private String code;

	private String name;
	
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

	public Long getId() {
		return id;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
