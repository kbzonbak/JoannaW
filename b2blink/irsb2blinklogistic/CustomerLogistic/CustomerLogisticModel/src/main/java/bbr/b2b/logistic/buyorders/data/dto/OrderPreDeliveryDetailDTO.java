package bbr.b2b.logistic.buyorders.data.dto;

public class OrderPreDeliveryDetailDTO {
	
	private String codlocal;
	private String nombrelocal;
	private String codproducto;
	private String codprodproveedor;
	private String descripcion;
	private Integer solicitado;
	
	public String getCodlocal() {
		return codlocal;
	}
	public void setCodlocal(String codlocal) {
		this.codlocal = codlocal;
	}
	public String getNombrelocal() {
		return nombrelocal;
	}
	public void setNombrelocal(String nombrelocal) {
		this.nombrelocal = nombrelocal;
	}
	public String getCodproducto() {
		return codproducto;
	}
	public void setCodproducto(String codproducto) {
		this.codproducto = codproducto;
	}
	public String getCodprodproveedor() {
		return codprodproveedor;
	}
	public void setCodprodproveedor(String codprodproveedor) {
		this.codprodproveedor = codprodproveedor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getSolicitado() {
		return solicitado;
	}
	public void setSolicitado(Integer solicitado) {
		this.solicitado = solicitado;
	}
}
