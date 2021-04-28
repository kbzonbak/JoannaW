//package bbr.b2b.portal.classes.ui;
//
//import com.vaadin.annotations.Theme;
//import com.vaadin.server.Resource;
//import com.vaadin.server.Responsive;
//import com.vaadin.server.ThemeResource;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.shared.ui.ContentMode;
//import com.vaadin.shared.ui.MarginInfo;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Image;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.portal.classes.basics.LoginInfo;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.components.login.ChangePasswordPanel;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.users.report.classes.UserDTO;
//import bbr.b2b.users.report.classes.UserResultDTO;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.classes.events.BbrEventListener;
//import cl.bbr.core.classes.utilities.BbrUtils;
//import cl.bbr.core.components.basics.BbrMessageBox;
//import cl.bbr.core.components.basics.BbrUI;
//
//
///**
// * @author DSU 2016-03
// */
//
//@Theme("irsb2blinkportalweb")
//public class ChangePasswordUI extends BbrUI
//{
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = 5882724157372379105L;
//	private ChangePasswordPanel changePasswordForm;
//
//	private UserDTO currentUser ;
//	private String verificationCode ;
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//
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
//	protected void init(VaadinRequest request) 
//	{
//
//		if(request != null)
//		{
//			verificationCode = request.getParameter("vc");
//			if(verificationCode != null && verificationCode.length() > 0)
//			{
//				UserResultDTO userResult = EJBFactory.getUserOpenEJBFactory().getUserByVerificationCode(verificationCode);
//
//				String errorMessage = I18NManager.getErrorMessageBaseResult(userResult,userResult.getParams()); // <-- Obtiene el mensaje de error. "" si no hay errores.
//
//				//Mostrar formulario para cambio de clave.
//				this.getPage().setTitle(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "change_password_title"));
//
//				// Header Layout
//				final HorizontalLayout pnlHeader = new HorizontalLayout();
//				pnlHeader.setWidth(100, Unit.PERCENTAGE);
//				pnlHeader.setHeight(100, Unit.PIXELS);
//
//				String res_logo = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "logo_regional");
//				Resource res = new ThemeResource(res_logo);
//
//				Image image = new Image(null, res);
//				pnlHeader.addComponent(image);
//				pnlHeader.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
//
//
//				//Footer Layout
//				final HorizontalLayout pnlFooter = new HorizontalLayout();
//				pnlFooter.setId("pnlFooter");
//				pnlFooter.setSizeFull();
//				pnlFooter.setWidth(100, Unit.PERCENTAGE);
//				pnlFooter.setHeight(25, Unit.PIXELS);
//				pnlFooter.setStyleName("pnlFooter");
//				Label lblFooter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "app_footer"));
//
//				lblFooter.setWidthUndefined();
//				pnlFooter.addComponent(lblFooter);
//				pnlFooter.setComponentAlignment(lblFooter, Alignment.MIDDLE_CENTER);
//
//
//				// Login Layout
//				final HorizontalLayout pnlChangePassword = new HorizontalLayout();
//				pnlChangePassword.setSizeFull();
//				pnlChangePassword.setHeight(220, Unit.PIXELS);
//
//
//				if(errorMessage != null && errorMessage.length() <= 0)
//				{
//					currentUser = userResult.getUser();
//
//					changePasswordForm = new ChangePasswordPanel(this,userResult.getUser());
//					changePasswordForm.addBbrEventListener(frmChangePassword_handler);
//
//					pnlChangePassword.addComponent(changePasswordForm);
//					Responsive.makeResponsive(changePasswordForm);
//					pnlChangePassword.setComponentAlignment(changePasswordForm, Alignment.MIDDLE_CENTER);
//
//				}
//				else
//				{
//					String strHere = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "here");
//					String loginUrl = getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "login");
//
//					String link = "<a href='{0}'> {1} </a>";
//
//					link = BbrUtils.getInstance().substitute(link, loginUrl, strHere);
//
//					if(userResult.getStatuscode().equals("U5030"))
//					{
//						errorMessage = I18NManager.getErrorMessageBaseResult(userResult,link); 	
//					}
//
//					//Error en código de verificación.
//					Label errorMsg = new Label(errorMessage);
//					errorMsg.setContentMode(ContentMode.HTML);
//					VerticalLayout errorLayout = new VerticalLayout();
//					errorLayout.setWidthUndefined();
//					errorLayout.addComponents(errorMsg);
//					errorLayout.setComponentAlignment(errorMsg, Alignment.MIDDLE_CENTER);
//					pnlChangePassword.addComponent(errorLayout);
//					pnlChangePassword.setComponentAlignment(errorLayout, Alignment.MIDDLE_CENTER);
//				}
//
//
//				//Main Layout
//				final VerticalLayout pnlMain = new VerticalLayout();
//				pnlMain.setMargin(new MarginInfo(true, false, false, false));
//				pnlMain.setSizeFull();
//				pnlMain.setSpacing(true);
//				pnlMain.addComponent(pnlHeader);
//				pnlMain.addComponent(pnlChangePassword);
//				pnlMain.setExpandRatio(pnlChangePassword, 1F);
//				pnlMain.addComponent(pnlFooter);
//
//				//This BbrUI Content
//				this.setContent(pnlMain);
//			}
//		}
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
//	BbrEventListener frmChangePassword_handler = new BbrEventListener() 
//	{
//		private static final long serialVersionUID = -5133107251854882015L;
//
//		@Override
//		public void bbrEvent_handler(BbrEvent event) 
//		{
//			if(event!= null && event.getResultObject() != null )
//			{
//				if(event.getEventType().equals(BbrEvent.USER_CHANGE_PASS_ATTEMPT))
//				{
//					LoginInfo loginInfo = (LoginInfo) event.getResultObject();
//
//					doChangePassword(loginInfo);
//				}
//			}
//		}
//	};
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			EVENTS HANDLERS
//	// ****************************************************************************************
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
//	public void doChangePassword(LoginInfo loginInfo) 
//	{
//		String message = "";
//		try 
//		{
//			if(currentUser != null)
//			{
//				String newpassword 			= loginInfo.getNewPassword();
//				String confirmnewpassword 	= loginInfo.getConfirmNewPassword();
//
//
//				if((newpassword != null && newpassword.length()>0) && (confirmnewpassword != null && confirmnewpassword.length()>0))
//				{
//					if(newpassword.equals(confirmnewpassword))
//					{
//						int minPass = Integer.parseInt(getPropertyValue(BbrUtilsResources.RES_B2B_SYSTEM, "password_min_length"));
//						int maxPass = Integer.parseInt(getPropertyValue(BbrUtilsResources.RES_B2B_SYSTEM, "password_max_length"));
//
//						if((newpassword.length() >= minPass && newpassword.length() <= maxPass) && BbrUtils.getInstance().isAlphaNumericRegEx(newpassword))
//						{
//							UserResultDTO userResult = EJBFactory.getUserOpenEJBFactory().recoverPasswordSecondPhase(currentUser.getLogid(), verificationCode, newpassword, confirmnewpassword);
//							if(userResult != null)
//							{
//								message = I18NManager.getErrorMessageBaseResult(userResult, userResult.getParams()); // <-- Obtiene el mensaje de error. "" si no hay errores.
//								if(message.length() == 0) 
//								{
//									BbrMessageBox
//									.createInfo((BbrUI) UI.getCurrent())
//									.withOkButton(new Runnable() {
//
//										@Override
//										public void run() 
//										{
//											String loginUrl = getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "login");
//											goToURL(loginUrl);											
//										}
//									})
//									.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"))
//									.withHtmlMessage(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "password_changed"))
//									.withWidthForAllButtons("100px")
//									.open();
//								}
//							}
//							else // <-- Error changePasswordByUser() --- result == null
//							{
//								message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0011");
//							}
//
//						}
//						else // <-- Error - Longitud de la clave y debe ser alfanumérica
//						{
//							message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U5028");
//						}
//					}
//					else
//					{
//						message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U5022");
//						changePasswordForm.setConfirmPasswordError(message);
//					}
//				}
//				else // -> Hay campos vacíos 
//				{
//					this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields"));
//				}
//
//			}
//			else // <-- Error de sesion --- SessionBean == null
//			{
//				message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0011");
//			}
//		} 
//		catch (Exception e) //Error no controlado - changePasswordByUser
//		{
//			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0011");
//		}
//
//		if(message.length() > 0)
//		{
//			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
//		}
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//}
