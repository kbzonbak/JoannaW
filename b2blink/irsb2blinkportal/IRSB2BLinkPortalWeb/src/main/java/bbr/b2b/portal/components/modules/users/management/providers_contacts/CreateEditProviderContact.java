package bbr.b2b.portal.components.modules.users.management.providers_contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ComponentRenderer;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.classes.wrappers.management.NewPhoneProviderContactDTO;
import bbr.b2b.portal.classes.wrappers.management.ProviderContactOperationInfo;
import bbr.b2b.portal.components.basics.BbrButtonGroup;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.renderers.grid_details.PositionObligatoryRenderer;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ContactDataDTO;
import bbr.b2b.users.report.classes.ContactDataInitParamDTO;
import bbr.b2b.users.report.classes.ContactPhoneDTO;
import bbr.b2b.users.report.classes.ContactPhoneTypeDTO;
import bbr.b2b.users.report.classes.ContactProviderPositionDTO;
import bbr.b2b.users.report.classes.ContactLogDataDTO;
import bbr.b2b.users.report.classes.ContactProvInitParamDTO;
import bbr.b2b.users.report.classes.PositionDTO;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class CreateEditProviderContact extends BbrWindow implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long					serialVersionUID			= -6312277934984059659L;
	private static final String					POSITION_NAME				= "position_name";
	private static final String					NAME					= "name";
	private static final String					AREA					= "area";
	private static final String					TYPE					= "type";
	private static final String					NUMBER					= "number";
	private static final String					BTN_ADD_CONTACT			= "BTN_ADD_CONTACT";
	private static final String					BTN_EDIT_CONTACT			= "BTN_EDIT_CONTACT";
	// Components
	private BbrTextField						txt_Name				= null;
	private BbrTextField						txt_LastName				= null;
	private BbrTextField						txt_Email				= null;
	private BbrAdvancedGrid<ContactProviderPositionDTO>	dgd_PendingPositions			= null;
	private BbrAdvancedGrid<ContactProviderPositionDTO>	dgd_ContactPositions			= null;
	private BbrAdvancedGrid<ContactPhoneDTO>			dgd_Phones				= null;
	private Button						btn_AddPendingPosition		= null;
	private Button						btn_AddContactPosition		= null;
	private Button						btn_AddContactPhone			= null;
	private Button						btn_DeleteContactPhone		= null;
	private Button						btn_RemoveContactPosition		= null;
	private BbrHInputFieldContainer				fld_PendingPositions			= null;
	// Variables
	private boolean						isEdit					= false;
	private ContactDataDTO					contactSelected			= null;
	private ContactLogDataDTO					contactLogData			= null;
	private List<ContactPhoneDTO>				contactPhonesList			= new ArrayList<ContactPhoneDTO>();
	private List<ContactProviderPositionDTO>			contactPositionsList			= new ArrayList<ContactProviderPositionDTO>();
	private List<ContactProviderPositionDTO>			pendingPositionsList			= new ArrayList<ContactProviderPositionDTO>();
	private List<ContactProviderPositionDTO>			allPositionsList			= new ArrayList<ContactProviderPositionDTO>();
	private ContactDataInitParamDTO				initParam				= null;
	private BbrWork<BaseResultDTO>				createWork				= null;
	private BbrWork<BaseResultDTO>				editWork				= null;
	private ContactProviderPositionDTO				contactPositionGridSelected		= null;
	private ContactProviderPositionDTO				pendingPositionGridSelected		= null;
	private boolean						isStillPending			= false;
	private CreateEditProviderContactBuilder			createEditProviderContactBuilder	= null;
	private boolean						isShowingPendingPositions		= false;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public boolean isStillPending()
	{
		return this.isStillPending;
	}

	private CreateEditProviderContact(CreateEditProviderContactBuilder createEditProviderContactBuilder)
	{
		super(createEditProviderContactBuilder.bbrUIParent);
		this.createEditProviderContactBuilder = createEditProviderContactBuilder;
		this.initializeView();
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
		// name
		this.txt_Name = new BbrTextField();
		this.txt_Name.addStyleName("bbr-fields");
		this.txt_Name.setWidth("90%");
		BbrHInputFieldContainer fld_Name = new BbrHInputFieldContainer(
		I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_names"), this.txt_Name);
		// lastname
		this.txt_LastName = new BbrTextField();
		this.txt_LastName.addStyleName("bbr-fields");
		this.txt_LastName.setWidth("90%");
		BbrHInputFieldContainer fld_LastName = new BbrHInputFieldContainer(
		I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_lastnames"), this.txt_LastName);

		this.isShowingPendingPositions = this.getIfIsShowingPendingPositions(createEditProviderContactBuilder.providerPositions);
		if (this.isShowingPendingPositions)
		{
			this.dgd_PendingPositions = new BbrAdvancedGrid<>(this.getBbrUIParent().getLocale());
			this.dgd_PendingPositions.setWidth("100%");
			this.dgd_PendingPositions.setHeaderRowHeight(30D);
			this.dgd_PendingPositions.addStyleName("horizontal-layout-grid");
			this.dgd_PendingPositions
			.addSelectionListener((SelectionListener<ContactProviderPositionDTO> & Serializable) e -> this.dgd_PendingPosition_SelectionHandler(e));

			// positions button pending add
			this.btn_AddPendingPosition = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
			this.btn_AddPendingPosition
			.setDescription(I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_position"));
			this.btn_AddPendingPosition.addClickListener((ClickListener & Serializable) e -> this.btn_AddPendingPosition_clickHandler());
			this.btn_AddPendingPosition.addStyleName("toolbar-button");
			this.btn_AddPendingPosition.setEnabled(false);

			VerticalLayout cmp_PendingPositionsButtons = new VerticalLayout();
			cmp_PendingPositionsButtons.setMargin(false);
			cmp_PendingPositionsButtons.setSpacing(false);
			cmp_PendingPositionsButtons.setHeight("100%");
			cmp_PendingPositionsButtons.setWidth("30px");
			cmp_PendingPositionsButtons.addComponent(this.btn_AddPendingPosition);
			cmp_PendingPositionsButtons.setComponentAlignment(this.btn_AddPendingPosition, Alignment.MIDDLE_CENTER);

			HorizontalLayout cmp_PendingPositionGridAndButtons = new HorizontalLayout();
			cmp_PendingPositionGridAndButtons.addComponents(this.dgd_PendingPositions, cmp_PendingPositionsButtons);
			cmp_PendingPositionGridAndButtons.setSpacing(true);
			cmp_PendingPositionGridAndButtons.setSizeFull();
			cmp_PendingPositionGridAndButtons.setComponentAlignment(this.dgd_PendingPositions, Alignment.MIDDLE_CENTER);
			cmp_PendingPositionGridAndButtons.setExpandRatio(this.dgd_PendingPositions, 1F);

			this.fld_PendingPositions = new BbrHInputFieldContainer("", cmp_PendingPositionGridAndButtons);
			this.fld_PendingPositions.setComponentAlignment(cmp_PendingPositionGridAndButtons, Alignment.MIDDLE_LEFT);
		}
		// this.fld_PendingPositions.setVisible(false);
		/// -------------------------------------------------------------------------------------

		// positions datagrid
		this.dgd_ContactPositions = new BbrAdvancedGrid<>(this.getBbrUIParent().getLocale());
		this.dgd_ContactPositions.setWidth("100%");
		this.dgd_ContactPositions.setHeaderRowHeight(30D);
		this.dgd_ContactPositions.addStyleName("horizontal-layout-grid");
		this.dgd_ContactPositions.addSelectionListener((SelectionListener<ContactProviderPositionDTO> & Serializable) e -> this.dgd_Position_SelectionHandler(e));
		this.initializeContactPositionsGridColumns();

		this.dgd_ContactPositions.getEditor().setEnabled(true);
		this.dgd_ContactPositions.getEditor().setSaveCaption(I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_GENERIC, "save"));
		this.dgd_ContactPositions.getEditor()
		.setCancelCaption(I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_GENERIC, "cancel"));

		// positions button add
		this.btn_AddContactPosition = new Button("", EnumToolbarButton.ADD_ALTERNATIVE.getResource());
		this.btn_AddContactPosition
		.setDescription(I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_position"));
		this.btn_AddContactPosition.addClickListener((ClickListener & Serializable) e -> this.btn_AddPosition_ClickHandler(null));
		this.btn_AddContactPosition.addStyleName("toolbar-button");

		// positions button remove
		this.btn_RemoveContactPosition = new Button("", EnumToolbarButton.DELETE.getResource());
		this.btn_RemoveContactPosition
		.setDescription(I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "delete_position"));
		this.btn_RemoveContactPosition.addClickListener((ClickListener & Serializable) e -> this.btn_RemovePosition_clickHandler(e));
		this.btn_RemoveContactPosition.addStyleName("toolbar-button");
		this.btn_RemoveContactPosition.setEnabled(false);

		VerticalLayout cmp_ContactPositionsButtons = new VerticalLayout();
		cmp_ContactPositionsButtons.setMargin(false);
		cmp_ContactPositionsButtons.setSpacing(false);
		cmp_ContactPositionsButtons.setHeight("100%");
		cmp_ContactPositionsButtons.setWidth("30px");
		cmp_ContactPositionsButtons.addComponent(this.btn_AddContactPosition);
		cmp_ContactPositionsButtons.addComponent(this.btn_RemoveContactPosition);
		cmp_ContactPositionsButtons.setComponentAlignment(this.btn_AddContactPosition, Alignment.BOTTOM_CENTER);
		cmp_ContactPositionsButtons.setComponentAlignment(this.btn_RemoveContactPosition, Alignment.TOP_CENTER);

		HorizontalLayout cmp_ContactPositionGridAndButtons = new HorizontalLayout();
		cmp_ContactPositionGridAndButtons.addComponents(this.dgd_ContactPositions, cmp_ContactPositionsButtons);
		cmp_ContactPositionGridAndButtons.setSpacing(true);
		cmp_ContactPositionGridAndButtons.setSizeFull();
		cmp_ContactPositionGridAndButtons.setExpandRatio(this.dgd_ContactPositions, 1F);

		VerticalLayout cmp_ContactPosition = new VerticalLayout();
		cmp_ContactPosition.setMargin(false);
		cmp_ContactPosition.setSpacing(true);
		cmp_ContactPosition.setSizeFull();
		cmp_ContactPosition.addComponents(cmp_ContactPositionGridAndButtons);
		cmp_ContactPosition.setExpandRatio(cmp_ContactPositionGridAndButtons, 1F);

		/// -------------------------------------------------------------------------------------

		BbrHInputFieldContainer fld_Positions = new BbrHInputFieldContainer(
		I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_positions"), cmp_ContactPosition);

		// phone
		this.dgd_Phones = new BbrAdvancedGrid<>(this.getBbrUIParent().getLocale());
		this.dgd_Phones.setWidth("100%");
		this.dgd_Phones.setHeaderRowHeight(30D);
		this.initializePhonesGrid();
		this.dgd_Phones.addSelectionListener((SelectionListener<ContactPhoneDTO> & Serializable) e -> this.dgd_Phones_ItemClickHandler(e));

		this.btn_AddContactPhone = new Button("", EnumToolbarButton.ADD_ALTERNATIVE.getResource());
		this.btn_AddContactPhone
		.setDescription(I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_contact_phone"));
		this.btn_AddContactPhone.addClickListener((ClickListener & Serializable) e -> this.btn_AddPhone_ClickHandler());
		this.btn_AddContactPhone.addStyleName("toolbar-button");

		this.btn_DeleteContactPhone = new Button("", EnumToolbarButton.DELETE.getResource());
		this.btn_DeleteContactPhone
		.setDescription(I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "delete_contact_phone"));
		this.btn_DeleteContactPhone.addClickListener((ClickListener & Serializable) e -> this.btn_DeletePhone_clickHandler(e));
		this.btn_DeleteContactPhone.addStyleName("toolbar-button");
		this.btn_DeleteContactPhone.setEnabled(false);

		VerticalLayout cmp_PhoneButtons = new VerticalLayout();
		cmp_PhoneButtons.setMargin(false);
		cmp_PhoneButtons.setSpacing(false);
		cmp_PhoneButtons.setHeight("100%");
		cmp_PhoneButtons.setWidth("30px");
		cmp_PhoneButtons.addComponent(this.btn_AddContactPhone);
		cmp_PhoneButtons.addComponent(this.btn_DeleteContactPhone);
		cmp_PhoneButtons.setComponentAlignment(this.btn_AddContactPhone, Alignment.BOTTOM_CENTER);
		cmp_PhoneButtons.setComponentAlignment(this.btn_DeleteContactPhone, Alignment.TOP_CENTER);

		HorizontalLayout cmp_PhonesContent = new HorizontalLayout();
		cmp_PhonesContent.addComponents(this.dgd_Phones, cmp_PhoneButtons);
		cmp_PhonesContent.setSizeFull();
		cmp_PhonesContent.setMargin(false);
		cmp_PhonesContent.setSpacing(true);
		cmp_PhonesContent.setExpandRatio(this.dgd_Phones, 1F);

		BbrHInputFieldContainer fld_Phones = new BbrHInputFieldContainer(
		I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_phones"), cmp_PhonesContent);

		// email
		this.txt_Email = new BbrTextField();
		this.txt_Email.addStyleName("bbr-fields");
		this.txt_Email.setWidth("90%");
		BbrHInputFieldContainer fld_Email = new BbrHInputFieldContainer(
		I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_email"), this.txt_Email);

		// ----------------------------------
		BbrButtonGroup bbrGroupButton = new BbrButtonGroup.BbrGroupButtonBuilder(this.getBbrUIParent())
		.withPrimaryButtonLabel(I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_GENERIC, "accept"))
		.withPrimaryButtonListener((ClickListener & Serializable) (e) -> this.btn_AcceptHandler(e))
		.withCancelButtonLabel(I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_GENERIC, "cancel"))
		.withCancelButtonListener((ClickListener & Serializable) (e) -> this.close()).build();
		bbrGroupButton.setStyleName("bbr-buttons-panel");
		// bbrGroupButton.getPrimaryButton().setClickShortcut(KeyCode.ENTER);

		// FormLayout as Panel Content
		VerticalLayout cmp_Fields = new VerticalLayout();
		cmp_Fields.addStyleName("generic-form");
		cmp_Fields.setSizeFull();
		cmp_Fields.setMargin(false);

		cmp_Fields.addComponents(fld_Name, fld_LastName, fld_Email);
		if (isShowingPendingPositions)
		{
			cmp_Fields.addComponents(fld_PendingPositions);
		}
		cmp_Fields.addComponents(fld_Positions, fld_Phones);

		cmp_Fields.setExpandRatio(fld_Name, 0.3F);
		cmp_Fields.setExpandRatio(fld_LastName, 0.3F);
		if (isShowingPendingPositions)
		{
			cmp_Fields.setExpandRatio(fld_PendingPositions, 1F);
		}
		cmp_Fields.setExpandRatio(fld_Positions, 1.7F);
		cmp_Fields.setExpandRatio(fld_Phones, 1F);
		cmp_Fields.setExpandRatio(fld_Email, 0.3F);

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(cmp_Fields, bbrGroupButton);
		mainLayout.setComponentAlignment(cmp_Fields, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(bbrGroupButton, Alignment.BOTTOM_CENTER);
		mainLayout.setExpandRatio(cmp_Fields, 1F);
		mainLayout.setExpandRatio(bbrGroupButton, 0.1F);
		mainLayout.setSpacing(true);
		mainLayout.addStyleName("bbr-margin-windows");

		// Main Windows
		this.setWidth("550px");
		this.setHeight("580px");
		this.setResizable(false);

		this.contactSelected = this.createEditProviderContactBuilder.contactSelected;
		if (this.contactSelected != null && this.contactSelected.getContact() != null && !this.contactSelected.getContact().getId().equals(null))
		{
			this.isEdit = true;
			this.setCaption(
			I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_edit_contact_provider"));
		}
		else
		{
			this.setCaption(
			I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_add_contact_provider"));
		}
		this.setContent(mainLayout);

		this.initializeProviderContactPhones(this.contactPhonesList, this.contactSelected, this.isEdit);
		this.doUpdatePositionsAndPhoneTypesBy(createEditProviderContactBuilder.companyPhoneTypes, createEditProviderContactBuilder.providerPositions);
		if (this.isEdit)
		{
			this.initializeProviderContactData();
		}
	}

	private boolean getIfIsShowingPendingPositions(ContactProviderPositionDTO[] positions)
	{
		List<ContactProviderPositionDTO> obligatoryPositions = new ArrayList<>();
		obligatoryPositions = this.getObligatoryPositions(positions);
		return !obligatoryPositions.isEmpty();
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject.equals(BTN_ADD_CONTACT))
			{
				this.doUpdateAfterAdd(result, sender);
			}
			if (triggerObject.equals(BTN_EDIT_CONTACT))
			{
				this.doUpdateAfterEdit(result, sender);
			}
		}
		else
		{
			CreateEditProviderContact senderReport = (CreateEditProviderContact) sender;

			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
				I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}

	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================
	private void dgd_Phones_ItemClickHandler(SelectionEvent<ContactPhoneDTO> e)
	{
		Optional<ContactPhoneDTO> phoneSelected = e.getFirstSelectedItem();
		this.btn_DeleteContactPhone.setEnabled(phoneSelected.isPresent());
	}

	private void btn_RemovePosition_clickHandler(ClickEvent e)
	{
		if (this.contactPositionGridSelected != null)
		{
			this.removeFromContactPositionsGrid(this.contactPositionGridSelected);
			this.addToPendingPositionsGrid(this.contactPositionGridSelected);
			this.btn_RemoveContactPosition.setEnabled(false);
		}
	}

	private void btn_AddPendingPosition_clickHandler()
	{
		if (this.pendingPositionGridSelected != null)
		{
			this.addToContactPositionsGrid(this.pendingPositionGridSelected);
			this.removeFromPendingPositionsGrid(this.pendingPositionGridSelected);
			this.btn_AddPendingPosition.setEnabled(false);
		}
	}

	private ContactProviderPositionDTO getSamePositionContained(List<ContactProviderPositionDTO> positions, ContactProviderPositionDTO position)
	{
		return positions.stream().filter(p -> (position != null) && (p.getName().trim().equalsIgnoreCase(position.getName().trim()))).findFirst().orElse(null);
	}

	private void dgd_Position_SelectionHandler(SelectionEvent<ContactProviderPositionDTO> e)
	{
		if (e.getFirstSelectedItem().isPresent())
		{
			ContactProviderPositionDTO selected = e.getFirstSelectedItem().get();
			if (selected != null)
			{
				this.contactPositionGridSelected = selected;
				this.btn_RemoveContactPosition.setEnabled(true);
			}
		}
		else
		{
			this.btn_RemoveContactPosition.setEnabled(false);
		}
	}

	private void dgd_PendingPosition_SelectionHandler(SelectionEvent<ContactProviderPositionDTO> e)
	{
		if (e.getFirstSelectedItem().isPresent())
		{
			ContactProviderPositionDTO selected = e.getFirstSelectedItem().get();
			if (selected != null)
			{
				this.pendingPositionGridSelected = selected;
				this.btn_AddPendingPosition.setEnabled(true);
			}
		}
		else
		{
			this.btn_AddPendingPosition.setEnabled(false);
		}
	}

	private void column_PendingPositions_SelectionHandler(ContactProviderPositionDTO item)
	{
		if (item != null)
		{
			if (this.dgd_PendingPositions.getSelectedItems().size() > 0)
			{
				this.dgd_PendingPositions.deselectAll();
				this.dgd_PendingPositions.select(item);
			}
			else
			{
				this.dgd_PendingPositions.select(item);
			}
		}
	}

	private void btn_Created_ClickHandler()
	{
		this.dispatchEventByType(BbrEvent.ITEM_CREATED);

	}

	private void btn_Edited_ClickHandler()
	{
		this.dispatchEventByType(BbrEvent.ITEM_UPDATED);
	}

	private void dispatchEventByType(String eventType)
	{
		BbrEvent alertResponseEvent = new BbrEvent(eventType);
		alertResponseEvent.setResultObject(this.pendingPositionsList);
		this.dispatchBbrEvent(alertResponseEvent);
	}

	private void btn_AddPosition_ClickHandler(ContactProviderPositionDTO positionName)
	{
		NewPositionProviderContact winAddPosition = new NewPositionProviderContact(this.getBbrUIParent(), this.allPositionsList);
		winAddPosition.setDefaultPosition(positionName);
		winAddPosition.initializeView();
		winAddPosition.addBbrEventListener((BbrEventListener & Serializable) e -> this.add_ProviderContactPosition_EventHandler(e));
		winAddPosition.open(true);
	}

	private void btn_AddPhone_ClickHandler()
	{
		NewPhoneProviderContact winAddPhone = new NewPhoneProviderContact(this.getBbrUIParent(), this.createEditProviderContactBuilder.companyPhoneTypes);
		winAddPhone.initializeView();
		winAddPhone.addBbrEventListener((BbrEventListener & Serializable) e -> this.add_ProviderContactPhone_EventHandler(e));
		winAddPhone.open(true);
	}

	private void btn_DeletePhone_clickHandler(ClickEvent e)
	{
		if (this.dgd_Phones.getSelectedItems().size() > 0)
		{
			ContactPhoneDTO phone = this.dgd_Phones.getSelectedItems().iterator().next();
			this.removeFromPhonesGrid(phone);
		}
	}

	private void add_ProviderContactPosition_EventHandler(BbrEvent e)
	{
		if (e.getEventType().equals(NewPositionProviderContact.BTN_ACCEPT) && e.getResultObject() != null)
		{
			ContactProviderPositionDTO result = (ContactProviderPositionDTO) e.getResultObject();

			ContactProviderPositionDTO position = this.getPositionResultMapped(result);

			boolean isExistingPositionOfContact = this.isExistingPosition(this.contactPositionsList, position);
			boolean isExistingPositionOfAll = this.isExistingPosition(this.allPositionsList, position);
			boolean isExistingPositionOfPending = this.isShowingPendingPositions ? this.isExistingPosition(this.pendingPositionsList, position) : false;

			if (!isExistingPositionOfContact && !isExistingPositionOfAll)
			{
				BbrMessagesBoxUtils.showYesNoQuestionMessage(this.getBbrUIParent(),
				I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
				I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_add_new_cp_position", position.getName()),
				() -> this.addToContactPositionsGrid(position));
			}
			else if (!isExistingPositionOfContact && !isExistingPositionOfPending && isExistingPositionOfAll)
			{
				this.addToContactPositionsGrid(position);
			}
			else if (isExistingPositionOfPending)
			{
				ContactProviderPositionDTO selectPosition = getSamePositionContained(this.pendingPositionsList, position);
				this.pendingPositionGridSelected = selectPosition;
				this.btn_AddPendingPosition_clickHandler();
			}
			else if (isExistingPositionOfContact)
			{
				ContactProviderPositionDTO selectPosition = getSamePositionContained(this.contactPositionsList, position);
				this.dgd_ContactPositions.select(selectPosition);
			}
			else
			{
				this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_error"),
				I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_position"));
			}

		}
	}

	private ContactProviderPositionDTO getPositionResultMapped(ContactProviderPositionDTO result)
	{
		ContactProviderPositionDTO position = new ContactProviderPositionDTO();
		position.setArea(result.getArea());
		position.setContactproviderid(result.getContactproviderid());
		position.setName(result.getName());
		position.setObligatory(result.getObligatory());
		position.setPositionid(result.getPositionid() != null ? result.getPositionid() : -1);
		position.setSelected(result.getSelected());

		return position;
	}

	private boolean isExistingPosition(List<ContactProviderPositionDTO> positions, ContactProviderPositionDTO position)
	{
		boolean result = false;
		result = positions.stream().anyMatch(data -> data.getName().trim().equalsIgnoreCase(position.getName().trim()));
		return result;
	}

	private void add_ProviderContactPhone_EventHandler(BbrEvent e)
	{
		if (e.getEventType().equals(NewPhoneProviderContact.BTN_ACCEPT) && e.getResultObject() != null)
		{
			NewPhoneProviderContactDTO result = (NewPhoneProviderContactDTO) e.getResultObject();

			if (!result.getNumber().isEmpty())
			{
				ContactPhoneDTO phone = new ContactPhoneDTO();
				phone.setContactproviderid(this.contactSelected.getContact() != null ? this.contactSelected.getContact().getId() : -1L);
				phone.setContactphonetype(result.getContactPhoneType().getType());
				phone.setContactphonetypeid(result.getContactPhoneType().getId());
				phone.setNumber(result.getNumber());

				this.addToPhoneGrid(phone);
			}
		}
	}

	private void btn_AcceptHandler(ClickEvent e)
	{
		String errorMsg = this.validateData();
		if ((errorMsg == null) || (errorMsg.length() == 0))
		{
			if (this.isEdit)
			{
				this.editWork = new BbrWork<BaseResultDTO>(this, this.getBbrUIParent(), BTN_EDIT_CONTACT);
				this.editWork.addFunction(new Function<Object, BaseResultDTO>()
				{
					@Override
					public BaseResultDTO apply(Object t)
					{
						return executeEditService(CreateEditProviderContact.this.getBbrUIParent());
					}

				});
				this.startWaiting();
				this.executeBbrWork(editWork);

			}
			else
			{
				this.createWork = new BbrWork<BaseResultDTO>(this, this.getBbrUIParent(), BTN_ADD_CONTACT);
				this.createWork.addFunction(new Function<Object, BaseResultDTO>()
				{
					@Override
					public BaseResultDTO apply(Object t)
					{
						return executeCreateService(CreateEditProviderContact.this.getBbrUIParent());
					}

				});
				this.startWaiting();
				this.executeBbrWork(createWork);
			}

		}
		else
		{
			CreateEditProviderContact.this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), errorMsg);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void initializeProviderContactData()
	{
		this.txt_Email.setValue(this.contactSelected.getContact().getEmail());
		this.txt_LastName.setValue(this.contactSelected.getContact().getLastname());
		this.txt_Name.setValue(this.contactSelected.getContact().getName());
	}

	private void doUpdateAfterAdd(Object result, BbrWorkExecutor sender)
	{
		CreateEditProviderContact senderReport = (CreateEditProviderContact) sender;
		BaseResultDTO reportResult = (BaseResultDTO) result;

		BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

		if (!error.hasError())
		{

			this.showInfoMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_info"),
			I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation"), "100%",
			() -> this.btn_Created_ClickHandler());
		}
		this.stopWaiting();
		this.close();
	}

	private void doUpdateAfterEdit(Object result, BbrWorkExecutor sender)
	{
		CreateEditProviderContact senderReport = (CreateEditProviderContact) sender;
		BaseResultDTO reportResult = (BaseResultDTO) result;

		BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

		if (!error.hasError())
		{
			if (senderReport.pendingPositionsList != null)
			{
				senderReport.isStillPending = senderReport.pendingPositionsList.size() > 0;
			}
			else
			{
				senderReport.pendingPositionsList = new ArrayList<ContactProviderPositionDTO>();
			}

			this.showInfoMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_info"),
			I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation"), "100%",
			() -> this.btn_Edited_ClickHandler());
		}

		this.stopWaiting();
		this.close();
	}

	private void initializePhonesGrid()
	{
		this.dgd_Phones
		.addCustomColumn(ContactPhoneDTO::getContactphonetype,
		I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_type"), true)
		.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(TYPE);
		this.dgd_Phones
		.addCustomColumn(ContactPhoneDTO::getNumber,
		I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_phone"), true)
		.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(NUMBER);
	}

	private void initializeContactPositionsGridColumns()
	{
		TextField editor_Area = new TextField();

		this.dgd_ContactPositions
		.addCustomColumn(ContactProviderPositionDTO::getName,
		I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_positions_assigned"), true).setWidth(220D)
		.setId(NAME);

		this.dgd_ContactPositions
		.addCustomColumn(ContactProviderPositionDTO::getArea,
		I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_area"),
		I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_area"))
		.setEditorComponent(editor_Area, ContactProviderPositionDTO::setArea).setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(AREA);
	}

	private void initializePendingPositionsGridColumns()
	{
		this.dgd_PendingPositions.addColumn(position -> {
			PositionObligatoryRenderer renderd = new PositionObligatoryRenderer(position, true);
			renderd.addLayoutClickListener((LayoutClickListener & Serializable) e -> this.column_PendingPositions_SelectionHandler(position));
			return renderd;
		}, new ComponentRenderer()).setSortable(false).setWidthUndefined()
		.setCaption(I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_pending_position"))
		.setId(POSITION_NAME);
	}

	private ContactDataInitParamDTO getInitParam()
	{
		ContactProvInitParamDTO contactProvider = new ContactProvInitParamDTO();
		ContactProviderPositionDTO[] contactProviderPosition = null;
		ContactPhoneDTO[] contactPhones = null;
		ContactDataInitParamDTO result = new ContactDataInitParamDTO();

		contactProvider.setProviderid(this.getBbrUIParent().getUser().getEnterpriseId());
		contactProvider.setId(this.isEdit ? this.contactSelected.getContact().getId() : -1L);
		contactProvider.setEmail(this.txt_Email.getValue());
		contactProvider.setLastname(this.txt_LastName.getValue());
		contactProvider.setName(this.txt_Name.getValue());

		List<ContactProviderPositionDTO> selectedPosition = this.contactPositionsList;
		contactProviderPosition = selectedPosition.toArray(new ContactProviderPositionDTO[this.contactPositionsList.size()]);

		contactPhones = this.contactPhonesList.toArray(new ContactPhoneDTO[this.contactPhonesList.size()]);
		// result
		result.setContact(contactProvider);
		result.setContactPosition(contactProviderPosition);
		result.setPhones(contactPhones);
		return result;
	}

	private String validateData()
	{
		String result = null;

		if ((this.txt_Name.getValue() != null && this.txt_Name.getValue().trim().length() > 0)
		&& (this.txt_LastName.getValue() != null && this.txt_LastName.getValue().trim().length() > 0)
		&& (this.txt_Email.getValue() != null && this.txt_Email.getValue().trim().length() > 0)
		&& (this.contactPositionsList != null && this.contactPositionsList.size() > 0))
		{

			if (this.txt_LastName.getValue() == null || this.txt_LastName.getValue().trim().length() < 1)
			{
				result = I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_lastname");
			}
			else if (this.txt_Name.getValue() == null || this.txt_Name.getValue().trim().length() < 1)
			{
				result = I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_name");
			}
			else if (this.isExistingEmailContactProvider(this.txt_Email.getValue()))
			{
				result = I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_existing_mail");
			}
			else if (!BbrUtils.getInstance().checkEmailAddress(this.txt_Email.getValue()))// ->
			{
				result = I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_LOGIN, "valid_mail");
			}

		}
		else
		{

			if (this.contactPositionsList != null && !(this.contactPositionsList.size() > 0))// ->
			{
				result = I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_LOGIN, "valid_position");
			}
			else if (this.txt_LastName.getValue() == null || this.txt_LastName.getValue().trim().length() < 1)
			{
				result = I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_lastname");
			}
			else if (this.txt_Name.getValue() == null || this.txt_Name.getValue().trim().length() < 1)
			{
				result = I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_name");
			}
			else if (this.isExistingEmailContactProvider(this.txt_Email.getValue()))
			{
				result = I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_existing_mail");
			}
			else if (!BbrUtils.getInstance().checkEmailAddress(this.txt_Email.getValue()))// ->
			{
				result = I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_LOGIN, "valid_mail");
			}
			else
			{
				// -> Campos vacíos.
				result = I18NManager.getI18NString(createEditProviderContactBuilder.bbrUIParent, BbrUtilsResources.RES_GENERIC, "msg_missing_fields");
			}
		}
		return result;
	}

	private boolean isExistingEmailContactProvider(String email)
	{
		if (this.contactSelected.getContact() != null && this.contactSelected.getContact().getEmail().equalsIgnoreCase(email))
		{
			return false;
		}
		return this.createEditProviderContactBuilder.createdEmailsList.contains(email);
	}

	private void doUpdatePositionsAndPhoneTypesBy(ContactPhoneTypeDTO[] providerPhones, ContactProviderPositionDTO[] providerPositions)
	{
		if (this.isShowingPendingPositions)
		{
			this.initializePendingPositions(providerPositions);
		}
		this.initializeContactPositions(providerPositions);
	}

	private void initializeProviderContactPhones(List<ContactPhoneDTO> contactPhonesList, ContactDataDTO contactSelected, boolean isEdit)
	{
		if (contactSelected != null)
		{
			if (isEdit)
			{
				ContactPhoneDTO[] provider = contactSelected.getContact().getPhones();
				contactPhonesList = Arrays.stream(provider).collect(Collectors.toList());
			}
		}
		this.setContactPhonesGridDataProvider(contactPhonesList);
	}

	private void setContactPhonesGridDataProvider(List<ContactPhoneDTO> contactPhonesList)
	{
		this.contactPhonesList = contactPhonesList;
		ListDataProvider<ContactPhoneDTO> dataprovider = new ListDataProvider<>(this.contactPhonesList);
		this.dgd_Phones.setDataProvider(dataprovider);
	}

	private void initializePendingPositions(ContactProviderPositionDTO[] positions)
	{
		List<ContactProviderPositionDTO> obligatoryPositions = new ArrayList<>();
		obligatoryPositions = this.getObligatoryPositions(positions);
		this.setPendingPositionsGridDataProvider(obligatoryPositions);
		this.initializePendingPositionsGridColumns();
	}

	private void initializeContactPositions(ContactProviderPositionDTO[] positions)
	{
		// COMBOBOX // Pasar ésta variable al combo
		List<ContactProviderPositionDTO> resultList = Arrays.stream(positions).collect(Collectors.toList());
		this.allPositionsList = this.orderListByObligatory(resultList);

		// POSITIONS ASSIGNED
		this.contactPositionsList = this.isEdit ? this.getSelectedPositions(this.contactSelected.getPositionsDetail()) : new ArrayList<>();

		this.setContactPositionsGridDataProvider(this.contactPositionsList);
	}

	private List<ContactProviderPositionDTO> getObligatoryPositions(ContactProviderPositionDTO[] positions)
	{
		List<ContactProviderPositionDTO> positionsData = new ArrayList<>();
		if (positions != null)
		{
			positionsData = Arrays.stream(positions).filter(p -> p.getObligatory()).collect(Collectors.toList());
			positionsData = this.getPendingMandatoryPositions(this.getBbrUIParent(), Arrays.asList(positions));
			positionsData = this.orderListByObligatory(positionsData);
		}

		return positionsData;
	}

	private List<ContactProviderPositionDTO> getPendingMandatoryPositions(BbrUI bbrUI, List<ContactProviderPositionDTO> positionsData)
	{
		ProviderContactOperationInfo providerContactOperationInfo = new ProviderContactOperationInfo(bbrUI);
		List<PositionDTO> obligatoryPoisitionList = providerContactOperationInfo.getObligatoryPositionsList();
		if (obligatoryPoisitionList != null)
		{
			List<String> obligatoryPositionsNames = obligatoryPoisitionList.stream().map(p -> p.getName()).collect(Collectors.toList());
			positionsData = positionsData.stream().filter(p -> obligatoryPositionsNames.contains(p.getName())).collect(Collectors.toList());
		}
		else
		{
			positionsData = new ArrayList<>();
		}
		return positionsData;
	}

	private List<ContactProviderPositionDTO> getSelectedPositions(ContactProviderPositionDTO[] positions)
	{
		List<ContactProviderPositionDTO> positionsData = new ArrayList<>();
		positionsData = Arrays.stream(positions).filter(p -> p.getSelected()).collect(Collectors.toList());
		positionsData = this.orderListByObligatory(positionsData);
		return positionsData;
	}

	private void setContactPositionsGridDataProvider(List<ContactProviderPositionDTO> positions)
	{
		this.contactPositionsList = this.orderListByObligatory(positions);

		ListDataProvider<ContactProviderPositionDTO> dataprovider = new ListDataProvider<>(this.contactPositionsList);
		this.dgd_ContactPositions.setDataProvider(dataprovider);
	}

	private void addToContactPositionsGrid(ContactProviderPositionDTO... position)
	{
		for (ContactProviderPositionDTO p : position)
		{
			if (!this.contactPositionsList.contains(p))
			{
				this.contactPositionsList.add(p);
			}
		}
		this.refreshContactPositionsGrid();
	}

	private void removeFromContactPositionsGrid(ContactProviderPositionDTO... position)
	{
		this.contactPositionsList.removeAll(Arrays.asList(position));
		this.refreshContactPositionsGrid();
	}

	private void refreshContactPositionsGrid()
	{
		this.dgd_ContactPositions.getDataProvider().refreshAll();
		this.dgd_ContactPositions.recalculateColumnWidths();
	}

	private void setPendingPositionsGridDataProvider(List<ContactProviderPositionDTO> positions)
	{
		this.pendingPositionsList = this.orderListByObligatory(positions);

		ListDataProvider<ContactProviderPositionDTO> dataprovider = new ListDataProvider<>(this.pendingPositionsList);
		this.dgd_PendingPositions.setDataProvider(dataprovider);
	}

	private void addToPendingPositionsGrid(ContactProviderPositionDTO... position)
	{
		if (isShowingPendingPositions)
		{
			for (ContactProviderPositionDTO p : position)
			{
				if (!this.pendingPositionsList.contains(p) && (p.getObligatory() != null && p.getObligatory()))
				{
					this.pendingPositionsList.add(p);
				}
			}
			this.refreshPendingPositionsGrid();
		}
	}

	private void refreshPendingPositionsGrid()
	{
		this.dgd_PendingPositions.getDataProvider().refreshAll();
		this.dgd_PendingPositions.recalculateColumnWidths();
	}

	private void removeFromPendingPositionsGrid(ContactProviderPositionDTO... position)
	{
		this.pendingPositionsList.removeAll(Arrays.asList(position));
		this.refreshPendingPositionsGrid();
	}

	private void addToPhoneGrid(ContactPhoneDTO... phone)
	{
		for (ContactPhoneDTO p : phone)
		{
			if (!this.contactPhonesList.contains(p))
			{
				this.contactPhonesList.add(p);
			}
		}
		this.refreshPhonesGrid();
	}

	private void removeFromPhonesGrid(ContactPhoneDTO... phone)
	{
		this.contactPhonesList.removeAll(Arrays.asList(phone));

		this.refreshPhonesGrid();
	}

	private void refreshPhonesGrid()
	{
		this.dgd_Phones.getDataProvider().refreshAll();
		this.dgd_Phones.recalculateColumnWidths();
	}

	private List<ContactProviderPositionDTO> orderListByObligatory(List<ContactProviderPositionDTO> positions)
	{
		if (!positions.isEmpty() && positions.size() >= 2)
		{
			positions.sort((o1, o2) -> (o1.getObligatory() == null || o2.getObligatory() == null) ? -1 : o2.getObligatory().compareTo(o1.getObligatory()));
		}

		return positions;
	}

	// private EditContactProviderInfoResultDTO executeService(BbrUI
	// bbrUIParent)
	// {
	// EditContactProviderInfoResultDTO result = new
	// EditContactProviderInfoResultDTO();
	//
	// Long ctpkey = this.contactSelected.getContact() != null ?
	// this.contactSelected.getContact().getId() : -1L;
	// Long prkey = this.getBbrUIParent().getUser().getEnterpriseId();
	// try
	// {
	// result =
	// EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(this.getBbrUIParent()).getEditContactProviderInfo(ctpkey,
	// prkey);
	// }
	// catch (BbrUserException e)
	// {
	// AppUtils.getInstance().doLogOut(e.getMessage(), bbrUIParent);
	// }
	// catch (BbrSystemException e)
	// {
	// e.printStackTrace();
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	//
	// return result;
	// }

	private BaseResultDTO executeCreateService(BbrUI bbrUIParent)
	{
		BaseResultDTO result = null;
		this.initParam = this.getInitParam();
		this.contactLogData = this.getContactLogData();
		try
		{
			if (initParam != null)
			{
				result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUIParent).addProviderContact(initParam,
				bbrUIParent.getUser().getEnterpriseId(), contactLogData);
			}
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

	private BaseResultDTO executeEditService(BbrUI bbrUIParent)
	{
		BaseResultDTO result = null;
		this.initParam = this.getInitParam();
		this.contactLogData = this.getContactLogData();
		try
		{
			if (initParam != null)
			{

				result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUIParent).updateProviderContact(initParam,
				bbrUIParent.getUser().getEnterpriseId(), contactLogData);
			}
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

	private ContactLogDataDTO getContactLogData()
	{
		ContactLogDataDTO result = new ContactLogDataDTO();
		result.setName(this.getBbrUIParent().getUser().getName());
		result.setLastname(this.getBbrUIParent().getUser().getLastName());
		result.setFecha(LocalDateTime.now());
		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	public static class CreateEditProviderContactBuilder
	{
		private Set<String>				createdEmailsList	= null;
		private BbrUI					bbrUIParent		= null;
		private ContactDataDTO			contactSelected	= null;
		private ContactPhoneTypeDTO[]		companyPhoneTypes	= null;
		private ContactProviderPositionDTO[]	providerPositions	= null;

		public CreateEditProviderContactBuilder withProviderPositions(ContactProviderPositionDTO[] providerPositions)
		{
			this.providerPositions = providerPositions;
			return this;
		}

		public CreateEditProviderContactBuilder withCompanyPhoneTypes(ContactPhoneTypeDTO[] companyPhoneTypes)
		{
			this.companyPhoneTypes = companyPhoneTypes;
			return this;
		}

		public CreateEditProviderContactBuilder withContactSelected(ContactDataDTO contactSelected)
		{
			this.contactSelected = contactSelected;
			return this;
		}

		public CreateEditProviderContactBuilder withCreatedEmailsList(Set<String> createdEmailsList)
		{
			this.createdEmailsList = createdEmailsList;
			return this;
		}

		public CreateEditProviderContact build(BbrUI bbrUIParent)
		{
			this.bbrUIParent = bbrUIParent;
			return new CreateEditProviderContact(this);
		}

	}
}
