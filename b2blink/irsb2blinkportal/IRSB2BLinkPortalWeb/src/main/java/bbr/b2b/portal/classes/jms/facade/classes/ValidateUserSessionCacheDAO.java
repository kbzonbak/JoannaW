package bbr.b2b.portal.classes.jms.facade.classes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import bbr.b2b.portal.classes.jms.facade.interfaces.ValidateUserSessionCacheDAOLocal;

@Stateless(name = "dao/ValidateUserSessionCacheDAO")
public class ValidateUserSessionCacheDAO implements ValidateUserSessionCacheDAOLocal {
	
	private static final String VALIDATE_CONST = "validate-user-session-dao";

	@Resource(lookup="java:jboss/infinispan/validate-user-session-dao-container")
	private CacheContainer  container; 		
	
	// EN ESTE MAPA SE MANTENDRA EL CODIGO DE PERFIL ASOCIADO A UNA LISTA DFE ONLINEUSERDTO 	
	private static Cache<String, Map<String, LocalDateTime>> lastValidatedCache;	
	
	@PostConstruct
	private void initCache() {
		lastValidatedCache = container.getCache();
	} 
	
	public LocalDateTime get(String sessionId) {		
		Map<String, LocalDateTime> onlineUserMap = lastValidatedCache.get(VALIDATE_CONST);
		if(onlineUserMap != null)
			return onlineUserMap.get(sessionId);
		else
			return null;
	}
	
	public void put(String sessionId, LocalDateTime value) {
		Map<String, LocalDateTime> onlineMap = lastValidatedCache.get(VALIDATE_CONST);
		
		if (onlineMap == null)
			onlineMap = new ConcurrentHashMap<String, LocalDateTime>();
		
		onlineMap.put(sessionId, value);	
		lastValidatedCache.put(VALIDATE_CONST, onlineMap);
	}
	
	public int size() {
		return lastValidatedCache.size();
	}
	
	public LocalDateTime remove(String sessionId) {		
		Map<String, LocalDateTime> onlineUserMap = lastValidatedCache.get(VALIDATE_CONST);
		if(onlineUserMap != null)
			return onlineUserMap.remove(sessionId);
		else
			return null;
	}
	
	
	
}
