package bbr.b2b.portal.components.renderers.grid_details;

import com.vaadin.ui.Label;

import bbr.b2b.users.report.classes.ContactProviderPositionDTO;

public class PositionObligatoryLabelRenderer extends Label
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2229874206320610693L;

	public PositionObligatoryLabelRenderer(ContactProviderPositionDTO item) 
	{
		if(item != null)
		{
			if(item != null && item.getObligatory() != null) 
			{
				this.setCaption(item.getName());
				if (item.getObligatory())
				{
					this.addStyleName("red-words");
				}
				this.setWidth("100%");
			}
		}
	}

}