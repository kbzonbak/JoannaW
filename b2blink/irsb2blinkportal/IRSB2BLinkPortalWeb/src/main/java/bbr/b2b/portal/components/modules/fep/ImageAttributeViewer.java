package bbr.b2b.portal.components.modules.fep;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.cp.data.classes.CPItemAttributeValueDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.components.basics.BbrUI;

public class ImageAttributeViewer extends VerticalLayout
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long			serialVersionUID	= 8889869398476733801L;

	private CPItemAttributeValueDTO	image					= null;
	private BbrUI							parentUI				= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public ImageAttributeViewer(BbrUI parentUI, CPItemAttributeValueDTO image)
	{
		super();
		this.image = image;
		this.parentUI = parentUI;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************
	public VerticalLayout getLayout()
	{
		this.initializeView();
		return this;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private void initializeView()
	{
		if (this.image != null && this.image.getValue() != null)
		{
			// Sección Header
			Label lblTitle = new Label(this.image.getAttributevendorname());
			lblTitle.addStyleName("bold_style");

			HorizontalLayout pnlHeader = new HorizontalLayout();
			pnlHeader.setWidth("100%");
			pnlHeader.setHeight("30px");
			pnlHeader.setMargin(false);
			pnlHeader.addStyleName("data-panel");
			pnlHeader.addComponent(lblTitle);
			this.addComponent(pnlHeader);

			Label lblName = new Label(I18NManager.getI18NString(this.parentUI, BbrUtilsResources.RES_MODULES_FEP, "file_name"));
			lblName.addStyleName("data-panel-name-label");
			lblName.setWidth("120px");
			lblName.addStyleName("truncate");

			ExternalResource resource = new ExternalResource(this.image.getMetadata() != null ? this.image.getMetadata() 
					: "sin imagen");
			Link lblValue = new Link(this.image.getValue(), resource);
			lblValue.setTargetName("_blank");
			lblValue.addStyleName("data-panel-name-value");
			lblValue.setSizeFull();

			Image imgImagen = new Image();
			imgImagen.setSource(this.image.getMetadata() != null ? resource 
					:BbrThemeResourcesUtils.getInstance().getResource(this.parentUI, CoreConstants.PATH_BASE_IMAGES_UTILS + "no_image.jpg"));
			imgImagen.setWidth("100%");
			imgImagen.setHeight("150px");
			imgImagen.addStyleName("data-panel-image");

			HorizontalLayout pnlAttribute = new HorizontalLayout();
			pnlAttribute.setMargin(false);
			pnlAttribute.setSpacing(false);
			pnlAttribute.setWidth("100%");
			pnlAttribute.setHeight("100%");
			pnlAttribute.addStyleName("data-panel-row");
			pnlAttribute.addComponents(lblName, lblValue);
			pnlAttribute.setExpandRatio(lblValue, 1F);

			HorizontalLayout pnlImage = new HorizontalLayout();
			pnlImage.setMargin(true);
			pnlImage.setSpacing(false);
			pnlImage.setWidth("100%");
			pnlImage.setHeight("100%");
			pnlImage.addStyleName("data-panel-row");
			pnlImage.addComponents(imgImagen);
			pnlImage.setComponentAlignment(imgImagen, Alignment.MIDDLE_CENTER);
			pnlImage.setExpandRatio(imgImagen, 1F);
			
			this.addComponents(pnlAttribute, pnlImage);
			this.setComponentAlignment(pnlImage, Alignment.MIDDLE_CENTER);
			this.setMargin(false);
			this.setSpacing(false);
			this.setId("pnl_id");
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
