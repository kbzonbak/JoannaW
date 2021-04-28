package bbr.b2b.portal.modules.stockpool;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.ChartOptions;
import com.vaadin.addon.charts.model.Background;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.Lang;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.Pane;
import com.vaadin.addon.charts.model.PlotOptionsBar;
import com.vaadin.addon.charts.model.PlotOptionsSolidgauge;
import com.vaadin.addon.charts.model.SeriesTooltip;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.event.SortEvent.SortListener;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.logistic.report.data.dto.ClientAvailabilityInitParamDTO;
import bbr.b2b.logistic.report.data.dto.ClientAvailabilityReportDTO;
import bbr.b2b.logistic.report.data.dto.ClientAvailabilityReportResultDTO;
import bbr.b2b.logistic.report.data.dto.IndicatorsInitParamDTO;
import bbr.b2b.logistic.report.data.dto.IndicatorsResultDTO;
import bbr.b2b.logistic.report.data.dto.LastSelledUnitsDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.constants.ModulesCodes;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.ui.MainUI;
import bbr.b2b.portal.classes.utils.stockpool.StateCurrentActiveReportedAlerts;
import bbr.b2b.portal.classes.utils.stockpool.StateCurrentAvailableLevels;
import bbr.b2b.portal.classes.utils.stockpool.StateCurrentPendigHomologation;
import bbr.b2b.portal.components.filters.stockpool.StockPoolReportFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.utils.PortalDates;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;

public class StockPoolReport extends BbrModule implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants

	private static final long								serialVersionUID						= 1282944624651942272L;

	private static final String								CLIENTNAME								= "clientname";
	private static final String								TOTALQUANTITY							= "totalquantity";
	private static final String								TOTALAVAILABLESKU						= "totalavailablesku";
	private static final String								AVAILABLESKUWITHSTOCK							= "availableskuwithstock";

	private final int										DEFAULT_PAGE_NUMBER						= 1;
	private final int										MAX_ROWS_NUMBER							= 200;

	private VerticalLayout									mainLayout								= null;
	private Button											btn_Refresh								= null;

	// Productos sin homologacion
	private BbrAdvancedGrid<ClientAvailabilityReportDTO>	datGrid_ClientAvailability				= null;
	private OrderCriteriaDTO[]								orderCriteriaClientAvailability			= null;
	private BbrWork<ClientAvailabilityReportResultDTO>		reportWorkClientAvailability			= null;
	private final String									DEFAULT_SORT_FIELD_CLIENT_AVAILABILITY	= CLIENTNAME;

	// Indicadores
	private StateCurrentAvailableLevels						pnlAvailableLevels						= null;
	private StateCurrentActiveReportedAlerts				pnlActiveReportedAlerts					= null;
	private StateCurrentPendigHomologation					pnlProductWithoutHomologation			= null;

	private BbrWork<IndicatorsResultDTO>					reportWorkIndicators					= null;

	// Grafico
	private VerticalLayout									showAvailabilityContainer				= new VerticalLayout();
	private Chart											chartAvailability						= null;
	private Chart											chart									= null;

	private Boolean											trackReport								= true;
	private Boolean											resetPageInfo							= true;

	private CompanyDataDTO									selectedProvider						= null;
	private Label											dayTime									= new Label();
	private Label											lbl_productsAvailabilityClient			= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public StockPoolReport(BbrUI bbrUIParent, FunctionalityMngr functionalityMngr)
	{
		super(bbrUIParent);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		BbrUser user = this.getUser();

		if (user != null)
		{
			// Filtro
			StockPoolReportFilter filterLayout = new StockPoolReportFilter(this);
			filterLayout.initializeView();
			this.setBbrFilterContainer(filterLayout);

			// Barra de herramientas superior izq
			HorizontalLayout leftButtonsBar = new HorizontalLayout();
			leftButtonsBar.setSpacing(true);
			leftButtonsBar.setMargin(false);
			leftButtonsBar.setHeight("30px");
			leftButtonsBar.addStyleName("toolbar-layout");

			// Refrescar
			this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
			this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
			this.btn_Refresh.addStyleName("toolbar-button");

			Button btn_Help = this.getHelpButton();

			// Barra superior dercha
			HorizontalLayout rightButtonsBar = new HorizontalLayout();
			rightButtonsBar.addComponents(this.btn_Refresh, btn_Help);
			rightButtonsBar.setSpacing(true);
			rightButtonsBar.setMargin(false);
			rightButtonsBar.setHeight("30px");
			rightButtonsBar.addStyleName("toolbar-layout");

			// Barra superior
			HorizontalLayout toolBar = new HorizontalLayout();
			toolBar.setWidth("100%");
			toolBar.addComponents(leftButtonsBar, this.dayTime, rightButtonsBar);
			toolBar.addStyleName("filter-toolbar");
			toolBar.setExpandRatio(leftButtonsBar, 1F);
			toolBar.setExpandRatio(this.dayTime, 1F);
			toolBar.setExpandRatio(rightButtonsBar, 1F);

			toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
			toolBar.setComponentAlignment(this.dayTime, Alignment.MIDDLE_CENTER);
			toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

			//// fin primera parte //////////////////////////////////////////////////

			this.initializeChartAvailabilityLayout(null);

			// primera parte grafico slogauge
			VerticalLayout pnLUnoGrafic = new VerticalLayout();
			pnLUnoGrafic.addComponents(showAvailabilityContainer);
			pnLUnoGrafic.setComponentAlignment(showAvailabilityContainer, Alignment.TOP_CENTER);
			pnLUnoGrafic.setMargin(false);
			pnLUnoGrafic.setSpacing(false);
			pnLUnoGrafic.setSizeFull();

			// Productos sin homologación
			VerticalLayout vClientAvailability = this.getClientAvailability();

			VerticalLayout pnlLeft = new VerticalLayout();
			pnlLeft.setSizeFull();
			pnlLeft.setMargin(false);
			pnlLeft.addComponents(pnLUnoGrafic, vClientAvailability);
			pnlLeft.setExpandRatio(pnLUnoGrafic, 1F);
			pnlLeft.setExpandRatio(vClientAvailability, 1F);

			// CENTER
			// indicators
			VerticalLayout pnLIndicators = this.getpnLIndicators();

			VerticalLayout pnlCenter = new VerticalLayout();
			pnlCenter.setSizeFull();
			pnlCenter.setMargin(false);
			pnlCenter.addComponents(pnLIndicators);
			pnlCenter.setExpandRatio(pnLIndicators, 1F);

			// Grafico

			VerticalLayout pnLGrafic = this.getpnLGrafic();

			VerticalLayout pnlRight = new VerticalLayout();
			pnlRight.setSizeFull();
			pnlRight.setMargin(false);
			pnlRight.setSpacing(false);
			pnlRight.addComponents(pnLGrafic);
			pnlRight.setExpandRatio(pnLGrafic, 3F);

			HorizontalLayout dataLayout = new HorizontalLayout();
			dataLayout.setSizeFull();
			dataLayout.addComponents(pnlLeft, pnlCenter, pnlRight);

			this.mainLayout = new VerticalLayout();
			mainLayout.addStyleName("report-layout");
			mainLayout.setSizeFull();
			mainLayout.setMargin(false);
			mainLayout.addComponents(toolBar, dataLayout);
			mainLayout.setExpandRatio(dataLayout, 2F);

			this.addComponents(mainLayout);

			this.updateButtons(false);

			this.updateSortOrderCriteriaProductsWithoutApproval(null);

			// Productos sin homologación
			reportWorkClientAvailability = new BbrWork<>(this, this.getBbrUIParent(), this);
			reportWorkClientAvailability.addFunction(new Function<Object, ClientAvailabilityReportResultDTO>()
			{
				@Override
				public ClientAvailabilityReportResultDTO apply(Object t)
				{
					return executeServiceProductsWithoutApproval(StockPoolReport.this.getBbrUIParent());
				}
			});

			// indicators
			reportWorkIndicators = new BbrWork<>(this, this.getBbrUIParent(), this);
			reportWorkIndicators.addFunction(new Function<Object, IndicatorsResultDTO>()
			{
				@Override
				public IndicatorsResultDTO apply(Object t)
				{
					return executeServiceIndicators(StockPoolReport.this.getBbrUIParent());
				}
			});
		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0016"));
		}
	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.selectedProvider = (CompanyDataDTO) event.getResultObject();
			this.startWaiting(2);
			this.resetPageInfo = true;

			this.executeBbrWork(reportWorkClientAvailability);
			this.executeBbrWork(reportWorkIndicators);

		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		StockPoolReport bbrSender = (StockPoolReport) sender;
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				if (result instanceof ClientAvailabilityReportResultDTO)
				{
					bbrSender.doUpdateProductsWithoutApproval(result, sender);
				}
				else if (result instanceof IndicatorsResultDTO)
				{
					bbrSender.doUpdateIndicators(result, sender);
				}
			}

		}
		else
		{
			bbrSender.showErrorMessage(I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(bbrSender.getBbrUIParent(),
							BbrUtilsResources.RES_GENERIC,
							"unsuccessful_operation"));
		}
		bbrSender.stopWaiting();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	private VerticalLayout getClientAvailability()
	{
		VerticalLayout productsWithoutApproval = new VerticalLayout();

		HorizontalLayout leftButtonsBarAvailabilityClient = new HorizontalLayout();
		leftButtonsBarAvailabilityClient.setSpacing(true);
		leftButtonsBarAvailabilityClient.setMargin(false);
		leftButtonsBarAvailabilityClient.setHeight("30px");
		leftButtonsBarAvailabilityClient.addStyleName("toolbar-layout");

		this.lbl_productsAvailabilityClient = new Label("");
		this.lbl_productsAvailabilityClient.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "products_availability_client"));
		this.lbl_productsAvailabilityClient.setStyleName("lbl-products-availability-client");

		leftButtonsBarAvailabilityClient.addComponents(this.lbl_productsAvailabilityClient);

		// Barra superior
		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsBarAvailabilityClient);
		toolBar.setExpandRatio(leftButtonsBarAvailabilityClient, 1F);

		toolBar.setComponentAlignment(leftButtonsBarAvailabilityClient, Alignment.BOTTOM_CENTER);

		// Reporte: Grilla
		this.datGrid_ClientAvailability = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.datGrid_ClientAvailability.setSortable(false);

		this.initializeDataGridColumnsProductsWithoutApproval();

		this.datGrid_ClientAvailability.addStyleName("report-grid");
		this.datGrid_ClientAvailability.setSizeFull();
		this.datGrid_ClientAvailability.setSelectionMode(SelectionMode.SINGLE);
		this.datGrid_ClientAvailability.addSortListener((SortListener<GridSortOrder<ClientAvailabilityReportDTO>> & Serializable) e -> dataGridProductsWithoutApproval_sortHandler(e));

		productsWithoutApproval.setSizeFull();
		productsWithoutApproval.setMargin(false);
		productsWithoutApproval.addComponents(toolBar, this.datGrid_ClientAvailability);
		productsWithoutApproval.setExpandRatio(this.datGrid_ClientAvailability, 1F);

		return productsWithoutApproval;
	}

	private void dataGridProductsWithoutApproval_sortHandler(SortEvent<GridSortOrder<ClientAvailabilityReportDTO>> e)
	{

		// this.startWaiting();
		this.updateSortOrderCriteriaProductsWithoutApproval(e.getSortOrder());
		this.trackReport = false;
		this.resetPageInfo = true;
		this.executeBbrWork(reportWorkClientAvailability);
	}

	private VerticalLayout getpnLIndicators()
	{

		this.pnlAvailableLevels = new StateCurrentAvailableLevels(this.getBbrUIParent(), null, this);
		this.pnlActiveReportedAlerts = new StateCurrentActiveReportedAlerts(this.getBbrUIParent(), null, this);
		this.pnlProductWithoutHomologation = new StateCurrentPendigHomologation(this.getBbrUIParent(), null, this);

		VerticalLayout pnLIndicators = new VerticalLayout();
		pnLIndicators.addComponents(this.pnlAvailableLevels, this.pnlActiveReportedAlerts , this.pnlProductWithoutHomologation);
		pnLIndicators.setExpandRatio(this.pnlAvailableLevels, 1F);
		pnLIndicators.setExpandRatio(this.pnlActiveReportedAlerts, 1F);
		pnLIndicators.setExpandRatio(this.pnlProductWithoutHomologation, 1F);
		pnLIndicators.setMargin(false);
		pnLIndicators.setSpacing(false);
		pnLIndicators.setSizeFull();

		return pnLIndicators;
	}

	private VerticalLayout getpnLGrafic()
	{

		this.initializeChart();

		HorizontalLayout graficContainer = new HorizontalLayout();
		graficContainer.addComponents(this.chart);
		graficContainer.setMargin(false);
		graficContainer.setStyleName("chart-sales-variation");

		VerticalLayout pnLGrafic = new VerticalLayout(graficContainer);
		pnLGrafic.setComponentAlignment(graficContainer, Alignment.MIDDLE_LEFT);
		pnLGrafic.setMargin(false);
		pnLGrafic.setSpacing(false);
		pnLGrafic.setSizeFull();
		pnLGrafic.setId("panel grafico derecho");
		pnLGrafic.setStyleName("panel-right-chart");
		pnLGrafic.setExpandRatio(graficContainer, 1F);
		return pnLGrafic;
	}

	private void initializeChartAvailabilityLayout(ClientAvailabilityReportResultDTO clientAvailability)
	{
		this.showAvailabilityContainer.removeAllComponents();

		this.initializeChartAvailability(clientAvailability);

		HorizontalLayout graficContainer = new HorizontalLayout();
		graficContainer.addComponents(this.chartAvailability);
		graficContainer.setMargin(false);
		graficContainer.setSpacing(false);
		graficContainer.setSizeFull();
		graficContainer.setStyleName("chart-sales-variation");

		this.showAvailabilityContainer.addComponents(graficContainer);
		this.showAvailabilityContainer.setComponentAlignment(graficContainer, Alignment.TOP_CENTER);
		this.showAvailabilityContainer.setMargin(false);
		this.showAvailabilityContainer.setSpacing(false);
		this.showAvailabilityContainer.setSizeFull();		
		this.showAvailabilityContainer.setHeight("100%");

	}

	private void initializeChart()
	{
		Lang es = new Lang();
		es.setNoData(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "chart_no_data"));
		ChartOptions.get().setLang(es);

		this.chart = new Chart(ChartType.BAR);
		this.chart.setHeight("100%");

		Tooltip tooltip = new Tooltip();
		tooltip.setValueDecimals(0);
		tooltip.setPointFormat("<b>{point.name}: {point.y:%02f}</b>");				

		PlotOptionsBar plotOptions = new PlotOptionsBar();
		plotOptions.setAllowPointSelect(true);
		plotOptions.setCursor(Cursor.POINTER);
		plotOptions.setShowInLegend(true);		
		
		DataLabels dataLabels = new DataLabels();
		dataLabels.setEnabled(true);		
		dataLabels.setFormat("{point.y:,.0f}");
		plotOptions.setDataLabels(dataLabels);

		Legend legend = new Legend(false);
		legend.setAlign(HorizontalAlign.LEFT);
		legend.setVerticalAlign(VerticalAlign.MIDDLE);
		legend.setLayout(LayoutDirection.VERTICAL);

		Configuration conf = this.chart.getConfiguration();
		conf.setTitle("");
		conf.setTooltip(tooltip);
		conf.setPlotOptions(plotOptions);
		conf.setLegend(legend);		
		
		XAxis xaxis = new XAxis();	
		conf.addxAxis(xaxis);	
		xaxis.setCategories();
				
		YAxis yaxis = new YAxis();
		yaxis.setTitle("Unidades");
		conf.addyAxis(yaxis);
		
		Labels ylabels = yaxis.getLabels();
		ylabels.setAlign(HorizontalAlign.CENTER);

		this.chart.drawChart(conf);		
		this.chart.setWidth("100%");
		this.chart.setHeight("100%");
		this.chart.setId("grafico derecho");
		this.chart.setStyleName("right-chart");
		this.chart.setResponsive(true);	

	}

	private void initializeChartAvailability(ClientAvailabilityReportResultDTO clientAvailability)
	{

		this.chartAvailability = new Chart(ChartType.SOLIDGAUGE);

		Configuration configuration = this.chartAvailability.getConfiguration();

		Pane pane = configuration.getPane();
		pane.setCenter(new String[] { "50%", "50%" });
		pane.setStartAngle(-90);
		pane.setEndAngle(90);

		Background paneBackground = new Background();
		paneBackground.setInnerRadius("60%");
		paneBackground.setOuterRadius("100%");
		paneBackground.setShape("ARC");
		pane.setBackground(paneBackground);

		PlotOptionsSolidgauge plotOptions = new PlotOptionsSolidgauge();
		plotOptions.setTooltip(new SeriesTooltip());
		plotOptions.getTooltip().setValueSuffix("");
		DataLabels labels = new DataLabels();
		labels.setY(5);
		labels.setBorderWidth(0);
		labels.setUseHTML(true);
		labels.setFormat("<div style=\"text-align:center\"><span style=\"font-size:20px;\">{y}</span><br/>");
		plotOptions.setDataLabels(labels);
		configuration.setPlotOptions(plotOptions);

		if (clientAvailability != null)
		{
			Number availablevendorunits = (clientAvailability != null) ? (int) clientAvailability.getAvailablevendorunits() : 0;
			Integer lastselledunitstotal = (clientAvailability != null) ? (int) clientAvailability.getTotalinformedbyvendor() : 0;
			String strDate = (clientAvailability != null) ? this.transformDate(clientAvailability.getLaststockupdatedate(), true) : "";

			YAxis yAxis = configuration.getyAxis();
			yAxis.setTickInterval(lastselledunitstotal);
			yAxis.getTitle().setY(-50);
			yAxis.getLabels().setY(16);
			yAxis.setMin(0);			
			yAxis.setMax(lastselledunitstotal);
			DataSeries series = new DataSeries(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, "availability"));

			DataSeriesItem item = new DataSeriesItem();
			item.setY(availablevendorunits);
			DataLabels dataLabelsSeries = new DataLabels();
			dataLabelsSeries.setFormat("<div style=\"text-align:center\"><br/><span style=\"font-size:20px;\">{y}</span><br/>");

			item.setDataLabels(dataLabelsSeries);

			series.add(item);

			configuration.addSeries(series);
			configuration.setTitle(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, "availability"));
			configuration.setSubTitle(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, "obs_last_stock_update_date", strDate));

		}
		else
		{
			configuration.setTitle("");
		}

		this.chartAvailability.setWidth("100%");
		this.chartAvailability.setHeight("250px");
		this.chartAvailability.setId("grafico izquierdo");
		this.chartAvailability.setStyleName("left-chart");

	}

	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.startWaiting(2);

		this.trackReport = false;
		this.resetPageInfo = false;
		this.executeBbrWork(reportWorkClientAvailability);
		this.executeBbrWork(reportWorkIndicators);
		this.updateButtons(true);
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void initializeDataGridColumnsProductsWithoutApproval()
	{
		// Cliente
		this.datGrid_ClientAvailability.addCustomComponentColumn(item -> this.getGridLinkButton(item), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_client_name"), true)
				.setDescriptionGenerator(ClientAvailabilityReportDTO::getClientname, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setId(CLIENTNAME);

		// # SKU \n habilitados
		this.datGrid_ClientAvailability.addCustomColumn(ClientAvailabilityReportDTO::getTotalavailablesku, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_available_sku"), true)
				.setDescriptionGenerator(i -> NumericManager.getFormatter(0).format(i.getTotalavailablesku()), ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setId(TOTALAVAILABLESKU);
		
		// # SKU \n con stock
		this.datGrid_ClientAvailability.addCustomColumn(ClientAvailabilityReportDTO::getAvailableskuwithstock, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_availablestock_sku"), true)
				.setDescriptionGenerator(i -> NumericManager.getFormatter(0).format(i.getAvailableskuwithstock()), ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setId(AVAILABLESKUWITHSTOCK);

		// Total /n disponible
		this.datGrid_ClientAvailability.addCustomColumn(i -> NumericManager.getFormatter(0).format(i.getTotalquantity()), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_total_quantity"), true)
				.setDescriptionGenerator(i -> NumericManager.getFormatter(0).format(i.getTotalquantity()), ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setId(TOTALQUANTITY);
	}

	private Button getGridLinkButton(ClientAvailabilityReportDTO itemData)
	{
		Button result = null;

		if (itemData != null)
		{

			result = ((itemData.getClientname() != null && itemData.getClientname().length() > 0)) ? new Button(itemData.getClientname()) : new Button();
			result.setVisible((itemData.getClientname() != null && itemData.getClientname().length() > 0) ? true : false);
			if (result.isVisible())
			{
				result.addStyleName("grid-link-button-blue");
				result.setData(itemData);
				result.addClickListener((ClickListener & Serializable) e -> this.linkButton_clickHandler(e));
			}

		}

		return result;
	}

	private void linkButton_clickHandler(ClickEvent event)
	{
		ClientAvailabilityReportDTO itemData = ((event.getButton() != null) && (event.getButton().getData() instanceof ClientAvailabilityReportDTO)) ? (ClientAvailabilityReportDTO) event.getButton().getData() : null;

		try
		{
			MainUI main = (MainUI) this.getBbrUIParent();
			main.showModuleFromAvailabilityReport(ModulesCodes.STKP_DISP, itemData.getClientname());
		}

		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void updateButtons(Boolean isSelectionEvent)
	{
		if (this.selectedProvider != null)
		{
			this.btn_Refresh.setEnabled(true);
		}

	}

	private void updateSortOrderCriteriaProductsWithoutApproval(List<GridSortOrder<ClientAvailabilityReportDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<ClientAvailabilityReportDTO> sorOrder : sortOrderList)
			{
				OrderCriteriaDTO order = new OrderCriteriaDTO();
				order.setPropertyname(sorOrder.getSorted().getId().toUpperCase());
				order.setAscending(sorOrder.getDirection() == SortDirection.ASCENDING);
				resultList.add(order);
			}
			this.orderCriteriaClientAvailability = resultList.toArray(new OrderCriteriaDTO[resultList.size()]);
		}

		else
		{
			OrderCriteriaDTO order = new OrderCriteriaDTO();
			order.setPropertyname(DEFAULT_SORT_FIELD_CLIENT_AVAILABILITY.toUpperCase());
			order.setAscending(true);
			this.orderCriteriaClientAvailability = new OrderCriteriaDTO[] { order };

			GridSortOrder<ClientAvailabilityReportDTO> sortOrder = new GridSortOrder<>(datGrid_ClientAvailability.getColumn(DEFAULT_SORT_FIELD_CLIENT_AVAILABILITY), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<ClientAvailabilityReportDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_ClientAvailability.setSortOrder(sortOrderList);
		}
	}

	private void doUpdateProductsWithoutApproval(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		StockPoolReport senderReport = (StockPoolReport) sender;

		if (result != null)
		{
			ClientAvailabilityReportResultDTO reportResult = (ClientAvailabilityReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				ListDataProvider<ClientAvailabilityReportDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getClientavailabilityreport()));
				senderReport.datGrid_ClientAvailability.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.dayTime.setValue(this.formatDate(LocalDateTime.now()));

				// info para el grafico;
				// senderReport.updateChartAvailabilitySerie(reportResult.getAvailablevendorunits(), reportResult.getTotalinformedbyvendor(), strDate); BGA
				senderReport.initializeChartAvailabilityLayout(reportResult);
				this.markAsDirty();
				// End Tracking
				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}
			}

			else
			{
				errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
			}
		}

		if (errordescription.length() > 0 && senderReport.trackReport == true)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}

		senderReport.stopWaiting();
	}

	private void doUpdateIndicators(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		StockPoolReport senderReport = (StockPoolReport) sender;

		if (result != null)
		{
			IndicatorsResultDTO reportResult = (IndicatorsResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{

				// panel
				this.pnlAvailableLevels.updateData(reportResult, this.selectedProvider);
				this.pnlActiveReportedAlerts.updateData(reportResult, this.selectedProvider);
				this.pnlProductWithoutHomologation.updateData(reportResult, this.selectedProvider);

				// GRAFICO
				if (reportResult.getLastselledunits() != null)
				{
					this.updateChartSerie(reportResult.getLastselledunits(), reportResult.getLastselledunitstotal());
				}
			}

			else
			{
				errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
			}
		}

		if (errordescription.length() > 0 && senderReport.trackReport == true)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}

		senderReport.stopWaiting();
		this.markAsDirty();
	}

	protected ClientAvailabilityReportResultDTO executeServiceProductsWithoutApproval(BbrUI bbrUI)
	{
		ClientAvailabilityReportResultDTO result = null;
		ClientAvailabilityInitParamDTO initParamDTO = new ClientAvailabilityInitParamDTO();

		initParamDTO.setVendorcode(this.selectedProvider.getRut());
		initParamDTO.setByfilter(true);
		initParamDTO.setPageNumber(DEFAULT_PAGE_NUMBER);
		initParamDTO.setPaginated(this.resetPageInfo);
		initParamDTO.setRows(MAX_ROWS_NUMBER);
		initParamDTO.setOrderby(this.orderCriteriaClientAvailability);

		try
		{
			// Start Tracking
			this.getTimingMngr().startTimer();

			result = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUI).getClientAvailabilityWS(initParamDTO, bbrUI.getUser().getId());

		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "error_webservice"));
		}

		return result;
	}

	protected IndicatorsResultDTO executeServiceIndicators(BbrUI bbrUI)
	{
		IndicatorsResultDTO result = null;

		try
		{
			// Start Tracking
			this.getTimingMngr().startTimer();
			IndicatorsInitParamDTO initparamDTO = new IndicatorsInitParamDTO();
			initparamDTO.setVendorcode(this.selectedProvider.getRut());

			result = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUI).getIndicatorsWS(initparamDTO, bbrUI.getUser().getId());

		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "error_webservice"));
		}

		return result;
	}

	private void updateChartSerie(LastSelledUnitsDTO[] itemsResumeDataDTO, Integer lastselledunitstotal)
	{
		DataSeries series = new DataSeries();
		series.setName("");
		if (itemsResumeDataDTO != null)
		{
			for (LastSelledUnitsDTO mpItemsResumeDataDTO : itemsResumeDataDTO)
			{
				DataSeriesItem seriePending = new DataSeriesItem(mpItemsResumeDataDTO.getBuyername(), mpItemsResumeDataDTO.getSales());
				series.add(seriePending);
			}
		}

		String salesUnint = NumericManager.getFormatter().format(lastselledunitstotal);
		Configuration config = this.chart.getConfiguration();
		config.setTitle(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, "title_stockpool_grafic", salesUnint));
		config.setSeries(series);

		this.chart.drawChart(config);

	}

	private String formatDate(LocalDateTime date)
	{
		return date != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(date) : "";
	}

	public String transformDate(String strString, boolean includetime)
	{
		String result = "";
		if (strString != null)
		{
			LocalDateTime dateTime = PortalDates.getLocalDateTimeformatT(strString);
			if (includetime)
			{
				result = this.formatDate(dateTime);
			}
			else
			{
				result = BbrDateUtils.getInstance().toShortDateTime(dateTime);
			}

		}

		return result;
	}

	public int compareDate(String strString1, String strString2)
	{
		int result = 0;

		if (strString1 != null && strString2 != null)
		{
			LocalDateTime dateTime1 = PortalDates.getLocalDateTimeformatT(strString1);
			LocalDateTime dateTime2 = PortalDates.getLocalDateTimeformatT(strString2);

			result = dateTime1.compareTo(dateTime2);

		}
		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
