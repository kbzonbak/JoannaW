package bbr.b2b.portal.classes.wrappers.customer;

import java.time.LocalDateTime;

import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class RequestReportPeriodFilterSectionInfo
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public RequestReportPeriodFilterSectionInfo(SearchCriterion dateOption, LocalDateTime startDate, LocalDateTime endDate, BbrTextField refrencenumeber, BbrTextField requestNumber)
	{
		super();
		this.selectedStartDate = startDate;
		this.selectedEndDate = endDate;
		this.setDateOption(dateOption);
		this.setSelectedRequestNumber(requestNumber);
		this.setSelectedRefrencenumeber(refrencenumeber);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private LocalDateTime	selectedStartDate		= null;
	private LocalDateTime	selectedEndDate			= null;
	private SearchCriterion	dateOption				= null;
	private BbrTextField	selectedRefrencenumeber	= null;
	private BbrTextField	selectedRequestNumber	= null;

	public BbrTextField getSelectedRefrencenumeber()
	{
		return selectedRefrencenumeber;
	}

	public void setSelectedRefrencenumeber(BbrTextField selectedRefrencenumeber)
	{
		this.selectedRefrencenumeber = selectedRefrencenumeber;
	}

	public BbrTextField getSelectedRequestNumber()
	{
		return selectedRequestNumber;
	}

	public void setSelectedRequestNumber(BbrTextField selectedRequestNumber)
	{
		this.selectedRequestNumber = selectedRequestNumber;
	}

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
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

}
