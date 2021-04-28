package bbr.b2b.portal.components.modules.users.management.providers_contacts;

import java.io.Serializable;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.management.NewPhoneProviderContactDTO;
import bbr.b2b.portal.components.basics.BbrButtonGroup;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ContactPhoneTypeDTO;
import cl.bbr.core.classes.container.BbrHFieldContainer;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class NewPhoneProviderContact extends BbrWindow
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long									serialVersionUID	= -2658188071720585696L;
	public static final String									BTN_ACCEPT			= "btnAccept";
	public static final String									BTN_CANCEL			= "btnCancel";
	private BbrTextField										txt_Phone			= null;
	private BbrComboBox<ContactPhoneTypeDTO>					cbx_PhoneTypes		= null;
	private ContactPhoneTypeDTO[]								companyPhoneTypes	= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public NewPhoneProviderContact(BbrUI parent, ContactPhoneTypeDTO[] companyPhoneTypes)
	{
		super(parent);
		this.companyPhoneTypes = companyPhoneTypes;
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
		String labelWidth = "80px";

		this.cbx_PhoneTypes = new BbrComboBox<>();
		this.cbx_PhoneTypes.setItemCaptionGenerator(ContactPhoneTypeDTO::getType);
		this.cbx_PhoneTypes.setTextInputAllowed(false);
		this.cbx_PhoneTypes.setEmptySelectionAllowed(false);
		BbrHFieldContainer<BbrComboBox<ContactPhoneTypeDTO>, ContactPhoneTypeDTO, ContactPhoneTypeDTO> fieldTypes = new BbrHFieldContainer<>(
				this.getBbrUIParent(), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_type"), this.cbx_PhoneTypes);
		fieldTypes.setLabelWidth(labelWidth);
		fieldTypes.setSizeFull();

		this.txt_Phone = new BbrTextField();
		this.txt_Phone.addStyleName("bbr-fields");
		this.txt_Phone.focus();
		BbrHFieldContainer<BbrTextField, String, String> fieldPhone = new BbrHFieldContainer<>(
				this.getBbrUIParent(), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_phone"), this.txt_Phone);
		fieldPhone.setLabelWidth(labelWidth);
		fieldPhone.setSizeFull();

		BbrButtonGroup bbrGroupButton = new BbrButtonGroup.BbrGroupButtonBuilder(this.getBbrUIParent())
				.withPrimaryButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"))
				.withPrimaryButtonListener((ClickListener & Serializable) (e) -> this.btn_Accept_clickHandler())
				.withCancelButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"))
				.withCancelButtonListener((ClickListener & Serializable) (e) -> this.btn_Cancel_clickHandler()).build();
		bbrGroupButton.setStyleName("bbr-buttons-panel");
		bbrGroupButton.getPrimaryButton().setClickShortcut(KeyCode.ENTER);

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(fieldTypes, fieldPhone, bbrGroupButton);
		mainLayout.setComponentAlignment(fieldTypes, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(fieldPhone, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(bbrGroupButton, Alignment.BOTTOM_CENTER);
		mainLayout.addStyleName("bbr-margin-windows");

		// Main Windows
		this.setWidth("350px");
		this.setHeight("200px");
		this.setResizable(false);
		String winTitle = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_phone");
		this.setCaption(winTitle);
		this.setContent(mainLayout);
		this.addStyleName("win-details");

		this.doUpdatePhones(this.companyPhoneTypes);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================
	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void btn_Accept_clickHandler()
	{
		this.getEvent(BTN_ACCEPT);
		this.close();
	}

	private void btn_Cancel_clickHandler()
	{
		this.getEvent(BTN_CANCEL);
		this.close();
	}

	private void getEvent(String eventType)
	{
		BbrEvent alertResponseEvent = new BbrEvent(eventType);
		if (eventType.equals(BTN_ACCEPT))
		{
			NewPhoneProviderContactDTO initParam = this.getInitParam();
			alertResponseEvent.setResultObject(initParam);
		}
		this.dispatchBbrEvent(alertResponseEvent);
	}

	private NewPhoneProviderContactDTO getInitParam()
	{
		NewPhoneProviderContactDTO result = new NewPhoneProviderContactDTO();
		result.setContactPhoneType(this.cbx_PhoneTypes != null ? this.cbx_PhoneTypes.getSelectedValue() : new ContactPhoneTypeDTO());
		result.setNumber(this.txt_Phone != null ? this.txt_Phone.getValue() : "");
		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void doUpdatePhones(ContactPhoneTypeDTO[] companyPhoneTypes)
	{
		if (companyPhoneTypes.length > 0)
		{
			this.cbx_PhoneTypes.setItems(companyPhoneTypes);
			this.cbx_PhoneTypes.selectFirstItem();
		}
		else
		{
			this.cbx_PhoneTypes.setEnabled(false);
		}
	}

	// private ContactPhoneTypeResultDTO executeService(BbrUI bbrUIParent)
	// {
	// ContactPhoneTypeResultDTO result = null;
	// try
	// {
	// result =
	// EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(this.getBbrUIParent()).getPhoneTypes();
	// }
	// catch (BbrUserException e)
	// {
	// AppUtils.getInstance().doLogOut(e.getMessage(),
	// this.getBbrUIParent());
	// }
	// catch (BbrSystemException e)
	// {
	// e.printStackTrace();
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// return result;
	// }
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
