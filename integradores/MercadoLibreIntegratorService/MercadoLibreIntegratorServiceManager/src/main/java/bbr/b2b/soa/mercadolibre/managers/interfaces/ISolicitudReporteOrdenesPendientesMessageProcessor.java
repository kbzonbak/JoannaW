package bbr.b2b.soa.mercadolibre.managers.interfaces;

import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes;

public interface ISolicitudReporteOrdenesPendientesMessageProcessor {

	/**
	 * Procesa la solicitud de reporte de ordenes pendientes, informando las Ordenes con flag "enviado" en false
	 * 
	 * @param solicitudProveedoresOrdenesPendientes
	 *            Los datos de la solicitud, incluyendo el proveedor
	 */
	public void processMessage(SolicitudProveedoresOrdenesPendientes solicitudProveedoresOrdenesPendientes);

}
