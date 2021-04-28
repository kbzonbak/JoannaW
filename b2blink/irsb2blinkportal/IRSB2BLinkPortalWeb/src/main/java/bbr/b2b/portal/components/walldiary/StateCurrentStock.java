package bbr.b2b.portal.components.walldiary;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.components.basics.BbrUI;

public class StateCurrentStock extends VerticalLayout
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long	serialVersionUID	= 422110660891507871L;

	private BbrUI				bbrUI;
	private double				stockmc;

	private DecimalFormat		df					= new DecimalFormat("MM $ 0.0", NumericManager.getSymbols());

	private Label				lblDate				= null;
	private Label				lblData				= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	public StateCurrentStock(BbrUI bbrUI, double stockmc)
	{
		this.bbrUI = bbrUI;
		this.stockmc = stockmc;
		this.initializeControl();

	}

	private void initializeControl()
	{
		if (bbrUI != null && bbrUI.getUser() != null)
		{
			Component toolBar = this.getToolBar();

			Resource res = BbrThemeResourcesUtils.getInstance().getResource(bbrUI, CoreConstants.PATH_BASE_IMAGES_WALLDIARY + "state_current_day_stock.png");
			Image imgData = new Image(null, res);
			imgData.setStyleName("walldiary-stats-image");

			lblDate = new Label();
			lblDate.addStyleName("walldiary-stats-title");
			lblDate.setHeight("20px");

			lblData = new Label();
			lblData.addStyleName("walldiary-stats-data");

			VerticalLayout pnlData = new VerticalLayout();
			pnlData.setSizeFull();
			pnlData.setMargin(false);
			pnlData.addComponents(lblDate, lblData);
			pnlData.setComponentAlignment(lblDate, Alignment.TOP_LEFT);
			pnlData.setComponentAlignment(lblData, Alignment.MIDDLE_LEFT);
			pnlData.setExpandRatio(lblData, 1F);

			HorizontalLayout content = new HorizontalLayout(pnlData, imgData);
			content.setExpandRatio(pnlData, 1F);
			content.setComponentAlignment(pnlData, Alignment.MIDDLE_LEFT);
			content.setComponentAlignment(imgData, Alignment.MIDDLE_RIGHT);
			content.setSizeFull();
			content.setMargin(false);

			this.addComponents(toolBar, content);
			this.setSpacing(false);
			this.setExpandRatio(content, 1);
			this.setSizeFull();
			this.addStyleName("notification-panel");
			Responsive.makeResponsive(this);
		}

	}

	private HorizontalLayout getToolBar()
	{
		HorizontalLayout result = new HorizontalLayout();
		result.addStyleName("stats-header");
		result.setWidth("100%");

		Label headerCaption = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_WALLDIARY, "valued_stock"));

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

	public void updateData(double stockmc, LocalDateTime localDate)
	{
		if (localDate != null)
		{
			this.stockmc = stockmc;

			String date = localDate.format(DateTimeFormatter.ofPattern("dd-MM-YYYY").withLocale(bbrUI.getUser().getLocale()));
			String dataValue = String.valueOf(df.format(this.stockmc / 1000000));

			lblDate.setValue(date);
			lblData.setValue(dataValue);
		}
		else
		{
			lblDate.setValue("");
			lblData.setValue("");
		}

	}
}
