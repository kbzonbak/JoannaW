package bbr.esb.web.ui;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;

import org.vaadin.peter.contextmenu.ContextMenu;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.renderers.HtmlRenderer;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.managers.UserManagerLocal;
import bbr.esb.users.data.classes.UserDTO;
import bbr.esb.web.DialogResult;
import bbr.esb.web.resources.EJBUtils;
import bbr.esb.web.resources.ResourceUtil;

@SuppressWarnings("serial")
public class UsersPanel extends Panel {

	protected UI owner;

	@EJB
	private UserManagerLocal usermanagerlocal = null;

	protected Grid usersTable;

	protected UserDTO selectedUser;

	protected ContextMenu menu;

	protected Boolean dirty = true;

	public UsersPanel(UI owner) {
		this.owner = owner;

		usermanagerlocal = EJBUtils.getInstance().getUserManagerLocalEJB();

		final VerticalSplitPanel layout = new VerticalSplitPanel();

		// Panel Superior : Botones
		Component commandPanel = createCommandPanel();

		// Panel Principal : Grid
		Component gridPanel = createGridPanel();

		layout.setFirstComponent(commandPanel);
		layout.setSecondComponent(gridPanel);
		layout.setSplitPosition(10, Unit.PERCENTAGE);
		layout.setLocked(true);

		layout.setSizeFull();
		this.setSizeFull();
		this.setContent(layout);

	}

	protected Component createCommandPanel() {

		Panel panel = new Panel();

		final HorizontalLayout commandLayout = new HorizontalLayout();
		commandLayout.setStyleName("command-layout");
		commandLayout.setSizeFull();
		commandLayout.setSpacing(true);
		commandLayout.setMargin(true);

		Button btnAddUser = new Button("Agregar Usuario");
		btnAddUser.addStyleName("commandButton");
		btnAddUser.addClickListener(e -> doAddUser());

		Button btnEditUser = new Button("Editar Usuario");
		btnEditUser.addStyleName("commandButton");
		btnEditUser.addClickListener(e -> doEditUser());

		Button btnCompanies = new Button("Empresas");
		btnCompanies.addStyleName("commandButton");
		btnCompanies.addClickListener(e -> doManageCompanies());

		Button btnRefresh = new Button();
		btnRefresh.setSizeUndefined();
		btnRefresh.addStyleName("commandButton");
		Resource icon = ResourceUtil.getInstance().getResourceFromThemeResource("icon_refresh");
		btnRefresh.setIcon(icon, "Refrescar");
		btnRefresh.setDescription("Refrescar");
		btnRefresh.setId("btnrefresh");
		btnRefresh.setStyleName("btn-refresh");

		btnRefresh.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// Invocar funcionalidad
				doShowData();
			}
		});

		commandLayout.addComponent(btnAddUser);
		commandLayout.addComponent(btnEditUser);
		commandLayout.addComponent(btnCompanies);
		commandLayout.addComponent(btnRefresh);
		commandLayout.setComponentAlignment(btnRefresh, Alignment.TOP_RIGHT);

		panel.setContent(commandLayout);
		// panel.setSizeFull();
		return panel;
	}

	private Component createGridPanel() {
		Panel panel = new Panel();
		HorizontalLayout layout = new HorizontalLayout();
		BooleanConverter booleanConverter = new BooleanConverter();
		DateConverter dateConverter = new DateConverter();

		usersTable = new Grid("");

		usersTable.setContainerDataSource(new BeanItemContainer<>(UserDTO.class));
		usersTable.removeColumn("answer");
		usersTable.removeColumn("checkvolatile");
		usersTable.removeColumn("id");
		usersTable.removeColumn("password");
		usersTable.removeColumn("question");
		usersTable.removeColumn("sessionid");
		usersTable.removeColumn("triescount");
		usersTable.removeColumn("usertypeid");
		usersTable.removeColumn("lastaccess");
		usersTable.removeColumn("lastpasschange");
		usersTable.setColumnOrder("rut", "lastname", "name", "email", "active", "blocked", "lastlogon");
		usersTable.getColumn("rut").setHeaderCaption("RUT");
		usersTable.getColumn("lastname").setHeaderCaption("Apellido");
		usersTable.getColumn("name").setHeaderCaption("Nombre");
		usersTable.getColumn("active").setHeaderCaption("Activo");
		usersTable.getColumn("blocked").setHeaderCaption("Bloqueado");
		usersTable.getColumn("lastlogon").setHeaderCaption("Último Acceso");
		usersTable.getColumn("active").setRenderer(new HtmlRenderer(), booleanConverter);
		usersTable.getColumn("blocked").setRenderer(new HtmlRenderer(), booleanConverter);
		usersTable.getColumn("lastlogon").setRenderer(new HtmlRenderer(), dateConverter);

		usersTable.setSizeFull();

		usersTable.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				// El ID del item corresponde a la llave de la empresa
				if (event.getItemId() != null) {
					UserDTO user = (UserDTO) event.getItemId();
					selectedUser = user;
				}
			}
		});

		layout.addComponent(usersTable);
		layout.setSizeFull();
		panel.setContent(layout);
		panel.setSizeFull();
		return panel;
	}

	private void doShowData() {
		// Listar en la grilla
		try {

			// Obtener todos los servicios de los sitios
			UserDTO[] usersArr = usermanagerlocal.getUsers();
			List<UserDTO> usersList = Arrays.asList(usersArr);

			BeanItemContainer<UserDTO> container = new BeanItemContainer<>(UserDTO.class, usersList);
			usersTable.setContainerDataSource(container);
			usersTable.refreshAllRows();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showData() {
		if (this.dirty) {
			doShowData();
			this.dirty = false;
		}
	}

	private void doAddUser() {
		try {
			EditUserWindow window = new EditUserWindow("Agregar Usuario", null);
			window.center();
			UsersPanel.this.owner.addWindow(window);
			window.addCloseListener(new CloseListener() {

				@Override
				public void windowClose(CloseEvent e) {
					if (window.getDialogResult().equals(DialogResult.OK)) {
						// Obtener el dato y actualizarlo en la BD
						UserDTO editedUser = window.getUser();
						try {
							usermanagerlocal.addUser(editedUser);
							doShowData();
							selectedUser = null;
						} catch (AccessDeniedException | OperationFailedException | NotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doEditUser() {
		try {
			if (selectedUser == null)
				return;
			EditUserWindow window = new EditUserWindow("Editar Usuario", selectedUser);
			window.center();
			UsersPanel.this.owner.addWindow(window);
			window.addCloseListener(new CloseListener() {

				@Override
				public void windowClose(CloseEvent e) {
					if (window.getDialogResult().equals(DialogResult.OK)) {
						// Obtener el dato y actualizarlo en la BD
						UserDTO editedUser = window.getUser();
						try {
							usermanagerlocal.updateUser(editedUser);
							doShowData();
							selectedUser = null;
						} catch (AccessDeniedException | OperationFailedException | NotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doManageCompanies() {
		try {
			if (selectedUser == null)
				return;
			UserCompanyWindow window = new UserCompanyWindow("Asociación de Usuario con Empresas - Usuario " + selectedUser.getName() + " " + selectedUser.getLastname(), selectedUser);
			window.center();
			UsersPanel.this.owner.addWindow(window);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
