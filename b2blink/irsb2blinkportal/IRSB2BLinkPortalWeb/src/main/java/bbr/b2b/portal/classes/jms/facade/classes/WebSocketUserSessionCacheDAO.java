package bbr.b2b.portal.classes.jms.facade.classes;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import bbr.b2b.portal.classes.jms.facade.interfaces.WebSocketUserSessionCacheDAOLocal;

@Stateless(name = "dao/WebSocketUserSessionCacheDAO")
public class WebSocketUserSessionCacheDAO implements WebSocketUserSessionCacheDAOLocal{

	@Resource(lookup="java:jboss/infinispan/websocket-user-session-container")
	private CacheContainer container; 		
	
	// EN ESTE MAPA SE MANTENDRA EL ID DEL USUARIO ASOCIADO A UN SESSIONID DE SESION WEBSOCKET  
	private static Cache<Long, Integer> userSessionMap = null;	
	
	@PostConstruct
	private void initCache() {
		userSessionMap = container.getCache();
	}

	public Integer get(Long key) {
		return userSessionMap.get(key);
	}

	public void put(Long key, Integer value) {
		userSessionMap.put(key, value);		
	}
	
	public void remove(Long key) {
		userSessionMap.remove(key);
	}
	
	public Collection<Integer> values() {
		return userSessionMap.values();
	}
	
	public boolean contains(Long key){
		return userSessionMap.containsKey(key);
	}
	
	public int size() {
		return userSessionMap.size();
	}
}
