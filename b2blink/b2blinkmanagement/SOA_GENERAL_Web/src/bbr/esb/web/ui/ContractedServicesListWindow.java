package bbr.esb.web.ui;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.HtmlRenderer;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.data.classes.ContractedServiceDTO;
import bbr.esb.services.data.classes.ContractedServiceDataDTO;
import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.web.DialogResult;
import bbr.esb.web.resources.EJBUtils;

@SuppressWarnings("serial")
public class ContractedServicesListWindow extends Window {

	private ServiceManagerLocal servicemanagerlocal = null;

	protected Grid servicesTable;

	private CompanyDTO company;

	private Button btnContractService;

	private Button btnEditService;

	private Button btnSwitchServiceActivation;

	private Button btnCancel;

	protected ContractedServiceDataDTO selectedService;

	private DialogResult dialogResult = DialogResult.NONE;

	public ContractedServicesListWindow(String caption, CompanyDTO company) {
		super(caption);

		servicemanagerlocal = EJBUtils.getInstance().getServiceManagerLocalEJB();

		this.company = company;
		this.setModal(true);
		this.setWidth(35, Unit.CM);
		this.setHeight(12, Unit.CM);
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

		btnContractService = new Button("Contratar Servicio");
		btnContractService.addStyleName("commandButton");
		btnContractService.addClickListener(e -> doContractService());

		btnEditService = new Button("Editar Servicio");
		btnEditService.addStyleName("commandButton");
		btnEditService.addClickListener(e -> doEditContractedService());

		btnSwitchServiceActivation = new Button("Activar / Desactivar Servicio");
		btnSwitchServiceActivation.addStyleName("commandButton");
		btnSwitchServiceActivation.addClickListener(e -> doSwitchServiceActivation());

		btnCancel = new Button("Cancelar");
		btnCancel.addStyleName("commandButton");
		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				dialogResult = DialogResult.CANCEL;
				ContractedServicesListWindow.this.close();
			}
		});

		commandLayout.addComponent(btnContractService);
		commandLayout.addComponent(btnEditService);
		commandLayout.addComponent(btnSwitchServiceActivation);
		commandLayout.addComponent(btnCancel);

		panel.setContent(commandLayout);
		panel.setSizeFull();
		return panel;
	}

	private Component createGridPanel() {
		Panel panel = new Panel();
		HorizontalLayout layout = new HorizontalLayout();
		BooleanConverter booleanConverter = new BooleanConverter();
		DateConverter dateConverter = new DateConverter();

		servicesTable = new Grid("");

		servicesTable.setContainerDataSource(new BeanItemContainer<>(ContractedServiceDataDTO.class));
		servicesTable.removeColumn("sitekey");
		servicesTable.removeColumn("servicekey");
		servicesTable.removeColumn("companykey");
		servicesTable.removeColumn("companyname");
		servicesTable.removeColumn("folderkey");
		servicesTable.removeColumn("formatkey");
		servicesTable.removeColumn("encrypt");
		servicesTable.removeColumn("encpwd");
		servicesTable.removeColumn("lastmonitored");
		servicesTable.removeColumn("pendingmessages");
		servicesTable.removeColumn("servicecode");
		servicesTable.removeColumn("sitecode");
		servicesTable.removeColumn("wsendpoint");
		servicesTable.removeColumn("wsendpointkey");
		servicesTable.getColumn("compresseddocument").setHeaderCaption("comprimido");
		servicesTable.getColumn("sitename").setHeaderCaption("Sitio");
		servicesTable.getColumn("sitename").setMaximumWidth(160.0);
		servicesTable.getColumn("servicename").setHeaderCaption("Servicio");
		servicesTable.getColumn("activation").setHeaderCaption("Fecha Activación");
		servicesTable.getColumn("foldername").setHeaderCaption("Casilla");
		servicesTable.getColumn("formatname").setHeaderCaption("Formato");
		servicesTable.getColumn("active").setHeaderCaption("Activo");
		servicesTable.getColumn("monitor").setHeaderCaption("Monitorear");
		servicesTable.getColumn("lastsentmessage").setHeaderCaption("Último Doc.");
		servicesTable.setColumnOrder("sitename", "servicename", "foldername", "formatname", "active", "activation", "monitor", "lastsentmessage","compresseddocument" );
		servicesTable.getColumn("active").setRenderer(new HtmlRenderer(), booleanConverter);
		servicesTable.getColumn("monitor").setRenderer(new HtmlRenderer(), booleanConverter);
		servicesTable.getColumn("compresseddocument").setRenderer(new HtmlRenderer(), booleanConverter);
		servicesTable.getColumn("activation").setRenderer(new HtmlRenderer(), dateConverter);
		servicesTable.getColumn("lastsentmessage").setRenderer(new HtmlRenderer(), dateConverter);

		servicesTable.setSizeFull();

		servicesTable.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				// El ID del item corresponde a la llave de la empresa
				if (event.getItemId() != null) {
					ContractedServiceDataDTO service = (ContractedServiceDataDTO) event.getItemId();
					selectedService = service;
				}
			}
		});

		layout.addComponent(servicesTable);
		layout.setSizeFull();
		panel.setContent(layout);
		panel.setSizeFull();
		return panel;
	}

	private void doContractService() {
		try {

			ContractServiceWindow window = new ContractServiceWindow("Contratar Servicio - Empresa " + company.getName(), company, null);
			window.center();
			ContractedServicesListWindow.this.getUI().addWindow(window);
			window.addCloseListener(new CloseListener() {

				@Override
				public void windowClose(CloseEvent e) {
					if (window.getDialogResult().equals(DialogResult.OK)) {
						// Obtener el dato y actualizarlo en la BD
						ContractedServiceDTO editedService = window.getContractedService();

						try {
							servicemanagerlocal.doContractSiteService(editedService.getSitekey(), editedService.getServicekey(), editedService.getCompanykey(), editedService.getFolderkey(), editedService.getFormatkey(), editedService.getCompresseddocument() );
							doShowData();
							selectedService = null;
							Notification.show("El servicio fue contratado exitosamente");
						} catch (AccessDeniedException | OperationFailedException | NotFoundException exc) {
							Notification.show("Error : " + exc.getMessage(), Type.ERROR_MESSAGE);
							exc.printStackTrace();
						}
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doEditContractedService() {
		try {

			if (selectedService == null)
				return;

			ContractedServiceDTO selectedSvcDTO = new ContractedServiceDTO();
			selectedSvcDTO.setSitekey(selectedService.getSitekey());
			selectedSvcDTO.setServicekey(selectedService.getServicekey());
			selectedSvcDTO.setCompanykey(selectedService.getCompanykey());
			selectedSvcDTO.setFolderkey(selectedService.getFolderkey());
			selectedSvcDTO.setFormatkey(selectedService.getFormatkey());
			selectedSvcDTO.setCompresseddocument(selectedService.getCompresseddocument());
			ContractServiceWindow window = new ContractServiceWindow("Editar Servicio - Empresa " + company.getName(), company, selectedSvcDTO);
			window.center();
			ContractedServicesListWindow.this.getUI().addWindow(window);
			window.addCloseListener(new CloseListener() {

				@Override
				public void windowClose(CloseEvent e) {
					if (window.getDialogResult().equals(DialogResult.OK)) {
						// Obtener el dato y actualizarlo en la BD
						ContractedServiceDTO editedService = window.getContractedService();

						try {
							servicemanagerlocal.doUpdateContractedService(editedService.getSitekey(), editedService.getServicekey(), editedService.getCompanykey(), editedService.getFolderkey(), editedService.getFormatkey(), editedService.getMonitor(), editedService.getCompresseddocument());
							doShowData();
							selectedService = null;
							Notification.show("El servicio fue editado exitosamente");
						} catch (AccessDeniedException | OperationFailedException | NotFoundException exc) {
							Notification.show("Error : " + exc.getMessage(), Type.ERROR_MESSAGE);
							exc.printStackTrace();
						}
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doSwitchServiceActivation() {
		try {
			if (selectedService == null)
				return;
			String message = selectedService.getActive() ? "¿Desactivar Servicio?" : "¿Activar Servicio?";
			ConfirmDialog.show(this.getUI(), "Favor Confirmar:", message, "OK", "Cancel", new ConfirmDialog.Listener() {
				public void onClose(ConfirmDialog dialog) {
					if (dialog.isConfirmed()) {

						try {
							servicemanagerlocal.switchSiteService(selectedService.getSitekey(), selectedService.getServicekey(), selectedService.getCompanykey(), !selectedService.getActive());
							Notification.show("El servicio fue actualizado exitosamente", Notification.Type.ERROR_MESSAGE);
							doShowData();
							selectedService = null;
						} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {
							Notification.show("Ocurrió un error al actualizar el servicio: \n\n" + e.getMessage(), Notification.Type.ERROR_MESSAGE);
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
			ContractedServiceDataDTO[] servicesArr = servicemanagerlocal.getContractedServicesofCompany(company.getId());
			List<ContractedServiceDataDTO> servicesList = Arrays.asList(servicesArr);

			BeanItemContainer<ContractedServiceDataDTO> container = new BeanItemContainer<>(ContractedServiceDataDTO.class, servicesList);
			servicesTable.setContainerDataSource(container);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
