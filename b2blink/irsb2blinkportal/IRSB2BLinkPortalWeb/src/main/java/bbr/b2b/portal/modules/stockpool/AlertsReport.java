package bbr.b2b.portal.modules.stockpool;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.event.SortEvent.SortListener;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.logistic.report.data.dto.AlertReportDTO;
import bbr.b2b.logistic.report.data.dto.AlertReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.AlertReportResultDTO;
import bbr.b2b.logistic.report.data.dto.DisableAlertInitParamDTO;
import bbr.b2b.portal.classes.basics.PageRangesEvent;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.basics.SelectPageRanges;
import bbr.b2b.portal.components.filters.stockpool.AlertReportFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.utils.PortalDates;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrErrorList;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class AlertsReport extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long					serialVersionUID			= 1717349615397710932L;

	// Constantes
	private static final String					ALERTDATE					= "alertdate";
	private static final String					DESCRIPTION					= "description";
	private static final String					ALERTTYPE					= "alerttype";

	private final String						DEFAULT_SORT_FIELD			= ALERTDATE;

	// Variables
	private AlertReportInitParamDTO				initParam					= null;
	private AlertReportFilter					filterLayout				= null;
	private BbrWork<AlertReportResultDTO>		reportWork					= null;
	private BbrWork<FileDownloadInfoResultDTO>	downloadsWork				= null;

	private Boolean								trackReport					= true;
	private Boolean								resetPageInfo				= true;
	private VerticalLayout						mainLayout					= null;
	private BbrAdvancedGrid<AlertReportDTO>		datGrid_ActiveAlerts		= null;
	private BbrWork<BaseResultsDTO>				reportWorkAlertOff			= null;
	private BbrPagingManager					pagingManager				= null;

	private Button								btn_DownloadExcel			= null;
	private BbrPopupButton						btn_Download				= null;
	private Button								btn_Refresh					= null;
	private Button								btn_AlertsOff				= null;

	ListDataProvider<AlertReportDTO>			dataproviderDatingReport	= null;
	private OrderCriteriaDTO[]					orderCriteria				= null;
	private String								codeState					= null;
	private boolean								generateAutomatic			= false;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public AlertsReport(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
		this.generateAutomatic = false;
	}

	public AlertsReport(BbrUI bbrUIParent, FunctionalityMngr functionalityMngr, String codeState)
	{
		super(bbrUIParent);
		this.codeState = codeState;
		this.generateAutomatic = true;
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
		this.filterLayout = new AlertReportFilter(this, this.codeState);
		this.filterLayout.initializeView();
		this.setBbrFilterContainer(this.filterLayout);

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Reporte: Barra de herramientas superior izq
		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");
		leftButtonsBar.addStyleName(BbrStyles.TOOLBAR_LAYOUT);

		// DOWNLOAD BUTTONS SECTION
		VerticalLayout cmp_DownloadButtons = new VerticalLayout();
		cmp_DownloadButtons.setMargin(new MarginInfo(false, true));
		cmp_DownloadButtons.setSpacing(false);

		this.btn_DownloadExcel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "download_excel_file"));
		this.btn_DownloadExcel.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "download_excel_file"));
		this.btn_DownloadExcel.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
		this.btn_DownloadExcel.addStyleName("action-button");
		cmp_DownloadButtons.addComponent(this.btn_DownloadExcel);

		this.btn_Download = new BbrPopupButton("");
		this.btn_Download.setIcon(EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		this.btn_Download.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "downloads"));
		this.btn_Download.addStyleName("toolbar-button");
		this.btn_Download.setContentLayout(cmp_DownloadButtons);
		this.btn_Download.setClosePopupOnOutsideClick(true);

		this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
		this.btn_Refresh.addStyleName(BbrStyles.TOOLBAR_BUTTON);
		this.btn_Refresh.setEnabled(false);

		this.btn_AlertsOff = new Button("", EnumToolbarButton.DELETE.getResource());
		this.btn_AlertsOff.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "btn_alerts_off"));
		this.btn_AlertsOff.addClickListener((ClickListener & Serializable) e -> this.btn_AlertsOff_clickHandler(e));
		this.btn_AlertsOff.addStyleName(BbrStyles.TOOLBAR_BUTTON);
		this.btn_AlertsOff.setEnabled(true);

		leftButtonsBar.addComponents(this.btn_AlertsOff);

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(this.btn_Download, this.btn_Refresh);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName(BbrStyles.TOOLBAR_LAYOUT);
		rightButtonsBar.setComponentAlignment(btn_Refresh, Alignment.MIDDLE_RIGHT);

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsBar, rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(leftButtonsBar, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Reporte: Grilla
		this.datGrid_ActiveAlerts = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.datGrid_ActiveAlerts.setSortable(false);

		this.initializeDataGridColumns();

		this.datGrid_ActiveAlerts.addStyleName("report-grid");
		this.datGrid_ActiveAlerts.setSizeFull();
		this.datGrid_ActiveAlerts.setPagingManager(pagingManager, this.DEFAULT_SORT_FIELD);
		this.datGrid_ActiveAlerts.setSelectionMode(SelectionMode.MULTI);
		this.datGrid_ActiveAlerts.addSortListener((SortListener<GridSortOrder<AlertReportDTO>> & Serializable) e -> this.dataGrid_sortHandler(e));
		this.datGrid_ActiveAlerts.addSelectionListener((SelectionListener<AlertReportDTO> & Serializable) e -> this.selection_gridHandler(e));

		this.mainLayout = new VerticalLayout();
		this.mainLayout.addStyleName("report-layout");
		this.mainLayout.setSizeFull();
		this.mainLayout.setMargin(false);
		this.mainLayout.addComponents(toolBar, this.datGrid_ActiveAlerts, pagingManager);
		this.mainLayout.setExpandRatio(this.datGrid_ActiveAlerts, 1F);
		this.addComponents(mainLayout);

		this.updateButtons(false);
		this.updateSortOrderCriteria(null);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, AlertReportResultDTO>()
		{
			@Override
			public AlertReportResultDTO apply(Object t)
			{
				return executeService(AlertsReport.this.getBbrUIParent());
			}
		});
		if (generateAutomatic)
		{
			this.generateFilterClick();
		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doUpdateReport(result, sender);
			}
			else if (result != null && result instanceof FileDownloadInfoResultDTO)
			{
				this.doDownloadFile((FileDownloadInfoResultDTO) result, sender);
			}
			else if (triggerObject == this.btn_AlertsOff)
			{
				this.doValidationAlertsOffResult(result, sender);
			}

		}
		else
		{
			AlertsReport senderReport = (AlertsReport) sender;
			if (!this.getBbrUIParent().hasAlertWindowOpen())
			{
				this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
			}
			senderReport.stopWaiting();
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.initParam = (AlertReportInitParamDTO) event.getResultObject();
			this.trackReport = true;
			this.resetPageInfo = true;
			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}

	@Override
	protected void downloadFormat_selectedHandler(BbrEvent event)
	{
		DownloadFormats selectedFormat = ((event != null) && (event.getResultObject() instanceof DownloadFormats)) ? (DownloadFormats) event.getResultObject() : null;

		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(AlertsReport.this.getBbrUIParent(), selectedFormat, btn_DownloadTriggerButton);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}

	private void pagingChange_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.startWaiting();
			this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<AlertReportDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.startWaiting();

			this.updateSortOrderCriteria(e.getSortOrder());
			this.trackReport = false;
			this.resetPageInfo = true;
			this.executeBbrWork(reportWork);
		}
	}

	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.reloadDatingsGrid();
	}

	private void selection_gridHandler(SelectionEvent<AlertReportDTO> e)
	{
		this.updateButtons(true);
	}

	private void updateButtons(Boolean isSelectionEvent)
	{
		if (this.datGrid_ActiveAlerts != null)
		{
			if (!isSelectionEvent)
			{
				boolean isEmptyGrid = this.datGrid_ActiveAlerts.isEmpty();
				this.btn_DownloadExcel.setEnabled(!isEmptyGrid);
			}

			this.btn_Refresh.setEnabled(initParam != null);
		}
	}

	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
		{
			this.selectDownloadFileType(DownloadFormats.XLS, DownloadFormats.XLS, DownloadFormats.CSV);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void reloadDatingsGrid()
	{
		this.startWaiting();
		this.trackReport = false;
		this.resetPageInfo = true;
		this.executeBbrWork(reportWork);
	}

	private void initializeDataGridColumns()
	{
		// Fecha
		this.datGrid_ActiveAlerts.addCustomColumn(item -> this.transformDate(item.getAlertdate(), false), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_txdate"), true)
				.setDescriptionGenerator(item -> this.transformDate(item.getAlertdate(), false), ContentMode.TEXT)
				.setComparator((item1, item2) -> compareDate(item1.getAlertdate(), item2.getAlertdate()))
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setWidth(160)
				.setId(ALERTDATE);

		// Tipo Alerta
		this.datGrid_ActiveAlerts.addCustomColumn(AlertReportDTO::getAlerttype, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_alert_type"), true)
				.setDescriptionGenerator(AlertReportDTO::getAlerttype, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setWidth(250)
				.setId(ALERTTYPE);

		// Descripcion
		this.datGrid_ActiveAlerts.addCustomColumn(AlertReportDTO::getDescription, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_description"), true)
				.setDescriptionGenerator(AlertReportDTO::getDescription, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setId(DESCRIPTION);

	}

	public String transformDate(String strString, boolean includetime)
	{
		String result = "";
		if (strString != null)
		{
			LocalDateTime dateTime = PortalDates.getLocalDateTimeformatYYYYMMDD(strString);
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
		int result = 1;

		if (strString1 != null && strString2 != null)
		{
			LocalDateTime dateTime1 = PortalDates.getLocalDateTimeformatYYYYMMDD(strString1);
			LocalDateTime dateTime2 = PortalDates.getLocalDateTimeformatYYYYMMDD(strString2);

			result = dateTime1.compareTo(dateTime2);

		}
		return result;
	}

	private String formatDate(LocalDateTime date)
	{
		return date != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(date) : "";
	}

	private void updateSortOrderCriteria(List<GridSortOrder<AlertReportDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();

			for (GridSortOrder<AlertReportDTO> sortOrder : sortOrderList)
			{
				OrderCriteriaDTO order = new OrderCriteriaDTO();
				order.setPropertyname(sortOrder.getSorted().getId().toUpperCase());
				order.setAscending(sortOrder.getDirection() == SortDirection.ASCENDING);
				resultList.add(order);
			}
			this.orderCriteria = resultList.toArray(new OrderCriteriaDTO[resultList.size()]);
		}
		else
		{
			OrderCriteriaDTO order = new OrderCriteriaDTO();
			order.setPropertyname(DEFAULT_SORT_FIELD.toUpperCase());
			order.setAscending(false);
			this.orderCriteria = new OrderCriteriaDTO[] { order };

			GridSortOrder<AlertReportDTO> sortOrder = new GridSortOrder<>(this.datGrid_ActiveAlerts.getColumn(DEFAULT_SORT_FIELD), SortDirection.DESCENDING);
			sortOrderList = new ArrayList<GridSortOrder<AlertReportDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_ActiveAlerts.setSortOrder(sortOrderList);
		}
	}

	private AlertReportResultDTO executeService(BbrUI bbrUI)
	{
		Integer requestedPage = (!this.resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage()
				: PortalConstants.DEFAULT_PAGE_NUMBER;

		AlertReportResultDTO result = null;

		if (this.initParam != null)
		{
			try
			{
				this.getTimingMngr().startTimer();
				this.initParam.setOrderby(this.orderCriteria);
				this.initParam.setPageNumber(requestedPage);
				this.initParam.setPaginated(true);
				this.initParam.setByfilter(true);

				result = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUI).getAlertReportWS(this.initParam, bbrUI.getUser().getId());
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
			}
			catch (BbrSystemException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button btn_DownloadTriggerButton)
	{
		FileDownloadInfoResultDTO fileResult = null;
		AlertReportInitParamDTO initparam = this.initParam;
		initparam.setPaginated(false);

		try
		{
			if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
			{
				fileResult = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).getExcelAlertReportWS(initparam, selectedFormat.getValue(), bbrUIParent.getUser().getId(), bbrUIParent.getLocale());

			}
		}
		catch (BbrUserException | BbrSystemException e)
		{
			e.printStackTrace();
		}

		return fileResult;
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		AlertsReport senderReport = (AlertsReport) sender;

		if (result != null)
		{
			AlertReportResultDTO reportResult = (AlertReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);
			if (!error.hasError())
			{
				if (reportResult.getAlertsreport() != null && reportResult.getAlertsreport().length > 0)
				{
					dataproviderDatingReport = new ListDataProvider<>(Arrays.asList(reportResult.getAlertsreport()));
					senderReport.datGrid_ActiveAlerts.setDataProvider(dataproviderDatingReport, senderReport.resetPageInfo);
					senderReport.updateButtons(false);

					if (senderReport.resetPageInfo)
					{
						PageInfoDTO pageInfo = reportResult.getPageInfo();
						BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
						senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);
					}

					// End Tracking
					if (senderReport.trackReport == true)
					{
						senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
					}
				}
				else
				{
					if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
					{
						senderReport.askToOpenFilter(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
								I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "empty_filter_question"));
					}
					dataproviderDatingReport = new ListDataProvider<>(Arrays.asList(new AlertReportDTO[0]));
					senderReport.datGrid_ActiveAlerts.setDataProvider(dataproviderDatingReport, senderReport.resetPageInfo);
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

		this.stopWaiting();

		if (!btn_Refresh.isEnabled())
		{
			this.btn_Refresh.setEnabled(true);
		}
	}

	private void doDownloadFile(FileDownloadInfoResultDTO downloadResult, BbrWorkExecutor senderPostExecute)
	{
		AlertsReport senderReport = (AlertsReport) senderPostExecute;

		if (downloadResult != null)
		{
			BbrError error = ErrorsMngr.getInstance().getError(downloadResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				senderReport.downloadLinkFile(downloadResult);
			}
		}

		senderReport.stopWaiting();
	}

	private void doValidationAlertsOffResult(Object result, BbrWorkExecutor sender)
	{
		AlertsReport senderReport = (AlertsReport) sender;

		if (result != null)
		{
			BaseResultsDTO reportResult = (BaseResultsDTO) result;

			// MULTIPLES ALERTAS DE MENSAJES
			boolean canOpenAlert = !senderReport.getBbrUIParent().hasAlertWindowOpen();

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), false);

			if (!error.hasError())
			{
				this.showInfoMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "successful_operation"));
			}
			else
			{
				if (reportResult.getBaseresults() != null && reportResult.getBaseresults().length > 0)
				{
					if (canOpenAlert)
					{
						BbrErrorList<BaseResultDTO> winErrors = new BbrErrorList<>(senderReport.getBbrUIParent(),
								reportResult.getBaseresults(), senderReport.getUser().getLocale());
						winErrors.setCloseButtonStyle("btn-generic");
						winErrors.setConverterFunction(new Function<BaseResultDTO, BbrError>()
						{
							@Override
							public BbrError apply(BaseResultDTO t)
							{
								BbrError result = new BbrError(t.getStatuscode(), t.getStatusmessage());

								return result;
							}
						});
						winErrors.initializeView();
						winErrors.setWidth("350px");
						winErrors.setHeight("350px");
						winErrors.open(true);
					}

				}
				else
				{
					ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), canOpenAlert);
				}
			}

		}

		senderReport.stopWaiting();

		senderReport.startWaiting();
		senderReport.executeBbrWork(reportWork);
	}

	// test
	private void btn_AlertsOff_clickHandler(ClickEvent e)
	{
		//this.btn_DownloadTriggerButton = e.getButton();

		SelectPageRanges selectPageRanges = new SelectPageRanges(AlertsReport.this.getBbrUIParent(),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "btn_alerts_off"));
		selectPageRanges.addBbrEventListener((BbrEventListener & Serializable) ev -> doDownloadFile(ev));
		selectPageRanges.setMaxPageValue(this.pagingManager.getTotalsPages());
		selectPageRanges.setalertSelected(this.datGrid_ActiveAlerts.getSelectedItems().size() > 0);
		selectPageRanges.initializeView();
		selectPageRanges.open(true, true, this);
	}

	// TODO
	private void doDownloadFile(BbrEvent e)
	{
		PageRangesEvent pageRangeEvent = ((PageRangesEvent) e.getResultObject());
		String eventType = pageRangeEvent.getOption();

		boolean statusIncorrectForAlertsOff = (this.datGrid_ActiveAlerts.getSelectedItems() != null && this.datGrid_ActiveAlerts.getSelectedItems().size() > 0) || eventType.equals(PageRangesEvent.ALL_PAGES_OPTION);
		if (!statusIncorrectForAlertsOff)
		{
			this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, "valid_alerts_off_interface_message"));
		}
		else
		{
			if(eventType.equals(PageRangesEvent.ALL_PAGES_OPTION)){
				BbrMessagesBoxUtils.showYesNoQuestionMessage(this.getBbrUIParent(), I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
						I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, "confirmation_all_alerts_off_interface_action_question"), () -> this.doAlertOffInterface(true, e),
						() -> this.doAlertOffInterface(false, e));
			}else{
				BbrMessagesBoxUtils.showYesNoQuestionMessage(this.getBbrUIParent(), I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
						I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, "confirmation_alerts_off_interface_action_question"), () -> this.doAlertOffInterface(true, e),
						() -> this.doAlertOffInterface(false, e));
			}

		}
	}

	private void doAlertOffInterface(boolean isAcept, BbrEvent e)
	{
		if (isAcept)
		{
			reportWorkAlertOff = new BbrWork<>(this, this.getBbrUIParent(), this.btn_AlertsOff);
			reportWorkAlertOff.addFunction(new Function<Object, BaseResultsDTO>()
			{
				@Override
				public BaseResultsDTO apply(Object t)
				{
					return executeAlertOffInterfaceService(AlertsReport.this.getBbrUIParent(), AlertsReport.this.btn_AlertsOff, e);
				}
			});

			this.startWaiting();
			this.executeBbrWork(reportWorkAlertOff);
		}
	}

	private BaseResultsDTO executeAlertOffInterfaceService(BbrUI bbrUIParent, Button triggerButton, BbrEvent ev)
	{
		BaseResultsDTO result = null;
		try
		{
			// Start Tracking
			this.getTimingMngr().startTimer();

			DisableAlertInitParamDTO initParamDTO = this.getDisableAlertInitparam(ev);

			if (triggerButton == this.btn_AlertsOff)
			{
				result = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).doDisableAlertWS(initParamDTO, this.getUser().getId());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.reloadDatingsGrid();
		return result;
	}

	private DisableAlertInitParamDTO getDisableAlertInitparam(BbrEvent event)
	{
		List<Long> alertIdsList = null;
		DisableAlertInitParamDTO initparam = new DisableAlertInitParamDTO();
		PageRangesEvent pageRangeEvent = ((PageRangesEvent) event.getResultObject());
		String eventType = pageRangeEvent.getOption();
		switch (eventType)
		{
			case PageRangesEvent.ALL_PAGES_OPTION:
				initparam.setDisableall(true);
				initparam.setAlertstate(this.initParam.getAlertstate());
				initparam.setAlerttype(this.initParam.getAlerttype());
				initparam.setVendorcode(this.initParam.getVendorcode());
				break;
			case PageRangesEvent.SELECTED_ITEMS_OPTION:
				alertIdsList = this.datGrid_ActiveAlerts.getSelectedItems().stream().map(i -> Long.valueOf(i.getAlertid())).collect(Collectors.toList());
				initparam.setDisableall(false);
				initparam.setAlertids(alertIdsList);
				break;
			default:
				break;
		}
		initparam.setResponsibleuser(this.getBbrUIParent().getUser().getFullName());

		return initparam;
	}

	private void generateFilterClick()
	{
		ClickEvent e = new ClickEvent(filterLayout);
		filterLayout.buttonClick(e);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
}