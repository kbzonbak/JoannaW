package bbr.b2b.portal.classes.jms.facade.classes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cl.bbr.core.interfaces.BroadcastReceiver;

public class WebSocketClientDAO{

	// EN ESTE MAPA SE MANTIENEN LAS SESIONES WEBSOCKET ACTIVAS POR SERVIDOR, ASOCIADAS AL SESSIONID
	private static Map<Integer, BroadcastReceiver> sessionMap = Collections.synchronizedMap(new HashMap<Integer, BroadcastReceiver>());

	public static Map<Integer, BroadcastReceiver> getSessionMap() {
		System.out.println("Usuarios Locales: " + sessionMap.keySet());
		return sessionMap;
	}

	public static void setSessionMap(Map<Integer, BroadcastReceiver> sessionMap) {
		WebSocketClientDAO.sessionMap = sessionMap;
	}
	
	public static BroadcastReceiver getSessionById(Integer sessionId){
		return sessionMap.get(sessionId);
	}
	
	public static void putSessionById(Integer sessionId, BroadcastReceiver session){
		System.out.println("Usuario Local: " + sessionId);
		sessionMap.put(sessionId, session);
	}	
	
	public static Boolean containsSessionById(Integer sessionId){
		System.out.println("Verificando usuario local: " + sessionId + " - " + sessionMap.containsKey(sessionId));
		return sessionMap.containsKey(sessionId);
	}
	
	public static void removeSessionById(Integer sessionId){
		sessionMap.remove(sessionId);
	}	
}
