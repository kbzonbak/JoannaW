package bbr.b2b.portal.components.modules.users.management.notifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.AssignedProfilesArrayResultDTO;
import bbr.b2b.users.report.classes.ProfileArrayResultDTO;
import bbr.b2b.users.report.classes.ProfileDTO;
import bbr.b2b.users.report.classes.RuleTypeDTO;
import bbr.b2b.users.report.classes.UserPublishingRelationshipsInitParamsDTO;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;

public class EditProfilesSubscription extends BbrWindow 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = 3247626779164695209L;

	private Long 				publishingId;
	private RuleTypeDTO 		rule;

	private BbrAdvancedGrid<ProfileDTO> dgd_Profiles ;

	private Map<Long,ProfileDTO> mapProfiles = null;

	private ArrayList<ProfileDTO> profiles = null;


	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	public EditProfilesSubscription(BbrUI parent, Long publishingId, RuleTypeDTO rule) 
	{
		super(parent);
		this.publishingId = publishingId;
		this.rule = rule;
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView() 
	{
		if(this.publishingId != null)
		{
			//Grilla
			this.dgd_Profiles = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.initializeGridColumns();
			this.dgd_Profiles.addStyleName("report-grid");
			this.dgd_Profiles.addStyleName("bbr-multi-grid");
			this.dgd_Profiles.setHeight("100%");
			this.dgd_Profiles.setWidth("100%");
			this.dgd_Profiles.setLocale(this.getUser().getLocale());
			this.dgd_Profiles.setSelectionMode(SelectionMode.MULTI);

			//Accept Button
			Button btn_Create = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"));
			btn_Create.setStyleName("primary");
			btn_Create.addStyleName("btn-generic");
			btn_Create.setWidth("100%");
			btn_Create.addClickListener((ClickListener & Serializable) e -> btnCreate_clickHandler(e));

			//Cancel Button
			Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
			btn_Cancel.setStyleName("primary");
			btn_Cancel.addStyleName("btn-generic");
			btn_Cancel.setWidth("100%");
			btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));

			HorizontalLayout buttonsPanel = new HorizontalLayout(btn_Create, btn_Cancel);
			buttonsPanel.addStyleName("bbr-buttons-panel");

			buttonsPanel.setWidth("300px");
			buttonsPanel.setSpacing(true);

			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.setSizeFull();
			mainLayout.addComponents(this.dgd_Profiles,buttonsPanel);
			mainLayout.addStyleName("bbr-margin-windows");
			mainLayout.setExpandRatio(this.dgd_Profiles, 1F);
			mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
			mainLayout.setMargin(false);
			this.updateAllProfiles();
			this.updateAssignedProfiles();

			//Main Windows
			this.setWidth("800px");
			this.setHeight("550px");
			this.setResizable(false);
			this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "create_notification_win_title") + " : " + rule.getDescription());
			this.setContent(mainLayout);
		}

	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			OVERRIDDEN METHODS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			EVENTS HANDLERS
	// ****************************************************************************************
	private void btnCreate_clickHandler(ClickEvent e) 
	{
		this.addProfilesSuscriptions();
	}

	private void btnClose_clickHandler(ClickEvent e) 
	{
		this.close();
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PRIVATE METHODS
	// ****************************************************************************************
	private void initializeGridColumns()
	{
		this.dgd_Profiles.addColumn(ProfileDTO::getName)
		.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "name"));
		this.dgd_Profiles.addColumn(ProfileDTO::getDescription)
		.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "systems_profiles"));
		this.dgd_Profiles.setFrozenColumnCount(1);
	}

	private void updateAllProfiles() 
	{
		try 
		{
			ProfileArrayResultDTO profilesResult = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().getProfiles();

			if(profilesResult != null && profilesResult.getProfiles() != null && profilesResult.getProfiles().length > 0)
			{
				this.profiles = new ArrayList<>(Arrays.asList(profilesResult.getProfiles()));
				
				ListDataProvider<ProfileDTO> dataprovider = new ListDataProvider<>(this.profiles);
				this.dgd_Profiles.setDataProvider(dataprovider);
				
				this.mapProfiles = new HashMap<>();
				for (ProfileDTO profile : this.profiles) 
				{
					this.mapProfiles.put(profile.getId(), profile);
				}
			}
		}
		catch (BbrUserException ex) 
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), this.getBbrUIParent());
		} 
		catch (BbrSystemException ex) 
		{
			ex.printStackTrace();
		}	

	}

	private void updateAssignedProfiles() 
	{
		try 
		{
			AssignedProfilesArrayResultDTO profilesResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getAssignedProfilesToPublishing(publishingId);

			if(profilesResult != null && profilesResult.getAssignedProfiles() != null && profilesResult.getAssignedProfiles().length > 0)
			{
				for (ProfileDTO profile : profilesResult.getAssignedProfiles()) 
				{
					ProfileDTO selectedProfile = this.mapProfiles.get(profile.getId());
					if(selectedProfile != null)
					{
						this.dgd_Profiles.select(selectedProfile);
					}
				}
			}
		}
		catch (BbrUserException ex) 
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), this.getBbrUIParent());
		} 
		catch (BbrSystemException ex) 
		{
			ex.printStackTrace();
		}		
	}

	
	private Long[] getProfilesIds()
	{
		Long[]  profilesIds = null;

		Set<ProfileDTO> profiles = this.dgd_Profiles.getSelectedItems();
		int i = 0;

		if(profiles != null && profiles.size() > 0)
		{
			profilesIds = new Long[profiles.size()];
			
			for (ProfileDTO profile : profiles) 
			{
				profilesIds[i] = profile.getId();
				i++;
			}
		}

		return profilesIds;
	}

	private void addProfilesSuscriptions()
	{
		String message = "";
		BaseResultsDTO 	publishingResult = null ;
		try 
		{
			Long[] profilesKeys = this.getProfilesIds();

			if(profilesKeys != null && profilesKeys.length > 0)
			{
				UserPublishingRelationshipsInitParamsDTO initParams = new UserPublishingRelationshipsInitParamsDTO();
				
				initParams.setPublishingId(this.publishingId);
				initParams.setRuleTypeId(this.rule.getId());
				initParams.setKeys(profilesKeys);
				
				
				publishingResult 	= EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doAddUserPublicationRelationshipsByRule(initParams);

				if(publishingResult != null)
				{
					message = I18NManager.getErrorMessageBaseResult(publishingResult, publishingResult.getParams()); // <-- Obtiene el mensaje de error. "" si no hay errores.
				}
				else
				{
					// -> Error companyResult = null
					message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
				}
			}
			else
			{
				message = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_profile_at_least");
			}

		} 
		catch (Exception e) //Error no controlado
		{
			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}

		if(message.length() > 0)
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
		}
		else
		{
			this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_question"), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation"));
			this.close();
		}
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			PRIVATE METHODS
	// ****************************************************************************************



}
