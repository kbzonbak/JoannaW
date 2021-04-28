package bbr.b2b.portal.classes.binders;

import java.util.ArrayList;

import com.vaadin.ui.AbstractOrderedLayout;

import cl.bbr.core.components.basics.BbrUI;

public abstract class BbrAbstractBinder<T, K>
{
	private ArrayList<BbrBinderListener<T>>	bbrEventsListenerList	= new ArrayList<BbrBinderListener<T>>();

	public T								modelObject				= null;

	private BbrUI							bbrUI					= null;

	public BbrUI getBbrUI()
	{
		return bbrUI;
	}

	public void setBbrUI(BbrUI bbrUI)
	{
		this.bbrUI = bbrUI;
	}

	public BbrAbstractBinder(BbrUI bbrUI)
	{
		this.setBbrUI(bbrUI);
	}

	protected abstract void initializeControls();

	public abstract void clearView();

	public abstract void updateBinders();

	public abstract void removeRegistrations();

	public abstract AbstractOrderedLayout getLayout();

	public synchronized void addBbrBinderEventListener(BbrBinderListener<T> listener)
	{
		if (bbrEventsListenerList == null)
		{
			bbrEventsListenerList = new ArrayList<BbrBinderListener<T>>();
		}
		if (!bbrEventsListenerList.contains(listener))
		{
			bbrEventsListenerList.add(listener);
		}
	}

	public synchronized void removeBbrBinderEventListener(BbrBinderListener<T> listener)
	{
		if (bbrEventsListenerList == null)
		{
			bbrEventsListenerList = new ArrayList<BbrBinderListener<T>>();
		}
		bbrEventsListenerList.remove(listener);
	}

	public synchronized void dispatchBbrBinderEvent(BbrBinderEvent<T> event)
	{
		if (bbrEventsListenerList != null)
		{
			for (BbrBinderListener<T> listener : bbrEventsListenerList)
			{
				listener.bbrBinderEvent_handler(event);
			}
		}
	}
}
