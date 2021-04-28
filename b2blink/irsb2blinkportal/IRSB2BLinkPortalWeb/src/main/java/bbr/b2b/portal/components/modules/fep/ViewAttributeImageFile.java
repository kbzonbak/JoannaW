package bbr.b2b.portal.components.modules.fep;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.AttributeDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class ViewAttributeImageFile extends BbrWindow
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long	serialVersionUID	= -8454585578751490L;

	private AttributeDTO		attribute			= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ViewAttributeImageFile(BbrUI parent, AttributeDTO attribute)
	{
		super(parent);
		this.attribute = attribute;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		// Main Layout
		Panel mainLayout = new Panel();
		mainLayout.setSizeFull();
		mainLayout.addStyleName("bbr-margin-windows");
		
		String winTitle = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_help_file"); 
		if(this.attribute != null && this.attribute.getFilename() != null)
		{
			Resource resource = new ExternalResource(attribute.getFilename());
		
			Image image = new Image(null, resource);
			
			VerticalLayout pnlImage = new VerticalLayout();
			pnlImage.addComponent(image);
			pnlImage.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
			
			
			mainLayout.setContent(pnlImage);
			
			winTitle += " - " + attribute.getInternalname();
		}
		// mainLayout.addComponents(pnl_GeneralGridsSpace, pnl_ButtonsSpace, pnl_RequireField);
		// mainLayout.setExpandRatio(pnl_GeneralGridsSpace, 1F);
		// Ventana
		this.setWidth("950px");
		this.setHeight("500px");
		this.setCaption(winTitle);
		this.setContent(mainLayout);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
