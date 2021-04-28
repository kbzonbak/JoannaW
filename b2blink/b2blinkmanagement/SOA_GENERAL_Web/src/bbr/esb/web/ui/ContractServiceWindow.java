package bbr.esb.web.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.HtmlRenderer;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.data.classes.ContractedServiceDTO;
import bbr.esb.services.data.classes.SiteServiceReportDTO;
import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.users.data.classes.MessageFolderDTO;
import bbr.esb.users.data.classes.MessageFormatDTO;
import bbr.esb.web.DialogResult;
import bbr.esb.web.resources.EJBUtils;

@SuppressWarnings("serial")
public class ContractServiceWindow extends Window {

	private ServiceManagerLocal servicemanagerlocal = null;

	private CompanyDTO company;

	private ContractedServiceDTO contractedService;

	private SiteServiceReportDTO selectedService;

	protected Grid servicesTable;

	private ComboBox edFolder;

	private ComboBox edFormat;

	private CheckBox edMonitor;

	private CheckBox edCompress;
	
	private Button btnSave;

	private Button btnCancel;

	private DialogResult dialogResult = DialogResult.NONE;

	public ContractServiceWindow(String caption, CompanyDTO company, ContractedServiceDTO contractedService) {
		super(caption);

		servicemanagerlocal = EJBUtils.getInstance().getServiceManagerLocalEJB();

		this.company = company;
		this.contractedService = contractedService;
		this.setModal(true);
		this.setWidth(25, Unit.CM);
		this.setHeight(15, Unit.CM);
		this.setWindowMode(WindowMode.NORMAL);

		VerticalLayout controlsLayout = new VerticalLayout();

		controlsLayout.setWidth("100%");
		// controlsLayout.setMargin(true);
		// controlsLayout.setSpacing(true);

		// Primer Panel : Grid
		Component gridPanel = createGridPanel();
		controlsLayout.addComponent(gridPanel);
		controlsLayout.setExpandRatio(gridPanel, 50f);

		// Segundo Panel : Opciones
		Component optionsPanel = createOptionsPanel();
		controlsLayout.addComponent(optionsPanel);
		controlsLayout.setExpandRatio(optionsPanel, 40f);

		// Tercer Panel : Botones
		Component commandsPanel = createCommandsPanel();
		controlsLayout.addComponent(commandsPanel);
		controlsLayout.setExpandRatio(commandsPanel, 10f);

		this.setContent(controlsLayout);

		this.addCloseListener(new CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {
				// Si se cerró usando el botón propio de la ventana, se asume CANCEL
				if (dialogResult.equals(DialogResult.NONE))
					dialogResult = DialogResult.CANCEL;
			}
		});

		doShowData();

		this.center();
	}

	private Component createGridPanel() {
		Panel panel = new Panel();

		HorizontalLayout layout = new HorizontalLayout();
		servicesTable = new Grid("");
		servicesTable.setContainerDataSource(new BeanItemContainer<>(SiteServiceReportDTO.class));
		servicesTable.removeColumn("servicekey");
		servicesTable.removeColumn("sitekey");
		servicesTable.setColumnOrder("sitename", "servicename", "active");
		servicesTable.getColumn("sitename").setHeaderCaption("Sitio");
		servicesTable.getColumn("servicename").setHeaderCaption("Servicio");
		servicesTable.getColumn("active").setHeaderCaption("Activo");
		servicesTable.getColumn("active").setRenderer(new HtmlRenderer(), new BooleanConverter());

		servicesTable.setSizeFull();

		servicesTable.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.getItemId() != null) {
					selectedService = (SiteServiceReportDTO) event.getItemId();
					// Llenar el dropdown de Formato para el servicio correspondiente
					setFormatsByService();
				}
			}
		});

		layout.addComponent(servicesTable);
		layout.setSizeFull();
		panel.setContent(layout);
		panel.setSizeFull();
		return panel;
	}

	private Component createOptionsPanel() {
		Panel panel = new Panel();

		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		Label lblFolder = new Label("Casilla : ");

		edFolder = new ComboBox();
		edFolder.setItemCaptionPropertyId("path");
		edFolder.setTextInputAllowed(false);
		edFolder.setNullSelectionAllowed(false);
		edFolder.setWidth(10F, Unit.CM);
		edFolder.setPageLength(0);

		Label lblFormat = new Label("Formato : ");

		edFormat = new ComboBox();
		edFormat.setItemCaptionPropertyId("name");
		edFormat.setTextInputAllowed(false);
		edFormat.setNullSelectionAllowed(false);
		edFormat.setWidth(5F, Unit.CM);
		edFormat.setPageLength(0);

		// edFormat.add

		Label lblMonitor = new Label("Monitorear : ");
		edMonitor = new CheckBox();
		
		Label lblCompress = new Label("Comprimido : ");
		edCompress = new CheckBox();

		layout.addComponent(lblFolder);
		layout.addComponent(edFolder);
		layout.addComponent(lblFormat);
		layout.addComponent(edFormat);
		layout.addComponent(lblMonitor);
		layout.addComponent(edMonitor);
		layout.addComponent(lblCompress);
		layout.addComponent(edCompress);

		// layout.setSizeFull();
		panel.setContent(layout);
		panel.setSizeFull();
		return panel;
	}

	private Component createCommandsPanel() {
		Panel panel = new Panel();

		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		layout.setWidthUndefined();

		btnSave = new Button("Guardar");
		btnSave.addStyleName("commandButton");
		btnSave.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// VALIDAR LOS CAMPOS
				if (edFolder.getValue() == null) {
					Notification.show("La casilla es obligatoria", Type.ERROR_MESSAGE);
					return;
				}
				if (edFormat.getValue() == null) {
					Notification.show("El formato es obligatorio", Type.ERROR_MESSAGE);
					return;
				}
				MessageFolderDTO selectedFolder = (MessageFolderDTO) edFolder.getValue();
				MessageFormatDTO selectedFormat = (MessageFormatDTO) edFormat.getValue();

				ContractedServiceDTO result = new ContractedServiceDTO();
				result.setSitekey(selectedService.getSitekey());
				result.setServicekey(selectedService.getServicekey());
				result.setCompanykey(company.getId());
				result.setFolderkey(selectedFolder.getId());
				result.setFormatkey(selectedFormat.getId());
				result.setMonitor(edMonitor.getValue());
				result.setCompresseddocument(edCompress.getValue());

				ContractServiceWindow.this.contractedService = result;

				// Si todo está OK, cerrar la ventana
				dialogResult = DialogResult.OK;
				ContractServiceWindow.this.close();
			}
		});

		layout.addComponent(btnSave);
		layout.setComponentAlignment(btnSave, Alignment.MIDDLE_CENTER);

		btnCancel = new Button("Cancelar");
		btnCancel.addStyleName("commandButton");
		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				dialogResult = DialogResult.CANCEL;
				ContractServiceWindow.this.close();
			}
		});

		layout.addComponent(btnCancel);
		layout.setComponentAlignment(btnCancel, Alignment.MIDDLE_CENTER);
		layout.setSizeFull();

		layout.setSizeFull();
		panel.setContent(layout);
		panel.setSizeFull();
		return panel;
	}

	private void setFormatsByService() {
		try {
			MessageFormatDTO[] formats = servicemanagerlocal.getMessageFormatsofService(selectedService.getServicekey());
			List<MessageFormatDTO> formatsList = Arrays.asList(formats);
			BeanItemContainer<MessageFormatDTO> container = new BeanItemContainer<>(MessageFormatDTO.class, formatsList);
			edFormat.setContainerDataSource(container);
		} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {
			e.printStackTrace();
		}
	}

	private void setFoldersByCompany() {
		try {
			MessageFolderDTO[] folders = servicemanagerlocal.getMessageFoldersofCompany(company.getId());
			List<MessageFolderDTO> foldersList = Arrays.asList(folders);
			BeanItemContainer<MessageFolderDTO> container = new BeanItemContainer<>(MessageFolderDTO.class, foldersList);
			edFolder.setContainerDataSource(container);
		} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {
			e.printStackTrace();
		}
	}

	private void doShowData() {
		// Listar en la grilla
		try {

			// Obtener todos los servicios de los sitios
			SiteServiceReportDTO[] servicesArr = servicemanagerlocal.getSiteServiceReport();
			List<SiteServiceReportDTO> servicesList = Arrays.asList(servicesArr);

			// Llenar dropdown de casillas
			setFoldersByCompany();

			if (contractedService != null) {
				// Si se está editando el servicio, no se puede cambiar el sitio ni el servicio; quitar todo lo demás de la lista
				servicesList = servicesList.stream().filter(service -> service.getSitekey().equals(contractedService.getSitekey()) && service.getServicekey().equals(contractedService.getServicekey())).collect(Collectors.toList());
				
			}
			BeanItemContainer<SiteServiceReportDTO> container = new BeanItemContainer<>(SiteServiceReportDTO.class, servicesList);
			servicesTable.setContainerDataSource(container);
			servicesTable.refreshAllRows();
			
			
			
			// Seleccionar el primer elemento de la grilla
			if (servicesList != null && !servicesList.isEmpty()) {
				selectedService = servicesList.get(0);
				edCompress.setValue(contractedService.getCompresseddocument());
				// Llenar dropdown de formatos
				setFormatsByService();				
				// Seleccionar formato y casilla según lo contratado
				if (contractedService != null) {
					BeanItemContainer<MessageFolderDTO> folderContainer = (BeanItemContainer<MessageFolderDTO>) edFolder.getContainerDataSource();
					List<MessageFolderDTO> listFolders = folderContainer.getItemIds();
					Optional<MessageFolderDTO> optFolder = listFolders.stream().filter(folder -> contractedService.getFolderkey().equals(folder.getId())).findFirst();
					if (optFolder != null && optFolder.isPresent()) {
						edFolder.select(optFolder.get());
					}

					BeanItemContainer<MessageFormatDTO> formatContainer = (BeanItemContainer<MessageFormatDTO>) edFormat.getContainerDataSource();
					List<MessageFormatDTO> listFormats = formatContainer.getItemIds();
					Optional<MessageFormatDTO> optFormat = listFormats.stream().filter(format -> contractedService.getFormatkey().equals(format.getId())).findFirst();
					if (optFormat != null && optFormat.isPresent()) {
						edFormat.select(optFormat.get());
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Grid getServicesTable() {
		return servicesTable;
	}

	public void setServicesTable(Grid servicesTable) {
		this.servicesTable = servicesTable;
	}

	public ContractedServiceDTO getContractedService() {
		return contractedService;
	}

	public CompanyDTO getCompany() {
		return company;
	}

	public DialogResult getDialogResult() {
		return this.dialogResult;
	}
}
