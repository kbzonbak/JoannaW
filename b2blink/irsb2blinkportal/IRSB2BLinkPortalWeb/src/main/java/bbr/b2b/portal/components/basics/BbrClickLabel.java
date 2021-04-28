package bbr.b2b.portal.components.basics;

import java.io.Serializable;
import java.util.ArrayList;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.Resource;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;


public class BbrClickLabel extends HorizontalLayout 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5029908210495746267L;
	private Label lbl_Icon = null;
	private Label lbl_Text = null;

	private ArrayList<BbrEventListener> bbrEventsListenerList = new ArrayList<BbrEventListener>();

	public BbrClickLabel(String content, Resource icon) 
	{
		this(content,icon,ContentMode.HTML);
	}

	public BbrClickLabel(String content, Resource icon, ContentMode contentMode) 
	{
		this.setMargin(false);
		this.setSpacing(false);

		this.removeAllComponents();

		this.lbl_Text 	= new Label(content, contentMode);
		this.lbl_Text.addStyleName("bbr-click-label");

		this.lbl_Icon = new Label();
		this.lbl_Icon.setIcon(icon);
		this.lbl_Icon.addStyleName("bbr-click-label");
		this.lbl_Icon.setWidth("21px");
		
		Label lbl_Spacer = new Label(" ");
		lbl_Spacer.setWidth("5px");
		this.setWidth("100%");
		this.addComponents(lbl_Icon,lbl_Spacer,lbl_Text);
		
		this.setExpandRatio(lbl_Text, 1F);
		this.setComponentAlignment(lbl_Icon, Alignment.MIDDLE_LEFT);
		this.setComponentAlignment(lbl_Text, Alignment.MIDDLE_LEFT);
		
		this.addLayoutClickListener((LayoutClickListener & Serializable)e -> clickListener(e));

	}


	private void clickListener(LayoutClickEvent e) 
	{
		if(e.getButton()!= MouseButton.RIGHT)
		{
			BbrEvent event = new BbrEvent(BbrEvent.ITEMS_SELECTED);
			event.setResultObject(this.getData());
			this.dispatchBbrEvent(event);	
		}
	}

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PUBLIC METHODS
	// ****************************************************************************************
	public synchronized void addBbrEventListener(BbrEventListener listener)
	{
		if (bbrEventsListenerList == null) 
		{
			bbrEventsListenerList = new ArrayList<BbrEventListener>();
		}
		if (!bbrEventsListenerList.contains(listener)) 
		{
			bbrEventsListenerList.add(listener);
		}
	}

	public synchronized void removeBbrEventListener(BbrEventListener listener) 
	{
		if (bbrEventsListenerList == null) 
		{
			bbrEventsListenerList = new ArrayList<BbrEventListener>();
		}
		bbrEventsListenerList.remove(listener);
	}

	public synchronized void dispatchBbrEvent(BbrEvent event)
	{
		if (bbrEventsListenerList != null) 
		{
			for (BbrEventListener listener : bbrEventsListenerList) 
			{
				listener.bbrEvent_handler(event);
			}
		}
	}

	// ****************************************************************************************
	// ENDING SECTION 		---->			PUBLIC METHODS
	// ****************************************************************************************
}
