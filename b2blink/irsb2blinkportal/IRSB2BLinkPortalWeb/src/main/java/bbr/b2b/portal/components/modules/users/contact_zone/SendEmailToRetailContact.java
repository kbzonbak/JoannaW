package bbr.b2b.portal.components.modules.users.contact_zone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.constants.BbrPublishingConstants;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.management.SendMailToRetailInfo;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ContactB2BDTO;
import bbr.b2b.users.report.classes.SendMailContactInitParamDTO;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.files_transfer.BbrFileUploader;
import cl.bbr.core.classes.files_transfer.BbrFileWrapper;
import cl.bbr.core.classes.files_transfer.BbrMultiFileUploader;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class SendEmailToRetailContact extends BbrWindow 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = 3247626779164695209L;

	private CKEditorConfig 				config 				= null;
	private CKEditorTextField 			ckEditorTextField 	= null;
	private BbrTextField				txt_Subject 			= null ;
	private BbrMultiFileUploader 		filesUploader 		= null;

	private ContactB2BDTO 	selectedContact = null ;

	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	public SendEmailToRetailContact(BbrUI parent, ContactB2BDTO selectedContact) 
	{
		super(parent);
		this.selectedContact = selectedContact;
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView() 
	{
		if(this.selectedContact != null)
		{
			BbrUser user = this.getUser();
			
			//To Layout
			Label lbl_To = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "to") + " :");
			lbl_To.setWidth("100px");
			Label txt_To = new Label(BbrUtils.getInstance().substitute("{0} {1} ({2})",selectedContact.getName(),selectedContact.getLastname(),selectedContact.getPosition()));

			HorizontalLayout pnlToInfo = new HorizontalLayout();
			pnlToInfo.setWidth("100%");
			pnlToInfo.addComponents(lbl_To,txt_To);
			pnlToInfo.setExpandRatio(txt_To, 1F);

			//From Layout
			Label lbl_From = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "from") + " :");
			lbl_From.setWidth("100px");

			VerticalLayout pnlFromData = new VerticalLayout();
			pnlFromData.setWidth("100%");
			pnlFromData.setMargin(false);

			HorizontalLayout pnlFromTop = new HorizontalLayout();
			pnlFromTop.setWidth("100%");
			pnlFromTop.setMargin(false);
			
			Label txt_FromName 		= new Label(BbrUtils.getInstance().substitute("{0} {1}",user.getName(),user.getLastName()));
			Label txt_FromPosition 	= new Label(user.getPosition());
			
			pnlFromTop.addComponents(txt_FromName, txt_FromPosition);

			HorizontalLayout pnlFromBottom = new HorizontalLayout();
			pnlFromBottom.setWidth("100%");
			
			Link txt_FromEmail = new Link(user.getEmail(), new ExternalResource("mailto:" + user.getEmail()));

			Label txt_FromCompany 	= new Label(user.getEnterpriseDescription());
			
			pnlFromBottom.addComponents(txt_FromEmail, txt_FromCompany);
			pnlFromBottom.setMargin(false);

			pnlFromData.addComponents(pnlFromTop,pnlFromBottom);
			pnlFromData.setSpacing(false);
			
			HorizontalLayout pnlFromInfo = new HorizontalLayout();
			pnlFromInfo.setWidth("100%");
			pnlFromInfo.addComponents(lbl_From,pnlFromData);
			pnlFromInfo.setExpandRatio(pnlFromData, 1F);
			
			
			VerticalLayout pnlMailContactInfo = new VerticalLayout();
			pnlMailContactInfo.setWidth("100%");
			pnlMailContactInfo.setHeight("80px");
			
			pnlMailContactInfo.addComponents(pnlToInfo,pnlFromInfo);
			pnlMailContactInfo.setMargin(false);
			pnlMailContactInfo.setSpacing(true);
			pnlMailContactInfo.setExpandRatio(pnlFromInfo, 1F);
			
			//Asunto
			Label lblSubject = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "subject") + " :");
			lblSubject.setWidth("100px");
			txt_Subject = new BbrTextField();
			txt_Subject.setMaxLength(50);
			txt_Subject.addStyleName("bbr-filter-fields");
			HorizontalLayout pnlTitle = new HorizontalLayout();
			pnlTitle.setWidth("100%");
			pnlTitle.addComponents(lblSubject,txt_Subject);
			pnlTitle.setExpandRatio(txt_Subject, 1F);
			pnlTitle.addStyleName("bbr-panel-space");
			
			//Cuerpo
			HorizontalLayout pnlBody = this.getBodyLayout();

			//Accept Button
			Button btn_Send = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "send"));
			btn_Send.setStyleName("primary");
			btn_Send.addStyleName("btn-generic");
			btn_Send.setWidth("140px");
			btn_Send.addClickListener((ClickListener & Serializable) e -> btnSendMail_clickHandler(e));

			//Cancel Button
			Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
			btn_Cancel.setStyleName("primary");
			btn_Cancel.addStyleName("btn-generic");
			btn_Cancel.setWidth("140px");
			btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));

			HorizontalLayout buttonsPanel = new HorizontalLayout(btn_Send, btn_Cancel);
			buttonsPanel.addStyleName("bbr-buttons-panel");

			buttonsPanel.setWidth("500px");
			buttonsPanel.setSpacing(true);

			//Vertical Layout for Components
			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.addComponents(pnlMailContactInfo,pnlTitle,pnlBody,buttonsPanel);
			mainLayout.setExpandRatio(pnlBody, 1F);
			mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
			mainLayout.setSizeFull();
			mainLayout.addStyleName("bbr-win-container");

			//Main Windows
			this.setWidth(1000,Unit.PIXELS);
			this.setHeight(600,Unit.PIXELS);
			this.setResizable(false);
			this.setContent(mainLayout);
			this.txt_Subject.focus();
			this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_send_mail_to_retail"));
		}

	}

	// ****************************************************************************************
	// ENDING SECTION 		---->			OVERRIDDEN METHODS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			EVENTS HANDLERS
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
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_attach"));
		}
	}

	private void uploadFinished_handler(FinishedEvent event) 
	{
		BbrFileWrapper bbrFile = new BbrFileWrapper(-1L,event.getMIMEType(), event.getFilename(), event.getLength());
		ArrayList<BbrFileWrapper> files = new ArrayList<BbrFileWrapper>();
		files.add(bbrFile);
		filesUploader.addBbrFilesWrapper(files);
	}

	private void btnClose_clickHandler(ClickEvent e) 
	{
		this.close();
	}

	private void btnSendMail_clickHandler(ClickEvent e) 
	{
		SendMailToRetailInfo mailInfo = new SendMailToRetailInfo(this.getUser(), this.selectedContact
				,this.txt_Subject.getValue().trim(),this.ckEditorTextField.getValue()
				,filesUploader.getNewFiles()
		);

		this.doSendMailToContact(mailInfo);

	}

	private void doSendMailToContact(SendMailToRetailInfo mailInfo) 
	{
		String message = "";
		BaseResultDTO 	sendEmailResult = null ;
		try 
		{
			if(mailInfo != null)
			{
				message = mailInfo.validateSendData();
				if(message == null || message.length() == 0)
				{

					SendMailContactInitParamDTO initParams = mailInfo.toSendMailContactInitParamDTO();

					sendEmailResult = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(this.getBbrUIParent()).sendEmailContact(initParams, this.getUser().getRut());

					if(sendEmailResult != null)
					{
						message = I18NManager.getErrorMessageBaseResult(sendEmailResult, sendEmailResult.getParams()); // <-- Obtiene el mensaje de error. "" si no hay errores.
					}
					else
					{
						// -> Error companyResult = null
						message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
					}
				}
			}

		} 
		catch (Exception e) //Error no controlado
		{
			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}

		if(message.length() > 0)
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
		}
		else
		{
			BbrEvent createEvent = new BbrEvent(BbrEvent.ITEM_CREATED);
			this.dispatchBbrEvent(createEvent);
			this.close();
		}
	}

	// ****************************************************************************************
	// ENDING SECTION 		---->			EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PRIVATE METHODS
	// ****************************************************************************************
	private HorizontalLayout getBodyLayout()
	{
		//Message Area
		//Body
		Label lblBody = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "body") + " :");
		lblBody.setWidth("100px");

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
		pnlBody.addComponents(lblBody,ckEditorTextField);
		pnlBody.setExpandRatio(ckEditorTextField, 1F);

		pnlBody.addStyleName("bbr-panel-space");

		VerticalLayout pnlMessageArea = new VerticalLayout();
		pnlMessageArea.addComponents(pnlBody);
		pnlMessageArea.setHeight("100%");
		pnlMessageArea.setMargin(false);
		pnlMessageArea.setExpandRatio(pnlBody, 1F);


		//Attachment Area
		//Attachment
		HorizontalLayout toolBar 	= this.getToolBar();

		filesUploader = new BbrMultiFileUploader(this.getBbrUIParent(),BbrAppConstants.getUploadPathOfContact());
		filesUploader.setMargin(false);
		filesUploader.setWidth("310px");
		filesUploader.setHeight("100%");

		Label lblMaxCount 	= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "max_files",String.valueOf(BbrPublishingConstants.DEFAULT_PUBLISHING_MAX_FILES)));
		lblMaxCount.addStyleName("bbr-uploader-footer-label");

		Label lblMaxSize 	= new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "max_file_size",BbrUtils.getInstance().humanReadableByteCount(BbrPublishingConstants.DEFAULT_PUBLISHING_FILE_SIZE_LIMIT, false)));
		lblMaxSize.addStyleName("bbr-uploader-footer-label");

		VerticalLayout pnlAttachmentArea = new VerticalLayout();
		pnlAttachmentArea.setMargin(false);
		pnlAttachmentArea.setHeight("100%");
		pnlAttachmentArea.setWidth("310px");
		pnlAttachmentArea.addComponents(toolBar,filesUploader,lblMaxCount,lblMaxSize);
		pnlAttachmentArea.setComponentAlignment(toolBar, Alignment.TOP_CENTER);
		pnlAttachmentArea.setExpandRatio(filesUploader, 1F);
		pnlAttachmentArea.setSpacing(false);
		HorizontalLayout result = new HorizontalLayout();
		result.setSizeFull();
		result.addComponents(pnlMessageArea,pnlAttachmentArea);
		result.setSpacing(true);
		result.setExpandRatio(pnlMessageArea, 1F);
		result.setMargin(false);


		return result;
	}

	private HorizontalLayout getToolBar()
	{
		HorizontalLayout result = new HorizontalLayout();
		result.setWidth("100%");

		BbrFileUploader uploaderReceiver = new BbrFileUploader(BbrAppConstants.getUploadPathOfContact());
		Upload btnAddAttach = new Upload(null,uploaderReceiver);
		btnAddAttach.addStyleNames("bbr-upload-button");

		btnAddAttach.addFinishedListener((FinishedListener & Serializable) e -> uploadFinished_handler(e));

		Button btnRemoveAttach = new Button("",BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(),CoreConstants.PATH_BASE_IMAGES_BUTTONS+"icon_DeleteSmall.png"));
		btnRemoveAttach.addStyleName("toolbar-button");
		btnRemoveAttach.addClickListener((ClickListener & Serializable)e -> removeAttachment_handler(e));

		HorizontalLayout fillPanel = new HorizontalLayout();

		result.addComponents(btnAddAttach, btnRemoveAttach, fillPanel);
		result.setComponentAlignment(btnAddAttach, Alignment.MIDDLE_LEFT);
		result.setExpandRatio(fillPanel, 1F);
		result.setSpacing(true);
		return result;
	}


	
	// ****************************************************************************************
	// ENDING SECTION 		---->			PRIVATE METHODS
	// ****************************************************************************************



}
