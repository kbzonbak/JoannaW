package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DescriptionGenerator;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.StyleGenerator;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.fep.common.data.classes.ArticleTypeResultDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeDataDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoDTO;
import bbr.b2b.fep.common.data.classes.UserTypeDTO;
import bbr.b2b.fep.cp.data.classes.CPArticleTypeInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemAttributeValueDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.fep.cp.data.classes.CPItemErrorArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByIDsInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPUpdateItemsInitParamDTO;
import bbr.b2b.portal.classes.binders.BbrBinderEvent;
import bbr.b2b.portal.classes.binders.BbrBinderListener;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.constants.HtmlConstants;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.functions.ConverterFunctionsUtils;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.InitParamsUtil;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.classes.wrappers.fep.ProductCreationDetailsResult;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.modules.fep.binders.FEPCPItemsBinder;
import bbr.b2b.portal.components.utils.generic.FindProvider;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.files_transfer.BbrFileUploader;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrErrorList;
import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class ProductsReleaseDetails extends BbrWindow implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long											serialVersionUID					= 714410004889409337L;

	private static final String										PROVIDER								= "providersocialreason";
	private static final String										REQUEST_NUMBER						= "id";

	private Boolean														commercialUserMode				= null;
	private Boolean														editionMode							= null;

	private BbrAdvancedGrid<CPItemDTO>								datGrid_Items						= null;

	private BbrWork<ProductCreationDetailsResult>				templateAndValuesWork			= null;
	private BbrWork<CPItemErrorArrayResultDTO>					updateDataWork						= null;
	private BbrWork<CPItemErrorArrayResultDTO>					uploadWork							= null;
	private BbrWork<FileDownloadInfoResultDTO>					downloadsWork						= null;

	private ArrayList<CPItemDTO>										currentWorkingData				= null;

	private FEPCPItemsBinder											itemBinder							= null;

	private List<DefinableAttributeDataDTO>						listVariant							= null;
	private List<DefinableAttributeDataDTO>						listGeneric							= null;

	private Panel															pnlEditor							= null;

	private Map<Long, Map<String, CPItemAttributeValueDTO>>	mapItemsAttributes				= null;

	private Button															btn_AddProduct						= null;
	private Button															btn_RemoveProduct					= null;
	private Button															btn_SaveData						= null;
	private Button															btn_SendData						= null;
	private Button															btn_ApproveRequest				= null;
	private Button															btn_RejectRequest					= null;
	private Button															btn_FinalRejectRequest			= null;
	private Button															btn_RejectRequestGrped			= null;
	private Button															btn_FinalRejectRequestGrped	= null;
	private BbrPopupButton												btn_Rejections						= null;

	private Map<String, List<ErrorInfoDTO>>						mapCellsErrors						= new HashMap<>();

	private HeaderRow														headerRow							= null;

	private Upload															btn_UploadExcel					= null;
	private Button															btn_DownloadExcel					= null;

	private CPItemDTO[]													selectedItems						= null;
	private CompanyDataDTO												selectedProvider					= null;
	private UserTypeDTO													selectedRole						= null;
	private Long															selectedTemplateID				= null;
	private CompanyDataDTO												locallySelectedProvider			= null;
	private Long															internalIDCounter					= 0L;
	private Boolean														finalRejectionTrigger			= false;
	private Boolean														firstCreationTrigger				= false;
	private Boolean														allRequestsSelected				= false;
	private Boolean														managerUserMode					= false;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductsReleaseDetails(BbrUI parent, CPItemDTO[] selectedItems, CompanyDataDTO selectedProvider, UserTypeDTO selectedRole, Long templateID, Boolean managerUserMode)
	{
		super(parent);
		this.selectedItems = selectedItems;
		this.selectedProvider = selectedProvider;
		this.selectedTemplateID = templateID;
		this.selectedRole = selectedRole;
		this.commercialUserMode = (selectedRole != null) ? selectedRole.getInternalname().equals(FEPConstants.UCDM_ROLE_NAME) : false;
		this.editionMode = (this.commercialUserMode || ((this.selectedItems != null) && (this.selectedItems.length > 0)));
		this.managerUserMode = managerUserMode;
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
		// ACTIONS BUTTONS SECTION

		VerticalLayout cmp_RejectButtons = new VerticalLayout();
		cmp_RejectButtons.setMargin(new MarginInfo(false, true));
		cmp_RejectButtons.setSpacing(false);

		// Proveedor (Nuevas Solicitudes): Añadir, Enviar
		// Comprador: Aprobar, Rechazar, Rechazar Final
		// Proveedor (Solicitudes Existentes): Añadir, Enviar, Rechazar Final

		// Si se está llamando desde el reporte de Admin. Productos, los botones
		// de "Aprobar" y "Enviar Info" no van, y son reemplazados
		// por el botón "Guardar"

		if (this.managerUserMode)
		{
			this.btn_SaveData = new Button("",
					BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_ActivatePrimary.png"));
			this.btn_SaveData.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "btn_save_request"));
			this.btn_SaveData.addClickListener((ClickListener & Serializable) e -> this.btnSave_clickHandler(e));
			this.btn_SaveData.addStyleName("toolbar-button");
		}
		else if (this.commercialUserMode)
		{
			this.btn_ApproveRequest = new Button("",
					BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_ActivatePrimary.png"));
			this.btn_ApproveRequest.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "btn_approve_request"));
			this.btn_ApproveRequest.addClickListener((ClickListener & Serializable) e -> this.btnApprove_clickHandler(e));
			this.btn_ApproveRequest.addStyleName("toolbar-button");
			this.btn_ApproveRequest.setVisible(false);

			this.btn_RejectRequest = new Button("",
					BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Delete.png"));
			this.btn_RejectRequest.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "btn_reject_request"));
			this.btn_RejectRequest.addClickListener((ClickListener & Serializable) e -> this.btnRejectRequest_clickHandler(e));
			this.btn_RejectRequest.addStyleName("toolbar-button");
			this.btn_RejectRequest.setVisible(false);

			this.btn_FinalRejectRequest = new Button("",
					BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Delete.png"));
			this.btn_FinalRejectRequest.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "btn_final_reject_request"));
			this.btn_FinalRejectRequest.addClickListener((ClickListener & Serializable) e -> this.btnRejectRequest_clickHandler(e));
			this.btn_FinalRejectRequest.addStyleName("toolbar-button");
			this.btn_FinalRejectRequest.setVisible(false);

			// Habilitar un boton conjunto para el caso donde el comprador puede
			// realizar tanto un rechazo normal como uno final

			this.btn_RejectRequestGrped = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "btn_reject_request"));
			this.btn_RejectRequestGrped.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "btn_reject_request"));
			this.btn_RejectRequestGrped.addClickListener((ClickListener & Serializable) e -> this.btnRejectRequest_clickHandler(e));
			this.btn_RejectRequestGrped.addStyleName("action-button");
			cmp_RejectButtons.addComponent(this.btn_RejectRequestGrped);

			this.btn_FinalRejectRequestGrped = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "btn_final_reject_request"));
			this.btn_FinalRejectRequestGrped.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "btn_final_reject_request"));
			this.btn_FinalRejectRequestGrped.addClickListener((ClickListener & Serializable) e -> this.btnRejectRequest_clickHandler(e));
			this.btn_FinalRejectRequestGrped.addStyleName("action-button");
			cmp_RejectButtons.addComponent(this.btn_FinalRejectRequestGrped);

			this.btn_Rejections = new BbrPopupButton("");
			this.btn_Rejections.setIcon(BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Delete.png"));
			this.btn_Rejections.addStyleName("toolbar-button");
			this.btn_Rejections.setContentLayout(cmp_RejectButtons);
			this.btn_Rejections.setClosePopupOnOutsideClick(true);
			this.btn_Rejections.setVisible(false);
		}
		else
		{
			if (this.editionMode)
			{
				this.btn_FinalRejectRequest = new Button("",
						BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Delete.png"));
				this.btn_FinalRejectRequest.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "btn_final_reject_request"));
				this.btn_FinalRejectRequest.addClickListener((ClickListener & Serializable) e -> this.btnRejectRequest_clickHandler(e));
				this.btn_FinalRejectRequest.addStyleName("toolbar-button");
				this.btn_FinalRejectRequest.setVisible(false);
			}
			else
			{
				this.btn_AddProduct = new Button("",
						BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_AddPrimary.png"));
				this.btn_AddProduct.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "add_product"));
				this.btn_AddProduct.addClickListener((ClickListener & Serializable) e -> this.btnAddProduct_clickHandler(e));
				this.btn_AddProduct.addStyleName("toolbar-button");

				this.firstCreationTrigger = true;
			}

			this.btn_RemoveProduct = new Button("",
					BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DeactivatePrimary.png"));
			this.btn_RemoveProduct.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "btn_remove_product"));
			this.btn_RemoveProduct.addClickListener((ClickListener & Serializable) e -> this.btnRemoveProduct_clickHandler(e));
			this.btn_RemoveProduct.addStyleName("toolbar-button");

			this.btn_SendData = new Button("",
					BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_ActionRightPrimary.png"));
			this.btn_SendData.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "send_information"));
			this.btn_SendData.addClickListener((ClickListener & Serializable) e -> this.btnSend_clickHandler(e));
			this.btn_SendData.addStyleName("toolbar-button");
		}

		this.btn_DownloadExcel = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		this.btn_DownloadExcel.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_products_info"));
		this.btn_DownloadExcel.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
		this.btn_DownloadExcel.addStyleName("toolbar-button");

		BbrFileUploader uploaderReceiver = new BbrFileUploader(BbrAppConstants.getUploadPathOfUser(this.getUser().getId()));

		this.btn_UploadExcel = new Upload(null, uploaderReceiver);
		this.btn_UploadExcel.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "upload_products_info"));
		this.btn_UploadExcel.addStyleName("bbr-upload-excel-button");
		this.btn_UploadExcel.addFinishedListener((FinishedListener & Serializable) e -> uploadExcelFinished_handler(e));

		HorizontalLayout pnlRequestActions = new HorizontalLayout();
		pnlRequestActions.setMargin(false);

		HorizontalLayout pnlFileActions = new HorizontalLayout(this.btn_DownloadExcel, this.btn_UploadExcel);
		pnlFileActions.setMargin(false);

		HorizontalLayout pnlFilter = new HorizontalLayout();
		pnlFilter.setMargin(false);
		pnlFilter.setWidth("100%");

		if (this.managerUserMode)
		{
			pnlRequestActions.addComponents(this.btn_SaveData);
		}
		else if (this.commercialUserMode)
		{
			pnlRequestActions.addComponents(this.btn_ApproveRequest, this.btn_RejectRequest, this.btn_FinalRejectRequest, this.btn_Rejections);
		}
		else if (this.editionMode)
		{
			pnlRequestActions.addComponents(this.btn_SendData, this.btn_FinalRejectRequest);
		}
		else
		{
			pnlRequestActions.addComponents(this.btn_SendData, this.btn_AddProduct, this.btn_RemoveProduct);
		}

		pnlFilter.addComponents(pnlRequestActions, pnlFileActions);
		pnlFilter.setExpandRatio(pnlRequestActions, 1F);

		// Panel de la grilla
		this.datGrid_Items = new BbrAdvancedGrid<>(this.getUser().getLocale());

		this.datGrid_Items.addStyleName("report-grid");
		this.datGrid_Items.setSizeFull();
		this.datGrid_Items.setSelectionMode(SelectionMode.MULTI);
		this.datGrid_Items.addSelectionListener((SelectionListener<CPItemDTO> & Serializable) e -> selection_gridHandler(e));

		this.pnlEditor = new Panel(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "editor"));
		this.pnlEditor.addStyleName("panel-item-editor");
		this.pnlEditor.setWidth("300px");
		this.pnlEditor.setHeight("100%");

		VerticalLayout dataLayout = new VerticalLayout();
		dataLayout.setMargin(false);
		dataLayout.setSizeFull();
		dataLayout.addComponents(pnlFilter, datGrid_Items);
		dataLayout.setExpandRatio(datGrid_Items, 1F);

		Label lblRequieresFields = new Label(
				HtmlConstants.RED_ASTERISK_BETWEEN_PARENTHESES + " " + I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_required_fields"));
		lblRequieresFields.setContentMode(ContentMode.HTML);

		VerticalLayout editorLayout = new VerticalLayout();
		editorLayout.setMargin(false);
		editorLayout.setWidth("300px");
		editorLayout.setHeight("100%");
		editorLayout.addComponents(this.pnlEditor, lblRequieresFields);
		editorLayout.setExpandRatio(this.pnlEditor, 1F);

		HorizontalLayout mainLayout = new HorizontalLayout(dataLayout, editorLayout);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
		mainLayout.setExpandRatio(dataLayout, 1F);
		mainLayout.addStyleName("bbr-margin-windows");

		// Ventana
		String winTitle = (this.commercialUserMode || this.editionMode) ? I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "edit_product_info")
				: I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "add_product");

		this.setWidth(90, Unit.PERCENTAGE);
		this.setHeight(90, Unit.PERCENTAGE);
		this.setResizable(true);
		this.setCaption(winTitle);
		this.setContent(mainLayout);

		this.templateAndValuesWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		this.templateAndValuesWork.addFunction(new Function<Object, ProductCreationDetailsResult>()
		{
			@Override
			public ProductCreationDetailsResult apply(Object t)
			{
				return executeTemplateValuesService(ProductsReleaseDetails.this.getBbrUIParent());
			}
		});

		this.startWaiting();
		this.executeBbrWork(this.templateAndValuesWork);
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doUpdateReport((ProductCreationDetailsResult) result, sender);
			}
			else if (triggerObject == btn_SaveData)
			{
				this.doUpdateSave((CPItemErrorArrayResultDTO) result, sender);
			}
			else if ((triggerObject == btn_ApproveRequest) || (triggerObject == btn_SendData))
			{
				this.doUpdateSend((CPItemErrorArrayResultDTO) result, sender);
			}
			else if (triggerObject == btn_DownloadExcel)
			{
				this.doDownloadFile((FileDownloadInfoResultDTO) result, sender, triggerObject);
			}
			else if (triggerObject == btn_UploadExcel)
			{
				this.reportUploaded(result, sender);
			}
		}
		else
		{
			ProductsReleaseDetails senderReport = (ProductsReleaseDetails) sender;

			this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(senderReport.getBbrUIParent(),
							BbrUtilsResources.RES_GENERIC,
							"unsuccessful_operation"));

			senderReport.stopWaiting();
		}

	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void btnRejectRequest_clickHandler(ClickEvent e)
	{
		if ((e != null) && (e.getButton() != null) && (this.datGrid_Items.getSelectedItems() != null) && (this.datGrid_Items.getSelectedItems().size() > 0))
		{
			this.allRequestsSelected = (this.datGrid_Items.getSelectedItems().size() == this.datGrid_Items.getTotalRowsCount());
			this.finalRejectionTrigger = (e.getButton().equals(this.btn_FinalRejectRequest) || e.getButton().equals(this.btn_FinalRejectRequestGrped));

			BbrMessageBox.createQuestion(this.getBbrUIParent())
			.withYesButton((Runnable & Serializable) () -> this.handleRejectRequestYes())
			.withNoButton()
			.withCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"))
			.withHtmlMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "ask_reject_request"))
			.open();
		}
		else
		{
			this.showInfoMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "request_required"));
		}
	}


	private void btnAddProduct_clickHandler(ClickEvent e)
	{
		if (this.selectedProvider.getId() == -1)
		{
			if (this.locallySelectedProvider == null)
			{
				FindProvider winFindProvider = new FindProvider(this.getBbrUIParent(), true);
				winFindProvider.initializeView();
				winFindProvider.addBbrEventListener((BbrEventListener & Serializable) bbrEvent -> this.companySelectedHandler(bbrEvent));
				winFindProvider.open(true);
			}
			else if ((this.locallySelectedProvider != null) && (this.locallySelectedProvider.getId() > 0))
			{
				BbrMessageBox.createQuestion(this.getBbrUIParent())
				.withYesButton((Runnable & Serializable) () -> this.handleKeepProviderYes())
				.withNoButton((Runnable & Serializable) () -> this.handleKeepProviderNo())
				.withCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"))
				.withHtmlMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "ask_keep_working_provider"))
				.open();
			}
		}
		else
		{
			if (this.locallySelectedProvider == null)
			{
				this.locallySelectedProvider = this.selectedProvider;
			}

			this.enterNewProduct();
		}
	}


	private void handleKeepProviderYes()
	{
		this.enterNewProduct();
	}


	private void handleKeepProviderNo()
	{
		FindProvider winFindProvider = new FindProvider(this.getBbrUIParent(), false);
		winFindProvider.initializeView();
		winFindProvider.addBbrEventListener((BbrEventListener & Serializable) bbrEvent -> this.companySelectedHandler(bbrEvent));
		winFindProvider.open(true);
	}


	private void companySelectedHandler(BbrEvent bbrEvent)
	{
		if ((bbrEvent != null) && (bbrEvent.getResultObject() != null))
		{
			this.locallySelectedProvider = (CompanyDataDTO) bbrEvent.getResultObject();
			this.enterNewProduct();
		}
	}


	private void handleRejectRequestYes()
	{
		CPItemDTO[] selectedItems = this.datGrid_Items.getSelectedItems().stream().toArray(CPItemDTO[]::new);

		RejectProductItemRequest rejectWin = new RejectProductItemRequest(this.getBbrUIParent(), selectedItems, this.selectedRole, this.finalRejectionTrigger);
		rejectWin.addBbrEventListener((BbrEventListener & Serializable) e -> this.updateHandler(e));
		rejectWin.initializeView();
		rejectWin.open(true);
	}


	private void updateHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getEventType().equals(BbrEvent.ITEMS_UPDATED))
		{
			this.dispatchBbrEvent(new BbrEvent(BbrEvent.ITEMS_UPDATED));

			if (!this.allRequestsSelected)
			{
				this.btnRemoveProduct_clickHandler(null);
			}
			else
			{
				this.close();
			}
		}
	}


	private void enterNewProduct()
	{
		if (this.locallySelectedProvider != null)
		{
			this.internalIDCounter++;

			CPItemDTO newProductItem = new CPItemDTO();
			newProductItem.setId(this.internalIDCounter);
			newProductItem.setArticleid(this.internalIDCounter);
			newProductItem.setAttributevalues(this.getAttributeValuesForNewProduct());
			newProductItem.setProvidersocialreason(this.locallySelectedProvider.getName());
			newProductItem.setProvidercode(this.locallySelectedProvider.getRut());
			newProductItem.setProviderid(this.locallySelectedProvider.getId());

			// Agregar el nuevo item a la tabla

			if (this.currentWorkingData == null)
			{
				this.currentWorkingData = new ArrayList<>();
			}

			this.currentWorkingData.add(newProductItem);
			this.updateGridData(this.currentWorkingData);
			this.datGrid_Items.deselectAll();
			this.datGrid_Items.select(newProductItem);
		}
	}


	private CPItemAttributeValueDTO[] getAttributeValuesForNewProduct()
	{
		ArrayList<DefinableAttributeDataDTO> allDefAttributes = new ArrayList<>();
		allDefAttributes.addAll((this.listGeneric != null) ? this.listGeneric : new ArrayList<DefinableAttributeDataDTO>());
		allDefAttributes.addAll((this.listVariant != null) ? this.listVariant : new ArrayList<DefinableAttributeDataDTO>());

		ArrayList<CPItemAttributeValueDTO> valuesList = new ArrayList<>();
		CPItemAttributeValueDTO newAttributeValue;

		if ((allDefAttributes != null) && !allDefAttributes.isEmpty())
		{
			for (DefinableAttributeDataDTO defAttribute : allDefAttributes)
			{
				try
				{
					newAttributeValue = CPItemAttributeValueDTO.getNewCPAttributevalueFromDefinable(defAttribute);
					newAttributeValue.setItemid(this.internalIDCounter);
					valuesList.add(newAttributeValue);
				}
				catch (OperationFailedException e)
				{
					e.printStackTrace();
				}
			}
		}

		return valuesList.toArray(new CPItemAttributeValueDTO[valuesList.size()]);
	}


	private void btnRemoveProduct_clickHandler(ClickEvent e)
	{
		if ((this.datGrid_Items.getSelectedItems() != null) && (this.currentWorkingData != null) && !currentWorkingData.isEmpty())
		{
			for (CPItemDTO selectedItem : this.datGrid_Items.getSelectedItems())
			{
				this.currentWorkingData.remove(selectedItem);
			}

			this.updateGridData(this.currentWorkingData);
		}
	}


	private void btnSend_clickHandler(ClickEvent e)
	{
		CPItemDTO[] selectedItems = this.datGrid_Items.getSelectedItems().stream().toArray(CPItemDTO[]::new);
		this.allRequestsSelected = (this.datGrid_Items.getSelectedItems().size() == this.datGrid_Items.getTotalRowsCount());

		if (selectedItems != null && selectedItems.length > 0)
		{
			CPUpdateItemsInitParamDTO saveInitParams = this.getUpdateInitParam(selectedItems, false);

			this.startWaiting();
			this.updateDataWork = new BbrWork<>(ProductsReleaseDetails.this, ProductsReleaseDetails.this.getBbrUIParent(), this.btn_SendData);
			this.updateDataWork.addFunction(new Function<Object, CPItemErrorArrayResultDTO>()
			{
				@Override
				public CPItemErrorArrayResultDTO apply(Object t)
				{
					return executeUpdateDataService(ProductsReleaseDetails.this.getBbrUIParent(), saveInitParams);
				}
			});

			this.executeBbrWork(this.updateDataWork);
		}
	}


	private void btnApprove_clickHandler(ClickEvent e)
	{
		CPItemDTO[] selectedItems = this.datGrid_Items.getSelectedItems().stream().toArray(CPItemDTO[]::new);
		this.allRequestsSelected = (this.datGrid_Items.getSelectedItems().size() == this.datGrid_Items.getTotalRowsCount());

		if (selectedItems != null && selectedItems.length > 0)
		{
			CPUpdateItemsInitParamDTO saveInitParams = this.getUpdateInitParam(selectedItems, false);

			this.startWaiting();
			this.updateDataWork = new BbrWork<>(ProductsReleaseDetails.this, ProductsReleaseDetails.this.getBbrUIParent(), this.btn_ApproveRequest);
			this.updateDataWork.addFunction(new Function<Object, CPItemErrorArrayResultDTO>()
			{
				@Override
				public CPItemErrorArrayResultDTO apply(Object t)
				{
					return executeUpdateDataService(ProductsReleaseDetails.this.getBbrUIParent(), saveInitParams);
				}
			});

			this.executeBbrWork(this.updateDataWork);
		}
	}


	private void btnSave_clickHandler(ClickEvent e)
	{
		CPItemDTO[] selectedItems = this.datGrid_Items.getSelectedItems().stream().toArray(CPItemDTO[]::new);
		this.allRequestsSelected = (this.datGrid_Items.getSelectedItems().size() == this.datGrid_Items.getTotalRowsCount());

		if (selectedItems != null && selectedItems.length > 0)
		{
			CPUpdateItemsInitParamDTO saveInitParams = this.getUpdateInitParam(selectedItems, true);

			this.startWaiting();
			this.updateDataWork = new BbrWork<>(ProductsReleaseDetails.this, ProductsReleaseDetails.this.getBbrUIParent(), this.btn_SaveData);
			this.updateDataWork.addFunction(new Function<Object, CPItemErrorArrayResultDTO>()
			{
				@Override
				public CPItemErrorArrayResultDTO apply(Object t)
				{
					return executeUpdateDataService(ProductsReleaseDetails.this.getBbrUIParent(), saveInitParams);
				}
			});

			this.executeBbrWork(this.updateDataWork);
		}
	}


	private CPItemErrorArrayResultDTO executeUpdateDataService(BbrUI bbrUIParent, CPUpdateItemsInitParamDTO saveInitParams)
	{
		CPItemErrorArrayResultDTO result = null;

		if (saveInitParams != null)
		{
			try
			{
				result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).addOrUpdateItems(saveInitParams);
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


	private void selection_gridHandler(SelectionEvent<CPItemDTO> e)
	{
		if (!e.getAllSelectedItems().isEmpty())
		{
			itemBinder.updateView(e.getAllSelectedItems().iterator().next());
		}
		else
		{
			itemBinder.clearView();
		}

		this.updateButtons();
	}


	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
		{
			this.downloadProductsData();
		}
	}


	private void uploadExcelFinished_handler(FinishedEvent e)
	{
		this.startWaiting();

		uploadWork = new BbrWork<>(this, this.getBbrUIParent(), btn_UploadExcel);
		uploadWork.addFunction(new Function<Object, CPItemErrorArrayResultDTO>()
		{
			@Override
			public CPItemErrorArrayResultDTO apply(Object t)
			{
				return executeUploadService(ProductsReleaseDetails.this.getBbrUIParent(), e.getFilename());
			}
		});

		this.executeBbrWork(uploadWork);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void downloadProductsData()
	{
		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(ProductsReleaseDetails.this.getBbrUIParent(), DownloadFormats.CSV, btn_DownloadTriggerButton, null);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}


	private ProductCreationDetailsResult executeTemplateValuesService(BbrUI parentUI)
	{
		ProductCreationDetailsResult result = null;
		ArticleTypeResultDTO templateResult = null;
		CPItemArrayResultDTO valuesResult = null;

		try
		{
			templateResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(parentUI).getArticleTypeWithCPPrivilegies(this.getArticleTypeInitParam());

			if ((this.selectedItems != null) && (this.selectedItems.length > 0))
			{
				valuesResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(parentUI).getItemsByIDs(this.getDetailsInitParam());
			}

			result = new ProductCreationDetailsResult(templateResult, valuesResult);
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

		return result;
	}


	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton, Object extraData)
	{
		FileDownloadInfoResultDTO fileResult = null;

		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
				{
					CPItemDTO[] currentItems = (this.currentWorkingData != null) ? this.currentWorkingData.toArray(new CPItemDTO[this.currentWorkingData.size()]) : new CPItemDTO[0];

					if (this.editionMode)
					{
						fileResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).downloadExcelWithItemsToEdit(this.getArticleTypeInitParam(),
								currentItems,
								true,
								bbrUIParent.getUser().getId());
					}
					else
					{
						fileResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).downloadExcelWithItemsToCreate(this.getArticleTypeInitParam(),
								currentItems,
								true,
								bbrUIParent.getUser().getId());
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


	private CPArticleTypeInitParamDTO getArticleTypeInitParam()
	{
		CPArticleTypeInitParamDTO initParam = InitParamsUtil.getBaseInitParamInstance(CPArticleTypeInitParamDTO.class, this.getBbrUIParent());
		initParam.setArticletypeid(this.selectedTemplateID);
		initParam.setItemstatename((this.selectedItems != null) ? this.selectedItems[0].getCurrentstateinternalname() : FEPConstants.PROVIDER_NEW_NAME_STATUS);
		initParam.setIncludeAttributeInfo(true);
		initParam.setNeedPageInfo(false);
		initParam.setRows(100);
		initParam.setUsertypename(this.selectedRole.getInternalname());

		return initParam;
	}


	private CPItemErrorArrayResultDTO executeUploadService(BbrUI bbrUIParent, String fileName)
	{
		CPItemErrorArrayResultDTO result = null;

		if (bbrUIParent != null)
		{
			try
			{
				this.getTimingMngr().startTimer();

				CPItemDTO[] currentItems = (this.currentWorkingData != null) ? this.currentWorkingData.toArray(new CPItemDTO[this.currentWorkingData.size()]) : new CPItemDTO[0];

				if (this.editionMode)
				{
					result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).uploadExcelWithEditedItems(this.getArticleTypeInitParam(),
							currentItems,
							fileName,
							bbrUIParent.getUser().getId());
				}
				else
				{
					result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).uploadExcelWithNewsItems(this.getArticleTypeInitParam(),
							currentItems,
							fileName,
							bbrUIParent.getUser().getId());
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
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}


	private CPUpdateItemsInitParamDTO getUpdateInitParam(CPItemDTO[] selectedItems, boolean save)
	{
		CPUpdateItemsInitParamDTO result = InitParamsUtil.getBaseInitParamInstance(CPUpdateItemsInitParamDTO.class, this.getBbrUIParent());

		result.setArticletypeid(this.selectedTemplateID);
		result.setItems(selectedItems);
		result.setPartialsave(save);
		result.setUser(FepUtils.createPersonDTO(this.getUser()));
		result.setUsertypename(this.selectedRole.getInternalname());
		result.setItemstatename(
				(this.selectedItems != null) && (this.selectedItems[0] != null) ? this.selectedItems[0].getCurrentstateinternalname() : FEPConstants.PROVIDER_NEW_NAME_STATUS);

		return result;
	}


	private CPItemsByIDsInitParamDTO getDetailsInitParam()
	{
		Long selectedItemsIDs[] = Arrays.stream(this.selectedItems).map(s -> s.getId()).toArray(size -> new Long[size]);

		CPItemsByIDsInitParamDTO initParam = new CPItemsByIDsInitParamDTO();
		initParam.setArticletypeid(this.selectedItems[0].getArticletypeid());
		initParam.setItemids(selectedItemsIDs);
		initParam.setLocale(this.getUser().getLocale());
		initParam.setWithdetails(true);
		initParam.setUsertypeid(this.selectedRole.getId());

		return initParam;
	}


	private void initializeItemsAttributeMap(CPItemDTO[] items)
	{
		mapItemsAttributes = new HashMap<>();

		if (items != null && items.length > 0)
		{
			for (CPItemDTO item : items)
			{
				Map<String, CPItemAttributeValueDTO> mapAttibutes = new HashMap<>();

				if (item.getAttributevalues() != null && item.getAttributevalues().length > 0)
				{
					for (CPItemAttributeValueDTO attributes : item.getAttributevalues())
					{
						mapAttibutes.put(attributes.getAttributeinternalname(), attributes);
					}
				}

				this.mapItemsAttributes.put(item.getId(), mapAttibutes);
			}
		}
	}


	private void updateItemsAttributeMap(CPItemDTO item)
	{
		if (mapItemsAttributes == null)
		{
			mapItemsAttributes = new HashMap<>();
		}

		if (item != null)
		{
			Map<String, CPItemAttributeValueDTO> mapAttibutes = mapItemsAttributes.get(item.getId());
			if (mapAttibutes == null)
			{
				mapAttibutes = new HashMap<>();
			}

			if (item.getAttributevalues() != null && item.getAttributevalues().length > 0)
			{
				for (CPItemAttributeValueDTO attributes : item.getAttributevalues())
				{
					mapAttibutes.put(attributes.getAttributeinternalname(), attributes);
				}
			}

			this.mapItemsAttributes.put(item.getId(), mapAttibutes);
		}

		this.updateButtons();
	}


	private CPItemAttributeValueDTO getAttribute(Long itemId, String internalName)
	{
		CPItemAttributeValueDTO result = null;

		if (this.mapItemsAttributes != null)
		{
			Map<String, CPItemAttributeValueDTO> attributes = this.mapItemsAttributes.get(itemId);
			if (attributes != null)
			{
				result = attributes.get(internalName);
			}
		}

		return result;
	}


	private void initializeDataGridColumns(ArticleTypeResultDTO articleTypeResult)
	{
		this.datGrid_Items.removeAllColumns();

		if (headerRow != null)
		{
			this.datGrid_Items.removeHeaderRow(headerRow);
		}

		if (this.commercialUserMode || this.editionMode)
		{
			this.datGrid_Items.addCustomColumn(CPItemDTO::getId, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_request_number"), true)
			.setWidth(120)
			.setId(REQUEST_NUMBER);
		}

		this.datGrid_Items.addCustomColumn(item -> {
			String strEdited = (item.isEdited()) ? "* " : "";
			return strEdited + item.getProvidersocialreason();
		}, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_provider"), true).setWidth(160).setId(PROVIDER);

		String[] basicIds = new String[] { PROVIDER };

		if (articleTypeResult != null && articleTypeResult.getDefattributes() != null && articleTypeResult.getDefattributes().length > 0)
		{
			DefinableAttributeDataDTO[] arr_Definitions = articleTypeResult.getDefattributes();

			listVariant = new ArrayList<>();
			listGeneric = new ArrayList<>();

			for (DefinableAttributeDataDTO definition : arr_Definitions)
			{
				if (definition.getForvariant())
				{
					listVariant.add(definition);
				}
				else
				{
					listGeneric.add(definition);
				}
			}

			Collections.sort(listGeneric, Comparator.comparingInt(DefinableAttributeDataDTO::getVisualorder));
			Collections.sort(listVariant, Comparator.comparingInt(DefinableAttributeDataDTO::getVisualorder));

			String[] genericIds = null;
			String[] variantIds = null;

			if (!listGeneric.isEmpty() || !listVariant.isEmpty())
			{
				headerRow = this.datGrid_Items.prependHeaderRow();
			}

			// Columnas genéricas
			if (!listGeneric.isEmpty())
			{
				genericIds = new String[listGeneric.size()];
				for (int i = 0; i < listGeneric.size(); i++)
				{
					String columnId = this.createColumn(listGeneric.get(i));

					genericIds[i] = columnId;
				}
			}

			// Columnas Variantes
			if (!listVariant.isEmpty())
			{
				variantIds = new String[listVariant.size()];
				for (int i = 0; i < listVariant.size(); i++)
				{
					String columnId = this.createColumn(listVariant.get(i));

					variantIds[i] = columnId;
				}
			}

			this.createHeader(" ", basicIds);
			this.createHeader(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_header_generic"), genericIds);
			this.createHeader(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_header_variant"), variantIds);
		}

		itemBinder = new FEPCPItemsBinder(this.getParentModule(), listGeneric, listVariant, this.commercialUserMode);
		itemBinder.addBbrBinderEventListener(new BbrBinderListener<CPItemDTO>()
		{
			private static final long serialVersionUID = 1898407026147407083L;


			@Override
			public void bbrBinderEvent_handler(BbrBinderEvent<CPItemDTO> event)
			{
				DefinableAttributeDTO attribute = event.getAttribute();
				CPItemDTO item = event.getResultObject();

				if (attribute != null)
				{
					// El atributo no es generico y no hay que actualizar a
					// todos los del mismo artículo.
					if (attribute.getForvariant())
					{
						item.setEdited(true);
						updateItemsAttributeMap(item);
						datGrid_Items.getDataProvider().refreshItem(item);
					}
					else
					{
						List<CPItemDTO> updatedItems = getUpdatedItems(item, event.getAttribute(), event.getAttributeValue());
						if (updatedItems != null && !updatedItems.isEmpty())
						{
							for (CPItemDTO genericItems : updatedItems)
							{
								genericItems.setEdited(true);
								updateItemsAttributeMap(genericItems);
								datGrid_Items.getDataProvider().refreshItem(genericItems);
							}
						}
					}
				}
			}
		});

		this.pnlEditor.setContent(itemBinder.getLayout());
	}


	private List<CPItemDTO> getUpdatedItems(CPItemDTO sourceItem, DefinableAttributeDataDTO attribute, String attributeValue)
	{
		List<CPItemDTO> result = null;
		// Es un atributo genérico
		if (attribute != null && !attribute.getForvariant())
		{
			result = this.currentWorkingData.stream().filter(item -> item.getId().equals(sourceItem.getId())).collect(Collectors.toList());
			result.forEach(item -> updateItemAttributeValue(item, attribute, attributeValue));
		}
		return result;
	}


	private void updateItemAttributeValue(CPItemDTO item, DefinableAttributeDataDTO attribute, String value)
	{
		Map<String, CPItemAttributeValueDTO> mapAttribute = item.getAttributevaluesAsMap();

		CPItemAttributeValueDTO cpAttribute = mapAttribute.get(attribute.getAttributeinternalname());

		if (cpAttribute == null)
		{
			try
			{
				cpAttribute = CPItemAttributeValueDTO.getNewCPAttributevalueFromDefinable(attribute);
				cpAttribute.setItemid(item.getId());
			}
			catch (OperationFailedException e)
			{
				e.printStackTrace();
			}
		}

		cpAttribute.setValue(value);

		mapAttribute.put(attribute.getAttributeinternalname(), cpAttribute);

		CPItemAttributeValueDTO[] attributes = mapAttribute.values().toArray(new CPItemAttributeValueDTO[mapAttribute.size()]);

		item.setAttributevalues(attributes);
	}


	private String createColumn(DefinableAttributeDataDTO item)
	{

		String columnId = item.getAttributeinternalname();

		Column<CPItemDTO, String> column = this.datGrid_Items.addCustomColumn(new ValueProvider<CPItemDTO, String>()
		{
			private static final long serialVersionUID = 1908119097580191467L;


			@Override
			public String apply(CPItemDTO source)
			{
				CPItemAttributeValueDTO attribute = getAttribute(source.getId(), columnId);
				String value = (attribute != null && attribute.getValue() != null) ? attribute.getValue() : "";

				return value;
			}
		}, item.getAttributevendorname());

		column.setId(columnId);
		column.setWidth(160);
		column.setStyleGenerator(new StyleGenerator<CPItemDTO>()
		{
			private static final long serialVersionUID = 4385401091958251103L;


			@Override
			public String apply(CPItemDTO item)
			{
				String result = "";
				if (hasCellError(item, column.getId()))
				{
					result = "grid-cell-corner-triangle";
				}

				return result;
			}
		});
		column.setDescriptionGenerator(new DescriptionGenerator<CPItemDTO>()
		{
			private static final long serialVersionUID = 459833642844251715L;


			@Override
			public String apply(CPItemDTO item)
			{
				return getCellError(item, column.getId());
			}
		}, ContentMode.HTML);
		return columnId;
	}


	@SuppressWarnings("rawtypes")
	private void createHeader(String caption, String[] columnsIds)
	{
		if (columnsIds != null)
		{
			HeaderRow headerRow = datGrid_Items.getHeaderRow(0);

			if (columnsIds.length > 1)
			{
				Column[] columns = new Column[columnsIds.length];
				for (int i = 0; i < columns.length; i++)
				{
					columns[i] = this.datGrid_Items.getColumn(columnsIds[i]);
				}

				headerRow.join(columns).setHtml(BbrUtils.getGridHeaderFormattedCellText(caption, true));
			}
			else if (columnsIds.length == 1)
			{
				HeaderCell cell = headerRow.getCell(columnsIds[0]);
				cell.setHtml(BbrUtils.getGridHeaderFormattedCellText(caption, true));
			}
		}
	}


	private void updateGridData(List<CPItemDTO> reportData)
	{
		ListDataProvider<CPItemDTO> items = new ListDataProvider<>(reportData);
		this.datGrid_Items.setDataProvider(items);
		this.datGrid_Items.getDataProvider().refreshAll();
	}


	private void setButtonsVisibilityByTemplateData(ArticleTypeResultDTO templateData)
	{
		if (templateData != null)
		{
			if (this.btn_ApproveRequest != null)
			{
				this.btn_ApproveRequest.setVisible(templateData.isCanApprove());
			}

			if (templateData.isCanReject() && templateData.isCanFinalReject())
			{
				if (this.btn_Rejections != null)
				{
					this.btn_Rejections.setVisible(true);
				}
			}

			else
			{
				if (this.btn_Rejections != null)
				{
					this.btn_RejectRequest.setVisible(templateData.isCanReject());
				}

				if (this.btn_FinalRejectRequest != null)
				{
					this.btn_FinalRejectRequest.setVisible(templateData.isCanFinalReject());
				}
			}
		}
	}


	private void doUpdateReport(ProductCreationDetailsResult result, BbrWorkExecutor sender)
	{
		ProductsReleaseDetails senderReport = (ProductsReleaseDetails) sender;

		if (result != null)
		{
			BbrError error = ErrorsMngr.getInstance().getError(result.getArticleTypeResult(), this.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				senderReport.initializeDataGridColumns(result.getArticleTypeResult());
				senderReport.updateButtons();

				if (this.editionMode || this.commercialUserMode)
				{
					senderReport.setButtonsVisibilityByTemplateData(result.getArticleTypeResult());

					if ((result.getCpItemArrayResult().getValues() != null) && (result.getCpItemArrayResult().getValues().length > 0))
					{
						senderReport.initializeItemsAttributeMap(result.getCpItemArrayResult().getValues());
						senderReport.currentWorkingData = new ArrayList<>(Arrays.asList(result.getCpItemArrayResult().getValues()));
						senderReport.updateGridData(senderReport.currentWorkingData);
					}
					else
					{
						senderReport.initializeItemsAttributeMap(result.getCpItemArrayResult().getValues());

						if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
						{
							senderReport.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
									I18NManager.getI18NString(senderReport.getBbrUIParent(),
											BbrUtilsResources.RES_MODULES_FEP,
											"no_mdm_products_data"));
						}

						this.dispatchBbrEvent(null);
						senderReport.close();
					}
				}
			}
		}
		else
		{
			senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_SYSTEM, "P200"));
		}

		senderReport.stopWaiting();

		if (senderReport.firstCreationTrigger)
		{
			senderReport.firstCreationTrigger = false;
			senderReport.btnAddProduct_clickHandler(null);
		}
	}


	private void doUpdateSave(CPItemErrorArrayResultDTO result, BbrWorkExecutor sender)
	{
		ProductsReleaseDetails senderReport = (ProductsReleaseDetails) sender;

		if (senderReport != null)
		{
			if (result != null)
			{
				BbrError error = ErrorsMngr.getInstance().getError(result, senderReport.getBbrUIParent(), false);

				if (!error.hasError())
				{
					//					senderReport.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
					//					I18NManager.getI18NString(senderReport.getBbrUIParent(),
					//					BbrUtilsResources.RES_GENERIC, "successful_operation"));
					//					senderReport.updateItems(result.getItems(), false);
					//					this.dispatchBbrEvent(new BbrEvent(BbrEvent.ITEM_UPDATED));

					ProductDataUpdateResults resultsWin = new ProductDataUpdateResults(this.getBbrUIParent(), result.getItems(), null, this.commercialUserMode);
					resultsWin.addCloseListener((CloseListener & Serializable) e -> this.products_closeHandler(e));
					resultsWin.initializeView();
					resultsWin.open(true);
				}
				else if(result.getItems() != null)
				{
					//					ErrorInfoDTO[] errors = result.getErrors();
					//					if (errors != null && errors.length > 0)
					//					{
					//						if (errors.length == 1)
					//						{
					//							senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					//							errors[0].getStatusmessage());
					//						}
					//						else
					//						{
					//							BbrErrorList<ErrorInfoDTO> winErrors = new BbrErrorList<>(senderReport.getBbrUIParent(), errors, senderReport.getUser().getLocale());
					//							winErrors.setConverterFunction(ConverterFunctionsUtils.errorConverterFunction);
					//							winErrors.open(true, true, senderReport.getParentModule());
					//						}
					//					}
					//					else
					//					{
					//						senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					//						result.getStatusmessage());
					//					}
					//
					//					senderReport.processErrors(result.getItems());
					//					senderReport.datGrid_Items.getDataProvider().refreshAll();

					CPItemDTO successfulItems[] = Arrays.stream(result.getItems())
							.filter(i -> (i.getErrors() == null) || (i.getErrors().length <= 0))
							.toArray(size -> new CPItemDTO[size]);
					CPItemDTO failedItems[] = Arrays.stream(result.getItems()).filter(i -> (i.getErrors() != null) && (i.getErrors().length > 0)).toArray(size -> new CPItemDTO[size]);

					if ((failedItems != null) && (failedItems.length > 0))
					{
						this.cleanAndOrganizeGridData(failedItems, false);
						ErrorInfoDTO errors[] = this.currentWorkingData.stream()
								.flatMap(s -> Arrays.stream(s.getErrors() != null ? s.getErrors() : new ErrorInfoDTO[0]))
								.toArray(size -> new ErrorInfoDTO[size]);

						ProductDataUpdateResults resultsWin = new ProductDataUpdateResults(this.getBbrUIParent(), successfulItems, errors, this.commercialUserMode);
						resultsWin.open(true, true, senderReport.getParentModule());

						itemBinder.clearView();
						this.datGrid_Items.deselectAll();
					}
					else
					{
						senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
								result.getStatusmessage());
					}

					senderReport.processErrors(result.getItems());
					senderReport.datGrid_Items.getDataProvider().refreshAll();
				}
			}
			else
			{
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_SYSTEM, "P200"));
			}
		}
		senderReport.stopWaiting();
	}


	private void doUpdateSend(CPItemErrorArrayResultDTO result, BbrWorkExecutor sender)
	{
		ProductsReleaseDetails senderReport = (ProductsReleaseDetails) sender;

		if (senderReport != null)
		{
			if (result != null)
			{
				BbrError error = ErrorsMngr.getInstance().getError(result, senderReport.getBbrUIParent(), false);

				if (!error.hasError())
				{
					ProductDataUpdateResults resultsWin = new ProductDataUpdateResults(this.getBbrUIParent(), result.getItems(), null, this.commercialUserMode);
					resultsWin.addCloseListener((CloseListener & Serializable) e -> this.products_closeHandler(e));
					resultsWin.initializeView();
					resultsWin.open(true);
				}
				else if (result.getItems() != null)
				{
					CPItemDTO successfulItems[] = Arrays.stream(result.getItems())
							.filter(i -> (i.getErrors() == null) || (i.getErrors().length <= 0))
							.toArray(size -> new CPItemDTO[size]);
					CPItemDTO failedItems[] = Arrays.stream(result.getItems()).filter(i -> (i.getErrors() != null) && (i.getErrors().length > 0)).toArray(size -> new CPItemDTO[size]);

					if ((failedItems != null) && (failedItems.length > 0))
					{
						this.cleanAndOrganizeGridData(failedItems, false);
						ErrorInfoDTO errors[] = this.currentWorkingData.stream()
								.flatMap(s -> Arrays.stream(s.getErrors() != null ? s.getErrors() : new ErrorInfoDTO[0]))
								.toArray(size -> new ErrorInfoDTO[size]);

						ProductDataUpdateResults resultsWin = new ProductDataUpdateResults(this.getBbrUIParent(), successfulItems, errors, this.commercialUserMode);
						resultsWin.open(true, true, senderReport.getParentModule());

						itemBinder.clearView();
						this.datGrid_Items.deselectAll();
					}
					else
					{
						senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
								result.getStatusmessage());
					}

					senderReport.processErrors(result.getItems());
					senderReport.datGrid_Items.getDataProvider().refreshAll();
				}
			}
			else
			{
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_SYSTEM, "P200"));
			}
		}

		senderReport.stopWaiting();
	}


	private void cleanAndOrganizeGridData(CPItemDTO failedItems[], Boolean isUploadingExcel)
	{
		if (!isUploadingExcel)
		{
			// Si estamos enviando las solicitudes, sólo dejamos en la grilla las
			// que fallaron la validacion

			Iterator<CPItemDTO> iter = this.currentWorkingData.iterator();
			Boolean foundItem = false;

			while (iter.hasNext())
			{
				CPItemDTO actualItem = iter.next();
				foundItem = false;

				for (CPItemDTO failItem : failedItems)
				{
					if (failItem.getId().equals(actualItem.getId()))
					{
						foundItem = true;
					}
				}

				if (!foundItem)
				{
					iter.remove();
				}
			}
		}

		// Copiar a los elementos existentes los errores de cada item

		for (CPItemDTO actualItem : this.currentWorkingData)
		{
			for (CPItemDTO failedItem : failedItems)
			{
				if (actualItem.getId().equals(failedItem.getId()))
				{
					actualItem.setErrors(failedItem.getErrors());
				}
			}
		}

		// Asignar numeros de fila para que el usuario pueda corregir errores en
		// la grilla

		int rowNumber = 1;

		for (CPItemDTO item : this.currentWorkingData)
		{
			item.setRow(rowNumber);

			for (ErrorInfoDTO err : item.getErrors())
			{
				err.setRownumber(item.getRow());
			}

			rowNumber++;
		}

		// Actualizar el contador de IDs interno para no causar conflictos si
		// despues de intentar
		// enviar las solicitudes actuales y solo lograr actualizacion parcial, el
		// usuario decide
		// seguir agregando nuevas solicitudes

		this.internalIDCounter = Integer.toUnsignedLong(rowNumber);
	}


	private void products_closeHandler(CloseEvent event)
	{
		// No tiene mucho sentido actualizar la grilla del reporte si se está
		// creando una nueva solicitud,
		// sólo si se edita o se aprueba una existente

		if (this.editionMode || this.commercialUserMode)
		{
			this.dispatchBbrEvent(new BbrEvent(BbrEvent.ITEMS_UPDATED));
		}

		if (!this.allRequestsSelected)
		{
			this.btnRemoveProduct_clickHandler(null);
		}
		else
		{
			this.close();
		}
	}


	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ProductsReleaseDetails senderReport = (ProductsReleaseDetails) sender;

		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}


	private void reportUploaded(Object result, BbrWorkExecutor sender)
	{
		ProductsReleaseDetails senderReport = (ProductsReleaseDetails) sender;

		if (result != null)
		{
			CPItemErrorArrayResultDTO reportResult = (CPItemErrorArrayResultDTO) result;
			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), false);

			if (!error.hasError() && (reportResult != null) && (reportResult.getItems() != null))
			{
				senderReport.currentWorkingData = new ArrayList<>(Arrays.asList(reportResult.getItems()));
				senderReport.updateGridData(senderReport.currentWorkingData);
				senderReport.updateItems(reportResult.getItems(), true);

				this.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(),
								BbrUtilsResources.RES_MODULES_COMMERCIAL,
								"file_processed"));

				senderReport.initializeItemsAttributeMap(reportResult.getItems());
			}
			else if ((reportResult != null) && (reportResult.getItems() != null))
			{
				senderReport.currentWorkingData = new ArrayList<>(Arrays.asList(reportResult.getItems()));
				senderReport.updateGridData(senderReport.currentWorkingData);
				senderReport.updateItems(reportResult.getItems(), true);

				CPItemDTO successfulItems[] = Arrays.stream(reportResult.getItems())
						.filter(i -> (i.getErrors() == null) || (i.getErrors().length <= 0))
						.toArray(size -> new CPItemDTO[size]);
				CPItemDTO failedItems[] = Arrays.stream(reportResult.getItems())
						.filter(i -> (i.getErrors() != null) && (i.getErrors().length > 0))
						.toArray(size -> new CPItemDTO[size]);

				if ((failedItems != null) && (failedItems.length > 0))
				{
					this.cleanAndOrganizeGridData(failedItems, true);
					ErrorInfoDTO errors[] = this.currentWorkingData.stream()
							.flatMap(s -> Arrays.stream(s.getErrors() != null ? s.getErrors() : new ErrorInfoDTO[0]))
							.toArray(size -> new ErrorInfoDTO[size]);

					ProductDataUpdateResults resultsWin = new ProductDataUpdateResults(this.getBbrUIParent(), successfulItems, errors, this.commercialUserMode);
					resultsWin.open(true, true, senderReport.getParentModule());

					itemBinder.clearView();
					this.datGrid_Items.deselectAll();
				}
				else
				{
					senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
							reportResult.getStatusmessage());
				}

				senderReport.processErrors(reportResult.getItems());
				senderReport.datGrid_Items.getDataProvider().refreshAll();
				senderReport.initializeItemsAttributeMap(reportResult.getItems());
			}
			else
			{
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						reportResult.getStatusmessage());
			}
		}
		else
		{
			senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_SYSTEM, "P200"));
		}

		senderReport.stopWaiting();
	}


	private boolean hasCellError(CPItemDTO item, String attribute)
	{
		String id = item.getId() + "-" + attribute;

		List<ErrorInfoDTO> error = this.mapCellsErrors.get(id);

		boolean result = (error == null || error.isEmpty()) ? false : true;
		return result;
	}


	private String getCellError(CPItemDTO item, String attribute)
	{
		String result = null;
		String id = item.getId() + "-" + attribute;

		List<ErrorInfoDTO> errors = this.mapCellsErrors.get(id);

		String[] strErrors = (errors == null || errors.isEmpty()) ? null : this.getErrors(errors);

		if (strErrors != null && strErrors.length > 0)
		{
			for (int i = 0; i < strErrors.length; i++)
			{
				if (i == 0)
				{
					result = strErrors[i];
				}
				else
				{
					result += "<br />" + strErrors[i];
				}
			}
		}

		return result;
	}


	private String[] getErrors(List<ErrorInfoDTO> errors)
	{
		String[] result = null;
		if (errors != null && !errors.isEmpty())
		{
			result = new String[errors.size()];

			for (int i = 0; i < errors.size(); i++)
			{
				result[i] = errors.get(i).getStatusmessage();
			}
		}

		return result;
	}


	private void processErrors(CPItemDTO[] items)
	{
		this.mapCellsErrors = new HashMap<>();

		if (items != null && items.length > 0)
		{
			for (CPItemDTO item : items)
			{
				if (item.getErrors() != null && item.getErrors().length > 0)
				{
					for (ErrorInfoDTO error : item.getErrors())
					{
						String id = item.getId() + "-" + error.getColumnname();

						List<ErrorInfoDTO> errors = this.mapCellsErrors.get(id);
						if (errors == null)
						{
							errors = new ArrayList<>();
						}
						errors.add(error);

						this.mapCellsErrors.put(id, errors);
					}
				}
			}
		}
	}


	private void updateItems(CPItemDTO[] items, boolean isEddited)
	{
		if (items != null && items.length > 0 && currentWorkingData != null && !currentWorkingData.isEmpty())
		{
			for (CPItemDTO item : items)
			{
				Optional<CPItemDTO> editedItem = currentWorkingData.stream().filter(mpItem -> mpItem.getId().equals(item.getId())).findAny();
				if (editedItem.isPresent())
				{
					editedItem.get().setEdited(isEddited);
					datGrid_Items.getDataProvider().refreshItem(editedItem.get());
				}
			}
		}
	}


	private void updateButtons()
	{
		Boolean itemsSelected = ((this.datGrid_Items.getSelectedItems() != null) && (this.datGrid_Items.getSelectedItems().size() > 0));

		if(this.managerUserMode)
		{
			this.btn_SaveData.setEnabled(itemsSelected);
		}
		else if (this.commercialUserMode)
		{
			this.btn_ApproveRequest.setEnabled(itemsSelected);

			if (this.btn_RejectRequest != null)
			{
				this.btn_RejectRequest.setEnabled(itemsSelected);
			}
			if (this.btn_FinalRejectRequest != null)
			{
				this.btn_FinalRejectRequest.setEnabled(itemsSelected);
			}
			else if (this.btn_RejectRequestGrped != null)
			{
				this.btn_RejectRequestGrped.setEnabled(itemsSelected);
			}
			else if (this.btn_FinalRejectRequestGrped != null)
			{
				this.btn_FinalRejectRequestGrped.setEnabled(itemsSelected);
			}
		}
		else if (this.editionMode)
		{
			this.btn_RemoveProduct.setEnabled(itemsSelected);
			this.btn_FinalRejectRequest.setEnabled(itemsSelected);
			this.btn_SendData.setEnabled(itemsSelected);
		}
		else
		{
			this.btn_RemoveProduct.setEnabled(itemsSelected);
			this.btn_SendData.setEnabled(itemsSelected);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
