package bbr.b2b.portal.modules.external;

import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;

public class QlikExternalModule extends BbrModule 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = 7027502352762624910L;

	private VerticalLayout mainLayout ;

	private String url;
	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	public QlikExternalModule(BbrUI bbrUIParent) 
	{
		super(bbrUIParent);
	}
	public QlikExternalModule(BbrUI bbrUIParent, String url) 
	{
		super(bbrUIParent);
		this.url = url;
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView() 
	{
		BbrUser user = this.getUser();
		if(user != null)
		{
			mainLayout = new VerticalLayout();
			mainLayout.addStyleName("report-layout-no-filter");
			mainLayout.setSizeFull();
			mainLayout.setMargin(false);
			BrowserFrame iframe = BbrUtils.getInstance().getExternalIFrame(this.url);
			mainLayout.addComponents(iframe);
			mainLayout.setExpandRatio(iframe, 1F);
	
			this.addComponents(mainLayout);
		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0016"));
		}
	}

	// ****************************************************************************************
	// ENDING SECTION 		---->			OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION 		---->			EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PRIVATE METHODS
	// ****************************************************************************************
	 
	// ****************************************************************************************
	// ENDING SECTION 		---->			PRIVATE METHODS
	// ****************************************************************************************
	


}
