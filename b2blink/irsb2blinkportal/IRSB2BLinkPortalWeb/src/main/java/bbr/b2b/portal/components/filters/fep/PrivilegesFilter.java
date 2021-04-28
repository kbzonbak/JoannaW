package bbr.b2b.portal.components.filters.fep;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.UserTypeDTO;
import bbr.b2b.fep.cp.data.classes.CPItemStateDTO;
import bbr.b2b.fep.cp.data.classes.CPPrivilegeInitParamDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.components.filter_section.fep.PrivilegesFilterSection;
import bbr.b2b.portal.components.filter_section.fep.UserRolesFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class PrivilegesFilter extends BbrFilterContainer implements Button.ClickListener
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long			serialVersionUID				= 3153326837988496037L;
	private static final String		PROVISIONAL_PROVIDER_CODE	= "764160657";

	private PrivilegesFilterSection	privilegesFilterSection		= null;
	private UserRolesFilterSection	rolesFilterSection			= null;
	private Button							btn_FilterSearch				= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public PrivilegesFilter(BbrModule parentModule)
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
			CPPrivilegeInitParamDTO initParam = this.getInitParam();

			bbrFilter.setResultObject(initParam);

			dispatchBbrFilterEvent(bbrFilter);
		}

		else
		{
			// Faltan datos en el filtro seleccionado
			PrivilegesFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}


	public void initializeView()
	{
		// Filtro Privilegios

		this.privilegesFilterSection = new PrivilegesFilterSection(this.getBbrUIParent(), PROVISIONAL_PROVIDER_CODE);
		this.privilegesFilterSection.initializeView();

		// Filtro Roles de usuario

		this.rolesFilterSection = new UserRolesFilterSection(this.getBbrUIParent(), PROVISIONAL_PROVIDER_CODE, false);
		this.rolesFilterSection.initializeView();

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
		mainLayout.setSpacing(true);
		mainLayout.addComponents(this.privilegesFilterSection, this.rolesFilterSection, pnlSearchButton);
		mainLayout.addComponent(pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("190px");
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

		errorResult = this.privilegesFilterSection.validateData();

		if (errorResult == null)
		{
			errorResult = this.rolesFilterSection.validateData();
		}

		return errorResult;
	}


	private BbrFilterEvent getBbrFilterEventObject()
	{
		CPItemStateDTO privilegeSearchInfo = this.privilegesFilterSection.getSectionResult();
		UserTypeDTO roleSectionInfo = this.rolesFilterSection.getSectionResult();

		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);

		result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES, "lbl_state", true) + privilegeSearchInfo.getName());

		String roleCaption = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES, "user_role", false) + roleSectionInfo.getName();

		result.setSecondaryCaption(roleCaption);

		return result;
	}


	private CPPrivilegeInitParamDTO getInitParam()
	{
		CPItemStateDTO privilegeSearchInfo = this.privilegesFilterSection.getSectionResult();
		UserTypeDTO roleSectionInfo = this.rolesFilterSection.getSectionResult();

		CPPrivilegeInitParamDTO initParam = new CPPrivilegeInitParamDTO();

		if (this.validateData() == null)
		{
			initParam.setItemstateid(privilegeSearchInfo.getId());
			initParam.setAttributeid(-1L);
			initParam.setUsertypeid(roleSectionInfo.getId());
		}

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
