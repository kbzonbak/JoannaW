package bbr.esb.web.ui;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.users.data.classes.AccessDTO;
import bbr.esb.users.data.classes.AccessDataDTO;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.web.DialogResult;
import bbr.esb.web.resources.EJBUtils;

@SuppressWarnings("serial")
public class AccessListWindow extends Window {

	private ServiceManagerLocal servicemanagerlocal = null;

	protected Grid accessTable;

	private CompanyDTO company;

	private Button btnAddAccess;

	private Button btnEditAccess;

	private Button btnCancel;

	protected AccessDataDTO selectedAccess;

	private DialogResult dialogResult = DialogResult.NONE;

	public AccessListWindow(String caption, CompanyDTO company) {
		super(caption);

		servicemanagerlocal = EJBUtils.getInstance().getServiceManagerLocalEJB();

		this.company = company;
		this.setModal(true);
		this.setWidth(15, Unit.CM);
		this.setHeight(8, Unit.CM);
		this.setWindowMode(WindowMode.NORMAL);

		VerticalSplitPanel layout = new VerticalSplitPanel();

		// Panel Principal : Grid
		Component gridPanel = createGridPanel();

		// Panel Inferior : Botones
		Component commandPanel = createCommandPanel();

		layout.setFirstComponent(gridPanel);
		layout.setSecondComponent(commandPanel);
		layout.setSplitPosition(80);

		this.setContent(layout);

		doShowData();

		this.center();
	}

	protected Component createCommandPanel() {

		Panel panel = new Panel();

		final HorizontalLayout commandLayout = new HorizontalLayout();
		commandLayout.setMargin(true);
		commandLayout.setSizeFull();

		btnAddAccess = new Button("Agregar Acceso");
		btnAddAccess.addStyleName("commandButton");
		btnAddAccess.addClickListener(e -> doAddAccess());

		btnEditAccess = new Button("Editar Acceso");
		btnEditAccess.addStyleName("commandButton");
		btnEditAccess.addClickListener(e -> doEditAccess());

		btnCancel = new Button("Cancelar");
		btnCancel.addStyleName("commandButton");
		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				dialogResult = DialogResult.CANCEL;
				AccessListWindow.this.close();
			}
		});

		commandLayout.addComponent(btnAddAccess);
		commandLayout.addComponent(btnEditAccess);
		commandLayout.addComponent(btnCancel);

		panel.setContent(commandLayout);
		panel.setSizeFull();
		return panel;
	}

	private Component createGridPanel() {
		Panel panel = new Panel();
		HorizontalLayout layout = new HorizontalLayout();

		accessTable = new Grid("");

		accessTable.setContainerDataSource(new BeanItemContainer<>(AccessDataDTO.class));
		accessTable.removeColumn("companykey");
		accessTable.removeColumn("sitekey");
		accessTable.removeColumn("companyname");
		accessTable.getColumn("sitename").setHeaderCaption("Sitio");
		accessTable.getColumn("sitename").setMaximumWidth(160.0);
		accessTable.getColumn("code").setHeaderCaption("Código");
		accessTable.getColumn("code").setMaximumWidth(90.0);
		accessTable.getColumn("name").setHeaderCaption("Nombre");
		// accessTable.getColumn("name").setMaximumWidth(200.0);
		accessTable.setColumnOrder("sitename", "code", "name");

		accessTable.setSizeFull();

		accessTable.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				// El ID del item corresponde a la llave de la empresa
				if (event.getItemId() != null) {
					AccessDataDTO access = (AccessDataDTO) event.getItemId();
					selectedAccess = access;
				}
			}
		});

		layout.addComponent(accessTable);
		layout.setSizeFull();
		panel.setContent(layout);
		panel.setSizeFull();
		return panel;
	}

	private void doAddAccess() {
		try {
			EditAccessWindow window = new EditAccessWindow("Agregar Acceso", company.getId(), null);
			window.center();
			AccessListWindow.this.getUI().addWindow(window);
			window.addCloseListener(new CloseListener() {

				@Override
				public void windowClose(CloseEvent e) {
					if (window.getDialogResult().equals(DialogResult.OK)) {
						// Obtener el dato y actualizarlo en la BD
						AccessDataDTO editedAccess = window.getAccess();
						try {
							AccessDTO newAccess = new AccessDTO();
							newAccess.setCode(editedAccess.getCode());
							newAccess.setName(editedAccess.getName());
							newAccess.setCompanykey(editedAccess.getCompanykey());
							newAccess.setSitekey(editedAccess.getSitekey());
							servicemanagerlocal.addAccess(newAccess);
							doShowData();
							selectedAccess = null;
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

	private void doEditAccess() {
		try {
			if (selectedAccess == null)
				return;

			EditAccessWindow window = new EditAccessWindow("Editar Acceso", company.getId(), selectedAccess);
			window.center();
			AccessListWindow.this.getUI().addWindow(window);
			window.addCloseListener(new CloseListener() {

				@Override
				public void windowClose(CloseEvent e) {
					if (window.getDialogResult().equals(DialogResult.OK)) {
						// Obtener el dato y actualizarlo en la BD
						AccessDataDTO editedAccess = window.getAccess();
						try {
							AccessDTO newAccess = new AccessDTO();
							newAccess.setCode(editedAccess.getCode());
							newAccess.setName(editedAccess.getName());
							newAccess.setCompanykey(editedAccess.getCompanykey());
							newAccess.setSitekey(editedAccess.getSitekey());
							servicemanagerlocal.updateAccess(newAccess);
							doShowData();
							selectedAccess = null;
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

	public CompanyDTO getCompany() {
		return company;
	}

	public DialogResult getDialogResult() {
		return this.dialogResult;
	}

	private void doShowData() {
		// Listar en la grilla
		try {

			// Obtener todos los servicios de los sitios
			AccessDataDTO[] accessesDataArr = servicemanagerlocal.getAccessDataofCompany(company.getId());
			List<AccessDataDTO> servicesList = Arrays.asList(accessesDataArr);

			BeanItemContainer<AccessDataDTO> container = new BeanItemContainer<>(AccessDataDTO.class, servicesList);
			accessTable.setContainerDataSource(container);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
