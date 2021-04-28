package bbr.b2b.portal.modules.users.contact_zone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.modules.users.contact_zone.SendEmailToRetailContact;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ContactB2BArrayResultDTO;
import bbr.b2b.users.report.classes.ContactB2BDTO;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.utilities.BbrLambdaFunctions;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;

public class ListRetailsContacts extends BbrModule implements BbrWorkExecutor
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long					serialVersionUID	= 7027502352762624910L;

	private ArrayList<ContactB2BDTO>			dsContacts			= null;
	private ArrayList<ContactB2BDTO>			dsAreas				= null;

	private BbrAdvancedGrid<ContactB2BDTO>		dgd_Contacts		= null;
	private BbrAdvancedGrid<ContactB2BDTO>		dgd_Area			= null;

	private VerticalLayout						mainLayout			= null;

	private Button								btn_SendMail		= null;

	private final int							DEFAULT_PAGE_NUMBER	= 1;
	private final int							MAX_ROWS_NUMBER		= 20;

	private BbrWork<ContactB2BArrayResultDTO>	reportWork			= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public ListRetailsContacts(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		// Reporte
		// Barra de Herramientas
		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");

		leftButtonsBar.addStyleName("toolbar-layout");

		Button btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> refreshReport_clickHandler(e));
		btn_Refresh.addStyleName("toolbar-button");

		btn_SendMail = new Button("", BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_MailEdit.png"));
		btn_SendMail.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "send_retail_contact_email"));
		btn_SendMail.addClickListener((ClickListener & Serializable) e -> sendMail_clickHandler(e));
		btn_SendMail.addStyleName("toolbar-button");

		HorizontalLayout rightButtonsBar = new HorizontalLayout();

		Button btn_Help = this.getHelpButton();

		rightButtonsBar.addComponents(btn_SendMail, btn_Refresh, btn_Help);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName("toolbar-layout");

		rightButtonsBar.setComponentAlignment(btn_Refresh, Alignment.MIDDLE_RIGHT);

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsBar, rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setSpacing(true);
		toolBar.setMargin(false);
		toolBar.setExpandRatio(leftButtonsBar, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Grilla
		dgd_Contacts = new BbrAdvancedGrid<>(this.getUser().getLocale());
		dgd_Contacts.setSortable(false);
		this.initializeContactsGridColumns();
		dgd_Contacts.addStyleName("report-grid");
		dgd_Contacts.setSizeFull();
		dgd_Contacts.addSelectionListener((SelectionListener<ContactB2BDTO> & Serializable) e -> selection_gridHandler(e));

		dgd_Area = new BbrAdvancedGrid<>(this.getUser().getLocale());
		dgd_Area.setSortable(false);
		this.initializeAreasGridColumns();
		dgd_Area.addStyleName("report-grid");
		dgd_Area.setHeight("100%");
		dgd_Area.setWidth("250px");

		dgd_Area.addSelectionListener(new SelectionListener<ContactB2BDTO>()
		{

			private static final long serialVersionUID = 6072245606903888048L;

			@Override
			public void selectionChange(SelectionEvent<ContactB2BDTO> event)
			{
				if (event != null && event.getAllSelectedItems() != null && event.getAllSelectedItems().size() > 0)
				{
					updateContactsDataProvider(event.getAllSelectedItems().iterator().next());
				}
				else
				{
					updateContactsDataProvider(null);
				}

			}
		});

		HorizontalLayout gridsLayout = new HorizontalLayout();
		gridsLayout.setSizeFull();
		gridsLayout.setSpacing(true);

		gridsLayout.addComponents(dgd_Area, dgd_Contacts);
		gridsLayout.setExpandRatio(dgd_Contacts, 1F);

		this.updateButtons(false);

		mainLayout = new VerticalLayout();
		mainLayout.addStyleName("report-layout-no-filter");
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.addComponents(toolBar, gridsLayout);
		mainLayout.setExpandRatio(gridsLayout, 1F);

		this.addComponents(mainLayout);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, ContactB2BArrayResultDTO>()
		{
			@Override
			public ContactB2BArrayResultDTO apply(Object t)
			{
				return executeService(ListRetailsContacts.this.getBbrUIParent());
			}
		});

		this.startWaiting();

		this.executeBbrWork(reportWork);

	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doUpdateReport(result, sender);
			}
		}
		else
		{
			ListRetailsContacts senderReport = (ListRetailsContacts) sender;

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
	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.startWaiting();

		this.executeBbrWork(reportWork);
	}

	private void emailSent_handler(BbrEvent e)
	{
		String successful = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation");
		this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), successful);
	}

	private void selection_gridHandler(SelectionEvent<ContactB2BDTO> e)
	{
		this.updateButtons(true);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private void initializeContactsGridColumns()
	{
		dgd_Contacts.addColumn(contact -> nameContact_function(contact)).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "name"));
		dgd_Contacts.addColumn(ContactB2BDTO::getPosition).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position"));
		dgd_Contacts.addColumn(ContactB2BDTO::getPhone).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "phone"));
	}

	private void initializeAreasGridColumns()
	{
		dgd_Area.addColumn(ContactB2BDTO::getDepartment).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "area"));
	}

	private String nameContact_function(ContactB2BDTO contact)
	{
		String result = "";
		if (contact != null && contact.getName() != null && contact.getLastname() != null)
		{
			result = contact.getName() + " " + contact.getLastname();
		}

		return result;
	}

	private void updateContactsDataProvider(ContactB2BDTO selectedArea)
	{
		ArrayList<ContactB2BDTO> filterDataSource = new ArrayList<>();
		if (selectedArea != null && dsContacts != null && dsContacts.size() > 0)
		{

			List<ContactB2BDTO> areaList = dsContacts
					.stream()
					.filter(contact -> selectedArea.getDepartment().equals(contact.getDepartment()))
					.collect(Collectors.toList());

			if (areaList != null && areaList.size() > 0)
			{
				filterDataSource = new ArrayList<>(areaList);
			}
		}

		ListDataProvider<ContactB2BDTO> dpContact = new ListDataProvider<>(filterDataSource);
		dgd_Contacts.setDataProvider(dpContact);
	}

	private void sendMail_clickHandler(ClickEvent e)
	{
		if (this.dgd_Contacts.getSelectedItems() != null && this.dgd_Contacts.getSelectedItems().size() > 0)
		{
			ContactB2BDTO selectedContact = this.dgd_Contacts.getSelectedItems().iterator().next();

			BbrWindow winSendMail = null;

			winSendMail = new SendEmailToRetailContact(this.getBbrUIParent(), selectedContact);

			winSendMail.initializeView();
			winSendMail.addBbrEventListener((BbrEventListener & Serializable) event -> emailSent_handler(event));
			winSendMail.open(true);
		}
	}

	private ContactB2BArrayResultDTO executeService(BbrUI bbrUI)
	{
		ContactB2BArrayResultDTO result = null;

		try
		{
			// Start Tracking

			this.getTimingMngr().startTimer();
			result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUI).getRetailerContacts(this.DEFAULT_PAGE_NUMBER, this.MAX_ROWS_NUMBER, false);
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

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		ListRetailsContacts senderReport = (ListRetailsContacts) sender;

		if (result != null)
		{
			ContactB2BArrayResultDTO reportResult = (ContactB2BArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				senderReport.dsContacts = new ArrayList<>(Arrays.asList(reportResult.getContactB2BDTOs()));
				senderReport.dsAreas = new ArrayList<>(senderReport.dsContacts.stream().filter(BbrLambdaFunctions.distinctByKey(p -> p.getDepartment())).collect(Collectors.toList()));

				ListDataProvider<ContactB2BDTO> dpAreas = new ListDataProvider<>(senderReport.dsAreas);
				senderReport.dgd_Area.setDataProvider(dpAreas);
				if (senderReport.dsAreas != null && senderReport.dsAreas.size() > 0)
				{
					senderReport.dgd_Area.select(senderReport.dsAreas.get(0));
				}

				this.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());

			}

			else
			{
				errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
			}
		}

		if (errordescription.length() > 0)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}

		this.stopWaiting();
	}

	private void updateButtons(Boolean isSelectionEvent)
	{
		this.btn_SendMail.setEnabled(this.dgd_Contacts.getSelectedItems() != null && !this.dgd_Contacts.getSelectedItems().isEmpty());

		if (!isSelectionEvent)
		{
			Boolean isEmptyGrid = this.dgd_Contacts.isEmpty();

			this.btn_SendMail.setEnabled(!isEmptyGrid);
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
