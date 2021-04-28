package bbr.esb.web.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.vaadin.peter.contextmenu.ContextMenu;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ConnectorResource;
import com.vaadin.server.DownloadStream;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.renderers.HtmlRenderer;

import bbr.esb.services.data.classes.ContractedServiceDataDTO;
import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.web.DialogResult;
import bbr.esb.web.resources.EJBUtils;
import bbr.esb.web.resources.ResourceUtil;

@SuppressWarnings("serial")
public class ContractedServicesPanel extends Panel {

	protected UI owner;

	private ServiceManagerLocal servicemanagerlocal = null;

	// private UserManagerLocal usermanagerlocal = null;

	protected Grid contractedServicesTable;

	protected ContractedServiceDataDTO selectedService;

	protected ContextMenu menu;

	protected Boolean dirty = true;

	public ContractedServicesPanel(UI owner) {
		this.owner = owner;

		servicemanagerlocal = EJBUtils.getInstance().getServiceManagerLocalEJB();

		final VerticalSplitPanel layout = new VerticalSplitPanel();

		// Panel Superior : Botones
		Component commandPanel = createCommandPanel();

		// Panel Principal : Grid
		Component gridPanel = createGridPanel();
		layout.setFirstComponent(commandPanel);

		layout.setSecondComponent(gridPanel);
		layout.setSplitPosition(8, Unit.PERCENTAGE);
		layout.setLocked(true);

		this.setSizeFull();
		this.setContent(layout);

	}

	protected Component createCommandPanel() {

		Panel panel = new Panel();

		final HorizontalLayout commandLayout = new HorizontalLayout();
		commandLayout.setSpacing(true);
		commandLayout.setMargin(true);
		commandLayout.setSizeFull();
		// commandLayout.setSizeUndefined();

		Button btnResend = new Button();
		btnResend.setCaption("Reenvios de OC");
		btnResend.setDescription("Reenviar OC's");
		btnResend.addStyleName("commandButton");
		btnResend.addClickListener(e -> doManageContractedServices());
		
		Button btnDownloadReport = new Button();
		btnDownloadReport.setSizeUndefined();
		btnDownloadReport.addStyleName("btn-download-report");

		Resource icon = ResourceUtil.getInstance().getResourceFromThemeResource("icon_download_excel");
		btnDownloadReport.setIcon(icon, "Descargar Reporte");
		btnDownloadReport.setDescription("Descargar Reporte");

		try {
			FileDownloader fd = new FileDownloader(new ConnectorResource() {

				File file = null;

				@Override
				public String getFilename() {
					return file != null ? file.getName() : null;
				}

				@Override
				public String getMIMEType() {
					return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
				}

				@Override
				public DownloadStream getStream() {
					try {
						// Generar el archivo
						this.file = servicemanagerlocal.getAllContractedServicesXLS();

						// Retornar un stream de lectura del archivo recién creado
						InputStream data = new FileInputStream(this.file);
						final DownloadStream stream = new DownloadStream(data, getMIMEType(), this.file.getName());
						stream.setParameter("Content-Disposition", "attachment;filename=" + this.file.getName());
						// This magic incantation should prevent anyone from caching the data
						stream.setParameter("Cache-Control", "private,no-cache,no-store");
						// In theory <=0 disables caching. In practice Chrome, Safari (and, apparently, IE) all ignore
						// <=0.
						// Set to 1s
						stream.setCacheTime(1000);
						return stream;
					} catch (final Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			});

			fd.extend(btnDownloadReport);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Button btnRefresh = new Button();
		btnRefresh.setSizeUndefined();
		icon = ResourceUtil.getInstance().getResourceFromThemeResource("icon_refresh");
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
		
		commandLayout.addComponent(btnResend);
		commandLayout.addComponent(btnDownloadReport);
		commandLayout.addComponent(btnRefresh);
		
		commandLayout.setComponentAlignment(btnResend, Alignment.TOP_RIGHT);
		commandLayout.setComponentAlignment(btnDownloadReport, Alignment.TOP_RIGHT);
		commandLayout.setComponentAlignment(btnRefresh, Alignment.TOP_RIGHT);

		panel.setContent(commandLayout);
		panel.setSizeFull();
		return panel;
	}

	private Component createGridPanel() {
		Panel panel = new Panel();
		HorizontalLayout layout = new HorizontalLayout();
		BooleanConverter booleanConverter = new BooleanConverter();
		DateConverter dateConverter = new DateConverter();

		contractedServicesTable = new Grid("");
		contractedServicesTable.setContainerDataSource(new BeanItemContainer<>(ContractedServiceDataDTO.class));
		contractedServicesTable.removeColumn("companykey");
		contractedServicesTable.removeColumn("encpwd");
		contractedServicesTable.removeColumn("encrypt");
		contractedServicesTable.removeColumn("folderkey");
		contractedServicesTable.removeColumn("formatkey");
		contractedServicesTable.removeColumn("servicekey");
		contractedServicesTable.removeColumn("sitekey");
		
		contractedServicesTable.removeColumn("compresseddocument");
		contractedServicesTable.removeColumn("servicecode");
		contractedServicesTable.removeColumn("sitecode");
		contractedServicesTable.removeColumn("wsendpoint");
		contractedServicesTable.removeColumn("wsendpointkey");
		
		contractedServicesTable.setColumnOrder("sitename", "servicename", "companyname", "active", "activation", "monitor", "formatname", "foldername", "lastsentmessage", "lastmonitored", "pendingmessages");
		contractedServicesTable.getColumn("sitename").setHeaderCaption("Sitio");
		contractedServicesTable.getColumn("servicename").setHeaderCaption("Servicio");
		contractedServicesTable.getColumn("companyname").setHeaderCaption("Empresa");
		contractedServicesTable.getColumn("active").setHeaderCaption("Activo");
		contractedServicesTable.getColumn("activation").setHeaderCaption("Fecha Activación");
		contractedServicesTable.getColumn("monitor").setHeaderCaption("Monitorear");
		contractedServicesTable.getColumn("formatname").setHeaderCaption("Formato");
		contractedServicesTable.getColumn("foldername").setHeaderCaption("Casilla");
		contractedServicesTable.getColumn("lastsentmessage").setHeaderCaption("Fecha Último Mensaje");
		contractedServicesTable.getColumn("lastmonitored").setHeaderCaption("Fecha Último Monitoreo");
		contractedServicesTable.getColumn("pendingmessages").setHeaderCaption("Mensajes Pendientes");
		contractedServicesTable.getColumn("active").setRenderer(new HtmlRenderer(), booleanConverter);
		contractedServicesTable.getColumn("monitor").setRenderer(new HtmlRenderer(), booleanConverter);
		contractedServicesTable.getColumn("activation").setRenderer(new HtmlRenderer(), dateConverter);
		contractedServicesTable.getColumn("lastsentmessage").setRenderer(new HtmlRenderer(), dateConverter);
		contractedServicesTable.getColumn("lastmonitored").setRenderer(new HtmlRenderer(), dateConverter);

		contractedServicesTable.setSizeFull();

		contractedServicesTable.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				// El ID del item corresponde a la llave del perfil
				if (event.getItemId() != null) {
					ContractedServicesPanel.this.selectedService = (ContractedServiceDataDTO) event.getItemId();
				}
			}
		});

		layout.addComponent(contractedServicesTable);
		layout.setSizeFull();
		panel.setContent(layout);
		panel.setSizeFull();
		return panel;
	}

	private void doShowData() {
		// Listar en la grilla
		try {

			// Obtener todos los servicios de los sitios
			ContractedServiceDataDTO[] servicesArr = servicemanagerlocal.getAllContractedServices();
			List<ContractedServiceDataDTO> servicesList = Arrays.asList(servicesArr);

			BeanItemContainer<ContractedServiceDataDTO> container = new BeanItemContainer<>(ContractedServiceDataDTO.class, servicesList);
			contractedServicesTable.setContainerDataSource(container);

		} catch (Exception exc) {
			Notification.show("Error : " + exc.getMessage(), Type.ERROR_MESSAGE);
		}
	}
	
	private void doRemakeFileFlow() {
		try {
			EditCompanyWindow window = new EditCompanyWindow("Agregar Empresa", null);
			window.center();
			ContractedServicesPanel.this.owner.addWindow(window);
			window.addCloseListener(new CloseListener() {

				@Override
				public void windowClose(CloseEvent e) {
					if (window.getDialogResult().equals(DialogResult.OK)) {
						// Obtener el dato y actualizarlo en la BD
						CompanyDTO editedCompany = window.getCompany();
//						try {
////							usermanagerlocal.addCompany(editedCompany);
////							doShowData();
////							selectedCompany = null;
//						} catch (AccessDeniedException | OperationFailedException | NotFoundException e1) {
//							e1.printStackTrace();
//						}
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doManageContractedServices() {
		try {
			if (selectedService == null){
				Notification.show("ATENCIÓN",
		                  "Seleccione un contrato antes de continuar",
		                  Notification.Type.HUMANIZED_MESSAGE);
			}else{
				ResendWindow window = new ResendWindow("Reenvio de Ordenes de compara - Contrato: "
						 + selectedService.getCompanyname().toUpperCase()+"  -- "+selectedService.getSitename().toUpperCase(), selectedService );
				window.center();
				ContractedServicesPanel.this.owner.addWindow(window);
			}
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
