package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class DirectOrdersWSInitParamDTO implements Serializable {

	private Long numero;

	private String fechaCompromiso; //ddmmaaaa

	private DirectOrderDetailsWSInitParamDTO[] detalles;

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getFechaCompromiso() {
		return fechaCompromiso;
	}

	public void setFechaCompromiso(String fechaCompromiso) {
		this.fechaCompromiso = fechaCompromiso;
	}

	public DirectOrderDetailsWSInitParamDTO[] getDetalles() {
		return detalles;
	}

	public void setDetalles(DirectOrderDetailsWSInitParamDTO[] detalles) {
		this.detalles = detalles;
	}
}
