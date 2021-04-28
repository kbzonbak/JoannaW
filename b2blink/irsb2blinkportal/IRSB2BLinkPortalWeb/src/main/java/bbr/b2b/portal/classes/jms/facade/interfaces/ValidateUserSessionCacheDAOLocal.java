package bbr.b2b.portal.classes.jms.facade.interfaces;

import java.time.LocalDateTime;

import javax.ejb.Local;

import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;

@Local
public interface ValidateUserSessionCacheDAOLocal extends IGenericEJBInterface {
	
	public LocalDateTime get(String sessionId); 
	public void put(String sessionId, LocalDateTime value); 
	public int size();
	public LocalDateTime remove(String sessionId);
}
