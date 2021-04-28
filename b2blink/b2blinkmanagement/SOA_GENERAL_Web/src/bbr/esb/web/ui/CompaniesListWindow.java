package bbr.esb.web.ui;

import java.util.Arrays;
import java.util.Collection;
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
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

import bbr.esb.services.managers.UserManagerLocal;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.users.data.classes.MessageFolderDTO;
import bbr.esb.users.data.classes.UserDTO;
import bbr.esb.web.DialogResult;
import bbr.esb.web.resources.EJBUtils;

@SuppressWarnings("serial")
public class CompaniesListWindow extends Window {

	private UserManagerLocal usermanagerlocal = null;

	protected Grid companiesTable;

	private UserDTO user;

	private long[] companiesIds;

	private Button btnOK;

	private Button btnCancel;

	protected MessageFolderDTO selectedFolder;

	private DialogResult dialogResult = DialogResult.NONE;

	public CompaniesListWindow(String caption, UserDTO user) {
		super(caption);

		usermanagerlocal = EJBUtils.getInstance().getUserManagerLocalEJB();

		this.user = user;
		this.setModal(true);
		this.setWidth(20, Unit.CM);
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

		btnOK = new Button("OK");
		btnOK.addStyleName("commandButton");
		btnOK.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// Obtener las filas seleccionadas
				Collection collection = companiesTable.getSelectedRows();
				Collection<CompanyDTO> companiesCol = (Collection<CompanyDTO>) collection;
				long[] ids = companiesCol.stream().mapToLong(c -> c.getId()).toArray();
				CompaniesListWindow.this.companiesIds = ids;

				dialogResult = DialogResult.OK;
				CompaniesListWindow.this.close();
			}
		});

		btnCancel = new Button("Cancelar");
		btnCancel.addStyleName("commandButton");
		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				dialogResult = DialogResult.CANCEL;
				CompaniesListWindow.this.close();
			}
		});

		commandLayout.addComponent(btnOK);
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
		companiesTable.setSelectionMode(SelectionMode.MULTI);
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
					// CompanyDTO company = (CompanyDTO) event.getItemId();
					// selectedCompany = company;
				}
			}
		});

		layout.addComponent(companiesTable);
		layout.setSizeFull();
		panel.setContent(layout);
		panel.setSizeFull();
		return panel;
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

	public long[] getCompaniesIds() {
		return companiesIds;
	}

	public DialogResult getDialogResult() {
		return this.dialogResult;
	}

}
