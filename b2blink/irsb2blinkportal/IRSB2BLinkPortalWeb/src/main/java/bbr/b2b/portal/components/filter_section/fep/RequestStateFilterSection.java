package bbr.b2b.portal.components.filter_section.fep;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;

public class RequestStateFilterSection extends BbrVerticalSection<SearchCriterion>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = -6392593059151642075L;

	public RequestStateFilterSection(BbrUI parent)
	{
		super(parent);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private BbrComboBox<SearchCriterion>	cbx_FilterType			= null;
	private VerticalLayout						pnlContent				= null;

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
		Label lbl_ProviderHeader = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_title_requests"));
		lbl_ProviderHeader.addStyleName("bbr-panel-space");
		lbl_ProviderHeader.setWidth("220px");

		HorizontalLayout pnlHeader = new HorizontalLayout();
		pnlHeader.addStyleName("bbr-filter-label-header");
		pnlHeader.addComponents(lbl_ProviderHeader);
		pnlHeader.setWidth("100%");

		// Titulo Filtro
		Label lblFilter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_filter"));
		lblFilter.setWidth("120px");

		cbx_FilterType = this.getSearchCriterionComboBox();

		HorizontalLayout pnlSearchCriteria = new HorizontalLayout();
		pnlSearchCriteria.setWidth("100%");
		pnlSearchCriteria.addComponents(lblFilter, cbx_FilterType);
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
	public SearchCriterion getSectionResult()
	{
		SearchCriterion searchCriterion = (cbx_FilterType != null) ? cbx_FilterType.getSelectedValue() : null;
		return searchCriterion;
	}

	@Override
	public String validateData()
	{
		String result = null;

		SearchCriterion selectedType = this.cbx_FilterType.getSelectedValue();

		if (selectedType == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "filter_type_required");
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
		SearchCriterion[] searcCriterions = FepUtils.getRequestStatesCriteria();
		BbrComboBox<SearchCriterion> result = new BbrComboBox<SearchCriterion>(searcCriterions);
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();
		result.setWidth("255px");
		return result;
	}
	
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
}
