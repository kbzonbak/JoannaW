package bbr.esb.web.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.data.classes.SiteDTO;
import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.users.data.classes.AccessDataDTO;
import bbr.esb.web.DialogResult;
import bbr.esb.web.resources.EJBUtils;

@SuppressWarnings("serial")
public class EditAccessWindow extends Window {

	private ServiceManagerLocal servicemanagerlocal = null;

	private Long companyid;

	private AccessDataDTO access;

	private TextField edCode;

	private TextField edName;

	private ComboBox edSite;

	private Button btnSave;

	private Button btnCancel;

	private DialogResult dialogResult = DialogResult.NONE;

	public EditAccessWindow(String caption, Long companyid, AccessDataDTO access) {
		super(caption);

		servicemanagerlocal = EJBUtils.getInstance().getServiceManagerLocalEJB();

		this.companyid = companyid;
		this.access = access;
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

		controlsLayout.addComponent(new Label("Sitio  : "), 0, 0);
		controlsLayout.addComponent(new Label("Código : "), 0, 1);
		controlsLayout.addComponent(new Label("Nombre : "), 0, 2);

		SiteDTO[] sites = null;
		try {
			sites = servicemanagerlocal.getSites();
		} catch (AccessDeniedException | OperationFailedException | NotFoundException e1) {
			e1.printStackTrace();
		}

		List<SiteDTO> sitesList = Arrays.asList(sites);
		BeanItemContainer<SiteDTO> container = new BeanItemContainer<>(SiteDTO.class, sitesList);

		// edSite = new ComboBox();
		edSite = new ComboBox(null, container);
		edSite.setItemCaptionPropertyId("name");
		edSite.setTextInputAllowed(false);
		edSite.setNullSelectionAllowed(false);
		edSite.setWidth(100F, Unit.PERCENTAGE);
		edSite.setPageLength(0);

		controlsLayout.addComponent(edSite, 1, 0);
		if (access != null) {
			// Setear el combobox en el valor del Site y dejarlo fijo
			Optional<SiteDTO> siteOptional = sitesList.stream().filter(site -> site.getId().equals(access.getSitekey())).findFirst();
			if (siteOptional.isPresent()) {
				edSite.select(siteOptional.get());
				edSite.setReadOnly(true);
			}
		}

		edCode = new TextField();
		edCode.setWidth(100F, Unit.PERCENTAGE);
		edCode.setValue(access != null ? access.getCode() : "");
		controlsLayout.addComponent(edCode, 1, 1);

		edName = new TextField();
		edName.setWidth(100F, Unit.PERCENTAGE);
		edName.setValue(access != null ? access.getName() : "");
		controlsLayout.addComponent(edName, 1, 2);

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
				if (edCode.getValue().trim().isEmpty()) {
					Notification.show("El Código es obligatorio", Type.ERROR_MESSAGE);
					return;
				}
				if (edName.getValue().trim().isEmpty()) {
					Notification.show("El Nombre es obligatorio", Type.ERROR_MESSAGE);
					return;
				}
				if (edSite.getValue() == null) {
					Notification.show("El Sitio es obligatorio", Type.ERROR_MESSAGE);
					return;
				}
				// // GUARDAR LAS PROPIEDADES EN EL OBJETO
				AccessDataDTO anAccess = new AccessDataDTO();
				anAccess.setCode(edCode.getValue());
				anAccess.setName(edName.getValue());

				if (EditAccessWindow.this.access != null) {
					anAccess.setCompanykey(EditAccessWindow.this.access.getCompanykey());
					anAccess.setSitekey(EditAccessWindow.this.access.getSitekey());
				} else {
					anAccess.setCompanykey(EditAccessWindow.this.companyid);
					SiteDTO site = (SiteDTO) edSite.getValue();
					anAccess.setSitekey(site.getId());
				}

				EditAccessWindow.this.access = anAccess;
				// Si todo está OK, cerrar la ventana
				dialogResult = DialogResult.OK;
				EditAccessWindow.this.close();
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
				EditAccessWindow.this.close();
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

	public AccessDataDTO getAccess() {
		return access;
	}

	public DialogResult getDialogResult() {
		return this.dialogResult;
	}
}
