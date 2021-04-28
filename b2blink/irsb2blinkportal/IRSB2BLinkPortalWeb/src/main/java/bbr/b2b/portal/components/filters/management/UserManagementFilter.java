package bbr.b2b.portal.components.filters.management;

import java.io.Serializable;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.constants.EnumUserType;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.utils.generic.FindProvider;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import bbr.b2b.users.report.classes.CompanyTypeArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyTypeDTO;
import bbr.b2b.users.report.classes.ProfileArrayResultDTO;
import bbr.b2b.users.report.classes.ProfileDTO;
import bbr.b2b.users.report.classes.UserReportInitParamDTO;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrItemSelectField;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;

public class UserManagementFilter extends BbrFilterContainer implements Button.ClickListener
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long					serialVersionUID				= 5712042403953778345L;
	private BbrComboBox<SearchCriterion>		cbx_FilterType					= null;
	private Button								btn_FilterSearch				= null;
	private Component							cmp_FilterFields				= null;

	private BbrComboBox<CompanyTypeDTO>			cbx_UserTypes					= null;
	private BbrComboBox<SearchCriterion>		cbx_UserState					= null;
	private BbrComboBox<ProfileDTO>				cbx_Profile						= null;
	private BbrTextField						txt_UserId						= null;
	private BbrTextField						txt_UserName					= null;
	private BbrTextField						txt_UserLastName				= null;
	private BbrItemSelectField<CompanyDataDTO>	sfdEnterprise					= null;
	private BbrItemSelectField<CompanyDataDTO>	sfdProvider						= null;
	private BbrHInputFieldContainer				pnlFilterFields					= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public UserManagementFilter(BbrModule bbrModule)
	{
		super(bbrModule);
	}

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void buttonClick(ClickEvent event)
	{
		String errorMsg = this.validateData();
		if (errorMsg != null && errorMsg.length() == 0)
		{
			BbrFilterEvent bbrFilter = this.getBbrFilterEventObject();

			UserReportInitParamDTO initParams = this.getInitParams();

			bbrFilter.setResultObject(initParams);

			dispatchBbrFilterEvent(bbrFilter);
		}
		else
		{
			// Faltan datos en el filtro seleccionado
			UserManagementFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}

	}
	// ****************************************************************************************
	// ENDING SECTION 8 ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		BbrHInputFieldContainer pnlHeader = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MENU, "USR_CU1300"))
				.withLabelWidth("255px")
				.withLabelStyle(BbrStyles.BBR_PANEL_SPACE)
				.build();
		pnlHeader.addStyleName(BbrStyles.BBR_FILTER_LABEL_HEADER);
		pnlHeader.setWidth("100%");

		this.cbx_FilterType = this.getSearchCriterionComboBox();
		this.cbx_FilterType.setWidth("255px");
		this.cbx_FilterType.addStyleName(BbrStyles.BBR_FILTER_FIELDS);
		BbrHInputFieldContainer pnlCriteria = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "filter_by"))
				.withLabelWidth("120px")
				.withComponent(cbx_FilterType)
				.build();
		pnlCriteria.addStyleName(BbrStyles.BBR_PANEL_SPACE);

		this.cmp_FilterFields = this.getFieldsComponents();
		this.cmp_FilterFields.setWidth("255px");
		this.cmp_FilterFields.addStyleName(BbrStyles.BBR_FILTER_FIELDS);
		pnlFilterFields = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "value"))
				.withLabelWidth("120px")
				.withComponent(this.cmp_FilterFields)
				.build();
		pnlFilterFields.addStyleName(BbrStyles.BBR_PANEL_SPACE);

		VerticalLayout userManagementFilterSection = new VerticalLayout();
		userManagementFilterSection.setWidth("400px");
		userManagementFilterSection.addStyleName(BbrStyles.BBR_FILTER_SECTIONS);
		userManagementFilterSection.addStyleName(BbrStyles.BBR_PANEL_SPACE);
		userManagementFilterSection.setSpacing(false);
		userManagementFilterSection.addComponents(pnlHeader, pnlCriteria, pnlFilterFields);

		btn_FilterSearch = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "generate_report"), this);
		btn_FilterSearch.setIcon(VaadinIcons.CHECK_CIRCLE_O);
		btn_FilterSearch.setStyleName("btn-filter-search");
		btn_FilterSearch.setClickShortcut(KeyCode.ENTER);

		VerticalLayout pnlSearchButton = new VerticalLayout();
		pnlSearchButton.setWidth("400px");
		pnlSearchButton.addStyleName(BbrStyles.BBR_PANEL_SPACE);
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
		mainLayout.addComponents(userManagementFilterSection, pnlSearchButton, pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("130px");
		this.addComponent(mainLayout);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private UserReportInitParamDTO getInitParams()
	{
		UserReportInitParamDTO result = new UserReportInitParamDTO();
		SearchCriterion selectedValue = cbx_FilterType.getSelectedValue();
		if (selectedValue != null)
		{
			result.setFilterType(selectedValue.getId());
			switch (selectedValue.getId())
			{
				case 0: // User name
					result.setUserName(this.txt_UserName.getValue().trim());
					break;
				case 1: // User last name
					result.setLastName(this.txt_UserLastName.getValue().trim());
					break;
				case 2: // Company type
					result.setCompanyTypeId(this.cbx_UserTypes.getSelectedValue().getId());
					break;
				case 3: // User state
					result.setUserStateId(this.cbx_UserState.getSelectedValue().getId().longValue());
					break;
				case 4: // User enterprise
					result.setCompanyId(sfdEnterprise.getValue().getId());
					break;
				case 5: // Provider Access
					result.setCompanyId(sfdProvider.getValue().getId());
					break;
				case 6: // Profile
					result.setProfileId(this.cbx_Profile.getSelectedValue().getId());
					break;
				case 7: // User Id
					result.setPersonalid(this.txt_UserId.getValue().trim());
					break;
				default:
					break;
			}
		}

		return result;
	}

	private BbrFilterEvent getBbrFilterEventObject()
	{
		BbrFilterEvent result = null;
		SearchCriterion selectedValue = cbx_FilterType.getSelectedValue();
		if (selectedValue != null)
		{
			result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);
			result.setCaption(selectedValue.getDescription());
			String secondaryHeaderTitle = "";
			switch (selectedValue.getId())
			{
				case 0: // User name
					String searchValue_UserName = this.txt_UserName.getValue().trim();
					secondaryHeaderTitle = (searchValue_UserName != null) ? searchValue_UserName : "";
					break;
				case 1: // User last name
					String searchValue_LastName = this.txt_UserLastName.getValue().trim();
					secondaryHeaderTitle = (searchValue_LastName != null) ? searchValue_LastName : "";
					break;
				case 2: // User type
					CompanyTypeDTO searchValue_UserType = this.cbx_UserTypes.getSelectedValue();
					secondaryHeaderTitle = (searchValue_UserType != null) ? searchValue_UserType.getName() : "";
					break;
				case 3: // User state
					SearchCriterion searchValue_UserState = this.cbx_UserState.getSelectedValue();
					secondaryHeaderTitle = (searchValue_UserState != null) ? searchValue_UserState.getDescription() : "";
					break;
				case 4: // User enterprise
					CompanyDataDTO searchValue_Enterprise = this.sfdEnterprise.getValue();
					secondaryHeaderTitle = (searchValue_Enterprise != null) ? searchValue_Enterprise.getName() : "";
					break;
				case 5: // Provider Access
					CompanyDataDTO searchValue_ProviderAccess = this.sfdProvider.getValue();
					secondaryHeaderTitle = (searchValue_ProviderAccess != null) ? searchValue_ProviderAccess.getName() : "";
					break;
				case 6: // Profile
					ProfileDTO searchValue_Profile = this.cbx_Profile.getSelectedValue();
					secondaryHeaderTitle = (searchValue_Profile != null) ? searchValue_Profile.getName() : "";
					break;
				case 7: // User Id
					String searchValue_Id = this.txt_UserId.getValue().trim();
					secondaryHeaderTitle = (searchValue_Id != null) ? searchValue_Id : "";
					break;

				default:
					break;
			}

			result.setSecondaryCaption("- " + secondaryHeaderTitle);
			result.setResultObject(selectedValue);

		}

		return result;
	}

	private BbrComboBox<SearchCriterion> getSearchCriterionComboBox()
	{
		BbrComboBox<SearchCriterion> result = null;

		SessionBean userBean = (SessionBean) this.getBbrUIParent().getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		if (userBean != null)
		{
			SearchCriterion[] searcCriterions = SearchFilterUtils.getInstance().getUserManagementFilterCriteria(userBean.getEnumUserType());

			result = new BbrComboBox<SearchCriterion>(searcCriterions);

			result.setItemCaptionGenerator(SearchCriterion::getDescription);
			result.setTextInputAllowed(false);
			result.setEmptySelectionAllowed(false);
			if (userBean.getEnumUserType() == EnumUserType.PROVIDER)
			{
				result.selectFirstItem();
			}
			else
			{
				result.selectItem(3);
			}

			result.addValueChangeListener(new ValueChangeListener<SearchCriterion>()
			{
				private static final long serialVersionUID = -885062614408629961L;

				@Override
				public void valueChange(ValueChangeEvent<SearchCriterion> event)
				{
					if (cbx_FilterType.getSelectedValue() != null)
					{
						pnlFilterFields.removeComponent(cmp_FilterFields);

						cmp_FilterFields = getFieldsComponents();
						cmp_FilterFields.markAsDirty();

						pnlFilterFields.addComponent(cmp_FilterFields);
						pnlFilterFields.setExpandRatio(cmp_FilterFields, 1);
					}
				}
			});
			result.setWidth(100F, Unit.PERCENTAGE);
		}

		return result;
	}

	private BbrComboBox<SearchCriterion> getUserStatesComboBox()
	{
		BbrComboBox<SearchCriterion> result = null;
		SearchCriterion[] searcCriterions = SearchFilterUtils.getInstance().getUserStateFilterCriteria();

		result = new BbrComboBox<SearchCriterion>(searcCriterions);

		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();

		result.setWidth(100F, Unit.PERCENTAGE);

		return result;
	}

	private CompanyTypeDTO[] companiesTypes = null;

	private BbrComboBox<CompanyTypeDTO> getCompanyTypesComboBox()
	{
		BbrComboBox<CompanyTypeDTO> result = null;

		try
		{
			if (companiesTypes == null || companiesTypes.length == 0)
			{
				CompanyTypeArrayResultDTO userTypesResult = EJBFactory.getUserEJBFactory().getCompanyTypeReportManagerLocal(this.getBbrUIParent()).getCompanyTypesOfUserForFilter(this.getBbrUIParent().getUser().getId());
				companiesTypes = userTypesResult.getCompanyTypeDTOs();
			}

			result = new BbrComboBox<CompanyTypeDTO>(companiesTypes);

			result.setItemCaptionGenerator(CompanyTypeDTO::getName);
			result.setTextInputAllowed(false);
			result.setEmptySelectionAllowed(false);
			result.selectFirstItem();

			result.setWidth(100F, Unit.PERCENTAGE);
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
		}
		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private ProfileDTO[] profiles = null;

	private BbrComboBox<ProfileDTO> getProfilesComboBox()
	{
		BbrComboBox<ProfileDTO> result = null;

		try
		{
			if (profiles == null || profiles.length == 0)
			{
				ProfileArrayResultDTO profilesResult = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().getProfilesOfUserForFilter(this.getBbrUIParent().getUser().getId());
				profiles = profilesResult.getProfiles();
			}

			result = new BbrComboBox<ProfileDTO>(profiles);

			result.setItemCaptionGenerator(ProfileDTO::getName);
			result.setTextInputAllowed(false);
			result.setEmptySelectionAllowed(false);
			result.selectFirstItem();

			result.setWidth(100F, Unit.PERCENTAGE);
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
		}
		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private Component getFieldsComponents()
	{
		Component result = null;
		SearchCriterion criterion = cbx_FilterType.getSelectedValue();
		if (criterion != null)
		{
			switch (criterion.getId())
			{
				case 0: // User name
					txt_UserName = new BbrTextField();
					txt_UserName.setMaxLength(25);
					txt_UserName.setRestrict(RestrictRange.ALL_LETTERS_SPACE);
					txt_UserName.addStyleName(BbrStyles.BBR_FILTER_FIELDS);
					result = txt_UserName;
					break;
				case 1: // User last name
					txt_UserLastName = new BbrTextField();
					txt_UserLastName.setMaxLength(25);
					txt_UserLastName.setRestrict(RestrictRange.ALL_LETTERS_SPACE);
					txt_UserLastName.addStyleName(BbrStyles.BBR_FILTER_FIELDS);
					result = txt_UserLastName;
					break;
				case 2: // User Type
					cbx_UserTypes = this.getCompanyTypesComboBox();
					result = cbx_UserTypes;
					break;
				case 3: // User state
					cbx_UserState = this.getUserStatesComboBox();
					result = cbx_UserState;
					break;
				case 4: // User enterprise
					sfdEnterprise = new BbrItemSelectField<CompanyDataDTO>();
					sfdEnterprise.addBbrEventListener((BbrEventListener & Serializable) e -> userEnterprise_handler(e));
					sfdEnterprise.setFieldName("name");
					sfdEnterprise.setDescriptionName("name");
					sfdEnterprise.addStyleName(BbrStyles.BBR_FILTER_FIELDS);
					sfdEnterprise.addStyleName("fixed-item-select-field-label");
					result = sfdEnterprise;
					break;
				case 5: // Provider Access
					sfdProvider = new BbrItemSelectField<CompanyDataDTO>();
					sfdProvider.addBbrEventListener((BbrEventListener & Serializable) e -> userProvider_handler(e));
					sfdProvider.setFieldName("name");
					sfdProvider.setDescriptionName("name");
					sfdProvider.addStyleName(BbrStyles.BBR_FILTER_FIELDS);
					sfdProvider.addStyleName("fixed-item-select-field-label");
					result = sfdProvider;
					break;
				case 6: // User Profile
					cbx_Profile = this.getProfilesComboBox();
					result = cbx_Profile;
					break;
				case 7: // User Id
					txt_UserId = new BbrTextField();
					txt_UserId.setMaxLength(25);
					txt_UserId.addStyleName(BbrStyles.BBR_FILTER_FIELDS);
					result = txt_UserId;
					break;

				default:
					break;
			}
		}
		result.setWidth("255px");
		return result;
	}

	private String validateData()
	{
		String result = "";

		SearchCriterion criterion = cbx_FilterType.getSelectedValue();
		if (criterion != null)
		{
			switch (criterion.getId())
			{
				case 0: // User name
					String userName = this.txt_UserName.getValue().trim();
					if (userName == null || userName.length() < 3)
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_required");
					}
					else
					{
						result = (!validateExtendedTextFieldNoSpace(userName, 50)) ? I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_only_letters")
								: "";
					}

					break;
				case 1: // User last name
					String userLastName = this.txt_UserLastName.getValue().trim();
					if (userLastName == null || userLastName.length() < 3)
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_required");
					}
					else
					{
						result = (!validateExtendedTextFieldNoSpace(userLastName, 50))
								? I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_only_letters") : "";
					}

					break;
				case 2: // User type
					CompanyTypeDTO user = this.cbx_UserTypes.getSelectedValue();
					result = (user == null) ? I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields") : "";
					break;
				case 3: // User state
					SearchCriterion userState = this.getUserStatesComboBox().getSelectedValue();
					result = (userState == null) ? I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields") : "";
					break;
				case 4: // User enterprise
					CompanyDataDTO selectEnterprise = sfdEnterprise.getValue();
					result = (selectEnterprise == null) ? I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields") : "";
					break;
				case 5: // Provider Access
					CompanyDataDTO selectProvider = sfdProvider.getValue();
					result = (selectProvider == null) ? I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields") : "";
					break;
				case 6: // Profile
					ProfileDTO profile = this.cbx_Profile.getSelectedValue();
					result = (profile == null) ? I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields") : "";
					break;
				case 7: // User Id
					String userId = this.txt_UserId.getValue().trim();
					if (userId == null || userId.length() < 3)
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_required");
					}
					else
					{
						result = (!validateRutTextField(userId, 9)) ? I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_invalid_search") : "";
					}
					break;

				default:
					break;
			}
		}

		return result;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void userEnterprise_handler(BbrEvent event)
	{
		FindProvider winFindCompany = new FindProvider(getBbrUIParent(), true);
		winFindCompany.initializeView();
		winFindCompany.addBbrEventListener((BbrEventListener & Serializable) e -> companySelected_handler(e));
		winFindCompany.open(true);
	}

	private void userProvider_handler(BbrEvent event)
	{
		FindProvider winFindCompany = new FindProvider(getBbrUIParent(), true);
		winFindCompany.initializeView();
		winFindCompany.addBbrEventListener((BbrEventListener & Serializable) e -> providerSelected_handler(e));
		winFindCompany.open(true);
	}

	private void companySelected_handler(BbrEvent event)
	{
		CompanyDataDTO company = (CompanyDataDTO) event.getResultObject();

		if (cmp_FilterFields != null && (cmp_FilterFields instanceof BbrItemSelectField))
		{
			sfdEnterprise.setValue(company);
		}

	}

	private void providerSelected_handler(BbrEvent event)
	{
		CompanyDataDTO company = (CompanyDataDTO) event.getResultObject();

		if (cmp_FilterFields != null && (cmp_FilterFields instanceof BbrItemSelectField))
		{
			sfdProvider.setValue(company);
		}

	}

	private Boolean validateExtendedTextFieldNoSpace(String fieldValue, int maxLength)
	{
		Boolean result = false;
		if (fieldValue != null && fieldValue.length() <= maxLength)
		{
			result = BbrUtils.getInstance().isAlphaNumericWithSpecialNoSpaceCharsRegEx(fieldValue);
		}
		return result;
	}

	private Boolean validateRutTextField(String fieldValue, int maxLength)
	{
		Boolean result = false;
		if (fieldValue != null && fieldValue.length() <= maxLength)
		{
			result = BbrUtils.getInstance().isRUTRegEx(fieldValue);
		}
		return result;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

}
