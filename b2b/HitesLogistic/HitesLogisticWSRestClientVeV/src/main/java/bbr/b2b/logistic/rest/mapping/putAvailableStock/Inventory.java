package bbr.b2b.logistic.rest.mapping.putAvailableStock;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;


@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class Inventory implements Serializable {

	private String codigo;
	private Integer diario;
	private Integer disponible;
	private String bodega;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Integer getDiario() {
		return diario;
	}
	public void setDiario(Integer diario) {
		this.diario = diario;
	}
	public Integer getDisponible() {
		return disponible;
	}
	public void setDisponible(Integer disponible) {
		this.disponible = disponible;
	}
	public String getBodega() {
		return bodega;
	}
	public void setBodega(String bodega) {
		this.bodega = bodega;
	}
	

}
