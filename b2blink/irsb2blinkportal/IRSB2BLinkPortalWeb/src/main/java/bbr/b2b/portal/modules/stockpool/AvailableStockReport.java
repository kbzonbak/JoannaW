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
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.ComponentRenderer;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockInitParamDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockReportDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockReportResultDTO;
import bbr.b2b.logistic.report.data.dto.UpdateItemResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.components.filters.stockpool.AvailableStockReportFilter;
import bbr.b2b.portal.components.modules.stockpool.AvailableStockDetails;
import bbr.b2b.portal.components.modules.stockpool.ItemNotificationDetails;
import bbr.b2b.portal.components.modules.stockpool.ProductDetailReport;
import bbr.b2b.portal.components.modules.stockpool.ProductDetailUpdate;
import bbr.b2b.portal.components.renderers.grid_details.HomologatedProductRenderer;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.utils.PortalDates;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.CoreConstants;
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
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class AvailableStockReport extends BbrModule implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants

	private static final long							serialVersionUID				= 1442791796539290664L;

	private static final String							SKU								= "sku";
	private static final String							DESCRIPTION						= "description";
	private static final String							THRESHOLD						= "threshold";
	private static final String							CRITICALSTOCK					= "criticalstock";
	private static final String							AVAILABLESTOCK					= "availablestock";
	private static final String							NOTIFICATIONDATE				= "notificationdate";
	private static final String							LASTUPLOADEDSTOCK				= "lastuploadedstock";
	private static final String							UPDATEDATE						= "updatedate";
	private static final String							SALES							= "sales";
	private static final String							ACTIVEVEV						= "activevev";

	private static final Integer						DEFAULT_PAGE_NUMBER				= 1;
	private final int									MAX_ROWS_NUMBER					= 100;
	private final String								DEFAULT_SORT_FIELD				= NOTIFICATIONDATE;

	private VerticalLayout								mainLayout						= null;
	private AvailableStockReportFilter					filterLayout					= null;
	private BbrAdvancedGrid<AvailableStockReportDTO>	datGrid_AvailableStock			= null;
	private BbrPagingManager							pagingManager					= null;
	private Button										btn_Refresh						= null;
	private BbrWork<AvailableStockReportResultDTO>		reportWork						= null;
	private BbrWork<FileDownloadInfoResultDTO>			downloadsWork					= null;
	private Boolean										trackReport						= true;
	private Boolean										resetPageInfo					= true;
	private OrderCriteriaDTO							orderCriteria					= null;
	private AvailableStockInitParamDTO					initParam						= null;
	private Button										btn_ExcelDownload				= null;
	private BbrPopupButton								btn_DownloadPopUp				= null;
	private Label										dayTime							= new Label();
	private AvailableStockReportDTO						selectesAvailableStockReport	= null;

	private FunctionalityMngr							functionalityMngr				= null;
	private Button										btn_EditProduct					= null;
	private BbrPopupButton								btn_Actions						= null;

	private Button										btn_ItemDetail					= null;
	private Button										btn_NotificationDetail			= null;
	private String										codeState						= null;
	private boolean										generateAutomatic				= false;
	private Button										btn_ItemHistoryDetail			= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public AvailableStockReport(BbrUI bbrUIParent, FunctionalityMngr functionalityMngr)
	{
		super(bbrUIParent);
		this.functionalityMngr = functionalityMngr;
		this.generateAutomatic = false;
	}

	public AvailableStockReport(BbrUI bbrUIParent, FunctionalityMngr functionalityMngr, String codeState)
	{
		super(bbrUIParent);
		this.functionalityMngr = functionalityMngr;
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
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.initParam = (AvailableStockInitParamDTO) event.getResultObject();
			this.startWaiting();
			this.trackReport = true;
			this.resetPageInfo = true;
			this.executeBbrWork(reportWork);
		}

	}

	@Override
	public void initializeView()
	{
		BbrUser user = this.getUser();

		if (user != null)
		{
			// Filtro
			this.filterLayout = new AvailableStockReportFilter(this, codeState);
			this.filterLayout.initializeView();
			this.setBbrFilterContainer(this.filterLayout);

			// Paging Manager
			this.pagingManager = new BbrPagingManager();
			this.pagingManager.setLocale(this.getUser().getLocale());
			this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

			// Barra de herramientas superior izq
			HorizontalLayout leftButtonsBar = new HorizontalLayout();
			leftButtonsBar.setSpacing(true);
			leftButtonsBar.setMargin(false);
			leftButtonsBar.setHeight("30px");
			leftButtonsBar.addStyleName("toolbar-layout");

			// ACTION BUTTONS SECTION
			VerticalLayout cmp_ActionButtons = new VerticalLayout();
			cmp_ActionButtons.setMargin(new MarginInfo(false, true));
			cmp_ActionButtons.setSpacing(false);

			// reporte detalle de productos
			//TODO
			this.btn_ItemHistoryDetail = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "events_history"));
			this.btn_ItemHistoryDetail.addClickListener((ClickListener & Serializable) e -> this.btn_ProductDetailReport_clickHandler(e));
			this.btn_ItemHistoryDetail.addStyleName(BbrStyles.ACTION_BUTTON);
			this.btn_ItemHistoryDetail.setVisible(true);
			cmp_ActionButtons.addComponent(this.btn_ItemHistoryDetail);

			this.btn_ItemDetail = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "transaction_details_m"));
			this.btn_ItemDetail.addClickListener((ClickListener & Serializable) e -> this.btn_AvailableStockDetails_clickHandler(e));
			this.btn_ItemDetail.addStyleName(BbrStyles.ACTION_BUTTON);
			this.btn_ItemDetail.setVisible(true);
			cmp_ActionButtons.addComponent(this.btn_ItemDetail);

			this.btn_NotificationDetail = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "client_notifications"));
			this.btn_NotificationDetail.addClickListener((ClickListener & Serializable) e -> this.btn_NotificationDetail_ClickHandler(e));
			this.btn_NotificationDetail.addStyleName(BbrStyles.ACTION_BUTTON);
			this.btn_NotificationDetail.setVisible(true);
			cmp_ActionButtons.addComponent(this.btn_NotificationDetail);

			this.btn_Actions = new BbrPopupButton("");
			this.btn_Actions.setIcon(EnumToolbarButton.SEARCH_PRIMARY.getResource());
			this.btn_Actions.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "aditional_actions"));
			this.btn_Actions.addStyleName(BbrStyles.TOOLBAR_BUTTON);
			this.btn_Actions.setContentLayout(cmp_ActionButtons);
			this.btn_Actions.setClosePopupOnOutsideClick(true);
			// END ACTION BUTTONS SECTION

			this.btn_EditProduct = new Button("", EnumToolbarButton.EDIT_ALTERNATIVE.getResource());
			this.btn_EditProduct.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "edit_product_category"));
			this.btn_EditProduct.addClickListener((ClickListener & Serializable) e -> this.btn_EditProduct_clickHandler(e));
			this.btn_EditProduct.addStyleName("toolbar-button");

			leftButtonsBar.addComponents(this.btn_Actions, this.btn_EditProduct);

			VerticalLayout cmp_DownloadProductsButtons = new VerticalLayout();
			cmp_DownloadProductsButtons.setMargin(new MarginInfo(false, true));
			cmp_DownloadProductsButtons.setSpacing(false);

			this.btn_ExcelDownload = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "download_excel_file"));
			this.btn_ExcelDownload.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "download_excel_file"));
			this.btn_ExcelDownload.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_ExcelDownload.addStyleName("action-button");
			cmp_DownloadProductsButtons.addComponent(this.btn_ExcelDownload);

			this.btn_DownloadPopUp = new BbrPopupButton("");
			this.btn_DownloadPopUp.setIcon(BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DownloadPrimary.png"));
			this.btn_DownloadPopUp.addStyleName("toolbar-button");
			this.btn_DownloadPopUp.setContentLayout(cmp_DownloadProductsButtons);
			this.btn_DownloadPopUp.setClosePopupOnOutsideClick(true);

			this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
			this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
			this.btn_Refresh.addStyleName("toolbar-button");

			Button btn_Help = this.getHelpButton();

			// Barra superior dercha
			HorizontalLayout rightButtonsBar = new HorizontalLayout();
			rightButtonsBar.addComponents(this.btn_DownloadPopUp, this.btn_Refresh, btn_Help);
			rightButtonsBar.setSpacing(true);
			rightButtonsBar.setMargin(false);
			rightButtonsBar.setHeight("30px");
			rightButtonsBar.addStyleName(BbrStyles.TOOLBAR_LAYOUT);
			rightButtonsBar.setComponentAlignment(this.btn_DownloadPopUp, Alignment.MIDDLE_RIGHT);

			// Barra superior
			HorizontalLayout toolBar = new HorizontalLayout();
			toolBar.setWidth("100%");
			toolBar.addComponents(leftButtonsBar, this.dayTime, rightButtonsBar);

			toolBar.addStyleName("filter-toolbar");
			toolBar.setExpandRatio(leftButtonsBar, 1F);
			toolBar.setExpandRatio(this.dayTime, 1F);
			toolBar.setExpandRatio(rightButtonsBar, 1F);

			toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
			toolBar.setComponentAlignment(dayTime, Alignment.MIDDLE_CENTER);
			toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);
			// Reporte: Grilla

			this.datGrid_AvailableStock = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.datGrid_AvailableStock.setSortable(false);
			this.initializeDataGridColumns();
			this.datGrid_AvailableStock.addStyleName("report-grid");
			this.datGrid_AvailableStock.setSizeFull();
			this.datGrid_AvailableStock.setSelectionMode(SelectionMode.SINGLE);
			this.datGrid_AvailableStock.setPagingManager(pagingManager, DEFAULT_SORT_FIELD);
			this.datGrid_AvailableStock.addSelectionListener((SelectionListener<AvailableStockReportDTO> & Serializable) e -> selection_gridHandler(e));
			this.datGrid_AvailableStock.addSortListener((SortListener<GridSortOrder<AvailableStockReportDTO>> & Serializable) e -> dataGrid_sortHandler(e));
			this.datGrid_AvailableStock.addItemClickListener((ItemClickListener<AvailableStockReportDTO> & Serializable) e -> dgdItem_clickHandler(e));

			VerticalLayout gridLayout = new VerticalLayout();
			gridLayout.setSizeFull();
			gridLayout.setMargin(false);
			gridLayout.addComponents(this.datGrid_AvailableStock, pagingManager);
			gridLayout.setExpandRatio(this.datGrid_AvailableStock, 1F);

			HorizontalLayout dataLayout = new HorizontalLayout();
			dataLayout.setSizeFull();
			dataLayout.setMargin(false);
			dataLayout.addComponents(gridLayout);

			this.mainLayout = new VerticalLayout();
			mainLayout.addStyleName("report-layout");
			mainLayout.setSizeFull();
			mainLayout.setMargin(false);
			mainLayout.addComponents(toolBar, dataLayout);
			mainLayout.setExpandRatio(dataLayout, 1F);

			this.addComponents(mainLayout);

			this.updateButtons(false);

			this.updateSortOrderCriteria(null);

			reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			reportWork.addFunction(new Function<Object, AvailableStockReportResultDTO>()
			{
				@Override
				public AvailableStockReportResultDTO apply(Object t)
				{
					return executeService(AvailableStockReport.this.getBbrUIParent());
				}
			});

			if (generateAutomatic)
			{
				this.generateFilterClick();
			}

		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0016"));
		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		AvailableStockReport bbrSender = (AvailableStockReport) sender;
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateReport(result, sender);
			}
			else if (triggerObject == this.btn_ExcelDownload)
			{
				bbrSender.doDownloadFile(result, sender, triggerObject);
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
				return executeDownloadService(AvailableStockReport.this.getBbrUIParent(), selectedFormat, btn_DownloadTriggerButton);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	protected FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_ExcelDownload)
				{
					Integer requestedPage = (!this.resetPageInfo && this.pagingManager.getCurrentPage() > 0)
							? (Integer) this.pagingManager.getCurrentPage() : AvailableStockReport.DEFAULT_PAGE_NUMBER;

					this.initParam.setPageNumber(requestedPage);
					this.initParam.setPaginated(false);

					fileResult = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).downloadAvailableStockReport(this.initParam, selectedFormat.getValue(), bbrUIParent.getUser().getId(), bbrUIParent.getLocale());

				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

		return fileResult;
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		AvailableStockReport senderReport = (AvailableStockReport) sender;

		if (result != null)
		{
			AvailableStockReportResultDTO reportResult = (AvailableStockReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				ListDataProvider<AvailableStockReportDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getAvailablestock()));
				senderReport.datGrid_AvailableStock.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.dayTime.setValue(this.transformDate(reportResult.getReportdate(), true));
				senderReport.updateButtons(false);

				if (senderReport.resetPageInfo)
				{
					if (reportResult.getPageInfo() != null)
					{
						PageInfoDTO pageInfo = reportResult.getPageInfo();
						BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
						senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);
					}

				}
				// End Tracking
				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}

				if (reportResult.getAvailablestock().length < 1)
				{
					senderReport.askToOpenFilter(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
							I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "empty_filter_question"));
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

	private AvailableStockReportResultDTO executeService(BbrUI bbrUI)
	{
		AvailableStockReportResultDTO result = null;
		if (this.initParam != null)
		{

			Integer requestedPage = (!this.resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : AvailableStockReport.DEFAULT_PAGE_NUMBER;
			this.initParam.setPageNumber(requestedPage);
			this.initParam.setPaginated(true);
			this.initParam.setByfilter(this.resetPageInfo);
			OrderCriteriaDTO[] orderby = { this.orderCriteria };
			this.initParam.setOrderby(orderby);
			this.initParam.setRows(MAX_ROWS_NUMBER);

			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();

				result = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUI).getAvailableStockByVendorWS(this.initParam, bbrUI.getUser().getId());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "error_webservice"));
			}
		}

		return result;
	}

	private void updateSortOrderCriteria(List<GridSortOrder<AvailableStockReportDTO>> sortOrderList)
	{

		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			OrderCriteriaDTO order = new OrderCriteriaDTO();
			order.setPropertyname(sortOrderList.get(0).getSorted().getId().toUpperCase());
			order.setAscending(sortOrderList.get(0).getDirection() == SortDirection.ASCENDING);
			this.orderCriteria = order;
		}
		else
		{
			OrderCriteriaDTO order = new OrderCriteriaDTO();
			order.setPropertyname(DEFAULT_SORT_FIELD.toUpperCase());
			order.setAscending(true);
			this.orderCriteria = order;

			GridSortOrder<AvailableStockReportDTO> sortOrder = new GridSortOrder<>(datGrid_AvailableStock.getColumn(DEFAULT_SORT_FIELD), SortDirection.DESCENDING);
			sortOrderList = new ArrayList<GridSortOrder<AvailableStockReportDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_AvailableStock.setSortOrder(sortOrderList);
		}
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<AvailableStockReportDTO>> e)
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
		this.startWaiting();

		this.trackReport = false;
		this.resetPageInfo = false;
		this.executeBbrWork(reportWork);
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

	private void initializeDataGridColumns()
	{
		// SKU
		this.datGrid_AvailableStock.addCustomColumn(AvailableStockReportDTO::getSku, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku"), true)
				.setDescriptionGenerator(AvailableStockReportDTO::getSku, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(160)
				.setId(SKU);

		// Descripción
		this.datGrid_AvailableStock.addCustomColumn(AvailableStockReportDTO::getDescription, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_description"), true)
				.setDescriptionGenerator(AvailableStockReportDTO::getDescription, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setId(DESCRIPTION);

		// activo vev
		this.datGrid_AvailableStock.addColumn(item ->
		{
			HomologatedProductRenderer activeRenderer = new HomologatedProductRenderer(item.isActive());
			return activeRenderer;
		}, new ComponentRenderer()).setWidth(110).setStyleGenerator(item -> BbrAlignment.CENTER.getValue()).setId(ACTIVEVEV).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_activevev"));

		// Stock Disponible
		this.datGrid_AvailableStock.addCustomColumn(AvailableStockReportDTO::getAvailablestock, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_available_stock"), true)
				.setDescriptionGenerator(i -> NumericManager.getFormatter(0).format(i.getAvailablestock()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(0))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(110)
				.setId(AVAILABLESTOCK);

		// Stock Critico
		this.datGrid_AvailableStock.addCustomColumn(AvailableStockReportDTO::getCriticalstock, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_available_critical_stock"), true)
				.setDescriptionGenerator(i -> NumericManager.getFormatter(0).format(i.getCriticalstock()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(0))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(100)
				.setId(CRITICALSTOCK);

		// Variacion
		this.datGrid_AvailableStock.addCustomColumn(AvailableStockReportDTO::getThreshold, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_variation"), true)
				.setDescriptionGenerator(i -> NumericManager.getFormatter(0).format(i.getThreshold()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(0))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(100)
				.setId(THRESHOLD);

		// Ultimo stock cargado
		this.datGrid_AvailableStock.addCustomColumn(AvailableStockReportDTO::getLastuploadedstock, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_last_uploaded_stock"), true)
				.setDescriptionGenerator(i -> (i.getLastuploadedstock() != null) ? NumericManager.getFormatter(0).format(i.getLastuploadedstock()) : "", ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(0))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(110)
				.setId(LASTUPLOADEDSTOCK);

		// Ultima carga de stock
		this.datGrid_AvailableStock.addCustomColumn(item -> this.transformDate(item.getUpdatedate(), false), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_update_date"), true)
				.setDescriptionGenerator(item -> this.transformDate(item.getUpdatedate(), false), ContentMode.TEXT)
				.setComparator((item1, item2) -> compareDate(item1.getUpdatedate(), item2.getUpdatedate()))
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setWidth(180)
				.setId(UPDATEDATE);

		// Ventas
		this.datGrid_AvailableStock.addCustomColumn(AvailableStockReportDTO::getSales, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sales"), true)
				.setDescriptionGenerator(i -> (i.getSales() != null) ? NumericManager.getFormatter(0).format(i.getSales()) : "", ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(0))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(110)
				.setId(SALES);

		// Ultima notificacion
		this.datGrid_AvailableStock.addCustomColumn(item -> this.transformDate(item.getNotificationdate(), false), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_last_notification"), true)
				.setDescriptionGenerator(item -> this.transformDate(item.getNotificationdate(), false), ContentMode.TEXT)
				.setComparator((item1, item2) -> compareDate(item1.getNotificationdate(), item2.getNotificationdate()))
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setWidth(180)
				.setId(NOTIFICATIONDATE);

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

	private void updateButtons(Boolean isSelectionEvent)
	{
		if (this.datGrid_AvailableStock != null)

		{
			boolean isEnable = this.datGrid_AvailableStock.getSelectedItems() != null && this.datGrid_AvailableStock.getSelectedItems().size() > 0;
			this.btn_Actions.setEnabled(isEnable);
			this.btn_EditProduct.setEnabled(isEnable);
			if (!isSelectionEvent)
			{
				boolean isEmptyGrid = this.datGrid_AvailableStock.isEmpty();
				this.btn_ExcelDownload.setEnabled(!isEmptyGrid);
			}
			this.btn_Refresh.setEnabled(this.initParam != null);
		}
	}

	private void selection_gridHandler(SelectionEvent<AvailableStockReportDTO> e)
	{
		if (e.getFirstSelectedItem().isPresent())
		{
			this.selectesAvailableStockReport = e.getFirstSelectedItem().get();
		}
		this.updateButtons(true);
	}

	private void dgdItem_clickHandler(ItemClick<AvailableStockReportDTO> e)
	{
		if (e.getMouseEventDetails().isDoubleClick() && e.getItem() != null)
		{
			this.viewConciliationDetails(e.getItem());
		}
	}

	private void viewConciliationDetails(AvailableStockReportDTO selectedItem)
	{
		if (selectedItem != null)
		{
			AvailableStockDetails conciliationDetailsCtrl = new AvailableStockDetails(this.getBbrUIParent(), this.selectesAvailableStockReport, this.initParam, this.functionalityMngr);
			conciliationDetailsCtrl.open(true, true, this);
			conciliationDetailsCtrl.addBbrEventListener((BbrEventListener & Serializable) event -> this.refreshAvailableStock());
		}
	}

	private void btn_ProductDetailReport_clickHandler(ClickEvent event)
	{
		if (this.selectesAvailableStockReport != null)
		{
			this.viewProducDetailReport(this.selectesAvailableStockReport);
		}
	}

	private void btn_AvailableStockDetails_clickHandler(ClickEvent event)
	{
		if (this.selectesAvailableStockReport != null)
		{
			this.viewConciliationDetails(this.selectesAvailableStockReport);
		}
	}

	private void btn_NotificationDetail_ClickHandler(ClickEvent event)
	{
		if (this.selectesAvailableStockReport != null)
		{
			this.viewNotificationDetails(this.selectesAvailableStockReport);
		}
	}

	private void viewNotificationDetails(AvailableStockReportDTO selectedItem)
	{
		if (selectedItem != null)
		{
			ItemNotificationDetails conciliationDetailsCtrl = new ItemNotificationDetails(this.getBbrUIParent(), this.selectesAvailableStockReport, this.initParam, this.functionalityMngr);
			conciliationDetailsCtrl.open(true, true, this);
			conciliationDetailsCtrl.addBbrEventListener((BbrEventListener & Serializable) event -> this.refreshAvailableStock());
		}
	}
	
	private void viewProducDetailReport(AvailableStockReportDTO selectedItem)
	{
		if (selectedItem != null)
		{
			ProductDetailReport productDetailReport = new ProductDetailReport(this.getBbrUIParent(), this.selectesAvailableStockReport, this.initParam, this.functionalityMngr);
			productDetailReport.open(true, true, this);
			productDetailReport.addBbrEventListener((BbrEventListener & Serializable) event -> this.refreshAvailableStock());
		}
	}

	private void btn_EditProduct_clickHandler(ClickEvent event)
	{
		AvailableStockReportDTO selectedProduct = this.datGrid_AvailableStock.getSelectedItems().iterator().next();
		ProductDetailUpdate winEditProduct = new ProductDetailUpdate(this.getBbrUIParent(), selectedProduct, this.initParam.getVendorcode());
		winEditProduct.initializeView();
		winEditProduct.open(true);
		winEditProduct.addBbrEventListener((BbrEventListener & Serializable) e -> productOperation_handler(e));
	}

	private void refreshAvailableStock()
	{
		this.startWaiting();

		this.resetPageInfo = false;
		this.trackReport = false;
		this.executeBbrWork(reportWork);
	}

	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		if (this.btn_DownloadTriggerButton == this.btn_ExcelDownload)
		{
			this.selectDownloadFileType(DownloadFormats.XLS, DownloadFormats.XLS, DownloadFormats.CSV);
		}
	}

	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		AvailableStockReport senderReport = (AvailableStockReport) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

	protected void downloadLinkFile(Object fileInfo)
	{
		downloadLinkFile(fileInfo);
	}

	private String formatDate(LocalDateTime date)
	{
		return date != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(date) : "";
	}

	private void productOperation_handler(BbrEvent e)
	{
		if (e != null && e.getResultObject() != null)
		{
			this.refreshAvailableStock();
		}
		this.startWaiting();
		this.executeBbrWork(reportWork);

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
