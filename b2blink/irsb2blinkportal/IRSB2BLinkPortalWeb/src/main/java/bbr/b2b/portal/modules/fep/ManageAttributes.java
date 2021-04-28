package bbr.b2b.portal.modules.fep;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.fep.common.data.classes.AttributeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeDTO;
import bbr.b2b.fep.common.data.classes.AttributeInitParamDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoArrayResultDTO;
import bbr.b2b.fep.common.data.classes.LanguageArrayResultDTO;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.wrappers.fep.AttributesInfo;
import bbr.b2b.portal.classes.wrappers.fep.DateTypeRangeSelectionInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.fep.AttributesFilter;
import bbr.b2b.portal.components.modules.fep.AddAttribute;
import bbr.b2b.portal.components.modules.fep.EditAttribute;
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
import cl.bbr.core.classes.files_transfer.BbrFileUploader;
import cl.bbr.core.classes.sets.BbrSetsUtils;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrErrorList;
import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class ManageAttributes extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long							serialVersionUID						= 2462429487268725521L;
	private static final String						INTERNAL_NAME							= "internalname";
	private static final String						VENDOR_NAME								= "vendorname";
	private static final String						ATTRIBUTE_TYPE_NAME					= "localchainname";
	private static final String						ACTIVE									= "active";
	private static final String						USER_HELP								= "userhelp";
	private static final String						CLIENT_NAME								= "clientinternalname";

	private VerticalLayout								mainLayout								= null;

	private BbrAdvancedGrid<AttributeDTO>			datGrid_Attributes					= null;
	private BbrPagingManager							pagingManager							= null;

	private AttributeInitParamDTO						initParam								= null;

	private final int										DEFAULT_PAGE_NUMBER					= 1;
	private final int										MAX_ROWS_NUMBER						= 200;

	private final String									DEFAULT_SORT_FIELD					= INTERNAL_NAME;

	private Button											btn_AddAttribute						= null;
	private Button											btn_EditAttribute						= null;
	private Button											btn_DeleteAttributes					= null;
	private Button											btn_DownloadAttributes				= null;
	private Button											btn_DownloadTranslation				= null;
	private Button											btn_DownloadBaseFile					= null;
	private BbrPopupButton								btn_DownloadAttributesGrp			= null;
	private Upload											btn_UploadAttributeTranslation	= null;
	private Upload											btn_UploadAttributeFile				= null;
//	private BbrPopupButton								btn_UploadAttributesGrp				= null;
	private Button											btn_Refresh								= null;

	private Boolean										trackReport								= true;
	private Boolean										resetPageInfo							= true;

	private OrderCriteriaDTO[]							orderCriteria							= null;

	private BbrWork<AttributeArrayResultDTO>		reportWork								= null;
	private BbrWork<BaseResultDTO>					deleteResourceWork					= null;
	private BbrWork<FileDownloadInfoResultDTO>	downloadsWork							= null;
	private AttributesInfo								filterInfo								= null;
	private BbrWork<ErrorInfoArrayResultDTO>		uploadInfoWork							= null;
	private BbrWork<AttributeArrayResultDTO>		uploadFileWork							= null;
	private String											type										= null;
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ManageAttributes(BbrUI bbrUIParent, String type)
	{
		super(bbrUIParent);
		this.type = type;
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
			AttributesFilter filterLayout = new AttributesFilter(this, this.type);
			filterLayout.initializeView();
			this.setBbrFilterContainer(filterLayout);

			// Paging Manager
			this.pagingManager = new BbrPagingManager();
			this.pagingManager.setLocale(this.getUser().getLocale());
			this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

			HorizontalLayout leftButtonsBar = new HorizontalLayout();
			leftButtonsBar.setSpacing(true);
			leftButtonsBar.setMargin(false);
			leftButtonsBar.setHeight("30px");
			leftButtonsBar.addStyleName("toolbar-layout");

			this.btn_AddAttribute = new Button("",
			BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_AddAlternative.png"));
			this.btn_AddAttribute.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "add_attribute"));
			this.btn_AddAttribute.addClickListener((ClickListener & Serializable) e -> this.btn_AddAttribute_clickHandler(e));
			this.btn_AddAttribute.addStyleName("toolbar-button");

			this.btn_EditAttribute = new Button("",
			BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_EditAlternative.png"));
			this.btn_EditAttribute.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "edit_attribute"));
			this.btn_EditAttribute.addClickListener((ClickListener & Serializable) e -> this.btn_EditAttribute_clickHandler(e));
			this.btn_EditAttribute.addStyleName("toolbar-button");

			this.btn_DeleteAttributes = new Button("",
			BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Delete.png"));
			this.btn_DeleteAttributes.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "delete_attribute"));
			this.btn_DeleteAttributes.addClickListener((ClickListener & Serializable) e -> this.btn_DeleteAttribute_clickHandler(e));
			this.btn_DeleteAttributes.addStyleName("toolbar-button");

			leftButtonsBar.addComponents(this.btn_AddAttribute, btn_EditAttribute, btn_DeleteAttributes);

			VerticalLayout cmp_DownloadAttributesButtons = new VerticalLayout();
			cmp_DownloadAttributesButtons.setMargin(new MarginInfo(false, true));
			cmp_DownloadAttributesButtons.setSpacing(false);

			this.btn_DownloadAttributes = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_list_attributes"));
			this.btn_DownloadAttributes.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_list_attributes"));
			this.btn_DownloadAttributes.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadAttributes.addStyleName("action-button");
			cmp_DownloadAttributesButtons.addComponent(this.btn_DownloadAttributes);

			this.btn_DownloadTranslation = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_translation_attributes"));
			this.btn_DownloadTranslation.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_translation_attributes"));
			this.btn_DownloadTranslation.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadTranslation.addStyleName("action-button");
			cmp_DownloadAttributesButtons.addComponent(this.btn_DownloadTranslation);

			this.btn_DownloadBaseFile = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_attributes_base"));
			this.btn_DownloadBaseFile.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_attributes_base"));
			this.btn_DownloadBaseFile.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadBaseFile.addStyleName("action-button");
			cmp_DownloadAttributesButtons.addComponent(this.btn_DownloadBaseFile);

			this.btn_DownloadAttributesGrp = new BbrPopupButton("");
			this.btn_DownloadAttributesGrp
			.setIcon(BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DownloadPrimary.png"));
			this.btn_DownloadAttributesGrp.addStyleName("toolbar-button");
			this.btn_DownloadAttributesGrp.setContentLayout(cmp_DownloadAttributesButtons);
			this.btn_DownloadAttributesGrp.setClosePopupOnOutsideClick(true);

			VerticalLayout cmp_UploadAttributesButtons = new VerticalLayout();
			cmp_UploadAttributesButtons.setMargin(new MarginInfo(false, true));
			cmp_UploadAttributesButtons.setSpacing(false);

			BbrFileUploader uploaderReceiver = new BbrFileUploader(BbrAppConstants.getUploadPathOfUser(this.getUser().getId()));

			this.btn_UploadAttributeFile = new Upload(null, uploaderReceiver);
			this.btn_UploadAttributeFile.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "upload_attributes_file"));
			this.btn_UploadAttributeFile.addStyleName("bbr-upload-excel-button");
			this.btn_UploadAttributeFile.addFinishedListener((FinishedListener & Serializable) e -> uploadExcelFinished_handler(e));
			
//			this.btn_UploadAttributeTranslation = new Upload(null, uploaderReceiver);
//			this.btn_UploadAttributeTranslation.setButtonCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "upload_attribute_translation"));
//			this.btn_UploadAttributeTranslation.setButtonStyleName("action-button");
//			this.btn_UploadAttributeTranslation.addFinishedListener((FinishedListener & Serializable) e -> uploadExcelFinished_handler(e));
//			cmp_UploadAttributesButtons.addComponent(this.btn_UploadAttributeTranslation);

//			this.btn_UploadAttributeFile = new Upload(null, uploaderReceiver);
//			this.btn_UploadAttributeFile.setButtonCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "upload_attributes_file"));
//			this.btn_UploadAttributeFile.setButtonStyleName("action-button");
//			this.btn_UploadAttributeFile.addFinishedListener((FinishedListener & Serializable) e -> uploadExcelFinished_handler(e));
//			cmp_UploadAttributesButtons.addComponent(this.btn_UploadAttributeFile);
//
//			this.btn_UploadAttributesGrp = new BbrPopupButton("");
//			this.btn_UploadAttributesGrp
//			.setIcon(BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_UploadAlternative.png"));
//			this.btn_UploadAttributesGrp.addStyleName("toolbar-button");
//			this.btn_UploadAttributesGrp.setContentLayout(cmp_UploadAttributesButtons);
//			this.btn_UploadAttributesGrp.setClosePopupOnOutsideClick(true);

			this.btn_Refresh = new Button("", BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Refresh.png"));
			this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
			this.btn_Refresh.addStyleName("toolbar-button");

			HorizontalLayout rightButtonsBar = new HorizontalLayout();
			rightButtonsBar.addComponents(this.btn_DownloadAttributesGrp, this.btn_UploadAttributeFile, this.btn_Refresh);
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
			this.datGrid_Attributes = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.datGrid_Attributes.setSortable(false);

			this.initializeDataGridColumns();

			this.datGrid_Attributes.addStyleName("report-grid");
			this.datGrid_Attributes.setSizeFull();
			this.datGrid_Attributes.setPagingManager(pagingManager, this.DEFAULT_SORT_FIELD);
			this.datGrid_Attributes.setSelectionMode(SelectionMode.MULTI);
			this.datGrid_Attributes.addSelectionListener((SelectionListener<AttributeDTO> & Serializable) e -> selection_gridHandler(e));
			this.datGrid_Attributes.addSortListener((SortListener<GridSortOrder<AttributeDTO>> & Serializable) e -> dataGrid_sortHandler(e));
			this.datGrid_Attributes.addItemClickListener((ItemClickListener<AttributeDTO> & Serializable) e -> dgdItem_clickHandler(e));

			this.mainLayout = new VerticalLayout();
			mainLayout.addStyleName("report-layout");
			mainLayout.setSizeFull();
			mainLayout.setMargin(false);
			mainLayout.addComponents(toolBar, this.datGrid_Attributes, pagingManager);
			mainLayout.setExpandRatio(this.datGrid_Attributes, 1F);

			this.addComponents(mainLayout);

			this.updateButtons(false);

			reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			reportWork.addFunction(new Function<Object, AttributeArrayResultDTO>()
			{
				@Override
				public AttributeArrayResultDTO apply(Object t)
				{
					return executeService(ManageAttributes.this.getBbrUIParent());
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
			this.filterInfo = (AttributesInfo) event.getResultObject();
			this.initParam = this.filterInfo.getAttributeInitParam();

			this.trackReport = true;
			this.resetPageInfo = true;

			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ManageAttributes bbrSender = (ManageAttributes) sender;
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
			else if (triggerObject == btn_DeleteAttributes)
			{
				bbrSender.doDeleteReport(result, sender);
			}
			else if (triggerObject == btn_UploadAttributeTranslation)
			{
				bbrSender.reportUploaded(result, sender);
			}
			else if (triggerObject == btn_UploadAttributeFile)
			{
				bbrSender.attributesUploaded(result, sender);
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
		DownloadFormats selectedFormat = ((event != null) && (event.getResultObject() instanceof DownloadFormats)) ? (DownloadFormats) event.getResultObject() : null;

		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(ManageAttributes.this.getBbrUIParent(), selectedFormat, btn_DownloadTriggerButton, null);
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

	private void btn_AddAttribute_clickHandler(ClickEvent event)
	{
		if (this.initParam != null)
		{
			AddAttribute addAttributeWin = new AddAttribute(this.getBbrUIParent(), initParam, this.getAllLanguages());
			addAttributeWin.addBbrEventListener((BbrEventListener & Serializable) e -> attribute_createdHandler(e));
			addAttributeWin.initializeView();
			addAttributeWin.open(true);
		}
	}


	private void attribute_createdHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getEventType().equals(BbrEvent.ITEM_CREATED))
		{
			this.startWaiting();

			this.trackReport = false;
			this.resetPageInfo = true;
			this.executeBbrWork(reportWork);
		}
	}


	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		if (this.btn_DownloadTriggerButton == this.btn_DownloadAttributes || this.btn_DownloadTriggerButton == this.btn_DownloadTranslation)
		{
			this.selectDownloadFileType(DownloadFormats.XLS, DownloadFormats.XLS, DownloadFormats.CSV);
		}
		else if (this.btn_DownloadTriggerButton == this.btn_DownloadBaseFile)
		{
			downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
			downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
			{
				@Override
				public FileDownloadInfoResultDTO apply(Object t)
				{
					return executeDownloadBaseService(ManageAttributes.this.getBbrUIParent());
				}
			});

			this.startWaiting();
			this.executeBbrWork(downloadsWork);
		}
	}


	private void uploadExcelFinished_handler(FinishedEvent e)
	{
		if ((e != null) && (e.getUpload() != null))
		{
			if (e.getUpload().equals(this.btn_UploadAttributeTranslation))
			{
				uploadInfoWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_UploadAttributeTranslation);
				uploadInfoWork.addFunction(new Function<Object, ErrorInfoArrayResultDTO>()
				{
					@Override
					public ErrorInfoArrayResultDTO apply(Object t)
					{
						String filename = e.getFilename();

						return executeUploadInfoService(ManageAttributes.this.getBbrUIParent(), filename);
					}
				});

				this.startWaiting();
				this.executeBbrWork(uploadInfoWork);
			}

			else if (e.getUpload().equals(this.btn_UploadAttributeFile))
			{
				uploadFileWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_UploadAttributeFile);
				uploadFileWork.addFunction(new Function<Object, AttributeArrayResultDTO>()
				{
					@Override
					public AttributeArrayResultDTO apply(Object t)
					{
						String filename = e.getFilename();

						return executeUploadFileService(ManageAttributes.this.getBbrUIParent(), filename);
					}
				});

				this.startWaiting();
				this.executeBbrWork(uploadFileWork);
			}
		}
	}


	private void btn_EditAttribute_clickHandler(ClickEvent event)
	{
		if ((this.datGrid_Attributes.getSelectedItems() != null) && (this.datGrid_Attributes.getSelectedItems().size() > 0))
		{
			AttributeDTO[] selectedAttributes = BbrSetsUtils.getInstance().toArray(this.datGrid_Attributes.getSelectedItems(), AttributeDTO.class);

			AttributesInfo paramsInfo = new AttributesInfo(this.filterInfo.getAttributeSearchInfo(),
			this.filterInfo.getSelectedDataType(),
			this.type,
			this.filterInfo.getSelectedClient());

			EditAttribute updateWin = new EditAttribute(this.getBbrUIParent(), paramsInfo, selectedAttributes, this.getAllLanguages());
			updateWin.addBbrEventListener((BbrEventListener & Serializable) ev -> attribute_editedHandler(ev));
			updateWin.initializeView();
			updateWin.open(true);
		}
	}


	private void attribute_editedHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getEventType().equals(BbrEvent.ITEM_UPDATED))
		{
			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}


	private void btn_DeleteAttribute_clickHandler(ClickEvent event)
	{
		BbrMessageBox.createQuestion(ManageAttributes.this.getBbrUIParent())
		.withYesButton(new Runnable()
		{
			@Override
			public void run()
			{
				ManageAttributes.this.callDeleteService(false);
			}
		})
		.withNoButton()
		.withCaption(I18NManager.getI18NString(ManageAttributes.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"))
		.withHtmlMessage(I18NManager.getI18NString(ManageAttributes.this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "ask_delete_attribute"))
		.open();
	}


	private void selection_gridHandler(SelectionEvent<AttributeDTO> e)
	{
		this.updateButtons(true);
	}


	private void dgdItem_clickHandler(ItemClick<AttributeDTO> e)
	{
		if (e.getMouseEventDetails().isDoubleClick() && e.getItem() != null)
		{
			this.btn_EditAttribute_clickHandler(null);
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
		this.datGrid_Attributes.addCustomColumn(AttributeDTO::getInternalname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_internal_name"), true)
		.setId(INTERNAL_NAME)
		.setWidth(180);
		
		this.datGrid_Attributes.addCustomColumn(AttributeDTO::getVendorname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_vendor_name"), true)
		.setId(VENDOR_NAME)
		.setWidth(220);
		
		this.datGrid_Attributes.addCustomColumn(AttributeDTO::getDatatypename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_type_name"), true)
		.setId(ATTRIBUTE_TYPE_NAME)
		.setWidth(130);

		String active = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_active");
		String inactive = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_inactive");
		
		this.datGrid_Attributes.addCustomColumn(a -> a.getActive() ? active : inactive, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_state"), true)
		.setId(ACTIVE)
		.setWidth(100);
		
		this.datGrid_Attributes.addCustomColumn(AttributeDTO::getUserhelp, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_user_help"), true).setId(USER_HELP);
		
		this.datGrid_Attributes.addCustomColumn(AttributeDTO::getClientinternalname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_client"), true)
		.setId(CLIENT_NAME)
		.setWidth(200);
	}


	private void updateButtons(Boolean isSelectionEvent)
	{
		this.btn_EditAttribute.setEnabled(this.datGrid_Attributes.getSelectedItems() != null && this.datGrid_Attributes.getSelectedItems().size() == 1);
		this.btn_DeleteAttributes.setEnabled(this.datGrid_Attributes.getSelectedItems() != null && !this.datGrid_Attributes.getSelectedItems().isEmpty());
		this.btn_DownloadAttributesGrp.setEnabled(initParam != null);
		this.btn_UploadAttributeFile.setEnabled(initParam != null);
		// this.btn_UploadAttributeTranslation.setEnabled(initParam != null);
		// this.btn_UploadAttributesGrp.setEnabled(this.btn_UploadAttributeTranslation.isEnabled() || this.btn_UploadAttributeFile.isEnabled());
		this.btn_Refresh.setEnabled(initParam != null);
		this.btn_AddAttribute.setEnabled(initParam != null);
	}


	private AttributeArrayResultDTO executeService(BbrUI bbrUI)
	{
		AttributeArrayResultDTO result = null;

		if (this.initParam != null)
		{
			Integer requestedPage = (!resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;

			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();
				result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUI).getAttributes(this.initParam,
				requestedPage,
				this.MAX_ROWS_NUMBER,
				resetPageInfo,
				orderCriteria,
				this.getBbrUIParent().getLocale().getLanguage());
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


	private BaseResultDTO executeDeleteService(BbrUI bbrUIParent, ArrayList<AttributeDTO> selectedResources, boolean force)
	{
		BaseResultDTO result = null;
		if (selectedResources != null && selectedResources.size() > 0)
		{
			AttributeDTO[] attributes = new AttributeDTO[selectedResources.size()];
			attributes = selectedResources.toArray(attributes);
			try
			{
				result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUIParent).removeAttributes(attributes, force, this.getBbrUIParent().getUser().getFullName());
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


	protected FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton, DateTypeRangeSelectionInfo rangeInfo)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				LocalDateTime localDateTime = LocalDateTime.now();
				int day = localDateTime.getDayOfMonth();
				int month = localDateTime.getMonth().getValue();
				int year = localDateTime.getYear();
				int hour = localDateTime.getHour();
				int minute = localDateTime.getMinute();
				int second = localDateTime.getSecond();
				String filename = "AtributosProductosInfo_" + day + "-" + month + "-" + year + "_" + hour + "." + minute + "." + second;
				if (this.btn_DownloadTriggerButton == this.btn_DownloadAttributes)
				{
					fileResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUIParent).downloadAttributes(this.initParam,
					this.orderCriteria,
					filename,
					bbrUIParent.getUser().getId(),
					selectedFormat.getValue(),
					bbrUIParent.getUser().getLocale());
				}
				else if (this.btn_DownloadTriggerButton == this.btn_DownloadTranslation)
				{
					fileResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUIParent).downloadAttributesLanguageData(bbrUIParent.getUser().getId(),
					selectedFormat.getValue(),
					bbrUIParent.getUser().getLocale());
				}
				else if (this.btn_DownloadTriggerButton == this.btn_DownloadBaseFile)
				{
					fileResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUIParent).downloadAttributesFileBase(bbrUIParent.getUser().getId(),
					bbrUIParent.getUser().getLocale());
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


	protected FileDownloadInfoResultDTO executeDownloadBaseService(BbrUI bbrUIParent)
	{
		FileDownloadInfoResultDTO fileResult = null;

		try
		{
			fileResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUIParent).downloadAttributesFileBase(bbrUIParent.getUser().getId(),
			bbrUIParent.getUser().getLocale());
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

		return fileResult;
	}


	private ErrorInfoArrayResultDTO executeUploadInfoService(BbrUI bbrUIParent, String filename)
	{
		ErrorInfoArrayResultDTO result = null;
		try
		{
			result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUIParent).uploadAttributesLanguageData(filename,
			this.getBbrUIParent().getUser().getId(),
			this.getBbrUIParent().getLocale());
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


	private AttributeArrayResultDTO executeUploadFileService(BbrUI bbrUIParent, String filename)
	{
		AttributeArrayResultDTO result = null;

		try
		{
			result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUIParent).uploadNewAttributes(filename,
			this.getBbrUIParent().getUser().getId(),
			this.getBbrUIParent().getLocale(),
			this.type);
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


	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		ManageAttributes senderReport = (ManageAttributes) sender;

		if (result != null)
		{
			AttributeArrayResultDTO reportResult = (AttributeArrayResultDTO) result;
			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), false);

			senderReport.updateButtons(false);

			if (!error.hasError())
			{
				AttributeDTO[] data = (reportResult.getValues() != null) ? reportResult.getValues() : new AttributeDTO[0];

				ListDataProvider<AttributeDTO> dataprovider = new ListDataProvider<>(Arrays.asList(data));

				senderReport.datGrid_Attributes.setDataProvider(dataprovider, senderReport.resetPageInfo);

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
			}
			else if (reportResult.getValues() == null || reportResult.getValues().length == 0)
			{
				AttributeDTO[] data = new AttributeDTO[0];
				ListDataProvider<AttributeDTO> dataprovider = new ListDataProvider<>(Arrays.asList(data));
				senderReport.datGrid_Attributes.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.datGrid_Attributes.getDataProvider().refreshAll();
				
				senderReport.askToOpenFilter(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
				I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "empty_filter_question"));
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


	private void doDeleteReport(Object result, BbrWorkExecutor sender)
	{
		ManageAttributes senderReport = (ManageAttributes) sender;

		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), false);

			this.stopWaiting();

			if (!error.hasError())
			{
				senderReport.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
				I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "successful_operation"));

				senderReport.startWaiting();

				senderReport.trackReport = false;
				senderReport.resetPageInfo = true;
				senderReport.executeBbrWork(reportWork);
			}
			else if (error.getErrorCode().equals("M120"))
			{
				BbrMessageBox.createQuestion(ManageAttributes.this.getBbrUIParent())
				.withYesButton(new Runnable()
				{
					@Override
					public void run()
					{
						ManageAttributes.this.callDeleteService(true);
					}
				})
				.withNoButton()
				.withCaption(I18NManager.getI18NString(ManageAttributes.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"))
				.withHtmlMessage(I18NManager.getI18NString(ManageAttributes.this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "ask_delete_attribute_forcely"))
				.open();

			}
			else
			{
				this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"), error.getErrorMessage());
			}
		}
	}


	private void reportUploaded(Object result, BbrWorkExecutor sender)
	{
		ManageAttributes senderReport = (ManageAttributes) sender;

		if (result != null)
		{
			ErrorInfoArrayResultDTO reportResult = (ErrorInfoArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), true);

			if (!error.hasError())
			{
				this.executeBbrWork(reportWork);
				this.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
				I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_COMMERCIAL, "file_processed"));
			}
			else
			{
				BbrError errorMSG = ErrorsMngr.getInstance().getError(reportResult, null, false);

				this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"), errorMSG.getErrorMessage());
			}

		}
		senderReport.stopWaiting();
	}


	private void attributesUploaded(Object result, BbrWorkExecutor sender)
	{
		ManageAttributes senderReport = (ManageAttributes) sender;

		if (result != null)
		{
			AttributeArrayResultDTO reportResult = (AttributeArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), false);

			if (!error.hasError())
			{
				this.executeBbrWork(reportWork);
				this.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
				I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_COMMERCIAL, "file_processed"));
			}
			else
			{
				if((reportResult.getErrors() != null) && (reportResult.getErrors().length > 1))
				{
					BbrErrorList<BaseResultDTO> winErrors = new BbrErrorList<>(senderReport.getBbrUIParent(), reportResult.getErrors(),
					senderReport.getUser().getLocale());
					
					winErrors.setCloseButtonStyle("btn-generic");
					winErrors.setConverterFunction(new Function<BaseResultDTO, BbrError>()
					{
						@Override
						public BbrError apply(BaseResultDTO t)
						{
							BbrError result = new BbrError(t.getStatuscode(), t.getStatusmessage());
							return result;
						}
					});
					winErrors.setWidth("350px");
					winErrors.setHeight("350px");
					winErrors.open(true, true, this);
				}
				else
				{
					BbrError errorMSG = ErrorsMngr.getInstance().getError(reportResult, null, false);

					this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"), errorMSG.getErrorMessage());
				}
			}
		}
		senderReport.stopWaiting();
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

			GridSortOrder<AttributeDTO> sortOrder = new GridSortOrder<>(datGrid_Attributes.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<AttributeDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_Attributes.setSortOrder(sortOrderList);
		}
	}


	private LanguageArrayResultDTO getAllLanguages()
	{
		LanguageArrayResultDTO result = null;

		try
		{
			// Start Tracking
			result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getLanguages();
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

		return result;
	}


	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ManageAttributes senderReport = (ManageAttributes) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}


	private void callDeleteService(boolean force)
	{
		if ((this.datGrid_Attributes.getSelectedItems() != null) && (this.datGrid_Attributes.getSelectedItems().size() > 0))
		{
			ArrayList<AttributeDTO> selectedResource = new ArrayList<>();
			selectedResource.addAll(this.datGrid_Attributes.getSelectedItems());

			deleteResourceWork = new BbrWork<>(ManageAttributes.this, ManageAttributes.this.getBbrUIParent(), btn_DeleteAttributes);
			deleteResourceWork.addFunction(new Function<Object, BaseResultDTO>()
			{
				@Override
				public BaseResultDTO apply(Object t)
				{
					return executeDeleteService(ManageAttributes.this.getBbrUIParent(), selectedResource, force);
				}
			});

			this.startWaiting();

			this.executeBbrWork(deleteResourceWork);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
}
