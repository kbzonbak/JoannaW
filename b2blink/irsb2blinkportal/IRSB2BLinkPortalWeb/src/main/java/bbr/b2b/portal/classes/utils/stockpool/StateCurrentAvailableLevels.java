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

public class StateCurrentAvailableLevels extends VerticalLayout
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long	serialVersionUID	= 422110660891507871L;

	private BbrUI				bbrUI;
	// private Object initParam = null;
	private Label				lblWithStock		= null;
	private Button				btn_WithStock		= null;
	private Label				lblCriticalStock	= null;
	private Button				btn_CriticalStock	= null;
	private Label				lblOutOfStock		= null;
	private Button				btn_OutOfStock		= null;
	private Label				lblNegativeStock	= null;
	private Button				btn_NegativeStock	= null;
	private BbrModule			phatherBbrModule	= null;
	private VerticalLayout		pnlWithStock		= null;
	private VerticalLayout		pnlCriticalStock	= null;
	private VerticalLayout		pnlNegativeStock	= null;
	private VerticalLayout		pnlOutOfStock		= null;

	private static final String	COLORAZULSTOCKPOOL	= "v-label-colorazulStockPool";
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	public StateCurrentAvailableLevels(BbrUI bbrUI, Object selectedVeVKPI, BbrModule phatherBbrModule)
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

			// 1 CON STOCK
			this.lblWithStock = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "obs_with_stock"));
			this.lblWithStock.addStyleName("stats-title");

			this.btn_WithStock = new Button();
			this.btn_WithStock.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "view_details"));
			this.btn_WithStock.addClickListener((ClickListener & Serializable) e -> this.withStock_clickHandler(e));
			this.btn_WithStock.addStyleName(BbrStyles.ACTION_BUTTON);
			this.btn_WithStock.addStyleName("action-button-fix");

			pnlWithStock = new VerticalLayout();
			pnlWithStock.addComponents(this.lblWithStock, this.btn_WithStock);
			pnlWithStock.setComponentAlignment(this.lblWithStock, Alignment.TOP_CENTER);
			pnlWithStock.setComponentAlignment(this.btn_WithStock, Alignment.BOTTOM_CENTER);
			pnlWithStock.setWidth("100%");
			pnlWithStock.setHeight("100%");
			pnlWithStock.addStyleName("box-standard-panel");
			pnlWithStock.setVisible(true);

			// 2 STOCK CRITICO
			this.lblCriticalStock = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "obs_critical_stock"));
			this.lblCriticalStock.addStyleName("stats-title");

			this.btn_CriticalStock = new Button();
			this.btn_CriticalStock.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "view_details"));
			this.btn_CriticalStock.addClickListener((ClickListener & Serializable) e -> this.criticalStock_clickHandler(e));
			this.btn_CriticalStock.addStyleName(BbrStyles.ACTION_BUTTON);
			this.btn_CriticalStock.addStyleName("action-button-fix");

			pnlCriticalStock = new VerticalLayout();
			pnlCriticalStock.addComponents(this.lblCriticalStock, this.btn_CriticalStock);
			pnlCriticalStock.setComponentAlignment(this.lblCriticalStock, Alignment.TOP_LEFT);
			pnlCriticalStock.setComponentAlignment(this.btn_CriticalStock, Alignment.BOTTOM_CENTER);
			pnlCriticalStock.setWidth("100%");
			pnlCriticalStock.setHeight("100%");
			pnlCriticalStock.addStyleName("box-standard-panel");

			// 3 SIN STOCK
			this.lblOutOfStock = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "obs_out_of_stock"));
			this.lblOutOfStock.addStyleName("stats-title");

			this.btn_OutOfStock = new Button();
			this.btn_OutOfStock.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "view_details"));
			this.btn_OutOfStock.addClickListener((ClickListener & Serializable) e -> this.outOfStock_clickHandler(e));
			this.btn_OutOfStock.addStyleName(BbrStyles.ACTION_BUTTON);
			this.btn_OutOfStock.addStyleName("action-button-fix");

			pnlOutOfStock = new VerticalLayout();
			pnlOutOfStock.addComponents(this.lblOutOfStock, this.btn_OutOfStock);
			pnlOutOfStock.setComponentAlignment(this.lblOutOfStock, Alignment.TOP_LEFT);
			pnlOutOfStock.setComponentAlignment(this.btn_OutOfStock, Alignment.BOTTOM_CENTER);
			pnlOutOfStock.setWidth("100%");
			pnlOutOfStock.setHeight("100%");
			pnlOutOfStock.addStyleName("box-standard-panel");

			// 4 stock negativo
			this.lblNegativeStock = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "obs_negative_stock"));
			this.lblNegativeStock.addStyleName("stats-title");

			this.btn_NegativeStock = new Button();
			this.btn_NegativeStock.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "view_details"));
			this.btn_NegativeStock.addClickListener((ClickListener & Serializable) e -> this.negativeStock_clickHandler(e));
			this.btn_NegativeStock.addStyleName(BbrStyles.ACTION_BUTTON);
			this.btn_NegativeStock.addStyleName("action-button-fix");

			pnlNegativeStock = new VerticalLayout();
			pnlNegativeStock.addComponents(this.lblNegativeStock, this.btn_NegativeStock);
			pnlNegativeStock.setComponentAlignment(this.lblNegativeStock, Alignment.TOP_LEFT);
			pnlNegativeStock.setComponentAlignment(this.btn_NegativeStock, Alignment.BOTTOM_CENTER);
			pnlNegativeStock.setWidth("100%");
			pnlNegativeStock.setHeight("100%");
			pnlNegativeStock.addStyleName("box-standard-panel");

			HorizontalLayout pnlData = new HorizontalLayout();
			pnlData.setSizeFull();
			pnlData.setMargin(false);
			pnlData.setSpacing(true);
			pnlData.addComponents(pnlWithStock, pnlCriticalStock, pnlOutOfStock, pnlNegativeStock);

			HorizontalLayout content = new HorizontalLayout(pnlData);
			content.setExpandRatio(pnlData, 1F);
			content.setComponentAlignment(pnlData, Alignment.MIDDLE_LEFT);
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

	private void negativeStock_clickHandler(ClickEvent e)
	{
		try
		{
			MainUI main = (MainUI) this.phatherBbrModule.getBbrUIParent();
			main.showModuleFromAvailableStock(ModulesCodes.STKP_STKA, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "negative_stock"));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void outOfStock_clickHandler(ClickEvent e)
	{
		try
		{
			MainUI main = (MainUI) this.phatherBbrModule.getBbrUIParent();
			main.showModuleFromAvailableStock(ModulesCodes.STKP_STKA, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "no_stock"));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void withStock_clickHandler(ClickEvent e)
	{
		try
		{
			MainUI main = (MainUI) this.phatherBbrModule.getBbrUIParent();
			main.showModuleFromAvailableStock(ModulesCodes.STKP_STKA, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "onstock"));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void criticalStock_clickHandler(ClickEvent e)
	{
		try
		{
			MainUI main = (MainUI) this.phatherBbrModule.getBbrUIParent();
			main.showModuleFromAvailableStock(ModulesCodes.STKP_STKA, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "critical_stock"));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private HorizontalLayout getToolBar()
	{
		HorizontalLayout result = new HorizontalLayout();
		result.addStyleName("stats-header-StockPool");
		result.setWidth("100%");

		Label headerCaption = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "lbl_availability"));

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

			if (kpiData.getNoncriticalstockskus() > 0)
			{
				this.pnlWithStock.setStyleName("box-blue-panel");

			}
			else
			{

				this.pnlWithStock.setStyleName("box-red-panel");
			}

			if (kpiData.getCriticalstockproducts() > 0)
			{
				this.pnlCriticalStock.setStyleName("box-yellow-panel");

			}
			else
			{

				this.pnlCriticalStock.setStyleName("box-blue-panel");
			}

			if (kpiData.getNostockproducts() > 0)
			{
				this.pnlOutOfStock.setStyleName("box-red-panel");

			}
			else
			{

				this.pnlOutOfStock.setStyleName("box-blue-panel");
			}

			if (kpiData.getNegativestock() > 0)
			{
				this.pnlNegativeStock.setStyleName("box-red-panel");

			}
			else
			{

				this.pnlNegativeStock.setStyleName("box-blue-panel");
			}

			this.btn_WithStock.setCaption((kpiData.getNoncriticalstockskus() != null && kpiData.getNoncriticalstockskus() > 0) ? NumericManager.getFormatter(0).format(kpiData.getNoncriticalstockskus()) : "0");
			this.btn_WithStock.setEnabled((kpiData.getNoncriticalstockskus() != null && kpiData.getNoncriticalstockskus() > 0) ? true : false);

			this.btn_CriticalStock.setCaption((kpiData.getCriticalstockproducts() != null && kpiData.getCriticalstockproducts() > 0) ? NumericManager.getFormatter(0).format(kpiData.getCriticalstockproducts()) : "0");
			this.btn_CriticalStock.setEnabled((kpiData.getCriticalstockproducts() != null && kpiData.getCriticalstockproducts() > 0) ? true : false);

			this.btn_OutOfStock.setCaption((kpiData.getNostockproducts() != null && kpiData.getNostockproducts() > 0) ? NumericManager.getFormatter(0).format(kpiData.getNostockproducts()) : "0");
			this.btn_OutOfStock.setEnabled((kpiData.getNostockproducts() != null && kpiData.getNostockproducts() > 0) ? true : false);

			this.btn_NegativeStock.setCaption((kpiData.getNegativestock() != null && kpiData.getNegativestock() > 0) ? NumericManager.getFormatter(0).format(kpiData.getNegativestock()) : "0");
			this.btn_NegativeStock.setEnabled((kpiData.getNegativestock() != null && kpiData.getNegativestock() > 0) ? true : false);

		}
		else
		{
			this.btn_WithStock.setCaption("0");
			this.btn_WithStock.setEnabled(false);

			this.btn_CriticalStock.setCaption("0");
			this.btn_CriticalStock.setEnabled(false);

			this.btn_OutOfStock.setCaption("0");
			this.btn_OutOfStock.setEnabled(false);

			this.btn_NegativeStock.setCaption("0");
			this.btn_NegativeStock.setEnabled(false);
		}
	}
}
