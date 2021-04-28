package bbr.b2b.portal.classes.constants;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;

public enum EnumCountry 
{
	ARGENTINA("AR"),BRASIL("BR")
	,CHILE("CL"),COLOMBIA("CO")
	,PERU("PE");

	private String 	code ;
	private String 	name ;
	
	private EnumCountry(String code)
	{
		this.code = code ;
	}
	public String getCode() 
	{
		return code;
	}
	public String getName() 
	{
		this.name = I18NManager.getI18NString(BbrUtilsResources.RES_BU, this.code.toLowerCase());
		return name;
	}
	
	public static EnumCountry getEnumByCode(String code)
	{
		EnumCountry result = null;
		for (EnumCountry item : values()) 
		{
			if(item.getCode().equalsIgnoreCase(code))
			{
				result = item;
				break;
			}
		}
		
		return result;
	}
}
