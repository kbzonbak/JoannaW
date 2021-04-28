package bbr.b2b.logistic.rest.mapping.putAvailableStock;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class AvailableStockPutDataWSContainer implements Serializable {

	private String proveedor;
	private String fecha;
	private String formatoFecha;
	private String usuario;
	private String tipoUsuario;
	
	private List<Inventory> productos;

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
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

	public List<Inventory> getProductos() {
		return productos;
	}

	public void setProductos(List<Inventory> productos) {
		this.productos = productos;
	}


	
	
}
