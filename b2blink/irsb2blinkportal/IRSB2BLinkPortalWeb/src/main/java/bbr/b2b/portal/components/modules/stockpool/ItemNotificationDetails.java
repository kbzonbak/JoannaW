package bbr.b2b.portal.components.modules.stockpool;

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
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockInitParamDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockReportDTO;
import bbr.b2b.logistic.report.data.dto.ProductNotificationReportDTO;
import bbr.b2b.logistic.report.data.dto.ProductNotificationReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.ProductNotificationReportResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.utils.PortalDates;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class ItemNotificationDetails extends BbrWindow implements BbrWorkExecutor
{
	private static final long								serialVersionUID				= 409086468924554268L;

	private static final String								NOTIFDATE						= "notifdate";
	private static final String								NOTIFID							= "notificationid";
	private static final String								CLIENTNAME						= "clientname";
	private static final String								SKUBUYER						= "skubuyer";
	private static final String								QUANTITY						= "notifquantity";

	private Button											btn_Refresh						= null;
	private Button											btn_ExcelDownload				= null;
	private BbrPopupButton									btn_DownloadPopUp				= null;
	private Label											dayTime							= new Label();

	private BbrAdvancedGrid<ProductNotificationReportDTO>	datGrid_NotifDetails			= null;
	private BbrPagingManager								pagingManager					= null;
	private Boolean											resetPageInfo					= true;
	private OrderCriteriaDTO[]								orderCriteria					= null;
	private final int										DEFAULT_PAGE_NUMBER				= 1;
	private final int										MAX_ROWS_NUMBER					= 50;
	private final String									DEFAULT_SORT_FIELD				= NOTIFDATE;
	private CompanyDataDTO									selectedCompanyData				= null;
	private BbrWork<FileDownloadInfoResultDTO>				downloadsWork					= null;
	private BbrWork<ProductNotificationReportResultDTO>		reportWork						= null;

	private AvailableStockReportDTO							selectesAvailableStockReport	= null;
	private AvailableStockInitParamDTO						initParam						= null;

	public ItemNotificationDetails(BbrUI parentUI, AvailableStockReportDTO selectesAvailableStockReport, AvailableStockInitParamDTO initParam, FunctionalityMngr functionalityMngr)
	{
		super(parentUI);
		this.selectesAvailableStockReport = selectesAvailableStockReport;
		this.initParam = initParam;
	}

	@Override
	public void initializeView()
	{

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChangeDetails_Listener(e), BbrPagingEvent.PAGE_CHANGED);

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

		// Barra superior dercha
		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(this.btn_DownloadPopUp, this.btn_Refresh);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName(BbrStyles.TOOLBAR_LAYOUT);
		rightButtonsBar.setComponentAlignment(this.btn_DownloadPopUp, Alignment.MIDDLE_RIGHT);

		// Barra superior
		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(this.dayTime, rightButtonsBar);

		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(this.dayTime, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(dayTime, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Reporte: Grilla
		this.datGrid_NotifDetails = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.datGrid_NotifDetails.setSortable(false);
		this.datGrid_NotifDetails.setSizeFull();
		this.datGrid_NotifDetails.addStyleName("report-grid");
		this.datGrid_NotifDetails.addSortListener((SortListener<GridSortOrder<ProductNotificationReportDTO>> & Serializable) e -> dataGrid_sortHandler(e));

		this.initializeMatchableDocumentDataGridColumns();

		// Main Layout

		VerticalLayout mainLayout = new VerticalLayout(toolBar, this.datGrid_NotifDetails, this.pagingManager);
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.setExpandRatio(this.datGrid_NotifDetails, 1F);
		mainLayout.addStyleName("bbr-margin-windows");

		// Ventana

		this.setWidth("1100px");
		this.setHeight("600px");
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, "client_notifications"));
		this.setContent(mainLayout);

		this.updateSortOrderMatchableDocumentCriteria(null);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, ProductNotificationReportResultDTO>()
		{
			@Override
			public ProductNotificationReportResultDTO apply(Object t)
			{
				return executetaxDocumentsService(ItemNotificationDetails.this.getBbrUIParent());
			}
		});

		this.startWaiting();
		this.executeBbrWork(reportWork);
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor senderPostExecute, Object triggerObject)
	{
		if (result != null)
		{
			if (result instanceof ProductNotificationReportResultDTO)
			{
				this.doUpdateReport(result, senderPostExecute);
			}
			else if (triggerObject == this.btn_ExcelDownload)
			{
				this.doDownloadFile(result, senderPostExecute, triggerObject);
			}
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
				return executeDownloadService(ItemNotificationDetails.this.getBbrUIParent(), selectedFormat, btn_DownloadTriggerButton);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		ItemNotificationDetails senderReport = (ItemNotificationDetails) sender;

		if (result != null)
		{
			ProductNotificationReportResultDTO reportResult = (ProductNotificationReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				ListDataProvider<ProductNotificationReportDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getProductreport()));

				senderReport.datGrid_NotifDetails.setDataProvider(dataprovider, senderReport.resetPageInfo);															
				senderReport.dayTime.setValue("Producto     " + this.selectesAvailableStockReport.getSku() +"  -  "+ this.selectesAvailableStockReport.getDescription() /*Descomentar si piden agregar fecha this.transformDate(reportResult.getReportdate(), true)*/);
				if (senderReport.resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageInfo();

					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);
				}
			}
		}
		this.stopWaiting();

	}

	private ProductNotificationReportResultDTO executetaxDocumentsService(BbrUI bbrUIParent)
	{
		ProductNotificationReportResultDTO result = null;
		ProductNotificationReportInitParamDTO initParams = this.getProductNotificationDetailInitParam();

		if (initParams != null)
		{
			try
			{
				result = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).getProductNotificationReportWS(initParams, bbrUIParent.getUser().getId());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	private ProductNotificationReportInitParamDTO getProductNotificationDetailInitParam()
	{
		ProductNotificationReportInitParamDTO initParams = new ProductNotificationReportInitParamDTO();

		Integer requestedPage = (!resetPageInfo && pagingManager.getCurrentPage() > 0) ? (Integer) pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;

		initParams.setPageNumber(requestedPage);
		initParams.setRows(MAX_ROWS_NUMBER);
		initParams.setByfilter(true);
		initParams.setPaginated(true);
		initParams.setVendoritemid(this.selectesAvailableStockReport.getVendoritemid());
		initParams.setVendorcode(this.initParam.getVendorcode());
		initParams.setVendoritemid(this.selectesAvailableStockReport.getVendoritemid());
		initParams.setOrderby(this.orderCriteria);
		return initParams;
	}

	private void initializeMatchableDocumentDataGridColumns()
	{
		// Fecha notificacion
		this.datGrid_NotifDetails.addCustomColumn(i -> this.transformDate(i.getNotifdate(), false), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_txdate"), true)
				.setDescriptionGenerator(i -> this.transformDate(i.getNotifdate(), false), ContentMode.TEXT)
				.setComparator((item1, item2) -> compareDate(item1.getNotifdate(), item2.getNotifdate()))
				.setWidth(160)
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setId(NOTIFDATE);

		// Id de la notificación

		this.datGrid_NotifDetails.addCustomColumn(i -> (i.getNotificationid() != null) ? "SPL_"+i.getNotificationid().toString() : "", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_notifid"), true)
				.setDescriptionGenerator(i -> (i.getNotificationid() != null) ? "SPL_"+i.getNotificationid().toString() : "", ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setWidth(160)
				.setId(NOTIFID);
		
//		this.datGrid_NotifDetails.addCustomColumn(ProductNotificationReportDTO::getNotificationid, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku_buyer"), true)
//		.setDescriptionGenerator(ProductNotificationReportDTO::getNotificationid, ContentMode.TEXT)
//		.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
//		.setId(NOTIFID);

		// Nombre Cliente
		this.datGrid_NotifDetails.addCustomColumn(ProductNotificationReportDTO::getClientname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_client"), true)
				.setDescriptionGenerator(ProductNotificationReportDTO::getClientname, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setId(CLIENTNAME);

		// Sku Cliente
		this.datGrid_NotifDetails.addCustomColumn(ProductNotificationReportDTO::getSkubuyer, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku_buyer"), true)
				.setDescriptionGenerator(ProductNotificationReportDTO::getSkubuyer, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setId(SKUBUYER);

		// Cantidad
		this.datGrid_NotifDetails.addCustomColumn(i -> (i.getNotifquantity() != null) ? NumericManager.getFormatter(0).format(i.getNotifquantity()) : "", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_quantity"), true)
				.setDescriptionGenerator(i -> (i.getNotifquantity() != null) ? NumericManager.getFormatter(0).format(i.getNotifquantity()) : "", ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(140)
				.setId(QUANTITY);

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

	private void updateSortOrderMatchableDocumentCriteria(List<GridSortOrder<ProductNotificationReportDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<ProductNotificationReportDTO> sorOrder : sortOrderList)
			{
				OrderCriteriaDTO order = new OrderCriteriaDTO();
				order.setPropertyname(sorOrder.getSorted().getId().toUpperCase());
				order.setAscending(sorOrder.getDirection() == SortDirection.ASCENDING);
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

			GridSortOrder<ProductNotificationReportDTO> sortOrder = new GridSortOrder<>(datGrid_NotifDetails.getColumn(DEFAULT_SORT_FIELD), SortDirection.DESCENDING);
			sortOrderList = new ArrayList<GridSortOrder<ProductNotificationReportDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_NotifDetails.setSortOrder(sortOrderList);
		}
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<ProductNotificationReportDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.startWaiting();
			this.updateSortOrderMatchableDocumentCriteria(e.getSortOrder());
			// this.trackReport = false;
			this.resetPageInfo = true;
			this.executeBbrWork(reportWork);
		}
	}

	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.startWaiting();

		this.resetPageInfo = false;
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

	protected FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_ExcelDownload)
				{
					ProductNotificationReportInitParamDTO initParams = this.getProductNotificationDetailInitParam();
					initParams.setPaginated(false);
					fileResult = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).downloadItemNotificationReport(initParams, selectedFormat.getValue(), bbrUIParent.getUser().getId(), bbrUIParent.getLocale());
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

		return fileResult;
	}

	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ItemNotificationDetails senderReport = (ItemNotificationDetails) sender;
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

	private void pagingChangeDetails_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.startWaiting();
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
	}
}
