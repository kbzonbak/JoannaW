package bbr.b2b.portal.components.filter_section.fep;

import java.time.LocalDateTime;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.classes.wrappers.fep.FEPPeriodFilterSectionInfo;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrDateTimeField;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;

public class FEPPeriodFilterSection extends BbrVerticalSection<FEPPeriodFilterSectionInfo>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public FEPPeriodFilterSection(BbrUI parent)
	{
		super(parent);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long					serialVersionUID			= 7595700833881522285L;

	private BbrDateTimeField					datFld_SinceDate			= null;
	private BbrDateTimeField					datFld_UntilDate			= null;
	private BbrComboBox<SearchCriterion>	cbx_FilterDateCriteria	= null;
	private HorizontalLayout					pnlPeriodsSubsection		= null;
	private HorizontalLayout					pnl_FillSpace				= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		// Header
		Label lbl_Period = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_date_range"));
		lbl_Period.addStyleName("bbr-panel-space");
		lbl_Period.setWidth("200px");

		HorizontalLayout pnlHeader = new HorizontalLayout();
		pnlHeader.addStyleName("bbr-filter-label-header");
		pnlHeader.addComponent(lbl_Period);
		pnlHeader.setWidth("100%");

		// Sección Filtro
		Label lbl_Filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_filter"));
		lbl_Filter.setWidth("120px");

		this.cbx_FilterDateCriteria = this.getSearchCriterionDatesComboBox();

		HorizontalLayout pnlFilterDates = new HorizontalLayout();
		pnlFilterDates.setWidth("100%");
		pnlFilterDates.addComponents(lbl_Filter, this.cbx_FilterDateCriteria);
		pnlFilterDates.setExpandRatio(this.cbx_FilterDateCriteria, 1F);
		pnlFilterDates.addStyleName("bbr-panel-space");

		this.datFld_SinceDate = new BbrDateTimeField();
		this.datFld_UntilDate = new BbrDateTimeField();

		// Selección Períodos
		this.updatePeriodsSection(this.datFld_SinceDate, this.datFld_UntilDate);
		this.pnlPeriodsSubsection = this.getPeriodsPanel(this.datFld_SinceDate, this.datFld_UntilDate);
		this.pnlPeriodsSubsection.setVisible(false);

		// Panel Espacio
		this.pnl_FillSpace = new HorizontalLayout();
		this.pnl_FillSpace.setVisible(true);
		this.pnl_FillSpace.setHeight("32px");

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlHeader, pnlFilterDates, this.pnlPeriodsSubsection, pnl_FillSpace);
	}


	@Override
	public FEPPeriodFilterSectionInfo getSectionResult()
	{
		FEPPeriodFilterSectionInfo result = null;
		if (this.validateData() == null)
		{
			if (this.cbx_FilterDateCriteria.getSelectedValue().getId() == 0 || this.cbx_FilterDateCriteria.getSelectedValue().getId() == 1)
			{
				result = new FEPPeriodFilterSectionInfo(this.cbx_FilterDateCriteria.getSelectedValue(), this.datFld_SinceDate.getValue(), this.datFld_UntilDate.getValue());
			}
			else
			{
				result = new FEPPeriodFilterSectionInfo(this.cbx_FilterDateCriteria.getSelectedValue(), null, null);
			}
		}

		return result;
	}


	@Override
	public String validateData()
	{
		String result = null;

		if (this.cbx_FilterDateCriteria.getSelectedValue().getId() == 0 || this.cbx_FilterDateCriteria.getSelectedValue().getId() == 1)
		{
			if (this.datFld_SinceDate == null)
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "valid_since_date");
			}
			else if (this.datFld_UntilDate == null)
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "valid_until_date");
			}
			else 
			{
				LocalDateTime selectedStartDate = this.datFld_SinceDate.getOptionalValue().orElseGet(null);
				LocalDateTime selectedEndDate = this.datFld_UntilDate.getOptionalValue().orElseGet(null);
				
				if(selectedStartDate != null)
				{
					if(selectedEndDate != null)
					{
						if(selectedStartDate.isAfter(selectedEndDate))
						{
							result = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_GENERIC, "valid_range_date");	
						}
					}
					else
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "valid_until_date");
					}
				}
				else
				{
					result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "valid_since_date");
				}
			}
		}

		return result;
	}


	@Override
	public void setSectionData(Object data)
	{
		super.setSectionData(data);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	private void filter_changeHandler(ValueChangeEvent<SearchCriterion> e)
	{
		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime firstDayOfMonth = currentDate.withDayOfMonth(1);
		if (this.cbx_FilterDateCriteria.getSelectedValue().getId() == -1)
		{
			this.pnlPeriodsSubsection.setVisible(false);
			this.pnl_FillSpace.setVisible(true);
		}
		else
		{
			this.pnlPeriodsSubsection.setVisible(true);
			this.pnl_FillSpace.setVisible(false);
			this.datFld_SinceDate.setValue(firstDayOfMonth);
			this.datFld_UntilDate.setValue(currentDate);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private HorizontalLayout getPeriodsPanel(BbrDateTimeField sinceDate, BbrDateTimeField untilDate)
	{
		HorizontalLayout pnlSinceSubsection = new HorizontalLayout();

		Label lblSince = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "start_date"));

		pnlSinceSubsection = new HorizontalLayout();
		pnlSinceSubsection.addComponents(lblSince, sinceDate);
		pnlSinceSubsection.setSpacing(true);

		HorizontalLayout pnlUntilSubsection = new HorizontalLayout();

		Label lblUntil = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "end_date"));

		pnlUntilSubsection = new HorizontalLayout();
		pnlUntilSubsection.addComponents(lblUntil, untilDate);
		pnlUntilSubsection.setSpacing(true);

		HorizontalLayout result = new HorizontalLayout();
		result.setWidth("400px");
		result.addComponents(pnlSinceSubsection, new BbrHSeparator("10px"), pnlUntilSubsection);
		result.setExpandRatio(pnlUntilSubsection, 1F);
		result.setSpacing(true);
		result.addStyleName("bbr-panel-space");

		return result;
	}


	private void updatePeriodsSection(BbrDateTimeField sinceDate, BbrDateTimeField untilDate)
	{
		sinceDate.setParseErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_start_date"));
		sinceDate.setLocale(this.getBbrUIParent().getUser().getLocale());
		sinceDate.setDateFormat("dd-MM-yyyy");
		sinceDate.setResolution(DateTimeResolution.DAY);
		sinceDate.setWidth("125px");
		sinceDate.setHeight("35px");
		sinceDate.setTextFieldEnabled(false);

		untilDate.setParseErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_end_date"));
		untilDate.setLocale(this.getBbrUIParent().getUser().getLocale());
		untilDate.setDateFormat("dd-MM-yyyy");
		untilDate.setResolution(DateTimeResolution.DAY);
		untilDate.setWidth("125px");
		untilDate.setHeight("35px");
		untilDate.setTextFieldEnabled(false);
	}


	private BbrComboBox<SearchCriterion> getSearchCriterionDatesComboBox()
	{
		SearchCriterion[] searchDatesCriterions = FepUtils.getDatesCriteria();
		BbrComboBox<SearchCriterion> result = new BbrComboBox<SearchCriterion>(searchDatesCriterions);
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();
		result.addValueChangeListener((e) -> filter_changeHandler(e));
		result.setWidth("255px");
		result.setHeight("30px");
		result.addStyleName("bbr-filter-fields");
		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
