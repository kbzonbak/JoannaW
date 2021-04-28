package bbr.b2b.portal.modules.customer;

import java.io.Serializable;
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
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.customer.report.classes.ProductReportDataDTO;
import bbr.b2b.customer.report.classes.ProductReportInitParamDTO;
import bbr.b2b.customer.report.classes.ProductReportResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.HasI18n;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.customer_service.ProductCardsFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;

public class ApprovedProducts extends BbrModule implements BbrWorkExecutor, HasI18n
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long						serialVersionUID	= -3947159600615633249L;
	private static final String						PRODUCTCLIENTCODE	= "productclientcode";
	private static final String						PRODUCTPROVIDERCODE	= "productprovidercode";
	private static final String						PRODUCTDESCRIPTION	= "productdescription";
	private static final String						BRAND				= "brand";
	private static final String						CATN1				= "catn1";
	private static final String						CATN2				= "catn2";
	private static final String						CATN3				= "catn3";
	// Components
	private VerticalLayout							mainLayout;
	private BbrAdvancedGrid<ProductReportDataDTO>	datGrid_Products	= null;
	private BbrPagingManager						pagingManager		= null;
	private Button									btn_DownloadExcel	= null;
	private Button									btn_Refresh			= null;
	// Variables
	private ProductReportInitParamDTO				initParam			= null;
	private final int								DEFAULT_PAGE_NUMBER	= 1;
	private final int								MAX_ROWS_NUMBER		= 100;
	private final String							DEFAULT_SORT_FIELD	= PRODUCTPROVIDERCODE;
	private OrderCriteriaDTO[]						orderCriteria		= null;
	private Boolean									resetPageInfo		= true;
	private BbrWork<ProductReportResultDTO>			reportWork			= null;
	private BbrWork<FileDownloadInfoResultDTO>		downloadsWork		= null;
	// private Boolean trackReport = true;
	// private BbrWork<CommercialFileDownloadInfoResultDTO>
	// productCardsDownloadsDataSourceWork = null;
	// private DownloadFormats dataSourceSelectedFormat = null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ApprovedProducts(BbrUI bbrUIParent)
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
		ProductCardsFilter filterLayout = new ProductCardsFilter(this);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Reporte: Barra de herramientas superior izq
		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");
		leftButtonsBar.addStyleName("toolbar-layout");

		// this.btn_ProductDetails = new Button("",
		// EnumToolbarButton.SEARCH_PRIMARY.getResource());
		// this.btn_ProductDetails.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
		// "view_product_details"));
		// this.btn_ProductDetails.addClickListener((ClickListener &
		// Serializable) e -> this.btn_ProductDetails_clickHandler(e));
		// this.btn_ProductDetails.addStyleName("toolbar-button");
		//
		// leftButtonsBar.addComponent(this.btn_ProductDetails);

		// DOWNLOAD BUTTONS SECTION
		VerticalLayout cmp_DownloadButtons = new VerticalLayout();
		cmp_DownloadButtons.setMargin(new MarginInfo(false, true));
		cmp_DownloadButtons.setSpacing(false);

		this.btn_DownloadExcel = new Button("");
		this.btn_DownloadExcel.setIcon(EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		this.btn_DownloadExcel.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_report"));
		this.btn_DownloadExcel.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
		this.btn_DownloadExcel.addStyleName("toolbar-button");
		cmp_DownloadButtons.addComponent(this.btn_DownloadExcel);

		// this.btn_DownloadBarCodes = new
		// Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
		// "download_barcodes_file"));
		// this.btn_DownloadBarCodes.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
		// "download_barcodes_file"));
		// this.btn_DownloadBarCodes.addClickListener((ClickListener &
		// Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
		// this.btn_DownloadBarCodes.addStyleName("action-button");
		// cmp_DownloadButtons.addComponent(this.btn_DownloadBarCodes);
		//
		// this.btn_DownloadSourceData = new
		// Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
		// "download_source_file"));
		// this.btn_DownloadSourceData.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
		// "download_source_file"));
		// this.btn_DownloadSourceData.addClickListener((ClickListener &
		// Serializable) e -> btn_DownloadFile_downloadHandler(e));
		// this.btn_DownloadSourceData.addStyleName("action-button");
		// cmp_DownloadButtons.addComponent(this.btn_DownloadSourceData);
		//
		// this.btn_Download = new BbrPopupButton("");
		// this.btn_Download.setIcon(EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		// this.btn_Download.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT,
		// "downloads"));
		// this.btn_Download.addStyleName("toolbar-button");
		// this.btn_Download.setContentLayout(cmp_DownloadButtons);
		// this.btn_Download.setClosePopupOnOutsideClick(true);
		// END DOWNLOAD BUTTONS SECTION

		this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
		this.btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(this.btn_DownloadExcel, this.btn_Refresh);
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

		// Reporte: Grilla

		this.datGrid_Products = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.datGrid_Products.setSortable(false);
		this.datGrid_Products.addStyleName("report-grid");
		this.datGrid_Products.setSizeFull();
		this.datGrid_Products.setPagingManager(pagingManager, this.DEFAULT_SORT_FIELD);
		this.datGrid_Products.setSelectionMode(SelectionMode.SINGLE);

		this.initializeDataGridColumns();

		this.datGrid_Products.addSelectionListener((SelectionListener<ProductReportDataDTO> & Serializable) e -> selection_gridHandler(e));
		this.datGrid_Products.addSortListener((SortListener<GridSortOrder<ProductReportDataDTO>> & Serializable) e -> dataGrid_sortHandler(e));
		this.datGrid_Products.addItemClickListener((ItemClickListener<ProductReportDataDTO> & Serializable) e -> dgdItem_clickHandler(e));

		this.mainLayout = new VerticalLayout();
		this.mainLayout.addStyleName("report-layout");
		this.mainLayout.setSizeFull();
		this.mainLayout.setMargin(false);
		this.mainLayout.addComponents(toolBar, this.datGrid_Products, pagingManager);
		this.mainLayout.setExpandRatio(this.datGrid_Products, 1F);

		this.addComponents(this.mainLayout);

		this.updateButtons(false);
		this.updateSortOrderCriteria(null);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, ProductReportResultDTO>()
		{
			@Override
			public ProductReportResultDTO apply(Object t)
			{
				return executeService(ApprovedProducts.this.getBbrUIParent());
			}
		});
	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.initParam = (ProductReportInitParamDTO) event.getResultObject();

			// this.trackReport = true;
			this.resetPageInfo = true;

			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}

	@Override
	protected void downloadFormat_selectedHandler(BbrEvent event)
	{
		// DownloadFormats selectedFormat = ((event != null) &&
		// (event.getResultObject() instanceof DownloadFormats)) ?
		// (DownloadFormats) event.getResultObject() : null;

		if (btn_DownloadTriggerButton == btn_DownloadExcel)
		{
			this.doExecuteDownloadServiceWork();
		}
	}

	private void doExecuteDownloadServiceWork()
	{
		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(ApprovedProducts.this.getBbrUIParent(), btn_DownloadTriggerButton);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}

	// private void doExecuteDownloadDataSourceServiceWork(DownloadFormats
	// selectedFormat, boolean confirmdownload)
	// {
	// productCardsDownloadsDataSourceWork = new BbrWork<>(this,
	// this.getBbrUIParent(), btn_DownloadTriggerButton);
	// productCardsDownloadsDataSourceWork.addFunction(new Function<Object,
	// CommercialFileDownloadInfoResultDTO>()
	// {
	// @Override
	// public CommercialFileDownloadInfoResultDTO apply(Object t)
	// {
	// return
	// executeDownloadDataSourceService(ApprovedProducts.this.getBbrUIParent(),
	// selectedFormat, btn_DownloadTriggerButton, confirmdownload);
	// }
	// });
	//
	// this.startWaiting();
	// this.executeBbrWork(productCardsDownloadsDataSourceWork);
	// }

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doUpdateReport(result, sender);
			}
			else if (triggerObject instanceof Button)
			{
				this.doDownloadFile(result, sender, triggerObject);
			}
		}
		else
		{
			ApprovedProducts senderReport = (ApprovedProducts) sender;

			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// private void btn_ProductDetails_clickHandler(ClickEvent event)
	// {
	// if ((this.datGrid_Products.getSelectedItems() != null) &&
	// (this.datGrid_Products.getSelectedItems().size() > 0))
	// {
	// ProductReportDataDTO selectedProduct =
	// this.datGrid_Products.getSelectedItems().iterator().next();
	//
	// this.viewProductDetails(selectedProduct);
	//
	// }
	// }

	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
		{
			this.downloadFormat_selectedHandler(null);
		}
	}

	private void pagingChange_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.startWaiting();

			// this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
	}

	private void selection_gridHandler(SelectionEvent<ProductReportDataDTO> e)
	{
		this.updateButtons(true);
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<ProductReportDataDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.startWaiting();

			this.updateSortOrderCriteria(e.getSortOrder());
			// this.trackReport = false;
			this.resetPageInfo = true;
			this.executeBbrWork(reportWork);
		}
	}

	private void dgdItem_clickHandler(ItemClick<ProductReportDataDTO> e)
	{
		if (e.getMouseEventDetails().isDoubleClick() && e.getItem() != null)
		{
			this.viewProductDetails(e.getItem());
		}
	}

	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.startWaiting();

		// this.trackReport = false;
		this.resetPageInfo = false;
		this.executeBbrWork(reportWork);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void initializeDataGridColumns()
	{
		this.datGrid_Products.addCustomColumn(ProductReportDataDTO::getProductclientcode,
				this.getI18n("product_code_client"), true)
				.setWidth(130)
				.setId(PRODUCTCLIENTCODE);

		this.datGrid_Products.addCustomColumn(ProductReportDataDTO::getProductprovidercode,
				this.getI18n("product_code_provider"), true)
				.setWidth(160)
				.setId(PRODUCTPROVIDERCODE);

		this.datGrid_Products.addCustomColumn(ProductReportDataDTO::getProductdescription,
				this.getI18n("col_description_product_provider"), true)
				// .setWidth(130)
				.setId(PRODUCTDESCRIPTION);

		this.datGrid_Products.addCustomColumn(ProductReportDataDTO::getBrand,
				this.getI18n("col_description_mark_provider"), true)
				.setId(BRAND);

		this.datGrid_Products.addCustomColumn(ProductReportDataDTO::getCatn1,
				this.getI18n("cp_descn_1"), true)
				// .setWidth(120)
				.setId(CATN1);

		this.datGrid_Products.addCustomColumn(ProductReportDataDTO::getCatn2,
				this.getI18n("cp_descn_2"), true)
				// .setWidth(120)
				.setId(CATN2);

		this.datGrid_Products.addCustomColumn(ProductReportDataDTO::getCatn3,
				this.getI18n("cp_descn_3"), true)
				// .setWidth(100)
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setId(CATN3);

	}

	private void updateButtons(Boolean isSelectionEvent)
	{
		// this.btn_ProductDetails.setEnabled(this.datGrid_Products.getSelectedItems()
		// != null && !this.datGrid_Products.getSelectedItems().isEmpty());

		if (!isSelectionEvent)
		{
			Boolean isEmptyGrid = this.datGrid_Products.isEmpty();

			this.btn_DownloadExcel.setEnabled(!isEmptyGrid);
			// this.btn_DownloadSourceData.setEnabled(!isEmptyGrid);
			// this.btn_DownloadBarCodes.setEnabled(!isEmptyGrid);
		}
		this.btn_Refresh.setEnabled(initParam != null);
	}

	private void updateSortOrderCriteria(List<GridSortOrder<ProductReportDataDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<ProductReportDataDTO> sorOrder : sortOrderList)
			{
				OrderCriteriaDTO order = new OrderCriteriaDTO();
				order.setPropertyname(sorOrder.getSorted().getId().toUpperCase());
				order.setAscending(sorOrder.getDirection() == SortDirection.ASCENDING);
				resultList.add(order);
			}
			orderCriteria = resultList.toArray(new OrderCriteriaDTO[resultList.size()]);
		}

		else
		{
			OrderCriteriaDTO order = new OrderCriteriaDTO();
			order.setPropertyname(DEFAULT_SORT_FIELD.toUpperCase());
			order.setAscending(true);
			orderCriteria = new OrderCriteriaDTO[] { order };

			GridSortOrder<ProductReportDataDTO> sortOrder = new GridSortOrder<>(datGrid_Products.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<ProductReportDataDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_Products.setSortOrder(sortOrderList);
		}
	}

	private void viewProductDetails(ProductReportDataDTO selectedProduct)
	{
		if (selectedProduct != null)
		{
			// ProductCardsDetails detailsWin = new
			// ProductCardsDetails(this.getBbrUIParent(), selectedProduct,
			// this.initParam.getPvcode());
			// detailsWin.open(true, true, this);
		}
	}

	private ProductReportResultDTO executeService(BbrUI bbrUIParent)
	{
		ProductReportResultDTO result = null;

		if (this.initParam != null)
		{
			Integer requestedPage = (!resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;

			try
			{
				// Start Tracking
				this.initParam.setPageNumber(requestedPage);
				this.initParam.setRows(MAX_ROWS_NUMBER);
				this.initParam.setNeedPageInfo(this.resetPageInfo);
				this.initParam.setOrderBy(this.orderCriteria);
				result = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(bbrUIParent).getProductReport(initParam);
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut(e.getMessage(), bbrUIParent);
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

	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, Button downloadTriggerButton)
	{
		FileDownloadInfoResultDTO fileResult = null;
		try
		{
			ProductReportInitParamDTO initParamDownload = new ProductReportInitParamDTO();
			initParamDownload.setClkey(this.initParam.getClkey());
			initParamDownload.setLocale(this.getBbrUIParent().getLocale());
			initParamDownload.setNeedPageInfo(false);
			initParamDownload.setProductprovidercode(this.initParam.getProductprovidercode());
			initParamDownload.setPvcode(this.initParam.getPvcode());
			initParamDownload.setPageNumber(1);
			initParamDownload.setRows(this.MAX_ROWS_NUMBER);
			initParamDownload.setOrderBy(this.orderCriteria);

			if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
			{
				fileResult = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(bbrUIParent).downloadProductReport(initParamDownload,
						bbrUIParent.getUser().getId());
			}
		}
		catch (BbrUserException ex)
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), bbrUIParent);
		}
		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return fileResult;
	}

	// private CommercialFileDownloadInfoResultDTO
	// executeDownloadDataSourceService(BbrUI bbrUIParent, DownloadFormats
	// selectedFormat, Button downloadTriggerButton,
	// boolean confirmdownload)
	// {
	// this.dataSourceSelectedFormat = selectedFormat;
	// CommercialFileDownloadInfoResultDTO fileResult = null;
	// if (selectedFormat != null)
	// {
	// try
	// {
	// // if (this.btn_DownloadTriggerButton ==
	// // this.btn_DownloadSourceData)
	// // {
	// // fileResult =
	// //
	// EJBFactory.getCommercialEJBFactory().getProductReportManagerLocal(bbrUIParent).downloadProductDataSource(initParam,
	// // bbrUIParent.getUser().getId(), selectedFormat.getValue(),
	// // bbrUIParent.getUser().getLocale(), confirmdownload,
	// // CommercialUtils.getMinRowsExcelDownload());
	// // }
	// }
	// // catch (BbrUserException ex)
	// // {
	// // AppUtils.getInstance().doLogOut(ex.getMessage(), bbrUIParent);
	// // }
	// // catch (BbrValidateCommercialException ex)
	// // {
	// // AppUtils.getInstance().showCommerialIsLoadingMessage(bbrUIParent,
	// // ex.getCode());
	// // }
	// // catch (BbrSystemException e)
	// // {
	// // e.printStackTrace();
	// // }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	//
	// return fileResult;
	// }

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{

		ApprovedProducts senderReport = (ApprovedProducts) sender;

		if (result != null)
		{
			ProductReportResultDTO reportResult = (ProductReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				ListDataProvider<ProductReportDataDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getProductReportDataDTOs()));

				senderReport.datGrid_Products.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.updateButtons(false);

				if (senderReport.resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageInfoDTO();

					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);
				}

				// End Tracking

				if (reportResult.getProductReportDataDTOs().length == 0)
				{
					senderReport.askToOpenFilter(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
							I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "empty_filter_question"));
				}
			}
			else
			{
				ListDataProvider<ProductReportDataDTO> dataprovider = new ListDataProvider<>(new ArrayList<>());
				senderReport.datGrid_Products.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.updateButtons(false);
			}

		}

		senderReport.stopWaiting();
	}

	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ApprovedProducts senderReport = (ApprovedProducts) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	@Override
	public String getI18n(String resource)
	{
		return I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, resource);
	}

}
