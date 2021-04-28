package bbr.b2b.portal.classes.wrappers.customer;

import bbr.b2b.customer.report.classes.PendingReprocessInventoryDTO;
import bbr.b2b.customer.report.classes.PendingReprocessSalesDTO;

public class ControlPanelCardInfo
{
	private String							selectedPanel				= null;
	private String							caption						= null;
	private Long							clientId					= null;
	private String							name						= null;
	private String							value						= null;
	private boolean							late						= false;

	private PendingReprocessSalesDTO[]		pendingReprocessSales		= null;
	private PendingReprocessInventoryDTO[]	pendingReprocessInventory	= null;

	public PendingReprocessSalesDTO[] getPendingReprocessSales()
	{
		return pendingReprocessSales;
	}

	public void setPendingReprocessSales(PendingReprocessSalesDTO[] pendingReprocessSales)
	{
		this.pendingReprocessSales = pendingReprocessSales;
	}

	public PendingReprocessInventoryDTO[] getPendingReprocessInventory()
	{
		return pendingReprocessInventory;
	}

	public void setPendingReprocessInventory(PendingReprocessInventoryDTO[] pendingReprocessInventory)
	{
		this.pendingReprocessInventory = pendingReprocessInventory;
	}

	public String getSelectedPanel()
	{
		return selectedPanel;
	}

	public void setSelectedPanel(String selectedPanel)
	{
		this.selectedPanel = selectedPanel;
	}

	public String getCaption()
	{
		return caption;
	}

	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	public Long getClientId()
	{
		return clientId;
	}

	public void setClientId(Long clientId)
	{
		this.clientId = clientId;
	}

	public boolean isLate()
	{
		return late;
	}

	public void setLate(boolean late)
	{
		this.late = late;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
