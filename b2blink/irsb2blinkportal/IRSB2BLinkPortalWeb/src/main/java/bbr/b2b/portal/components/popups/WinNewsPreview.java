package bbr.b2b.portal.components.popups;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.components.walldiary.NewsRenderer;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.PublishingReportDataDTO;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class WinNewsPreview extends BbrWindow {


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = -2720489231076965593L;

	private PublishingReportDataDTO news = null;
	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	public WinNewsPreview(BbrUI parent, PublishingReportDataDTO news) 
	{
		super(parent);
		this.news = news ;
	}

	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION 		---->			INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView() 
	{
		if(news != null)
		{
			NewsRenderer newsPanel = new NewsRenderer(news);
			//newsPanel.setSizeFull();
			// Main Layout
			Panel panel = new Panel();
			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.addComponent(newsPanel);
			mainLayout.addStyleName("bbr-margin-windows");
			mainLayout.setSizeUndefined();		
			panel.setContent(mainLayout);
			panel.setWidth("590px");
			panel.setHeight("460px");
			this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "preview"));
			this.setWidth("600px");
			this.setHeight("500px");
			this.setResizable(false);
			this.setContent(panel);	
		}
	}



	// ****************************************************************************************
	// ENDING SECTION 		---->			OVERRIDDEN METHODS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION 		---->			PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PRIVATE METHODS
	// ****************************************************************************************
	
	// ****************************************************************************************
	// ENDING SECTION 		---->			PRIVATE METHODS
	// ****************************************************************************************
}
