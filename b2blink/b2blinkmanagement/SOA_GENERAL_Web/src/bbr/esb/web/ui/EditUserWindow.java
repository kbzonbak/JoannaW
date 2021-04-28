package bbr.esb.web.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.managers.UserManagerLocal;
import bbr.esb.users.data.classes.UserDTO;
import bbr.esb.users.data.classes.UserTypeDTO;
import bbr.esb.web.DialogResult;
import bbr.esb.web.resources.EJBUtils;

@SuppressWarnings("serial")
public class EditUserWindow extends Window {

	private UserDTO user;

	private TextField edRUT;

	private TextField edName;

	private TextField edLastname;

	private TextField edEmail;

	private CheckBox edActive;

	private CheckBox edBlocked;

	private PasswordField edPassword1;

	private PasswordField edPassword2;

	private ComboBox edUserTypes;

	private Button btnSave;

	private Button btnCancel;

	private DialogResult dialogResult = DialogResult.NONE;

	private UserManagerLocal usermanagerlocal = null;

	public EditUserWindow(String caption, UserDTO user) {
		super(caption);

		usermanagerlocal = EJBUtils.getInstance().getUserManagerLocalEJB();

		this.user = user;
		this.setModal(true);
		this.setWidth(15, Unit.CM);
		this.setHeight(12, Unit.CM);
		this.setWindowMode(WindowMode.NORMAL);

		GridLayout controlsLayout = new GridLayout(2, 9);
		controlsLayout.setColumnExpandRatio(0, 0.2f);
		controlsLayout.setColumnExpandRatio(1, 0.8f);

		controlsLayout.setWidth("100%");
		controlsLayout.setMargin(true);
		controlsLayout.setSpacing(true);

		controlsLayout.addComponent(new Label("RUT: "), 0, 0);
		controlsLayout.addComponent(new Label("Nombre: "), 0, 1);
		controlsLayout.addComponent(new Label("Apellido: "), 0, 2);
		controlsLayout.addComponent(new Label("Email: "), 0, 3);
		controlsLayout.addComponent(new Label("Activo: "), 0, 4);
		controlsLayout.addComponent(new Label("Bloqueado: "), 0, 5);
		controlsLayout.addComponent(new Label("Password: "), 0, 6);
		controlsLayout.addComponent(new Label("Repita Password: "), 0, 7);
		controlsLayout.addComponent(new Label("Tipo: "), 0, 8);

		edRUT = new TextField();
		edRUT.setWidth(100F, Unit.PERCENTAGE);
		edRUT.setValue(user != null ? user.getRut() : "");
		controlsLayout.addComponent(edRUT, 1, 0);

		edName = new TextField();
		edName.setWidth(100F, Unit.PERCENTAGE);
		edName.setValue(user != null ? user.getName() : "");
		controlsLayout.addComponent(edName, 1, 1);

		edLastname = new TextField();
		edLastname.setWidth(100F, Unit.PERCENTAGE);
		edLastname.setValue(user != null ? user.getLastname() : "");
		controlsLayout.addComponent(edLastname, 1, 2);

		edEmail = new TextField();
		edEmail.setWidth(100F, Unit.PERCENTAGE);
		edEmail.setValue(user != null && user.getEmail() != null ? user.getEmail() : "");
		controlsLayout.addComponent(edEmail, 1, 3);

		edActive = new CheckBox();
		edActive.setValue(user != null ? user.getActive() : false);
		controlsLayout.addComponent(edActive, 1, 4);

		edBlocked = new CheckBox();
		edBlocked.setValue(user != null && user.getBlocked() != null ? user.getBlocked() : false);
		controlsLayout.addComponent(edBlocked, 1, 5);

		edPassword1 = new PasswordField();
		edPassword1.setWidth(100F, Unit.PERCENTAGE);
		edPassword1.setValue(user != null ? user.getPassword() : "");
		controlsLayout.addComponent(edPassword1, 1, 6);

		edPassword2 = new PasswordField();
		edPassword2.setWidth(100F, Unit.PERCENTAGE);
		edPassword2.setValue(user != null ? user.getPassword() : "");
		controlsLayout.addComponent(edPassword2, 1, 7);

		UserTypeDTO[] usertypes = null;
		try {
			usertypes = usermanagerlocal.getUserTypes();
			List<UserTypeDTO> usertypesList = Arrays.asList(usertypes);
			BeanItemContainer<UserTypeDTO> usertypesContainer = new BeanItemContainer<>(UserTypeDTO.class, usertypesList);
			edUserTypes = new ComboBox();
			edUserTypes.setWidth(100F, Unit.PERCENTAGE);
			edUserTypes.setContainerDataSource(usertypesContainer);
			edUserTypes.setItemCaptionPropertyId("name");
			edUserTypes.setTextInputAllowed(false);
			edUserTypes.setNullSelectionAllowed(false);

			if (user != null) {
				Optional<UserTypeDTO> optUserType = usertypesList.stream().filter(myuser -> user.getUsertypeid().equals(myuser.getId())).findFirst();
				if (optUserType != null && optUserType.isPresent()) {
					edUserTypes.select(optUserType.get());
				}
			}

			controlsLayout.addComponent(edUserTypes, 1, 8);

		} catch (AccessDeniedException | OperationFailedException | NotFoundException e1) {
			e1.printStackTrace();
		}

		HorizontalLayout commandsLayout = new HorizontalLayout();
		commandsLayout.setSpacing(true);
		commandsLayout.setMargin(true);
		commandsLayout.setWidthUndefined();

		btnSave = new Button("Guardar");
		btnSave.addStyleName("commandButton");
		btnSave.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// VALIDAR LOS CAMPOS
				if (edRUT.getValue().trim().isEmpty()) {
					Notification.show("El RUT es obligatorio", Type.ERROR_MESSAGE);
					return;
				}
				if (edName.getValue().trim().isEmpty()) {
					Notification.show("El Nombre es obligatorio", Type.ERROR_MESSAGE);
					return;
				}
				if (edPassword1.getValue().trim().isEmpty() || edPassword2.getValue().trim().isEmpty()) {
					Notification.show("La password es obligatoria", Type.ERROR_MESSAGE);
					return;
				}
				if (!edPassword1.getValue().equals(edPassword2.getValue())) {
					Notification.show("Las passwords ingresadas no coinciden", Type.ERROR_MESSAGE);
					return;
				}
				if (edUserTypes.getValue() == null) {
					Notification.show("El tipo de usuario es obligatorio", Type.ERROR_MESSAGE);
					return;
				}
				UserTypeDTO selectedUserType = (UserTypeDTO) edUserTypes.getValue();

				// GUARDAR LAS PROPIEDADES EN EL OBJETO
				UserDTO auser = new UserDTO();
				auser.setRut(edRUT.getValue());
				auser.setName(edName.getValue());
				auser.setLastname(edLastname.getValue());
				auser.setEmail(edEmail.getValue());
				auser.setActive(edActive.getValue());
				auser.setBlocked(edBlocked.getValue());
				auser.setPassword(edPassword1.getValue());
				auser.setUsertypeid(selectedUserType.getId());
				

				if (EditUserWindow.this.user != null) {
					auser.setId(EditUserWindow.this.user.getId());
					auser.setLastaccess(EditUserWindow.this.user.getLastaccess());
					auser.setLastlogon(EditUserWindow.this.user.getLastlogon());
					auser.setLastpasschange(EditUserWindow.this.user.getLastpasschange());
					// auser.setPassword(EditUserWindow.this.user.getPassword());
					auser.setSessionid(EditUserWindow.this.user.getSessionid());
					auser.setTriescount(EditUserWindow.this.user.getTriescount());
					auser.setCheckvolatile(0);
					auser.setQuestion(EditUserWindow.this.user.getQuestion());
					auser.setAnswer(EditUserWindow.this.user.getAnswer());
					// auser.setUsertypeid(EditUserWindow.this.user.getUsertypeid());
				}
				EditUserWindow.this.user = auser;
				// Si todo está OK, cerrar la ventana
				dialogResult = DialogResult.OK;
				EditUserWindow.this.close();
			}
		});

		commandsLayout.addComponent(btnSave);
		commandsLayout.setComponentAlignment(btnSave, Alignment.MIDDLE_CENTER);

		btnCancel = new Button("Cancelar");
		btnCancel.addStyleName("commandButton");
		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				dialogResult = DialogResult.CANCEL;
				EditUserWindow.this.close();
			}
		});

		commandsLayout.addComponent(btnCancel);
		commandsLayout.setComponentAlignment(btnCancel, Alignment.MIDDLE_CENTER);
		commandsLayout.setSizeFull();

		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(controlsLayout);
		layout.addComponent(commandsLayout);

		this.setContent(layout);

		this.addCloseListener(new CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {
				// Si se cerró usando el botón propio de la ventana, se asume CANCEL
				if (dialogResult.equals(DialogResult.NONE))
					dialogResult = DialogResult.CANCEL;
			}
		});
		this.center();
	}

	public UserDTO getUser() {
		return user;
	}

	public DialogResult getDialogResult() {
		return this.dialogResult;
	}
}
