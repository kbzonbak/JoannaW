package bbr.b2b.regional.logistic.deliveries.report.classes;

public class UpdateDODeliveryWSInitParamDTO {

	private String codigoportal;
	private String rutproveedor;
	private Long numeroOrden;
	private String nuevoestado;
	private String fechaEntrega;	//ddmmyyyy
	private String comentario;
	private String nombrereceptor;
	private String rutreceptor;
	private String actaentrega;
	private DirectOrderDetailsWSInitParamDTO[] detalles;

	public String getCodigoportal() {
		return codigoportal;
	}

	public void setCodigoportal(String codigoportal) {
		this.codigoportal = codigoportal;
	}

	public String getRutproveedor() {
		return rutproveedor;
	}

	public void setRutproveedor(String rutproveedor) {
		this.rutproveedor = rutproveedor;
	}

	public Long getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(Long numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public String getNuevoestado() {
		return nuevoestado;
	}

	public void setNuevoestado(String nuevoestado) {
		this.nuevoestado = nuevoestado;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public String getNombrereceptor() {
		return nombrereceptor;
	}

	public void setNombrereceptor(String nombrereceptor) {
		this.nombrereceptor = nombrereceptor;
	}

	public String getRutreceptor() {
		return rutreceptor;
	}

	public void setRutreceptor(String rutreceptor) {
		this.rutreceptor = rutreceptor;
	}

	public String getActaentrega() {
		return actaentrega;
	}

	public void setActaentrega(String actaentrega){
		this.actaentrega = actaentrega;
	}

	public DirectOrderDetailsWSInitParamDTO[] getDetalles() {
		return detalles;
	}

	public void setDetalles(DirectOrderDetailsWSInitParamDTO[] detalles) {
		this.detalles = detalles;
	}
}
