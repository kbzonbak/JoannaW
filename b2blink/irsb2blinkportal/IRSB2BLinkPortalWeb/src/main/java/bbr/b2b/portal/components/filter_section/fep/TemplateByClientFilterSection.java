package bbr.b2b.portal.components.filter_section.fep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeInitParamDTO;
import bbr.b2b.fep.common.data.classes.ClientArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ClientDTO;
import bbr.b2b.fep.common.data.classes.ClientInitParamDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.wrappers.fep.FEPTemplateByClientFilterSectionInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrVSeparator;

public class TemplateByClientFilterSection extends BbrVerticalSection<FEPTemplateByClientFilterSectionInfo>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 5129425386873577513L;

	public TemplateByClientFilterSection(BbrUI parent)
	{
		super(parent);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private BbrComboBox<ArticleTypeDataDTO> 			cbx_Templates			= null;
	private BbrComboBox<ClientDTO> 						cbx_Clients				= null;
	private static final String							TEMPLATE_NAME			= "description";
	private final String								DEFAULT_SORT_FIELD		= TEMPLATE_NAME;
	private final int									DEFAULT_PAGE_NUMBER		= 1;
	private final int									MAX_ROWS_NUMBER			= 100;
	private HorizontalLayout 							pnlState			= null;

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
		Label lbl_Products = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_client"));
		lbl_Products.addStyleName("bbr-panel-space");
		lbl_Products.setWidth("200px");

		HorizontalLayout pnlProductsHeader = new HorizontalLayout();
		pnlProductsHeader.addStyleName("bbr-filter-label-header");
		pnlProductsHeader.addComponents(lbl_Products);
		pnlProductsHeader.setWidth("100%");
		
		// Seccion Clientes
		Label lbl_Filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_filter"));
		lbl_Filter.setWidth("120px");

		this.cbx_Clients = this.updateClientComboBox();
		this.cbx_Clients.addValueChangeListener((ValueChangeListener<ClientDTO> & Serializable) e -> clientSelectionHandler());
		this.cbx_Clients.setWidth(100, Unit.PERCENTAGE);

		this.pnlState = new HorizontalLayout();
		pnlState.addComponents(lbl_Filter, this.cbx_Clients);
		pnlState.setExpandRatio(this.cbx_Clients, 1F);
		pnlState.setWidth("100%");
		pnlState.addStyleName("bbr-panel-space");
		pnlState.setComponentAlignment(lbl_Filter, Alignment.MIDDLE_LEFT);
		pnlState.setComponentAlignment(this.cbx_Clients, Alignment.MIDDLE_LEFT);
				
		// Sección Plantilla
		Label lbl_Template = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_template"));
		lbl_Template.setWidth("120px");
		
		inicializeTemplatesComboBox();

		HorizontalLayout pnlTemplateSubsection = new HorizontalLayout();
		pnlTemplateSubsection.addComponents(lbl_Template, this.cbx_Templates);
		pnlTemplateSubsection.setExpandRatio(this.cbx_Templates, 1F);
		pnlTemplateSubsection.setWidth("100%");
		pnlTemplateSubsection.addStyleName("bbr-panel-space");
		
		pnlTemplateSubsection.setComponentAlignment(lbl_Template, Alignment.MIDDLE_LEFT);
		pnlTemplateSubsection.setComponentAlignment(this.cbx_Templates, Alignment.MIDDLE_LEFT);

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlProductsHeader, pnlState, pnlTemplateSubsection);
	}

	@Override
	public FEPTemplateByClientFilterSectionInfo getSectionResult()
	{
		FEPTemplateByClientFilterSectionInfo result = null;

		if (validateData() == null)
		{
			ClientDTO selectedClient = this.cbx_Clients.getSelectedValue();
			ArticleTypeDataDTO selectedTemplate = this.cbx_Templates.getSelectedValue();
			
			result = new FEPTemplateByClientFilterSectionInfo(selectedClient, selectedTemplate);
		}

		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;
		
		if (this.cbx_Clients.getSelectedValue() == null || this.cbx_Clients.getSelectedValue().getId() < 0)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "client_required");
		}
		else if (this.cbx_Templates == null || this.cbx_Templates.getSelectedValue() == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "template_requires");
		}
		
		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private BbrComboBox<ArticleTypeDataDTO> updateTemplatesComboBox()
	{
		
		if (this.cbx_Clients.getSelectedValue().getId() < 0)
		{
			inicializeTemplatesComboBox();
			return this.cbx_Templates;
		}
		
		Integer requestedPage = DEFAULT_PAGE_NUMBER;
		Integer maxRow = MAX_ROWS_NUMBER;

		ArticleTypeInitParamDTO initParam = new ArticleTypeInitParamDTO();
		initParam.setActive(true);
		initParam.setDescription(null);
		initParam.setType(FEPConstants.ARTICLETYPE_CLASS_NAME_CP);
		initParam.setClientname(this.cbx_Clients.getSelectedValue().getInternalname());

		OrderCriteriaDTO ordercriteria[] = new OrderCriteriaDTO[1];
		OrderCriteriaDTO order = new OrderCriteriaDTO();
		order.setPropertyname(DEFAULT_SORT_FIELD);
		order.setAscending(true);
		ordercriteria[0] = order;

		ArticleTypeArrayResultDTO templatesData;
		
		try
		{
			templatesData = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getArticleTypeData(initParam,
			requestedPage,
			maxRow,
			false,
			ordercriteria,
			this.getBbrUIParent().getLocale().getLanguage());

			if ((templatesData != null) && (templatesData.getValues() != null) && (templatesData.getValues().length > 0))
			{
				ArticleTypeDataDTO allTemplatesOption = new ArticleTypeDataDTO();
				allTemplatesOption.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "all_upper_f"));
				allTemplatesOption.setId(-1L);

				ArrayList<ArticleTypeDataDTO> templatesList = new ArrayList<>();
				templatesList.add(allTemplatesOption);
				templatesList.addAll(Arrays.asList(templatesData.getValues()));

				this.cbx_Templates.setItems(templatesList);
				this.cbx_Templates.selectFirstItem();
				this.cbx_Templates.addStyleName("bbr-filter-fields");
				this.cbx_Templates.setEnabled(true);
			}
			else
			{
				ArticleTypeDataDTO emptyResult = new ArticleTypeDataDTO();
				emptyResult.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_template_assigned"));

				this.cbx_Templates.setItems(emptyResult);
				this.cbx_Templates.setSelectedItem(emptyResult);
				this.cbx_Templates.setEnabled(false);
			}
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut();
		}
		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}

		return this.cbx_Templates;
	}
	
	private BbrComboBox<ClientDTO> updateClientComboBox()
	{
		BbrComboBox<ClientDTO> result = null;
		
		result = new BbrComboBox<ClientDTO>();
		result.setItemCaptionGenerator(ClientDTO::getName);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.setWidth("255px");

		if (this.getBbrUIParent().getUser() != null)
		{
			try
			{
				ClientInitParamDTO initparam = new ClientInitParamDTO();
				initparam.setIsRetail(true);
				
				ClientArrayResultDTO clientsResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getAllClients(initparam);
				
				if ((clientsResult != null) && (clientsResult.getValues() != null) && (clientsResult.getValues().length > 0))
				{
					ClientDTO noOption = new ClientDTO();
					noOption.setName("Seleccione...");
					noOption.setId(-1L);
					
					ArrayList<ClientDTO> clientList = new ArrayList<>();
					clientList.add(noOption);
					clientList.addAll(Arrays.asList(clientsResult.getValues()));

					result.setItems(clientList);
					result.addStyleName("bbr-filter-fields");
					result.selectFirstItem();
				}
				else
				{
					ClientDTO emptyResult = new ClientDTO();
					emptyResult.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_clients_available"));

					result.setItems(emptyResult);
					result.setSelectedItem(emptyResult);
					result.setEnabled(false);
				}
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut();
			}
			catch (BbrSystemException e)
			{
				e.printStackTrace();
			}
		}

		return result;

	}
	
	private void clientSelectionHandler()
	{
		if(pnlState != null)
		{
			this.cbx_Templates = this.updateTemplatesComboBox();
		}
	}
	
	private void inicializeTemplatesComboBox(){
		
		if (this.cbx_Templates == null)		{
			this.cbx_Templates = new BbrComboBox<ArticleTypeDataDTO>();
			
			cbx_Templates.setItemCaptionGenerator(ArticleTypeDataDTO::getDescription);
			cbx_Templates.setTextInputAllowed(false);
			cbx_Templates.setEmptySelectionAllowed(false);
			cbx_Templates.setWidth(100, Unit.PERCENTAGE);
		}
		
		ArticleTypeDataDTO allTemplatesOption = new ArticleTypeDataDTO();
		allTemplatesOption.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "all_upper_f"));
		allTemplatesOption.setId(-1L);

		ArrayList<ArticleTypeDataDTO> templatesList = new ArrayList<>();
		templatesList.add(allTemplatesOption);
		this.cbx_Templates.setItems(templatesList);
		this.cbx_Templates.selectFirstItem();
		this.cbx_Templates.addStyleName("bbr-filter-fields");
		this.cbx_Templates.setEnabled(false);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
