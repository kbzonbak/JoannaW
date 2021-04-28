package bbr.esb.web.ui;

import java.util.Arrays;
import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.renderers.HtmlRenderer;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.data.classes.SiteServiceReportDTO;
import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.web.resources.EJBUtils;
import bbr.esb.web.resources.ResourceUtil;

@SuppressWarnings("serial")
public class ServicesPanel extends Panel {

	protected UI owner;

	private ServiceManagerLocal servicemanagerlocal = null;

	protected Grid servicesTable;

	protected SiteServiceReportDTO selectedService;

	protected ContextMenu menu;

	protected Boolean dirty = true;

	public ServicesPanel(UI owner) {
		this.owner = owner;

		servicemanagerlocal = EJBUtils.getInstance().getServiceManagerLocalEJB();

		final VerticalSplitPanel layout = new VerticalSplitPanel();

		// Panel Superior : Botones
		Component commandPanel = createCommandPanel();

		// Panel Principal : Grid
		Component gridPanel = createGridPanel();

		layout.setFirstComponent(commandPanel);		
		layout.setSecondComponent(gridPanel);
		layout.setSplitPosition(10, Unit.PERCENTAGE);
		layout.setLocked(true);

		layout.setSizeFull();
		this.setSizeFull();		
		this.setContent(layout);

	}

	protected Component createCommandPanel() {

		Panel panel = new Panel();

		final HorizontalLayout commandLayout = new HorizontalLayout();
		commandLayout.setStyleName("command-layout");
		commandLayout.setSizeFull();
		commandLayout.setSpacing(true);
		commandLayout.setMargin(true);
		Button btnActivateInactivateSiteService = new Button("Activar/Desactivar Servicio");
		btnActivateInactivateSiteService.addStyleName("commandButton");
		btnActivateInactivateSiteService.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// Activar/Desactivar servicio
				if (selectedService == null) {
					Notification.show("Debe seleccionar un servicio", Type.ERROR_MESSAGE);
					return;
				}
				if (selectedService.getActive()) {
					// Desactivar
					ConfirmDialog.show(ServicesPanel.this.getUI(), "Favor Confirmar:", "¿Desactivar Servicio?", "OK", "Cancel", new ConfirmDialog.Listener() {
						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								try {
									servicemanagerlocal.doActivateInactivateSiteService(selectedService.getSitekey(), selectedService.getServicekey(), false);
									Notification.show("El servicio '" + selectedService.getServicename() + "' en '" + selectedService.getSitename() + "' fue DESACTIVADO exitosamente", Type.ERROR_MESSAGE);
									doShowData();
								} catch (AccessDeniedException | OperationFailedException | NotFoundException exc) {
									Notification.show("Error : " + exc.getMessage(), Type.ERROR_MESSAGE);
								}
							}
						}
					});

				} else {
					ConfirmDialog.show(ServicesPanel.this.getUI(), "Favor Confirmar:", "¿Activar Servicio?", "OK", "Cancel", new ConfirmDialog.Listener() {
						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								try {
									servicemanagerlocal.doActivateInactivateSiteService(selectedService.getSitekey(), selectedService.getServicekey(), true);
									Notification.show("El servicio '" + selectedService.getServicename() + "' en '" + selectedService.getSitename() + "' fue ACTIVADO exitosamente", Type.ERROR_MESSAGE);
									doShowData();
								} catch (AccessDeniedException | OperationFailedException | NotFoundException exc) {
									Notification.show("Error : " + exc.getMessage(), Type.ERROR_MESSAGE);
								}
							}
						}
					});
				}
			}
		});
		commandLayout.addComponent(btnActivateInactivateSiteService);
		commandLayout.setComponentAlignment(btnActivateInactivateSiteService, Alignment.TOP_LEFT);

		Button btnRefresh = new Button();
		btnRefresh.setSizeUndefined();
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

		commandLayout.addComponent(btnRefresh);
		commandLayout.setComponentAlignment(btnRefresh, Alignment.TOP_RIGHT);

		panel.setContent(commandLayout);
		//panel.setSizeFull();		
		return panel;
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
				// El ID del item corresponde a la llave de la empresa
				if (event.getItemId() != null) {
					SiteServiceReportDTO siteservice = (SiteServiceReportDTO) event.getItemId();
					selectedService = siteservice;
				}
			}
		});

		layout.addComponent(servicesTable);
		layout.setSizeFull();
		panel.setContent(layout);
		panel.setSizeFull();
		return panel;
	}

	private void doShowData() {
		// Listar en la grilla
		try {

			// Obtener todos los servicios de los sitios
			SiteServiceReportDTO[] servicesArr = servicemanagerlocal.getSiteServiceReport();
			List<SiteServiceReportDTO> servicesList = Arrays.asList(servicesArr);

			BeanItemContainer<SiteServiceReportDTO> container = new BeanItemContainer<>(SiteServiceReportDTO.class, servicesList);
			servicesTable.setContainerDataSource(container);
			servicesTable.refreshAllRows();

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
