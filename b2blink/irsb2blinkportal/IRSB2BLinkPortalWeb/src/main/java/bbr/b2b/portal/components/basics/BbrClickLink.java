package bbr.b2b.portal.components.basics;

import java.io.Serializable;
import java.util.ArrayList;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;


public class BbrClickLink extends HorizontalLayout 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2597154708631869321L;

	private Label lbl_Text = null;

	private ArrayList<BbrEventListener> bbrEventsListenerList = new ArrayList<BbrEventListener>();

	public BbrClickLink(String content) 
	{
		this(content,ContentMode.HTML);
	}

	public BbrClickLink(String content, ContentMode contentMode) {
		this.removeAllComponents();

		this.lbl_Text 	= new Label(content, contentMode);
		this.lbl_Text.addStyleName("login-link-button");
		lbl_Text.setWidthUndefined();
		this.addComponents(lbl_Text);
		this.setComponentAlignment(lbl_Text, Alignment.MIDDLE_CENTER);
		this.setWidth("100%");
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
