//package bbr.b2b.portal.components.login;
//
//import java.io.Serializable;
//
//import com.vaadin.data.HasValue.ValueChangeEvent;
//import com.vaadin.data.HasValue.ValueChangeListener;
//import com.vaadin.event.ShortcutAction;
//import com.vaadin.event.ShortcutListener;
//import com.vaadin.icons.VaadinIcons;
//import com.vaadin.server.Resource;
//import com.vaadin.server.ThemeResource;
//import com.vaadin.shared.ui.ContentMode;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.FormLayout;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Image;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.PasswordField;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.portal.classes.basics.LoginInfo;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.portal.classes.constants.EnumBusinessUnit;
//import bbr.b2b.portal.classes.constants.EnumCountry;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.utils.app.BUUtils;
//import bbr.b2b.portal.classes.wrappers.app.BusinessUnit;
//import bbr.b2b.portal.classes.wrappers.app.Country;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.classes.events.BbrEventListener;
//import cl.bbr.core.components.basics.BbrComboBox;
//import cl.bbr.core.components.basics.BbrMessageBox;
//import cl.bbr.core.components.basics.BbrPanel;
//import cl.bbr.core.components.basics.BbrUI;
//import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
//
//public class LoginPanel extends BbrPanel 
//{
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = -1274130557171792948L;
//
//	private BbrComboBox<Country> cbx_Countries = null;
//	private BbrComboBox<BusinessUnit> cbx_BusinessUnits = null;
//
//	private BbrTextField txt_UserName = null;
//	private PasswordField txt_Password = null;
//	private Button btn_Login = null;
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//	public LoginPanel(BbrUI bbrUI) 
//	{
//		super(bbrUI);
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
//		// Logo Image
//		String res_Logo = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "logo");
//		Resource res = new ThemeResource(res_Logo);		
//		Image image = new Image(null, res);
//		image.addStyleName("logo");
//
//		//Country Field
//		Label lblCountry = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "country"));
//		lblCountry.setContentMode(ContentMode.HTML);
//		lblCountry.setWidth("100%");		
//		
//		cbx_Countries = this.getCountriesComboBox(EnumBusinessUnit.EASYAR);
//		cbx_Countries.addValueChangeListener((ValueChangeListener<Country> & Serializable)e -> countryChange_Listener(e));
//		VerticalLayout countryLayout = new VerticalLayout(lblCountry,cbx_Countries);
//		countryLayout.setWidth("100%");
//		countryLayout.setExpandRatio(cbx_Countries, 1F);
//		countryLayout.addStyleName("country-layout");
//		countryLayout.setMargin(false);
//		
//		//Business Unit Field
//		Label lblBU = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "business_unit"));
//		lblBU.setContentMode(ContentMode.HTML);
//		lblBU.setWidth("100%");
//		
//		cbx_BusinessUnits = this.getBUComboBox(EnumBusinessUnit.EASYAR);
//		
//		VerticalLayout buLayout = new VerticalLayout(lblBU,cbx_BusinessUnits);
//		buLayout.setWidth("100%");
//		buLayout.setExpandRatio(cbx_BusinessUnits, 1F);
//		buLayout.addStyleName("business-layout");
//		buLayout.setMargin(false);
//
//		//User Name Field
//		Label lblUserName = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "usercode"));
//		lblUserName.setContentMode(ContentMode.HTML);
//		lblUserName.setWidth("100%");
//		
//		txt_UserName = new BbrTextField();
//		txt_UserName.addStyleName("loginInputText");
//		txt_UserName.setWidth("100%");
//		
//		VerticalLayout userCodeLayout = new VerticalLayout(lblUserName, txt_UserName);
//		userCodeLayout.setWidth("100%");
//		userCodeLayout.setExpandRatio(txt_UserName, 1F);
//		userCodeLayout.setMargin(false);
//		
//		//Password Field
//		Label lblPass = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "password"));
//		lblPass.setContentMode(ContentMode.HTML);
//		lblPass.setWidth("100%");
//		
//		txt_Password = new PasswordField();
//		txt_Password.addStyleName("loginInputText");
//		txt_Password.setWidth("100%");
//		
//		VerticalLayout passwordContent = new VerticalLayout(lblPass,txt_Password);
//		passwordContent.setWidth("100%");
//		passwordContent.setExpandRatio(txt_Password, 1F);
//		passwordContent.setMargin(false);
//
//		//Login Button
//		
//		//BbrHSeparator separator = new BbrHSeparator("100%");
//		
//		btn_Login = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "login"));
//		btn_Login.setStyleName("primary");
//		btn_Login.addStyleName("btn-login");
//		btn_Login.setWidth("100%");
//		btn_Login.setDisableOnClick(true);
//		btn_Login.addClickListener((ClickListener & Serializable)e -> login_clickListener(e)); 
//
//		VerticalLayout pnlLoginButton = new VerticalLayout(btn_Login);
//		pnlLoginButton.setWidth("100%");
//		//pnlLoginButton.setExpandRatio(btn_Login, 1F);
//		pnlLoginButton.setMargin(false);
//		
//		// Enter key listener
//		this.addShortcutListener(new ShortcutListener("Enter", ShortcutAction.KeyCode.ENTER, null) 
//		{
//			private static final long serialVersionUID = 1573146312433672548L;
//
//			@Override
//			public void handleAction(Object sender, Object target) 
//			{
//				if (target instanceof BbrTextField || target instanceof PasswordField) 
//				{
//					btn_Login.setEnabled(false);
//					LoginInfo loginInfo = new LoginInfo(txt_UserName.getValue(), txt_Password.getValue()); 
//					doAttemptLogin(loginInfo);
//					btn_Login.setEnabled(true);
//				}
//			}
//		});
//
//		//Actions Buttons
//		Button btn_MoreInfo = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "more_information"));
//		btn_MoreInfo.addStyleName("login-link-button");
//		btn_MoreInfo.setIcon(VaadinIcons.INFO_CIRCLE);
//
//		Button btn_PassRecovery = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "recover_password"));
//		btn_PassRecovery.setIcon(VaadinIcons.UNLOCK);		
//		btn_PassRecovery.addStyleName("login-link-button");
//
//		Button btn_LoginRecovery = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "recover_user"));
//		btn_LoginRecovery.setIcon(VaadinIcons.USER_CARD);
//		btn_LoginRecovery.addStyleName("login-link-button");
//
//		btn_MoreInfo.addClickListener((ClickListener & Serializable)e -> moreInfo_clickListener(e)); 
//		btn_PassRecovery.addClickListener((ClickListener & Serializable)e -> passRecovery_clickListener(e));		
//		btn_LoginRecovery.addClickListener((ClickListener & Serializable)e -> userNameRecovery_clickHandler(e));	
//
//		//FormLayout as Panel Content
//		FormLayout frmLogin = new FormLayout();
//		frmLogin.addComponents(countryLayout,buLayout,userCodeLayout, passwordContent, pnlLoginButton);
//		frmLogin.addStyleName("login-fields");
//		//Actions Layout
//		HorizontalLayout actionLayout = new HorizontalLayout();
//
//		actionLayout.setWidth("100%");
//		actionLayout.setHeight(40, Unit.PIXELS);
//		actionLayout.setMargin(false);
//		actionLayout.addComponents(btn_MoreInfo, btn_PassRecovery, btn_LoginRecovery);
//
//		//Main Layout
//		VerticalLayout mainLayout = new VerticalLayout();
//		mainLayout.setSizeFull();
//		mainLayout.addComponents(image, frmLogin,actionLayout);
//		mainLayout.setSpacing(true);
//		mainLayout.setComponentAlignment(image, Alignment.TOP_CENTER);
//		mainLayout.setComponentAlignment(frmLogin, Alignment.MIDDLE_CENTER);
//		mainLayout.setComponentAlignment(actionLayout, Alignment.BOTTOM_CENTER);
//		mainLayout.setExpandRatio(frmLogin, 1F);
//		mainLayout.setMargin(false);		
//		
//		//This BbrPanel
//		this.setWidth("450px");
//		this.setStyleName("login-mainlayout");
//		this.setContent(mainLayout);		
//		
//		txt_UserName.focus();		
//		txt_UserName.setValue("dsuarez");
//		txt_Password.setValue("dsM791211");
//	}
//	
//	
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			EVENTS HANDLERS
//	// ****************************************************************************************
//	private void login_clickListener(ClickEvent e) 
//	{
//		LoginInfo loginInfo = new LoginInfo(txt_UserName.getValue(), txt_Password.getValue()); 
//		doAttemptLogin(loginInfo);
//		btn_Login.setEnabled(true);
//	}
//	
//	private void moreInfo_clickListener(ClickEvent e) 
//	{
//		LoginMoreInformation loginMore = new LoginMoreInformation((BbrUI)UI.getCurrent());
//		loginMore.initializeView();
//		loginMore.open(true);
//	}
//	
//	private void passRecovery_clickListener(ClickEvent e) 
//	{
//		WinPasswordRecovery win_PassRecovery = new WinPasswordRecovery((BbrUI)UI.getCurrent(), this.txt_UserName.getValue());
//		win_PassRecovery.initializeView();
//		win_PassRecovery.addBbrEventListener(bbrEventListener);
//		win_PassRecovery.open(true);
//	}
//	
//	private void userNameRecovery_clickHandler(ClickEvent e) 
//	{
//		WinUserNameRecovery win_UserNameRecovery = new WinUserNameRecovery((BbrUI)UI.getCurrent());
//		win_UserNameRecovery.initializeView();
//		win_UserNameRecovery.addBbrEventListener(bbrEventListener);
//		win_UserNameRecovery.open(true);
//	}
//	
//	private BbrEventListener bbrEventListener = new BbrEventListener() 
//	{
//		private static final long serialVersionUID = -4862099665962056120L;
//
//		@Override
//		public void bbrEvent_handler(BbrEvent event) 
//		{
//			if(event!= null && event.getResultObject() != null )
//			{
//				if(event.getEventType().equals(BbrEvent.USER_PASSWORD_RECOVERED) || event.getEventType().equals(BbrEvent.USER_LOGID_RECOVERED))
//				{
//					String message = (String) event.getResultObject();
//					BbrMessageBox
//					.createInfo(LoginPanel.this.getBbrUIParent())
//					.withOkButton()
//					.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"))
//					.withHtmlMessage(message)
//					.open();
//				}
//			}	
//		}
//	};
//	
//	
//	private void countryChange_Listener(ValueChangeEvent<Country> e) 
//	{
//		
//		BusinessUnit[] bus = BUUtils.getInstance().getAllBusinessUnitsOfCountry(EnumCountry.getEnumByCode(e.getValue().getCode()));
//		if(bus != null)
//		{
//			cbx_BusinessUnits.setItems(bus);
//			cbx_BusinessUnits.selectFirstItem();
//		}
//	}
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			EVENTS HANDLERS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PUBLIC METHODS
//	// ****************************************************************************************
//	public void clearForm() 
//	{
//		txt_UserName.setValue("");
//		txt_Password.setValue("");
//		txt_UserName.focus();
//		
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PUBLIC METHODS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
//	void doAttemptLogin(LoginInfo loginInfo)
//	{
//		BbrEvent bbrEvent = new BbrEvent(BbrEvent.USER_LOGIN_ATTEMPT);
//		bbrEvent.setResultObject(loginInfo);
//		dispatchBbrEvent(bbrEvent);	
//	}
//	
//	private BbrComboBox<Country> getCountriesComboBox(EnumBusinessUnit currentBU) 
//	{
//		BbrComboBox<Country> result = null;
//		Country[] countries = BUUtils.getInstance().getCountries();
//		Country selected = BUUtils.getInstance().getCountryByBusinessUnit(currentBU);
//		
//		result = new BbrComboBox<Country>(countries);
//		result.setItemCaptionGenerator(Country::getName);
//		result.setTextInputAllowed(false);
//		result.setEmptySelectionAllowed(false);
//		result.setSelectedItem(selected);
//		result.setWidth(100F, Unit.PERCENTAGE);
//		
//		return result;
//	}
//
//	private BbrComboBox<BusinessUnit> getBUComboBox(EnumBusinessUnit currentBU) 
//	{
//		BbrComboBox<BusinessUnit> result = null;
//		BusinessUnit[] bus = BUUtils.getInstance().getAllBusinessUnitsOfCountry(currentBU.getCountry());
//		BusinessUnit selected = BUUtils.getInstance().getBusinessUnit(currentBU);
//		
//		result = new BbrComboBox<BusinessUnit>(bus);
//		result.setItemCaptionGenerator(BusinessUnit::getName);
//		result.setTextInputAllowed(false);
//		result.setEmptySelectionAllowed(false);
//		result.setSelectedItem(selected);
//		
//		result.setWidth(100F, Unit.PERCENTAGE);
//		
//		return result;
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//	
//}
