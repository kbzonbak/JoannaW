package bbr.b2b.soa.mercadolibre.managers.interfaces;

import bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden.NotificacionReciboOrden;

public interface INotificacionReciboOrdenMessageProcessor {

	/**
	 * Procesa la notificación de recibo de Orden, obteniendo el detalle de notificación asociado y del tópico "ORDERS"
	 * (orders_v2) y marcándolo como recibido
	 * 
	 * @param notificacionReciboOrden
	 *            Los datos del recibo, incluyendo
	 */
	public void processMessage(NotificacionReciboOrden notificacionReciboOrden);

}
