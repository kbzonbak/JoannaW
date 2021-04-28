package bbr.b2b.portal.modules.customer;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisTitle;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.Stacking;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.constants.ModulesCodes;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.ui.MainUI;
import bbr.b2b.portal.components.filters.customer_service.ScoreCardFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.customer.CustomerServiceConstants;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import bbr.esb.services.webservices.facade.ScoreCardDTO;
import bbr.esb.services.webservices.facade.ScoreCardManagerServer;
import bbr.esb.services.webservices.facade.ScoreCardManagerServerService;
import bbr.esb.services.webservices.facade.SiteProgress;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;

public class ScoreCardManagement extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long		serialVersionUID			= -3947159600615633249L;
	public static final String		LAST_INVENTORY_SEND			= "last_inventory_send";
	public static final String		LAST_SALES_SEND				= "last_sales_send";
	public static final String		LATE_SALES					= "late_sales";
	public static final String		NOT_LOADED_PRODUCT			= "not_loaded_product";
	public static final String		NOT_LOADED_LOCAL			= "not_loaded_local";
	public static final String		PENDING_REPROCESS_SALES		= "pending_reprocess_sales";
	public static final String		PENDING_REPROCESS_INVENTORY	= "pending_reprocess_inventory";
	public static final String		CONTROL_PANEL_GRID_STYLE	= "control-panel-grid bbr-overflow-auto";
	public static final String		ACTION_BUTTON_STYLE			= "score-card-action-button";
	public static final String		ACTION_TITLE_STYLE			= "score-card-action-title";
	private static final String		TOTAL_SUCCESS				= "total_success";
	private static final String		TOTAL_INPROGRESS			= "total_inprogress";
	private static final String		TOTAL_ERROR					= "total_error";
	// Components
	private VerticalLayout			mainCards					= null;
	// Variables
	private BbrWork<ScoreCardDTO>	reportWork					= null;
	private CompanyDataDTO			selectedCompany				= null;
	private Label					dayTime						= new Label(formatDate(LocalDateTime.now()));

	private String formatDate(LocalDateTime date)
	{
		return date != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(date) : "";
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ScoreCardManagement(BbrUI bbrUIParent)
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
		// Filtro
		ScoreCardFilter filterLayout = new ScoreCardFilter(this);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// Horizontal first 3 columns
		// Horizontal chart
		ScoreCardDTO initialResult = this.getInitialEmptyResultDTO();
		this.mainCards = this.initializeInfoCards(initialResult, this);

		Button btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
		btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(this.dayTime, btn_Refresh);
		rightButtonsBar.setExpandRatio(this.dayTime, 1);
		rightButtonsBar.setWidth("100%");
		rightButtonsBar.setComponentAlignment(dayTime, Alignment.MIDDLE_CENTER);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName("toolbar-layout");

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(rightButtonsBar, 1F);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Reporte: Contenido

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName("report-layout");
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.addComponents(toolBar);
		mainLayout.addComponents(mainCards);
		mainLayout.setExpandRatio(mainCards, 1F);

		this.addComponents(mainLayout);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, ScoreCardDTO>()
		{
			@Override
			public ScoreCardDTO apply(Object t)
			{
				return executeService(ScoreCardManagement.this.getBbrUIParent());
			}
		});
	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.selectedCompany = (CompanyDataDTO) event.getResultObject();
			this.executeReportWork();

		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ScoreCardManagement senderReport = (ScoreCardManagement) sender;

		if (result != null)
		{
			if (result instanceof ScoreCardDTO)
			{
				senderReport.doUpdateReport(result, senderReport);
			}
			else if (result instanceof BaseResultDTO)
			{
				senderReport.doUpdateReportAssociated(result, senderReport);
			}

			senderReport.updateHeaderTimer(senderReport);
		}
		else
		{
			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
			{
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
			}

			senderReport.stopWaiting();
		}
	}

	private void updateHeaderTimer(ScoreCardManagement senderReport)
	{
		senderReport.dayTime.setValue(formatDate(LocalDateTime.now()));
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================
	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.executeReportWork();
	}

	private void executeReportWork()
	{
		this.startWaiting();
		this.executeBbrWork(reportWork);

	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private ScoreCardDTO getScoreCardDTOWS(String selectedCompany)
	{

		ScoreCardManagerServer loginClientPort;
		ScoreCardDTO item = null;
		try
		{
			URL url = new URL(CustomerServiceConstants.getInstance().getScoreCardWebServiceEndpointPath());
			ScoreCardManagerServerService loginClient = new ScoreCardManagerServerService(url);
			loginClientPort = loginClient.getScoreCardManagerServerPort();
			item = loginClientPort.getScoreCardOfCompany(selectedCompany, CustomerServiceConstants.REPORT_OPTION);
			EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(this.getBbrUIParent()).saveCompanySelectedAndAddCountUserProvider(this.getBbrUIParent().getUser().getId(), selectedCompany);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "error_webservice"));
		}
		return item;
	}

	private ScoreCardDTO getInitialEmptyResultDTO()
	{

		ScoreCardDTO result = new ScoreCardDTO();
		result.setTotalsucess(0);
		result.setTotalinprogress(0);
		result.setTotalerror(0);
		// result.setId(1L);
		List<SiteProgress> sites = result.getSiteprogressarray();
		SiteProgress sp = new SiteProgress();
		sp.setSitename("");
		sp.setInprogress(0);
		sp.setSuccess(0);
		sp.setError(0);
		sites.add(sp);

		return result;
	}

	private ScoreCardDTO executeService(BbrUI bbrUIParent)
	{
		ScoreCardDTO result = new ScoreCardDTO();

		try
		{
			String pvcode = this.selectedCompany != null ? this.selectedCompany.getRut() : null;
			// del reporte
			result = this.getScoreCardDTOWS(pvcode);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{

		ScoreCardManagement senderReport = (ScoreCardManagement) sender;

		if (result != null)
		{
			ScoreCardDTO resultDTO = (ScoreCardDTO) result;
			senderReport.mainCards.removeAllComponents();
			senderReport.mainCards.addComponent(senderReport.initializeInfoCards(resultDTO, senderReport));
		}
		senderReport.stopWaiting();

	}

	private void doUpdateReportAssociated(Object result, BbrWorkExecutor sender)
	{

		ScoreCardManagement senderReport = (ScoreCardManagement) sender;

		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				senderReport.stopWaiting();
				senderReport.executeReportWork();
			}
		}
		else
		{
			senderReport.stopWaiting();
		}
	}

	private VerticalLayout initializeInfoCards(ScoreCardDTO result, ScoreCardManagement senderReport)
	{
		VerticalLayout column1 = senderReport.getColumnCard(TOTAL_SUCCESS,
				result.getTotalsucess());
		VerticalLayout column2 = senderReport.getColumnCard(TOTAL_INPROGRESS,
				result.getTotalinprogress());
		VerticalLayout column3 = senderReport.getColumnCard(TOTAL_ERROR,
				result.getTotalerror());

		HorizontalLayout topRow = new HorizontalLayout();
		topRow.addComponents(column1, column2, column3);
		topRow.setComponentAlignment(column1, Alignment.MIDDLE_CENTER);
		topRow.setComponentAlignment(column2, Alignment.MIDDLE_CENTER);
		topRow.setComponentAlignment(column3, Alignment.MIDDLE_CENTER);
		topRow.setMargin(false);
		topRow.setWidth("80%");
		topRow.setHeight("100%");

		List<SiteProgress> sites = result.getSiteprogressarray();
		Chart chart = senderReport.getChart(sites);
		chart.setWidth("80%");
		chart.setHeight("100%");

		HorizontalLayout bottomRow = new HorizontalLayout();
		bottomRow.addComponent(chart);
		bottomRow.setComponentAlignment(chart, Alignment.BOTTOM_CENTER);
		bottomRow.setMargin(false);
		bottomRow.setSizeFull();

		VerticalLayout content = new VerticalLayout();
		content.addComponent(topRow);
		content.setComponentAlignment(topRow, Alignment.MIDDLE_CENTER);
		content.addComponent(bottomRow);
		content.setExpandRatio(topRow, 0.3f);
		content.setExpandRatio(bottomRow, 0.7f);
		content.setMargin(false);
		content.setSizeFull();
		return content;
	}

	private VerticalLayout getColumnCard(String captionId, Integer value)
	{
		String caption = I18NManager.getI18NString(this.getBbrUIParent(),
				BbrUtilsResources.RES_MODULES_CUSTOMER, captionId);

		Label title = new Label(caption);
		title.addStyleName(ACTION_TITLE_STYLE);

		Button btn_action = new Button(value != null ? String.valueOf(value) : "");
		btn_action.addStyleName(ACTION_BUTTON_STYLE);

		if (captionId.equals(TOTAL_ERROR))
		{
			btn_action.addClickListener((ClickListener & Serializable) (e) -> this.openDetailReportByState(
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "total_error")));
		}
		else if (captionId.equals(TOTAL_INPROGRESS))
		{
			btn_action.addClickListener((ClickListener & Serializable) (e) -> this.openDetailReportByState(
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "in_process")));
		}
		else if (captionId.equals(TOTAL_SUCCESS))
		{
			btn_action.addClickListener((ClickListener & Serializable) (e) -> this.openDetailReportByState(
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "total_success")));
		}

		VerticalLayout result = new VerticalLayout(title, btn_action, new BbrHSeparator("10px"));
		result.setMargin(false);
		result.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
		result.setComponentAlignment(btn_action, Alignment.MIDDLE_CENTER);
		result.setSizeFull();
		return result;
	}

	private void openDetailReportByState(String state)
	{
		if (!state.isEmpty())
		{
			try
			{
				MainUI main = (MainUI) this.getBbrUIParent();
				main.showModuleFromScoreCard(ModulesCodes.CUS_DOC, state);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private Chart getChart(List<SiteProgress> sites)
	{

		Chart chart = new Chart(ChartType.COLUMN);

		Configuration conf = chart.getConfiguration();
		conf.setTitle(new Title(""));

		YAxis yAxis = new YAxis();
		yAxis.setMin(0);
		yAxis.setTitle(new AxisTitle(""));
		yAxis.setGridLineColor(new SolidColor("white"));
		// TOTALES
		// StackLabels sLabels = new StackLabels(true);
		// yAxis.setStackLabels(sLabels);
		// TOTALES
		yAxis.setLabels(new Labels(false));
		conf.addyAxis(yAxis);

		PlotOptionsColumn column = new PlotOptionsColumn();
		column.setStacking(Stacking.NORMAL);

		DataLabels labels = new DataLabels(true);
		Style style = new Style();
		style.setTextShadow("0 0 1px black");
		labels.setStyle(style);
		labels.setColor(SolidColor.WHITE);
		column.setDataLabels(labels);

		conf.setPlotOptions(column);

		XAxis xAxis = new XAxis();

		for (SiteProgress siteProgress : sites)
		{
			xAxis.addCategory(siteProgress.getRetailname());
		}
		List<Number> valuesError = new ArrayList<>();
		List<Number> valuesSuccess = new ArrayList<>();
		List<Number> valuesInprogress = new ArrayList<>();
		for (int i = 0; i < sites.size(); i++)
		{
			valuesInprogress.add(sites.get(i).getInprogress());
			valuesSuccess.add(sites.get(i).getSuccess());
			valuesError.add(sites.get(i).getError());
		}
		ListSeries series3 = getSerie(I18NManager.getI18NString(this.getBbrUIParent(),
				BbrUtilsResources.RES_MODULES_CUSTOMER, "total_error"), valuesError, SolidColor.DARKSLATEGREY, 3);
		conf.addSeries(series3);
		ListSeries series2 = getSerie(I18NManager.getI18NString(this.getBbrUIParent(),
				BbrUtilsResources.RES_MODULES_CUSTOMER, "in_process"), valuesInprogress, SolidColor.ORANGE, 2);
		conf.addSeries(series2);
		ListSeries series1 = getSerie(I18NManager.getI18NString(this.getBbrUIParent(),
				BbrUtilsResources.RES_MODULES_CUSTOMER, "total_success"), valuesSuccess, SolidColor.BLUE, 1);
		conf.addSeries(series1);

		conf.addxAxis(xAxis);
		conf.setCredits(new Credits(false));

		Tooltip tooltip = new Tooltip();
		tooltip.setFormatter("function() { return ''+ this.series.name +': '+ this.y +'';}");
		conf.setTooltip(tooltip);

		chart.drawChart(conf);
		return chart;
	}

	private ListSeries getSerie(String title, List<Number> values, SolidColor color, int index)
	{
		ListSeries series = new ListSeries(title, values);
		PlotOptionsColumn seriesOptions = new PlotOptionsColumn();
		seriesOptions.setColor(color);
		seriesOptions.setLegendIndex(index);
		series.setPlotOptions(seriesOptions);
		return series;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
