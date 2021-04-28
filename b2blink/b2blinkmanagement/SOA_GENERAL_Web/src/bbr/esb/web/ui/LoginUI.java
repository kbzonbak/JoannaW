package bbr.esb.web.ui;

import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import bbr.esb.web.beans.SessionBean;
import bbr.esb.web.constants.BbrAppConstants;
import bbr.esb.web.resources.LDAPLoginResultDTO;
import bbr.esb.web.resources.LDAPUtils;
import bbr.esb.web.resources.LoginUtils;
import bbr.esb.web.resources.ResourceUtil;
import cl.bbr.core.components.basics.BbrMessageBox;

@SuppressWarnings("serial")
@Theme("soa_general_webtheme")
// @Widgetset("bbr.esb.web.ui.widgetset.SOAGENERALManagementWidgetset")
public class LoginUI extends UI {

	protected Logger logger = Logger.getLogger("ProfilemanagementwebUI");

	protected TextField txt_UserName;

	protected PasswordField txt_Password;

	protected Button btn_Login;

	@WebServlet(value = { "/login/*", "/VAADIN/*" }, asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = LoginUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		// Header Layout

		/*
		 * final HorizontalLayout pnlLogo = new HorizontalLayout(); pnlLogo.setWidth(100, Unit.PERCENTAGE);
		 * pnlLogo.setHeight(107, Unit.PIXELS);
		 * 
		 * String res_logo = ResourceUtil.getInstance().getPropertyValue(BbrAppConstants.RES_B2B_PAGES, "logo_header");
		 * Resource res = new ThemeResource(res_logo);
		 * 
		 * Image image = new Image(null, res); pnlLogo.addComponent(image); pnlLogo.setComponentAlignment(image,
		 * Alignment.MIDDLE_CENTER);
		 */

		// Login Layout
		final VerticalLayout pnlLogin = new VerticalLayout();
		pnlLogin.setSizeFull();
		pnlLogin.setHeight(250, Unit.PIXELS);

		Component loginPanel = getLoginPanel();
		pnlLogin.setId("panellogin");
		// Responsive.makeResponsive(loginPanel);
		pnlLogin.addComponent(loginPanel);
		pnlLogin.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

		// Footer Layout
		final HorizontalLayout pnlFooter = new HorizontalLayout();
		pnlFooter.setId("pnlFooter");
		pnlFooter.setSizeFull();
		pnlFooter.setWidth(100, Unit.PERCENTAGE);
		pnlFooter.setHeight(25, Unit.PIXELS);
		pnlFooter.setStyleName("pnl-footer");
		Label lblFooter = new Label("© 2018 - Derechos Reservados - Desarrollado por BBR SPA.");
		lblFooter.setWidthUndefined();
		pnlFooter.addComponent(lblFooter);
		pnlFooter.setComponentAlignment(lblFooter, Alignment.MIDDLE_CENTER);

		// Main Layout
		final VerticalLayout pnlMain = new VerticalLayout();
		pnlMain.setMargin(new MarginInfo(false, false, false, false));
		pnlMain.setSizeFull();

		pnlMain.addComponents(pnlLogin, pnlFooter);
		// pnlMain.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		pnlMain.setExpandRatio(pnlLogin, 1F);
		pnlMain.setId("panel-main");
		pnlMain.setStyleName("pnl-main");
		this.setContent(pnlMain);
	}

	private Component getLoginPanel() {
		Panel panel = new Panel();
		// User Name Field
		txt_UserName = new TextField("Usuario");
		txt_UserName.setInputPrompt("username");
		txt_UserName.addStyleName("loginInputText");
		txt_UserName.setIcon(FontAwesome.USER);

		// Password Field
		txt_Password = new PasswordField("Password");
		txt_Password.setInputPrompt("password");
		txt_Password.addStyleName("loginInputText");
		txt_Password.setIcon(FontAwesome.LOCK);

		// Login Button
		btn_Login = new Button("Entrar");
		btn_Login.setStyleName("primary");
		btn_Login.addStyleName("btnLogin");
		btn_Login.setDisableOnClick(true);
		btn_Login.addClickListener(e -> doLogin());
		btn_Login.setClickShortcut(KeyCode.ENTER);

		// FormLayout as Panel Content
		FormLayout frmLogin = new FormLayout();
		frmLogin.setId("frmLogin");
		frmLogin.setStyleName("frm_login");
		frmLogin.setMargin(true);
		// frmLogin.setSizeFull();
		// frmLogin.setWidth(100, Unit.PERCENTAGE);
		// frmLogin.setHeight(100, Unit.PERCENTAGE);
		frmLogin.addComponents(txt_UserName, txt_Password, btn_Login);
		frmLogin.setComponentAlignment(txt_UserName, Alignment.MIDDLE_CENTER);
		frmLogin.setComponentAlignment(txt_Password, Alignment.MIDDLE_CENTER);
		frmLogin.setComponentAlignment(btn_Login, Alignment.MIDDLE_CENTER);

		// Actions Layout
		HorizontalLayout actionLayout = new HorizontalLayout();

		actionLayout.setWidth(100, Unit.PERCENTAGE);
		actionLayout.setHeight(50, Unit.PIXELS);
		actionLayout.setId("actionLayout");

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(frmLogin, actionLayout);
		mainLayout.setComponentAlignment(frmLogin, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(actionLayout, Alignment.MIDDLE_CENTER);
		mainLayout.setExpandRatio(frmLogin, 1F);

		// This BbrPanel
		panel.addStyleName("main-panel");
		panel.setWidth("400px");
		panel.setContent(mainLayout);

		return panel;
	}

	private void doLogin() {
		String username = txt_UserName.getValue().trim();
		String password = txt_Password.getValue();
		logger.fine("Username: " + username + ", Password: " + password);

		boolean authenticated = false;
		try {

			String authentication_type = System.getProperty("authentication_type");

			if (authentication_type == null || authentication_type.equals(BbrAppConstants.AUTHENTICATION_TYPE_LOGIN)) {

				authenticated = LoginUtils.getInstance().doLogin(username, password);

			} else if (authentication_type.equals(BbrAppConstants.AUTHENTICATION_TYPE_LDAP_INTERNAL)) {

				LDAPLoginResultDTO result = LDAPUtils.getInstance().doLogin(username, password);
				authenticated = (result != null) && (result.getStatuscode().equals("0"));
			}

			if (!authenticated) {
				BbrMessageBox.createError().withCloseButton().withCaption("Información").withMessage("Usuario o contraseña inválidos").withWidthForAllButtons("100px").open();

				txt_UserName.selectAll();
				txt_Password.selectAll();
				txt_UserName.focus();

			} else {

				SessionBean sessionBean = new SessionBean();
				sessionBean.setUsername(username);
				sessionBean.setLoginDate(new Date());
				sessionBean.setLastAccessDate(new Date());

				VaadinSession.getCurrent().getSession().setAttribute("sessionBean", sessionBean);

				String loginUrl = ResourceUtil.getInstance().getPropertyValue(BbrAppConstants.RES_B2B_PAGES, "main");
				this.getPage().setLocation(loginUrl);

			}

		} finally {
			btn_Login.setEnabled(true);
		}
	}

}
