package bbr.esb.web.ui;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.data.sort.SortDirection;
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
import bbr.esb.services.managers.UserManagerLocal;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.users.data.classes.UserCompanyDTO;
import bbr.esb.users.data.classes.UserDTO;
import bbr.esb.web.DialogResult;
import bbr.esb.web.resources.EJBUtils;

@SuppressWarnings("serial")
public class UserCompanyWindow extends Window {

	private UserManagerLocal usermanagerlocal = null;

	protected Grid companiesTable;

	private UserDTO user;

	private Button btnAddCompany;

	private Button btnDeleteCompany;

	private Button btnCancel;

	protected CompanyDTO selectedCompany;

	private DialogResult dialogResult = DialogResult.NONE;

	public UserCompanyWindow(String caption, UserDTO user) {
		super(caption);

		usermanagerlocal = EJBUtils.getInstance().getUserManagerLocalEJB();

		this.user = user;
		this.setModal(true);
		this.setWidth(20, Unit.CM);
		this.setHeight(15, Unit.CM);
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

		btnAddCompany = new Button("Asociar Empresa");
		btnAddCompany.addStyleName("commandButton");
		btnAddCompany.addClickListener(e -> doAddCompany());

		btnDeleteCompany = new Button("Desasociar Empresa");
		btnDeleteCompany.addStyleName("commandButton");
		btnDeleteCompany.addClickListener(e -> doDeleteCompany());

		btnCancel = new Button("Cancelar");
		btnCancel.addStyleName("commandButton");
		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				dialogResult = DialogResult.CANCEL;
				UserCompanyWindow.this.close();
			}
		});

		commandLayout.addComponent(btnAddCompany);
		commandLayout.addComponent(btnDeleteCompany);
		commandLayout.addComponent(btnCancel);

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
		companiesTable.setColumnOrder("rut", "name", "as2id");
		companiesTable.setSortOrder(Arrays.asList(new SortOrder("name", SortDirection.ASCENDING)));
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
			CompaniesListWindow window = new CompaniesListWindow("Agregar Empresas a Usuario " + user.getName() + " " + user.getLastname(), user);
			window.center();
			UserCompanyWindow.this.getUI().addWindow(window);
			window.addCloseListener(new CloseListener() {

				@Override
				public void windowClose(CloseEvent e) {
					if (window.getDialogResult().equals(DialogResult.OK)) {
						// Obtener las empresas y asociarlas al usuario en la BD
						long[] companiesids = window.getCompaniesIds();
						for (int i = 0; i < companiesids.length; i++) {
							long companykey = companiesids[i];
							try {
								UserCompanyDTO dto = new UserCompanyDTO();
								dto.setCompanykey(companykey);
								dto.setUserkey(user.getId());
								dto.setActive(true);
								usermanagerlocal.addUserCompany(dto);
							} catch (AccessDeniedException | OperationFailedException | NotFoundException e1) {
								e1.printStackTrace();
							}
							doShowData();
						}
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doDeleteCompany() {
		// try {
		// if (selectedAccess == null)
		// return;
		//
		// EditAccessWindow window = new EditAccessWindow("Editar Acceso", user.getId(), selectedAccess);
		// window.center();
		// UserCompanyWindow.this.getUI().addWindow(window);
		// window.addCloseListener(new CloseListener() {
		//
		// @Override
		// public void windowClose(CloseEvent e) {
		// if (window.getDialogResult().equals(DialogResult.OK)) {
		// // Obtener el dato y actualizarlo en la BD
		// AccessDataDTO editedAccess = window.getAccess();
		// try {
		// AccessDTO newAccess = new AccessDTO();
		// newAccess.setCode(editedAccess.getCode());
		// newAccess.setName(editedAccess.getName());
		// newAccess.setCompanykey(editedAccess.getCompanykey());
		// newAccess.setSitekey(editedAccess.getSitekey());
		// usermanagerlocal.updateAccess(newAccess);
		// doShowData();
		// selectedAccess = null;
		// } catch (AccessDeniedException | OperationFailedException | NotFoundException e1) {
		// e1.printStackTrace();
		// }
		// }
		// }
		// });
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public UserDTO getUser() {
		return user;
	}

	public DialogResult getDialogResult() {
		return this.dialogResult;
	}

	private void doShowData() {
		// Listar en la grilla
		try {

			// Obtener todos los servicios de los sitios
			CompanyDTO[] companiesArr = usermanagerlocal.getCompaniesofUser(this.user.getId());
			// CompanyDTO[] companiesArr = usermanagerlocal.getCompanies();
			List<CompanyDTO> companiesList = Arrays.asList(companiesArr);

			BeanItemContainer<CompanyDTO> container = new BeanItemContainer<>(CompanyDTO.class, companiesList);
			companiesTable.setContainerDataSource(container);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
