package bbr.b2b.portal.components.walldiary;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.components.basics.BbrUI;

public class NotificationPanel extends VerticalLayout
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long	serialVersionUID	= 422110660891507871L;
	private BbrUI				bbrUI;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	public NotificationPanel(BbrUI bbrUI)
	{
		this.bbrUI = bbrUI;
		initializeControl();
	}

	private void initializeControl()
	{
		Component toolBar = this.getToolBar();
		Component content = new NewsSlidePanel(this.bbrUI);
		this.addComponents(toolBar, content);
		this.setExpandRatio(content, 1);

	}

	private HorizontalLayout getToolBar()
	{
		HorizontalLayout result = new HorizontalLayout();
		result.addStyleName("news-toolbar");
		result.setWidth("100%");

		Label headerCaption = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, "news"));
		headerCaption.addStyleName(ValoTheme.LABEL_H4);
		headerCaption.addStyleName(ValoTheme.LABEL_COLORED);
		headerCaption.addStyleName(ValoTheme.LABEL_NO_MARGIN);

		MenuBar leftMenuBar = new MenuBar();
		leftMenuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		result.addComponents(leftMenuBar, headerCaption);
		result.setExpandRatio(headerCaption, 1);
		result.setComponentAlignment(leftMenuBar, Alignment.MIDDLE_LEFT);
		result.setComponentAlignment(headerCaption, Alignment.MIDDLE_LEFT);

		return result;
	}
}
