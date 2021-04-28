package bbr.b2b.portal.modules.walldiary;

import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.components.walldiary.WallDiaryBuilder;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;

@Widgetset("cl.bbr.core.components.basics.sliderpanel.WidgetSet")
public class WallDiary extends BbrModule 
{
	public WallDiary(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
	}

	private static final long serialVersionUID = 4018799342649264571L;

	@Override
	public void initializeView() 
	{	
		//Top Panel
		Component notificationPanel = WallDiaryBuilder.buildNotificationsPanel(this.getBbrUIParent());
		
		HorizontalLayout topLeftLayout = new HorizontalLayout();
		topLeftLayout.addComponents(notificationPanel);
		topLeftLayout.setWidth("100%");
		topLeftLayout.setHeight("100%");
		topLeftLayout.setStyleName("bottom-left-layout");		
		Responsive.makeResponsive(topLeftLayout);
		
		//Bottom Panel
		VerticalLayout leftLayout = new VerticalLayout();
		leftLayout.addComponents(topLeftLayout);
		
		Component ticketsPanel = WallDiaryBuilder.buildTicketsPanel(this.getBbrUIParent());
		if (ticketsPanel != null)
		{
			ticketsPanel.setId("ticketsPanel");
			HorizontalLayout bottomLeftLayout = new HorizontalLayout();
			bottomLeftLayout.addComponents(ticketsPanel);
			bottomLeftLayout.setWidth("100%");
			bottomLeftLayout.setHeight("100%");
			bottomLeftLayout.setId("bottomLeftLayout");
			bottomLeftLayout.setStyleName("bottom-left-layout");
			Responsive.makeResponsive(bottomLeftLayout);
			leftLayout.addComponents(bottomLeftLayout);
			leftLayout.setExpandRatio(topLeftLayout, 0.7F);
			leftLayout.setExpandRatio(bottomLeftLayout, 0.3F);
		}
	
		leftLayout.setSizeFull();		
		leftLayout.setId("leftLayout");
		leftLayout.addStyleName("left-panel");
		leftLayout.setMargin(false);
		leftLayout.setSpacing(false);
		Responsive.makeResponsive(leftLayout);
		
		
		Accordion statisticPanel = WallDiaryBuilder.buildStatisticPanel(this.getBbrUIParent());
		statisticPanel.addStyleName("accordion-bar");
		statisticPanel.setWidth("200px");
		statisticPanel.setHeight("97%");
		
//		StatsPanel pnlRight = new StatsPanel(this.getBbrUIParent());
//		pnlRight.setWidth("300px");
//		pnlRight.setHeight("97%");
		
		statisticPanel.addStyleName("accordion-bar");
		statisticPanel.setWidth("0px");
		statisticPanel.setHeight("97%");
		
		HorizontalLayout mainLayout = new HorizontalLayout();
		mainLayout.addComponents(leftLayout, statisticPanel);
		mainLayout.setId("mainLayout");
		
		mainLayout.setComponentAlignment(statisticPanel, Alignment.TOP_CENTER);
		mainLayout.setExpandRatio(leftLayout, 1F);
		mainLayout.setResponsive(true);
	
		mainLayout.setSizeFull();
		this.addComponent(mainLayout);
		Responsive.makeResponsive(mainLayout);
	}
	
	
}
