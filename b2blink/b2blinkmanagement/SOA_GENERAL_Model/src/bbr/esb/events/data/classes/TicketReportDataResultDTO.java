package bbr.esb.events.data.classes;

import java.util.Date;

public class TicketReportDataResultDTO  {

	Long nrosolicitud;
	String tiposolicitud;
	String cliente;
	Date fechasolicitud;
	Date fechaproceso;
	String referencia;
	String estado;
	String adjunto;
	
	public Long getNrosolicitud() {
		return nrosolicitud;
	}
	public void setNrosolicitud(Long nrosolicitud) {
		this.nrosolicitud = nrosolicitud;
	}
	public String getTiposolicitud() {
		return tiposolicitud;
	}
	public void setTiposolicitud(String tiposolicitud) {
		this.tiposolicitud = tiposolicitud;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public Date getFechasolicitud() {
		return fechasolicitud;
	}
	public void setFechasolicitud(Date fechasolicitud) {
		this.fechasolicitud = fechasolicitud;
	}
	public Date getFechaproceso() {
		return fechaproceso;
	}
	public void setFechaproceso(Date fechaproceso) {
		this.fechaproceso = fechaproceso;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getAdjunto() {
		return adjunto;
	}
	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}
}


	
