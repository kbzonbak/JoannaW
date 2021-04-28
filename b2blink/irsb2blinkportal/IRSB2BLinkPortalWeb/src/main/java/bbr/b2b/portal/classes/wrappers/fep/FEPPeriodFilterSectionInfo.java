package bbr.b2b.portal.classes.wrappers.fep;

import java.time.LocalDateTime;

import cl.bbr.core.classes.basics.SearchCriterion;

public class FEPPeriodFilterSectionInfo
{

// =====================================================================================================================================
// BEGINNING SECTION 	---->			CONSTRUCTORS
// =====================================================================================================================================

	public FEPPeriodFilterSectionInfo(SearchCriterion dateOption, LocalDateTime startDate, LocalDateTime endDate)
	{
		super();
		this.selectedStartDate = startDate;
		this.selectedEndDate = endDate;
		this.setDateOption(dateOption);
	}

// =====================================================================================================================================
// ENDING SECTION 	---->			CONSTRUCTORS
// =====================================================================================================================================

// =====================================================================================================================================
// BEGINNING SECTION 	---->			PROPERTIES
// =====================================================================================================================================

	private LocalDateTime	selectedStartDate	= null;
	private LocalDateTime	selectedEndDate		= null;
	private SearchCriterion	dateOption			= null;

	public LocalDateTime getSelectedStartDate()
	{
		return selectedStartDate;
	}

	public void setSelectedStartDate(LocalDateTime selectedStartDate)
	{
		this.selectedStartDate = selectedStartDate;
	}

	public LocalDateTime getSelectedEndDate()
	{
		return selectedEndDate;
	}

	public void setSelectedEndDate(LocalDateTime selectedEndDate)
	{
		this.selectedEndDate = selectedEndDate;
	}

	public SearchCriterion getDateOption()
	{
		return dateOption;
	}

	public void setDateOption(SearchCriterion dateOption)
	{
		this.dateOption = dateOption;
	}

// =====================================================================================================================================
// ENDING SECTION 		---->			PROPERTIES
// =====================================================================================================================================

}
