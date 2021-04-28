package bbr.b2b.portal.components.modules.users.management.user_request;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Function;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.PositionDTO;
import bbr.b2b.users.report.classes.ProfileDetailRequestDTO;
import bbr.b2b.users.report.classes.RequestDetailReportInitParamDTO;
import bbr.b2b.users.report.classes.RequestDetailReportResultDTO;
import bbr.b2b.users.report.classes.VendorDetailRequestDTO;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrTabContent;
import cl.bbr.core.components.basics.BbrTabSheet;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;

public class DetailsUserRequest extends BbrWindow implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long							serialVersionUID			= -1815292288128411410L;
	private static final String							PROFILE_NAME				= "profile_name";
	private static final String							COMPANY_CODE				= "company_code";
	private static final String							COMPANY_NAME				= "company_name";
	private static final String							COMPANY_ASSIGNED			= "company_assigned";
	private static final String							PROFILE_DESCRIPTION			= "profile_description";
	private static final String							PROFILE_STATUS				= "profile_status";
	private static final String							PROFILE_ASSIGNED			= "profile_assigned";
	private BbrTextField								txt_PersonalId				= null;
	private BbrTextField								txt_Email					= null;
	private BbrTextField								txt_Name					= null;
	private BbrTextField								txt_LastName				= null;
	private BbrTextField								txt_Company					= null;
	private BbrTextField								txt_Phone					= null;

	private BbrTabSheet									tabNav_UserRequest			= null;
	private BbrTabContent								tabCont_UserRequestProfiles	= null;
	private BbrTabContent								tabCont_UserRequestVendors	= null;
	private BbrAdvancedGrid<ProfileDetailRequestDTO>	dgd_Profiles				= null;
	private BbrAdvancedGrid<VendorDetailRequestDTO>		dgd_Vendors					= null;

	private BbrComboBox<PositionDTO>					cbx_Position				= null;
	private BbrWork<RequestDetailReportResultDTO>		positionsWork				= null;
	private boolean										resetPageInfo;
	private Long										userRequestSelected			= null;

	// ==============================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public DetailsUserRequest(BbrUI parentUI, Long userRequestSelected)
	{
		super(parentUI);
		this.userRequestSelected = userRequestSelected;
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================
	@Override
	public void initializeView()
	{
		FormLayout pnlHeader = this.getUserRequestHeader();

		HorizontalLayout pnlTabs = this.getUserRequestTabs();

		HorizontalLayout buttonsBar = new HorizontalLayout();

		Button btn_Close = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "close"));
		btn_Close.addClickListener((ClickListener & Serializable) e -> this.close());
		btn_Close.setWidth("120px");
		btn_Close.addStyleName("btn-generic");

		buttonsBar.addComponent(btn_Close);
		buttonsBar.setStyleName("bbr-buttons-panel");
		buttonsBar.setMargin(false);

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout(pnlHeader, pnlTabs, buttonsBar);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
		mainLayout.addStyleName("bbr-margin-windows-zero-top-bottom");
		mainLayout.setComponentAlignment(buttonsBar, Alignment.MIDDLE_CENTER);
		mainLayout.setExpandRatio(pnlTabs, 1F);

		// Ventana
		this.setWidth("45%");
		this.setHeight("600px");
		this.setResizable(true);
		this.setResponsive(true);
		String selectedId = this.userRequestSelected.toString();
		String caption = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_user_request_detail", selectedId);
		this.setCaption(caption);
		this.setContent(mainLayout);

		this.positionsWork = new BbrWork<RequestDetailReportResultDTO>(this, this.getBbrUIParent(), this);
		this.positionsWork.addFunction(new Function<Object, RequestDetailReportResultDTO>()
		{
			@Override
			public RequestDetailReportResultDTO apply(Object t)
			{
				return executeService(DetailsUserRequest.this.getBbrUIParent());
			}
		});

		this.startWaiting();
		this.resetPageInfo = true;
		this.executeBbrWork(this.positionsWork);

	}

	private RequestDetailReportInitParamDTO getInitParam()
	{
		RequestDetailReportInitParamDTO initParam = new RequestDetailReportInitParamDTO();
		initParam.setRequestid(this.userRequestSelected);
		return initParam;
	}

	private HorizontalLayout getUserRequestTabs()
	{
		this.tabNav_UserRequest = new BbrTabSheet();
		this.tabNav_UserRequest.setSizeFull();
		this.tabNav_UserRequest.addStyleName("main-tab-sheet");

		this.dgd_Profiles = new BbrAdvancedGrid<>(this.getBbrUIParent().getLocale());
		this.dgd_Profiles.setSizeFull();
		this.dgd_Profiles.addStyleName("report-grid");
		this.dgd_Profiles.setEnabled(true);
		this.initializeUserProfileDataGridColumns();
		this.dgd_Profiles.addSelectionListener((SelectionListener<ProfileDetailRequestDTO> & Serializable) e -> this.deselectProfiles_Handler());

		this.dgd_Vendors = new BbrAdvancedGrid<>(this.getBbrUIParent().getLocale());
		this.dgd_Vendors.setSizeFull();
		this.dgd_Vendors.addStyleName("report-grid");
		this.dgd_Vendors.setEnabled(true);
		this.initializeUserCompanyDataGridColumns();
		this.dgd_Vendors.addSelectionListener((SelectionListener<VendorDetailRequestDTO> & Serializable) e -> this.deselectVendors_Handler());

		this.tabCont_UserRequestProfiles = new BbrTabContent();
		this.tabCont_UserRequestProfiles.addComponents(this.dgd_Profiles);
		this.tabCont_UserRequestProfiles.setSizeFull();

		this.tabCont_UserRequestVendors = new BbrTabContent();
		this.tabCont_UserRequestVendors.addComponents(this.dgd_Vendors);
		this.tabCont_UserRequestVendors.setSizeFull();

		this.tabNav_UserRequest.addBbrTab(this.tabCont_UserRequestProfiles, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "profiles"), false);
		this.tabNav_UserRequest.addBbrTab(this.tabCont_UserRequestVendors, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "vendors"), false);

		HorizontalLayout tabs = new HorizontalLayout(this.tabNav_UserRequest);
		tabs.setSizeFull();
		tabs.setSpacing(false);
		return tabs;
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor senderPostExecute, Object triggerObject)
	{
		this.updateComboBox((RequestDetailReportResultDTO) result);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================
	private void deselectProfiles_Handler()
	{
		this.dgd_Profiles.deselectAll();
	}

	private void deselectVendors_Handler()
	{
		this.dgd_Vendors.deselectAll();
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private void initializeUserProfileDataGridColumns()
	{
		this.dgd_Profiles.addCustomColumn(p -> this.setNameAndSimbol(p), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "name"), true)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(PROFILE_NAME);

		this.dgd_Profiles.addCustomColumn(ProfileDetailRequestDTO::getProfiledesc, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"), true)
				.setWidth(300D)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(PROFILE_DESCRIPTION);

		this.dgd_Profiles.addComponentColumn(p -> this.setComboBoxColumn(p.getAssigned())).setStyleGenerator(item -> BbrAlignment.CENTER.getValue()).setWidth(65D)
				.setId(PROFILE_ASSIGNED);

		this.dgd_Profiles.addCustomColumn(p -> this.setApprovedByRetailStatusName(p), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "state"), true)
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue()).setWidth(120D).setId(PROFILE_STATUS);// STATUS/ESTADO
	}

	private CheckBox setComboBoxColumn(Integer p)
	{
		CheckBox result = new CheckBox();
		result.setCaption("");
		if (p == 1)
		{
			result.setValue(true);
		}
		result.setEnabled(false);
		return result;
	}

	private String setNameAndSimbol(ProfileDetailRequestDTO p)
	{
		String result = "";
		if (p.getStatus() == 0)// SIGNO JUNTO AL NOMBRE
		{
			result = p.getProfilename();
		}
		else if (p.getStatus() == -1)
		{
			result = p.getProfilename() + " [-]";
		}
		else
		{
			result = p.getProfilename() + " [+]";
		}
		return result;
	}

	private String setApprovedByRetailStatusName(ProfileDetailRequestDTO p)
	{
		String result = "";
		if (p.getApprovedbyretail() != null)
		{
			if (p.getApprovedbyretail() == 0)
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_request_rejected");
			}
			else if (p.getApprovedbyretail() == -1)
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_request_tocheck");
			}
			else if (p.getApprovedbyretail() == 1)
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_request_approved");
			}
		}
		return result;
	}

	private void initializeUserCompanyDataGridColumns()
	{
		this.dgd_Vendors.addCustomColumn(VendorDetailRequestDTO::getEmrut, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"), true)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(COMPANY_CODE);

		this.dgd_Vendors.addCustomColumn(VendorDetailRequestDTO::getEmdesc, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"), true)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(COMPANY_NAME);

		this.dgd_Vendors.addComponentColumn(p -> this.setComboBoxColumn(p.getAssigned())).setStyleGenerator(item -> BbrAlignment.CENTER.getValue()).setWidth(65D)
				.setId(COMPANY_ASSIGNED);
	}

	private FormLayout getUserRequestHeader()
	{
		this.txt_PersonalId = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id"));
		this.txt_PersonalId.setMaxLength(50);
		this.txt_PersonalId.setRequiredIndicatorVisible(true);
		this.txt_PersonalId.setRestrict(RestrictRange.RUT);
		this.txt_PersonalId.addStyleName("bbr-fields");
		this.txt_PersonalId.setEnabled(false);

		this.txt_Email = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "email"));
		this.txt_Email.setMaxLength(50);
		this.txt_Email.setRequiredIndicatorVisible(true);
		this.txt_Email.addStyleName("bbr-fields");
		this.txt_Email.setEnabled(false);

		this.txt_Name = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_name"));
		this.txt_Name.setMaxLength(50);
		this.txt_Name.setRequiredIndicatorVisible(true);
		this.txt_Name.setRestrict(RestrictRange.ALL_LETTERS);
		this.txt_Name.addStyleName("bbr-fields");
		this.txt_Name.setEnabled(false);

		this.txt_LastName = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastname"));
		this.txt_LastName.setMaxLength(50);
		this.txt_LastName.setRequiredIndicatorVisible(true);
		this.txt_LastName.setRestrict(RestrictRange.ALL_LETTERS);
		this.txt_LastName.addStyleName("bbr-fields");
		this.txt_LastName.setEnabled(false);

		this.txt_Phone = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "phone"));
		this.txt_Phone.setMaxLength(15);
		this.txt_Phone.setRestrict(RestrictRange.PHONE);
		this.txt_Phone.addStyleName("bbr-fields");
		this.txt_Phone.setEnabled(false);

		this.cbx_Position = new BbrComboBox<PositionDTO>(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position"));
		this.cbx_Position.setRequiredIndicatorVisible(true);
		this.cbx_Position.setTextInputAllowed(false);
		this.cbx_Position.setWidth("100%");
		this.cbx_Position.setEmptySelectionAllowed(false);
		this.cbx_Position.addStyleName("bbr-fields");
		this.cbx_Position.setEnabled(false);

		this.txt_Company = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_enterprise"));
		this.txt_Company.setRequiredIndicatorVisible(true);
		this.txt_Company.setValue(this.getBbrUIParent().getUser().getEnterpriseDescription());
		this.txt_Company.setWidth("100%");
		this.txt_Company.addStyleName("bbr-fields");
		this.txt_Company.setEnabled(false);

		FormLayout header = new FormLayout(this.txt_PersonalId, this.txt_Email, this.txt_Name, this.txt_LastName, this.txt_Phone, this.cbx_Position, this.txt_Company);
		header.setSpacing(false);
		return header;
	}

	private void updateComboBox(RequestDetailReportResultDTO resultDTO)
	{
		try
		{
			if (resultDTO != null)
			{
				RequestDetailReportResultDTO reportResult = (RequestDetailReportResultDTO) resultDTO;
				BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());
				if (!error.hasError())
				{
					this.txt_PersonalId.setValue((resultDTO.getRequestDetail().getPersonalId() != null) ? resultDTO.getRequestDetail().getPersonalId() : "");
					this.txt_Email.setValue((resultDTO.getRequestDetail().getEmail() != null) ? resultDTO.getRequestDetail().getEmail() : "");
					this.txt_Name.setValue((resultDTO.getRequestDetail().getName() != null) ? resultDTO.getRequestDetail().getName() : "");
					this.txt_LastName.setValue((resultDTO.getRequestDetail().getLastName() != null) ? resultDTO.getRequestDetail().getLastName() : "");
					this.txt_Phone.setValue((resultDTO.getRequestDetail().getPhone() != null) ? resultDTO.getRequestDetail().getPhone() : "");

					if (resultDTO.getRequestDetail().getPosition() != null)
					{
						String position = resultDTO.getRequestDetail().getPosition().trim();
						PositionDTO positionDTO = new PositionDTO();
						positionDTO.setName(position);
						if (position.length() > 0)
						{
							this.cbx_Position.addItem(positionDTO);
						}
						this.cbx_Position.setSelectedItem(positionDTO);
						this.cbx_Position.setItemCaptionGenerator(PositionDTO::getName);
					}
					else
					{
						this.cbx_Position.setEnabled(false);
					}
					if (resultDTO.getRequestDetail().getProfiles() != null)
					{
						ProfileDetailRequestDTO[] userProfileArrayResult = resultDTO.getRequestDetail().getProfiles();

						ListDataProvider<ProfileDetailRequestDTO> dataproviderUserProfile = new ListDataProvider<>(Arrays.asList(userProfileArrayResult));

						this.dgd_Profiles.setDataProvider(dataproviderUserProfile, this.resetPageInfo);
					}
					else
					{
						this.dgd_Profiles.setEnabled(false);
					}
					if (resultDTO.getRequestDetail() != null)
					{

						VendorDetailRequestDTO[] userCompanyArrayResult = resultDTO.getRequestDetail().getVendors();

						ListDataProvider<VendorDetailRequestDTO> dataproviderUserCompany = new ListDataProvider<>(Arrays.asList(userCompanyArrayResult));

						this.dgd_Vendors.setDataProvider(dataproviderUserCompany, this.resetPageInfo);

					}
					else
					{
						this.dgd_Profiles.setEnabled(false);
					}

				}
			}
			this.stopWaiting();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private RequestDetailReportResultDTO executeService(BbrUI parentUI)
	{
		RequestDetailReportResultDTO result = null;
		try
		{
			RequestDetailReportInitParamDTO initParam = this.getInitParam();
			result = EJBFactory.getUserEJBFactory().getRequestManagerLocal(parentUI).getRequestDetail(initParam);

		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), parentUI);
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

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
