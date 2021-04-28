package bbr.b2b.portal.classes.jms.facade.interfaces;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface WebSocketSessionUserCacheDAOLocal {
	public Long get(Integer key); 
	public void put(Integer key, Long value); 
	public void remove(Integer key);
	public Collection<Long> values();
	public int size();
	public boolean contains(Integer key);
}
