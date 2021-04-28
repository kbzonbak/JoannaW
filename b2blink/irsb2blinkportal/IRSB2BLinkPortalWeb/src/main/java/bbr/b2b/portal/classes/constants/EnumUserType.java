package bbr.b2b.portal.classes.constants;

/**
*  Éste enum se utiliza para identificar que un usuario tiene asignado un tipo de empresa especifico.
*  <p>
*  Nota: Lo unico que se deberia cambiar por proyecto es el campo description del RETAILER 
*/
public enum EnumUserType 
{
	BBR(1L,"BBR","BBR Spa"), PROVIDER(2L,"PROVEEDOR","Proveedor"),RETAILER(3L, "RETAIL", "Retail");

	private String 	description ;
	private String 	value ;
	private Long 	id ;
	
	private EnumUserType(Long id, String value, String description)
	{
		this.description = description ;
		this.value = value ;
		this.id = id ;
	}
	public String getValue() 
	{
		return value;
	}
	public String getDescription() 
	{
		return description;
	}
	public Long getId() 
	{
		return id;
	}
	
	
	public static EnumUserType getEnumUserType(String strUserType)
	{
		EnumUserType result = null;
		switch (strUserType.toUpperCase()) 
		{
			case "PROVEEDOR":
				result = EnumUserType.PROVIDER;
				break;
			case "RETAIL":
				result = EnumUserType.RETAILER;
				break;
			case "BBR":
				result = EnumUserType.BBR;
				break;

			default:
				result = null;
				break;
		}
		return result;
	}
	
	
}
