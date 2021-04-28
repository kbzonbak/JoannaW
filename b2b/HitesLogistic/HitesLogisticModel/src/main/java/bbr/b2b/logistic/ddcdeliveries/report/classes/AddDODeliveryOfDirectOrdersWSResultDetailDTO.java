package bbr.b2b.logistic.ddcdeliveries.report.classes;

import java.io.Serializable;

public class AddDODeliveryOfDirectOrdersWSResultDetailDTO implements Serializable {

	private Long numeroOrden;

	private String codigo;

	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(Long numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public AddDODeliveryOfDirectOrdersWSResultDetailDTO(Long numeroOrden, String codigo, String descripcion) {
		this.numeroOrden = numeroOrden;
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

}
