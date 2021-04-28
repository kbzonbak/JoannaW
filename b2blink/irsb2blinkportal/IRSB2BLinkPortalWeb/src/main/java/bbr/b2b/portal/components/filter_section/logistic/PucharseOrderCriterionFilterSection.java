package bbr.b2b.portal.components.filter_section.logistic;

import static bbr.b2b.portal.constants.logistic.ConstantsPucharseOrderSearchCriteria.*;

import java.time.LocalDateTime;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.logistic.buyorders.data.dto.OrderStateTypeDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderStateTypeResultDTO;
import bbr.b2b.logistic.rest.client.CustomerLogisticRestFulWSClient;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.logistic.PucharseOrderSearchFilterUtils;
import bbr.b2b.portal.classes.wrappers.logistic.PucharseOrderCriterion;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrDateTimeField;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;

public class PucharseOrderCriterionFilterSection extends BbrVerticalSection<PucharseOrderCriterion>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 4279165028171908839L;

	public PucharseOrderCriterionFilterSection(BbrUI parent)
	{
		super(parent);
		this.clientCustomerWS = CustomerLogisticRestFulWSClient.getInstance(BbrAppConstants.CUSTOMER_URL);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static CustomerLogisticRestFulWSClient	clientCustomerWS	= null;

	private BbrComboBox<SearchCriterion>			cbx_FilterType		= null;
	private BbrComboBox<OrderStateTypeDTO>			cbx_OrderStateType	= null;

	private HorizontalLayout						cmp_FilterFields	= null;

	private BbrTextField							txt_OrderNumber		= null;
	private BbrTextField							txt_VoucherNumber	= null;
	private BbrDateTimeField						datFld_SinceDate	= null;
	private BbrDateTimeField						datFld_UntilDate	= null;

	private VerticalLayout							pnlContent			= null;

	private enum EnumDateType
	{
		EMITTED_DATE, RECEPTION_B2B_DATE
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{

		// Label Título
		Label lbl_ProviderHeader = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "criteria"));
		lbl_ProviderHeader.addStyleName("bbr-panel-space");
		lbl_ProviderHeader.setWidth("220px");

		HorizontalLayout pnlHeader = new HorizontalLayout();
		pnlHeader.addStyleName("bbr-filter-label-header");
		pnlHeader.addComponents(lbl_ProviderHeader);
		pnlHeader.setWidth("100%");

		// Filtro OC
		Label lblOCFilter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "filter_oc"));
		lblOCFilter.setWidth("120px");

		cbx_FilterType = this.getSearchCriterionComboBox();

		HorizontalLayout pnlSearchCriteria = new HorizontalLayout();
		pnlSearchCriteria.setWidth("100%");
		pnlSearchCriteria.addComponents(lblOCFilter, cbx_FilterType);
		pnlSearchCriteria.setExpandRatio(cbx_FilterType, 1F);
		pnlSearchCriteria.addStyleName("bbr-panel-space");

		pnlContent = new VerticalLayout();
		pnlContent.addComponents(pnlHeader, pnlSearchCriteria);
		pnlContent.setWidth("100%");
		pnlContent.setSpacing(false);
		pnlContent.setMargin(false);

		this.setWidth("400px");
		this.setHeight("95px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponent(pnlContent);
	}

	@Override
	public PucharseOrderCriterion getSectionResult()
	{
		OrderStateTypeDTO orderStatus 	= 	(cbx_OrderStateType != null) ? cbx_OrderStateType.getValue() : null;
		LocalDateTime selectedStartDate = 	(datFld_SinceDate != null) ? datFld_SinceDate.getValue() : null;
		LocalDateTime selectedEndDate 	= 	(datFld_UntilDate != null) ? datFld_UntilDate.getValue() : null;
		SearchCriterion searchCriterion = 	(cbx_FilterType != null) ? cbx_FilterType.getSelectedValue() : null;

		Long numberValue = null;

		if ((txt_OrderNumber != null) && (txt_OrderNumber.getValue().length() > 0))
		{
			numberValue = Long.parseLong(txt_OrderNumber.getValue());
		}
		else if ((txt_VoucherNumber != null) && (txt_VoucherNumber.getValue().length() > 0))
		{
			numberValue = Long.parseLong(txt_VoucherNumber.getValue());
		}

		PucharseOrderCriterion result = new PucharseOrderCriterion(searchCriterion.getId(), numberValue, orderStatus, selectedStartDate, selectedEndDate);

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
				case EMITTED_DATE:
				case RECEPTION_B2B_DATE:

					LocalDateTime selectedStartDate = (this.datFld_SinceDate != null) ? this.datFld_SinceDate.getValue() : null;
					LocalDateTime selectedEndDate = (this.datFld_UntilDate != null) ? this.datFld_UntilDate.getValue() : null;

					if (selectedStartDate == null)
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "valid_since_date");
					}
					else if (selectedEndDate == null)
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "valid_until_date");
					}
					else if (selectedStartDate.toLocalDate().isAfter(selectedEndDate.toLocalDate()))
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "valid_range_date");
					}

					break;

				case ORDER_NUMBER:

					if (txt_OrderNumber == null || txt_OrderNumber.getValue().length() == 0)
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "filter_order_number_required");
					}
					else if (!BbrUtils.isLongNumber(txt_OrderNumber.getValue()))
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "filter_order_number_required_number");
					}

					break;

				case ORDER_STATE:

					if (cbx_OrderStateType == null || cbx_OrderStateType.getSelectedValue() == null)
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "filter_order_status_required");
					}

					break;

				default:
					break;
			}
		}
		else
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "filter_type_required");
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
		SearchCriterion[] searcCriterions = PucharseOrderSearchFilterUtils.getInstance().getPucharseOrdersCriteria();
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

		if (cbx_FilterType.getSelectedValue().getId() != 0)
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
				case VALID_ORDERS:
					break;
				case ORDER_STATE:
					result = this.getOrderStateTypePanel();
					break;
				case ORDER_NUMBER:
					result = this.getOrderNumberPanel();
					break;
				case EMITTED_DATE:
					result = this.getPeriodsSelectionPanel(EnumDateType.EMITTED_DATE);
					break;
				case RECEPTION_B2B_DATE:
					result = this.getPeriodsSelectionPanel(EnumDateType.RECEPTION_B2B_DATE);
					break;
				default:
					break;
			}
		}
		return result;
	}

	private HorizontalLayout getOrderNumberPanel()
	{
		Label lbl_OrderNumber = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "order_number"));
		lbl_OrderNumber.setWidth("120px");

		if (this.txt_OrderNumber == null)
		{
			// DSU No es necesario crear el TextField cada vez que se
			// cambie el filtro de OC

			this.txt_OrderNumber = new BbrTextField();
			this.txt_OrderNumber.setMaxLength(25);
			this.txt_OrderNumber.setRestrict(RestrictRange.NUMBERS);
			this.txt_OrderNumber.addStyleName("bbr-filter-fields");
			this.txt_OrderNumber.setWidth("255px");
			this.txt_OrderNumber.setHeight("30px");
		}

		this.txt_OrderNumber.setValue("");

		HorizontalLayout result = new HorizontalLayout();
		result.addComponents(lbl_OrderNumber, txt_OrderNumber);
		result.setSpacing(true);
		result.setExpandRatio(txt_OrderNumber, 1F);
		return result;
	}

	private HorizontalLayout getVoucherNumberPanel()
	{
		Label lbl_VoucherNumber = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "voucher_number"));
		lbl_VoucherNumber.setWidth("120px");

		if (this.txt_VoucherNumber == null)
		{
			// DSU No es necesario crear el TextField cada vez que se
			// cambie el filtro de OC

			this.txt_VoucherNumber = new BbrTextField();
			this.txt_VoucherNumber.setMaxLength(25);
			this.txt_VoucherNumber.setRestrict(RestrictRange.NUMBERS);
			this.txt_VoucherNumber.addStyleName("bbr-filter-fields");
			this.txt_VoucherNumber.setWidth("255px");
			this.txt_VoucherNumber.setHeight("30px");
		}

		this.txt_VoucherNumber.setValue("");

		HorizontalLayout result = new HorizontalLayout();
		result.addComponents(lbl_VoucherNumber, txt_VoucherNumber);
		result.setSpacing(true);
		result.setExpandRatio(txt_VoucherNumber, 1F);
		return result;
	}

	private HorizontalLayout getOrderStateTypePanel()
	{
		if (this.cbx_OrderStateType == null)
		{
			// DSU No es necesario crear el Combobox cada vez que se
			// cambie el filtro de OC

			OrderStateTypeDTO[] orderStateTypes = this.getOrdersStatesTypes();
			if (orderStateTypes != null && orderStateTypes.length > 0)
			{
				this.cbx_OrderStateType = new BbrComboBox<OrderStateTypeDTO>(orderStateTypes);
				this.cbx_OrderStateType.selectFirstItem();
			}
			else
			{
				this.cbx_OrderStateType = new BbrComboBox<OrderStateTypeDTO>();
			}

			this.cbx_OrderStateType.setItemCaptionGenerator(OrderStateTypeDTO::getName);
			this.cbx_OrderStateType.setTextInputAllowed(false);
			this.cbx_OrderStateType.setEmptySelectionAllowed(false);
			this.cbx_OrderStateType.addStyleName("bbr-filter-fields");
			this.cbx_OrderStateType.setWidth("255px");
			this.cbx_OrderStateType.setHeight("30px");
		}

		Label lbl_OrderStateType = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "order_status"));
		lbl_OrderStateType.setWidth("120px");

		HorizontalLayout result = new HorizontalLayout();
		result.addComponents(lbl_OrderStateType, this.cbx_OrderStateType);
		return result;
	}

	private HorizontalLayout getPeriodsSelectionPanel(EnumDateType dateType)
	{
		this.updatePeriodsSection(dateType);

		Label lblSince = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "start_date"));
		HorizontalLayout pnlSinceSubsection = new HorizontalLayout();
		pnlSinceSubsection.addComponents(lblSince, datFld_SinceDate);
		pnlSinceSubsection.setSpacing(true);

		Label lblUntil = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "end_date"));
		HorizontalLayout pnlUntilSubsection = new HorizontalLayout();
		pnlUntilSubsection.addComponents(lblUntil, datFld_UntilDate);
		pnlUntilSubsection.setSpacing(true);

		HorizontalLayout result = new HorizontalLayout();
		result.setWidth("400px");
		result.addComponents(pnlSinceSubsection, new BbrHSeparator("10px"), pnlUntilSubsection);
		result.setExpandRatio(pnlUntilSubsection, 1F);
		result.setSpacing(true);
		result.addStyleName("bbr-panel-space");
		return result;
	}

	private void updatePeriodsSection(EnumDateType dateType)
	{
		LocalDateTime sinceDate = LocalDateTime.now();
		LocalDateTime untilDate = LocalDateTime.now();

		if (EnumDateType.EMITTED_DATE == dateType)
		{
			// Desde el primer día del mes actual
			sinceDate = sinceDate.withDayOfMonth(1);
		}
		else if (EnumDateType.RECEPTION_B2B_DATE == dateType)
		{
			// Hasta el mismo día del próximo mes
			untilDate = untilDate.plusMonths(1);
		}

		if (this.datFld_SinceDate == null)
		{
			this.datFld_SinceDate = new BbrDateTimeField();
			this.datFld_SinceDate.setParseErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_start_date"));
			this.datFld_SinceDate.setLocale(this.getBbrUIParent().getUser().getLocale());
			this.datFld_SinceDate.setDateFormat("dd-MM-yyyy");
			this.datFld_SinceDate.setResolution(DateTimeResolution.DAY);
			this.datFld_SinceDate.setWidth("125px");
			this.datFld_SinceDate.setHeight("30px");
			this.datFld_SinceDate.setTextFieldEnabled(false);
		}

		if (this.datFld_UntilDate == null)
		{
			this.datFld_UntilDate = new BbrDateTimeField();
			this.datFld_UntilDate.setParseErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_end_date"));
			this.datFld_UntilDate.setLocale(this.getBbrUIParent().getUser().getLocale());
			this.datFld_UntilDate.setDateFormat("dd-MM-yyyy");
			this.datFld_UntilDate.setResolution(DateTimeResolution.DAY);
			this.datFld_UntilDate.setWidth("125px");
			this.datFld_UntilDate.setHeight("30px");
			this.datFld_UntilDate.setTextFieldEnabled(false);
		}

		this.datFld_SinceDate.setValue(sinceDate);
		this.datFld_UntilDate.setValue(untilDate);
	}

	private OrderStateTypeDTO[] getOrdersStatesTypes()
	{
		OrderStateTypeDTO[] result = null;
		try
		{
			OrderStateTypeResultDTO orderStateTypeResult = PucharseOrderCriterionFilterSection.clientCustomerWS.getOrderStateTypesWS();

			BbrError error = ErrorsMngr.getInstance().getError(orderStateTypeResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				result = orderStateTypeResult.getOrderstatetype();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
}
