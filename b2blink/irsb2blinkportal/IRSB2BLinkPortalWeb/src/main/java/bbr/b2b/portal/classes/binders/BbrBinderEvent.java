package bbr.b2b.portal.classes.binders;

import java.io.Serializable;

import bbr.b2b.fep.common.data.classes.DefinableAttributeDataDTO;

public class BbrBinderEvent<T> implements Serializable
{
	private static final long			serialVersionUID	= -11827715686046603L;

	public static final String			ITEMS_CHANGED		= "itemsChanged";
	public static final String			DYN_ITEMS_CHANGED	= "dynamic_ItemsChanged";

	private String						eventType;
	private DefinableAttributeDataDTO	attribute;
	private String						attributeValue;
	private T							resultObject;

	public String getEventType()
	{
		return eventType;
	}

	public void setEventType(String eventType)
	{
		this.eventType = eventType;
	}

	public T getResultObject()
	{
		return resultObject;
	}

	public void setResultObject(T resultObject)
	{
		this.resultObject = resultObject;
	}

	public BbrBinderEvent(String eventType)
	{
		this.setEventType(eventType);
	}

	public DefinableAttributeDataDTO getAttribute()
	{
		return attribute;
	}

	public void setAttribute(DefinableAttributeDataDTO attribute)
	{
		this.attribute = attribute;
	}

	public String getAttributeValue()
	{
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue)
	{
		this.attributeValue = attributeValue;
	}
	
}
