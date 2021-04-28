package bbr.b2b.portal.classes.wrappers.customer;

import cl.bbr.core.classes.basics.SearchCriterion;

public class AlertStateSectionInfo
{
	private SearchCriterion typeState;

	public AlertStateSectionInfo(SearchCriterion typeSearch)
	{
		super();
		this.typeState = typeSearch;
	}

	public SearchCriterion getTypeSearch()
	{
		return typeState;
	}

	public void setTypeSearch(SearchCriterion typeSearch)
	{
		this.typeState = typeSearch;
	}
}
