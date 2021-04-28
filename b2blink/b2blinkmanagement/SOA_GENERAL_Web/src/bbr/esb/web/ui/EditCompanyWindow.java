package bbr.esb.web.ui;

import java.util.Date;

import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.web.DialogResult;

@SuppressWarnings("serial")
public class EditCompanyWindow extends Window {

	private CompanyDTO company;

	private TextField edRUT;

	private TextField edName;

	private TextField edAS2ID;

	private Button btnSave;

	private Button btnCancel;

	private DialogResult dialogResult = DialogResult.NONE;

	public EditCompanyWindow(String caption, CompanyDTO company) {
		super(caption);
		this.company = company;
		this.setModal(true);
		this.setWidth(15, Unit.CM);
		this.setHeight(8, Unit.CM);
		this.setWindowMode(WindowMode.NORMAL);

		GridLayout controlsLayout = new GridLayout(2, 3);
		controlsLayout.setColumnExpandRatio(0, 0.2f);
		controlsLayout.setColumnExpandRatio(1, 0.8f);

		controlsLayout.setWidth("100%");
		controlsLayout.setMargin(true);
		controlsLayout.setSpacing(true);

		controlsLayout.addComponent(new Label("RUT: "), 0, 0);
		controlsLayout.addComponent(new Label("Nombre: "), 0, 1);
		controlsLayout.addComponent(new Label("AS2 ID: "), 0, 2);

		edRUT = new TextField();
		edRUT.setWidth(100F, Unit.PERCENTAGE);
		edRUT.setValue(company != null ? company.getRut() : "");
		controlsLayout.addComponent(edRUT, 1, 0);

		edName = new TextField();
		edName.setWidth(100F, Unit.PERCENTAGE);
		edName.setValue(company != null ? company.getName() : "");
		controlsLayout.addComponent(edName, 1, 1);

		edAS2ID = new TextField();
		edAS2ID.setWidth(100F, Unit.PERCENTAGE);
		edAS2ID.setValue(company != null ? company.getAs2id() : "");
		controlsLayout.addComponent(edAS2ID, 1, 2);

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
				// // GUARDAR LAS PROPIEDADES EN EL OBJETO
				CompanyDTO acompany = new CompanyDTO();
				acompany.setRut(edRUT.getValue());
				acompany.setName(edName.getValue());
				acompany.setAs2id(edAS2ID.getValue());

				if (EditCompanyWindow.this.company != null) {
					acompany.setId(EditCompanyWindow.this.company.getId());
					acompany.setEncrypt(EditCompanyWindow.this.company.getEncrypt());
					acompany.setEncryptpassword(EditCompanyWindow.this.company.getEncryptpassword());
					acompany.setDafaultmaxdelayendflow(60);
					acompany.setLastclientcheck(new Date());
				}
				EditCompanyWindow.this.company = acompany;
				// Si todo está OK, cerrar la ventana
				dialogResult = DialogResult.OK;
				EditCompanyWindow.this.close();
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
				EditCompanyWindow.this.close();
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

	public CompanyDTO getCompany() {
		return company;
	}

	public DialogResult getDialogResult() {
		return this.dialogResult;
	}
}
