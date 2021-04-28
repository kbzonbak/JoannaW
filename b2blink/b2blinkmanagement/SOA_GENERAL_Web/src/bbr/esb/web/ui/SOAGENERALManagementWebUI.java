package bbr.esb.web.ui;

import java.util.Date;

import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import bbr.esb.web.beans.SessionBean;
import bbr.esb.web.constants.BbrAppConstants;
import bbr.esb.web.resources.ResourceUtil;

@SuppressWarnings("serial")
@Theme("soa_general_webtheme")
// @Widgetset("bbr.esb.web.ui.widgetset.SOAGENERALManagementWidgetset")
public class SOAGENERALManagementWebUI extends UI {

	protected Logger logger = LoggerFactory.getLogger("SOAGENERALManagementWebUI");

	protected Long selectedFunctionalityId;

	protected Label lblSessionInfo;

	protected ComboBox databaseCombo;

	protected SessionBean sessionBean;

	protected TabSheet tabsheet;

	protected ServicesPanel servicesPanel;

	protected UsersPanel usersPanel;

	protected CompaniesPanel companiesPanel;

	protected ContractedServicesPanel contractedServicesPanel;

	protected FileEventsPanel fileEventsPanel;

	@WebServlet(value = { "/main/*", "/VAADIN/*" }, asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = SOAGENERALManagementWebUI.class, closeIdleSessions = true)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		this.sessionBean = (SessionBean) request.getWrappedSession().getAttribute("sessionBean");

		if (this.sessionBean == null) {

			// 2016-06-02 SI NO ESTÁ AUTENFICIADO, SE CONSULTA POR EL TIPO DE AUTENTIFICACIÓN:
			// 1) SI ES POR LDAP (HEADERS) SE HACE LA VALIDACIÓN POR HEADERS
			// 2) SI ES POR AUTENTIFICACIÓN, YA SEA POR ARCHIVO DE PROPERTIES INTERNO O POR LDAP INTERNO,
			// SE REDIRIGE A LA PÁGINA DE LOGIN

			String authentication_type = System.getProperty("authentication_type");

			if (authentication_type == null || authentication_type.equals(BbrAppConstants.AUTHENTICATION_TYPE_LOGIN) || authentication_type.equals(BbrAppConstants.AUTHENTICATION_TYPE_LDAP_INTERNAL)) {

				// Redirigir a página de login
				String loginUrl = ResourceUtil.getInstance().getPropertyValue(BbrAppConstants.RES_B2B_PAGES, "login");
				this.getPage().setLocation(loginUrl);
				return;

			} else if (authentication_type.equals(BbrAppConstants.AUTHENTICATION_TYPE_LDAP)) {

				// Validar headers

				boolean validated = false;
				String groups = request.getHeader(BbrAppConstants.REQUEST_REMOTE_USER_GROUPS);

				if (groups != null) {

					String[] groupsArr = groups.split(",");
					for (String group : groupsArr) {
						if (BbrAppConstants.LDAP_GROUP_NAME.equals(group.trim())) {
							validated = true;
							break;
						}
					}
				}

				String username = request.getHeader(BbrAppConstants.REQUEST_REMOTE_USER_EMAIL);

				if (validated && username != null && !username.trim().isEmpty()) {

					SessionBean newSessionBean = new SessionBean();
					newSessionBean.setUsername(username);
					newSessionBean.setLoginDate(new Date());
					newSessionBean.setLastAccessDate(new Date());

					VaadinSession.getCurrent().getSession().setAttribute("sessionBean", newSessionBean);

					this.sessionBean = newSessionBean;

					logger.info("Usuario autenticado : " + this.sessionBean);

				} else {

					// Redirigir a página de login
					String loginUrl = ResourceUtil.getInstance().getPropertyValue(BbrAppConstants.RES_B2B_PAGES, "login");
					this.getPage().setLocation(loginUrl);
					return;

				}
			} else {

				// Redirigir a página de login
				String loginUrl = ResourceUtil.getInstance().getPropertyValue(BbrAppConstants.RES_B2B_PAGES, "login");
				this.getPage().setLocation(loginUrl);
				return;

			}

		} else {
			logger.info("Usuario autenticado : " + this.sessionBean);
		}

		final VerticalLayout layout = new VerticalLayout();

		Component headerPanel = createHeaderPanel();
		Component userPanel = createUserPanel();
		Component mainPanel = createMainPanel();

		layout.addComponent(headerPanel, 0);
		layout.addComponent(userPanel, 1);
		layout.addComponent(mainPanel, 2);

		layout.setExpandRatio(headerPanel, 0);
		layout.setExpandRatio(userPanel, 0);
		layout.setExpandRatio(mainPanel, 1);

		layout.setSizeFull();
		this.setContent(layout);

		showData();

	}

	private Component createHeaderPanel() {
		final AbsoluteLayout headerLayout = new AbsoluteLayout();
		Panel headerPanel = new Panel();
		headerPanel.setHeight(107, Unit.PIXELS);
		headerPanel.setContent(headerLayout);
		headerPanel.setStyleName("header-panel");
		headerPanel.setId("headerPanel");

		Image image = getImage("bg_header");
		Image logo_image = getImage("logo_header");
		// image.addStyleName("logo-header");

		headerLayout.addComponent(image, "right: 0px; top: 0px");
		headerLayout.addComponent(logo_image, "left: 0px; top: 0px");

		return headerPanel;

	}

	private Component createUserPanel() {
		final AbsoluteLayout headerLayout = new AbsoluteLayout();
		Panel headerPanel = new Panel();
		headerPanel.setHeight(40, Unit.PIXELS);
		headerPanel.setContent(headerLayout);
		headerPanel.setStyleName("user-panel");
		headerPanel.setId("userPanel");

		lblSessionInfo = new Label();
		lblSessionInfo.addStyleName("label-session");

		Label label = new Label("Usuario : " + (this.sessionBean != null ? this.sessionBean.getUsername() : ""));
		label.addStyleName("label-user");
		Button btnClose = new Button();
		btnClose.setStyleName("btn-close-session");
		btnClose.setIcon(FontAwesome.POWER_OFF);
		btnClose.setDescription("Cerrar Sesión");

		btnClose.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				VaadinSession.getCurrent().getSession().setAttribute("sessionBean", null);

				String loginUrl = ResourceUtil.getInstance().getPropertyValue(BbrAppConstants.RES_B2B_PAGES, "login");
				SOAGENERALManagementWebUI.this.getPage().setLocation(loginUrl);

			}
		});

		headerLayout.addComponent(lblSessionInfo);
		headerLayout.addComponent(label);
		headerLayout.addComponent(btnClose);

		return headerPanel;
	}

	private Component createMainPanel() {

		usersPanel = new UsersPanel(this);

		servicesPanel = new ServicesPanel(this);

		companiesPanel = new CompaniesPanel(this);

		contractedServicesPanel = new ContractedServicesPanel(this);

		fileEventsPanel = new FileEventsPanel(this);

		tabsheet = new TabSheet();

		tabsheet.addTab(usersPanel, "Usuarios");
		tabsheet.addTab(servicesPanel, "Servicios");
		tabsheet.addTab(companiesPanel, "Empresas");
		tabsheet.addTab(contractedServicesPanel, "Servicios Contratados");
		tabsheet.addTab(fileEventsPanel, "Archivos Generados");
		tabsheet.setSizeFull();
		tabsheet.setStyleName("border");

		tabsheet.addSelectedTabChangeListener(e -> changeSelectedTab(e));

		return tabsheet;
	}

	private Image getImage(String name) {
		String res_logo = ResourceUtil.getInstance().getPropertyValue(BbrAppConstants.RES_B2B_PAGES, name);
		Resource res = new ThemeResource(res_logo);
		Image image = new Image(null, res);
		return image;
	}

	private void changeSelectedTab(SelectedTabChangeEvent event) {
		if (event.getTabSheet().getSelectedTab() instanceof UsersPanel) {
			UsersPanel panel = (UsersPanel) event.getTabSheet().getSelectedTab();
			panel.showData();
		} else if (event.getTabSheet().getSelectedTab() instanceof ServicesPanel) {
			ServicesPanel panel = (ServicesPanel) event.getTabSheet().getSelectedTab();
			panel.showData();
		} else if (event.getTabSheet().getSelectedTab() instanceof CompaniesPanel) {
			CompaniesPanel panel = (CompaniesPanel) event.getTabSheet().getSelectedTab();
			panel.showData();
		} else if (event.getTabSheet().getSelectedTab() instanceof ContractedServicesPanel) {
			ContractedServicesPanel panel = (ContractedServicesPanel) event.getTabSheet().getSelectedTab();
			panel.showData();
			// } else if (event.getTabSheet().getSelectedTab() instanceof ServiceOperationsPanel) {
			// ServiceOperationsPanel panel = (ServiceOperationsPanel) event.getTabSheet().getSelectedTab();
			// panel.showData();
		}
	}

	public void showData() {
		// funcsPanel.update();
		// profilesPanel.update();
		// servicesPanel.update();
		// serviceOperationsPanel.update();
		changeSelectedTab(new SelectedTabChangeEvent(tabsheet));
	}

}