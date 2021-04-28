package bbr.b2b.portal.components.filters.fep;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.ArticleTypeInitParamDTO;
import bbr.b2b.fep.common.data.classes.ClientDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.classes.wrappers.fep.TemplatesSearchInfo;
import bbr.b2b.portal.components.filter_section.fep.ClientsFilterSection;
import bbr.b2b.portal.components.filter_section.fep.TemplatesFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class TemplatesFilter extends BbrFilterContainer implements Button.ClickListener
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long		serialVersionUID		= 3153326837988496037L;

	private TemplatesFilterSection	templatesFilterSection	= null;
	private ClientsFilterSection	clientsFilterSection	= null;
	private Button					btn_FilterSearch		= null;
	private String					type					= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public TemplatesFilter(BbrModule parentModule, String type)
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
			ArticleTypeInitParamDTO initParam = this.getInitParam();

			bbrFilter.setResultObject(initParam);

			dispatchBbrFilterEvent(bbrFilter);
		}

		else
		{
			// Faltan datos en el filtro seleccionado
			TemplatesFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	public void initializeView()
	{
		// Filtro Plantillas

		templatesFilterSection = new TemplatesFilterSection(this.getBbrUIParent());
		templatesFilterSection.initializeView();

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
		mainLayout.addComponents(templatesFilterSection, clientsFilterSection);
		mainLayout.addComponent(pnlSearchButton);
		mainLayout.addComponent(pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("235px");
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

		errorResult = this.templatesFilterSection.validateData();

		return errorResult;
	}

	private BbrFilterEvent getBbrFilterEventObject()
	{
		TemplatesSearchInfo templateSearchInfo = this.templatesFilterSection.getSectionResult();
		ClientDTO selectedClient = this.clientsFilterSection.getSectionResult();
		
		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);

		if (templateSearchInfo.getFilterState().getId() != 0)
		{
			result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES,
					"lbl_template", true) + templateSearchInfo.getValue());
		}
		else
		{
			result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES,
					"lbl_template", true) + templateSearchInfo.getFilterState().getDescription());
		}
		
		String clientCaption = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES,
				"lbl_client", false) + selectedClient.getName();
		
		result.setSecondaryCaption(clientCaption);

		return result;
	}

	private ArticleTypeInitParamDTO getInitParam()
	{
		TemplatesSearchInfo result = this.templatesFilterSection.getSectionResult();
		ClientDTO selectedClient = this.clientsFilterSection.getSectionResult();
		
		ArticleTypeInitParamDTO initParam = new ArticleTypeInitParamDTO();
		initParam.setActive(result.getActiveOption().getValue() ? result.getActiveOption().getValue() : null);
		initParam.setDescription((result.getFilterState().getId() == 1) ? result.getValue() : null);
		initParam.setClientname(selectedClient.getInternalname());
		
		// Esperar a que BE implemente la parte del cliente en el initParam
		
		initParam.setType(this.type);

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
