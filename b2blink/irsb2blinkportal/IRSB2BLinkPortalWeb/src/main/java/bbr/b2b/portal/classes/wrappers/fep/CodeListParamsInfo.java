package bbr.b2b.portal.classes.wrappers.fep;

public class CodeListParamsInfo
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public CodeListParamsInfo(String description, String code)
	{
		super();
		this.setDescription(description);
		this.setCode(code);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private String	description	= null;
	private String	code		= null;

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

}
