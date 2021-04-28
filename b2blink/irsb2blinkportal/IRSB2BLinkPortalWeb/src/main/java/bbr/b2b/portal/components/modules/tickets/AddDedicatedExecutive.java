//package bbr.b2b.portal.components.modules.tickets;
//
//import java.io.Serializable;
//
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.FormLayout;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.wrappers.management.NewExecutiveInfo;
//import bbr.b2b.portal.components.utils.FindCompany;
//import bbr.b2b.portal.components.utils.FindExecutive;
//import bbr.b2b.trac.report.classes.DedicatedAnalystInitParamDTO;
//import bbr.b2b.users.report.classes.CompanyDTO;
//import bbr.b2b.users.report.classes.UserDTO;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.classes.events.BbrEventListener;
//import cl.bbr.core.components.basics.BbrItemSelectField;
//import cl.bbr.core.components.basics.BbrUI;
//import cl.bbr.core.components.basics.BbrWindow;
//import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
//import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;
//
//public class AddDedicatedExecutive extends BbrWindow 
//{
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = 3247626779164695209L;
//
//	private BbrItemSelectField<CompanyDTO> 	slc_Vendor 			= null ;	
//	private BbrTextField 					txt_VendorName 		= null;
//	private BbrItemSelectField<UserDTO> 	slc_Executive 			= null ;
//	private BbrTextField 					txt_ExecutiveName 		= null;
//	private BbrTextField 					txt_ExecutiveLastName 	= null;
//	private BbrTextField 					txt_ExecutiveEmail		= null;
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//	public AddDedicatedExecutive() 
//	{
//		this.setParentUI((BbrUI) UI.getCurrent());
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
//		// --- Vendor Data ---
//		//Vendor Code
//		slc_Vendor = new BbrItemSelectField<CompanyDTO>("");
//		slc_Vendor.addBbrEventListener((BbrEventListener & Serializable)e -> userEnterprise_handler(e));
//		slc_Vendor.setFieldName("name");
//		slc_Vendor.setDescriptionName("name");
//		slc_Vendor.addStyleName("bbr-filter-fields");
//
//		Label lbl_VendorCode = new Label(new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "vendor_code")));
//		lbl_VendorCode.setWidth("130px");
//
//		HorizontalLayout vendorCodeLayout = new HorizontalLayout(lbl_VendorCode, slc_Vendor);
//		vendorCodeLayout.setWidth("100%");
//		vendorCodeLayout.setExpandRatio(slc_Vendor, 1F);
//
//		//User Name Field
//		txt_VendorName = new BbrTextField();
//		txt_VendorName.setRestrict(RestrictRange.ALL_LETTERS_SPACE);
//		txt_VendorName.setMaxLength(50);
//		txt_VendorName.addStyleName("login-text-field");
//		txt_VendorName.setWidth("100%");
//		txt_VendorName.setReadOnly(true);
//
//		Label lbl_VendorName = new Label(new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "vendor_name")));
//		lbl_VendorName.setWidth("130px");
//
//		HorizontalLayout vendorNameLayout = new HorizontalLayout(lbl_VendorName , txt_VendorName);
//		vendorNameLayout.setWidth("100%");
//		vendorNameLayout.setExpandRatio(txt_VendorName, 1F);
//
//		// --- Executive Data ---
//		//Executive Code
//		slc_Executive = new BbrItemSelectField<UserDTO>("");
//		slc_Executive.setFieldName("name");
//		slc_Executive.setDescriptionName("name");
//		slc_Executive.addStyleName("bbr-filter-fields");
//		slc_Executive.addBbrEventListener((BbrEventListener & Serializable)e -> executive_handler(e));	
//
//		Label lbl_ExecutiveCode = new Label(new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "executive_rut")));
//		lbl_ExecutiveCode.setWidth("130px");
//
//		HorizontalLayout executiveCodeLayout = new HorizontalLayout(lbl_ExecutiveCode, slc_Executive);
//		executiveCodeLayout.setWidth("100%");
//		executiveCodeLayout.setExpandRatio(slc_Executive, 1F);
//
//		//Executive Name Field
//		txt_ExecutiveName = new BbrTextField();
//		txt_ExecutiveName.setMaxLength(50);
//		txt_ExecutiveName.addStyleName("login-text-field");
//		txt_ExecutiveName.setWidth("100%");
//		txt_ExecutiveName.setReadOnly(true);
//
//		Label lbl_ExecutiveName = new Label(new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "executive_name")));
//		lbl_ExecutiveName.setWidth("130px");
//
//		HorizontalLayout executiveNameLayout = new HorizontalLayout(lbl_ExecutiveName , txt_ExecutiveName);
//		executiveNameLayout.setWidth("100%");
//		executiveNameLayout.setExpandRatio(txt_ExecutiveName, 1F);
//
//		//Executive Last Name Field
//		txt_ExecutiveLastName = new BbrTextField();
//		txt_ExecutiveLastName.setMaxLength(10);
//		txt_ExecutiveLastName.addStyleName("login-text-field");
//		txt_ExecutiveLastName.setWidth("100%");
//		txt_ExecutiveLastName.setReadOnly(true);
//
//		Label lbl_ExecutiveLastName = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "executive_last_name"));
//		lbl_ExecutiveLastName.setWidth("130px");
//
//		HorizontalLayout executiveLastNameLayout = new HorizontalLayout(lbl_ExecutiveLastName, txt_ExecutiveLastName);
//		executiveLastNameLayout.setWidth("100%");
//		executiveLastNameLayout.setExpandRatio(txt_ExecutiveLastName, 1F);
//
//
//		//Executive Last Name Field
//		txt_ExecutiveEmail = new BbrTextField();
//		txt_ExecutiveEmail.setMaxLength(10);
//		txt_ExecutiveEmail.addStyleName("login-text-field");
//		txt_ExecutiveEmail.setWidth("100%");
//		txt_ExecutiveEmail.setReadOnly(true);
//
//		Label lbl_ExecutiveEmail = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "executive_email"));
//		lbl_ExecutiveEmail.setWidth("130px");
//
//		HorizontalLayout executiveEmailLayout = new HorizontalLayout(lbl_ExecutiveEmail, txt_ExecutiveEmail);
//		executiveEmailLayout.setWidth("100%");
//		executiveEmailLayout.setExpandRatio(txt_ExecutiveEmail, 1F);
//
//		//Register User Button
//		Button btn_RegisterUser = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"));
//		btn_RegisterUser.setStyleName("primary");
//		btn_RegisterUser.addStyleName("btn-login");
//		btn_RegisterUser.setWidth("100%");
//		btn_RegisterUser.addClickListener((ClickListener & Serializable) e -> btnCreate_clickHandler(e));
//
//		//Cancel Button
//		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
//		btn_Cancel.setStyleName("primary");
//		btn_Cancel.addStyleName("btn-login");
//		btn_Cancel.setWidth("100%");
//		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));
//
//		HorizontalLayout buttonsPanel = new HorizontalLayout(btn_RegisterUser, btn_Cancel);
//		buttonsPanel.addStyleName("bbr-buttons-panel");
//
//		buttonsPanel.setWidth("300px");
//		buttonsPanel.setSpacing(true);
//
//		//FormLayout as Panel Content
//		FormLayout frmAddExecutive = new FormLayout();
//		frmAddExecutive.setWidth(450, Unit.PIXELS);
//		frmAddExecutive.setHeight(100, Unit.PERCENTAGE);
//		frmAddExecutive.addComponents(vendorCodeLayout,vendorNameLayout, executiveCodeLayout, executiveNameLayout , executiveLastNameLayout, executiveEmailLayout);
//
//		//Main Layout
//		VerticalLayout mainLayout = new VerticalLayout();
//		mainLayout.setSizeFull();
//		mainLayout.addComponents(frmAddExecutive,buttonsPanel);
//		mainLayout.setComponentAlignment(frmAddExecutive, Alignment.MIDDLE_CENTER);
//		mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
//
//
//		//Main Windows
//		this.setWidth(500,Unit.PIXELS);
//		this.setHeight(380,Unit.PIXELS);
//		this.setResizable(false);
//		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "win_add_dedicated_executive_title"));
//		this.setContent(mainLayout);
//
//		txt_ExecutiveLastName.focus();
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			EVENTS HANDLERS
//	// ****************************************************************************************
//	private void btnCreate_clickHandler(ClickEvent event) 
//	{
//		if(this.slc_Vendor.getItem()!=null && this.slc_Executive.getItem()!=null)
//		{
//			NewExecutiveInfo userInfo = new NewExecutiveInfo(this.slc_Vendor.getItem(), this.slc_Executive.getItem());
//
//			this.doCreateExecutive(userInfo);	
//		}
//		else // -> Falta empresa
//		{
//			String message = I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields");
//
//			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
//
//		}
//
//	}
//
//	private void btnClose_clickHandler(ClickEvent event) 
//	{
//		this.close();			
//	}
//
//	private void userEnterprise_handler(BbrEvent event) 
//	{
//		FindCompany winFindCompany = new FindCompany();
//		winFindCompany.initializeView();
//		winFindCompany.addBbrEventListener((BbrEventListener & Serializable)e -> companySelected_handler(e));
//		winFindCompany.open(getParentUI(), true);
//	}
//
//	private void executive_handler(BbrEvent event) 
//	{
//		FindExecutive winFindExecutive = new FindExecutive();
//		winFindExecutive.initializeView();
//		winFindExecutive.addBbrEventListener((BbrEventListener & Serializable)e -> executiveSelected_handler(e));
//		winFindExecutive.open(getParentUI(), true);
//	}
//
//	private void companySelected_handler(BbrEvent event) 
//	{
//		CompanyDTO company = (CompanyDTO) event.getResultObject();
//		if(company != null)
//		{
//			slc_Vendor.setItem(company);
//			String digit = (company.getDigit() != null && company.getDigit().length()>0)?"-"+company.getDigit():"";
//			String rut = company.getRut() + digit;
//			slc_Vendor.setCustomLabel(rut);
//			txt_VendorName.setReadOnly(false);
//			txt_VendorName.setValue(company.getName());
//			txt_VendorName.setReadOnly(true);
//		}
//	}
//
//	private void executiveSelected_handler(BbrEvent event) 
//	{
//		UserDTO user = (UserDTO) event.getResultObject();
//		if(user != null)
//		{
//			slc_Executive.setItem(user);
//			String rut = user.getRut();
//			slc_Executive.setCustomLabel(rut);
//
//			txt_ExecutiveName.setReadOnly(false);
//			txt_ExecutiveLastName.setReadOnly(false);
//			txt_ExecutiveEmail.setReadOnly(false);
//
//			txt_ExecutiveName.setValue(user.getName());
//			txt_ExecutiveLastName.setValue(user.getLastname());
//			txt_ExecutiveEmail.setValue(user.getEmail());
//
//			txt_ExecutiveName.setReadOnly(true);
//			txt_ExecutiveLastName.setReadOnly(true);
//			txt_ExecutiveEmail.setReadOnly(true);
//		}
//	}
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			EVENTS HANDLERS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
//	private void doCreateExecutive(NewExecutiveInfo newExecutiveInfo) 
//	{
//		String message = "";
//		BaseResultDTO 	executiveResult = null ;
//		try 
//		{
//			if(newExecutiveInfo != null)
//			{
//				message = newExecutiveInfo.validateData();
//				if(message == null || message.length() == 0)
//				{
//					DedicatedAnalystInitParamDTO initParam = newExecutiveInfo.toDedicatedAnalystInitParamDTO();
//					
//					executiveResult 	= EJBFactory.getTrackingEJBFactory().getTracReportManagerLocal().doAddDedicatedAnalyst(initParam);
//					if(executiveResult != null)
//					{
//						message = I18NManager.getErrorMessageBaseResult(executiveResult, executiveResult.getParams()); // <-- Obtiene el mensaje de error. "" si no hay errores.
//					}
//					else
//					{
//						// -> Error executiveResult = null
//						message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "T1");
//					}
//
//				}
//			}
//
//		} 
//		catch (Exception e) //Error no controlado
//		{
//			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "T1");
//		}
//
//		if(message.length() > 0)
//		{
//			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
//		}
//		else
//		{
//			this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "successful_operation"));
//
//			BbrEvent createEvent = new BbrEvent(BbrEvent.ITEM_CREATED);
//			this.dispatchBbrEvent(createEvent);
//			this.close();
//		}
//	}
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//
//
//}
