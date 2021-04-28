package bbr.b2b.portal.classes.constants;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;

public enum EnumBusinessUnit 
{
	//Argentina
	EASYAR("EasyAR","http://",EnumCountry.ARGENTINA),
	SUPERAR("SuperAR","http://",EnumCountry.ARGENTINA),
	//Brasil
	SUPERBR("SuperBR","http://",EnumCountry.BRASIL),
	//Chile
	EASYCL("EasyCL","http://",EnumCountry.CHILE),
	OTSOCCL("OtSocCL","http://",EnumCountry.CHILE),
	PARISCL("ParisCL","http://",EnumCountry.CHILE),
	SHOPPINGCL("ShoppingCL","http://",EnumCountry.CHILE),
	SUPERCL("SuperCL","http://",EnumCountry.CHILE),
	//Colombia
	EASYCO("EasyCO","http://",EnumCountry.COLOMBIA),
	//Peru
	PARISPE("ParisPE","http://",EnumCountry.PERU),
	SUPERPE("SuperPE","http://",EnumCountry.PERU);

	
	private String 	code ;
	private String 	name ;
	private String url	= "";
	private EnumCountry country = null;
	
	private EnumBusinessUnit(String code, String url, EnumCountry country)
	{
		this.code = code ;
		this.url = url ;
		this.country = country;
	}
	public String getCode() 
	{
		return code;
	}
	public String getUrl() 
	{
		return url;
	}
	public EnumCountry getCountry() 
	{
		return country;
	}
	public String getName() 
	{
		this.name = I18NManager.getI18NString(BbrUtilsResources.RES_BU, this.code.toLowerCase());
		return name;
	}
	
}
