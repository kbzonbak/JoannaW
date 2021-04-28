package bbr.b2b.extractors.walmart.dto;

import java.util.Date;

public class OrderReceptionDTO {
	
	private String numeroOc;
	private Date fechaCancelacionOc;
	private double numeroLineaOC;
	private double numeroTienda;
	private String nombreTienda;
	private double idConsumidor;
	private double numeroArticulo;
	private String descripcion;
	private String upc_emcd;
	private String upc_empro;
	private String upc;
	private String descripcionUpc;
	private double total;
	private double emcd;
	private double total_emcd;
	
	public OrderReceptionDTO() {
	}
	
	public String getNumeroOc() {
		return numeroOc;
	}
	public void setNumeroOc(String numeroOc) {
		this.numeroOc = numeroOc;
	}
	public Date getFechaCancelacionOc() {
		return fechaCancelacionOc;
	}
	public void setFechaCancelacionOc(Date fechaCancelacionOc) {
		this.fechaCancelacionOc = fechaCancelacionOc;
	}
	public double getNumeroLineaOC() {
		return numeroLineaOC;
	}
	public void setNumeroLineaOC(double numeroLineaOC) {
		this.numeroLineaOC = numeroLineaOC;
	}
	public double getNumeroTienda() {
		return numeroTienda;
	}
	public void setNumeroTienda(double numeroTienda) {
		this.numeroTienda = numeroTienda;
	}
	public String getNombreTienda() {
		return nombreTienda;
	}
	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}
	public double getIdConsumidor() {
		return idConsumidor;
	}
	public void setIdConsumidor(double idConsumidor) {
		this.idConsumidor = idConsumidor;
	}
	public double getNumeroArticulo() {
		return numeroArticulo;
	}
	public void setNumeroArticulo(double numeroArticulo) {
		this.numeroArticulo = numeroArticulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUpc_emcd() {
		return upc_emcd;
	}
	public void setUpc_emcd(String upc_emcd) {
		this.upc_emcd = upc_emcd;
	}
	public String getUpc_empro() {
		return upc_empro;
	}
	public void setUpc_empro(String upc_empro) {
		this.upc_empro = upc_empro;
	}
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getDescripcionUpc() {
		return descripcionUpc;
	}
	public void setDescripcionUpc(String descripcionUpc) {
		this.descripcionUpc = descripcionUpc;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getEmcd() {
		return emcd;
	}
	public void setEmcd(double emcd) {
		this.emcd = emcd;
	}
	public double getTotal_emcd() {
		return total_emcd;
	}
	public void setTotal_emcd(double total_emcd) {
		this.total_emcd = total_emcd;
	}
	
	

}
