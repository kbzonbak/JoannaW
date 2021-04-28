package bbr.b2b.portal.components.filters.fep;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.ClientDTO;
import bbr.b2b.fep.common.data.classes.UserDataTypeDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.classes.wrappers.fep.AttributeSearchInfo;
import bbr.b2b.portal.classes.wrappers.fep.AttributesInfo;
import bbr.b2b.portal.components.filter_section.fep.AttributesFilterSection;
import bbr.b2b.portal.components.filter_section.fep.ClientsFilterSection;
import bbr.b2b.portal.components.filter_section.fep.DataTypeFilterSectionMultiCombo;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class AttributesFilter extends BbrFilterContainer implements Button.ClickListener
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long						serialVersionUID			= -689883002303274930L;

	private AttributesFilterSection				attributesFilterSection	= null;
	private DataTypeFilterSectionMultiCombo	dataTypeFilterSection	= null;
	private ClientsFilterSection					clientsFilterSection		= null;
	private Button										btn_FilterSearch			= null;
	private String										type							= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public AttributesFilter(BbrModule parentModule, String type)
	{
		super(parentModule);
		this.type = type;
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
			AttributesInfo initParam = this.getInitParam();

			bbrFilter.setResultObject(initParam);

			dispatchBbrFilterEvent(bbrFilter);
		}

		else
		{
			// Faltan datos en el filtro seleccionado
			AttributesFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}


	public void initializeView()
	{
		// Seccion Actividad
		attributesFilterSection = new AttributesFilterSection(this.getBbrUIParent());
		attributesFilterSection.initializeView();

		dataTypeFilterSection = new DataTypeFilterSectionMultiCombo(this.getBbrUIParent());
		dataTypeFilterSection.initializeView();

		clientsFilterSection = new ClientsFilterSection(this.getBbrUIParent());
		clientsFilterSection.initializeView();

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
		mainLayout.addComponents(attributesFilterSection, dataTypeFilterSection, clientsFilterSection);
		mainLayout.addComponent(pnlSearchButton);
		mainLayout.addComponent(pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("305px");
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

		errorResult = this.attributesFilterSection.validateData();

		if (errorResult == null)
		{
			errorResult = this.dataTypeFilterSection.validateData();
		}
		return errorResult;
	}


	private BbrFilterEvent getBbrFilterEventObject()
	{
		AttributeSearchInfo attributeSearchInfo = this.attributesFilterSection.getSectionResult();
		Set<UserDataTypeDTO> selectedDataType = this.dataTypeFilterSection.getSectionResult();
		ClientDTO selectedClient = this.clientsFilterSection.getSectionResult();

		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);

		List<String> dataTypesList = selectedDataType.stream().map(d -> d.getName()).collect(Collectors.toList());
		String dataTypesString = String.join(", ", dataTypesList);
		StringBuilder dataTypesStringBuilder = new StringBuilder(dataTypesString);

		if (dataTypesStringBuilder.length() > 50)
		{
			StringBuilder stringBuilder = new StringBuilder(dataTypesStringBuilder.substring(0, 50).concat("..."));
			dataTypesStringBuilder = stringBuilder;
		}

		if (attributeSearchInfo.getFilterState().getId() != -1)
		{
			result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES,
			"attribute",
			true) + attributeSearchInfo.getValue());
		}
		else
		{
			result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES,
			"attribute",
			true) + attributeSearchInfo.getFilterState().getDescription());
		}
		String dataTypeCaption = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES,
		"lbl_data_type",
		false) + dataTypesStringBuilder;

		String clientCaption = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES,
		"lbl_client",
		false) + selectedClient.getName();

		result.setSecondaryCaption(dataTypeCaption + clientCaption);

		return result;
	}


	private AttributesInfo getInitParam()
	{
		AttributeSearchInfo attributeSearchInfo = this.attributesFilterSection.getSectionResult();
		Set<UserDataTypeDTO> selectedDataType = this.dataTypeFilterSection.getSectionResult();
		ClientDTO selectedClient = this.clientsFilterSection.getSectionResult();

		AttributesInfo result = new AttributesInfo(attributeSearchInfo, selectedDataType, this.type, selectedClient);

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
