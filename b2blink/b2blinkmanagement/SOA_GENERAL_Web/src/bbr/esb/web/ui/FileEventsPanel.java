package bbr.esb.web.ui;

import java.util.Arrays;
import java.util.List;

import org.vaadin.peter.contextmenu.ContextMenu;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.events.data.classes.FileEventDataDTO;
import bbr.esb.services.data.classes.ServiceDTO;
import bbr.esb.services.data.classes.SiteDTO;
import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.web.resources.EJBUtils;
import bbr.esb.web.resources.ResourceUtil;

@SuppressWarnings("serial")
public class FileEventsPanel extends Panel {

	protected UI owner;

	private ServiceManagerLocal servicemanagerlocal = null;

	protected Grid fileEventsTable;

	protected FileEventDataDTO selectedFileEvent;

	protected ContextMenu menu;

	protected Boolean dirty = true;

	protected ComboBox edSites;

	protected ComboBox edServices;

	protected ComboBox edCompanies;

	protected SiteDTO selectedSite;

	protected ServiceDTO selectedService;

	protected CompanyDTO selectedCompany;

	protected int currentPageNumber = 1;

	protected int pagesCount = 0;

	private int rows = 20;

	protected Label pageInfo;

	public FileEventsPanel(UI owner) {
		this.owner = owner;

		servicemanagerlocal = EJBUtils.getInstance().getServiceManagerLocalEJB();

		// final VerticalSplitPanel layout = new VerticalSplitPanel();
		final VerticalLayout layout = new VerticalLayout();

		// Panel Superior : Botones
		Component commandPanel = createCommandPanel();

		// Panel Principal : Grid
		Component gridPanel = createGridPanel();

		// Panel de botones de paginación
		Component footerPanel = createFooterPanel();

		layout.addComponent(commandPanel);
		// layout.setExpandRatio(commandPanel, 7);

		layout.addComponent(gridPanel);
		layout.setExpandRatio(gridPanel, 87);

		layout.addComponent(footerPanel);
		layout.setExpandRatio(footerPanel, 6);

		layout.setSizeFull();
		this.setSizeFull();
		this.setContent(layout);

	}

	protected Component createCommandPanel() {

		Panel panel = new Panel();

		final HorizontalLayout commandLayout = new HorizontalLayout();
		commandLayout.setSpacing(true);
		commandLayout.setMargin(true);
		commandLayout.setSizeFull();

		// Filtros
		edSites = new ComboBox("Sitio");
		edSites.setPageLength(0);
		edSites.setTextInputAllowed(false);
		edSites.setNullSelectionAllowed(false);
		edSites.setWidth(100, Unit.PERCENTAGE);
		edSites.setStyleName("site-filter");
		edSites.setItemCaptionPropertyId("name");
		edSites.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					selectedSite = (SiteDTO) event.getProperty().getValue();
				}
			}
		});

		edServices = new ComboBox("Servicio");
		edServices.setPageLength(0);
		edServices.setTextInputAllowed(false);
		edServices.setNullSelectionAllowed(false);
		edServices.setWidth(100, Unit.PERCENTAGE);
		edServices.setStyleName("service-filter");
		edServices.setItemCaptionPropertyId("name");
		edServices.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					selectedService = (ServiceDTO) event.getProperty().getValue();
				}
			}
		});

		edCompanies = new ComboBox("Empresa");
		edCompanies.setPageLength(0);
		edCompanies.setTextInputAllowed(false);
		edCompanies.setNullSelectionAllowed(false);
		edCompanies.setWidth(100, Unit.PERCENTAGE);
		edCompanies.setStyleName("company-filter");
		edCompanies.setItemCaptionPropertyId("name");
		edCompanies.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					selectedCompany = (CompanyDTO) event.getProperty().getValue();
				}
			}
		});

		try {
			SiteDTO[] sites = servicemanagerlocal.getSites();
			List<SiteDTO> sitesList = Arrays.asList(sites);
			BeanItemContainer<SiteDTO> sitesContainer = new BeanItemContainer<>(SiteDTO.class, sitesList);
			edSites.setContainerDataSource(sitesContainer);

			ServiceDTO[] services = servicemanagerlocal.getServices();
			List<ServiceDTO> servicesList = Arrays.asList(services);
			BeanItemContainer<ServiceDTO> servicesContainer = new BeanItemContainer<>(ServiceDTO.class, servicesList);
			edServices.setContainerDataSource(servicesContainer);

			CompanyDTO[] companies = servicemanagerlocal.getCompanies();
			List<CompanyDTO> companiesList = Arrays.asList(companies);
			BeanItemContainer<CompanyDTO> companiesContainer = new BeanItemContainer<>(CompanyDTO.class, companiesList);
			edCompanies.setContainerDataSource(companiesContainer);

		} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {
			e.printStackTrace();
		}

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
				currentPageNumber = 1;
				pagesCount = 0;
				doShowData();
				showPageInfo();
			}
		});

		commandLayout.addComponent(edSites);
		commandLayout.setComponentAlignment(edSites, Alignment.MIDDLE_RIGHT);

		commandLayout.addComponent(edServices);
		commandLayout.setComponentAlignment(edServices, Alignment.MIDDLE_RIGHT);

		commandLayout.addComponent(edCompanies);
		commandLayout.setComponentAlignment(edCompanies, Alignment.MIDDLE_RIGHT);

		commandLayout.addComponent(btnRefresh);
		commandLayout.setComponentAlignment(btnRefresh, Alignment.MIDDLE_RIGHT);

		commandLayout.setId("contentlayout");
		commandLayout.setMargin(true);
		panel.setContent(commandLayout);
		// panel.setSizeFull();
		panel.setId("panellayout");

		return panel;
	}

	private Component createGridPanel() {
		Panel panel = new Panel();
		HorizontalLayout layout = new HorizontalLayout();
		BooleanConverter booleanConverter = new BooleanConverter();
		DateConverter dateConverter = new DateConverter();

		fileEventsTable = new Grid("");

		fileEventsTable.setContainerDataSource(new BeanItemContainer<>(FileEventDataDTO.class));
		fileEventsTable.removeColumn("companykey");
		fileEventsTable.removeColumn("id");
		fileEventsTable.removeColumn("servicekey");
		fileEventsTable.removeColumn("sitekey");
		fileEventsTable.removeColumn("accesscode");
		fileEventsTable.setColumnOrder("sitename", "servicename", "filename", "documentid", "datecreated", "received", "datereceived", "ok");
		fileEventsTable.getColumn("sitename").setHeaderCaption("Sitio").setWidth(200);
		fileEventsTable.getColumn("servicename").setHeaderCaption("Servicio");
		fileEventsTable.getColumn("received").setHeaderCaption("Recibido").setWidth(100).setRenderer(new HtmlRenderer(), booleanConverter);
		fileEventsTable.getColumn("ok").setHeaderCaption("OK").setWidth(100).setRenderer(new HtmlRenderer(), booleanConverter);
		fileEventsTable.getColumn("datecreated").setHeaderCaption("Fecha Creación").setWidth(200).setRenderer(new HtmlRenderer(), dateConverter);
		fileEventsTable.getColumn("datereceived").setHeaderCaption("Fecha Recepción").setWidth(200).setRenderer(new HtmlRenderer(), dateConverter);
		fileEventsTable.getColumn("documentid").setHeaderCaption("ID Documento");
		fileEventsTable.getColumn("filename").setHeaderCaption("Nombre Archivo").setWidth(450);
		fileEventsTable.setSizeFull();

		fileEventsTable.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				// El ID del item corresponde a la llave del perfil
				if (event.getItemId() != null) {
					FileEventsPanel.this.selectedFileEvent = (FileEventDataDTO) event.getItemId();
				}
			}
		});

		layout.addComponent(fileEventsTable);
		layout.setSizeFull();
		panel.setContent(layout);
		panel.setSizeFull();
		return panel;
	}

	protected Component createFooterPanel() {

		Panel panel = new Panel();

		final HorizontalLayout commandLayout = new HorizontalLayout();
		commandLayout.setSpacing(true);
		commandLayout.setMargin(true);
		commandLayout.setSizeFull();

		Button btnFirstPage = new Button();
		btnFirstPage.addStyleName("commandButton");
		// Resource icon = ResourceUtil.getInstance().getResourceFromThemeResource("icon_refresh");
		// btnLeft.setIcon(icon, "Refrescar");
		btnFirstPage.setCaption("<<");
		btnFirstPage.setDescription("Primera Página");
		btnFirstPage.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (currentPageNumber > 1) {
					currentPageNumber = 1;
					doShowData();
					showPageInfo();
				}
			}
		});

		Button btnPreviousPage = new Button();
		btnPreviousPage.addStyleName("commandButton");
		// Resource icon = ResourceUtil.getInstance().getResourceFromThemeResource("icon_refresh");
		// btnLeft.setIcon(icon, "Refrescar");
		btnPreviousPage.setCaption("<");
		btnPreviousPage.setDescription("Página anterior");
		btnPreviousPage.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (currentPageNumber > 1) {
					currentPageNumber--;
					doShowData();
					showPageInfo();
				}
			}
		});

		pageInfo = new Label("");
		pageInfo.setStyleName("commandButton");

		Button btnNextPage = new Button();
		btnNextPage.addStyleName("commandButton");
		// Resource icon = ResourceUtil.getInstance().getResourceFromThemeResource("icon_refresh");
		// btnLeft.setIcon(icon, "Refrescar");
		btnNextPage.setCaption(">");
		btnNextPage.setDescription("Página siguiente");
		btnNextPage.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (currentPageNumber < pagesCount) {
					currentPageNumber++;
					doShowData();
					showPageInfo();
				}
			}
		});

		Button btnLastPage = new Button();
		btnLastPage.addStyleName("commandButton");
		// Resource icon = ResourceUtil.getInstance().getResourceFromThemeResource("icon_refresh");
		// btnLeft.setIcon(icon, "Refrescar");
		btnLastPage.setCaption(">>");
		btnLastPage.setDescription("Última Página");
		btnLastPage.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (currentPageNumber < pagesCount) {
					currentPageNumber = pagesCount;
					doShowData();
					showPageInfo();
				}
			}
		});

		commandLayout.addComponent(btnFirstPage);
		commandLayout.setExpandRatio(btnFirstPage, 0);

		commandLayout.addComponent(btnPreviousPage);
		commandLayout.setExpandRatio(btnPreviousPage, 0);
		// commandLayout.setComponentAlignment(btnPreviousPage, Alignment.MIDDLE_RIGHT);

		commandLayout.addComponent(pageInfo);
		commandLayout.setExpandRatio(pageInfo, 1);
		commandLayout.setComponentAlignment(pageInfo, Alignment.MIDDLE_RIGHT);

		commandLayout.addComponent(btnNextPage);
		commandLayout.setExpandRatio(btnNextPage, 0);
		// commandLayout.setComponentAlignment(btnNextPage, Alignment.MIDDLE_RIGHT);

		commandLayout.addComponent(btnLastPage);
		commandLayout.setExpandRatio(btnLastPage, 0);

		panel.setContent(commandLayout);
		panel.setSizeUndefined();
		return panel;
	}

	private void showPageInfo() {
		int page = pagesCount > 0 ? currentPageNumber : 0;
		String info = "Página " + page + " de " + pagesCount;
		pageInfo.setCaption(info);
	}

	private void doShowData() {

		if (selectedSite != null && selectedService != null && selectedCompany != null) {
			try {
				int count = servicemanagerlocal.countFileEventsByContractedService(selectedSite.getId(), selectedService.getId(), selectedCompany.getId());
				this.pagesCount = (int) Math.ceil((double) count / rows);

				FileEventDataDTO[] fileEventsArr = servicemanagerlocal.getFileEventsByContractedService(selectedSite.getId(), selectedService.getId(), selectedCompany.getId(), true, currentPageNumber, rows);
				if (fileEventsArr == null || fileEventsArr.length == 0) {
					Notification.show("No existen eventos asociados a este servicio", Type.ERROR_MESSAGE);
				}
				List<FileEventDataDTO> fileEventsList = Arrays.asList(fileEventsArr);

				BeanItemContainer<FileEventDataDTO> container = new BeanItemContainer<>(FileEventDataDTO.class, fileEventsList);
				fileEventsTable.setContainerDataSource(container);

			} catch (AccessDeniedException | OperationFailedException | NotFoundException exc) {
				Notification.show("Error : " + exc.getMessage(), Type.ERROR_MESSAGE);
			}
		} else {
			Notification.show("Debe seleccionar todos los parámetros del filtro", Type.ERROR_MESSAGE);
		}
	}

	public void showData() {
		if (this.dirty) {
			doShowData();
			this.dirty = false;
		}
	}

}
