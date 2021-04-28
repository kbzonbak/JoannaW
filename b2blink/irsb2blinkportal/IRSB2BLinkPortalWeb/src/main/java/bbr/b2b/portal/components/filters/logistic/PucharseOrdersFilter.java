package bbr.b2b.portal.components.filters.logistic;

import static bbr.b2b.portal.constants.logistic.ConstantsPucharseOrderSearchCriteria.*;

import java.io.Serializable;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.logistic.buyorders.data.dto.OrderReportInitParamDTO;
import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.logistic.PucharseOrderSearchFilterUtils;
import bbr.b2b.portal.classes.wrappers.customer.RequestClientReportFilterSectionInfo;
import bbr.b2b.portal.classes.wrappers.logistic.PucharseOrderCriterion;
import bbr.b2b.portal.components.filter_section.customer.RequestClientFilterSection;
import bbr.b2b.portal.components.filter_section.generic.ProviderFilterSection;
import bbr.b2b.portal.components.filter_section.logistic.PucharseOrderCriterionFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.utils.PortalDates;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.container.BbrSectionEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class PucharseOrdersFilter extends BbrFilterContainer implements Button.ClickListener
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long					serialVersionUID					= 1801295453250167206L;
	private static final int					MAX_ROWS_BY_PAGE					= 20;
	private ProviderFilterSection				providerFilterSection	= null;
	private RequestClientFilterSection			clientFilterSection		= null;
	private PucharseOrderCriterionFilterSection	pucharseOrderCriterionFilterSection	= null;
	private Button								btn_FilterSearch					= null;
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public PucharseOrdersFilter(BbrModule bbrModule)
	{
		super(bbrModule);
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
			OrderReportInitParamDTO initParam = this.getInitParam();
			BbrFilterEvent bbrFilter = this.getBbrFilterEventObject();
			bbrFilter.setResultObject(initParam);

			this.dispatchBbrFilterEvent(bbrFilter);
		}
		else
		{
			// Faltan datos en el filtro seleccionado
			PucharseOrdersFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	public void initializeView()
	{
		// Seccion Proveedor
		providerFilterSection = new ProviderFilterSection(this.getBbrUIParent());
		providerFilterSection.initializeView();
		providerFilterSection.setSearchProvider(true);
		providerFilterSection.addBbrSectionListener(BbrFilterSectionConstants.FS_PROVIDER, (BbrSectionEventListener & Serializable) e -> this.companyChange_Listener(e));

		// Sección Cliente
		clientFilterSection = new RequestClientFilterSection(this.getBbrUIParent());
		clientFilterSection.setSectionData(providerFilterSection.getSectionResult());
		clientFilterSection.initializeView();

		// Sección Criterio
		pucharseOrderCriterionFilterSection = new PucharseOrderCriterionFilterSection(this.getBbrUIParent());
		pucharseOrderCriterionFilterSection.initializeView();

		// Botón de Búsqueda
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
		mainLayout.addComponents(providerFilterSection, clientFilterSection, pucharseOrderCriterionFilterSection, pnlSearchButton, pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("280px");
		this.addComponent(mainLayout);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private BbrFilterEvent getBbrFilterEventObject()
	{
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		RequestClientReportFilterSectionInfo selectionClientFilter = clientFilterSection.getSectionResult();
		PucharseOrderCriterion pucharseOrderCriterionInfo = this.pucharseOrderCriterionFilterSection.getSectionResult();

		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);

		String criteriaCaption = "";

		result.setCaption(PucharseOrderSearchFilterUtils.getFilterCaption(PucharseOrderSearchFilterUtils.LOGISTIC_RESOURCES, "company", true) + selectedCompany.getName());

		
		String strClient = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client");
		String cLientHeader = strClient + ": " + selectionClientFilter.getSitie().getName();
		
		switch (pucharseOrderCriterionInfo.getTypeSearch())
		{

			case VALID_ORDERS: 
				criteriaCaption = PucharseOrderSearchFilterUtils.getValidOrders(PucharseOrderSearchFilterUtils.LOGISTIC_RESOURCES, "filter_oc", false);
				break;

			case EMITTED_DATE: 
			case RECEPTION_B2B_DATE:
				if (pucharseOrderCriterionInfo.getStartDateTime() != null && pucharseOrderCriterionInfo.getEndDateTime() != null)
				{
					criteriaCaption = PucharseOrderSearchFilterUtils.getPeriodCaption(PucharseOrderSearchFilterUtils.LOGISTIC_RESOURCES, "period",
							pucharseOrderCriterionInfo.getStartDateTime(), pucharseOrderCriterionInfo.getEndDateTime());
				}
				break;

			case ORDER_NUMBER: 
				criteriaCaption = PucharseOrderSearchFilterUtils.getOrderNumberCaption(PucharseOrderSearchFilterUtils.LOGISTIC_RESOURCES, "order_number", false,
						pucharseOrderCriterionInfo.getNumberValue());
				break;
				
			case ORDER_STATE: 
				criteriaCaption = PucharseOrderSearchFilterUtils.getOrderStateCaption(PucharseOrderSearchFilterUtils.LOGISTIC_RESOURCES, "order_number", false,
						pucharseOrderCriterionInfo.getOrderState().getName());
				break;

			default:
				break;
		}

		
		result.setSecondaryCaption(cLientHeader + criteriaCaption);
		return result;
	}

	private OrderReportInitParamDTO getInitParam()
	{
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		RequestClientReportFilterSectionInfo selectionClientFilter = clientFilterSection.getSectionResult();
		PucharseOrderCriterion pucharseOrderInfo = this.pucharseOrderCriterionFilterSection.getSectionResult();

		OrderReportInitParamDTO initParam = new OrderReportInitParamDTO();
		

		// RUT = DNI
		initParam.setVendorcode(selectedCompany.getRut());
		initParam.setClientrut(selectionClientFilter.getSitie().getCode());
		initParam.setFiltertype(pucharseOrderInfo.getTypeSearch());


		switch (pucharseOrderInfo.getTypeSearch())
		{
			case EMITTED_DATE:
				String emittedSince   = PortalDates.getFormatDateDDMMYY2YYMMDD(BbrDateUtils.getInstance().toShortDate(pucharseOrderInfo.getStartDateTime()));
				String emittedUntil   = PortalDates.getFormatDateDDMMYY2YYMMDD(BbrDateUtils.getInstance().toShortDate(pucharseOrderInfo.getEndDateTime()));
				initParam.setEmittedsince(emittedSince);
				initParam.setEmitteduntil(emittedUntil);
				break;
			case RECEPTION_B2B_DATE:
				String receptionSince 	 = PortalDates.getFormatDateDDMMYY2YYMMDD(BbrDateUtils.getInstance().toShortDate(pucharseOrderInfo.getStartDateTime()));
				String receptiondUntil   = PortalDates.getFormatDateDDMMYY2YYMMDD(BbrDateUtils.getInstance().toShortDate(pucharseOrderInfo.getEndDateTime()));
				initParam.setReceptionsince(receptionSince);
				initParam.setReceptionuntil(receptiondUntil);
				break;
			case ORDER_NUMBER:
				initParam.setOrdernumber(pucharseOrderInfo.getNumberValue().toString());
				break;
			case ORDER_STATE: 
				initParam.setOrderstatetypeid(pucharseOrderInfo.getOrderState().getId());
				break;
			default:
				break;
		}

		initParam.setRows(MAX_ROWS_BY_PAGE);
		return initParam;
	}

	public String validateData()
	{
		String result = null;

		if (providerFilterSection.validateData() != null)
		{
			result = providerFilterSection.validateData();
		}
		else if (clientFilterSection.validateData() != null)
		{
			result = clientFilterSection.validateData();
		}
		else if (pucharseOrderCriterionFilterSection.validateData() != null)
		{
			result = pucharseOrderCriterionFilterSection.validateData();
		}

		return result;
	}
	
	private void companyChange_Listener(BbrSectionEvent e)
	{
		this.clientFilterSection.setSectionData((CompanyDataDTO) e.getResultObject());
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
