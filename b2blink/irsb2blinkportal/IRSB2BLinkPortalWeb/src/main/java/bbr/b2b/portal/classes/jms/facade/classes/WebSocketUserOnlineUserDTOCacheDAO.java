package bbr.b2b.portal.classes.jms.facade.classes;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import bbr.b2b.portal.classes.jms.facade.interfaces.WebSocketUserOnlineUserDTOCacheDAOLocal;
import cl.bbr.core.classes.basics.BbrUser;

@Stateless(name = "dao/WebSocketUserOnlineUserDTOCacheDAO")
public class WebSocketUserOnlineUserDTOCacheDAO implements WebSocketUserOnlineUserDTOCacheDAOLocal{

	@Resource(lookup="java:jboss/infinispan/websocket-user-onlineuserdto-container")
	private CacheContainer container; 		
	
	// EN ESTE MAPA SE MANTENDRA EL ID DEL USUARIO ASOCIADO A UN BbrUser 
	private static Cache<Long, BbrUser> userOnlineUserDTOMap = null;	
	
	@PostConstruct
	private void initCache() {
		userOnlineUserDTOMap = container.getCache();
	}

	public BbrUser get(Long key) {
		return userOnlineUserDTOMap.get(key);
	}

	public void put(Long key, BbrUser value) {
		userOnlineUserDTOMap.put(key, value);		
	}
	
	public void remove(Long key) {
		userOnlineUserDTOMap.remove(key);
	}
	
	public Collection<BbrUser> values() {
		return userOnlineUserDTOMap.values();
	}
	
	public int size() {
		return userOnlineUserDTOMap.size();
	}
}
