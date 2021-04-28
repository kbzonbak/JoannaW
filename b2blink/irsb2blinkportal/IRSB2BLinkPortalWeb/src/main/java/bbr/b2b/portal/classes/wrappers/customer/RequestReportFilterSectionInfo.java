package bbr.b2b.portal.classes.wrappers.customer;

import bbr.esb.services.webservices.facade.RequestFilterForTicketDTO;
import bbr.esb.services.webservices.facade.ServiceDTO;
import cl.bbr.core.classes.basics.SearchCriterion;

public class RequestReportFilterSectionInfo
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public RequestReportFilterSectionInfo(SearchCriterion filterType, RequestFilterForTicketDTO service)
	{
		super();
		this.service = service;
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

	private SearchCriterion				filterType	= null;
	private RequestFilterForTicketDTO	service		= null;

	public RequestFilterForTicketDTO getService()
	{
		return service;
	}

	public void setService(RequestFilterForTicketDTO service)
	{
		this.service = service;
	}

	public SearchCriterion getFilterType()
	{
		return filterType;
	}

	public void setFilterType(SearchCriterion filterType)
	{
		this.filterType = filterType;
	}

}
