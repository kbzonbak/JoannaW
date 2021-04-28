//package bbr.b2b.portal.components.modules.tickets;
//
//import java.io.Serializable;
//
//import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
//import org.vaadin.openesignforms.ckeditor.CKEditorTextField;
//
//import com.vaadin.server.ExternalResource;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.Link;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
//import bbr.b2b.portal.classes.constants.BbrPublishingConstants;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.wrappers.management.VendorTicketInfo;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.trac.report.classes.AddTicketInitParamDTO;
//import cl.bbr.core.classes.basics.BbrUser;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.components.basics.BbrUI;
//import cl.bbr.core.components.basics.BbrWindow;
//import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
//
//public class AddVendorTicket extends BbrWindow 
//{
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = 3247626779164695209L;
//
//	private CKEditorConfig 						config 				= null;
//	private CKEditorTextField 					ckEditorTextField 	= null;
//	private BbrTextField						txt_Title 			= null ;
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//	public AddVendorTicket(BbrUI parent) 
//	{
//		super(parent);
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			CONSTRUCTORS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//	public void initializeView() 
//	{
//		HorizontalLayout pnlTicketLayout = this.getTicketUserInfoLayout(this.getUser());
//
//		//Accept Button
//		Button btn_Create = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "send"));
//		btn_Create.setStyleName("primary");
//		btn_Create.addStyleName("btn-login");
//		btn_Create.setWidth("140px");
//		btn_Create.addClickListener((ClickListener & Serializable) e -> btnCreate_clickHandler(e));
//
//		//Cancel Button
//		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
//		btn_Cancel.setStyleName("primary");
//		btn_Cancel.addStyleName("btn-login");
//		btn_Cancel.setWidth("140px");
//		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));
//
//		HorizontalLayout buttonsPanel = new HorizontalLayout(btn_Create, btn_Cancel);
//		buttonsPanel.addStyleName("bbr-buttons-panel");
//
//		buttonsPanel.setWidth("600px");
//		buttonsPanel.setSpacing(true);
//
//		//Vertical Layout for Components
//		VerticalLayout mainLayout = new VerticalLayout();
//		mainLayout.addComponents(pnlTicketLayout,buttonsPanel);
//		mainLayout.setExpandRatio(pnlTicketLayout, 1F);
//		mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
//		mainLayout.setSizeFull();
//		mainLayout.addStyleName("bbr-win-container");
//
//		//Main Windows
//		this.setWidth(1000,Unit.PIXELS);
//		this.setHeight(600,Unit.PIXELS);
//		this.setResizable(false);
//		this.setContent(mainLayout);
//		this.txt_Title.focus();
//		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "create_vendor_ticket_win_title"));
//	}
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			EVENTS HANDLERS
//	// ****************************************************************************************
//	private void btnClose_clickHandler(ClickEvent e) 
//	{
//		this.close();
//	}
//
//	private void btnCreate_clickHandler(ClickEvent e) 
//	{
//		VendorTicketInfo vendorTicketInfo = new VendorTicketInfo(this.getUser(), this.txt_Title.getValue().trim(), 
//				this.ckEditorTextField.getValue());
//		//Create
//		this.doCreateTicket(vendorTicketInfo);
//	}
//
//	private void doCreateTicket(VendorTicketInfo vendorTicketInfo) 
//	{
//		String message = "";
//		BaseResultDTO 	ticketResult = null ;
//		try 
//		{
//			if(vendorTicketInfo != null)
//			{
//				message = vendorTicketInfo.validateCreateData();
//				if(message == null || message.length() == 0)
//				{
//
//					AddTicketInitParamDTO initParams = vendorTicketInfo.toAddTicketInitParamDTO();
//
//					ticketResult 	= EJBFactory.getTicketsEJBFactory().getTicketReportManagerLocal().doAddTicket(initParams);
//
//					if(ticketResult != null)
//					{
//						message = I18NManager.getErrorMessageBaseResult(ticketResult, ticketResult.getParams()); // <-- Obtiene el mensaje de error. "" si no hay errores.
//					}
//					else
//					{
//						// -> Error companyResult = null
//						message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
//					}
//				}
//			}
//
//		} 
//		catch (Exception e) //Error no controlado
//		{
//			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
//		}
//
//		if(message.length() > 0)
//		{
//			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
//		}
//		else
//		{
//			BbrEvent createEvent = new BbrEvent(BbrEvent.ITEM_CREATED);
//			this.dispatchBbrEvent(createEvent);
//			this.close();
//		}
//	}
//
//
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			EVENTS HANDLERS
//	// ****************************************************************************************
//
//
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//
//
//	private HorizontalLayout getTicketUserInfoLayout(BbrUser user)
//	{
//		//Company
//		Label lblCompany = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "contact_company"));
//		lblCompany.setWidth("80px");
//		Label txtCompanyCode = new Label(user.getEnterpriseCode());
//		txtCompanyCode.setWidth("100px");
//		Label txtCompanyName = new Label(user.getEnterpriseDescription());
//
//		HorizontalLayout pnlCompany = new HorizontalLayout();
//		pnlCompany.setWidth("100%");
//		pnlCompany.addComponents(lblCompany,txtCompanyCode, txtCompanyName);
//		pnlCompany.setExpandRatio(txtCompanyName, 1F);
//		pnlCompany.addStyleName("bbr-panel-space");
//
//		//Contact Name
//		Label lblContactName = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "contact_name"));
//		lblContactName.setWidth("80px");
//		Label txtContactName = new Label(user.getFullName());
//
//		HorizontalLayout pnlContactName = new HorizontalLayout();
//		pnlContactName.setWidth("100%");
//		pnlContactName.addComponents(lblContactName,txtContactName);
//		pnlContactName.setExpandRatio(txtContactName, 1F);
//		pnlContactName.addStyleName("bbr-panel-space");
//
//		//Contact Position
//		Label lblContactPosition = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "contact_position"));
//		lblContactPosition.setWidth("80px");
//		Label txtContactPosition = new Label(user.getPosition());
//
//		HorizontalLayout pnlContactPosition = new HorizontalLayout();
//		pnlContactPosition.setWidth("100%");
//		pnlContactPosition.addComponents(lblContactPosition,txtContactPosition);
//		pnlContactPosition.setExpandRatio(txtContactPosition, 1F);
//		pnlContactPosition.addStyleName("bbr-panel-space");
//
//		//Contact Email
//
//		Label lblContactEmail = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "contact_email"));
//		lblContactEmail.setWidth("80px");
//		Link txtContactEmail = new Link(user.getEmail(), new ExternalResource("mailto:" + user.getEmail()));
//
//		HorizontalLayout pnlContactEmail = new HorizontalLayout();
//		pnlContactEmail.setWidth("100%");
//		pnlContactEmail.addComponents(lblContactEmail,txtContactEmail);
//		pnlContactEmail.setExpandRatio(txtContactEmail, 1F);
//		pnlContactEmail.addStyleName("bbr-panel-space");
//
//		//Contact Phone
//		Label lblContactPhone = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "contact_phone"));
//		lblContactPhone.setWidth("80px");
//		Label txtContactPhone = new Label(user.getPhoneNumber());
//
//		HorizontalLayout pnlContactPhone = new HorizontalLayout();
//		pnlContactPhone.setWidth("100%");
//		pnlContactPhone.addComponents(lblContactPhone,txtContactPhone);
//		pnlContactPhone.setExpandRatio(txtContactPhone, 1F);
//		pnlContactPhone.addStyleName("bbr-panel-space");
//
//		//Title
//		Label lblTitle = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "title"));
//		lblTitle.setWidth("80px");
//		txt_Title = new BbrTextField();
//		txt_Title.addStyleName("bbr-filter-fields");
//		HorizontalLayout pnlTitle = new HorizontalLayout();
//		pnlTitle.setWidth("100%");
//		pnlTitle.addComponents(lblTitle,txt_Title);
//		pnlTitle.setExpandRatio(txt_Title, 1F);
//		pnlTitle.addStyleName("bbr-panel-space");
//
//		//Details
//		Label lblDetails = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_details"));
//		lblDetails.setWidth("80px");
//		HorizontalLayout detailsLayout = this.getTicketsLayout();
//		HorizontalLayout pnlDetails = new HorizontalLayout();
//		pnlDetails.setWidth("100%");
//		pnlDetails.setHeight("100%");
//		pnlDetails.addComponents(lblDetails,detailsLayout);
//		pnlDetails.setExpandRatio(detailsLayout, 1F);
//		pnlDetails.addStyleName("bbr-panel-space");
//
//
//		VerticalLayout pnlMessageArea = new VerticalLayout();
//		pnlMessageArea.addComponents(pnlCompany,pnlContactName,pnlContactPosition,pnlContactEmail, pnlContactPhone,pnlTitle,pnlDetails);
//		pnlMessageArea.setHeight("100%");
//		pnlMessageArea.setExpandRatio(pnlDetails, 1F);
//
//
//		HorizontalLayout result = new HorizontalLayout();
//		result.setSizeFull();
//		result.addComponents(pnlMessageArea);
//		result.setExpandRatio(pnlMessageArea, 1F);
//
//		return result;
//	}
//
//	private HorizontalLayout getTicketsLayout()
//	{
//		HorizontalLayout result = new HorizontalLayout();
//
//		//TEXT AREA
//		config = new CKEditorConfig();
//		config.useCompactTags();
//		config.disableElementsPath();
//		config.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
//		config.disableSpellChecker();
//		config.setWidth("100%");
//		config.setHeight("100%");
//		config.setFilebrowserUploadUrl(BbrPublishingConstants.FILE_UPLOAD_URL);
//		config.setFilebrowserImageUploadUrl(BbrPublishingConstants.IMAGE_UPLOAD_URL);
//
//		ckEditorTextField = new CKEditorTextField(config);
//		ckEditorTextField.setHeight("100%");
//
//		
//		result.setWidth("100%");
//		result.setHeight("100%");
//		result.addComponents(ckEditorTextField);
//		result.setExpandRatio(ckEditorTextField, 1F);
//
//		result.addStyleName("bbr-panel-space");
//
//		result.setSizeFull();
//		return result;
//	}
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//
//
//}
