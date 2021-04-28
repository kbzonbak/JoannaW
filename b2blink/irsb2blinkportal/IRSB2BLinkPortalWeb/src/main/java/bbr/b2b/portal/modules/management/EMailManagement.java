//package bbr.b2b.portal.modules.management;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import com.vaadin.event.ItemClickEvent;
//import com.vaadin.event.ItemClickEvent.ItemClickListener;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.Grid.SelectionMode;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.portal.classes.constants.FunctionalitiesCodes;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.jms.constants.MessageTopics;
//import bbr.b2b.portal.classes.managers.FunctionalityMngr;
//import bbr.b2b.portal.classes.utils.app.AppUtils;
//import bbr.b2b.portal.components.basics.BbrSystemException;
//import bbr.b2b.portal.components.basics.BbrUserException;
//import bbr.b2b.portal.components.modules.management.EditEmailMessage;
//import bbr.b2b.users.report.classes.MaintainerEmailArrayResultDTO;
//import bbr.b2b.users.report.classes.MaintainerEmailDTO;
//import cl.bbr.core.classes.basics.BbrUser;
//import cl.bbr.core.classes.constants.CoreConstants;
//import cl.bbr.core.classes.constants.TrackingConstants;
//import cl.bbr.core.classes.errors.BbrError;
//import cl.bbr.core.classes.errors.ErrorsMngr;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.classes.events.BbrEventListener;
//import cl.bbr.core.classes.events.BbrMessageEvent;
//import cl.bbr.core.classes.events.BbrMessageEventListener;
//import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
//import cl.bbr.core.classes.utilities.BbrUtils;
//import cl.bbr.core.components.basics.BbrMessageBox;
//import cl.bbr.core.components.basics.BbrModule;
//import cl.bbr.core.components.basics.BbrUI;
//import cl.bbr.core.components.grid.BbrAdvancedGrid;
//import cl.bbr.core.components.grid.BbrColumn;
//
//public class EMailManagement extends BbrModule 
//{
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = 7027502352762624910L;
//
//	private BbrAdvancedGrid<MaintainerEmailDTO> dgd_EmailMessages ;
//	private VerticalLayout mainLayout ;
//
//	private final String SUBJECT_FIELD 		= "subject" ;
//	private final String SENDER_FIELD 		= "sender" ;
//	private final String TYPE_FIELD 		= "type" ;
//
//	private BbrMessageEventListener messagingListener = null;
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//	public EMailManagement(BbrUI bbrUIParent) 
//	{
//		super(bbrUIParent);
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
//		BbrUser user = this.getUser();
//		if(user != null)
//		{
//			//Reporte
//			//Barra de Herramientas
//			HorizontalLayout leftButtonsBar = new HorizontalLayout();
//			leftButtonsBar.setSpacing(true);
//			leftButtonsBar.setMargin(false);
//			leftButtonsBar.setHeight("30px");
//			leftButtonsBar.addStyleName("toolbar-layout");
//
//			if(FunctionalityMngr.getInstance().hasFunctionalityAssigned(FunctionalitiesCodes.USR_CU111))
//			{
//				Button btn_EditEmail 	= new Button("", BbrThemeResourcesUtils.getInstance().getResource(CoreConstants.PATH_BASE_IMAGES_BUTTONS+"icon_MailEdit.png"));
//				btn_EditEmail.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_email_message"));
//				btn_EditEmail.addClickListener((ClickListener & Serializable)e -> editEmail_clickHandler(e));
//				btn_EditEmail.addStyleName("toolbar-button");
//
//				leftButtonsBar.addComponents(btn_EditEmail);
//			}
//
//			Button btn_Refresh 		= new Button("", BbrThemeResourcesUtils.getInstance().getResource(CoreConstants.PATH_BASE_IMAGES_BUTTONS+"icon_Refresh.png"));
//			btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
//			btn_Refresh.addClickListener((ClickListener & Serializable)e -> refreshReport_clickHandler(e));
//			btn_Refresh.addStyleName("toolbar-button");
//
//			HorizontalLayout rightButtonsBar = new HorizontalLayout();
//
//			rightButtonsBar.addComponents(btn_Refresh);
//			rightButtonsBar.setSpacing(true);
//			rightButtonsBar.setMargin(false);
//			rightButtonsBar.setHeight("30px");
//			rightButtonsBar.addStyleName("toolbar-layout");
//
//			rightButtonsBar.setComponentAlignment(btn_Refresh, Alignment.MIDDLE_RIGHT);
//
//			HorizontalLayout toolBar = new HorizontalLayout();
//			toolBar.setWidth("100%");
//			toolBar.addComponents(leftButtonsBar,rightButtonsBar);
//			toolBar.addStyleName("filter-toolbar");
//			toolBar.setExpandRatio(leftButtonsBar, 1F);
//			toolBar.setExpandRatio(rightButtonsBar, 1F);
//
//			toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
//			toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);
//
//			//Grilla
//			dgd_EmailMessages = new BbrAdvancedGrid<>(MaintainerEmailDTO.class, this.getUser().getLocale());
//
//			dgd_EmailMessages.setBbrColumns(this.getReportColumns(),MaintainerEmailDTO.class);
//			dgd_EmailMessages.addStyleName("report-grid");
//			dgd_EmailMessages.setSizeFull();
//			dgd_EmailMessages.setLocale(this.getUser().getLocale());
//			dgd_EmailMessages.setSelectionMode(SelectionMode.SINGLE);
//			dgd_EmailMessages.addItemClickListener((ItemClickListener & Serializable)e -> dgdItem_clickHandler(e));
//
//
//			mainLayout = new VerticalLayout();
//			mainLayout.addStyleName("report-layout-no-filter");
//			mainLayout.setSizeFull();
//			mainLayout.addComponents(toolBar,dgd_EmailMessages);
//			mainLayout.setExpandRatio(dgd_EmailMessages, 1F);
//
//			this.updateReport(true);
//
//			this.addComponents(mainLayout);
//		}
//		else
//		{
//			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0016"));
//		}
//
//
//	}
//
//	@Override
//	public void attach() 
//	{
//		super.attach();
//		this.messagingListener = (BbrMessageEventListener & Serializable)e -> bbrMessage_Listener(e);
//		((BbrUI) UI.getCurrent()).getMessagingEventMngr().addBbrMessageListener(messagingListener, MessageTopics.ADM_EMAIL_MESSAGES);
//	}
//
//	@Override
//	public void detach() 
//	{
//		super.detach();
//		((BbrUI) UI.getCurrent()).getMessagingEventMngr().removeBbrMessageListener(messagingListener, MessageTopics.ADM_EMAIL_MESSAGES);
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//
//
//
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			EVENTS HANDLERS
//	// ****************************************************************************************
//	private void refreshReport_clickHandler(ClickEvent event) 
//	{
//		this.updateReport(true);
//	}
//
//	private void editEmail_clickHandler(ClickEvent event) 
//	{
//		MaintainerEmailDTO selectedMessage = (MaintainerEmailDTO) dgd_EmailMessages.getSelectedRow();
//		this.doEditEmailMessage(selectedMessage);
//	}
//	
//	private void dgdItem_clickHandler(ItemClickEvent event) 
//	{
//		if (event.isDoubleClick()) 
//        {
//			MaintainerEmailDTO selectedMessage = (MaintainerEmailDTO) event.getItemId();
//			this.doEditEmailMessage(selectedMessage);
//        }
//	}
//	
//	private void emailUpdated_handler(BbrEvent e) 
//	{
//		if(e.getEventType().equals(BbrEvent.ITEM_UPDATED))
//		{
//			updateReport(false);
//		}
//	}
//
//	private void bbrMessage_Listener(BbrMessageEvent event) 
//	{
//		if(event != null && event.getMessage() != null && event.getMessage().getSenderBbrUser() != null)
//		{
//			BbrUser sender = event.getMessage().getSenderBbrUser();
//			Boolean trackReport = (sender.getId().equals(this.getUser().getId()));
//
//			updateReport(trackReport);	
//
//		}
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			EVENTS HANDLERS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//
//	private ArrayList<BbrColumn<?>> getReportColumns()
//	{
//		ArrayList<BbrColumn<?>> result = new ArrayList<BbrColumn<?>>();
//
//		BbrColumn<String> col_Type 			= new BbrColumn<>(this.TYPE_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "email_type"));
//		BbrColumn<String> col_Subject 		= new BbrColumn<>(this.SUBJECT_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "email_subject"));
//		BbrColumn<String> col_Activity		= new BbrColumn<>(this.SENDER_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "email_sender"));
//
//		result.add(col_Type);
//		result.add(col_Subject);
//		result.add(col_Activity);
//
//		return result;
//	}
//
//	private void updateReport(Boolean trackReport) 
//	{
//		String errordescription = "";
//
//		try 
//		{
//			//Start Tracking
//			this.getTimingMngr().startTimer();
//
//			MaintainerEmailArrayResultDTO emailsReportResult = EJBFactory.getUserEJBFactory().getMaintainerEmailReportManagerLocal().getMaintainerEmails();
//			if(emailsReportResult != null)
//			{
//				BbrError error = ErrorsMngr.getInstance().getError(emailsReportResult, this.getBbrUIParent(), true);
//				if(!error.hasError())
//				{
//					dgd_EmailMessages.setRows(Arrays.asList(emailsReportResult.getMaintainerEmailDTOs()),true);
//
//					//End Tracking
//					if(trackReport == true)
//					{
//						this.trackEvent(TrackingConstants.REPORT_VIEW, this.getBbrFilterDescription());	
//					}
//				}
//				else
//				{
//					errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error",error.getErrorCode(), error.getErrorMessage());
//				}
//			}
//
//		} 
//		catch (BbrUserException e) 
//		{
//			AppUtils.getInstance().doLogOut(e.getMessage());
//		} 
//		catch (BbrSystemException e) 
//		{
//			e.printStackTrace();
//		}
//		catch (Exception e) 
//		{
//			errordescription = BbrUtils.getInstance().substitute("{0}",e.getMessage());
//			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P200"));
//		}
//
//		if(errordescription.length() > 0 && trackReport == true)
//		{
//			//Track Error
//			this.trackError(TrackingConstants.REPORT_VIEW, this.getBbrFilterDescription(), errordescription, null, this);	
//		}
//	}
//
//	private void doEditEmailMessage(MaintainerEmailDTO selectedMessage)
//	{
//		if(selectedMessage != null)
//		{
//			EditEmailMessage winEditFaq = new EditEmailMessage(selectedMessage);
//			winEditFaq.initializeView();
//			winEditFaq.addBbrEventListener((BbrEventListener & Serializable)e -> emailUpdated_handler(e));
//			winEditFaq.open(getBbrUIParent(), true);	
//		}
//		else
//		{
//			BbrMessageBox
//			.createWarning()
//			.withOkButton()
//			.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"))
//			.withMessage(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "email_message_required"))
//			.open();
//		}	
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//
//}
