package bbr.b2b.portal.classes.jms.managers.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import bbr.b2b.portal.classes.jms.constants.ChatProfiles;
import bbr.b2b.portal.classes.jms.facade.classes.WebSocketClientDAO;
import bbr.b2b.portal.classes.jms.facade.interfaces.WebSocketEnterpriseOnlineUsersDTOCacheDAOLocal;
import bbr.b2b.portal.classes.jms.facade.interfaces.WebSocketProfileOnlineUsersDTOCacheDAOLocal;
import bbr.b2b.portal.classes.jms.facade.interfaces.WebSocketSessionUserCacheDAOLocal;
import bbr.b2b.portal.classes.jms.facade.interfaces.WebSocketUserOnlineUserDTOCacheDAOLocal;
import bbr.b2b.portal.classes.jms.facade.interfaces.WebSocketUserSessionCacheDAOLocal;
import bbr.b2b.portal.classes.jms.managers.interfaces.AppMessagesMngrLocal;
import bbr.b2b.portal.classes.jms.messaging.WSMessageProducer;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.messaging.BbrMessage;
import cl.bbr.core.interfaces.BroadcastReceiver;

/**
 * Clase encargada de manejar la mensajería.
 * @author DSU
 * @author EBO
 *
 */
@Stateless(name = "managers/AppMessagesMngr")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AppMessagesMngr implements AppMessagesMngrLocal{

	@EJB
	private WebSocketSessionUserCacheDAOLocal sessionUsercacheDAO;

	@EJB
	private WebSocketUserSessionCacheDAOLocal userSessioncacheDAO;

	@EJB
	private WebSocketUserOnlineUserDTOCacheDAOLocal userOnlineUserDTOCacheDAO;

	@EJB
	private WebSocketEnterpriseOnlineUsersDTOCacheDAOLocal enterpriseOnlineUsersDTOCacheDAO;

	@EJB
	private WebSocketProfileOnlineUsersDTOCacheDAOLocal profileOnlineUsersDTOCacheDAO;

	@Resource(lookup="java:comp/DefaultManagedExecutorService")
	private ManagedExecutorService executor;	
	
	
	@PostConstruct
	private void init(){
		
		try {
			if (executor == null){
				Object umreference = (new InitialContext()).lookup("java:comp/DefaultManagedExecutorService");
				executor = (ManagedExecutorService) PortableRemoteObject.narrow(umreference, ManagedExecutorService.class);
			}
		} catch (NamingException e) {			
			e.printStackTrace();
		}		
	}	
	
	/**
	 * Retorna una lista de BbrUser.
	 * Esta lista corresponde a todos los usuarios que son visible por perfil y empresa
	 * para el usuario actual.
	 * <b>Busca en las estructuras de cache compartida</b>.
	 * @param user Usuario actual.
	 * @param includeUser Si true retorna en la lista al usuario actual.
	 * @return List de BbrUser.
	 * @author      DSU
	 * @author      EBO
	 */
	public List<BbrUser> getAllAvailableUsersByUserId(BbrUser user, Boolean includeUser)
	{
		List<BbrUser> result = new ArrayList<BbrUser>();		

		if (ChatProfiles.ADMINISTRATOR.equalsIgnoreCase(user.getChatProfile()))
		{
			result = new ArrayList<BbrUser>( userOnlineUserDTOCacheDAO.values());
		}
		else
		{
			List<BbrUser> adminUsers = profileOnlineUsersDTOCacheDAO.get(ChatProfiles.ADMINISTRATOR);
			List<BbrUser> enterpriseUsers = enterpriseOnlineUsersDTOCacheDAO.get(user.getEnterpriseCode());

			if (adminUsers != null)
				result.addAll(adminUsers);
			if (enterpriseUsers != null)
				result.addAll(enterpriseUsers);			
		}

		if(!includeUser)
		{
			result.remove(user);
		}
		return result;		
	}

	/**
	 * Retorna una lista de BbrUser.
	 * Esta lista corresponde a todos los usuarios conectados.
	 * <b>Busca en las estructuras de cache compartida</b>.
	 * @param includeUser Si true retorna en la lista al usuario actual.
	 * @return List de BbrUser.
	 * @author      DSU
	 * @author      EBO
	 */
	public List<BbrUser> getAllAvailableUsers()
	{
		List<BbrUser> result = (userOnlineUserDTOCacheDAO.values().size() > 0) ? new ArrayList<BbrUser>(userOnlineUserDTOCacheDAO.values()) : new ArrayList<BbrUser>(); 
		return result;		
	}

	/** 
	 * Retorna una lista de Session.
	 * Esta lista corresponde a todas las sesiones correspondientes a los usuarios
	 * que son visible por perfil y empresa para el usuario actual.
	 * <b>Busca en las estructuras de cache compartida</b>.
	 * @param user Usuario actual.
	 * @param includeUser Si true retorna en la lista al usuario actual.
	 * @return List de javax.websocket.Session.
	 * @author      DSU
	 * @author      EBO
	 */
	public List<BroadcastReceiver> getAllAvailableUserSessionsByUserId(BbrUser user, Boolean includeUser)
	{
		List<BroadcastReceiver> result = new ArrayList<BroadcastReceiver>();
		List<BbrUser> availableUserList = this.getAllAvailableUsersByUserId(user,includeUser);		

		for (BbrUser onlineuser : availableUserList)
		{
			Integer uiId = userSessioncacheDAO.get(onlineuser.getId());
			if (uiId != null)
			{
				BroadcastReceiver session = WebSocketClientDAO.getSessionById(uiId);
				if (session != null)
					result.add(session);
			}			
		}		

		return result;		
	}

	/** 
	 * Retorna una lista de BroadcastReceiver.
	 * Esta lista corresponde a todas las sesiones correspondientes a los usuarios.
	 * <b>Busca en las estructuras de cache compartida</b>.
	 * @param user Usuario actual.
	 * @param includeUser Si true retorna en la lista al usuario actual.
	 * @return List de javax.websocket.Session.
	 * @author      DSU
	 * @author      EBO
	 */
	public List<BroadcastReceiver> getAllAvailableUserSessions()
	{
		List<BroadcastReceiver> result = new ArrayList<BroadcastReceiver>();
		
		List<BbrUser> availableUserList = this.getAllAvailableUsers();		
		
		for (BbrUser onlineuser : availableUserList)
		{
			Integer uiId = userSessioncacheDAO.get(onlineuser.getId());
			if (uiId != null)
			{
				BroadcastReceiver session = WebSocketClientDAO.getSessionById(uiId);
				if (session != null)
					result.add(session);
			}			
		}		
		
		return result;		
	}

	
	/** 
	 * Retorna una lista de BroadcastReceiver.
	 * Esta lista corresponde a todas las sesiones 
	 * correspondiente a los usuarios especificados.
	 * <b>Busca en las estructuras de cache compartida</b>.
	 * @param usersIds Usuarios especificados.
	 * @return List de javax.websocket.Session.
	 * @author      DSU
	 * @author      EBO
	 */
	public List<BroadcastReceiver> getAllUserSessionsByUserId(Long[] usersIds)
	{
		List<BroadcastReceiver> result = new ArrayList<BroadcastReceiver>();

		if(usersIds != null && usersIds.length > 0)
		{
			for (Long userId : usersIds) 
			{
				Integer uiId = userSessioncacheDAO.get(userId);
				if (uiId != null)
				{
					BroadcastReceiver session = WebSocketClientDAO.getSessionById(uiId);
					if (session != null)
						result.add(session);
				}	
			}
		}

		return result;		
	}

	/** 
	 * Retorna una BroadcastReceiver.
	 * Esta sesion corresponde al usuario especificado.
	 * <b>Busca en las estructuras de cache compartida</b>.
	 * @param usersId Usuario especificado.
	 * @return 	javax.websocket.Session.
	 * @author      DSU
	 * @author      EBO
	 */
	public BroadcastReceiver getUserSessionByUserId(Long userId)
	{
		BroadcastReceiver result = null;
		
		if(userId != null && userId >= 0)
		{
			Integer uiId = userSessioncacheDAO.get(userId);
			if (uiId != null)
			{
				result = WebSocketClientDAO.getSessionById(uiId);
			}	
		}
		
		return result;		
	}

	/** 
	 * Agrega un usuario a las estructuras de chache
	 * compartidas por los servidores, así como a 
	 * una estructura local.
	 * @param session 	BroadcastReceiver del usuario.
	 * @param user 		Usuario.
	 * @author      DSU
	 * @author      EBO
	 */
	public void addOnlineUserToLists(BroadcastReceiver session, BbrUser user)
	{

		// Para eventos de reconexion es necesario mantener consistentes las estructuras
		// de usuarios son sesiones, por ende se realiza una simple validación.
		// La idea es que las estructuras Usuario-Sesión y Sesión-Usuario sean 1-1 y esten iguales.
		if (userSessioncacheDAO.contains(user.getId()))
		{
			Integer uiId = userSessioncacheDAO.get(user.getId());
			
			// Eliminamos la sesión asociada al usuario
			sessionUsercacheDAO.remove(uiId);
		}		
		
		// Estructuras en Cache
		Integer uiId = getRandomIdentifier();
		while(true){
			if (sessionUsercacheDAO.contains(uiId))
				uiId = getRandomIdentifier();			
			else 
				break;			
		}
		
		userSessioncacheDAO.put(user.getId(), uiId);
		sessionUsercacheDAO.put(uiId, user.getId());
		userOnlineUserDTOCacheDAO.put(user.getId(), user);				

		enterpriseOnlineUsersDTOCacheDAO.put(user.getEnterpriseCode(), user);
		profileOnlineUsersDTOCacheDAO.put(user.getChatProfile(), user);

		// Estructura Local por Servidor
		WebSocketClientDAO.putSessionById(uiId, session);		
	}
	
	/** 
	 * Elimina un usuario a las estructuras de chache
	 * compartidas por los servidores, así como de 
	 * una estructura local.
	 * @param session 	BroadcastReceiver del usuario.
	 * @return usuario eliminado (BbrUser).
	 * @author      DSU
	 * @author      EBO
	 */
	public BbrUser removeOnlineUserToLists(Long userId)
	{
		BbrUser result = null ;
		Integer uiId 	= userSessioncacheDAO.get(userId);		

		if(userId != null)
		{
			result = userOnlineUserDTOCacheDAO.get(userId);

			userSessioncacheDAO.remove(userId);
			sessionUsercacheDAO.remove(uiId);
			userOnlineUserDTOCacheDAO.remove(userId);
			enterpriseOnlineUsersDTOCacheDAO.removeUser(result.getEnterpriseCode(), result);
			profileOnlineUsersDTOCacheDAO.removeUser(result.getChatProfile(), result);

			// ESTRUCTURA LOCAL POR SERVIDOR
			WebSocketClientDAO.removeSessionById(uiId);
		}

		return result ;
	}

	
	/** 
	 * Envía un mensaje al tópico por defecto.
	 * <p><b>Este mensaje es enviado al tópico para que sea
	 * consumido por cada uno de los servidores suscritos.</b></p>
	 * @param bbrMessage 	Mensaje de tipo bbr.b2b.irsb2blink.portal.messaging.model.classes.BbrMessage.
	 * @author      DSU
	 * @author      EBO
	 */
	public void sendBbrMessageToTopic(BbrMessage bbrMessage)
	{
		try 
		{
			WSMessageProducer.getInstance().putMessage("web-socket", bbrMessage);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	
	/** 
	 * Envía un mensaje a todos los usuarios que son visible por perfil y empresa
	 * para el usuario actual.
	 * <p><b>Este mensaje es enviado a los usuario determinados por su session.</b></p>
	 * @param onlineuser Usuario actual (BbrUser).
	 * @param bbrmessage Mensaje a enviar (BbrMessage).
	 * @param includeUser si true le envía el mensaje al usuario actual(Boolean).
	 * @author      DSU
	 * @author      EBO
	 */
	public void sendMessageToAllVisibleUsers(BbrMessage bbrmessage, Boolean includeUser)
	{
		List<BroadcastReceiver> userSessionList = this.getAllAvailableUserSessionsByUserId(bbrmessage.getSenderBbrUser(),includeUser);

		for (BroadcastReceiver session : userSessionList)
		{
			executor.execute(new Runnable() {
                @Override
                public void run() {
                	session.receiveBroadcast(bbrmessage);
                }
            });						
		}		
	}	

	/** 
	 * Envía un mensaje a todos los usuarios.
	 * <p><b>Este mensaje es enviado a los usuario determinados por su session.</b></p>
	 * @param bbrmessage Mensaje a enviar (BbrMessage).
	 * @author      DSU
	 * @author      EBO
	 */
	public void sendMessageToAllOnlineUsers(BbrMessage bbrmessage)
	{
		List<BroadcastReceiver> userSessionList = this.getAllAvailableUserSessions();
		
		for (BroadcastReceiver session : userSessionList)
		{
			executor.execute(new Runnable() {
                @Override
                public void run() {
                	session.receiveBroadcast(bbrmessage);
                }
            });			
		}		
	}	

	
	/** 
	 * Envía un mensaje a todos los usuarios especificados.
	 * <p><b>Este mensaje es enviado a los usuario determinados por su session.</b></p>
	 * @param userIds Ids de los usuarios especificados (Long[]).
	 * @param bbrmessage Mensaje a enviar (BbrMessage).
	 * @author      DSU
	 * @author      EBO
	 */
	public void sendMessageToUsers(Long[] userIds, BbrMessage bbrmessage)
	{
		List<BroadcastReceiver> userSessionList = this.getAllUserSessionsByUserId(userIds);

		for (BroadcastReceiver session : userSessionList)
		{
			executor.execute(new Runnable() {
                @Override
                public void run() {
                	session.receiveBroadcast(bbrmessage);
                }
            });			
		}		
	}	

	/** 
	 * Envía un mensaje a un usuario especificado.
	 * <p><b>Este mensaje es enviado al usuario determinado por su session.</b></p>
	 * @param userId Id del usuario especificado (Long).
	 * @param bbrmessage Mensaje a enviar (BbrMessage).
	 * @author      DSU
	 * @author      EBO
	 */
	public void sendMessageToUser(Long userId, BbrMessage bbrmessage)
	{
		BroadcastReceiver userSession = this.getUserSessionByUserId(userId);
		
		if(userSession != null)
		{
			executor.execute(new Runnable() {
                @Override
                public void run() {
                	userSession.receiveBroadcast(bbrmessage);
                }
            });				
		}		
	}	
	
	private Integer getRandomIdentifier(){
		Integer key = ThreadLocalRandom.current().nextInt();
		return key;                
    }
}
