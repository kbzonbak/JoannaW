package bbr.b2b.portal.classes.wrappers.fep;

import cl.bbr.core.classes.basics.SearchCriterion;

public class FoundAttribSearchInfo
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public FoundAttribSearchInfo(SearchCriterion criteria, String parameter)
	{
		super();
		this.criteria = criteria;
		this.parameter = parameter;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	public SearchCriterion getCriteria()
	{
		return criteria;
	}

	public void setCriteria(SearchCriterion criteria)
	{
		this.criteria = criteria;
	}

	public String getParameter()
	{
		return parameter;
	}

	public void setParameter(String parameter)
	{
		this.parameter = parameter;
	}

	private SearchCriterion	criteria	= null;
	private String			parameter	= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================
}
