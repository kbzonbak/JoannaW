package bbr.b2b.portal.components.modules.fep.binders;

import com.vaadin.data.BeanPropertySet;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.PropertySet;

public class BinderComponentPair<T, K>
{
	private Binder<T>	binder		= null;
	private HasValue<?>	component	= null;
	private K			data		= null;

	@SuppressWarnings("rawtypes")
	public BinderComponentPair(Class<T> type, HasValue component, K data)
	{
		super();

		PropertySet<T> ps = BeanPropertySet.get(type);
		this.binder = Binder.withPropertySet(ps);
		this.component = component;
		this.setData(data);
	}

	public Binder<T> getBinder()
	{
		return binder;
	}

	public void setBinder(Binder<T> binder)
	{
		this.binder = binder;
	}

	@SuppressWarnings("rawtypes")
	public HasValue getComponent()
	{
		return component;
	}

	public void setComponent(HasValue<?> component)
	{
		this.component = component;
	}

	public K getData()
	{
		return data;
	}

	public void setData(K data)
	{
		this.data = data;
	}

}
