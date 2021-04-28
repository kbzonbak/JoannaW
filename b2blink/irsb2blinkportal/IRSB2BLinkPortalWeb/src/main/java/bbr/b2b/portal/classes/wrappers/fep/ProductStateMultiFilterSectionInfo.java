package bbr.b2b.portal.classes.wrappers.fep;

import java.time.LocalDateTime;

import cl.bbr.core.classes.basics.SearchCriterion;

public class ProductStateMultiFilterSectionInfo
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductStateMultiFilterSectionInfo(SearchCriterion filterType, SearchCriterion status, String sku, LocalDateTime startDate, LocalDateTime endDate)
	{
		super();
		this.status = status;
		this.sku = sku;
		this.selectedStartDate = startDate;
		this.selectedEndDate = endDate;
		this.filterType = filterType;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private SearchCriterion	filterType			= null;
	private SearchCriterion	status				= null;
	private String				sku					= null;
	private LocalDateTime	selectedStartDate	= null;
	private LocalDateTime	selectedEndDate	= null;


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

	public SearchCriterion getFilterType()
	{
		return filterType;
	}


	public void setFilterType(SearchCriterion filterType)
	{
		this.filterType = filterType;
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
}
