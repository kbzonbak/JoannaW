
package bbr.b2b.logistic.rest.mapping.putAvailableStock;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown=true)
public class SingleStockPutDataWSContainer implements Serializable {

	private String proveedor;
	private LocalDateTime fecha;
	private String formatoFecha;
	private String usuario;
	private String tipoUsuario;
	
	private Inventory productos;

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getFormatoFecha() {
		return formatoFecha;
	}

	public void setFormatoFecha(String formatoFecha) {
		this.formatoFecha = formatoFecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Inventory getProductos() {
		return productos;
	}

	public void setProductos(Inventory productos) {
		this.productos = productos;
	}


	
	
}
