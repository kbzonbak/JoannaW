package bbr.b2b.portal.components.popups;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.users.report.classes.PopUpReportDataDTO;
import cl.bbr.core.components.basics.BbrUI;

public class PopupSlidePanel extends CustomComponent
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long	serialVersionUID	= -3547137606474888716L;
	private PopUpReportDataDTO	activePopups		= null;
	private Component			popupsComponent		= null;
	private BbrUI				bbrUI				= null;
	private Runnable			executeAccept		= null;
	// private BbrSlidePanel popupsContent = null;
	// private Component[] popupsComponents = null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	public void setExecuteAccept(Runnable executeAccept)
	{
		this.executeAccept = executeAccept;
	}

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public PopupSlidePanel(BbrUI bbrUI, PopUpReportDataDTO activePopups)
	{
		this.bbrUI = bbrUI;
		this.activePopups = activePopups;
		this.initializeView();
	}

	public PopupSlidePanel(BbrUI bbrUI, PopUpReportDataDTO activePopups, Runnable executeAccept)
	{
		this.bbrUI = bbrUI;
		this.activePopups = activePopups;
		this.setExecuteAccept(executeAccept);
		this.initializeView();
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

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
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
	private void initializeView()
	{
		popupsComponent = this.getPopupsPanels(activePopups);
		popupsComponent.addStyleName("bbr-overflow-auto override");

		// popupsComponents = this.getPopupsPanels(activePopups);
		// COMENTADO HASTA QUE SE NECESITE SLIDES EN LOS POPUP
		// popupsContent = new BbrSlidePanel();
		// popupsContent.setSizeFull();
		// popupsContent.addStyleName("bbr-overflow-auto override");
		// // popupsContent.addSlides(popupsComponents);
		// popupsContent.setAutoSlide(true);
		// popupsContent.setAutoSlideTimeInMillis(15000);

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addComponents(popupsComponent);
		mainLayout.setExpandRatio(popupsComponent, 1F);
		mainLayout.setMargin(false);
		mainLayout.setSizeFull();

		this.setSizeFull();
		this.setCompositionRoot(mainLayout);

	}

	private Component getPopupsPanels(PopUpReportDataDTO popups)
	{
		Component result = null;
		PopupRenderer popupLayout = new PopupRenderer(this.bbrUI, popups, executeAccept);
		popupLayout.setSizeFull();
		result = popupLayout;
		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
