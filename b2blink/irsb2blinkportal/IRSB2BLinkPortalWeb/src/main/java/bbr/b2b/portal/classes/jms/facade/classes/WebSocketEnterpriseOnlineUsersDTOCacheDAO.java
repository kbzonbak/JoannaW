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

import bbr.b2b.portal.classes.jms.facade.interfaces.WebSocketEnterpriseOnlineUsersDTOCacheDAOLocal;
import cl.bbr.core.classes.basics.BbrUser;

@Stateless(name = "dao/WebSocketEnterpriseOnlineUsersDTOCacheDAO")
public class WebSocketEnterpriseOnlineUsersDTOCacheDAO implements WebSocketEnterpriseOnlineUsersDTOCacheDAOLocal{

	@Resource(lookup="java:jboss/infinispan/websocket-enterprise-onlineusersdto-container")
	private CacheContainer container; 		
	
	// EN ESTE MAPA SE MANTENDRA EL CODIGO DE EMPRESA ASOCIADO A UNA LISTA DE ONLINEUSERDTO 
	private static Cache<String, Map<Long, BbrUser>> enterpriseOnlineUsersDTOMap = null;	
	
	@PostConstruct
	private void initCache() {
		enterpriseOnlineUsersDTOMap = container.getCache();
	}

	public List<BbrUser> get(String key) {		
		Map<Long, BbrUser> onlineUserMap = enterpriseOnlineUsersDTOMap.get(key);
		if(onlineUserMap != null)
			return new ArrayList<BbrUser>(onlineUserMap.values());
		else
			return null;
	}

	public void put(String key, BbrUser value) {
		Map<Long, BbrUser> onlineMap = enterpriseOnlineUsersDTOMap.get(key);
		
		if (onlineMap == null)
			onlineMap = new HashMap<Long, BbrUser>();
		
		onlineMap.put(value.getId(), value);			
		enterpriseOnlineUsersDTOMap.put(key, onlineMap);				
	}
	
	public void remove(String key) {		
		enterpriseOnlineUsersDTOMap.remove(key);
	}
	
	public void removeUser(String key, BbrUser user) {
		Map<Long, BbrUser> onlineMap = enterpriseOnlineUsersDTOMap.get(key);
		if (onlineMap != null)
			onlineMap.remove(user.getId());
		enterpriseOnlineUsersDTOMap.put(key, onlineMap);		
	}
	
	public Collection<Map<Long, BbrUser>> values() {
		return enterpriseOnlineUsersDTOMap.values();
	}
	
	public int size() {
		return enterpriseOnlineUsersDTOMap.size();
	}
}
