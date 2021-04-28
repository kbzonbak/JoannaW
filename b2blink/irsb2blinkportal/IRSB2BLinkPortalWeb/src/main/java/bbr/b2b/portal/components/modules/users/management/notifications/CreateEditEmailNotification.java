package bbr.b2b.portal.components.modules.users.management.notifications;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.constants.BbrPublishingConstants;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.wrappers.management.NotificationDatesPanelInfo;
import bbr.b2b.portal.classes.wrappers.management.NotificationInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.basics.NotificationDatesPanel;
import bbr.b2b.portal.components.popups.WinNewsPreview;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.AddPublishingInitParamsDTO;
import bbr.b2b.users.report.classes.AttachmentDTO;
import bbr.b2b.users.report.classes.AttachmentInitParamsDTO;
import bbr.b2b.users.report.classes.PublishingDetailReportResultDTO;
import bbr.b2b.users.report.classes.PublishingReportDataDTO;
import bbr.b2b.users.report.classes.PublishingResultDTO;
import bbr.b2b.users.report.classes.PublishingTypeDTO;
import bbr.b2b.users.report.classes.RuleTypeDTO;
import bbr.b2b.users.report.classes.RuleTypesResultDTO;
import bbr.b2b.users.report.classes.UpdatePublishingInitParamsDTO;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.files_transfer.BbrFileUploader;
import cl.bbr.core.classes.files_transfer.BbrFileWrapper;
import cl.bbr.core.classes.files_transfer.BbrMultiFileUploader;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class CreateEditEmailNotification extends BbrWindow
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long			serialVersionUID			= 3247626779164695209L;

	private CKEditorConfig				config						= null;
	private CKEditorTextField			ckEditorTextField			= null;
	private BbrComboBox<RuleTypeDTO>	cbx_Recipient				= null;
	private BbrTextField				txt_Title					= null;
	private BbrMultiFileUploader		filesUploader				= null;

	private PublishingTypeDTO			publishingType;

	private RuleTypeDTO[]				rulesTypes					= null;

	private PublishingReportDataDTO		selectedNotification		= null;
	private NotificationDatesPanelInfo	notificationDatesPanelInfo	= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public CreateEditEmailNotification(BbrUI parent, PublishingReportDataDTO selectedNotification)
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
		publishingType = (PublishingTypeDTO) this.getData();
		if (this.publishingType != null)
		{
			NotificationDatesPanel notificationDatesPanel = new NotificationDatesPanel(this.getBbrUIParent(), this.getStartDate(), this.getEndDate());
			this.notificationDatesPanelInfo = notificationDatesPanel.getNotificationDatesPanelInfo();

			Panel pnlDates = this.notificationDatesPanelInfo.getPnlDates();

			Panel pnlPublishing = new Panel(publishingType.getName());
			pnlPublishing.addStyleName("bbr-panel-space");
			pnlPublishing.setSizeFull();
			pnlPublishing.setContent(this.getPublishingLayout());

			// Accept Button
			Button btn_CreateOrEdit = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"));
			btn_CreateOrEdit.setStyleName("primary");
			btn_CreateOrEdit.addStyleName("btn-login");
			btn_CreateOrEdit.setWidth("140px");
			btn_CreateOrEdit.addClickListener((ClickListener & Serializable) e -> btnCreateOrEdit_clickHandler(e));

			// Cancel Button
			Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
			btn_Cancel.setStyleName("primary");
			btn_Cancel.addStyleName("btn-login");
			btn_Cancel.setWidth("140px");
			btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));

			// Preview Button
			Button btn_Preview = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "preview"));
			btn_Preview.setStyleName("primary");
			btn_Preview.addStyleName("btn-login");
			btn_Preview.setWidth("140px");
			btn_Preview.setIcon(VaadinIcons.SEARCH);
			btn_Preview.addClickListener((ClickListener & Serializable) e -> btnPreview_clickHandler(e));

			HorizontalLayout buttonsPanel = new HorizontalLayout(btn_CreateOrEdit, btn_Cancel, btn_Preview);
			buttonsPanel.addStyleName("bbr-buttons-panel");

			buttonsPanel.setWidth("600px");
			buttonsPanel.setSpacing(true);

			// Vertical Layout for Components
			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.addComponents(pnlDates, pnlPublishing, buttonsPanel);
			mainLayout.setExpandRatio(pnlPublishing, 1F);
			mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
			mainLayout.setSizeFull();
			mainLayout.addStyleName("bbr-win-container");

			// Main Windows
			this.setWidth("1000px");
			this.setHeight("600px");
			this.setResizable(false);
			this.setContent(mainLayout);

			if (this.selectedNotification == null)
			{
				// Crear Notificaci�n
				this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "create_notification_win_title", publishingType.getName()));
			}
			else
			{
				// Editar Notificaci�n
				this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_notification", publishingType.getName()));

				this.notificationDatesPanelInfo.getDfSinceDate().setValue(this.selectedNotification.getInitdate());
				this.notificationDatesPanelInfo.getDfUntilDate().setValue(this.selectedNotification.getEnddate());
				this.txt_Title.setValue(this.selectedNotification.getTitle());
				this.ckEditorTextField.setValue(this.selectedNotification.getHtmlcontent());
				this.cbx_Recipient.setSelectedItem(getRulebyId(this.selectedNotification.getRuleid()));
				this.cbx_Recipient.setEnabled(false);

				this.updateAttachments(this.selectedNotification.getPukey());
			}

		}

	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void removeAttachment_handler(ClickEvent e)
	{
		List<BbrFileWrapper> selectedFile = filesUploader.getSelectedBbrFiles();
		if (selectedFile != null && !selectedFile.isEmpty())
		{
			filesUploader.removeSelectedBbrFile();
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_attach"));
		}
	}

	private void uploadFinished_handler(FinishedEvent event)
	{
		BbrFileWrapper bbrFile = new BbrFileWrapper(-1L, event.getMIMEType(), event.getFilename(), event.getLength());
		ArrayList<BbrFileWrapper> files = new ArrayList<BbrFileWrapper>();
		files.add(bbrFile);
		filesUploader.addBbrFilesWrapper(files);
	}

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
		NotificationInfo notificationInfo = new NotificationInfo(this.getUser(),
				this.txt_Title.getValue().trim(),
				this.notificationDatesPanelInfo.getDfSinceDate().getValue(),
				this.notificationDatesPanelInfo.getDfUntilDate().getValue(),
				this.ckEditorTextField.getValue(),
				null, publishingType.getId(),
				this.cbx_Recipient.getSelectedValue(),
				filesUploader.getNewFiles(),
				filesUploader.getOriginalsRemovedFilesList(),
				null);

		if (this.selectedNotification != null)
		{
			notificationInfo.setPublishingId(this.selectedNotification.getPukey());
			// Edit
			this.removeAttachments(notificationInfo);
			this.doEditNotification(notificationInfo);
		}
		else
		{
			// Create
			this.doCreateNotification(notificationInfo);
		}

	}

	private void removeAttachments(NotificationInfo notificationInfo)
	{
		AttachmentInitParamsDTO[] toRemove = notificationInfo.getToRemoveAttachments();
		if (toRemove != null && toRemove.length > 0)
		{
			try
			{
				for (AttachmentInitParamsDTO attachment : toRemove)
				{
					EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doDeleteAttachmentById(attachment.getFileid());
				}
			}
			catch (Exception e) // Error no controlado
			{
				e.printStackTrace();
			}

		}
	}

	private void doCreateNotification(NotificationInfo newInfo)
	{
		String message = "";
		PublishingResultDTO publishingResult = null;
		try
		{
			if (newInfo != null)
			{
				message = newInfo.validateCreateData();
				if (message == null || message.length() == 0)
				{

					AddPublishingInitParamsDTO initParams = newInfo.toPublishingDTO();

					publishingResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doAddPublishing(initParams,
							this.getUser().getId());

					if (publishingResult != null)
					{
						message = I18NManager.getErrorMessageBaseResult(publishingResult, publishingResult.getParams()); // <--
						// Obtiene
						// el
						// mensaje
						// de
						// error.
						// ""
						// si
						// no
						// hay
						// errores.
					}
					else
					{
						// -> Error companyResult = null
						message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
					}
				}
			}

		}
		catch (Exception e) // Error no controlado
		{
			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}

		if (message.length() > 0)
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
		}
		else
		{
			BbrEvent createEvent = new BbrEvent(BbrEvent.ITEM_CREATED);
			createEvent.setResultObject((publishingResult != null) ? publishingResult.getPublishing().getId() : null);
			this.dispatchBbrEvent(createEvent);
			this.close();
		}
	}

	private void doEditNotification(NotificationInfo newInfo)
	{
		String message = "";
		PublishingResultDTO publishingResult = null;
		try
		{
			if (newInfo != null)
			{
				message = newInfo.validateEditData();
				if (message == null || message.length() == 0)
				{

					UpdatePublishingInitParamsDTO initParams = newInfo.toUpdatePublishingDTO();

					publishingResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doUpdatePublishing(initParams,
							this.getUser().getId());

					if (publishingResult != null)
					{
						message = I18NManager.getErrorMessageBaseResult(publishingResult, publishingResult.getParams()); // <--
						// Obtiene
						// el
						// mensaje
						// de
						// error.
						// ""
						// si
						// no
						// hay
						// errores.
					}
					else
					{
						// -> Error companyResult = null
						message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
					}
				}
			}

		}
		catch (Exception e) // Error no controlado
		{
			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}

		if (message.length() > 0)
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
		}
		else
		{
			BbrEvent createEvent = new BbrEvent(BbrEvent.ITEM_UPDATED);
			createEvent.setResultObject((publishingResult != null) ? publishingResult.getPublishing().getId() : null);
			this.dispatchBbrEvent(createEvent);
			this.close();
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private HorizontalLayout getPublishingLayout()
	{
		String indentation = "80px";
		// Message Area
		// Recipient
		Label lblRecipient = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "to"));
		lblRecipient.setWidth(indentation);
		cbx_Recipient = this.getRecipientsComboBox();
		HorizontalLayout pnlNotificactionsTypes = new HorizontalLayout();
		pnlNotificactionsTypes.setWidth("100%");
		pnlNotificactionsTypes.addComponents(lblRecipient, cbx_Recipient);
		pnlNotificactionsTypes.setExpandRatio(cbx_Recipient, 1F);
		pnlNotificactionsTypes.addStyleName("bbr-panel-space");

		// Title
		Label lblTitle = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "title"));
		lblTitle.setWidth(indentation);
		txt_Title = new BbrTextField();
		txt_Title.setMaxLength(50);
		txt_Title.addStyleName("bbr-fields");
		HorizontalLayout pnlTitle = new HorizontalLayout();
		pnlTitle.setWidth("100%");
		pnlTitle.addComponents(lblTitle, txt_Title);
		pnlTitle.setExpandRatio(txt_Title, 1F);
		pnlTitle.addStyleName("bbr-panel-space");

		// Body
		Label lblBody = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "body"));
		lblBody.setWidth(indentation);

		config = new CKEditorConfig();
		config.useCompactTags();
		config.disableElementsPath();
		config.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
		config.disableSpellChecker();
		config.setWidth("100%");
		config.setHeight("100%");
		config.setFilebrowserUploadUrl(BbrPublishingConstants.FILE_UPLOAD_URL);
		config.setFilebrowserImageUploadUrl(BbrPublishingConstants.IMAGE_UPLOAD_URL);

		ckEditorTextField = new CKEditorTextField(config);
		ckEditorTextField.setHeight("100%");

		HorizontalLayout pnlBody = new HorizontalLayout();
		pnlBody.setWidth("100%");
		pnlBody.setHeight("100%");
		pnlBody.addComponents(lblBody, ckEditorTextField);
		pnlBody.setExpandRatio(ckEditorTextField, 1F);

		pnlBody.addStyleName("bbr-panel-space");

		VerticalLayout pnlMessageArea = new VerticalLayout();
		pnlMessageArea.addComponents(pnlNotificactionsTypes, pnlTitle, pnlBody);
		pnlMessageArea.setHeight("100%");
		pnlMessageArea.setMargin(false);
		pnlMessageArea.setExpandRatio(pnlBody, 1F);

		// Attachment Area
		// Attachment
		HorizontalLayout toolBar = this.getToolBar();

		filesUploader = new BbrMultiFileUploader(this.getBbrUIParent(), BbrPublishingConstants.getUploadFullPath(this.publishingType.getCode()));
		filesUploader.setMargin(false);
		filesUploader.setWidth("310px");
		filesUploader.setHeight("100%");

		Label lblMaxCount = new Label(
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "max_files", String.valueOf(BbrPublishingConstants.DEFAULT_PUBLISHING_MAX_FILES)));
		lblMaxCount.addStyleName("bbr-uploader-footer-label");

		Label lblMaxSize = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "max_file_size",
				BbrUtils.getInstance().humanReadableByteCount(BbrPublishingConstants.DEFAULT_PUBLISHING_FILE_SIZE_LIMIT, false)));
		lblMaxSize.addStyleName("bbr-uploader-footer-label");

		VerticalLayout pnlAttachmentArea = new VerticalLayout();
		pnlAttachmentArea.setMargin(false);
		pnlAttachmentArea.setHeight("100%");
		pnlAttachmentArea.setWidth("310px");
		pnlAttachmentArea.addComponents(toolBar, filesUploader, lblMaxCount, lblMaxSize);
		pnlAttachmentArea.setComponentAlignment(toolBar, Alignment.TOP_CENTER);
		pnlAttachmentArea.setExpandRatio(filesUploader, 1F);
		pnlAttachmentArea.setSpacing(false);
		HorizontalLayout result = new HorizontalLayout();
		result.setSizeFull();
		result.addComponents(pnlMessageArea, pnlAttachmentArea);
		result.setSpacing(true);
		result.setExpandRatio(pnlMessageArea, 1F);
		result.setMargin(true);

		return result;
	}

	private RuleTypeDTO getRulebyId(Long ruleId)
	{
		RuleTypeDTO result = null;
		for (RuleTypeDTO rule : rulesTypes)
		{
			if (rule.getId().equals(ruleId))
			{
				result = rule;
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
			if (rulesTypes == null || rulesTypes.length == 0)
			{
				RuleTypesResultDTO rulesTypesResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).getRuleTypes();

				if (rulesTypesResult.getRuletypes() != null)
				{

					rulesTypes = rulesTypesResult.getRuletypes();
				}
			}

			result = new BbrComboBox<RuleTypeDTO>(rulesTypes);
			result.setPlaceholder(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_option"));
			result.setItemCaptionGenerator(RuleTypeDTO::getDescription);
			result.setTextInputAllowed(false);
			result.setEmptySelectionAllowed(false);

			result.setWidth(100F, Unit.PERCENTAGE);
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

	private HorizontalLayout getToolBar()
	{
		HorizontalLayout result = new HorizontalLayout();
		result.setWidth("100%");

		BbrFileUploader uploaderReceiver = new BbrFileUploader(BbrPublishingConstants.getUploadFullPath(publishingType.getCode()));
		Upload btnAddAttach = new Upload(null, uploaderReceiver);
		btnAddAttach.addStyleName("bbr-upload-button");

		btnAddAttach.addFinishedListener((FinishedListener & Serializable) e -> uploadFinished_handler(e));

		Button btnRemoveAttach = new Button("",
				BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DeleteSmall.png"));
		btnRemoveAttach.addStyleName("toolbar-button");
		btnRemoveAttach.addClickListener((ClickListener & Serializable) e -> removeAttachment_handler(e));

		HorizontalLayout fillPanel = new HorizontalLayout();

		result.addComponents(btnAddAttach, btnRemoveAttach, fillPanel);
		result.setComponentAlignment(btnAddAttach, Alignment.MIDDLE_LEFT);
		result.setExpandRatio(fillPanel, 1F);
		result.setSpacing(true);
		return result;
	}

	private LocalDateTime getStartDate()
	{
		// Ma�ana a las 08:00 - DSU
		LocalDateTime result = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.HOURS).withHour(8);

		return result;
	}

	private LocalDateTime getEndDate()
	{
		// �ltimo d�a del mes. - DSU
		LocalDateTime today = LocalDateTime.now();

		LocalDateTime result = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())
				.truncatedTo(ChronoUnit.MINUTES)
				.withMinute(59).withHour(23);

		// Si el �ltimo d�a es hoy, entonces se tomar� el �ltimo d�a del mes
		// siguiente. - DSU
		if (today.getDayOfMonth() == result.getDayOfMonth())
		{
			result = result.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
		}

		return result;
	}

	private void showPreview()
	{
		PublishingReportDataDTO news = new PublishingReportDataDTO();
		NotificationInfo notificationInfo = new NotificationInfo(this.getUser(),
				this.txt_Title.getValue().trim(),
				this.notificationDatesPanelInfo.getDfSinceDate().getValue(),
				this.notificationDatesPanelInfo.getDfUntilDate().getValue(),
				this.ckEditorTextField.getValue(),
				null,
				publishingType.getId(),
				this.cbx_Recipient.getSelectedValue(),
				null,
				null,
				null);

		news.setTitle(notificationInfo.getTitle());
		news.setInitdate(notificationInfo.getInitdate());
		news.setHtmlcontent(notificationInfo.getHtmlcontent());
		news.setPublishingdate(LocalDateTime.now());

		WinNewsPreview winPopup = new WinNewsPreview(this.getBbrUIParent(), news);
		winPopup.initializeView();
		winPopup.open(true);
	}

	private void updateAttachments(Long publishingid)
	{
		try
		{
			if (publishingid != null)
			{
				PublishingDetailReportResultDTO detailsResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent())
						.getPublishingDetailReportById(publishingid);

				if (detailsResult != null && detailsResult.getAttachments() != null && detailsResult.getAttachments().length > 0)
				{
					ArrayList<BbrFileWrapper> files = new ArrayList<>();
					for (AttachmentDTO attach : detailsResult.getAttachments())
					{
						BbrFileWrapper file = new BbrFileWrapper(attach.getId(), attach.getExtension(), attach.getName(), attach.getSize().longValue());
						files.add(file);
					}

					filesUploader.addBbrFilesWrapper(files);
				}
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
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
