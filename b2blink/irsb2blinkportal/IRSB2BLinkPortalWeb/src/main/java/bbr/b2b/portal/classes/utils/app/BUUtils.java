package bbr.b2b.portal.classes.utils.app;

import java.util.HashMap;

import bbr.b2b.portal.classes.constants.EnumBusinessUnit;
import bbr.b2b.portal.classes.constants.EnumCountry;
import bbr.b2b.portal.classes.wrappers.app.BusinessUnit;
import bbr.b2b.portal.classes.wrappers.app.Country;

public class BUUtils 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static HashMap<String, BusinessUnit[]> mapBusinessUnits = new HashMap<>();
	private static Country[] countries = null;
	static
	{
		//Argentina
		BusinessUnit buEasAr = new BusinessUnit(EnumBusinessUnit.EASYAR);
		BusinessUnit buSupAr = new BusinessUnit(EnumBusinessUnit.SUPERAR);
		BusinessUnit[] arrAr = new BusinessUnit[]{buEasAr,buSupAr};
		
		//Brasil
		BusinessUnit buSupBr = new BusinessUnit(EnumBusinessUnit.SUPERBR);
		BusinessUnit[] arrBr = new BusinessUnit[]{buSupBr};
		
		//Chile
		BusinessUnit buEasCl = new BusinessUnit(EnumBusinessUnit.EASYCL);
		BusinessUnit buOtSCl = new BusinessUnit(EnumBusinessUnit.OTSOCCL);
		BusinessUnit buPaJCl = new BusinessUnit(EnumBusinessUnit.PARISCL);
		BusinessUnit buShoCL = new BusinessUnit(EnumBusinessUnit.SHOPPINGCL);
		BusinessUnit buSupCl = new BusinessUnit(EnumBusinessUnit.SUPERCL);
		BusinessUnit[] arrCl = new BusinessUnit[]{buEasCl,buOtSCl,buPaJCl,buShoCL,buSupCl};
		
		//Colombia
		BusinessUnit buEasCo = new BusinessUnit(EnumBusinessUnit.EASYCO);
		BusinessUnit[] arrCo = new BusinessUnit[]{buEasCo};
		
		//Peru
		BusinessUnit buParPe = new BusinessUnit(EnumBusinessUnit.PARISPE);
		BusinessUnit buSupPe = new BusinessUnit(EnumBusinessUnit.SUPERPE);
		BusinessUnit[] arrPe = new BusinessUnit[]{buParPe,buSupPe};
		
		mapBusinessUnits.put(EnumCountry.ARGENTINA.getCode(), arrAr);
		mapBusinessUnits.put(EnumCountry.BRASIL.getCode(), arrBr);
		mapBusinessUnits.put(EnumCountry.CHILE.getCode(), arrCl);
		mapBusinessUnits.put(EnumCountry.COLOMBIA.getCode(), arrCo);
		mapBusinessUnits.put(EnumCountry.PERU.getCode(), arrPe);
		
		Country countryAr = new Country(EnumCountry.ARGENTINA.getName(),EnumCountry.ARGENTINA.getCode());
		Country countryBr = new Country(EnumCountry.BRASIL.getName(),EnumCountry.BRASIL.getCode());
		Country countryCh = new Country(EnumCountry.CHILE.getName(),EnumCountry.CHILE.getCode());
		Country countryCo = new Country(EnumCountry.COLOMBIA.getName(),EnumCountry.COLOMBIA.getCode());
		Country countryPe = new Country(EnumCountry.PERU.getName(),EnumCountry.PERU.getCode());
		
		countries= new  Country[]{countryAr,countryBr,countryCh,countryCo,countryPe};
	}
	private static BUUtils instance = new BUUtils();
	public static BUUtils getInstance()
	{
		return instance;
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	private BUUtils() 
	{
		
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************
	
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION 		---->			PUBLIC METHODS
	// ****************************************************************************************
	public Country[] getCountries()
	{
		return countries;
	}
	
	public BusinessUnit[] getAllBusinessUnitsOfCountry(EnumCountry country)
	{
		BusinessUnit[] result = null;
		
		if(mapBusinessUnits != null)
		{
			result = mapBusinessUnits.get(country.getCode());
		}
		
		return result;
	}

	public Country getCountryByBusinessUnit(EnumBusinessUnit businessUnit)
	{
		Country result = null;
		
		if(businessUnit != null)
		{
			for (Country item : countries) 
			{
				if(item.getCode().equalsIgnoreCase(businessUnit.getCountry().getCode()))
				{
					result = item;
					break;
				}
			}
		}
		
		return result;
	}
	
	public BusinessUnit getBusinessUnit(EnumBusinessUnit businessUnit)
	{
		BusinessUnit result = null;
		
		if(businessUnit != null)
		{
			BusinessUnit[] bus = mapBusinessUnits.get(businessUnit.getCountry().getCode());
			for (BusinessUnit item : bus) 
			{
				if(item.getCode().equalsIgnoreCase(businessUnit.getCode()))
				{
					result = item;
					break;
				}
			}
		}
		
		return result;
	}

	
}
