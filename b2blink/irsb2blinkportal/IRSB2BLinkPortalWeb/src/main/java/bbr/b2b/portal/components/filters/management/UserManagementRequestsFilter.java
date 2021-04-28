package bbr.b2b.portal.components.filters.management;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrModule;
// import cl.bbr.core.components.basics.BbrLineSeparator;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class UserManagementRequestsFilter extends BbrFilterContainer implements Button.ClickListener
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************

	private static final long			serialVersionUID	= 5712042403953778345L;

	private BbrComboBox<SearchCriterion>	cbx_Criteria		= null;
	private Button				btn_FilterSearch	= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public UserManagementRequestsFilter(BbrModule parentModule)
	{
		super(parentModule);
	}

	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	@Override
	public void buttonClick(ClickEvent event)
	{
		SearchCriterion selectedValue = this.cbx_Criteria.getSelectedValue();

		if (selectedValue != null)
		{
			BbrFilterEvent filterEvent = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);
			filterEvent.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "filter_by"));
			filterEvent.setSecondaryCaption("- " + selectedValue.getDescription());
			filterEvent.setResultObject(selectedValue);
			dispatchBbrFilterEvent(filterEvent);
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	public void initializeView()
	{
		Label lbl_FilterCriteriaHeader = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "criteria"));
		lbl_FilterCriteriaHeader.addStyleName("bbr-panel-space");
		lbl_FilterCriteriaHeader.setWidth("220px");

		HorizontalLayout pnlHeader = new HorizontalLayout();
		pnlHeader.addComponent(lbl_FilterCriteriaHeader);
		pnlHeader.addStyleName("bbr-filter-label-header");
		pnlHeader.setWidth("100%");

		Label lbl_filterBy = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "filter_by") + ":");
		lbl_filterBy.setWidth("120px");

		this.cbx_Criteria = this.getCriteriaComboBox();
		HorizontalLayout pnlFilterBy = new HorizontalLayout();

		pnlFilterBy.setWidth("100%");
		pnlFilterBy.addComponents(lbl_filterBy, this.cbx_Criteria);
		pnlFilterBy.setExpandRatio(this.cbx_Criteria, 1F);

		VerticalLayout pnlSearchCriteria = new VerticalLayout(pnlHeader, pnlFilterBy);
		pnlSearchCriteria.setHeight("70px");
		pnlSearchCriteria.setWidth("400px");
		pnlSearchCriteria.addStyleName("bbr-filter-sections");
		pnlSearchCriteria.addStyleName("bbr-panel-space");
		pnlSearchCriteria.setSpacing(false);

		// Bot�n de B�squeda
		this.btn_FilterSearch = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "generate_report"), this);
		this.btn_FilterSearch.setIcon(VaadinIcons.CHECK_CIRCLE_O);
		this.btn_FilterSearch.setStyleName("btn-filter-search");
		this.btn_FilterSearch.setClickShortcut(KeyCode.ENTER);

		VerticalLayout pnlGenerateReport = new VerticalLayout();
		pnlGenerateReport.setWidth("400px");
		pnlGenerateReport.addStyleName("bbr-panel-space");
		pnlGenerateReport.setSpacing(false);
		pnlGenerateReport.setMargin(false);
		pnlGenerateReport.addComponents(btn_FilterSearch);
		pnlGenerateReport.setComponentAlignment(btn_FilterSearch, Alignment.BOTTOM_RIGHT);

		VerticalLayout pnlFill = new VerticalLayout();

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName("bbr-filter-main-panel");
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.setSpacing(false);
		mainLayout.addComponents(pnlSearchCriteria, pnlGenerateReport, pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("110px");
		this.addComponent(mainLayout);

	}

	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private BbrComboBox<SearchCriterion> getCriteriaComboBox()
	{
		BbrComboBox<SearchCriterion> result = null;
		SearchCriterion[] searchCriteria = SearchFilterUtils.getInstance().getUserManagementRequestsSearchCriteria();

		result = new BbrComboBox<SearchCriterion>(searchCriteria);
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();
		result.addStyleName("bbr-filter-fields");
		result.setWidth("255px");
		result.setHeight("30px");

		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
}
