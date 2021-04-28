package bbr.b2b.portal.classes.jms.facade.interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import cl.bbr.core.classes.basics.BbrUser;

@Local
public interface WebSocketEnterpriseOnlineUsersDTOCacheDAOLocal {
	public List<BbrUser> get(String key); 
	public void put(String key, BbrUser value); 
	public void remove(String key);
	public void removeUser(String key, BbrUser user);
	public Collection<Map<Long, BbrUser>> values();
	public int size();
}
