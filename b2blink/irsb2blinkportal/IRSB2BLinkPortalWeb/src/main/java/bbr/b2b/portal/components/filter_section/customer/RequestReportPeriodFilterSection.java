package bbr.b2b.portal.components.filter_section.customer;

import java.time.LocalDateTime;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils;
import bbr.b2b.portal.classes.wrappers.customer.RequestReportPeriodFilterSectionInfo;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.CustomerConstants;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrDateTimeField;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;

public class RequestReportPeriodFilterSection extends BbrVerticalSection<RequestReportPeriodFilterSectionInfo>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public RequestReportPeriodFilterSection(BbrUI parent)
	{
		super(parent);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long				serialVersionUID			= 7595700833881522285L;

	private BbrDateTimeField				datFld_SinceDate			= null;
	private BbrDateTimeField				datFld_UntilDate			= null;
	private BbrComboBox<SearchCriterion>	cbx_FilterDateCriteria		= null;
	private HorizontalLayout				pnlPeriodsSubsection		= null;
	private HorizontalLayout				pnlReferenceSubsection		= null;
	private HorizontalLayout				pnlRequestNumberSubsection	= null;
	private BbrTextField					txt_RequestNumber			= null;
	private BbrTextField					txt_ReferenceNumber			= null;
	
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
		Label lbl_Period = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "lbl_filters_request"));
		lbl_Period.addStyleName("bbr-panel-space");
		lbl_Period.setWidth("200px");

		HorizontalLayout pnlHeader = new HorizontalLayout();
		pnlHeader.addStyleName("bbr-filter-label-header");
		pnlHeader.addComponent(lbl_Period);
		pnlHeader.setWidth("100%");

		// Sección Filtro
		Label lbl_Filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "lbl_filter_by"));
		lbl_Filter.setWidth("120px");

		this.cbx_FilterDateCriteria = this.getSearchCriterionDatesComboBox();

		HorizontalLayout pnlFilterDates = new HorizontalLayout();
		pnlFilterDates.setWidth("100%");
		pnlFilterDates.addComponents(lbl_Filter, this.cbx_FilterDateCriteria);
		pnlFilterDates.setExpandRatio(this.cbx_FilterDateCriteria, 1F);
		pnlFilterDates.addStyleName("bbr-panel-space");

		this.datFld_SinceDate = new BbrDateTimeField();
		this.datFld_UntilDate = new BbrDateTimeField();

		LocalDateTime currentDate = LocalDateTime.now();

		this.datFld_SinceDate.setValue(currentDate);
		this.datFld_UntilDate.setValue(currentDate);

		// Selección Períodos
		this.updatePeriodsSection(this.datFld_SinceDate, this.datFld_UntilDate);
		this.pnlPeriodsSubsection = this.getPeriodsPanel(this.datFld_SinceDate, this.datFld_UntilDate);
		this.pnlPeriodsSubsection.setVisible(true);

		// Selección Referencia
		this.pnlReferenceSubsection = this.getReferencePanel();
		this.pnlReferenceSubsection.setVisible(false);

		// Selección Numero Solicitud
		this.pnlRequestNumberSubsection = this.getRequestNumberPanel();
		this.pnlRequestNumberSubsection.setVisible(false);

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlHeader, pnlFilterDates, this.pnlPeriodsSubsection, pnlReferenceSubsection, pnlRequestNumberSubsection);

	}

	@Override
	public RequestReportPeriodFilterSectionInfo getSectionResult()
	{
		RequestReportPeriodFilterSectionInfo result = null;
		if (this.validateData() == null)
		{
			if (this.cbx_FilterDateCriteria.getSelectedValue().getId() == CustomerConstants.CREATION_DATE_FILTER_VALUE)
			{
				result = new RequestReportPeriodFilterSectionInfo(this.cbx_FilterDateCriteria.getSelectedValue(), this.datFld_SinceDate.getValue(), this.datFld_UntilDate.getValue(), null, null);
			}
			else if (this.cbx_FilterDateCriteria.getSelectedValue().getId() == CustomerConstants.REQUEST_NUMBER_FILTER_VALUE)
			{
				result = new RequestReportPeriodFilterSectionInfo(this.cbx_FilterDateCriteria.getSelectedValue(), null, null, null, this.txt_RequestNumber);
			}
			else if (this.cbx_FilterDateCriteria.getSelectedValue().getId() == CustomerConstants.REFERENCE_NUMBER_FILTER_VALUE)
			{
				result = new RequestReportPeriodFilterSectionInfo(this.cbx_FilterDateCriteria.getSelectedValue(), null, null, this.txt_ReferenceNumber, null);
			}
		}

		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;

		if (this.cbx_FilterDateCriteria.getSelectedValue().getId() == CustomerConstants.CREATION_DATE_FILTER_VALUE)
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

				if (selectedStartDate != null)
				{
					if (selectedEndDate != null)
					{
						if (selectedStartDate.isAfter(selectedEndDate))
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
		else if (this.cbx_FilterDateCriteria.getSelectedValue().getId() == CustomerConstants.REQUEST_NUMBER_FILTER_VALUE)
		{
			if (this.txt_RequestNumber == null || txt_RequestNumber.getValue().length() == 0)
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "filter_order_number_required");
			}
			else if (!BbrUtils.isLongNumber(txt_RequestNumber.getValue()))
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "filter_order_number_required_number");
			}
		}
		else if (this.cbx_FilterDateCriteria.getSelectedValue().getId() == CustomerConstants.REFERENCE_NUMBER_FILTER_VALUE)
		{
			if (this.txt_ReferenceNumber == null || txt_ReferenceNumber.getValue().length() == 0)
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "filter_order_number_required");
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
		if (this.cbx_FilterDateCriteria.getSelectedValue().getId() == CustomerConstants.REFERENCE_NUMBER_FILTER_VALUE)
		{
			this.pnlPeriodsSubsection.setVisible(false);
			this.pnlRequestNumberSubsection.setVisible(false);
			this.pnlReferenceSubsection.setVisible(true);
		}
		if (this.cbx_FilterDateCriteria.getSelectedValue().getId() == CustomerConstants.REQUEST_NUMBER_FILTER_VALUE)
		{
			this.pnlPeriodsSubsection.setVisible(false);
			this.pnlReferenceSubsection.setVisible(false);
			this.pnlRequestNumberSubsection.setVisible(true);
		}
		else if (this.cbx_FilterDateCriteria.getSelectedValue().getId() == CustomerConstants.CREATION_DATE_FILTER_VALUE)
		{
			LocalDateTime currentDate = LocalDateTime.now();
			// LocalDateTime firstDayOfMonth = currentDate.withDayOfMonth(1);

			this.pnlRequestNumberSubsection.setVisible(false);
			this.pnlReferenceSubsection.setVisible(false);
			this.pnlPeriodsSubsection.setVisible(true);
			this.datFld_SinceDate.setValue(currentDate);
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

	private HorizontalLayout getReferencePanel()
	{
		Label lblReferenceValue = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "lbl_value"));
		lblReferenceValue.setWidth("120px");

		if (this.txt_ReferenceNumber == null)
		{
			// No es necesario crear el TextField cada vez que se cambie el filtro de OC

			this.txt_ReferenceNumber = new BbrTextField();
			this.txt_ReferenceNumber.setMaxLength(25);
			this.txt_ReferenceNumber.setRestrict("a-z,A-Z,0-9,_");
			this.txt_ReferenceNumber.addStyleName("bbr-filter-fields");
			this.txt_ReferenceNumber.setWidth("255px");
			this.txt_ReferenceNumber.setHeight("30px");
		}

		this.txt_ReferenceNumber.setValue("");

		HorizontalLayout result = new HorizontalLayout();
		result.addComponents(lblReferenceValue, txt_ReferenceNumber);
		result.setSpacing(true);
		result.setExpandRatio(txt_ReferenceNumber, 1F);
		return result;
	}

	private HorizontalLayout getRequestNumberPanel()
	{
		Label lblRequestValue = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "lbl_value"));
		lblRequestValue.setWidth("120px");

		if (this.txt_RequestNumber == null)
		{
			// No es necesario crear el TextField cada vez que se cambie el filtro de OC

			this.txt_RequestNumber = new BbrTextField();
			this.txt_RequestNumber.setMaxLength(25);
			this.txt_RequestNumber.setRestrict(RestrictRange.NUMBERS);
			this.txt_RequestNumber.addStyleName("bbr-filter-fields");
			this.txt_RequestNumber.setWidth("255px");
			this.txt_RequestNumber.setHeight("30px");
		}

		this.txt_RequestNumber.setValue("");

		HorizontalLayout result = new HorizontalLayout();
		result.addComponents(lblRequestValue, txt_RequestNumber);
		result.setSpacing(true);
		result.setExpandRatio(txt_RequestNumber, 1F);
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
		SearchCriterion[] searchDatesCriterions = CustomerSearchCriteriaFilterUtils.getRequestReferencePeriodFilterCriteria();
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
