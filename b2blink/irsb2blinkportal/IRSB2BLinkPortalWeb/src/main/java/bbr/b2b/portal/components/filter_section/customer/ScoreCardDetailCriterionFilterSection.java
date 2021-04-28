package bbr.b2b.portal.components.filter_section.customer;

import static bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils.CLIENT;
import static bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils.ORDER_NUMBER;
import static bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils.ORDER_STATE;
import static bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils.SHIPPING_DATE;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.HasI18n;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils;
import bbr.b2b.portal.classes.wrappers.customer.CustomerCriterionDTO;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
import bbr.b2b.portal.components.utils.customer.FindSite;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.customer.CustomerServiceConstants;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import bbr.esb.services.webservices.facade.ScoreCardManagerServer;
import bbr.esb.services.webservices.facade.ScoreCardManagerServerService;
import bbr.esb.services.webservices.facade.ScoreCardSiteFilterDTO;
import bbr.esb.services.webservices.facade.SiteDTO;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrDateTimeField;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;

public class ScoreCardDetailCriterionFilterSection extends BbrVerticalSection<CustomerCriterionDTO> implements HasI18n
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long				serialVersionUID	= 4279165028171908839L;
	private final int						MAX_SITES_SEARCH	= 10;
	// Components
	private HorizontalLayout				pnlSearchPanel		= null;
	private Button							btn_SearchCompany	= null;
	private String							stateSelected		= null;
	private BbrComboBox<SearchCriterion>	cbx_FilterType		= null;
	private HorizontalLayout				cmp_FilterFields	= null;
	private BbrTextField					txt_OrderNumber		= null;
	private BbrDateTimeField				datFld_SinceDate	= null;
	private BbrDateTimeField				datFld_UntilDate	= null;
	private VerticalLayout					pnlContent			= null;
	private BbrComboBox<SearchCriterion>	cbx_OrderState		= null;
	private BbrComboBox<SiteDTO>			cbx_Sites			= null;
	// Variables
	private ScoreCardSiteFilterDTO			resultService		= new ScoreCardSiteFilterDTO();
	private List<SiteDTO>					listOfSites			= new ArrayList<>();
	private CompanyDataDTO					selectedCompany		= null;
	private List<SiteDTO>					maxSitesList		= new ArrayList<>();
	private boolean							refreshSites		= false;

	private enum EnumDateType
	{
		EMITTED_DATE, DELIVERY_DATE
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public ScoreCardDetailCriterionFilterSection(BbrUI parent, CompanyDataDTO selectedCompany, String stateSelected)
	{
		super(parent);
		this.selectedCompany = selectedCompany;
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

		this.getSitesByService();

	}

	private void getSitesByService()
	{
		this.resultService = this.executeService(this.getBbrUIParent());
		if (this.listOfSites.size() > 0)
		{
			this.listOfSites = new ArrayList<>();
		}
		this.listOfSites.addAll(this.resultService.getSites());
		this.refreshSites = true;
	}

	@Override
	public CustomerCriterionDTO getSectionResult()
	{
		SearchCriterion searchCriterion = (cbx_FilterType != null) ? cbx_FilterType.getSelectedValue() : null;

		SearchCriterion criterion = cbx_FilterType.getSelectedValue();
		SiteDTO selectedSite = null;
		LocalDateTime selectedStartDate = null;
		LocalDateTime selectedEndDate = null;
		SearchCriterion orderState = null;
		Long orderNumber = null;

		if (criterion != null)
		{
			switch (criterion.getId())
			{
				case CLIENT:
					selectedSite = (cbx_Sites != null) ? cbx_Sites.getSelectedValue() : null;
					break;
				case SHIPPING_DATE:
					selectedStartDate = (datFld_SinceDate != null) ? datFld_SinceDate.getValue() : null;
					selectedEndDate = (datFld_UntilDate != null) ? datFld_UntilDate.getValue() : null;
					break;
				case ORDER_NUMBER:
					orderNumber = (txt_OrderNumber != null && txt_OrderNumber.getValue().length() > 0) ? Long.parseLong(txt_OrderNumber.getValue()) : null;
					break;
				case ORDER_STATE:
					orderState = (cbx_OrderState != null) ? cbx_OrderState.getSelectedValue() : null;
					break;
			}
		}

		CustomerCriterionDTO result = new CustomerCriterionDTO(searchCriterion, orderNumber, selectedStartDate, selectedEndDate, orderState, selectedSite);

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
				case SHIPPING_DATE:
					LocalDateTime selectedStartDate = (this.datFld_SinceDate != null) ? this.datFld_SinceDate.getValue() : null;
					selectedStartDate = selectedStartDate.withHour(0).withMinute(0).withSecond(0);
					LocalDateTime selectedEndDate = (this.datFld_UntilDate != null) ? this.datFld_UntilDate.getValue() : null;
					selectedEndDate = selectedEndDate.withHour(23).withMinute(0).withSecond(0);
					int maxDaysRange = 3;
					if (selectedStartDate == null)
					{
						result = this.getI18n("valid_since_date");
					}
					else if (selectedEndDate == null)
					{
						result = this.getI18n("valid_until_date");
					}
					else if (selectedStartDate.isAfter(selectedEndDate))
					{
						result = this.getI18n("valid_range_date");
					}
					else if (selectedEndDate.isAfter(selectedStartDate.plusDays(maxDaysRange + 1)))
					{
						result = this.getI18n("valid_max_range_date", String.valueOf(maxDaysRange));
					}

					break;

				case ORDER_NUMBER:
					if (txt_OrderNumber == null || txt_OrderNumber.getValue().length() == 0)
					{
						result = this.getI18n("filter_order_number_required");
					}
					else if (!BbrUtils.isLongNumber(txt_OrderNumber.getValue()))
					{
						result = this.getI18n("filter_order_number_required_number");
					}

					break;

				case CLIENT:
					if (this.cbx_Sites == null || this.cbx_Sites.getSelectedValue() == null || this.cbx_Sites.getSelectedValue().getId() == null)
					{
						result = this.getI18n("filter_clientsite_required");
					}
					break;
				case ORDER_STATE:
					if (this.cbx_OrderState == null || this.cbx_OrderState.getSelectedValue() == null)
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

	private void showSearchPnl(boolean show)
	{
		if (this.btn_SearchCompany == null)
		{
			if (show && this.listOfSites.size() > MAX_SITES_SEARCH)
			{
				this.btn_SearchCompany = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "search_site"));
				this.btn_SearchCompany.addStyleName("link-filter-button");
				this.btn_SearchCompany.addClickListener(this::btn_SearchSite_clickHandler);

				this.pnlSearchPanel.setWidth("160px");
				this.pnlSearchPanel.addComponent(btn_SearchCompany);
				this.pnlSearchPanel.addStyleName("search-panel");
			}

		}
		else if (!show)
		{
			this.btn_SearchCompany = null;
			this.pnlSearchPanel.removeAllComponents();
		}
	}

	private void btn_SearchSite_clickHandler(ClickEvent e)
	{
		if (e != null)
		{
			FindSite winFindSite = new FindSite(this.getBbrUIParent(), this.listOfSites);
			winFindSite.initializeView();
			winFindSite.addBbrEventListener(this::siteSelectedHandler);
			winFindSite.open(true);
		}
	}

	private void siteSelectedHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getResultObject() != null)
		{
			SiteDTO company = (SiteDTO) bbrEvent.getResultObject();

			this.updateSites(company);
		}
	}

	private void updateSites(SiteDTO company)
	{

		if (this.maxSitesList.contains(company))
		{
			this.cbx_Sites.setSelectedItem(company);
		}
		else
		{
			this.maxSitesList.add(0, company);
			this.cbx_Sites.setSelectedItem(company);
		}
	}

	private BbrComboBox<SearchCriterion> getSearchCriterionComboBox()
	{
		SearchCriterion[] searcCriterions = CustomerSearchCriteriaFilterUtils.getInstance().getPucharseOrdersCriteria();
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
			boolean showPanel = false;
			switch (criterion.getId())
			{
				case CLIENT:
					showPanel = true;
					result = this.getSitesPanel();
					break;
				case SHIPPING_DATE:
					result = this.getPeriodsSelectionPanel(EnumDateType.DELIVERY_DATE);
					break;
				case ORDER_NUMBER:
					result = this.getOrderNumberPanel();
					break;
				case ORDER_STATE:
					result = this.getOrderStatePanel();
					break;
				default:
					result = new HorizontalLayout();
					result.setSizeFull();
					break;
			}
			this.showSearchPnl(showPanel);
		}

		return result;
	}

	private HorizontalLayout getOrderStatePanel()
	{
		if (this.cbx_OrderState == null)
		{
			SearchCriterion[] orderStateTypes = CustomerSearchCriteriaFilterUtils.getInstance().getOrderStatesCriteria();

			if (orderStateTypes != null && orderStateTypes.length > 0)
			{
				this.cbx_OrderState = new BbrComboBox<SearchCriterion>(orderStateTypes);
				if (this.stateSelected != null)
				{
					for (int i = 0; i < orderStateTypes.length; i++)
					{
						if (orderStateTypes[i].getDescription().equals(this.stateSelected))
						{
							this.cbx_OrderState.selectItem(i);
						}
					}
				}
				else
				{
					this.cbx_OrderState.selectFirstItem();
				}
			}
			else
			{
				this.cbx_OrderState = new BbrComboBox<SearchCriterion>();
			}

			this.cbx_OrderState.setItemCaptionGenerator(SearchCriterion::getDescription);
			this.cbx_OrderState.setTextInputAllowed(false);
			this.cbx_OrderState.setEmptySelectionAllowed(false);
			this.cbx_OrderState.addStyleName("bbr-filter-fields");
			this.cbx_OrderState.setWidth("255px");
			this.cbx_OrderState.setHeight("30px");
		}

		BbrHInputFieldContainer result = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "value"))
				.withLabelWidth("120px")
				.withComponent(this.cbx_OrderState)
				.build();
		return result;
	}

	private HorizontalLayout getSitesPanel()
	{
		if (this.cbx_Sites == null || this.refreshSites)
		{
			if (this.listOfSites != null && this.listOfSites.size() > 0)
			{
				this.cbx_Sites = new BbrComboBox<SiteDTO>();
				if (this.listOfSites.size() > MAX_SITES_SEARCH)
				{
					this.maxSitesList = this.listOfSites.stream()
							.sorted(Comparator.comparing(SiteDTO::getName))
							.limit(MAX_SITES_SEARCH)
							.collect(Collectors.toList());
					this.cbx_Sites.setItems(this.maxSitesList);
				}
				else
				{
					this.cbx_Sites.setItems(this.listOfSites);
				}
				this.cbx_Sites.selectFirstItem();
			}
			else
			{
				this.cbx_Sites = new BbrComboBox<SiteDTO>();
				SiteDTO empty = new SiteDTO();
				empty.setRetailname(this.getI18n("no_sites_by_user"));
				this.cbx_Sites.setItems(empty);
				this.cbx_Sites.selectFirstItem();
			}
			this.cbx_Sites.setItemCaptionGenerator(SiteDTO::getRetailname);
			this.cbx_Sites.setTextInputAllowed(false);
			this.cbx_Sites.setEmptySelectionAllowed(false);
			this.cbx_Sites.addStyleName("bbr-filter-fields");
			this.cbx_Sites.setWidth("255px");
			this.cbx_Sites.setHeight("30px");
		}

		BbrHInputFieldContainer result = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "value"))
				.withLabelWidth("120px")
				.withComponent(this.cbx_Sites)
				.build();
		return result;
	}

	private HorizontalLayout getOrderNumberPanel()
	{
		if (this.txt_OrderNumber == null)
		{
			this.txt_OrderNumber = new BbrTextField();
			this.txt_OrderNumber.setMaxLength(10);
			this.txt_OrderNumber.setRestrict(RestrictRange.NUMBERS);
			this.txt_OrderNumber.addStyleName("bbr-filter-fields");
			this.txt_OrderNumber.setWidth("255px");
			this.txt_OrderNumber.setHeight("30px");
		}

		this.txt_OrderNumber.setValue("");

		BbrHInputFieldContainer result = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "value"))
				.withLabelWidth("120px")
				.withComponent(this.txt_OrderNumber)
				.build();
		return result;

	}

	private HorizontalLayout getPeriodsSelectionPanel(EnumDateType dateType)
	{
		this.updatePeriodsSection();

		BbrHInputFieldContainer pnlSinceSubsection = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_GENERIC, "start_date"))
				.withLabelWidth("30px")
				.withWidth("185px")
				.withComponent(datFld_SinceDate)
				.build();

		BbrHInputFieldContainer pnlUntilSubsection = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_GENERIC, "end_date"))
				.withLabelWidth("30px")
				.withWidth("125px")
				.withComponent(datFld_UntilDate)
				.build();

		HorizontalLayout result = new HorizontalLayout();
		result.setWidth("400px");
		result.addComponents(pnlSinceSubsection, new BbrHSeparator("10px"), pnlUntilSubsection);
		result.setExpandRatio(pnlUntilSubsection, 1F);
		result.setSpacing(true);
		result.addStyleName("bbr-panel-space");
		return result;
	}

	private void updatePeriodsSection()
	{
		LocalDateTime sinceDate = LocalDateTime.now();
		LocalDateTime untilDate = LocalDateTime.now();

		if (this.datFld_SinceDate == null)
		{
			this.datFld_SinceDate = new BbrDateTimeField();
			this.datFld_SinceDate.setParseErrorMessage(this.getI18n("valid_start_date"));
			this.datFld_SinceDate.setLocale(this.getBbrUIParent().getUser().getLocale());
			this.datFld_SinceDate.setDateFormat("dd-MM-yyyy");
			this.datFld_SinceDate.setResolution(DateTimeResolution.DAY);
			this.datFld_SinceDate.setRangeStart(LocalDateTime.now().minusDays(3));
			this.datFld_SinceDate.setRangeEnd(LocalDateTime.now().plusDays(1));
			this.datFld_SinceDate.setWidth("125px");
			this.datFld_SinceDate.setHeight("30px");
			this.datFld_SinceDate.setTextFieldEnabled(false);
		}

		if (this.datFld_UntilDate == null)
		{
			this.datFld_UntilDate = new BbrDateTimeField();
			this.datFld_UntilDate.setParseErrorMessage(this.getI18n("valid_end_date"));
			this.datFld_UntilDate.setLocale(this.getBbrUIParent().getUser().getLocale());
			this.datFld_UntilDate.setDateFormat("dd-MM-yyyy");
			this.datFld_UntilDate.setResolution(DateTimeResolution.DAY);
			this.datFld_UntilDate.setRangeEnd(LocalDateTime.now().plusDays(1));
			this.datFld_UntilDate.setRangeStart(LocalDateTime.now().minusDays(3));
			this.datFld_UntilDate.setWidth("125px");
			this.datFld_UntilDate.setHeight("30px");
			this.datFld_UntilDate.setTextFieldEnabled(false);
		}

		this.datFld_SinceDate.setValue(sinceDate);
		this.datFld_UntilDate.setValue(untilDate);
	}

	private ScoreCardSiteFilterDTO executeService(BbrUI bbrUIParent)
	{
		ScoreCardSiteFilterDTO result = new ScoreCardSiteFilterDTO();

		try
		{
			CompanyDataDTO selectedCompany = this.selectedCompany;
			String pvcode = selectedCompany != null ? selectedCompany.getRut() : null;
			// del reporte
			result = this.getScoreCardDTOWS(pvcode);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private ScoreCardSiteFilterDTO getScoreCardDTOWS(String selectedCompany)
	{

		ScoreCardManagerServer loginClientPort;
		ScoreCardSiteFilterDTO item = null;
		try
		{
			URL url = new URL(CustomerServiceConstants.getInstance().getScoreCardWebServiceEndpointPath());
			ScoreCardManagerServerService loginClient = new ScoreCardManagerServerService(url);
			loginClientPort = loginClient.getScoreCardManagerServerPort();
			item = loginClientPort.getAvailableSite(Long.valueOf(selectedCompany), CustomerServiceConstants.REPORT_OPTION);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (!this.getBbrUIParent().hasAlertWindowOpen())
			{
				this.getBbrUIParent().showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "error_webservice"));
			}
		}
		return item;
	}

	@Override
	public void setSectionData(Object data)
	{
		if (this.selectedCompany != (CompanyDataDTO) data)
		{
			this.selectedCompany = (CompanyDataDTO) data;
			this.getSitesByService();
			this.ocFilter_changeHandler(null);
		}
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
