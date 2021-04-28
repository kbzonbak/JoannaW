//package bbr.b2b.portal.components.login;
//
//import com.vaadin.icons.VaadinIcons;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.FormLayout;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.users.report.classes.UserResultDTO;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.classes.utilities.BbrUtils;
//import cl.bbr.core.components.basics.BbrMessageBox;
//import cl.bbr.core.components.basics.BbrUI;
//import cl.bbr.core.components.basics.BbrWindow;
//import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
//
//public class WinUserNameRecovery extends BbrWindow {
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = -2720489231076965593L;
//	
//	private BbrTextField txt_UserEmail = null;
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//	public WinUserNameRecovery(BbrUI parent) 
//	{
//		super(parent);
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			CONSTRUCTORS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			INTERFACE IMPLEMENTATIONS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			INTERFACE IMPLEMENTATIONS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//	@Override
//	public void initializeView() 
//	{
//		//Email Code Field
//		txt_UserEmail = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "email"));
//		txt_UserEmail.addStyleName("login-text-field");
//		txt_UserEmail.setIcon(VaadinIcons.ENVELOPE);
//		txt_UserEmail.setWidth("100%");
//
//		HorizontalLayout userEmailLayout = new HorizontalLayout(txt_UserEmail);
//		userEmailLayout.setWidth("100%");
//
//		//Send Button
//		Button btn_Send = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "send"));
//		btn_Send.setStyleName("primary");
//		btn_Send.addStyleName("btn-generic");
//		btn_Send.setDisableOnClick(true);
//		btn_Send.setWidth("100%");
//		btn_Send.addClickListener(new ClickListener() 
//		{
//			private static final long serialVersionUID = -468813083095455289L;
//
//			@Override
//			public void buttonClick(ClickEvent event) 
//			{
//				String userMail 	= txt_UserEmail.getValue() ;
//
//				String result = doRecoverUserName(userMail);
//
//				if(result != null && result.length() <= 0)
//				{
//					String message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0017");
//
//					BbrEvent bbrEvent = new BbrEvent(BbrEvent.USER_LOGID_RECOVERED);
//					bbrEvent.setResultObject(message);
//
//					dispatchBbrEvent(bbrEvent);
//					WinUserNameRecovery.this.close();
//				}
//				else
//				{
//					BbrMessageBox
//					.createWarning(WinUserNameRecovery.this.getBbrUIParent())
//					.withCloseButton()
//					.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"))
//					.withHtmlMessage(result)
//					.open();
//				}
//
//				btn_Send.setEnabled(true);
//			}
//		});
//
//		//FormLayout as Panel Content
//		FormLayout frmRecoverPassword = new FormLayout();
//		frmRecoverPassword.setMargin(true);
//		frmRecoverPassword.setWidth(310, Unit.PIXELS);
//		frmRecoverPassword.setHeight(100, Unit.PERCENTAGE);
//		frmRecoverPassword.addComponents(userEmailLayout,btn_Send);
//		frmRecoverPassword.setComponentAlignment(btn_Send, Alignment.BOTTOM_CENTER);
//
//		// Main Layout
//		VerticalLayout mainLayout = new VerticalLayout();
//		mainLayout.addComponent(frmRecoverPassword);
//		mainLayout.setMargin(false);		
//		mainLayout.setSizeFull();		
//
//		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "title_recovery_user"));
//		this.setWidth("330px");
//		this.setHeight("165px");
//		this.setResizable(false);
//		this.setContent(mainLayout);
//
//		txt_UserEmail.focus();
//	}
//
//
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PUBLIC METHODS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PUBLIC METHODS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
//	String doRecoverUserName(String userMail)
//	{
//		String result = null;
//
//		if(userMail.length() > 0)
//		{
//			if(BbrUtils.getInstance().checkEmailAddress(userMail))
//			{
//				UserResultDTO userResult = EJBFactory.getUserOpenEJBFactory().recoverLogId(userMail);
//
//				result = I18NManager.getErrorMessageBaseResult(userResult,userResult.getParams()); // <-- Obtiene el mensaje de error. "" si no hay errores.
//				
//				if(result!= null && result.length() > 0)
//				{
//					clearForm() ;
//				}
//			}
//			else // <-- Formato de correo no válido.
//			{
//				result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_mail");
//				
//				clearForm() ;
//			}
//
//		}
//		else // <-- Falta Email
//		{
//			result = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U6002");
//		}
//
//
//		return result;
//	}
//
//	
//	private void clearForm() 
//	{
//		txt_UserEmail.setValue("");
//		txt_UserEmail.focus();
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//}
