package bbr.b2b.soa.mercadolibre.managers.interfaces;

import java.util.Optional;

import bbr.b2b.soa.mercadolibre.dto.classes.NotificationData;
import bbr.b2b.soa.mercadolibre.entities.Notification;

public interface INotificationManager {

	/**
	 * Obtiene las notificaciones pendientes de procesar e invoca el cliente REST de MELI para obtener el detalle de
	 * cada notificación, almacenándolos en la BD
	 */
	public void requestPendingNotificationDetails();

	/**
	 * Obtiene los detalles de notificación pendientes de informar y envía mensajes de Notificación correspondientes. Si
	 * el tópico de la notificación asociada al detalle es "ORDERS" (orders_v2) se envía un mensaje de tipo
	 * NotificacionOrden
	 */
	public void sendUninformedNotificationDetails();

	/**
	 * Registra el mensaje de notificación recibido desde MELI (via servicio REST en esta app e invocada por la
	 * plataforma MELI) enviándolo al sistema de mensajería de colas para su posterior procesamiento
	 * 
	 * @param jsonBody
	 *            El cuerpo de la notificación, en formato JSON
	 */
	public void saveNotificationMessage(String jsonBody);

	/**
	 * Guarda la notificación en la BD, validando que no se duplique si es que la misma ya existe
	 * 
	 * @param data
	 *            Los datos de la notificación
	 * @return La entidad Notificación guardada en la BD
	 */
	public Optional<Notification> addNotification(NotificationData data);

}
