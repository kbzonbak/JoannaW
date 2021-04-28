package bbr.b2b.portal.classes.wrappers.logistic;

import java.time.LocalDateTime;

import bbr.b2b.logistic.buyorders.data.dto.OrderStateTypeDTO;

public class PucharseOrderCriterion
{

	private Long					numberValue;
	private OrderStateTypeDTO		orderState;
	private LocalDateTime			startDateTime;
	private LocalDateTime			endDateTime;
	private Integer					typeSearch;

	public PucharseOrderCriterion(Integer typeSearch, Long numberValue, OrderStateTypeDTO orderState, LocalDateTime startDateTime, LocalDateTime endDateTime)
	{
		super();
		this.typeSearch = typeSearch;
		this.numberValue = numberValue;
		this.orderState = orderState;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public Integer getTypeSearch()
	{
		return typeSearch;
	}

	public void setTypeSearch(Integer typeSearch)
	{
		this.typeSearch = typeSearch;
	}

	public OrderStateTypeDTO getOrderState()
	{
		return orderState;
	}

	public void setOrderState(OrderStateTypeDTO orderState)
	{
		this.orderState = orderState;
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

	public Long getNumberValue()
	{
		return numberValue;
	}

	public void setNumberValue(Long numberValue)
	{
		this.numberValue = numberValue;
	}

}
