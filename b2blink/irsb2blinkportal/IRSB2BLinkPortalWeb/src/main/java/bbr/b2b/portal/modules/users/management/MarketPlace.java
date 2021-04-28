package bbr.b2b.portal.modules.users.management;

import java.util.function.Function;

import com.vaadin.addon.charts.shared.MouseEventDetails.MouseButton;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.BbrActionPanel;
import bbr.b2b.portal.components.filters.stockpool.MarketPlaceReportFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;

public class MarketPlace extends BbrModule implements BbrWorkExecutor
{
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Components

	// Variables
	private BbrWork<BaseResultDTO>	reportWork			= null;
	private CompanyDataDTO			company				= null;
	private BbrActionPanel			pnlActionMeli		= null;
	private BbrActionPanel			pnlActionLinio		= null;
	private BbrActionPanel			pnlActionFalabella	= null;
	private BbrActionPanel			pnlActionRipley		= null;
	private BbrActionPanel			pnlActionParis		= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public MarketPlace(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		// Filtro del reporte
		MarketPlaceReportFilter filterLayout = new MarketPlaceReportFilter(this);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// Panel Action
		Resource resMeli = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), "assets/images/modules/logo_mercadoLibre.png");
		Image imageMeli = new Image(null, resMeli);

		Resource resLinio = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), "assets/images/modules/logo_linio.jpg");
		Image imageLinio = new Image(null, resLinio);

		Resource resFalabella = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), "assets/images/modules/logo_falabella_marketPlace.png");
		Image imageFalabella = new Image(null, resFalabella);

		Resource resRipley = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), "assets/images/modules/logo_mercadoRipley.png");
		Image imageRipley = new Image(null, resRipley);

		Resource resParis = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), "assets/images/modules/logo_parisMarketPlace.png");
		Image imageParis = new Image(null, resParis);

		this.pnlActionMeli = new BbrActionPanel(imageMeli);
		this.pnlActionMeli.initializeView();
		this.pnlActionMeli.addLayoutClickListener(event -> actionPanel_clickHandler(event, 1));
		this.pnlActionMeli.setEnabled(false);
		this.pnlActionMeli.setStyleName("panel-meli");

		this.pnlActionLinio = new BbrActionPanel(imageLinio);
		this.pnlActionLinio.initializeView();
		this.pnlActionLinio.addLayoutClickListener(event -> actionPanel_clickHandler(event, 2));
		this.pnlActionLinio.setEnabled(false);
		this.pnlActionLinio.setStyleName("panel-linio");

		this.pnlActionFalabella = new BbrActionPanel(imageFalabella);
		this.pnlActionFalabella.initializeView();
		this.pnlActionFalabella.addLayoutClickListener(event -> actionPanel_clickHandler(event, 3));
		this.pnlActionFalabella.setEnabled(false);
		this.pnlActionFalabella.setStyleName("panel-falabella");

		this.pnlActionRipley = new BbrActionPanel(imageRipley);
		this.pnlActionRipley.initializeView();
		this.pnlActionRipley.addLayoutClickListener(event -> actionPanel_clickHandler(event, 4));
		this.pnlActionRipley.setEnabled(false);
		this.pnlActionRipley.setStyleName("panel-ripley");

		this.pnlActionParis = new BbrActionPanel(imageParis);
		this.pnlActionParis.initializeView();
		this.pnlActionParis.addLayoutClickListener(event -> actionPanel_clickHandler(event, 5));
		this.pnlActionParis.setEnabled(false);
		this.pnlActionParis.setStyleName("panel-paris");

		CssLayout pnlMain = new CssLayout();
		pnlMain.addComponents(this.pnlActionMeli, this.pnlActionLinio, this.pnlActionFalabella, this.pnlActionRipley, this.pnlActionParis);		
		pnlMain.setResponsive(true);
		pnlMain.setSizeFull();
		pnlMain.setId("panel-main");
		pnlMain.setStyleName("marketplace-panel-main");

		// Reporte
		// Barra de Herramientas
		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		HorizontalLayout rightButtonsBar = new HorizontalLayout();

		// ACTION BUTTONS SECTION
		VerticalLayout cmp_ActionButtons = new VerticalLayout();
		cmp_ActionButtons.setMargin(new MarginInfo(false, true));
		cmp_ActionButtons.setSpacing(false);

		VerticalLayout cmp_DownloadButtons = new VerticalLayout();
		cmp_DownloadButtons.setMargin(new MarginInfo(false, true));
		cmp_DownloadButtons.setSpacing(false);

		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");
		leftButtonsBar.addStyleName("toolbar-layout");

		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName("toolbar-layout");

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsBar, rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(leftButtonsBar, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName("report-layout");
		mainLayout.setSizeFull();
		mainLayout.addComponents(toolBar, pnlMain);
		mainLayout.setExpandRatio(toolBar, 0F);
		mainLayout.setExpandRatio(pnlMain, 1F);
		mainLayout.setMargin(false);
		this.addComponents(mainLayout);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, BaseResultDTO>()
		{
			@Override
			public BaseResultDTO apply(Object t)
			{
				return executeService(MarketPlace.this.getBbrUIParent());
			}
		});

	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			MarketPlace senderReport = (MarketPlace) sender;
			this.pnlActionMeli.setEnabled(true);
			senderReport.stopWaiting();

		}
		else
		{
			MarketPlace senderReport = (MarketPlace) sender;

			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.company = (CompanyDataDTO) event.getResultObject();

			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private BaseResultDTO executeService(BbrUI bbrUI)
	{
		BaseResultDTO result = new BaseResultDTO();
		{
			try
			{
				this.pnlActionMeli.setEnabled(true);
				EJBFactory.getUserEJBFactory().getUserReportManagerLocal(bbrUI).saveCompanySelectedAndAddCountUserProvider(bbrUI.getUser().getId(), this.company.getRut());

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	private void actionPanel_clickHandler(LayoutClickEvent event, int panelId)
	{
		if (event.getButton().name().equals(MouseButton.LEFT.name()))
		{
			switch (panelId)
			{
				case 1:
					// MERCADO LIBRE
					String url = B2BSystemPropertiesUtil.getProperty("AUTHMELI_URL");
					this.startWaiting(2);
					getUI().getPage().setLocation(url);

					break;
				case 2:
					// LINIO
					break;
				case 3:
					// FALABELLA MARKETPLACE
					break;
				case 4:
					// MERCADO RIPLEY
					break;
				case 5:
					// PARIS MARKETPLACE
					break;

				default:
					break;
			}
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
