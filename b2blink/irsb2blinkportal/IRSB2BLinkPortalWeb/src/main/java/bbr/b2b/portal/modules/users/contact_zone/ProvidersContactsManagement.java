package bbr.b2b.portal.modules.users.contact_zone;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.event.SortEvent.SortListener;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.constants.FunctionalitiesCodes;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.jms.constants.MessageTopics;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.classes.wrappers.management.ProviderContactOperationInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.modules.users.management.providers_contacts.CreateEditProviderContact;
import bbr.b2b.portal.components.modules.users.management.providers_contacts.CreateEditProviderContact.CreateEditProviderContactBuilder;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ContactDataDTO;
import bbr.b2b.users.report.classes.ContactPhoneDTO;
import bbr.b2b.users.report.classes.ContactPhoneTypeDTO;
import bbr.b2b.users.report.classes.ContactProvArrayResultDTO;
import bbr.b2b.users.report.classes.ContactProviderPositionDTO;
import bbr.b2b.users.report.classes.EditContactProviderInfoResultDTO;
import bbr.b2b.users.report.classes.ContactLogDataDTO;
import bbr.b2b.users.report.classes.ProviderContactReportInitParamDTO;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.events.BbrMessageEvent;
import cl.bbr.core.classes.events.BbrMessageEventListener;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;

public class ProvidersContactsManagement extends BbrModule implements BbrWorkExecutor
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Constants
	private static final long							serialVersionUID			= 7027502352762624910L;
	private static final String							NAME						= "name";
	private static final String							LASTNAME					= "lastname";
	private static final String							EMAIL						= "email";
	private static final String							POSITION					= "position";
	private static final String							PHONE						= "phone";
	private static final String							MAIN_PAGE_URL				= "";
	private final String								SEPARATOR					= " / ";
	private MultipleProviderContactOperation			providerContactOperation	= null;
	// Components
	private BbrAdvancedGrid<ContactDataDTO>				dgd_ProviderContact			= null;
	private BbrPagingManager							pagingManager				= null;
	private Button										btn_EditProviderContact		= null;
	private Button										btn_DeleteProviderContact	= null;
	private Button										btn_ConfirmProviderContact	= null;
	private Button										btn_AddProviderContact		= null;
	// Variables
	private ProviderContactReportInitParamDTO			initParams					= null;
	private final String								LOG_ID_FIELD				= "logid";
	private final String								DEFAULT_SORT_FIELD			= NAME;
	private final int									DEFAULT_PAGE_NUMBER			= 1;
	private final int									MAX_ROWS_NUMBER				= 50;
	private BbrMessageEventListener						messagingListener			= null;
	private BbrWork<ContactProvArrayResultDTO>			reportWork					= null;
	private BbrWork<BaseResultDTO>						confirmWork					= null;
	private Boolean										trackReport					= true;
	private Boolean										resetPageInfo				= true;
	private Boolean										isPending					= false;
	private Boolean										isStillPending				= false;
	private BbrWork<EditContactProviderInfoResultDTO>	positionsAndPhonesWork		= null;
	// private Boolean isInit = null;
	private ContactDataDTO								contactSelected				= null;
	private ContactLogDataDTO							contactLogData				= null;
	private Set<String>									createdEmailsList			= null;

	private ContactPhoneTypeDTO[]						companyPhoneTypes			= null;
	private ContactProviderPositionDTO[]				providerPositions			= null;

	private enum MultipleProviderContactOperation
	{
		CONFIRM_CONTACT, REMOVE_CONTACT;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public ProvidersContactsManagement(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Grilla
		this.dgd_ProviderContact = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_ProviderContact.addSortListener((SortListener<GridSortOrder<ContactDataDTO>>) e -> this.dgd_ProviderContact_SortHandler(e));
		// this.dgd_ProviderContact.setHasRowsDetails(true);
		// this.dgd_ProviderContact.setRowsDetailsGenerator(new
		// ProviderContactDetailsGenerator(this.getBbrUIParent()));
		this.initializeGridColumns();

		this.dgd_ProviderContact.addStyleName("report-grid");
		// this.dgd_ProviderContact.addStyleName("bbr-multi-grid");
		this.dgd_ProviderContact.setSizeFull();
		this.dgd_ProviderContact.setSelectionMode(SelectionMode.SINGLE);
		this.dgd_ProviderContact.setPagingManager(pagingManager, this.LOG_ID_FIELD);
		this.dgd_ProviderContact.addItemClickListener((ItemClickListener<ContactDataDTO> & Serializable) e -> this.selectableRow_ItemHandler(e));
		this.dgd_ProviderContact.addSelectionListener((SelectionListener<ContactDataDTO> & Serializable) e -> this.updateToolBarButtons_SelectionHandler(e));

		// Reporte
		// Barra de Herramientas
		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		HorizontalLayout rightButtonsBar = new HorizontalLayout();

		if (FunctionalityMngr.getInstance().hasFunctionalityAssigned(FunctionalitiesCodes.USR_CPV)) // CrearContact
		{
			this.btn_AddProviderContact = new Button("",
					EnumToolbarButton.ADD_ALTERNATIVE.getResource());
			this.btn_AddProviderContact.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_contact_provider"));
			this.btn_AddProviderContact.addClickListener((ClickListener & Serializable) e -> this.addProviderContact_ClickHandler());
			this.btn_AddProviderContact.addStyleName("toolbar-button");

			leftButtonsBar.addComponent(this.btn_AddProviderContact);
		}
		if (FunctionalityMngr.getInstance().hasFunctionalityAssigned(FunctionalitiesCodes.USR_CPV)) // EditarContact
		{
			this.btn_EditProviderContact = new Button("",
					EnumToolbarButton.EDIT_ALTERNATIVE.getResource());
			this.btn_EditProviderContact.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_contact_provider"));
			this.btn_EditProviderContact.addClickListener((ClickListener & Serializable) e -> this.editProviderContact_ClickHandler());
			this.btn_EditProviderContact.addStyleName("toolbar-button");
			leftButtonsBar.addComponent(this.btn_EditProviderContact);

		}

		if (FunctionalityMngr.getInstance().hasFunctionalityAssigned(FunctionalitiesCodes.USR_CPV)) // EliminarContact
		{
			this.btn_DeleteProviderContact = new Button("",
					EnumToolbarButton.DELETE.getResource());
			this.btn_DeleteProviderContact.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "delete_contact_provider"));
			this.btn_DeleteProviderContact.addClickListener((ClickListener & Serializable) e -> this.deleteProviderContact_ClickHandler(e));
			this.btn_DeleteProviderContact.addStyleName("toolbar-button");

			leftButtonsBar.addComponent(this.btn_DeleteProviderContact);
		}

		if (FunctionalityMngr.getInstance().hasFunctionalityAssigned(FunctionalitiesCodes.USR_CPV)) // ConfirmContact
		{
			this.btn_ConfirmProviderContact = new Button("",
					EnumToolbarButton.ACTIVATE_ALTERNATIVE.getResource());
			this.btn_ConfirmProviderContact.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "confirm_contact_provider"));
			this.btn_ConfirmProviderContact.addClickListener((ClickListener & Serializable) e -> this.confirmProviderContact_ClickHandler(e));
			this.btn_ConfirmProviderContact.addStyleName("toolbar-button");

			leftButtonsBar.addComponent(this.btn_ConfirmProviderContact);
		}

		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");
		leftButtonsBar.addStyleName("toolbar-layout");

		Button btn_Refresh = new Button("",
				EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> refreshReport());
		btn_Refresh.addStyleName("toolbar-button");

		Button btn_Help = this.getHelpButton();

		rightButtonsBar.addComponents(btn_Refresh, btn_Help);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName("toolbar-layout");

		rightButtonsBar.setComponentAlignment(btn_Refresh, Alignment.MIDDLE_RIGHT);

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsBar, rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(leftButtonsBar, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		this.updateToolBarButtons_SelectionHandler(null);

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName("report-layout-no-filter");
		mainLayout.setSizeFull();
		mainLayout.addComponents(toolBar, dgd_ProviderContact, pagingManager);
		mainLayout.setExpandRatio(dgd_ProviderContact, 1F);
		mainLayout.setMargin(false);
		this.addComponents(mainLayout);

		this.updateContactSortOrderCriteria(null);

		this.isPending = this.validatePendingContacts(this);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, ContactProvArrayResultDTO>()
		{
			@Override
			public ContactProvArrayResultDTO apply(Object t)
			{
				return executeService(ProvidersContactsManagement.this.getBbrUIParent());
			}
		});
		this.startWaiting();
		this.executeBbrWork(reportWork);

	}


	@Override
	public void attach()
	{
		super.attach();
		this.messagingListener = (BbrMessageEventListener & Serializable) e -> bbrMessage_Listener(e);
		((BbrUI) UI.getCurrent()).getMessagingEventMngr().addBbrMessageListener(messagingListener, MessageTopics.FAQS);
	}

	@Override
	public void detach()
	{
		super.detach();
		((BbrUI) UI.getCurrent()).getMessagingEventMngr().removeBbrMessageListener(messagingListener, MessageTopics.FAQS);
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ProvidersContactsManagement senderReport = (ProvidersContactsManagement) sender;
		if (result != null)
		{
			if (triggerObject.equals(this.btn_AddProviderContact))
			{
				this.doUpdatePositionsAndPhoneTypes(result, sender);
				this.initializeComponentCreateEditContact(new ContactDataDTO());
			}
			if (triggerObject.equals(this.btn_EditProviderContact))
			{
				this.doUpdatePositionsAndPhoneTypes(result, sender);
				this.initializeComponentCreateEditContact(this.contactSelected);
			}
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doUpdateReport(result, senderReport);
			}
			else if (triggerObject == MultipleProviderContactOperation.CONFIRM_CONTACT)
			{
				this.doConfirm(result, sender);
			}
		}
		else
		{

			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void selectableRow_ItemHandler(ItemClick<ContactDataDTO> e)
	{
		if (e.getMouseEventDetails().isDoubleClick() && e.getItem() != null)
		{
			if (FunctionalityMngr.getInstance().hasFunctionalityAssigned(FunctionalitiesCodes.USR_CPV)) // EditarUsuario
			{
				this.contactSelected = e.getItem();
				this.editProviderContact_ClickHandler();
			}
		}
		else
		{
			ContactDataDTO item = new ContactDataDTO();
			if (e.getItem() instanceof ContactDataDTO)
			{
				item = e.getItem();
				if (item != null)
				{
					if (this.dgd_ProviderContact.getSelectedItems().size() > 0)
					{
						this.dgd_ProviderContact.deselectAll();
					}
					else
					{
						this.dgd_ProviderContact.select(item);
					}
				}
			}
		}
	}

	private void pagingChange_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.startWaiting();
			initParams.setPagenumber(e.getResultObject().getCurrentPage());

			this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
	}

	private void selectContact()
	{
		ContactDataDTO item = new ContactDataDTO();
		if (this.dgd_ProviderContact != null && this.dgd_ProviderContact.getSelectedItems().size() > 0)
		{
			item = this.dgd_ProviderContact.getSelectedItems().iterator().next();
		}
		this.contactSelected = item;
	}

	private void updateToolBarButtons_SelectionHandler(SelectionEvent<ContactDataDTO> event)
	{
		this.selectContact();

		boolean canEdit = false;
		boolean canDelete = false;

		if (event != null && event.getAllSelectedItems() != null && event.getAllSelectedItems().size() > 0)
		{
			canDelete = true;

			if (event.getAllSelectedItems().size() == 1)
			{
				canEdit = true;
			}
		}

		if (this.btn_EditProviderContact != null)
		{
			this.btn_EditProviderContact.setEnabled(canEdit);
		}
		if (this.btn_DeleteProviderContact != null)
		{
			this.btn_DeleteProviderContact.setEnabled(canDelete);
		}
	}

	private boolean validatePendingContacts(ProvidersContactsManagement senderReport)
	{
		Boolean isPendingContacts = false;
		Boolean showErrorListMessage = false;
		ProviderContactOperationInfo providerContactOperationInfo = new ProviderContactOperationInfo(senderReport.getBbrUIParent());
		isPendingContacts = providerContactOperationInfo.showPendingContactsPositions(showErrorListMessage);
		return isPendingContacts;
	}

	private void refreshAllView(BbrUI bbrUIParent)
	{
		bbrUIParent.goToURL(MAIN_PAGE_URL);
	}

	private void deleteProviderContact_ClickHandler(ClickEvent event)
	{

		if (this.dgd_ProviderContact.getSelectedItems() != null && this.dgd_ProviderContact.getSelectedItems().size() > 0)
		{
			ArrayList<ContactDataDTO> items = new ArrayList<>(this.dgd_ProviderContact.getSelectedItems());

			this.providerContactOperation = MultipleProviderContactOperation.REMOVE_CONTACT;

			String alertMessage = (items.size() == 1) ? I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_delete_the_provider_contact",
					items.get(0).getContact().getName(),
					items.get(0).getContact().getLastname(),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "delete_app_name")) // #UNIDAD_NEGOCIO
					: I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_delete_provider_contact");

			BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
					I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					alertMessage,
					(Runnable & Serializable) () -> multipleUserAction(items));

		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_at_least_one"));
		}
	}

	private void confirmProviderContact_ClickHandler(ClickEvent event)
	{
		String alertMessage = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_confirm_provider_contact");
		BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
				I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_question"),
				alertMessage,
				(Runnable & Serializable) () -> this.confirm_Handler());
	}

	private void confirm_Handler()
	{
		confirmWork = new BbrWork<>(this, this.getBbrUIParent(), MultipleProviderContactOperation.CONFIRM_CONTACT);
		confirmWork.addFunction(new Function<Object, BaseResultDTO>()
		{
			@Override
			public BaseResultDTO apply(Object t)
			{
				return executeConfirmService(ProvidersContactsManagement.this.getBbrUIParent());
			}
		});
		this.startWaiting();
		this.executeBbrWork(confirmWork);
	}

	private void refreshReport()
	{
		this.refreshReport(null);
	}

	private void refreshReport(BbrEvent e)
	{
		boolean isReload = false;
		if (e != null)
		{
			@SuppressWarnings("unchecked")
			List<ContactProviderPositionDTO> b = (List<ContactProviderPositionDTO>) e.getResultObject();
			if (b.size() == 0)
			{
				this.isStillPending = false;
			}
			else
			{
				this.isStillPending = true;
			}
		}
		if (this.isPending)
		{
			if (this.isStillPending)
			{
				isReload = false;
			}
			else
			{
				isReload = true;
			}
		}
		else
		{
			if (!isStillPending)
			{
				isReload = false;
			}
			else
			{
				isReload = true;
			}
		}
		if (!isReload)
		{
			// this.isInit = false;
			this.startWaiting();

			initParams = this.updateInitParams();

			this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
		else
		{
			this.refreshAllView(this.getBbrUIParent());
		}
	}

	private void addProviderContact_ClickHandler()
	{
		this.executeEditProviderContact(this.btn_AddProviderContact);
	}

	private void initializeComponentCreateEditContact(ContactDataDTO contactSelected)
	{
		CreateEditProviderContact winCreateBasicUser = new CreateEditProviderContactBuilder()
				.withContactSelected(contactSelected)
				.withCreatedEmailsList(this.createdEmailsList)
				.withCompanyPhoneTypes(this.companyPhoneTypes)
				.withProviderPositions(this.providerPositions)
				.build(this.getBbrUIParent());
		winCreateBasicUser.addBbrEventListener((BbrEventListener & Serializable) e -> this.refreshReport(e));
		winCreateBasicUser.open(true);
	}

	private void editProviderContact_ClickHandler()
	{
		this.executeEditProviderContact(this.btn_EditProviderContact);
	}

	private void executeEditProviderContact(Button btn_TriggerCreateEditProviderContact)
	{
		this.positionsAndPhonesWork = new BbrWork<EditContactProviderInfoResultDTO>(this, this.getBbrUIParent(), btn_TriggerCreateEditProviderContact);
		this.positionsAndPhonesWork.addFunction(new Function<Object, EditContactProviderInfoResultDTO>()
		{
			@Override
			public EditContactProviderInfoResultDTO apply(Object t)
			{
				return executeEditProviderContactService(ProvidersContactsManagement.this.getBbrUIParent());
			}

		});

		this.startWaiting();
		this.executeBbrWork(this.positionsAndPhonesWork);
	}

	private EditContactProviderInfoResultDTO executeEditProviderContactService(BbrUI bbrUIParent)
	{
		EditContactProviderInfoResultDTO result = new EditContactProviderInfoResultDTO();

		Long ctpkey = this.contactSelected.getContact() != null ? this.contactSelected.getContact().getId() : -1L;
		Long prkey = this.getBbrUIParent().getUser().getEnterpriseId();
		try
		{
			result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUIParent).getEditContactProviderInfo(ctpkey, prkey);
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), bbrUIParent);
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

	private void doUpdatePositionsAndPhoneTypes(Object result, BbrWorkExecutor sender)
	{
		ProvidersContactsManagement senderReport = (ProvidersContactsManagement) sender;

		EditContactProviderInfoResultDTO infoResult = (EditContactProviderInfoResultDTO) result;

		BbrError error = ErrorsMngr.getInstance().getError(infoResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());
		if (!error.hasError())
		{
			ContactPhoneTypeDTO[] providerPhones = infoResult.getPhonetypes();
			this.companyPhoneTypes = providerPhones;

			ContactProviderPositionDTO[] providerPositions = infoResult.getPositions();
			this.providerPositions = providerPositions;

		}
		senderReport.stopWaiting();

	}

	private void bbrMessage_Listener(BbrMessageEvent event)
	{
		if (event != null && event.getMessage() != null && event.getMessage().getSenderBbrUser() != null)
		{
			BbrUser sender = event.getMessage().getSenderBbrUser();

			this.startWaiting();

			this.trackReport = (sender.getId().equals(this.getUser().getId()));
			this.resetPageInfo = true;
			this.executeBbrWork(reportWork);
		}
	}

	private void dgd_ProviderContact_SortHandler(SortEvent<GridSortOrder<ContactDataDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.updateContactSortOrderCriteria(e.getSortOrder());
			initParams.setPagenumber(DEFAULT_PAGE_NUMBER);

			this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private ProviderContactReportInitParamDTO updateInitParams()
	{
		if (initParams != null)
		{
			initParams.setPagenumber(this.DEFAULT_PAGE_NUMBER);
			initParams.setRows(this.MAX_ROWS_NUMBER);
		}

		return initParams;
	}

	private void initializeGridColumns()
	{
		this.dgd_ProviderContact.addColumn(c -> c.getContact().getName())
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_name"))
				.setWidth(230D)
				.setId(NAME);
		this.dgd_ProviderContact.addColumn(c -> c.getContact().getLastname())
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_lastname"))
				.setWidth(230D)
				.setId(LASTNAME);
		this.dgd_ProviderContact.addColumn(c -> c.getContact().getEmail())
				.setWidth(250D)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_email"))
				.setId(EMAIL);
		this.dgd_ProviderContact.addColumn(c -> this.showAllContactPositions(c, false))
				.setDescriptionGenerator(c -> this.showAllContactPositions(c, true))
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_positions"))
				.setWidth(350D)
				.setId(POSITION);
		this.dgd_ProviderContact.addColumn(c -> this.showAllContactPhones(c, false))
				.setDescriptionGenerator(c -> this.showAllContactPhones(c, true))
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_phones"))
				.setWidthUndefined()
				.setId(PHONE);

		// this.dgd_ProviderContact.setFrozenColumnCount(4);

		// Column<ContactDataDTO, ?> detailsColum =
		// this.dgd_ProviderContact.getColumns().get(0);
		// detailsColum.setSortable(false);
		// detailsColum.setWidth(60);

	}

	private String showAllContactPositions(ContactDataDTO c, boolean isTooltip)
	{
		StringBuilder result = new StringBuilder();
		ContactProviderPositionDTO[] positions = c.getContact().getPositionsAssigned() != null ? c.getContact().getPositionsAssigned() : new ContactProviderPositionDTO[0];

		if (!isTooltip)
		{
			for (ContactProviderPositionDTO pos : positions)
			{
				String separator = (!pos.equals(positions[positions.length - 1])) ? this.SEPARATOR : "";
				String concatArea = (pos.getArea() != null && !pos.getArea().isEmpty()) ? " [" + pos.getArea() + "] " : "";
				result.append(pos.getName() + " " + concatArea + separator);
			}
		}
		else
		{
			for (ContactProviderPositionDTO contactProviderPositionDTO : positions)
			{
				String concat = (contactProviderPositionDTO.getArea() != null && !contactProviderPositionDTO.getArea().isEmpty()) ? " [" + contactProviderPositionDTO.getArea() + "] " : "";
				result.append(contactProviderPositionDTO.getName() + concat + "\n");
			}
		}
		return result.toString();
	}

	private String showAllContactPhones(ContactDataDTO c, boolean isTooltip)
	{
		StringBuilder result = new StringBuilder();
		ContactPhoneDTO[] phones = c.getContact().getPhones() != null ? c.getContact().getPhones() : new ContactPhoneDTO[0];

		if (!isTooltip)
		{
			for (ContactPhoneDTO phone : phones)
			{
				String separator = (!phone.equals(phones[phones.length - 1])) ? this.SEPARATOR : "";
				result.append(phone.getNumber() + " " + separator);
			}
		}
		else
		{
			for (ContactPhoneDTO phone : phones)
			{
				result.append(phone.getNumber() + " " + "\n");
			}
		}

		return result.toString();
	}

	private void multipleUserAction(ArrayList<ContactDataDTO> contacts)
	{
		String errordescription = "";
		String trackValidMessage = TrackingConstants.ITEM_EDDITED;
		String trackErrorMessage = TrackingConstants.ERROR_ITEM_EDDITED;
		try
		{

			// Start Tracking
			this.getTimingMngr().startTimer();

			ArrayList<Long> ids = new ArrayList<>();

			for (ContactDataDTO contact : contacts)
			{
				ids.add(contact.getContact().getId());
			}

			Long[] contactsIds = ids.toArray(new Long[ids.size()]);

			BaseResultDTO result;

			String resultMessage = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation");

			this.contactLogData = this.getContactLogData();
			Long pvkey = this.getBbrUIParent().getUser().getEnterpriseId();
			switch (this.providerContactOperation)
			{
				case CONFIRM_CONTACT:
					result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(this.getBbrUIParent()).doConfirmContactInformation(this.contactLogData,
							pvkey);
					break;
				case REMOVE_CONTACT:
					result = new BaseResultDTO();
					List<BaseResultDTO> baseResults = new ArrayList<>();
					for (Long contactProvId : contactsIds)
					{
						result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(this.getBbrUIParent()).deleteProviderContact(contactProvId, pvkey,
								this.contactLogData);
						baseResults.add(result);
					}

					trackValidMessage = TrackingConstants.ITEM_DELETED;
					trackErrorMessage = TrackingConstants.ERROR_ITEM_DELETED;

					break;

				default:
					result = null;
					resultMessage = "";
					break;
			}

			if (result != null)
			{
				String message = I18NManager.getErrorMessageBaseResult(result, result.getParams()); // <--
				// Obtiene // el // mensaje // de // error. // "" // si // no //
				// hay // errores.

				if (message.length() == 0)
				{
					// Operaci�n Exitosa.
					this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"),
							resultMessage,
							null,
							() -> this.afterDelete_Handler());

					// End Tracking
					this.trackEvent(trackValidMessage, this.getBbrFilterDescription());
				}
				else
				{
					errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", result.getStatuscode(), result.getStatusmessage());
					this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
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
		catch (Exception e)
		{
			errordescription = BbrUtils.getInstance().substitute("{0}", e.getMessage());
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P200"));
		}

		if (errordescription.length() > 0)
		{
			// Track Error
			this.trackError(trackErrorMessage, this.getBbrFilterDescription(), errordescription, null, this);
		}
	}

	private void afterDelete_Handler()
	{
		this.isPending = this.validatePendingContacts(this);
		this.refreshReport();
	}

	private void updateContactSortOrderCriteria(List<GridSortOrder<ContactDataDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<ContactDataDTO> sorOrder : sortOrderList)
			{
				OrderCriteriaDTO order = new OrderCriteriaDTO();
				order.setPropertyname(sorOrder.getSorted().getId().toUpperCase());
				order.setAscending(sorOrder.getDirection() == SortDirection.ASCENDING);
				resultList.add(order);
			}
			resultList.toArray(new OrderCriteriaDTO[resultList.size()]);
		}
		else
		{
			OrderCriteriaDTO order = new OrderCriteriaDTO();
			order.setPropertyname(DEFAULT_SORT_FIELD.toUpperCase());
			order.setAscending(true);
			GridSortOrder<ContactDataDTO> sortOrder = new GridSortOrder<>(this.dgd_ProviderContact.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<ContactDataDTO>>();
			sortOrderList.add(sortOrder);

			this.dgd_ProviderContact.setSortOrder(sortOrderList);
		}
	}

	private ContactProvArrayResultDTO executeService(BbrUI bbrUI)
	{
		ContactProvArrayResultDTO result = null;
		try
		{
			// Start Tracking
			this.getTimingMngr().startTimer();
			result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUI).getProviderContacts(this.getBbrUIParent().getUser().getEnterpriseId());
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

	private BaseResultDTO executeConfirmService(BbrUI bbrUI)
	{
		BaseResultDTO result = null;
		this.contactLogData = this.getContactLogData();
		Long pvkey = this.getBbrUIParent().getUser().getEnterpriseId();

		try
		{
			// Start Tracking
			this.getTimingMngr().startTimer();
			result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(this.getBbrUIParent()).doConfirmContactInformation(this.contactLogData, pvkey);
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), bbrUI);
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

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		ProvidersContactsManagement senderReport = (ProvidersContactsManagement) sender;

		if (result != null)
		{
			ContactProvArrayResultDTO reportResult = (ContactProvArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				ContactDataDTO[] provider = reportResult.getContactDataW();
				ListDataProvider<ContactDataDTO> dataprovider = new ListDataProvider<>(Arrays.asList(provider));

				senderReport.dgd_ProviderContact.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.createdEmailsList = Arrays.stream(provider).map(p -> p.getContact().getEmail()).collect(Collectors.toSet());

				// if (senderReport.resetPageInfo)
				// {
				// PageInfoDTO pageInfo =
				// reportResult.getPageInfoDTO();
				// BbrPage bbrPage = new
				// BbrPage(pageInfo.getPagenumber(),
				// pageInfo.getTotalpages(), pageInfo.getRows());
				// senderReport.pagingManager.setPages(bbrPage,
				// pageInfo.getTotalrows(),
				// senderReport.resetPageInfo);
				// }

				// End Tracking
				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}

			}

			else
			{
				errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
			}
		}

		if (errordescription.length() > 0 && senderReport.trackReport == true)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}

		this.stopWaiting();
	}

	private void doConfirm(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		ProvidersContactsManagement senderReport = (ProvidersContactsManagement) sender;

		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				String resultMessage = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation");
				this.showInfoMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"), resultMessage, "",
						() -> this.refreshReport());
			}
			else
			{
				errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
			}
		}

		if (errordescription.length() > 0 && senderReport.trackReport == true)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}

		this.stopWaiting();
	}

	private ContactLogDataDTO getContactLogData()
	{
		ContactLogDataDTO result = new ContactLogDataDTO();
		result.setName(this.getBbrUIParent().getUser().getName());
		result.setLastname(this.getBbrUIParent().getUser().getLastName());
		result.setFecha(LocalDateTime.now());
		return result;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
