package bbr.b2b.portal.classes.jms.facade.classes;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import bbr.b2b.portal.classes.jms.facade.interfaces.WebSocketSessionUserCacheDAOLocal;

@Stateless(name = "dao/WebSocketSessionUserCacheDAO")
public class WebSocketSessionUserCacheDAO implements WebSocketSessionUserCacheDAOLocal{

	@Resource(lookup="java:jboss/infinispan/websocket-session-user-container")
	private CacheContainer container; 		
	
	// EN ESTE MAPA SE MANTENDRA EL SESSIONID DE SESION WEBSOCKET ASOCIADO A UN ID DEL USUARIO
	private static Cache<Integer, Long> sessionUserMap = null;	
	
	@PostConstruct
	private void initCache() {
		sessionUserMap = container.getCache();
	}

	public Long get(Integer key) {
		return sessionUserMap.get(key);
	}

	public void put(Integer key, Long value) {
		sessionUserMap.put(key, value);		
	}
	
	public void remove(Integer key) {
		sessionUserMap.remove(key);
	}
	
	public boolean contains(Integer key){
		return sessionUserMap.containsKey(key);
	}
	
	public Collection<Long> values() {
		return sessionUserMap.values();
	}
	
	public int size() {
		return sessionUserMap.size();
	}
}
