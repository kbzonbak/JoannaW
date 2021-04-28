package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.fep.common.data.classes.UserTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.UserTypeDTO;
import bbr.b2b.fep.cp.data.classes.CPItemArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.fep.cp.data.classes.CPItemErrorArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemStateDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByArticleTypeDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByFilterInitParamDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class ViewProductInformation extends BbrWindow implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long							serialVersionUID			= 4946776453603112345L;

	private static final String						ID								= "id";
	private static final String						PLU							= "plu";
	private static final String						DESCRIPTION					= "description";
	private static final String						EANCODE						= "eancode";
	private static final String						TRADE							= "trade";
	private static final String						CREATIONDATE				= "creationdate";
	private static final String						LASTMODIFIED				= "lastmodified";
	private static final String						CURRENTSTATENAME			= "currentstatename";

	private final Long									ADMIN_TYPE_ID				= 3L; 
	
	private Button											btn_ProductDataSheet		= null;
	private Button											btn_UpdateProductState	= null;
	private Button											btn_EditProductInfo		= null;
	private Button											btn_DeleteProduct			= null;
	private Button											btn_Download				= null;
	private Button											btn_DownloadRelatedProduct				= null;
	private Button											btn_Refresh					= null;
	private BbrPopupButton								btn_OtherActions			= null;
	private BbrPopupButton								btn_DownloadProductsGrp			= null;

	private BbrAdvancedGrid<CPItemDTO>				datGrid_CPItems			= null;

	private ArrayList<CPItemDTO>						mpItems						= new ArrayList<CPItemDTO>();
	private BbrWork<CPItemArrayResultDTO>			updateWork					= null;
	private BbrWork<FileDownloadInfoResultDTO>	downloadsWork				= null;
	private BbrWork<BaseResultDTO>					deleteResourceWork		= null;
	private BbrWork<CPItemErrorArrayResultDTO>	stateChangeWork			= null;
	private CPItemsByArticleTypeDTO					selectedArticleType		= null;

	private BbrTextField									txt_Template				= null;
	private BbrTextField									txt_ProviderName			= null;
	private CPItemsByFilterInitParamDTO				initParam					= null;
	private BbrPagingManager							pagingManager				= null;

	private Boolean										resetPageInfo				= true;

	private final int										DEFAULT_PAGE_NUMBER		= 1;
	private final int										MAX_ROWS_NUMBER			= 100;

	private CompanyDataDTO								selectedProvider			= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ViewProductInformation(BbrUI parent, CPItemsByArticleTypeDTO selectedProduct, CompanyDataDTO selectedProvider, CPItemsByFilterInitParamDTO initparam)
	{
		super(parent);
		
		if (initparam != null)
		{
			this.selectedArticleType = selectedProduct;
			this.selectedProvider = selectedProvider;
			this.initParam = (CPItemsByFilterInitParamDTO) BbrUtils.getInstance().copyObject(initparam);
			this.initParam.setArticletypeid(this.selectedArticleType.getArticletypeid());
			this.initParam.setRows(MAX_ROWS_NUMBER);
		}
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
		if (this.selectedArticleType != null)
		{
			// Paging Manager
			this.pagingManager = new BbrPagingManager();
			this.pagingManager.setLocale(this.getUser().getLocale());
			this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);
			
			this.txt_ProviderName = this.getInfoTextField("col_provider");
			this.txt_ProviderName.setValue("(" + this.selectedProvider.getRut() + ") " + this.selectedProvider.getName());

			// Sección Plantilla
			this.txt_Template = this.getInfoTextField("articletype_name");
			this.txt_Template.setValue(this.selectedArticleType.getArticletypedescription());
			FormLayout frmHeader = this.embedIntoForm(this.txt_ProviderName, this.txt_Template);
			
			// Barra de herramientas superior izq
			HorizontalLayout leftButtonsBar = new HorizontalLayout();
			leftButtonsBar.setSpacing(true);
			leftButtonsBar.setMargin(false);
			leftButtonsBar.setHeight("30px");
			leftButtonsBar.addStyleName("toolbar-layout");

			this.btn_ProductDataSheet = new Button("", EnumToolbarButton.ACTION_RIGHT_ALTERNATIVE.getResource());
			this.btn_ProductDataSheet.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "product_data_sheet"));
			this.btn_ProductDataSheet.addClickListener((ClickListener & Serializable) e -> this.btn_ProductInformation_clickHandler(e));
			this.btn_ProductDataSheet.addStyleName("toolbar-button");

			this.btn_EditProductInfo = new Button("", EnumToolbarButton.EDIT_ALTERNATIVE.getResource());
			this.btn_EditProductInfo.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "edit_product_info"));
			this.btn_EditProductInfo.addClickListener((ClickListener & Serializable) e -> this.btn_EditProductInfo_clickHandler(e));
			this.btn_EditProductInfo.addStyleName("toolbar-button");
						
			this.btn_DeleteProduct = new Button("", EnumToolbarButton.DELETE.getResource());
			this.btn_DeleteProduct.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "delete_product"));
			this.btn_DeleteProduct.addClickListener((ClickListener & Serializable) e -> this.btn_DeleteAttribute_clickHandler(e));
			this.btn_DeleteProduct.addStyleName("toolbar-button");

			VerticalLayout cmp_InfoProducts = new VerticalLayout();
			cmp_InfoProducts.setMargin(new MarginInfo(false, true));
			cmp_InfoProducts.setSpacing(false);
			
			this.btn_UpdateProductState = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "update_product_state"));
			this.btn_UpdateProductState.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "update_product_state"));
			this.btn_UpdateProductState.addClickListener((ClickListener & Serializable) e -> this.btn_UpdateProductState_clickHandler(e));
			this.btn_UpdateProductState.addStyleName("action-button");
			cmp_InfoProducts.addComponent(this.btn_UpdateProductState);

			this.btn_OtherActions = new BbrPopupButton("");
			this.btn_OtherActions.setIcon(EnumToolbarButton.LIST.getResource());
			this.btn_OtherActions.addStyleName("toolbar-button");
			this.btn_OtherActions.setContentLayout(cmp_InfoProducts);
			this.btn_OtherActions.setClosePopupOnOutsideClick(true);

			leftButtonsBar.addComponents(this.btn_ProductDataSheet, this.btn_EditProductInfo, this.btn_OtherActions, this.btn_DeleteProduct);
			
			//MPE
			VerticalLayout cmp_DownloadProductButtons = new VerticalLayout();
			cmp_DownloadProductButtons.setMargin(new MarginInfo(false, true));
			cmp_DownloadProductButtons.setSpacing(false);
			
			this.btn_Download = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_selected_products"));
			this.btn_Download.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_selected_products"));
			this.btn_Download.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_Download.addStyleName("action-button");
			cmp_DownloadProductButtons.addComponent(this.btn_Download);
			
			this.btn_DownloadRelatedProduct = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_related_products"));
			this.btn_DownloadRelatedProduct.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_related_products"));
			this.btn_DownloadRelatedProduct.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadRelatedProduct.addStyleName("action-button");
			cmp_DownloadProductButtons.addComponent(this.btn_DownloadRelatedProduct);
			
			this.btn_DownloadProductsGrp = new BbrPopupButton("");
			this.btn_DownloadProductsGrp.setIcon(BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DownloadPrimary.png"));
			this.btn_DownloadProductsGrp.addStyleName("toolbar-button");
			this.btn_DownloadProductsGrp.setContentLayout(cmp_DownloadProductButtons);
			this.btn_DownloadProductsGrp.setClosePopupOnOutsideClick(true);
			
			//MPE

			this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
			this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
			this.btn_Refresh.addStyleName("toolbar-button");

			HorizontalLayout rightButtonsBar = new HorizontalLayout();
			rightButtonsBar.addComponents(this.btn_DownloadProductsGrp, this.btn_Refresh);
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

			// Sección Grilla
			this.datGrid_CPItems = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.datGrid_CPItems.setSortable(false);

			this.initializeCPItemsDataGridColumns();

			this.datGrid_CPItems.addStyleName("report-grid");
			this.datGrid_CPItems.setSizeFull();
			this.datGrid_CPItems.setPagingManager(pagingManager, ID);
			this.datGrid_CPItems.setSelectionMode(SelectionMode.MULTI);
			this.datGrid_CPItems.addSelectionListener((SelectionListener<CPItemDTO> & Serializable) e -> selection_gridHandler(e));

			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.addComponents(frmHeader, toolBar, this.datGrid_CPItems, this.pagingManager);
			mainLayout.setSizeFull();
			mainLayout.setSpacing(true);
			mainLayout.setExpandRatio(this.datGrid_CPItems, 1F);
			mainLayout.addStyleName("bbr-margin-windows");

			// Ventana
			this.setWidth(90, Unit.PERCENTAGE);
			this.setHeight(90, Unit.PERCENTAGE);
			this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_product_information"));
			this.setContent(mainLayout);
			this.resetPageInfo = true;
			this.startWaiting();

			this.updateWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			this.updateWork.addFunction(new Function<Object, CPItemArrayResultDTO>()
			{
				@Override
				public CPItemArrayResultDTO apply(Object t)
				{
					return executeUpdateService(ViewProductInformation.this.getBbrUIParent());
				}
			});
			this.executeBbrWork(this.updateWork);
		}
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			ViewProductInformation bbrSender = (ViewProductInformation) sender;
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateReport(result, sender);
			}
			else if (triggerObject == this.btn_DownloadTriggerButton)
			{
				bbrSender.doDownloadFile(result, sender, triggerObject);
			}
			else if (triggerObject == this.btn_DeleteProduct)
			{
				bbrSender.doDeleteReport(result, sender);
			}
			else if (triggerObject == this.btn_UpdateProductState)
			{
				bbrSender.doChangeProductState(result, sender);
			}
			
		}
		else
		{
			ViewProductInformation senderReport = (ViewProductInformation) sender;

			this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
			I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}


	@Override
	protected void downloadFormat_selectedHandler(BbrEvent event)
	{
		DownloadFormats selectedFormat = ((event != null) && (event.getResultObject() instanceof DownloadFormats)) ? (DownloadFormats) event.getResultObject() : null;

		this.downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_DownloadTriggerButton);
		this.downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(ViewProductInformation.this.getBbrUIParent(), selectedFormat, ViewProductInformation.this.btn_DownloadTriggerButton, null);
			}
		});

		this.startWaiting();
		this.executeBbrWork(this.downloadsWork);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================
	private void selection_gridHandler(SelectionEvent<CPItemDTO> e)
	{
		if (e.isUserOriginated())
		{
			this.updateButtons(true);
		}
	}

	
	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		if (this.btn_DownloadTriggerButton == this.btn_Download || this.btn_DownloadTriggerButton == this.btn_DownloadRelatedProduct)
		{
			this.selectDownloadFileType(DownloadFormats.XLS, DownloadFormats.XLS, DownloadFormats.CSV);
		}
	}

	
	private void btn_UpdateProductState_clickHandler(ClickEvent e)
	{
		UpdateProductState updateStatesWin = new UpdateProductState(this.getBbrUIParent());
		updateStatesWin.setParentModule(this.getParentModule());
		updateStatesWin.addBbrEventListener((BbrEventListener & Serializable) ce -> state_changeHandler(ce));
		updateStatesWin.open(true, true, this.getParentModule());
	}
	
	
	private void state_changeHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getEventType().equals(BbrEvent.ITEM_UPDATED))
		{
			CPItemStateDTO state = (CPItemStateDTO) bbrEvent.getResultObject();
			
			if(state != null)
			{
				this.stateChangeWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_UpdateProductState);
				this.stateChangeWork.addFunction(new Function<Object, CPItemErrorArrayResultDTO>()
				{
					@Override
					public CPItemErrorArrayResultDTO apply(Object t)
					{
						return executeChangeStateService(ViewProductInformation.this.getBbrUIParent(), state);
					}
				});

				this.startWaiting();
				this.executeBbrWork(this.stateChangeWork);
			}
		}
	}
	

	private void btn_DeleteAttribute_clickHandler(ClickEvent e)
	{
		if ((this.datGrid_CPItems.getSelectedItems() != null) && (this.datGrid_CPItems.getSelectedItems().size() > 0))
		{

			ArrayList<CPItemDTO> selectedResource = new ArrayList<>();
			selectedResource.addAll(this.datGrid_CPItems.getSelectedItems());

			CPItemDTO[] mpItemsArray = selectedResource.toArray(new CPItemDTO[selectedResource.size()]);

			BbrMessageBox.createQuestion(ViewProductInformation.this.getBbrUIParent())
			.withYesButton(new Runnable()
			{
				@Override
				public void run()
				{
					deleteResourceWork = new BbrWork<>(ViewProductInformation.this, ViewProductInformation.this.getBbrUIParent(), btn_DeleteProduct);
					deleteResourceWork.addFunction(new Function<Object, BaseResultDTO>()
					{
						@Override
						public BaseResultDTO apply(Object t)
						{
							return executeDeleteService(ViewProductInformation.this.getBbrUIParent(), mpItemsArray);
						}
					});

					ViewProductInformation.this.startWaiting();

					ViewProductInformation.this.executeBbrWork(deleteResourceWork);
				}
			})
			.withNoButton()
			.withCaption(I18NManager.getI18NString(ViewProductInformation.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"))
			.withHtmlMessage(I18NManager.getI18NString(ViewProductInformation.this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "ask_delete_product"))
			.open();
		}
	}


	private void btn_EditProductInfo_clickHandler(ClickEvent e)
	{
		if(this.datGrid_CPItems.getSelectedItems().size() > 0)
		{
			try
			{
				UserTypeArrayResultDTO userRoles = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getUserTypes();
				UserTypeDTO commmercialRole;
				
				if((userRoles != null) && (userRoles.getValues() != null) && (userRoles.getValues().length > 0))
				{
					commmercialRole = Arrays.stream(userRoles.getValues()).filter(ur -> ur.getInternalname().equals(FEPConstants.UCDM_ROLE_NAME)).findAny().orElse(null);
					
					if(commmercialRole != null)
					{
						CPItemDTO[] selectedProdsArray = this.datGrid_CPItems.getSelectedItems().toArray(new CPItemDTO[this.datGrid_CPItems.getSelectedItems().size()]);
						
						ProductsReleaseDetails detailsWin = new ProductsReleaseDetails(this.getBbrUIParent(), selectedProdsArray, this.selectedProvider, commmercialRole, selectedProdsArray[0].getArticletypeid(), true);
						detailsWin.setParentModule(this.getParentModule());
						detailsWin.addBbrEventListener((BbrEventListener & Serializable) bbrEvent -> this.requestUpdatedHandler(bbrEvent));
						detailsWin.open(true, true, this.getParentModule());
					}
				}
			}
			catch (BbrUserException ex)
			{
				AppUtils.getInstance().doLogOut();
			}
			catch (BbrSystemException ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{
			
		}
	}
	
	
	private void requestUpdatedHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getEventType().equals(BbrEvent.ITEM_UPDATED))
		{
			this.updateReport();
		}
	}


	private void btn_ProductInformation_clickHandler(ClickEvent e)
	{
		if (this.datGrid_CPItems.getAllSelectedsItems() != null && !this.datGrid_CPItems.getAllSelectedsItems().isEmpty())
		{
			CPItemDTO selectdItem = this.datGrid_CPItems.getAllSelectedsItems().get(0);
			ViewProductDatasheet winDataSheet = new ViewProductDatasheet(getBbrUIParent(), selectdItem, this.selectedProvider, true);
			winDataSheet.open(true, true, this.getParentModule());
		}
	}


	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.resetPageInfo = true;

		this.updateReport();
	}


	private void pagingChange_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.resetPageInfo = false;

			this.updateReport();
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void initializeCPItemsDataGridColumns()
	{
		this.datGrid_CPItems.addCustomColumn(CPItemDTO::getId, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_request_number"), true)
		.setId(ID);
		
		this.datGrid_CPItems.addCustomColumn(CPItemDTO::getSku, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_sku"), true)
		.setId(PLU);

		this.datGrid_CPItems.addCustomColumn(CPItemDTO::getDescription, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_description_product"), true)
		.setId(DESCRIPTION);

		this.datGrid_CPItems.addCustomColumn(CPItemDTO::getEancode, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_ean_code"), true)
		.setId(EANCODE);

		this.datGrid_CPItems.addCustomColumn(CPItemDTO::getTrade, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_trade_mark"), true)
		.setId(TRADE);

		this.datGrid_CPItems.addCustomColumn(CPItemDTO::getCreationdate, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_creation_date"), true)
		.setStyleGenerator(item -> "v-align-center")
		.setRenderer(new LocalDateTimeRenderer("dd-MM-yyyy HH:mm"))
		.setWidth(150)
		.setId(CREATIONDATE);

		this.datGrid_CPItems.addCustomColumn(CPItemDTO::getLastmodified, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_last_modified"), true)
		.setStyleGenerator(item -> "v-align-center")
		.setRenderer(new LocalDateTimeRenderer("dd-MM-yyyy HH:mm"))
		.setWidth(150)
		.setId(LASTMODIFIED);

		this.datGrid_CPItems.addCustomColumn(CPItemDTO::getCurrentstatename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_state"), true)
		.setId(CURRENTSTATENAME);
	}


	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton, Object extraData)
	{
		FileDownloadInfoResultDTO fileResult = null;
	
		if (selectedFormat != null)
		{
			try
			{
				UserTypeArrayResultDTO userRoles = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getUserTypes();
				UserTypeDTO commmercialRole;
				
				if((userRoles != null) && (userRoles.getValues() != null) && (userRoles.getValues().length > 0))
				{
					commmercialRole = Arrays.stream(userRoles.getValues()).filter(ur -> ur.getInternalname().equals(FEPConstants.UCDM_ROLE_NAME)).findAny().orElse(null);
					
					if(commmercialRole != null)
					{
						CPItemsByFilterInitParamDTO downloadInitParam = new CPItemsByFilterInitParamDTO();
						downloadInitParam.setArticletypeid(this.selectedArticleType.getArticletypeid());
						downloadInitParam.setProvidercode(this.selectedProvider.getRut());
						downloadInitParam.setUsertypeid(commmercialRole.getId());
						downloadInitParam.setCpkeys(this.mpItems.stream().map(ci -> ci.getId()).toArray(Long[]::new));   
						
						if (this.btn_DownloadTriggerButton == this.btn_Download){
							
							fileResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).downloadItemsByFilter(downloadInitParam, bbrUIParent.getUser().getId(), selectedFormat.getValue());
						
						}else if (this.btn_DownloadTriggerButton == this.btn_DownloadRelatedProduct){
							
							ArrayList<CPItemDTO> selectdItem = this.datGrid_CPItems.getAllSelectedsItems();
							CPItemDTO[] items = selectdItem.stream().toArray(CPItemDTO[]::new);
							
							fileResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).downloadRelatedProducts(downloadInitParam.getArticletypeid(), items, bbrUIParent.getUser().getId());
						}
						
					}
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


	private BaseResultDTO executeDeleteService(BbrUI bbrUIParent, CPItemDTO[] mpItemsArray)
	{
		BaseResultDTO result = null;
		if (mpItemsArray != null && mpItemsArray.length > 0)
		{
			try
			{
				UserTypeArrayResultDTO userRoles = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getUserTypes();
				UserTypeDTO commmercialRole;
				
				if((userRoles != null) && (userRoles.getValues() != null) && (userRoles.getValues().length > 0))
				{
					commmercialRole = Arrays.stream(userRoles.getValues()).filter(ur -> ur.getInternalname().equals(FEPConstants.UCDM_ROLE_NAME)).findAny().orElse(null);
					
					if(commmercialRole != null)
					{
						result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).removeItems(mpItemsArray, commmercialRole.getId(), FepUtils.createPersonDTO(this.getUser()));
					}
				}
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


	private CPItemArrayResultDTO executeUpdateService(BbrUI bbrUIParent)
	{
		CPItemArrayResultDTO result = null;
		if (this.initParam != null)
		{
			try
			{
				Integer requestedPage = (!resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;
				this.initParam.setPageNumber(requestedPage);
				this.initParam.setNeedPageInfo(resetPageInfo);

				result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).getItemsByFilter(this.initParam);
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

		return result;
	}

	
	private CPItemErrorArrayResultDTO executeChangeStateService(BbrUI bbrUIParent, CPItemStateDTO state)
	{
		CPItemErrorArrayResultDTO result = null;
		CPItemDTO[] selectedItems = this.datGrid_CPItems.getSelectedItems().stream().toArray(CPItemDTO[]::new);
		
		if(this.getBbrUIParent().getUser() != null)
		{
			try
			{
				result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).updateItemsStatus(selectedItems, this.ADMIN_TYPE_ID, state.getInternalname(), FepUtils.createPersonDTO(this.getUser()));
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

		return result;
	}
	

	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ViewProductInformation senderReport = (ViewProductInformation) sender;
		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), true);

			this.stopWaiting();

			if (!error.hasError())
			{
				senderReport.datGrid_CPItems.resetSelection();
				senderReport.downloadLinkFile(result);
				senderReport.updateButtons(false);
			}
		}
	}


	private void doDeleteReport(Object result, BbrWorkExecutor sender)
	{
		ViewProductInformation senderReport = (ViewProductInformation) sender;

		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), true);

			this.stopWaiting();

			if (!error.hasError())
			{
				senderReport.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
				I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "successful_operation"));

//				senderReport.startWaiting();
//
//				senderReport.resetPageInfo = true;
//				senderReport.executeBbrWork(updateWork);
				
				BbrEvent event = new BbrEvent(BbrEvent.ITEM_UPDATED);
				
				this.dispatchBbrEvent(event);
				
				this.close();
			}
			else
			{
				this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"), error.getErrorMessage());
			}
		}
	}
	
	
	private void doChangeProductState(Object result, BbrWorkExecutor sender)
	{
		ViewProductInformation senderReport = (ViewProductInformation) sender;

		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), true);

			this.stopWaiting();

			if (!error.hasError())
			{
				senderReport.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
				I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "successful_operation"));

//				senderReport.startWaiting();
//
//				senderReport.resetPageInfo = true;
//				senderReport.executeBbrWork(updateWork);
				
				BbrEvent event = new BbrEvent(BbrEvent.ITEM_UPDATED);
				
				this.dispatchBbrEvent(event);
				
				this.close();
			}
			else
			{
				this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"), error.getErrorMessage());
			}
		}
	}


	private void updateButtons(Boolean isSelectionEvent)
	{
		boolean hasValuesSelected = this.datGrid_CPItems.getAllSelectedsItems() != null && !this.datGrid_CPItems.getAllSelectedsItems().isEmpty();
		this.btn_DeleteProduct.setEnabled(hasValuesSelected);
		this.btn_EditProductInfo.setEnabled(hasValuesSelected && this.datGrid_CPItems.getAllSelectedsItems().size() > 0);
		this.btn_UpdateProductState.setEnabled(hasValuesSelected && this.datGrid_CPItems.getAllSelectedsItems().size() > 0);
		this.btn_OtherActions.setEnabled(this.btn_UpdateProductState.isEnabled());
		this.btn_ProductDataSheet.setEnabled(hasValuesSelected && this.datGrid_CPItems.getAllSelectedsItems().size() == 1);
		this.btn_Download.setEnabled(this.initParam != null);
		this.btn_Refresh.setEnabled(this.initParam != null);
		this.btn_DownloadRelatedProduct.setEnabled(this.datGrid_CPItems.getAllSelectedsItems().size() > 0);
		
	}


	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		ViewProductInformation senderReport = (ViewProductInformation) sender;

		if (result != null)
		{
			CPItemArrayResultDTO reportResult = (CPItemArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				if (reportResult != null)
				{
					this.mpItems = reportResult.getValues() != null ? new ArrayList<CPItemDTO>(Arrays.asList(reportResult.getValues())) : null;
					ListDataProvider<CPItemDTO> dataproviderConstraintData = new ListDataProvider<>(this.mpItems);
					this.datGrid_CPItems.setDataProvider(dataproviderConstraintData);
					this.datGrid_CPItems.getDataProvider().refreshAll();

					if (reportResult.getPageinfo() != null && senderReport.resetPageInfo)
					{
						PageInfoDTO pageInfo = reportResult.getPageinfo();
						BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
						senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);
					}
				}

				this.updateButtons(false);
			}
		}

		senderReport.stopWaiting();
	}


	private BbrTextField getInfoTextField(String captionResourceName)
	{
		BbrTextField textField = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, captionResourceName));
		textField.addStyleName("bbr-text-field-read-only");
		textField.setWidth(100, Unit.PERCENTAGE);
		textField.setReadOnly(true);

		return textField;
	}


	private void updateReport()
	{
		this.startWaiting();
		this.executeBbrWork(updateWork);
	}

	//
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
