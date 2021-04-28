package bbr.b2b.portal.classes.jms.facade.interfaces;

import java.util.Collection;

import javax.ejb.Local;

import cl.bbr.core.classes.basics.BbrUser;

@Local
public interface WebSocketUserOnlineUserDTOCacheDAOLocal {
	public BbrUser get(Long key); 
	public void put(Long key, BbrUser value); 
	public void remove(Long key);
	public Collection<BbrUser> values();
	public int size();
}
