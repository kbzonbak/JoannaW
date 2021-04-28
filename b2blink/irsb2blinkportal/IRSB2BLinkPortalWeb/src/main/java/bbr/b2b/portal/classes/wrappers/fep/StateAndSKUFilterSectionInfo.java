package bbr.b2b.portal.classes.wrappers.fep;

import cl.bbr.core.classes.basics.SearchCriterion;

public class StateAndSKUFilterSectionInfo
{
	private String		sku				= null;
	private SearchCriterion	status	= null;


	public StateAndSKUFilterSectionInfo(SearchCriterion status, String sku)
	{
		super();
		this.status = status;
		this.sku = sku;
	}


	public String getSku()
	{
		return sku;
	}


	public void setSku(String sku)
	{
		this.sku = sku;
	}


	public SearchCriterion getStatus()
	{
		return status;
	}


	public void setStatus(SearchCriterion status)
	{
		this.status = status;
	}
}
