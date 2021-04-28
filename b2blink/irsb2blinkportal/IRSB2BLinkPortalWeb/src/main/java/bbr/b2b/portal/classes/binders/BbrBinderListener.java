package bbr.b2b.portal.classes.binders;

import java.io.Serializable;

public interface BbrBinderListener<T> extends Serializable
{
	public void bbrBinderEvent_handler(BbrBinderEvent<T> event); 
}
