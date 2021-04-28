package bbr.b2b.portal.components.popups;

import java.awt.image.BufferedImage;

import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.constants.BbrPublishingConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.ImagesBufferedUtils;
import bbr.b2b.portal.classes.utils.app.SelectedPopupUtils;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.PopUpReportDataDTO;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class WinPopupPreview extends BbrWindow
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long	serialVersionUID	= -2720489231076965593L;

	private PopUpReportDataDTO	popup				= null;
	private SelectedPopupUtils	selectedPopupUtils	= new SelectedPopupUtils();
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public WinPopupPreview(BbrUI parent, PopUpReportDataDTO popup)
	{
		super(parent);
		this.popup = popup;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		if (popup != null)
		{
			this.selectedPopupUtils.setNotification(popup);
			PopupRenderer popupLayout = new PopupRenderer(this.getBbrUIParent(), popup);
			popupLayout.setHeight("110%");
			popupLayout.setWidth("100%");
			popupLayout.addStyleName("bbr-overflow-auto override");
			// Main Layout
			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.addComponents(popupLayout);
			mainLayout.setSizeFull();
			mainLayout.setMargin(false);
			mainLayout.setExpandRatio(popupLayout, 1F);

			if (this.popup.getPopuptemplatecode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TEMPLATE_2))
			{
				String windowCaption = (this.popup.getTitle() != null) ? I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "preview") + " - " + this.popup.getTitle()
						: I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "preview");
				this.setCaption(windowCaption);
			}
			else
			{
				this.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "preview"));
			}

			String width = "";
			String height = "";

			if (this.selectedPopupUtils.isPopupTemplateImage())
			{
				if (this.selectedPopupUtils.isValidHtmlPathForImage())
				{
					int marginHeight = 33;
					int marginWidth = 25;
					int checkAcceptSpace = 44;
					int minWidth = 400;
					String pixelUnit = "px";

					BufferedImage bImage = ImagesBufferedUtils.getBufferedImageFromFile(this.popup.getHtmlcontent());
					width = (Integer.valueOf(bImage.getWidth()) < minWidth) ? minWidth + pixelUnit : (Integer.valueOf(bImage.getWidth()) + marginWidth + pixelUnit);
					if (this.selectedPopupUtils.isPopupTypeInf())
					{
						height = Integer.valueOf(bImage.getHeight()) + marginHeight + pixelUnit;
					}
					else if (this.selectedPopupUtils.isPopupTypeAudit())
					{
						height = Integer.valueOf(bImage.getHeight()) + marginHeight + checkAcceptSpace + pixelUnit;
					}
				}
				else
				{
					width = "600px";
					height = "300px";
					this.addStyleName("bbr-margin-windows");
				}

			}
			else
			{
				width = "600px";
				height = "500px";
				this.addStyleName("bbr-margin-windows");
			}

			this.setContent(mainLayout);
			this.setWidth(width);
			this.setHeight(height);
			this.setResizable(false);

		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
}
