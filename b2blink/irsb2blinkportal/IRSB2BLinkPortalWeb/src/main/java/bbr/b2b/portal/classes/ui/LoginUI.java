//package bbr.b2b.portal.classes.ui;
//
//import java.util.Locale;
//
//import javax.persistence.Transient;
//
//import com.vaadin.annotations.Theme;
//import com.vaadin.data.HasValue.ValueChangeEvent;
//import com.vaadin.data.HasValue.ValueChangeListener;
//import com.vaadin.server.Resource;
//import com.vaadin.server.Responsive;
//import com.vaadin.server.ThemeResource;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.server.VaadinService;
//import com.vaadin.ui.AbsoluteLayout;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Image;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.portal.classes.basics.LocaleInfo;
//import bbr.b2b.portal.classes.basics.LoginInfo;
//import bbr.b2b.portal.classes.beans.SessionBean;
//import bbr.b2b.portal.classes.constants.BbrAppConstants;
//import bbr.b2b.portal.classes.constants.EnumUserType;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.components.login.LoginPanel;
//import bbr.b2b.portal.constants.BbrUtilsConstants;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.users.report.classes.CompanyResultDTO;
//import bbr.b2b.users.report.classes.CompanyTypeResultDTO;
//import bbr.b2b.users.report.classes.UserDTO;
//import bbr.b2b.users.report.classes.UserResultDTO;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.classes.events.BbrEventListener;
//import cl.bbr.core.classes.utilities.BbrUtils;
//import cl.bbr.core.components.basics.BbrComboBox;
//import cl.bbr.core.components.basics.BbrUI;
//
//
///**
// * @author DSU 2017-04
// */
//
//@Theme("irsb2blinkportalweb")
//public class LoginUI extends BbrUI
//{
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = -1046487744803475753L;
//
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//
//	@Override
//	protected void init(VaadinRequest request) 
//	{
//		String loginUrl = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "login");
//		this.goToURL(loginUrl);	
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
//	@Transient
//	BbrEventListener frmLogin_handler = new BbrEventListener() 
//	{
//		private static final long serialVersionUID = -6482329316092312557L;
//
//		@Override
//		public void bbrEvent_handler(BbrEvent event) 
//		{
//			if(event!= null && event.getResultObject() != null )
//			{
//				if(event.getEventType().equals(BbrEvent.USER_LOGIN_ATTEMPT))
//				{
//					LoginInfo loginInfo = (LoginInfo) event.getResultObject();
//
//					LoginUI.this.doLogin(loginInfo);	
//				}
//			}
//		}
//	};
//
//	@Transient
//	ValueChangeListener<LocaleInfo> comboLocale_handler = new ValueChangeListener<LocaleInfo>() 
//	{
//
//		@Override
//		public void valueChange(ValueChangeEvent<LocaleInfo> event) 
//		{
//			LocaleInfo localeInfo = event.getValue();
//			if(localeInfo != null)
//			{
//				Locale userLocale = new Locale(localeInfo.getLanguage(),localeInfo.getCountry());
//
//				Object sessionBeanObj = getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
//
//				SessionBean sessionBean = (sessionBeanObj == null)?new SessionBean():(SessionBean) getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
//
//				sessionBean.setLocale(userLocale);
//				setSessionBean(sessionBean, BbrUtilsConstants.SESSION_BEAN_NAME);
//
//				String loginUrl = getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "login");
//
//				goToURL(loginUrl);	
//			}
//		}
//	}; 
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			EVENTS HANDLERS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PUBLIC METHODS
//	// ****************************************************************************************
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PUBLIC METHODS
//	// ****************************************************************************************
//
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
//	void doLogin(LoginInfo loginInfo)
//	{
//		if(loginInfo != null)
//		{
//			String login 	= BbrUtils.getInstance().cleanString(loginInfo.getUserName());	
//			String password = loginInfo.getPassword();
//
//			if((login != null && login.length()>0) && (password != null && password.length()>0))
//			{
//				try
//				{
//					UserResultDTO userResult = EJBFactory.getUserOpenEJBFactory().doLoginUser(login, password);
//					if(userResult != null)
//					{
//						// En dependencia de si vienen parametros, es que se escogen mensajes personalizados o no
//						String errorMessage = userResult.getParams() != null ? I18NManager.getErrorMessageBaseResult(userResult, userResult.getParams()) : I18NManager.getErrorMessageBaseResult(userResult); // <-- Obtiene el mensaje de error. "" si no hay errores.
//						if(errorMessage.length() == 0) 
//						{
//							if(userResult.getUser() != null)
//							{
//								//Renovando la sesion.
//								VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
//
//								UserDTO 				user 			= userResult.getUser();
//								CompanyResultDTO 		company 		= EJBFactory.getUserOpenEJBFactory().findCompanyById(user.getEmkey());
//								CompanyTypeResultDTO	companytype 	= EJBFactory.getUserOpenEJBFactory().findCompanyTypeById(company.getCompanyDTO().getTekey());
//
//								String  enterpriseCode 	= (company != null && company.getCompanyDTO() != null)?company.getCompanyDTO().getRut():"";
//								String  enterpriseName 	= (company != null && company.getCompanyDTO() != null)?company.getCompanyDTO().getName():"";
//								Long 	enterpriseId 	= (company != null && company.getCompanyDTO() != null)?company.getCompanyDTO().getId():-1L;
//
//								user.setSession(this.getSessionId());
//								Object objSessionBean = this.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
//								Locale userLocale = (objSessionBean!= null)?((((SessionBean)objSessionBean).getLocale() != null)?((SessionBean)objSessionBean).getLocale():Locale.getDefault()):Locale.getDefault();
//								SessionBean sessionBean = new SessionBean();
//								sessionBean.setUser(user);
//								sessionBean.setCompanyType(companytype.getCompanyTypeResultDTO());
//								sessionBean.setSessionId(this.getSessionId());
//								sessionBean.setEmailUser(user.getEmail());
//								sessionBean.setUserId(user.getId());
//								sessionBean.setUserLastName(user.getLastname());
//								sessionBean.setUserName(user.getName());
//								sessionBean.setLocale(userLocale);
//								sessionBean.setEnterpriseId(enterpriseId);
//								sessionBean.setEnterpriseCode(enterpriseCode);
//								sessionBean.setEnterpriseName(enterpriseName);
//								sessionBean.setEnumUserType(EnumUserType.getEnumUserType(companytype.getCompanyTypeResultDTO().getName()));
//
//								EJBFactory.getUserOpenEJBFactory().updateUser(user);
//
//								this.doLogInfo(BbrUtils.getInstance().substitute(BbrAppConstants.SESSION_CREATED + " {0} : {1}", user.getSession(),sessionBean.getUserFullName()));
//
//								if(!user.getCheckpassexpiration()) // <-- Verificar si la clave está vigente
//								{
//									sessionBean.setLogged(true);
//									this.setSessionBean(sessionBean, BbrUtilsConstants.SESSION_BEAN_NAME);
//
//									String mainUrl = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "main");
//									this.goToURL(mainUrl);
//								}
//								else // <-- Expiró la clave
//								{
//									sessionBean.setPasswordExpires(true);
//									this.setSessionBean(sessionBean, BbrUtilsConstants.SESSION_BEAN_NAME);
//
//									String changeUrl = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "change");
//									this.goToURL(changeUrl);
//								}
//							}
//							else // <-- Error doLogin() --- user == null
//							{
//								this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U101"));
//
//							}
//						}
//						else // <-- Error doLogin() --- statuscode != 0
//						{
//							this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), errorMessage);
//							this.loginForm.clearForm();
//						}
//					}
//					else // <-- Error doLogin() --- result == null
//					{
//						this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U101"));
//					}
//				}
//				catch (Exception e)  //Error no controlado - doLogin o findTypeUserById
//				{
//					e.printStackTrace();
//					this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error") , I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1"));
//				}
//
//
//			}
//			else // -> Hay campos vacíos 
//			{
//				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields"));
//			}
//		}
//
//	}
//
//	private void initializeSessionBeanLocale()
//	{
//		Object sessionBeanObj = this.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
//		SessionBean sessionBean = (sessionBeanObj == null)?new SessionBean():(SessionBean) this.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
//
//		if(sessionBean.getLocale() == null)
//		{
//			Locale userLocale = new Locale("es", "ES");
//
//			sessionBean.setLocale(userLocale);
//			this.setSessionBean(sessionBean, BbrUtilsConstants.SESSION_BEAN_NAME);
//		}
//	}
//
//	private BbrComboBox<LocaleInfo> getBbrComboBox() 
//	{
//
//		String spanish = this.getPropertyValue(BbrUtilsResources.RES_B2B_UTILS, "spanish");
//		String english = this.getPropertyValue(BbrUtilsResources.RES_B2B_UTILS, "english");
//
//		LocaleInfo localeSpanish = new LocaleInfo(spanish, "es", "ES",new ThemeResource(this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "flag_es")));
//		LocaleInfo localeEnglish = new LocaleInfo(english, "en", "US",new ThemeResource(this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "flag_en")));
//
//		BbrComboBox<LocaleInfo> cbx_Locale = new BbrComboBox<LocaleInfo>(new LocaleInfo[] { localeSpanish,localeEnglish});
//
//		SessionBean sessionBean = (SessionBean) this.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
//		if(sessionBean.getLocale().getLanguage().equals(localeEnglish.getLanguage()))
//		{
//			cbx_Locale.setSelectedItem(localeEnglish);
//		}
//		else
//		{
//			cbx_Locale.setSelectedItem(localeSpanish);
//		}
//
//		cbx_Locale.setItemCaptionGenerator(LocaleInfo::getCaption);
//		cbx_Locale.setItemIconGenerator(LocaleInfo::getIcon);
//		cbx_Locale.setTextInputAllowed(false);
//		cbx_Locale.setEmptySelectionAllowed(false);
//		cbx_Locale.addValueChangeListener(comboLocale_handler);
//
//		return cbx_Locale;
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************	
//}
