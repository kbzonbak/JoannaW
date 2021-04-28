package bbr.b2b.portal.components.renderers.grid_details;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;

import bbr.b2b.portal.classes.wrappers.fep.HelpFileValues;

public class MDMImageHelpFilesRender extends HorizontalLayout
{

	private static final long	serialVersionUID	= 5822928366369304679L;

	private int					maxHeight			= 95;
	private int					maxWidth			= 295;

	public MDMImageHelpFilesRender(HelpFileValues item)
	{
		if (item != null)
		{
			if (item != null && item.getFileName() != null)
			{
				Image image = new Image(null, item.getResFile());
				if (item.isImage()) // Si es una imagen
				{
					int height = item.getHeight();
					int width = item.getWidth();
					if (height > this.maxHeight)
					{
						image.setHeight(this.maxHeight, Unit.PIXELS);
						image.setStyleName("image-width-auto");
					}
					else if (width > maxWidth)
					{
						image.setWidth(this.maxWidth, Unit.PIXELS);
						image.setStyleName("image-height-auto");
					}
					else
					{
						image.setStyleName("image-full-auto");
					}
				}
				else
				{
					image.setHeight(this.maxHeight, Unit.PIXELS);
					image.setStyleName("image-width-auto");
				}
				image.addStyleName("image-layout");
				this.addComponent(image);
				this.markAsDirtyRecursive();
			}
		}
	}
}
