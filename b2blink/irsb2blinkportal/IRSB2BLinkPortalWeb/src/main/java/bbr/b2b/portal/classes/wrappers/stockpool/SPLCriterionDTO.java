package bbr.b2b.portal.classes.wrappers.stockpool;

import cl.bbr.core.classes.basics.SearchCriterion;

public class SPLCriterionDTO
{

	private String			sku;
	private SearchCriterion	state;
	private SearchCriterion typeSearch;

	public SPLCriterionDTO(SearchCriterion typeSearch, String sku, SearchCriterion state)
	{
		super();
		this.typeSearch = typeSearch;
		this.sku = sku;
		this.state = state;
	}

	public String getSku()
	{
		return sku;
	}

	public void setSku(String sku)
	{
		this.sku = sku;
	}

	public SearchCriterion getState()
	{
		return state;
	}

	public void setState(SearchCriterion state)
	{
		this.state = state;
	}

	public SearchCriterion getTypeSearch()
	{
		return typeSearch;
	}

	public void setTypeSearch(SearchCriterion typeSearch)
	{
		this.typeSearch = typeSearch;
	}
	
	

	

}
