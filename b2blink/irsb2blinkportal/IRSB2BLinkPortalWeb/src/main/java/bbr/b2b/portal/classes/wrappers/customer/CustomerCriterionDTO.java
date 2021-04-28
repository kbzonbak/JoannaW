package bbr.b2b.portal.classes.wrappers.customer;

import java.time.LocalDateTime;

import bbr.esb.services.webservices.facade.SiteDTO;
import cl.bbr.core.classes.basics.SearchCriterion;

public class CustomerCriterionDTO
{

	private Long			orderNumber;
	private LocalDateTime	startDateTime;
	private LocalDateTime	endDateTime;
	private SearchCriterion	typeSearch;
	private SearchCriterion	orderState;
	private SiteDTO			site;

	public CustomerCriterionDTO(SearchCriterion typeSearch, Long orderNumber, LocalDateTime startDateTime, LocalDateTime endDateTime, SearchCriterion orderState, SiteDTO site)
	{
		super();
		this.typeSearch = typeSearch;
		this.orderNumber = orderNumber;
		this.orderState = orderState;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.site = site;
	}

	public SearchCriterion getTypeSearch()
	{
		return typeSearch;
	}

	public void setTypeSearch(SearchCriterion typeSearch)
	{
		this.typeSearch = typeSearch;
	}

	public LocalDateTime getStartDateTime()
	{
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime)
	{
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime()
	{
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime)
	{
		this.endDateTime = endDateTime;
	}

	public Long getOrderNumber()
	{
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	public SearchCriterion getOrderState()
	{
		return orderState;
	}

	public void setOrderState(SearchCriterion orderState)
	{
		this.orderState = orderState;
	}

	public SiteDTO getSite()
	{
		return site;
	}

	public void setSite(SiteDTO site)
	{
		this.site = site;
	}

}
