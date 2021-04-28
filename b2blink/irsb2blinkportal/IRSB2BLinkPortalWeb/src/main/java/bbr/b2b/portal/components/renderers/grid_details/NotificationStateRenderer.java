package bbr.b2b.portal.components.renderers.grid_details;

import com.vaadin.server.Resource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;

import bbr.b2b.portal.classes.constants.BbrPublishingConstants;
import bbr.b2b.users.report.classes.PublishingReportDataDTO;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.components.basics.BbrUI;

public class NotificationStateRenderer extends HorizontalLayout
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1226678720939602529L;
	private BbrUI bbrUIParent;

	public NotificationStateRenderer(BbrUI bbrUIParent, PublishingReportDataDTO item) 
	{
		this.bbrUIParent = bbrUIParent;
		if(item != null)
		{
			if(item != null && item.getStatecode() != null) 
			{
				Resource res = null;
				if(item.getStatecode().equals(BbrPublishingConstants.NOTIFICATION_ACTIVE_STATE))
				{
					res = BbrThemeResourcesUtils.getInstance().getResource(this.bbrUIParent,CoreConstants.PATH_BASE_IMAGES_UTILS+"icon_Green.png");
				}
				else if(item.getStatecode().equals(BbrPublishingConstants.NOTIFICATION_INACTIVE_STATE))
				{
					res = BbrThemeResourcesUtils.getInstance().getResource(this.bbrUIParent,CoreConstants.PATH_BASE_IMAGES_UTILS+"icon_Red.png");
				}
				else if(item.getStatecode().equals(BbrPublishingConstants.NOTIFICATION_EXPIRED_STATE))
				{
					res = BbrThemeResourcesUtils.getInstance().getResource(this.bbrUIParent,CoreConstants.PATH_BASE_IMAGES_UTILS+"icon_Yellow.png");
				}
				
				Image image = new Image(null, res);
				image.setDescription(item.getState());
				this.addComponent(image);
				this.setMargin(false);
				this.markAsDirtyRecursive();
			}
		}
	}
}
