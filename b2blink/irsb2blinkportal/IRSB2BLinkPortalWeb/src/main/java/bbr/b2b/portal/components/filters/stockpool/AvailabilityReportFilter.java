package bbr.b2b.portal.components.filters.stockpool;

import java.io.Serializable;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.logistic.report.data.dto.AvailabilityReportInitParamDTO;
import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.logistic.PucharseOrderSearchFilterUtils;
import bbr.b2b.portal.classes.wrappers.stockpool.RequestClientSPLReportFilterSectionInfo;
import bbr.b2b.portal.components.filter_section.generic.ProviderFilterSection;
import bbr.b2b.portal.components.filter_section.stockpool.RequestClientSPLFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.container.BbrSectionEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class AvailabilityReportFilter extends BbrFilterContainer implements Button.ClickListener
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long				serialVersionUID		= 4666846080775267018L;
	private static final int				MAX_ROWS_BY_PAGE		= 100;

	private ProviderFilterSection			providerFilterSection	= null;
	private RequestClientSPLFilterSection	clientsFilterSection	= null;
	private String							clientName				= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public AvailabilityReportFilter(BbrModule bbrModule, String clienName)
	{
		super(bbrModule);
		this.clientName = clienName;
	}

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
			AvailabilityReportInitParamDTO initParam = this.getInitParam();
			BbrFilterEvent bbrFilter = this.getBbrFilterEventObject();
			bbrFilter.setResultObject(initParam);
			this.dispatchBbrFilterEvent(bbrFilter);
		}
		else
		{
			// Faltan datos en el filtro seleccionado
			AvailabilityReportFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	public void initializeView()
	{
		// Seccion Proveedor
		providerFilterSection = new ProviderFilterSection(this.getBbrUIParent());
		providerFilterSection.initializeView();
		providerFilterSection.setSearchProvider(true);
		providerFilterSection.addBbrSectionListener(BbrFilterSectionConstants.FS_PROVIDER, (BbrSectionEventListener & Serializable) e -> this.companyChange_Listener(e));

		// Seccion Cliente
		clientsFilterSection = new RequestClientSPLFilterSection(this.getBbrUIParent(), this.clientName);
		clientsFilterSection.setSectionData(providerFilterSection.getSectionResult());
		clientsFilterSection.initializeView();

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
		mainLayout.addComponents(this.providerFilterSection, this.clientsFilterSection, pnlSearchButton, pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("160px");
		this.addComponent(mainLayout);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private BbrFilterEvent getBbrFilterEventObject()
	{
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		RequestClientSPLReportFilterSectionInfo selectedClient = this.clientsFilterSection.getSectionResult();

		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);

		String company = (selectedCompany.getId() == -1L) ? selectedCompany.getName() : selectedCompany.getName();
		String companyCaption = PucharseOrderSearchFilterUtils.getFilterCaption(PucharseOrderSearchFilterUtils.LOGISTIC_RESOURCES, "company", true) + company;

		String client = "";
		if (selectedClient != null)
		{
			client = PucharseOrderSearchFilterUtils.getFilterCaption(PucharseOrderSearchFilterUtils.CUSTOMER_RESOURCES, "client", true) + selectedClient.getSitie().getName();
		}

		result.setCaption(companyCaption);
		result.setSecondaryCaption(client);

		return result;
	}

	private AvailabilityReportInitParamDTO getInitParam()
	{
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		RequestClientSPLReportFilterSectionInfo selectedClient = this.clientsFilterSection.getSectionResult();

		AvailabilityReportInitParamDTO initParam = new AvailabilityReportInitParamDTO();

		initParam.setVendorcode(selectedCompany.getRut() == null || selectedCompany.getRut().equals("") ? "-1" : selectedCompany.getRut());
		initParam.setBuyercode((selectedClient != null) ? selectedClient.getSitie().getCode() : "-1");
		initParam.setRows(MAX_ROWS_BY_PAGE);

		return initParam;
	}

	public String validateData()
	{
		String result = null;

		if (this.providerFilterSection.validateData() != null)
		{
			result = this.providerFilterSection.validateData();
		}
		else if (this.clientsFilterSection.validateData() != null)
		{
			result = this.clientsFilterSection.validateData();
		}

		return result;
	}

	private void companyChange_Listener(BbrSectionEvent e)
	{

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