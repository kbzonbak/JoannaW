package bbr.b2b.portal.classes.wrappers.app;

import bbr.b2b.portal.classes.constants.EnumBusinessUnit;
import bbr.b2b.portal.classes.constants.EnumCountry;

public class BusinessUnit 
{
	private String code = "";
	private String name = "";
	private String url	= "";
	private EnumCountry country = null;
	
	public BusinessUnit(EnumBusinessUnit businessUnit) 
	{
		this.setCode(businessUnit.getCode()) ;
		this.name = businessUnit.getName() ;
		this.country = businessUnit.getCountry();
		this.url = businessUnit.getUrl();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public EnumCountry getCountry() {
		return country;
	}

	public void setCountry(EnumCountry country) {
		this.country = country;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
