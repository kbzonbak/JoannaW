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
//public class WinPasswordRecovery extends BbrWindow {
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = -2720489231076965593L;
//	private String selectedUserName = "";
//	
//	private BbrTextField txt_UserName = null;
//	private BbrTextField txt_UserEmail = null;
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//	public WinPasswordRecovery(BbrUI parent, String userName) 
//	{
//		super(parent);
//		if(userName != null)
//		{
//			selectedUserName = userName.trim();
//		}
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
//		//User Name Field
//		txt_UserName = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "usercode"));
//		txt_UserName.addStyleName("login-text-field");
//		txt_UserName.setIcon(VaadinIcons.USER);
//		txt_UserName.setWidth("100%");
//		txt_UserName.setValue(this.selectedUserName);
//
//		HorizontalLayout userCodeLayout = new HorizontalLayout(txt_UserName);
//		userCodeLayout.setWidth("100%");
//
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
//				String userRut 			= txt_UserName.getValue() ;
//				String userMail 	= txt_UserEmail.getValue() ;
//
//				String result = doRecoverPassword(userRut, userMail);
//
//				if(result != null && result.length() <= 0)
//				{
//					String message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0005");
//
//					BbrEvent bbrEvent = new BbrEvent(BbrEvent.USER_PASSWORD_RECOVERED);
//					bbrEvent.setResultObject(message);
//
//					dispatchBbrEvent(bbrEvent);
//					WinPasswordRecovery.this.close();
//				}
//				else
//				{
//					BbrMessageBox
//					.createWarning(WinPasswordRecovery.this.getParentUI())
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
//		frmRecoverPassword.setId("frmRecoverPassword");
//		frmRecoverPassword.setMargin(true);
//		frmRecoverPassword.setWidth(310, Unit.PIXELS);
//		frmRecoverPassword.setHeight(100, Unit.PERCENTAGE);
//		frmRecoverPassword.addComponents(userCodeLayout, userEmailLayout,btn_Send);
//		frmRecoverPassword.setComponentAlignment(btn_Send, Alignment.BOTTOM_CENTER);
//
//		// Main Layout
//		VerticalLayout mainLayout = new VerticalLayout();
//		mainLayout.addComponent(frmRecoverPassword);
//		//mainLayout.setMargin(new MarginInfo(false, true, false, true));		
//		mainLayout.setMargin(false);		
//		mainLayout.setSizeFull();		
//
//		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "generate_password"));
//		this.setWidth("330px");
//		this.setHeight("230px");
//		this.setResizable(false);
//		this.setContent(mainLayout);
//
//		txt_UserName.focus();
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
//	String doRecoverPassword(String userCode , String userMail)
//	{
//		String result = null;
//
//		if(userMail.length() > 0 && userCode.length()>0)
//		{
//			if(BbrUtils.getInstance().checkEmailAddress(userMail))
//			{
//				//URL del cambio de contraseña.
//				String changeUrl = this.getCurrentURL().replace(this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "login"), this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "change"));
//
//				UserResultDTO userResult = EJBFactory.getUserOpenEJBFactory().recoverPasswordFirstPhase(userCode, userMail, changeUrl);
//
//				result = I18NManager.getErrorMessageBaseResult(userResult,userResult.getParams()); // <-- Obtiene el mensaje de error. "" si no hay errores.
//				
//				if(result!= null && result.length() > 0)
//				{
//					clearForm(false) ;
//				}
//			}
//			else // <-- Formato de correo no válido.
//			{
//				result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_mail");
//				
//				clearForm(true) ;
//			}
//
//		}
//		else // <-- Falta Usuario o Email
//		{
//			result = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U6000");
//		}
//
//
//		return result;
//	}
//
//	
//	private void clearForm(Boolean onlyMail) 
//	{
//		txt_UserEmail.setValue("");
//		if(!onlyMail)
//		{
//			txt_UserName.setValue("");
//			txt_UserName.focus();	
//		}
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//}
