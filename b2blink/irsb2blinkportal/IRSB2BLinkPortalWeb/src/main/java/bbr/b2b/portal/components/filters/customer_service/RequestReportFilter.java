package bbr.b2b.portal.components.filters.customer_service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.classes.wrappers.customer.RequestClientReportFilterSectionInfo;
import bbr.b2b.portal.classes.wrappers.customer.RequestReportFilterSectionInfo;
import bbr.b2b.portal.classes.wrappers.customer.RequestReportPeriodFilterSectionInfo;
import bbr.b2b.portal.classes.wrappers.customer.RequestReportSelectedInfo;
import bbr.b2b.portal.components.filter_section.customer.RequestClientFilterSection;
import bbr.b2b.portal.components.filter_section.customer.RequestReportFilterSection;
import bbr.b2b.portal.components.filter_section.customer.RequestReportPeriodFilterSection;
import bbr.b2b.portal.components.filter_section.generic.ProviderFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.CustomerConstants;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.container.BbrSectionEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class RequestReportFilter extends BbrFilterContainer implements Button.ClickListener
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long					serialVersionUID		= 8489213839800376590L;
	private ProviderFilterSection				providerFilterSection	= null;
	private RequestClientFilterSection			clientFilterSection		= null;
	private RequestReportFilterSection			requestFilterSection	= null;
	private RequestReportPeriodFilterSection	periodFilterSection		= null;

	private String formatDate(LocalDateTime date)
	{
		return date != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date) : "";
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	public RequestReportFilter(BbrModule parentModule)
	{
		super(parentModule);
	}
	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

	@Override
	public void buttonClick(ClickEvent event)
	{
		String errorMsg = this.validateData();

		if ((errorMsg == null) || (errorMsg.length() == 0))
		{
			BbrFilterEvent bbrFilter = this.getBbrFilterEventObject();
			dispatchBbrFilterEvent(bbrFilter);
		}
		else
		{
			// Faltan datos en el filtro seleccionado
			RequestReportFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	public void initializeView()
	{
		// Seccion Proveedor
		providerFilterSection = new ProviderFilterSection(this.getBbrUIParent());
		providerFilterSection.initializeView();
		providerFilterSection.setSearchProvider(true);
		providerFilterSection.addBbrSectionListener(BbrFilterSectionConstants.FS_PROVIDER, (BbrSectionEventListener & Serializable) e -> this.companyChange_Listener(e));

		// Sección Cliente
		clientFilterSection = new RequestClientFilterSection(this.getBbrUIParent());
		clientFilterSection.setSectionData(providerFilterSection.getSectionResult());
		clientFilterSection.initializeView();

		// Seccion Solicitudes
		requestFilterSection = new RequestReportFilterSection(this.getBbrUIParent());
		requestFilterSection.setSectionData(providerFilterSection.getSectionResult());
		requestFilterSection.initializeView();

		// Seccion Filtros
		periodFilterSection = new RequestReportPeriodFilterSection(this.getBbrUIParent());
		periodFilterSection.initializeView();

		// Botón de Búsqueda
		Button btn_FilterSearch = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "generate_report"), this);
		btn_FilterSearch.setIcon(VaadinIcons.CHECK_CIRCLE_O);
		btn_FilterSearch.setStyleName("btn-filter-search");
		btn_FilterSearch.setClickShortcut(KeyCode.ENTER);

		VerticalLayout pnlSearchButton = new VerticalLayout();
		pnlSearchButton.setWidth("400px");
		pnlSearchButton.addStyleName("bbr-panel-space");
		pnlSearchButton.setSpacing(false);
		pnlSearchButton.setMargin(false);
		pnlSearchButton.addComponents(btn_FilterSearch);
		pnlSearchButton.setComponentAlignment(btn_FilterSearch, Alignment.BOTTOM_RIGHT);

		VerticalLayout pnlFill = new VerticalLayout();
		pnlFill.setMargin(false);

		// Main Layout

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName("bbr-filter-main-panel");
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.setSpacing(false);
		mainLayout.addComponents(providerFilterSection, clientFilterSection, requestFilterSection, periodFilterSection);
		mainLayout.addComponent(pnlSearchButton);
		mainLayout.addComponent(pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("355px");
		this.addComponent(mainLayout);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private String validateData()
	{
		String errorResult = null;

		if (this.providerFilterSection.validateData() != null)
		{
			errorResult = this.providerFilterSection.validateData();
		}

		return errorResult;
	}

	private BbrFilterEvent getBbrFilterEventObject()
	{
		RequestReportSelectedInfo requestReportSelectedInfo = new RequestReportSelectedInfo();
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();

		requestReportSelectedInfo.setSelectedProviderFilterSection(selectedCompany);

		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);
		result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.COMMERCIAL_RESOURCES, "provider", true) + selectedCompany.getName());

		RequestClientReportFilterSectionInfo selectionClientFilter = clientFilterSection.getSectionResult();
		String strClient = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client");
		String cLientHeader = strClient + ": " + selectionClientFilter.getSitie().getRetailname();
		requestReportSelectedInfo.setSelectedRequestClientReportFilterSectionInfo(selectionClientFilter);

		RequestReportFilterSectionInfo selectionRequestFilter = requestFilterSection.getSectionResult();
		String requestHeader = selectionRequestFilter.getFilterType().getDescription() + ": " + selectionRequestFilter.getService().getValues();
		requestReportSelectedInfo.setSelectedRequestReportFilterSection(selectionRequestFilter);

		RequestReportPeriodFilterSectionInfo selectedRequestReportPeriodFilter = periodFilterSection.getSectionResult();
		requestReportSelectedInfo.setSelectedRequestReportPeriodFilterSection(selectedRequestReportPeriodFilter);
		String filterHeader = selectedRequestReportPeriodFilter.getDateOption().getDescription();
		String filterValueHeader = "";

		if (selectedRequestReportPeriodFilter.getDateOption().getId() == CustomerConstants.CREATION_DATE_FILTER_VALUE)
		{
			String star = (formatDate(selectedRequestReportPeriodFilter.getSelectedStartDate()));
			String end = (formatDate(selectedRequestReportPeriodFilter.getSelectedEndDate()));
			filterValueHeader = star + " - " + end;
		}
		else if (selectedRequestReportPeriodFilter.getDateOption().getId() == CustomerConstants.REFERENCE_NUMBER_FILTER_VALUE)
		{
			filterValueHeader = selectedRequestReportPeriodFilter.getSelectedRefrencenumeber().getValue();
		}
		else if (selectedRequestReportPeriodFilter.getDateOption().getId() == CustomerConstants.REQUEST_NUMBER_FILTER_VALUE)
		{
			filterValueHeader = selectedRequestReportPeriodFilter.getSelectedRequestNumber().getValue();
		}
		filterHeader = cLientHeader + " - " + requestHeader + " -  " + filterHeader + ": " + filterValueHeader;
		result.setSecondaryCaption(filterHeader);
		result.setResultObject(requestReportSelectedInfo);
		return result;
	}

	private void companyChange_Listener(BbrSectionEvent e)
	{
		this.requestFilterSection.setSectionData((CompanyDataDTO) e.getResultObject());
		this.clientFilterSection.setSectionData((CompanyDataDTO) e.getResultObject());
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

}
