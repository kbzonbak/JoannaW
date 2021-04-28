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
import bbr.b2b.users.report.classes.AssignedEventsArrayResultDTO;
import bbr.b2b.users.report.classes.EventArrayResultDTO;
import bbr.b2b.users.report.classes.EventDTO;
import bbr.b2b.users.report.classes.RuleTypeDTO;
import bbr.b2b.users.report.classes.UserPublishingRelationshipsInitParamsDTO;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;

public class EditEventsSubscription extends BbrWindow 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = 3247626779164695209L;

	private Long 				publishingId;
	private RuleTypeDTO 		rule;

	private BbrAdvancedGrid<EventDTO> dgd_Events ;

	private ArrayList<EventDTO> events = null;
	private Map<Long,EventDTO> mapEvents = null;

	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	public EditEventsSubscription(BbrUI parent, Long publishingId, RuleTypeDTO rule) 
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
			dgd_Events = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.initializeGridColumns();			
			dgd_Events.addStyleName("report-grid");
			dgd_Events.setHeight("100%");
			dgd_Events.setWidth("100%");
			dgd_Events.setLocale(this.getUser().getLocale());
			dgd_Events.setSelectionMode(SelectionMode.SINGLE);

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
			mainLayout.addComponents(dgd_Events,buttonsPanel);
			mainLayout.addStyleName("bbr-margin-windows");
			mainLayout.setExpandRatio(dgd_Events, 1F);
			mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);

			this.updateAllEvents();
			this.updateAssignedEvents();

			//Main Windows
			this.setWidth(800,Unit.PIXELS);
			this.setHeight(550,Unit.PIXELS);
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
		this.addEventsSuscriptions();
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
		dgd_Events.addColumn(EventDTO::getDescription).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "available_events"));
	}
	
	private void updateAllEvents() 
	{
		try 
		{
			EventArrayResultDTO eventResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getAllEvents();

			if(eventResult != null && eventResult.getEventDtos() != null && eventResult.getEventDtos().length > 0)
			{
				events = new ArrayList<>(Arrays.asList(eventResult.getEventDtos()));
				
				ListDataProvider<EventDTO> dataprovider = new ListDataProvider<>(events);
				dgd_Events.setDataProvider(dataprovider);
				
				mapEvents = new HashMap<>();
				for (EventDTO event : events) 
				{
					mapEvents.put(event.getId(), event);
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

	private void updateAssignedEvents() 
	{
		try 
		{
			AssignedEventsArrayResultDTO eventsResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getAssignedEventsToPublishing(publishingId);

			if(eventsResult != null && eventsResult.getAssignedEvents() != null && eventsResult.getAssignedEvents().length > 0)
			{
				for (EventDTO event : eventsResult.getAssignedEvents()) 
				{
					EventDTO selectedEvent = mapEvents.get(event.getId());
					if(selectedEvent != null)
					{
						dgd_Events.select(selectedEvent);
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


	private Long[] getEventsIds()
	{
		Long[] result = null;

		Set<EventDTO> events = dgd_Events.getSelectedItems();
		int i = 0;
		if(events != null && events.size() > 0)
		{
			result = new Long[events.size()];
			for (EventDTO event : events) 
			{
				result[i] = event.getId();
				i++;
			}
		}

		return result;
	}

	private void addEventsSuscriptions()
	{
		String message = "";
		BaseResultsDTO 	publishingResult = null ;
		try 
		{
			Long[] eventsKeys = this.getEventsIds();

			if(eventsKeys != null && eventsKeys.length > 0)
			{
				UserPublishingRelationshipsInitParamsDTO initParams = new UserPublishingRelationshipsInitParamsDTO();
				
				initParams.setPublishingId(publishingId);
				initParams.setRuleTypeId(rule.getId());
				initParams.setKeys(eventsKeys);
				
				
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
				message = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_event_at_least");
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
