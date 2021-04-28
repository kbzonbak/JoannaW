package bbr.b2b.portal.classes.jms.managers.interfaces;

import java.util.List;

import javax.ejb.Local;

import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.messaging.BbrMessage;
import cl.bbr.core.interfaces.BroadcastReceiver;

/**
 * Clase encargada de manejar la mensajería.
 * @author DSU
 * @author EBO
 *
 */
@Local
public interface AppMessagesMngrLocal extends IGenericEJBInterface
{
	/**
	 * Retorna una lista de OnlineUserDTO.
	 * Esta lista corresponde a todos los usuarios que son visible por perfil y empresa
	 * para el usuario actual.
	 * <p><b>Busca en las estructuras de cache compartida.</b></p>
	 * @param user Usuario actual.
	 * @param includeUser Si true retorna en la lista al usuario actual.
	 * @return List de OnlineUserDTO.
	 * @author      DSU
	 * @author      EBO
	 */
	public List<BbrUser> getAllAvailableUsersByUserId(BbrUser user, Boolean includeUser);
	
	
	/**
	 * Retorna una lista de OnlineUserDTO.
	 * Esta lista corresponde a todos los usuarios conectados.
	 * <b>Busca en las estructuras de cache compartida</b>.
	 * @param includeUser Si true retorna en la lista al usuario actual.
	 * @return List de OnlineUserDTO.
	 * @author      DSU
	 * @author      EBO
	 */
	public List<BbrUser> getAllAvailableUsers() ;
	
	
	/** 
	 * Retorna una lista de BroadcastReceiver.
	 * Esta lista corresponde a todas las sesiones correspondiente a los usuarios
	 * que son visible por perfil y empresa para el usuario actual.
	 * <p><b>Busca en las estructuras de cache compartida.</b></p>
	 * @param user Usuario actual.
	 * @param includeUser Si true retorna en la lista al usuario actual.
	 * @return List de javax.websocket.Session.
	 * @author      DSU
	 * @author      EBO
	 */
	public List<BroadcastReceiver> getAllAvailableUserSessionsByUserId(BbrUser user, Boolean includeUser);
	
	
	/** 
	 * Retorna una BroadcastReceiver.
	 * Esta sesion corresponde al usuario especificado.
	 * <b>Busca en las estructuras de cache compartida</b>.
	 * @param usersId Usuario especificado.
	 * @return 	javax.websocket.Session.
	 * @author      DSU
	 * @author      EBO
	 */
	public BroadcastReceiver getUserSessionByUserId(Long userId);
	
	/** 
	 * Retorna una lista de BroadcastReceiver.
	 * Esta lista corresponde a todas las sesiones 
	 * correspondiente a los usuarios especificados.
	 * <p><b>Busca en las estructuras de cache compartida.</b></p>
	 * @param usersIds Usuarios especificados.
	 * @return List de javax.websocket.Session.
	 * @author      DSU
	 * @author      EBO
	 */
	public List<BroadcastReceiver> getAllUserSessionsByUserId(Long[] usersIDs);
	
	
	/** 
	 * Agrega un usuario a las estructuras de chache
	 * compartidas por los servidores, así como a 
	 * una estructura local.
	 * @param session 	BroadcastReceiver del usuario.
	 * @param user 		Usuario.
	 * @author      DSU
	 * @author      EBO
	 */
	public void addOnlineUserToLists(BroadcastReceiver session, BbrUser user);
	
	
	/** 
	 * Elimina un usuario a las estructuras de chache
	 * compartidas por los servidores, así como de 
	 * una estructura local.
	 * @param session 	BroadcastReceiver del usuario.
	 * @return usuario eliminado (OnlineUserDTO).
	 * @author      DSU
	 * @author      EBO
	 */
	public BbrUser removeOnlineUserToLists(Long userId);
	
	
	public void sendBbrMessageToTopic(BbrMessage bbrMessage);
	
	
	/** 
	 * Envía un mensaje a todos los usuarios que son visible por perfil y empresa
	 * para el usuario actual.
	 * <p><b>Este mensaje es enviado a los usuario determinados por su session.</b></p>
	 * @param onlineuser Usuario actual (OnlineUserDTO).
	 * @param bbrmessage Mensaje a enviar (BbrMessage).
	 * @param includeUser si true le envía el mensaje al usuario actual(Boolean).
	 * @author      DSU
	 * @author      EBO
	 */
	public void sendMessageToAllVisibleUsers(BbrMessage bbrmessage, Boolean includeUser);
	
	
	/** 
	 * Envía un mensaje a todos los usuarios especificados.
	 * <p><b>Este mensaje es enviado a los usuario determinados por su session.</b></p>
	 * @param userIds Ids de los usuarios especificados (Long[]).
	 * @param bbrmessage Mensaje a enviar (BbrMessage).
	 * @author      DSU
	 * @author      EBO
	 */
	public void sendMessageToUsers(Long[] userIds, BbrMessage bbrmessage);

	/** 
	 * Envía un mensaje a un usuario especificado.
	 * <p><b>Este mensaje es enviado al determinados por su session.</b></p>
	 * @param userId Id del usuario especificado (Long).
	 * @param bbrmessage Mensaje a enviar (BbrMessage).
	 * @author      DSU
	 * @author      EBO
	 */
	public void sendMessageToUser(Long userId, BbrMessage bbrmessage);
	
	
	/** 
	 * Envía un mensaje a todos los usuarios.
	 * <p><b>Este mensaje es enviado a los usuario determinados por su session.</b></p>
	 * @param bbrmessage Mensaje a enviar (BbrMessage).
	 * @author      DSU
	 * @author      EBO
	 */
	public void sendMessageToAllOnlineUsers(BbrMessage bbrmessage) ;
	

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
	public List<BroadcastReceiver> getAllAvailableUserSessions();
	
	
	
}
