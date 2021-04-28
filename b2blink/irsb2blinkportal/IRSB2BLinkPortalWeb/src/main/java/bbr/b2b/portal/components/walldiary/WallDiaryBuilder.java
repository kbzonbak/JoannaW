package bbr.b2b.portal.components.walldiary;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Component;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.components.basics.BbrUI;

public class WallDiaryBuilder
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public WallDiaryBuilder()
	{

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
	public static Accordion buildStatisticPanel(BbrUI parentUI)
	{
		Accordion dashboardPanels = new Accordion();

		dashboardPanels.addStyleName("dashboard-statistic-panel");
		Responsive.makeResponsive(dashboardPanels);

		Component faqs = buildFaqs(parentUI);
		Component companyUsers = buildCompanyUsers(parentUI);
//		Component lastLoadDate = buildLastLoadDates(parentUI);

		dashboardPanels.addTab(companyUsers, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, "company_users"), VaadinIcons.USER);
//		dashboardPanels.addTab(lastLoadDate, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, "last_load_date"), VaadinIcons.CALENDAR_O);
		dashboardPanels.addTab(faqs, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, "faqs"), VaadinIcons.QUESTION);
		dashboardPanels.setSelectedTab(companyUsers);

		return dashboardPanels;
	}

	public static Component buildTicketsPanel(BbrUI parentUI)
	{
		TicketsPanel tickets = new TicketsPanel(parentUI);
		if (tickets.getComponentCount() > 0)
		{

			tickets.setHeight("100%");
			tickets.addStyleName("tickets-panel");
			Responsive.makeResponsive(tickets);
		}
		else
		{
			tickets = null;
		}
		return tickets;
	}

	public static Component buildNotificationsPanel(BbrUI parentUI)
	{
		NotificationPanel notification = new NotificationPanel(parentUI);

		notification.setHeight("100%");
		notification.addStyleName("notification-panel");

		return notification;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private static Component buildFaqs(BbrUI parentUI)
	{
		FaqsPanel faqsPanel = new FaqsPanel(parentUI);

		faqsPanel.setHeight("100%");
		faqsPanel.addStyleName("faqs-panel");
		faqsPanel.addStyleName("bbr-overflow-auto");
		return faqsPanel;
	}

	private static Component buildCompanyUsers(BbrUI parentUI)
	{
		CompanyUsersPanel companyUsersPanel = new CompanyUsersPanel(parentUI);

		companyUsersPanel.setHeight("100%");
		companyUsersPanel.addStyleName("faqs-panel");
		companyUsersPanel.addStyleName("bbr-overflow-auto");
		return companyUsersPanel;
	}

//	private static Component buildLastLoadDates(BbrUI parentUI)
//	{
//		LastLoadDatePanel lastLoadDatePanel = new LastLoadDatePanel(parentUI);
//
//		lastLoadDatePanel.setHeight("100%");
//		lastLoadDatePanel.addStyleName("faqs-panel");
//		lastLoadDatePanel.addStyleName("last-load-date-panel");
//
//		return lastLoadDatePanel;
//	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
}
