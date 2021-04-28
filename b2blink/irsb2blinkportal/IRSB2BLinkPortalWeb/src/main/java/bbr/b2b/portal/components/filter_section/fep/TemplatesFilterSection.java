package bbr.b2b.portal.components.filter_section.fep;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.classes.wrappers.fep.TemplatesSearchInfo;
import bbr.b2b.portal.components.filters.fep.TemplatesFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class TemplatesFilterSection extends BbrVerticalSection<TemplatesSearchInfo>
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public TemplatesFilterSection(BbrUI parent)
	{
		super(parent);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long				serialVersionUID	= 6568321882814949548L;

	private BbrComboBox<SearchCriterion>	cbx_FilterCriteria	= null;
	private CheckBox						chbx_ActiveOption	= null;
	private BbrTextField					txt_TemplateValue	= null;
	private HorizontalLayout				pnlValueSection		= null;
	VerticalLayout							pnlBodySection		= null;
	TemplatesFilter							parentComponent		= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		Label lbl_TemplatesHeader = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_templates"));
		lbl_TemplatesHeader.addStyleName("bbr-panel-space");

		HorizontalLayout pnlTemplateHeader = new HorizontalLayout();
		pnlTemplateHeader.addStyleName("bbr-filter-label-header");
		pnlTemplateHeader.addComponent(lbl_TemplatesHeader);
		pnlTemplateHeader.setWidth("100%");

		Label lbl_Criterion = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_criterion"));
		lbl_Criterion.setWidth("120px");

		this.cbx_FilterCriteria = this.getSearchCriterionStateComboBox();

		HorizontalLayout pnlSelectionSection = new HorizontalLayout();
		pnlSelectionSection.setWidth("100%");
		pnlSelectionSection.addComponents(lbl_Criterion, this.cbx_FilterCriteria);
		pnlSelectionSection.setExpandRatio(cbx_FilterCriteria, 1F);
		pnlSelectionSection.addStyleName("bbr-panel-space");

		HorizontalLayout pnlValueSubsection = new HorizontalLayout();

		Label lbl_Filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_filter"));
		lbl_Filter.setWidth("120px");

		this.txt_TemplateValue = new BbrTextField();
		this.txt_TemplateValue.setWidth("245px");
		this.txt_TemplateValue.setHeight("30px");

		pnlValueSubsection.addComponents(lbl_Filter, this.txt_TemplateValue);
		pnlValueSubsection.setSpacing(true);

		this.pnlValueSection = new HorizontalLayout();
		this.pnlValueSection.setWidth("100%");
		this.pnlValueSection.addComponents(pnlValueSubsection);
		this.pnlValueSection.setExpandRatio(pnlValueSubsection, 1F);
		this.pnlValueSection.addStyleName("bbr-panel-space");

		this.chbx_ActiveOption = new CheckBox();
		this.chbx_ActiveOption.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "only_active_templates"));
		this.chbx_ActiveOption.setWidth("245px");
		this.chbx_ActiveOption.setHeight("20px");

		HorizontalLayout pnl_spaces = new HorizontalLayout();
		pnl_spaces.setWidth("120px");

		HorizontalLayout pnlOptionSection = new HorizontalLayout();
		pnlOptionSection.addComponents(pnl_spaces, this.chbx_ActiveOption);
		pnlOptionSection.addStyleName("bbr-panel-space");

		this.pnlBodySection = new VerticalLayout();
		this.pnlBodySection.setWidth("100%");
		this.pnlBodySection.setHeight("95px");
		this.pnlBodySection.setMargin(false);
		this.pnlBodySection.setSpacing(false);
		this.pnlBodySection.addComponentsAndExpand(pnlSelectionSection, this.pnlValueSection, pnlOptionSection);

		this.pnlValueSection.setVisible(false);

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlTemplateHeader, this.pnlBodySection);
	}

	@Override
	public TemplatesSearchInfo getSectionResult()
	{
		TemplatesSearchInfo result = null;

		if (validateData() == null)
		{
			SearchCriterion filterState = this.cbx_FilterCriteria.getSelectedValue();

			result = new TemplatesSearchInfo(filterState, txt_TemplateValue.getValue(), chbx_ActiveOption);
		}

		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;

		if (this.cbx_FilterCriteria.getSelectedValue() == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "filter_search_criteria_required");
		}
		else if (this.cbx_FilterCriteria.getSelectedValue().getId() > 0)
		{
			if (txt_TemplateValue.getValue() == null || txt_TemplateValue.getValue().trim().length() == 0)
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "value_search_criteria_required");
			}
		}

		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private BbrComboBox<SearchCriterion> getSearchCriterionStateComboBox()
	{
		SearchCriterion[] searchCriterions = FepUtils.getTemplatesCriteria();
		BbrComboBox<SearchCriterion> result = new BbrComboBox<SearchCriterion>(searchCriterions);
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();
		result.addValueChangeListener((e) -> filter_changeHandler(e));
		result.setWidth("245px");
		return result;
	}

	private void filter_changeHandler(ValueChangeEvent<SearchCriterion> e)
	{
		// Preguntar a MHE por el id correspondiente a la opcion TODOS

		if (cbx_FilterCriteria.getSelectedValue().getId() == 0)
		{
			this.pnlValueSection.setVisible(false);
		}
		else
		{
			this.txt_TemplateValue.setValue("");
			this.pnlValueSection.setVisible(true);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
