package bbr.b2b.portal.components.filters.stockpool;

import java.io.Serializable;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.logistic.report.data.dto.AlertReportInitParamDTO;
import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.logistic.PucharseOrderSearchFilterUtils;
import bbr.b2b.portal.classes.wrappers.customer.AlertStateSectionInfo;
import bbr.b2b.portal.classes.wrappers.customer.TypeAlertSectionInfo;
import bbr.b2b.portal.components.filter_section.generic.ProviderFilterSection;
import bbr.b2b.portal.components.filter_section.stockpool.AlertReportStateCriterionFilterSection;
import bbr.b2b.portal.components.filter_section.stockpool.TypeAlertReportCriterionFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.container.BbrSectionEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class AlertReportFilter extends BbrFilterContainer implements Button.ClickListener
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long						serialVersionUID			= 4666846080775267018L;
	private static final int						MAX_ROWS_BY_PAGE			= 100;

	private ProviderFilterSection					providerFilterSection		= null;
	private AlertReportStateCriterionFilterSection	stateCriterioFilterSection	= null;
	private TypeAlertReportCriterionFilterSection	typeAlertFilterSection		= null;
	private String									codeState					= "";

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public AlertReportFilter(BbrModule bbrModule, String codeState)
	{
		super(bbrModule);
		this.codeState = codeState;
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
			AlertReportInitParamDTO initParam = this.getInitParam();
			BbrFilterEvent bbrFilter = this.getBbrFilterEventObject();
			bbrFilter.setResultObject(initParam);
			this.dispatchBbrFilterEvent(bbrFilter);
		}
		else
		{
			// Faltan datos en el filtro seleccionado
			AlertReportFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	public void initializeView()
	{
		// Seccion Proveedor
		providerFilterSection = new ProviderFilterSection(this.getBbrUIParent());
		providerFilterSection.initializeView();
		providerFilterSection.setSearchProvider(true);
		providerFilterSection.addBbrSectionListener(BbrFilterSectionConstants.FS_PROVIDER, (BbrSectionEventListener & Serializable) e -> this.companyChange_Listener(e));

		// Seccion Estados
		stateCriterioFilterSection = new AlertReportStateCriterionFilterSection(this.getBbrUIParent());
		stateCriterioFilterSection.initializeView();

		// Tipo de Alerta
		typeAlertFilterSection = new TypeAlertReportCriterionFilterSection(this.getBbrUIParent(), this.codeState);
		typeAlertFilterSection.initializeView();

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
		mainLayout.addComponents(this.providerFilterSection, this.stateCriterioFilterSection, this.typeAlertFilterSection, pnlSearchButton, pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("240px");
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
		AlertStateSectionInfo alertFilterSection = this.stateCriterioFilterSection.getSectionResult();
		TypeAlertSectionInfo TypealertFilterSection = this.typeAlertFilterSection.getSectionResult();

		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);

		String company = (selectedCompany.getId() == -1L) ? selectedCompany.getName() : selectedCompany.getName();
		String companyCaption = PucharseOrderSearchFilterUtils.getFilterCaption(PucharseOrderSearchFilterUtils.LOGISTIC_RESOURCES, "company", true) + company;

		String stateCriterio = "";
		String alertType = "";
		if (alertFilterSection.getTypeSearch() != null)
		{
			stateCriterio = PucharseOrderSearchFilterUtils.getFilterCaption(PucharseOrderSearchFilterUtils.CUSTOMER_RESOURCES, "state", true) + alertFilterSection.getTypeSearch().getDescription();
		}
		if (TypealertFilterSection.getTypeSearch() != null)
		{
			alertType = PucharseOrderSearchFilterUtils.getFilterCaption(PucharseOrderSearchFilterUtils.CUSTOMER_RESOURCES, "type_alert", true) + TypealertFilterSection.getTypeSearch().getDescription();
		}

		result.setCaption(companyCaption);
		result.setSecondaryCaption(stateCriterio + " - " + alertType);

		return result;
	}

	private AlertReportInitParamDTO getInitParam()
	{
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		AlertStateSectionInfo alertFilterSection = this.stateCriterioFilterSection.getSectionResult();
		TypeAlertSectionInfo typealertFilterSection = this.typeAlertFilterSection.getSectionResult();

		AlertReportInitParamDTO initParam = new AlertReportInitParamDTO();

		initParam.setVendorcode(selectedCompany.getRut() == null || selectedCompany.getRut().equals("") ? "-1" : selectedCompany.getRut());
		initParam.setAlerttype((typealertFilterSection != null) ? typealertFilterSection.getTypeSearch().getId() : -1);
		initParam.setAlertstate((alertFilterSection != null) ? alertFilterSection.getTypeSearch().getId() : -1);
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
		else if (this.stateCriterioFilterSection.validateData() != null)
		{
			result = this.stateCriterioFilterSection.validateData();
		}
		else if (this.typeAlertFilterSection.validateData() != null)
		{
			result = this.typeAlertFilterSection.validateData();
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