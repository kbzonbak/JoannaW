//package bbr.b2b.portal.components.modules.tickets;
//
//import java.io.Serializable;
//
//import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
//import org.vaadin.openesignforms.ckeditor.CKEditorTextField;
//
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.portal.classes.constants.BbrPublishingConstants;
//import bbr.b2b.portal.classes.constants.EnumTicketOperation;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.wrappers.management.AnalystTicketOperationInfo;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.trac.report.classes.AnalystTicketInitParamDTO;
//import bbr.b2b.trac.report.classes.TicketReportDataDTO;
//import bbr.b2b.trac.report.classes.TicketReportResultDTO;
//import cl.bbr.core.classes.basics.BbrUser;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.components.basics.BbrUI;
//import cl.bbr.core.components.basics.BbrWindow;
//
//public class SendAnalystTicketOperation extends BbrWindow 
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
//	private EnumTicketOperation					ticketOperation		= null;
//	
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//	public SendAnalystTicketOperation(BbrUI parent, TicketReportDataDTO selectedTicket, Long vendorId, String vendorName, EnumTicketOperation ticketOperation) 
//	{
//		super(parent);
//		this.selectedTicket 	= selectedTicket;
//		this.vendorId 			= vendorId;
//		this.vendorName			= vendorName;
//		this.ticketOperation 	= ticketOperation;
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
//			
//			HorizontalLayout pnlTicketLayout = this.getTicketUserInfoLayout(this.getUser());
//
//			//Accept Button
//			Button btn_Create = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "send"));
//			btn_Create.setStyleName("primary");
//			btn_Create.addStyleName("btn-login");
//			btn_Create.setWidth("140px");
//			btn_Create.addClickListener((ClickListener & Serializable) e -> btnDoOperation_clickHandler(e));
//
//			//Cancel Button
//			Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
//			btn_Cancel.setStyleName("primary");
//			btn_Cancel.addStyleName("btn-login");
//			btn_Cancel.setWidth("140px");
//			btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));
//
//			HorizontalLayout buttonsPanel = new HorizontalLayout(btn_Create, btn_Cancel);
//			buttonsPanel.addStyleName("bbr-buttons-panel");
//
//			
//			buttonsPanel.setSpacing(true);
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
//			this.setWidth(800,Unit.PIXELS);
//			this.setHeight(400,Unit.PIXELS);
//			this.setResizable(false);
//			this.setContent(mainLayout);
//			this.setCaption(this.getWinTitle() + " - ID: " + this.selectedTicket.getTicketnumber());
//		}
//		
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
//	private void btnClose_clickHandler(ClickEvent e) 
//	{
//		this.close();
//	}
//
//	private void btnDoOperation_clickHandler(ClickEvent e) 
//	{
//		AnalystTicketOperationInfo vendorTicketInfo = new AnalystTicketOperationInfo(this.selectedTicket, this.vendorId, 
//				this.vendorName, this.ckEditorTextField.getValue(),this.ticketOperation, this.getUser());
//		//Create
//		this.doTicketOperation(vendorTicketInfo);
//	}
//
//	private void doTicketOperation(AnalystTicketOperationInfo vendorTicketOperationInfo) 
//	{
//		String message = "";
//		TicketReportResultDTO 	ticketResult = null ;
//		try 
//		{
//			if(vendorTicketOperationInfo != null)
//			{
//				message = vendorTicketOperationInfo.validateCreateData();
//				if(message == null || message.length() == 0)
//				{
//
//					AnalystTicketInitParamDTO initParams = vendorTicketOperationInfo.toAnalystTicketInitParamDTO();
//
//					switch (this.ticketOperation) 
//					{
//					case SOLVE:
//						ticketResult 	= EJBFactory.getTicketsEJBFactory().getTicketReportManagerLocal().doResolveAnalystTicket(initParams);
//						break;
//					case REPLY:
//						ticketResult 	= EJBFactory.getTicketsEJBFactory().getTicketReportManagerLocal().doResponseAnalystTicket(initParams);
//						break;
//
//					default:
//						break;
//					}
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
//			
//			if(ticketResult.getReport() != null && ticketResult.getReport().length>0)
//			{
//				BbrEvent createEvent = new BbrEvent(BbrEvent.ITEM_CREATED);
//				createEvent.setResultObject(ticketResult.getReport()[0]);
//				this.dispatchBbrEvent(createEvent);
//				this.close();
//			}
//			
//			
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
//	private HorizontalLayout getTicketUserInfoLayout(BbrUser user)
//	{
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
//		pnlMessageArea.addComponents(pnlDetails);
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
//	
//	private String getWinTitle() 
//	{
//		String result = "";
//		switch (this.ticketOperation) 
//		{
//		case ACCEPT:
//			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "accept_win_title");
//			break;
//		case CLOSE:
//			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "close_win_title");
//			break;
//		case REOPEN:
//			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "reopen_win_title");
//			break;
//		case REPLY:
//			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "reply_win_title");
//			break;
//		case SOLVE:
//			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "solve_win_title");
//			break;
//
//		default:
//			break;
//		}
//		
//		return result;
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//
//
//}
