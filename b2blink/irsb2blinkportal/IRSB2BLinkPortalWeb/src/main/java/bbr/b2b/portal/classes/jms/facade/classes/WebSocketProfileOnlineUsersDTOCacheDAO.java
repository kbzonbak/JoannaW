package bbr.b2b.portal.classes.jms.facade.classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import bbr.b2b.portal.classes.jms.facade.interfaces.WebSocketProfileOnlineUsersDTOCacheDAOLocal;
import cl.bbr.core.classes.basics.BbrUser;

@Stateless(name = "dao/WebSocketProfileOnlineUsersDTOCacheDAO")
public class WebSocketProfileOnlineUsersDTOCacheDAO implements WebSocketProfileOnlineUsersDTOCacheDAOLocal{

	@Resource(lookup="java:jboss/infinispan/websocket-profile-onlineusersdto-container")
	private CacheContainer container; 		
	
	// EN ESTE MAPA SE MANTENDRA EL CODIGO DE PERFIL ASOCIADO A UNA LISTA DFE ONLINEUSERDTO 
	private static Cache<String, Map<Long, BbrUser>> profileOnlineUsersDTOMap = null;	
	
	@PostConstruct
	private void initCache() {
		profileOnlineUsersDTOMap = container.getCache();
	}

	public List<BbrUser> get(String key) {		
		Map<Long, BbrUser> onlineUserMap = profileOnlineUsersDTOMap.get(key);
		if(onlineUserMap != null)
			return new ArrayList<BbrUser>(onlineUserMap.values());
		else
			return null;
	}

	public void put(String key, BbrUser value) {
		Map<Long, BbrUser> onlineMap = profileOnlineUsersDTOMap.get(key);
		
		if (onlineMap == null)
			onlineMap = new HashMap<Long, BbrUser>();
		
		onlineMap.put(value.getId(), value);		
		profileOnlineUsersDTOMap.put(key, onlineMap);				
	}
	
	public void remove(String key) {		
		profileOnlineUsersDTOMap.remove(key);
	}
	
	public void removeUser(String key, BbrUser user) {
		Map<Long, BbrUser> onlineMap = profileOnlineUsersDTOMap.get(key);
		if (onlineMap != null)
			onlineMap.remove(user.getId());
		profileOnlineUsersDTOMap.put(key, onlineMap);			
	}
	
	public Collection<Map<Long, BbrUser>> values() {
		return profileOnlineUsersDTOMap.values();
	}
	
	public int size() {
		return profileOnlineUsersDTOMap.size();
	}
}
