package bbr.b2b.portal.components.filters.management;

import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.components.filter_section.generic.ProviderFilterSection;
import bbr.b2b.portal.components.filter_section.management.PositionFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import bbr.b2b.users.report.classes.PositionDTO;
import bbr.b2b.users.report.classes.ProviderContactReportInitParamDTO;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class ProvidersContactsAdministratorFilter extends BbrFilterContainer implements Button.ClickListener
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long		serialVersionUID		= 9201140812938225663L;
	private ProviderFilterSection	providerFilterSection	= null;
	private PositionFilterSection	positionFilterSection	= null;
	private Button					btn_FilterSearch		= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public ProvidersContactsAdministratorFilter(BbrModule parentModule)
	{
		super(parentModule);
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
			BbrFilterEvent bbrFilter = this.getBbrFilterEventObject();
			ProviderContactReportInitParamDTO initParam = this.getInitParam();
			bbrFilter.setResultObject(initParam);
			dispatchBbrFilterEvent(bbrFilter);
		}
		else
		{
			// Faltan datos en el filtro seleccionado
			ProvidersContactsAdministratorFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	public void initializeView()
	{
		// Seccion Proveedor
		providerFilterSection = new ProviderFilterSection(this.getBbrUIParent());
		providerFilterSection.setCompanyDefault(true);
		providerFilterSection.initializeView();

		// Seccion Position
		positionFilterSection = new PositionFilterSection(this.getBbrUIParent());
		positionFilterSection.initializeView();

		// Bot�n de B�squeda

		btn_FilterSearch = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "generate_report"), this);
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
		mainLayout.addComponent(providerFilterSection);
		mainLayout.addComponent(positionFilterSection);
		mainLayout.addComponent(pnlSearchButton);
		mainLayout.addComponent(pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("165px");
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
		if (this.positionFilterSection.validateData() != null)
		{
			errorResult = this.positionFilterSection.validateData();
		}

		return errorResult;
	}

	private BbrFilterEvent getBbrFilterEventObject()
	{
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		List<PositionDTO> selectedPositions = this.positionFilterSection.getSectionResult();

		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);

		result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.USERS_RESOURCES, "company", true) + selectedCompany.getName());

		String trademarkCaption = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.USERS_RESOURCES, "positions", false)
				+ selectedPositions.get(0).getName() + "...";

		result.setSecondaryCaption(trademarkCaption);

		return result;
	}

	private ProviderContactReportInitParamDTO getInitParam()
	{
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		List<PositionDTO> selectedPositions = this.positionFilterSection.getSectionResult();
		List<Long> positions = selectedPositions.stream().map(PositionDTO::getId).collect(Collectors.toList());

		ProviderContactReportInitParamDTO initParam = new ProviderContactReportInitParamDTO();
		initParam.setCarkey(positions.toArray(new Long[positions.size()]));
		initParam.setPvgkey(selectedCompany.getId());
		initParam.setPvrut(selectedCompany.getRut() != null ? selectedCompany.getRut() : "");

		return initParam;
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
