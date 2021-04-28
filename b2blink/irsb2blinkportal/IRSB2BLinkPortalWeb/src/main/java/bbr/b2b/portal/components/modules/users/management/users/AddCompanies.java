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
import bbr.b2b.users.report.classes.CompanyDataArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyDataDTO;
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
public class AddCompanies extends BbrWindow implements EntityEditor<UserDTO>
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long				serialVersionUID		= 3247626779164695209L;

	private UserDTO							item					= null;

	private BbrAdvancedGrid<CompanyDataDTO>	dgd_AssignedCompanies;
	private BbrAdvancedGrid<CompanyDataDTO>	dgd_FoundCompanies;

	private ArrayList<CompanyDataDTO>		dsAssignedCompanies		= new ArrayList<>();
	private ArrayList<CompanyDataDTO>		dsAvailableCompanies	= null;

	private HashMap<Long, CompanyDataDTO>	toAssignCompanies		= new HashMap<>();
	private Set<CompanyDataDTO>				setLastSelection		= null;

	private Button							btnAddItems				= new Button(VaadinIcons.ANGLE_DOUBLE_RIGHT);
	private BbrComboBox<SearchCriterion>	cbx_Criteria			= null;
	private BbrTextField					txt_Value				= null;
	private Label							lblCounter				= new Label();

	private final String					CMP_CODE_FIELD			= "id";
	private final int						DEFAULT_PAGE_NUMBER		= 1;
	private final int						MAX_ROWS_NUMBER			= 20;

	private BbrPagingManager				pagingManagerCompany;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public AddCompanies(BbrUI parent, UserDTO selectedUser)
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
		pagingManagerCompany = new BbrPagingManager();
		pagingManagerCompany.setLocale(this.getUser().getLocale());
		pagingManagerCompany.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> pagingCompanyChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		Label lblAvailables = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "found_companies"));

		HorizontalLayout pnlAvailables = new HorizontalLayout();
		pnlAvailables.setWidth("100%");
		pnlAvailables.setId("adasdsadsasadsad");
		pnlAvailables.setMargin(false);
		pnlAvailables.addComponents(lblAvailables);
		pnlAvailables.setComponentAlignment(lblAvailables, Alignment.MIDDLE_LEFT);
		pnlAvailables.setExpandRatio(lblAvailables, 1F);

		dgd_FoundCompanies = new BbrAdvancedGrid<>(this.getUser().getLocale());
		dgd_FoundCompanies.addColumn(CompanyDataDTO::getRut).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"));
		dgd_FoundCompanies.addColumn(CompanyDataDTO::getName).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"));

		dgd_FoundCompanies.setSortable(false);
		dgd_FoundCompanies.setSelectionMode(SelectionMode.MULTI);
		dgd_FoundCompanies.addStyleName("report-grid");
		dgd_FoundCompanies.addStyleName("bbr-multi-grid");
		dgd_FoundCompanies.setSizeFull();
		dgd_FoundCompanies.setPagingManager(pagingManagerCompany, this.CMP_CODE_FIELD);
		dgd_FoundCompanies.addSelectionListener((SelectionListener<CompanyDataDTO> & Serializable) e -> selectedItem_handler(e));

		VerticalLayout availablePanel = new VerticalLayout();
		availablePanel.setSizeFull();
		availablePanel.addComponents(pnlAvailables, dgd_FoundCompanies, pagingManagerCompany);
		availablePanel.setMargin(false);
		availablePanel.setExpandRatio(dgd_FoundCompanies, 1F);

		// Assigned Panel
		Label lblAssigned = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "companies_to_assign"));

		Button btn_RemoveCompanies = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "remove_companies"));
		btn_RemoveCompanies.addStyleName("link-button");
		btn_RemoveCompanies.addClickListener((ClickListener & Serializable) e -> remove_clickHandler(e));
		btn_RemoveCompanies.setHeight("20px");
		Button btn_RemoveAllCompanies = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "remove_all_companies"));
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

		dgd_AssignedCompanies = new BbrAdvancedGrid<>(this.getUser().getLocale());
		dgd_AssignedCompanies.addColumn(CompanyDataDTO::getRut).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"));
		dgd_AssignedCompanies.addColumn(CompanyDataDTO::getName).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"));
		dgd_AssignedCompanies.setSortable(false);
		dgd_AssignedCompanies.setSelectionMode(SelectionMode.MULTI);
		dgd_AssignedCompanies.addStyleName("report-grid");
		dgd_AssignedCompanies.addStyleName("bbr-multi-grid");
		dgd_AssignedCompanies.setSizeFull();

		HorizontalLayout pnlCounter = new HorizontalLayout();
		pnlCounter.setId("asdasd");
		pnlCounter.setWidth("100%");
		pnlCounter.setHeight("30px");

		pnlCounter.addComponent(lblCounter);
		pnlCounter.setComponentAlignment(lblCounter, Alignment.MIDDLE_RIGHT);

		VerticalLayout assignedPanel = new VerticalLayout();
		assignedPanel.setSizeFull();
		assignedPanel.addComponents(pnlAssignedOperation, dgd_AssignedCompanies, pnlCounter);
		assignedPanel.setMargin(false);
		assignedPanel.setExpandRatio(dgd_AssignedCompanies, 1F);

		// Operations Buttons Panel
		VerticalLayout operationsButtonsPanel = new VerticalLayout();
		operationsButtonsPanel.setWidth("50px");
		operationsButtonsPanel.setMargin(false);

		btnAddItems.addClickListener((ClickListener & Serializable) e -> addAllCompanies_clickHandler(e));

		operationsButtonsPanel.addComponent(btnAddItems);
		operationsButtonsPanel.setComponentAlignment(btnAddItems, Alignment.MIDDLE_CENTER);

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

		HorizontalLayout formLayout = new HorizontalLayout(finderPanel, profilesPanel);
		formLayout.setExpandRatio(profilesPanel, 1F);
		formLayout.setSizeFull();
		formLayout.setSpacing(true);

		// -------------BUTTONS PANEL-------------//
		Button btn_UpdateProfiles = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "save"));
		btn_UpdateProfiles.setStyleName("primary");
		btn_UpdateProfiles.addStyleName("btn-login");
		btn_UpdateProfiles.setWidth("150px");
		btn_UpdateProfiles.addClickListener((ClickListener & Serializable) e -> btnUpdateCompanies_clickHandler(e));

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
		this.updateSelectionLabel();
		// Main Windows
		this.setWidth(1000, Unit.PIXELS);
		this.setHeight(500, Unit.PIXELS);
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_add_company"));
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
		dsAssignedCompanies = new ArrayList<>();

		toAssignCompanies = new HashMap<>();

		dgd_FoundCompanies.deselectAll();

		dgd_FoundCompanies.resetSelection();

		setLastSelection = null;

		this.updateToAssignDataSource();
		this.updateSelectionLabel();
	}

	private void remove_clickHandler(ClickEvent e)
	{

		dgd_AssignedCompanies.getSelectedItems().forEach(company ->
		{

			Predicate<CompanyDataDTO> companyPredicate = p -> p.getId().equals(company.getId());

			CompanyDataDTO cmp = toAssignCompanies.remove(company.getId());

			dgd_FoundCompanies.removeItemSelection(cmp.getId());

			dsAssignedCompanies.removeIf(companyPredicate);
		});

		this.updateToAssignDataSource();
		this.updateSelectionLabel();
	}

	private void selectedItem_handler(SelectionEvent<CompanyDataDTO> e)
	{
		Set<CompanyDataDTO> selection = e.getAllSelectedItems();
		if (e.isUserOriginated())
		{
			updateToAssignSelection(selection);
		}
		this.setLastSelection = selection;
	}

	private void pagingCompanyChange_Listener(BbrPagingEvent e)
	{
		if (e != null && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && e.getResultObject() != null)
		{
			this.updateCompanies(e.getResultObject().getCurrentPage(), false, true);
		}
	}

	private void btnClose_clickHandler(ClickEvent event)
	{
		this.close();
	}

	private void btnUpdateCompanies_clickHandler(ClickEvent e)
	{
		if (dsAssignedCompanies != null && dsAssignedCompanies.size() > 0)
		{

			Long[] ids = new Long[dsAssignedCompanies.size()];
			for (int i = 0; i < dsAssignedCompanies.size(); i++)
			{
				ids[i] = new Long(dsAssignedCompanies.get(i).getId());
			}
			this.doUpdateCompanies(ids);
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "no_companies_selected"));
		}

	}

	private void addAllCompanies_clickHandler(ClickEvent e)
	{
		if (dsAvailableCompanies != null && dsAvailableCompanies.size() > 0)
		{
			dsAvailableCompanies.forEach(company ->
			{
				if (toAssignCompanies.get(company.getId()) == null)
				{
					toAssignCompanies.put(company.getId(), company);
					dsAssignedCompanies.add(company);
				}
			});

			this.updateDataSourceAndUI();
			this.updateToAssignDataSource();
			this.updateSelectionLabel();
		}
	}

	private void search_clickHandler(ClickEvent e)
	{
		this.updateCompanies(this.DEFAULT_PAGE_NUMBER, true, true);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private void doUpdateCompanies(Long[] ids)
	{
		String message = "";
		try
		{
			if (ids != null && ids.length > 0)
			{
				BaseResultDTO addResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().addUserCompanyRelations(this.item.getId(), ids, this.getUser().getId());
				if (addResult != null)
				{
					message = I18NManager.getErrorMessageBaseResult(addResult); // <--// Obtiene // el // mensaje // de // error. // "" // si // no // hay // errores.
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

		if (message.length() > 0 && !message.equals(""))
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

	private void updateCompanies(int pageNumber, boolean resetPageInfo, boolean isPaginated)
	{
		String errorMessage = validateData();
		if (errorMessage == null || errorMessage.length() <= 0)
		{
			dsAvailableCompanies = new ArrayList<>(Arrays.asList(new CompanyDataDTO[0]));
			this.setLastSelection = null;
			try
			{
				String value = txt_Value.getValue().trim();
				SearchCriterion criterion = cbx_Criteria.getSelectedValue();
				CompanyDataArrayResultDTO companiesResult = null;
				if (criterion.getId() == 0)
				{
					companiesResult = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().findCompaniesLikeNameNotAssigned(this.getItem().getId(), value, pageNumber, this.MAX_ROWS_NUMBER, resetPageInfo, isPaginated, null);
				}
				else if (criterion.getId() == 1)
				{
					companiesResult = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().findCompaniesLikeCodeNotAssigned(this.getItem().getId(), value, pageNumber, this.MAX_ROWS_NUMBER, resetPageInfo, isPaginated, null);
				}
				else if (criterion.getId() == 2)
				{
					companiesResult = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().findAllCompaniesNotAssigned(this.getItem().getId(), pageNumber, this.MAX_ROWS_NUMBER, resetPageInfo, isPaginated, null);
				}

				if (companiesResult != null && companiesResult.getCompanyDataDTOs() != null && companiesResult.getCompanyDataDTOs().length > 0)
				{
					dsAvailableCompanies = new ArrayList<>(Arrays.asList(companiesResult.getCompanyDataDTOs()));

					if (resetPageInfo)
					{
						PageInfoDTO pageInfo = companiesResult.getPageInfoDTO();

						BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
						pagingManagerCompany.setPages(bbrPage, resetPageInfo);
					}
				}
				else
				{
					this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "no_result"));
				}

				ListDataProvider<CompanyDataDTO> dataprovider = new ListDataProvider<>(dsAvailableCompanies);
				dgd_FoundCompanies.setDataProvider(dataprovider);

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
		ListDataProvider<CompanyDataDTO> dataprovider = new ListDataProvider<>(dsAssignedCompanies);
		dgd_AssignedCompanies.setDataProvider(dataprovider);
	}

	private void updateDataSourceAndUI()
	{
		btnAddItems.setEnabled((dsAvailableCompanies != null && dsAvailableCompanies.size() > 0));

		updateAvailableGridSelection();
	}

	private void updateAvailableGridSelection()
	{
		if (dsAvailableCompanies != null && dsAvailableCompanies.size() > 0)
		{
			dsAvailableCompanies.forEach(company ->
			{
				if (toAssignCompanies.get(company.getId()) != null)
				{
					dgd_FoundCompanies.select(company);
				}
			});
		}

		this.setLastSelection = dgd_FoundCompanies.getSelectedItems();
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

	private void updateToAssignSelection(Set<CompanyDataDTO> selection)
	{
		SetOperationResult<CompanyDataDTO> resultOperation = BbrSetsUtils.getInstance().difference(selection, this.setLastSelection);

		if (resultOperation != null && resultOperation.getState() != EnumDifferenceState.NONE)
		{
			if (resultOperation.getState() == EnumDifferenceState.POSITIVE)
			{
				// Se agreg� uno nuevo
				resultOperation.getSetResult().forEach(company ->
				{
					toAssignCompanies.put(company.getId(), company);
					if (dsAssignedCompanies != null)
					{
						dsAssignedCompanies.add(company);
					}
				});
				this.updateToAssignDataSource();
			}
			else if (resultOperation.getState() == EnumDifferenceState.NEGATIVE)
			{
				// Se elimin� uno nuevo
				resultOperation.getSetResult().forEach(company ->
				{
					toAssignCompanies.remove(company.getId());
					if (dsAssignedCompanies != null)
					{
						Predicate<CompanyDataDTO> companyPredicate = p -> p.getId().equals(company.getId());

						dsAssignedCompanies.removeIf(companyPredicate);
					}
				});
				this.updateToAssignDataSource();
			}
		}

		this.updateSelectionLabel();
	}

	private void updateSelectionLabel()
	{
		if (dsAssignedCompanies != null && dsAssignedCompanies.size() > 0)
		{
			if (dsAssignedCompanies.size() == 1)
			{
				lblCounter.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "one_company_selected"));

			}
			else
			{
				lblCounter.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "more_companies_selecteds", String.valueOf(dsAssignedCompanies.size())));
			}
		}
		else
		{
			lblCounter.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "no_company_selected"));
		}

	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
