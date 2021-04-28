package bbr.b2b.portal.components.modules.stockpool;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockInitParamDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockReportDTO;
import bbr.b2b.logistic.report.data.dto.ProductDetailReportDTO;
import bbr.b2b.logistic.report.data.dto.ProductDetailReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.ProductDetailReportResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.utils.PortalDates;
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
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class ProductDetailReport extends BbrWindow implements BbrWorkExecutor
{
	private static final long						serialVersionUID				= 409086468924554268L;

	private static final String						DATETIME						= "datetime";
	private static final String						TYPE							= "type";
	private static final String						CLIENTNAME						= "clientname";
	private static final String						REFERENCE						= "reference";
	private static final String						UPDATE							= "update";
	private static final String						SALE							= "sale";
	private static final String						NOTIFICATION					= "notification";

	private Button									btn_Refresh						= null;
	private Button									btn_ExcelDownload				= null;
	private BbrPopupButton							btn_DownloadPopUp				= null;
	private Label									dayTime							= new Label();

	private BbrAdvancedGrid<ProductDetailReportDTO>	datGrid_NotifDetails			= null;
	private BbrPagingManager						pagingManager					= null;
	private Boolean									resetPageInfo					= true;
	private OrderCriteriaDTO[]						orderCriteria					= null;
	private final int								DEFAULT_PAGE_NUMBER				= 1;
	private final int								MAX_ROWS_NUMBER					= 50;
	private final String							DEFAULT_SORT_FIELD				= DATETIME;
	private BbrWork<FileDownloadInfoResultDTO>		downloadsWork					= null;
	private BbrWork<ProductDetailReportResultDTO>	reportWork						= null;

	private AvailableStockReportDTO					selectesAvailableStockReport	= null;
	private AvailableStockInitParamDTO				initParam						= null;
	private BbrComboBox<String>						cbx_lasttx						= null;
	private String[]								numbers							= { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

	public ProductDetailReport(BbrUI parentUI, AvailableStockReportDTO selectesAvailableStockReport, AvailableStockInitParamDTO initParam, FunctionalityMngr functionalityMngr)
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
		
		Label lbl_productDetailLimit = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "product_detail_limit"));
		lbl_productDetailLimit.setStyleName("label-product-detail-limit");		

		this.cbx_lasttx = new BbrComboBox<String>(this.numbers);		
		this.cbx_lasttx.setTextInputAllowed(false);		
		this.cbx_lasttx.setEmptySelectionAllowed(false);
		this.cbx_lasttx.setWidth("20%");		
		this.cbx_lasttx.setId("combo producto");
		this.cbx_lasttx.selectFirstItem();

		this.cbx_lasttx.addValueChangeListener((ValueChangeListener<String>) e -> this.refreshReport_comboHandler(e));

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
		toolBar.addComponents(lbl_productDetailLimit, this.cbx_lasttx, rightButtonsBar);

		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(lbl_productDetailLimit, 0.65F);
		toolBar.setExpandRatio(this.cbx_lasttx, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(cbx_lasttx, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Reporte: Grilla
		this.datGrid_NotifDetails = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.datGrid_NotifDetails.setSortable(false);
		this.datGrid_NotifDetails.setSizeFull();
		this.datGrid_NotifDetails.addStyleName("report-grid");
		this.datGrid_NotifDetails.addSortListener((SortListener<GridSortOrder<ProductDetailReportDTO>> & Serializable) e -> dataGrid_sortHandler(e));

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
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "events_history"));
		this.setContent(mainLayout);

		this.updateSortOrderMatchableDocumentCriteria(null);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, ProductDetailReportResultDTO>()
		{
			@Override
			public ProductDetailReportResultDTO apply(Object t)
			{
				return executetaxDocumentsService(ProductDetailReport.this.getBbrUIParent());
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
			if (result instanceof ProductDetailReportResultDTO)
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
				return executeDownloadService(ProductDetailReport.this.getBbrUIParent(), selectedFormat, btn_DownloadTriggerButton);
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
		ProductDetailReport senderReport = (ProductDetailReport) sender;

		if (result != null)
		{
			ProductDetailReportResultDTO reportResult = (ProductDetailReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				ListDataProvider<ProductDetailReportDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getProductdetailreport()));

				senderReport.datGrid_NotifDetails.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.dayTime.setValue(
						"Producto     " + this.selectesAvailableStockReport.getSku() + "  -  " + this.selectesAvailableStockReport.getDescription() /* Descomentar si piden agregar fecha this.transformDate(reportResult.getReportdate(), true) */);
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

	private ProductDetailReportResultDTO executetaxDocumentsService(BbrUI bbrUIParent)
	{
		ProductDetailReportResultDTO result = null;
		ProductDetailReportInitParamDTO initParams = this.getProductDetailReportInitParam();

		if (initParams != null)
		{
			try
			{
				// TODO
				result = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).getProductDetailReportWS(initParams, bbrUIParent.getUser().getId());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	private ProductDetailReportInitParamDTO getProductDetailReportInitParam()
	{
		ProductDetailReportInitParamDTO initParams = new ProductDetailReportInitParamDTO();

		Integer requestedPage = (!resetPageInfo && pagingManager.getCurrentPage() > 0) ? (Integer) pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;

		initParams.setPageNumber(requestedPage);
		initParams.setRows(MAX_ROWS_NUMBER);
		initParams.setByfilter(true);
		initParams.setPaginated(true);
		initParams.setVendoritemid(this.selectesAvailableStockReport.getVendoritemid());
		initParams.setVendoritemid(this.selectesAvailableStockReport.getVendoritemid());
		initParams.setOrderby(this.orderCriteria);
		initParams.setUpdatequantitylimit(this.cbx_lasttx.getSelectedValue() == null ? 1
				: Integer.valueOf(this.cbx_lasttx.getSelectedValue()));
		return initParams;
	}

	private void initializeMatchableDocumentDataGridColumns()
	{

		// FECHA / HORA
		this.datGrid_NotifDetails.addCustomColumn(i -> this.transformDate(i.getDatetime(), false), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "date_time"), true)
				.setDescriptionGenerator(i -> this.transformDate(i.getDatetime(), false), ContentMode.TEXT)
				.setComparator((item1, item2) -> compareDate(item1.getDatetime(), item2.getDatetime()))
				.setWidth(160)
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setId(DATETIME);

		// TIPO
		this.datGrid_NotifDetails.addCustomColumn(ProductDetailReportDTO::getType, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "type"), true)
				.setDescriptionGenerator(ProductDetailReportDTO::getType, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setId(TYPE);

		// Cliente
		this.datGrid_NotifDetails.addCustomColumn(ProductDetailReportDTO::getClientname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "client"), true)
				.setDescriptionGenerator(ProductDetailReportDTO::getClientname, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setId(CLIENTNAME);

//		// REFERENCIA
//		this.datGrid_NotifDetails.addCustomColumn(i -> (i.getReference() != null) ? NumericManager.getFormatter(0).format(i.getReference()) : "", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "reference"), true)
//				.setDescriptionGenerator(i -> (i.getReference() != null) ? NumericManager.getFormatter(0).format(i.getReference()) : "", ContentMode.TEXT)
//				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
//				.setWidth(140)
//				.setId(REFERENCE);
		
		// REFERENCIA
		this.datGrid_NotifDetails.addCustomColumn(ProductDetailReportDTO::getReference, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "reference"), true)
				.setDescriptionGenerator(ProductDetailReportDTO::getReference, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setId(REFERENCE);

		// ACTUALIZACIÃ“N
		this.datGrid_NotifDetails.addCustomColumn(i -> (i.getUpdate() != null) ? NumericManager.getFormatter(0).format(i.getUpdate()) : "", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "update"), true)
				.setDescriptionGenerator(i -> (i.getUpdate() != null) ? NumericManager.getFormatter(0).format(i.getUpdate()) : "", ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(140)
				.setId(UPDATE);

		// VENTA
		this.datGrid_NotifDetails.addCustomColumn(i -> (i.getSale() != null) ? NumericManager.getFormatter(0).format(i.getSale()) : "", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "sale"), true)
				.setDescriptionGenerator(i -> (i.getSale() != null) ? NumericManager.getFormatter(0).format(i.getSale()) : "", ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(140)
				.setId(SALE);

		// NOTIFICACIONES
		this.datGrid_NotifDetails.addCustomColumn(i -> (i.getNotification() != null) ? NumericManager.getFormatter(0).format(i.getNotification()) : "", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "notifications"), true)
				.setDescriptionGenerator(i -> (i.getNotification() != null) ? NumericManager.getFormatter(0).format(i.getNotification()) : "", ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(140)
				.setId(NOTIFICATION);

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

	private void updateSortOrderMatchableDocumentCriteria(List<GridSortOrder<ProductDetailReportDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<ProductDetailReportDTO> sorOrder : sortOrderList)
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

			GridSortOrder<ProductDetailReportDTO> sortOrder = new GridSortOrder<>(datGrid_NotifDetails.getColumn(DEFAULT_SORT_FIELD), SortDirection.DESCENDING);
			sortOrderList = new ArrayList<GridSortOrder<ProductDetailReportDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_NotifDetails.setSortOrder(sortOrderList);
		}
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<ProductDetailReportDTO>> e)
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

	private void refreshReport_comboHandler(ValueChangeEvent<String> e)
	{
		this.startWaiting();

		this.resetPageInfo = true;
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
					ProductDetailReportInitParamDTO initParams = this.getProductDetailReportInitParam();
					initParams.setPaginated(false);
					// TODO
					fileResult = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).getExcelProductDetailReportWS(initParams, selectedFormat.getValue(), bbrUIParent.getUser().getId(), bbrUIParent.getLocale());
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
		ProductDetailReport senderReport = (ProductDetailReport) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

	// protected void downloadLinkFile(Object fileInfo)
	// {
	// downloadLinkFile(fileInfo, false);
	// }

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
