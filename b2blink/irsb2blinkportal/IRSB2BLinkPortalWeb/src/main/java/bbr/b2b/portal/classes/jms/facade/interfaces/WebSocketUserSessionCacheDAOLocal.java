package bbr.b2b.portal.classes.jms.facade.interfaces;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface WebSocketUserSessionCacheDAOLocal {
	public Integer get(Long key); 
	public void put(Long key, Integer value); 
	public void remove(Long key);
	public Collection<Integer> values();
	public boolean contains(Long key);
	public int size();
}
