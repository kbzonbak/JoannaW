package bbr.b2b.portal.modules.fep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.event.SortEvent.SortListener;
import com.vaadin.shared.data.sort.SortDirection;
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

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeByClientInitParamDTO;
import bbr.b2b.fep.common.data.classes.AttributeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeDTO;
import bbr.b2b.fep.common.data.classes.StandardArticleTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.StandardArticleTypeResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByArticleTypeDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.fep.AttributeStandardizerFilter;
import bbr.b2b.portal.components.modules.fep.StandardizedAttributeEditor;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.basics.BbrUser;
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
import cl.bbr.core.components.paging.BbrPagingManager;

public class AttributeStandardizer extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long								serialVersionUID							= 1282944624651942272L;
	private static final String								ATTRIBUTE_NAME								= "attributename";
	private static final String								ATTRIBUTE_VENDOR_NAME						= "attributevendorname";
	private static final String								ATTRIBUTE_TYPE_NAME							= "attributetypename";

	private final int										DEFAULT_PAGE_NUMBER							= 1;
	private final int										MAX_ROWS_NUMBER								= 200;
	private final String									DEFAULT_SORT_FIELD							= ATTRIBUTE_NAME;

	private VerticalLayout									mainLayout									= null;
	private BbrAdvancedGrid<AttributeDTO>			    	datGrid_Retail_Attributes					= null;
	private BbrAdvancedGrid<AttributeDTO>					datGrid_Bbr_Atttributes						= null;

	private Button											btn_DownloadProductsResume					= null;
	private Button											btn_Refresh									= null;

	private BbrPagingManager								pagingManager								= null;

	private ArticleTypeByClientInitParamDTO					initParamFilter								= null;
	private Boolean											trackReport									= true;
	private Boolean											resetPageInfo								= true;
	private OrderCriteriaDTO[]								orderCriteria								= null;
	private BbrWork<AttributeArrayResultDTO>				reportWork									= null;
	private BbrWork<FileDownloadInfoResultDTO>				downloadsWork								= null;

	private StandardArticleTypeArrayResultDTO 				standardizedAttributesValues 				= null;

	private Map<Long, List<StandardArticleTypeResultDTO>>	standardizerAttMap 							= null; 

	Label 													lbl_registry 								= null;

	private Button											btn_EditAttribute							= null;

	private ArrayList<AttributeDTO>   				    	standardizerAttList 						= null;

	AttributeDTO 											selectedAttribute							= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public AttributeStandardizer(BbrUI bbrUIParent)
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
			AttributeStandardizerFilter filterLayout = new AttributeStandardizerFilter(this, null);
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

			this.btn_EditAttribute = new Button("", BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_EditAlternative.png"));
			this.btn_EditAttribute.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "edit_product"));
			this.btn_EditAttribute.addClickListener((ClickListener & Serializable) e -> this.btn_EditAttribute_clickHandler(e));
			this.btn_EditAttribute.addStyleName("toolbar-button");
			this.btn_EditAttribute.setEnabled(false);

			leftButtonsBar.addComponents(this.btn_EditAttribute);

			this.btn_DownloadProductsResume = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
			this.btn_DownloadProductsResume.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_products_resume"));
			this.btn_DownloadProductsResume.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadProductsResume.addStyleName("toolbar-button");

			this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
			this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
			this.btn_Refresh.addStyleName("toolbar-button");

			// Barra superior dercha
			HorizontalLayout rightButtonsBar = new HorizontalLayout();
			rightButtonsBar.addComponents(this.btn_DownloadProductsResume, this.btn_Refresh);
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

			Label lbl_AttRetailName = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_att_retail_name"));
			lbl_AttRetailName.setWidth("120px");
			lbl_AttRetailName.addStyleName("bold_style");

			Label lbl_AttB2BLinkName = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_att_b2blink_name"));
			lbl_AttB2BLinkName.setWidth("120px");
			lbl_AttB2BLinkName.addStyleName("bold_style");

			// Reporte atributos Retail: Grilla
			this.datGrid_Retail_Attributes = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.datGrid_Retail_Attributes.setSortable(false);

			this.initializeDataGridColumns();

			this.datGrid_Retail_Attributes.addStyleName("report-grid");
			this.datGrid_Retail_Attributes.setSizeFull();
			this.datGrid_Retail_Attributes.setPagingManager(pagingManager, this.DEFAULT_SORT_FIELD);
			this.datGrid_Retail_Attributes.setSelectionMode(SelectionMode.SINGLE);
			//this.datGrid_Retail_Attributes.addSelectionListener((SelectionListener<AttributeDTO> & Serializable) e -> selection_gridHandler(e));
			this.datGrid_Retail_Attributes.addSortListener((SortListener<GridSortOrder<AttributeDTO>> & Serializable) e -> dataGrid_sortHandler(e));
			this.datGrid_Retail_Attributes.addItemClickListener((ItemClickListener<AttributeDTO> & Serializable) e -> dgdItem_clickHandler(e));

			VerticalLayout gridLayout = new VerticalLayout();
			gridLayout.setSizeFull();
			gridLayout.setMargin(false);
			gridLayout.addComponents(lbl_AttRetailName, this.datGrid_Retail_Attributes, pagingManager);
			gridLayout.setExpandRatio(this.datGrid_Retail_Attributes, 1F);

			gridLayout.setComponentAlignment(lbl_AttRetailName, Alignment.MIDDLE_LEFT);

			// Reporte atributos BBR: Grilla
			this.datGrid_Bbr_Atttributes = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.datGrid_Bbr_Atttributes.setSortable(false);

			this.initializeBbrDataGridColumns();

			this.datGrid_Bbr_Atttributes.addStyleName("report-grid");
			this.datGrid_Bbr_Atttributes.setSizeFull();
			this.datGrid_Bbr_Atttributes.setSelectionMode(SelectionMode.SINGLE);

			this.lbl_registry = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_footer_total"));
			this.lbl_registry.setWidth("120px");

			VerticalLayout gridBbrLayout = new VerticalLayout();
			gridBbrLayout.setSizeFull();
			gridBbrLayout.setMargin(false);
			gridBbrLayout.addComponents(lbl_AttB2BLinkName, this.datGrid_Bbr_Atttributes, this.lbl_registry);
			gridBbrLayout.setExpandRatio(this.datGrid_Bbr_Atttributes, 1F);

			gridBbrLayout.setComponentAlignment(lbl_AttB2BLinkName, Alignment.MIDDLE_LEFT);
			gridBbrLayout.setComponentAlignment(this.lbl_registry, Alignment.MIDDLE_LEFT);

			HorizontalLayout finalLayout = new HorizontalLayout();
			finalLayout.setSizeFull();
			finalLayout.setMargin(false);
			finalLayout.addComponents(gridLayout, gridBbrLayout);
			finalLayout.setExpandRatio(gridLayout, 1F);
			finalLayout.setExpandRatio(gridBbrLayout, 1F);

			this.mainLayout = new VerticalLayout();
			mainLayout.addStyleName("report-layout");
			mainLayout.setSizeFull();
			mainLayout.setMargin(false);
			mainLayout.addComponents(toolBar, finalLayout);
			//mainLayout.setExpandRatio(toolBar, 0.1F);
			mainLayout.setExpandRatio(finalLayout, 1F);

			this.addComponents(mainLayout);

			this.updateButtons(false);

			this.updateSortOrderCriteria(null);

			reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			reportWork.addFunction(new Function<Object, AttributeArrayResultDTO>()
			{
				@Override
				public AttributeArrayResultDTO apply(Object t)
				{
					return executeService(AttributeStandardizer.this.getBbrUIParent());
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
			this.initParamFilter = (ArticleTypeByClientInitParamDTO) event.getResultObject();

			this.trackReport = true;
			this.resetPageInfo = true;

			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		AttributeStandardizer bbrSender = (AttributeStandardizer) sender;
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
					I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
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
				return executeDownloadService(AttributeStandardizer.this.getBbrUIParent(), selectedFormat);
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
	}

	private void dgdItem_clickHandler(ItemClick<AttributeDTO> e)
	{
		if ( e.getItem() != null)
		{
			this.btn_EditAttribute.setEnabled(true);
			
			this.doUpdateStandardizedAttributesByArticleType();
			
			datGrid_Bbr_Atttributes.setDataProvider(new ListDataProvider<>(new ArrayList<>()));

			this.lbl_registry.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_footer_total") + ": "  + 0);

			if (this.standardizedAttributesValues != null && this.standardizedAttributesValues.getValues() != null 
					&&  this.standardizedAttributesValues.getValues().length > 0){

				if (this.standardizerAttMap.containsKey(e.getItem().getId()) ){

					this.updateBbrDataSource(e.getItem().getId());
				}

			}
			
		}
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<AttributeDTO>> e)
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
		//this.executeBbrWork(reportWork);

		this.startWaiting();
		this.executeBbrWork(reportWork);

	}

	private void btn_EditAttribute_clickHandler(ClickEvent event)
	{
		if ((this.datGrid_Retail_Attributes.getSelectedItems() != null) && (this.datGrid_Retail_Attributes.getSelectedItems().size() > 0))
		{
			this.selectedAttribute = this.datGrid_Retail_Attributes.getSelectedItems().iterator().next();

			StandardizedAttributeEditor editProductWin = new StandardizedAttributeEditor(this.getBbrUIParent(),
					selectedAttribute, 
					this.standardizerAttList);

			editProductWin.addBbrEventListener((BbrEventListener & Serializable) e -> this.attributes_updateHandler(e)); 
			editProductWin.initializeView();
			editProductWin.open(true, this);
		}

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
		this.datGrid_Retail_Attributes
		.addCustomColumn(AttributeDTO::getInternalname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_name"), true)
		.setId(ATTRIBUTE_NAME);

		this.datGrid_Retail_Attributes
		.addCustomColumn(AttributeDTO::getVendorname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_vendorname"), true)
		.setId(ATTRIBUTE_VENDOR_NAME);

		this.datGrid_Retail_Attributes
		.addCustomColumn(AttributeDTO::getDatatypename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_type_name"), true)
		.setId(ATTRIBUTE_TYPE_NAME);
	}

	private void initializeBbrDataGridColumns()
	{
		this.datGrid_Bbr_Atttributes
		.addCustomColumn(AttributeDTO::getInternalname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_name"), true)
		.setId(ATTRIBUTE_NAME);

		this.datGrid_Bbr_Atttributes
		.addCustomColumn(AttributeDTO::getVendorname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_vendorname"), true)
		.setId(ATTRIBUTE_VENDOR_NAME);

		this.datGrid_Bbr_Atttributes
		.addCustomColumn(AttributeDTO::getDatatypename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_type_name"), true)
		.setId(ATTRIBUTE_TYPE_NAME);
	}

	private void updateButtons(Boolean isSelectionEvent)
	{
		this.btn_DownloadProductsResume.setEnabled(initParamFilter != null);
		this.btn_Refresh.setEnabled(initParamFilter != null);

	}
	
	private void updateBbrDataSource(Long attributeid)
	{
		ArrayList<AttributeDTO> data = null;

		data = new ArrayList<AttributeDTO>(standardizerAttMap.get(attributeid)) ;

		ListDataProvider<AttributeDTO> dataprovider = new ListDataProvider<>(data != null ? data : new ArrayList<>());
		datGrid_Bbr_Atttributes.setDataProvider(dataprovider);

		this.standardizerAttList = new ArrayList<>(data != null ? data : new ArrayList<>());

		this.lbl_registry.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_footer_total") + ": "  + data.size());
	}

	private AttributeArrayResultDTO executeService(BbrUI bbrUI)
	{
		AttributeArrayResultDTO result = null;

		if (this.initParamFilter != null)
		{
			Integer requestedPage = (!this.resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;
			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();
				this.initParamFilter.setPageNumber(requestedPage);
				this.initParamFilter.setNeedPageInfo(this.resetPageInfo);
				result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUI).getAttributesByFilter(this.initParamFilter);

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
				if (this.btn_DownloadTriggerButton == this.btn_DownloadProductsResume)
				{
					fileResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUIParent)
							.downloadStandardizedAttributesList(this.initParamFilter);
				}
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

		AttributeStandardizer senderReport = (AttributeStandardizer) sender;

		if (result != null)
		{
			AttributeArrayResultDTO reportResult = (AttributeArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				AttributeDTO[] data = (reportResult.getValues() != null) ? reportResult.getValues() : new AttributeDTO[0];

				ListDataProvider<AttributeDTO> dataprovider = new ListDataProvider<>(Arrays.asList(data));

				senderReport.datGrid_Retail_Attributes.setDataProvider(dataprovider, senderReport.resetPageInfo);
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

			ListDataProvider<AttributeDTO> dataprovider = new ListDataProvider<>(new ArrayList<>());
			datGrid_Bbr_Atttributes.setDataProvider(dataprovider);
		}

		if (errordescription.length() > 0 && senderReport.trackReport == true)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}

		senderReport.stopWaiting();
	}


	private StandardArticleTypeArrayResultDTO getStandardizedAttributesByArticleType()
	{
		StandardArticleTypeArrayResultDTO result = null;
		try
		{
			if (this.initParamFilter != null){

				result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getStandardizedAttributes(this.initParamFilter);
			}

		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
		}
		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}

		return result;
	}


	private void updateSortOrderCriteria(List<GridSortOrder<AttributeDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<AttributeDTO> sorOrder : sortOrderList)
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

			GridSortOrder<AttributeDTO> sortOrder = new GridSortOrder<>(datGrid_Retail_Attributes.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<AttributeDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_Retail_Attributes.setSortOrder(sortOrderList);
		}
	}


	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		AttributeStandardizer senderReport = (AttributeStandardizer) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}
	
	class TemplateDetailData
	{
		private CPItemsByArticleTypeDTO template;
		private Integer state;

		public TemplateDetailData(CPItemsByArticleTypeDTO selectedTemplate, Integer selectedState)
		{
			this.template = selectedTemplate;
			this.state = selectedState;
		}

		public CPItemsByArticleTypeDTO getTemplate()
		{
			return template;
		}

		public Integer getState()
		{
			return state;
		}
	}

	@SuppressWarnings("unchecked")
	private void attributes_updateHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent.getEventType().equals(BbrEvent.ITEMS_UPDATED) && bbrEvent.getResultObject() != null)
		{
			ArrayList<AttributeDTO> attibutes = (ArrayList<AttributeDTO>) bbrEvent.getResultObject();

			if (attibutes != null)
			{
				this.standardizerAttList = attibutes;
				ListDataProvider<AttributeDTO> dataprovider = new ListDataProvider<>(this.standardizerAttList);

				this.datGrid_Bbr_Atttributes.setDataProvider(dataprovider);
				this.datGrid_Bbr_Atttributes.getDataProvider().refreshAll();

			}

		}
	}

	private void doUpdateStandardizedAttributesByArticleType(){

		this.standardizedAttributesValues = this.getStandardizedAttributesByArticleType();

		if (standardizedAttributesValues!= null && standardizedAttributesValues.getValues() != null &&  standardizedAttributesValues.getValues().length > 0){

			this.standardizerAttMap = standardizedAttributesValues.getStandardizedAttributesMap();
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}