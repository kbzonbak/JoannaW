package bbr.b2b.portal.components.filters.management;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.classes.utils.app.InitParamsUtil;
import bbr.b2b.portal.classes.wrappers.management.CompanyClassificationFilterSectionInfo;
import bbr.b2b.portal.components.filter_section.generic.CompaniesClassificationFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyClassificationReportInitParamDTO;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class CompaniesClassificationManagementFilter extends BbrFilterContainer implements Button.ClickListener
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long						serialVersionUID						= 8489213839800376590L;
	private CompaniesClassificationFilterSection	companiesClassificationFilterSection	= null;
	private Button									btn_FilterSearch						= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	public CompaniesClassificationManagementFilter(BbrModule parentModule)
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
			CompanyClassificationReportInitParamDTO initParam = this.getInitParam();
			bbrFilter.setResultObject(initParam);
			dispatchBbrFilterEvent(bbrFilter);
		}
		else
		{
			// Faltan datos en el filtro seleccionado
			CompaniesClassificationManagementFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	public void initializeView()
	{
		// Seccion Proveedor
		companiesClassificationFilterSection = new CompaniesClassificationFilterSection(this.getBbrUIParent(), true, true);
		companiesClassificationFilterSection.initializeView();

		// Botón de Búsqueda
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
		mainLayout.addComponents(companiesClassificationFilterSection, pnlSearchButton, pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("130px");
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

		if (this.companiesClassificationFilterSection.validateData() != null)
		{
			errorResult = this.companiesClassificationFilterSection.validateData();
		}

		return errorResult;
	}

	private BbrFilterEvent getBbrFilterEventObject()
	{
		CompanyClassificationFilterSectionInfo productSectionInfo = this.companiesClassificationFilterSection.getSectionResult();
		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);

		result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.USERS_RESOURCES, "criteria", true) + productSectionInfo.getSelectedCriteria().getDescription());

		if (!productSectionInfo.getSelectedCriteria().getId().equals(-1))
		{
			String secundaryCaption = FilterHeaderUtils.getClassificationFilterCaption(productSectionInfo.getCaptionValueByType(productSectionInfo.getFiltertype()), false);

			result.setSecondaryCaption(secundaryCaption);
		}

		return result;
	}

	private CompanyClassificationReportInitParamDTO getInitParam()
	{
		CompanyClassificationFilterSectionInfo productSectionInfo = this.companiesClassificationFilterSection.getSectionResult();

		CompanyClassificationReportInitParamDTO initParam = InitParamsUtil.getBaseInitParamInstance(CompanyClassificationReportInitParamDTO.class, this.getBbrUIParent());
		initParam.setFiltertype(productSectionInfo.getFiltertype());
		initParam.setClasificationsId(productSectionInfo.getClassificationsIds(productSectionInfo.getClasificationsSelected()));
		initParam.setValue(productSectionInfo.getValue());

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
