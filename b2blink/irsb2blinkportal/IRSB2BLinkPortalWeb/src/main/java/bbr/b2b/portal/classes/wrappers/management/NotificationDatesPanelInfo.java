package bbr.b2b.portal.classes.wrappers.management;

import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Panel;

public class NotificationDatesPanelInfo
{
	private Panel			pnlDates	= null;
	private DateTimeField	dfSinceDate	= null;
	private DateTimeField	dfUntilDate	= null;

	public Panel getPnlDates()
	{
		return pnlDates;
	}

	public void setPnlDates(Panel pnlDates)
	{
		this.pnlDates = pnlDates;
	}

	public DateTimeField getDfSinceDate()
	{
		return dfSinceDate;
	}

	public void setDfSinceDate(DateTimeField dfSinceDate)
	{
		this.dfSinceDate = dfSinceDate;
	}

	public DateTimeField getDfUntilDate()
	{
		return dfUntilDate;
	}

	public void setDfUntilDate(DateTimeField dfUntilDate)
	{
		this.dfUntilDate = dfUntilDate;
	}
}
