package bbr.b2b.portal.components.renderers.grid_details;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.users.report.classes.ContactProviderPositionDTO;

public class PositionObligatoryRenderer extends HorizontalLayout
{
	private static final long serialVersionUID = 3226437034167344169L;

	public PositionObligatoryRenderer(ContactProviderPositionDTO item, boolean redMarked)
	{
		if (item != null)
		{
			Label caption = new Label();
			caption.setCaption(item.getName());
			if (item.getObligatory() != null)
			{
				if (item.getObligatory() && redMarked)
				{
					this.addStyleNames("red-positions-text");
				}
			}
			this.addStyleName("margin-top-5");
			this.setMargin(false);
			this.addComponent(caption);
			this.setExpandRatio(caption, 1F);
			this.setWidth("100%");
			this.markAsDirty();
		}
	}
}
