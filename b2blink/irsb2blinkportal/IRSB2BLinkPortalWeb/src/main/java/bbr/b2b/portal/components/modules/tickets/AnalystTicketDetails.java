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
//import bbr.b2b.portal.classes.constants.BbrPublishingConstants;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.portal.classes.constants.EnumTicketOperation;
//import bbr.b2b.portal.classes.constants.TicketsConstants;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.components.basics.BbrSystemException;
//import bbr.b2b.portal.components.basics.BbrUserException;
//import bbr.b2b.trac.report.classes.TicketDetailReportDataDTO;
//import bbr.b2b.trac.report.classes.TicketDetailReportInitParamDTO;
//import bbr.b2b.trac.report.classes.TicketDetailReportResultDTO;
//import bbr.b2b.trac.report.classes.TicketReportDataDTO;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.classes.events.BbrEventListener;
//import cl.bbr.core.classes.utilities.BbrDateUtils;
//import cl.bbr.core.components.basics.BbrWindow;
//
//public class AnalystTicketDetails extends BbrWindow 
//{
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = 3247626779164695209L;
//
//	private CKEditorConfig 						config 				= null;
//	private CKEditorTextField 					ckEditorTextField 	= null;
//
//	private TicketReportDataDTO 				selectedTicket 		= null;
//	private Long 								vendorId 			= null;
//	private String 								vendorName 			= null;
//	
//	private HorizontalLayout 					buttonsPanel		= null ;
//	private Label 								txtTicketStatus		= null;
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//	public AnalystTicketDetails(TicketReportDataDTO selectedTicket, Long vendorId) 
//	{
//		this.selectedTicket = selectedTicket;
//		this.vendorId 		= vendorId;
//		this.vendorName 	= selectedTicket.getVendorname();
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
//		if(this.selectedTicket != null)
//		{
//			HorizontalLayout pnlTicketLayout = this.getTicketUserInfoLayout(this.selectedTicket);
//
//			this.buttonsPanel = new HorizontalLayout();
//			
//			this.updateButtonPanel();
//
//			//Vertical Layout for Components
//			VerticalLayout mainLayout = new VerticalLayout();
//			mainLayout.addComponents(pnlTicketLayout,buttonsPanel);
//			mainLayout.setExpandRatio(pnlTicketLayout, 1F);
//			mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
//			mainLayout.setSizeFull();
//			mainLayout.addStyleName("bbr-win-container");
//
//			//Main Windows
//			this.setWidth(1000,Unit.PIXELS);
//			this.setHeight(600,Unit.PIXELS);
//			this.setResizable(false);
//			this.setContent(mainLayout);
//			this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "details_vendor_ticket_win_title"));
//		}
//	}
//
//	private void updateButtonPanel() 
//	{
//		this.buttonsPanel.removeAllComponents();
//		
//		if(this.selectedTicket.getCodestatus().equalsIgnoreCase(TicketsConstants.TICKET_IN_ANALYSIS) 
//				|| this.selectedTicket.getCodestatus().equalsIgnoreCase(TicketsConstants.TICKET_REOPEN))
//		{
//			//Reply Button
//			Button btn_Reply = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "reply"));
//			btn_Reply.setStyleName("primary");
//			btn_Reply.addStyleName("btn-login");
//			btn_Reply.setWidth("170px");
//			btn_Reply.addClickListener((ClickListener & Serializable) e -> btnReply_clickHandler(e));
//			
//			this.buttonsPanel.addComponent(btn_Reply);
//		}
//
//		if(!this.selectedTicket.getCodestatus().equalsIgnoreCase(TicketsConstants.TICKET_SOLVENTA_RESOLVED))
//		{
//			//Close Button
//			Button btn_SolveTicket = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "solve_ticket"));
//			btn_SolveTicket.setStyleName("primary");
//			btn_SolveTicket.addStyleName("btn-login");
//			btn_SolveTicket.setWidth("170px");
//			btn_SolveTicket.addClickListener((ClickListener & Serializable) e -> btnSolve_clickHandler(e));
//			
//			this.buttonsPanel.addComponent(btn_SolveTicket);
//		}
//		
//
//		//Cancel Button
//		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
//		btn_Cancel.setStyleName("primary");
//		btn_Cancel.addStyleName("btn-login");
//		btn_Cancel.setWidth("170px");
//		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnCancel_clickHandler(e));
//
//		this.buttonsPanel.addComponent(btn_Cancel);
//		
//		this.buttonsPanel.addStyleName("bbr-buttons-panel");
//
//		this.buttonsPanel.setSpacing(true);
//	}
//
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			EVENTS HANDLERS
//	// ****************************************************************************************
//	private void btnCancel_clickHandler(ClickEvent e) 
//	{
//		this.close();
//	}
//
//
//
//	private void btnSolve_clickHandler(ClickEvent e) 
//	{
//		SendAnalystTicketOperation winVendorTicketOperation 
//		= new SendAnalystTicketOperation(this.selectedTicket,this.vendorId, this.vendorName,EnumTicketOperation.SOLVE);
//		winVendorTicketOperation.initializeView();
//		winVendorTicketOperation.addBbrEventListener((BbrEventListener & Serializable) event -> sendTicketOperation_handler(event));		
//		winVendorTicketOperation.open(this.getParentUI(), true);
//	}
//
//	
//	private void btnReply_clickHandler(ClickEvent e) 
//	{
//		SendAnalystTicketOperation winVendorTicketOperation 
//		= new SendAnalystTicketOperation(this.selectedTicket,this.vendorId, this.vendorName,EnumTicketOperation.REPLY);
//		winVendorTicketOperation.initializeView();
//		winVendorTicketOperation.addBbrEventListener((BbrEventListener & Serializable) event -> sendTicketOperation_handler(event));		
//		winVendorTicketOperation.open(this.getParentUI(), true);	
//	}
//
//	
//	private void sendTicketOperation_handler(BbrEvent event) 
//	{
//		if(event != null && event.getResultObject() != null)
//		{
//			TicketReportDataDTO item = (TicketReportDataDTO) event.getResultObject();
//			if(item != null)
//			{
//				this.selectedTicket.setCodestatus(item.getCodestatus());
//				this.selectedTicket.setInternalstatus(item.getInternalstatus());
//				this.selectedTicket.setProviderstatus(item.getProviderstatus());
//				
//				
//				this.ckEditorTextField.setValue(this.getBodyDetails(this.selectedTicket));
//				this.txtTicketStatus.setValue(item.getProviderstatus());
//				
//				this.updateButtonPanel();
//				
//				BbrEvent createEvent = new BbrEvent(BbrEvent.ITEM_UPDATED);
//				this.dispatchBbrEvent(createEvent);
//			}
//		}
//	}
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
//	private HorizontalLayout getTicketUserInfoLayout(TicketReportDataDTO ticket)
//	{
//		HorizontalLayout result = new HorizontalLayout();
//		if(ticket != null)
//		{
//			//Ticket Id
//			Label lblTicketId = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_id"));
//			lblTicketId.setWidth("100px");
//			Label txtTicketId = new Label(ticket.getTicketnumber().toString());
//
//			HorizontalLayout pnlTicketId = new HorizontalLayout();
//			pnlTicketId.setWidth("100%");
//			pnlTicketId.addComponents(lblTicketId,txtTicketId);
//			pnlTicketId.setExpandRatio(txtTicketId, 1F);
//			pnlTicketId.addStyleName("bbr-panel-space");
//
//			//Ticket Status
//			Label lblTicketStatus = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_status"));
//			lblTicketStatus.setWidth("100px");
//			txtTicketStatus = new Label(ticket.getProviderstatus().toString());
//			
//			HorizontalLayout pnlTicketStatus = new HorizontalLayout();
//			pnlTicketStatus.setWidth("100%");
//			pnlTicketStatus.addComponents(lblTicketStatus,txtTicketStatus);
//			pnlTicketStatus.setExpandRatio(txtTicketStatus, 1F);
//			pnlTicketStatus.addStyleName("bbr-panel-space");
//			
//			//Company
//			Label lblCompany = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "contact_company"));
//			lblCompany.setWidth("100px");
//			Label txtCompanyCode = new Label(ticket.getVendorrut());
//			txtCompanyCode.setWidth("100px");
//			Label txtCompanyName = new Label(ticket.getVendorname());
//
//			HorizontalLayout pnlCompany = new HorizontalLayout();
//			pnlCompany.setWidth("100%");
//			pnlCompany.addComponents(lblCompany,txtCompanyCode, txtCompanyName);
//			pnlCompany.setExpandRatio(txtCompanyName, 1F);
//			pnlCompany.addStyleName("bbr-panel-space");
//
//			//Created by
//			Label lblContact = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_created_by"));
//			lblContact.setWidth("100px");
//			Label txtContact = new Label(ticket.getUsername());
//
//			HorizontalLayout pnlContact = new HorizontalLayout();
//			pnlContact.setWidth("100%");
//			pnlContact.addComponents(lblContact,txtContact);
//			pnlContact.setExpandRatio(txtContact, 1F);
//			pnlContact.addStyleName("bbr-panel-space");
//
//			//Contact Position
//			Label lblContactPosition = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "contact_position"));
//			lblContactPosition.setWidth("100px");
//			Label txtContactPosition = new Label(ticket.getUserposition());
//
//			HorizontalLayout pnlContactPosition = new HorizontalLayout();
//			pnlContactPosition.setWidth("100%");
//			pnlContactPosition.addComponents(lblContactPosition,txtContactPosition);
//			pnlContactPosition.setExpandRatio(txtContactPosition, 1F);
//			pnlContactPosition.addStyleName("bbr-panel-space");
//
//			//Contact Email
//
//			Label lblContactEmail = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "contact_email"));
//			lblContactEmail.setWidth("100px");
//			Link txtContactEmail = new Link(ticket.getUsermail(), new ExternalResource("mailto:" + ticket.getUsermail()));
//
//			HorizontalLayout pnlContactEmail = new HorizontalLayout();
//			pnlContactEmail.setWidth("100%");
//			pnlContactEmail.addComponents(lblContactEmail,txtContactEmail);
//			pnlContactEmail.setExpandRatio(txtContactEmail, 1F);
//			pnlContactEmail.addStyleName("bbr-panel-space");
//
//			//Contact Phone
//			Label lblContactPhone = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "contact_phone"));
//			lblContactPhone.setWidth("100px");
//			Label txtContactPhone = new Label(ticket.getUserphone());
//
//			HorizontalLayout pnlContactPhone = new HorizontalLayout();
//			pnlContactPhone.setWidth("100%");
//			pnlContactPhone.addComponents(lblContactPhone,txtContactPhone);
//			pnlContactPhone.setExpandRatio(txtContactPhone, 1F);
//			pnlContactPhone.addStyleName("bbr-panel-space");
//
//			//Case Type
//			Label lblCaseType = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "case_type"));
//			lblCaseType.setWidth("100px");
//			Label txtCaseType = new Label(ticket.getTickettype());
//			
//			HorizontalLayout pnlCaseType = new HorizontalLayout();
//			pnlCaseType.setWidth("100%");
//			pnlCaseType.addComponents(lblCaseType,txtCaseType);
//			pnlCaseType.setExpandRatio(txtCaseType, 1F);
//			pnlCaseType.addStyleName("bbr-panel-space");
//
//			//Title
//			Label lblTitle = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "title"));
//			lblTitle.setWidth("100px");
//			Label txt_Title = new Label(ticket.getReference());
//
//			HorizontalLayout pnlTitle = new HorizontalLayout();
//			pnlTitle.setWidth("100%");
//			pnlTitle.addComponents(lblTitle,txt_Title);
//			pnlTitle.setExpandRatio(txt_Title, 1F);
//			pnlTitle.addStyleName("bbr-panel-space");
//
//			//Details
//			Label lblDetails = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_details"));
//			lblDetails.setWidth("100px");
//			HorizontalLayout detailsLayout = this.getTicketsLayout(ticket);
//			HorizontalLayout pnlDetails = new HorizontalLayout();
//			pnlDetails.setWidth("100%");
//			pnlDetails.setHeight("100%");
//			pnlDetails.addComponents(lblDetails,detailsLayout);
//			pnlDetails.setExpandRatio(detailsLayout, 1F);
//			pnlDetails.addStyleName("bbr-panel-space");
//
//			VerticalLayout pnlMessageArea = new VerticalLayout();
//			pnlMessageArea.addComponents(pnlTicketId, pnlTicketStatus, pnlCompany,pnlContact,pnlContactPosition,pnlContactEmail, pnlContactPhone, pnlCaseType,pnlTitle,pnlDetails);
//			pnlMessageArea.setHeight("100%");
//			pnlMessageArea.setExpandRatio(pnlDetails, 1F);
//
//
//			result = new HorizontalLayout();
//			result.setSizeFull();
//			result.addComponents(pnlMessageArea);
//			result.setExpandRatio(pnlMessageArea, 1F);	
//		}
//		
//
//		return result;
//	}
//
//	private HorizontalLayout getTicketsLayout(TicketReportDataDTO ticket)
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
//		
//		ckEditorTextField = new CKEditorTextField(config);
//		ckEditorTextField.setHeight("100%");
//		ckEditorTextField.setViewWithoutEditor(true);
//		ckEditorTextField.setValue(this.getBodyDetails(ticket));
//		ckEditorTextField.addStyleName("ck-editor-reader");
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
//
//	private TicketDetailReportDataDTO[] getTicketDetails(TicketReportDataDTO vendorTicket) 
//	{
//		TicketDetailReportDataDTO[] result = null;
//		if(vendorTicket != null)
//		{
//			TicketDetailReportInitParamDTO initParams = this.getDetailsInitParams(vendorTicket);
//
//			try 
//			{
//				TicketDetailReportResultDTO ticketResult = EJBFactory.getTicketsEJBFactory().getTicketReportManagerLocal().getAnalystTicketDetailReport(initParams);
//				if(ticketResult != null && ticketResult.getReport() != null && ticketResult.getReport().length>0)
//				{
//					result = ticketResult.getReport();
//				}
//			} 
//			catch (BbrUserException | BbrSystemException e) 
//			{
//				e.printStackTrace();
//			}
//		}
//		
//		return result;
//	}
//
//	private TicketDetailReportInitParamDTO getDetailsInitParams(TicketReportDataDTO vendorTicket)
//	{
//		TicketDetailReportInitParamDTO result = new TicketDetailReportInitParamDTO();
//		result.setTicketId(vendorTicket.getTicketid());
//		result.setUserId(this.getUser().getId());
//		result.setVendorId(this.vendorId);
//
//		return result;
//	}
//	
//	private String getBodyDetails(TicketReportDataDTO ticket)
//	{
//		String result = "";
//		
//		TicketDetailReportDataDTO[] details = this.getTicketDetails(ticket); 
//		if(details != null && details.length > 0)
//		{
//			StringBuilder sb = new StringBuilder();
//			for (TicketDetailReportDataDTO item : details) 
//			{
//				sb.append("<b>");
//				sb.append(BbrDateUtils.getInstance().dateToShortDateTime(item.getEventdate()));
//				sb.append(" - ");
//				sb.append(item.getUsername());
//				sb.append(" - ");
//				sb.append(item.getTicketstatus());
//				sb.append("</b>");
//				sb.append("<br />");
//				sb.append(item.getEventcomment());
//				sb.append("<hr />");
//			}
//			
//			result = sb.toString();
//		}
//		return result;
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//
//
//}
