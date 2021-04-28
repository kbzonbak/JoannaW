package bbr.b2b.portal.modules.fep;

import java.io.Serializable;
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
import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.cp.data.classes.CPItemArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByStatusInitParamDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.wrappers.fep.ProductsReleasesFilterSearch;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.fep.ProductsReleasesFilter;
import bbr.b2b.portal.components.modules.fep.CreateProduct;
import bbr.b2b.portal.components.modules.fep.ProductsReleaseDetails;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
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
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.grid.renderer.ShortDateRenderer;
import cl.bbr.core.components.paging.BbrPagingManager;

public class ProductsReleases extends BbrModule implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long serialVersionUID = -8676078484311460085L;
	
	private static final String						REQUEST_NUMBER			= "id";
	private static final String						TEMPLATE					= "articletypename";
	private static final String						REQUEST_DATE			= "creationdate";
	private static final String						CREATED_BY				= "responsablename";
	private static final String						PROVIDER_CODE			= "providercode";
	private static final String						PROVIDER_NAME			= "providersocialreason";
	private static final String						PRODUCT_DESCRIPTION	= "description";
	private static final String						LAST_UPDATE				= "lastmodified";
	private static final String						CURRENT_STATE			= "currentstatename";

	private VerticalLayout								mainLayout				= null;

	private BbrAdvancedGrid<CPItemDTO>				datGrid_ProductItems	= null;
	private BbrPagingManager							pagingManager			= null;

	private CPItemsByStatusInitParamDTO				initParam				= null;
	private ProductsReleasesFilterSearch			filterSearch			= null;

	private final int										DEFAULT_PAGE_NUMBER	= 1;
	private final int										MAX_ROWS_NUMBER		= 200;

	private final String									DEFAULT_SORT_FIELD	= REQUEST_DATE;

	private Button											btn_EditProduct		= null;
	private Button											btn_AddProduct			= null;
	private Button											btn_DownloadReport	= null;
	private Button											btn_Refresh				= null;

	private Boolean										trackReport				= true;
	private Boolean										resetPageInfo			= true;

	private OrderCriteriaDTO[]							orderCriteria			= null;

	private BbrWork<CPItemArrayResultDTO>			reportWork				= null;
	private BbrWork<FileDownloadInfoResultDTO>	downloadsWork			= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductsReleases(BbrUI bbrUIParent)
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
			ProductsReleasesFilter filterLayout = new ProductsReleasesFilter(this);
			filterLayout.initializeView();
			this.setBbrFilterContainer(filterLayout);

			// Paging Manager
			this.pagingManager = new BbrPagingManager();
			this.pagingManager.setLocale(this.getUser().getLocale());
			this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

			// Botones lado izquierdo

			HorizontalLayout leftButtonsBar = new HorizontalLayout();
			leftButtonsBar.setSpacing(true);
			leftButtonsBar.setMargin(false);
			leftButtonsBar.setHeight("30px");
			leftButtonsBar.addStyleName("toolbar-layout");

			this.btn_EditProduct = new Button("",
			BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_EditAlternative.png"));
			this.btn_EditProduct.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "edit_product"));
			this.btn_EditProduct.addClickListener((ClickListener & Serializable) e -> this.btn_EditProduct_clickHandler(e));
			this.btn_EditProduct.addStyleName("toolbar-button");

			this.btn_AddProduct = new Button("",
			BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_AddAlternative.png"));
			this.btn_AddProduct.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "add_product"));
			this.btn_AddProduct.addClickListener((ClickListener & Serializable) e -> this.btn_AddProduct_clickHandler(e));
			this.btn_AddProduct.addStyleName("toolbar-button");
			this.btn_AddProduct.setEnabled(false);
			this.btn_AddProduct.setVisible(false);
			
			leftButtonsBar.addComponents(this.btn_EditProduct, btn_AddProduct);

			// Botones lado derecho

			this.btn_DownloadReport = new Button("",
			BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DownloadPrimary.png"));
			this.btn_DownloadReport.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_report"));
			this.btn_DownloadReport.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadReport.addStyleName("toolbar-button");

			this.btn_Refresh = new Button("", BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Refresh.png"));
			this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
			this.btn_Refresh.addStyleName("toolbar-button");

			HorizontalLayout rightButtonsBar = new HorizontalLayout();
			rightButtonsBar.addComponents(this.btn_DownloadReport, this.btn_Refresh);
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
			this.datGrid_ProductItems = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.datGrid_ProductItems.setSortable(false);

			this.initializeDataGridColumns();

			this.datGrid_ProductItems.addStyleName("report-grid");
			this.datGrid_ProductItems.setSizeFull();
			this.datGrid_ProductItems.setPagingManager(pagingManager, this.DEFAULT_SORT_FIELD);
			this.datGrid_ProductItems.setSelectionMode(SelectionMode.MULTI);
			this.datGrid_ProductItems.addSelectionListener((SelectionListener<CPItemDTO> & Serializable) e -> selection_gridHandler(e));
			this.datGrid_ProductItems.addSortListener((SortListener<GridSortOrder<CPItemDTO>> & Serializable) e -> dataGrid_sortHandler(e));
			this.datGrid_ProductItems.addItemClickListener((ItemClickListener<CPItemDTO> & Serializable) e -> dgdItem_clickHandler(e));

			this.mainLayout = new VerticalLayout();
			mainLayout.addStyleName("report-layout");
			mainLayout.setSizeFull();
			mainLayout.setMargin(false);
			mainLayout.addComponents(toolBar, this.datGrid_ProductItems, pagingManager);
			mainLayout.setExpandRatio(this.datGrid_ProductItems, 1F);

			this.addComponents(mainLayout);

			this.updateButtons(false);

			reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			reportWork.addFunction(new Function<Object, CPItemArrayResultDTO>()
			{
				@Override
				public CPItemArrayResultDTO apply(Object t)
				{
					return executeService(ProductsReleases.this.getBbrUIParent());
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
			this.btn_AddProduct.setEnabled(true);
			
			this.filterSearch = (ProductsReleasesFilterSearch) event.getResultObject();
			this.initParam 	= (this.filterSearch != null) ? this.filterSearch.getInitParam() : null;
			this.btn_AddProduct.setVisible(this.initParam.getUsertypename().equals(FEPConstants.PRODUCT_MANAGER_ROLE_NAME));
			
			this.trackReport = true;
			this.resetPageInfo = true;

			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ProductsReleases bbrSender = (ProductsReleases) sender;

		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateReport(result, sender);
			}
			else if (triggerObject == this.btn_DownloadReport)
			{
				bbrSender.doDownloadFile(result, sender, triggerObject);
			}
		}
		else
		{
			bbrSender.showErrorMessage(I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
			I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
		}
		bbrSender.stopWaiting();
	}


	@Override
	protected void downloadFormat_selectedHandler(BbrEvent event)
	{
		DownloadFormats selectedFormat = ((event != null) && (event.getResultObject() instanceof DownloadFormats)) ?
													(DownloadFormats) event.getResultObject() : null;

		this.downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_DownloadReport);
		this.downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(ProductsReleases.this.getBbrUIParent(), selectedFormat, ProductsReleases.this.btn_DownloadReport);
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

	private void btn_EditProduct_clickHandler(ClickEvent event)
	{
		CPItemDTO[] selectedItems 		= this.datGrid_ProductItems.getSelectedItems().stream().toArray(CPItemDTO[]::new);

		ProductsReleaseDetails editProductWin = new ProductsReleaseDetails(this.getBbrUIParent(), selectedItems, this.filterSearch.getSelectedCompany(), this.filterSearch.getSelectedRole(), selectedItems[0].getArticletypeid(), false);
		editProductWin.addBbrEventListener((BbrEventListener & Serializable) e -> this.products_updateHandler(e)); 
		editProductWin.initializeView();
		editProductWin.open(true, this);
	}


	private void products_updateHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getEventType().equals(BbrEvent.ITEMS_UPDATED))
		{
			this.startWaiting();

			this.trackReport = false;
			this.resetPageInfo = true;
			this.executeBbrWork(reportWork);
		}
	}


	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		 this.selectDownloadFileType(DownloadFormats.XLS, DownloadFormats.XLS, DownloadFormats.CSV);
	}


	private void btn_AddProduct_clickHandler(ClickEvent event)
	{
		CreateProduct selectionWin = new CreateProduct(this.getBbrUIParent());
		selectionWin.addBbrEventListener((BbrEventListener & Serializable) e -> template_selectedHandler(e));
		selectionWin.initializeView();
		selectionWin.open(true, this);
	}


	private void template_selectedHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getEventType().equals(BbrEvent.ITEM_SELECTED))
		{
			ArticleTypeDataDTO selectedTemplate = (ArticleTypeDataDTO) bbrEvent.getResultObject();
			
			ProductsReleaseDetails createProductWin = new ProductsReleaseDetails(this.getBbrUIParent(), null, this.filterSearch.getSelectedCompany(), this.filterSearch.getSelectedRole(), selectedTemplate.getId(), false);
			createProductWin.addBbrEventListener((BbrEventListener & Serializable) e -> this.products_updateHandler(e)); 
			createProductWin.initializeView();
			createProductWin.open(true, this);
		}
	}


	private void selection_gridHandler(SelectionEvent<CPItemDTO> e)
	{
		this.updateButtons(true);
	}


	private void dgdItem_clickHandler(ItemClick<CPItemDTO> e)
	{
		if (e.getMouseEventDetails().isDoubleClick() && e.getItem() != null)
		{
			this.btn_EditProduct_clickHandler(null);
		}
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
		this.datGrid_ProductItems.addCustomColumn(CPItemDTO::getId, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_request_number"), true)
		.setId(REQUEST_NUMBER);

		this.datGrid_ProductItems.addCustomColumn(CPItemDTO::getArticletypename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_article_type"), true)
		.setId(TEMPLATE);

		this.datGrid_ProductItems.addCustomColumn(CPItemDTO::getCreationdate, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_request_date"), true)
		.setRenderer(new ShortDateRenderer())
		.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
		.setId(REQUEST_DATE);

		this.datGrid_ProductItems.addCustomColumn(CPItemDTO::getResponsablename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_created_by"), true)
		.setId(CREATED_BY);

		this.datGrid_ProductItems.addCustomColumn(CPItemDTO::getProvidercode, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_provider_code"), true)
		.setId(PROVIDER_CODE);

		this.datGrid_ProductItems.addCustomColumn(CPItemDTO::getProvidersocialreason, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_provider_name"), true)
		.setId(PROVIDER_NAME);

		this.datGrid_ProductItems.addCustomColumn(CPItemDTO::getDescription, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_product_description"), true)
		.setId(PRODUCT_DESCRIPTION);

		this.datGrid_ProductItems.addCustomColumn(CPItemDTO::getLastmodified, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_last_modified"), true)
		.setRenderer(new ShortDateRenderer())
		.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
		.setId(LAST_UPDATE);

		this.datGrid_ProductItems.addCustomColumn(CPItemDTO::getCurrentstatename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_current_state"), true)
		.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
		.setId(CURRENT_STATE);
	}


	private void updateButtons(Boolean isSelectionEvent)
	{
		if((initParam != null) && (this.datGrid_ProductItems.getSelectedItems() != null) && (this.datGrid_ProductItems.getSelectedItems().size() > 0))
		{
			if(this.datGrid_ProductItems.getSelectedItems().size() == 1)
			{
				this.btn_EditProduct.setEnabled(true);
			}
			else 
			{
				CPItemDTO[] selectedItems 					= this.datGrid_ProductItems.getSelectedItems().stream().toArray(CPItemDTO[]::new);
				List<CPItemDTO> distinctTemplateItems 	= this.datGrid_ProductItems.getSelectedItems().stream().filter(i -> !i.getArticletypeid().equals(selectedItems[0].getArticletypeid())).collect(Collectors.toList());
				List<CPItemDTO> distinctStateItems 		= this.datGrid_ProductItems.getSelectedItems().stream().filter(c -> !c.getCurrentstateid().equals(selectedItems[0].getCurrentstateid())).collect(Collectors.toList());

				if(distinctTemplateItems.isEmpty() && distinctStateItems.isEmpty())
				{
					this.btn_EditProduct.setEnabled(true);
				}
				else
				{
					this.btn_EditProduct.setEnabled(false);
					
					this.showInfoMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
												I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "same_template_state_required"));
				}
			}
		}
		else
		{
			this.btn_EditProduct.setEnabled(false);
		}
		
		this.btn_DownloadReport.setEnabled(initParam != null);
		this.btn_Refresh.setEnabled(initParam != null);
	}


	private CPItemArrayResultDTO executeService(BbrUI bbrUI)
	{
		CPItemArrayResultDTO result = null;

		if (this.initParam != null)
		{
			Integer requestedPage = (!resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;

			this.initParam.setPageNumber(requestedPage);
			this.initParam.setRows(this.MAX_ROWS_NUMBER);
			this.initParam.setNeedPageInfo(resetPageInfo);
			this.initParam.setOrderBy(orderCriteria);
			this.initParam.setLocale(this.getBbrUIParent().getLocale());
			this.initParam.setUsertypeid(this.initParam.getUsertypeid());

			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();
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


	protected FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton)
	{
		FileDownloadInfoResultDTO fileResult = null;
		
		if (selectedFormat != null)
		{
			try
			{
				fileResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).downloadItemsByStatusData(this.initParam, bbrUIParent.getUser().getId(), selectedFormat.getValue(), false);
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

		ProductsReleases senderReport = (ProductsReleases) sender;

		if (result != null)
		{
			CPItemArrayResultDTO reportResult = (CPItemArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				CPItemDTO[] data = (reportResult.getValues() != null) ? reportResult.getValues() : new CPItemDTO[0];

				ListDataProvider<CPItemDTO> dataprovider = new ListDataProvider<>(Arrays.asList(data));

				senderReport.datGrid_ProductItems.setDataProvider(dataprovider, senderReport.resetPageInfo);
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

			GridSortOrder<CPItemDTO> sortOrder = new GridSortOrder<>(datGrid_ProductItems.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<CPItemDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_ProductItems.setSortOrder(sortOrderList);
		}
	}


	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ProductsReleases senderReport = (ProductsReleases) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
}
