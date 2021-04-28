//package bbr.b2b.portal.components.main;
//
//import com.vaadin.icons.VaadinIcons;
//import com.vaadin.shared.ui.MarginInfo;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.FormLayout;
//import com.vaadin.ui.PasswordField;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.utils.app.AppUtils;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.users.report.classes.UserResultDTO;
//import cl.bbr.core.classes.basics.BbrUser;
//import cl.bbr.core.classes.utilities.BbrUtils;
//import cl.bbr.core.components.basics.BbrMessageBox;
//import cl.bbr.core.components.basics.BbrUI;
//import cl.bbr.core.components.basics.BbrWindow;
//
//public class WinChangePassword extends BbrWindow 
//{
//		// ****************************************************************************************
//		// BEGINNING SECTION 	---->			PROPERTIES
//		// ****************************************************************************************
//		private static final long serialVersionUID = 7097156314416889299L;
//
//		private PasswordField txt_Password 				= null;
//		private PasswordField txt_NewPassword 			= null;
//		private PasswordField txt_ConfirmNewPassword 	= null ;
//
//		// ****************************************************************************************
//		// ENDING SECTION 		---->			PROPERTIES
//		// ****************************************************************************************
//
//		// ****************************************************************************************
//		// BEGINNING SECTION 	---->			CONSTRUCTORS
//		// ****************************************************************************************
//		public WinChangePassword(BbrUI parent) 
//		{
//			super(parent);
//		}
//
//		// ****************************************************************************************
//		// ENDING SECTION 		---->			CONSTRUCTORS
//		// ****************************************************************************************
//
//
//		// ****************************************************************************************
//		// BEGINNING SECTION 	---->			INTERFACE IMPLEMENTATIONS
//		// ****************************************************************************************
//
//		// ****************************************************************************************
//		// ENDING SECTION 		---->			INTERFACE IMPLEMENTATIONS
//		// ****************************************************************************************
//
//
//		// ****************************************************************************************
//		// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
//		// ****************************************************************************************
//		@Override
//		public void initializeView() 
//		{
//			//New Password Field
//			txt_Password = new PasswordField(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "current_password"));
//			txt_Password.addStyleName("login-text-field");
//			txt_Password.setIcon(VaadinIcons.LOCK);	
//			txt_Password.setWidth("100%");
//			txt_Password.setMaxLength(10);
//			//New Password Field
//			txt_NewPassword = new PasswordField(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "new_password"));
//			txt_NewPassword.addStyleName("login-text-field");
//			txt_NewPassword.setIcon(VaadinIcons.LOCK);	
//			txt_NewPassword.setWidth("100%");	
//			txt_NewPassword.setMaxLength(10);	
//
//			//Confirm Password Field
//			txt_ConfirmNewPassword = new PasswordField(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "confirm_new_password"));
//			txt_ConfirmNewPassword.addStyleName("login-text-field");
//			txt_ConfirmNewPassword.setIcon(VaadinIcons.LOCK);	
//			txt_ConfirmNewPassword.setWidth("100%");
//			txt_ConfirmNewPassword.setMaxLength(10);
//
//			//Send Button
//			Button btn_Send = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "change_password"));
//			btn_Send.setStyleName("primary");
//			btn_Send.addStyleName("btn-login");
//			btn_Send.setWidth("100%");
//			btn_Send.setDisableOnClick(true);
//			btn_Send.addClickListener(new ClickListener() 
//			{
//				private static final long serialVersionUID = 5860813875879559449L;
//
//				@Override
//				public void buttonClick(ClickEvent event) 
//				{
//					String result = WinChangePassword.this.doChangePassword();
//
//					if(result != null && result.length() <= 0)
//					{
//						String message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0014");
//						
//						WinChangePassword.this.close();
//						
//						BbrMessageBox
//						.createInfo(WinChangePassword.this.getParentUI())
//						.withCloseButton(new Runnable() {
//							@Override
//							public void run() {
//								AppUtils.getInstance().doLogOut();
//							}
//						})
//						.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"))
//						.withHtmlMessage(message)
//						.withWidthForAllButtons("100px")
//						.open();						
//					}
//					else
//					{
//						WinChangePassword.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), result);
//					}
//
//					btn_Send.setEnabled(true);
//				}
//			});
//
//			//FormLayout as Panel Content
//			FormLayout frmChangePassword = new FormLayout();
//			frmChangePassword.setMargin(true);
//			frmChangePassword.setWidth(400, Unit.PIXELS);
//			frmChangePassword.setHeight(100, Unit.PERCENTAGE);
//			frmChangePassword.addComponents(txt_Password, txt_NewPassword, txt_ConfirmNewPassword, btn_Send);
//			frmChangePassword.setComponentAlignment(btn_Send, Alignment.BOTTOM_CENTER);
//
//			// Main Layout
//			VerticalLayout mainLayout = new VerticalLayout();
//			mainLayout.addComponent(frmChangePassword);
//			mainLayout.setMargin(new MarginInfo(false, true, false, true));		
//			mainLayout.setSizeFull();		
//
//			this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "change_password"));
//			this.setWidth("450px");
//			this.setHeight("250px");
//			this.setResizable(false);
//			this.setContent(mainLayout);
//
//			txt_Password.focus();
//		}
//
//
//
//		// ****************************************************************************************
//		// ENDING SECTION 		---->			OVERRIDDEN METHODS
//		// ****************************************************************************************
//
//
//		// ****************************************************************************************
//		// BEGINNING SECTION 	---->			PUBLIC METHODS
//		// ****************************************************************************************
//
//		// ****************************************************************************************
//		// ENDING SECTION 		---->			PUBLIC METHODS
//		// ****************************************************************************************
//
//
//		// ****************************************************************************************
//		// BEGINNING SECTION 	---->			PRIVATE METHODS
//		// ****************************************************************************************
//		String doChangePassword()
//		{
//			String result = "";
//
//			String password 			= this.txt_Password.getValue();
//			String newPassword 			= this.txt_NewPassword.getValue();
//			String confirmNewPassword 	= this.txt_ConfirmNewPassword.getValue();
//
//			if((password != null && password.length() > 0) 
//					&& (newPassword!= null && newPassword.length() > 0) 
//					&& (confirmNewPassword != null && confirmNewPassword.length() > 0))
//			{
//				if(newPassword.equals(confirmNewPassword))
//				{
//					int minPass = Integer.parseInt(getPropertyValue(BbrUtilsResources.RES_B2B_SYSTEM, "password_min_length"));
//					int maxPass = Integer.parseInt(getPropertyValue(BbrUtilsResources.RES_B2B_SYSTEM, "password_max_length"));
//
//					if((newPassword.length() >= minPass && newPassword.length() <= maxPass) && BbrUtils.getInstance().isAlphaNumericRegEx(newPassword))
//					{
//						BbrUser user = this.getUser(); 
//						if(user!=null)
//						{
//							UserResultDTO userResult = EJBFactory.getUserOpenEJBFactory().changePasswordByUser(user.getId(), password, newPassword, confirmNewPassword);
//							if(userResult != null)
//							{
//								result = I18NManager.getErrorMessageBaseResult(userResult, userResult.getParams()); // <-- Obtiene el mensaje de error. "" si no hay errores.
//							}
//							else // <-- Error changePasswordByUser() --- result == null
//							{
//								result = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0011");
//							}
//							result = I18NManager.getErrorMessageBaseResult(userResult, userResult.getParams()); // <-- Obtiene el mensaje de error. "" si no hay errores.
//						}
//					}
//					else
//					{
//						//Formato de la nueva clave no válido.
//						result = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U5028");	
//					}
//
//				}
//				else
//				{
//					//No coinciden las claves (nueva y la confirmación)
//					result = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U5022");
//				}
//
//			}
//			else
//			{
//				// Faltan campos
//				result = I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields");
//			}
//
//			return result;
//		}
//		// ****************************************************************************************
//		// ENDING SECTION 		---->			PRIVATE METHODS
//		// ****************************************************************************************
//}
