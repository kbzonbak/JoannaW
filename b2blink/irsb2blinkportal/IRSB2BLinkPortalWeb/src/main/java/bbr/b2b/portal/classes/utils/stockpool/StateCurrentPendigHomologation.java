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

public class StateCurrentPendigHomologation extends VerticalLayout
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long	serialVersionUID			= 422110660891507871L;

	private BbrUI				bbrUI;
	// private Object initParam = null;
	// private Label lblPendinHomolationUnits = null;
	private Button				btn_PendinHomolationUnits	= null;
	private BbrModule			phatherBbrModule			= null;
	private static final String	COLORAZULSTOCKPOOL			= "v-label-colorazulStockPool";
	private VerticalLayout		pnlPendinHomolationUnits	= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	public StateCurrentPendigHomologation(BbrUI bbrUI, Object selectedVeVKPI, BbrModule phatherBbrModule)
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

			// 3 Productos a Homologar
			// this.lblPendinHomolationUnits = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "obs_pendin_homolation_units"));
			// this.lblPendinHomolationUnits.addStyleName("stats-title");

			this.btn_PendinHomolationUnits = new Button();
			this.btn_PendinHomolationUnits.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "view_details"));
			this.btn_PendinHomolationUnits.addClickListener((ClickListener & Serializable) e -> this.pendinHomolationUnits_clickHandler(e));
			this.btn_PendinHomolationUnits.addStyleName(BbrStyles.ACTION_BUTTON);
			this.btn_PendinHomolationUnits.addStyleName("action-button-fix-homolog");

			pnlPendinHomolationUnits = new VerticalLayout();
			pnlPendinHomolationUnits.addComponents(/* this.lblPendinHomolationUnits, */ this.btn_PendinHomolationUnits);
			// pnlPendinHomolationUnits.setComponentAlignment(this.lblPendinHomolationUnits, Alignment.TOP_CENTER);
			pnlPendinHomolationUnits.setComponentAlignment(this.btn_PendinHomolationUnits, Alignment.BOTTOM_CENTER);
			pnlPendinHomolationUnits.setWidth("32%");
			pnlPendinHomolationUnits.setHeight("100%");
			pnlPendinHomolationUnits.addStyleName("box-standard-panel");

			// merge
			HorizontalLayout pnlData = new HorizontalLayout();
			pnlData.setSizeFull();
			pnlData.setMargin(false);
			pnlData.setSpacing(false);
			pnlData.addComponents(pnlPendinHomolationUnits);
			pnlData.setComponentAlignment(pnlPendinHomolationUnits, Alignment.MIDDLE_CENTER);

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

	private void pendinHomolationUnits_clickHandler(ClickEvent e)
	{
		try
		{
			MainUI main = (MainUI) this.phatherBbrModule.getBbrUIParent();
			main.showModuleFromProductWithoutHomologation(ModulesCodes.STKP_WITHOUT_HOMOLOG, "");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private HorizontalLayout getToolBar()
	{
		HorizontalLayout result = new HorizontalLayout();
		// result.addStyleName("stats-header");
		result.addStyleName("stats-header-StockPool");
		result.setWidth("100%");

		Label headerCaption = new Label(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_LOGISTIC, "product_without_homologation"));

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
			
			if (kpiData.getPendinghomologationunits() > 0)
			{
				this.pnlPendinHomolationUnits.setStyleName("box-yellow-panel");

			}
			else
			{

				this.pnlPendinHomolationUnits.setStyleName("box-blue-panel");
			}

			this.btn_PendinHomolationUnits.setCaption((kpiData.getPendinghomologationunits() != null && kpiData.getPendinghomologationunits() > 0) ? NumericManager.getFormatter(0).format(kpiData.getPendinghomologationunits()) : "0");
			this.btn_PendinHomolationUnits.setEnabled((kpiData.getPendinghomologationunits() != null && kpiData.getPendinghomologationunits() > 0) ? true : false);

		}
		else
		{

			this.btn_PendinHomolationUnits.setCaption("0");
			this.btn_PendinHomolationUnits.setEnabled(false);
		}
	}
}
