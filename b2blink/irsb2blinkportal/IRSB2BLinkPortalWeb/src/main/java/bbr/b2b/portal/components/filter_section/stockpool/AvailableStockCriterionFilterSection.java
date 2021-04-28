package bbr.b2b.portal.components.filter_section.stockpool;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.HasI18n;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.classes.utils.stockpool.ScockPoolSearchCriteriaFilterUtils;
import bbr.b2b.portal.classes.wrappers.stockpool.SPLCriterionDTO;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class AvailableStockCriterionFilterSection extends BbrVerticalSection<SPLCriterionDTO> implements HasI18n
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long				serialVersionUID	= 4279165028171908839L;
	// Components
	private HorizontalLayout				pnlSearchPanel		= null;
	private String							stateSelected		= null;
	private BbrComboBox<SearchCriterion>	cbx_FilterType		= null;
	private HorizontalLayout				cmp_FilterFields	= null;
	private BbrTextField					txt_itemSku			= null;
	private VerticalLayout					pnlContent			= null;
	private BbrComboBox<SearchCriterion>	cbx_State			= null;
	// Variables

	public static final int					SKU					= 2;
	public static final int					STATE				= 1;


	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public AvailableStockCriterionFilterSection(BbrUI parent, String stateSelected)
	{
		super(parent);
		this.stateSelected = stateSelected;
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		// Label Título
		this.pnlSearchPanel = new HorizontalLayout();

		BbrHInputFieldContainer pnlHeader = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "criteria"))
				.withLabelWidth("220px")
				.withLabelStyle(BbrStyles.BBR_PANEL_SPACE)
				.withComponent(this.pnlSearchPanel)
				.build();
		pnlHeader.addStyleName(BbrStyles.BBR_FILTER_LABEL_HEADER);

		// Filtro por
		this.cbx_FilterType = this.getSearchCriterionComboBox();
		this.cbx_FilterType.setWidth("255px");
		this.cbx_FilterType.addStyleName(BbrStyles.BBR_FILTER_FIELDS);
		BbrHInputFieldContainer pnlSearchCriteria = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "filter_by"))
				.withLabelWidth("120px")
				.withComponent(cbx_FilterType)
				.build();
		pnlSearchCriteria.addStyleName(BbrStyles.BBR_PANEL_SPACE);

		cmp_FilterFields = getFieldsComponents();

		this.pnlContent = new VerticalLayout();
		this.pnlContent.addComponents(pnlHeader, pnlSearchCriteria, cmp_FilterFields);
		this.pnlContent.setSizeFull();
		this.pnlContent.setSpacing(false);
		this.pnlContent.setMargin(false);

		this.setWidth("400px");
		this.setHeight("110px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(this.pnlContent);

	}

	@Override
	public SPLCriterionDTO getSectionResult()
	{
		SearchCriterion searchCriterion = (cbx_FilterType != null) ? cbx_FilterType.getSelectedValue() : null;

		SearchCriterion criterion = cbx_FilterType.getSelectedValue();
		SearchCriterion state = null;
		String sku = null;

		if (criterion != null)
		{
			switch (criterion.getId())
			{
				case 2:
					state = (cbx_State != null) ? cbx_State.getSelectedValue() : null;
					break;
				case 1:
					sku = (txt_itemSku != null && txt_itemSku.getValue().length() > 0) ? txt_itemSku.getValue().toString() : null;
					break;
			}
		}

		SPLCriterionDTO result = new SPLCriterionDTO(searchCriterion, sku, state);

		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;

		SearchCriterion selectedType = this.cbx_FilterType.getSelectedValue();

		if (selectedType != null)
		{

			switch (selectedType.getId())
			{

				case SKU:
					if (txt_itemSku == null || txt_itemSku.getValue().length() == 0)
					{
						result = this.getI18n("filter_order_number_required");
					}
					else if (!BbrUtils.isLongNumber(txt_itemSku.getValue()))
					{
						result = this.getI18n("filter_order_number_required_number");
					}

					break;

				case STATE:
					if (this.cbx_State == null || this.cbx_State.getSelectedValue() == null)
					{
						result = this.getI18n("filter_order_state_required");
					}
					break;
				default:
					break;
			}
		}
		else
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
		SearchCriterion[] searcCriterions = ScockPoolSearchCriteriaFilterUtils.getInstance().getStockCriteria();
		BbrComboBox<SearchCriterion> result = new BbrComboBox<SearchCriterion>(searcCriterions);
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();
		result.addValueChangeListener((e) -> ocFilter_changeHandler(e));
		result.setWidth("255px");
		return result;
	}

	private void ocFilter_changeHandler(ValueChangeEvent<SearchCriterion> e)
	{
		if (pnlContent.getComponentIndex(cmp_FilterFields) != -1)
		{
			pnlContent.removeComponent(cmp_FilterFields);
		}

		if (cbx_FilterType.getSelectedValue().getId() != null)
		{
			cmp_FilterFields = getFieldsComponents();
			cmp_FilterFields.markAsDirty();

			pnlContent.addComponent(cmp_FilterFields);
		}
	}

	private HorizontalLayout getFieldsComponents()
	{

		HorizontalLayout result = null;
		SearchCriterion criterion = cbx_FilterType.getSelectedValue();
		if (criterion != null)
		{
	
			switch (criterion.getId())
			{
				case 1: //SKU
					result = this.getItemSkuPanel();
					break;
				case 2: //STATE
					result = this.getStatePanel();
					break;
				default:
					result = new HorizontalLayout();
					result.setSizeFull();
					break;
			}
		}

		return result;
	}

	private HorizontalLayout getStatePanel()
	{
		if (this.cbx_State == null)
		{
			SearchCriterion[] stateTypes = ScockPoolSearchCriteriaFilterUtils.getInstance().getStatesCriteria();
			if (stateTypes != null && stateTypes.length > 0)
			{
				this.cbx_State = new BbrComboBox<SearchCriterion>(stateTypes);
				this.cbx_State.selectItem(0);
				if (this.stateSelected != null)
				{
					for (int i = 0; i < stateTypes.length; i++)
					{
						if (stateTypes[i].getDescription().equals(this.stateSelected))
						{
							this.cbx_State.selectItem(i);
						}
					}
				}
				else
				{
					this.cbx_State.selectItem(0);
				}
			}
			else
			{
				this.cbx_State = new BbrComboBox<SearchCriterion>();
			}

			this.cbx_State.setItemCaptionGenerator(SearchCriterion::getDescription);
			this.cbx_State.setTextInputAllowed(false);
			this.cbx_State.setEmptySelectionAllowed(false);
			this.cbx_State.addStyleName("bbr-filter-fields");
			this.cbx_State.setWidth("255px");
			this.cbx_State.setHeight("30px");
		}

		BbrHInputFieldContainer result = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "value"))
				.withLabelWidth("120px")
				.withComponent(this.cbx_State)
				.build();
		return result;
	}


	private HorizontalLayout getItemSkuPanel()
	{
		if (this.txt_itemSku == null)
		{
			this.txt_itemSku = new BbrTextField();
			this.txt_itemSku.addStyleName("bbr-filter-fields");
			this.txt_itemSku.setWidth("255px");
			this.txt_itemSku.setHeight("30px");
		}

		this.txt_itemSku.setValue("");

		BbrHInputFieldContainer result = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "value"))
				.withLabelWidth("120px")
				.withComponent(this.txt_itemSku)
				.build();
		return result;
	}


	@Override
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
