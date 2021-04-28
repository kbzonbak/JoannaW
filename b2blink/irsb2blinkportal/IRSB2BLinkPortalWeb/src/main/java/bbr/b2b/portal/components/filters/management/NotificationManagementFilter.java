package bbr.b2b.portal.components.filters.management;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.PublishingTypeDTO;
import bbr.b2b.users.report.classes.PublishingTypeResultDTO;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class NotificationManagementFilter extends BbrFilterContainer implements Button.ClickListener
{

	
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = 5712042403953778345L;

	private BbrComboBox<PublishingTypeDTO> cbx_NotificactionsTypes = null ;
	private Button btn_FilterSearch = null;
	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	public NotificationManagementFilter(BbrModule parentModule)
	{
		super(parentModule);
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void buttonClick(ClickEvent event) 
	{
		PublishingTypeDTO selectedValue = cbx_NotificactionsTypes.getSelectedValue();
		if(selectedValue != null)
		{
			BbrFilterEvent filterEvent = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);
			filterEvent.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "notification_type")) ;
			filterEvent.setSecondaryCaption("- " + selectedValue.getName());
			filterEvent.setResultObject(selectedValue);
			dispatchBbrFilterEvent(filterEvent);	
		}
	}
	// ****************************************************************************************
	// ENDING SECTION 8		---->			OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PUBLIC METHODS
	// ****************************************************************************************
	public void initializeView() 
	{
		//Notification Panel
		Label lblNotificationHeader = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "notifications_title"));
		lblNotificationHeader.setWidth("100%");
		lblNotificationHeader.addStyleName("bbr-filter-label-header");
		lblNotificationHeader.addStyleName("bbr-panel-space");

		Label lblNotificactionType = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "notification_type"));
		lblNotificactionType.setWidth("100px");
		cbx_NotificactionsTypes = this.getNotificactionsTypeComboBox();
		HorizontalLayout pnlNotificactionsTypes = new HorizontalLayout();
		pnlNotificactionsTypes.setWidth("100%");
		pnlNotificactionsTypes.addComponents(lblNotificactionType,cbx_NotificactionsTypes);
		pnlNotificactionsTypes.setExpandRatio(cbx_NotificactionsTypes, 1F);
		pnlNotificactionsTypes.addStyleName("bbr-panel-space");

		VerticalLayout pnlNotificactions = new VerticalLayout();
		pnlNotificactions.setWidth("400px");
		pnlNotificactions.addStyleName("bbr-filter-sections");
		pnlNotificactions.addStyleName("bbr-panel-space");
		pnlNotificactions.setSpacing(false);
		pnlNotificactions.addComponents(lblNotificationHeader,pnlNotificactionsTypes);


		//Filter Search Button
		btn_FilterSearch = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "generate_report"), this);
		btn_FilterSearch.setIcon(VaadinIcons.CHECK_CIRCLE_O);
		btn_FilterSearch.setStyleName("btn-filter-search");
		btn_FilterSearch.setClickShortcut(KeyCode.ENTER);

		VerticalLayout pnlSearchButton = new VerticalLayout();
		pnlSearchButton.setWidth("400px");
		pnlSearchButton.addStyleName("bbr-panel-space");
		pnlSearchButton.setSpacing(false);
		pnlSearchButton.setMargin(false);
		pnlSearchButton.addComponents(btn_FilterSearch);
		pnlSearchButton.setComponentAlignment(btn_FilterSearch, Alignment.BOTTOM_RIGHT);

		VerticalLayout mainLayout = new VerticalLayout();		
		mainLayout.setSizeFull();

		VerticalLayout pnlFill = new VerticalLayout();
		pnlFill.setMargin(false);

		btn_FilterSearch = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "generate_report"), this);
		btn_FilterSearch.setIcon(VaadinIcons.CHECK_CIRCLE_O);		
		btn_FilterSearch.setStyleName("btn-filter-search");
		mainLayout.setMargin(false);
		mainLayout.addComponent(pnlNotificactions);		
		mainLayout.addComponent(pnlSearchButton);
		mainLayout.addComponent(pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);
		
		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("110px");
		this.addComponent(mainLayout);
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PRIVATE METHODS
	// ****************************************************************************************
	private BbrComboBox<PublishingTypeDTO> getNotificactionsTypeComboBox() 
	{
		BbrComboBox<PublishingTypeDTO> result = null;
		try 
		{
			PublishingTypeResultDTO reportResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).getPublishingTypes();
			if(reportResult != null && reportResult.getPublishingtypes() != null && reportResult.getPublishingtypes().length > 0)
			{
				result = new BbrComboBox<PublishingTypeDTO>(reportResult.getPublishingtypes());
				result.setItemCaptionGenerator(PublishingTypeDTO::getName);
				result.setTextInputAllowed(false);
				result.setEmptySelectionAllowed(false);
				result.selectFirstItem();

				result.setWidth("270px");
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
			e.printStackTrace();
		}

		return result;
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			PRIVATE METHODS
	// ****************************************************************************************
}
