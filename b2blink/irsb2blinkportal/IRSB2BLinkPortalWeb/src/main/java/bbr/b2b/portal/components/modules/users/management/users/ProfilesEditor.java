package bbr.b2b.portal.components.modules.users.management.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ProfileArrayResultDTO;
import bbr.b2b.users.report.classes.ProfileDTO;
import bbr.b2b.users.report.classes.UserDTO;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.interfaces.EntityEditor;

/**
 * @author DSU 2017-04
 */
public class ProfilesEditor extends BbrWindow implements EntityEditor<UserDTO>
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long			serialVersionUID			= 3247626779164695209L;

	private UserDTO						item						= null;

	private BbrAdvancedGrid<ProfileDTO>	dgd_AvailableProfiles;
	private BbrAdvancedGrid<ProfileDTO>	dgd_AssignedProfiles;

	private ArrayList<ProfileDTO>		assignedProfiles			= null;
	private ArrayList<ProfileDTO>		availableProfiles			= null;

	private HashMap<Long, ProfileDTO>	originalAssignedProfiles	= new HashMap<>();

	private Button						btnAddItem					= new Button(VaadinIcons.ANGLE_RIGHT);
	private Button						btnAddItems					= new Button(VaadinIcons.ANGLE_DOUBLE_RIGHT);
	private Button						btnRemoveItem				= new Button(VaadinIcons.ANGLE_LEFT);
	private Button						btnRemoveItems				= new Button(VaadinIcons.ANGLE_DOUBLE_LEFT);

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public ProfilesEditor(BbrUI parent, UserDTO selectedUser)
	{
		super(parent);
		this.setItem(selectedUser);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		this.updateAssignedProfiles();
		this.updateAvailableProfiles();

		// -------------PROFILES PANELS-------------//

		// Available Panel
		Label lblAvailables = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "availables_profiles"));

		this.dgd_AvailableProfiles = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.initializeAvailableProfilesGridColumns();
		this.dgd_AvailableProfiles.setSortable(false);
		this.dgd_AvailableProfiles.setSelectionMode(SelectionMode.MULTI);
		this.dgd_AvailableProfiles.addStyleName("report-grid");
		this.dgd_AvailableProfiles.addStyleName("bbr-multi-grid");
		this.dgd_AvailableProfiles.setSizeFull();
		this.dgd_AvailableProfiles.addSelectionListener((SelectionListener<ProfileDTO> & Serializable) e -> addProfile_clickHandler(e));

		VerticalLayout availablePanel = new VerticalLayout();
		availablePanel.setSizeFull();
		availablePanel.addComponents(lblAvailables, this.dgd_AvailableProfiles);
		availablePanel.setMargin(false);
		availablePanel.setExpandRatio(this.dgd_AvailableProfiles, 1F);

		// Assigned Panel
		Label lblAssigned = new Label("Perfiles Usuario");

		this.dgd_AssignedProfiles = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.initializeAssignedProfilesGridColumns();

		this.dgd_AssignedProfiles.setSortable(false);
		this.dgd_AssignedProfiles.setSelectionMode(SelectionMode.MULTI);
		this.dgd_AssignedProfiles.addStyleName("report-grid");
		this.dgd_AssignedProfiles.addStyleName("bbr-multi-grid");
		this.dgd_AssignedProfiles.setSizeFull();
		this.dgd_AssignedProfiles.addSelectionListener((SelectionListener<ProfileDTO> & Serializable) e -> removeProfile_clickHandler(e));

		VerticalLayout assignedPanel = new VerticalLayout();
		assignedPanel.setSizeFull();
		assignedPanel.addComponents(lblAssigned, this.dgd_AssignedProfiles);
		assignedPanel.setMargin(false);
		assignedPanel.setExpandRatio(this.dgd_AssignedProfiles, 1F);

		// Operations Buttons Panel
		VerticalLayout operationsButtonsPanel = new VerticalLayout();
		operationsButtonsPanel.setWidth("50px");
		operationsButtonsPanel.setMargin(false);

		this.btnAddItems.addClickListener((ClickListener & Serializable) e -> addAllProfiles_clickHandler(e));
		this.btnRemoveItems.addClickListener((ClickListener & Serializable) e -> removeAllProfiles_clickHandler(e));

		operationsButtonsPanel.addComponents(this.btnAddItems, this.btnRemoveItems);
		operationsButtonsPanel.setComponentAlignment(this.btnAddItems, Alignment.MIDDLE_CENTER);
		operationsButtonsPanel.setComponentAlignment(this.btnRemoveItems, Alignment.MIDDLE_CENTER);

		HorizontalLayout profilesPanel = new HorizontalLayout();
		profilesPanel.setHeight("100%");
		profilesPanel.setWidth("100%");
		profilesPanel.addStyleName("bbr-filter-sections");
		profilesPanel.addStyleName("bbr-panel-space");
		profilesPanel.addStyleName("bbr-margin-panel-zero-top");

		profilesPanel.addComponents(availablePanel, operationsButtonsPanel, assignedPanel);
		profilesPanel.setExpandRatio(availablePanel, 1F);
		profilesPanel.setExpandRatio(assignedPanel, 1F);
		profilesPanel.setComponentAlignment(operationsButtonsPanel, Alignment.MIDDLE_CENTER);

		HorizontalLayout formLayout = new HorizontalLayout(profilesPanel);
		formLayout.setExpandRatio(profilesPanel, 1F);
		formLayout.setSizeFull();
		formLayout.setSpacing(true);

		// -------------BUTTONS PANEL-------------//
		Button btn_UpdateProfiles = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "save"));
		btn_UpdateProfiles.setStyleName("primary");
		btn_UpdateProfiles.addStyleName("btn-login");
		btn_UpdateProfiles.setWidth("150px");
		btn_UpdateProfiles.addClickListener((ClickListener & Serializable) e -> btnUpdateProfiles_clickHandler(e));
		btn_UpdateProfiles.setClickShortcut(KeyCode.ENTER);

		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-login");
		btn_Cancel.setWidth("150px");
		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));

		HorizontalLayout buttonsPanel = new HorizontalLayout(btn_UpdateProfiles, btn_Cancel);
		buttonsPanel.setComponentAlignment(btn_UpdateProfiles, Alignment.MIDDLE_LEFT);
		buttonsPanel.setComponentAlignment(btn_Cancel, Alignment.MIDDLE_RIGHT);
		buttonsPanel.setWidth("400px");
		buttonsPanel.addStyleName("bbr-margin-panel");
		buttonsPanel.setSpacing(true);

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(formLayout, buttonsPanel);
		mainLayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);
		mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
		mainLayout.setExpandRatio(formLayout, 1F);
		mainLayout.setSpacing(true);
		mainLayout.addStyleName("bbr-margin-windows");

		this.updateDataSourceAndUI();
		// Main Windows
		this.setWidth("1200px");
		this.setHeight("500px");
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_profile_editor"));
		this.setContent(mainLayout);
	}

	private void initializeAvailableProfilesGridColumns()
	{
		this.dgd_AvailableProfiles.addColumn(ProfileDTO::getName)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"))
				.setDescriptionGenerator(ProfileDTO::getName)
				.setWidth(180F);
		this.dgd_AvailableProfiles.addColumn(ProfileDTO::getDescription)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"))
				.setDescriptionGenerator(ProfileDTO::getDescription);
	}

	private void initializeAssignedProfilesGridColumns()
	{
		this.dgd_AssignedProfiles.addColumn(ProfileDTO::getName)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"))
				.setDescriptionGenerator(ProfileDTO::getName)
				.setWidth(180F);
		this.dgd_AssignedProfiles.addColumn(ProfileDTO::getDescription)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"))
				.setDescriptionGenerator(ProfileDTO::getDescription);
	}

	@Override
	public void setItem(UserDTO item)
	{
		this.item = item;
	}

	@Override
	public UserDTO getItem()
	{
		return item;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	private void btnClose_clickHandler(ClickEvent event)
	{
		this.close();
	}

	private void btnUpdateProfiles_clickHandler(ClickEvent e)
	{
		Long[] toadd = this.getProfilesToAdd();
		Long[] toremove = this.getProfilesToRemove();
		this.doUpdateProfiles(toadd, toremove);
	}

	private void addProfile_clickHandler(SelectionEvent<ProfileDTO> e)
	{
		ArrayList<ProfileDTO> items = new ArrayList<>(dgd_AvailableProfiles.getSelectedItems());
		for (ProfileDTO profile : items)
		{
			profile = removeAvailableProfileById(profile.getId());

			if (assignedProfiles == null)
			{
				assignedProfiles = new ArrayList<>();
			}

			assignedProfiles.add(profile);
		}

		updateDataSourceAndUI();
	}

	private void addAllProfiles_clickHandler(ClickEvent e)
	{
		if (availableProfiles != null && availableProfiles.size() > 0)
		{
			for (ProfileDTO profile : availableProfiles)
			{
				if (assignedProfiles == null)
				{
					assignedProfiles = new ArrayList<>();
				}

				assignedProfiles.add(profile);
			}

			availableProfiles = new ArrayList<>();

			updateDataSourceAndUI();
		}
	}

	private void removeProfile_clickHandler(SelectionEvent<ProfileDTO> e)
	{
		ArrayList<ProfileDTO> items = new ArrayList<>(dgd_AssignedProfiles.getSelectedItems());
		for (ProfileDTO profile : items)
		{
			profile = removeAssignedProfileById(profile.getId());

			if (availableProfiles == null)
			{
				availableProfiles = new ArrayList<>();
			}

			availableProfiles.add(profile);
		}

		updateDataSourceAndUI();
	}

	private void removeAllProfiles_clickHandler(ClickEvent e)
	{
		if (assignedProfiles != null && assignedProfiles.size() > 0)
		{
			for (ProfileDTO profile : assignedProfiles)
			{
				if (availableProfiles == null)
				{
					availableProfiles = new ArrayList<>();
				}

				availableProfiles.add(profile);
			}

			assignedProfiles = new ArrayList<>();

			updateDataSourceAndUI();
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private void doUpdateProfiles(Long[] toAdd, Long[] toRemove)
	{
		String message = "";
		try
		{
			if (toAdd != null && toAdd.length > 0)
			{
				BaseResultDTO addResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().addUserProfileRelations(this.item.getId(), toAdd, this.getUser().getId());
				if (addResult != null)
				{
					message = I18NManager.getErrorMessageBaseResult(addResult); // <--
																				// Obtiene
																				// el
																				// mensaje
																				// de
																				// error.
																				// ""
																				// si
																				// no
																				// hay
																				// errores.
				}
				else
				{
					// -> Error userResult = null
					message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
				}
			}
			if (message.length() <= 0 && toRemove != null && toRemove.length > 0)
			{
				BaseResultDTO removeResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().deleteUserProfileRelations(this.item.getId(), toRemove, this.getUser().getId());
				if (removeResult != null)
				{
					message = I18NManager.getErrorMessageBaseResult(removeResult); // <--
																					// Obtiene
																					// el
																					// mensaje
																					// de
																					// error.
																					// ""
																					// si
																					// no
																					// hay
																					// errores.
				}
				else
				{
					// -> Error userResult = null
					message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
				}
			}

		}
		catch (Exception e) // Error no controlado
		{
			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}

		if (message.length() > 0)
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
		}
		else
		{
			message = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation");
			this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), message);
			BbrEvent editEvent = new BbrEvent(BbrEvent.ITEM_UPDATED);
			this.dispatchBbrEvent(editEvent);
			this.close();
		}
	}

	private void updateAssignedProfiles()
	{
		assignedProfiles = null;

		try
		{
			ProfileArrayResultDTO profilesResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getAssignedProfilesOfUser(this.getItem().getId(), 0, 0, false, false, null);

			if (profilesResult != null && profilesResult.getProfiles() != null)
			{
				assignedProfiles = new ArrayList<>(Arrays.asList(profilesResult.getProfiles()));

				if (assignedProfiles != null && assignedProfiles.size() > 0)
				{
					for (ProfileDTO profile : assignedProfiles)
					{
						originalAssignedProfiles.put(profile.getId(), profile);
					}
				}
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
	}

	private void updateAvailableProfiles()
	{
		availableProfiles = null;

		try
		{
			ProfileArrayResultDTO profilesResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getAvailableProfilesOfUser(this.getItem().getId());

			if (profilesResult != null && profilesResult.getProfiles() != null)
			{
				availableProfiles = new ArrayList<>(Arrays.asList(profilesResult.getProfiles()));
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
	}

	private ProfileDTO removeAvailableProfileById(Long profileId)
	{
		ProfileDTO result = null;

		if (availableProfiles != null && availableProfiles.size() > 0)
		{
			result = availableProfiles.stream()
					.filter(profile -> profile.getId().equals(profileId))
					.findFirst().map(p ->
					{
						availableProfiles.remove(p);
						return p;
					}).get();
		}

		return result;
	}

	private ProfileDTO removeAssignedProfileById(Long profileId)
	{
		ProfileDTO result = null;

		if (assignedProfiles != null && assignedProfiles.size() > 0)
		{
			result = assignedProfiles.stream()
					.filter(profile -> profile.getId().equals(profileId))
					.findFirst().map(p ->
					{
						assignedProfiles.remove(p);
						return p;
					}).get();
		}

		return result;
	}

	private void updateDataSourceAndUI()
	{
		ListDataProvider<ProfileDTO> assignedDataprovider = new ListDataProvider<>(assignedProfiles);
		dgd_AssignedProfiles.setDataProvider(assignedDataprovider);

		ListDataProvider<ProfileDTO> availableDataprovider = new ListDataProvider<>(availableProfiles);
		dgd_AvailableProfiles.setDataProvider(availableDataprovider);

		btnAddItem.setEnabled((availableProfiles != null && availableProfiles.size() > 0));
		btnAddItems.setEnabled((availableProfiles != null && availableProfiles.size() > 0));
		btnRemoveItem.setEnabled((assignedProfiles != null && assignedProfiles.size() > 0));
		btnRemoveItems.setEnabled((assignedProfiles != null && assignedProfiles.size() > 0));
	}

	private Long[] getProfilesToAdd()
	{
		Long[] result = null;
		ArrayList<Long> profiles = new ArrayList<>();

		if (assignedProfiles != null && assignedProfiles.size() > 0)
		{
			for (ProfileDTO item : assignedProfiles)
			{
				if (originalAssignedProfiles.get(item.getId()) == null)
				{
					profiles.add(item.getId());
				}
			}
		}

		if (profiles.size() > 0)
		{
			result = new Long[profiles.size()];

			result = profiles.toArray(result);
		}

		return result;
	}

	private Long[] getProfilesToRemove()
	{
		Long[] result = null;
		ArrayList<Long> profiles = new ArrayList<>();

		if (availableProfiles != null && availableProfiles.size() > 0)
		{
			for (ProfileDTO item : availableProfiles)
			{
				if (originalAssignedProfiles.get(item.getId()) != null)
				{
					profiles.add(item.getId());
				}
			}
		}

		if (profiles.size() > 0)
		{
			result = new Long[profiles.size()];

			result = profiles.toArray(result);
		}

		return result;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
