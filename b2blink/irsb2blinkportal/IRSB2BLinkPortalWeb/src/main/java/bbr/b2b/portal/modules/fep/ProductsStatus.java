package bbr.b2b.portal.modules.fep;

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
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.fep.cp.data.classes.CPItemArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByStatusInitParamDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.wrappers.fep.ProductsStatusFilterSearch;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.fep.ProductsStatusFilter;
import bbr.b2b.portal.components.modules.fep.DownloadProductsByTemplate;
import bbr.b2b.portal.components.modules.fep.ViewProductDatasheet;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class ProductsStatus extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long							serialVersionUID						= 1282944624651942272L;

	private static final String						REQUEST_NUMBER							= "id";
	private static final String						PROVIDERCODE							= "providercode";
	private static final String						PROVIDERSOCIALREASON					= "providersocialreason";
	private static final String						TEMPLATE_NAME							= "articletypename";
	private static final String						PLU										= "sku";
	private static final String						DESCRIPTION								= "description";
	private static final String						CREATIONDATE							= "creationdate";
	private static final String						LASTMODIFIED							= "lastmodified";
	private static final String						CURRENTSTATENAME						= "currentstatename";
	private static final String						CREATED_BY								= "responsablename";
	private final int										DEFAULT_PAGE_NUMBER					= 1;
	private final int										MAX_ROWS_NUMBER						= 200;
	private final String									DEFAULT_SORT_FIELD					= PROVIDERCODE;

	private VerticalLayout								mainLayout								= null;
	private BbrAdvancedGrid<CPItemDTO>				datGrid_RequestItems					= null;
	private ArrayList<CPItemDTO>						mpItems									= new ArrayList<CPItemDTO>();
	private Button											btn_DownloadProductsResume			= null;
	private Button											btn_DownloadProductsByTemplate	= null;
	private BbrPopupButton								btn_DownloadProductsGrp				= null;
	private Button											btn_Refresh								= null;
	private Button											btn_ProductInformation				= null;
	private BbrPagingManager							pagingManager							= null;
	private CPItemsByStatusInitParamDTO				initParam								= null;
	private Boolean										trackReport								= true;
	private Boolean										resetPageInfo							= true;
	private CompanyDataDTO								selectedProvider						= null;
	private OrderCriteriaDTO[]							orderCriteria							= null;
	private BbrWork<CPItemArrayResultDTO>			reportWork								= null;
	private BbrWork<FileDownloadInfoResultDTO>	downloadsWork							= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductsStatus(BbrUI bbrUIParent)
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

			ProductsStatusFilter filterLayout = new ProductsStatusFilter(this);
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

			this.btn_ProductInformation = new Button("", EnumToolbarButton.SEARCH_PRIMARY.getResource());
			this.btn_ProductInformation.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "product_information"));
			this.btn_ProductInformation.addClickListener((ClickListener & Serializable) e -> this.btn_ProductInformation_clickHandler(e));
			this.btn_ProductInformation.addStyleName("toolbar-button");

			leftButtonsBar.addComponents(this.btn_ProductInformation);
			
			VerticalLayout cmp_DownloadProductsButtons = new VerticalLayout();
			cmp_DownloadProductsButtons.setMargin(new MarginInfo(false, true));
			cmp_DownloadProductsButtons.setSpacing(false);
			
			this.btn_DownloadProductsResume = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_products_resume"));
			this.btn_DownloadProductsResume.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_products_resume"));
			this.btn_DownloadProductsResume.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadProductsResume.addStyleName("action-button");
			cmp_DownloadProductsButtons.addComponent(this.btn_DownloadProductsResume);
			
			this.btn_DownloadProductsByTemplate = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_standarized_products"));
			this.btn_DownloadProductsByTemplate.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_standarized_products"));
			this.btn_DownloadProductsByTemplate.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadProductsByTemplate.addStyleName("action-button");
			cmp_DownloadProductsButtons.addComponent(this.btn_DownloadProductsByTemplate);

			this.btn_DownloadProductsGrp = new BbrPopupButton(""); 
			this.btn_DownloadProductsGrp.setIcon(BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DownloadPrimary.png"));
			this.btn_DownloadProductsGrp.addStyleName("toolbar-button");
			this.btn_DownloadProductsGrp.setContentLayout(cmp_DownloadProductsButtons);
			this.btn_DownloadProductsGrp.setClosePopupOnOutsideClick(true);

			this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
			this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
			this.btn_Refresh.addStyleName("toolbar-button");

			// Barra superior dercha
			HorizontalLayout rightButtonsBar = new HorizontalLayout();
			rightButtonsBar.addComponents(this.btn_DownloadProductsGrp, this.btn_Refresh);
			rightButtonsBar.setSpacing(true);
			rightButtonsBar.setMargin(false);
			rightButtonsBar.setHeight("30px");
			rightButtonsBar.addStyleName("toolbar-layout");

			// Barra superior
			HorizontalLayout toolBar = new HorizontalLayout();
			toolBar.setWidth("100%");
			toolBar.addComponents(leftButtonsBar, rightButtonsBar);
			toolBar.addStyleName("filter-toolbar");
			toolBar.setExpandRatio(leftButtonsBar, 1F);
			toolBar.setExpandRatio(rightButtonsBar, 1F);

			toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
			toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

			// Reporte: Grilla
			this.datGrid_RequestItems = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.datGrid_RequestItems.setSortable(false);

			this.initializeDataGridColumns();

			this.datGrid_RequestItems.addStyleName("report-grid");
			this.datGrid_RequestItems.setSizeFull();
			this.datGrid_RequestItems.setPagingManager(pagingManager, REQUEST_NUMBER);
			this.datGrid_RequestItems.setSelectionMode(SelectionMode.MULTI);
			this.datGrid_RequestItems.addSelectionListener((SelectionListener<CPItemDTO> & Serializable) e -> selection_gridHandler(e));
			this.datGrid_RequestItems.addSortListener((SortListener<GridSortOrder<CPItemDTO>> & Serializable) e -> dataGrid_sortHandler(e));

			VerticalLayout gridLayout = new VerticalLayout();
			gridLayout.setSizeFull();
			gridLayout.setMargin(false);
			gridLayout.addComponents(this.datGrid_RequestItems, pagingManager);
			gridLayout.setExpandRatio(this.datGrid_RequestItems, 1F);

			HorizontalLayout dataLayout = new HorizontalLayout();
			dataLayout.setSizeFull();
			dataLayout.setMargin(false);
			dataLayout.addComponents(gridLayout);

			this.mainLayout = new VerticalLayout();
			mainLayout.addStyleName("report-layout");
			mainLayout.setSizeFull();
			mainLayout.setMargin(false);
			mainLayout.addComponents(toolBar, dataLayout);
			mainLayout.setExpandRatio(dataLayout, 2F);

			this.addComponents(mainLayout);

			this.updateButtons(false);

			this.updateSortOrderCriteria(null);

			reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			reportWork.addFunction(new Function<Object, CPItemArrayResultDTO>()
			{
				@Override
				public CPItemArrayResultDTO apply(Object t)
				{
					return executeService(ProductsStatus.this.getBbrUIParent());
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
			this.resetPageInfo = true;
			this.trackReport = true;

			ProductsStatusFilterSearch search = (ProductsStatusFilterSearch) event.getResultObject();

			if (search != null)
			{
				this.initParam = search.getInitParam();
				this.initParam.setOrderBy(this.orderCriteria);
				this.initParam.setRows(MAX_ROWS_NUMBER);

				this.selectedProvider = search.getSelectedCompany();

				this.startWaiting();
				this.executeBbrWork(reportWork);
			}
		}
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ProductsStatus bbrSender = (ProductsStatus) sender;
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateReport(result, sender);
			}
			else if (triggerObject == this.btn_DownloadTriggerButton)
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
				return executeDownloadService(ProductsStatus.this.getBbrUIParent(), selectedFormat);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();
		
		if (this.btn_DownloadTriggerButton == this.btn_DownloadProductsResume)
		{
			this.selectDownloadFileType(DownloadFormats.XLS, DownloadFormats.XLS, DownloadFormats.CSV);
		}
		else if(this.btn_DownloadTriggerButton == this.btn_DownloadProductsByTemplate)
		{
			if((this.datGrid_RequestItems.getAllSelectedsItems() != null) && (this.datGrid_RequestItems.getAllSelectedsItems().size() > 0))
			{
				CPItemDTO[] selectedItems = this.datGrid_RequestItems.getAllSelectedsItems().toArray(new CPItemDTO[this.datGrid_RequestItems.getAllSelectedsItems().size()]);
				
				DownloadProductsByTemplate downloadWin = new DownloadProductsByTemplate(this.getBbrUIParent(), selectedItems);
				downloadWin.initializeView();
				downloadWin.open(true);
			}
		}
	}


	private void btn_ProductInformation_clickHandler(ClickEvent event)
	{
		if (this.datGrid_RequestItems.getSelectedItems() != null && !this.datGrid_RequestItems.getSelectedItems().isEmpty())
		{
			CPItemDTO selectdItem = this.datGrid_RequestItems.getSelectedItems().iterator().next();
			ViewProductDatasheet winDataSheet = new ViewProductDatasheet(getBbrUIParent(), selectdItem, this.selectedProvider, false);
			winDataSheet.initializeView();
			winDataSheet.open(true);
		}
	}


	private void selection_gridHandler(SelectionEvent<CPItemDTO> e)
	{
		this.updateButtons(true);
	}


	private void dataGrid_sortHandler(SortEvent<GridSortOrder<CPItemDTO>> e)
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
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void initializeDataGridColumns()
	{
		this.datGrid_RequestItems.addCustomColumn(CPItemDTO::getId, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_request_number"), true)
		.setWidth(100)
		.setId(REQUEST_NUMBER);

		this.datGrid_RequestItems.addCustomColumn(CPItemDTO::getProvidercode, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_provider_code"), true)
		.setDescriptionGenerator(CPItemDTO::getProvidercode)
		.setWidth(150)
		.setId(PROVIDERCODE);

		this.datGrid_RequestItems.addCustomColumn(CPItemDTO::getProvidersocialreason, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_provider_name"), true)
		.setDescriptionGenerator(CPItemDTO::getProvidersocialreason)
		.setWidth(200)
		.setId(PROVIDERSOCIALREASON);

		this.datGrid_RequestItems.addCustomColumn(CPItemDTO::getResponsablename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_created_by"), true)
		.setDescriptionGenerator(CPItemDTO::getResponsablename)
		.setWidth(180)
		.setId(CREATED_BY);

		this.datGrid_RequestItems.addCustomColumn(CPItemDTO::getArticletypename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_article_type_name"), true)
		.setWidth(100)
		.setId(TEMPLATE_NAME);

		this.datGrid_RequestItems.addCustomColumn(CPItemDTO::getSku, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_sku"), true)
		.setWidth(130)
		.setId(PLU);

		this.datGrid_RequestItems.addCustomColumn(CPItemDTO::getDescription, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_description_product"), true)
		.setWidthUndefined()
		.setId(DESCRIPTION);

		this.datGrid_RequestItems.addCustomColumn(CPItemDTO::getCreationdate, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_creation_date"), true)
		.setStyleGenerator(item -> "v-align-center")
		.setRenderer(new LocalDateTimeRenderer("dd-MM-yyyy HH:mm"))
		.setWidth(150)
		.setId(CREATIONDATE);

		this.datGrid_RequestItems.addCustomColumn(CPItemDTO::getLastmodified, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_last_modified"), true)
		.setStyleGenerator(item -> "v-align-center")
		.setRenderer(new LocalDateTimeRenderer("dd-MM-yyyy HH:mm"))
		.setWidth(150)
		.setId(LASTMODIFIED);

		this.datGrid_RequestItems.addCustomColumn(CPItemDTO::getCurrentstatename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_state"), true)
		.setDescriptionGenerator(CPItemDTO::getCurrentstatename)
		.setWidth(150)
		.setId(CURRENTSTATENAME);
	}


	private void updateButtons(Boolean isSelectionEvent)
	{
		this.btn_DownloadProductsGrp.setEnabled(initParam != null);
		this.btn_DownloadProductsByTemplate.setEnabled(this.datGrid_RequestItems.getSelectedItems() != null && this.datGrid_RequestItems.getSelectedItems().size() > 0);
		this.btn_Refresh.setEnabled(initParam != null);
	}


	private CPItemArrayResultDTO executeService(BbrUI bbrUI)
	{
		CPItemArrayResultDTO result = null;

		if (this.initParam != null)
		{
			this.initParam.setUsertypeid(-1L);
			Integer requestedPage = (!this.resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;

			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();
				this.initParam.setPageNumber(requestedPage);
				this.initParam.setNeedPageInfo(this.resetPageInfo);
				result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUI).getItemsByStatusData(this.initParam, bbrUI.getUser().getId());
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut();
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


	protected FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				fileResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).downloadItemsByStatusData(initParam,
				bbrUIParent.getUser().getId(),
				selectedFormat.toString(),
				true);
			}
			catch (BbrUserException ex)
			{
				AppUtils.getInstance().doLogOut(ex.getMessage(), this.getBbrUIParent());
			}
			catch (BbrSystemException ex)
			{
				ex.printStackTrace();
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

		ProductsStatus senderReport = (ProductsStatus) sender;

		if (result != null)
		{
			CPItemArrayResultDTO reportResult = (CPItemArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				this.mpItems = reportResult.getValues() != null ? new ArrayList<CPItemDTO>(Arrays.asList(reportResult.getValues())) : null;
				// CPItemDTO[] data = (reportResult.getValues() != null) ?
				// reportResult.getValues() : new CPItemDTO[0];

				ListDataProvider<CPItemDTO> dataprovider = new ListDataProvider<>(this.mpItems);

				senderReport.datGrid_RequestItems.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.updateButtons(false);

				if (reportResult.getPageinfo() != null && senderReport.resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageinfo();
					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);
				}

				// End Tracking
				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}

				if (reportResult.getValues() == null || reportResult.getValues().length == 0)
				{
					senderReport.askToOpenFilter(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
					I18NManager.getI18NString(senderReport.getBbrUIParent(),
					BbrUtilsResources.RES_FILTERS,
					"empty_filter_question"));
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


	private void updateSortOrderCriteria(List<GridSortOrder<CPItemDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<CPItemDTO> sorOrder : sortOrderList)
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

			GridSortOrder<CPItemDTO> sortOrder = new GridSortOrder<>(datGrid_RequestItems.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<CPItemDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_RequestItems.setSortOrder(sortOrderList);
		}
	}


	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ProductsStatus senderReport = (ProductsStatus) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
}
