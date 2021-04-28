package bbr.b2b.portal.components.modules.users.management.notifications;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.constants.BbrPublishingConstants;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.classes.utils.app.SelectedPopupUtils;
import bbr.b2b.portal.classes.wrappers.management.NotificationDatesPanelInfo;
import bbr.b2b.portal.classes.wrappers.management.NotificationInfo;
import bbr.b2b.portal.classes.wrappers.management.PopupInfo;
import bbr.b2b.portal.components.basics.BbrButtonGroup;
import bbr.b2b.portal.components.basics.BbrButtonGroup.BbrGroupButtonBuilder;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.basics.NotificationDatesPanel;
import bbr.b2b.portal.components.modules.management.SelectFunctionality;
import bbr.b2b.portal.components.popups.WinPopupPreview;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.AddPopUpInitParamsDTO;
import bbr.b2b.users.report.classes.PopUpBehaviorDTO;
import bbr.b2b.users.report.classes.PopUpBehaviorResultDTO;
import bbr.b2b.users.report.classes.PopUpReportDataDTO;
import bbr.b2b.users.report.classes.PopUpResultDTO;
import bbr.b2b.users.report.classes.PopUpTemplateDTO;
import bbr.b2b.users.report.classes.PopUpTemplateResultDTO;
import bbr.b2b.users.report.classes.PopUpTypeDTO;
import bbr.b2b.users.report.classes.PopUpTypeResultDTO;
import bbr.b2b.users.report.classes.PublishingTypeDTO;
import bbr.b2b.users.report.classes.RuleTypeDTO;
import bbr.b2b.users.report.classes.RuleTypesResultDTO;
import bbr.b2b.users.report.classes.UpdatePopUpInitParamsDTO;
import bbr.b2b.users.report.classes.UserTreeFunctionalityDTO;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.files_transfer.BbrFileUploader;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrItemSelectField;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class CreateEditPopupNotification extends BbrWindow implements BbrWorkExecutor
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Constants
	private static final long								serialVersionUID			= 3247626779164695209L;
	private static final String								STYLE_IMAGE_CONTENT_DEFAULT	= "bbr-overflow-auto override center-content";
	private static final String								STYLE_BG_COLOR_DEFAULT		= "bg-color-gray";
	// Components
	private CKEditorConfig									config						= null;
	private String											attachmentPath				= null;
	private CKEditorTextField								ckEditorTextField			= null;
	private BbrComboBox<RuleTypeDTO>						cbx_Recipient				= null;
	private BbrTextField									txt_Title					= null;
	private BbrTextField									txt_Link					= null;
	private BbrComboBox<PopUpBehaviorDTO>					cbx_PopUpsBehaviors			= null;
	private BbrComboBox<PopUpTypeDTO>						cbx_PopUpsTypes				= null;
	private BbrComboBox<PopUpTemplateDTO>					cbx_PopUpsTemplates			= null;
	private BbrHInputFieldContainer							pnlLinkContainer			= null;
	private BbrItemSelectField<UserTreeFunctionalityDTO>	selectFunctionality			= null;
	private Upload											btn_UploadImage				= null;
	private VerticalLayout									imageContent				= null;
	private HorizontalLayout								imageContentContainer		= null;
	private BbrHInputFieldContainer							pnlUploadImage				= null;
	private BbrHInputFieldContainer							pnlBodyEditor				= null;
	// Variables
	private PublishingTypeDTO								publishingType				= null;
	private RuleTypeDTO[]									rulesTypes					= null;
	private List<PopUpBehaviorDTO>							popUpBehaviors				= new ArrayList<>();
	private PopUpTypeDTO[]									popUpTypes					= null;
	private PopUpTypeDTO									popUpTypeSelected			= null;
	private PopUpTemplateDTO[]								popUpTemplates				= null;
	private PopUpReportDataDTO								selectedNotification		= null;
	private NotificationDatesPanelInfo						notificationDatesPanelInfo	= null;
	private Boolean											isActive					= false;
	private BbrFileUploader									uploaderReceiver			= null;
	private Button											btn_DeleteImage				= null;
	private PopUpTemplateDTO								popUpTemplateSelected		= null;
	private File											newfile						= null;
	private SelectedPopupUtils								selectedPopupUtils			= new SelectedPopupUtils();
	private BbrWork<PopUpResultDTO>							createEditWork				= null;
	private BbrWork<PopUpResultDTO>							addItemWork					= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public CreateEditPopupNotification(BbrUI parent, PopUpReportDataDTO selectedNotification)
	{
		super(parent);
		this.selectedNotification = selectedNotification;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		this.publishingType = (PublishingTypeDTO) this.getData();
		if (this.publishingType != null)
		{
			if (!isEdit())
			{
				PopUpReportDataDTO defaultPopup = new PopUpReportDataDTO();
				defaultPopup.setPopuptemplatecode(BbrPublishingConstants.POPUP_TEMPLATE_1);
				defaultPopup.setCpkey(1L);
				this.selectedPopupUtils.setNotification(defaultPopup);
			}
			else
			{
				this.selectedPopupUtils.setNotification(selectedNotification);
			}

			// pnlDates
			NotificationDatesPanel notificationDatesPanel = new NotificationDatesPanel(this.getBbrUIParent(), this.getStartDate(), this.getEndDate());
			this.notificationDatesPanelInfo = notificationDatesPanel.getNotificationDatesPanelInfo();
			Panel pnlDates = this.notificationDatesPanelInfo.getPnlDates();

			// pnlPublishing
			Panel pnlPublishing = new Panel(this.publishingType.getName());
			pnlPublishing.addStyleName("bbr-panel-space");
			pnlPublishing.setSizeFull();
			pnlPublishing.setContent(this.getPublishingLayout());

			// buttons
			BbrButtonGroup bbrGroupButton = new BbrGroupButtonBuilder(this.getBbrUIParent())
					.withPrimaryButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"))
					.withPrimaryButtonStyle("primary btn-login")
					.withPrimaryButtonListener((ClickListener & Serializable) (e) -> this.btnCreateOrEdit_clickHandler(e))
					.withSecundaryButton(true)
					.withSecundaryButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"))
					.withSecundaryButtonStyle("primary btn-login")
					.withSecundaryButtonListener((ClickListener & Serializable) (e) -> this.btnClose_clickHandler(e))
					.withCancelButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "preview"))
					.withCancelButtonStyle("primary btn-login")
					.withCancelButtonListener((ClickListener & Serializable) (e) -> this.btnPreview_clickHandler(e))
					.build();
			bbrGroupButton.addStyleName("bbr-buttons-panel");

			// Vertical Layout for Components
			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.addComponents(pnlDates, pnlPublishing, bbrGroupButton);
			mainLayout.setExpandRatio(pnlPublishing, 1F);
			mainLayout.setComponentAlignment(bbrGroupButton, Alignment.BOTTOM_CENTER);
			mainLayout.setSizeFull();

			mainLayout.addStyleName("bbr-win-container");

			// Main Windows
			this.setWidth("1000px");
			this.setHeight("600px");
			this.setResizable(false);
			this.setContent(mainLayout);

			if (!this.isEdit())
			{

				// Crear Notificación
				this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "create_notification_win_title", publishingType.getName()));
			}
			else
			{
				// Editar Notificación
				this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_notification", publishingType.getName()));

				this.notificationDatesPanelInfo.getDfSinceDate().setValue(this.selectedNotification.getInitdate());
				this.notificationDatesPanelInfo.getDfUntilDate().setValue(this.selectedNotification.getEnddate());
				this.txt_Title.setValue(this.selectedNotification.getTitle());
				this.cbx_Recipient.setSelectedItem(getRulebyId(this.selectedNotification.getRuleid()));
				// Al editar se selecciona el comportamiento definido cpkey
				this.cbx_PopUpsBehaviors.setSelectedItem(getBehaviorId(this.selectedNotification.getCpkey()));
				this.cbx_PopUpsTemplates.setSelectedItem(getTemplateById(this.selectedNotification.getPopuptemplateid()));
				this.cbx_PopUpsTypes.setSelectedItem(getTypeByCode(this.selectedNotification.getPopuptypecode()));

				// template
				if (this.selectedPopupUtils.isPopupTemplate1())
				{
					this.ckEditorTextField.setValue(this.selectedNotification.getHtmlcontent());
				}
				else if (this.selectedPopupUtils.isPopupTemplateImage())
				{
					this.loadImageFromSelected(this.selectedNotification);
				}
				//
				UserTreeFunctionalityDTO functionality = FunctionalityMngr.getInstance().getUserFunctionalityById(selectedNotification.getModuleid());
				this.selectFunctionality.setValue(functionality);
				this.cbx_Recipient.setEnabled(false);

				// link
				if (this.selectedPopupUtils.isLinkAvailable())
				{
					this.txt_Link.setValue(this.selectedNotification.getLink());
				}
			}

		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		CreateEditPopupNotification senderReport = (CreateEditPopupNotification) sender;
		if (result != null)
		{
			if (result instanceof PopUpBehaviorResultDTO)
			{
				PopUpBehaviorResultDTO popUpBehaviorResult = (PopUpBehaviorResultDTO) result;
				senderReport.getItemsPopupBehaviourResult(senderReport.popUpBehaviors, popUpBehaviorResult);
			}
			else if (triggerObject.equals(BbrEvent.ITEM_UPDATED))
			{
				PopUpResultDTO resultItem = (PopUpResultDTO) result;
				BbrEvent createEvent = new BbrEvent(BbrEvent.ITEM_UPDATED);
				createEvent.setResultObject((resultItem != null) ? resultItem.getPopup().getId() : null);
				this.dispatchBbrEvent(createEvent);
				this.stopWaiting();
				this.close();
			}
			else if (triggerObject.equals(BbrEvent.ITEM_CREATED))
			{
				PopUpResultDTO resultItem = (PopUpResultDTO) result;
				BbrEvent createEvent = new BbrEvent(BbrEvent.ITEM_CREATED);
				createEvent.setResultObject((resultItem != null) ? resultItem.getPopup().getId() : null);
				this.dispatchBbrEvent(createEvent);
				this.stopWaiting();
				this.close();
			}
		}
		else
		{
			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}

	}

	private List<PopUpBehaviorDTO> getItemsPopupBehaviourResult(List<PopUpBehaviorDTO> popUpBehaviors, PopUpBehaviorResultDTO result)
	{
		// validateResultDTO
		if (result != null)
		{
			popUpBehaviors = Arrays.asList(result.getPopupbehaviors());
		}
		this.stopWaiting();
		return popUpBehaviors;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void btnClose_clickHandler(ClickEvent e)
	{
		this.close();
	}

	private void btnPreview_clickHandler(ClickEvent e)
	{
		this.showPreview();
	}

	private void btnCreateOrEdit_clickHandler(ClickEvent e)
	{
		PopupInfo item = this.initializePopupInfoItem();
		String message = item.validateEditData();
		if (message != null && message.length() > 0)
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
		}
		else
		{
			if (this.isEdit())
			{
				// Edit
				item.setPublishingId(this.selectedNotification.getPukey());
				createEditWork = new BbrWork<>(this, this.getBbrUIParent(), BbrEvent.ITEM_UPDATED);
				createEditWork.addFunction(new Function<Object, PopUpResultDTO>()
				{
					@Override
					public PopUpResultDTO apply(Object t)
					{
						return executeCreateEditService(CreateEditPopupNotification.this.getBbrUIParent(), item);
					}
				});
				this.startWaiting();
				this.executeBbrWork(createEditWork);
			}
			else
			{
				// Create
				BbrMessagesBoxUtils.showYesNoQuestionMessage(this.getBbrUIParent(),
						I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_question"),
						I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "wish_activate_notification"),
						() -> this.create_SetActive_Handler(item, true),
						() -> this.create_SetActive_Handler(item, false));

			}

		}
	}

	private PopupInfo initializePopupInfoItem()
	{
		this.selectedPopupUtils.setPopUpTemplate(this.cbx_PopUpsTemplates.getSelectedValue());
		if (this.selectedPopupUtils.isPopupTemplateImage())
		{
			this.attachmentPath = (this.newfile != null) ? this.newfile.getPath()
					: (this.isEdit()) ? this.selectedNotification.getHtmlcontent() : "";
		}
		else
		{
			this.attachmentPath = this.ckEditorTextField.getValue();
		}

		PopupInfo notificationInfo = new PopupInfo(this.getUser(),
				this.txt_Title.getValue().trim(),
				this.notificationDatesPanelInfo.getDfSinceDate().getValue(),
				this.notificationDatesPanelInfo.getDfUntilDate().getValue(),
				this.attachmentPath,
				null,
				this.publishingType.getId(),
				this.cbx_Recipient.getSelectedValue(),
				this.selectFunctionality.getValue(),
				this.cbx_PopUpsBehaviors.getSelectedValue(),
				this.txt_Link.getValue(),
				this.isActive,
				this.cbx_PopUpsTemplates.getSelectedValue(),
				this.cbx_PopUpsTypes.getSelectedValue());
		return notificationInfo;
	}

	private void create_SetActive_Handler(PopupInfo item, boolean activate)
	{
		this.isActive = activate;
		item.setIsActive(this.isActive);
		String message = item.validateEditData();
		if (message != null && message.length() > 0)
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
		}
		else
		{
			// this.doCreateNotification(item);
			addItemWork = new BbrWork<>(this, this.getBbrUIParent(), BbrEvent.ITEM_CREATED);
			addItemWork.addFunction(new Function<Object, PopUpResultDTO>()
			{
				@Override
				public PopUpResultDTO apply(Object t)
				{
					return executeAddItemService(CreateEditPopupNotification.this.getBbrUIParent(), item);
				}
			});
			this.startWaiting();
			this.executeBbrWork(addItemWork);
		}
	}

	private void functionalityEnterprise_handler(BbrEvent e)
	{
		SelectFunctionality winSelect = new SelectFunctionality(this.getBbrUIParent());
		winSelect.initializeView();
		winSelect.addBbrEventListener((BbrEventListener & Serializable) event -> companySelected_handler(event));
		winSelect.open(true);
	}

	private void companySelected_handler(BbrEvent e)
	{
		UserTreeFunctionalityDTO functionality = (UserTreeFunctionalityDTO) e.getResultObject();
		this.selectFunctionality.setValue(functionality);

	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private PopUpResultDTO executeCreateEditService(BbrUI bbrUIParent, PopupInfo item)
	{
		PopUpResultDTO result = null;
		try
		{
			if (item != null)
			{
				UpdatePopUpInitParamsDTO initParams = item.toUpdatePopUpInitParamsDTO();

				result = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(bbrUIParent).doUpdatePopUp(initParams,
						bbrUIParent.getUser().getId());
			}
		}
		catch (BbrUserException ex)
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), bbrUIParent);
		}
		catch (BbrSystemException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return result;
	}

	private PopUpResultDTO executeAddItemService(BbrUI bbrUIParent, PopupInfo item)
	{
		PopUpResultDTO result = null;
		try
		{
			if (item != null)
			{
				AddPopUpInitParamsDTO initParams = item.toAddPopUpInitParamsDTO();

				result = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doAddPopUp(initParams,
						this.getUser().getId());
			}
		}
		catch (BbrUserException ex)
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), bbrUIParent);
		}
		catch (BbrSystemException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return result;
	}

	private void loadImageFromSelected(PopUpReportDataDTO selectedNotification)
	{
		if (selectedPopupUtils.isHtmlcontentTemplateImage())
		{
			try
			{
				if (selectedPopupUtils.isValidHtmlPathForImage())
				{
					Image image = this.getImageFromHtmlcontent(selectedNotification.getHtmlcontent());

					this.updateImageContent(image);
				}
				else
				{
					this.setDefaultImage();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private HorizontalLayout getPublishingLayout()
	{
		// Suscriptores
		this.cbx_Recipient = this.getRecipientsComboBox();
		this.cbx_Recipient.addStyleName("bbr-fields");
		BbrHInputFieldContainer pnlNotificactionsTypesContainer = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "to"))
				.withLabelWidth("80px")
				.withComponent(this.cbx_Recipient)
				.build();

		// Title
		this.txt_Title = new BbrTextField();
		this.txt_Title.setMaxLength(50);
		this.txt_Title.addStyleName("bbr-fields");
		BbrHInputFieldContainer pnlTitleContainer = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "publishing_title"))
				.withLabelWidth("80px")
				.withComponent(this.txt_Title)
				.build();

		// Modules
		this.selectFunctionality = new BbrItemSelectField<UserTreeFunctionalityDTO>("");
		this.selectFunctionality.addBbrEventListener((BbrEventListener & Serializable) e -> this.functionalityEnterprise_handler(e));
		this.selectFunctionality.setFieldName("description");
		this.selectFunctionality.setDescriptionName("description");
		this.selectFunctionality.setHeight("30px");
		this.selectFunctionality.addStyleName("bbr-fields");
		BbrHInputFieldContainer pnlSelectFunctionalityContainer = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "module"))
				.withLabelWidth("110px")
				.withComponent(this.selectFunctionality)
				.build();

		// PopUp Template
		this.cbx_PopUpsTemplates = this.getTemplatesComboBox();
		this.cbx_PopUpsTemplates.setWidth("100%");
		this.cbx_PopUpsTemplates.addStyleName("bbr-fields");
		BbrHInputFieldContainer pnlPopUpsTemplatesContainer = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "module_template"))
				.withLabelWidth("110px")
				.withComponent(this.cbx_PopUpsTemplates)
				.build();

		// PopUp Types
		this.cbx_PopUpsTypes = this.getTypeComboBox();
		this.cbx_PopUpsTypes.setWidth("100%");
		this.cbx_PopUpsTypes.addStyleName("bbr-fields");
		BbrHInputFieldContainer pnlPopUpsTypesContainer = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "module_types"))
				.withLabelWidth("110px")
				.withComponent(this.cbx_PopUpsTypes)
				.build();

		// PopUp Behavior
		// Al editar inicializan comportamientos dependiendo del tipo(tpokey)
		this.cbx_PopUpsBehaviors = this.getPopupBehaviourComboBox(!this.isEdit() ? 1L : this.selectedNotification.getTpokey());
		BbrHInputFieldContainer pnlPopUpsBehaviorsContainer = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "module_behavior"))
				.withLabelWidth("110px")
				.withComponent(this.cbx_PopUpsBehaviors)
				.build();

		// Link
		this.txt_Link = new BbrTextField();
		this.txt_Link.addStyleName("bbr-fields");
		this.txt_Link.setPlaceholder(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "example_link_placeholder"));
		this.pnlLinkContainer = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "link"))
				.withLabelWidth("110px")
				.withComponent(this.txt_Link)
				.build();
		this.pnlLinkContainer.setEnabled(false);

		// Body
		this.config = new CKEditorConfig();
		this.config.useCompactTags();
		this.config.disableElementsPath();
		this.config.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
		this.config.disableSpellChecker();
		this.config.setWidth("100%");
		this.config.setHeight("100%");
		// RUTAS UTILIZADAS EN CREAR POPUP
		this.config.setFilebrowserUploadUrl(BbrPublishingConstants.FILE_UPLOAD_URL);
		this.config.setFilebrowserImageUploadUrl(BbrPublishingConstants.IMAGE_UPLOAD_URL);

		this.ckEditorTextField = new CKEditorTextField(this.config);
		this.ckEditorTextField.setHeight("100%");

		this.pnlBodyEditor = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "body"))
				.withLabelWidth("60px")
				.withComponent(this.ckEditorTextField)
				.build();
		this.pnlBodyEditor.addStyleName("bbr-panel-space");

		// Upload Button
		this.uploaderReceiver = new BbrFileUploader(BbrAppConstants.getUploadPathOfUser(this.getUser().getId()));
		this.btn_UploadImage = new Upload(null, this.uploaderReceiver);
		this.btn_UploadImage.setWidth("150px");
		this.btn_UploadImage.setButtonCaption("Escoger imagen");
		this.btn_UploadImage.setButtonStyleName("popup-upload-image-button btn-generic");
		this.btn_UploadImage.addFinishedListener((FinishedListener & Serializable) (e) -> this.uploadImage_FinishedHandler(e));

		this.btn_DeleteImage = new Button("", EnumToolbarButton.DELETE.getResource());
		this.btn_DeleteImage.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "delete_notification"));
		this.btn_DeleteImage.addClickListener((ClickListener & Serializable) e -> this.deleteImages_clickHandler(e));
		this.btn_DeleteImage.addStyleName("toolbar-button");
		this.btn_DeleteImage.setEnabled(false);

		this.imageContentContainer = new HorizontalLayout();
		this.imageContentContainer.setSizeFull();
		this.imageContentContainer.setMargin(false);
		this.imageContentContainer.setSpacing(false);
		this.setDefaultImage();

		HorizontalLayout buttonsToolbar = new HorizontalLayout(this.btn_UploadImage, this.btn_DeleteImage);
		buttonsToolbar.setHeight("40px");
		buttonsToolbar.setWidth("100%");
		buttonsToolbar.setMargin(false);
		buttonsToolbar.setSpacing(true);
		buttonsToolbar.setComponentAlignment(this.btn_UploadImage, Alignment.TOP_RIGHT);
		buttonsToolbar.setExpandRatio(this.btn_UploadImage, 1);

		this.imageContent = new VerticalLayout(buttonsToolbar, this.imageContentContainer);
		this.imageContent.setSizeFull();
		this.imageContent.setMargin(false);
		this.imageContent.setSpacing(false);
		this.imageContent.setComponentAlignment(buttonsToolbar, Alignment.TOP_CENTER);
		this.imageContent.setExpandRatio(this.imageContentContainer, 1);

		this.pnlUploadImage = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "image_body"))
				.withLabelWidth("60px")
				.withComponent(this.imageContent)
				.build();
		this.pnlUploadImage.setVisible(false);
		this.pnlUploadImage.setComponentAlignment(this.imageContent, Alignment.TOP_CENTER);

		if (this.isEdit())
		{
			pnlPopUpsTypesContainer.setEnabled(false);
			pnlPopUpsBehaviorsContainer.setEnabled(false);
		}

		VerticalLayout leftPanel = new VerticalLayout();
		leftPanel.addComponents(pnlNotificactionsTypesContainer,
				pnlTitleContainer,
				pnlSelectFunctionalityContainer,
				pnlPopUpsTemplatesContainer,
				pnlPopUpsTypesContainer,
				pnlPopUpsBehaviorsContainer,
				this.pnlLinkContainer);
		leftPanel.setWidth("300px");
		leftPanel.setMargin(false);

		VerticalLayout rightPanel = new VerticalLayout();
		rightPanel.addComponents(this.pnlUploadImage);
		rightPanel.addComponents(this.pnlBodyEditor);
		rightPanel.setHeight("100%");
		rightPanel.setMargin(false);

		HorizontalLayout result = new HorizontalLayout();
		result.addStyleName("bbr-win-container");
		result.addComponents(leftPanel, rightPanel);
		result.setHeight("100%");
		result.setWidth("100%");
		result.setSpacing(true);
		result.setMargin(false);
		result.setExpandRatio(rightPanel, 1F);
		result.markAsDirtyRecursive();

		return result;

	}

	private boolean isEdit()
	{
		return (this.selectedNotification != null);
	}

	private void deleteImages_clickHandler(ClickEvent e)
	{
		if (this.imageContentContainer != null && this.imageContentContainer.getComponentCount() > 0)
		{
			this.imageContentContainer.removeAllComponents();
			this.btn_DeleteImage.setEnabled(false);
			this.setDefaultImage();
		}
	}

	private BbrComboBox<PopUpBehaviorDTO> getPopupBehaviourComboBox(Long poupuptypeid)
	{
		PopUpBehaviorResultDTO popUpBehaviorResult = this.executePopupBehaviourService(this.getBbrUIParent(), poupuptypeid);
		List<PopUpBehaviorDTO> items = this.getItemsPopupBehaviourResult(Arrays.asList(popUpBehaviorResult.getPopupbehaviors()), popUpBehaviorResult);
		this.popUpBehaviors = items;
		return this.initializePopupBehaviourComboBox(items);
	}

	private List<PopUpBehaviorDTO> getItemsPopupBehaviourByType(Long poupuptypeid)
	{
		PopUpBehaviorResultDTO popUpBehaviorResult = this.executePopupBehaviourService(this.getBbrUIParent(), poupuptypeid);
		List<PopUpBehaviorDTO> items = new ArrayList<>();
		items = this.getItemsPopupBehaviourResult(Arrays.asList(popUpBehaviorResult.getPopupbehaviors()), popUpBehaviorResult);
		return items;
	}

	private void uploadImage_FinishedHandler(FinishedEvent e)
	{
		String filename = e.getFilename();
		String directory = BbrAppConstants.getUploadPathOfUser(this.getUser().getId());
		newfile = createImageOfContent(directory, filename);
	}

	private File createImageOfContent(String directory, String filename)
	{
		File newfile = (filename != null && directory != null) ? new File(directory, filename) : null;

		if (newfile != null && newfile.canRead())
		{
			Resource resFile = (newfile != null) ? new FileResource(newfile) : null;
			Image image = new Image(null, resFile);
			if (image != null)
			{
				updateImageContent(image);
			}
		}
		return newfile;
	}

	private void setDefaultImage()
	{
		Resource res = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), BbrUtilsResources.PATH_BASE_IMAGES_BASIC + "default_insert_image.png");
		Image image = new Image(null, res);
		updateImageContent(image);
		this.imageContentContainer.addStyleName(STYLE_BG_COLOR_DEFAULT);
	}

	private void updateImageContent(Image image)
	{
		this.imageContentContainer.removeAllComponents();
		this.imageContentContainer.addComponent(image);
		this.btn_DeleteImage.setEnabled(true);
		this.imageContentContainer.setStyleName(STYLE_IMAGE_CONTENT_DEFAULT);
	}

	private RuleTypeDTO getRulebyId(Long ruleId)
	{
		RuleTypeDTO result = null;
		for (RuleTypeDTO rule : this.rulesTypes)
		{
			if (rule.getId().equals(ruleId))
			{
				result = rule;
				break;
			}
		}

		return result;
	}

	private PopUpBehaviorDTO getBehaviorId(Long behaviorId)
	{
		PopUpBehaviorDTO result = null;
		for (PopUpBehaviorDTO behavior : this.popUpBehaviors)
		{
			if (behavior.getId().equals(behaviorId))
			{
				result = behavior;
				break;
			}
		}

		return result;
	}

	private PopUpTemplateDTO getTemplateById(Long id)
	{
		PopUpTemplateDTO result = null;
		for (PopUpTemplateDTO templates : this.popUpTemplates)
		{
			if (templates.getId().equals(id))
			{
				result = templates;
				break;
			}
		}

		return result;
	}

	private PopUpTypeDTO getTypeByCode(String code)
	{
		PopUpTypeDTO result = null;
		for (PopUpTypeDTO type : this.popUpTypes)
		{
			if (type.getCode().equals(code))
			{
				result = type;
				break;
			}
		}

		return result;
	}

	private BbrComboBox<RuleTypeDTO> getRecipientsComboBox()
	{
		BbrComboBox<RuleTypeDTO> result = null;

		try
		{
			if (this.rulesTypes == null || this.rulesTypes.length == 0)
			{
				RuleTypesResultDTO rulesTypesResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).getRuleTypes();

				if (rulesTypesResult.getRuletypes() != null)
				{

					this.rulesTypes = rulesTypesResult.getRuletypes();
				}
			}

			result = new BbrComboBox<RuleTypeDTO>(this.rulesTypes);
			result.setPlaceholder(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_option"));
			result.setItemCaptionGenerator(RuleTypeDTO::getDescription);
			result.setTextInputAllowed(false);
			result.setEmptySelectionAllowed(false);

			result.setWidth("100%");
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

	private PopUpBehaviorResultDTO executePopupBehaviourService(BbrUI bbrUI, Long poupuptypeid)
	{
		PopUpBehaviorResultDTO result = new PopUpBehaviorResultDTO();
		try
		{
			result = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(bbrUI).getPopUpBehaviorByPopUpType(poupuptypeid);
			BbrError error = ErrorsMngr.getInstance().getError(result, this.getBbrUIParent(), true);
			if (!error.hasError())
			{
				return result;
			}
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), bbrUI);
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

	public BbrComboBox<PopUpBehaviorDTO> initializePopupBehaviourComboBox(List<PopUpBehaviorDTO> popUpBehaviors)
	{
		BbrComboBox<PopUpBehaviorDTO> resultCombobox = new BbrComboBox<PopUpBehaviorDTO>((PopUpBehaviorDTO[]) popUpBehaviors.toArray());
		resultCombobox.setItemCaptionGenerator(PopUpBehaviorDTO::getName);
		resultCombobox.setTextInputAllowed(false);
		resultCombobox.setEmptySelectionAllowed(false);
		resultCombobox.setWidth("100%");
		resultCombobox.addStyleName("bbr-fields");
		if (this.isEdit())
		{
			resultCombobox.setSelectedItem(getBehaviorId(this.selectedNotification.getCpkey()));
		}
		else
		{
			resultCombobox.selectFirstItem();
		}
		resultCombobox.addValueChangeListener((ValueChangeListener<PopUpBehaviorDTO> & Serializable) e -> this.enableOrDisableLinkPnl(e));
		return resultCombobox;
	}

	private BbrComboBox<PopUpTypeDTO> getTypeComboBox()
	{
		BbrComboBox<PopUpTypeDTO> result = new BbrComboBox<>();
		try
		{
			PopUpTypeResultDTO reportResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).getPopUpTypes();
			if (reportResult != null && reportResult.getPopuptype() != null && reportResult.getPopuptype().length > 0)
			{
				this.popUpTypes = reportResult.getPopuptype();

				result = new BbrComboBox<PopUpTypeDTO>(this.popUpTypes);
				result.setItemCaptionGenerator(PopUpTypeDTO::getName);
				result.setTextInputAllowed(false);
				result.setEmptySelectionAllowed(false);
				result.selectFirstItem();
				result.addValueChangeListener((ValueChangeListener<PopUpTypeDTO> & Serializable) e -> this.typeCombobox_ValueChangeHandler(e));

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

		return result;
	}

	private void typeCombobox_ValueChangeHandler(ValueChangeEvent<PopUpTypeDTO> e)
	{
		if (e.getValue() != null)
		{
			this.selectedPopupUtils.setPopUpTemplate(this.popUpTemplateSelected);
			if (this.selectedPopupUtils.isPopupTemplateImage())
			{
				this.pnlLinkContainer.setEnabled(true);
			}
			this.popUpTypeSelected = e.getValue();
			this.popUpBehaviors = this.getItemsPopupBehaviourByType(this.popUpTypeSelected.getId());
			this.cbx_PopUpsBehaviors.removeAllItems();
			this.cbx_PopUpsBehaviors.setItems(this.popUpBehaviors);
			if (this.isEdit())
			{
				this.cbx_PopUpsBehaviors.setSelectedItem(getBehaviorId(this.selectedNotification.getCpkey()));
			}
			else
			{
				this.cbx_PopUpsBehaviors.selectFirstItem();
			}
		}
	}

	private BbrComboBox<PopUpTemplateDTO> getTemplatesComboBox()
	{
		BbrComboBox<PopUpTemplateDTO> result = new BbrComboBox<>();
		try
		{
			PopUpTemplateResultDTO reportResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).getPopUpTemplates();
			if (reportResult != null && reportResult.getPopuptemplate() != null && reportResult.getPopuptemplate().length > 0)
			{
				this.popUpTemplates = reportResult.getPopuptemplate();

				result = new BbrComboBox<PopUpTemplateDTO>(this.popUpTemplates);
				result.setItemCaptionGenerator(PopUpTemplateDTO::getName);
				result.setTextInputAllowed(false);
				result.setEmptySelectionAllowed(false);
				result.selectFirstItem();
				result.addValueChangeListener((ValueChangeListener<PopUpTemplateDTO> & Serializable) e -> this.enableOrDisableBodyPnl(e));

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

		return result;
	}

	private void enableOrDisableLinkPnl(ValueChangeEvent<PopUpBehaviorDTO> e)
	{
		if (cbx_PopUpsBehaviors.getSelectedValue() != null)
		{
			if (cbx_PopUpsBehaviors.getSelectedValue().getCode().equalsIgnoreCase(BbrPublishingConstants.POPUP_MODE_LINK))
			{
				pnlLinkContainer.setEnabled(true);
			}
			else
			{
				if (!((this.popUpTemplateSelected != null) ? this.popUpTemplateSelected.getCode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TEMPLATE_2)
						: this.selectedPopupUtils.isPopupTemplate1()))
				{
					pnlLinkContainer.setEnabled(false);
				}
			}
		}
	}

	private void enableOrDisableBodyPnl(ValueChangeEvent<PopUpTemplateDTO> e)
	{
		if (this.cbx_PopUpsTemplates.getSelectedValue() != null)
		{
			PopUpTemplateDTO actualValue = (PopUpTemplateDTO) e.getValue();
			this.selectedPopupUtils.setPopUpTemplate(actualValue);

			if ((actualValue.getCode()).equals(BbrPublishingConstants.POPUP_TEMPLATE_2))
			{
				this.pnlUploadImage.setVisible(true);
				this.pnlLinkContainer.setEnabled(true);
				this.pnlBodyEditor.setVisible(false);
				this.setDefaultImage();
			}
			else
			{
				this.pnlUploadImage.setVisible(false);
				this.pnlLinkContainer.setEnabled(false);
				this.pnlBodyEditor.setVisible(true);
			}
		}
	}

	private LocalDateTime getStartDate()
	{
		// Mañana a las 08:00 - DSU
		LocalDateTime result = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.HOURS).withHour(8);

		return result;
	}

	private LocalDateTime getEndDate()
	{
		// Último día del mes. - DSU
		LocalDateTime today = LocalDateTime.now();

		LocalDateTime result = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())
				.truncatedTo(ChronoUnit.MINUTES)
				.withMinute(59).withHour(23);

		// Si el último ía es hoy, entonces se tomará el último día del mes
		// siguiente. - DSU
		if (today.getDayOfMonth() == result.getDayOfMonth())
		{
			result = result.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
		}

		return result;
	}

	private void showPreview()
	{
		if (this.cbx_PopUpsBehaviors.getSelectedValue() != null)
		{
			if (!this.cbx_PopUpsBehaviors.getSelectedValue().getCode().equalsIgnoreCase(BbrPublishingConstants.POPUP_MODE_LINK))
			{
				PopUpReportDataDTO popup = new PopUpReportDataDTO();
				if (this.cbx_PopUpsTemplates.getSelectedValue().getCode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TEMPLATE_2))
				{
					this.attachmentPath = (this.newfile != null) ? this.newfile.getPath() : this.isEdit() ? this.selectedNotification.getHtmlcontent() : "";
				}
				else
				{
					this.attachmentPath = this.ckEditorTextField.getValue();
				}

				NotificationInfo notificationInfo = new NotificationInfo(this.getUser(),
						this.txt_Title.getValue().trim(),
						this.notificationDatesPanelInfo.getDfSinceDate().getValue(),
						this.notificationDatesPanelInfo.getDfUntilDate().getValue(),
						this.attachmentPath,
						null, publishingType.getId(),
						this.cbx_Recipient.getSelectedValue(),
						null,
						null,
						this.isActive);

				popup.setTitle(notificationInfo.getTitle());
				popup.setHtmlcontent(notificationInfo.getHtmlcontent());
				popup.setPublishingdate(LocalDateTime.now());
				popup.setInitdate(notificationInfo.getInitdate());
				popup.setLink(this.txt_Link.getValue());

				String templateCode = this.selectedNotification != null ? this.selectedNotification.getPopuptemplatecode()
						: this.cbx_PopUpsTemplates.getSelectedItem().get().getCode();
				popup.setPopuptemplatecode(templateCode);

				String typeCode = (this.cbx_PopUpsTypes.getSelectedItem() != null) ? this.cbx_PopUpsTypes.getSelectedItem().get().getCode() : this.selectedNotification.getPopuptypecode();
				popup.setPopuptypecode(typeCode);

				WinPopupPreview winPopup = new WinPopupPreview(this.getBbrUIParent(), popup);
				winPopup.initializeView();
				winPopup.open(true);
			}
			else
			{
				this.getBbrUIParent().goToNewURL(this.txt_Link.getValue());
			}
		}
	}

	private Image getImageFromHtmlcontent(String htmlcontent)
	{
		File res = new File(htmlcontent);
		Resource source = new FileResource(res);
		Image image = new Image(null, source);
		return image;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
