package bbr.b2b.portal.components.walldiary;

import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.components.basics.BbrUI;

public class StateCurrentStockDays extends VerticalLayout
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long	serialVersionUID	= 422110660891507871L;

	private BbrUI				bbrUI;
	private int					stockdays;

	private Label				lblData				= new Label();

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	public StateCurrentStockDays(BbrUI bbrUI, int stockdays)
	{
		this.bbrUI = bbrUI;
		this.stockdays = stockdays;

		this.initializeControl();
	}

	private void initializeControl()
	{
		if (bbrUI != null && bbrUI.getUser() != null)
		{
			Component toolBar = this.getToolBar();

			Resource res = BbrThemeResourcesUtils.getInstance().getResource(bbrUI, CoreConstants.PATH_BASE_IMAGES_WALLDIARY + "state_current_stock_days.png");
			Image imgData = new Image(null, res);
			imgData.addStyleName("walldiary-stats-image");

			lblData.addStyleName("walldiary-stats-data");

			VerticalLayout pnlData = new VerticalLayout();
			pnlData.setSizeFull();
			pnlData.setMargin(false);
			pnlData.addComponents(lblData);
			pnlData.setComponentAlignment(lblData, Alignment.MIDDLE_LEFT);

			HorizontalLayout content = new HorizontalLayout(pnlData, imgData);
			content.setExpandRatio(pnlData, 1F);
			content.setComponentAlignment(pnlData, Alignment.MIDDLE_LEFT);
			content.setComponentAlignment(imgData, Alignment.MIDDLE_RIGHT);
			content.setSizeFull();
			content.setMargin(false);
			
			this.addComponents(toolBar, content);
			this.setSpacing(true);
			this.setExpandRatio(content, 1);
			this.setSizeFull();
			this.addStyleName("notification-panel");
			Responsive.makeResponsive(this);

			

			this.updateData(this.stockdays);
		}

	}

	private HorizontalLayout getToolBar()
	{
		HorizontalLayout result = new HorizontalLayout();
		result.addStyleName("stats-header");
		result.setWidth("100%");

		Label headerCaption = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_WALLDIARY, "stock_days"));

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

	public void updateData(int stockdays)
	{
		this.stockdays = stockdays;

		String day = I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_WALLDIARY, "days");
		String dataValue = String.valueOf(stockdays);

		lblData.setValue(stockdays > 0 ? dataValue + " " + day : ""); 
	}
}
