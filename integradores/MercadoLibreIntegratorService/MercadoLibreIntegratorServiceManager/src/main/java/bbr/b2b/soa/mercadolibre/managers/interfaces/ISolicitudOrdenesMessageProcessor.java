package bbr.b2b.soa.mercadolibre.managers.interfaces;

import bbr.b2b.b2blink.logistic.xml.SolicitudOrdenes.SolicitudOrdenes;

public interface ISolicitudOrdenesMessageProcessor {

	/**
	 * Procesa la solicitud de Ordenes pendientes para la empresa informada, obteniendo los detalles de notificación
	 * asociados al tópico "ORDERS" (orders_v2) y generando un mensaje de OC Interno por cada uno de estos y enviándolos
	 * por mensajería de colas a SOA
	 * 
	 * @param solicitud
	 *            Los datos de solicitud, incluyendo el código del proveedor
	 */
	public void processMessage(SolicitudOrdenes solicitud);

}
