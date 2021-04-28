package bbr.b2b.portal.components.filter_section.generic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.utils.generic.FindProvider;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import bbr.b2b.users.report.classes.CompanyDataResultDTO;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;

public class ProviderFilterSectionCustom extends BbrVerticalSection<CompanyDataDTO>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	private static final long serialVersionUID = -8569131291169324776L;

	public ProviderFilterSectionCustom(BbrUI parent)
	{
		super(parent);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private BbrComboBox<CompanyDataDTO>	cbx_Providers					= null;
	private HorizontalLayout			pnlSearchPanel					= null;
	private ArrayList<CompanyDataDTO>	companies						= null;

	private boolean						isAllEnterprises				= false;
	private boolean						companyDefault					= false;
	private boolean						searchProvider					= false;
	private Button						btn_SearchCompany				= null;
	private CheckBox					chkBox_active					= null;
	public String						CBX_ONLY_ACTIVE_PRODUCTS_EVENT	= "CBX_ONLY_ACTIVE_PRODUCTS_EVENT";

	public void setSearchProvider(boolean searchProvider)
	{
		this.searchProvider = searchProvider;
	}

	public void setCompanyDefault(boolean companyDefault)
	{
		this.companyDefault = companyDefault;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		Label lbl_ProviderHeader = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "provider"));
		lbl_ProviderHeader.addStyleName("bbr-panel-space");
		lbl_ProviderHeader.setWidth("220px");

		this.pnlSearchPanel = new HorizontalLayout();

		HorizontalLayout pnlProvidersHeader = new HorizontalLayout();
		pnlProvidersHeader.addStyleName("bbr-filter-label-header");
		pnlProvidersHeader.addComponents(lbl_ProviderHeader, this.pnlSearchPanel);
		pnlProvidersHeader.setExpandRatio(this.pnlSearchPanel, 1F);
		pnlProvidersHeader.setWidth("100%");

		Label lbl_Provider = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "provider"));
		lbl_Provider.setWidth("120px");

		BbrComboBox<CompanyDataDTO> providers = this.getProvidersComboBox();
		this.cbx_Providers = providers;
		HorizontalLayout pnlProviderSubSection = new HorizontalLayout();
		pnlProviderSubSection.setWidth("100%");
		pnlProviderSubSection.addComponents(lbl_Provider, this.cbx_Providers);
		pnlProviderSubSection.setExpandRatio(this.cbx_Providers, 1F);
		pnlProviderSubSection.addStyleName("bbr-panel-space");

		if (this.searchProvider)
		{
			this.showSearchPnl();
		}
		HorizontalLayout pnlActiveProductsSubsection = new HorizontalLayout();
		this.chkBox_active = new CheckBox(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "only_active_products"));
		// this.chkBox_OnlyActiveProducts = new CheckBox("Mostrar productos activos VeV");
		this.chkBox_active.addValueChangeListener((ValueChangeListener<Boolean> & Serializable) e -> this.cbx_OnlyActiveProducts_ValueChangeHandler(e));
		this.chkBox_active.setValue(true);

		pnlActiveProductsSubsection = new HorizontalLayout();
		pnlActiveProductsSubsection.setWidth("100%");
		pnlActiveProductsSubsection.addComponents(new BbrHSeparator("120px"), this.chkBox_active);
		pnlActiveProductsSubsection.setExpandRatio(this.chkBox_active, 1F);
		pnlActiveProductsSubsection.addStyleName("bbr-panel-space");

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlProvidersHeader, pnlProviderSubSection, pnlActiveProductsSubsection);

	}

	@Override
	public CompanyDataDTO getSectionResult()
	{
		CompanyDataDTO result = null;
		if (this.cbx_Providers.getSelectedValue() != null)
		{
			result = this.cbx_Providers.getSelectedValue();
		}
		return result;
	}

	public boolean getActiveCheckBox()
	{
		return this.chkBox_active.getValue();
	}

	@Override
	public String validateData()
	{
		String result = null;
		CompanyDataDTO selectedCompany = this.cbx_Providers.getSelectedValue();

		if (selectedCompany == null || selectedCompany.getId() == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "provider_requires");
		}

		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	private void btn_SearchCompany_clickHandler(ClickEvent e)
	{
		if (e != null)
		{
			FindProvider winFindProvider = new FindProvider(this.getBbrUIParent(), this.isAllEnterprises);
			winFindProvider.initializeView();
			winFindProvider.addBbrEventListener(this::companySelectedHandler);
			winFindProvider.open(true);
		}
	}

	private void companySelectedHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getResultObject() != null)
		{
			CompanyDataDTO company = (CompanyDataDTO) bbrEvent.getResultObject();

			this.updateCompanies(company);
		}
	}

	private void company_changeHandler(ValueChangeEvent<CompanyDataDTO> e)
	{
		if (ProviderFilterSectionCustom.this.cbx_Providers != null && ProviderFilterSectionCustom.this.cbx_Providers.getSelectedValue() != null && e != null)
		{
			BbrSectionEvent bbrEvent = new BbrSectionEvent(BbrFilterSectionConstants.FS_PROVIDER);
			bbrEvent.setResultObject(ProviderFilterSectionCustom.this.cbx_Providers.getSelectedValue());
			dispatchBbrSectionEvent(bbrEvent);
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private BbrComboBox<CompanyDataDTO> getProvidersComboBox()
	{
		BbrComboBox<CompanyDataDTO> result = null;
		try
		{
			result = this.initializeCombobox();

			CompanyDataResultDTO providersResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(this.getBbrUIParent()).getMostUsedProvidersByUserId(this.getBbrUIParent().getUser().getId());
			BbrError error = ErrorsMngr.getInstance().getError(providersResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError() && providersResult != null)
			{
				result.addValueChangeListener(this::company_changeHandler);
				this.isAllEnterprises = providersResult.getAllenterprises();
				CompanyDataDTO lastUsedCompany = providersResult.getLastUsedCompanyDataDTOs() != null ? providersResult.getLastUsedCompanyDataDTOs() : null;

				// todas las empresas y ultimo proveedor utilizado
				if (this.isAllEnterprises)
				{
					if (lastUsedCompany != null)
					{
						this.companies = new ArrayList<>(Arrays.asList(lastUsedCompany));
						this.showSearchPnl();
						if (companyDefault)
						{
							CompanyDataDTO companyDefaultValue = this.getCompanyDefault();
							this.companies.add(0, companyDefaultValue);
						}

						result = this.setComboboxItemsAndSelect(result, this.companies, lastUsedCompany);
					}
					else
					{
						this.setComboboxEmptyItem(result);
					}
				}
				// ultimo proveedor utilizado
				else if ((providersResult.getMostUsedCompanyDataDTOs() != null) && (providersResult.getMostUsedCompanyDataDTOs().length > 0))
				{
					this.showSearchPnl();
					CompanyDataDTO[] providerslist = providersResult.getMostUsedCompanyDataDTOs();
					Arrays.sort(providerslist, (p1, p2) -> p1.getName().compareTo(p2.getName()));

					this.companies = new ArrayList<>(Arrays.asList(providerslist));

					if (companyDefault)
					{
						CompanyDataDTO companyDefaultValue = this.getCompanyDefault();
						this.companies.add(0, companyDefaultValue);

						result = this.setComboboxItemsAndSelect(result, this.companies, null);
					}
					else if (lastUsedCompany != null)
					{
						result = this.setComboboxItemsAndSelect(result, this.companies, lastUsedCompany);
					}
					else
					{
						result = this.setComboboxItemsAndSelect(result, this.companies, null);
					}

				}
			}
			else
			{
				this.setComboboxEmptyItem(result);
			}
		}

		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
		}

		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}

		result.markAsDirty();
		return result;

	}

	private void cbx_OnlyActiveProducts_ValueChangeHandler(ValueChangeEvent<Boolean> e)
	{
		BbrSectionEvent alertResponseEvent = new BbrSectionEvent(this.CBX_ONLY_ACTIVE_PRODUCTS_EVENT);
		alertResponseEvent.setResultObject(e.getValue());
		this.dispatchBbrSectionEvent(alertResponseEvent);
	}

	private BbrComboBox<CompanyDataDTO> setComboboxItemsAndSelect(BbrComboBox<CompanyDataDTO> result, ArrayList<CompanyDataDTO> companies, CompanyDataDTO lastUsedCompany)
	{
		result.setItems(companies);
		if (lastUsedCompany != null)
		{
			result.setSelectedItem(lastUsedCompany);
		}
		else
		{
			result.selectFirstItem();
		}
		return result;
	}

	private BbrComboBox<CompanyDataDTO> initializeCombobox()
	{
		BbrComboBox<CompanyDataDTO> result = new BbrComboBox<>();
		result.setItemCaptionGenerator(CompanyDataDTO::getName);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.setWidth("255px");
		result.addStyleName("bbr-filter-fields");
		return result;
	}

	private CompanyDataDTO getCompanyDefault()
	{
		CompanyDataDTO companyDefaultValue = new CompanyDataDTO();
		companyDefaultValue.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "all"));
		companyDefaultValue.setId(-1L);
		companyDefaultValue.setRut("");
		return companyDefaultValue;
	}

	private CompanyDataDTO getCompanyEmpty()
	{
		CompanyDataDTO companyDefaultValue = new CompanyDataDTO();
		companyDefaultValue.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "no_companies_assigned"));
		return companyDefaultValue;
	}

	private BbrComboBox<CompanyDataDTO> setComboboxEmptyItem(BbrComboBox<CompanyDataDTO> result)
	{
		CompanyDataDTO emptyResult = this.getCompanyEmpty();
		result.setItems(emptyResult);
		result.setSelectedItem(emptyResult);
		result.setEnabled(false);
		return result;
	}

	private void showSearchPnl()
	{
		if (this.btn_SearchCompany == null)
		{
			this.btn_SearchCompany = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "search_company"));
			this.btn_SearchCompany.addStyleName("link-filter-button");
			this.btn_SearchCompany.addClickListener(this::btn_SearchCompany_clickHandler);

			this.pnlSearchPanel.setWidth("160px");
			this.pnlSearchPanel.addComponent(btn_SearchCompany);
			this.pnlSearchPanel.addStyleName("search-panel");
		}
	}

	private void updateCompanies(CompanyDataDTO company)
	{
		if (company != null && this.companies != null && !this.companies.isEmpty())
		{
			Optional<CompanyDataDTO> optional = this.companies.stream().filter(item -> item.getRut().equals(company.getRut())).findFirst();
			if (optional.isPresent())
			{
				if (this.isAllEnterprises)
				{
					this.cbx_Providers.removeAllItems();
					this.cbx_Providers.setItems(optional.get());
				}
				this.cbx_Providers.setSelectedItem(optional.get());
			}
			else
			{

				CompanyDataDTO newCompany = new CompanyDataDTO();
				newCompany.setId(company.getId());
				newCompany.setName(company.getName());
				newCompany.setRut(company.getRut());

				if (this.isAllEnterprises)
				{
					this.cbx_Providers.removeAllItems();
					this.companies = new ArrayList<>();
				}
				this.companies.add(newCompany);
				if (companyDefault)
				{
					CompanyDataDTO companyDefaultValue = this.getCompanyDefault();
					this.companies.add(0, companyDefaultValue);
				}
				this.cbx_Providers.setItems(this.companies);
				this.cbx_Providers.setSelectedItem(newCompany);
			}
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
