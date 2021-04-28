package bbr.b2b.portal.components.login;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.basics.LoginInfo;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.UserDTO;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrPanel;
import cl.bbr.core.components.basics.BbrUI;

public class ChangePasswordPanel extends BbrPanel 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = -629089940862748607L;
	
	private UserDTO currentUser;
	
	private PasswordField txt_NewPassword = null;
	private PasswordField txt_ConfirmNewPassword = null;
	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	public ChangePasswordPanel(BbrUI bbrUI, UserDTO user) 
	{
		super(bbrUI);
		this.currentUser = user;
		this.initializeView();
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************
	
	
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************
	
	// ****************************************************************************************
	// ENDING SECTION 		---->			INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************

	
	
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView() 
	{
		
		if(this.currentUser != null)
		{
			//User Name Label
			Label lbl_UserName = new Label(this.currentUser.getName() + " " + this.currentUser.getLastname());
		
			//New Password Field
			txt_NewPassword = new PasswordField(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "new_password"));
			txt_NewPassword.setMaxLength(10);
			txt_NewPassword.addStyleName("login-text-field");
			txt_NewPassword.setIcon(VaadinIcons.LOCK);	
			txt_NewPassword.setWidth("100%");	

			//Confirm Password Field
			txt_ConfirmNewPassword = new PasswordField(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "confirm_new_password"));
			txt_ConfirmNewPassword.setMaxLength(10);
			txt_ConfirmNewPassword.addStyleName("login-text-field");
			txt_ConfirmNewPassword.setIcon(VaadinIcons.LOCK);	
			txt_ConfirmNewPassword.setWidth("100%");	
			
			
			//Change Password Button
			Button btn_ChangePassword = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "change_password"));
			btn_ChangePassword.setStyleName("primary");
			btn_ChangePassword.addStyleName("btn-login");
			btn_ChangePassword.setWidth("100%");
			btn_ChangePassword.setDisableOnClick(true);
			btn_ChangePassword.addClickListener(new ClickListener() 
			{
				private static final long serialVersionUID = -3580573490854499659L;

				@Override
				public void buttonClick(ClickEvent event) 
				{
					LoginInfo loginInfo = new LoginInfo(currentUser.getLogid(), "", txt_NewPassword.getValue(),txt_ConfirmNewPassword.getValue()); 
					doAttemptChangePassword(loginInfo);
					btn_ChangePassword.setEnabled(true);
				}
			});


			// Enter key listener
			this.addShortcutListener(new ShortcutListener("Enter", ShortcutAction.KeyCode.ENTER, null) 
			{
				private static final long serialVersionUID = 6038366467503251749L;

				@Override
				public void handleAction(Object sender, Object target) 
				{

					if (target instanceof TextField || target instanceof PasswordField) 
					{
						btn_ChangePassword.setEnabled(false);
						LoginInfo loginInfo = new LoginInfo(currentUser.getLogid(), "", txt_NewPassword.getValue(),txt_ConfirmNewPassword.getValue());						doAttemptChangePassword(loginInfo);
						btn_ChangePassword.setEnabled(true);
					}
				}
			});
			
			//FormLayout as Panel Content
			FormLayout frmChangePassword = new FormLayout();
			frmChangePassword.setId("frmChangePassword");
			frmChangePassword.setMargin(true);
			frmChangePassword.setWidth(400, Unit.PIXELS);
			frmChangePassword.setHeight(100, Unit.PERCENTAGE);
			frmChangePassword.addComponents(lbl_UserName,txt_NewPassword, txt_ConfirmNewPassword,btn_ChangePassword);

			//Main Layout
			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.setSizeFull();
			mainLayout.addComponent(frmChangePassword);
			mainLayout.setMargin(false);
			mainLayout.setComponentAlignment(frmChangePassword, Alignment.MIDDLE_CENTER);

			//This BbrPanel
			this.addStyleName("panelMain");
			this.setWidth("430px");
			this.setContent(mainLayout);	
			txt_NewPassword.focus();
		}
		else
		{
			String loginUrl = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "login");

			this.goToURL(loginUrl);
		}
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			OVERRIDDEN METHODS
	// ****************************************************************************************

	
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION 		---->			EVENTS HANDLERS
	// ****************************************************************************************
	
	
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PUBLIC METHODS
	// ****************************************************************************************
	public void setConfirmPasswordError(String error)
	{
		txt_ConfirmNewPassword.setComponentError(new UserError(error));
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			PUBLIC METHODS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PRIVATE METHODS
	// ****************************************************************************************
	void doAttemptChangePassword(LoginInfo loginInfo) 
	{
		this.clearErrors();
		BbrEvent bbrEvent = new BbrEvent(BbrEvent.USER_CHANGE_PASS_ATTEMPT);
		bbrEvent.setResultObject(loginInfo);
		dispatchBbrEvent(bbrEvent);		
	}
	
	private void clearErrors()
	{
		txt_ConfirmNewPassword.setComponentError(null);
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			PRIVATE METHODS
	// ****************************************************************************************
}
