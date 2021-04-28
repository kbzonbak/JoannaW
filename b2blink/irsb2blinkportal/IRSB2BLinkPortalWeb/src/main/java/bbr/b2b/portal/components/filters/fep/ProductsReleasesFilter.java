package bbr.b2b.portal.components.filters.fep;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.UserTypeDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByStatusInitParamDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.classes.wrappers.fep.ProductsReleasesFilterSearch;
import bbr.b2b.portal.components.filter_section.fep.UserRolesFilterSection;
import bbr.b2b.portal.components.filter_section.generic.ProviderFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class ProductsReleasesFilter extends BbrFilterContainer implements Button.ClickListener
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long				serialVersionUID				= -689883002303274930L;

	private UserRolesFilterSection		rolesFilterSection			= null;
	private ProviderFilterSection			providerFilterSection		= null;
	private Button								btn_FilterSearch				= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductsReleasesFilter(BbrModule parentModule)
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
			BbrFilterEvent bbrFilter 						= this.getBbrFilterEventObject();
			ProductsReleasesFilterSearch filterSearch = this.getFilterSearch();

			bbrFilter.setResultObject(filterSearch);

			dispatchBbrFilterEvent(bbrFilter);
		}

		else
		{
			// Faltan datos en el filtro seleccionado
			ProductsReleasesFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	public void initializeView()
	{
		rolesFilterSection = new UserRolesFilterSection(getBbrUIParent(), null, true);
		rolesFilterSection.initializeView();
		
		// Seccion Proveedor
		providerFilterSection = new ProviderFilterSection(this.getBbrUIParent());
		providerFilterSection.initializeView();

		// Botón de Búsqueda
		btn_FilterSearch = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "generate_report"), this);
		btn_FilterSearch.setIcon(VaadinIcons.CHECK_CIRCLE_O);
		btn_FilterSearch.setStyleName("btn-filter-search");

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
		mainLayout.addComponents(rolesFilterSection, providerFilterSection);
		mainLayout.addComponent(pnlSearchButton);
		mainLayout.addComponent(pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("175px");
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

		errorResult = this.rolesFilterSection.validateData();
		
		if (errorResult == null)
		{
			errorResult = this.providerFilterSection.validateData();
		}
		
		return errorResult;
	}

	private BbrFilterEvent getBbrFilterEventObject()
	{
		CompanyDataDTO selectedCompany 	= this.providerFilterSection.getSectionResult();
		UserTypeDTO roleSectionInfo 		= this.rolesFilterSection.getSectionResult();

		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);
		
		String companyHeader = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.COMMERCIAL_RESOURCES, "company", true) + selectedCompany.getName();
		String rolesHeader = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES, "user_role", false) + roleSectionInfo.getName();
		
		result.setCaption(companyHeader);
		result.setSecondaryCaption(rolesHeader);
		
		return result;
	}

	private ProductsReleasesFilterSearch getFilterSearch()
	{
		CompanyDataDTO selectedCompany 	= this.providerFilterSection.getSectionResult();
		UserTypeDTO selectedRole			= this.rolesFilterSection.getSectionResult();
		
		CPItemsByStatusInitParamDTO initParam = new CPItemsByStatusInitParamDTO();
		initParam.setProvidercode((selectedCompany != null) ? selectedCompany.getRut() : null);
		initParam.setProviderid((selectedCompany != null) ? selectedCompany.getId() : -1);
		initParam.setStatus(FEPConstants.PENDING_STATE_VALUE);
		initParam.setUsertypeid(selectedRole.getId());
		initParam.setUsertypename(selectedRole.getInternalname());
		
		ProductsReleasesFilterSearch result = new ProductsReleasesFilterSearch(selectedCompany, selectedRole, initParam);
		
		return result;
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
