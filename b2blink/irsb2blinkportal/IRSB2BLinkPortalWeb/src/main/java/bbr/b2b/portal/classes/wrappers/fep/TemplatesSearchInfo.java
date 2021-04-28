package bbr.b2b.portal.classes.wrappers.fep;

import com.vaadin.ui.CheckBox;

import cl.bbr.core.classes.basics.SearchCriterion;

public class TemplatesSearchInfo
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public TemplatesSearchInfo(SearchCriterion filterState, String value, CheckBox activeOption)
	{
		super();
		this.setFilterState(filterState);
		this.setValue(value);
		this.setActiveOption(activeOption);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private SearchCriterion	filterState		= null;
	private String			value			= null;
	private CheckBox		activeOption	= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	public SearchCriterion getFilterState()
	{
		return filterState;
	}

	public void setFilterState(SearchCriterion filterState)
	{
		this.filterState = filterState;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public CheckBox getActiveOption()
	{
		return activeOption;
	}

	public void setActiveOption(CheckBox activeOption)
	{
		this.activeOption = activeOption;
	}
}
