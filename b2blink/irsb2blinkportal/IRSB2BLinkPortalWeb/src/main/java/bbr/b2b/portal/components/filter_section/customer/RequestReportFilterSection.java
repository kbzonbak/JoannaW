package bbr.b2b.portal.components.filter_section.customer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils;
import bbr.b2b.portal.classes.wrappers.customer.RequestReportFilterSectionInfo;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.CustomerConstants;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.portal.constants.customer.CustomerServiceConstants;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import bbr.esb.services.webservices.facade.RequestFilterForTicketDTO;
import bbr.esb.services.webservices.facade.ScoreCardManagerServer;
import bbr.esb.services.webservices.facade.ScoreCardManagerServerService;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;

public class RequestReportFilterSection extends BbrVerticalSection<RequestReportFilterSectionInfo>
{
	// ==================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// ==================================================================================================

	private static final long						serialVersionUID	= -7659625480567661782L;
	private CompanyDataDTO							selectedCompany		= null;
	private BbrComboBox<SearchCriterion>			cbx_FilterCriteria	= null;
	private BbrComboBox<RequestFilterForTicketDTO>	cbx_ServiceValue	= null;
	private List<RequestFilterForTicketDTO>			listOfService		= new ArrayList<>();
	private boolean									refreshSites		= false;
	// ==================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// ==================================================================================================

	// ==================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTOR
	// ==================================================================================================
	public RequestReportFilterSection(BbrUI parent)
	{
		super(parent);
	
	}

	// ==================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTOR
	// ==================================================================================================

	// ==================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ==================================================================================================
	@Override
	public void setSectionData(Object data)
	{
		if (this.selectedCompany != (CompanyDataDTO) data)
		{
			super.setSectionData(data);
			this.selectedCompany = (CompanyDataDTO) data;
			if (this.cbx_FilterCriteria != null)
			{
				if (this.cbx_FilterCriteria.getSelectedValue().getId().compareTo(CustomerConstants.TYPE_REQUEST_FILTER_VALUE) == 0)
				{
					this.updateSelectedServiceValue();
				} 
			}
		}
	}

	@Override
	public void initializeView()
	{
		// Header
		Label lbl_Request = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "lbl_request"));
		lbl_Request.addStyleName("bbr-panel-space");
		lbl_Request.setWidth("200px");

		HorizontalLayout pnlRequestHeader = new HorizontalLayout();
		pnlRequestHeader.addStyleName("bbr-filter-label-header");
		pnlRequestHeader.addComponents(lbl_Request);
		pnlRequestHeader.setWidth("100%");

		// Sección Criterio

		Label lbl_Filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "lbl_filter_by"));
		lbl_Filter.setWidth("120px");

		this.cbx_FilterCriteria = this.updateCriteriaComboBox();

		HorizontalLayout pnlFilterCriteria = new HorizontalLayout();
		pnlFilterCriteria.addComponents(lbl_Filter, this.cbx_FilterCriteria);
		pnlFilterCriteria.setExpandRatio(this.cbx_FilterCriteria, 1F);
		pnlFilterCriteria.setWidth("100%");
		pnlFilterCriteria.addStyleName("bbr-panel-space");

		HorizontalLayout pnlService = this.getServicePanel();
		
		this.criterionSelectionHandler(null);
		
		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlRequestHeader, pnlFilterCriteria, pnlService);
	}

	@Override
	public RequestReportFilterSectionInfo getSectionResult()
	{
		RequestReportFilterSectionInfo result = null;

		if (this.validateData() == null)
		{
			SearchCriterion filterCriterion = (this.cbx_FilterCriteria != null) ? this.cbx_FilterCriteria.getSelectedValue() : null;
			RequestFilterForTicketDTO selectedService = (this.cbx_ServiceValue != null) ? this.cbx_ServiceValue.getSelectedValue() : null;

			result = new RequestReportFilterSectionInfo(filterCriterion, selectedService);
		}

		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;
		SearchCriterion selectedFilterType = this.cbx_FilterCriteria.getSelectedValue();

		if (selectedFilterType.getId().equals(FEPConstants.STATE_FILTER_VALUE) &&
				(this.cbx_ServiceValue == null) || (this.cbx_ServiceValue.getSelectedValue() == null))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "status_requires");
		}

		return result;
	}

	// ==================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ==================================================================================================

	// ==================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// ==================================================================================================

	// ==================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// ==================================================================================================

	// ==================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ==================================================================================================

	private BbrComboBox<SearchCriterion> updateCriteriaComboBox()
	{
		SearchCriterion[] searchCriteria = CustomerSearchCriteriaFilterUtils.getRequestFilterCriteria();
		BbrComboBox<SearchCriterion> result = new BbrComboBox<SearchCriterion>(searchCriteria);
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.addSelectionListener((SingleSelectionListener<SearchCriterion>) s -> this.criterionSelectionHandler(s));
		result.selectFirstItem();
		result.setEmptySelectionAllowed(false);
		result.setWidth("255px");

		return result;
	}

	private void criterionSelectionHandler(SingleSelectionEvent<SearchCriterion> event)
	{
		if (this.cbx_FilterCriteria != null)
		{
			this.updateSelectedServiceValue();
		}
	}
	
	private void updateSelectedServiceValue()
	{
		try
		{
			this.listOfService = this.executeService(this.getBbrUIParent());
			
			//TODOS
			RequestFilterForTicketDTO AllFilterForTiket = new RequestFilterForTicketDTO();
			AllFilterForTiket.setValues("TODOS");
			AllFilterForTiket.setIds(-1L);
			
			if (this.listOfService != null && this.listOfService.size() > 0)
			{
				if(this.cbx_ServiceValue != null)
				{
					this.listOfService.add(AllFilterForTiket); 
					this.cbx_ServiceValue.setItems(this.listOfService);
					this.cbx_ServiceValue.setSelectedItem(AllFilterForTiket);
					this.cbx_ServiceValue.setEnabled(true);
				}
			}
			// no hay errores pero de todas formas hay que agregar todos.
			else
			{
				if(this.cbx_ServiceValue != null)
				{
					List<RequestFilterForTicketDTO> tmpListOfService = new ArrayList<>();
					tmpListOfService.add(AllFilterForTiket);
					
					this.cbx_ServiceValue.setItems(tmpListOfService);
					this.cbx_ServiceValue.setSelectedItem(AllFilterForTiket);
					this.cbx_ServiceValue.setEnabled(true);
				}
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<RequestFilterForTicketDTO> executeService(BbrUI bbrUIParent)
	{
		List<RequestFilterForTicketDTO> result = null;
		try
		{
			SearchCriterion selectedState = this.cbx_FilterCriteria.getSelectedValue();
			result = this.getServiceDTOWS(Long.valueOf(selectedState.getId()));

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private List<RequestFilterForTicketDTO> getServiceDTOWS(Long selectedValue)
	{

		ScoreCardManagerServer loginClientPort;
		List<RequestFilterForTicketDTO> servicedata = null;
		try
		{
			URL url = new URL(CustomerServiceConstants.getInstance().getScoreCardWebServiceEndpointPath());
			ScoreCardManagerServerService loginClient = new ScoreCardManagerServerService(url);
			loginClientPort = loginClient.getScoreCardManagerServerPort();
			servicedata = loginClientPort.getRequestFilter(this.selectedCompany.getRut(), Integer.valueOf(selectedValue.toString()));
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return servicedata;
	}

	private HorizontalLayout getServicePanel()
	{
		if (this.cbx_ServiceValue == null)
		{
			this.cbx_ServiceValue = new BbrComboBox<RequestFilterForTicketDTO>();
			
//			if (this.listOfService != null && this.listOfService.size() > 0)
//			{
//				this.cbx_ServiceValue.setItems(this.listOfService);
//				this.cbx_ServiceValue.selectFirstItem();
//				this.cbx_ServiceValue.setEnabled(true);
//			}
//			else
//			{
//				
//				List<RequestFilterForTicketDTO> tmpListOfService = new ArrayList<>();
//				RequestFilterForTicketDTO empty = new RequestFilterForTicketDTO();
//				empty.setValues("N/A");
//				empty.setIds(0L);
//				tmpListOfService.add(empty);
//				this.cbx_ServiceValue.setItems(tmpListOfService);
//				this.cbx_ServiceValue.selectFirstItem();
//				this.cbx_ServiceValue.setEnabled(false);
//			}
			this.cbx_ServiceValue.setItemCaptionGenerator(RequestFilterForTicketDTO::getValues);
			this.cbx_ServiceValue.setTextInputAllowed(false);
			this.cbx_ServiceValue.setEmptySelectionAllowed(false);
			this.cbx_ServiceValue.addStyleName("bbr-filter-fields");
			this.cbx_ServiceValue.setWidth("255px");
			this.cbx_ServiceValue.setHeight("30px");
		}

		BbrHInputFieldContainer result = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "lbl_request_value"))
				.withLabelWidth("120px")
				.withComponent(this.cbx_ServiceValue)
				.build();
		return result;
	}

	// ==================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// ==================================================================================================

}
