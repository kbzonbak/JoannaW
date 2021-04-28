package bbr.b2b.portal.classes.wrappers.generic;

import java.time.LocalDateTime;

public class ComparativePeriodFilterSectionInfo 
{
	
// =====================================================================================================================================
// BEGINNING SECTION 	---->			CONSTRUCTORS
// =====================================================================================================================================

	public ComparativePeriodFilterSectionInfo(LocalDateTime selectedBaseStartDate, LocalDateTime selectedBaseEndDate, LocalDateTime selectedToCompareStartDate, LocalDateTime selectedToCompareEndDate) 
	{
		super();
		this.selectedBaseStartDate = selectedBaseStartDate;
		this.selectedBaseEndDate = selectedBaseEndDate;
		this.selectedToCompareStartDate = selectedToCompareStartDate;
		this.selectedToCompareEndDate = selectedToCompareEndDate;
	}	

// =====================================================================================================================================
// ENDING SECTION 	---->			CONSTRUCTORS
// =====================================================================================================================================
	
	
// =====================================================================================================================================
// BEGINNING SECTION 	---->			PROPERTIES
// =====================================================================================================================================
	
	private LocalDateTime selectedBaseStartDate 		= null;
	private LocalDateTime selectedBaseEndDate 			= null;
	private LocalDateTime selectedToCompareStartDate 	= null;
	private LocalDateTime selectedToCompareEndDate 		= null;

	public LocalDateTime getSelectedBaseStartDate() 
	{
		return selectedBaseStartDate;
	}
	public void setSelectedBaseStartDate(LocalDateTime selectedBaseStartDate) 
	{
		this.selectedBaseStartDate = selectedBaseStartDate;
	}
	
	public LocalDateTime getSelectedBaseEndDate() 
	{
		return selectedBaseEndDate;
	}
	public void setSelectedBaseEndDate(LocalDateTime selectedBaseEndDate) 
	{
		this.selectedBaseEndDate = selectedBaseEndDate;
	}
	
	public LocalDateTime getSelectedToCompareStartDate() 
	{
		return selectedToCompareStartDate;
	}
	public void setSelectedToCompareStartDate(LocalDateTime selectedToCompareStartDate) 
	{
		this.selectedToCompareStartDate = selectedToCompareStartDate;
	}
	
	public LocalDateTime getSelectedToCompareEndDate() 
	{
		return selectedToCompareEndDate;
	}
	public void setSelectedToCompareEndDate(LocalDateTime selectedToCompareEndDate) 
	{
		this.selectedToCompareEndDate = selectedToCompareEndDate;
	}
	
// =====================================================================================================================================
// ENDING SECTION 		---->			PROPERTIES
// =====================================================================================================================================

}
