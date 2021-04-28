package bbr.esb.web.ui;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.managers.UserManagerLocal;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.web.DialogResult;
import bbr.esb.web.resources.EJBUtils;
import bbr.esb.web.resources.ResourceUtil;

@SuppressWarnings("serial")
public class CompaniesPanel extends Panel {

	protected Logger logger = LoggerFactory.getLogger("SOAGENERALManagementWebUI");

	protected UI owner;

	private UserManagerLocal usermanagerlocal = null;

	protected Grid companiesTable;

	protected CompanyDTO selectedCompany;

	protected ContextMenu menu;

	protected Boolean dirty = true;

	public CompaniesPanel(UI owner) {
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

		this.setSizeFull();
		this.setContent(layout);

	}

	protected Component createCommandPanel() {

		Panel panel = new Panel();

		final HorizontalLayout commandLayout = new HorizontalLayout();
		commandLayout.setMargin(true);
		commandLayout.setSizeFull();

		Button btnAddCompany = new Button("Agregar Empresa");
		btnAddCompany.addStyleName("commandButton");
		btnAddCompany.addClickListener(e -> doAddCompany());

		Button btnEditCompany = new Button("Editar Empresa");
		btnEditCompany.addStyleName("commandButton");
		btnEditCompany.addClickListener(e -> doEditCompany());

		Button btnAccesses = new Button("Accesos");
		btnAccesses.addStyleName("commandButton");
		btnAccesses.addClickListener(e -> doManageAccesses());

		Button btnFolders = new Button("Casillas");
		btnFolders.addStyleName("commandButton");
		btnFolders.addClickListener(e -> doManageFolders());

		Button btnContractedServices = new Button("Servicios Contratados");
		btnContractedServices.addStyleName("commandButton");
		btnContractedServices.addClickListener(e -> doManageContractedServices());

		Button btnRefresh = new Button();
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

		commandLayout.addComponent(btnAddCompany);
		commandLayout.addComponent(btnEditCompany);
		commandLayout.addComponent(btnAccesses);
		commandLayout.addComponent(btnFolders);
		commandLayout.addComponent(btnContractedServices);
		commandLayout.addComponent(btnRefresh);

		commandLayout.setComponentAlignment(btnAddCompany, Alignment.TOP_RIGHT);
		commandLayout.setComponentAlignment(btnEditCompany, Alignment.TOP_RIGHT);
		commandLayout.setComponentAlignment(btnAccesses, Alignment.TOP_RIGHT);
		commandLayout.setComponentAlignment(btnFolders, Alignment.TOP_RIGHT);
		commandLayout.setComponentAlignment(btnContractedServices, Alignment.TOP_RIGHT);
		commandLayout.setComponentAlignment(btnRefresh, Alignment.TOP_RIGHT);

		panel.setContent(commandLayout);
		panel.setSizeFull();
		return panel;
	}

	private Component createGridPanel() {
		Panel panel = new Panel();
		HorizontalLayout layout = new HorizontalLayout();

		companiesTable = new Grid("");

		companiesTable.setContainerDataSource(new BeanItemContainer<>(CompanyDTO.class));
		companiesTable.removeColumn("id");
		companiesTable.removeColumn("encrypt");
		companiesTable.removeColumn("encryptpassword");
		
//		companiesTable.removeColumn("Clientavaliable");
//		companiesTable.removeColumn("Dafaultmaxdelayendflow");
//		companiesTable.removeColumn("Lastclientcheck");
//		companiesTable.removeColumn("Monitoreable");
		
		companiesTable.setColumnOrder("rut", "name", "as2id");
		companiesTable.getColumn("rut").setHeaderCaption("RUT");
		companiesTable.getColumn("name").setHeaderCaption("Empresa");
		companiesTable.getColumn("as2id").setHeaderCaption("Partner AS2");

		companiesTable.setSizeFull();

		companiesTable.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				// El ID del item corresponde a la llave de la empresa
				if (event.getItemId() != null) {
					CompanyDTO company = (CompanyDTO) event.getItemId();
					selectedCompany = company;
				}
			}
		});

		layout.addComponent(companiesTable);
		layout.setSizeFull();
		panel.setContent(layout);
		panel.setSizeFull();
		return panel;
	}

	private void doAddCompany() {
		try {
			EditCompanyWindow window = new EditCompanyWindow("Agregar Empresa", null);
			window.center();
			CompaniesPanel.this.owner.addWindow(window);
			window.addCloseListener(new CloseListener() {

				@Override
				public void windowClose(CloseEvent e) {
					if (window.getDialogResult().equals(DialogResult.OK)) {
						// Obtener el dato y actualizarlo en la BD
						CompanyDTO editedCompany = window.getCompany();
						try {
							usermanagerlocal.addCompany(editedCompany);
							doShowData();
							selectedCompany = null;
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

	private void doEditCompany() {
		try {
			if (selectedCompany == null)
				return;
			EditCompanyWindow window = new EditCompanyWindow("Editar Empresa", selectedCompany);
			window.center();
			CompaniesPanel.this.owner.addWindow(window);
			window.addCloseListener(new CloseListener() {

				@Override
				public void windowClose(CloseEvent e) {
					if (window.getDialogResult().equals(DialogResult.OK)) {
						// Obtener el dato y actualizarlo en la BD
						CompanyDTO editedCompany = window.getCompany();
						try {
							usermanagerlocal.updateCompany(editedCompany);
							doShowData();
							selectedCompany = null;
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

	private void doManageAccesses() {
		try {
			if (selectedCompany == null)
				return;
			AccessListWindow window = new AccessListWindow("Edición de Accesos - Empresa " + selectedCompany.getName(), selectedCompany);
			window.center();
			CompaniesPanel.this.owner.addWindow(window);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doManageFolders() {
		try {
			if (selectedCompany == null)
				return;
			FolderListWindow window = new FolderListWindow("Edición de Casillas - Empresa " + selectedCompany.getName(), selectedCompany);
			window.center();
			CompaniesPanel.this.owner.addWindow(window);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doManageContractedServices() {
		try {
			if (selectedCompany == null)
				return;
			ContractedServicesListWindow window = new ContractedServicesListWindow("Edición de Servicios Contratados - Empresa " + selectedCompany.getName(), selectedCompany);
			window.center();
			CompaniesPanel.this.owner.addWindow(window);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doShowData() {
		// Listar en la grilla
		try {

			// Obtener todos los servicios de los sitios
			CompanyDTO[] servicesArr = usermanagerlocal.getCompanies();
			List<CompanyDTO> servicesList = Arrays.asList(servicesArr);

			BeanItemContainer<CompanyDTO> container = new BeanItemContainer<>(CompanyDTO.class, servicesList);
			companiesTable.setContainerDataSource(container);

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

}
