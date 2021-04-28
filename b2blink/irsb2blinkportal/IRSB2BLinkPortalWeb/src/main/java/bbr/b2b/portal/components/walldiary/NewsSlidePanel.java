package bbr.b2b.portal.components.walldiary;

import java.io.Serializable;

import com.vaadin.shared.ui.Orientation;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.constants.BbrPublishingConstants;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.users.report.classes.PublishingReportDataDTO;
import bbr.b2b.users.report.classes.PublishingReportResultDTO;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.components.basics.BbrObjectList;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.slidepanel.BbrSlidePanel;
import cl.bbr.core.components.widgets.slidepanel.model.BbrSlidePanelChangeEvent;
import cl.bbr.core.components.widgets.slidepanel.model.BbrSlidePanelChangeEventListener;

public class NewsSlidePanel extends CustomComponent
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long	serialVersionUID	= -3547137606474888716L;
	private BbrSlidePanel		newsContent			= null;
	private BbrObjectList		listButtons			= null;
	private boolean				isProcedure			= false;
	private BbrUI				bbrUI				= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public NewsSlidePanel(BbrUI bbrUI)
	{
		this.bbrUI = bbrUI;
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
		PublishingReportDataDTO[] activeNews = this.getActiveNews();
		Component[] newsComponents = this.getNewsPanels(activeNews);
		newsContent = new BbrSlidePanel();
		newsContent.setSizeFull();
		newsContent.addStyleName("bbr-overflow-auto override");

		if (newsComponents != null && newsComponents.length > 0)
		{
			newsContent.addSlides(newsComponents);
			newsContent.setAutoSlide(true);
			newsContent.addBbrSlidePanelChangeEventListener(new BbrSlidePanelChangeEventListener()
			{
				private static final long serialVersionUID = -7441104711247956386L;

				@Override
				public void bbrSlidePanelChangeEvent_handler(BbrSlidePanelChangeEvent event)
				{
					listButtons.selectItem(event.getResultComponent());
				}
			}, BbrSlidePanelChangeEvent.SLIDE_CHANGED);
		}

		HorizontalLayout footerBar = this.getFooterBar(newsComponents);
		footerBar.setHeight("30px");

		footerBar.addStyleName("main-news-footerbar");

		VerticalLayout mainLayout = new VerticalLayout();

		mainLayout.addComponents(newsContent, footerBar);
		mainLayout.setSizeFull();

		mainLayout.setExpandRatio(newsContent, 1F);
		mainLayout.setMargin(false);
		mainLayout.setStyleName("news-slider-content");

		this.setSizeFull();
		this.setCompositionRoot(mainLayout);
	}

	private Component[] getNewsPanels(PublishingReportDataDTO[] news)
	{
		Component[] result = null;
		if (news != null && news.length > 0)
		{
			result = new Component[news.length];
			for (int i = 0; i < news.length; i++)
			{
				NewsRenderer newsLayout = new NewsRenderer(news[i]);
				newsLayout.setSizeFull();
				result[i] = newsLayout;

			}
		}

		return result;
	}

	private HorizontalLayout getFooterBar(Component[] activeNews)
	{
		HorizontalLayout result = new HorizontalLayout();
		result.setWidth("100%");

		if (activeNews != null && activeNews.length > 0)
		{
			listButtons = new BbrObjectList(Orientation.HORIZONTAL, activeNews);

			listButtons.addStyleName("bbr-object-list");
			listButtons.addBbrEventListener((BbrEventListener & Serializable) e -> newsManualSelected_handler(e));
			result.addComponent(listButtons);
			result.setComponentAlignment(listButtons, Alignment.MIDDLE_CENTER);
		}

		return result;
	}

	private void newsManualSelected_handler(BbrEvent e)
	{
		if (e.getResultObject() != null)
		{
			newsContent.showSlide((Component) e.getResultObject());
		}
	}

	private PublishingReportDataDTO[] getActiveNews()
	{
		PublishingReportDataDTO[] result = null;

		try
		{
			PublishingReportResultDTO newsResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.bbrUI)
					.getActivePublishingsByUserAndType(this.bbrUI.getUser().getId(), BbrPublishingConstants.NEWS_PUBLISHING_CODE, isProcedure);
			if (newsResult != null && newsResult.getPublishingdata() != null)
			{
				result = newsResult.getPublishingdata();
			}
		}
		catch (BbrUserException ex)
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), this.bbrUI);
		}
		catch (BbrSystemException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;

	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
