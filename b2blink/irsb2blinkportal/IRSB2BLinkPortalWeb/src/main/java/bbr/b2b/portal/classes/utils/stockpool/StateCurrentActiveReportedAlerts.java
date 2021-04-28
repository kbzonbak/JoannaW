package bbr.b2b.portal.classes.utils.stockpool;

import java.io.Serializable;

import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import bbr.b2b.logistic.report.data.dto.IndicatorsResultDTO;
import bbr.b2b.portal.classes.constants.ModulesCodes;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.classes.ui.MainUI;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;

public class StateCurrentActiveReportedAlerts extends VerticalLayout
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long	serialVersionUID		= 422110660891507871L;

	private BbrUI				bbrUI;
	// private Object initParam = null;
	private Label				lblOversoldAlerts		= null;
	private Button				btn_OversoldAlerts		= null;
	private Label				lblNegativeUnitsAlerts	= null;
	private Button				btn_NegativeUnitsAlerts	= null;
	private Label				lblAllActiveAlerts		= null;
	private Button				btn_AllActiveAlerts		= null;
	private BbrModule			phatherBbrModule		= null;
	private static final String	COLORAZULSTOCKPOOL		= "v-label-colorazulStockPool";
	private VerticalLayout		pnlOversoldAlerts		= null;
	private VerticalLayout		pnlNegativeUnitsAlerts	= null;
	private VerticalLayout		pnlAllActiveAlerts		= null;

	// private String funcionatyCode = null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	public StateCurrentActiveReportedAlerts(BbrUI bbrUI, Object selectedVeVKPI, BbrModule phatherBbrModule)
	{
		this.bbrUI = bbrUI;
		this.initializeControl();
		// this.initParam = selectedVeVKPI;
		this.phatherBbrModule = phatherBbrModule;
	}

	private void initializeControl()
	{
		if (bbrUI != null && bbrUI.getUser() != null)
		{
			Component toolBar = this.getToolBar();

			// 1 Sobreventa de Clientes
			this.lblOversoldAlerts = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "obs_oversold_alerts"));
			this.lblOversoldAlerts.addStyleName("stats-title");

			this.btn_OversoldAlerts = new Button();
			this.btn_OversoldAlerts.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "view_details"));
			this.btn_OversoldAlerts.addClickListener((ClickListener & Serializable) e -> this.oversoldAlerts_clickHandler(e));
			this.btn_OversoldAlerts.addStyleName(BbrStyles.ACTION_BUTTON);
			this.btn_OversoldAlerts.addStyleName("action-button-fix");

			pnlOversoldAlerts = new VerticalLayout();
			pnlOversoldAlerts.addComponents(this.lblOversoldAlerts, this.btn_OversoldAlerts);
			pnlOversoldAlerts.setComponentAlignment(this.lblOversoldAlerts, Alignment.TOP_CENTER);
			pnlOversoldAlerts.setComponentAlignment(this.btn_OversoldAlerts, Alignment.MIDDLE_CENTER);
			pnlOversoldAlerts.setSizeFull();
			pnlOversoldAlerts.addStyleName("box-standard-panel");

			// 2 Stock Negativo
			this.lblNegativeUnitsAlerts = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "obs_negative_units_alerts"));
			this.lblNegativeUnitsAlerts.addStyleName("stats-title");

			this.btn_NegativeUnitsAlerts = new Button();
			this.btn_NegativeUnitsAlerts.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "view_details"));
			this.btn_NegativeUnitsAlerts.addClickListener((ClickListener & Serializable) e -> this.negativeUnitsAlerts_clickHandler(e));
			this.btn_NegativeUnitsAlerts.addStyleName(BbrStyles.ACTION_BUTTON);
			this.btn_NegativeUnitsAlerts.addStyleName("action-button-fix");

			pnlNegativeUnitsAlerts = new VerticalLayout();
			pnlNegativeUnitsAlerts.addComponents(this.lblNegativeUnitsAlerts, this.btn_NegativeUnitsAlerts);
			pnlNegativeUnitsAlerts.setComponentAlignment(this.lblNegativeUnitsAlerts, Alignment.TOP_CENTER);
			pnlNegativeUnitsAlerts.setComponentAlignment(this.btn_NegativeUnitsAlerts, Alignment.MIDDLE_CENTER);
			pnlNegativeUnitsAlerts.setSizeFull();
			pnlNegativeUnitsAlerts.addStyleName("box-standard-panel");

			// 3 Total Alertas Activas
			this.lblAllActiveAlerts = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "obs_all_active_alerts"));
			this.lblAllActiveAlerts.addStyleName("stats-title");

			this.btn_AllActiveAlerts = new Button();
			this.btn_AllActiveAlerts.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "view_details"));
			this.btn_AllActiveAlerts.addClickListener((ClickListener & Serializable) e -> this.allActiveAlerts_clickHandler(e));
			this.btn_AllActiveAlerts.addStyleName(BbrStyles.ACTION_BUTTON);
			this.btn_AllActiveAlerts.addStyleName("action-button-fix");

			pnlAllActiveAlerts = new VerticalLayout();
			pnlAllActiveAlerts.addComponents(this.lblAllActiveAlerts, this.btn_AllActiveAlerts);
			pnlAllActiveAlerts.setComponentAlignment(this.lblAllActiveAlerts, Alignment.TOP_CENTER);
			pnlAllActiveAlerts.setComponentAlignment(this.btn_AllActiveAlerts, Alignment.MIDDLE_CENTER);
			pnlAllActiveAlerts.setSizeFull();
			pnlAllActiveAlerts.addStyleName("box-standard-panel");

			// merge
			HorizontalLayout pnlTopData = new HorizontalLayout();
			pnlTopData.setSizeFull();
			pnlTopData.setMargin(false);
			pnlTopData.setSpacing(true);
			pnlTopData.addComponents(pnlAllActiveAlerts, pnlOversoldAlerts, pnlNegativeUnitsAlerts);
			pnlTopData.setComponentAlignment(pnlOversoldAlerts, Alignment.TOP_CENTER);
			pnlTopData.setComponentAlignment(pnlNegativeUnitsAlerts, Alignment.MIDDLE_CENTER);
			pnlTopData.setComponentAlignment(pnlAllActiveAlerts, Alignment.MIDDLE_CENTER);

			///// BOTTOM

			///// terminar

			VerticalLayout vAll = new VerticalLayout(pnlTopData/* , pnlBottomData */);
			vAll.setExpandRatio(pnlTopData, 1F);
			// vAll.setExpandRatio(pnlBottomData, 1F);
			vAll.setComponentAlignment(pnlTopData, Alignment.MIDDLE_LEFT);
			vAll.setComponentAlignment(pnlTopData, Alignment.MIDDLE_LEFT);
			vAll.setSizeFull();
			vAll.setMargin(false);

			HorizontalLayout content = new HorizontalLayout(vAll);
			content.setExpandRatio(vAll, 1F);
			content.setComponentAlignment(vAll, Alignment.MIDDLE_LEFT);
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

	private void allActiveAlerts_clickHandler(ClickEvent e)
	{
		try
		{
			MainUI main = (MainUI) this.phatherBbrModule.getBbrUIParent();
			main.showModuleFromAlertReport(ModulesCodes.STKP_ALERT, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "all_state"));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void oversoldAlerts_clickHandler(ClickEvent e)
	{
		try
		{
			MainUI main = (MainUI) this.phatherBbrModule.getBbrUIParent();
			main.showModuleFromAlertReport(ModulesCodes.STKP_ALERT, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "type_alert_oversold_client"));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void negativeUnitsAlerts_clickHandler(ClickEvent e)
	{
		try
		{
			MainUI main = (MainUI) this.phatherBbrModule.getBbrUIParent();
			main.showModuleFromAlertReport(ModulesCodes.STKP_ALERT, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "type_alert_stock_negative"));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// private void pendinHomolationUnits_clickHandler(ClickEvent e)
	// {
	// try
	// {
	// MainUI main = (MainUI) this.phatherBbrModule.getBbrUIParent();
	// main.showModuleFromProductWithoutHomologation(ModulesCodes.STKP_WITHOUT_HOMOLOG, "");
	// }
	// catch (Exception ex)
	// {
	// ex.printStackTrace();
	// }
	// }

	private HorizontalLayout getToolBar()
	{
		HorizontalLayout result = new HorizontalLayout();
		result.addStyleName("stats-header-StockPool");
		result.setWidth("100%");

		Label headerCaption = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "lbl_alert_actives"));

		headerCaption.addStyleName(ValoTheme.LABEL_H4);
		headerCaption.addStyleName(COLORAZULSTOCKPOOL);
		headerCaption.addStyleName(ValoTheme.LABEL_NO_MARGIN);

		MenuBar leftMenuBar = new MenuBar();
		leftMenuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		result.addComponents(leftMenuBar, headerCaption);
		result.setExpandRatio(headerCaption, 1);
		result.setComponentAlignment(leftMenuBar, Alignment.MIDDLE_LEFT);
		result.setComponentAlignment(headerCaption, Alignment.MIDDLE_CENTER);

		return result;
	}

	public void updateData(Object data, Object selectedVeVKPI)
	{
		// this.initParam = selectedVeVKPI;
		if (data != null)
		{

			IndicatorsResultDTO kpiData = (IndicatorsResultDTO) data;

			if (kpiData.getOversoldalerts() > 0)
			{
				this.pnlOversoldAlerts.setStyleName("box-red-panel");

			}
			else
			{

				this.pnlOversoldAlerts.setStyleName("box-blue-panel");
			}

			if (kpiData.getNegativeunitsalerts() > 0)
			{
				this.pnlNegativeUnitsAlerts.setStyleName("box-red-panel");

			}
			else
			{

				this.pnlNegativeUnitsAlerts.setStyleName("box-blue-panel");
			}

			if (kpiData.getAllactivealerts() > 0)
			{
				this.pnlAllActiveAlerts.setStyleName("box-yellow-panel");

			}
			else
			{

				this.pnlAllActiveAlerts.setStyleName("box-blue-panel");
			}

			this.btn_OversoldAlerts.setCaption((kpiData.getOversoldalerts() != null && kpiData.getOversoldalerts() > 0) ? NumericManager.getFormatter(0).format(kpiData.getOversoldalerts()) : "0");
			this.btn_OversoldAlerts.setEnabled((kpiData.getOversoldalerts() != null && kpiData.getOversoldalerts() > 0) ? true : false);

			this.btn_NegativeUnitsAlerts.setCaption((kpiData.getNegativeunitsalerts() != null && kpiData.getNegativeunitsalerts() > 0) ? NumericManager.getFormatter(0).format(kpiData.getNegativeunitsalerts()) : "0");
			this.btn_NegativeUnitsAlerts.setEnabled((kpiData.getNegativeunitsalerts() != null && kpiData.getNegativeunitsalerts() > 0) ? true : false);

			this.btn_AllActiveAlerts.setCaption((kpiData.getAllactivealerts() != null && kpiData.getAllactivealerts() > 0) ? NumericManager.getFormatter(0).format(kpiData.getAllactivealerts()) : "0");
			this.btn_AllActiveAlerts.setEnabled((kpiData.getAllactivealerts() != null && kpiData.getAllactivealerts() > 0) ? true : false);

		}
		else
		{
			this.btn_OversoldAlerts.setCaption("0");
			this.btn_OversoldAlerts.setEnabled(false);

			this.btn_NegativeUnitsAlerts.setCaption("0");
			this.btn_NegativeUnitsAlerts.setEnabled(false);

			this.btn_AllActiveAlerts.setCaption("0");
			this.btn_AllActiveAlerts.setEnabled(false);

		}
	}
}
