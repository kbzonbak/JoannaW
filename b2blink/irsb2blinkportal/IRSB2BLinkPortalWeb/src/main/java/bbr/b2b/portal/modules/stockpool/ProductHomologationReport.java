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
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ComponentRenderer;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.logistic.report.data.dto.HomologationReportDTO;
import bbr.b2b.logistic.report.data.dto.HomologationReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.HomologationReportResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.components.filters.stockpool.ProductHomologationReportFilter;
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
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class ProductHomologationReport extends BbrModule implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants

	private static final long						serialVersionUID				= 1442791796539290664L;

	private static final String						VENDORSKU						= "skuvendor";
	private static final String						ITEMDESCRIPTION					= "vendordescription";
	private static final String						ACTIVEVEV						= "activevev";
	private static final String						BUYERNAME						= "buyername";
	private static final String						BUYERSKU						= "skubuyer";
	private static final String						ENABLEDSPL						= "enabledspl";

	private static final Integer					DEFAULT_PAGE_NUMBER				= 1;
	private final int								MAX_ROWS_NUMBER					= 100;
	private final String							DEFAULT_SORT_FIELD				= VENDORSKU;

	private VerticalLayout							mainLayout						= null;
	private BbrAdvancedGrid<HomologationReportDTO>	datGrid_HomologationReport		= null;
	private BbrPagingManager						pagingManager					= null;
	private Button									btn_Refresh						= null;
	private BbrWork<HomologationReportResultDTO>	reportWork						= null;
	private BbrWork<FileDownloadInfoResultDTO>		downloadsWork					= null;
	private Boolean									trackReport						= true;
	private Boolean									resetPageInfo					= true;
	private OrderCriteriaDTO						orderCriteria					= null;
	private HomologationReportInitParamDTO			initParam						= null;
	private Button									btn_ExcelDownload				= null;
	private BbrPopupButton							btn_DownloadPopUp				= null;
	private Label									dayTime							= new Label();


	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductHomologationReport(BbrUI bbrUIParent)
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
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.initParam = (HomologationReportInitParamDTO) event.getResultObject();
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
			ProductHomologationReportFilter filterLayout = new ProductHomologationReportFilter(this);
			filterLayout.initializeView();
			this.setBbrFilterContainer(filterLayout);

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

			// Barra superior derecha
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

			this.datGrid_HomologationReport = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.datGrid_HomologationReport.setSortable(false);
			this.initializeDataGridColumns();
			this.datGrid_HomologationReport.addStyleName("report-grid");
			this.datGrid_HomologationReport.setSizeFull();
			this.datGrid_HomologationReport.setSelectionMode(SelectionMode.SINGLE);
			this.datGrid_HomologationReport.setPagingManager(pagingManager, DEFAULT_SORT_FIELD);
			this.datGrid_HomologationReport.addSortListener((SortListener<GridSortOrder<HomologationReportDTO>> & Serializable) e -> dataGrid_sortHandler(e));

			VerticalLayout gridLayout = new VerticalLayout();
			gridLayout.setSizeFull();
			gridLayout.setMargin(false);
			gridLayout.addComponents(this.datGrid_HomologationReport, pagingManager);
			gridLayout.setExpandRatio(this.datGrid_HomologationReport, 1F);

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

			//this.updateButtons(false);

			this.updateSortOrderCriteria(null);

			reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			reportWork.addFunction(new Function<Object, HomologationReportResultDTO>()
			{
				@Override
				public HomologationReportResultDTO apply(Object t)
				{
					return executeService(ProductHomologationReport.this.getBbrUIParent());
				}
			});

		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0016"));
		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ProductHomologationReport bbrSender = (ProductHomologationReport) sender;
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
				return executeDownloadService(ProductHomologationReport.this.getBbrUIParent(), selectedFormat, btn_DownloadTriggerButton);
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
							? (Integer) this.pagingManager.getCurrentPage() : ProductHomologationReport.DEFAULT_PAGE_NUMBER;

					this.initParam.setPageNumber(requestedPage);
					this.initParam.setPaginated(false);

					fileResult = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).downloadHomologationReport(this.initParam, selectedFormat.getValue(), bbrUIParent.getUser().getId(), bbrUIParent.getLocale());

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

		ProductHomologationReport senderReport = (ProductHomologationReport) sender;

		if (result != null)
		{
			HomologationReportResultDTO reportResult = (HomologationReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				ListDataProvider<HomologationReportDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getHomologationreport()));
				senderReport.datGrid_HomologationReport.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.dayTime.setValue(this.transformDate(reportResult.getReportdate(), true));
				//senderReport.updateButtons(false);

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

				if (reportResult.getHomologationreport().length < 1)
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

	private HomologationReportResultDTO executeService(BbrUI bbrUI)
	{
		HomologationReportResultDTO result = null;
		if (this.initParam != null)
		{

			Integer requestedPage = (!this.resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : ProductHomologationReport.DEFAULT_PAGE_NUMBER;
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
				result = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUI).getHomologationReport(this.initParam, bbrUI.getUser().getId());
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


	private void updateSortOrderCriteria(List<GridSortOrder<HomologationReportDTO>> sortOrderList)
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

			GridSortOrder<HomologationReportDTO> sortOrder = new GridSortOrder<>(datGrid_HomologationReport.getColumn(DEFAULT_SORT_FIELD), SortDirection.DESCENDING);
			sortOrderList = new ArrayList<GridSortOrder<HomologationReportDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_HomologationReport.setSortOrder(sortOrderList);
		}
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<HomologationReportDTO>> e)
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

		// SKU vendor
		this.datGrid_HomologationReport.addCustomColumn(HomologationReportDTO::getSkuvendor, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku_vendor"), true)
				.setDescriptionGenerator(HomologationReportDTO::getSkuvendor, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(200)
				.setId(VENDORSKU);

		// Descripción vendoritem
		this.datGrid_HomologationReport.addCustomColumn(HomologationReportDTO::getVendoritemdescription, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_description"), true)
				.setDescriptionGenerator(HomologationReportDTO::getVendoritemdescription, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setId(ITEMDESCRIPTION);


		// activo vev
		this.datGrid_HomologationReport.addColumn(item ->
		{
			HomologatedProductRenderer activeRenderer = new HomologatedProductRenderer(item.isActive());
			return activeRenderer;
		}, new ComponentRenderer()).setWidth(110).setStyleGenerator(item -> BbrAlignment.CENTER.getValue()).setId(ACTIVEVEV).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_activevev"));

		// Buyername
		this.datGrid_HomologationReport.addCustomColumn(HomologationReportDTO::getBuyername, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_client"), true)
				.setDescriptionGenerator(HomologationReportDTO::getBuyername, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(200)
				.setId(BUYERNAME);

		// Buyer SKU
		this.datGrid_HomologationReport.addCustomColumn(HomologationReportDTO::getSkubuyer, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku_buyer"), true)
				.setDescriptionGenerator(HomologationReportDTO::getSkubuyer, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(200)
				.setId(BUYERSKU);


		// habilitado SPL
		this.datGrid_HomologationReport.addColumn(item ->
		{
			HomologatedProductRenderer activeRenderer = new HomologatedProductRenderer(item.isEnabledspl());
			return activeRenderer;
		}, new ComponentRenderer()).setWidth(150).setStyleGenerator(item -> BbrAlignment.CENTER.getValue()).setId(ENABLEDSPL).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_enabledspl"));

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
		ProductHomologationReport senderReport = (ProductHomologationReport) sender;
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

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
