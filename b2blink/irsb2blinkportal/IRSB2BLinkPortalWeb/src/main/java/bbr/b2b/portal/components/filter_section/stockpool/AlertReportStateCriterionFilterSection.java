package bbr.b2b.portal.components.filter_section.stockpool;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.stockpool.ScockPoolSearchCriteriaFilterUtils;
import bbr.b2b.portal.classes.wrappers.customer.AlertStateSectionInfo;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;

public class AlertReportStateCriterionFilterSection extends BbrVerticalSection<AlertStateSectionInfo>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 4279165028171908839L;

	public AlertReportStateCriterionFilterSection(BbrUI parent)
	{
		super(parent);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private BbrComboBox<SearchCriterion>	cbx_FilterType		= null;
	private VerticalLayout					pnlContent			= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		// Label Título
		Label lbl_StateHeader = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "state"));
		lbl_StateHeader.addStyleName("bbr-panel-space");
		lbl_StateHeader.setWidth("220px");

		HorizontalLayout pnlHeader = new HorizontalLayout();
		pnlHeader.addStyleName("bbr-filter-label-header");
		pnlHeader.addComponents(lbl_StateHeader);
		pnlHeader.setWidth("100%");

		// Filtro OC
		Label lblOCFilter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "filter"));
		lblOCFilter.setWidth("120px");

		cbx_FilterType = this.getSearchCriterionComboBox();

		HorizontalLayout pnlSearchCriteria = new HorizontalLayout();
		pnlSearchCriteria.setWidth("100%");
		pnlSearchCriteria.addComponents(lblOCFilter, cbx_FilterType);
		pnlSearchCriteria.setExpandRatio(cbx_FilterType, 1F);
		pnlSearchCriteria.addStyleName("bbr-panel-space");

		pnlContent = new VerticalLayout();
		pnlContent.addComponents(pnlHeader, pnlSearchCriteria);
		pnlContent.setWidth("100%");
		pnlContent.setSpacing(false);
		pnlContent.setMargin(false);

		this.setWidth("400px");
		this.setHeight("65px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponent(pnlContent);
	}

	@Override
	public AlertStateSectionInfo getSectionResult()
	{
		
		SearchCriterion searchCriterion = (cbx_FilterType != null) ? cbx_FilterType.getSelectedValue() : null;

		AlertStateSectionInfo result = new AlertStateSectionInfo(searchCriterion);

		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;

		SearchCriterion selectedType = this.cbx_FilterType.getSelectedValue();

		if (selectedType == null)
		{

			result = this.getI18n("filter_type_required");
		}
		

		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private BbrComboBox<SearchCriterion> getSearchCriterionComboBox()
	{
		SearchCriterion[] searcCriterions = ScockPoolSearchCriteriaFilterUtils.getInstance().getAlertStateCriteria();
		BbrComboBox<SearchCriterion> result = new BbrComboBox<SearchCriterion>(searcCriterions);
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();
		result.setWidth("255px");
		return result;
	}

	public String getI18n(String resource)
	{
		return I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, resource);
	}

	public String getI18n(String resource, String... params)
	{
		return I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, resource, params);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
}
