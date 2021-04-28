package bbr.b2b.logistic.buyorders.data.dto;

public class OrderProductDetailDTO {

	private String ocnumber;
	private Integer pos;
	private String codproducto;
	private String codprodproveedor;
	private String descripcion;
	private String umc;
	private Integer umbxumc;
	private Double costounitario;
	private Integer solicitado;
	private Double costofinal;
	
	public String getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(String ocnumber) {
		this.ocnumber = ocnumber;
	}
	public Integer getPos() {
		return pos;
	}
	public void setPos(Integer pos) {
		this.pos = pos;
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
	public String getUmc() {
		return umc;
	}
	public void setUmc(String umc) {
		this.umc = umc;
	}
	public Integer getUmbxumc() {
		return umbxumc;
	}
	public void setUmbxumc(Integer umbxumc) {
		this.umbxumc = umbxumc;
	}
	public Double getCostounitario() {
		return costounitario;
	}
	public void setCostounitario(Double costounitario) {
		this.costounitario = costounitario;
	}
	public Integer getSolicitado() {
		return solicitado;
	}
	public void setSolicitado(Integer solicitado) {
		this.solicitado = solicitado;
	}
	public Double getCostofinal() {
		return costofinal;
	}
	public void setCostofinal(Double costofinal) {
		this.costofinal = costofinal;
	}
	
	
	
	

}
