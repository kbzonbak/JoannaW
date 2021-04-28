package bbr.b2b.portal.classes.wrappers.customer;

import cl.bbr.core.classes.basics.SearchCriterion;

public class TypeAlertSectionInfo
{
	private SearchCriterion alertType;

	public TypeAlertSectionInfo(SearchCriterion alertType)
	{
		super();
		this.alertType = alertType;
	}

	public SearchCriterion getTypeSearch()
	{
		return alertType;
	}

	public void setTypeSearch(SearchCriterion AlertType)
	{
		this.alertType = AlertType;
	}
}
