package bbr.b2b.portal.components.filters.fep;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.ArticleTypeByClientInitParamDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.classes.wrappers.fep.FEPTemplateByClientFilterSectionInfo;
import bbr.b2b.portal.components.filter_section.fep.TemplateByClientFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class AttributeStandardizerFilter extends BbrFilterContainer implements Button.ClickListener
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long				serialVersionUID				= 3153326837988496037L;

	private TemplateByClientFilterSection 	templateByFilterSection 		= null;
	private Button							btn_FilterSearch				= null;
	private String							type							= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public AttributeStandardizerFilter(BbrModule parentModule, String type)
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
			ArticleTypeByClientInitParamDTO initParam = this.getInitParam();

			bbrFilter.setResultObject(initParam);

			dispatchBbrFilterEvent(bbrFilter);
		}

		else
		{
			// Faltan datos en el filtro seleccionado
			AttributeStandardizerFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	public void initializeView()
	{
		// Filtro Plantillas

//		templatesFilterSection = new TemplatesFilterSection(this.getBbrUIParent());
//		templatesFilterSection.initializeView();
//
//		clientsFilterSection = new ClientsFilterSection(this.getBbrUIParent());
//		clientsFilterSection.initializeView();
		
		templateByFilterSection = new TemplateByClientFilterSection(this.getBbrUIParent());
		templateByFilterSection.initializeView();
		
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
		mainLayout.addComponents(templateByFilterSection);
		mainLayout.addComponent(pnlSearchButton);
		mainLayout.addComponent(pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("125px");
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

		errorResult = this.templateByFilterSection.validateData();

		return errorResult;
	}

	private BbrFilterEvent getBbrFilterEventObject()
	{
		FEPTemplateByClientFilterSectionInfo templateByClientInfo = this.templateByFilterSection.getSectionResult();
		
		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);
		
		String templateCaption = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES, "lbl_template", false) + templateByClientInfo.getTemplate().getDescription();
		
		result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES,
				"lbl_client", true) + templateByClientInfo.getSelectedClient().getName());
		
		result.setSecondaryCaption(templateCaption);
		
		return result;
	}

	private ArticleTypeByClientInitParamDTO getInitParam()
	{
		/*TemplatesSearchInfo result = this.templatesFilterSection.getSectionResult();
		ClientDTO selectedClient = this.clientsFilterSection.getSectionResult();
		
		ArticleTypeInitParamDTO initParam = new ArticleTypeInitParamDTO();
		initParam.setActive(result.getActiveOption().getValue() ? result.getActiveOption().getValue() : null);
		initParam.setDescription((result.getFilterState().getId() == 1) ? result.getValue() : null);
		initParam.setClientname(selectedClient.getInternalname());
		
		// Esperar a que BE implemente la parte del cliente en el initParam
		
		initParam.setType(this.type);*/
		
		FEPTemplateByClientFilterSectionInfo result = this.templateByFilterSection.getSectionResult();
		
		ArticleTypeByClientInitParamDTO initParam = new ArticleTypeByClientInitParamDTO();
		initParam.setClientname(result.getSelectedClient() != null
							? result.getSelectedClient().getInternalname() : null);
		initParam.setArticletypeid(result.getTemplate() != null && result.getTemplate().getId() > -1L
							? result.getTemplate().getId() 
							: -1L);

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
