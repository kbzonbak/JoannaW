package bbr.b2b.portal.classes.constants;

public enum CompanySearchCriteria
{
	DESCRIPTION(0) , CODE(1);
	
	private Integer 	value ;
	
	private CompanySearchCriteria(Integer value)
	{
		this.value = value ;
	}

	public Integer getValue()
	{
		return value;
	}

}
