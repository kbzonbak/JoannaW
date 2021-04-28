package bbr.b2b.portal.classes.wrappers.generic;

import java.time.LocalDateTime;

public class PeriodFilterSectionInfo 
{
	
// =====================================================================================================================================
// BEGINNING SECTION 	---->			CONSTRUCTORS
// =====================================================================================================================================

	public PeriodFilterSectionInfo(LocalDateTime startDate, LocalDateTime endDate) 
	{
		super();
		this.selectedStartDate	= startDate;
		this.selectedEndDate 	= endDate;
	}

// =====================================================================================================================================
// ENDING SECTION 	---->			CONSTRUCTORS
// =====================================================================================================================================
	
	
// =====================================================================================================================================
// BEGINNING SECTION 	---->			PROPERTIES
// =====================================================================================================================================
	
	private LocalDateTime selectedStartDate = null;
	private LocalDateTime selectedEndDate 	= null;
	
	
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
	
// =====================================================================================================================================
// ENDING SECTION 		---->			PROPERTIES
// =====================================================================================================================================

}
