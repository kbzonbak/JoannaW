package bbr.b2b.portal.components.filter_section.fep;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.classes.wrappers.fep.FoundAttribSearchInfo;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrHorizontalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class TemplateSearchAttribSection extends BbrHorizontalSection<FoundAttribSearchInfo>
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public TemplateSearchAttribSection(BbrUI parent, Button btn_AttribSearch)
	{
		super(parent);
		this.btn_AttribSearch = btn_AttribSearch;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long				serialVersionUID				= 4790440572941282139L;

	private BbrComboBox<SearchCriterion>	cbx_AttributesSearchCriteria	= null;
	private BbrTextField					txt_Parameter					= null;
	private Button							btn_AttribSearch				= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		// Sección Filtro Atributos Genericos
		Label lbl_criteria = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "criteria"));
		lbl_criteria.setWidth("150px");
		lbl_criteria.addStyleName("bold_style");

		this.cbx_AttributesSearchCriteria = this.getSearchCriterionComboBox();

		HorizontalLayout pnlCriteria = new HorizontalLayout(lbl_criteria, this.cbx_AttributesSearchCriteria);
		pnlCriteria.setExpandRatio(this.cbx_AttributesSearchCriteria, 1F);
		pnlCriteria.setSizeFull();

		Label lbl_Parameter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_parameter"));
		lbl_Parameter.setWidth("150px");
		lbl_Parameter.addStyleName("bold_style");

		this.txt_Parameter = new BbrTextField();
		this.txt_Parameter.setWidth(100, Unit.PERCENTAGE);
		this.txt_Parameter.addStyleName("bbr-filter-fields");

		HorizontalLayout pnlParameter = new HorizontalLayout(lbl_Parameter, txt_Parameter);
		pnlParameter.setExpandRatio(txt_Parameter, 1F);
		pnlParameter.setSizeFull();

		VerticalLayout attribSearchForm = new VerticalLayout();
		attribSearchForm.addComponents(pnlCriteria, pnlParameter);
		attribSearchForm.setMargin(false);

		HorizontalLayout attribFilterSection = new HorizontalLayout();
		attribFilterSection.setWidth("100%");
		btn_AttribSearch.setHeight("100%");
		attribFilterSection.addComponents(attribSearchForm, btn_AttribSearch);
		attribFilterSection.setExpandRatio(attribSearchForm, 1F);

		this.setWidth("100%");
		this.addStyleName("bbr-panel-sections");
		this.addStyleName("bbr-panel-space");
		this.addComponents(attribFilterSection);

	}

	@Override
	public FoundAttribSearchInfo getSectionResult()
	{
		FoundAttribSearchInfo result = null;
		if (validateData() == null)
		{
			SearchCriterion criteria = this.cbx_AttributesSearchCriteria.getSelectedValue();
			String parameter = this.txt_Parameter.getValue();

			result = new FoundAttribSearchInfo(criteria, parameter);
		}

		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;

		if ((this.txt_Parameter.getValue() == null) || (this.txt_Parameter.getValue().trim().length() == 0))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "field_required");
		}

		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private BbrComboBox<SearchCriterion> getSearchCriterionComboBox()
	{
		BbrComboBox<SearchCriterion> result = null;
		SearchCriterion[] searcCriterions = FepUtils.getGenericAttributesForTemplatesCriteria();

		result = new BbrComboBox<SearchCriterion>(searcCriterions);

		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();
		result.addStyleName("bbr-filter-fields");

		result.addValueChangeListener(new ValueChangeListener<SearchCriterion>()
		{
			private static final long serialVersionUID = -885062614408629961L;

			@Override
			public void valueChange(ValueChangeEvent<SearchCriterion> event)
			{
				if (cbx_AttributesSearchCriteria.getSelectedValue() != null)
				{
					txt_Parameter.setValue("");
					txt_Parameter.focus();
				}
			}
		});
		result.setWidth(100F, Unit.PERCENTAGE);

		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
