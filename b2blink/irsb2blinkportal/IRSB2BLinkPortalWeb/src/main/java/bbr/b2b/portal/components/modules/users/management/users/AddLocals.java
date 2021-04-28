package bbr.b2b.portal.components.modules.users.management.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Predicate;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
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
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.LocationArrayResultDTO;
import bbr.b2b.users.report.classes.LocationDTO;
import bbr.b2b.users.report.classes.UserDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.sets.BbrSetsUtils;
import cl.bbr.core.classes.sets.EnumDifferenceState;
import cl.bbr.core.classes.sets.SetOperationResult;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.interfaces.EntityEditor;

/**
 * @author DSU 2017-04
 */
public class AddLocals extends BbrWindow implements EntityEditor<UserDTO>
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long				serialVersionUID	= 3247626779164695209L;

	private UserDTO							item				= null;

	private BbrAdvancedGrid<LocationDTO>	dgd_AssignedLocals;
	private BbrAdvancedGrid<LocationDTO>	dgd_FoundLocals;

	private ArrayList<LocationDTO>			dsAssignedLocals	= new ArrayList<>();
	private ArrayList<LocationDTO>			dsAvailableLocals	= null;

	private HashMap<Long, LocationDTO>		toAssignLocals		= new HashMap<>();
	private Set<LocationDTO>				setLastSelection	= null;

	private Button							btnAddItems			= new Button(VaadinIcons.ANGLE_DOUBLE_RIGHT);
	private BbrComboBox<SearchCriterion>	cbx_Criteria		= null;
	private BbrTextField					txt_Value			= null;
	private Label							lblCounter			= new Label();

	private final String					LOC_CODE_FIELD		= "id";
	private final int						DEFAULT_PAGE_NUMBER	= 1;
	private final int						MAX_ROWS_NUMBER		= 20;

	private BbrPagingManager				pagingManagerLocals;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public AddLocals(BbrUI parent, UserDTO selectedUser)
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
		// -------------FINDER PANEL-------------//
		cbx_Criteria = this.getSearchCriterionComboBox();

		txt_Value = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "value"));
		txt_Value.setWidth(100, Unit.PERCENTAGE);
		txt_Value.addStyleName("bbr-fields");
		txt_Value.focus();

		Button btn_Search = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "search"), VaadinIcons.SEARCH);
		btn_Search.setStyleName("btn-filter-search");
		btn_Search.setClickShortcut(KeyCode.ENTER);
		btn_Search.addClickListener((ClickListener & Serializable) e -> search_clickHandler(e));

		VerticalLayout finderPanel = new VerticalLayout();
		finderPanel.setHeight("100%");
		finderPanel.setWidth("180px");
		finderPanel.addComponents(cbx_Criteria, txt_Value, btn_Search);
		finderPanel.addStyleName("bbr-filter-sections");
		finderPanel.addStyleName("bbr-panel-space");
		finderPanel.addStyleName("bbr-margin-panel");
		finderPanel.setExpandRatio(btn_Search, 1F);
		finderPanel.setComponentAlignment(btn_Search, Alignment.TOP_RIGHT);

		// -------------COMPANIES PANELS-------------//

		// Available Panel
		// Paging Manager
		pagingManagerLocals = new BbrPagingManager();
		pagingManagerLocals.setLocale(this.getUser().getLocale());
		pagingManagerLocals.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> pagingLocalsChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		Label lblAvailables = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "found_locals"));

		HorizontalLayout pnlAvailables = new HorizontalLayout();
		pnlAvailables.setWidth("100%");
		pnlAvailables.setMargin(false);
		pnlAvailables.addComponents(lblAvailables);
		pnlAvailables.setComponentAlignment(lblAvailables, Alignment.MIDDLE_LEFT);
		pnlAvailables.setExpandRatio(lblAvailables, 1F);

		dgd_FoundLocals = new BbrAdvancedGrid<>(this.getUser().getLocale());
		dgd_FoundLocals.addColumn(LocationDTO::getCode).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"));
		dgd_FoundLocals.addColumn(LocationDTO::getName).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"));

		dgd_FoundLocals.setSortable(false);
		dgd_FoundLocals.setSelectionMode(SelectionMode.MULTI);
		dgd_FoundLocals.addStyleName("report-grid");
		dgd_FoundLocals.addStyleName("bbr-multi-grid");
		dgd_FoundLocals.setSizeFull();
		dgd_FoundLocals.setPagingManager(pagingManagerLocals, this.LOC_CODE_FIELD);
		dgd_FoundLocals.addSelectionListener((SelectionListener<LocationDTO> & Serializable) e -> selectedItem_handler(e));

		VerticalLayout availablePanel = new VerticalLayout();
		availablePanel.setSizeFull();
		availablePanel.addComponents(pnlAvailables, dgd_FoundLocals, pagingManagerLocals);
		availablePanel.setMargin(false);
		availablePanel.setExpandRatio(dgd_FoundLocals, 1F);

		// Assigned Panel
		Label lblAssigned = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "locals_to_assign"));

		Button btn_RemoveCompanies = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "remove_locals"));
		btn_RemoveCompanies.addStyleName("link-button");
		btn_RemoveCompanies.addClickListener((ClickListener & Serializable) e -> remove_clickHandler(e));
		btn_RemoveCompanies.setHeight("20px");
		Button btn_RemoveAllCompanies = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "remove_all_locals"));
		btn_RemoveAllCompanies.addStyleName("link-button");
		btn_RemoveAllCompanies.addClickListener((ClickListener & Serializable) e -> removeAll_clickHandler(e));
		btn_RemoveAllCompanies.setHeight("20px");

		HorizontalLayout pnlAssignedOperation = new HorizontalLayout();
		pnlAssignedOperation.setWidth("100%");
		pnlAssignedOperation.setHeight("20px");

		pnlAssignedOperation.setMargin(false);
		pnlAssignedOperation.addComponents(lblAssigned, btn_RemoveCompanies, btn_RemoveAllCompanies);
		pnlAssignedOperation.setComponentAlignment(lblAssigned, Alignment.MIDDLE_LEFT);
		pnlAssignedOperation.setComponentAlignment(btn_RemoveCompanies, Alignment.MIDDLE_RIGHT);
		pnlAssignedOperation.setComponentAlignment(btn_RemoveAllCompanies, Alignment.MIDDLE_RIGHT);
		pnlAssignedOperation.setExpandRatio(lblAssigned, 1F);

		dgd_AssignedLocals = new BbrAdvancedGrid<>(this.getUser().getLocale());
		dgd_AssignedLocals.addColumn(LocationDTO::getCode).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"));
		dgd_AssignedLocals.addColumn(LocationDTO::getName).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"));
		dgd_AssignedLocals.setSortable(false);
		dgd_AssignedLocals.setSelectionMode(SelectionMode.MULTI);
		dgd_AssignedLocals.addStyleName("report-grid");
		dgd_AssignedLocals.addStyleName("bbr-multi-grid");
		dgd_AssignedLocals.setSizeFull();

		HorizontalLayout pnlCounter = new HorizontalLayout();
		pnlCounter.setWidth("100%");
		pnlCounter.setHeight("30px");

		pnlCounter.addComponent(lblCounter);
		pnlCounter.setComponentAlignment(lblCounter, Alignment.MIDDLE_RIGHT);

		VerticalLayout assignedPanel = new VerticalLayout();
		assignedPanel.setSizeFull();
		assignedPanel.addComponents(pnlAssignedOperation, dgd_AssignedLocals, pnlCounter);
		assignedPanel.setMargin(false);
		assignedPanel.setExpandRatio(dgd_AssignedLocals, 1F);

		// Operations Buttons Panel
		VerticalLayout operationsButtonsPanel = new VerticalLayout();
		operationsButtonsPanel.setWidth("50px");
		operationsButtonsPanel.setMargin(false);

		btnAddItems.addClickListener((ClickListener & Serializable) e -> addAllLocals_clickHandler(e));

		operationsButtonsPanel.addComponent(btnAddItems);
		operationsButtonsPanel.setComponentAlignment(btnAddItems, Alignment.MIDDLE_CENTER);

		HorizontalLayout localsPanel = new HorizontalLayout();
		localsPanel.setHeight("100%");
		localsPanel.setWidth("100%");
		localsPanel.addStyleName("bbr-filter-sections");
		localsPanel.addStyleName("bbr-panel-space");
		localsPanel.addStyleName("bbr-margin-panel-zero-top");

		localsPanel.addComponents(availablePanel, operationsButtonsPanel, assignedPanel);
		localsPanel.setExpandRatio(availablePanel, 1F);
		localsPanel.setExpandRatio(assignedPanel, 1F);
		localsPanel.setComponentAlignment(operationsButtonsPanel, Alignment.MIDDLE_CENTER);

		HorizontalLayout formLayout = new HorizontalLayout(finderPanel, localsPanel);
		formLayout.setExpandRatio(localsPanel, 1F);
		formLayout.setSizeFull();
		formLayout.setSpacing(true);

		// -------------BUTTONS PANEL-------------//
		Button btn_UpdateLocals = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "save"));
		btn_UpdateLocals.setStyleName("primary");
		btn_UpdateLocals.addStyleName("btn-login");
		btn_UpdateLocals.setWidth("150px");
		btn_UpdateLocals.addClickListener((ClickListener & Serializable) e -> btnUpdateLocals_clickHandler(e));

		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-login");
		btn_Cancel.setWidth("150px");
		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));

		HorizontalLayout buttonsPanel = new HorizontalLayout(btn_UpdateLocals, btn_Cancel);
		buttonsPanel.setComponentAlignment(btn_UpdateLocals, Alignment.MIDDLE_LEFT);
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
		this.updateSelectionLabel();
		// Main Windows
		this.setWidth(1000, Unit.PIXELS);
		this.setHeight(500, Unit.PIXELS);
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_add_locals"));
		this.setContent(mainLayout);
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
	private void removeAll_clickHandler(ClickEvent e)
	{
		dsAssignedLocals = new ArrayList<>();

		toAssignLocals = new HashMap<>();

		dgd_FoundLocals.deselectAll();

		dgd_FoundLocals.resetSelection();

		setLastSelection = null;

		this.updateToAssignDataSource();
		this.updateSelectionLabel();
	}

	private void remove_clickHandler(ClickEvent e)
	{

		dgd_AssignedLocals.getSelectedItems().forEach(company ->
		{

			Predicate<LocationDTO> localPredicate = p -> p.getId().equals(company.getId());

			LocationDTO loc = toAssignLocals.remove(company.getId());

			dgd_FoundLocals.removeItemSelection(loc.getId());

			dsAssignedLocals.removeIf(localPredicate);
		});

		this.updateToAssignDataSource();
		this.updateSelectionLabel();
	}

	private void selectedItem_handler(SelectionEvent<LocationDTO> e)
	{
		Set<LocationDTO> selection = e.getAllSelectedItems();
		if (e.isUserOriginated())
		{
			updateToAssignSelection(selection);
		}
		this.setLastSelection = selection;
	}

	private void pagingLocalsChange_Listener(BbrPagingEvent e)
	{
		if (e != null && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && e.getResultObject() != null)
		{
			this.updateLocals(e.getResultObject().getCurrentPage(), false, true);
		}
	}

	private void btnClose_clickHandler(ClickEvent event)
	{
		this.close();
	}

	private void btnUpdateLocals_clickHandler(ClickEvent e)
	{
		if (dsAssignedLocals != null && dsAssignedLocals.size() > 0)
		{

			Long[] ids = new Long[dsAssignedLocals.size()];
			for (int i = 0; i < dsAssignedLocals.size(); i++)
			{
				ids[i] = new Long(dsAssignedLocals.get(i).getId());
			}
			this.doUpdateLocals(ids);
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "no_locals_selected"));
		}

	}

	private void addAllLocals_clickHandler(ClickEvent e)
	{
		if (dsAvailableLocals != null && dsAvailableLocals.size() > 0)
		{
			dsAvailableLocals.forEach(local ->
			{
				if (toAssignLocals.get(local.getId()) == null)
				{
					toAssignLocals.put(local.getId(), local);
					dsAssignedLocals.add(local);
				}
			});

			this.updateDataSourceAndUI();
			this.updateToAssignDataSource();
			this.updateSelectionLabel();
		}
	}

	private void search_clickHandler(ClickEvent e)
	{
		this.updateLocals(this.DEFAULT_PAGE_NUMBER, true, true);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private void doUpdateLocals(Long[] ids)
	{
		String message = "";
		try
		{
			if (ids != null && ids.length > 0)
			{
				BaseResultDTO addResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().addUserLocalRelations(this.item.getId(), ids, this.getUser().getId());
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

	private void updateLocals(int pageNumber, boolean resetPageInfo, boolean isPaginated)
	{
		String errorMessage = validateData();
		if (errorMessage == null || errorMessage.length() <= 0)
		{
			dsAvailableLocals = new ArrayList<>(Arrays.asList(new LocationDTO[0]));
			this.setLastSelection = null;
			try
			{
				String value = txt_Value.getValue().trim();
				SearchCriterion criterion = cbx_Criteria.getSelectedValue();
				LocationArrayResultDTO localsResult = null;
				if (criterion.getId() == 0)
				{
					localsResult = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().findLocalsLikeNameNotAssigned(this.getItem().getId(), value, pageNumber, this.MAX_ROWS_NUMBER, resetPageInfo, isPaginated, null);
				}
				else if (criterion.getId() == 1)
				{
					localsResult = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().findLocalsLikeCodeNotAssigned(this.getItem().getId(), value, pageNumber, this.MAX_ROWS_NUMBER, resetPageInfo, isPaginated, null);
				}
				else if (criterion.getId() == 2)
				{
					localsResult = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().findAllLocalsNotAssigned(this.getItem().getId(), pageNumber, this.MAX_ROWS_NUMBER, resetPageInfo, isPaginated, null);
				}

				if (localsResult != null && localsResult.getLocationDTOs() != null && localsResult.getLocationDTOs().length > 0)
				{
					dsAvailableLocals = new ArrayList<>(Arrays.asList(localsResult.getLocationDTOs()));

					if (resetPageInfo)
					{
						PageInfoDTO pageInfo = localsResult.getPageInfoDTO();

						BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
						pagingManagerLocals.setPages(bbrPage, resetPageInfo);

					}
				}
				else
				{
					this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "no_result"));
				}

				ListDataProvider<LocationDTO> dataprovider = new ListDataProvider<>(dsAvailableLocals);
				dgd_FoundLocals.setDataProvider(dataprovider);

			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
			}
			catch (BbrSystemException e)
			{
				e.printStackTrace();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMessage);
		}

		this.updateDataSourceAndUI();
		this.updateToAssignDataSource();
	}

	private void updateToAssignDataSource()
	{
		ListDataProvider<LocationDTO> dataprovider = new ListDataProvider<>(dsAssignedLocals);
		dgd_AssignedLocals.setDataProvider(dataprovider);
	}

	private void updateDataSourceAndUI()
	{
		btnAddItems.setEnabled((dsAvailableLocals != null && dsAvailableLocals.size() > 0));

		updateAvailableGridSelection();
	}

	private void updateAvailableGridSelection()
	{
		if (dsAvailableLocals != null && dsAvailableLocals.size() > 0)
		{
			dsAvailableLocals.forEach(company ->
			{
				if (toAssignLocals.get(company.getId()) != null)
				{
					dgd_FoundLocals.select(company);
				}
			});
		}

		this.setLastSelection = dgd_FoundLocals.getSelectedItems();
	}

	private BbrComboBox<SearchCriterion> getSearchCriterionComboBox()
	{
		BbrComboBox<SearchCriterion> result = null;

		SearchCriterion[] searcCriterions = SearchFilterUtils.getInstance().getUsersManagerCriteria();

		result = new BbrComboBox<SearchCriterion>(searcCriterions);

		result.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "criteria"));
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();
		result.addSelectionListener(new SingleSelectionListener<SearchCriterion>()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void selectionChange(SingleSelectionEvent<SearchCriterion> event)
			{
				if (event != null)
				{
					if (event.getValue().getId() == 2)
					{
						txt_Value.setEnabled(false);
					}
					else
					{
						txt_Value.setEnabled(true);
					}
				}
			}
		});
		result.setWidth(100F, Unit.PERCENTAGE);

		return result;
	}

	private String validateData()
	{
		SearchCriterion criterion = cbx_Criteria.getSelectedValue();
		String result = "";
		if (criterion.getId() != 2)
		{
			String value = txt_Value.getValue().trim();
			if (value.length() < 3)
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_required");
			}
		}

		return result;
	}

	private void updateToAssignSelection(Set<LocationDTO> selection)
	{
		SetOperationResult<LocationDTO> resultOperation = BbrSetsUtils.getInstance().difference(selection, this.setLastSelection);

		if (resultOperation != null && resultOperation.getState() != EnumDifferenceState.NONE)
		{
			if (resultOperation.getState() == EnumDifferenceState.POSITIVE)
			{
				// Se agreg� uno nuevo
				resultOperation.getSetResult().forEach(local ->
				{
					toAssignLocals.put(local.getId(), local);
					if (dsAssignedLocals != null)
					{
						dsAssignedLocals.add(local);
					}
				});
				this.updateToAssignDataSource();
			}
			else if (resultOperation.getState() == EnumDifferenceState.NEGATIVE)
			{
				// Se elimin� uno nuevo
				resultOperation.getSetResult().forEach(local ->
				{
					toAssignLocals.remove(local.getId());
					if (dsAssignedLocals != null)
					{
						Predicate<LocationDTO> localPredicate = p -> p.getId().equals(local.getId());

						dsAssignedLocals.removeIf(localPredicate);
					}
				});
				this.updateToAssignDataSource();
			}
		}

		this.updateSelectionLabel();
	}

	private void updateSelectionLabel()
	{
		if (dsAssignedLocals != null && dsAssignedLocals.size() > 0)
		{
			if (dsAssignedLocals.size() == 1)
			{
				lblCounter.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "one_local_selected"));

			}
			else
			{
				lblCounter.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "more_locals_selecteds", String.valueOf(dsAssignedLocals.size())));
			}
		}
		else
		{
			lblCounter.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "no_local_selected"));
		}

	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
