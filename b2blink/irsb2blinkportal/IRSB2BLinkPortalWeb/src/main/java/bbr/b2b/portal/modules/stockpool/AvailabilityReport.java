package bbr.b2b.portal.modules.stockpool;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.logistic.report.data.dto.AvailabilityReportDTO;
import bbr.b2b.logistic.report.data.dto.AvailabilityReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.AvailabilityReportResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.stockpool.AvailabilityReportFilter;
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
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class AvailabilityReport extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long						serialVersionUID			= 1717349615397710932L;

	private static final String						BUYERNAME					= "buyername";
	private static final String						SKUBUYER					= "skubuyer";
	private static final String						SKUVENDOR					= "skuvendor";
	private static final String						ITEMDESCRIPTION				= "itemdescription";
	private static final String						NOTIFICATIONDATE			= "notificationdate";
	private static final String						NOTIFIEDSTOCK				= "notifiedstock";
	private static final String						LASTSALES					= "lastsales";
	private static final String						CLIENTAVAILABILITY			= "clientavailability";

	// Constantes

	private final String							DEFAULT_SORT_FIELD			= NOTIFICATIONDATE;

	// Variables
	private AvailabilityReportInitParamDTO			initParam					= null;
	private AvailabilityReportFilter				filterLayout				= null;
	private BbrWork<AvailabilityReportResultDTO>	reportWork					= null;
	private BbrWork<FileDownloadInfoResultDTO>		downloadsWork				= null;

	private Boolean									trackReport					= true;
	private Boolean									resetPageInfo				= true;
	private VerticalLayout							mainLayout					= null;
	private BbrAdvancedGrid<AvailabilityReportDTO>	datGrid_Availability		= null;
	private BbrPagingManager						pagingManager				= null;

	private Button									btn_DownloadExcel			= null;
	private BbrPopupButton							btn_Download				= null;
	private Button									btn_Refresh					= null;

	ListDataProvider<AvailabilityReportDTO>			dataproviderDatingReport	= null;
	private OrderCriteriaDTO[]						orderCriteria				= null;
	private String									clientName					= "";
	private boolean									generateAutomatic			= false;
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public AvailabilityReport(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
		this.generateAutomatic = false;
	}

	public AvailabilityReport(BbrUI bbrUIParent, FunctionalityMngr functionalityMngr, String clientName)
	{
		super(bbrUIParent);
		this.clientName = clientName;
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
		this.filterLayout = new AvailabilityReportFilter(this, this.clientName);
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

		Button btn_Help = this.getHelpButton();

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(this.btn_Download, this.btn_Refresh, btn_Help);
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
		this.datGrid_Availability = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.datGrid_Availability.setSortable(false);

		this.initializeDataGridColumns();

		this.datGrid_Availability.addStyleName("report-grid");
		this.datGrid_Availability.setSizeFull();
		this.datGrid_Availability.setPagingManager(pagingManager, this.DEFAULT_SORT_FIELD);
		this.datGrid_Availability.setSelectionMode(SelectionMode.SINGLE);
		this.datGrid_Availability.addSortListener((SortListener<GridSortOrder<AvailabilityReportDTO>> & Serializable) e -> this.dataGrid_sortHandler(e));
		this.datGrid_Availability.addSelectionListener((SelectionListener<AvailabilityReportDTO> & Serializable) e -> this.selection_gridHandler(e));

		this.mainLayout = new VerticalLayout();
		this.mainLayout.addStyleName("report-layout");
		this.mainLayout.setSizeFull();
		this.mainLayout.setMargin(false);
		this.mainLayout.addComponents(toolBar, this.datGrid_Availability, pagingManager);
		this.mainLayout.setExpandRatio(this.datGrid_Availability, 1F);
		this.addComponents(mainLayout);

		this.updateButtons(false);
		this.updateSortOrderCriteria(null);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, AvailabilityReportResultDTO>()
		{
			@Override
			public AvailabilityReportResultDTO apply(Object t)
			{
				return executeService(AvailabilityReport.this.getBbrUIParent());
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
		}
		else
		{
			AvailabilityReport senderReport = (AvailabilityReport) sender;
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
			this.initParam = (AvailabilityReportInitParamDTO) event.getResultObject();
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
				return executeDownloadService(AvailabilityReport.this.getBbrUIParent(), selectedFormat, btn_DownloadTriggerButton);
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

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<AvailabilityReportDTO>> e)
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

	private void selection_gridHandler(SelectionEvent<AvailabilityReportDTO> e)
	{
		this.updateButtons(true);
	}

	private void updateButtons(Boolean isSelectionEvent)
	{
		if (this.datGrid_Availability != null)
		{
			if (!isSelectionEvent)
			{
				boolean isEmptyGrid = this.datGrid_Availability.isEmpty();
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
		this.resetPageInfo = false;
		this.executeBbrWork(reportWork);
	}

	private void initializeDataGridColumns()
	{

		// Cliente
		this.datGrid_Availability.addCustomColumn(AvailabilityReportDTO::getBuyername, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_buyer_name"), true)
				.setDescriptionGenerator(AvailabilityReportDTO::getBuyername, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setWidth(150)
				.setId(BUYERNAME);

		// SKU Cliente
		this.datGrid_Availability.addCustomColumn(AvailabilityReportDTO::getSkubuyer, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_buyer_sku"), true)
				.setDescriptionGenerator(AvailabilityReportDTO::getSkubuyer, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(150)
				.setId(SKUBUYER);

		// SKU Prov.
		this.datGrid_Availability.addCustomColumn(AvailabilityReportDTO::getSkuvendor, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_vendor_sku"), true)
				.setDescriptionGenerator(AvailabilityReportDTO::getSkuvendor, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(150)
				.setId(SKUVENDOR);

		// Descripcion
		this.datGrid_Availability.addCustomColumn(AvailabilityReportDTO::getItemdescription, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_description"), true)
				.setDescriptionGenerator(AvailabilityReportDTO::getItemdescription, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setId(ITEMDESCRIPTION);

		// Fecha Act.
		this.datGrid_Availability.addCustomColumn(item -> this.transformDate(item.getNotificationdate(), false), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_notification_date"), true)
				.setDescriptionGenerator(item -> this.transformDate(item.getNotificationdate(), false), ContentMode.TEXT)
				.setComparator((item1, item2) -> compareDate(item1.getNotificationdate(), item2.getNotificationdate()))
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setWidth(160)
				.setId(NOTIFICATIONDATE);

		// Disponible Informado
		this.datGrid_Availability.addCustomColumn(AvailabilityReportDTO::getNotifiedstock, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_notified_stock"), true)
				.setDescriptionGenerator(i -> NumericManager.getFormatter(0).format(i.getNotifiedstock()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(0))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(110)
				.setId(NOTIFIEDSTOCK);

		// Ventas
		this.datGrid_Availability.addCustomColumn(AvailabilityReportDTO::getLastsales, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_last_sales"), true)
				.setDescriptionGenerator(i -> NumericManager.getFormatter(0).format(i.getLastsales()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(0))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(110)
				.setId(LASTSALES);

		// Disponible teórico
		this.datGrid_Availability.addCustomColumn(AvailabilityReportDTO::getClientavailability, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_client_availability"), true)
				.setDescriptionGenerator(i -> NumericManager.getFormatter(0).format(i.getClientavailability()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(0))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(150)
				.setId(CLIENTAVAILABILITY);

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
		int result = 0;

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

	private void updateSortOrderCriteria(List<GridSortOrder<AvailabilityReportDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();

			for (GridSortOrder<AvailabilityReportDTO> sortOrder : sortOrderList)
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

			GridSortOrder<AvailabilityReportDTO> sortOrder = new GridSortOrder<>(this.datGrid_Availability.getColumn(DEFAULT_SORT_FIELD), SortDirection.DESCENDING);
			sortOrderList = new ArrayList<GridSortOrder<AvailabilityReportDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_Availability.setSortOrder(sortOrderList);
		}
	}

	private AvailabilityReportResultDTO executeService(BbrUI bbrUI)
	{
		Integer requestedPage = (!this.resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage()
				: PortalConstants.DEFAULT_PAGE_NUMBER;

		AvailabilityReportResultDTO result = null;

		if (this.initParam != null)
		{
			try
			{
				this.getTimingMngr().startTimer();

				this.initParam.setOrderby(this.orderCriteria);
				this.initParam.setPageNumber(requestedPage);
				this.initParam.setPaginated(this.resetPageInfo);
				this.initParam.setByfilter(true);

				result = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUI).getAvailabilityReportWS(this.initParam, bbrUI.getUser().getId());
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

		try
		{
			if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
			{
				fileResult = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).getExcelAvailabilityReportWS(this.initParam, selectedFormat.getValue(), bbrUIParent.getUser().getId(), bbrUIParent.getLocale());

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

		AvailabilityReport senderReport = (AvailabilityReport) sender;

		if (result != null)
		{
			AvailabilityReportResultDTO reportResult = (AvailabilityReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);
			if (!error.hasError())
			{
				if (reportResult.getAvailabilityreport() != null && reportResult.getAvailabilityreport().length > 0)
				{
					dataproviderDatingReport = new ListDataProvider<>(Arrays.asList(reportResult.getAvailabilityreport()));
					senderReport.datGrid_Availability.setDataProvider(dataproviderDatingReport, senderReport.resetPageInfo);
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
					dataproviderDatingReport = new ListDataProvider<>(Arrays.asList(new AvailabilityReportDTO[0]));
					senderReport.datGrid_Availability.setDataProvider(dataproviderDatingReport, senderReport.resetPageInfo);
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
		AvailabilityReport senderReport = (AvailabilityReport) senderPostExecute;

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

	private void generateFilterClick()
	{
		ClickEvent e = new ClickEvent(filterLayout);
		filterLayout.buttonClick(e);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
}