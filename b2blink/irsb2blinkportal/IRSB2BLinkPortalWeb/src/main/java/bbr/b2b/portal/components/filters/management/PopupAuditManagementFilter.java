package bbr.b2b.portal.components.filters.management;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.classes.wrappers.management.PopupAuditFilterSectionInfo;
import bbr.b2b.portal.components.filter_section.management.PopupAuditManagementFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.AuditPopUpReportInitParamDTO;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class PopupAuditManagementFilter extends BbrFilterContainer implements Button.ClickListener
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long					serialVersionUID					= -1827773939301665354L;
	private PopupAuditManagementFilterSection	popupAuditManagementFilterSection	= null;
	private Button								btn_FilterSearch					= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public PopupAuditManagementFilter(BbrModule parentModule)
	{
		super(parentModule);
	}

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
			AuditPopUpReportInitParamDTO initParam = this.getInitParam();

			bbrFilter.setResultObject(initParam);

			dispatchBbrFilterEvent(bbrFilter);
		}

		else
		{
			// Faltan datos en el filtro seleccionado
			PopupAuditManagementFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	public void initializeView()
	{
		// Seccion Locales
		popupAuditManagementFilterSection = new PopupAuditManagementFilterSection(this.getBbrUIParent(), true, true, true);
		popupAuditManagementFilterSection.initializeView();

		// Boton de Busqueda
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
		mainLayout.addComponent(popupAuditManagementFilterSection);
		mainLayout.addComponent(pnlSearchButton);
		mainLayout.addComponent(pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("160px");
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

		if (this.popupAuditManagementFilterSection.validateData() != null)
		{
			errorResult = this.popupAuditManagementFilterSection.validateData();
		}

		return errorResult;
	}

	private BbrFilterEvent getBbrFilterEventObject()
	{
		PopupAuditFilterSectionInfo filter = this.popupAuditManagementFilterSection.getSectionResult();

		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);

		String year = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.USERS_RESOURCES, "col_app_year", true) + filter.getYear();
		String title = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.USERS_RESOURCES, "col_app_title", true) + filter.getTitle().getTitle();
		String action = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.USERS_RESOURCES, "col_app_action", true) + filter.getAction();

		result.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "app_publication")+FilterHeaderUtils.CAPTION_SUFIX);
		result.setSecondaryCaption(year + FilterHeaderUtils.CAPTION_PREFIX + title + FilterHeaderUtils.CAPTION_PREFIX + action);

		return result;
	}

	private AuditPopUpReportInitParamDTO getInitParam()
	{
		PopupAuditFilterSectionInfo sectionResult = this.popupAuditManagementFilterSection.getSectionResult();
		AuditPopUpReportInitParamDTO initParam = new AuditPopUpReportInitParamDTO();
		initParam.setAction(sectionResult.getAction());
		initParam.setPukey(sectionResult.getTitle().getPukey());
		initParam.setTitle(sectionResult.getTitle().getTitle());

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
