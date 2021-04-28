package bbr.b2b.portal.components.filter_section.fep;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.classes.wrappers.fep.AttributeSearchInfo;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class AttributesFilterSection extends BbrVerticalSection<AttributeSearchInfo>
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public AttributesFilterSection(BbrUI parent)
	{
		super(parent);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long				serialVersionUID		= 7794677752367386325L;

	private BbrComboBox<SearchCriterion>	cbx_FilterCriteria		= null;
	private CheckBox						chbx_ActiveOption		= null;
	private BbrTextField					txt_AttributeCriteria	= null;
	private HorizontalLayout				pnlValueSection			= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		Label lbl_AttributesHeader = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_attributes"));
		lbl_AttributesHeader.addStyleName("bbr-panel-space");

		HorizontalLayout pnlAttributeHeader = new HorizontalLayout();
		pnlAttributeHeader.addStyleName("bbr-filter-label-header");
		pnlAttributeHeader.addComponent(lbl_AttributesHeader);
		pnlAttributeHeader.setWidth("100%");

		Label lbl_filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_filter"));
		lbl_filter.setWidth("120px");

		this.cbx_FilterCriteria = this.getSearchCriterionStateComboBox();

		HorizontalLayout pnlSelectionSection = new HorizontalLayout();
		pnlSelectionSection.setWidth("100%");
		pnlSelectionSection.addComponents(lbl_filter, this.cbx_FilterCriteria);
		pnlSelectionSection.setExpandRatio(cbx_FilterCriteria, 1F);
		pnlSelectionSection.addStyleName("bbr-panel-space");

		HorizontalLayout pnlValueSubsection = new HorizontalLayout();

		HorizontalLayout space = new HorizontalLayout();
		space.setWidth("120px");

		this.txt_AttributeCriteria = new BbrTextField();
		this.txt_AttributeCriteria.setWidth("245px");
		this.txt_AttributeCriteria.setHeight("30px");

		pnlValueSubsection.addComponents(space, this.txt_AttributeCriteria);
		pnlValueSubsection.setSpacing(true);

		this.pnlValueSection = new HorizontalLayout();
		this.pnlValueSection.setWidth("100%");
		this.pnlValueSection.addComponents(pnlValueSubsection);
		this.pnlValueSection.setExpandRatio(pnlValueSubsection, 1F);
		this.pnlValueSection.addStyleName("bbr-panel-space");

		this.chbx_ActiveOption = new CheckBox();
		this.chbx_ActiveOption.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "active_option"));
		this.chbx_ActiveOption.setWidth("245px");
		this.chbx_ActiveOption.setHeight("20px");

		HorizontalLayout pnl_spaces = new HorizontalLayout();
		pnl_spaces.setWidth("120px");

		HorizontalLayout pnlOptionSection = new HorizontalLayout();
		pnlOptionSection.addComponents(pnl_spaces, this.chbx_ActiveOption);
		pnlOptionSection.addStyleName("bbr-panel-space");

		VerticalLayout pnlBodySection = new VerticalLayout();
		pnlBodySection.setWidth("100%");
		pnlBodySection.setHeight("95px");
		pnlBodySection.setMargin(false);
		pnlBodySection.setSpacing(false);
		pnlBodySection.addComponentsAndExpand(pnlSelectionSection, this.pnlValueSection, pnlOptionSection);

		this.pnlValueSection.setVisible(false);

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlAttributeHeader, pnlBodySection);

	}

	@Override
	public AttributeSearchInfo getSectionResult()
	{
		AttributeSearchInfo result = null;
		if (validateData() == null)
		{
			SearchCriterion filterState = this.cbx_FilterCriteria.getSelectedValue();

			result = new AttributeSearchInfo(filterState, txt_AttributeCriteria.getValue(), chbx_ActiveOption);
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
		else if (this.cbx_FilterCriteria.getSelectedValue().getId() >= 0)
		{
			if (txt_AttributeCriteria.getValue() == null || txt_AttributeCriteria.getValue().trim().length() == 0)
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "value_search_criteria_required");
			}
		}

		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private BbrComboBox<SearchCriterion> getSearchCriterionStateComboBox()
	{
		SearchCriterion[] searcCriterions = FepUtils.getAttributesCriteria();
		BbrComboBox<SearchCriterion> result = new BbrComboBox<SearchCriterion>(searcCriterions);
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
		if (cbx_FilterCriteria.getSelectedValue().getId() == -1)
		{
			this.pnlValueSection.setVisible(false);
		}
		else
		{
			this.txt_AttributeCriteria.setValue("");
			this.pnlValueSection.setVisible(true);
		}

	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
