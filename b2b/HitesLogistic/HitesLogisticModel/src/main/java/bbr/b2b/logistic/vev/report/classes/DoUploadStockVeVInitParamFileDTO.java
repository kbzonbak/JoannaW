package bbr.b2b.logistic.vev.report.classes;


import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DoUploadStockVeVInitParamFileDTO extends BaseResultDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7526013989429901777L;
	private String filename;
	private String proveedor;
	private String fecha;
	private String formatoFecha;
	private String usuario;
	private String tipoUsuario;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
